//
// This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2002-2003 The OpenNMS Group, Inc.  All rights reserved.
// OpenNMS(R) is a derivative work, containing both original code, included code and modified
// code that was published under the GNU General Public License. Copyrights for modified 
// and included code are below.
//
// OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
//
// Copyright (C) 1999-2001 Oculan Corp.  All rights reserved.
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.                                                            
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
//       
// For more information contact: 
//      OpenNMS Licensing       <license@opennms.org>
//      http://www.opennms.org/
//      http://www.opennms.com/
//
// RRDDataSource.java,v 1.1.1.1 2001/11/11 17:34:38 ben Exp
//

package org.opennms.netmgt.rrd;
import java.util.List;

import org.opennms.netmgt.collectd.DataSource;
import org.opennms.netmgt.collectd.MibObject;
import org.opennms.netmgt.config.DataCollectionConfigFactory;

import org.apache.log4j.Category;
import org.apache.log4j.Priority;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.protocols.snmp.SnmpSMI;
import org.opennms.protocols.snmp.SnmpSyntax;
import org.opennms.protocols.snmp.SnmpOctetString;
import org.opennms.protocols.snmp.SnmpTimeTicks;


/**
 * This class encapsulates an RRDTool data source. Data source information
 * parsed from the DataCollection.xml file is stored in RRDDataSource objects.
 * 
 * For additional information on RRD and RRDTool see:
 * http://ee-staff.ethz.ch/~oetiker/webtools/rrdtool/
 * 
 * @author <A HREF="mailto:mike@opennms.org">Mike </A>
 * @author <A HREF="http://www.opennms.org/">OpenNMS </A>
 * 
 * @version 1.1.1.1
 * 
 */
public class RRDDataSource extends DataSource {
	private static final int MAX_DS_NAME_LENGTH = 19;
	public static final String RRD_ERROR = "RRD_ERROR";

    /**
     * Defines the list of supported (MIB) object types whic may be mapped to
     * one of the supported RRD data source types. Currently the only two
     * supported RRD data source types are: COUNTER & GAUGE. A simple string
     * comparison is performed against this list of supported types to determine
     * if an object can be represented by an RRD data source. NOTE: String
     * comparison uses String.startsWith() method so "counter32" & "counter64"
     * values will match "counter" entry. Comparison is case sensitive.
     */
    private static final String[] supportedObjectTypes = new String[] { "counter", "gauge", "timeticks", "integer", "octetstring" };

    /**
     * Index of data type in supportedObjectTypes string array.
     */
    private static final int COUNTER_INDEX = 0;

    private static final int GAUGE_INDEX = 1;

    private static final int TIMETICKS_INDEX = 2;

    private static final int INTEGER_INDEX = 3;

    private static final int OCTETSTRING_INDEX = 4;

    /**
     * RRDTool defined Data Source Types NOTE: "DERIVE" and "ABSOLUTE" not
     * currently supported.
     */
    private static final String DST_GAUGE = "GAUGE";

    private static final String DST_COUNTER = "COUNTER";

    // private static final String DST_DERIVE = "DERIVE";
    // private static final String DST_ABSOLUTE = "ABSOLUTE";

    /**
     * Data Source Type. This must be one of the available RRDTool data source
     * type values: GAUGE, COUNTER, DERIVE, or ABSOLUTE
     */
    private String m_type;

    /**
     * Data Source Heartbeat. This is the maximum number of seconds that may
     * pass between updates of this data source before the value of the data
     * source is assumed to be 'Unknown'.
     */
    private int m_heartbeat;

    /**
     * Minimum Expected Range. Together with m_max defines the expected range of
     * the data supplied by this data source. May be set to "U" for 'Unknown'.
     */
    private String m_min;

    /**
     * Maximum Expected Range. Together with m_min defines the expected range of
     * the data supplied by this data source. May be set to "U" for Unknown.
     */
    private String m_max;

	/**
          * @param objectType MIB object type being inquired about
          * @return true if RRDDataSource can  handle the given type, false if it can't
          */
         public static boolean handlesType(String objectType) {
                 return (RRDDataSource.mapType(objectType)!=null);
         }



    /**
     * Static method which takes a MIB object type (counter, counter32,
     * octetstring, etc...) and returns the appropriate RRD data type. If the
     * object type cannot be mapped to an RRD type, null is returned. RRD only
     * supports integer data so MIB objects of type 'octetstring' are not
     * supported.
     * 
     * @param objectType -
     *            MIB object type to be mapped.
     * 
     * @return RRD type string or NULL object type is not supported.
     */
    public static String mapType(String objectType) {
        String rrdType = null;

        // For consistency lower case objecType parameter before comparison
        objectType = objectType.toLowerCase();

        int index;
        for (index = 0; index < supportedObjectTypes.length; index++) {
            if (objectType.startsWith(supportedObjectTypes[index]))
                break;
        }

        switch (index) {
        // counter maps to RRD data source type COUNTER.
        case COUNTER_INDEX:
            rrdType = DST_COUNTER;
            break;
        // gauge, timeticks, and integer types all map to RRD
        // data source type GAUGE.
        case OCTETSTRING_INDEX:
        case TIMETICKS_INDEX:
        case INTEGER_INDEX:
        case GAUGE_INDEX:
            rrdType = DST_GAUGE;
            break;
        // no match, object data type is NOT supported
        default:
            rrdType = null;
            break;
        }
        return rrdType;
    }

    /**
     * Constructor
     */
    public RRDDataSource() {
	super();
        m_type = null;
        m_heartbeat = 600; // 10 minutes
        m_min = "U";
        m_max = "U";
    }

       public RRDDataSource(MibObject obj, String collectionName) {
                super(obj);
                Category log = ThreadCategory.getInstance(getClass());

                // Assign heartbeat using formula (2 * step) and hard code
                // min & max values to "U" ("unknown").
                this.setHeartbeat(
                        2
                                * DataCollectionConfigFactory.getInstance().getStep(
                                        collectionName));

                // Truncate MIB object name/alias if it exceeds the 19 char max for
                // RRD data source names.
                if (this.getName().length() > MAX_DS_NAME_LENGTH) {
                        if (log.isEnabledFor(Priority.WARN))
                                log.warn(
                                        "buildDataSourceList: Mib object name/alias '"
                                                + obj.getAlias()
                                                + "' exceeds 19 char maximum for RRD data source names, truncatin g.");
                        char[] temp = this.getName().toCharArray();
                        this.setName(String.copyValueOf(temp, 0, MAX_DS_NAME_LENGTH));
                }

                // Map MIB object data type to RRD data type
                this.setType(RRDDataSource.mapType(obj.getType()));
                this.m_min = "U";
                this.m_max = "U";

                // Assign the data source object identifier and instance
                if (log.isDebugEnabled()) {
                        log.debug(
                                "buildDataSourceList: ds_name: "
                                        + this.getName()
                                        + " ds_oid: "
                                        + this.getOid()
                                        + "."
                                        + this.getInstance()
                                        + " ds_max: "
                                        + this.getMax()
                                        + " ds_min: "
                                        + this.getMin());
                }

        }


    /**
     * Class copy constructor. Constructs a new object that is an identical to
     * the passed object, however no data is shared between the two objects. Any
     * changes to one will not affect the other.
     * 
     * @param second
     *            The object to make a duplicate of.
     * 
     */
    public RRDDataSource(RRDDataSource second) {
        m_oid = second.m_oid;
        m_instance = second.m_instance;
        m_name = second.m_name;
        m_type = second.m_type;
        m_heartbeat = second.m_heartbeat;
        m_min = second.m_min;
        m_max = second.m_max;
    }

    /**
     * This method is used to assign the object's expected data type.
     * 
     * @param type -
     *            object's data type
     */
    public void setType(String type) {
        m_type = type;
    }

    public void setHeartbeat(int heartbeat) {
        m_heartbeat = heartbeat;
    }

    public void setMin(String minimum) {
        m_min = minimum;
    }

    public void setMax(String maximum) {
        m_max = maximum;
    }

    /**
     * Returns the object's data type.
     * 
     * @return The object's data type
     */
    public String getType() {
        return m_type;
    }

    public int getHeartbeat() {
        return m_heartbeat;
    }

    public String getMin() {
        return m_min;
    }

    public String getMax() {
        return m_max;
    }

    /**
     * Used to get a duplicate of self. The duplicate is identical to self but
     * shares no common data.
     * 
     * @return A newly created copy of self.
     */
    public Object clone() {
        return new RRDDataSource(this);
    }

    /**
     * This method is responsible for returning a String object which represents
     * the content of this RRDDataSource object. Primarily used for debugging
     * purposes.
     * 
     * @return String which represents the content of this RRDDataSource object
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        // Build the buffer
        buffer.append("\n   oid:       ").append(m_oid);
        buffer.append("\n   name: 	 ").append(m_name);
        buffer.append("\n   type:      ").append(m_type);
        buffer.append("\n   heartbeat: ").append(m_heartbeat);
        buffer.append("\n   min:       ").append(m_min);
        buffer.append("\n   max:       ").append(m_max);

        return buffer.toString();
    }
       
	public boolean performUpdate(
		String collectionName,
		String owner,
                String repository,
		String dsName,
		String val) {

	        int step = DataCollectionConfigFactory.getInstance().getStep(collectionName);
	        List rraList = DataCollectionConfigFactory.getInstance().getRRAList(collectionName);
		boolean result=false;
		try {
		        RrdUtils.createRRD(owner, repository, this.getName(), step, this.getType(), this.getHeartbeat(), 
				this.getMin(), this.getMax(), rraList);
	
			RrdUtils.updateRRD(owner, repository, dsName, val);
		} catch (RrdException e) {
			result=true;
		}
		return result;
	}

    public String getStorableValue(SnmpSyntax snmpVar) {
        // RRD only supports the storage of integer data types. If we see a
        // data type other than those listed below an error will be logged
        // and no RRD update will take place.
        // Am I missing any SNMP data types here?
        switch (snmpVar.typeId()) {
        case SnmpSMI.SMI_INTEGER:
            return snmpVar.toString();
        case SnmpSMI.SMI_COUNTER32:
            return snmpVar.toString();
        case SnmpSMI.SMI_COUNTER64:
            return snmpVar.toString();
        case SnmpSMI.SMI_GAUGE32:
            return snmpVar.toString();
        // *NOTE* Same as SnmpSMI.SMI_GAUGE32
        // case SnmpSMI.SMI_UNSIGNED32:
        // dsValue = ((SnmpUInt32)snmpVar).getValue();
        // break;
        case SnmpSMI.SMI_TIMETICKS:
            return "" + (((SnmpTimeTicks) snmpVar).getValue());
        case SnmpSMI.SMI_STRING:
            String dsValue = ((SnmpOctetString) snmpVar).toString();

            // Validate that the octet string value represents an
            // integer/double value, otherwise it can't be stored in the RRD
            // database
            try {
                new Double(dsValue);
                return dsValue;
            } catch (NumberFormatException nfE) {
                throw new IllegalArgumentException("number format exception attempting to convert octet string value '" + dsValue + "' to a numeric value for data source '" + this.getName() + "'");

            }
        default:
            throw new IllegalArgumentException("SNMP value of data source '" + this.getName() + "' is not one of the supported data types by RRD, invalid typeID: " + snmpVar.typeId() + " Valid RRD data types are:  COUNTER, GAUGE, DERIVE, & ABSOLUTE.  Please check content of 'DataCollection.xml' file.");
        }

    }
}

//
// // // This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2002-2003 Blast Internet Services, Inc. All
// rights reserved.
// OpenNMS(R) is a derivative work, containing both original code, included
// code and modified
// code that was published under the GNU General Public License. Copyrights for
// modified
// and included code are below.
//
// OpenNMS(R) is a registered trademark of Blast Internet Services, Inc.
//
// Copyright (C) 1999-2001 Oculan Corp. All rights reserved.
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
//       
// For more information contact:
//      OpenNMS Licensing <license@opennms.org>
//      http://www.opennms.org/
//      http://www.blast.com/
//
//
package org.opennms.netmgt.collectd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Category;
import org.apache.log4j.Priority;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.protocols.snmp.SnmpOctetString;
import org.opennms.protocols.snmp.SnmpSMI;
import org.opennms.protocols.snmp.SnmpSyntax;

/**
 * @author craig.miskell@agresearch.co.nz
 *  
 */
public class StringDataSource extends DataSource {
	
	/**
	 * @param objectType MIB object type being inquired about
	 * @return true if StringDataSource can  handle the given type, false if it can't
	 */
	public static boolean handlesType(String objectType) {
		return (objectType.toLowerCase().equals("string"));
	}

	/**
	 * @param obj mibObject to use as the basis for configuring this datasource
	 */
	public StringDataSource(MibObject obj) {
		super(obj);
		//Nothing else custom to do
	}

        public boolean performUpdate(
		String collectionName,
                String owner,
               String repository,
                String dsName,
                String val) {

		Category log = ThreadCategory.getInstance(getClass());

		boolean error = false;

		Properties props = new Properties();
                File propertiesFile =
                        new File(repository + File.separator + "strings.properties");
		
		//Preload existing data
                if (propertiesFile.exists()) {
                        try {
                                props.load(new FileInputStream(propertiesFile));
                        } catch (Exception e) {
                                //Can't be filenotfound, must be an IOException - we can't
                                // handle that - just quit
                                e.printStackTrace();
                                return true;
                        }
                }
                props.setProperty(this.getName(), val);
                try {
                        props.store(new FileOutputStream(propertiesFile), null);
                } catch (Exception e) {
                        //Ouch, something went wrong that we should mention to the outside world
                        e.printStackTrace();
                        return true;
                }
                return false;
		
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();

		// Build the buffer
		buffer.append("\n   oid:       ").append(m_oid);
		buffer.append("\n   name: 	 ").append(m_name);

		return buffer.toString();
	}

    public String getStorableValue(SnmpSyntax snmpVar) {
	String dsValue;
	if(snmpVar.typeId()==SnmpSMI.SMI_STRING) {
		dsValue = ((SnmpOctetString) snmpVar).toString();
	} else {
		dsValue = snmpVar.toString();
	}
	return dsValue;
    }

}

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
// Tab Size = 8
//

package org.opennms.netmgt.capsd.snmp;

/**
 * The NamedSnmpVar class is used to associate a name for a particular snmp
 * instance with its object identifier. Common names often include ifIndex,
 * sysObjectId, etc al. These names are the names of particular variables as
 * defined by the SMI.
 * 
 * Should the instance also be part of a table, then the column number of the
 * instance is also stored in the object.
 * 
 * @author <A HREF="mailto:weave@oculan.com">Brian Weaver </A>
 * @author <A HREF="mailto:mike@opennms.org">Mike Davidson </A>
 * @author <A HREF="http://www.opennms.org/">OpenNMS </A>
 * 
 * 
 */
final class NamedSnmpVar {
    /**
     * String which contains the Class name of the expected SNMP data type for
     * the object.
     */
    private String m_type;

    /**
     * The class object for the class name stored in the m_type string.
     */
    private Class m_typeClass;

    /**
     * The alias name for the object identifier.
     */
    private String m_name;

    /**
     * The actual object identifer string for the object.
     */
    private String m_oid;

    /**
     * If set then the object identifier is an entry some SNMP table.
     */
    private boolean m_isTabular;

    /**
     * If the instance is part of a table then this is the column number for the
     * element.
     */
    private int m_column;

    //
    // Class strings for valid SNMP data types
    // 
    public static final String SNMPINT32 = "org.opennms.protocols.snmp.SnmpInt32";

    public static final String SNMPUINT32 = "org.opennms.protocols.snmp.SnmpUInt32";

    public static final String SNMPCOUNTER32 = "org.opennms.protocols.snmp.SnmpCounter32";

    public static final String SNMPCOUNTER64 = "org.opennms.protocols.snmp.SnmpCounter64";

    public static final String SNMPGAUGE32 = "org.opennms.protocols.snmp.SnmpGauge32";

    public static final String SNMPTIMETICKS = "org.opennms.protocols.snmp.SnmpTimeTicks";

    public static final String SNMPOCTETSTRING = "org.opennms.protocols.snmp.SnmpOctetString";

    public static final String SNMPOPAQUE = "org.opennms.protocols.snmp.SnmpOpaque";

    public static final String SNMPIPADDRESS = "org.opennms.protocols.snmp.SnmpIPAddress";

    public static final String SNMPOBJECTID = "org.opennms.protocols.snmp.SnmpObjectId";

    public static final String SNMPV2PARTYCLOCK = "org.opennms.protocols.snmp.SnmpV2PartyClock";

    public static final String SNMPNOSUCHINSTANCE = "org.opennms.protocols.snmp.SnmpNoSuchInstance";

    public static final String SNMPNOSUCHOBJECT = "org.opennms.protocols.snmp.SnmpNoSuchObject";

    public static final String SNMPENDOFMIBVIEW = "org.opennms.protocols.snmp.SnmpEndOfMibView";

    public static final String SNMPNULL = "org.opennms.protocols.snmp.SnmpNull";

    /**
     * The class default constructor. The default constructor is disallowed in
     * this class and thus the unsupported operation exception is always thrown
     * by this constructor.
     * 
     * @exception java.lang.UnsupportedOperationException
     *                Always thrown by this constructor.
     */
    private NamedSnmpVar() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("default constructor not supported");
    }

    /**
     * This constructor creates a new instance of the class with the type, alias
     * and object identifier. The instance is not considered to be part of a
     * table.
     * 
     * @param type
     *            The expected SNMP data type of this object.
     * @param alias
     *            The alias for the object identifier.
     * @param oid
     *            The object identifier for the instance.
     */
    NamedSnmpVar(String type, String alias, String oid) {
        m_type = type;
        m_typeClass = null;
        m_name = alias;
        m_oid = oid;
        m_isTabular = false;
        m_column = 0;
    }

    /**
     * This constructor creates a new instance of the class with the type,
     * alias, object identifier, and table column set. The instance is
     * considered to be part of a table and the column is the "instance" number
     * for the table.
     * 
     * @param type
     *            The expected SNMP data type of this object.
     * @param alias
     *            The alias for the object identifier.
     * @param oid
     *            The object identifier for the instance.
     * @param column
     *            The column entry for its table.
     * 
     */
    NamedSnmpVar(String type, String alias, String oid, int column) {
        m_type = type;
        m_typeClass = null;
        m_name = alias;
        m_oid = oid;
        m_isTabular = true;
        m_column = column;
    }

    /**
     * Returns the class name stored in m_type which represents the expected
     * SNMP data type of the object.
     */
    String getType() {
        return m_type;
    }

    /**
     * Returns the class object associated with the class name stored in m_type.
     * 
     * @exception java.lang.ClassNotFoundException
     *                Thrown from this method if forName() fails.
     */
    Class getTypeClass() throws ClassNotFoundException {
        if (m_typeClass == null) {
            m_typeClass = Class.forName(m_type);
        }
        return m_typeClass;
    }

    /**
     * Returns the alias for the object identifier.
     */
    String getAlias() {
        return m_name;
    }

    /**
     * Returns the object identifer for this instance.
     */
    String getOid() {
        return m_oid;
    }

    /**
     * Returns true if this instance is part of a table.
     */
    boolean isTableEntry() {
        return m_isTabular;
    }

    /**
     * Returns the column of the table this instance is in. If the instance is
     * not part of a table then the return code is not defined.
     */
    int getColumn() {
        return m_column;
    }
}

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
// Modifications:
//
// 2002 Sep 24: Added the ability to select SNMP interfaces for collection.
//              Code based on original manage/unmanage code.
//
// Original code base Copyright (C) 1999-2001 Oculan Corp.  All rights reserved.
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

package org.opennms.web.admin.nodeManagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.opennms.netmgt.config.DatabaseConnectionFactory;

/**
 * A servlet that handles querying the database for node, interface, service
 * combinations for use in setting up SNMP data collection per interface
 * 
 * @author <A HREF="mailto:tarus@opennms.org">Tarus Balog </A>
 * @author <A HREF="http://www.opennms.org/">OpenNMS </A>
 */
public class SnmpGetInterfacesServlet extends HttpServlet {

    private static final String INTERFACE_QUERY = "SELECT ipinterface.nodeid, ipinterface.ipaddr, ipinterface.ifindex, ipinterface.iphostname, ipinterface.issnmpprimary, snmpinterface.snmpifdescr, snmpinterface.snmpiftype, snmpinterface.snmpifname, snmpinterface.snmpifalias FROM ipinterface, snmpinterface WHERE ipinterface.nodeid=snmpinterface.nodeid AND ipinterface.ifindex=snmpinterface.snmpifindex AND ipinterface.nodeid=?;";

    public void init() throws ServletException {
        try {
            DatabaseConnectionFactory.init();
        } catch (Exception e) {
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession user = request.getSession(true);

        String nodeIdString = request.getParameter( "node" );

        if( nodeIdString == null ) {
            throw new org.opennms.web.MissingParameterException( "node" );
        }

        int nodeid = Integer.parseInt( nodeIdString );

        try {
            user.setAttribute("listInterfacesForNode.snmpselect.jsp", getNodeInterfaces(user,nodeid));
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        // forward the request for proper display
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/snmpselect.jsp");
        dispatcher.forward(request, response);
    }

    private List getNodeInterfaces(HttpSession userSession, int nodeid) throws SQLException {
        Connection connection = null;
        List nodeInterfaces = new ArrayList();
        int lineCount = 0;

        try {
            connection = DatabaseConnectionFactory.getInstance().getConnection();

            PreparedStatement interfaceSelect = connection.prepareStatement(INTERFACE_QUERY);
            interfaceSelect.setInt(1, nodeid);

            ResultSet interfaceSet = interfaceSelect.executeQuery();

            if (interfaceSet != null) {
                while (interfaceSet.next()) {
                    lineCount++;
                    SnmpManagedInterface newInterface = new SnmpManagedInterface();
                    nodeInterfaces.add(newInterface);
                    newInterface.setNodeid(interfaceSet.getInt(1));
                    newInterface.setAddress(interfaceSet.getString(2));
                    newInterface.setIfIndex(interfaceSet.getInt(3));
                    newInterface.setIpHostname(interfaceSet.getString(4));
                    newInterface.setStatus(interfaceSet.getString(5));
                    newInterface.setIfDescr(interfaceSet.getString(6));
                    newInterface.setIfType(interfaceSet.getInt(7));
                    newInterface.setIfName(interfaceSet.getString(8));
                    newInterface.setIfAlias(interfaceSet.getString(9));
                }
            }
            interfaceSelect.close();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }

        return nodeInterfaces;

    }
}

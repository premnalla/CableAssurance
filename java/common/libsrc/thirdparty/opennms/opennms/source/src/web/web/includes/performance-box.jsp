<!--

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

-->

<%--
  This page is included by other JSPs to create a box containing an
  entry to the performance reporting system.
  
  It expects that a <base> tag has been set in the including page
  that directs all URLs to be relative to the servlet context.
--%>

<%@page language="java" contentType="text/html" session="true" import="java.util.*,org.opennms.web.performance.*,org.opennms.web.Util" %>

<%!
    public PerformanceModel model = null;

    public void init() throws ServletException {
        try {
            this.model = new PerformanceModel( org.opennms.core.resource.Vault.getHomeDir() );
        }
        catch( Exception e ) {
            throw new ServletException( "Could not initialize the PerformanceModel", e );
        }
    }
%>

<%
    PerformanceModel.QueryableNode[] nodes = this.model.getQueryableNodes();
%>
      
<table width="100%" border="1" cellspacing="0" cellpadding="2" bordercolor="black" bgcolor="#cccccc">
  <tr>
    <td bgcolor="#999999" ><b><a href="performance/index.jsp">Performance</a></b></td>
  </tr>

<%  if( nodes != null && nodes.length > 0 ) { %>
  <tr> 
    <td>
      <form method="get" action="performance/addIntfFromNode" >
        <input type="hidden" name="endUrl" value="performance/addReportsToUrl" />
        <input type="hidden" name="relativetime" value="lastday" />

        <table width="100%" border="0" cellspacing="0" cellpadding="1">
          <tr>
            <td>
              <font size="-1">Choose a node to query</font>
            </td>
          </tr>
          <tr>
            <td>
              <select name="node" size="1">
                <% for( int i=0; i < nodes.length; i++ ) { %>
                   <option value="<%=nodes[i].nodeId%>"><%=nodes[i].nodeLabel%></option>
                <% } %>
              </select>
            </td>
          </tr>
          <tr>
            <td>
              <input type="submit" value="Execute Query" />
            </td>
          </tr>
        </table>
      </form>
    </td>
  </tr>
<% } else { %>
  <tr>
    <td>
      No performance data has been gathered yet
    </td>
  </tr>        
<% }  %>
</table>

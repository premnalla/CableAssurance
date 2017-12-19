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
// Modifications:
//
// 2003 Apr 16: Changed the notification box to show outstanding notifications
//              for logged in user.
// 2003 Feb 07: Fixed URLEncoder issues.
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

-->

<%--
  This page is included by other JSPs to create a box containing a
  table that provides links for notification queries.
  
  It expects that a <base> tag has been set in the including page
  that directs all URLs to be relative to the servlet context.
--%>

<%@page language="java" contentType="text/html" session="true" import="org.opennms.web.notification.*" %>

<%!
    protected NotificationModel model = new NotificationModel();
    protected java.text.ChoiceFormat formatter = new java.text.ChoiceFormat( "0#No outstanding notices|1#1 outstanding notice|2#{0} outstanding notices" );
%>

<table width="100%" border="1" cellspacing="0" cellpadding="2" bordercolor="black" bgcolor="#cccccc">
  <tr> 
    <td bgcolor="#999999" ><b><a href="notification/index.jsp">Notification</a></b></td>
  </tr>
  <tr> 
    <td>
      <table width="100%" border="0" cellspacing="0" cellpadding="1">
        <tr>
          <td>
            <a href="notification/browse?acktype=unack&filter=<%= java.net.URLEncoder.encode("user="+request.getRemoteUser()) %>">Check Your Notices</a>
          </td>
        </tr>
        <tr>
          <td>
            <%
                int count = this.model.getOutstandingNoticeCount(request.getRemoteUser());
                String format = this.formatter.format( count );
                out.println( java.text.MessageFormat.format( format, new Object[] { new Integer(count) } ));
             %>
          </td>
        </tr>
        <tr>
          <td>
            <a href="notification/browse?acktype=unack">Check All Open Notices</a>
          </td>
        </tr>
        <tr>
          <td>
            <%
                count = this.model.getOutstandingNoticeCount();
                format = this.formatter.format( count );
                out.println( java.text.MessageFormat.format( format, new Object[] { new Integer(count) } ));
             %>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

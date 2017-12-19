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
// 2003 Feb 07: Fixed URLEncoder issues.
// 2002 Nov 26: Fixed breadcrumbs issue.
// 2002 Nov 10: Removed "http://" from the UEIs and references to bluebird.
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

<%@page language="java" contentType = "text/html" session = "true" import="java.util.*,java.net.*,org.opennms.web.eventconf.bobject.*,org.opennms.web.eventconf.*" %>

<%
  //init method
  EventConfFactory eventFactory = EventConfFactory.getInstance();
  List eventList = eventFactory.getEvents();
 %>

<html>
<head>
  <title>Event Configuration | Event Config | OpenNMS Web Console</title>
  <base HREF="<%=org.opennms.web.Util.calculateUrlBase( request )%>" />
  <link rel="stylesheet" type="text/css" href="includes/styles.css" />
</head>

<script language="Javascript" type="text/javascript" >

    function renameEvent(eventUEI)
    {
        var newUEI = prompt("Enter new name for event.", eventUEI);
        
        if (newUEI != null && newUEI != "")
        {
          document.allEvents.newEventUEI.value = newUEI;
          document.allEvents.oldEventUEI.value = eventUEI;
          document.allEvents.action="admin/eventconf/renameEvent";
          document.allEvents.submit();
        }
    }
    
    function modifyEvent(eventUEI)
    {
        document.allEvents.oldEventUEI.value=eventUEI;
        document.allEvents.action="admin/eventconf/modifyParam";
        document.allEvents.submit();
    }
    
    function addNewEvent()
    {
        var newEventUEI = prompt("Enter the UEI of the new event. It can be renamed after it is saved.", "uei.opennms.org/");
        if (newEventUEI != null && newEventUEI != "")
        {
            document.allEvents.newEventUEI.value = newEventUEI;
            document.allEvents.action="admin/eventconf/newEvent";
            document.allEvents.submit();
        }
    }
    
    function copyEvent(eventUEI)
    {
        var newUEI = prompt("Enter the UEI of the new event. It can be renamed after it is saved.", eventUEI);
        
        if (newUEI == eventUEI)
        {
            alert("The copied event must have a different UEI than the original.");
            return;
        }
        
        if (newUEI != null && newUEI != "")
        {
            document.allEvents.oldEventUEI.value = eventUEI;
            document.allEvents.newEventUEI.value = newUEI;
            document.allEvents.action="admin/eventconf/copyEvent";
            document.allEvents.submit();
        }
    }
    
</script>

<body marginwidth="0" marginheight="0" LEFTMARGIN="0" RIGHTMARGIN="0" TOPMARGIN="0">

<% String breadcrumb1 = "<a href='admin/index.jsp'> Admin </a>"; %>
<% String breadcrumb2 = "Event Configuration"; %>
<jsp:include page="/includes/header.jsp" flush="false" >
  <jsp:param name="title" value="Event Configuration" />
  <jsp:param name="location" value="admin" />
  <jsp:param name="breadcrumb" value="<%=breadcrumb1%>" />
  <jsp:param name="breadcrumb" value="<%=breadcrumb2%>" />
</jsp:include>

<br>

<table width="100%" border="0" cellspacing="0" cellpadding="2" >
  <tr>
    <td>&nbsp;</td>
    
      <!-- list of existing  event  ueis -->
      <td>
      <FORM METHOD="POST" NAME="allEvents">
        <input type="hidden" name="newEventUEI">
        <input type="hidden" name="oldEventUEI">
        <a href="javascript:addNewEvent()"> <img src="images/add1.gif" border="0" alt="Add new event"> Add new event</a>
        
        &nbsp;&nbsp;<a href="admin/eventconf/rearrangeEvents.jsp"> Rearange Event Configurations </a>
        <p>
      
      <table width="100%" border="1" cellspacing="0" cellpadding="2" bordercolor="black">
         <tr bgcolor="#999999">
          <td width="5%"><b>Delete</b></td>
          <td width="5%"><b>Modify</b></td>
          <td width="5%"><b>Copy</b></td>
          <td width="5%"><b>Rename</b></td>
          <td width="10%"><b>Severity</b></td>
          <td><b>UEI</b></td>
        </tr>
         <%
           for (int row = 0; row < eventList.size(); row++)
           {
              Event event = (Event)eventList.get(row);
              String eventUEI = event.getUei();
              String encodedUEI = URLEncoder.encode(eventUEI);
         %>
              <tr bgcolor=<%=row%2==0 ? "#ffffff" : "#cccccc"%>>
                <td width="5%" rowspan="2" align="center" valign="middle">
                  <a href="admin/eventconf/deleteEvent?event=<%=encodedUEI%>" onclick="return confirm('Are you sure you want to delete the event\n <%=eventUEI%>')"><img src="images/trash.gif" border="0" alt=<%="Delete Event "+eventUEI%>></a> 
                </td>
                
                <td width="5%" rowspan="2" align="center" valign="middle"> 
                  <a href="javascript:modifyEvent('<%=eventUEI%>')"><img src="images/modify.gif" border="0">
                </td>
                
                <td width="5%" rowspan="2" align="center" valign="middle"> 
                  <a href="javascript:copyEvent('<%=eventUEI%>')"><img src="images/copy.gif" border="0"></a>
                </td>
                
                <td width="5%" rowspan="2" align="center" valign="middle">
                  <input type="button" name="rename" value="Rename" onclick="renameEvent('<%=eventUEI%>')">
                </td>
                
                <td width="10%"><%=event.getSeverity()%></td>
                <td><a href="admin/eventconf/detail.jsp?uei=<%=encodedUEI%>"><%=eventUEI%></a></td>
             </tr>
                
              <tr bgcolor=<%=row%2==0 ? "#ffffff" : "#cccccc"%>>
                <td colspan="2"><%=event.getLogMessage()%></td>
              </tr>
         <% 
            } /*end for loop*/
         %>
      </table>
      </FORM>
      </td>
      
    <td> &nbsp; </td>
  </tr>
</table>

<br>

<jsp:include page="/includes/footer.jsp" flush="true" >
  <jsp:param name="location" value="admin" />
</jsp:include>
</body>
</html>

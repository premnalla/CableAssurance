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

<%@page language="java" contentType="text/html" session="true" import="java.util.*,org.opennms.web.Util,org.opennms.netmgt.config.*,org.opennms.netmgt.config.destinationPaths.*" %>

<%!
    public void init() throws ServletException {
        try {
            UserFactory.init();
            GroupFactory.init();
            DestinationPathFactory.init();
            NotificationCommandFactory.init();
        }
        catch( Exception e ) {
            throw new ServletException( "Cannot load configuration file", e );
        }
    }
%>

<%
    HttpSession user = request.getSession(true);
    Path newPath = (Path)user.getAttribute("newPath");
%>

<html>
<head>
  <title>Choose Commands | Admin | OpenNMS Web Console</title>
  <base HREF="<%=org.opennms.web.Util.calculateUrlBase( request )%>" />
  <link rel="stylesheet" type="text/css" href="includes/styles.css" />
</head>

<script language="Javascript" type="text/javascript" >

    function next() 
    {
        var missingCommands=false;
        for (i=0; i<document.commands.length; i++)
        {
            if (document.commands.elements[i].type=="select-multiple" && 
                document.commands.elements[i].selectedIndex==-1)
            {
                missingCommands=true;
            }
        }
        
        if (missingCommands)
        {
            alert("Please choose at least command for each user and group.");
        }
        else
        {
            document.commands.submit();
        }
    }

</script>

<body marginwidth="0" marginheight="0" LEFTMARGIN="0" RIGHTMARGIN="0" TOPMARGIN="0">

<% String breadcrumb1 = "<a href='admin/index.jsp'>Admin</a>"; %>
<% String breadcrumb2 = "<a href='admin/notification/index.jsp'>Configure Notifications</a>"; %>
<% String breadcrumb3 = "<a href='admin/notification/destinationPaths.jsp'>Destination Paths</a>"; %>
<% String breadcrumb4 = "Choose Commands"; %>
<jsp:include page="/includes/header.jsp" flush="false" >
  <jsp:param name="title" value="Choose Commands" />
  <jsp:param name="breadcrumb" value="<%=breadcrumb1%>" />
  <jsp:param name="breadcrumb" value="<%=breadcrumb2%>" />
  <jsp:param name="breadcrumb" value="<%=breadcrumb3%>" />
  <jsp:param name="breadcrumb" value="<%=breadcrumb4%>" />
</jsp:include>

<br>
<!-- Body -->

<table width="100%" cellspacing="0" cellpadding="0" border="0">
  <tr>
    <td> &nbsp; </td>

    <td>
    <h2><%=(newPath.getName()!=null ? "Editing path: " + newPath.getName() + "<br>" : "")%></h2>
    <h3>Choose the commands to use for each user and group. More than one command can be choosen
        for each (except for email addresses). Also choose the desired behavior for automatic
        notification on "UP" events.</h3>
    <form METHOD="POST" NAME="commands" ACTION="admin/notification/destinationWizard" >
      <%=Util.makeHiddenTags(request)%>
      <input type="hidden" name="sourcePage" value="chooseCommands.jsp"/>
      <table width="50%" cellspacing="2" cellpadding="2" border="0">
        <tr>
          <td valign="top" align="left">
            <%=buildCommands(newPath, Integer.parseInt(request.getParameter("targetIndex")))%>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <input type="reset"/>
          </td>
        </tr>
        <tr>
          <td colspan="2">
           <a HREF="javascript:next()">Next &#155;&#155;&#155;</a>
          </td>
        </tr>
      </table>
    </form>
    </td>

    <td> &nbsp; </td>
  </tr>
</table>

<br>

<jsp:include page="/includes/footer.jsp" flush="false" />

</body>
</html>

<%!
    public String buildCommands(Path path, int index)
      throws ServletException
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<table width=\"100%\" cellspacing=\"2\" cellpadding=\"2\" border=\"0\">");
        
        Target targets[] = null;
        
        try {
          targets = DestinationPathFactory.getInstance().getTargetList(index, path);
        
        for (int i = 0; i < targets.length; i++)
        {
            buffer.append("<tr><td>").append(targets[i].getName()).append("</td>");
            // don't let user pick commands for email addresses
            if (targets[i].getName().indexOf("@")==-1)
            {
                buffer.append("<td>").append(buildCommandSelect(path, index, targets[i].getName())).append("</td>");
            }
            else
            {
                buffer.append("<td>").append("email adddress").append("</td>");
            }
            buffer.append("<td>").append(buildAutoNotifySelect(targets[i].getName(), targets[i].getAutoNotify())).append("<td>");
            buffer.append("</tr>");
        }
        } catch (Exception e)
        {
            throw new ServletException("couldn't get list of targets for path " + path.getName(), e);
        }
        
        buffer.append("</table>");
        return buffer.toString();
    }
    
    public String buildCommandSelect(Path path, int index, String name)
      throws ServletException
    {
        StringBuffer buffer = new StringBuffer("<select multiple size=\"3\" NAME=\"" + name + "Commands\">");
        
        TreeMap commands = null;
        Collection selectedOptions = null;
        
        try {
          selectedOptions = DestinationPathFactory.getInstance().getTargetCommands(path, index, name);
          commands = new TreeMap(NotificationCommandFactory.getInstance().getCommands());
        
        if (selectedOptions==null || selectedOptions.size()==0)
        {
            selectedOptions = new ArrayList();
            selectedOptions.add("email");
        }
        
        Iterator i = commands.keySet().iterator();
        while(i.hasNext())
        {
            String curCommand = (String)i.next();
            if (selectedOptions.contains(curCommand))
            {
                buffer.append("<option selected VALUE=\"" + curCommand + "\">").append(curCommand).append("</option>");
            }
            else
            {
                buffer.append("<option VALUE=\"" + curCommand + "\">").append(curCommand).append("</option>");
            }
        }
        } catch (Exception e)
        {
            throw new ServletException("couldn't get list of commands for path/target " + path.getName()+"/"+name, e);
        }
        
        buffer.append("</select>");
        
        return buffer.toString();
    }

    public String buildAutoNotifySelect(String name, String currValue)
    {
          String values[] = {"off", "auto", "on"};
          StringBuffer buffer = new StringBuffer("<select size=\"3\" NAME=\"" + name  + "AutoNotify\">");
          String defaultOption = "auto";
 
          if(currValue == null || currValue.equals("")) {
              currValue = defaultOption;
          }
          for (int i = 0; i < values.length; i++)
          {
             if (values[i].equalsIgnoreCase(currValue))
             {
                 buffer.append("<option selected VALUE=\"" + values[i] + "\">").append(values[i]).append("</option>");
             }
             else
             {
                  buffer.append("<option VALUE=\"" + values[i] + "\">").append(values[i]).append("</option>");
             }
          }
          buffer.append("</select>");
          
          return buffer.toString();
    }
%>

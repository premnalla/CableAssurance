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

<%@page language="java" contentType = "text/html" session = "true"  import="java.util.*,java.text.*,org.opennms.netmgt.config.*,org.opennms.netmgt.config.users.*"%>
<%
	HttpSession userSession = request.getSession(false);
	Map users;
	User user = null;
	String userid = "";
	UserFactory userFactory;
	try
	{
		UserFactory.init();	
		userFactory = UserFactory.getInstance();
		users = userFactory.getUsers();
	}
	catch(Exception e)
	{
		throw new ServletException( "UserFactory:modify() " + e );
	}

	if (userSession != null)
	{
		user = (User)userSession.getAttribute("user.modifyUser.jsp");
		userid = user.getUserId();
	}
	

%>
<html>
<head>
<title>Modify User | User Admin | OpenNMS Web Console</title>
<base HREF="<%=org.opennms.web.Util.calculateUrlBase( request )%>" />
<link rel="stylesheet" type="text/css" href="includes/styles.css" />
</head>

<script language="Javascript" type="text/javascript" >
    
    function validate()
    {
        for (var c = 0; c < document.modifyUser.dutySchedules.value; c++)
        {
            var beginName= "duty" + c + "Begin";
            var endName  = "duty" + c + "End";
            
            var beginValue = new Number(document.modifyUser.elements[beginName].value);
            var endValue = new Number(document.modifyUser.elements[endName].value);
            
            if (!document.modifyUser.elements["deleteDuty"+c].checked)
            {
            if (isNaN(beginValue))
            {
                alert("The begin time of duty schedule " + (c+1) + " must be expressed in military time with no other characters, such as 800, not 8:00");
                return false;
            }
            if (isNaN(endValue))
            {
                alert("The end time of duty schedule " + (c+1) + " must be expressed in military time with no other characters, such as 800, not 8:00");
                return false;
            }
            if (beginValue > endValue)
            {
                alert("The begin value for duty schedule " + (c+1) + " must be less than the end value.");
                return false;
            }
            if (beginValue < 0 || beginValue > 2359)
            {
                alert("The begin value for duty schedule " + (c+1) + " must be greater than 0 and less than 2400");
                return false;
            }
            if (endValue < 0 || endValue > 2359)
            {
                alert("The end value for duty schedule " + (c+1) + " must be greater than 0 and less than 2400");
                return false;
            }
            }
        }
        return true;
    }

    function resetPassword()
    {
        newUserWin = window.open("<%=org.opennms.web.Util.calculateUrlBase(request)%>/admin/userGroupView/users/newPassword.jsp", "", "fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no,width=500,height=300");
    }

    function addDutySchedules()
    {
        var ok = validate();

        if(ok)
        {
          document.modifyUser.redirect.value="/admin/userGroupView/users/addDutySchedules";
          document.modifyUser.action="admin/userGroupView/users/updateUser";
          document.modifyUser.submit();
        }
    }
    
    function removeDutySchedules()
    {
        var ok = validate();
        
        if(ok)
        {
          document.modifyUser.redirect.value="/admin/userGroupView/users/modifyUser.jsp";
          document.modifyUser.action="admin/userGroupView/users/updateUser";
          document.modifyUser.submit();
        }
    }
    
    function saveUser()
    {
        var ok = validate();
        if(ok)
        {
          document.modifyUser.redirect.value="/admin/userGroupView/users/saveUser";
          document.modifyUser.action="admin/userGroupView/users/updateUser";
          document.modifyUser.submit();
        }
    }
    
    function cancelUser()
    {
        document.modifyUser.action="admin/userGroupView/users/list.jsp";
        document.modifyUser.submit();
    }

</script>

<body marginwidth="0" marginheight="0" LEFTMARGIN="0" RIGHTMARGIN="0" TOPMARGIN="0">

<% String breadcrumb1 = "<a href='admin/index.jsp'>Admin</a>"; %>
<% String breadcrumb2 = "<a href='admin/userGroupView/index.jsp'>Users and Groups</a>"; %>
<% String breadcrumb3 = "<a href='admin/userGroupView/users/list.jsp'>User List</a>"; %>
<% String breadcrumb4 = "Modify User"; %>
<jsp:include page="/includes/header.jsp" flush="false" >
  <jsp:param name="title" value="Modify User" />
  <jsp:param name="breadcrumb" value="<%=breadcrumb1%>" />
  <jsp:param name="breadcrumb" value="<%=breadcrumb2%>" />
  <jsp:param name="breadcrumb" value="<%=breadcrumb3%>" />
  <jsp:param name="breadcrumb" value="<%=breadcrumb4%>" />
</jsp:include>

<br>

<FORM METHOD="POST" NAME="modifyUser">
<input type="hidden" name="userID" value="<%=user.getUserId()%>"/>
<input type="hidden" name="password"/>
<input type="hidden" name="redirect"/>

<table width="100%" border="0" cellspacing="0" cellpadding="2" >
  <tr>
    <td>&nbsp;</td>

    <td>
    <h3>Modify User: <%= userid %></h3>

    <table width="100%" border="0" cellspacing="0" cellpadding="2" >
      <tr>
        <td width="50%" valign="top">
          <p><input type="button" value="Reset Password" onClick="resetPassword()" /></p>

          <p><table width="100%" border="0" cellspacing="0" cellpadding="2">
            <tr>
              <td colspan="2">
                <p><b>User Information</b></p>
              </td>
            </tr>
	    <% 
		String email = userFactory.getEmail(userid); 
		String pagerEmail = userFactory.getPagerEmail(userid); 
		String xmppAddress = userFactory.getXMPPAddress(userid);
		String numericPage = userFactory.getNumericPage(userid); 
		String numericPin = userFactory.getNumericPin(userid); 
		String textPage = userFactory.getTextPage(userid); 
		String textPin = userFactory.getTextPin(userid); 
		String fullName = user.getFullName(); 
		String comments = user.getUserComments(); 
	    %>
            <tr>
              <td valign="top">
                Full Name:
              </td>
              <td align="left" valign="top">
                <input type="text" size="35" name="fullName" value="<%=(fullName == null || fullName.equals(""))? "":fullName %>" />
              </td>
            </tr>
            <tr>
              <td valign="top">
                Comments:
              </td>
              <td align="left" valign="top">
                <textarea rows="5" cols="33" name="comments"><%=(comments == null || comments.equals(""))? "":comments %></textarea>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <p><b>Notification Information</b></p>
              </td>
            </tr>
            <tr>
              <td valign="top">
                Email:
              </td>
              <td valign="top">
                <input type="text" size="35" name="email" value='<%= (email == null || email.equals(""))? "":email %>'>
              </td>
            </tr>
            <tr>
              <td valign="top">
                Pager Email:
              </td>
              <td valign="top">
                <input type="text" size="35" name="pemail" value='<%=(pagerEmail == null || pagerEmail.equals(""))? "":pagerEmail%>'>
              </td>
            </tr>
            <tr>
              <td valign="top">
                XMPP Address:
              </td>
              <td valign="top">
                <input type="text" size="35" name="xmppAddress" value='<%=(xmppAddress == null || xmppAddress.equals(""))? "":xmppAddress%>'>
              </td>
            </tr>
            <tr>
              <td valign="top">
                Numeric Service:
              </td>
              <td valign="top">
                <input type="text" size="35" name="numericalService" value='<%=(numericPage == null || numericPage.equals(""))? "":numericPage %>'>
              </td>
            </tr>
            <tr>
              <td valign="top">
                Numeric PIN:
              </td>
              <td valign="top">
                <input type="text" size="35" name="numericalPin" value='<%= (numericPin == null || numericPin.equals(""))? "":numericPin%>'>
              </td>
            </tr>
            <tr>
              <td valign="top">
                Text Service:
              </td>
              <td valign="top">
                <input type="text" size="35" name="textService" value='<%= (textPage == null || textPage.equals(""))? "":textPage%>'>
              </td>
            </tr>
            <tr>
              <td valign="top">
                Text PIN:
              </td>
              <td valign="top">
                <input type="text" size="35" name="textPin" value='<%=(textPin == null || textPin.equals(""))? "":textPin%>'>
              </td>
            </tr>
          </table></p>
        </td>
        <td width="15">&nbsp;&nbsp;</td>
        <td width="50%" valign="top">
          <p>This panel allows you to modify information for each user, including their name,
          notification information, and duty schedules.</p>

          <p><b>Notification Information</b> provides the ability for you to configure contact
          information for each user, including any of <em>email</em> address, <em>pager email</em>
          (in the case that the pager can be reached as an email destination), <em>XMPP address</em>
          (for instant messages using the Jabber XMPP protocol), <em>numeric service</em>
          (for pagers that cannot display text messages), and <em>text service</em> (for alphanumeric pagers).</p>

          <p><b>Duty Schedules</b> allow you to flexibility to determine when users should receive
          notifications.  A duty schedule consists of a list of days for which the time will apply
          and a time range, presented in military time with no punctuation.  Using this standard, days
          run from <em>0000</em> to <em>2359</em>.</p>

          <p>If your duty schedules span midnight, or if your users work multiple, noncontiniguous
          time periods, you will need to configure multiple duty schedules.  To do so, select the
          number of duty schedules to add from the drop-down box next to <b>[Add This Many Schedules]</b>,
          and click the button.  Then, using the duty schedule fields you've just added, create a
          duty schedule from the start time to 2359 on one day, and enter a second duty schedule
          which begins at 0000 and ends at the end of that users coverage.</p>

          <p>To remove configured duty schedules, put a check in the <em>Delete</em> column and click
          <b>[Remove Checked Schedules]</b>.</p>

          <p>To save your configuration, click on <b>[Finish]</b>.</p>
        </td>
      </tr>
      <tr>
        <td width="100%" colspan="3">
          <p><b>Duty Schedules</b></p>
          <table width="100%" border="1" cellspacing="0" cellpadding="2" >
            <tr bgcolor="#999999">
              <td>&nbsp;</td>
              <td><b>Delete</b></td>
              <td><b>Mo</b></td>
              <td><b>Tu</b></td>
              <td><b>We</b></td>
              <td><b>Th</b></td>
              <td><b>Fr</b></td>
              <td><b>Sa</b></td>
              <td><b>Su</b></td>
              <td><b>Begin Time</b></td>
              <td><b>End Time</b></td>
            </tr>
                        <%
				Collection dutySchedules = user.getDutyScheduleCollection(); %>
				<input type="hidden" name="dutySchedules" value="<%=user.getDutyScheduleCount()%>">
                        <%
				int i =0;
				Iterator iter = dutySchedules.iterator();
				while(iter.hasNext())
				{
					DutySchedule tmp = new DutySchedule((String)iter.next());
					Vector curSched = tmp.getAsVector();
                        %>
                        <tr>
                          <td width="1%"><%=(i+1)%></td>
                          <td width="1%">
                            <input type="checkbox" name="deleteDuty<%=i%>">
                          </td>
                          <% ChoiceFormat days = new ChoiceFormat("0#Mo|1#Tu|2#We|3#Th|4#Fr|5#Sa|6#Su");
                             for (int j = 0; j < 7; j++)
                             {
                                Boolean curDay = (Boolean)curSched.get(j);
                          %>
                          <td width="5%">
                            <input type="checkbox" name="duty<%=i+days.format(j)%>" <%= (curDay.booleanValue() ? "checked" : "")%>>
                          </td>
                          <% } %>
                          <td width="5%">
                            <input type="text" size="4" name="duty<%=i%>Begin" value="<%=curSched.get(7)%>">
                          </td>
                          <td width="5%">
                            <input type="text" size="4" name="duty<%=i%>End" value="<%=curSched.get(8)%>">
                          </td>
                        </tr>
                        <% i++; } %>
          </table>
        </td>
      </tr>
    </table></p>

    <p><input type="button" name="addSchedule" value="Add This Many Schedules" onclick="addDutySchedules()">
      <select name="numSchedules" value="3" size="1">
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
        <option value="6">6</option>
        <option value="7">7</option>
      </select>
    </p>

    <p><input type="button" name="addSchedule" value="Remove Checked Schedules" onclick="removeDutySchedules()"></p>

    <p><input type="button" name="finish" value="Finish" onclick="saveUser()">&nbsp;&nbsp;&nbsp;<input type="button" name="cancel" value="Cancel" onclick="cancelUser()"></p>
    <td>&nbsp;</td>
  </tr>
</table>

</FORM>

<br>

<jsp:include page="/includes/footer.jsp" flush="false" >
</jsp:include>
</body>
</html>

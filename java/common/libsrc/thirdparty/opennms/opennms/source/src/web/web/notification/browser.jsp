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
// 2004 Nov 18: Added "Acknowledge Notices" and "Select All" buttons at the top of the table
//              So it isn't necessary to scroll all the way to the bottom. Bill Ayres.
// 2003 Feb 07: Fixed URLEncoder issues.
// 2002 Nov 26: Fixed breadcrumbs issue.
// 2002 Nov 10: Removed "http://" from UEIs and references to bluebird.
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

<%@page language="java" contentType="text/html" session="true" import="org.opennms.web.notification.*,org.opennms.web.element.*,java.util.*,java.sql.SQLException,org.opennms.web.event.*,org.opennms.web.event.filter.*" %>

<%--
  This page is written to be the display (view) portion of the NotificationQueryServlet
  at the /notification/list URL.  It will not work by itself, as it requires two request
  attributes be set:
  
  1) notices: the list of org.opennms.web.notification.Notification instances to display
  2) parms: an org.opennms.web.notification.NoticeQueryParms object that holds all the 
     parameters used to make this query
--%>
<%!
   public String getBgColor(Notification n) {
	   String bgcolor="#cccccc";
	   try {
		return EventUtil.getSeverityColor(EventFactory.getEvent(n.getEventId()).getSeverity());
	   } catch (Exception e) {
	   	return bgcolor;
	   }
   }
%>

<%
    //required attributes
    Notification[] notices = (Notification[])request.getAttribute( "notices" );
    NoticeQueryParms parms = (NoticeQueryParms)request.getAttribute( "parms" );

    if( notices == null || parms == null ) {
        throw new ServletException( "Missing either the notices or parms request attribute." );
    }

    String action = null;

    if( parms.ackType == NoticeFactory.AcknowledgeType.UNACKNOWLEDGED ) {
        action = "1";
    } 
    else if( parms.ackType == NoticeFactory.AcknowledgeType.ACKNOWLEDGED ) {
        action = "2";
    }

    int noticeCount = NoticeFactory.getNoticeCount( parms.ackType, parms.getFilters() );
    HashMap nodeLabelMap = new HashMap();
    
    //useful constant strings
    String addPositiveFilterString = "[+]";    
%>


<html>
<head>
  <title> Browse | Notices | OpenNMS Web Console</title>
  <base HREF="<%=org.opennms.web.Util.calculateUrlBase( request )%>" />
  <link rel="stylesheet" type="text/css" href="includes/styles.css" />
  
  <style type="text/css"> 
    a.filterLink { color:black; text-decoration:none; };
  </style>  
</head>

<script language="Javascript" type="text/javascript" >
    function checkAllCheckboxes() {
       if( document.acknowledge_form.notices.length ) {  
         for( i = 0; i < document.acknowledge_form.notices.length; i++ ) {
           document.acknowledge_form.notices[i].checked = true
         }
       }
       else {
         document.acknowledge_form.notices.checked = true
       }
         
    }
    
    function submitAcknowledge()
    {
        var isChecked = false;
        var numChecked = 0;
        
        if (document.acknowledge_form.notices.length)
        {
            for( i = 0; i < document.acknowledge_form.notices.length; i++ ) 
            {
              //make sure something is checked before proceeding
              if (document.acknowledge_form.notices[i].checked)
              {
                  isChecked=true;
                  numChecked+=1;
              }
            }
            
            if (isChecked && document.acknowledge_form.multiple)
            {
              if (numChecked == parseInt(document.acknowledge_form.notices.length)) 
              { 
                var newPageNum = parseInt(document.acknowledge_form.multiple.value) - 1;
                var findVal = "multiple=" + document.acknowledge_form.multiple.value;
                var replaceWith = "multiple=" + newPageNum;
                var tmpRedirect = document.acknowledge_form.redirectParms.value;
                document.acknowledge_form.redirectParms.value = tmpRedirect.replace(findVal, replaceWith);
                document.acknowledge_form.submit();
              } 
              else 
              {
                document.acknowledge_form.submit();
              }
            }
            else if (isChecked)
            {
               document.acknowledge_form.submit();
            }
            else
            {
                alert("Please check the notices that you would like to acknowledge.");
            }
        }
        else
        {
            if (document.acknowledge_form.notices.checked)
            {

                document.acknowledge_form.submit();
            }
            else
            {
                alert("Please check the notices that you would like to acknowledge.");
            }
        }
    }
    
</script>

<body marginwidth="0" marginheight="0" LEFTMARGIN="0" RIGHTMARGIN="0" TOPMARGIN="0">

<% String breadcrumb1 = "<a href='notification/index.jsp' title='Notice System Page'>Notices</a>"; %>
<% String breadcrumb2 = "List"; %>
<jsp:include page="/includes/header.jsp" flush="false" >
  <jsp:param name="title" value="Notice List" />
  <jsp:param name="breadcrumb" value="<%=breadcrumb1%>" />
  <jsp:param name="breadcrumb" value="<%=breadcrumb2%>" />
</jsp:include>

<br>

<!-- Body -->
<table width="100%" border="0" cellspacing="0" cellpadding="2" >
  <tr>
    <td>&nbsp;</td>

    <td>
      <% if( parms.ackType == NoticeFactory.AcknowledgeType.UNACKNOWLEDGED ) { %>
        <p>Currently showing only <strong>outstanding</strong> notices.  
           <a href="<%=this.makeLink(parms, NoticeFactory.AcknowledgeType.ACKNOWLEDGED)%>" title="Show acknowledged notices">[Show acknowledged]</a>
        </p>
      <% } else if( parms.ackType == NoticeFactory.AcknowledgeType.ACKNOWLEDGED ) { %>
        <p>Currently showing only <strong>acknowledged</strong> notices.  
           <a href="<%=this.makeLink(parms, NoticeFactory.AcknowledgeType.UNACKNOWLEDGED)%>" title="Show outstanding notices">[Show outstanding]</a>
        </p>
      <% } %>
      
      <% if( noticeCount > 0 ) { %>      
      <p><%=(parms.multiple==0)?1:parms.multiple*parms.limit%>-<%=((parms.multiple+1)*parms.limit < noticeCount)?((parms.multiple+1)*parms.limit):noticeCount%> of <%=noticeCount%>.
        <a href="<%=this.makeLink(parms)%>&multiple=<%=parms.multiple+1%>">Next</a>&nbsp;
      <% int linkcnt = (int)Math.ceil( noticeCount/(float)parms.limit ); %>
      <% for( int i=0; i < linkcnt; i++ ) { %>
        <% if( parms.multiple == i ) { %>
           <%=i+1%>
        <% } else { %>
          <a href="<%=this.makeLink(parms)%>&multiple=<%=i%>"><%=i+1%></a>&nbsp;
        <% } %>
      <% } %>
      </p>
      <% } %>

      <% if( parms.filters.size() > 0 ) {
           int length = parms.filters.size(); 
      %>
           <p>Current active filters:
             <ol>
           <% for( int i = 0; i < length; i++ ) {
               NoticeFactory.Filter filter = (NoticeFactory.Filter)parms.filters.get(i); 
           %>
               <li><strong><%=filter.getTextDescription()%></strong> 
               <a href="<%=this.makeLink( parms, filter, false)%>" title="Remove filter">[Remove]</a>
          <% } %>
             </ol>
               <a href="<%=this.makeLink( parms, new ArrayList())%>" title="Remove all filters">[Remove All Filters]</a>
            </p>
      <% } %>

      <table width="100%" cellspacing="1" cellpadding="2" border="0" bordercolor="black">
        <form action="notification/acknowledge" method="POST" name="acknowledge_form">
          <input type="hidden" name="redirectParms" value="<%=request.getQueryString()%>" />
          <%=org.opennms.web.Util.makeHiddenTags(request)%>

      <tr>
        <td colspan="5">
        <% if( parms.ackType == NoticeFactory.AcknowledgeType.UNACKNOWLEDGED ) { %>
          <input type="button" value="Acknowledge Notices" onClick="submitAcknowledge()"/>
          <input TYPE="button" VALUE="Select All" onClick="checkAllCheckboxes()"/>
          <input TYPE="reset" />
        <% } %>
      </tr>
      <tr><td> &nbsp;</td></tr>
          
      <% for( int i=0; i < notices.length; i++ ) { %>
        <% if( i%5 == 0 ) { %>
        <tr bgcolor="#999999">
          <% if( parms.ackType == NoticeFactory.AcknowledgeType.UNACKNOWLEDGED ) { %>
            <td width="5%"><b>Ack</b></td>
          <% } %>
          <td width="5%"> <%=this.makeSortLink( parms, NoticeFactory.SortStyle.ID,          NoticeFactory.SortStyle.REVERSE_ID,          "id",          "ID"           )%></td>
          <td width="5%"><b>Event ID</b></td>
          <td width="10%"><%=this.makeSortLink( parms, NoticeFactory.SortStyle.PAGETIME,    NoticeFactory.SortStyle.REVERSE_PAGETIME,    "pagetime",    "Sent Time"    )%></td>
          <td width="10%"><%=this.makeSortLink( parms, NoticeFactory.SortStyle.RESPONDER,   NoticeFactory.SortStyle.REVERSE_RESPONDER,   "asweredby",   "Responder"    )%></td>
          <td width="10%"><%=this.makeSortLink( parms, NoticeFactory.SortStyle.RESPONDTIME, NoticeFactory.SortStyle.REVERSE_RESPONDTIME, "respondtime", "Respond Time" )%></td>  
          <td width="25%"><%=this.makeSortLink( parms, NoticeFactory.SortStyle.NODE,        NoticeFactory.SortStyle.REVERSE_NODE,        "node",        "Node"         )%></td>
          <td width="15%"><%=this.makeSortLink( parms, NoticeFactory.SortStyle.INTERFACE,   NoticeFactory.SortStyle.REVERSE_INTERFACE,   "interface",   "Interface"    )%></td>
          <td width="10%"><%=this.makeSortLink( parms, NoticeFactory.SortStyle.SERVICE,     NoticeFactory.SortStyle.REVERSE_SERVICE,     "service",     "Service"      )%></td>
        </tr>
        <% } /*end if*/%>
        <tr>
          <% if( parms.ackType == NoticeFactory.AcknowledgeType.UNACKNOWLEDGED ) { %>
          <td valign="top" rowspan="2">
            <nobr>
              <input type="checkbox" name="notices" value="<%=notices[i].getId()%>" /> 
            </nobr>
          </td>
          <% } %>
          <td bgcolor="<%=getBgColor(notices[i])%>" valign="top" rowspan="2" >
            <a href="notification/detail.jsp?notice=<%=notices[i].getId()%>"><%=notices[i].getId()%></a>
          </td>
          <td>
            <% if ( NoticeFactory.canDisplayEvent(notices[i].getEventId()) ) { %>
            <a href="event/detail.jsp?id=<%=notices[i].getEventId()%>"><%=notices[i].getEventId()%></a>
            <% } else { %>
                &nbsp;
            <% } %>
          </td>
          <td><nobr><%=org.opennms.netmgt.EventConstants.formatToUIString(notices[i].getTimeSent())%></nobr></td>
          <td valign="top">
            <% NoticeFactory.Filter responderFilter = new NoticeFactory.ResponderFilter(notices[i].getResponder()); %>      
            <% if(notices[i].getResponder()!=null) {%>
              <%=notices[i].getResponder()%>
              <% if( !parms.filters.contains( responderFilter )) { %>
                <a href="<%=this.makeLink( parms, responderFilter, true)%>" class="filterLink" title="Show only notices with this responder"><%=addPositiveFilterString%></a>
              <% } %>
            <% } else { %>
              &nbsp;
            <% } %>
          </td>
          <td><nobr>
            <%if (notices[i].getTimeReplied()!=null) { %>
              <%=org.opennms.netmgt.EventConstants.formatToUIString(notices[i].getTimeReplied())%>
            <% } else { %>
              &nbsp;
            <% } %>
            </nobr></td>
          <td>
            <% if(notices[i].getNodeId() != 0 ) { %>
              <% NoticeFactory.Filter nodeFilter = new NoticeFactory.NodeFilter(notices[i].getNodeId()); %>
              <% String[] labels = this.getNodeLabels( notices[i].getNodeId(), nodeLabelMap ); %>
              <a href="element/node.jsp?node=<%=notices[i].getNodeId()%>" title="<%=labels[1]%>"><%=labels[0]%></a>
              <% if( !parms.filters.contains(nodeFilter) ) { %>
                <a href="<%=this.makeLink( parms, nodeFilter, true)%>" class="filterLink" title="Show only notices on this node"><%=addPositiveFilterString%></a>
              <% } %>
            <% } else { %>
              &nbsp;
            <% } %>
          </td>
          <td>
            <% if(notices[i].getIpAddress() != null ) { %>
              <% NoticeFactory.Filter intfFilter = new NoticeFactory.InterfaceFilter(notices[i].getIpAddress()); %>
              <% if( notices[i].getNodeId() != 0 ) { %>
                 <a href="element/interface.jsp?node=<%=notices[i].getNodeId()%>&intf=<%=notices[i].getIpAddress()%>" title="More info on this interface"><%=notices[i].getIpAddress()%></a>
              <% } else { %>
                 <%=notices[i].getInterfaceId()%>
              <% } %>
              <% if( !parms.filters.contains(intfFilter) ) { %>
                <a href="<%=this.makeLink( parms, intfFilter, true)%>" class="filterLink" title="Show only notices on this IP address"><%=addPositiveFilterString%></a>
              <% } %>
            <% } else { %>
              &nbsp;
            <% } %>
          </td>
          <td>
            <% if(notices[i].getServiceName() != null) { %>
              <% NoticeFactory.Filter serviceFilter = new NoticeFactory.ServiceFilter(notices[i].getServiceId()); %>
              <% if( notices[i].getNodeId() != 0 && notices[i].getIpAddress() != null ) { %>
                <a href="element/service.jsp?node=<%=notices[i].getNodeId()%>&intf=<%=notices[i].getIpAddress()%>&service=<%=notices[i].getServiceId()%>" title="More info on this service"><%=notices[i].getServiceName()%></a>
              <% } else { %>
                <%=notices[i].getServiceName()%>
              <% } %>
              <% if( !parms.filters.contains( serviceFilter )) { %>
                <a href="<%=this.makeLink( parms, serviceFilter, true)%>" class="filterLink" title="Show only notices with this service type"><%=addPositiveFilterString%></a>
              <% } %>
            <% } else { %>
              &nbsp;
            <% } %>
          </td>
        </tr>
	<tr bgcolor="#cccccc">
          <td colspan="7"><%=notices[i].getTextMessage()%></td>          
        </tr>
      <% } /*end for*/%>

        <tr>
          <td colspan="2"><%=notices.length%> notices</td>
          <td colspan="5">
          <% if( parms.ackType == NoticeFactory.AcknowledgeType.UNACKNOWLEDGED ) { %>
            <input type="button" value="Acknowledge Notices" onClick="submitAcknowledge()"/>
            <input TYPE="button" VALUE="Select All" onClick="checkAllCheckboxes()"/>
            <input TYPE="reset" />
          <% } else { %>
            &nbsp;
          <% } %>
          </td>
          <td>
            <% if( noticeCount > 0 ) { %>
            <a href="<%=this.makeLink(parms)%>&multiple=<%=parms.multiple+1%>">Next</a>&nbsp;
            <% } else { %>
              &nbsp;
            <% } %>
          </td>
        </tr>
        </form>
      </table>
    <br>
    
    </td>
    
    <td>&nbsp;</td>
  </tr>
</table>

<br>

<jsp:include page="/includes/footer.jsp" flush="false" />

</body>
</html>


<%!
    String urlBase = "notification/browse";

    protected String makeSortLink( NoticeQueryParms parms, NoticeFactory.SortStyle style, NoticeFactory.SortStyle revStyle, String sortString, String title ) {
      StringBuffer buffer = new StringBuffer();

      if( parms.sortStyle == style ) {
          buffer.append( "<img src=\"images/arrowdown.gif\" hspace=\"0\" vspace=\"0\" border=\"0\" alt=\"" );
          buffer.append( title );
          buffer.append( " Ascending Sort\"/>" );
          buffer.append( "&nbsp;<a href=\"" );
          buffer.append( this.makeLink( parms, revStyle ));
          buffer.append( "\" title=\"Reverse the sort\">" );
      } else if( parms.sortStyle == revStyle ) {
          buffer.append( "<img src=\"images/arrowup.gif\" hspace=\"0\" vspace=\"0\" border=\"0\" alt=\"" );
          buffer.append( title );
          buffer.append( " Descending Sort\"/>" );
          buffer.append( "&nbsp;<a href=\"" );
          buffer.append( this.makeLink( parms, style )); 
          buffer.append( "\" title=\"Reverse the sort\">" );
      } else {
          buffer.append( "<a href=\"" );
          buffer.append( this.makeLink( parms, style ));
          buffer.append( "\" title=\"Sort by " );
          buffer.append( sortString );
          buffer.append( "\">" );   
      }

      buffer.append( "<font color=\"black\"><b>" );
      buffer.append( title );
      buffer.append( "</b></font></a>" );

      return( buffer.toString() );
    }


    public String makeLink( NoticeFactory.SortStyle sortStyle, NoticeFactory.AcknowledgeType ackType, ArrayList filters ) {
      StringBuffer buffer = new StringBuffer( this.urlBase );
      buffer.append( "?sortby=" );
      buffer.append( NoticeUtil.getSortStyleString(sortStyle) );
      buffer.append( "&acktype=" );
      buffer.append( NoticeUtil.getAcknowledgeTypeString(ackType) );

      if( filters != null ) {
        for( int i=0; i < filters.size(); i++ ) {
          buffer.append( "&filter=" );
          String filterString = NoticeUtil.getFilterString((NoticeFactory.Filter)filters.get(i));
          buffer.append( java.net.URLEncoder.encode(filterString) );
        }
      }      

      return( buffer.toString() );
    }


    public String makeLink( NoticeQueryParms parms ) {
      return( this.makeLink( parms.sortStyle, parms.ackType, parms.filters) );
    }


    public String makeLink( NoticeQueryParms parms, NoticeFactory.SortStyle sortStyle ) {
      return( this.makeLink( sortStyle, parms.ackType, parms.filters) );
    }


    public String makeLink( NoticeQueryParms parms, NoticeFactory.AcknowledgeType ackType ) {
      return( this.makeLink( parms.sortStyle, ackType, parms.filters) );
    }


    public String makeLink( NoticeQueryParms parms, ArrayList filters ) {
      return( this.makeLink( parms.sortStyle, parms.ackType, filters) );
    }


    public String makeLink( NoticeQueryParms parms, NoticeFactory.Filter filter, boolean add ) {
      ArrayList newList = new ArrayList( parms.filters );
      if( add ) {
        newList.add( filter );
      }
      else {
        newList.remove( filter );
      }

      return( this.makeLink( parms.sortStyle, parms.ackType, newList ));
    }


    public String[] getNodeLabels( int nodeId, Map labelMap ) throws SQLException {
        Integer nodeInt = new Integer( nodeId ); 
        String[] labels = (String[])labelMap.get( nodeInt );

        if( labels == null ) {
            String longLabel = NetworkElementFactory.getNodeLabel( nodeId );

            if( longLabel == null ) {
                //when they finally get the "not null" added to the nodelabel column,
                //this should never happen, but until then...
                labels = new String[] { "&lt;No Node Label&gt;", "&lt;No Node Label&gt;" };
            }
            else {
                if( longLabel.length() > 32 ) {
                    String shortLabel = longLabel.substring( 0, 31 ) + "...";                        
                    labels = new String[] { shortLabel, longLabel };
                }
                else {
                    labels = new String[] { longLabel, longLabel };
                }

                labelMap.put( nodeInt, labels );
            }
        }

        return( labels );
    }
%>

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
  This page is included by other JSPs to create a box containing a tree of 
  service level availability information for the services of a given interface.
  
  It expects that a <base> tag has been set in the including page
  that directs all URLs to be relative to the servlet context.
--%>

<%@page language="java" contentType="text/html" session="true" import="org.opennms.web.category.*,org.opennms.web.element.*,java.util.*" %>


<%!
    protected CategoryModel model;
    
    protected double normalThreshold;
    protected double warningThreshold; 
    
    public void init() throws ServletException {
        try {
            this.model = CategoryModel.getInstance();
            
            this.normalThreshold = this.model.getCategoryNormalThreshold(CategoryModel.OVERALL_AVAILABILITY_CATEGORY);
            this.warningThreshold = this.model.getCategoryWarningThreshold(CategoryModel.OVERALL_AVAILABILITY_CATEGORY);
        }
        catch( java.io.IOException e ) {
            throw new ServletException("Could not instantiate the CategoryModel", e);
        }
        catch( org.exolab.castor.xml.MarshalException e ) {
            throw new ServletException("Could not instantiate the CategoryModel", e);
        }
        catch( org.exolab.castor.xml.ValidationException e ) {
            throw new ServletException("Could not instantiate the CategoryModel", e);
        }        
    }
%>

<%
    //required parameter node
    String nodeIdString = request.getParameter( "node" );

    if( nodeIdString == null ) {
        throw new org.opennms.web.MissingParameterException( "node", new String[] {"node", "intf"} );
    }

    //required parameter intf
    String ipAddr = request.getParameter( "intf" );

    if( ipAddr == null ) {
        throw new org.opennms.web.MissingParameterException( "intf", new String[] {"node", "intf"} );
    }
    
    int nodeId = Integer.parseInt( nodeIdString );

    //get the database node info
    Interface intf = NetworkElementFactory.getInterface(nodeId, ipAddr);
    if( intf == null ) {
        //handle this WAY better, very awful
        throw new ServletException( "No such interface in database" );
    }

    //get the child services (in alphabetical order)
    Service[] services = this.getServices(intf);
    if( services == null ) { 
        services = new Service[0]; 
    }

    //get the interface's overall service level availiability for the last 24 hrs
    double overallRtcValue = this.model.getInterfaceAvailability(nodeId, ipAddr);
%>

<table width="100%" cellspacing="0" cellpadding="2" border="1" bordercolor="black" bgcolor="#cccccc">
  <tr bgcolor="#999999">
    <td><b>Overall Availability</b></td>

<% if( overallRtcValue < 0 ) { %>
      <td width="30%" bgcolor="#cccccc" align="right"><b><%=ElementUtil.getInterfaceStatusString(intf)%></b></td>
<% } else { %>
      <td width="30%" bgcolor="<%=CategoryUtil.getCategoryColor(this.normalThreshold, this.warningThreshold, overallRtcValue)%>" align="right"><b><%=CategoryUtil.formatValue(overallRtcValue)%>%</b></td>
  </tr>

  <tr>
    <td colspan="2">
      <table width="100%" cellspacing="0" cellpadding="2" border="1">
        <% for( int i=0; i < services.length; i++ ) { %>
          <% Service service = services[i]; %>
          
          <% if( service.isManaged() ) { %>
            <% double svcValue = this.model.getServiceAvailability(nodeId, ipAddr, service.getServiceId()); %>     

            <tr>
              <td align="left"  width="30%"><a href="element/service.jsp?node=<%=nodeId%>&intf=<%=ipAddr%>&service=<%=service.getServiceId()%>"><%=service.getServiceName()%></a></td>
              <td align="right" width="70%" bgcolor="<%=CategoryUtil.getCategoryColor(this.normalThreshold, this.warningThreshold, svcValue)%>"><b><%=CategoryUtil.formatValue(svcValue)%>%</b></td>
            </tr>
          <% } else { %>
            <tr>
              <td align="left"  width="30%"><a href="element/service.jsp?node=<%=nodeId%>&intf=<%=ipAddr%>&service=<%=service.getServiceId()%>"><%=service.getServiceName()%></a></td>
              <td align="right" width="70%" bgcolor="#cccccc"><b><%=ElementUtil.getServiceStatusString(service)%></b></td>
            </tr>          
          <% } %>
        <% } %>
      </table>
    </td>
  </tr>
  <tr bgcolor="#999999">
    <td colspan="2">Percentage over last 24 hours</td> <%-- next iteration, read this from same properties file that sets up for RTCVCM --%></td>
<% } %>
  </tr>   
</table>   

<%!    
    /** Convenient anonymous class for sorting Service objects by service name. */
    protected Comparator serviceComparator = new Comparator() {
        public int compare(Object o1, Object o2) {
            //for brevity's sake assume they're both Services
            Service s1 = (Service)o1;
            Service s2 = (Service)o2;
            
            return s1.getServiceName().compareTo(s2.getServiceName());
        }
        
        public boolean equals(Object o1, Object o2) {
            //for brevity's sake assume they're both Services
            Service s1 = (Service)o1;
            Service s2 = (Service)o2;
            
            return s1.getServiceName().equals(s2.getServiceName());
        }        
    };

    
    public Service[] getServices(Interface intf) throws java.sql.SQLException {
        if( intf == null ) {
            throw new IllegalArgumentException( "Cannot take null parameters." );
        }
        
        Service[] svcs = NetworkElementFactory.getServicesOnInterface(intf.getNodeId(), intf.getIpAddress());
        
        if( svcs != null ) {
            Arrays.sort(svcs, this.serviceComparator); 
        }
        
        return svcs;
    }
%>

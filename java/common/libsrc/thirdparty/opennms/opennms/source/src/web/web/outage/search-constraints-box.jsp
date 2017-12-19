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

<%@page language="java" contentType="text/html" session="true" import="java.util.*,org.opennms.web.Util,org.opennms.web.outage.*,org.opennms.web.outage.filter.Filter" %>

<%
    //required attribute parms
    OutageQueryParms parms = (OutageQueryParms)request.getAttribute( "parms" );

    if( parms == null ) {
        throw new ServletException( "Missing the outage parms request attribute." );
    }

    int length = parms.filters.size();    
%>

<!-- acknowledged/outstanding row -->
<p>
  <form action="outage/list" method="GET" name="outage_search_constraints_box_outtype_form">
    <%=Util.makeHiddenTags(request, new String[] {"outtype"})%>
    
    Outage type:
    <select name="outtype" size="1" onChange="javascript: document.outage_search_constraints_box_outtype_form.submit()">
      <option value="<%=OutageUtil.getOutageTypeString(OutageFactory.OutageType.CURRENT)%>" <%=(parms.outageType == OutageFactory.OutageType.CURRENT) ? "selected=\"1\"" : ""%>>
        Current
      </option>
      
      <option value="<%=OutageUtil.getOutageTypeString(OutageFactory.OutageType.RESOLVED)%>" <%=(parms.outageType == OutageFactory.OutageType.RESOLVED) ? "selected=\"1\"" : ""%>>
        Resolved
      </option>
      
      <option value="<%=OutageUtil.getOutageTypeString(OutageFactory.OutageType.BOTH)%>" <%=(parms.outageType == OutageFactory.OutageType.BOTH) ? "selected=\"1\"" : ""%>>
        Both Current &amp; Resolved
      </option>
    </select>        
  </form>    
</p>

<% if( length > 0 ) { %>
  <p>
    <ol>    
      <% for(int i=0; i < length; i++) { %>
        <% Filter filter = (Filter)parms.filters.get(i); %> 
        
        <li>
          <%=filter.getTextDescription()%>
          <a href="<%=OutageUtil.makeLink(request, parms, filter, false)%>">Remove</a>
        </li>
      <% } %>
    </ol>    
  </p>    
<% } %>  


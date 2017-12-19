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

-->

<%!
    public PerformanceModel model = null;
    protected KSC_PerformanceReportFactory reportFactory = null;

    public void init() throws ServletException {
        try {
            initPerfModel();
            initReportFactory();
        }
        catch (Exception e) {
            throw new ServletException( "Could not initialize the Graph Form Page", e );
        }
    }
    
    public void initPerfModel() throws ServletException
    {
        try {
            this.model = new PerformanceModel( org.opennms.web.ServletInitializer.getHomeDir() );
        }
        catch( Exception e ) {
            throw new ServletException( "Could not initialize the PerformanceModel", e );
        }
    }
    
    public void initReportFactory() throws ServletException
    {
        try {
            KSC_PerformanceReportFactory.init();
            this.reportFactory = KSC_PerformanceReportFactory.getInstance();
        }
        catch( Exception e ) {
            throw new ServletException( "Could not initialize KSC_PerformanceReportFactory", e );
        }
    }
%>


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

package org.opennms.web.availability.raw;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Category;
import org.opennms.core.resource.Vault;
import org.opennms.report.availability.AvailabilityReport;
import org.opennms.web.MissingParameterException;

/**
 * @author <A HREF="mailto:jacinta@opennms.org">Jacinta Remedios </A>
 * @author <A HREF="mailto:larry@opennms.org">Lawrence Karnowski </A>
 * @author <A HREF="http://www.opennms.org/">OpenNMS </A>
 */
public class RawAvailabilityServlet extends HttpServlet {
    static Category log = Category.getInstance(RawAvailabilityServlet.class.getName());

    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");
        String username = request.getRemoteUser();
        ServletConfig config = this.getServletConfig();

        if (category == null) {
            throw new MissingParameterException("category");
        }

        if (username == null) {
            username = "";
        }

        try {
            // String url =
            // config.getServletContext().getRealPath(request.getRequestURI());
            String url = config.getServletContext().getRealPath("/availability/availabilityRaw");
            int index = url.indexOf("/availability/availabilityRaw");
            String urlReplace = url.substring(0, index);
            urlReplace += "/images/logo.gif";

            AvailabilityReport report = new AvailabilityReport(username);
            report.getReportData(urlReplace, category, "all", null);

            if (log.isDebugEnabled())
                log.info("Generated Report Data... ");

            Reader xml = new FileReader(Vault.getHomeDir() + "/share/reports/AvailReport.xml");
            Writer out = response.getWriter();

            response.setContentType("text/xml");
            this.streamToStream(xml, out);
            out.close();
        } catch (Exception e) {
            throw new ServletException("AvailabilityServlet: ", e);
        }
    }

    /**
     * @deprecated Should use {@link org.opennms.web.Util#streamToStream
     *             Util.streamToStream} instead.
     */
    protected void streamToStream(Reader in, Writer out) throws IOException {
        char[] b = new char[100];
        int length;

        while ((length = in.read(b)) != -1) {
            out.write(b, 0, length);
        }
    }
}

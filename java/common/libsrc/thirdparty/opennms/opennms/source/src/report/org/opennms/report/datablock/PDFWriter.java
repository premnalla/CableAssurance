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
// Tab Size = 8
//

package org.opennms.report.datablock;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.text.SimpleDateFormat;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.avalon.framework.logger.*;

import org.apache.fop.apps.Version;
import org.apache.fop.messaging.MessageHandler;
import org.apache.log4j.Category;
import org.apache.log4j.Priority;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.opennms.core.utils.ThreadCategory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * PDFWriter is a XML to PDF generator. It uses apache's fop api for conversion.
 * The conversion is a 2 step process that takes 1) XML + XSL to FOT (Formatting
 * Object Tree) 2) FOT is converted to PDF using the FOP api's
 * 
 * @author <A HREF="mailto:jacinta@oculan.com">Jacinta Remedios </A>
 * @author <A HREF="http://www.oculan.com">oculan.com </A>
 * 
 */
public class PDFWriter extends Object {
    /**
     * The log4j category used to log debug messsages and statements.
     */
    private static final String LOG4J_CATEGORY = "OpenNMS.Report";

    /**
     * FOP Driver
     */
    org.apache.fop.apps.Driver m_driver;

    /**
     * XSL path
     */
    String m_xslSource = null;

    /**
     * Default Constructor
     */
    public PDFWriter(String xslSource) {
        m_xslSource = xslSource;
    }

    /**
     * This converts the outage report from xml to pdf format using fop.
     */
    public void generateHTML(FileReader xml, OutputStream fotFileName) throws MarshalException, ValidationException, IOException, Exception {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        ThreadCategory.setPrefix(LOG4J_CATEGORY);
        Category log = ThreadCategory.getInstance(PDFWriter.class);
        try {
            if (log.isInfoEnabled())
                log.info("XSL File " + m_xslSource);
            Reader xsl = new FileReader(m_xslSource);

            // create the SAX parser to supply to the FOP Driver to build the FO
            // tree.

            TransformerFactory tfact = TransformerFactory.newInstance();
            Transformer processor = tfact.newTransformer(new StreamSource(xsl));
            processor.transform(new StreamSource(xml), new StreamResult(fotFileName));

            xml = null;
            fotFileName.close();
            fotFileName = null;
        } catch (IOException ioe) {
            if (log.isEnabledFor(Priority.FATAL))
                log.fatal("IOException ", ioe);
            ioe.printStackTrace();
            throw ioe;
        } catch (Exception e) {
            if (log.isEnabledFor(Priority.FATAL))
                log.fatal("Exception ", e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This converts the outage report from xml to pdf format using fop.
     */
    public void generatePDF(FileReader xml, OutputStream pdfWriter, String fotFileName) throws MarshalException, ValidationException, IOException, Exception {
        ThreadCategory.setPrefix(LOG4J_CATEGORY);
        Category log = ThreadCategory.getInstance(PDFWriter.class);
		Logger avalonLogger = new Log4JLogger(log);
		
		try {
            if (log.isInfoEnabled())
                log.info("XSL File " + m_xslSource);
            Reader xsl = new FileReader(m_xslSource);

            // create the SAX parser to supply to the FOP Driver to build the FO
            // tree.
            // File file = new File(ConfigFileConstants.getHome() +
            // "/share/reports/temp.fot");
            File file = new File(fotFileName);
            FileWriter fot = new FileWriter(file);

            // XSLTProcessor processor = XSLTProcessorFactory.getProcessor();
            // processor.process( new XSLTInputSource(xml), new
            // XSLTInputSource(xsl), new XSLTResultTarget(fot) );
            TransformerFactory tfact = TransformerFactory.newInstance();
            Transformer processor = tfact.newTransformer(new StreamSource(xsl));
            processor.transform(new StreamSource(xml), new StreamResult(fot));

            if (log.isInfoEnabled())
                log.info("FOT generated is saved in " + fotFileName);

            xml = null;
            // initDriver();
			Logger nullLogger = new NullLogger();
			MessageHandler.setScreenLogger(nullLogger);
            MessageHandler.setOutputMethod(MessageHandler.NONE);

            fot.close();
            fot = null;
            Reader reader = new FileReader(file);
            InputSource dataSource = new InputSource(reader);

            m_driver = new org.apache.fop.apps.Driver(dataSource, pdfWriter);
			m_driver.setLogger(avalonLogger);
			m_driver.setRenderer(org.apache.fop.apps.Driver.RENDER_PDF);
            m_driver.run();

            /*
             * if(log.isInfoEnabled()) log.info("Build FO Tree");
             * m_driver.buildFOTree(xmlParser, dataSource);
             * 
             * if(log.isInfoEnabled()) log.info("Format"); m_driver.format();
             * 
             * m_driver.setOutputStream(pdfWriter);
             * 
             * if(log.isInfoEnabled()) log.info("Render"); m_driver.render();
             * 
             * if(log.isInfoEnabled()) { log.info("Rendered"); }
             */
            Runtime runn = Runtime.getRuntime();
            runn.exec("rm " + fotFileName);
        } catch (IOException ioe) {
            if (log.isEnabledFor(Priority.FATAL))
                log.fatal("IOException ", ioe);
            ioe.printStackTrace();
            throw ioe;
        } catch (Exception e) {
            if (log.isEnabledFor(Priority.FATAL))
                log.fatal("Exception ", e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Initialises the fop driver
     */
    private void initDriver() throws Exception {
        ThreadCategory.setPrefix(LOG4J_CATEGORY);
        Category log = ThreadCategory.getInstance(PDFWriter.class);
		Logger avalonLogger = new Log4JLogger(log);
		
        try {
            m_driver = new org.apache.fop.apps.Driver();
			m_driver.setLogger(avalonLogger);
            m_driver.setRenderer("org.apache.fop.render.pdf.PDFRenderer", Version.getVersion());
            m_driver.addElementMapping("org.apache.fop.fo.StandardElementMapping");
            m_driver.addElementMapping("org.apache.fop.svg.SVGElementMapping");
            // m_driver.addPropertyList(
            // "org.apache.fop.fo.StandardPropertyListMapping" );
            // m_driver.addPropertyList(
            // "org.apache.fop.svg.SVGPropertyListMapping" );
        } catch (Exception e) {
            if (log.isEnabledFor(Priority.FATAL))
                log.fatal("Exception : ", e);
            throw e;
        }
    }

    /**
     * Creates an xml parser.
     * 
     * @return XMLReader
     */
    private XMLReader createParser() throws Exception {
        ThreadCategory.setPrefix(LOG4J_CATEGORY);
        Category log = ThreadCategory.getInstance(PDFWriter.class);
        String parserClassName = null;
        if (parserClassName == null) {
            parserClassName = "org.apache.xerces.parsers.SAXParser";
        }
        try {
            return (XMLReader) Class.forName(parserClassName).newInstance();
        } catch (Exception e) {
            if (log.isEnabledFor(Priority.FATAL))
                log.fatal("Exception : ", e);
            e.printStackTrace();
            throw (e);
        }
    }

}

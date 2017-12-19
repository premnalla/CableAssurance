/*
 * Copyright: Copyright © 2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */
package ossj.qos.pm.threshold;

//import ossj.qos.pm.util.Log;
//import ossj.qos.pm.util.MeasurementReport_3GPP_XML_tags;

import ossj.qos.util.Log;
import ossj.qos.util.MeasurementReport_3GPP_XML_tags;

// XML stuff
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

// IO stuff (for parsing XML)
import java.io.InputStream;
import java.io.ByteArrayInputStream; // as a substitute for a file
import java.io.IOException;

// net stuff, for
import java.net.*;

/**
 * Handles performance monitor reports received by PerformanceDataEvents.
 * Extracts the measurement value from the performance data event and returns it
 * as a String.
 *
 * <p>Implementation based on <tt>3GPP TS 32.401 V4.0.0 (2001-09), technical
 * specification; 3rd Generation partnership Project; Technical Specification
 * Group Services and Aspects; Telecommunication Management; Performance
 * Management (PM); Concept and Requirements (Release 4).</tt>
 *
 * @author Henrik Lindstrom, Ericsson
 * @see ossj.qos.pm.util.MeasurementReport_3GPP_XML_tags
 */
public class PerformanceMonitorReportHandler {

    /**
     * The base URI used for parsing. (Where to find relative URIs)
     */
    private static String URI_BASE = null;

    /**
     * Name of the DTD file needed for processing the measurement reports.
     */
    private static final String DTD_FILE_NAME = "MeasDataCollection.dtd";

    /**
     * Defines the base URI for XML parsing. This is where the parser will look
     * for relative URIs, for instance to the DTD file.
     */
    public static void setURIBase( String uri ) {
        URI_BASE = uri;
    }

    /**
     * Initializes the URI_BASE to the DTD_FILE_NAME location. Uses
     * ClassLoader.getResource() to locate the file.
     */
    static {
        Log.write("PerformanceMonitorReportHandler",Log.LOG_ALL,"static()",
            "getting path to "+ DTD_FILE_NAME);
        // get url to DTD_FILE_NAME (the file must be in the class path)
        URL dtdURL = ClassLoader.getSystemClassLoader().getResource( DTD_FILE_NAME );

        // extract the uri
        if ( dtdURL != null ) {
            try {
                String uri = dtdURL.toExternalForm();
                uri = uri.substring(0, uri.lastIndexOf('/')+1 );
                // The '/' had to be used because URL does not use File.separatorChar
                PerformanceMonitorReportHandler.setURIBase( uri );
            } catch ( Exception e ) {
                Log.write("PerformanceMonitorReportHandler",Log.LOG_MAJOR,
                    "static()","dtdURL="+ dtdURL + ", exception="+e );
            }
        } else {
            Log.write("PerformanceMonitorReportHandler",Log.LOG_MAJOR,
                "static()","DTD_FILE_NAME="+ DTD_FILE_NAME + " not found by getResource()." );
        }
    }

    /**
     * Converts a report into a (XML) Document.
     *
     * @param report a String containing the XML content
     * @return measurment report as a Document
     * @exception ParserConfigurationException if there is a problem with the parser
     * @exception SAXException if there is a problem to parse the XML document
     * @exception IOException if there is a problem with the internal
     * ByteArrayInputStream
     */
    public static Document getDocument( String report )
                                            throws ParserConfigurationException,
                                                SAXException, IOException {
        Log.write("PerformanceMonitorReportHandler.getDocument(): called");

        // turn report into an inputstream that can be parsed.
        byte[] reportByteArray = ((String)report).getBytes();
        InputStream is = new ByteArrayInputStream( reportByteArray );

        // Create an instance of the DocumentBuilderFactory
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            //Get the DocumentBuilder from the factory that we just got above.
            DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();

            // parse the XML
            Document document = docBuilder.parse( is, URI_BASE );

            return document;
        } catch ( ParserConfigurationException e ) {
            Log.write("PerformanceMonitorReportHandler",Log.LOG_MAJOR,"getDocument()","ParserConfigurationException="+e);
            throw e;
        } catch ( SAXException e ) {
            Log.write("PerformanceMonitorReportHandler",Log.LOG_MAJOR,"getDocument()","SAXException="+e);
            throw e;
        } catch ( IOException e ) {
            Log.write("PerformanceMonitorReportHandler",Log.LOG_MAJOR,"getDocument()","IOException="+e);
            throw e;
        }
    }

    /**
     * Returns the measurement value for the nedn, moid, mt. moid is currently
     * ignored.
     *
     * @param doc XML document
     * @param nedn
     * @param mt
     * @param moid
     * @return measurement value as a string or null if it is not found.
     */
    public static String getMeasurementValue( Document doc, String nedn, String mt, String moid ) {
        Log.write("PerformanceMonitorReportHandler.getMeasurementValue(): called");
        Log.write("Get measurement value for " + nedn + " : " + mt + " : " + moid  );

        Element mdElement = null;
        NodeList nodeList = null;
        Node node = null;
        String mtValue = null;
        int mtValueIndex = -1;

        mdElement = getMDNodeWithNEDN( doc, nedn );

        if ( mdElement == null ) {
            return null;
        }
        // find the mt in the mt node list
        nodeList = mdElement.getElementsByTagName( MeasurementReport_3GPP_XML_tags.MT_ELEMENT );
        for (int i=0;i<nodeList.getLength();i++) {
            node = nodeList.item(i); // a mt element
            node = node.getFirstChild(); // get the mt text node
            mtValue = node.getNodeValue().trim(); // value of the text node
            if ( mt.equals( mtValue ) ) {
                mtValueIndex = i;
                break;
            }
        }
        if ( mtValueIndex==-1 ) { // no mt found
            return null;
        }

        //
        // Here code can be added to find the moid...
        // Then the mvElement should ge used to get the r value
        //
        // mdElement.getElementsByTagName( MeasurementReport_3GPP_XML_tags.MV_ELEMENT );
        // ...find the right mv (with moid)
        // ...get r value from that mv
        // mvElement.getElementsByTagName( MeasurementReport_3GPP_XML_tags.R_ELEMENT );
        //

        // get the r (mv) value list
        nodeList = mdElement.getElementsByTagName( MeasurementReport_3GPP_XML_tags.R_ELEMENT );

        try {
            node = nodeList.item( mtValueIndex ); // get the r element
        } catch ( ArrayIndexOutOfBoundsException e ) { // could happen if the corresponding value is missing in XML document
            Log.write("PerformanceMonitorReportHandler",Log.LOG_MAJOR,
                "getMeasurementValue()","suspicous document, exception="+e);
            throw e;
        }
        node = node.getFirstChild(); // get the mv text node
        return node.getNodeValue().trim(); // return the mv value
    }

    /**
     * Returns the measurement time stamp for a nead.
     *
     * @param doc the performance monitor report
     * @param nedn
     * @return measurement time stamp or null if nedn is not found
     */
    public static String getMeasurementTimeStamp( Document doc, String nedn ) {
        Log.write("PerformanceMonitorReportHandler.getMeasurementTimeStamp(): called");

        NodeList mtsNodeList = null;
        Node mtsNode = null, mtsTextNode = null;
        Element mdElement = null;

        mdElement = getMDNodeWithNEDN( doc, nedn );
        if ( mdElement == null ) {
            return null;
        }

        mtsNodeList = mdElement.getElementsByTagName( MeasurementReport_3GPP_XML_tags.MTS_ELEMENT );
        mtsNode = mtsNodeList.item(0);
        mtsTextNode = mtsNode.getFirstChild(); // the TextNode with the mts value
        return mtsTextNode.getNodeValue().trim();
    }

    /**
     * Returns the suspect flag for a nead. Currently the method do not support
     * the moid attribute, which is ignored.
     *
     * @param doc the performance monitor report
     * @param nedn
     * @param moid
     * @return measurement time stamp or null if nedn (or moid) is not found
     */
    public static String getSuspectFlag( Document doc, String nedn, String moid ) {
        Log.write("PerformanceMonitorReportHandler.getSuspectFlag(): called");

        NodeList sfNodeList = null;
        Node sfNode = null, sfTextNode = null;
        Element mdElement = null;

        mdElement = getMDNodeWithNEDN( doc, nedn );
        if ( mdElement == null ) {
            return null;
        }

        sfNodeList = mdElement.getElementsByTagName( MeasurementReport_3GPP_XML_tags.SF_ELEMENT );
        sfNode = sfNodeList.item(0);
        sfTextNode = sfNode.getFirstChild(); // the TextNode with the sf value
        return sfTextNode.getNodeValue().trim();
    }

    /**
     * Returns the md (measData) node.
     *
     * @param doc the performance monitor report
     * @param nedn distinguished name for NE
     * @return the md node or null if not found
     */
    private static Element getMDNodeWithNEDN( Document doc, String nedn ) {
        NodeList mdNodeList = null, nednNodeList=null;
        Node nednNode = null, nednTextNode = null;
        Element mdElement = null;
        String nednValue = null;

        // get all md elements in document
        mdNodeList = doc.getElementsByTagName(
            MeasurementReport_3GPP_XML_tags.MD_ELEMENT );

        // find md node with the nedn
        try {
            for (int i=0;i<mdNodeList.getLength();i++) {
                mdElement = (Element)mdNodeList.item(i); // get a md node
                // get nedn
                nednNodeList = mdElement.getElementsByTagName(
                    MeasurementReport_3GPP_XML_tags.NEDN_ELEMENT );
                nednNode = nednNodeList.item(0); // list returned but shall only contain one element, the nedn node

                nednTextNode = nednNode.getFirstChild(); // this is the nedn value (or something is wrong...)

                nednValue = nednTextNode.getNodeValue();
                if ( nedn.equals( nednValue ) ) {
                    return mdElement;
                }
            }
        } catch ( ArrayIndexOutOfBoundsException e ) {
            // could happen if the input document is not the expected
            Log.write("PerformanceMonitorReportHandler",Log.LOG_MAJOR,
                "getMDNodeWithNEDN()","Suspicious document, exception="+e );
            throw e;
        }
        return null; // not found!
    }
}
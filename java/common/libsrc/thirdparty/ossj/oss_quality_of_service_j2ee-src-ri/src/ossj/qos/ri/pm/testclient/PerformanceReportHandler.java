package ossj.qos.ri.pm.testclient;
/*
 * Copyright: Copyright © 2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */

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
 */
public class PerformanceReportHandler {
    private static final String MT_ELEMENT = "mt";
    private static final String MV_ELEMENT = "mv";
    private static final String R_ELEMENT = "r";
    private static final String MTS_ELEMENT = "mts";
    private static final String SF_ELEMENT = "sf";
    private static final String MD_ELEMENT = "md";
    private static final String NEDN_ELEMENT = "nedn";

    /**
     * The base URI used for parsing. (Where to find relative URIs)
     */
    private static String URI_BASE = null;

    /**
     * Name of the DTD file needed for processing the measurement reports.
     */
    private static final String DTD_FILE_NAME = "MeasDataCollection.dtd";

    private static String[] itemName = null;
    private static String[] itemVal = null;

    private static Document document = null;

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
        // get url to DTD_FILE_NAME (the file must be in the class path)
        URL dtdURL = ClassLoader.getSystemClassLoader().getResource( DTD_FILE_NAME );

        // extract the uri
        if ( dtdURL != null ) {
            try {
                String uri = dtdURL.toExternalForm();
                uri = uri.substring(0, uri.lastIndexOf('/')+1 );
                // The '/' had to be used because URL does not use File.separatorChar
                PerformanceReportHandler.setURIBase( uri );
            } catch ( Exception e ) {
            }
        } else {
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
    public static void createDocument( String report )
                      throws ParserConfigurationException,
                                      SAXException, IOException {
        // turn report into an inputstream that can be parsed.
        byte[] reportByteArray = ((String)report).getBytes();
        InputStream is = new ByteArrayInputStream( reportByteArray );

        // Create an instance of the DocumentBuilderFactory
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            //Get the DocumentBuilder from the factory that we just got above.
            DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();

            // parse the XML
            document = docBuilder.parse( is, URI_BASE );

        } catch ( ParserConfigurationException e ) {
            throw e;
        } catch ( SAXException e ) {
            throw e;
        } catch ( IOException e ) {
            throw e;
        }
    }

    /**
     * Returns the measurement value for the nedn, moid, mt. moid is currently
     * ignored.
     *
     * @param doc XML document
     * @param nedn
     */
    public static void getMeasurementValue(String nedn ) {
        Element mdElement = null;
        NodeList nodeList = null;
        Node node = null;
        String mtValue = null;
        int mtValueIndex = -1;
        mdElement = getMDNodeWithNEDN( nedn );

        System.out.println(nedn);

        // find the mt in the mt node list
        nodeList = mdElement.getElementsByTagName(MT_ELEMENT );

        int len = nodeList.getLength();
        itemName = new String[len];
        itemVal = new String[len];

        for (int i=0;i<len;i++) {
            node = nodeList.item(i); // a mt element
            node = node.getFirstChild(); // get the mt text node
            mtValue = node.getNodeValue().trim(); // value of the text node
            itemName[i] = mtValue;
        }

        //
        // Here code can be added to find the moid...
        // Then the mvElement should ge used to get the r value
        //
        // mdElement.getElementsByTagName( MV_ELEMENT );
        // ...find the right mv (with moid)
        // ...get r value from that mv
        // mvElement.getElementsByTagName( R_ELEMENT );
        //

        // get the r (mv) value list
        nodeList = mdElement.getElementsByTagName( R_ELEMENT );

        for (int i=0;i<nodeList.getLength();i++) {
            node = nodeList.item(i); // a mt element
            node = node.getFirstChild(); // get  r the text node
            mtValue = node.getNodeValue().trim(); // value of the text node
            itemVal[i] = mtValue;
        }
    }

    public static String[]  getItemName() {
        return itemName;
    }

    public static String[]  getItemValue() {
        return itemVal;
    }
    
    /**
     * Returns the md (measData) node.
     *
     * @param doc the performance monitor report
     * @param nedn distinguished name for NE
     * @return the md node or null if not found
     */
    private static Element getMDNodeWithNEDN(String nedn ) {
        NodeList mdNodeList = null, nednNodeList=null;
        Node nednNode = null, nednTextNode = null;
        Element mdElement = null;
        String nednValue = null;

        // get all md elements in document
        mdNodeList = document.getElementsByTagName( MD_ELEMENT );

        // find md node with the nedn
        try {
            for (int i=0;i<mdNodeList.getLength();i++) {
                mdElement = (Element)mdNodeList.item(i); // get a md node
                // get nedn
                nednNodeList = mdElement.getElementsByTagName(
                    NEDN_ELEMENT );
                nednNode = nednNodeList.item(0); // list returned but shall only contain one element, the nedn node

                nednTextNode = nednNode.getFirstChild(); // this is the nedn value (or something is wrong...)

                nednValue = nednTextNode.getNodeValue();
                if ( nedn.equals( nednValue ) ) {
                    return mdElement;
                }
            }
        } catch ( ArrayIndexOutOfBoundsException e ) {
            // could happen if the input document is not the expected
            throw e;
        }
        return null; // not found!
    }
}

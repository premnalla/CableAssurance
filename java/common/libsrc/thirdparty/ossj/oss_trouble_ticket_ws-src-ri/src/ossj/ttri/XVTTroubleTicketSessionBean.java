package ossj.ttri;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.oss.*;
import javax.oss.trouble.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Properties;
import java.util.Vector;

/**
 * XVTTroubleTicketSessionBean Implementation Class
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */

public class XVTTroubleTicketSessionBean implements SessionBean {

    private static final boolean VERBOSE = true;
    private InitialContext namingContext;
    private SessionContext ctx;
    private JVTTroubleTicketSession jvtSession = null;

    //-----------------------------------------------------------------------------
    //
    // Container callbacks
    //
    //-----------------------------------------------------------------------------

    public TroubleTicketKey SerializeKey(Document doc, String tag) {
        TroubleTicketKey trKey = new TroubleTicketKeyImpl();
        TroubleTicketKey retKey = null;
        Serializer serializer = trKey.makeSerializer(XmlSerializer.class.getName());
        XmlSerializer ttXmlSerializer = (XmlSerializer) serializer;
        Element element = getElement(doc, tag);
        retKey = (TroubleTicketKey) ttXmlSerializer.fromXml(element);
        return retKey;
    }

    public TroubleTicketValue SerializeValue(Document doc, String tag) {
        TroubleTicketValue trVal = new TroubleTicketValueImpl();
        TroubleTicketValue retVal = null;
        Serializer serializer = trVal.makeSerializer(XmlSerializer.class.getName());
        XmlSerializer ttXmlSerializer = (XmlSerializer) serializer;
        Element element = getElement(doc, tag);
        retVal = (TroubleTicketValue) ttXmlSerializer.fromXml(element);
        return retVal;
    }


    public org.w3c.dom.Element getElement(Document doc, String tagName) {

        log("tag name:" + tagName);


        log("getElement 1");
        Node node = null;
        NodeList nodeList = null;

        nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", tagName);

        log("getElement 2");
        if (nodeList != null) {

            log("nodeList != null");
            log("nodeList.getLength()" + nodeList.getLength());
            node = nodeList.item(0);
        } else
            log("element is null");

        log("getElement 3");
        if (node == null) log("node is null");
        Element element = (Element) node;

        log("getElement 4");
        return element;

    }

    public void ejbCreate() {
        log("XVT SessionBean.ejbCreate - ejbCreate method");


        try {
            namingContext = new InitialContext();
        } catch (NamingException nex) {
            log("XVTSession:ejbCreate:  Naming exception caught while creating InitialContext");
            nex.printStackTrace();
        }
    }

    public void ejbActivate() {
        log("XVTSession:ejbActivate");
    }

    public void ejbRemove() {
        log("XVTSessionejbRemove");
    }

    public void ejbPassivate() {
        log("XVTSessionejbPassivate called");
    }

    public void setSessionContext(SessionContext ctx) {
        log("XVTSessionsetSessionContext");
        this.ctx = ctx;
    }


    //------------------------------------------------------------------------
    //
    // MetaData Methods
    //
    //------------------------------------------------------------------------

    //Get the Options supported by a Session Bean
    //Input Parameter is equal to  getSupportedOperationsRequest as defined in XML Schema
    //Output Parameter is equal to  getSupportedOperationsResponse as defined in XML Schema
    public String getSupportedOperations(String getSupportedOperationsRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.EJBException {
        return new String();
    }

    //Get the Trouble Ticket types supported by the Session Component
    //Input Parameter is equal to  getTroubleTicketTypesRequest as defined in XML Schema
    //Output Parameter is equal to  getTroubleTicketTypesResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    public String getTroubleTicketTypes(String getTroubleTicketTypesRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.EJBException {
        return new String();
    }

    //Get the Query Names supported by the Session Component
    //Input Parameter is equal to  getQueryTypesRequest as defined in XML Schema
    //Output Parameter is equal to  getQueryTypesResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    public String getQueryTypes(String getQueryTypesRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.EJBException {
        return new String();
    }

    //Get the Event Type Names supported by the Session Component
    //Input Parameter is equal to  getEventTypesRequest as defined in XML Schema
    //Output Parameter is equal to  getEventTypesResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    public String getEventTypes(String getEventTypesRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.EJBException {
        return new String();
    }


    //Get the Event Descriptor associated with an event type
    //Input Parameter is equal to  getEventPropertyDescriptorRequest as defined in XML Schema
    //Output Parameter is equal to getEventPropertyDescriptorResponse as defined in XML Schema
    //which contains the XML representation of the EventPropertyDescriptor
    public String getEventPropertyDescriptor(String getEventPropertyDescriptorRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.EJBException {
        return new String();
    }

    //------------------------------------------------------------------------
    // Factory Methods
    //------------------------------------------------------------------------
    //Create a Value Type object for a specific Trouble Ticket type
    //Not to be confused with the creation of a Trouble Ticket
    //The Session Bean is used as a factory for the creation of Value types

    //Input Parameter is equal to  newTroubleTicketValueRequest as defined in XML Schema
    //Output Parameter is equal to newTroubleTicketValueResponse as defined in XML Schema
    /**
     public String newTroubleTicketValue( String newTroubleTicketValueRequest)
     throws javax.oss.UnsupportedOperationException,javax.ejb.CreateException,
     java.rmi.RemoteException
     **/
    public String makeTroubleTicketValue(String NewTroubleTicketValueRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.CreateException, javax.ejb.EJBException {
        //Wipro
		//return new String();
		return TroubleTicketValue.class.getName();
		//Wipro
    }

    //Create a Query Value Instance matching a Query type
    //Input Parameter is equal to  newQueryValueRequest as defined in XML Schema
    //Output Parameter is equal to  newQueryValueResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    public String makeQueryValue(String NewQueryValueRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.EJBException
            /**
             public String newQueryValue(String newQueryValueRequest)
             throws javax.oss.IllegalArgumentException, java.rmi.RemoteException
             **/ {
        return new String();
    }



    //------------------------------------------------------------------------
    //
    // Get Methods
    //
    //------------------------------------------------------------------------

    //Get a Single Trouble Ticket given its ID and return only the requested attributes
    //Input Parameter is equal to GetTroubleTicketByKeyRequest as defined in XML Schema
    //Output Parameter is equal to GetTroubleTicketByKeyResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema

    public String getTroubleTicketByKey(String getTroubleTicketByKeyRequest)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.ObjectNotFoundException,
            javax.ejb.EJBException {
        log("*****  XVT: getTroubleTicketByKey  *****  \n");

        TroubleTicketValue ttValue;
        String xmlGetResponse = null;
        JVTTroubleTicketSession jvtSession = null;
        try {

            // Since Validation is turned off
            Document doc = parseXmlString(getTroubleTicketByKeyRequest);

            // Verify that the root element name matches the one designated for this method.

            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);

            if (!rootName.equals("tt:getTroubleTicketByKeyRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }

            // Get the ttKey from the passed in xml doc


            TroubleTicketKey trKey = SerializeKey(doc, "troubleTicketKey");

            // Extract the attNames from the xml req
            String[] attrNames = this.getXmlAttNames(doc);

            //VP
            log("Number of attrNames = " + attrNames.length);
            log("XVT Calling TroubleTicketSession.getTroubleTicket");
            jvtSession = this.getJVTRemoteInterface();

            ttValue = (TroubleTicketValue) jvtSession.getTroubleTicketByKey(trKey, attrNames);


            String[] desAttrs = ttValue.getPopulatedAttributeNames();
            ((TroubleTicketValueImpl) ttValue).setDesiredAttributes(desAttrs, true);



            // Convert the ttValue object into its xml format
            String xmlTTValue = ((TroubleTicketValueImpl) ttValue).toXml();
            //System.out.println( xmlTTValue );

            // Add the respective XML envelope
            xmlGetResponse = this.addXmlRootElement("getTroubleTicketByKeyResponse", xmlTTValue);


        } catch (javax.oss.IllegalArgumentException illEx) {
            String xmlException = makeXmlException("getTroubleTicketByKeyException", "IllegalArgumentException",
                    illEx.getMessage());
            log("XVTTroubleTicketSession.getTT: IllegalArgumentException caught on TTSession.getManagedEntity");
            illEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);

        } catch (javax.oss.UnsupportedOperationException unSuppEx) {
            String xmlException = makeXmlException("getTroubleTicketByKeyException", "UnsupportedOperationException",
                    unSuppEx.getMessage());
            log("XVTTroubleTicketSession.getTT: UnsupportedOperationException caught on TTSession.getManagedEntity");
            unSuppEx.printStackTrace();


            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("getTroubleTicketByKeyException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.getTT: RemoteException caught on TTSession.getManagedEntity");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (javax.ejb.ObjectNotFoundException objNFex) {
            String xmlException = makeXmlException("getTroubleTicketByKeyException", "ObjectNotFoundException",
                    objNFex.getMessage());
            log("XVTTroubleTicketSession.getTT: ObjectNotFoundException caught on TTSession.getManagedEntity");
            objNFex.printStackTrace();

            throw new javax.ejb.ObjectNotFoundException(xmlException);

        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("getTroubleTicketByKeyException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession.getTT: IOException caught on TTSession.getManagedEntity");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("getTroubleTicketByKeyException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession.getTT: SAXException caught on TTSession.getManagedEntity");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("getTroubleTicketByKeyException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession.getTT: SAXException caught on TTSession.getManagedEntity");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }

        return xmlGetResponse;


    }


    //Get Multiple Trouble Tickets given their Ids and return only the requested attributes
    //Input Parameter is equal to GetTroubleTicketByKeysRequest as defined in XML Schema
    //Output Parameter is equal to GetTroubleTicketByKeysResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    public String getTroubleTicketsByKeys(String getTroubleTicketsByKeysRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.FinderException, javax.ejb.EJBException {
        log("*****  XVT: getTroubleTicketsByKeysRequest  *****  \n");

        TroubleTicketValue[] ttVals = null;
        TroubleTicketKey[] ttKeys = null;
        String xmlGetResponse = null;
        JVTTroubleTicketSession jvtSession = null;
        try {

            // Since Validation is turned off
            Document doc = parseXmlString(getTroubleTicketsByKeysRequest);

            // Verify that the root element name matches the one designated for this method.

            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);

            if (!rootName.equals("tt:getTroubleTicketsByKeysRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }

            // Get the ttKey from the passed in xml doc
            ttKeys = XmlTranslator.getTroubleTicketKeys(doc);
            // Extract the attNames from the xml req
            String[] attrNames = this.getXmlAttNames(doc);

            log("XVT Calling TroubleTicketSession.getTroubleTicket");
            jvtSession = this.getJVTRemoteInterface();
            ttVals = (TroubleTicketValue[]) jvtSession.getTroubleTicketsByKeys(ttKeys, attrNames);
            String xmlValues = XmlTranslator.getArrayofTroubleTicketValues(ttVals, true);

            // Add the respective XML envelope
            xmlGetResponse = this.addXmlRootElement("getTroubleTicketsByKeysResponse", xmlValues);

            //System.out.println( xmlGetResponse );
        }
                //> All of the following exceptions need to contain their respective
                //  xml exceptions.
        catch (javax.oss.IllegalArgumentException illEx) {
            String xmlException = makeXmlException("getTroubleTicketsByKeysException", "IllegalArgumentException",
                    illEx.getMessage());
            log("XVTTroubleTicketSession.getTT: IllegalArgumentException caught on TTSession.getManagedEntity");
            illEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);

        } catch (javax.oss.UnsupportedOperationException unSuppEx) {
            String xmlException = makeXmlException("getTroubleTicketsByKeysException", "UnsupportedOperationException",
                    unSuppEx.getMessage());
            log("XVTTroubleTicketSession.getTT: UnsupportedOperationException caught on TTSession.getManagedEntity");
            unSuppEx.printStackTrace();

            //throw new javax.oss.UnsupportedOperationException( xmlException );
            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("getTroubleTicketsByKeysException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.getTT: RemoteException caught on TTSession.getManagedEntity");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (javax.ejb.ObjectNotFoundException objNFex) {
            String xmlException = makeXmlException("getTroubleTicketsByKeysException", "ObjectNotFoundException",
                    objNFex.getMessage());
            log("XVTTroubleTicketSession.getTT: ObjectNotFoundException caught on TTSession.getManagedEntity");
            objNFex.printStackTrace();

            throw new javax.ejb.ObjectNotFoundException(xmlException);

        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("getTroubleTicketsByKeysException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession.getTT: IOException caught on TTSession.getManagedEntity");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("getTroubleTicketsByKeysException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession.getTT: SAXException caught on TTSession.getManagedEntity");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("getTroubleTicketsByKeysException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession.getTT: SAXException caught on TTSession.getManagedEntity");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }

        return xmlGetResponse;

    }


    //Get Multiple Trouble Tickets matching  the value template and return only the requested attributes
    //Input Parameter is equal to GetTroubleTicketByTemplateRequest as defined in XML Schema
    //Output Parameter is equal to XmlValueIterator where the XmlResponseIterator return a single
    //GetTroubleTicketByTemplateResponse popultaed with how_many Trouble Tickets as defined
    //in XML Schema
    //Exceptions Contains XML as defined in Schema
    public XmlResponseIterator
            getTroubleTicketsByTemplate(String getTroubleTicketsByTemplateRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.ObjectNotFoundException, javax.ejb.EJBException
            /**throws  javax.oss.UnsupportedOperationException,
             javax.ejb.ObjectNotFoundException, java.rmi.RemoteException
             **/ {
        //TroubleTicketKeyResultIteratorIF resultKeyIter = null;
        //VP remove IF
        TroubleTicketValueIterator valueIter = null;
        XmlResponseIteratorHome xmlResIterator = null;
        XmlResponseIteratorIF xmlIter = null;
        JVTTroubleTicketSession jvtSession = null;

        try {

            // Since Validation is turned off, parsing is performed without validation.
            //> The verification code is to be moved into the ttValue.fromXml() method
            // to aviod doubling the parsing effort.
            Document doc = parseXmlString(getTroubleTicketsByTemplateRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);

            if (!rootName.equals("tt:getTroubleTicketsByTemplateRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }


            TroubleTicketValue ttv = SerializeValue(doc, "template");
            String[] attrNames = this.getXmlAttNames(doc);


            jvtSession = this.getJVTRemoteInterface();

            //VP remove the IF in cast
            valueIter = (TroubleTicketValueIterator) jvtSession.getTroubleTicketsByTemplate(ttv, attrNames);


            try {
                xmlResIterator = (XmlResponseIteratorHome) namingContext.lookup
                        ("java:comp/env/ejb/XmlResponseIteratorHome");
            } catch (NamingException nex2) {
                log("XVTSessionBean.queryTroubleTickets: NamingException caught on lookup of XmlResponseIteratorHome");
                nex2.printStackTrace();
                //MR System.exit(1);
            }
            try {
                // Now let us create the xml resp iterator
                log("About to call xmlResIterator.create....");
                xmlIter = xmlResIterator.create(valueIter, "getTroubleTicketsByTemplateResponse", attrNames);
                log("Returning from call xmlResIterator.create....");
            } catch (javax.ejb.CreateException createEx) {
                log("TTClient.createTT: CreateException ");
                createEx.printStackTrace();
            }

        }   // End of try
        catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("getTroubleTicketsByTemplateException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.createTT: RemoteException caught on JVTSession.queryTroubleTickets");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (javax.ejb.CreateException createEx) {
            String xmlException = makeXmlException("getTroubleTicketsByTemplateException", "CreateException",
                    createEx.getMessage());
            log("XVTTroubleTicketSession: javax.ejb.CreateException caught on XVTSession.queryTroubleTickets");
            createEx.printStackTrace();
            //> rethrow the remote exception at the current phase
            //> to be replaced by the close exception as defined in the
            //> new xml schema.
            //throw new javax.ejb.CreateException( xmlException );
            throw new javax.ejb.EJBException(xmlException);
        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("getTroubleTicketsByTemplateException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession: IOException caught on XVTSession.queryTroubleTickets");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("getTroubleTicketsByTemplateException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession: SAXException caught on XVTSession.queryTroubleTickets");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("getTroubleTicketsByTemplateException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession: SAXException caught on XVTSession.queryTroubleTickets");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }
        log("returning XML response Iterator....");
        //VP
        XmlResponseIterator retXmlIter = new XmlResponseIteratorImpl(xmlIter);
        return retXmlIter;

    }


    //Get Multiple Trouble Tickets matching at least one of the value template and return only the
    //requested attributes
    //Input Parameter is equal to GetTroubleTicketByTemplatesRequest as defined in XML Schema
    //Output Parameter is equal to XmlValueIterator where the XmlIterator return a single
    //GetTroubleTicketByTemplatesResponse populatedd with how_many Trouble Tickets as defined
    //in XML Schema
    //Exceptions Contains XML as defined in Schema
    public XmlResponseIterator
            getTroubleTicketsByTemplates(String getTroubleTicketsByTemplatesRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.ObjectNotFoundException,
            javax.ejb.FinderException, javax.ejb.EJBException
            /**
             throws javax.oss.UnsupportedOperationException, javax.ejb.ObjectNotFoundException,
             javax.ejb.FinderException, java.rmi.RemoteException
             **/ {
        //TroubleTicketKeyResultIteratorIF resultKeyIter = null;
        TroubleTicketValueIterator valueIter = null;
        XmlResponseIteratorHome xmlResIterator = null;
        XmlResponseIteratorIF xmlIter = null;
        JVTTroubleTicketSession jvtSession = null;

        try {

            // Since Validation is turned off, parsing is performed without validation.
            //> The verification code is to be moved into the ttValue.fromXml() method
            // to aviod doubling the parsing effort.
            Document doc = parseXmlString(getTroubleTicketsByTemplatesRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);

            if (!rootName.equals("tt:getTroubleTicketsByTemplatesRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }

            //get templates node...
            NodeList nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "templates");
            Node node = null;
            node = nodeList.item(0);

            TroubleTicketValue[] values = null;

            values = XmlTranslator.TTValuesFromXmlItems(node);

            String[] attrNames = this.getXmlAttNames(doc);
            jvtSession = this.getJVTRemoteInterface();

            //VP remove the IF
            valueIter = (TroubleTicketValueIterator) jvtSession.getTroubleTicketsByTemplates(values, attrNames);

            //resultKeyIter = (TroubleTicketKeyResultIteratorIF)
            //                jvtSession.tryCloseTroubleTickets(template,ReturnMode.RETURNALL);
            // resultKeyIter = (TroubleTicketKeyResultIteratorIF)
            //                 jvtSession.tryCloseTroubleTickets(template,returnModeValue.intValue());

            try {
                xmlResIterator = (XmlResponseIteratorHome) namingContext.lookup
                        ("java:comp/env/ejb/XmlResponseIteratorHome");
            } catch (NamingException nex2) {
                log("XVTSessionBean.queryTroubleTickets: NamingException caught on lookup of XmlResponseIteratorHome");
                nex2.printStackTrace();
                //MR System.exit(1);
            }
            try {
                // Now let us create the xml resp iterator
                xmlIter = xmlResIterator.create(valueIter, "getTroubleTicketsByTemplatesResponse", attrNames);
            } catch (javax.ejb.CreateException createEx) {
                log("TTClient.createTT: CreateException ");
                createEx.printStackTrace();
            }

        }   // End of try
        catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("getTroubleTicketsByTemplatesException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.createTT: RemoteException caught on JVTSession.queryTroubleTickets");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (javax.ejb.CreateException createEx) {
            String xmlException = makeXmlException("getTroubleTicketsByTemplatesException", "CreateException",
                    createEx.getMessage());
            log("XVTTroubleTicketSession: javax.ejb.CreateException caught on XVTSession.queryTroubleTickets");
            createEx.printStackTrace();
            //> rethrow the remote exception at the current phase
            //> to be replaced by the close exception as defined in the
            //> new xml schema.
            throw new javax.ejb.EJBException(xmlException);
        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("getTroubleTicketsByTemplatesException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession: IOException caught on XVTSession.queryTroubleTickets");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("getTroubleTicketsByTemplatesException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession: SAXException caught on XVTSession.queryTroubleTickets");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("getTroubleTicketsByTemplatesException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession: SAXException caught on XVTSession.queryTroubleTickets");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }

        //VP
        XmlResponseIterator retXmlIter = new XmlResponseIteratorImpl(xmlIter);
        return retXmlIter;
    }




    //------------------------------------------------------------------------
    //
    // Atomic Methods
    //
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // Set (Atomic)
    //------------------------------------------------------------------------
    //Setting a Single Trouble Ticket given a Value Object
    //Input Parameter is equal to  SetTroubleTicketByValueRequest as defined in XML Schema
    //Output Parameter is equal to SetTroubleTicketByValueResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    //---Implemented for the Demo---
    public String setTroubleTicketByValue(String setTroubleTicketByValueRequest)
            throws javax.oss.IllegalArgumentException,
            SetException,
            javax.ejb.ObjectNotFoundException,
            javax.ejb.EJBException,
            javax.oss.ResyncRequiredException {

        JVTTroubleTicketSession jvtSession = null;
        TroubleTicketValue ttValue = null;

        try {

            // 1. Parse ( without validation )the input xml string and get the respective xml doc.
            // 2. Verify that the correct xml request is being received.
            // 4. Create a new value object and populate it with the passed-in attributes to be set
            //    using the fromXML method.
            // 5. Invoke the respective method on the undelying JVTSession using the populated
            //    ValObject.
            // 6. Send the respective XML-formatted reply string back to the client.



            // Get the xml document from the passed in xml string
            // since Validation is turned off
            Document doc = parseXmlString(setTroubleTicketByValueRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);

            if (!rootName.equals("tt:setTroubleTicketByValueRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }

            // 4. Create a new value object.
            //TroubleTicketValue ttValue = new TroubleTicketValue();
            jvtSession = this.getJVTRemoteInterface();
            //TroubleTicketValue ttValue = jvtSession.makeTroubleTicketValue("");
            ttValue = SerializeValue(doc, "troubleTicketValue");
            //((TroubleTicketValueImpl)ttValue).fromXml( (Element)  doc );
            boolean resyncRequired = false;

            resyncRequired = getResyncRequired(doc);


            jvtSession.setTroubleTicketByValue(ttValue, resyncRequired);
        }

                // All of the following exceptions will be used to consturct the respective xml exception.
                // This xml exceptin will be embedded as the message in the respective java exception.
        catch (javax.oss.IllegalArgumentException illEx) {
            String xmlException = makeXmlException("setTroubleTicketByValueException", "illegalArgumentException",
                    illEx.getMessage());
            log("XVTTroubleTicketSession.setTroubleTicketByValue: IllegalArgumentException caught on TTSession.setTroubleTicket");
            illEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (javax.oss.SetException setEx) {
            String xmlException = makeXmlException("setTroubleTicketByValueException", "setException",
                    setEx.getMessage());
            log("XVTTroubleTicketSession.setTroubleTicketByValue: SetException caught on TTSession.setTroubleTicket");
            setEx.printStackTrace();

            throw new javax.oss.SetException(xmlException);
        } catch (javax.ejb.ObjectNotFoundException objNFex) {
            String xmlException = makeXmlException("setTroubleTicketByValueException", "objectNotFoundException",
                    objNFex.getMessage());
            log("XVTTroubleTicketSession.setTroubleTicketByValue: ObjectNotFoundException caught on TTSession.setTroubleTicket");
            objNFex.printStackTrace();

            throw new javax.ejb.ObjectNotFoundException(xmlException);
        } catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("setTroubleTicketByValueException", "remoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.setTroubleTicketByValue: RemoteException caught on TTSession.setTroubleTicket");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("SetTroubleTicketByValueException", "remoteException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession.setTroubleTicketByValue: IOException caught on TTSession.setTroubleTicket");
            ioEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("SetTroubleTicketByValueException", "remoteException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession.setTroubleTicketByValue: SAXException caught on TTSession.setTroubleTicket");
            saxEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (javax.oss.ResyncRequiredException rre) {


            String xmlException = makeXmlException("setTroubleTicketByValueException", "resyncRequiredException",
                    rre.getMessage(), ttValue.getTroubleTicketKey());


            log("XVTTroubleTicketSession.setTroubleTicketByValue: ResyncRequiredException caught on TTSession.setTroubleTicket");


            throw new javax.oss.ResyncRequiredException(rre.getManagedEntityKey(), xmlException);


        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("setTroubleTicketByValueException", "remoteException",
                    ex.getMessage());
            log("XVTTroubleTicketSession.setTroubleTicketByValue: SAXException caught on TTSession.setTroubleTicket");
            ex.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        }

        //String xmlKey = returned_key.toXml();

        // Now add the respective xml envelop
        //return this.addXmlRootElement( "SetTroubleTicketByValueException", xmlKey );
        return this.addXmlRootElement("setTroubleTicketByValueResponse", null);

    }

    /**
     //Setting Multiple Trouble Tickets each with different values
     //Input Parameter is equal to  SetTroubleTicketsByValuesRequest as defined in XML Schema
     //Output Parameter is equal to SetTroubleTicketsByValuesResponse as defined in XML Schema
     //Exceptions Contains XML as defined in Schema
     public String setTroubleTicketsByValues( String SetTroubleTicketsByValuesRequest )
     throws javax.oss.UnsupportedOperationException, SetException, javax.ejb.DuplicateKeyException ,
     javax.ejb.FinderException , javax.ejb.EJBException
     {
     return new String();
     }
     **/

    /**
     //Setting Multiple Trouble Tickets given their ID with the same value
     //Input Parameter is equal to  SetTroubleTicketsByKeysRequest as defined in XML Schema
     //Output Parameter is equal to SetTroubleTicketsByKeysResponse as defined in XML Schema
     //Exceptions Contains XML as defined in Schema
     public String setTroubleTicketsByKeys( String SetTroubleTicketsByKeysRequest )
     throws javax.oss.UnsupportedOperationException, SetException,
     javax.ejb.FinderException, javax.ejb.EJBException

     {
     return new String();
     }
     **/

    /**
     //Setting Multiple Trouble Tickets matching  the template with the same value
     //Input Parameter is equal to  SetTroubleTicketsByTemplateRequest as defined in XML Schema
     //Output Parameter is equal to SetTroubleTicketsByTemplateResponse as defined in XML Schema
     public String setTroubleTicketsByTemplate ( String SetTroubleTicketsByTemplateRequest )
     throws javax.oss.UnsupportedOperationException, SetException,
     javax.ejb.ObjectNotFoundException, javax.ejb.EJBException
     {
     return new String();
     }
     **/

    /**
     //Setting Multiple Trouble Tickets matching at least one of the template with the same value
     //Input Parameter is equal to  SetTroubleTicketsByTemplatesRequest as defined in XML Schema
     //Output Parameter is equal to SetTroubleTicketsByTemplatesResponse as defined in XML Schema
     public String setTroubleTicketsByTemplates(String SetTroubleTicketsByTemplatesRequest )
     throws javax.oss.UnsupportedOperationException, SetException,
     javax.ejb.FinderException, javax.ejb.DuplicateKeyException ,
     javax.ejb.EJBException

     {
     return new String();
     }
     **/

    //------------------------------------------------------------------------
    // Create (Atomic)
    //------------------------------------------------------------------------

    //Creation of a Single Trouble Ticket
    //Input Parameter is equal to  createTroubleTicketByValueRequest as defined in XML Schema
    //Output Parameter is equal to createTroubleTicketByValueResponse which is itself defined
    //in the XML Schema
    //---Implemented for the Demo---
    public String createTroubleTicketByValue(String createTroubleTicketByValueRequest)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.DuplicateKeyException,
            javax.ejb.CreateException,
            javax.ejb.EJBException {

        TroubleTicketKeyImpl returned_key = null;
        JVTTroubleTicketSession jvtSession = null;
        try {


            Document doc = parseXmlString(createTroubleTicketByValueRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);

            if (!rootName.equals("tt:createTroubleTicketByValueRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }
			
			//Wipro
            //String valueType = "TroubleTicketValue";
			String valueType = TroubleTicketValue.class.getName();
			//Wipro
			
            jvtSession = this.getJVTRemoteInterface();

            TroubleTicketValue ttValueInit = jvtSession.makeTroubleTicketValue(valueType);
            //TroubleTicketValue ttValue = new TroubleTicketValueImpl();
            //((TroubleTicketValueImpl) ttValue).fromXml((Element)  doc );
            //Serialize to Value
            TroubleTicketValue ttValue = SerializeValue(doc, "troubleTicketValue");

            log("---------------------------------");
            log("Attributes used for TT creation: ");
            log("---------------------------------");
            //((TroubleTicketValueImpl)ttValue).print();

            returned_key = (TroubleTicketKeyImpl) jvtSession.createTroubleTicketByValue(ttValue);


        }

                // The following exceptions are be used to build the respective xml exception.
                // The xml exception will be embedded as the message in the respective java exception.
        catch (javax.oss.IllegalArgumentException illEx) {
            String xmlException = makeXmlException("createTroubleTicketByValueException", "IllegalArgumentException",
                    illEx.getMessage());
            log("XVTTroubleTicketSession.createTT: IllegalArgumentException caught on TTSession.createManagedEntity");
            illEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);

        } catch (javax.oss.UnsupportedOperationException unSuppEx) {
            String xmlException = makeXmlException("createTroubleTicketByValueException", "UnsupportedOperationException",
                    unSuppEx.getMessage());
            log("XVTTroubleTicketSession.createTT: UnsupportedOperationException caught on TTSession.createManagedEntity");
            unSuppEx.printStackTrace();

            //>
            //throw new javax.oss.UnsupportedOperationException( xmlException );
            throw new javax.ejb.EJBException(xmlException);
        } catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("createTroubleTicketByValueException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.createTT: RemoteException caught on TTSession.createManagedEntity");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (javax.ejb.DuplicateKeyException dupKeyEx) {
            String xmlException = makeXmlException("createTroubleTicketByValueException", "DuplicateKeyException",
                    dupKeyEx.getMessage());
            log("XVTTroubleTicketSession.createTT: DuplicateKeyException caught on TTSession.createManagedEntity");
            dupKeyEx.printStackTrace();

            throw new javax.ejb.DuplicateKeyException(xmlException);

        } catch (javax.ejb.CreateException createEx) {
            String xmlException = makeXmlException("createTroubleTicketByValueException", "CreateException",
                    createEx.getMessage());
            log("XVTTroubleTicketSession.createTT: javax.ejb.CreateException caught on TTSession.createManagedEntity");
            createEx.printStackTrace();

            throw new javax.ejb.CreateException(xmlException);
        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("createTroubleTicketByValueException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession.createTT: IOException caught on TTSession.createManagedEntity");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("createTroubleTicketByValueException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession.createTT: SAXException caught on TTSession.createManagedEntity");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("createTroubleTicketByValueException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession.createTT: SAXException caught on TTSession.createManagedEntity");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }

        String xmlKey = null;
        try {
            xmlKey = ((TroubleTicketKeyImpl) returned_key).toXml();
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("createTroubleTicketByValueException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession.createTT: SAXException caught on TTSession.createManagedEntity");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }


        // Now add the respective xml envelop

        return this.addXmlRootElement("createTroubleTicketByValueResponse", xmlKey);

    }

    /*
    //Creation of Multiple Trouble Tickets each with a different value
    //Input Parameter is equal to  createTroubleTicketByValuesRequest as defined in XML Schema
    //Output Parameter is equal to createTroubleTicketByValuesResponse which is itself defined
    //in the XML Schema
    public String createTroubleTicketsByValues(String createTroubleTicketByValuesRequest)
    throws javax.oss.UnsupportedOperationException,
           javax.ejb.DuplicateKeyException,
           javax.ejb.CreateException,
           javax.ejb.EJBException
    {
        return new String();
    }
	*/

    /**
     //Creation of Multiple Trouble Tickets each with the same value
     //Input Parameter is equal to  createTroubleTicketsByKeysRequest as defined in XML Schema
     //Output Parameter is equal to createTroubleTicketsByKeysResponse which is itself defined
     //in the XML Schema
     public String createTroubleTicketsByKeys(String createTroubleTicketsByKeysRequest )
     throws javax.oss.UnsupportedOperationException, javax.ejb.DuplicateKeyException,
     javax.ejb.CreateException, java.rmi.RemoteException
     {
     return new String();
     }
     **/





    //------------------------------------------------------------------------
    // Named Query Methods
    //------------------------------------------------------------------------
    //Query Multiple Trouble Tickets using a Query XML Document
    //Input Parameter is equal to  queryTroubleTicketsRequest as defined in XML Schema
    //Output Parameter is an XmlResponseIterator wich contains queryTroubleTicketsResponse
    //which is itself defined in the XML Schema
    //queryTroubleTickets TBD

    /*
    public XmlResponseIterator queryTroubleTickets( String queryTroubleTicketsRequest )
    throws javax.oss.UnsupportedOperationException, javax.ejb.EJBException

    {
        return null; //ew String();
    }
    */



    //------------------------------------------------------------------------
    // Close, Cancel, Escalate (Atomic)
    //------------------------------------------------------------------------
    //Close a Trouble Ticket matching a given key
    //Input Parameter is equal to  closeTroubleTicketByKeyRequest as defined in XML Schema
    //Output Parameter is equal to closeTroubleTicketByKeyResponse which is itself defined
    //in the XML Schema
    //---Implemented for the Demo---
    public String closeTroubleTicketByKey(String closeTroubleTicketByKeyRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.ObjectNotFoundException,
            javax.ejb.EJBException {


        //TroubleTicketValue ttValue = jvtSession.makeTroubleTicketValue("");
        String xmlCloseResponse = null;
        JVTTroubleTicketSession jvtSession = null;
        try {

            // Since Validation is turned off, parsing is performed without validation.
            Document doc = parseXmlString(closeTroubleTicketByKeyRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);

            if (!rootName.equals("tt:closeTroubleTicketByKeyRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }

            // Get the ttKey from the passed in xml doc
            //TroubleTicketKey trKey = new TroubleTicketKeyImpl();
            //((TroubleTicketKeyImpl)trKey).fromXml( (Element) doc );
            TroubleTicketKey trKey = SerializeKey(doc, "troubleTicketKey");

            int status = -1;
            String closeNarr = null;
            NodeList nodeList = null;
            Node node = null;
            nodeList = rootElement.getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                node = nodeList.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) continue;
                String nodeName = node.getNodeName();
                //System.out.println( "---->NODE NAME = " + nodeName );
                if (nodeName.equals("tt:status")) {
                    if (node.hasChildNodes()) {
                        status = XmlTranslator.EnumeratedTypeFromXml(node, TroubleStatus.class);
                    }
                } else if (nodeName.equals("tt:closeOutNarr")) {
                    if (node.hasChildNodes()) {
                        closeNarr = node.getFirstChild().getNodeValue();
                    }
                }

            }


            log("XVT Calling TroubleTicketSession.closeTroubleTicket");
            jvtSession = this.getJVTRemoteInterface();



            //TEMP KLUDGE! closeout status must be provided in XML schema
            jvtSession.closeTroubleTicketByKey(trKey, status, closeNarr);


            // Add the respective XML envelope
            xmlCloseResponse = this.addXmlRootElement("closeTroubleTicketByKeyResponse", null);

            //System.out.println( xmlSetResponse );
        }
                // All of the following exceptions need to contain their respective
                // xml exceptions.
        catch (javax.oss.IllegalArgumentException illEx) {
            String xmlException = makeXmlException("closeTroubleTicketException", "IllegalArgumentException",
                    illEx.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: IllegalArgumentException caught on TTSession.closeTroubleTicket");
            illEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);

        }
                /**
                 catch( javax.oss.UnsupportedOperationException unSuppEx )
                 {
                 String xmlException = makeXmlException( "CloseTroubleTicketException", "UnsupportedOperationException",
                 unSuppEx.getMessage());
                 log ("XVTTroubleTicketSession.closeTTByKey: UnsupportedOperationException caught on TTSession.closeTroubleTicket");
                 unSuppEx.printStackTrace();

                 throw new javax.oss.UnsupportedOperationException( xmlException );
                 }
                 **/ catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("closeTroubleTicketException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: RemoteException caught on TTSession.closeTroubleTicket");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (javax.ejb.ObjectNotFoundException objNFex) {
            String xmlException = makeXmlException("closeTroubleTicketException", "ObjectNotFoundException",
                    objNFex.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: ObjectNotFoundException caught on TTSession.closeTroubleTicket");
            objNFex.printStackTrace();

            throw new javax.ejb.ObjectNotFoundException(xmlException);

        } catch (javax.ejb.RemoveException rmvEx) {
            String xmlException = makeXmlException("closeTroubleTicketException", "ObjectNotFoundException",
                    rmvEx.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: ObjectNotFoundException caught on TTSession.closeTroubleTicket");
            rmvEx.printStackTrace();

            throw new javax.ejb.ObjectNotFoundException(xmlException);

        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("closeTroubleTicketException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: IOException caught on TTSession.closeTroubleTicket");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("closeTroubleTicketException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: SAXException caught on TTSession.closeTroubleTicket");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("closeTroubleTicketException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: SAXException caught on TTSession.closeTroubleTicket");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }
        return xmlCloseResponse;

    }

    //Cancel a Trouble Ticket matching a given key
    //Input Parameter is equal to  CancelTroubleTicketByKeyRequest as defined in XML Schema
    //Output Parameter is equal to CancelTroubleTicketByKeyResponse which is itself defined
    //in the XML Schema
    //---Implemented for the Demo---
    public String cancelTroubleTicketByKey(String cancelTroubleTicketByKeyRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.ObjectNotFoundException,
            javax.ejb.EJBException {
        //TroubleTicketValue ttValue = jvtSession.makeTroubleTicketValue("");
        String xmlCancelResponse = null;
        JVTTroubleTicketSession jvtSession = null;
        try {

            // Since Validation is turned off, parsing is performed without validation.
            Document doc = parseXmlString(cancelTroubleTicketByKeyRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);

            if (!rootName.equals("tt:cancelTroubleTicketByKeyRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }

            // Get the ttKey from the passed in xml doc
            //TroubleTicketKey trKey = new TroubleTicketKeyImpl();
            //((TroubleTicketKeyImpl)trKey).fromXml( (Element) doc );
            TroubleTicketKey trKey = SerializeKey(doc, "troubleTicketKey");


            NodeList nodeList = null;
            Node node = null;
            nodeList = rootElement.getChildNodes();

            int status = -1;
            String closeNarr = null;

            for (int i = 0; i < nodeList.getLength(); i++) {
                node = nodeList.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) continue;
                String nodeName = node.getNodeName();
                //System.out.println( "---->NODE NAME = " + nodeName );
                if (nodeName.equals("tt:status")) {
                    if (node.hasChildNodes()) {
                        status = XmlTranslator.EnumeratedTypeFromXml(node, TroubleStatus.class);
                    }
                } else if (nodeName.equals("tt:closeOutNarr")) {
                    if (node.hasChildNodes()) {
                        closeNarr = node.getFirstChild().getNodeValue();
                    }
                }

            }


            log("XVT Calling TroubleTicketSession.cancelTroubleTicketByKey");
            jvtSession = this.getJVTRemoteInterface();


            jvtSession.cancelTroubleTicketByKey(trKey, status, closeNarr);


            // Add the respective XML envelope
            xmlCancelResponse = this.addXmlRootElement("cancelTroubleTicketByKeyResponse", null);

            //System.out.println( xmlSetResponse );
        }
                // All of the following exceptions need to contain their respective
                // xml exceptions.
        catch (javax.oss.IllegalArgumentException illEx) {
            String xmlException = makeXmlException("cancelTroubleTicketByKeyException", "IllegalArgumentException",
                    illEx.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: IllegalArgumentException caught on TTSession.closeTroubleTicket");
            illEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);

        }
                /**
                 catch( javax.oss.UnsupportedOperationException unSuppEx )
                 {
                 String xmlException = makeXmlException( "CloseTroubleTicketException", "UnsupportedOperationException",
                 unSuppEx.getMessage());
                 log ("XVTTroubleTicketSession.closeTTByKey: UnsupportedOperationException caught on TTSession.closeTroubleTicket");
                 unSuppEx.printStackTrace();

                 throw new javax.oss.UnsupportedOperationException( xmlException );
                 }
                 **/ catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("cancelTroubleTicketByKeyException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: RemoteException caught on TTSession.closeTroubleTicket");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (javax.ejb.ObjectNotFoundException objNFex) {
            String xmlException = makeXmlException("cancelTroubleTicketByKeyException", "ObjectNotFoundException",
                    objNFex.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: ObjectNotFoundException caught on TTSession.closeTroubleTicket");
            objNFex.printStackTrace();

            throw new javax.ejb.ObjectNotFoundException(xmlException);

        } catch (javax.ejb.RemoveException rmvEx) {
            String xmlException = makeXmlException("cancelTroubleTicketByKeyException", "ObjectNotFoundException",
                    rmvEx.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: ObjectNotFoundException caught on TTSession.closeTroubleTicket");
            rmvEx.printStackTrace();

            throw new javax.ejb.ObjectNotFoundException(xmlException);

        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("cancelTroubleTicketByKeyException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: IOException caught on TTSession.closeTroubleTicket");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("cancelTroubleTicketByKeyException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: SAXException caught on TTSession.closeTroubleTicket");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("cancelTroubleTicketByKeyException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: SAXException caught on TTSession.closeTroubleTicket");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }
        return xmlCancelResponse;
    }

    //Escalate a Trouble Ticket matching a given key
    //Input Parameter is equal to  EscalateTroubleTicketByKeyRequest as defined in XML Schema
    //Output Parameter is equal to EscalateTroubleTicketByKeyResponse which is itself defined
    //in the XML Schema
    //---Implemented for the Demo---
    public String escalateTroubleTicketByKey(String escalateTroubleTicketByKeyRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.ObjectNotFoundException,
            javax.ejb.EJBException {
        log("XVT:escalateTroubleTicketByKey");
        String xmlEscalateResponse = null;
        JVTTroubleTicketSession jvtSession = null;
        try {


            // Since Validation is turned off, parsing is performed without validation.
            Document doc = parseXmlString(escalateTroubleTicketByKeyRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);

            if (!rootName.equals("tt:escalateTroubleTicketByKeyRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }

            // Get the ttKey from the passed in xml doc
            TroubleTicketKey trKey = SerializeKey(doc, "troubleTicketKey");



            //get escalation node

            Node node = null;
            NodeList nodeList = null;

            nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "escalationList");

            if (nodeList != null) {
                node = nodeList.item(0);
                if (node == null) {
                    String nullNode = new String("null Node in XmlTranslator.fromXml (multiple values)");
                    log(nullNode);
                    throw new javax.oss.IllegalArgumentException(nullNode);

                }

            }

            TroubleTicketValue ttv = new TroubleTicketValueImpl();
            XmlTranslator.EscalationListFromXml(node, ttv);
            //Get Escalation List from ttv

            EscalationList escs = ttv.getEscalationList();

            StringBuffer sb = new StringBuffer();

            XmlTranslator.EscalationList2Xml(sb, ttv);
            jvtSession = this.getJVTRemoteInterface();


            jvtSession.escalateTroubleTicketByKey(trKey, escs);


            // Add the respective XML envelope
            xmlEscalateResponse = this.addXmlRootElement("escalateTroubleTicketByKeyResponse", null);

            //System.out.println( xmlSetResponse );
        }
                // All of the following exceptions need to contain their respective
                // xml exceptions.
        catch (javax.oss.IllegalArgumentException illEx) {
            String xmlException = makeXmlException("escalateTroubleTicketByKeyException", "IllegalArgumentException",
                    illEx.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: IllegalArgumentException caught on TTSession.closeTroubleTicket");
            illEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);

        } catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("escalateTroubleTicketByKeyException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: RemoteException caught on TTSession.closeTroubleTicket");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (javax.ejb.ObjectNotFoundException objNFex) {
            String xmlException = makeXmlException("escalateTroubleTicketByKeyException", "ObjectNotFoundException",
                    objNFex.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: ObjectNotFoundException caught on TTSession.closeTroubleTicket");
            objNFex.printStackTrace();

            throw new javax.ejb.ObjectNotFoundException(xmlException);

        } catch (javax.ejb.RemoveException rmvEx) {
            String xmlException = makeXmlException("escalateTroubleTicketByKeyException", "ObjectNotFoundException",
                    rmvEx.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: ObjectNotFoundException caught on TTSession.closeTroubleTicket");
            rmvEx.printStackTrace();

            throw new javax.ejb.ObjectNotFoundException(xmlException);

        }
                //
        catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("escalateTroubleTicketByKeyException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: IOException caught on TTSession.closeTroubleTicket");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("escalateTroubleTicketByKeyException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: SAXException caught on TTSession.closeTroubleTicket");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("escalateTroubleTicketByKeyException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession.closeTTByKey: SAXException caught on TTSession.closeTroubleTicket");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }
        return xmlEscalateResponse;
    }


    //Query Multiple Trouble Tickets using a Query XML Document
    //Input Parameter is equal to  queryTroubleTicketsRequest as defined in XML Schema
    //Output Parameter is an XmlResponseIterator wich contains queryTroubleTicketsResponse
    //which is itself defined in the XML Schema
    //queryTroubleTickets TBD

    public XmlResponseIterator queryTroubleTickets(String QueryTroubleTicketsRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.EJBException {
        log("---XVT:queryTroubleTickets" + QueryTroubleTicketsRequest);

        //TroubleTicketKeyResultIteratorIF resultKeyIter = null;
        //VP remove IF
        TroubleTicketValueIterator valueIter = null;
        XmlResponseIteratorHome xmlResIterator = null;
        XmlResponseIteratorIF xmlIter = null;
        JVTTroubleTicketSession jvtSession = null;

        try {


            Document doc = parseXmlString(QueryTroubleTicketsRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);

            if (!rootName.equals("tt:queryTroubleTicketsRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }



            // Now let us deal with the desired attributes list issue
            // Extract the attNames from the xml req
            String[] attrNames = this.getXmlAttNames(doc);

            log(" calling ttSession.queryTroubleTickets");

            jvtSession = this.getJVTRemoteInterface();

            //Check the query type and manufacture the proper Query instance
            Element element = getElement(doc, "query");
            String queryType = element.getAttribute("xsi:type");

            log("----QUERY TYPE = " + queryType);
            //Serialize the XML Query into its QueryValue
            //Call the JVT Session Bean

            String queryName = new String(QueryAllOpenTroubleTicketsValue.class.getName());
            QueryValue queryVal = jvtSession.makeQueryValue(queryName);
            //VP remove IF in cast
            valueIter = (TroubleTicketValueIterator) jvtSession.queryTroubleTickets(queryVal, attrNames);


            try {
                xmlResIterator = (XmlResponseIteratorHome) namingContext.lookup
                        ("java:comp/env/ejb/XmlResponseIteratorHome");
            } catch (NamingException nex2) {
                log("XVTSessionBean.queryTroubleTickets: NamingException caught on lookup of XmlResponseIteratorHome");
                nex2.printStackTrace();
                //MR System.exit(1);
            }
            try {
                // Now let us create the xml resp iterator
                xmlIter = xmlResIterator.create(valueIter, "queryTroubleTicketsResponse", attrNames);

            } catch (javax.ejb.CreateException createEx) {
                log("TTClient.createTT: CreateException ");
                createEx.printStackTrace();
            }

        }   // End of try
        catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("queryTroubleTicketsException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.createTT: RemoteException caught on JVTSession.queryTroubleTickets");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (javax.ejb.CreateException createEx) {
            String xmlException = makeXmlException("queryTroubleTicketsException", "CreateException",
                    createEx.getMessage());
            log("XVTTroubleTicketSession: javax.ejb.CreateException caught on XVTSession.queryTroubleTickets");
            createEx.printStackTrace();
            //> rethrow the remote exception at the current phase
            //> to be replaced by the close exception as defined in the
            //> new xml schema.
            throw new javax.ejb.EJBException(xmlException);
        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("queryTroubleTicketsException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession: IOException caught on XVTSession.queryTroubleTickets");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("queryTroubleTicketsException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession: SAXException caught on XVTSession.queryTroubleTickets");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("queryTroubleTicketsException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession: SAXException caught on XVTSession.queryTroubleTickets");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }

        //VP
        XmlResponseIterator retXmlIter = new XmlResponseIteratorImpl(xmlIter);
        return retXmlIter;
    }

    //------------------------------------------------------------------------
    //
    // Best Effort Methods
    //
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // SET (best effort)
    //------------------------------------------------------------------------

    //Setting Multiple Trouble Tickets each with different values
    //Input Parameter is equal to  TrySetTroubleTicketsByValuesRequest as defined in XML Schema
    //Output Parameter is equal to TrySetTroubleTicketsByValuesResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    public String trySetTroubleTicketsByValues(String TrySetTroubleTicketsByValuesRequest)
            throws javax.oss.IllegalArgumentException,
            SetException, javax.ejb.DuplicateKeyException,
            javax.ejb.FinderException, javax.ejb.EJBException {
        log("XVT:trySetTroubleTicketsByValues");

        TroubleTicketKeyResult[] keyResults = null;

        try {

            // Get the xml document from the passed in xml string
            Document doc = parseXmlString(TrySetTroubleTicketsByValuesRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);
            if (!rootName.equals("tt:trySetTroubleTicketsByValuesRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }
            //WIPRO

            NodeList returnModeNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "resyncRequired");

            // Typically only one element exist in the return mode list
            String xmlResyncRequired = (returnModeNodeList.item(0)).getFirstChild().getNodeValue();
            boolean resync_required = XmlTranslator.BooleanFromXml(returnModeNodeList.item(0));

            //WIPRO

            jvtSession = this.getJVTRemoteInterface();
            jvtSession = this.getJVTRemoteInterface();	//lookup/create the JVT session bean

            //translate the ttValues from Xml to Java value types.
            //TroubleTicketValue[] ttValues = XmlTranslator.fromXml(doc);


            Node node = null;
            NodeList nodeList = null;

            nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "troubleTicketValues");
            if (nodeList != null) {
                node = nodeList.item(0);
                if (node == null) {
                    String nullNode = new String("null Node in XmlTranslator.fromXml (multiple values)");
                    log(nullNode);
                    throw new javax.oss.IllegalArgumentException(nullNode);

                }
            }

            TroubleTicketValue[] ttValues = XmlTranslator.TTValuesFromXmlItems(node);

            /*
            log ("---------------------------------");
            log ("Attributes used for TT creation: ");
            log ("---------------------------------");
            ((TroubleTicketValueImpl)ttValue).print();
            */

            //------------------------------------------------------
            // Call the corresponding JVTSession bean method
            //------------------------------------------------------

            //PG BUG Resync flag always set to false....


            //keyResults = jvtSession.trySetTroubleTicketsByValues( ttValues, false );  //WIPRO
            keyResults = jvtSession.trySetTroubleTicketsByValues(ttValues, resync_required);  //WIPRO



            /*
            log ("  KeyType:    " + returned_key.getType());
            log ("  KeyDomain:    " + returned_key.getDomain());
            log ("  KeyPKey:    " + returned_key.getPrimaryKey());
            */
        }

                // The following exceptions are be used to build the respective xml exception.
                // The xml exception will be embedded as the message in the respective java exception.
        catch (javax.oss.IllegalArgumentException illEx) {
            String xmlException = makeXmlException("tryCreateTroubleTicketsByValuesException", "IllegalArgumentException",
                    illEx.getMessage());
            log("XVTTroubleTicketSession.createTT: IllegalArgumentException caught on TTSession.createManagedEntity");
            illEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);

        } catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("tryCreateTroubleTicketsByValuesException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.createTT: RemoteException caught on TTSession.createManagedEntity");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("tryCreateTroubleTicketsByValuesException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession.createTT: IOException caught on TTSession.createManagedEntity");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("tryCreateTroubleTicketsByValuesException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession.createTT: SAXException caught on TTSession.createManagedEntity");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("tryCreateTroubleTicketsByValuesException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession.createTT: SAXException caught on TTSession.createManagedEntity");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }


        //translate the results back to XML
        String xmlKeyResults = null;

        try {
            xmlKeyResults = XmlTranslator.toXml(keyResults);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("tryCreateTroubleTicketsByValuesException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession.createTTsByValues: SAXException caught on TTSession.createManagedEntity");
            saxEx.printStackTrace();
            throw new javax.oss.IllegalArgumentException(xmlException);
        }

        //add the respective xml envelop
        return addXmlRootElement("trySetTroubleTicketsByValuesResponse", xmlKeyResults);


    }


    //Setting Multiple Trouble Tickets given their ID with the same value
    //Input Parameter is equal to  TrySetTroubleTicketsByKeysRequest as defined in XML Schema
    //Output Parameter is equal to TrySetTroubleTicketsByKeysResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    public String trySetTroubleTicketsByKeys(String trySetTroubleTicketsByKeysRequest)
            throws javax.oss.IllegalArgumentException, SetException,
            javax.ejb.FinderException, javax.ejb.EJBException {

        TroubleTicketKeyResult[] keyResults = null;

        try {
            // Get the xml document from the passed in xml string
            Document doc = parseXmlString(trySetTroubleTicketsByKeysRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);
            if (!rootName.equals("tt:trySetTroubleTicketsByKeysRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }

            jvtSession = this.getJVTRemoteInterface();	//lookup/create the JVT session bean

            //translate the ttValues from Xml to Java value types.
            //TroubleTicketValue[] ttValues = XmlTranslator.fromXml(doc);


            Node node = null;
            NodeList nodeList = null;

            nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "troubleTicketKeys");
            if (nodeList != null) {
                node = nodeList.item(0);
                if (node == null) {
                    String nullNode = new String("null Node in XmlTranslator.fromXml (multiple values)");
                    log(nullNode);
                    throw new javax.oss.IllegalArgumentException(nullNode);

                }
            }

            //New Method. Similar to TTValuesfromXMLItems...
            TroubleTicketKey[] ttKeys = XmlTranslator.TTKeysFromXmlItems(node);


            TroubleTicketValue ttVal = SerializeValue(doc, "troubleTicketValue");


            //------------------------------------------------------
            // Call the corresponding JVTSession bean method
            //------------------------------------------------------



            keyResults = jvtSession.trySetTroubleTicketsByKeys(ttKeys, ttVal);
            /*
            log ("  KeyType:    " + returned_key.getType());
            log ("  KeyDomain:    " + returned_key.getDomain());
            log ("  KeyPKey:    " + returned_key.getPrimaryKey());
            */
        }

                // The following exceptions are be used to build the respective xml exception.
                // The xml exception will be embedded as the message in the respective java exception.
        catch (javax.oss.IllegalArgumentException illEx) {
            String xmlException = makeXmlException("tryCreateTroubleTicketsByValuesException", "IllegalArgumentException",
                    illEx.getMessage());
            log("XVTTroubleTicketSession.createTT: IllegalArgumentException caught on TTSession.createManagedEntity");
            illEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);

        } catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("tryCreateTroubleTicketsByValuesException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.createTT: RemoteException caught on TTSession.createManagedEntity");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        }

                /*  catch( oracle.xml.parser.v2.XMLParseException parsEx )
                  {
                      String xmlException = makeXmlException( "tryCreateTroubleTicketsByValuesException", "IllegalArgumentException",
                                                              parsEx.getMessage()  );
                      log ("XVTTroubleTicketSession.createTT: XMLParseException caught on TTSession.createManagedEntity");
                      parsEx.printStackTrace();

                      throw new javax.oss.IllegalArgumentException( xmlException );

                  }
                  */ catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("tryCreateTroubleTicketsByValuesException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession.createTT: IOException caught on TTSession.createManagedEntity");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("tryCreateTroubleTicketsByValuesException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession.createTT: SAXException caught on TTSession.createManagedEntity");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }


        //translate the results back to XML
        String xmlKeyResults = null;

        try {
            xmlKeyResults = XmlTranslator.toXml(keyResults);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("trySetTroubleTicketsByKeysException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession.createTTsByValues: SAXException caught on TTSession.createManagedEntity");
            saxEx.printStackTrace();
            throw new javax.oss.IllegalArgumentException(xmlException);
        }

        //add the respective xml envelop
        return addXmlRootElement("trySetTroubleTicketsByKeysResponse", xmlKeyResults);
    }


    //Setting Multiple Trouble Tickets matching  the template with the same value
    //Input Parameter is equal to  TrySetTroubleTicketsByTemplateRequest as defined in XML Schema
    //Output Parameter is equal to TrySetTroubleTicketsByTemplateResponse as defined in XML Schema
    public XmlResponseIterator trySetTroubleTicketsByTemplate(String trySetTroubleTicketsByTemplateRequest)
            throws javax.oss.IllegalArgumentException, SetException,
            javax.ejb.ObjectNotFoundException, javax.ejb.EJBException {

        //VP supress the trailing IF in the cast due to the interface return now is the
        // TroubleTicketKeyResultIterator
        TroubleTicketKeyResultIterator resultKeyIter = null;
        XmlResponseIteratorHome xmlResIterator = null;
        XmlResponseIteratorIF xmlIter = null;
        JVTTroubleTicketSession jvtSession = null;

        try {

            // Since Validation is turned off, parsing is performed without validation.
            //> The verification code is to be moved into the ttValue.fromXml() method
            // to aviod doubling the parsing effort.
            Document doc = parseXmlString(trySetTroubleTicketsByTemplateRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);

            if (!rootName.equals("tt:trySetTroubleTicketsByTemplateRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }

            TroubleTicketValue ttTemplate = SerializeValue(doc, "template");
            TroubleTicketValue ttValue = SerializeValue(doc, "troubleTicketValue");

            //WIPRO

            NodeList returnModeNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "failuresOnly");

            // Typically only one element exist in the return mode list
            String xmlFailuresOnly = (returnModeNodeList.item(0)).getFirstChild().getNodeValue();
            boolean failures_only = XmlTranslator.BooleanFromXml(returnModeNodeList.item(0));

            //WIPRO

            jvtSession = this.getJVTRemoteInterface();

            //VP supress the trailing IF in the cast due to the interface return now is the
            // TroubleTicketKeyResultIterator
            resultKeyIter = (TroubleTicketKeyResultIterator) jvtSession.trySetTroubleTicketsByTemplate(ttTemplate, ttValue, failures_only);
            //resultKeyIter = (TroubleTicketKeyResultIterator)jvtSession.trySetTroubleTicketsByTemplate( ttTemplate, ttValue ,false);



            try {
                xmlResIterator = (XmlResponseIteratorHome) namingContext.lookup
                        ("java:comp/env/ejb/XmlResponseIteratorHome");
            } catch (NamingException nex2) {
                log("XVTSessionBean.queryTroubleTickets: NamingException caught on lookup of XmlResponseIteratorHome");
                nex2.printStackTrace();
                //MR System.exit(1);
            }
            try {
                // Now let us create the xml resp iterator
                log("About to call xmlResIterator.create....");
                xmlIter = xmlResIterator.create(resultKeyIter, "trySetTroubleTicketsByTemplateResponse", new String[0]);
                log("Returning from call xmlResIterator.create....");
            } catch (javax.ejb.CreateException createEx) {
                log("TTClient.createTT: CreateException ");
                createEx.printStackTrace();
            }

        }   // End of try
        catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("trySetTroubleTicketsByTemplateException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.createTT: RemoteException caught on JVTSession.queryTroubleTickets");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (javax.ejb.CreateException createEx) {
            String xmlException = makeXmlException("trySetTroubleTicketsByTemplateException", "CreateException",
                    createEx.getMessage());
            log("XVTTroubleTicketSession: javax.ejb.CreateException caught on XVTSession.queryTroubleTickets");
            createEx.printStackTrace();
            //> rethrow the remote exception at the current phase
            //> to be replaced by the close exception as defined in the
            //> new xml schema.
            //throw new javax.ejb.CreateException( xmlException );
            throw new javax.ejb.EJBException(xmlException);
        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("trySetTroubleTicketsByTemplateException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession: IOException caught on XVTSession.queryTroubleTickets");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("trySetTroubleTicketsByTemplateException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession: SAXException caught on XVTSession.queryTroubleTickets");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("trySetTroubleTicketsByTemplateException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession: SAXException caught on XVTSession.queryTroubleTickets");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }
        log("returning XML response Iterator....");
        //VP
        XmlResponseIterator retXmlIter = new XmlResponseIteratorImpl(xmlIter);
        return retXmlIter;

    }


    //Setting Multiple Trouble Tickets matching at least one of the template with the same value
    //Input Parameter is equal to  TrySetTroubleTicketsByTemplatesRequest as defined in XML Schema
    //Output Parameter is equal to TrySetTroubleTicketsByTemplatesResponse as defined in XML Schema
    public XmlResponseIterator trySetTroubleTicketsByTemplates(String trySetTroubleTicketsByTemplatesRequest)
            throws javax.oss.IllegalArgumentException, SetException,
            javax.ejb.FinderException, javax.ejb.DuplicateKeyException,
            javax.ejb.EJBException {
        //VP supress the trailing IF in the cast due to the interface return now is the
        // TroubleTicketKeyResultIterator
        TroubleTicketKeyResultIterator resultKeyIter = null;
        XmlResponseIteratorHome xmlResIterator = null;
        XmlResponseIteratorIF xmlIter = null;
        JVTTroubleTicketSession jvtSession = null;

        try {

            // Since Validation is turned off, parsing is performed without validation.
            //> The verification code is to be moved into the ttValue.fromXml() method
            // to aviod doubling the parsing effort.
            Document doc = parseXmlString(trySetTroubleTicketsByTemplatesRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);

            if (!rootName.equals("tt:trySetTroubleTicketsByTemplatesRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }

            //TroubleTicketValue ttValue    = new TroubleTicketValueImpl();
            //get template element
            TroubleTicketValue[] ttTemplates = XmlTranslator.getTroubleTicketValues(doc, "templates");
            //XmlTranslator.fromXmlTag( (Element) doc, ttValue, "value" );
            TroubleTicketValue ttValue = SerializeValue(doc, "troubleTicketValue");

            //WIPRO

            NodeList returnModeNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "failuresOnly");

            // Typically only one element exist in the return mode list
            String xmlFailuresOnly = (returnModeNodeList.item(0)).getFirstChild().getNodeValue();
            boolean failures_only = XmlTranslator.BooleanFromXml(returnModeNodeList.item(0));

            //WIPRO

            jvtSession = this.getJVTRemoteInterface();


            //VP supress the trailing IF in the cast due to the interface return now is the
            // TroubleTicketKeyResultIterator

            //WIPRO
            //resultKeyIter = (TroubleTicketKeyResultIterator)jvtSession.trySetTroubleTicketsByTemplates( ttTemplates, ttValue ,false);
            resultKeyIter = (TroubleTicketKeyResultIterator) jvtSession.trySetTroubleTicketsByTemplates(ttTemplates, ttValue, failures_only);
            //WIPRO


            //resultKeyIter = (TroubleTicketKeyResultIteratorIF)
            //                jvtSession.tryCloseTroubleTickets(template,ReturnMode.RETURNALL);
            // resultKeyIter = (TroubleTicketKeyResultIteratorIF)
            //                 jvtSession.tryCloseTroubleTickets(template,returnModeValue.intValue());

            try {
                xmlResIterator = (XmlResponseIteratorHome) namingContext.lookup
                        ("java:comp/env/ejb/XmlResponseIteratorHome");
            } catch (NamingException nex2) {
                log("XVTSessionBean.queryTroubleTickets: NamingException caught on lookup of XmlResponseIteratorHome");
                nex2.printStackTrace();
                //MR System.exit(1);
            }
            try {
                // Now let us create the xml resp iterator
                log("About to call xmlResIterator.create....");
                xmlIter = xmlResIterator.create(resultKeyIter, "trySetTroubleTicketsByTemplatesResponse", new String[0]);
                log("Returning from call xmlResIterator.create....");
            } catch (javax.ejb.CreateException createEx) {
                log("TTClient.createTT: CreateException ");
                createEx.printStackTrace();
            }

        }   // End of try
        catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("trySetTroubleTicketsByTemplatesException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.createTT: RemoteException caught on JVTSession.queryTroubleTickets");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (javax.ejb.CreateException createEx) {
            String xmlException = makeXmlException("trySetTroubleTicketsByTemplatesException", "CreateException",
                    createEx.getMessage());
            log("XVTTroubleTicketSession: javax.ejb.CreateException caught on XVTSession.queryTroubleTickets");
            createEx.printStackTrace();
            //> rethrow the remote exception at the current phase
            //> to be replaced by the close exception as defined in the
            //> new xml schema.
            //throw new javax.ejb.CreateException( xmlException );
            throw new javax.ejb.EJBException(xmlException);
        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("trySetTroubleTicketsByTemplatesException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession: IOException caught on XVTSession.queryTroubleTickets");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("trySetTroubleTicketsByTemplatesException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession: SAXException caught on XVTSession.queryTroubleTickets");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("trySetTroubleTicketsByTemplatesException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession: SAXException caught on XVTSession.queryTroubleTickets");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }
        log("returning XML response Iterator....");
        //VP
        XmlResponseIterator retXmlIter = new XmlResponseIteratorImpl(xmlIter);
        return retXmlIter;
    }



    //------------------------------------------------------------------------
    // CREATE (best effort)
    //------------------------------------------------------------------------

    //Creation of Multiple Trouble Tickets each with a different value
    //Input Parameter is TryCreateTroubleTicketByValuesRequest as defined in XML Schema
    //Output Parameter is TryCreateTroubleTicketByValuesResponse as defined in XML Schema
    public String tryCreateTroubleTicketsByValues(String TryCreateTroubleTicketsByValuesRequest)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.DuplicateKeyException,
            javax.ejb.CreateException,
            javax.ejb.EJBException {


        TroubleTicketKeyResult[] keyResults = null;

        try {

            // Get the xml document from the passed in xml string
            Document doc = parseXmlString(TryCreateTroubleTicketsByValuesRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);
            if (!rootName.equals("tt:tryCreateTroubleTicketsByValuesRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }

            jvtSession = this.getJVTRemoteInterface();	//lookup/create the JVT session bean

            //translate the ttValues from Xml to Java value types.
            //TroubleTicketValue[] ttValues = XmlTranslator.fromXml(doc);


            Node node = null;
            NodeList nodeList = null;

            nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "troubleTicketValues");
            if (nodeList != null) {
                node = nodeList.item(0);
                if (node == null) {
                    String nullNode = new String("null Node in XmlTranslator.fromXml (multiple values)");
                    log(nullNode);
                    throw new javax.oss.IllegalArgumentException(nullNode);

                }
            }

            TroubleTicketValue[] ttValues = XmlTranslator.TTValuesFromXmlItems(node);

            /*
            log ("---------------------------------");
            log ("Attributes used for TT creation: ");
            log ("---------------------------------");
            ((TroubleTicketValueImpl)ttValue).print();
            */

            //------------------------------------------------------
            // Call the corresponding JVTSession bean method
            //------------------------------------------------------
            keyResults = jvtSession.tryCreateTroubleTicketsByValues(ttValues);
            /*
            log ("  KeyType:    " + returned_key.getType());
            log ("  KeyDomain:    " + returned_key.getDomain());
            log ("  KeyPKey:    " + returned_key.getPrimaryKey());
            */
        }

                // The following exceptions are be used to build the respective xml exception.
                // The xml exception will be embedded as the message in the respective java exception.
        catch (javax.oss.IllegalArgumentException illEx) {
            String xmlException = makeXmlException("tryCreateTroubleTicketsByValuesException", "IllegalArgumentException",
                    illEx.getMessage());
            log("XVTTroubleTicketSession.createTT: IllegalArgumentException caught on TTSession.createManagedEntity");
            illEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);

        } catch (javax.oss.UnsupportedOperationException unSuppEx) {
            String xmlException = makeXmlException("tryCreateTroubleTicketsByValuesException", "UnsupportedOperationException",
                    unSuppEx.getMessage());
            log("XVTTroubleTicketSession.createTT: UnsupportedOperationException caught on TTSession.createManagedEntity");
            unSuppEx.printStackTrace();

            //>
            //throw new javax.oss.UnsupportedOperationException( xmlException );
            throw new javax.ejb.EJBException(xmlException);
        } catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("tryCreateTroubleTicketsByValuesException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.createTT: RemoteException caught on TTSession.createManagedEntity");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (javax.ejb.DuplicateKeyException dupKeyEx) {
            String xmlException = makeXmlException("tryCreateTroubleTicketsByValuesException", "DuplicateKeyException",
                    dupKeyEx.getMessage());
            log("XVTTroubleTicketSession.createTT: DuplicateKeyException caught on TTSession.createManagedEntity");
            dupKeyEx.printStackTrace();

            throw new javax.ejb.DuplicateKeyException(xmlException);

        } catch (javax.ejb.CreateException createEx) {
            String xmlException = makeXmlException("tryCreateTroubleTicketsByValuesException", "CreateException",
                    createEx.getMessage());
            log("XVTTroubleTicketSession.createTT: javax.ejb.CreateException caught on TTSession.createManagedEntity");
            createEx.printStackTrace();

            throw new javax.ejb.CreateException(xmlException);
        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("tryCreateTroubleTicketsByValuesException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession.createTT: IOException caught on TTSession.createManagedEntity");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("tryCreateTroubleTicketsByValuesException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession.createTT: SAXException caught on TTSession.createManagedEntity");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("tryCreateTroubleTicketsByValuesException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession.createTT: SAXException caught on TTSession.createManagedEntity");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }


        //translate the results back to XML
        String xmlKeyResults = null;

        try {
            xmlKeyResults = XmlTranslator.toXml(keyResults);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("tryCreateTroubleTicketsByValuesException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession.createTTsByValues: SAXException caught on TTSession.createManagedEntity");
            saxEx.printStackTrace();
            throw new javax.oss.IllegalArgumentException(xmlException);
        }

        //add the respective xml envelop
        return addXmlRootElement("tryCreateTroubleTicketsByValuesResponse", xmlKeyResults);

    }


    /*
    //Creation of Multiple Trouble Tickets each with the same value
    //Input Parameter is equal to  TryCreateTroubleTicketsByKeysRequest as defined in XML Schema
    //Output Parameter is equal to TryCreateTroubleTicketsByKeysResponse which is itself defined
    //in the XML Schema
    public String tryCreateTroubleTicketsByKeys(String TryCreateTroubleTicketsByKeysRequest )
    throws javax.oss.IllegalArgumentException,
           javax.ejb.DuplicateKeyException,
           javax.ejb.CreateException,
           java.rmi.RemoteException
    {
        return new String();
    }
    */

    //------------------------------------------------------------------------
    // CLOSE (best effort)
    //------------------------------------------------------------------------

    //Close Multiple Trouble Tickets given their keys
    //Input Parameter is equal to  TroubleTicketByKeysRequest as defined in XML Schema
    //Output Parameter is equal to TryCloseTroubleTicketByKeysResponse which is itself defined
    //in the XML Schema
    public String tryCloseTroubleTicketsByKeys(String tryCloseTroubleTicketsByKeysRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.FinderException,
            javax.ejb.RemoveException, javax.ejb.EJBException {
        TroubleTicketKeyResult[] keyResults = null;

        try {
            // Get the xml document from the passed in xml string
            Document doc = parseXmlString(tryCloseTroubleTicketsByKeysRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);
            if (!rootName.equals("tt:tryCloseTroubleTicketsByKeysRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }

            jvtSession = this.getJVTRemoteInterface();	//lookup/create the JVT session bean

            //translate the ttValues from Xml to Java value types.
            //TroubleTicketValue[] ttValues = XmlTranslator.fromXml(doc);


            Node node = null;
            NodeList nodeList = null;

            nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "troubleTicketKeys");
            if (nodeList != null) {
                node = nodeList.item(0);
                if (node == null) {
                    String nullNode = new String("null Node in XmlTranslator.fromXml (multiple values)");
                    log(nullNode);
                    throw new javax.oss.IllegalArgumentException(nullNode);

                }
            }

            //New Method. Similar to TTValuesfromXMLItems...
            TroubleTicketKey[] ttKeys = XmlTranslator.TTKeysFromXmlItems(node);


            nodeList = rootElement.getChildNodes();
            int status = -1;
            String closeNarr = null;
            for (int i = 0; i < nodeList.getLength(); i++) {
                node = nodeList.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) continue;
                String nodeName = node.getNodeName();
                //System.out.println( "---->NODE NAME = " + nodeName );
                if (nodeName.equals("tt:status")) {
                    if (node.hasChildNodes()) {
                        status = XmlTranslator.EnumeratedTypeFromXml(node, TroubleStatus.class);
                    }
                } else if (nodeName.equals("tt:closeOutNarr")) {
                    if (node.hasChildNodes()) {
                        closeNarr = node.getFirstChild().getNodeValue();
                    }
                }

            }





            //------------------------------------------------------
            // Call the corresponding JVTSession bean method
            //------------------------------------------------------




            keyResults = jvtSession.tryCloseTroubleTicketsByKeys(ttKeys, status, closeNarr);
            /*
            log ("  KeyType:    " + returned_key.getType());
            log ("  KeyDomain:    " + returned_key.getDomain());
            log ("  KeyPKey:    " + returned_key.getPrimaryKey());
            */
        }

                // The following exceptions are be used to build the respective xml exception.
                // The xml exception will be embedded as the message in the respective java exception.
        catch (javax.oss.IllegalArgumentException illEx) {
            String xmlException = makeXmlException("tryCloseTroubleTicketsByKeysRequest", "IllegalArgumentException",
                    illEx.getMessage());
            log("XVTTroubleTicketSession.createTT: IllegalArgumentException caught on TTSession.createManagedEntity");
            illEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);

        } catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("tryCloseTroubleTicketsByKeysException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.createTT: RemoteException caught on TTSession.createManagedEntity");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        }

                /*  catch( oracle.xml.parser.v2.XMLParseException parsEx )
                  {
                      String xmlException = makeXmlException( "tryCreateTroubleTicketsByValuesException", "IllegalArgumentException",
                                                              parsEx.getMessage()  );
                      log ("XVTTroubleTicketSession.createTT: XMLParseException caught on TTSession.createManagedEntity");
                      parsEx.printStackTrace();

                      throw new javax.oss.IllegalArgumentException( xmlException );

                  }
                  */ catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("tryCloseTroubleTicketsByKeysException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession.createTT: IOException caught on TTSession.createManagedEntity");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }
                /*  catch( org.xml.sax.SAXException saxEx )
                  {
                      String xmlException = makeXmlException( "tryCreateTroubleTicketsByValuesException", "IllegalArgumentException",
                                                              saxEx.getMessage()  );
                      log ("XVTTroubleTicketSession.createTT: SAXException caught on TTSession.createManagedEntity");
                      saxEx.printStackTrace();

                      throw new javax.oss.IllegalArgumentException( xmlException );
                  }
                  */ catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("tryCloseTroubleTicketsByKeysException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession.createTT: SAXException caught on TTSession.createManagedEntity");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }


        //translate the results back to XML
        String xmlKeyResults = null;

        try {
            xmlKeyResults = XmlTranslator.toXml(keyResults);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("tryCloseTroubleTicketsByKeysException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession.createTTsByValues: SAXException caught on TTSession.createManagedEntity");
            saxEx.printStackTrace();
            throw new javax.oss.IllegalArgumentException(xmlException);
        }

        //add the respective xml envelop
        return addXmlRootElement("tryCloseTroubleTicketsByKeysResponse", xmlKeyResults);
    }


    //Close multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  TryCloseTroubleTicketByValueRequest as defined in XML Schema
    //Output Parameter is equal to TryCloseTroubleTicketByValueResponse which is itself defined
    //in the XML Schema
    public XmlResponseIterator tryCloseTroubleTicketsByTemplate(String TryCloseTroubleTicketByValueRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.RemoveException,
            javax.ejb.ObjectNotFoundException, javax.ejb.EJBException {
        //VP supress the trailing IF in the cast due to the interface return now is the
        // TroubleTicketKeyResultIterator
        TroubleTicketKeyResultIterator resultKeyIter = null;
        XmlResponseIteratorHome xmlResIterator = null;
        XmlResponseIteratorIF xmlIter = null;
        JVTTroubleTicketSession jvtSession = null;

        try {

            // Since Validation is turned off, parsing is performed without validation.
            //> The verification code is to be moved into the ttValue.fromXml() method
            // to aviod doubling the parsing effort.
            Document doc = parseXmlString(TryCloseTroubleTicketByValueRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);

            if (!rootName.equals("tt:tryCloseTroubleTicketsByTemplateRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }

            // let us forget about the xml string for now
            //TroubleTicketValue template = new TroubleTicketValueImpl();

            // The following lines of code will be uncommented once the jvt integration starts.
            // Create the tmeplate ttValue object and populate it with the passed-in xml attributes.
            //XmlTranslator.fromXmlTag( (Element) doc, template , "template" );
            TroubleTicketValue template = SerializeValue(doc, "template");
            // Now let us deal with the return mode issue
            NodeList returnModeNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "failuresOnly");

            // Typically only one element exist in the return mode list
            String xmlFailuresOnly = (returnModeNodeList.item(0)).getFirstChild().getNodeValue();
            //System.out.println("xmlReturnMode: " + xmlReturnMode + " JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");

            boolean failures_only = XmlTranslator.BooleanFromXml(returnModeNodeList.item(0));


            log("TTClient.closeAllTTs: calling ttSession.tryCloseTroubleTickets");


            jvtSession = this.getJVTRemoteInterface();

            NodeList nodeList = null;
            Node node = null;
            nodeList = rootElement.getChildNodes();
            int status = -1;
            String closeNarr = null;
            for (int i = 0; i < nodeList.getLength(); i++) {
                node = nodeList.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) continue;
                String nodeName = node.getNodeName();
                //System.out.println( "---->NODE NAME = " + nodeName );
                if (nodeName.equals("tt:status")) {
                    if (node.hasChildNodes()) {
                        status = XmlTranslator.EnumeratedTypeFromXml(node, TroubleStatus.class);
                    }
                } else if (nodeName.equals("tt:closeOutNarr")) {
                    if (node.hasChildNodes()) {
                        closeNarr = node.getFirstChild().getNodeValue();
                    }
                }

            }

            //VP supress the trailing IF in the cast due to the interface return now is the
            // TroubleTicketKeyResultIterator
            resultKeyIter = (TroubleTicketKeyResultIterator)
                    //jvtSession.tryCloseTroubleTicketsByTemplate(template,status,closeNarr, false);
                    jvtSession.tryCloseTroubleTicketsByTemplate(template, status, closeNarr, failures_only);

            try {
                xmlResIterator = (XmlResponseIteratorHome) namingContext.lookup
                        ("java:comp/env/ejb/XmlResponseIteratorHome");
            } catch (NamingException nex2) {
                log("XVTSessionBean.tryCloseTroubleTicketsByTemplate: NamingException caught on lookup of XmlResponseIteratorHome");
                nex2.printStackTrace();
                //MR System.exit(1);
            }
            try {
                // Now let us create the xml resp iterator
                xmlIter = xmlResIterator.create(resultKeyIter, "tryCloseTroubleTicketsByTemplateResponse", null);
            } catch (javax.ejb.CreateException createEx) {
                log("TTClient.createTT: CreateException ");
                createEx.printStackTrace();
            }

        }   // End of try
        catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("TryCloseTroubleTicketsByTemplateException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.createTT: RemoteException caught on TTSession.createManagedEntity");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (javax.ejb.CreateException createEx) {
            String xmlException = makeXmlException("TryCloseTroubleTicketsByTemplateException", "CreateException",
                    createEx.getMessage());
            log("XVTTroubleTicketSession.createTT: javax.ejb.CreateException caught on TTSession.createManagedEntity");
            createEx.printStackTrace();
            //> rethrow the remote exception at the current phase
            //> to be replaced by the close exception as defined in the
            //> new xml schema.
            //throw new javax.ejb.CreateException( xmlException );
            throw new javax.ejb.EJBException(xmlException);
        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("TryCloseTroubleTicketsByTemplateException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession.createTT: IOException caught on TTSession.createManagedEntity");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("TryCloseTroubleTicketsByTemplateException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession.createTT: SAXException caught on TTSession.createManagedEntity");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("TryCloseTroubleTicketsByTemplateException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession.createTT: SAXException caught on TTSession.createManagedEntity");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);

        }
        //VP
        XmlResponseIterator retXmlIter = new XmlResponseIteratorImpl(xmlIter);
        return retXmlIter;
    }


    //Close multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  TryCloseTroubleTicketByValuesRequest as defined in XML Schema
    //Output Parameter is equal to TryCloseTroubleTicketByValuesResponse which is itself defined
    //in the XML Schema
    public XmlResponseIterator tryCloseTroubleTicketsByTemplates(String tryCloseTroubleTicketsByTemplatesRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.RemoveException,
            javax.ejb.FinderException, javax.ejb.EJBException {
        //VP supress the trailing IF in the cast due to the interface return now is the
        // TroubleTicketKeyResultIterator
        TroubleTicketKeyResultIterator resultKeyIter = null;
        XmlResponseIteratorHome xmlResIterator = null;
        XmlResponseIteratorIF xmlIter = null;
        JVTTroubleTicketSession jvtSession = null;

        try {

            // Since Validation is turned off, parsing is performed without validation.
            //> The verification code is to be moved into the ttValue.fromXml() method
            // to aviod doubling the parsing effort.
            Document doc = parseXmlString(tryCloseTroubleTicketsByTemplatesRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);

            if (!rootName.equals("tt:tryCloseTroubleTicketsByTemplatesRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }

            //TroubleTicketValue ttValue    = new TroubleTicketValueImpl();
            //get template element
            TroubleTicketValue[] ttTemplates = XmlTranslator.getTroubleTicketValues(doc, "templates");
            //XmlTranslator.fromXmlTag( (Element) doc, ttValue, "value" );
            //TroubleTicketValue ttValue = SerializeValue( doc, "value");

            //WIPRO

            NodeList returnModeNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "failuresOnly");

            // Typically only one element exist in the return mode list
            String xmlFailuresOnly = (returnModeNodeList.item(0)).getFirstChild().getNodeValue();
            boolean failures_only = XmlTranslator.BooleanFromXml(returnModeNodeList.item(0));

            //WIPRO

            NodeList nodeList = null;
            Node node = null;
            nodeList = rootElement.getChildNodes();
            int status = -1;
            String closeNarr = null;
            for (int i = 0; i < nodeList.getLength(); i++) {
                node = nodeList.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) continue;
                String nodeName = node.getNodeName();
                //System.out.println( "---->NODE NAME = " + nodeName );
                if (nodeName.equals("tt:status")) {
                    if (node.hasChildNodes()) {
                        status = XmlTranslator.EnumeratedTypeFromXml(node, TroubleStatus.class);
                    }
                } else if (nodeName.equals("tt:closeOutNarr")) {
                    if (node.hasChildNodes()) {
                        closeNarr = node.getFirstChild().getNodeValue();
                    }
                }

            }


            jvtSession = this.getJVTRemoteInterface();


            //VP supress the trailing IF in the cast due to the interface return now is the
            // TroubleTicketKeyResultIterator
            resultKeyIter =
                    (TroubleTicketKeyResultIterator)
                    //jvtSession.tryCloseTroubleTicketsByTemplates( ttTemplates, status,closeNarr,false);
                    jvtSession.tryCloseTroubleTicketsByTemplates(ttTemplates, status, closeNarr, failures_only);


            try {
                xmlResIterator = (XmlResponseIteratorHome) namingContext.lookup
                        ("java:comp/env/ejb/XmlResponseIteratorHome");
            } catch (NamingException nex2) {
                log("XVTSessionBean.queryTroubleTickets: NamingException caught on lookup of XmlResponseIteratorHome");
                nex2.printStackTrace();
                //MR System.exit(1);
            }
            try {
                // Now let us create the xml resp iterator
                log("About to call xmlResIterator.create....");
                xmlIter = xmlResIterator.create(resultKeyIter, "tryCloseTroubleTicketsByTemplatesResponse", new String[0]);
                log("Returning from call xmlResIterator.create....");
            } catch (javax.ejb.CreateException createEx) {
                log("TTClient.createTT: CreateException ");
                createEx.printStackTrace();
            }

        }   // End of try
        catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("tryCloseTroubleTicketsByTemplatesException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.createTT: RemoteException caught on JVTSession.queryTroubleTickets");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (javax.ejb.CreateException createEx) {
            String xmlException = makeXmlException("tryCloseTroubleTicketsByTemplatesException", "CreateException",
                    createEx.getMessage());
            log("XVTTroubleTicketSession: javax.ejb.CreateException caught on XVTSession.queryTroubleTickets");
            createEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("tryCloseTroubleTicketsByTemplatesException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession: IOException caught on XVTSession.queryTroubleTickets");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("tryCloseTroubleTicketsByTemplatesException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession: SAXException caught on XVTSession.queryTroubleTickets");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("tryCloseTroubleTicketsByTemplatesException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession: SAXException caught on XVTSession.queryTroubleTickets");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }
        log("returning XML response Iterator....");
        //VP
        XmlResponseIterator retXmlIter = new XmlResponseIteratorImpl(xmlIter);
        return retXmlIter;
    }


    //------------------------------------------------------------------------
    // CANCEL (best effort)
    //------------------------------------------------------------------------

    //Cancel Multiple Trouble Tickets given their keys
    //Input Parameter is equal to  TryCancelTroubleTicketByKeysRequest as defined in XML Schema
    //Output Parameter is equal to TryCancelTroubleTicketByKeysResponse which is itself defined
    //in the XML Schema
    public String tryCancelTroubleTicketsByKeys(String tryCancelTroubleTicketsByKeysRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.FinderException,
            javax.ejb.EJBException {
        TroubleTicketKeyResult[] keyResults = null;

        try {
            // Get the xml document from the passed in xml string
            Document doc = parseXmlString(tryCancelTroubleTicketsByKeysRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);
            if (!rootName.equals("tt:tryCancelTroubleTicketsByKeysRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }

            jvtSession = this.getJVTRemoteInterface();	//lookup/create the JVT session bean

            //translate the ttValues from Xml to Java value types.
            //TroubleTicketValue[] ttValues = XmlTranslator.fromXml(doc);


            Node node = null;
            NodeList nodeList = null;

            nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "troubleTicketKeys");
            if (nodeList != null) {
                node = nodeList.item(0);
                if (node == null) {
                    String nullNode = new String("null Node in XmlTranslator.fromXml (multiple values)");
                    log(nullNode);
                    throw new javax.oss.IllegalArgumentException(nullNode);

                }
            }

            //New Method. Similar to TTValuesfromXMLItems...
            TroubleTicketKey[] ttKeys = XmlTranslator.TTKeysFromXmlItems(node);

            int status = -1;
            String closeNarr = null;

            nodeList = rootElement.getChildNodes();


            for (int i = 0; i < nodeList.getLength(); i++) {
                node = nodeList.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) continue;
                String nodeName = node.getNodeName();
                //System.out.println( "---->NODE NAME = " + nodeName );
                if (nodeName.equals("tt:status")) {
                    if (node.hasChildNodes()) {
                        status = XmlTranslator.EnumeratedTypeFromXml(node, TroubleStatus.class);
                    }
                } else if (nodeName.equals("tt:closeOutNarr")) {
                    if (node.hasChildNodes()) {
                        closeNarr = node.getFirstChild().getNodeValue();
                    }
                }

            }





            //------------------------------------------------------
            // Call the corresponding JVTSession bean method
            //------------------------------------------------------




            keyResults = jvtSession.tryCancelTroubleTicketsByKeys(ttKeys, status, closeNarr);

        }

                // The following exceptions are be used to build the respective xml exception.
                // The xml exception will be embedded as the message in the respective java exception.
        catch (javax.oss.IllegalArgumentException illEx) {
            String xmlException = makeXmlException("tryCancelTroubleTicketsByKeysRequest", "IllegalArgumentException",
                    illEx.getMessage());
            log("XVTTroubleTicketSession.createTT: IllegalArgumentException caught on TTSession.createManagedEntity");
            illEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);

        } catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("tryCancelTroubleTicketsByKeysException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.createTT: RemoteException caught on TTSession.createManagedEntity");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        }

                /*  catch( oracle.xml.parser.v2.XMLParseException parsEx )
                  {
                      String xmlException = makeXmlException( "tryCreateTroubleTicketsByValuesException", "IllegalArgumentException",
                                                              parsEx.getMessage()  );
                      log ("XVTTroubleTicketSession.createTT: XMLParseException caught on TTSession.createManagedEntity");
                      parsEx.printStackTrace();

                      throw new javax.oss.IllegalArgumentException( xmlException );

                  }
                  */ catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("tryCancelTroubleTicketsByKeysException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession.createTT: IOException caught on TTSession.createManagedEntity");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }
                /*  catch( org.xml.sax.SAXException saxEx )
                  {
                      String xmlException = makeXmlException( "tryCreateTroubleTicketsByValuesException", "IllegalArgumentException",
                                                              saxEx.getMessage()  );
                      log ("XVTTroubleTicketSession.createTT: SAXException caught on TTSession.createManagedEntity");
                      saxEx.printStackTrace();

                      throw new javax.oss.IllegalArgumentException( xmlException );
                  }
                  */ catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("tryCancelTroubleTicketsByKeysException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession.createTT: SAXException caught on TTSession.createManagedEntity");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }


        //translate the results back to XML
        String xmlKeyResults = null;

        try {
            xmlKeyResults = XmlTranslator.toXml(keyResults);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("tryCancelTroubleTicketsByKeysException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession.createTTsByValues: SAXException caught on TTSession.createManagedEntity");
            saxEx.printStackTrace();
            throw new javax.oss.IllegalArgumentException(xmlException);
        }

        //add the respective xml envelop
        return addXmlRootElement("tryCancelTroubleTicketsByKeysResponse", xmlKeyResults);
    }


    //Cancel multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  TryCancelTroubleTicketByTemplateRequest as defined in XML Schema
    //Output Parameter is equal to TryCancelTroubleTicketByTemplateResponse which is itself defined
    //in the XML Schema
    public XmlResponseIterator tryCancelTroubleTicketsByTemplate(String tryCancelTroubleTicketsByTemplateRequest)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.ObjectNotFoundException, javax.ejb.EJBException {
        //VP supress the trailing IF in the cast due to the interface return now is the
        // TroubleTicketKeyResultIterator
        TroubleTicketKeyResultIterator resultKeyIter = null;
        XmlResponseIteratorHome xmlResIterator = null;
        XmlResponseIteratorIF xmlIter = null;
        JVTTroubleTicketSession jvtSession = null;

        try {

            // Since Validation is turned off, parsing is performed without validation.
            //> The verification code is to be moved into the ttValue.fromXml() method
            // to aviod doubling the parsing effort.
            Document doc = parseXmlString(tryCancelTroubleTicketsByTemplateRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);

            if (!rootName.equals("tt:tryCancelTroubleTicketsByTemplateRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }


            // The following lines of code will be uncommented once the jvt integration starts.
            // Create the tmeplate ttValue object and populate it with the passed-in xml attributes.
            // let us forget about the xml string for now
            //TroubleTicketValue template = new TroubleTicketValueImpl();

            // The following lines of code will be uncommented once the jvt integration starts.
            // Create the tmeplate ttValue object and populate it with the passed-in xml attributes.
            //XmlTranslator.fromXmlTag( (Element) doc, template , "template" );
            TroubleTicketValue template = SerializeValue(doc, "template");

            // Now let us deal with the return mode issue
            NodeList returnModeNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "failuresOnly");

            // Typically only one element exist in the return mode list
            String xmlFailuresOnly = (returnModeNodeList.item(0)).getFirstChild().getNodeValue();
            //System.out.println("xmlReturnMode: " + xmlReturnMode + " JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");

            boolean failures_only = XmlTranslator.BooleanFromXml(returnModeNodeList.item(0));


            log("TTClient.closeAllTTs: calling ttSession.tryCancelTroubleTicketsByTemplateRequest");
            //resultKeyIter = (TroubleTicketKeyResultIteratorIF)
            //                jvtSession.tryCloseTroubleTickets(template,ReturnMode.RETURNALL);



            jvtSession = this.getJVTRemoteInterface();


            //Bug is that status, closeOutNarr could be part of the
            //template as well so the following code would get them
            //from the template and not from the arguments.....
            //either remove it or work under the node after template ???
            //solution is to do the processing on the direct child only

            int status = -1;
            String closeNarr = null;
            NodeList nodeList = null;
            Node node = null;
            nodeList = rootElement.getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                node = nodeList.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) continue;
                String nodeName = node.getNodeName();
                //System.out.println( "---->NODE NAME = " + nodeName );
                if (nodeName.equals("tt:status")) {
                    if (node.hasChildNodes()) {
                        status = XmlTranslator.EnumeratedTypeFromXml(node, TroubleStatus.class);
                    }
                } else if (nodeName.equals("tt:closeOutNarr")) {
                    if (node.hasChildNodes()) {
                        closeNarr = node.getFirstChild().getNodeValue();
                    }
                }

            }




            //VP supress the trailing IF in the cast due to the interface return now is the
            // TroubleTicketKeyResultIterator
            resultKeyIter = (TroubleTicketKeyResultIterator)
                    //jvtSession.tryCancelTroubleTicketsByTemplate(template,status,closeNarr, false);        //WIPRO
                    jvtSession.tryCancelTroubleTicketsByTemplate(template, status, closeNarr, failures_only);  //WIPRO

            try {
                xmlResIterator = (XmlResponseIteratorHome) namingContext.lookup
                        ("java:comp/env/ejb/XmlResponseIteratorHome");
            } catch (NamingException nex2) {
                log("XVTSessionBean.tryCancelTroubleTicketsByTemplate: NamingException caught on lookup of XmlResponseIteratorHome");
                nex2.printStackTrace();
                //MR System.exit(1);
            }
            try {
                // Now let us create the xml resp iterator
                xmlIter = xmlResIterator.create(resultKeyIter, "tryCancelTroubleTicketsByTemplateResponse", null);
            } catch (javax.ejb.CreateException createEx) {
                log("TTClient.createTT: CreateException ");
                createEx.printStackTrace();
            }

        }   // End of try
        catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("tryCancelTroubleTickesByTemplateException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.createTT: RemoteException caught on TTSession.createManagedEntity");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (javax.ejb.CreateException createEx) {
            String xmlException = makeXmlException("tryCancelTroubleTicketsByTemplateException", "CreateException",
                    createEx.getMessage());
            log("XVTTroubleTicketSession.createTT: javax.ejb.CreateException caught on TTSession.createManagedEntity");
            createEx.printStackTrace();
            //> rethrow the remote exception at the current phase
            //> to be replaced by the close exception as defined in the
            //> new xml schema.
            //throw new javax.ejb.CreateException( xmlException );
            throw new javax.ejb.EJBException(xmlException);
        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("tryCancelTroubleTicketsByTemplateException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession.createTT: IOException caught on TTSession.createManagedEntity");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("tryCancelTroubleTicketsByTemplateException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession.createTT: SAXException caught on TTSession.createManagedEntity");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("tryCancelTroubleTicketsByTemplateException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession.createTT: SAXException caught on TTSession.createManagedEntity");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);

        }

        //VP
        XmlResponseIterator retXmlIter = new XmlResponseIteratorImpl(xmlIter);
        return retXmlIter;
    }


    //Cancel multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  TryCancelTroubleTicketByTemplatesRequest as defined in XML Schema
    //Output Parameter is equal to TryCancelTroubleTicketByTemplatesResponse which is itself defined
    //in the XML Schema
    public XmlResponseIterator tryCancelTroubleTicketsByTemplates(String tryCancelTroubleTicketsByTemplatesRequest)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.FinderException, javax.ejb.EJBException {
        //VP supress the trailing IF in the cast due to the interface return now is the
        // TroubleTicketKeyResultIterator
        TroubleTicketKeyResultIterator resultKeyIter = null;
        XmlResponseIteratorHome xmlResIterator = null;
        XmlResponseIteratorIF xmlIter = null;
        JVTTroubleTicketSession jvtSession = null;

        try {

            // Since Validation is turned off, parsing is performed without validation.
            //> The verification code is to be moved into the ttValue.fromXml() method
            // to aviod doubling the parsing effort.
            Document doc = parseXmlString(tryCancelTroubleTicketsByTemplatesRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);

            if (!rootName.equals("tt:tryCancelTroubleTicketsByTemplatesRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }

            //TroubleTicketValue ttValue    = new TroubleTicketValueImpl();
            //get template element
            TroubleTicketValue[] ttTemplates = XmlTranslator.getTroubleTicketValues(doc, "templates");
            //XmlTranslator.fromXmlTag( doc, ttValue, "value" );

            //WIPRO

            NodeList returnModeNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "failuresOnly");

            // Typically only one element exist in the return mode list
            String xmlFailuresOnly = (returnModeNodeList.item(0)).getFirstChild().getNodeValue();
            boolean failures_only = XmlTranslator.BooleanFromXml(returnModeNodeList.item(0));

            //WIPRO

            NodeList nodeList = null;
            Node node = null;
            nodeList = rootElement.getChildNodes();
            int status = -1;
            String closeNarr = null;
            for (int i = 0; i < nodeList.getLength(); i++) {
                node = nodeList.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) continue;
                String nodeName = node.getNodeName();
                //System.out.println( "---->NODE NAME = " + nodeName );
                if (nodeName.equals("tt:status")) {
                    if (node.hasChildNodes()) {
                        status = XmlTranslator.EnumeratedTypeFromXml(node, TroubleStatus.class);
                    }
                } else if (nodeName.equals("tt:closeOutNarr")) {
                    if (node.hasChildNodes()) {
                        closeNarr = node.getFirstChild().getNodeValue();
                    }
                }

            }

            jvtSession = this.getJVTRemoteInterface();

            //VP supress the trailing IF in the cast due to the interface return now is the
            // TroubleTicketKeyResultIterator
            resultKeyIter =
                    (TroubleTicketKeyResultIterator)
                    //jvtSession.tryCancelTroubleTicketsByTemplates( ttTemplates, status,closeNarr,false);
                    jvtSession.tryCancelTroubleTicketsByTemplates(ttTemplates, status, closeNarr, failures_only);


            try {
                xmlResIterator = (XmlResponseIteratorHome) namingContext.lookup
                        ("java:comp/env/ejb/XmlResponseIteratorHome");
            } catch (NamingException nex2) {
                log("XVTSessionBean.queryTroubleTickets: NamingException caught on lookup of XmlResponseIteratorHome");
                nex2.printStackTrace();
                //MR System.exit(1);
            }
            try {
                // Now let us create the xml resp iterator
                log("About to call xmlResIterator.create....");
                xmlIter = xmlResIterator.create(resultKeyIter, "tryCancelTroubleTicketsByTemplatesResponse", new String[0]);
                log("Returning from call xmlResIterator.create....");
            } catch (javax.ejb.CreateException createEx) {
                log("TTClient.createTT: CreateException ");
                createEx.printStackTrace();
            }

        }   // End of try
        catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("tryCancelTroubleTicketsByTemplatesException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.createTT: RemoteException caught on JVTSession.queryTroubleTickets");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (javax.ejb.CreateException createEx) {
            String xmlException = makeXmlException("tryCancelTroubleTicketsByTemplatesException", "CreateException",
                    createEx.getMessage());
            log("XVTTroubleTicketSession: javax.ejb.CreateException caught on XVTSession.queryTroubleTickets");
            createEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("tryCancelTroubleTicketsByTemplatesException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession: IOException caught on XVTSession.queryTroubleTickets");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("tryCancelTroubleTicketsByTemplatesException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession: SAXException caught on XVTSession.queryTroubleTickets");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("tryCancelTroubleTicketsByTemplatesException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession: SAXException caught on XVTSession.queryTroubleTickets");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }
        log("returning XML response Iterator....");
        //VP
        XmlResponseIterator retXmlIter = new XmlResponseIteratorImpl(xmlIter);
        return retXmlIter;
    }


    //------------------------------------------------------------------------
    // ESCALATE (best effort)
    //------------------------------------------------------------------------

    //Escalate Multiple Trouble Tickets given their keys
    //Input Parameter is equal to  TryEscalateTroubleTicketByKeysRequest as defined in XML Schema
    //Output Parameter is equal to TryEscalateTroubleTicketByKeysResponse which is itself defined
    //in the XML Schema
    public String tryEscalateTroubleTicketsByKeys(String tryEscalateTroubleTicketsByKeysRequest)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.FinderException, javax.ejb.EJBException {
        TroubleTicketKeyResult[] keyResults = null;

        try {
            // Get the xml document from the passed in xml string
            Document doc = parseXmlString(tryEscalateTroubleTicketsByKeysRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);
            if (!rootName.equals("tt:tryEscalateTroubleTicketsByKeysRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }

            jvtSession = this.getJVTRemoteInterface();	//lookup/create the JVT session bean

            //translate the ttValues from Xml to Java value types.
            //TroubleTicketValue[] ttValues = XmlTranslator.fromXml(doc);


            Node node = null;
            NodeList nodeList = null;

            nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "troubleTicketKeys");
            if (nodeList != null) {
                node = nodeList.item(0);
                if (node == null) {
                    String nullNode = new String("null Node in XmlTranslator.fromXml (multiple values)");
                    log(nullNode);
                    throw new javax.oss.IllegalArgumentException(nullNode);

                }
            }

            //New Method. Similar to TTValuesfromXMLItems...
            TroubleTicketKey[] ttKeys = XmlTranslator.TTKeysFromXmlItems(node);


            //------------------------------------------------------
            // Call the corresponding JVTSession bean method
            //------------------------------------------------------


            EscalationList escs = getEscalationList(rootElement);

            keyResults = jvtSession.tryEscalateTroubleTicketsByKeys(ttKeys, escs);

        }

                // The following exceptions are be used to build the respective xml exception.
                // The xml exception will be embedded as the message in the respective java exception.
        catch (javax.oss.IllegalArgumentException illEx) {
            String xmlException = makeXmlException("tryEscalateTroubleTicketsByKeysException", "IllegalArgumentException",
                    illEx.getMessage());
            log("XVTTroubleTicketSession.createTT: IllegalArgumentException caught on TTSession.createManagedEntity");
            illEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);

        } catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("tryEscalateTroubleTicketsByKeysException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.createTT: RemoteException caught on TTSession.createManagedEntity");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("tryEscalateTroubleTicketsByKeysException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession.createTT: IOException caught on TTSession.createManagedEntity");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("tryEscalateTroubleTicketsByKeysException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession.createTT: SAXException caught on TTSession.createManagedEntity");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }


        //translate the results back to XML
        String xmlKeyResults = null;

        try {
            xmlKeyResults = XmlTranslator.toXml(keyResults);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("tryEscalateTroubleTicketsByKeysException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession.createTTsByValues: SAXException caught on TTSession.createManagedEntity");
            saxEx.printStackTrace();
            throw new javax.oss.IllegalArgumentException(xmlException);
        }

        //add the respective xml envelop
        return addXmlRootElement("tryEscalateTroubleTicketsByKeysResponse", xmlKeyResults);


    }

    public String getCloseOutNarr(Document doc)
            throws javax.oss.IllegalArgumentException {

        String closeNarr = null;
        Node node = null;

        NodeList nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "closeOutNarr");
        if (nodeList != null) {

            node = nodeList.item(0);

            if (node == null) {
                String nullNode = new String("null Node in XmlTranslator.fromXml (multiple values)");
                log(nullNode);
                throw new javax.oss.IllegalArgumentException(nullNode);

            }
        }

        closeNarr = node.getFirstChild().getNodeValue();
        return closeNarr;

    }

    public boolean getResyncRequired(Document doc)
            throws javax.oss.IllegalArgumentException {
        boolean resyncRequired = false;

        Node node = null;
        NodeList nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "resyncRequired");
        if (nodeList != null) {
            node = nodeList.item(0);
            if (node == null) {
                String nullNode = new String("null Node in XmlTranslator.fromXml (multiple values)");
                log(nullNode);

                return resyncRequired;

            }

        }


        resyncRequired = XmlTranslator.BooleanFromXml(node);

        return resyncRequired;


    }

    public int getStatus(Document doc)
            throws javax.oss.IllegalArgumentException {
        int status = -1;
        Node node = null;
        NodeList nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "status");
        if (nodeList != null) {
            node = nodeList.item(0);
            if (node == null) {
                String nullNode = new String("null Node in XmlTranslator.fromXml (multiple values)");
                log(nullNode);
                throw new javax.oss.IllegalArgumentException(nullNode);

            }

        }


        status = XmlTranslator.EnumeratedTypeFromXml(node, TroubleStatus.class);

        return status;

    }

    public EscalationList getEscalationList(Element rootElement)
            throws javax.oss.IllegalArgumentException {
        NodeList nodeList = null;
        Node node = null;
        nodeList = rootElement.getChildNodes();
        EscalationList escs = null;
        for (int i = 0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) continue;
            String nodeName = node.getNodeName();
            //System.out.println( "---->NODE NAME = " + nodeName );
            if (nodeName.equals("tt:escalationList")) {
                if (node.hasChildNodes()) {
                    TroubleTicketValue ttv = new TroubleTicketValueImpl();
                    XmlTranslator.EscalationListFromXml(node, ttv);
                    escs = ttv.getEscalationList();
                }
            }

        }


        return escs;

    }

    //Escalate multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  TryEscalateTroubleTicketByTemplateRequest as defined in XML Schema
    //Output Parameter is equal to TryEscalateTroubleTicketByTemplateResponse which is itself defined
    //in the XML Schema
    public XmlResponseIterator
            tryEscalateTroubleTicketsByTemplate(String tryEscalateTroubleTicketsByTemplateRequest)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.ObjectNotFoundException, javax.ejb.EJBException {
        //VP supress the trailing IF in the cast due to the interface return now is the
        // TroubleTicketKeyResultIterator
        TroubleTicketKeyResultIterator resultKeyIter = null;
        XmlResponseIteratorHome xmlResIterator = null;
        XmlResponseIteratorIF xmlIter = null;
        JVTTroubleTicketSession jvtSession = null;

        try {

            // Since Validation is turned off, parsing is performed without validation.
            //> The verification code is to be moved into the ttValue.fromXml() method
            // to aviod doubling the parsing effort.
            Document doc = parseXmlString(tryEscalateTroubleTicketsByTemplateRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);

            if (!rootName.equals("tt:tryEscalateTroubleTicketsByTemplateRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }

            //TroubleTicketValue ttValue    = new TroubleTicketValueImpl();
            //get template element
            //TroubleTicketValue[] ttTemplates = XmlTranslator.getTroubleTicketValues( doc, "templates");
            //XmlTranslator.fromXmlTag( (Element)  doc, ttValue, "template" );

            TroubleTicketValue ttValue = SerializeValue(doc, "template");

            //WIPRO

            NodeList returnModeNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "failuresOnly");

            // Typically only one element exist in the return mode list
            String xmlFailuresOnly = (returnModeNodeList.item(0)).getFirstChild().getNodeValue();
            boolean failures_only = XmlTranslator.BooleanFromXml(returnModeNodeList.item(0));

            //WIPRO

            EscalationList escs = getEscalationList(rootElement);


            jvtSession = this.getJVTRemoteInterface();


            //VP supress the trailing IF in the cast due to the interface return now is the
            // TroubleTicketKeyResultIterator
            resultKeyIter =
                    (TroubleTicketKeyResultIterator)
                    jvtSession.tryEscalateTroubleTicketsByTemplate(ttValue, escs, failures_only);


            try {
                xmlResIterator = (XmlResponseIteratorHome) namingContext.lookup
                        ("java:comp/env/ejb/XmlResponseIteratorHome");
            } catch (NamingException nex2) {
                log("XVTSessionBean.queryTroubleTickets: NamingException caught on lookup of XmlResponseIteratorHome");
                nex2.printStackTrace();
                //MR System.exit(1);
            }
            try {
                // Now let us create the xml resp iterator
                log("About to call xmlResIterator.create....");
                xmlIter = xmlResIterator.create(resultKeyIter, "tryEscalateTroubleTicketsByTemplateResponse", new String[0]);
                log("Returning from call xmlResIterator.create....");
            } catch (javax.ejb.CreateException createEx) {
                log("TTClient.createTT: CreateException ");
                createEx.printStackTrace();
            }

        }   // End of try
        catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("tryEscalateTroubleTicketsByTemplateException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.createTT: RemoteException caught on JVTSession.queryTroubleTickets");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (javax.ejb.CreateException createEx) {
            String xmlException = makeXmlException("tryEscalateTroubleTicketsByTemplateException", "CreateException",
                    createEx.getMessage());
            log("XVTTroubleTicketSession: javax.ejb.CreateException caught on XVTSession.queryTroubleTickets");
            createEx.printStackTrace();
            //> rethrow the remote exception at the current phase
            //> to be replaced by the close exception as defined in the
            //> new xml schema.
            //throw new javax.ejb.CreateException( xmlException );
            throw new javax.ejb.EJBException(xmlException);
        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("tryEscalateTroubleTicketsByTemplateException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession: IOException caught on XVTSession.queryTroubleTickets");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("tryEscalateTroubleTicketsByTemplateException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession: SAXException caught on XVTSession.queryTroubleTickets");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("tryEscalateTroubleTicketsByTemplateException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession: SAXException caught on XVTSession.queryTroubleTickets");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }
        log("returning XML response Iterator....");

        //VP
        XmlResponseIterator retXmlIter = new XmlResponseIteratorImpl(xmlIter);
        return retXmlIter;

    }

    //Escalate multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  TryEscalateTroubleTicketByTemplatesRequest as defined in XML Schema
    //Output Parameter is equal to TryEscalateTroubleTicketByTemplatesResponse which is itself defined
    //in the XML Schema
    public XmlResponseIterator tryEscalateTroubleTicketsByTemplates(String tryEscalateTroubleTicketsByTemplatesRequest)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.FinderException, javax.ejb.EJBException {
        //VP supress the trailing IF in the cast due to the interface return now is the
        // TroubleTicketKeyResultIterator
        TroubleTicketKeyResultIterator resultKeyIter = null;
        XmlResponseIteratorHome xmlResIterator = null;
        XmlResponseIteratorIF xmlIter = null;
        JVTTroubleTicketSession jvtSession = null;

        try {

            // Since Validation is turned off, parsing is performed without validation.
            //> The verification code is to be moved into the ttValue.fromXml() method
            // to aviod doubling the parsing effort.
            Document doc = parseXmlString(tryEscalateTroubleTicketsByTemplatesRequest);

            // Verify that the root element name matches the one designated for this method.
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            log("XVT MDB rootName: " + rootName);

            if (!rootName.equals("tt:tryEscalateTroubleTicketsByTemplatesRequest")) {
                throw new javax.oss.IllegalArgumentException("XVTTroubleTicketSession: Wrong request - " + rootName);
            }

            //TroubleTicketValue ttValue    = new TroubleTicketValueImpl();
            //get template element
            TroubleTicketValue[] ttTemplates = XmlTranslator.getTroubleTicketValues(doc, "templates");
            //XmlTranslator.fromXmlTag( doc, ttValue, "value" );

            //WIPRO

            NodeList returnModeNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "failuresOnly");

            // Typically only one element exist in the return mode list
            String xmlFailuresOnly = (returnModeNodeList.item(0)).getFirstChild().getNodeValue();
            boolean failures_only = XmlTranslator.BooleanFromXml(returnModeNodeList.item(0));

            //WIPRO
            EscalationList escs = getEscalationList(rootElement);

            jvtSession = this.getJVTRemoteInterface();

            //VP supress the trailing IF in the cast due to the interface return now is the
            // TroubleTicketKeyResultIterator
            resultKeyIter =
                    (TroubleTicketKeyResultIterator)
                    //jvtSession.tryEscalateTroubleTicketsByTemplates( ttTemplates, escs ,false);
                    jvtSession.tryEscalateTroubleTicketsByTemplates(ttTemplates, escs, failures_only);


            try {
                xmlResIterator = (XmlResponseIteratorHome) namingContext.lookup
                        ("java:comp/env/ejb/XmlResponseIteratorHome");
            } catch (NamingException nex2) {
                log("XVTSessionBean.queryTroubleTickets: NamingException caught on lookup of XmlResponseIteratorHome");
                nex2.printStackTrace();
                //MR System.exit(1);
            }
            try {
                // Now let us create the xml resp iterator
                log("About to call xmlResIterator.create....");
                xmlIter = xmlResIterator.create(resultKeyIter, "tryEscalateTroubleTicketsByTemplatesResponse", new String[0]);
                log("Returning from call xmlResIterator.create....");
            } catch (javax.ejb.CreateException createEx) {
                log("TTClient.createTT: CreateException ");
                createEx.printStackTrace();
            }

        }   // End of try
        catch (java.rmi.RemoteException remEx) {
            String xmlException = makeXmlException("tryEscalateTroubleTicketsByTemplatesException", "RemoteException",
                    remEx.getMessage());
            log("XVTTroubleTicketSession.createTT: RemoteException caught on JVTSession.queryTroubleTickets");
            remEx.printStackTrace();

            throw new javax.ejb.EJBException(xmlException);
        } catch (javax.ejb.CreateException createEx) {
            String xmlException = makeXmlException("tryEscalateTroubleTicketsByTemplatesException", "CreateException",
                    createEx.getMessage());
            log("XVTTroubleTicketSession: javax.ejb.CreateException caught on XVTSession.queryTroubleTickets");
            createEx.printStackTrace();
            //> rethrow the remote exception at the current phase
            //> to be replaced by the close exception as defined in the
            //> new xml schema.
            //throw new javax.ejb.CreateException( xmlException );
            throw new javax.ejb.EJBException(xmlException);
        } catch (java.io.IOException ioEx) {
            String xmlException = makeXmlException("tryEscalateTroubleTicketsByTemplatesException", "IllegalArgumentException",
                    ioEx.getMessage());
            log("XVTTroubleTicketSession: IOException caught on XVTSession.queryTroubleTickets");
            ioEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("tryEscalateTroubleTicketsByTemplatesException", "IllegalArgumentException",
                    saxEx.getMessage());
            log("XVTTroubleTicketSession: SAXException caught on XVTSession.queryTroubleTickets");
            saxEx.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        } catch (java.lang.Exception ex) {
            String xmlException = makeXmlException("tryEscalateTroubleTicketsByTemplatesException", "IllegalArgumentException",
                    ex.getMessage());
            log("XVTTroubleTicketSession: SAXException caught on XVTSession.queryTroubleTickets");
            ex.printStackTrace();

            throw new javax.oss.IllegalArgumentException(xmlException);
        }
        log("returning XML response Iterator....");

        //VP
        XmlResponseIterator retXmlIter = new XmlResponseIteratorImpl(xmlIter);
        return retXmlIter;

    }


    //------------------------------------------------------------------------
    //
    // Atomic Methods (optional - not implemented)
    //
    //------------------------------------------------------------------------

    //Setting Multiple Trouble Tickets each with different values
    //Input Parameter is equal to  SetTroubleTicketsByValuesRequest as defined in XML Schema
    //Output Parameter is equal to SetTroubleTicketsByValuesResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    public String setTroubleTicketsByValues(String SetTroubleTicketsByValuesRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException,
            SetException, javax.ejb.DuplicateKeyException,
            javax.ejb.FinderException, javax.ejb.EJBException {
        return new String();
    }


    //Setting Multiple Trouble Tickets given their ID with the same value
    //Input Parameter is equal to  SetTroubleTicketsByKeysRequest as defined in XML Schema
    //Output Parameter is equal to SetTroubleTicketsByKeysResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    public String setTroubleTicketsByKeys(String SetTroubleTicketsByKeysRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, SetException,
            javax.ejb.FinderException, javax.ejb.EJBException {
        return new String();
    }

    //Setting Multiple Trouble Tickets matching  the template with the same value
    //Input Parameter is equal to  SetTroubleTicketsByTemplateRequest as defined in XML Schema
    //Output Parameter is equal to SetTroubleTicketsByTemplateResponse as defined in XML Schema
    public String setTroubleTicketsByTemplate(String SetTroubleTicketsByTemplateRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, SetException,
            javax.ejb.ObjectNotFoundException, javax.ejb.EJBException {
        return new String();
    }


    //Setting Multiple Trouble Tickets matching at least one of the template with the same value
    //Input Parameter is equal to  SetTroubleTicketsByTemplatesRequest as defined in XML Schema
    //Output Parameter is equal to SetTroubleTicketsByTemplatesResponse as defined in XML Schema
    public String setTroubleTicketsByTemplates(String SetTroubleTicketsByTemplatesRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, SetException,
            javax.ejb.FinderException, javax.ejb.DuplicateKeyException,
            javax.ejb.EJBException {
        return new String();
    }

    //Creation of Multiple Trouble Tickets each with a different value
    //Input Parameter is equal to  createTroubleTicketByValuesRequest as defined in XML Schema
    //Output Parameter is equal to createTroubleTicketByValuesResponse which is itself defined
    //in the XML Schema
    public String createTroubleTicketsByValues(String CreateTroubleTicketByValuesRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, javax.ejb.DuplicateKeyException,
            javax.ejb.CreateException, javax.ejb.EJBException {
        return new String();
    }

    //Creation of Multiple Trouble Tickets each with the same value
    //Input Parameter is equal to  createTroubleTicketsByKeysRequest as defined in XML Schema
    //Output Parameter is equal to createTroubleTicketsByKeysResponse which is itself defined
    //in the XML Schema
    public String createTroubleTicketsByKeys(String CreateTroubleTicketsByKeysRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, javax.ejb.DuplicateKeyException,
            javax.ejb.CreateException, javax.ejb.EJBException {
        return new String();
    }


    //Close Multiple Trouble Tickets given their keys
    //Input Parameter is equal to  closeTroubleTicketByKeysRequest as defined in XML Schema
    //Output Parameter is equal to closeTroubleTicketByKeysResponse which is itself defined
    //in the XML Schema
    public String closeTroubleTicketsByKeys(String CloseTroubleTicketByKeysRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, javax.ejb.FinderException,
            javax.ejb.RemoveException, javax.ejb.EJBException {
        return new String();
    }

    //Close multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  closeTroubleTicketByValueRequest as defined in XML Schema
    //Output Parameter is equal to closeTroubleTicketByValueResponse which is itself defined
    //in the XML Schema
    public String closeTroubleTicketByTemplate(String CloseTroubleTicketByValueRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, javax.ejb.RemoveException,
            javax.ejb.ObjectNotFoundException, javax.ejb.EJBException {

        return new String();
    }


    //Close multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  closeTroubleTicketByValuesRequest as defined in XML Schema
    //Output Parameter is equal to closeTroubleTicketByValuesResponse which is itself defined
    //in the XML Schema
    public String closeTroubleTicketsByTemplate(String CloseTroubleTicketByValuesRequest)
            throws javax.oss.IllegalArgumentException,
            javax.oss.UnsupportedOperationException,
            javax.ejb.RemoveException,
            javax.ejb.ObjectNotFoundException,
            javax.ejb.EJBException {
        return new String();
    }

    public String closeTroubleTicketsByTemplates(String CloseTroubleTicketsByTemplatesRequest)
            throws javax.oss.IllegalArgumentException,
            javax.oss.UnsupportedOperationException,
            javax.ejb.RemoveException,
            javax.ejb.FinderException,
            javax.ejb.EJBException {
        return new String();
    }

    //Cancel Multiple Trouble Tickets given their keys
    //Input Parameter is equal to  CancelTroubleTicketByKeysRequest as defined in XML Schema
    //Output Parameter is equal to CancelTroubleTicketByKeysResponse which is itself defined
    //in the XML Schema
    public String cancelTroubleTicketsByKeys(String CancelTroubleTicketByKeysRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, javax.ejb.FinderException,
            javax.ejb.EJBException {
        return new String();
    }

    //Cancel multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  CancelTroubleTicketByValueRequest as defined in XML Schema
    //Output Parameter is equal to CancelTroubleTicketByValueResponse which is itself defined
    //in the XML Schema
    public String cancelTroubleTicketsByTemplate(String CancelTroubleTicketByValueRequest)
            throws javax.oss.IllegalArgumentException,
            javax.oss.UnsupportedOperationException,
            javax.ejb.ObjectNotFoundException,
            javax.ejb.EJBException {
        return new String();
    }

    //Cancel multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  CancelTroubleTicketByValuesRequest as defined in XML Schema
    //Output Parameter is equal to CancelTroubleTicketByValuesResponse which is itself defined
    //in the XML Schema
    public String cancelTroubleTicketsByTemplates(String CancelTroubleTicketByValuesRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException,
            javax.ejb.FinderException, javax.ejb.EJBException {
        return new String();
    }

    //Escalate Multiple Trouble Tickets given their keys
    //Input Parameter is equal to  EscalateTroubleTicketByKeysRequest as defined in XML Schema
    //Output Parameter is equal to EscalateTroubleTicketByKeysResponse which is itself defined
    //in the XML Schema
    public String escalateTroubleTicketsByKeys(String CancelTroubleTicketByKeysRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException,
            javax.ejb.FinderException, javax.ejb.EJBException {
        return new String();
    }

    //Escalate multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  EscalateTroubleTicketByValueRequest as defined in XML Schema
    //Output Parameter is equal to EscalateTroubleTicketByValueResponse which is itself defined
    //in the XML Schema
    public String escalateTroubleTicketByTemplate(String CancelTroubleTicketByValueRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException,
            javax.ejb.ObjectNotFoundException, javax.ejb.EJBException {
        return new String();
    }

    //Escalate multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  EscalateTroubleTicketByValuesRequest as defined in XML Schema
    //Output Parameter is equal to EscalateTroubleTicketByValuesResponse which is itself defined
    //in the XML Schema
    public String escalateTroubleTicketsByTemplate(String CancelTroubleTicketByValuesRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException,
            javax.ejb.FinderException, javax.ejb.EJBException {
        return new String();
    }


    /*
    //Close Multiple Trouble Tickets given their keys
    //Input Parameter is equal to  closeTroubleTicketByKeysRequest as defined in XML Schema
    //Output Parameter is equal to closeTroubleTicketByKeysResponse which is itself defined
    //in the XML Schema
    public String closeTroubleTicketsByKeys(String closeTroubleTicketByKeysRequest)
    throws javax.oss.UnsupportedOperationException, javax.ejb.FinderException,
           javax.ejb.RemoveException, javax.ejb.EJBException
    {
        return new String();
    }
    */


    /**
     //Close multiple Trouble Ticket matching at least one of the template
     //Input Parameter is equal to  closeTroubleTicketByValueRequest as defined in XML Schema
     //Output Parameter is equal to closeTroubleTicketByValueResponse which is itself defined
     //in the XML Schema
     public String closeTroubleTicketByTemplate (String closeTroubleTicketByValueRequest)
     throws javax.oss.UnsupportedOperationException, javax.ejb.RemoveException,
     javax.ejb.ObjectNotFoundException, javax.ejb.EJBException
     {
     return new String();
     }
     **/


    /**
     //Close multiple Trouble Ticket matching at least one of the template
     //Input Parameter is equal to  closeTroubleTicketByValuesRequest as defined in XML Schema
     //Output Parameter is equal to closeTroubleTicketByValuesResponse which is itself defined
     //in the XML Schema
     public String closeTroubleTicketsByTemplate (String closeTroubleTicketByValuesRequest )
     throws javax.oss.UnsupportedOperationException, javax.ejb.RemoveException,
     javax.ejb.FinderException, javax.ejb.EJBException
     {
     return new String();
     }
     **/



    //------------------------------------------------------------------------
    //
    // XVT Session private methods
    //
    //------------------------------------------------------------------------
    private URL createURL(String fileName) {
        URL url = null;
        try {
            url = new URL(fileName);
        } catch (MalformedURLException ex) {
            File f = new File(fileName);
            try {
                String path = f.getAbsolutePath();
                log("path: " + path);
                String fs = System.getProperty("file.separator");
                if (fs.length() == 1) {
                    char sep = fs.charAt(0);
                    if (sep != '/')
                        path = path.replace(sep, '/');
                    if (path.charAt(0) != '/')
                        path = '/' + path;
                }
                path = "file://" + path;
                //path = "file:////" + path;
                url = new URL(path);
                log("URL: " + url);
            } catch (MalformedURLException e) {
                log("Cannot create url for: " + fileName);
                //MR System.exit(0);
            }
        }
        return url;
    }


    private URL getXMLFileURL(String xmlStringRequest, String xmlFileName)
            throws java.io.FileNotFoundException, java.io.IOException
            // Need to identify the exceptions
    {
        String fileName = "D:\\Projects\\ossj\\tt3\\javax\\oss\\trouble\\xml\\" + xmlFileName;

        // try
        // {
        // Create the XML output file object.
        File f1 = new File(fileName);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f1)));
        bw.write(xmlStringRequest, 0, xmlStringRequest.length());
        bw.flush();

        //DataOutputStream DOS1 = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f1),96));
        //DOS1.writeUTF(String str)
        //   }
        // catch (IOException ioe)
        //   {
        //	    System.out.println("writeFile: caught i/o exception");
        //     }

        return createURL(fileName);
    }


    // This method reads an XML file and converts it into a String.
    // No validation parsing is involved in this method
    public Document parseXmlString(String docTxt)
            throws org.xml.sax.SAXException,
            Exception,
            java.io.IOException {
        Document doc = null;

        log("Document getDocument ");


        try {
            log("--------document in getDocument-------------");
            log(docTxt);
            log("--------document in getDocument -------------");

            //Obtain an instance of DocumentBuilderFactory.
            DocumentBuilderFactory dbf =
                    DocumentBuilderFactory.newInstance();
            //Specify a validating parser.
            dbf.setValidating(false); // Requires loading the DTD.
            dbf.setNamespaceAware(true);
            dbf.setCoalescing(false);
            dbf.setIgnoringElementContentWhitespace(true);
            dbf.setIgnoringComments(true);



            //Obtain an instance of a DocumentBuilder from the factory.
            DocumentBuilder db = dbf.newDocumentBuilder();
            //Parse the document.

            StringReader reader = new StringReader(docTxt);
            InputSource is = new InputSource(reader);
            doc = db.parse(is);
        } catch (javax.xml.parsers.ParserConfigurationException pe) {
            log("Exception in parseXmlString1 ");
            pe.printStackTrace();
        } catch (Exception ex) {
            log("Exception in parseXmlString2" + ex.getMessage());
            ex.printStackTrace();
        }
        return doc;
    }


    private String addXmlRootElement(String rootElementTag, String xmlDoc) {


        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("<?xml version=\"1.0\"?>" + "\n");
        strBuffer.append("<tt:" + rootElementTag + "\n");
        strBuffer.append("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\"\n" +
                "xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"\n" +
                "xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"\n" +
                "xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"\n" +
                "xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"\n" +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                // "xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket\">\n\n" );
                "xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">\n\n");
        if (xmlDoc != null) {
            strBuffer.append(xmlDoc);
        }
        strBuffer.append("</tt:" + rootElementTag + ">");

        return strBuffer.toString();

    }


    private String[] getXmlAttNames(Document doc)
            throws org.xml.sax.SAXException {

        java.util.Vector attVector = new Vector();

        NodeList attNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "attrNames");
        if (attNodeList == null) {

            //return empty array
            return new String[0];

        }


        Node attNode = attNodeList.item(0);
        if (attNode == null) {

            return new String[0];
        }



        // Get all the elements contained by the key
        NodeList itemNodeList = attNode.getChildNodes();
        Node itemNode;

        for (int k = 0; k < itemNodeList.getLength(); k++) {
            itemNode = itemNodeList.item(k);
            if (itemNode.getNodeType() != Node.ELEMENT_NODE) continue;
            //String keyChildNodeName = keyChildNode.getNodeName();
            String childNodeValue = (itemNode.getFirstChild()).getNodeValue();
            attVector.addElement(childNodeValue);
        }

        String[] attrNames = (String[]) attVector.toArray(new String[0]);

        return attrNames;

    }


    // You might also consider using WebLogic's log service
    private void log(String s) {
        Logger.log(s);
    }


    // For the current phase of implementation. All of the general xml exceptions
    // are constructed either as IllegalAgrumentException or as an UnsupportedOperationException.
    private String makeXmlException(String rootElementTag, String xmlExceptionName, String message) {
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("<?xml version=\"1.0\"?>" + "\n");
        strBuffer.append("<tt:" + rootElementTag + "\n");
        strBuffer.append("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\"\n" +
                "xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"\n" +
                "xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"\n" +
                "xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"\n" +
                "xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"\n" +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                // "xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket\">\n\n" );
                "xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">\n\n");
        strBuffer.append("<tt:");
        strBuffer.append(xmlExceptionName + ">\n");
        if (message == null) message = "";
        strBuffer.append("<co:message>");
        strBuffer.append(message);
        strBuffer.append("</co:message>\n");
        strBuffer.append("</tt:");
        strBuffer.append(xmlExceptionName + ">\n");
        strBuffer.append("</tt:" + rootElementTag + ">");

        return strBuffer.toString();
    }

    private String makeXmlException(String rootElementTag, String xmlExceptionName, String message, TroubleTicketKey key) {
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("<?xml version=\"1.0\"?>" + "\n");
        strBuffer.append("<tt:" + rootElementTag + "\n");
        strBuffer.append("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\"\n" +
                "xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"\n" +
                "xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"\n" +
                "xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"\n" +
                "xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"\n" +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                //"xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket\">\n\n" );
                "xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">\n\n");
        strBuffer.append("<tt:");
        strBuffer.append(xmlExceptionName + ">\n");
        if (message == null) message = "";

        strBuffer.append("<co:message>");
        strBuffer.append(message);
        strBuffer.append("</co:message>\n");
        strBuffer.append("<co:managedEntityKey xsi:type=\"tt:TroubleTicketKey\">\n");
        strBuffer.append(getApplicationContextInfo(key));
        strBuffer.append("</co:managedEntityKey>" + "\n");
        strBuffer.append("</tt:");
        strBuffer.append(xmlExceptionName + ">\n");
        strBuffer.append("</tt:" + rootElementTag + ">");

        return strBuffer.toString();
    }


    private JVTTroubleTicketSession getJVTRemoteInterface() {

        //Note: since this is a stateless Session bean, we must do a lookup/create
        //of the JVTSession for each invocation

        JVTTroubleTicketSession jvtSession = null;
        JVTTroubleTicketSessionHome jvtSessionHome = null;

        jvtSessionHome = Finder.getInstance().lookupJVTSessionHome();

        if (jvtSessionHome != null) {
            try {
                jvtSession = jvtSessionHome.create();
            } catch (RemoteException rex) {
                log("XVTSessionBean.ejbCreate: RemoteException caught on create of JVT SessionHome");
                rex.printStackTrace();
            } catch (CreateException cex) {
                log("XVTSessionBean.ejbCreate: CreateException caught on create of JVT SessionHome");
                cex.printStackTrace();
            }

            log("XVTSessionBean.ejbCreate: JVTSession created OK.");
        }

        return jvtSession;

    }

    private String getApplicationContextInfo(TroubleTicketKey key) {

        StringBuffer strBuffer = new StringBuffer();
        ApplicationContext applicationContext = key.getApplicationContext();


        if (applicationContext != null) {
            strBuffer.append("<co:applicationContext>\n");

            String fact = applicationContext.getFactoryClass();
            if (fact != null)
                strBuffer.append("<co:factoryClass>" + fact + "</co:factoryClass>\n");
            //else
            //System.out.println("\n\n!!--The FactoryClass is null!!");
            String url = applicationContext.getURL();
            if (url != null)
                strBuffer.append("<co:url>" + url + "</co:url>\n");
            //else
            //System.out.println("\n\n!!--The URL is null!!");




            java.util.Map props = applicationContext.getSystemProperties();
            if (props != null) {
                Object currValue = null;
                Integer currKey = null;

                strBuffer.append("<co:systemProperties>\n");

                for (java.util.Iterator iter = props.keySet().iterator(); iter.hasNext();) {
                    currKey = (Integer) iter.next();
                    currValue = props.get(currKey);
                    strBuffer.append("<co:property>");

                    strBuffer.append("<co:name>" + "</co:name>\n");
                    strBuffer.append("<co:value>" + "</co:value>\n");
                    strBuffer.append("</co:property>\n");

                }
                strBuffer.append("</co:systemProperties>\n");
            } else
                strBuffer.append("<co:systemProperties/>\n");


            strBuffer.append("</co:applicationContext>\n");
        }


        String appDn = key.getApplicationDN();
        if (appDn != null)
            strBuffer.append("<co:applicationDN>" + appDn + "</co:applicationDN>\n");

        strBuffer.append("<co:type>" + "tt:TroubleTicketValue" + "</co:type>\n");
        strBuffer.append("<tt:primaryKey>" + key.getPrimaryKey() + "</tt:primaryKey>\n");

        return strBuffer.toString();
    }

}   // End of class

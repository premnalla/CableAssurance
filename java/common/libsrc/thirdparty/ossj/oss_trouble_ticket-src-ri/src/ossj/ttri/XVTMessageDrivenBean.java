package ossj.ttri;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.ejb.CreateException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.Properties;

/**
 * XVTMessageDrivenBean Implementation Class
 *
 * XVTMessageDrivenBean is a message driven bean that will be used to
 * receive XML Message requests.  The XML instance and schema for each
 * type of Request/Response/Error Message is detailed in OSS/J TT spec.
 * Each type of message has been modelled to mimic the methods from
 * the TroubleTicketSession interface. Therefore all functionality
 * that can be done directly through the TroubleTicketSession
 * interface, can also be done through messaging.
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class XVTMessageDrivenBean implements javax.ejb.MessageDrivenBean,
        javax.jms.MessageListener {
    private static final boolean VERBOSE = true;

    private javax.ejb.MessageDrivenContext context;
    private javax.xml.parsers.DocumentBuilder docBuilder;
    private JMSReplySender replySender;
    private String xvtStringReply;

    XVTTroubleTicketSession XVTTTSessionIF;

    /** Creates new XmlMessageDrivenBean */
    public XVTMessageDrivenBean() {
    }

    //====================================================

    public void setMessageDrivenContext(final javax.ejb.MessageDrivenContext messageDrivenContext)
            throws javax.ejb.EJBException {
        context = messageDrivenContext;
    }

    //====================================================

    public void ejbCreate()
            throws javax.ejb.CreateException {

        log("XVTMessageDrivenBean:ejbCreate");

        //create the initial context for lookups
        InitialContext initCtx = null;
        try {
            initCtx = new InitialContext();
        } catch (NamingException nex) {
            log("XVTMessageDrivenBean:ejbCreate:  Naming exception caught while creating InitialContext");
            nex.printStackTrace();
            throw new javax.ejb.CreateException();
        }

        //TODO: use Sun lookup/finder service
        //lookup the XVT Session Bean Home
        XVTTroubleTicketSessionHome ttXVTSessionHome = null;

        try {
            ttXVTSessionHome = (XVTTroubleTicketSessionHome) initCtx.lookup("java:comp/env/ejb/XVTHome");
        } catch (NamingException nex2) {
            log("XVTMessageDrivenBean:ejbCreate:  Naming exception caught on lookup of oss.XVTTroubleTicketSessionHome");
            nex2.printStackTrace();
        }
        log("TTSession:ejbCreate:  XVTTroubleTicketHome found OK");


        try {
            // Create an XVTSessionBean via its Home
            // and instantiate the JMSReplySender member
            this.XVTTTSessionIF = ttXVTSessionHome.create();
            replySender = JMSReplySender.getInstance();
            xvtStringReply = null;
        } catch (CreateException cex) {
            log("CreateException caught in XVTMessageDrivenBean: on create XVTSessionBean");
            cex.printStackTrace();
            throw cex;
        } catch (RemoteException rex) {
            log("RemoteException caught in XVTMessageDrivenBean: on create XVTSessionBean");
            rex.printStackTrace();
            throw new CreateException();
        }
        log("XVTMessageDrivenBean: created XVTSessionBean OK");

    }

    //====================================================

    public void ejbRemove()
            throws javax.ejb.EJBException {

        XVTTTSessionIF = null;
        replySender = null;
    }

    //====================================================

    public void onMessage(final javax.jms.Message message) {

        String rootName = null;
        Document doc = null;
        javax.jms.Destination replyTo = null;
        String errorMessage = null;

        //String messageTypeProp = null; //REQUEST, EXCEPTION, RESPONSE
        String messageNameProp = null; //From Root Name...
        String requestSenderIdProp = null; //OSS_REQUEST_SENDER_ID

        //VP
        XmlResponseIterator xmlResIter = null;
        //end VP
        try {
            Logger.log("XVTMessageDrivenBean:onMessage - msg received!");

            // As a first step, check the type of the received message
            if (!(message instanceof javax.jms.TextMessage)) {

                //> Need to find the type of xml exception to handle this case
                //  since it is not defined in the schema.
                //  At the current phase, a tentatvie general xml exception is
                //  being defined and used.
                String generalXmlEx = makeXmlException("GeneralTroubleTicketException", "IllegalArgumentException",
                        "XVTMessageDrivenBean: Illegal message type received - not a javax.jms.TextMessage ");
                throw new javax.oss.IllegalArgumentException(generalXmlEx);
            }

            // Get the text message
            String xmlString = ((javax.jms.TextMessage) message).getText();
            //get the REQUEST_SENDER_ID property + DESTINATION_TYPE
            requestSenderIdProp = message.getStringProperty("REQUEST_SENDER_ID");
            // Get the replyTo destination
            replyTo = message.getJMSReplyTo();
            Logger.log("Destination name: " + replyTo.toString());
            //Logger.log ("Destination class name: " + (replyTo.getClass()).getName() );
            try {
                // Convert the received message into an xml doc and find the name
                // of the root element to be able to direct the message to the
                // respective XVT Session method
                doc = parseXmlString(xmlString);
                Element rootElement = doc.getDocumentElement();
                rootName = rootElement.getTagName();
                messageNameProp = rootName;
                Logger.log("XVT MDB rootName: " + rootName);
            }
                    //> A general xml-based exception is needed to convert the following
                    //  exceptions to xml. A tentative xml general exception is being used.
            catch (org.xml.sax.SAXException saxEx) {
                String generalXmlEx = makeXmlException("GeneralTroubleTicketException", "IllegalArgumentException",
                        saxEx.getMessage());
                throw new javax.oss.IllegalArgumentException(generalXmlEx);
            } catch (java.io.IOException ioEx) {
                String generalXmlEx = makeXmlException("GeneralTroubleTicketException", "IllegalArgumentException",
                        ioEx.getMessage());
                throw new javax.oss.IllegalArgumentException(generalXmlEx);
            } catch (java.lang.Exception ex) {
                String generalXmlEx = makeXmlException("GeneralTroubleTicketException", "IllegalArgumentException",
                        ex.getMessage());
                throw new javax.oss.IllegalArgumentException(generalXmlEx);
            }

            // The parsing of the document is successful, hence the request
            // will be routed to the respective method in the underlying XVT Session bean

            if (rootName.equals("tt:createTroubleTicketByValueRequest")) {
                Logger.log("\n  MDB--> XVT: Create TT By Value XML Request");
                xvtStringReply = XVTTTSessionIF.createTroubleTicketByValue(xmlString);

                Logger.log("\n   *****  XVTMessageDrivenBean: Create TT By Value XML Response   *****  \n");
                Logger.log(xvtStringReply);
                Logger.log("\n \n");
                replySender.sendXmlReply(replyTo, xvtStringReply, "RESPONSE",
                        messageNameProp, requestSenderIdProp);

            } else if (rootName.equals("tt:escalateTroubleTicketByKeyRequest")) {
                xvtStringReply = XVTTTSessionIF.escalateTroubleTicketByKey(xmlString);

                Logger.log("\n   *****  XVTMessageDrivenBean: Get TT By Key XML Response   *****  \n");
                Logger.log(xvtStringReply);
                Logger.log("\n \n");
                replySender.sendXmlReply(replyTo, xvtStringReply, "RESPONSE",
                        messageNameProp, requestSenderIdProp);
            } else if (rootName.equals("tt:getTroubleTicketByKeyRequest")) {
                xvtStringReply = XVTTTSessionIF.getTroubleTicketByKey(xmlString);

                Logger.log("\n   *****  XVTMessageDrivenBean: Get TT By Key XML Response   *****  \n");
                Logger.log(xvtStringReply);
                Logger.log("\n \n");
                replySender.sendXmlReply(replyTo, xvtStringReply, "RESPONSE",
                        messageNameProp, requestSenderIdProp);
            } else if (rootName.equals("tt:tryEscalateTroubleTicketsByKeysRequest")) {
                xvtStringReply = XVTTTSessionIF.tryEscalateTroubleTicketsByKeys(xmlString);

                Logger.log("\n   *****  XVTMessageDrivenBean: Get TT By Key XML Response   *****  \n");
                Logger.log(xvtStringReply);
                Logger.log("\n \n");
                replySender.sendXmlReply(replyTo, xvtStringReply, "RESPONSE",
                        messageNameProp, requestSenderIdProp);
            } else if (rootName.equals("tt:getTroubleTicketsByKeysRequest")) {
                xvtStringReply = XVTTTSessionIF.getTroubleTicketsByKeys(xmlString);

                Logger.log("\n   *****  XVTMessageDrivenBean: Get TT By Key XML Response   *****  \n");
                Logger.log(xvtStringReply);
                Logger.log("\n \n");
                replySender.sendXmlReply(replyTo, xvtStringReply, "RESPONSE",
                        messageNameProp, requestSenderIdProp);
            } else if (rootName.equals("tt:trySetTroubleTicketsByValuesRequest")) {
                xvtStringReply = XVTTTSessionIF.trySetTroubleTicketsByValues(xmlString);

                Logger.log("\n   *****  XVTMessageDrivenBean: Get TT By Key XML Response   *****  \n");
                Logger.log(xvtStringReply);
                Logger.log("\n \n");
                replySender.sendXmlReply(replyTo, xvtStringReply, "RESPONSE",
                        messageNameProp, requestSenderIdProp);
            } else if (rootName.equals("tt:cancelTroubleTicketByKeyRequest")) {
                xvtStringReply = XVTTTSessionIF.cancelTroubleTicketByKey(xmlString);

                Logger.log("\n   *****  XVTMessageDrivenBean: Get TT By Key XML Response   *****  \n");
                Logger.log(xvtStringReply);
                Logger.log("\n \n");
                replySender.sendXmlReply(replyTo, xvtStringReply, "RESPONSE",
                        messageNameProp, requestSenderIdProp);
            } else if (rootName.equals("tt:closeTroubleTicketByKeyRequest")) {
                xvtStringReply = XVTTTSessionIF.closeTroubleTicketByKey(xmlString);

                Logger.log("\n   *****  XVTMessageDrivenBean: Get TT By Key XML Response   *****  \n");
                Logger.log(xvtStringReply);
                Logger.log("\n \n");
                replySender.sendXmlReply(replyTo, xvtStringReply, "RESPONSE",
                        messageNameProp, requestSenderIdProp);
            } else if (rootName.equals("tt:trySetTroubleTicketsByKeysRequest")) {
                xvtStringReply = XVTTTSessionIF.trySetTroubleTicketsByKeys(xmlString);

                Logger.log("\n   *****  XVTMessageDrivenBean: trySetTroubleTicketsByKeysRequest XML Response   *****  \n");
                Logger.log(xvtStringReply);
                Logger.log("\n \n");
                replySender.sendXmlReply(replyTo, xvtStringReply, "RESPONSE",
                        messageNameProp, requestSenderIdProp);
            } else if (rootName.equals("tt:tryCreateTroubleTicketsByValuesRequest")) {
                Logger.log("\n   *****  XVTMessageDrivenBean: Request tryCreateTroubleTicketsByValuesRequest   *****  \n");

                xvtStringReply = XVTTTSessionIF.tryCreateTroubleTicketsByValues(xmlString);

                Logger.log("\n   *****  XVTMessageDrivenBean: tryCreateTroubleTicketsByValuesRequest XML Response   *****  \n");
                Logger.log(xvtStringReply);
                Logger.log("\n \n");
                replySender.sendXmlReply(replyTo, xvtStringReply, "RESPONSE",
                        messageNameProp, requestSenderIdProp);
            } else if (rootName.equals("tt:setTroubleTicketByValueRequest")) {
                xvtStringReply = XVTTTSessionIF.setTroubleTicketByValue(xmlString);

                Logger.log("\n   *****  XVTMessageDrivenBean: Set TT By Value XML Response   *****  \n");
                Logger.log(xvtStringReply);
                Logger.log("\n \n");
                replySender.sendXmlReply(replyTo, xvtStringReply, "RESPONSE",
                        messageNameProp, requestSenderIdProp);
            } else if (rootName.equals("tt:closeTroubleTicketByKeyRequest")) {
                xvtStringReply = XVTTTSessionIF.closeTroubleTicketByKey(xmlString);

                Logger.log("\n   *****  XVTMessageDrivenBean: Close TT By Key XML Response   *****  \n");
                Logger.log(xvtStringReply);
                Logger.log("\n \n");
                replySender.sendXmlReply(replyTo, xvtStringReply, "RESPONSE",
                        messageNameProp, requestSenderIdProp);
            } else if (rootName.equals("tt:tryCloseTroubleTicketsByKeysRequest")) {
                xvtStringReply = XVTTTSessionIF.tryCloseTroubleTicketsByKeys(xmlString);

                Logger.log("\n   *****  XVTMessageDrivenBean: Close TT By Key XML Response   *****  \n");
                Logger.log(xvtStringReply);
                Logger.log("\n \n");
                replySender.sendXmlReply(replyTo, xvtStringReply, "RESPONSE",
                        messageNameProp, requestSenderIdProp);
            } else if (rootName.equals("tt:tryCancelTroubleTicketsByKeysRequest")) {
                xvtStringReply = XVTTTSessionIF.tryCancelTroubleTicketsByKeys(xmlString);

                Logger.log("\n   *****  XVTMessageDrivenBean: Close TT By Key XML Response   *****  \n");
                Logger.log(xvtStringReply);
                Logger.log("\n \n");
                replySender.sendXmlReply(replyTo, xvtStringReply, "RESPONSE",
                        messageNameProp, requestSenderIdProp);
            } else if (rootName.equals("tt:tryEscalateTroubleTicketsByTemplateRequestXXX")) {
                //VP XmlResponseIterator
                xmlResIter = XVTTTSessionIF.tryEscalateTroubleTicketsByTemplate(xmlString);

                // At this stage, the MDB must extract the value of
                // the "HowMany" element in the passed-in request
                // in order to communicate with the XmlResponseIterator
                NodeList HowManyNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "howMany");
                Node howManyNode = HowManyNodeList.item(0);
                String howManyString = (howManyNode.getFirstChild()).getNodeValue();
                int howMany = Integer.parseInt(howManyString);


                //Logger.log ("\n   *****  XVTMessageDrivenBean: TryCloseTTsBytemplate XML Response   *****  \n");
                //int howMany = 2;
                boolean more = true;
                //java.util.Vector resultVector = new java.util.Vector();

                while (more) {
                    String xmlTryCloseResult = xmlResIter.getNext(howMany);

                    // Send the reply in all cases back to the listening XML/JMS Client.
                    replySender.sendXmlReply(replyTo, xmlTryCloseResult, "RESPONSE",
                            messageNameProp, requestSenderIdProp);

                    // Need to parse the result and check the EndOfReply flag
                    // to determine when to stop calling the xmlResponseIterator.
                    doc = parseXmlString(xmlTryCloseResult);

                    NodeList resultNodeList = null;
                    resultNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "endOfReply");

                    // Typically only one element
                    Node resultNode = resultNodeList.item(0);
                    String nodeValue = resultNode.getFirstChild().getNodeValue();

                    //if( nodeValue.equals("false") )
                    if (nodeValue.equals("true")) {
                        //resultVector.addElement( xmlTryCloseResult );
                        break;
                    }

                }

                // The MDB now will remove the xmlResponseIterator stateful session bean
                // which in turn removes the underlying java-based bean
                //VP put it at the end
                //xmlResIter.remove();

            } else if (rootName.equals("tt:tryEscalateTroubleTicketsByTemplateRequest")) {
                Logger.log("$+Before Call to XVTTTSessionIF.tryEscalateTroubleTicketsByTemplate");
                //VP XmlResponseIteratorHome
                xmlResIter = XVTTTSessionIF.tryEscalateTroubleTicketsByTemplate(xmlString);
                Logger.log("$+After Call to XVTTTSessionIF.tryEscalateTroubleTicketsByTemplates");
                // At this stage, the MDB must extract the value of
                // the "HowMany" element in the passed-in request
                // in order to communicate with the XmlResponseIterator
                Logger.log("Before parsing howmany");
                NodeList HowManyNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "howMany");
                Logger.log("HowManyNodeList.getLength(): " + HowManyNodeList.getLength());
                Node howManyNode = HowManyNodeList.item(0);
                Logger.log("HowMany Value: " + (howManyNode.getFirstChild()).getNodeValue());
                String howManyString = (howManyNode.getFirstChild()).getNodeValue();
                int howMany = Integer.parseInt(howManyString);
                Logger.log("After parsing howmany: " + howMany);

                //Logger.log ("\n   *****  XVTMessageDrivenBean: TryCloseTTsBytemplate XML Response   *****  \n");
                //int howMany = 2;
                boolean more = true;
                //java.util.Vector resultVector = new java.util.Vector();

                while (more) {
                    Logger.log("Before call to xmlResIter.getNext");
                    String xmlTryCloseResult = xmlResIter.getNext(howMany);
                    Logger.log("After call to xmlResIter.getNext");
                    // Send the reply in all cases back to the listening XML/JMS Client.
                    replySender.sendXmlReply(replyTo, xmlTryCloseResult, "RESPONSE",
                            messageNameProp, requestSenderIdProp);
                    Logger.log("After call to sendXmlReply");
                    // Need to parse the result and check the EndOfReply flag
                    // to determine when to stop calling the xmlResponseIterator.
                    doc = parseXmlString(xmlTryCloseResult);
                    Logger.log("After call to parseXmlString");
                    NodeList resultNodeList = null;
                    resultNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "endOfReply");

                    // Typically only one element
                    Node resultNode = resultNodeList.item(0);
                    String nodeValue = resultNode.getFirstChild().getNodeValue();
                    Logger.log("After call to nodeValue" + nodeValue);
                    //if( nodeValue.equals("false") )
                    if (nodeValue.equals("true")) {
                        //resultVector.addElement( xmlTryCloseResult );
                        Logger.log("break");
                        break;
                    }

                }

                // The MDB now will remove the xmlResponseIterator stateful session bean
                // which in turn removes the underlying java-based bean
                //VP put it at the end
                //xmlResIter.remove();

            } else if (rootName.equals("tt:tryEscalateTroubleTicketsByTemplatesRequest")) {
                Logger.log("$+Before Call to XVTTTSessionIF.tryEscalateTroubleTicketsByTemplates");
                //VP XmlResponseIterator
                xmlResIter = XVTTTSessionIF.tryEscalateTroubleTicketsByTemplates(xmlString);
                Logger.log("$+After Call to XVTTTSessionIF.tryEscalateTroubleTicketsByTemplates");
                // At this stage, the MDB must extract the value of
                // the "HowMany" element in the passed-in request
                // in order to communicate with the XmlResponseIterator
                NodeList HowManyNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "howMany");
                Node howManyNode = HowManyNodeList.item(0);
                String howManyString = (howManyNode.getFirstChild()).getNodeValue();
                int howMany = Integer.parseInt(howManyString);
                Logger.log("After parsing howmany: " + howMany);

                //Logger.log ("\n   *****  XVTMessageDrivenBean: TryCloseTTsBytemplate XML Response   *****  \n");
                //int howMany = 2;
                boolean more = true;
                //java.util.Vector resultVector = new java.util.Vector();

                while (more) {
                    Logger.log("Before call to xmlResIter.getNext");
                    String xmlTryCloseResult = xmlResIter.getNext(howMany);
                    Logger.log("After call to xmlResIter.getNext");
                    // Send the reply in all cases back to the listening XML/JMS Client.
                    replySender.sendXmlReply(replyTo, xmlTryCloseResult, "RESPONSE",
                            messageNameProp, requestSenderIdProp);
                    Logger.log("After call to sendXmlReply");
                    // Need to parse the result and check the EndOfReply flag
                    // to determine when to stop calling the xmlResponseIterator.
                    doc = parseXmlString(xmlTryCloseResult);
                    Logger.log("After call to parseXmlString");
                    NodeList resultNodeList = null;
                    resultNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "endOfReply");

                    // Typically only one element
                    Node resultNode = resultNodeList.item(0);
                    String nodeValue = resultNode.getFirstChild().getNodeValue();
                    Logger.log("After call to nodeValue" + nodeValue);
                    //if( nodeValue.equals("false") )
                    if (nodeValue.equals("true")) {
                        //resultVector.addElement( xmlTryCloseResult );
                        Logger.log("break");
                        break;
                    }

                }

                // The MDB now will remove the xmlResponseIterator stateful session bean
                // which in turn removes the underlying java-based bean
                //VP put it at the end
                //xmlResIter.remove();

            } else if (rootName.equals("tt:tryCloseTroubleTicketsByTemplateRequest")) {
                //VP XmlResponseIterator
                xmlResIter = XVTTTSessionIF.tryCloseTroubleTicketsByTemplate(xmlString);

                // At this stage, the MDB must extract the value of
                // the "HowMany" element in the passed-in request
                // in order to communicate with the XmlResponseIterator
                NodeList HowManyNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "howMany");
                Node howManyNode = HowManyNodeList.item(0);
                String howManyString = (howManyNode.getFirstChild()).getNodeValue();
                int howMany = Integer.parseInt(howManyString);


                //Logger.log ("\n   *****  XVTMessageDrivenBean: TryCloseTTsBytemplate XML Response   *****  \n");
                //int howMany = 2;
                boolean more = true;
                //java.util.Vector resultVector = new java.util.Vector();

                while (more) {
                    String xmlTryCloseResult = xmlResIter.getNext(howMany);

                    // Send the reply in all cases back to the listening XML/JMS Client.
                    replySender.sendXmlReply(replyTo, xmlTryCloseResult, "RESPONSE",
                            messageNameProp, requestSenderIdProp);

                    // Need to parse the result and check the EndOfReply flag
                    // to determine when to stop calling the xmlResponseIterator.
                    doc = parseXmlString(xmlTryCloseResult);

                    NodeList resultNodeList = null;
                    resultNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "endOfReply");

                    // Typically only one element
                    Node resultNode = resultNodeList.item(0);
                    String nodeValue = resultNode.getFirstChild().getNodeValue();

                    //if( nodeValue.equals("false") )
                    if (nodeValue.equals("true")) {
                        //resultVector.addElement( xmlTryCloseResult );
                        break;
                    }

                }

                // The MDB now will remove the xmlResponseIterator stateful session bean
                // which in turn removes the underlying java-based bean
                //VP put it at the end
                //xmlResIter.remove();

            } else if (rootName.equals("tt:tryCloseTroubleTicketsByTemplatesRequest")) {
                //VP XmlResponseIterator
                xmlResIter = XVTTTSessionIF.tryCloseTroubleTicketsByTemplates(xmlString);

                // At this stage, the MDB must extract the value of
                // the "HowMany" element in the passed-in request
                // in order to communicate with the XmlResponseIterator
                NodeList HowManyNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "howMany");
                Node howManyNode = HowManyNodeList.item(0);
                String howManyString = (howManyNode.getFirstChild()).getNodeValue();
                int howMany = Integer.parseInt(howManyString);


                //Logger.log ("\n   *****  XVTMessageDrivenBean: TryCloseTTsBytemplate XML Response   *****  \n");
                //int howMany = 2;
                boolean more = true;
                //java.util.Vector resultVector = new java.util.Vector();

                while (more) {
                    String xmlTryCloseResult = xmlResIter.getNext(howMany);

                    // Send the reply in all cases back to the listening XML/JMS Client.
                    replySender.sendXmlReply(replyTo, xmlTryCloseResult, "RESPONSE",
                            messageNameProp, requestSenderIdProp);

                    // Need to parse the result and check the EndOfReply flag
                    // to determine when to stop calling the xmlResponseIterator.
                    doc = parseXmlString(xmlTryCloseResult);

                    NodeList resultNodeList = null;
                    resultNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "endOfReply");

                    // Typically only one element
                    Node resultNode = resultNodeList.item(0);
                    String nodeValue = resultNode.getFirstChild().getNodeValue();

                    //if( nodeValue.equals("false") )
                    if (nodeValue.equals("true")) {
                        //resultVector.addElement( xmlTryCloseResult );
                        break;
                    }

                }

                // The MDB now will remove the xmlResponseIterator stateful session bean
                // which in turn removes the underlying java-based bean
                //VP put it at the end
                //xmlResIter.remove();

            } else if (rootName.equals("tt:tryCancelTroubleTicketsByTemplateRequest")) {
                //VP XmlResponseIterator
                xmlResIter = XVTTTSessionIF.tryCancelTroubleTicketsByTemplate(xmlString);

                // At this stage, the MDB must extract the value of
                // the "HowMany" element in the passed-in request
                // in order to communicate with the XmlResponseIterator
                NodeList HowManyNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "howMany");
                Node howManyNode = HowManyNodeList.item(0);
                String howManyString = (howManyNode.getFirstChild()).getNodeValue();
                int howMany = Integer.parseInt(howManyString);


                //Logger.log ("\n   *****  XVTMessageDrivenBean: TryCloseTTsBytemplate XML Response   *****  \n");
                //int howMany = 2;
                boolean more = true;
                //java.util.Vector resultVector = new java.util.Vector();

                while (more) {
                    String xmlTryCloseResult = xmlResIter.getNext(howMany);

                    // Send the reply in all cases back to the listening XML/JMS Client.
                    replySender.sendXmlReply(replyTo, xmlTryCloseResult, "RESPONSE",
                            messageNameProp, requestSenderIdProp);

                    // Need to parse the result and check the EndOfReply flag
                    // to determine when to stop calling the xmlResponseIterator.
                    doc = parseXmlString(xmlTryCloseResult);

                    NodeList resultNodeList = null;
                    resultNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "endOfReply");

                    // Typically only one element
                    Node resultNode = resultNodeList.item(0);
                    String nodeValue = resultNode.getFirstChild().getNodeValue();

                    //if( nodeValue.equals("false") )
                    if (nodeValue.equals("true")) {
                        //resultVector.addElement( xmlTryCloseResult );
                        break;
                    }

                }

                // The MDB now will remove the xmlResponseIterator stateful session bean
                // which in turn removes the underlying java-based bean
                //VP put it at the end
                //xmlResIter.remove();

            } else if (rootName.equals("tt:tryCancelTroubleTicketsByTemplatesRequest")) {
                //VP XmlResponseIterator
                xmlResIter = XVTTTSessionIF.tryCancelTroubleTicketsByTemplates(xmlString);

                // At this stage, the MDB must extract the value of
                // the "HowMany" element in the passed-in request
                // in order to communicate with the XmlResponseIterator
                NodeList HowManyNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "howMany");
                Node howManyNode = HowManyNodeList.item(0);
                String howManyString = (howManyNode.getFirstChild()).getNodeValue();
                int howMany = Integer.parseInt(howManyString);


                //Logger.log ("\n   *****  XVTMessageDrivenBean: TryCloseTTsBytemplate XML Response   *****  \n");
                //int howMany = 2;
                boolean more = true;
                //java.util.Vector resultVector = new java.util.Vector();

                while (more) {
                    String xmlTryCloseResult = xmlResIter.getNext(howMany);

                    // Send the reply in all cases back to the listening XML/JMS Client.
                    replySender.sendXmlReply(replyTo, xmlTryCloseResult, "RESPONSE",
                            messageNameProp, requestSenderIdProp);

                    // Need to parse the result and check the EndOfReply flag
                    // to determine when to stop calling the xmlResponseIterator.
                    doc = parseXmlString(xmlTryCloseResult);

                    NodeList resultNodeList = null;
                    resultNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "endOfReply");

                    // Typically only one element
                    Node resultNode = resultNodeList.item(0);
                    String nodeValue = resultNode.getFirstChild().getNodeValue();

                    //if( nodeValue.equals("false") )
                    if (nodeValue.equals("true")) {
                        //resultVector.addElement( xmlTryCloseResult );
                        break;
                    }

                }

                // The MDB now will remove the xmlResponseIterator stateful session bean
                // which in turn removes the underlying java-based bean
                //VP put it at the end
                //xmlResIter.remove();

            } else if (rootName.equals("tt:queryTroubleTicketsRequest")) {
                log("MDB ---> tt:queryTroubleTicketsRequest");
                //VP XmlResponseIterator
                xmlResIter = XVTTTSessionIF.queryTroubleTickets(xmlString);

                // At this stage, the MDB must extract the value of
                // the "HowMany" element in the passed-in request
                // in order to communicate with the XmlResponseIterator
                NodeList HowManyNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "howMany");
                Node howManyNode = HowManyNodeList.item(0);
                String howManyString = (howManyNode.getFirstChild()).getNodeValue();
                int howMany = Integer.parseInt(howManyString);

                boolean more = true;

                while (more) {

                    String xmlQueryAllResult = xmlResIter.getNext(howMany);

                    // Send the reply in all cases back to the listening XML/JMS Client.
                    replySender.sendXmlReply(replyTo, xmlQueryAllResult, "RESPONSE",
                            messageNameProp, requestSenderIdProp);

                    // Need to parse the result and check the EndOfReply flag
                    // to determine when to stop calling the xmlResponseIterator.
                    doc = parseXmlString(xmlQueryAllResult);

                    NodeList resultNodeList = null;
                    resultNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "endOfReply");

                    // Typically only one element
                    Node resultNode = resultNodeList.item(0);
                    String nodeValue = resultNode.getFirstChild().getNodeValue();

                    //if( nodeValue.equals("false") )
                    if (nodeValue.equals("true")) {
                        //resultVector.addElement( xmlTryCloseResult );
                        break;
                    }

                }
                // The MDB now will remove the xmlResponseIterator stateful session bean
                // which in turn removes the underlying java-based bean
                //VP put it at the end
                //xmlResIter.remove();


            } else if (rootName.equals("tt:getTroubleTicketsByTemplatesRequest")) {
                //VP XmlResponseIterator
                xmlResIter = XVTTTSessionIF.getTroubleTicketsByTemplates(xmlString);

                // At this stage, the MDB must extract the value of
                // the "HowMany" element in the passed-in request
                // in order to communicate with the XmlResponseIterator
                NodeList HowManyNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "howMany");
                Node howManyNode = HowManyNodeList.item(0);
                String howManyString = (howManyNode.getFirstChild()).getNodeValue();
                int howMany = Integer.parseInt(howManyString);

                boolean more = true;

                while (more) {
                    String xmlQueryAllResult = xmlResIter.getNext(howMany);

                    // Send the reply in all cases back to the listening XML/JMS Client.
                    replySender.sendXmlReply(replyTo, xmlQueryAllResult, "RESPONSE",
                            messageNameProp, requestSenderIdProp);

                    // Need to parse the result and check the EndOfReply flag
                    // to determine when to stop calling the xmlResponseIterator.
                    doc = parseXmlString(xmlQueryAllResult);

                    NodeList resultNodeList = null;
                    resultNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "endOfReply");

                    // Typically only one element
                    Node resultNode = resultNodeList.item(0);
                    String nodeValue = resultNode.getFirstChild().getNodeValue();

                    //if( nodeValue.equals("false") )
                    if (nodeValue.equals("true")) {
                        //resultVector.addElement( xmlTryCloseResult );
                        break;
                    }

                }
                // The MDB now will remove the xmlResponseIterator stateful session bean
                // which in turn removes the underlying java-based bean
                //VP put it at the end
                //xmlResIter.remove();


            } else if (rootName.equals("tt:getTroubleTicketsByTemplateRequest")) {
                //VP XmlResponseIterator
                xmlResIter = XVTTTSessionIF.getTroubleTicketsByTemplate(xmlString);

                // At this stage, the MDB must extract the value of
                // the "HowMany" element in the passed-in request
                // in order to communicate with the XmlResponseIterator
                NodeList HowManyNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "howMany");
                Node howManyNode = HowManyNodeList.item(0);
                String howManyString = (howManyNode.getFirstChild()).getNodeValue();
                int howMany = Integer.parseInt(howManyString);

                boolean more = true;

                while (more) {
                    String xmlQueryAllResult = xmlResIter.getNext(howMany);

                    // Send the reply in all cases back to the listening XML/JMS Client.
                    replySender.sendXmlReply(replyTo, xmlQueryAllResult, "RESPONSE",
                            messageNameProp, requestSenderIdProp);

                    // Need to parse the result and check the EndOfReply flag
                    // to determine when to stop calling the xmlResponseIterator.
                    doc = parseXmlString(xmlQueryAllResult);

                    NodeList resultNodeList = null;
                    resultNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "endOfReply");

                    // Typically only one element
                    Node resultNode = resultNodeList.item(0);
                    String nodeValue = resultNode.getFirstChild().getNodeValue();

                    //if( nodeValue.equals("false") )
                    if (nodeValue.equals("true")) {
                        //resultVector.addElement( xmlTryCloseResult );
                        break;
                    }

                }
                // The MDB now will remove the xmlResponseIterator stateful session bean
                // which in turn removes the underlying java-based bean
                //VP put it at the end
                //xmlResIter.remove();


            } else if (rootName.equals("tt:trySetTroubleTicketsByTemplateRequest")) {
                //VP XmlResponseIterator
                xmlResIter = XVTTTSessionIF.trySetTroubleTicketsByTemplate(xmlString);

                // At this stage, the MDB must extract the value of
                // the "HowMany" element in the passed-in request
                // in order to communicate with the XmlResponseIterator
                NodeList HowManyNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "howMany");
                Node howManyNode = HowManyNodeList.item(0);
                String howManyString = (howManyNode.getFirstChild()).getNodeValue();
                int howMany = Integer.parseInt(howManyString);


                //Logger.log ("\n   *****  XVTMessageDrivenBean: TryCloseTTsBytemplate XML Response   *****  \n");
                //int howMany = 2;
                boolean more = true;
                //java.util.Vector resultVector = new java.util.Vector();

                while (more) {
                    String xmlTryCloseResult = xmlResIter.getNext(howMany);

                    // Send the reply in all cases back to the listening XML/JMS Client.
                    replySender.sendXmlReply(replyTo, xmlTryCloseResult, "RESPONSE",
                            messageNameProp, requestSenderIdProp);

                    // Need to parse the result and check the EndOfReply flag
                    // to determine when to stop calling the xmlResponseIterator.
                    doc = parseXmlString(xmlTryCloseResult);

                    NodeList resultNodeList = null;
                    resultNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "endOfReply");

                    // Typically only one element
                    Node resultNode = resultNodeList.item(0);
                    String nodeValue = resultNode.getFirstChild().getNodeValue();

                    //if( nodeValue.equals("false") )
                    if (nodeValue.equals("true")) {
                        //resultVector.addElement( xmlTryCloseResult );
                        break;
                    }

                }

                // The MDB now will remove the xmlResponseIterator stateful session bean
                // which in turn removes the underlying java-based bean
                //VP put it at the end
                //xmlResIter.remove();

            } else if (rootName.equals("tt:trySetTroubleTicketsByTemplatesRequest")) {
                //VP XmlResponseIterator
                xmlResIter = XVTTTSessionIF.trySetTroubleTicketsByTemplates(xmlString);

                // At this stage, the MDB must extract the value of
                // the "HowMany" element in the passed-in request
                // in order to communicate with the XmlResponseIterator
                NodeList HowManyNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "howMany");
                Node howManyNode = HowManyNodeList.item(0);
                String howManyString = (howManyNode.getFirstChild()).getNodeValue();
                int howMany = Integer.parseInt(howManyString);


                //Logger.log ("\n   *****  XVTMessageDrivenBean: TryCloseTTsBytemplate XML Response   *****  \n");
                //int howMany = 2;
                boolean more = true;
                //java.util.Vector resultVector = new java.util.Vector();

                while (more) {
                    String xmlTryCloseResult = xmlResIter.getNext(howMany);

                    // Send the reply in all cases back to the listening XML/JMS Client.
                    replySender.sendXmlReply(replyTo, xmlTryCloseResult, "RESPONSE",
                            messageNameProp, requestSenderIdProp);

                    // Need to parse the result and check the EndOfReply flag
                    // to determine when to stop calling the xmlResponseIterator.
                    doc = parseXmlString(xmlTryCloseResult);

                    NodeList resultNodeList = null;
                    resultNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common", "endOfReply");

                    // Typically only one element
                    Node resultNode = resultNodeList.item(0);
                    String nodeValue = resultNode.getFirstChild().getNodeValue();

                    //if( nodeValue.equals("false") )
                    if (nodeValue.equals("true")) {
                        //resultVector.addElement( xmlTryCloseResult );
                        break;
                    }

                }

                // The MDB now will remove the xmlResponseIterator stateful session bean
                // which in turn removes the underlying java-based bean
                //VP put it at the end
                //xmlResIter.remove();

            } else {
                errorMessage = makeXmlException("troubleTicketException", "illegalArgumentException",
                        "Unrecognized Request: " + rootName);
            }


        }
                // All of the following exceptions have embedded xml exceptions
                // as their messages. These xml messages are either being
                // thrown from the underlying XVT Session bean or from the above code.
                // Therefor, the catch blocks will forward it to the predefined
                // replyTo destination as is.
        catch (javax.oss.IllegalArgumentException iLLEx) {
            errorMessage = iLLEx.getMessage();
        } catch (javax.oss.UnsupportedOperationException unSuppEx) {
            errorMessage = unSuppEx.getMessage();
        } catch (javax.oss.SetException setEx) {
            errorMessage = setEx.getMessage();
        } catch (javax.ejb.DuplicateKeyException dupKeyEx) {
            errorMessage = dupKeyEx.getMessage();
        } catch (CreateException createEx) {
            errorMessage = createEx.getMessage();
        } catch (RemoteException remEx) {
            errorMessage = remEx.getMessage();
        } catch (javax.ejb.ObjectNotFoundException objNFEx) {
            errorMessage = objNFEx.getMessage();
        } catch (javax.jms.JMSException jmsEx) {
            errorMessage = jmsEx.getMessage();
        } catch (javax.oss.ResyncRequiredException rre) {

            Logger.log("---javax.oss.ResyncRequiredException in MDB----");
            errorMessage = rre.getLocalizedMessage();
            Logger.log("---Error Message is----");
            Logger.log(errorMessage);
            Logger.log("---END of Error Message----");

        } catch (org.xml.sax.SAXException saxEx) {
            errorMessage = makeXmlException("troubleTicketException", "remoteException",
                    saxEx.getMessage());

        } catch (java.io.IOException ioEx) {
            errorMessage = makeXmlException("troubleTicketException", "remoteException",
                    ioEx.getMessage());

        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            errorMessage = makeXmlException("troubleTicketException", "remoteException",
                    ex.getMessage());

        } finally {

            if (errorMessage != null) {
                //VP
                if (xmlResIter != null) {
                    // clean the staeful bean
                    try {
                        xmlResIter.remove();
                    } catch (RemoteException rex) {
                    } catch (javax.ejb.RemoveException rvex) {
                    }

                    xmlResIter = null;
                }
                //end VP

                try {
                    Logger.log("send Exception Message: " + errorMessage);
                    //Making corrections

                    int index = errorMessage.indexOf("Start server side stack trace:");
                    String newErrorMessage = null;

                    //VP test if index -1 => string not found then the message
                    if (index < 0) {
                        newErrorMessage = errorMessage;
                    } else {
                        newErrorMessage = errorMessage.substring(0, index);
                        Logger.log("ErrorMessage = " + newErrorMessage);
                    }
                    replySender.sendXmlReply(replyTo, newErrorMessage, "EXCEPTION",
                            messageNameProp, requestSenderIdProp);
                    //end VP
                } catch (Exception ex) {
                    log("XVTMessageDrivenBean: Unable to send the Error Message to the \"replyTo\" Destination\n" +
                            ex.toString());
                }
            }
        }

        //VP
        if (xmlResIter != null) {
            // clean the stateful bean
            try {
                xmlResIter.remove();
            } catch (RemoteException rex) {
            } catch (javax.ejb.RemoveException rvex) {
            }

        }
        //end VP
    }


    //====================================================

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
        strBuffer.append("<tt:message>");
        strBuffer.append(message);
        strBuffer.append("</tt:message>\n");
        strBuffer.append("</tt:");
        strBuffer.append(xmlExceptionName + ">\n");
        strBuffer.append("</tt:" + rootElementTag + ">");

        return strBuffer.toString();
    }

    //====================================================

    // You might also consider using WebLogic's log service
    private void log(String s) {
        if (VERBOSE) Logger.log(s);
    }

    //====================================================

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
            log("Exception in parseXmlString");
            pe.printStackTrace();
        } catch (Exception ex) {
            log("Exception in parseXmlString");
            ex.printStackTrace();
        }
        return doc;
    }

}



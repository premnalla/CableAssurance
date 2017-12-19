package ossj.ttri;

import javax.ejb.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.oss.Serializer;
import javax.oss.XmlSerializer;
import javax.oss.trouble.*;
import java.rmi.RemoteException;
import java.util.Properties;

/**
 * XmlResponseIteratorBean Implementation Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class XmlResponseIteratorBean implements SessionBean {

    private static final boolean VERBOSE = true;
    private SessionContext ctx;
    private InitialContext namingContext;
    //VP iterator are now simple java objects:
    //private Handle javaTTIteratorHandle;
    //private EJBObject javaTTIterator;
    private Object javaTTIterator;
    //end VP
    private int IteratorResponseSequence = 0;
    private String xmlResponseRootTag = null;
    private String[] desiredAttributeNames = null;


    private void log(String s) {
        Logger.log(s);
    }

    /**
     * This method is required by the EJB Specification.
     *
     *
     */
    public void ejbActivate() {
        //VP commented ??? don't know usage
        /*
          {
          log("XmlResponseIterator.ejbActivate called");
          javaTTIterator = this.javaTTIteratorHandle.getEJBObject();
          }
          catch( java.rmi.RemoteException remEx )
          {
          log("XmlResponseIterator.ejbActivate: Caught remote exception upon getting the handle for the underlying java iterator");
          }
        */
        log("XmlResponseIterator.ejbActivate");
        //end VP

    }

    /**
     * This method is required by the EJB Specification.
     *
     */
    public void ejbRemove() {
        log("XmlResponseIterator.ejbRemove called");
        try {
            remove();
        } catch (RemoteException rex) {
            log("XmlResponseIterator.ejbRemove RemoteException Failed");
        } catch (RemoveException remex) {
            log("XmlResponseIterator.ejbRemove RemoveException Failed");
        }
    }

    /**
     * This method is required by the EJB Specification.
     *
     *
     */
    public void ejbPassivate() {
        log("XmlResponseIterator.ejbPassivate called");
    }

    /**
     * Sets the session context.
     *
     * @param ctx               SessionContext Context for session
     */
    public void setSessionContext(SessionContext ctx) {
        log("XmlResponseIterator.setSessionContext called");
        this.ctx = ctx;
    }

    /**
     *
     */
    public void ejbCreate() throws CreateException {
        log("XmlResponseIterator.ejbCreate() called");


        try {
            namingContext = new InitialContext();
        } catch (NamingException nex) {
            log("XmlResponseIterator:ejbCreate:  Naming exception caught while creating InitialContext");
            nex.printStackTrace();
        }
    }

    public void ejbCreate(Object javaTTIter, String xmlRootTag, String[] desiredAttrNames)
            throws CreateException, EJBException {
        log("XmlResponseIterator.ejbCreate(Object javaTTIter) called");


        try {
            namingContext = new InitialContext();
        } catch (NamingException nex) {
            log("XmlResponseIterator:ejbCreate:  Naming exception caught while creating InitialContext");
            nex.printStackTrace();
        }
        //VP
        //this.javaTTIterator = (EJBObject)javaTTIter;
        this.javaTTIterator = javaTTIter;

        //try {
        //  this.javaTTIteratorHandle = ((EJBObject)javaTTIter).getHandle();
        //} catch (java.rmi.RemoteException rex) {
        //throw new EJBException("RemoteException" + rex.getMessage());
        //}
        // end VP

        this.xmlResponseRootTag = xmlRootTag;

        this.desiredAttributeNames = desiredAttrNames;
    }


    /**
     * Gets the xml response documents
     *
     * @param howMany         int Number of stringified xml response documents to return
     *                            in the getNext() method
     * @return                String[] xml response documents
     * @exception               EJBException if there is
     *                          a communications or systems failure
     */

    public String getNext(int howMany)
            throws javax.ejb.EJBException {
        String xmlResult = null;
        Class iterClass = javaTTIterator.getClass();
        //System.out.println("XmlResponseIterator: javaTTIteratorName: " + iterClass.getName() );

        try {

            if ((iterClass.getName()).indexOf("TroubleTicketKeyResultIterator") != -1) {
//System.out.println( "XMLResponseIterator: javaTTIterator is an instance of Key Result Iterator");
                xmlResult = this.getNextKeys(howMany);
            } else if ((iterClass.getName()).indexOf("TroubleTicketValueIterator") != -1) {
//System.out.println( "XMLResponseIterator: javaTTIterator is an instance of Value Iterator");
                xmlResult = this.getNextValues(howMany);
            } else {
                Logger.log("XMLResponseIterator: javaTTIterator is not an instance of any of the two TT Iterators");
            }

        } catch (java.rmi.RemoteException rex) {
            throw new javax.ejb.EJBException("RemoteException" + rex.getMessage());
        }

        return xmlResult;
    }

    //==================================================

    private String getNextKeys(int howMany)
            throws java.rmi.RemoteException {

        TroubleTicketKeyResult[] retKeys = null;
        TroubleTicketKeyResultIterator ttKeyResIter = null;

        try {

            ttKeyResIter = (TroubleTicketKeyResultIterator) this.javaTTIterator;
            retKeys = (TroubleTicketKeyResult[]) (ttKeyResIter.getNextTroubleTicketKeyResults(howMany));

            //simplified to add cast MR Aug 23
            //TroubleTicketKeyResult[] retKeys = ((TroubleTicketKeyResultIterator)(this.javaTTIterator)).getNext( howMany );

            boolean endOfReply = false;
            if (retKeys.length < howMany) {
                endOfReply = true;
            }
            StringBuffer strBuff = new StringBuffer();

            strBuff.append("<co:sequence>");
            strBuff.append(String.valueOf(IteratorResponseSequence++));
            strBuff.append("</co:sequence>\n");
            strBuff.append("<co:endOfReply>");
            strBuff.append(String.valueOf(endOfReply));
            strBuff.append("</co:endOfReply>\n");

            strBuff.append("<tt:results>\n");
            for (int i = 0; i < retKeys.length; i++) {
                TroubleTicketKey trKey = retKeys[i].getTroubleTicketKey();
                //strBuff.append("<tt:TroubleTicketKeyResult>\n");


                strBuff.append("\n<tt:item>\n");
                strBuff.append("<co:success>");
                if (retKeys[i].isSuccess())
                    strBuff.append("true");
                else
                    strBuff.append("false");
                strBuff.append("</co:success>\n");

                strBuff.append(((TroubleTicketKeyImpl) trKey).toXml());

                if (!retKeys[i].isSuccess()) {
                    strBuff.append("<co:exception>");
                    strBuff.append("<co:message>");
                    strBuff.append(retKeys[i].getException().getMessage());
                    strBuff.append("</co:message>");
                    strBuff.append("</co:exception>\n");
                }
                strBuff.append("</tt:item>\n");

                //strBuff.append("</tt:TroubleTicketKeyResult>\n");
            }
            strBuff.append("</tt:results>\n");
            return addXmlRootElement(xmlResponseRootTag, strBuff.toString());

        } catch (org.xml.sax.SAXException saxEx) {
            String xmlException = makeXmlException("TryCloseTroubleTicketsByTemplateException", "RemoteException",
                    saxEx.getMessage());
            log("XmlResponseIteratorBean.tryCloseTTsByTemplate: SAXException caught on JVT Iterator.getNext");
            saxEx.printStackTrace();

            throw new java.rmi.RemoteException(xmlException);
        }
    }

    //==================================================

    private String getNextValues(int howMany)
            throws java.rmi.RemoteException {


        TroubleTicketValue[] retValues = null;
        TroubleTicketValueIterator ttValIter = null;

        try {
            ttValIter = (TroubleTicketValueIterator) this.javaTTIterator;
            retValues = (TroubleTicketValue[]) (ttValIter.getNextTroubleTickets(howMany));


            boolean endOfReply = false;
            if (retValues.length < howMany) {
                endOfReply = true;
            }
            StringBuffer strBuff = new StringBuffer();

            strBuff.append("<co:sequence>");
            strBuff.append(String.valueOf(IteratorResponseSequence++));
            strBuff.append("</co:sequence>\n");
            strBuff.append("<co:endOfReply>");
            strBuff.append(String.valueOf(endOfReply));
            strBuff.append("</co:endOfReply>\n");
            //------------
            strBuff.append("<tt:troubleTicketValues>\n");

            for (int i = 0; i < retValues.length; i++) {
                //strBuff.append("\n<tt:item>\n");
                Serializer serializer = retValues[i].makeSerializer(XmlSerializer.class.getName());
                XmlSerializer ttXmlSerializer = (XmlSerializer) serializer;
                String xmlDoc = ttXmlSerializer.toXml(retValues[i], "tt:item");
                strBuff.append(xmlDoc);

                //((TroubleTicketValueImpl)retValues[i]).setDesiredAttributes( desiredAttributeNames, true );

                //strBuff.append(((TroubleTicketValueImpl)retValues[i]).toXmlNoRoot());

                //strBuff.append("\n</tt:item>\n");
            }
            strBuff.append("</tt:troubleTicketValues>\n");

            return addXmlRootElement(xmlResponseRootTag, strBuff.toString());
        }
                /* catch( org.xml.sax.SAXException saxEx )
                 {
                     String xmlException = makeXmlException( "QueryTroubleTicketsException", "RemoteException",
                                                             saxEx.getMessage()  );
                     log ("XmlResponseIteratorBean.queryTroubleTickets: SAXException caught on JVT Iterator.getNext");
                     saxEx.printStackTrace();

                     throw new java.rmi.RemoteException( xmlException );
                 }
                 */ catch (Exception e) {
            String xmlException = makeXmlException("QueryTroubleTicketsException", "RemoteException",
                    "");
            throw new java.rmi.RemoteException(xmlException);
        }
    }

    //==================================================

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

    //==================================================



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

    //==================================================
    /**
     * remove is called, when the client has finished iterating
     * through  the collection to allow resources to be deallocated.
     *
     * @exception               RemoteException if there is
     *                          a communications or systems failure
     */
    public void remove()
            throws java.rmi.RemoteException, javax.ejb.RemoveException {
        // remove the underlying java-based iterator
        Class iterClass = this.javaTTIterator.getClass();
        //System.out.println("XmlResponseIterator: javaTTIteratorName: " + iterClass.getName() );

        if ((iterClass.getName()).indexOf("TroubleTicketKeyResultIterator") != -1) {
            ((TroubleTicketKeyResultIterator) this.javaTTIterator).remove();
        } else if ((iterClass.getName()).indexOf("TroubleTicketValueIterator") != -1) {
            ((TroubleTicketValueIterator) this.javaTTIterator).remove();
        }

        // Now call the ejbRemove for this iterator
        // VP ejbRemove shall be call by the container when client or time out occurs
        //this.ejbRemove();

    }

}

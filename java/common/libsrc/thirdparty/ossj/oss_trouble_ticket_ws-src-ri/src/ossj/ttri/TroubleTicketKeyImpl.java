package ossj.ttri;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.oss.ApplicationContext;
import javax.oss.XmlSerializer;
import javax.oss.trouble.TroubleTicketKey;
import java.io.Serializable;

/**
 * TroubleTicketKey Implementation Class
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */

public class TroubleTicketKeyImpl implements TroubleTicketKey,
        Serializable {


    public javax.oss.Serializer makeSerializer(String serializerType)
            throws java.lang.IllegalArgumentException {
        if (serializerType.equals(XmlSerializer.class.getName()))
            return new XmlSerializerImpl(TroubleTicketKey.class.getName());
        else
            throw new java.lang.IllegalArgumentException("Unrecognized Serializer Type");

    }

    public String[] getSupportedSerializerTypes() {
        String[] serializerTypes = new String[1];
        serializerTypes[0] = new String(XmlSerializer.class.getName());
        return serializerTypes;
    }


    //data
    private String entityType = "TroubleTicket";
    //private ApplicationContextImpl  applicationContext = ApplicationContextImpl.getInstance();
    private ApplicationContext applicationContext = null;
    private String applicationDN = null;
    private String TroubleTicketId;  //the primary key field

    //ctors
    public TroubleTicketKeyImpl(String TroubleTicketId) {
        this.TroubleTicketId = TroubleTicketId;

    }

    public TroubleTicketKeyImpl() {


    }


    //--------------------------------------------------
    // methods from javax.oss.trouble.TroubleTicketKey
    //--------------------------------------------------
    public String getTroubleTicketPrimaryKey() {
        return TroubleTicketId;
    }

    public void setTroubleTicketPrimaryKey(String pk)
            throws java.lang.IllegalArgumentException {
        TroubleTicketId = pk;
    }

    public void setApplicationContext(ApplicationContext ctx) {
        applicationContext = ctx;
    }

    //--------------------------------------------------
    // methods from javax.oss.ManagedEntityKey
    //--------------------------------------------------

    //public String getDomain()           {return adminDomain;}   // admin domain
    public String getType() {//PG BUG return entityType;
        return "javax.oss.TroubleTicketValue";
    }

    public Object getPrimaryKey() {
        return TroubleTicketId;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //public ApplicationContext   getApplicationContext() {return ApplicationContextImpl.getInstance();}
    public String getApplicationDN() {
        return applicationDN;
    }


    //public void setDomain(String domain)    {adminDomain = domain;}
    public void setType(String type) {
        entityType = type;
    }

    public void setPrimaryKey(Object key) {
        TroubleTicketId = (String) key;
    }

    public javax.oss.ApplicationContext makeApplicationContext() {
        return new ApplicationContextImpl();
    }

    public void setApplicationDN(String dn) {
        applicationDN = dn;
    }

    public Object clone() {

        TroubleTicketKeyImpl newVal = new TroubleTicketKeyImpl();

        if (getType() != null)
            newVal.setType(new String(getType()));
        else
            newVal.setType(null);

        if (getApplicationDN() != null)
            newVal.setApplicationDN(new String(getApplicationDN()));
        else
            newVal.setApplicationDN(null);


        if (getApplicationContext() != null)
            newVal.setApplicationContext((ApplicationContext) ((ApplicationContext) getApplicationContext()).clone());
        else
            newVal.setApplicationContext(null);


        if (getPrimaryKey() != null)
            newVal.setPrimaryKey(new String((String) getPrimaryKey()));
        else
            newVal.setPrimaryKey(null);


        return newVal;
    }

    public Object makePrimaryKey() {
        //---------------------------------------------------------------------
        // create a key value for this TroubleTicketKey
        // use current time in milliseconds
        //---------------------------------------------------------------------
        TroubleTicketKeyImpl ttKey = new TroubleTicketKeyImpl();

        long timeMillis = java.lang.System.currentTimeMillis();
        Long longTime = new Long(timeMillis);
        String strTime = longTime.toString();
        ttKey.setTroubleTicketPrimaryKey(strTime);
        return ttKey;
    }


    //required by EJB
    public int hashCode() {
        return TroubleTicketId.hashCode();
    }


    //--------------------------------------------------
    // methods from javax.oss.XMLSerializable
    //--------------------------------------------------
    public void fromXml(org.w3c.dom.Element doc)
            throws org.xml.sax.SAXException {

        Logger.log("TTKEY::FROMXML");

        Element docElement = ((Document) doc).getDocumentElement();
        String reqType = docElement.getTagName();

        Logger.log("/n/nTTKey fromXml: Doc root element (request type): " + reqType);

        NodeList ttValNodeList = null;

        Node node1 = null;

        ttValNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "troubleTicketKey");
        node1 = ttValNodeList.item(0);
        if (node1 == null) {
            ttValNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "troubleTicketKey");
            node1 = ttValNodeList.item(0);
        }


        // Get all the elements contained by the key
        if (node1 != null) {
            NodeList nodeList = node1.getChildNodes();
            Node node2;

            for (int x = 0; x < nodeList.getLength(); x++) {
                node2 = nodeList.item(x);
                if (node2.getNodeType() != Node.ELEMENT_NODE) continue;
                String nodeName = node2.getNodeName();
                Logger.log("nodeName: " + nodeName);

                if (nodeName.equals("tt:primaryKey")) {
                    // System.out.println("########ABOUT TO SET PRIMARY in fromXML KEY WITH " + node2.getFirstChild().getNodeValue()+ "########");

                    setPrimaryKey(node2.getFirstChild().getNodeValue());
                } else if (nodeName.equals("co:type")) {
                    setType(node2.getFirstChild().getNodeValue());
                } else if (nodeName.equals("#comment")) {
                    continue;
                }
            }

        } else
            Logger.log("\nTTKey.fromXML(): no TT key in this document.");

    }

    public String toXml()
            throws org.xml.sax.SAXException {

        //System.out.println("\n         ******  TTKey To XML     ***********       \n");
        StringBuffer strBuffer = new StringBuffer();


        strBuffer.append("\n<tt:troubleTicketKey>\n");


        strBuffer.append(toXmlBase());

        strBuffer.append("</tt:troubleTicketKey>\n");
        return strBuffer.toString();
    }

    public String toXmlBase() {


        StringBuffer strBuffer = new StringBuffer();

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


        String appDn = getApplicationDN();
        if (appDn != null)
            strBuffer.append("<co:applicationDN>" + appDn + "</co:applicationDN>\n");


        strBuffer.append("<co:type>" + "tt:TroubleTicketValue" + "</co:type>\n");


        strBuffer.append("<tt:primaryKey>" + TroubleTicketId + "</tt:primaryKey>\n");

        return strBuffer.toString();
    }


    public String toXml(String elementName)
            throws org.xml.sax.SAXException {

        //System.out.println("\n         ******  TTKey To XML     ***********       \n");
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("\n<" + elementName + ">\n");
        strBuffer.append(toXmlBase());
        strBuffer.append("</" + elementName + ">\n");
        return strBuffer.toString();
    }

    public String getXmlHeader() {


        return null;
    }

    public String getRootName() {
        return null;
    }

    public String getRootAttributes() {
        return null;
    }

    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        sb.append("TroubleTicketKey: ");
        sb.append("\n");
        sb.append("Type: " + entityType);
        sb.append("\n");
        sb.append("TroubleTicketPrimaryKey: [" + TroubleTicketId + "]");
        sb.append("\n");
        return sb.toString();
    }

    public boolean equals(Object arg) {

        if ((arg != null) && (arg instanceof TroubleTicketKey)) {
            TroubleTicketKey argKey = (TroubleTicketKey) arg;
            String argKeyId = argKey.getTroubleTicketPrimaryKey();
            if ((TroubleTicketId == null) && (argKeyId == null)) return true;
            if ((TroubleTicketId != null) && (argKeyId == null)) return false;
            if (TroubleTicketId.equals(argKey.getTroubleTicketPrimaryKey())) return true;
        }
        return false;

    }

    public void print(int indent) {
        String sp = null;
        if ((indent > 0) && (indent < 60)) {
            char[] spaces = new char[indent];
            for (int x = 0; x < indent; x++) spaces[x] = ' ';
            sp = new String(spaces);
        }


        Logger.log(sp + "TroubleTicketKey:");
        if (applicationContext != null) {
            String fact = applicationContext.getFactoryClass();
            if (fact != null)
                Logger.log(sp + "              FactoryClass: " + fact);
            String url = applicationContext.getURL();
            if (url != null)
                Logger.log(sp + "                       URL: " + url);
        }
        if (applicationDN != null)
            Logger.log(sp + "             ApplicationDN: " + applicationDN);
        Logger.log(sp + "                      Type: " + entityType);
        Logger.log(sp + "   TroubleTicketPrimaryKey: " + TroubleTicketId);

    }

}



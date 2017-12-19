package com.sun.oss.common.ri;

import java.util.Vector;
import java.util.HashMap;
import java.util.Date;
import java.lang.StringBuffer;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.oss.Serializer;
import javax.oss.XmlSerializer;
import javax.oss.Event;
import javax.oss.ManagedEntityKey;
import javax.oss.QueryValue;
import javax.oss.ApplicationContext;
import javax.oss.ManagedEntityValue;
import javax.naming.*;

import com.nokia.oss.ossj.common.ri.* ;
import com.nokia.oss.ossj.common.ri.ex.* ;

/**
 * Common XML Translator Helper Class
 * 
 * Translates Managed Entity Value object to and from Java/XML
 * 
 */
public class XmlTranslator
{


    //Java singleton design pattern - private
    static private XmlTranslator singleton = new XmlTranslator();


    private static boolean debug;


    // -------------------------------------------
    // Constructor
    // -------------------------------------------
    //protected ctor - singleton pattern
    protected XmlTranslator()
    {
    	if( System.getProperty("DEBUG")!=null) {
			debug = true ;
		} else {
			debug = false ;
		}

    }

    // -------------------------------------------
    // toXml generic
    // -------------------------------------------
   /**
    * Serialize this Java object as an XML document instance with an XML
    * doctype declaration
    *
    * @return String XML document instance
    */
    public static String toXml( Object object, String elementName)
    throws java.lang.IllegalArgumentException {
     String doc = null;
     try {
     	if( object instanceof javax.oss.ManagedEntityValue ) {
         	  doc = toXml( (ManagedEntityValue) object, elementName );
        	}
        	else if( object instanceof  javax.oss.ManagedEntityKey ) {
            	doc = toXml( (ManagedEntityKey) object, elementName );
        	}
        	else if( object instanceof  javax.oss.Event ) {
            	doc = toXml( (Event) object, elementName );
        	}
        	else if( object instanceof  javax.oss.QueryValue ) {
            	doc = toXml( (QueryValue) object, elementName );
        	}
	
        	return doc;
     	}
     	catch( org.xml.sax.SAXException ex ) {
        	throw new java.lang.IllegalArgumentException( ex.getMessage() );
     	}
     }


    // -------------------------------------------
    // toXml for ManagedEnityValue
    // -------------------------------------------
   /**
    * Serialize this Java object as an XML document instance with an XML
    * doctype declaration
    *
    * @return String XML document instance
    */
    private static String toXml(ManagedEntityValue mev, String elementName)
    throws org.xml.sax.SAXException
    {
        log("XmlTranslator>>> toXml(ManagedEntityValue , " + elementName + ");") ;
        StringBuffer strBuffer = new StringBuffer();

        // Add the mevVal root element
        	strBuffer.append("<" + elementName + ">\n");

        // Add the key
        	strBuffer.append(toXml(mev.getManagedEntityKey(),"co:managedEntityKey"));

        // Add the attribute
        	LastUpdateVersionNumber2Xml(strBuffer,mev);

        // Add end tag for root element
        	strBuffer.append("</" + elementName + ">\n");

        // return part
        return ( strBuffer.toString() );
    }


    // -------------------------------------------
    // toXml for LastUpdateVersionNumber attribut
    // -------------------------------------------
    private static void LastUpdateVersionNumber2Xml(StringBuffer sb,ManagedEntityValue mev)
    {
        long attr = mev.getLastUpdateVersionNumber();
        sb.append("<co:lastUpdateVersionNumber>" + String.valueOf(attr) + "</co:lastUpdateVersionNumber>\n");

    }

    // -------------------------------------------
    // toXml for ManagedEntityKey
    // -------------------------------------------
    private static String toXml(ManagedEntityKey meKey,String elementName)
    throws org.xml.sax.SAXException
    {
        log("XmlTranslator>>> Begin toXml(ManagedEntityKey , " + elementName + ");") ;

        StringBuffer strBuffer = new StringBuffer();

        if ( meKey == null ) {
            //provide nil XML encoding for the top level only
			strBuffer.append("<"+ elementName + " xsi:nil=\"true\"></" + elementName + ">");
        	log("Key is null") ;

        } else {
			strBuffer.append("\n<" + elementName + ">\n") ;

	        ApplicationContext applicationContext = meKey.getApplicationContext() ;
	        if (applicationContext != null)
	        {
	            strBuffer.append("<co:applicationContext>\n");
	
	            String fact=applicationContext.getFactoryClass();
	            if (fact != null)
	                strBuffer.append("<co:factoryClass>" + fact + "</co:factoryClass>\n");
	            //else
	                //System.out.println("\n\n!!--The FactoryClass is null!!");
	            String url=applicationContext.getURL();
	            if (url != null)
	            strBuffer.append("<co:url>" + url + "</co:url>\n");
	            //else
	                //System.out.println("\n\n!!--The URL is null!!");
	
	
	        java.util.Map props=applicationContext.getSystemProperties();
	        if (props != null)
	        {
	        Object currValue = null;
	        Integer currKey = null;
	
	         strBuffer.append("<co:systemProperties>\n");
	
	        for ( java.util.Iterator iter = props.keySet().iterator();iter.hasNext(); )
			   {
	              currKey = (Integer) iter.next();
	              currValue = props.get( currKey );
	              strBuffer.append("<co:property>");
	              //V.R. TO DO get name and value of properties
	              strBuffer.append("<co:name>" +  "</co:name>\n");
	              strBuffer.append("<co:value>" +  "</co:value>\n");
	              strBuffer.append("</co:property>\n");
	
	            }
	             strBuffer.append("</co:systemProperties>\n");
	        }
	        //else
	           //System.out.println("\n\n!!--The Sys Properties are null!!");
	
	        strBuffer.append("</co:applicationContext>\n");
	        }
	
	
	        String appDn=meKey.getApplicationDN();
	        if (appDn != null)
	            strBuffer.append("<co:applicationDN>" + appDn + "</co:applicationDN>\n");
	        //else
	            //System.out.println("\n\n!!--The ApplicationDN is null!!");
	
	        String type=meKey.getType();
	        if (type != null)
	            strBuffer.append("<co:type>" + type + "</co:type>\n");
	        else
				strBuffer.append("<co:type>" + "co:ManagedEntityValue" + "</co:type>\n");


			strBuffer.append("</" + elementName + ">\n") ;
	
		}
        log("XmlTranslator>>> End   toXml(ManagedEntityKey , " + elementName + ");") ;
	    return strBuffer.toString();
    }

    // -------------------------------------------
    // toXml for Event
    // -------------------------------------------
   /**
    * Serialize this Java object as an XML document instance with an XML
    * doctype declaration
    *
    * @return String XML document instance
    */
    private static String toXml(Event event, String elementName)
    throws org.xml.sax.SAXException
    {
        log("XmlTranslator>>> toXml(Event , " + elementName + ");") ;
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<" + elementName + ">\n") ;

        // Add EventTime
        strBuffer.append("<co:eventTime>");
        if ( event.getEventTime() != null )
        {  
            strBuffer.append( getXmlDate(event.getEventTime()) );
        }  
        else
        {  
            strBuffer.append( "0000-00-00T00:00:00Z" );
        }  
        strBuffer.append("</co:eventTime>\n");


        // Add ApplicationDN
        strBuffer.append("<co:applicationDN>");
        strBuffer.append( event.getApplicationDN() );
        strBuffer.append("</co:applicationDN>\n");



		strBuffer.append("</" + elementName + ">\n") ;
        return ( strBuffer.toString() );
    }

    // -------------------------------------------
    // toXml for QueryValue
    // -------------------------------------------
   /**
    * Serialize this Java object as an XML document instance with an XML
    * doctype declaration
    *
    * @return String XML document instance
    */
    private static String toXml(QueryValue query, String elementName)
    throws org.xml.sax.SAXException
    {
        log("XmlTranslator>>> toXml(QueryValue , " + elementName + ");") ;
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<" + elementName + ">\n") ;

        // Add 

		strBuffer.append("</" + elementName + ">\n") ;
        return ( strBuffer.toString() );
    }

    // -------------------------------------------
    // toXml for Date Generic dispatcher
    // -------------------------------------------
    public static String getXmlDate( java.util.Date date )
    {
		java.util.TimeZone timeZone= new java.util.SimpleTimeZone(0,"GMT") ;
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss'Z'");
		formatter.setTimeZone(timeZone) ;
		return (formatter.format(date));
    }



    // -------------------------------------------
    // fromXml Generic dispatcher
    // -------------------------------------------
   public static Object fromXml( Element element, String originaltype, String commonType  )
    throws IllegalArgumentException {
    try {
        if( commonType.equals(ManagedEntityKey.class.getName() )) {
            log ("--fromXml on ManagedEntityKey---" );
            return managedEntityKeyFromXml(element,originaltype);
        }
		else if( commonType.equals(ManagedEntityValue.class.getName() )) {
           return  managedEntityValueFromXml (element,originaltype);
        }
        else if( commonType.equals(javax.oss.Event.class.getName() )) {
            return eventFromXml( element,originaltype );
        }
        else if( commonType.equals(javax.oss.QueryValue.class.getName() )) {
            return queryValueFromXml( element,originaltype );
        }

       }
        catch( Exception ex ) {
            return new IllegalArgumentException( ex.getMessage() );
        }
        return null;
    }

    // -------------------------------------------
    // fromXml from ManagedEntityValue
    // -------------------------------------------
    private static ManagedEntityValue managedEntityValueFromXml(Node topNode, String type)
    {
		ManagedEntityValue mev = null ; 

		try {
        	mev = (ManagedEntityValue) Class.forName(type).newInstance() ;
		} catch (java.lang.IllegalAccessException e) {
			return null ;
		} catch (java.lang.ClassNotFoundException e) {
			return null ;
		} catch (java.lang.InstantiationException e) {
			return null ;
		}
        // Get the node's children
        NodeList nodeList = topNode.getChildNodes();
        Node node2;
        log ("fromXmlNoRoot--> translated attributes:");
        log ("fromXmlNoRoot--> length:" + nodeList.getLength());
        for (int i=0; i<nodeList.getLength(); i++)
        {
            node2 = nodeList.item(i);

            if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
            String nodeName = node2.getNodeName();
            
			log ("Current node  = '" + nodeName + "'") ;

            if (nodeName.equals("co:managedEntityKey")) {
        				mev.setManagedEntityKey (managedEntityKeyFromXml(node2,mev.makeManagedEntityKey()) );
    		}
            else if (nodeName.equals("co:lastUpdateVersionNumber"))
			{
		        lastUpdateVersionNumberFromXml(node2,mev);

    		}
            else
            if (nodeName.equals("#comment"))
            {
                continue;
            }
            else
            {
                String unknownAttr = new String("XmlTranslator.fromXml(): Unknown Xml attribute: " + nodeName);
                log (unknownAttr);
                //throw new org.xml.sax.SAXException(unknownAttr);
            }

        }


        return mev;
    }


    // -------------------------------------------
    // fromXml from ManagedEntityKey
    // -------------------------------------------
    private static ManagedEntityKey managedEntityKeyFromXml(Node topNode, String type) {
        ManagedEntityKey key = null ;
        log (" ---- Begin managedEntityKeyFromXml managedEntityKeyFromXml(Node topNode)") ;
		try {
        	key = (ManagedEntityKey) Class.forName(type).newInstance() ;
		} catch (java.lang.IllegalAccessException e) {
			return null ;
		} catch (java.lang.ClassNotFoundException e) {
			return null ;
		} catch (java.lang.InstantiationException e) {
			return null ;
		}
		return managedEntityKeyFromXml (topNode, key ) ;
		
	}


    private static ManagedEntityKey managedEntityKeyFromXml(Node topNode, ManagedEntityKey key)
    {
        NodeList mainNodeList = topNode.getChildNodes();
        Node currentNode;

        for (int x=0; x<mainNodeList.getLength(); x++)
        {
            currentNode = mainNodeList.item(x);
            if( currentNode.getNodeType() != Node.ELEMENT_NODE ) continue;
            String currentNodeName = currentNode.getNodeName();
            log ("&&&&&currentNodeName: &&&&&" + currentNodeName);

            if ( currentNodeName.equals("co:applicationContext") )
            {
             // Get all the elements contained by the applicationContext
             NodeList applicationContextNoteList = currentNode.getChildNodes();
             Node applicationContextCurrentNode;

    		// =============
    		// Context creation
    		// =============
    		Context ctx = null ;
    		try {
        		ctx = new javax.naming.InitialContext(); 
    		} catch (javax.naming.NamingException ne) { 
				log ( "managedEntityKeyFromXml context creation error : " + ne.getMessage());
				return null ;
    		}

			String factory = null ;
			String url     = null ;
             for (int y=0; y<applicationContextNoteList.getLength(); y++)
               {
                applicationContextCurrentNode = applicationContextNoteList.item(y);
                String applicationContextCurrentNodeName = applicationContextCurrentNode.getNodeName();
                if ( applicationContextCurrentNodeName.equals("co:factoryClass") )
                   {
                    factory = applicationContextCurrentNode.getFirstChild().getNodeValue() ;
                	log ("nodeName: " + applicationContextCurrentNodeName + " -  nodeValue: " + factory);
                   }
                else if ( applicationContextCurrentNodeName.equals("co:url") )
                   {
                    url = applicationContextCurrentNode.getFirstChild().getNodeValue() ;
                	log ("nodeName: " + applicationContextCurrentNodeName + " -  nodeValue: " + url);
                   }
				else
				   {
                	log ("nodeName: " + applicationContextCurrentNodeName);
				   }
                }
             	ApplicationContextImpl apc = new  ApplicationContextImpl(factory,new java.util.HashMap(), url);
                key.setApplicationContext(apc);
            }
            else if ( currentNodeName.equals("co:type") )
            {
                key.setType( currentNode.getFirstChild().getNodeValue() );
            }
            else if ( currentNodeName.equals("co:applicationDN") )
            {
                key.setApplicationDN(currentNode.getFirstChild().getNodeValue() );
            }
        }


        return key;
    }

    // -------------------------------------------
    // fromXml from Event
    // -------------------------------------------
   private static Event eventFromXml(Node topNode, String type) {
        Event event = null ;
        log (" ---- Begin Event eventFromXml(Node topNode, String type)") ;
        try {
            event = (Event) Class.forName(type).newInstance() ;
        } catch (java.lang.IllegalAccessException e) {
            return null ;
        } catch (java.lang.ClassNotFoundException e) {
            return null ;
        } catch (java.lang.InstantiationException e) {
            return null ;
        }
        return eventFromXml (topNode, event ) ;

    }

 	private static Event eventFromXml( Node topNode, Event event ) {

        log (" ---- Begin Event eventFromXml( Node topNode, Event event )" ) ;

        NodeList mainNodeList = topNode.getChildNodes();
        Node currentNode;
		String dn = null ;
		java.util.Date date = new java.util.Date() ;

        for (int x=0; x<mainNodeList.getLength(); x++)
        {
            currentNode = mainNodeList.item(x);
            if( currentNode.getNodeType() != Node.ELEMENT_NODE ) continue;
            String currentNodeName = currentNode.getNodeName();
            log ("&&&&&currentNodeName: &&&&&" + currentNodeName);

            if ( currentNodeName.equals("co:applicationDN") )
            {
                dn = currentNode.getFirstChild().getNodeValue() ;
            }
            else if ( currentNodeName.equals("co:eventTime") )
            {
                date = DateFromXml(currentNode) ;
            }
        }
        event.setApplicationDN(dn);
		event.setEventTime(date) ;
		return event ;
    }



    // -------------------------------------------
    // fromXml from QueryValue
    // -------------------------------------------
   private static QueryValue queryValueFromXml(Node topNode, String type) {
        QueryValue queryValue = null ;
        log (" ---- Begin Event queryValueFromXml(Node topNode, String type)") ;
        try {
            queryValue = (QueryValue) Class.forName(type).newInstance() ;
        } catch (java.lang.IllegalAccessException e) {
            return null ;
        } catch (java.lang.ClassNotFoundException e) {
            return null ;
        } catch (java.lang.InstantiationException e) {
            return null ;
        }
        return queryValueFromXml (topNode, queryValue ) ;

    }

   private static QueryValue queryValueFromXml(Node topNode, QueryValue queryValue) {

        log (" ---- Begin QueryValue queryValueFromXml(Node topNode, QueryValue queryValue)") ;

		return queryValue ;
    }

    /*--------------------------------------------------------------------------------
    *  JAXP XML parsing notes:
    *
    *    getChildNodes():
    *        will never be null - it either contains nodes or is an empty list
    *
    *    getFirstChild():
    *        will return null if there are no children. Refers to the 0th child,
    *        e.g. the first child is the contents of the node itself
    *
    *        Currently, if a node is nilled (e.g. <tt:sometag xsi:nil="true"></tt:sometag>)
    *        null is returned from the getFirstChild call.  The getNodeName call
    *        correctly returns the appropriate node name (e.g. for the above example,
    *        it returns "tt:sometag"
    *
    *    hasChildNodes():
    *        determines if there are child nodes
    *
    *-------------------------------------------------------------------------------*/



    // -------------------------------------------
    // fromXml from lastUpdateVersionNumber
    // -------------------------------------------
    public static void lastUpdateVersionNumberFromXml(Node node,ManagedEntityValue mev)
    {
        if (node.hasChildNodes())
            //aLong = new Long(node.getFirstChild().getNodeValue());
            mev.setLastUpdateVersionNumber( Long.parseLong((node.getFirstChild().getNodeValue())));
    }


    // -------------------------------------------
    // fromXml from Date
    // -------------------------------------------
    public static java.util.Date DateFromXml(Node node)
    {

        //1999-05-31T13:20:00-05:00.
        java.util.Date date = null;

        if (! (node.hasChildNodes()) )      //is it nilled?
            return date;

        String childNodeValue = node.getFirstChild().getNodeValue();

        //"2001-02-18T14:01:00Z"
        StringTokenizer st = new StringTokenizer( childNodeValue, "T" );
        String year;
        String month;
        String day;
        String hour;
        String min;
        String sec;

        String dateToken = st.nextToken();
        String timeToken = st.nextToken();

        StringTokenizer st2 = new StringTokenizer( dateToken, "-");
        year = st2.nextToken();
        month = st2.nextToken();
        day = st2.nextToken();

        StringTokenizer st3 = new StringTokenizer( timeToken, ":");

        hour = st3.nextToken();
        min = st3.nextToken();
        sec = (st3.nextToken()).substring(0,2);

        java.util.Calendar calendar =  java.util.Calendar.getInstance() ;
		java.util.TimeZone timeZone= new java.util.SimpleTimeZone(0,"GMT") ;
		calendar.setTimeZone(timeZone) ;

		calendar.set	( Integer.parseInt(year) ,
                          Integer.parseInt(month) - 1,
                          Integer.parseInt(day),
                          Integer.parseInt(hour),
                          Integer.parseInt(min),
                          Integer.parseInt(sec) ) ;
        long utcDate = calendar.getTime().getTime();

        date = new java.util.Date ( utcDate );
        return date;

    }

    private  static void log(String log ) {
		if (debug) {
			System.out.println(log );
		}
 
    }



// DL //base types: pointer to base types - use a substitution group
// DL public static org.w3c.dom.Element getElement( Element doc, String tagName ) {
// DL 
// DL            log("tag name:" + tagName);
// DL            log("Element name: " + doc.getTagName() );
// DL 
// DL 
// DL             log("getElement 1");
// DL             Node node = null;
// DL             NodeList nodeList = null;
// DL 
// DL             nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", tagName);
// DL 
// DL             log("getElement 2");
// DL             if (nodeList != null) {
// DL 
// DL                 log("nodeList != null");
// DL                 log("nodeList.getLength()" + nodeList.getLength());
// DL                 node = nodeList.item(0);
// DL             }
// DL             else log("element is null");
// DL 
// DL             log("getElement 3");
// DL             if( node == null ) log("node is null");
// DL             Element element =  (Element) node;
// DL 
// DL             log("getElement 4");
// DL             return element;
// DL 
// DL    }
// DL public static org.w3c.dom.Element getElement( Document doc, String tagName ) {
// DL 
// DL            log("tag name:" + tagName);
// DL 
// DL 
// DL 
// DL             log("getElement 1");
// DL             Node node = null;
// DL             NodeList nodeList = null;
// DL 
// DL             nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", tagName);
// DL 
// DL             log("getElement 2");
// DL             if (nodeList != null) {
// DL 
// DL                 log("nodeList != null");
// DL                 log("nodeList.getLength()" + nodeList.getLength());
// DL                 node = nodeList.item(0);
// DL             }
// DL             else log("element is null");
// DL 
// DL             log("getElement 3");
// DL             if( node == null ) log("node is null");
// DL             Element element =  (Element) node;
// DL 
// DL             log("getElement 4");
// DL             return element;
// DL 
// DL    }
// DL 
// DL 
// DL     /**
// DL      * Serialize this Java object as an XML document instance with an XML
// DL      * doctype declaration.
// DL      *
// DL      * @return String XML document instance
// DL      */
// DL 
// DL     public static TroubleTicketKey SerializeKey( Element doc, String tag )
// DL 
// DL     {    TroubleTicketKey trKey = new TroubleTicketKeyImpl();
// DL          TroubleTicketKey retKey = null;
// DL          Serializer serializer = trKey.makeSerializer( XmlSerializer.class.getName());
// DL          XmlSerializer ttXmlSerializer = (XmlSerializer) serializer;
// DL          Element element = getElement( doc, tag );
// DL          retKey = (TroubleTicketKey) ttXmlSerializer.fromXml( element );
// DL          return retKey;
// DL     }
// DL 
// DL      public  static TroubleTicketValue SerializeValue( Element doc, String tag )
// DL 
// DL     {
// DL          log("----Serialize Value from Element---");
// DL          TroubleTicketValue trVal = new TroubleTicketValueImpl();
// DL          TroubleTicketValue retVal = null;
// DL          Serializer serializer = trVal.makeSerializer( XmlSerializer.class.getName());
// DL          XmlSerializer ttXmlSerializer = (XmlSerializer) serializer;
// DL          Element element = getElement( doc, tag );
// DL          if( element != null ) {
// DL             log("----Element != NULL---");
// DL             retVal = (TroubleTicketValue) ttXmlSerializer.fromXml( element );
// DL             log("---Number of Pop Attributes---> " + retVal.getPopulatedAttributeNames().length );
// DL          } else log("----Element is NULL---");
// DL          return retVal;
// DL     }
// DL 
// DL       public  static TroubleTicketValue SerializeValue( Document doc, String tag )
// DL 
// DL     {
// DL          log("----Serialize Value---");
// DL          TroubleTicketValue trVal = new TroubleTicketValueImpl();
// DL          TroubleTicketValue retVal = null;
// DL          Serializer serializer = trVal.makeSerializer( XmlSerializer.class.getName());
// DL          XmlSerializer ttXmlSerializer = (XmlSerializer) serializer;
// DL          Element element = getElement( doc, tag );
// DL          retVal = (TroubleTicketValue) ttXmlSerializer.fromXml( element );
// DL          log("---Number of Pop Attributes---> " + retVal.getPopulatedAttributeNames().length );
// DL          return retVal;
// DL     }
// DL 
// DL 
// DL     public static String toXml(TroubleTicketValue ttv,
// DL                                      String elementName )
// DL     throws org.xml.sax.SAXException {
// DL         return ((TroubleTicketValueImpl) ttv).toXml(elementName) ;
// DL     }
// DL 
// DL     public static String toXml(TroubleTicketKey ttkey,
// DL                                      String elementName )
// DL     throws org.xml.sax.SAXException {
// DL 
// DL        return ((TroubleTicketKeyImpl)ttkey).toXml(elementName) ;
// DL     }
// DL     public static String toXml(TroubleTicketCreateEvent event,
// DL                                      String elementName )
// DL     throws org.xml.sax.SAXException {
// DL         return troubleTicketCreateEventtoXml( event, elementName );
// DL     }
// DL     public static String toXml(TroubleTicketCloseOutEvent  event,
// DL                                      String elementName )
// DL     throws org.xml.sax.SAXException {
// DL         return troubleTicketCloseOutEventtoXml( event, elementName );
// DL     }
// DL     public static String toXml(TroubleTicketCancellationEvent  event,
// DL                                      String elementName )
// DL     throws org.xml.sax.SAXException {
// DL         return troubleTicketCancellationEventtoXml( event, elementName );
// DL     }
// DL     public static String toXml(TroubleTicketAttributeValueChangeEvent  event,
// DL                                      String elementName )
// DL     throws org.xml.sax.SAXException {
// DL         return troubleTicketAttributeValueChangeEventtoXml( event, elementName );
// DL     }
// DL     public static String toXml(TroubleTicketStatusChangeEvent  event,
// DL                                      String elementName )
// DL     throws org.xml.sax.SAXException {
// DL        return troubleTicketStatusChangeEventtoXml( event, elementName );
// DL     }
// DL     public static String toXml(QueryAllOpenTroubleTicketsValue  query,
// DL                                      String elementName )
// DL     throws org.xml.sax.SAXException {
// DL          return ((QueryAllOpenTroubleTicketsImpl) query).toXml(elementName) ;
// DL     }
// DL     public static String toXml(QueryByEscalationValue   query,
// DL                                      String elementName )
// DL     throws org.xml.sax.SAXException {
// DL         //return ((QueryByEscalationValueImpl) query).toXml(elementName) ;
// DL         return null;
// DL     }
// DL 
// DL 
// DL 
// DL   public static TroubleTicketCancellationEvent troubleTicketCancellationEventFromXml( Element doc )
// DL  {
// DL     TroubleTicketCancellationEvent event = new TroubleTicketCancellationEventImpl();
// DL 
// DL 
// DL 
// DL 
// DL             String closedOutnarr = null;
// DL 
// DL             Node node = null;
// DL             NodeList nodeList = null;
// DL 
// DL             nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket","closeOutNarr");
// DL              if (nodeList != null)
// DL              {
// DL                node = nodeList.item(0);
// DL                if (node == null)
// DL                {
// DL                 log( "No Status");
// DL                }
// DL                else {
// DL                 closedOutnarr = node.getFirstChild().getNodeValue();
// DL                }
// DL 
// DL              }
// DL 
// DL 
// DL 
// DL 
// DL 
// DL              nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket","troubleTicketKey");
// DL              if (nodeList != null)
// DL              {
// DL                node = nodeList.item(0);
// DL                if (node == null)
// DL                {
// DL                 log( "No Trouble Ticket Key");
// DL                }
// DL 
// DL              }
// DL 
// DL 
// DL              TroubleTicketKey key = XmlTranslator.TroubleTicketKeyFromXml(node);
// DL 
// DL              //should check statusTime too...
// DL 
// DL              event.setTroubleTicketKey(key);
// DL              event.setCloseOutNarr(closedOutnarr);
// DL 
// DL              return event;
// DL  }
// DL 
// DL   public static TroubleTicketAttributeValueChangeEvent troubleTicketAttributeValueChangeEventFromXml( Element doc ) {
// DL     TroubleTicketAttributeValueChangeEvent event = new TroubleTicketAttributeValueChangeEventImpl();
// DL     TroubleTicketValue ttValue = null;
// DL     try {
// DL 
// DL       ttValue = SerializeValue( doc, "troubleTicketValue" );
// DL 
// DL     }
// DL     catch( Exception e ) {
// DL         log("troubleTicketCreateEventFromXml" + e.getMessage() );
// DL     }
// DL     event.setTroubleTicketValue( ttValue );
// DL     return event;
// DL  }
// DL 
// DL  public static TroubleTicketValue getTroubleTicketValue( Document doc ) {
// DL     TroubleTicketValue ttValue = null;
// DL     try {
// DL 
// DL        ttValue = SerializeValue( doc, "troubleTicketValue" );
// DL     }
// DL     catch( Exception ex ) {
// DL     }
// DL     return ttValue;
// DL  }
// DL 
// DL  public static TroubleTicketCloseOutEvent troubleTicketCloseOutEventFromXml( Element doc ) {
// DL     TroubleTicketCloseOutEvent event = new TroubleTicketCloseOutEventImpl();
// DL     TroubleTicketValue ttValue = null;
// DL     try {
// DL         ttValue = SerializeValue( doc, "troubleTicketValue" );
// DL     }
// DL     catch( Exception e ) {
// DL         log("troubleTicketCreateEventFromXml" + e.getMessage() );
// DL     }
// DL     event.setTroubleTicketValue( ttValue );
// DL     return event;
// DL  }
// DL 
// DL  public static TroubleTicketStatusChangeEvent troubleTicketStatusChangeEventFromXml( Element doc )
// DL  {
// DL     TroubleTicketStatusChangeEvent event = new TroubleTicketStatusChangeEventImpl();
// DL 
// DL 
// DL 
// DL             int status = -1;
// DL             int state  =  -1;
// DL 
// DL             Node node = null;
// DL             NodeList nodeList = null;
// DL 
// DL             nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket","troubleStatus");
// DL              if (nodeList != null)
// DL              {
// DL                node = nodeList.item(0);
// DL                if (node == null)
// DL                {
// DL                 log( "No Status");
// DL                }
// DL 
// DL              }
// DL 
// DL 
// DL              status = XmlTranslator.EnumeratedTypeFromXml(node,TroubleStatus.class);
// DL 
// DL              nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket","troubleState");
// DL              if (nodeList != null)
// DL              {
// DL                node = nodeList.item(0);
// DL                if (node == null)
// DL                {
// DL                 log( "No State");
// DL                }
// DL 
// DL              }
// DL 
// DL 
// DL              state= XmlTranslator.EnumeratedTypeFromXml(node,TroubleState.class);
// DL 
// DL 
// DL              nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket","troubleTicketKey");
// DL              if (nodeList != null)
// DL              {
// DL                node = nodeList.item(0);
// DL                if (node == null)
// DL                {
// DL                 log( "No Trouble Ticket Key");
// DL                }
// DL 
// DL              }
// DL 
// DL 
// DL              TroubleTicketKey key = XmlTranslator.TroubleTicketKeyFromXml(node);
// DL 
// DL              //should check statusTime too...
// DL 
// DL              event.setTroubleTicketKey(key);
// DL              event.setStatus(status);
// DL              java.util.Date date = new java.util.Date();
// DL              event.setStatusTime(date);
// DL              event.setState(state);
// DL 
// DL              return event;
// DL 
// DL 
// DL  }
// DL 
// DL 
// DL     public static void AccountOwner2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         PersonReach attr = ttv.getAccountOwner();
// DL         if (attr == null)
// DL             sb.append("<tt:accountOwner xsi:nil=\"true\"></tt:accountOwner>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:accountOwner>\n");
// DL             XmlTranslator.PersonReach2Xml(sb,attr);
// DL             sb.append("</tt:accountOwner>\n");
// DL         }
// DL     }
// DL 
// DL     public static void ActivityDurationList2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         ActivityDurationList attr = ttv.getActivityDurationList();
// DL         if (attr == null) {
// DL             sb.append("<tt:activityDurationList xsi:nil=\"true\"></tt:activityDurationList>\n");
// DL             
// DL             return;
// DL         }
// DL         else
// DL         {
// DL             ActivityDuration[] adArray = null;
// DL             try
// DL             {
// DL                 adArray = attr.get();
// DL             }
// DL             catch (java.lang.IllegalStateException ex)
// DL             {
// DL                 //was never set, so set XML to null.
// DL                
// DL                 sb.append("<tt:activityDurationList xsi:nil=\"true\"></tt:activityDurationList>\n");
// DL                 return;
// DL             }
// DL             sb.append("<tt:activityDurationList>\n");
// DL             //PG BUG sb.append("<co:modifier>NONE</co:modifier>\n");
// DL             sb.append("<co:modifier>SET</co:modifier>\n");
// DL             sb.append("<tt:activityDurations>\n");
// DL             for (int x=0; x<adArray.length; x++)
// DL             {
// DL                 if (adArray[x] == null)
// DL                 {
// DL                     sb.append("<tt:Item xsi:nil=\"true\"></tt:Item>");
// DL                 }
// DL                 else
// DL                 {
// DL                     sb.append("<tt:Item>\n");
// DL                     XmlTranslator.ActivityDuration2Xml(sb,adArray[x]);
// DL                     sb.append("</tt:Item>\n");
// DL                 }
// DL             }
// DL             sb.append("</tt:activityDurations>\n");
// DL             sb.append("</tt:activityDurationList>\n");
// DL         }
// DL     }
// DL 
// DL     public static void AdditionalTroubleInfoList2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         String[] attr = ttv.getAdditionalTroubleInfoList();
// DL         if ( attr == null )
// DL             sb.append("\n<tt:AdditionalTroubleInfoList xsi:nil=\"true\"></tt:AdditionalTroubleInfoList>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:additionalTroubleInfoList>\n");
// DL             for (int x=0; x<attr.length;x++)
// DL             {
// DL                 if (attr[x] == null)
// DL                     sb.append("<co:Item xsi:nil=\"true\"></co:Item>\n");
// DL                 else
// DL                     sb.append("<co:Item>" + attr[x] + "</co:Item>\n");
// DL             }
// DL             sb.append("</tt:additionalTroubleInfoList>\n");
// DL         }
// DL 
// DL     }
// DL 
// DL     public static void AfterHoursRepairAuthority2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         sb.append("<tt:afterHoursRepairAuthority>");
// DL         XmlTranslator.Boolean2Xml(sb,ttv.getAfterHoursRepairAuthority());
// DL         sb.append("</tt:afterHoursRepairAuthority>\n");
// DL     }
// DL 
// DL     public static void AuthorizationList2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         Authorization[] attr = ttv.getAuthorizationList();
// DL 
// DL         if (attr == null)
// DL             sb.append("<tt:authorizationList xsi:nil=\"true\"></tt:authorizationList>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:authorizationList>\n");
// DL             for (int x=0;x<attr.length;x++ )
// DL             {
// DL                 if (attr[x] == null)
// DL                 {
// DL                     sb.append("<tt:Item xsi:nil=\"true\"></tt:Item>");
// DL                 }
// DL                 else
// DL                 {
// DL                     sb.append("<tt:Item>\n");
// DL                     XmlTranslator.Authorization2Xml(sb,attr[x]);
// DL                     sb.append("</tt:Item>\n");
// DL                 }
// DL             }
// DL             sb.append("</tt:authorizationList>\n");
// DL 
// DL         }
// DL     }
// DL 
// DL     public static void CancelRequestedByCustomer2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         sb.append("<tt:cancelRequestedByCustomer>");
// DL         XmlTranslator.Boolean2Xml(sb,ttv.getCancelRequestedByCustomer());
// DL         sb.append("</tt:cancelRequestedByCustomer>\n");
// DL     }
// DL 
// DL     public static void ClearancePerson2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         PersonReach attr = ttv.getClearancePerson();
// DL         if (attr == null)
// DL             sb.append("<tt:clearancePerson xsi:nil=\"true\"></tt:clearancePerson>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:clearancePerson>\n");
// DL             XmlTranslator.PersonReach2Xml(sb,attr);
// DL             sb.append("</tt:clearancePerson>\n");
// DL         }
// DL     }
// DL 
// DL     public static void CloseOutNarr2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         String attr = ttv.getCloseOutNarr();
// DL         if (attr == null)
// DL             sb.append("<tt:closeOutNarr xsi:nil=\"true\"></tt:closeOutNarr>\n");
// DL         else
// DL             sb.append("<tt:closeOutNarr>" + attr + "</tt:closeOutNarr>\n");
// DL     }
// DL 
// DL     public static void CloseOutVerification2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         XmlTranslator.EnumeratedType2Xml(sb,
// DL                                          CloseOutVerification.class,
// DL                                          ttv.getCloseOutVerification());
// DL     }
// DL 
// DL     public static void CommitmentTime2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         java.util.Date attr = ttv.getCommitmentTime();
// DL         if (attr == null)
// DL             sb.append("<tt:commitmentTime xsi:nil=\"true\"></tt:commitmentTime>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:commitmentTime>");
// DL             XmlTranslator.Date2Xml(sb,attr);
// DL             sb.append("</tt:commitmentTime>\n");
// DL         }
// DL     }
// DL 
// DL     public static void CommitmentTimeRequested2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         java.util.Date attr = ttv.getCommitmentTimeRequested();
// DL         if (attr == null)
// DL             sb.append("<tt:commitmentTimeRequested xsi:nil=\"true\"></tt:commitmentTimeRequested>/n");
// DL         else
// DL         {
// DL             sb.append("<tt:commitmentTimeRequested>");
// DL             XmlTranslator.Date2Xml(sb,attr);
// DL             sb.append("</tt:commitmentTimeRequested>\n");
// DL         }
// DL     }
// DL 
// DL     public static void Customer2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         PersonReach attr = ttv.getCustomer();
// DL         if (attr == null)
// DL             sb.append("<tt:customer xsi:nil=\"true\"></tt:customer>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:customer>\n");
// DL             XmlTranslator.PersonReach2Xml(sb,attr);
// DL             sb.append("</tt:customer>\n");
// DL         }
// DL     }
// DL 
// DL     public static void CustomerRoleAssignmentList2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         CustomerRoleAssignment[] attr = ttv.getCustomerRoleAssignmentList() ;
// DL 
// DL         if( attr == null )
// DL             sb.append("<tt:customerRoleAssignmentList xsi:nil=\"true\"></tt:customerRoleAssignmentList>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:customerRoleAssignmentList>\n");
// DL 
// DL             for( int x=0; x<attr.length; x++ )
// DL             {
// DL                 if (attr[x] == null)
// DL                 {
// DL                     sb.append("<tt:Item xsi:nil=\"true\"></tt:Item>");
// DL                 }
// DL                 else
// DL                 {
// DL                     sb.append("<tt:Item>\n");
// DL 
// DL                     String role = attr[x].getRole();
// DL                     if( role == null )
// DL                         sb.append( "<tt:role xsi:nil=\"true\"></tt:role>\n");
// DL                     else
// DL                         sb.append( "<tt:role>" + role + "</tt:role>\n" );
// DL 
// DL                     PersonReach cp = attr[x].getContactPerson();
// DL                     if( cp == null )
// DL                         sb.append( "<tt:contactPerson xsi:nil=\"true\"></tt:contactPerson>\n");
// DL                     else
// DL                     {
// DL                         sb.append( "<tt:contactPerson>\n");
// DL                         XmlTranslator.PersonReach2Xml(sb,attr[x].getContactPerson());
// DL                         sb.append( "</tt:contactPerson>\n");
// DL                     }
// DL                     sb.append("</tt:Item>\n");
// DL                 }
// DL             }
// DL 
// DL             sb.append("</tt:customerRoleAssignmentList>\n");
// DL         }
// DL 
// DL     }
// DL 
// DL     public static void CustomerTroubleNum2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         String attr = ttv.getCustomerTroubleNum();
// DL         if (attr == null)
// DL             sb.append("<tt:CustomerTroubleNum xsi:nil=\"true\"></tt:CustomerTroubleNum>\n");
// DL         else
// DL             sb.append("<tt:customerTroubleNum>" + attr + "</tt:customerTroubleNum>\n");
// DL 
// DL     }
// DL 
// DL     public static void Dialog2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         String attr = ttv.getDialog();
// DL         if (attr == null)
// DL             sb.append("<tt:dialog xsi:nil=\"true\"></tt:dialog>\n");
// DL         else
// DL             sb.append("<tt:dialog>" + attr + "</tt:dialog>\n");
// DL 
// DL     }
// DL 
// DL     public static String escalationListToXMLItems( EscalationList list ){
// DL 
// DL             Escalation[] escArray = list.get();
// DL             StringBuffer sb = new StringBuffer();
// DL 
// DL             sb.append("<co:modifier>SET</co:modifier>\n");
// DL             sb.append("<tt:escalations>\n");
// DL 
// DL             for (int x=0; x<escArray.length; x++)
// DL             {
// DL                 if (escArray[x] == null)
// DL                 {
// DL                     sb.append("<tt:Item xsi:nil=\"true\"></tt:Item>");
// DL                 }
// DL                 else
// DL                 {
// DL                     sb.append("<tt:Item>\n");       //start array item
// DL 
// DL                     PersonReach escaPerson = escArray[x].getEscalationPerson();
// DL                     if ( escaPerson == null )
// DL                         sb.append("<tt:escPerson xsi:nil=\"true\"></tt:escPerson>\n");
// DL                     else
// DL                     {
// DL                         sb.append("<tt:escPerson>\n");
// DL                         XmlTranslator.PersonReach2Xml(sb,escaPerson);
// DL                         sb.append("</tt:escPerson>\n");
// DL                     }
// DL 
// DL 
// DL                     java.util.Date escaTime = escArray[x].getEscalationTime();
// DL                     if ( escaTime == null )
// DL                         sb.append("<tt:escTime xsi:nil=\"true\"></tt:escTime>\n");
// DL                     else
// DL                     {
// DL                         sb.append("<tt:escTime>");
// DL                         XmlTranslator.Date2Xml(sb,escaTime);
// DL                         sb.append("</tt:escTime>\n");
// DL                     }
// DL 
// DL                     XmlTranslator.EnumeratedType2Xml(sb,OrgLevel.class,escArray[x].getLevel());
// DL 
// DL 
// DL                     PersonReach reqPerson = escArray[x].getRequestPerson();
// DL                     if ( reqPerson == null )
// DL                         sb.append("<tt:requestPerson xsi:nil=\"true\"></tt:requestPerson>\n");
// DL                     else
// DL                     {
// DL                         sb.append("<tt:requestPerson>\n");
// DL                         XmlTranslator.PersonReach2Xml(sb,reqPerson);
// DL                         sb.append("</tt:requestPerson>\n");
// DL                     }
// DL 
// DL                     XmlTranslator.EnumeratedType2Xml(sb,RequestState.class,escArray[x].getRequestState());
// DL 
// DL 
// DL                     sb.append("</tt:Item>\n");      //end array item
// DL                 }
// DL            }
// DL            sb.append("</tt:escalations>\n");
// DL 
// DL            return sb.toString();
// DL    }
// DL 
// DL 
// DL     public static void EscalationList2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         EscalationList attr = ttv.getEscalationList();
// DL         if (attr == null) {
// DL             sb.append("<tt:escalationList xsi:nil=\"true\"></tt:escalationList>\n");
// DL             return;
// DL         }
// DL         else
// DL         {
// DL 
// DL             Escalation[] escArray = null;
// DL             try
// DL             {
// DL                 escArray = attr.get();
// DL             }
// DL             catch (java.lang.IllegalStateException ex)
// DL             {
// DL                 //was never set, so set XML to null.
// DL                 sb.append("<tt:escalationList xsi:nil=\"true\"></tt:escalationList>\n");
// DL                 return;
// DL             }
// DL 
// DL             sb.append("<tt:escalationList>\n");
// DL             //PG BUG sb.append("<co:modifier>NONE</co:modifier>\n");
// DL             sb.append("<co:modifier>SET</co:modifier>\n");
// DL             sb.append("<tt:escalations>\n");
// DL 
// DL             for (int x=0; x<escArray.length; x++)
// DL             {
// DL                 if (escArray[x] == null)
// DL                 {
// DL                     sb.append("<tt:Item xsi:nil=\"true\"></tt:Item>");
// DL                 }
// DL                 else
// DL                 {
// DL                     sb.append("<tt:Item>\n");       //start array item
// DL 
// DL                     PersonReach escaPerson = escArray[x].getEscalationPerson();
// DL                     if ( escaPerson == null )
// DL                         sb.append("<tt:escPerson xsi:nil=\"true\"></tt:escPerson>\n");
// DL                     else
// DL                     {
// DL                         sb.append("<tt:escPerson>\n");
// DL                         XmlTranslator.PersonReach2Xml(sb,escaPerson);
// DL                         sb.append("</tt:escPerson>\n");
// DL                     }
// DL 
// DL 
// DL                     java.util.Date escaTime = escArray[x].getEscalationTime();
// DL                     if ( escaTime == null )
// DL                         sb.append("<tt:escTime xsi:nil=\"true\"></tt:escTime>\n");
// DL                     else
// DL                     {
// DL                         sb.append("<tt:escTime>");
// DL                         XmlTranslator.Date2Xml(sb,escaTime);
// DL                         sb.append("</tt:escTime>\n");
// DL                     }
// DL 
// DL                     XmlTranslator.EnumeratedType2Xml(sb,OrgLevel.class,escArray[x].getLevel());
// DL 
// DL 
// DL                     PersonReach reqPerson = escArray[x].getRequestPerson();
// DL                     if ( reqPerson == null )
// DL                         sb.append("<tt:requestPerson xsi:nil=\"true\"></tt:requestPerson>\n");
// DL                     else
// DL                     {
// DL                         sb.append("<tt:requestPerson>\n");
// DL                         XmlTranslator.PersonReach2Xml(sb,reqPerson);
// DL                         sb.append("</tt:requestPerson>\n");
// DL                     }
// DL 
// DL                     XmlTranslator.EnumeratedType2Xml(sb,RequestState.class,escArray[x].getRequestState());
// DL 
// DL 
// DL                     sb.append("</tt:Item>\n");      //end array item
// DL                 }
// DL            }
// DL            sb.append("</tt:escalations>\n");
// DL            sb.append("</tt:escalationList>\n");
// DL        }
// DL 
// DL 
// DL     }
// DL 
// DL     public static void InitiatingMode2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         XmlTranslator.EnumeratedType2Xml(sb,
// DL                                          InitiatingMode.class,
// DL                                          ttv.getInitiatingMode());
// DL     }
// DL 
// DL     public static void LastUpdateTime2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         java.util.Date attr = ttv.getLastUpdateTime();
// DL         if (attr == null)
// DL             sb.append("<tt:lastUpdateTime xsi:nil=\"true\"></tt:lastUpdateTime>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:lastUpdateTime>");
// DL             XmlTranslator.Date2Xml(sb,ttv.getLastUpdateTime());
// DL             sb.append("</tt:lastUpdateTime>\n");
// DL         }
// DL     }
// DL 
// DL     public static void MaintServiceCharge2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         sb.append("<tt:maintServiceCharge>");
// DL         XmlTranslator.Boolean2Xml(sb,ttv.getMaintServiceCharge());
// DL         sb.append("</tt:maintServiceCharge>\n");
// DL     }
// DL 
// DL     public static void Originator2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         String attr = ttv.getOriginator();
// DL         if (attr == null)
// DL             sb.append("<tt:originator xsi:nil=\"true\"></tt:originator>\n");
// DL         else
// DL             sb.append("<tt:originator>" + attr + "</tt:originator>\n");
// DL 
// DL     }
// DL 
// DL     public static void OutageDuration2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         TimeLength attr = ttv.getOutageDuration();
// DL         if (attr == null)
// DL             sb.append("<tt:outageDuration xsi:nil=\"true\"></tt:outageDuration>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:outageDuration>\n");
// DL             XmlTranslator.TimeLength2Xml(sb,ttv.getOutageDuration());
// DL             sb.append("</tt:outageDuration>\n");
// DL         }
// DL     }
// DL 
// DL     public static void ReceivedTime2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         java.util.Date attr = ttv.getReceivedTime();
// DL         if (attr == null)
// DL             sb.append("<tt:receivedTime xsi:nil=\"true\"></tt:receivedTime>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:receivedTime>");
// DL             XmlTranslator.Date2Xml(sb,ttv.getReceivedTime());
// DL             sb.append("</tt:receivedTime>\n");
// DL         }
// DL     }
// DL 
// DL 
// DL     public static void RelatedAlarmList2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         AlarmValueList attr = ttv.getRelatedAlarmList();
// DL         if (attr == null) {
// DL             sb.append("<tt:relatedAlarmList xsi:nil=\"true\"></tt:relatedAlarmList>\n");
// DL             return;
// DL         }
// DL         else
// DL         {
// DL 
// DL             AlarmValue[] alarmArray = null;
// DL             try
// DL             {
// DL                 alarmArray = attr.get();
// DL             }
// DL             catch (java.lang.IllegalStateException ex)
// DL             {
// DL                 sb.append("<tt:relatedAlarmList xsi:nil=\"true\"></tt:relatedAlarmList>\n");
// DL                 return;
// DL             }
// DL 
// DL             sb.append("<tt:relatedAlarmList>\n");
// DL             sb.append("<co:modifier>SET</co:modifier>\n");
// DL             sb.append("<tt:relatedAlarms>\n");
// DL 
// DL             AlarmValue aval = new AlarmValueImpl();
// DL 
// DL             XmlSerializer xmls= (XmlSerializer)aval.makeSerializer(XmlSerializer.class.getName());
// DL 
// DL             for (int x=0; x<alarmArray.length; x++)
// DL             {
// DL                     sb.append(xmls.toXml(alarmArray[x], "Item"));
// DL              }
// DL            sb.append("</tt:relatedAlarms>\n");
// DL            sb.append("</tt:relatedAlarmList>\n");
// DL        }
// DL 
// DL 
// DL     }
// DL 
// DL     public static void RelatedTroubleTicketKeyList2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     throws org.xml.sax.SAXException
// DL     {
// DL 
// DL         TroubleTicketKey[] attr = ttv.getRelatedTroubleTicketKeyList();
// DL 
// DL 
// DL 
// DL         if (attr == null) {
// DL             log ( "&&&&&&&t RelatedTroubleTicketKeyListFromXml is NULL&&&&&&" );
// DL             sb.append("<tt:relatedTroubleTicketKeyList xsi:nil=\"true\"></tt:relatedTroubleTicketKeyList>\n");
// DL             return;
// DL         }
// DL         else
// DL         {
// DL             sb.append("<tt:relatedTroubleTicketKeyList>\n");
// DL 
// DL             for (int x=0;x<attr.length;x++)
// DL             {
// DL                 if (attr[x] == null)
// DL                 {
// DL                     sb.append("<tt:Item xsi:nil=\"true\"></tt:Item>");
// DL                 }
// DL                 else
// DL                 {
// DL                     sb.append("<tt:Item>\n");
// DL                     //XmlTranslator.TroubleTicketKey2Xml(sb,attr[x]);
// DL 
// DL                     sb.append(((TroubleTicketKeyImpl) attr[x]).toXmlBase());
// DL                     sb.append("</tt:Item>\n");
// DL                 }
// DL             }
// DL 
// DL             sb.append("</tt:relatedTroubleTicketKeyList>\n");
// DL         }
// DL     }
// DL 
// DL     public static void RepairActivityList2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         RepairActivityList attr = ttv.getRepairActivityList();
// DL         if (attr == null) {
// DL             sb.append("<tt:repairActivityList xsi:nil=\"true\"></tt:repairActivityList>\n");
// DL             return;
// DL         }
// DL         else
// DL         {
// DL 
// DL             RepairActivity[] raArray = null;
// DL             try
// DL             {
// DL                 raArray = attr.get();
// DL             }
// DL             catch (java.lang.IllegalStateException ex)
// DL             {
// DL                 //was never set, so set XML to null.
// DL                 sb.append("<tt:repairActivityList xsi:nil=\"true\"></tt:repairActivityList>\n");
// DL                 return;
// DL             }
// DL 
// DL 
// DL             sb.append("<tt:repairActivityList>\n");
// DL             //PG BUG sb.append("<co:modifier>NONE</co:modifier>\n");
// DL             sb.append("<co:modifier>SET</co:modifier>\n");
// DL             sb.append("<tt:repairActivities>\n");
// DL             for (int x=0; x<raArray.length; x++)
// DL             {
// DL                 if (raArray[x] == null)
// DL                 {
// DL                     sb.append("<tt:Item xsi:nil=\"true\"></tt:Item>");
// DL                 }
// DL                 else
// DL                 {
// DL                     sb.append("<tt:Item>\n");       //array item
// DL 
// DL 
// DL                     XmlTranslator.EnumeratedType2Xml(sb, ActivityCode.class, raArray[x].getActivityCode());
// DL 
// DL                     String actInfo = raArray[x].getActivityInfo();
// DL                     if (actInfo == null)
// DL                         sb.append("<tt:activityInfo xsi:nil=\"true\"></tt:activityInfo>\n");
// DL                     else
// DL                         sb.append("<tt:activityInfo>" + actInfo + "</tt:activityInfo>\n");
// DL 
// DL                     PersonReach actPers = raArray[x].getActivityPerson();
// DL                     if (actPers == null)
// DL                         sb.append("<tt:activityPerson xsi:nil=\"true\"></tt:activityPerson>\n");
// DL                     else
// DL                     {
// DL                         sb.append("<tt:activityPerson>\n");
// DL                         XmlTranslator.PersonReach2Xml(sb,actPers);
// DL                         sb.append("</tt:activityPerson>\n");
// DL                     }
// DL                     java.util.Date entryTm = raArray[x].getEntryTime();
// DL                     if (entryTm == null)
// DL                         sb.append("<tt:entryTime xsi:nil=\"true\"></tt:entryTime>\n");
// DL                     else
// DL                     {
// DL                         sb.append("<tt:entryTime>");
// DL                         XmlTranslator.Date2Xml(sb,entryTm);
// DL                         sb.append("</tt:entryTime>\n");
// DL                     }
// DL                     sb.append("</tt:Item>\n");      //end array item
// DL                 }
// DL             }
// DL             sb.append("</tt:repairActivities>\n");
// DL             sb.append("</tt:repairActivityList>\n");
// DL         }
// DL 
// DL     }
// DL 
// DL     public static void RepeatReport2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         XmlTranslator.EnumeratedType2Xml(sb,
// DL                                          RepeatReport.class,
// DL                                          ttv.getRepeatReport());
// DL     }
// DL 
// DL     public static void RestoredTime2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         java.util.Date attr = ttv.getRestoredTime();
// DL         if (attr == null)
// DL             sb.append("<tt:restoredTime xsi:nil=\"true\"></tt:restoredTime>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:restoredTime>");
// DL             XmlTranslator.Date2Xml(sb,ttv.getRestoredTime());
// DL             sb.append("</tt:restoredTime>\n");
// DL         }
// DL     }
// DL 
// DL     public static void ServiceUnavailableBeginTime2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         java.util.Date attr = ttv.getServiceUnavailableBeginTime();
// DL         if (attr == null)
// DL             sb.append("<tt:serviceUnavailableBeginTime xsi:nil=\"true\"></tt:serviceUnavailableBeginTime>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:serviceUnavailableBeginTime>");
// DL             XmlTranslator.Date2Xml(sb,ttv.getServiceUnavailableBeginTime());
// DL             sb.append("</tt:serviceUnavailableBeginTime>\n");
// DL         }
// DL     }
// DL 
// DL     public static void ServiceUnavailableEndTime2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         java.util.Date attr = ttv.getServiceUnavailableEndTime();
// DL         if (attr == null)
// DL             sb.append("<tt:serviceUnavailableEndTime xsi:nil=\"true\"></tt:serviceUnavailableEndTime>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:serviceUnavailableEndTime>");
// DL             XmlTranslator.Date2Xml(sb,ttv.getServiceUnavailableEndTime());
// DL             sb.append("</tt:serviceUnavailableEndTime>\n");
// DL         }
// DL     }
// DL 
// DL     public static void SPRoleAssignmentList2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         SPRoleAssignment[] attr = ttv.getSPRoleAssignmentList();
// DL         if (attr == null)
// DL             sb.append("<tt:sPRoleAssignmentList xsi:nil=\"true\"></tt:sPRoleAssignmentList>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:sPRoleAssignmentList>\n");
// DL 
// DL             for (int x=0; x<attr.length; x++)
// DL             {
// DL                 if (attr[x] == null)
// DL                 {
// DL                     sb.append("<tt:Item xsi:nil=\"true\"></tt:Item>");
// DL                 }
// DL                 else
// DL                 {
// DL                     sb.append("<tt:Item>\n");
// DL 
// DL                     String role = attr[x].getRole();
// DL                     if (role == null)
// DL                         sb.append("<tt:role xsi:nil=\"true\"></tt:role>\n");
// DL                     else
// DL                         sb.append("<tt:role>" + role + "</tt:role>\n");
// DL 
// DL                     PersonReach cp = attr[x].getContactPerson();
// DL                     if (cp == null)
// DL                         sb.append("<tt:contactPerson xsi:nil=\"true\"></tt:contactPerson>\n");
// DL                     else
// DL                     {
// DL                         sb.append("<tt:contactPerson>\n");
// DL                         XmlTranslator.PersonReach2Xml(sb,cp);
// DL                         sb.append("</tt:contactPerson>\n");
// DL                     }
// DL 
// DL                     sb.append("</tt:Item>\n");
// DL                 }
// DL             }
// DL 
// DL             sb.append("</tt:sPRoleAssignmentList>\n");
// DL         }
// DL 
// DL     }
// DL 
// DL     public static void SuspectObjectList2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         SuspectObject[] attr = ttv.getSuspectObjectList();
// DL         if (attr == null)
// DL             sb.append("<tt:suspectObjectList xsi:nil=\"true\"></tt:suspectObjectList>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:suspectObjectList>\n");
// DL 
// DL             for (int x=0; x<attr.length; x++)
// DL             {
// DL                 if (attr[x] == null)
// DL                 {
// DL                     sb.append("<tt:Item xsi:nil=\"true\"></tt:Item>");
// DL                 }
// DL                 else
// DL                 {
// DL                     sb.append("<tt:Item>\n");
// DL 
// DL                     String sot = attr[x].getSuspectObjectType();
// DL                     if (sot == null)
// DL                         sb.append("<tt:suspectObjectType xsi:nil=\"true\"></tt:suspectObjectType>\n");
// DL                     else
// DL                         sb.append("<tt:suspectObjectType>" + sot + "</tt:suspectObjectType>\n");
// DL 
// DL                     String sid = attr[x].getSuspectObjectId();
// DL                     if (sid == null)
// DL                         sb.append("<tt:suspectObjectId xsi:nil=\"true\"></tt:suspectObjectId>\n");
// DL                     else
// DL                         sb.append("<tt:suspectObjectId>" + sid + "</tt:suspectObjectId>\n");
// DL 
// DL                     //FailureProbability - int (primitive) no null equivalent
// DL                     sb.append("<tt:failureProbability>");
// DL                     sb.append( String.valueOf(attr[x].getFailureProbability() ));
// DL                     sb.append("</tt:failureProbability>\n");
// DL 
// DL                     sb.append("</tt:Item>\n");
// DL                 }
// DL             }
// DL 
// DL             sb.append("</tt:suspectObjectList>\n");
// DL         }
// DL 
// DL     }
// DL 
// DL     public static void TroubleDescription2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         String attr = ttv.getTroubleDescription();
// DL         if (attr == null)
// DL             sb.append("<tt:troubleDescription xsi:nil=\"true\"></tt:troubleDescription>\n");
// DL         else
// DL             sb.append("<tt:troubleDescription>" + attr + "</tt:troubleDescription>\n");
// DL     }
// DL 
// DL     public static void TroubleFound2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         XmlTranslator.EnumeratedType2Xml(sb,
// DL                                          TroubleFound.class,
// DL                                          ttv.getTroubleFound());
// DL     }
// DL 
// DL     public static void TroubleLocation2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         String attr = ttv.getTroubleLocation();
// DL         if (attr == null)
// DL             sb.append("<tt:troubleLocation xsi:nil=\"true\"></tt:troubleLocation>\n");
// DL         else
// DL             sb.append("<tt:troubleLocation>" + attr + "</tt:troubleLocation>\n");
// DL     }
// DL 
// DL     public static void TroubleNumList2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         String[] attr = ttv.getTroubleNumList();
// DL         if (attr == null)
// DL             sb.append("<tt:troubleNumList xsi:nil=\"true\"></tt:troubleNumList>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:troubleNumList>\n");
// DL 
// DL             for (int x=0; x<attr.length; x++)
// DL             {
// DL                 if (attr[x] == null)
// DL                     sb.append("<co:Item xsi:nil=\"true\"></co:Item>\n");
// DL                 else
// DL                     sb.append("<co:Item>" + attr[x] + "</co:Item>\n");
// DL             }
// DL 
// DL             sb.append("</tt:troubleNumList>\n");
// DL         }
// DL     }
// DL 
// DL     public static void TroubledObject2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         String attr = ttv.getTroubledObject();
// DL         if (attr == null)
// DL             sb.append("<tt:troubledObject xsi:nil=\"true\"></tt:troubledObject>\n");
// DL         else
// DL             sb.append("<tt:troubledObject>" + attr + "</tt:troubledObject>\n");
// DL     }
// DL 
// DL     public static void TroubleType2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         XmlTranslator.EnumeratedType2Xml(sb,
// DL                                          TroubleType.class,
// DL                                          ttv.getTroubleType());
// DL     }
// DL 
// DL     public static void TroubleState2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         XmlTranslator.EnumeratedType2Xml(sb,
// DL                                          TroubleState.class,
// DL                                          ttv.getTroubleState());
// DL     }
// DL 
// DL     public static void TroubleStatus2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         XmlTranslator.EnumeratedType2Xml(sb,
// DL                                          TroubleStatus.class,
// DL                                          ttv.getTroubleStatus());
// DL     }
// DL 
// DL     public static void TroubleStatusTime2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         java.util.Date attr = ttv.getTroubleStatusTime();
// DL         if (attr == null)
// DL             sb.append("<tt:troubleStatusTime xsi:nil=\"true\"></tt:troubleStatusTime>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:troubleStatusTime>");
// DL             XmlTranslator.Date2Xml(sb,attr);
// DL             sb.append("</tt:troubleStatusTime>\n");
// DL         }
// DL     }
// DL 
// DL     public static void PerceivedTroubleSeverity2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         XmlTranslator.EnumeratedType2Xml(sb,
// DL                                          PerceivedTroubleSeverity.class,
// DL                                          ttv.getPerceivedTroubleSeverity());
// DL     }
// DL 
// DL     public static void PreferredPriority2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         XmlTranslator.EnumeratedType2Xml(sb,
// DL                                          PreferredPriority.class,
// DL                                          ttv.getPreferredPriority());
// DL     }
// DL 
// DL     public static void TroubleDetectionTime2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         java.util.Date attr = ttv.getTroubleDetectionTime();
// DL         if (attr == null)
// DL             sb.append("<tt:troubleDetectionTime xsi:nil=\"true\"></tt:troubleDetectionTime>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:troubleDetectionTime>");
// DL             XmlTranslator.Date2Xml(sb,attr);
// DL             sb.append("</tt:troubleDetectionTime>\n");
// DL         }
// DL     }
// DL 
// DL     public static void TroubleLocationInfoList2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         TroubleLocationInfo[] attr = ttv.getTroubleLocationInfoList();
// DL         if (attr == null)
// DL             sb.append("<tt:troubleLocationInfoList xsi:nil=\"true\"></tt:troubleLocationInfoList>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:troubleLocationInfoList>\n");
// DL 
// DL             for (int x=0; x<attr.length; x++)
// DL             {
// DL 
// DL                 if (attr[x] == null)
// DL                 {
// DL                     sb.append("<tt:Item xsi:nil=\"true\"></tt:Item>\n");
// DL                 }
// DL                 else
// DL                 {
// DL                     sb.append("<tt:Item>\n");
// DL 
// DL                     AccessHours[] accHrs = attr[x].getAccessHoursList();
// DL                     if (accHrs == null)
// DL                         sb.append("<tt:accessHoursList xsi:nil=\"true\"></tt:accessHoursList>\n");
// DL                     else
// DL                     {
// DL                         sb.append("<tt:accessHoursList>\n");
// DL                         for (int y=0; y<accHrs.length;y++ )
// DL                         {
// DL                             sb.append("<tt:Item>\n");
// DL                             XmlTranslator.AccessHours2Xml(sb,accHrs[y]);
// DL                             sb.append("</tt:Item>\n");
// DL                         }
// DL                         sb.append("</tt:accessHoursList>\n");
// DL                     }
// DL 
// DL                                         PersonReach locPerson = attr[x].getLocationPerson();
// DL                     if (locPerson == null)
// DL                         sb.append("<tt:locationPerson xsi:nil=\"true\"></tt:locationPerson>\n");
// DL                     else
// DL                     {
// DL                         sb.append("<tt:locationPerson>\n");
// DL                         XmlTranslator.PersonReach2Xml(sb,locPerson);
// DL                         sb.append("</tt:locationPerson>\n");
// DL                     }
// DL 
// DL 
// DL 
// DL                     String oid= attr[x].getObjectIdDN();
// DL                     if (oid == null)
// DL                         sb.append("<tt:objectIdDN xsi:nil=\"true\"></tt:objectIdDN>\n");
// DL                     else
// DL                         sb.append("<tt:objectIdDN>" + oid + "</tt:objectIdDN>\n");
// DL 
// DL 
// DL                     NorthAmericaAddress addr = (NorthAmericaAddress) attr[x].getPremiseAddress();
// DL                     if (addr == null)
// DL                         sb.append("<tt:premiseAddress xsi:nil=\"true\"></tt:premiseAddress>\n");
// DL                     else
// DL                     {
// DL                         sb.append("<tt:premiseAddress xsi:type=\"NorthAmericaAddress\">\n");
// DL                         XmlTranslator.Address2Xml(sb,addr);
// DL                         sb.append("</tt:premiseAddress>\n");
// DL                     }
// DL 
// DL                     String pn = attr[x].getPremiseName();
// DL                     if (pn == null)
// DL                         sb.append("<tt:premiseName xsi:nil=\"true\"></tt:premiseName>\n");
// DL                     else
// DL                         sb.append("<tt:premiseName>" + pn + "</tt:premiseName>\n");
// DL 
// DL 
// DL                     sb.append("</tt:Item>\n");  //end array item
// DL                 }
// DL             }
// DL 
// DL             sb.append("</tt:troubleLocationInfoList>\n");
// DL         }
// DL 
// DL 
// DL     }
// DL 
// DL     public static void TroubledObjectAccessFromTime2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         java.util.Date attr = ttv.getTroubledObjectAccessFromTime();
// DL         if (attr == null)
// DL             sb.append("<tt:troubledObjectAccessFromTime xsi:nil=\"true\"></tt:troubledObjectAccessFromTime>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:troubledObjectAccessFromTime>");
// DL             XmlTranslator.Date2Xml(sb,ttv.getTroubledObjectAccessFromTime());
// DL             sb.append("</tt:troubledObjectAccessFromTime>\n");
// DL         }
// DL     }
// DL 
// DL     public static void TroubledObjectAccessHoursList2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         AccessHours[] attr = ttv.getTroubledObjectAccessHoursList();
// DL         if (attr == null)
// DL             sb.append("<tt:troubledObjectAccessHoursList xsi:nil=\"true\"></tt:troubledObjectAccessHoursList>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:troubledObjectAccessHoursList>\n");
// DL 
// DL             for (int x=0;x<attr.length;x++ )
// DL             {
// DL                 if (attr[x] == null)
// DL                 {
// DL                     sb.append("<tt:Item xsi:nil=\"true\"></tt:Item>");
// DL                 }
// DL                 else
// DL                 {
// DL                     sb.append("<tt:Item>\n");
// DL                     XmlTranslator.AccessHours2Xml(sb,attr[x]);
// DL                     sb.append("</tt:Item>\n");
// DL                 }
// DL             }
// DL 
// DL             sb.append("</tt:troubledObjectAccessHoursList>\n");
// DL         }
// DL     }
// DL 
// DL     public static void TroubledObjectAccessToTime2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         java.util.Date attr = ttv.getTroubledObjectAccessToTime();
// DL         if (attr == null)
// DL             sb.append("<tt:troubledObjectAccessToTime xsi:nil=\"true\"></tt:troubledObjectAccessToTime>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:troubledObjectAccessToTime>");
// DL             XmlTranslator.Date2Xml(sb,ttv.getTroubledObjectAccessToTime());
// DL             sb.append("</tt:troubledObjectAccessToTime>\n");
// DL         }
// DL     }
// DL 
// DL     public static void TroubleSystemDN2Xml(StringBuffer sb,TroubleTicketValue ttv)
// DL     {
// DL         String attr = ttv.getTroubleSystemDN();
// DL         if (attr == null)
// DL             sb.append("<tt:troubleSystemDN xsi:nil=\"true\"></tt:troubleSystemDN>\n");
// DL         else
// DL             sb.append("<tt:troubleSystemDN>" + attr + "</tt:troubleSystemDN>\n");
// DL     }
// DL 
// DL 
// DL 
// DL     //----------------------------------------------------------------------------
// DL     //
// DL     //                  "To XML" for sub-types
// DL     //
// DL     //----------------------------------------------------------------------------
// DL 
// DL     public static void AccessHours2Xml(StringBuffer sb,AccessHours ah)
// DL     {
// DL 
// DL         if (ah != null)
// DL         {
// DL             XmlTranslator.EnumeratedType2Xml(sb,DayOfWeek.class,ah.getDayOfWeek());
// DL 
// DL             TimeInterval[] ti = ah.getTimeIntervalList();
// DL             if (ti == null)
// DL                 sb.append("<tt:timeIntervalList xsi:nil=\"true\"></tt:timeIntervalList>\n");
// DL             else
// DL             {
// DL                 sb.append("<tt:timeIntervalList>\n");
// DL                 for (int x=0;x<ti.length;x++)
// DL                 {
// DL                     sb.append("<tt:Item>\n");
// DL                     XmlTranslator.TimeInterval2Xml(sb,ti[x]);
// DL                     sb.append("</tt:Item>\n");
// DL                 }
// DL                 sb.append("</tt:timeIntervalList>\n");
// DL             }
// DL         }
// DL 
// DL     }
// DL 
// DL     public static void ActivityDuration2Xml(StringBuffer sb,ActivityDuration ad)
// DL     {
// DL         if (ad != null)
// DL         {
// DL 
// DL             TimeLength tl = ad.getDuration();
// DL             if (tl == null)
// DL                 sb.append("<tt:duration xsi:nil=\"true\"></tt:duration>\n");
// DL             else
// DL             {
// DL                 sb.append("<tt:duration>\n");
// DL                 XmlTranslator.TimeLength2Xml(sb,tl);
// DL                 sb.append("</tt:duration>\n");
// DL             }
// DL 
// DL             sb.append("<tt:billable>");
// DL             XmlTranslator.Boolean2Xml(sb,ad.getBillable());
// DL             sb.append("</tt:billable>\n");
// DL 
// DL             XmlTranslator.EnumeratedType2Xml(sb,ActivityType.class,ad.getActivityType());
// DL         }
// DL     }
// DL 
// DL 
// DL     //-----------------------------------------------------------------------------
// DL     // Maps the enumerated value (the int value) to its String equivalent using the
// DL     // interface reflector
// DL     //-----------------------------------------------------------------------------
// DL 
// DL     public static String EnumeratedType2Xml( Class interfaceClass, int enumInt )
// DL     {
// DL         String enumStrName = null;
// DL 
// DL         try
// DL         {
// DL             enumStrName = (String)
// DL                 (ttIntReflector.getInterfaceFieldName(interfaceClass,enumInt));
// DL 
// DL 
// DL 
// DL         }
// DL         catch( javax.oss.IllegalArgumentException iLLEx )
// DL         {
// DL             //throw new org.xml.sax.SAXException( iLLEx.getMessage() );
// DL             logException (iLLEx);
// DL         }
// DL         catch( java.lang.IllegalAccessException iLLAccEx )
// DL         {
// DL             //throw new org.xml.sax.SAXException( iLLAccEx.getMessage() );
// DL             logException (iLLAccEx);
// DL         }
// DL         return enumStrName;
// DL     }
// DL 
// DL     public static void EnumeratedType2Xml(StringBuffer sb,
// DL                                           Class interfaceClass,
// DL                                           int enumInt)
// DL     {
// DL 
// DL         String enumStrName = null;
// DL 
// DL         try
// DL         {
// DL             enumStrName = (String)
// DL                 (ttIntReflector.getInterfaceFieldName(interfaceClass,enumInt));
// DL 
// DL 
// DL 
// DL         }
// DL         catch( javax.oss.IllegalArgumentException iLLEx )
// DL         {
// DL             //throw new org.xml.sax.SAXException( iLLEx.getMessage() );
// DL             logException (iLLEx);
// DL         }
// DL         catch( java.lang.IllegalAccessException iLLAccEx )
// DL         {
// DL             //throw new org.xml.sax.SAXException( iLLAccEx.getMessage() );
// DL             logException (iLLAccEx);
// DL         }
// DL 
// DL         //-----------------------------------------------------------------------------
// DL         // The interface class name corresponds to the XML tag.
// DL         // Class.getName() returns the fully qualified name, so strip out the leaf node.
// DL         // -1 is the default for non-populated enum values.
// DL         //-----------------------------------------------------------------------------
// DL         String fqClassName = interfaceClass.getName();
// DL         //log  ("EnumeratedType2Xml - interface class name: " + fqClassName);
// DL         String classNameTxt = fqClassName.substring((fqClassName.lastIndexOf(".")) + 1);
// DL          //lower case first character
// DL          String lower = classNameTxt.substring(0,1);
// DL          //log ("****lower= " + lower );
// DL          String restOf = classNameTxt.substring( 1 ) ;
// DL          //log ("****restOf= " + restOf );
// DL          String className = lower.toLowerCase() + restOf;
// DL          //log ("****className= " + className );
// DL 
// DL 
// DL 
// DL         if (enumInt == -1)      //if not set (unpopulated)
// DL             sb.append("<tt:" + className + " xsi:nil=\"true\">" + "</tt:" + className + ">\n");
// DL         else
// DL             sb.append("<tt:" + className + ">" + enumStrName + "</tt:" + className + ">\n");
// DL 
// DL 
// DL     }
// DL 
// DL 
// DL     public static void Authorization2Xml(StringBuffer sb,Authorization auth)
// DL     {
// DL 
// DL         if (auth != null)
// DL         {
// DL 
// DL             XmlTranslator.EnumeratedType2Xml(sb,ActivityType.class,auth.getActivityType());
// DL 
// DL             sb.append("<tt:authPerson>\n");
// DL             XmlTranslator.PersonReach2Xml(sb,auth.getAuthPerson());
// DL             sb.append("</tt:authPerson>\n");
// DL 
// DL             sb.append("<tt:authTime>");
// DL             XmlTranslator.Date2Xml(sb,auth.getAuthTime());
// DL             sb.append("</tt:authTime>\n");
// DL 
// DL 
// DL             XmlTranslator.EnumeratedType2Xml(sb,RequestState.class,auth.getRequestState());
// DL         }
// DL         else
// DL         {
// DL 
// DL 
// DL 
// DL 
// DL         }
// DL 
// DL     }
// DL 
// DL 
// DL     public static void PersonReach2Xml(StringBuffer sb,PersonReach pr)
// DL     {
// DL 
// DL         if (pr != null)
// DL         {
// DL             sb.append("<tt:email>");
// DL             sb.append((String)(pr.getEmail()));
// DL             sb.append("</tt:email>\n");
// DL 
// DL             sb.append("<tt:fax>");
// DL             sb.append((String)(pr.getFax()));
// DL             sb.append("</tt:fax>\n");
// DL 
// DL             sb.append("<tt:location xsi:type=\"NorthAmericaAddress\">\n");
// DL             XmlTranslator.Address2Xml(sb,(NorthAmericaAddress) pr.getLocation());
// DL             sb.append("</tt:location>\n");
// DL 
// DL             sb.append("<tt:name>");
// DL             sb.append((String)(pr.getName()));
// DL             sb.append("</tt:name>\n");
// DL 
// DL             sb.append("<tt:number>");
// DL             sb.append((String)(pr.getNumber()));
// DL             sb.append("</tt:number>\n");
// DL 
// DL             sb.append("<tt:organizationName>");
// DL             sb.append((String)(pr.getOrganizationName()));
// DL             sb.append("</tt:organizationName>\n");
// DL 
// DL             sb.append("<tt:phone>");
// DL             sb.append((String)(pr.getPhone()));
// DL             sb.append("</tt:phone>\n");
// DL 
// DL             sb.append("<tt:responsible>");
// DL             sb.append((String)(pr.getResponsible()));
// DL             sb.append("</tt:responsible>\n");
// DL 
// DL             sb.append("<tt:sMSAddress>");
// DL             sb.append((String)(pr.getSMSAddress()));
// DL             sb.append("</tt:sMSAddress>\n");
// DL         }
// DL 
// DL     }
// DL 
// DL     public static void Address2Xml(StringBuffer sb,NorthAmericaAddress addr)
// DL     {
// DL 
// DL         if (addr != null)
// DL         {
// DL             sb.append("<tt:addressInfo>");
// DL             sb.append((String)(addr.getAddressInfo()));
// DL             sb.append("</tt:addressInfo>\n");
// DL 
// DL             sb.append("<tt:civicAddress>");
// DL             sb.append((String)(addr.getCivicAddress()));
// DL             sb.append("</tt:civicAddress>\n");
// DL 
// DL             sb.append("<tt:city>");
// DL             sb.append((String)(addr.getCity()));
// DL             sb.append("</tt:city>\n");
// DL 
// DL             sb.append("<tt:state>");
// DL             sb.append((String)(addr.getState()));
// DL             sb.append("</tt:state>\n");
// DL 
// DL             sb.append("<tt:zip>");
// DL             sb.append((String)(addr.getZip()));
// DL             sb.append("</tt:zip>\n");
// DL         }
// DL         else
// DL         {
// DL             //log ("null Address in Address2Xml");
// DL         }
// DL 
// DL     }
// DL 
// DL 
// DL     public static void Boolean2Xml(StringBuffer sb,boolean bool)
// DL     {
// DL         //Since boolean is a primitive type, assume that there is no "null" value
// DL         //for it - it's always either true or false.  May want to revisit this later.
// DL         if (bool)
// DL             sb.append("true");
// DL         else
// DL             sb.append("false");
// DL     }
// DL 
// DL     public static void TimeLength2Xml(StringBuffer sb,TimeLength tl)
// DL     {
// DL 
// DL         if (tl != null)
// DL         {
// DL             sb.append("<tt:years>"   + tl.getYears()   + "</tt:years>\n");
// DL             sb.append("<tt:months>"  + tl.getMonths()  + "</tt:months>\n");
// DL             sb.append("<tt:days>"    + tl.getDays()    + "</tt:days>\n");
// DL             sb.append("<tt:hours>"   + tl.getHours()   + "</tt:hours>\n");
// DL             sb.append("<tt:minutes>" + tl.getMinutes() + "</tt:minutes>\n");
// DL             sb.append("<tt:seconds>" + tl.getSeconds() + "</tt:seconds>\n");
// DL             sb.append("<tt:msecs>"   + tl.getMsecs()   + "</tt:msecs>\n");
// DL         }
// DL     }
// DL 
// DL     public static void Time2Xml(StringBuffer sb,javax.oss.trouble.Time tm)
// DL     {
// DL         if (tm != null)
// DL         {
// DL             sb.append("<tt:hour>"         + tm.getHour()         + "</tt:hour>\n");
// DL             sb.append("<tt:minute>"       + tm.getMinute()       + "</tt:minute>\n");
// DL             sb.append("<tt:seconds>"      + tm.getSeconds()      + "</tt:seconds>\n");
// DL             sb.append("<tt:msecs>"        + tm.getMilliSeconds() + "</tt:msecs>\n");
// DL             sb.append("<tt:utcOffset>"    + tm.getUtcOffset()    + "</tt:utcOffset>\n");
// DL         }
// DL     }
// DL 
// DL     public static void TimeInterval2Xml(StringBuffer sb,TimeInterval ti)
// DL     {
// DL 
// DL         if (ti != null)
// DL         {
// DL             sb.append("<tt:intervalBegin>\n");
// DL             XmlTranslator.Time2Xml(sb,ti.getIntervalBegin());
// DL             sb.append("</tt:intervalBegin>\n");
// DL 
// DL             sb.append("<tt:intervalEnd>\n");
// DL             XmlTranslator.Time2Xml(sb,ti.getIntervalEnd());
// DL             sb.append("</tt:intervalEnd>\n");
// DL         }
// DL     }
// DL 
// DL 
// DL     //-----------------------------------------------------------------------------------
// DL     // For now, use Date2Xml.  A future version may use a different idiom
// DL     // since java.util.Date is being deprecated.
// DL     //-----------------------------------------------------------------------------------
// DL     public static void Date2Xml(StringBuffer sb, java.util.Date date )
// DL     {
// DL 
// DL         if (date == null)
// DL         {
// DL             sb.append( "0000-00-00T00:00:00Z" );        //Zero time if date is null
// DL         }
// DL         else
// DL         {
// DL             StringTokenizer stTokenizer = new StringTokenizer(date.toGMTString());
// DL 
// DL             String day;
// DL             String month;
// DL             String year;
// DL             String time;
// DL             String zone;
// DL 
// DL             day = stTokenizer.nextToken();
// DL             if (Integer.parseInt(day) < 10)
// DL             {
// DL                 day = "0" + day;
// DL             }
// DL             month = stTokenizer.nextToken();
// DL             year  = stTokenizer.nextToken();
// DL             time  = stTokenizer.nextToken();
// DL             zone  = stTokenizer.nextToken();
// DL 
// DL             
// DL 
// DL             //----------------------------------------------------------------
// DL             // Convert from java Date format into XML timeInstant format.
// DL             //   date format:  7 Mar 2001 10:10:10 GMT
// DL             //    xml format:  2001-03-07T10:10:10Z
// DL             //----------------------------------------------------------------
// DL             String xmlStr = year + "-" + (String)monthHash.get(month) + "-" + day + "T" + time + "Z";
// DL 
// DL             //log ("xmlStr: " + xmlStr );
// DL             sb.append(xmlStr);
// DL         }
// DL 
// DL 
// DL     }
// DL 
// DL 
// DL     //-----------------------------------------------------------------------------------
// DL     //
// DL     // Xml response encoding.  TroubleTicketKeyResults
// DL     //
// DL     //-----------------------------------------------------------------------------------
// DL     public static String toXml(TroubleTicketKeyResult[] keyResults )
// DL     throws org.xml.sax.SAXException
// DL     {
// DL 
// DL         StringBuffer sb = new StringBuffer();
// DL 
// DL         if (keyResults == null)
// DL             sb.append("<tt:results xsi:nil=\"true\"></tt:results>\n");
// DL         else
// DL         {
// DL             sb.append("<tt:results>\n");
// DL 
// DL             for (int x=0; x<keyResults.length; x++)
// DL             {
// DL                 if (keyResults[x] == null)
// DL                 {
// DL                     sb.append("<tt:Item xsi:nil=\"true\"></tt:Item>");
// DL                 }
// DL                 else
// DL                 {
// DL                     sb.append("<tt:Item>\n");
// DL 
// DL 
// DL                     sb.append("<co:success>");
// DL                     XmlTranslator.Boolean2Xml(sb,keyResults[x].isSuccess());
// DL                     sb.append("</co:success>\n");
// DL                     Exception exception = keyResults[x].getException();
// DL 
// DL                     if (exception != null)
// DL                       sb.append("<tt:exception>\n" + exception.toString() + "</tt:exception>\n");
// DL                      TroubleTicketKey key = keyResults[x].getTroubleTicketKey();
// DL                      sb.append(((TroubleTicketKeyImpl) key ).toXml( "tt:troubleTicketKey" ));
// DL                     //TroubleTicketKey2Xml(sb,keyResults[x].getTroubleTicketKey());
// DL                     sb.append("</tt:Item>\n");
// DL                 }
// DL             }
// DL             sb.append("</tt:results>\n");
// DL 
// DL         }
// DL 
// DL         return sb.toString();
// DL 
// DL     }
// DL 
// DL 
// DL     //****************************************************************************
// DL     //
// DL     //                            FROM XML
// DL     //
// DL     //****************************************************************************
// DL 
// DL     // - single TT value or template
// DL     public static void fromXml( org.w3c.dom.Element doc, TroubleTicketValue ttv )
// DL     throws org.xml.sax.SAXException
// DL     {
// DL 
// DL         //log ("***************************************************************");
// DL         log ("*    XmlTranslator: fromXml (single TT value/template)        *");
// DL         //log ("***************************************************************");
// DL 
// DL         Element docElement = ((Document)doc).getDocumentElement();
// DL         String reqType = docElement.getTagName();
// DL         log ("Doc root element (request type): " + reqType);
// DL 
// DL         // Determine if we are getting a value or template based on request type
// DL         NodeList nodeList = null;
// DL         Node node = null;
// DL 
// DL         nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket","troubleTicketValue");
// DL         node = nodeList.item(0);
// DL         if( node == null ) {
// DL 
// DL            nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket","template");
// DL            node = nodeList.item(0);
// DL         }
// DL 
// DL         if (node != null)
// DL             fromXmlNoRoot(node,ttv);
// DL 
// DL     }
// DL 
// DL     public static void fromXmlTag( org.w3c.dom.Element doc, TroubleTicketValue ttv, String tag )
// DL     throws org.xml.sax.SAXException
// DL     {
// DL 
// DL         //log ("***************************************************************");
// DL         log ("*    XmlTranslator: fromXml (single TT value/template)        *");
// DL         //log ("***************************************************************");
// DL 
// DL         Element docElement = ((Document)doc).getDocumentElement();
// DL         String reqType = docElement.getTagName();
// DL         log ("Doc root element (request type): " + reqType);
// DL 
// DL         // Determine if we are getting a value or template based on request type
// DL         NodeList nodeList = null;
// DL         Node node = null;
// DL 
// DL         nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket",tag);
// DL         node = nodeList.item(0);
// DL 
// DL 
// DL         if (node != null)
// DL             fromXmlNoRoot(node,ttv);
// DL 
// DL     }
// DL 
// DL 
// DL 
// DL 
// DL     // multiple TT values or templates
// DL     public static TroubleTicketValue[] fromXml( org.w3c.dom.Element doc )
// DL     throws org.xml.sax.SAXException
// DL     {
// DL 
// DL         //log ("***************************************************************");
// DL         log ("*    XmlTranslator: fromXml (multiple TT values/templates)    *");
// DL         //log ("***************************************************************");
// DL 
// DL 
// DL         Element docElement = ((Document)doc).getDocumentElement();
// DL         String reqType = docElement.getTagName();
// DL         log ("Doc root element is: " + reqType);
// DL 
// DL         // Determine the request type
// DL         NodeList nodeList = null;
// DL 
// DL         if ( reqType.equals("tt:tryCreateTroubleTicketsByValuesRequest")    ||  //best effort
// DL              reqType.equals("tt:trySetTroubleTicketsByValuesRequest")       ||  //""
// DL              reqType.equals("tt:createTroubleTicketsByValuesRequest")       ||  //atomic
// DL              reqType.equals("tt:setTroubleTicketsByValuesRequest") )            //""
// DL         {
// DL             nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket","TroubleTicketValueList");
// DL         }
// DL         else
// DL         if ( reqType.equals("tt:getTroubleTicketsByTemplatesRequest")           ||
// DL              reqType.equals("tt:trySetTroubleTicketsByValuesRequest")           ||  //best effort
// DL              reqType.equals("tt:tryCloseTroubleTicketsByTemplatesRequest")      ||  //""
// DL              reqType.equals("tt:tryCancelTroubleTicketsByTemplatesRequest")     ||  //""
// DL              reqType.equals("tt:tryEscalateTroubleTicketsByTemplatesRequest")   ||  //""
// DL              reqType.equals("tt:setTroubleTicketsByValuesRequest")              ||  //atomic
// DL              reqType.equals("tt:closeTroubleTicketsByTemplatesRequest")         ||  //""
// DL              reqType.equals("tt:cancelTroubleTicketsByTemplatesRequest")        ||  //""
// DL              reqType.equals("tt:escalateTroubleTicketsByTemplatesRequest") )        //""
// DL         {
// DL             nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket","TroubleTicketTemplateList");
// DL         }
// DL 
// DL         Node node = null;
// DL         if (nodeList != null)
// DL         {
// DL             node = nodeList.item(0);
// DL             if (node == null)
// DL             {
// DL                 String nullNode = new String("null Node in XmlTranslator.fromXml (multiple values)");
// DL                 log (nullNode);
// DL                 throw new org.xml.sax.SAXException(nullNode);
// DL             }
// DL         }
// DL 
// DL         return TTValuesFromXml(node);
// DL     }
// DL     public static void fromXmlNoRoot(TroubleTicketKey ttkey, Node node)
// DL     throws org.xml.sax.SAXException
// DL     {
// DL         ttkey = TroubleTicketKeyFromXml(node);
// DL     }
// DL 
// DL     public static void fromXmlNoRoot(TroubleTicketCreateEvent event, Node node)
// DL     throws org.xml.sax.SAXException
// DL     {
// DL 
// DL     }
// DL      public static void fromXmlNoRoot(TroubleTicketCloseOutEvent event, Node node)
// DL     throws org.xml.sax.SAXException
// DL     {
// DL 
// DL     }
// DL 
// DL      public static void fromXmlNoRoot(TroubleTicketCancellationEvent event, Node node)
// DL     throws org.xml.sax.SAXException
// DL     {
// DL 
// DL     }
// DL 
// DL      public static void fromXmlNoRoot(TroubleTicketAttributeValueChangeEvent event, Node node)
// DL     throws org.xml.sax.SAXException
// DL     {
// DL 
// DL     }
// DL 
// DL      public static void fromXmlNoRoot(TroubleTicketStatusChangeEvent event, Node node)
// DL     throws org.xml.sax.SAXException
// DL     {
// DL 
// DL     }
// DL 
// DL     public static void fromXmlNoRoot(QueryAllOpenTroubleTicketsValue val, Node node)
// DL     throws org.xml.sax.SAXException
// DL     {
// DL 
// DL     }
// DL 
// DL     public static void fromXmlNoRoot(QueryByEscalationValue val, Node node)
// DL     throws org.xml.sax.SAXException
// DL     {
// DL 
// DL     }
// DL 
// DL 
// DL 
// DL     //handles XML TroubleTicketValueList (array of TroubleTicketValue or TroubleTicketTemplate)
// DL     public static TroubleTicketKey[] TTKeysFromXmlItems(Node node)
// DL     throws org.xml.sax.SAXException
// DL     {
// DL 
// DL         //what is passed is  anode pointing at the "values"->"item"->"ttvalue"
// DL         //the item is a container for a trouble ticket value
// DL 
// DL         java.util.Vector ttKeyVector = new java.util.Vector();
// DL 
// DL         NodeList items = node.getChildNodes();      //get the child items
// DL         //log ("XmlTranslator.TTValuesFromXml:  NodeList: " + items);
// DL 
// DL         Node node2;
// DL         TroubleTicketKey key = null;
// DL 
// DL         int len = items.getLength();
// DL 
// DL         for (int i=0; i<len; i++)
// DL         {
// DL             node2 = items.item(i);      //tt:Item
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL 
// DL                 String nodeName = node2.getNodeName();
// DL                 //log ("XmlTranslator.TTValuesFromXml:  nodeName: " + nodeName);
// DL 
// DL 
// DL                     key = TroubleTicketKeyFromXml(node2);
// DL 
// DL                     ttKeyVector.addElement(key);
// DL 
// DL 
// DL 
// DL         }
// DL 
// DL         //TroubleTicketValue[] ttValues = (TroubleTicketValue[])attVector.toArray(new TroubleTicketValue[0]);
// DL         return (TroubleTicketKey[])(ttKeyVector.toArray(new TroubleTicketKey[0]));
// DL 
// DL     }
// DL 
// DL     public static  String getArrayofTroubleTicketValues( TroubleTicketValue[] ttVals , boolean useValues) {
// DL     StringBuffer sb = new StringBuffer();
// DL     if( useValues ) sb.append("<tt:troubleTicketValues>");
// DL     for( int i = 0; i < ttVals.length ; i ++ ) {
// DL        sb.append("<tt:Item>");
// DL        sb.append( getXMLTroubleTicketValue( ttVals[i], true ));
// DL        sb.append("</tt:Item>");
// DL     }
// DL     if( useValues) sb.append("</tt:troubleTicketValues>");
// DL     return sb.toString();
// DL 
// DL   }
// DL 
// DL    public static  String getXMLTroubleTicketValue( TroubleTicketValue ttValue ,boolean noRoot ) {
// DL 
// DL 
// DL     String xmlValue = null;
// DL     try {
// DL 
// DL      String[] attrNames = ttValue.getPopulatedAttributeNames();
// DL      HashMap attMap = new HashMap();
// DL      if ( attrNames != null )
// DL         {
// DL             for( int i = 0; i < attrNames.length; i++ )
// DL             {
// DL                 attMap.put( attrNames[i], null );
// DL             }
// DL         }
// DL 
// DL      if( noRoot ) {
// DL         xmlValue = XmlTranslator.toXmlNoRoot(ttValue,attMap);
// DL      }
// DL      else {
// DL         xmlValue = XmlTranslator.toXml(ttValue,attMap);
// DL      }
// DL     }
// DL     catch( Exception ex ) {
// DL 
// DL     }
// DL 
// DL 
// DL 
// DL     return xmlValue;
// DL  }
// DL 
// DL 
// DL    public static  TroubleTicketKey[] getTroubleTicketKeys( Document doc) {
// DL 
// DL     Element docElement = ((Document)doc).getDocumentElement();
// DL     NodeList nodeList = null;
// DL     nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket","troubleTicketKeys");
// DL      Node node = null;
// DL         if (nodeList != null)
// DL         {
// DL             node = nodeList.item(0);
// DL             if (node == null)
// DL             {
// DL                 String nullNode = new String("null Node in XmlTranslator.fromXml (multiple values)");
// DL                 log (nullNode);
// DL                 return null;
// DL 
// DL             }
// DL         }
// DL         //node with item and under item
// DL 
// DL         TroubleTicketKey[] keys = null;
// DL         try {
// DL            keys = XmlTranslator.TTKeysFromXmlItems(node);
// DL         }
// DL         catch( Exception e) {
// DL 
// DL         }
// DL         return keys;
// DL    }
// DL 
// DL 
// DL 
// DL    //handles XML TroubleTicketValueList (array of TroubleTicketValue or TroubleTicketTemplate)
// DL     public static TroubleTicketValue[] TTValuesFromXmlItems(Node node)
// DL     throws org.xml.sax.SAXException
// DL     {
// DL 
// DL         //what is passed is  anode pointing at the "values"->"item"->"ttvalue"
// DL         //the item is a container for a trouble ticket value
// DL 
// DL         java.util.Vector ttValVector = new java.util.Vector();
// DL 
// DL         NodeList items = node.getChildNodes();      //get the child items
// DL         //log ("XmlTranslator.TTValuesFromXml:  NodeList: " + items);
// DL 
// DL         Node node2;
// DL         TroubleTicketValue ttVal = null;
// DL 
// DL         int len = items.getLength();
// DL 
// DL         for (int i=0; i<len; i++)
// DL         {
// DL             node2 = items.item(i);      //tt:Item
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL 
// DL                 String nodeName = node2.getNodeName();
// DL                 log ("XmlTranslator.TTValuesFromXml:  nodeName: " + nodeName);
// DL 
// DL                     ttVal = new TroubleTicketValueImpl();
// DL                     fromXmlNoRoot(node2,ttVal);
// DL                     
// DL                     //System.out.println( "$$$$$TTValuesFromXMLItems ttVal.getTroubleTicketKey(): " + ttVal.getTroubleTicketKey());
// DL                     ttValVector.addElement(ttVal);
// DL 
// DL 
// DL 
// DL         }
// DL 
// DL         
// DL         return (TroubleTicketValue[])(ttValVector.toArray(new TroubleTicketValue[0]));
// DL 
// DL     }
// DL 
// DL    public static TroubleTicketValue[] getTroubleTicketValues( Document doc, String tag)
// DL    {
// DL     Element docElement = ((Document)doc).getDocumentElement();
// DL     NodeList nodeList = null;
// DL     nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket",tag);
// DL      Node node = null;
// DL         if (nodeList != null)
// DL         {
// DL             node = nodeList.item(0);
// DL             if (node == null)
// DL             {
// DL                 String nullNode = new String("null Node in XmlTranslator.fromXml (multiple values)");
// DL                 log (nullNode);
// DL                 return null;
// DL 
// DL             }
// DL         }
// DL         //node with item and under item
// DL 
// DL         TroubleTicketValue[] values = null;
// DL         try {
// DL            values = TTValuesFromXmlItems(node);
// DL         }
// DL         catch( Exception e) {
// DL 
// DL         }
// DL         return values;
// DL    }
// DL 
// DL     //handles XML TroubleTicketValueList (array of TroubleTicketValue or TroubleTicketTemplate)
// DL     public static TroubleTicketValue[] TTValuesFromXml(Node node)
// DL     throws org.xml.sax.SAXException
// DL     {
// DL 
// DL         //------------------------------------------------------------------------------------
// DL         // The passed Node is either a TroubleTicketValueList or TroubleTicketTemplateList.
// DL         // Both are of type "tt:ArrayOfTroubleTicketValue".  Loop thru the array to translate
// DL         // into the returned TroubleTicketValue[]
// DL         //
// DL         // Sample XML request structure:
// DL         //
// DL         //  TrySetTroubleTicketsByTemplatesRequest
// DL         //      TroubleTicketTemplateList (search criteria - of type tt:ArrayOfTroubleTicketValue)
// DL         //          Item
// DL         //              TroubleTicketValue
// DL         //          Item
// DL         //              ...
// DL         //      TroubleTicketValue  (values to be set)
// DL         //      ReturnMode
// DL         //
// DL         //-----------------------------------------------------------------------------------
// DL 
// DL         java.util.Vector ttValVector = new java.util.Vector();
// DL 
// DL         NodeList items = node.getChildNodes();      //get the child items
// DL         //log ("XmlTranslator.TTValuesFromXml:  NodeList: " + items);
// DL 
// DL         Node node2;
// DL         TroubleTicketValue ttVal = null;
// DL 
// DL         int len = items.getLength();
// DL 
// DL         for (int i=0; i<len; i++)
// DL         {
// DL             node2 = items.item(i);      //tt:Item
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL             // Iterate over the contents of each item
// DL             NodeList nodeList2 = node2.getChildNodes();
// DL             Node node3 = null;
// DL 
// DL             for (int y=0;y<nodeList2.getLength();y++)
// DL             {
// DL                 node3 = nodeList2.item(y);
// DL                 if( node3.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL                 String nodeName = node3.getNodeName();
// DL                 //log ("XmlTranslator.TTValuesFromXml:  nodeName: " + nodeName);
// DL                 if ( nodeName.equals("tt:troubleTicketValue") ||
// DL                      nodeName.equals("tt:template") )
// DL                 {
// DL                     ttVal = new TroubleTicketValueImpl();
// DL                     fromXmlNoRoot(node3,ttVal);
// DL                     ttValVector.addElement(ttVal);
// DL                 }
// DL             }
// DL 
// DL         }
// DL 
// DL         //TroubleTicketValue[] ttValues = (TroubleTicketValue[])attVector.toArray(new TroubleTicketValue[0]);
// DL         return (TroubleTicketValue[])(ttValVector.toArray(new TroubleTicketValue[0]));
// DL 
// DL     }
// DL 
// DL     public static void AccountOwnerFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         ttv.setAccountOwner(XmlTranslator.PersonReachFromXml(node));
// DL     }
// DL 
// DL     public static void ActivityDurationListFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         java.util.Vector adVec = new java.util.Vector();
// DL 
// DL         // Get all the immediate children of the curr Node.
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2, node3;
// DL         //PG Default is SET
// DL         //was int modifier = MultiValueList.NONE;
// DL         int modifier = MultiValueList.SET;
// DL 
// DL         for (int x=0; x<nodeList.getLength(); x++)
// DL         {
// DL             node2 = nodeList.item(x);
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL             String nodeName = node2.getNodeName();
// DL             if (nodeName.equals("co:modifier"))
// DL                modifier = XmlTranslator.EnumeratedTypeFromXml(node2, MultiValueList.class);
// DL             else
// DL             {
// DL 
// DL 
// DL                 NodeList nodeList2 = node2.getChildNodes();
// DL                 for (int y=0; y<nodeList2.getLength(); y++)
// DL                     {
// DL                         node3 = nodeList2.item(y);
// DL                         if( node3.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL                         adVec.addElement(XmlTranslator.ActivityDurationFromXml(node3));
// DL                     }
// DL             }
// DL 
// DL         }
// DL 
// DL 
// DL         //Use the toArray(new type[0) call - need to specify the type or will
// DL         //get a runtime ClassCast exception
// DL         ActivityDuration[] adArray = (ActivityDuration[])adVec.toArray(new ActivityDuration[0]);
// DL 
// DL 
// DL         ActivityDurationList adList = new ActivityDurationListImpl();
// DL 
// DL         //use the appropriate  method based on the modifier...
// DL 
// DL         if (modifier == MultiValueList.SET)
// DL         {
// DL             adList.set(adArray);
// DL         }
// DL         else if (modifier == MultiValueList.ADD)
// DL         {
// DL             adList.add(adArray);
// DL         }
// DL         else if (modifier == MultiValueList.REMOVE)
// DL         {
// DL             adList.remove(adArray);
// DL         }
// DL 
// DL 
// DL         ttv.setActivityDurationList(adList);
// DL 
// DL     }
// DL 
// DL     public static void AdditionalTroubleInfoListFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         java.util.Vector atiVec = new java.util.Vector();
// DL 
// DL         // Get all the immediate children of the curr Node.
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL 
// DL         for ( int x=0; x< nodeList.getLength(); x++ )
// DL         {
// DL             node2 = nodeList.item(x);
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL             if (node2.hasChildNodes())
// DL                 atiVec.addElement( node2.getFirstChild().getNodeValue() );
// DL         }
// DL 
// DL         String[] addTrInfoArray = (String[])atiVec.toArray(new String [0]);
// DL         ttv.setAdditionalTroubleInfoList( addTrInfoArray );
// DL     }
// DL 
// DL     public static void AfterHoursRepairAuthorityFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         ttv.setAfterHoursRepairAuthority(XmlTranslator.BooleanFromXml(node));
// DL     }
// DL 
// DL     public static void AuthorizationListFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         java.util.Vector authVec = new java.util.Vector();
// DL 
// DL         // Get all the immediate children of the curr Node.
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL 
// DL         for ( int x=0; x< nodeList.getLength(); x++ )
// DL         {
// DL             node2 = nodeList.item(x);
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL             authVec.addElement(XmlTranslator.AuthorizationFromXml(node2));
// DL         }
// DL 
// DL         Authorization[] authArray = (Authorization[])authVec.toArray(new Authorization[0]);
// DL         ttv.setAuthorizationList( authArray );
// DL     }
// DL 
// DL     public static void CancelRequestedByCustomerFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         ttv.setCancelRequestedByCustomer(XmlTranslator.BooleanFromXml(node));
// DL     }
// DL 
// DL     public static void ClearancePersonFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         ttv.setClearancePerson(XmlTranslator.PersonReachFromXml(node));
// DL     }
// DL 
// DL     public static void CloseOutNarrFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         if (node.hasChildNodes())
// DL             ttv.setCloseOutNarr((node.getFirstChild()).getNodeValue());
// DL     }
// DL 
// DL     public static void CloseOutVerificationFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //ttv.setCloseOutVerification(XmlTranslator.BooleanFromXml(node));
// DL         ttv.setCloseOutVerification(XmlTranslator.EnumeratedTypeFromXml(node,CloseOutVerification.class));
// DL     }
// DL 
// DL     public static void CommitmentTimeFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         ttv.setCommitmentTime(XmlTranslator.DateFromXml(node));
// DL     }
// DL 
// DL     public static void CommitmentTimeRequestedFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //Date
// DL         ttv.setCommitmentTimeRequested(XmlTranslator.DateFromXml(node));
// DL     }
// DL 
// DL     public static void CustomerFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //PersonReach
// DL         ttv.setCustomer(XmlTranslator.PersonReachFromXml(node));
// DL     }
// DL 
// DL     public static void CustomerRoleAssignmentListFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         // iterate over the array elements - of type "item"
// DL         java.util.Vector craVec = new Vector();
// DL 
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL 
// DL         CustomerRoleAssignment custRA = null;
// DL 
// DL         for (int x=0; x<nodeList.getLength(); x++)
// DL         {
// DL             node2 = nodeList.item(x);
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL 
// DL             // Iterate over the contents of each item
// DL             NodeList nodeList2 = node2.getChildNodes();
// DL             Node node3;
// DL             String role = null;
// DL             String contactPerson = null;
// DL 
// DL             //CustomerRoleAssignment custRA = ttv.makeCustomerRoleAssignment();
// DL             custRA = new CustomerRoleAssignmentImpl();
// DL 
// DL             for (int y=0; y<nodeList2.getLength(); y++)
// DL             {
// DL                 node3 = nodeList2.item(y);
// DL                 if( node3.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL                 String nodeName = node3.getNodeName();
// DL                 if (nodeName.equals("tt:role"))
// DL                 {
// DL                     if (node3.hasChildNodes())
// DL                         custRA.setRole(node3.getFirstChild().getNodeValue());
// DL                 }
// DL                 else if (nodeName.equals("tt:contactPerson"))
// DL                 {
// DL                     custRA.setContactPerson(XmlTranslator.PersonReachFromXml(node3));
// DL                 }
// DL                 else if (nodeName.equals("#comment"))
// DL                 {
// DL                     continue;
// DL                 }
// DL 
// DL             }
// DL 
// DL             craVec.addElement(custRA);      //add to vector
// DL         }
// DL 
// DL         CustomerRoleAssignment[]  craArray = (CustomerRoleAssignment[])craVec.toArray(new CustomerRoleAssignment[0]);
// DL         ttv.setCustomerRoleAssignmentList( craArray );
// DL 
// DL     }
// DL 
// DL 
// DL     public static void CustomerTroubleNumFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //String
// DL         if (node.hasChildNodes())
// DL             ttv.setCustomerTroubleNum((node.getFirstChild()).getNodeValue());
// DL     }
// DL 
// DL     public static void DialogFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //String
// DL         if (node.hasChildNodes())
// DL             ttv.setDialog((node.getFirstChild()).getNodeValue());
// DL     }
// DL 
// DL 
// DL     public static void EscalationListFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         java.util.Vector escaVec = new Vector();
// DL 
// DL         // Get the immediate children of the escalation node
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL         //PG Default is SET
// DL         //was int modifier = MultiValueList.NONE;
// DL         int modifier = MultiValueList.SET;
// DL 
// DL         for (int x=0; x<nodeList.getLength(); x++)
// DL         {
// DL             node2 = nodeList.item(x);
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL             String nodeName = node2.getNodeName();
// DL             if (nodeName.equals("co:modifier"))
// DL                modifier = XmlTranslator.EnumeratedTypeFromXml(node2, MultiValueList.class);
// DL             else
// DL             {
// DL                 // Get the immediate children (items) of the node "Escalations"
// DL                 NodeList nodeList2 = node2.getChildNodes();
// DL                 Node node3;
// DL                 for (int y=0; y<nodeList2.getLength(); y++)
// DL                  {
// DL                         node3 = nodeList2.item(y);
// DL                         if( node3.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL 
// DL                     // Now iterate over the contents of each item node
// DL                     NodeList nodeList3 = node3.getChildNodes();
// DL                     Node node4;
// DL 
// DL                     Escalation esca = new EscalationImpl();
// DL 
// DL                     for (int z=0; z<nodeList3.getLength(); z++)
// DL                     {
// DL                         node4 = nodeList3.item(z);
// DL                         if( node4.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL                         String nodeName2 = node4.getNodeName();
// DL 
// DL                         if ( nodeName2.equals("tt:requestState") )
// DL 
// DL                                 esca.setRequestState(XmlTranslator.EnumeratedTypeFromXml(node4,RequestState.class));
// DL 
// DL                         else if ( nodeName2.equals("tt:escTime") )
// DL                             esca.setEscalationTime(XmlTranslator.DateFromXml(node4));
// DL 
// DL                         else if ( nodeName2.equals("tt:requestPerson") )
// DL                             esca.setRequestPerson(XmlTranslator.PersonReachFromXml(node4));
// DL 
// DL                         else if ( nodeName2.equals("tt:orgLevel") )
// DL 
// DL                             esca.setLevel(XmlTranslator.EnumeratedTypeFromXml(node4,OrgLevel.class));
// DL 
// DL 
// DL                         else if ( nodeName2.equals("tt:escPerson") )
// DL                             esca.setEscalationPerson(XmlTranslator.PersonReachFromXml(node4));
// DL 
// DL                         else if ( nodeName2.equals("#comment" ) )
// DL                         continue;
// DL                     }
// DL 
// DL                     escaVec.addElement(esca);
// DL                  }
// DL             }
// DL 
// DL         }
// DL 
// DL         Escalation[] escArray = (Escalation[])escaVec.toArray(new Escalation[0]);
// DL 
// DL         EscalationList escList = new EscalationListImpl();
// DL 
// DL         //use the appropriate  method based on the modifier...
// DL 
// DL         if (modifier == MultiValueList.SET)
// DL         {
// DL             escList.set(escArray);
// DL         }
// DL         else if (modifier == MultiValueList.ADD)
// DL         {
// DL             escList.add(escArray);
// DL         }
// DL         else if (modifier == MultiValueList.REMOVE)
// DL         {
// DL             escList.remove(escArray);
// DL         }
// DL 
// DL         ttv.setEscalationList(escList);
// DL     }
// DL 
// DL 
// DL     public static void InitiatingModeFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL        //enum
// DL        ttv.setInitiatingMode(XmlTranslator.EnumeratedTypeFromXml(node,InitiatingMode.class));
// DL     }
// DL 
// DL 
// DL     public static void LastUpdateTimeFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //Date
// DL         ttv.setLastUpdateTime(XmlTranslator.DateFromXml(node));
// DL     }
// DL 
// DL     public static void MaintServiceChargeFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         ttv.setMaintServiceCharge(XmlTranslator.BooleanFromXml(node));
// DL     }
// DL 
// DL     public static void OriginatorFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         if (node.hasChildNodes())
// DL             ttv.setOriginator((node.getFirstChild()).getNodeValue());
// DL     }
// DL 
// DL 
// DL     public static void OutageDurationFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         ttv.setOutageDuration(XmlTranslator.TimeLengthFromXml(node));
// DL     }
// DL 
// DL 
// DL     public static void ReceivedTimeFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //Date
// DL         ttv.setReceivedTime(XmlTranslator.DateFromXml(node));
// DL     }
// DL 
// DL 
// DL public static void RelatedAlarmListFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         java.util.Vector alarmVec = new Vector();
// DL 
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL         int modifier = MultiValueList.SET;
// DL 
// DL         for (int x=0; x<nodeList.getLength(); x++)
// DL         {
// DL             node2 = nodeList.item(x);
// DL             String nodeName = node2.getNodeName();
// DL             if (nodeName.equals("co:modifier"))
// DL                modifier = XmlTranslator.EnumeratedTypeFromXml(node2, MultiValueList.class);
// DL             else
// DL             {
// DL                 NodeList nodeList2 = node2.getChildNodes();
// DL                 Node node3;
// DL 
// DL                 AlarmValue aval = new AlarmValueImpl();
// DL 
// DL                 XmlSerializer xmls= (XmlSerializer)aval.makeSerializer(XmlSerializer.class.getName());
// DL 
// DL                 for (int y=0; y<nodeList2.getLength(); y++)
// DL                  {
// DL 
// DL                     node3 = nodeList2.item(y);
// DL                     if( node3.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL                     aval=( AlarmValue) xmls.fromXml((Element) node3);
// DL 
// DL                     alarmVec.addElement(aval);
// DL                  }
// DL             }
// DL 
// DL         }
// DL 
// DL         AlarmValue[] avArray = (AlarmValue[])alarmVec.toArray(new AlarmValue[0]);
// DL 
// DL         AlarmValueList avList = new AlarmValueListImpl();
// DL 
// DL         if (modifier == MultiValueList.SET)
// DL         {
// DL             avList.set(avArray);
// DL         }
// DL         else if (modifier == MultiValueList.ADD)
// DL         {
// DL             avList.add(avArray);
// DL         }
// DL         else if (modifier == MultiValueList.REMOVE)
// DL         {
// DL             avList.remove(avArray);
// DL         }
// DL 
// DL         ttv.setRelatedAlarmList(avList);
// DL 
// DL     }
// DL 
// DL     public static void RelatedTroubleTicketKeyListFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL 
// DL 
// DL 
// DL 
// DL         java.util.Vector rttVec = new Vector();
// DL 
// DL         // Get the immediate children
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL 
// DL         TroubleTicketKey ttKey = null;
// DL         for (int x=0; x<nodeList.getLength(); x++)
// DL         {
// DL             node2 = nodeList.item(x);
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL 
// DL             //NodeList nodeList2 = node2.getChildNodes();
// DL             //Node node3;
// DL 
// DL             //for (int y=0; y<nodeList2.getLength(); y++)
// DL             //{
// DL             //  node3 = nodeList2.item(y);
// DL             log ( "&&&&&&&t About to call TroubleTicketKeyFromXml RelatedTroubleTicketKeyListFromXml&&&&&&");
// DL             rttVec.addElement(XmlTranslator.TroubleTicketKeyFromXml(node2));
// DL             //}
// DL         }
// DL 
// DL         TroubleTicketKey[] ttkArray = (TroubleTicketKey[])rttVec.toArray(new TroubleTicketKey[0]);
// DL         log ( "&&&&&&&t RelatedTroubleTicketKeyListFromXml&&&&&&" + ttkArray.length );
// DL         if( ttkArray.length > 0 ) log ( "&&&&&&&tprimary key = " + ttkArray[0].getPrimaryKey());
// DL         ttv.setRelatedTroubleTicketKeyList(ttkArray);
// DL 
// DL         //for (int x=0;x<ttkArray.length;x++)
// DL         //  ((TroubleTicketKeyImpl)ttkArray[x]).print(10);
// DL     }
// DL 
// DL 
// DL 
// DL     public static void RepairActivityListFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL        log ("RepairActivy Element DEBUG");
// DL 
// DL         java.util.Vector raVec = new Vector();
// DL 
// DL         // Get all the immediate children of the escalation node - of type "Item"
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL         //PG Default is SET
// DL         //was int modifier = MultiValueList.NONE;
// DL         int modifier = MultiValueList.SET;
// DL 
// DL         for (int x=0; x<nodeList.getLength(); x++)
// DL         {
// DL             node2 = nodeList.item(x);
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL 
// DL             String nodeName = node2.getNodeName();
// DL             if (nodeName.equals("co:modifier"))
// DL                modifier = XmlTranslator.EnumeratedTypeFromXml(node2, MultiValueList.class);
// DL             else
// DL             {
// DL                 // Get the immediate children (items) of the node "repairActivities"
// DL                 NodeList nodeList2 = node2.getChildNodes();
// DL                 Node node3;
// DL                 for (int y=0; y<nodeList2.getLength(); y++)
// DL                  {
// DL                         node3 = nodeList2.item(y);
// DL                         if( node3.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL 
// DL 
// DL                     // Now iterate over the contents of each item node
// DL                     NodeList nodeList3 = node3.getChildNodes();
// DL                     Node node4;
// DL 
// DL                     RepairActivity ra = new RepairActivityImpl();
// DL 
// DL                     for (int z=0; z<nodeList3.getLength(); z++)
// DL                     {
// DL                         node4 = nodeList3.item(z);
// DL                         if( node4.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL 
// DL                         String nodeName2 = node4.getNodeName();
// DL 
// DL                         log ("RepairActivy Element is" + nodeName2);
// DL 
// DL                         if ( nodeName2.equals("tt:entryTime") )
// DL                         {
// DL                             ra.setEntryTime(XmlTranslator.DateFromXml(node4));
// DL                         }
// DL                         else if ( nodeName2.equals("tt:activityInfo") )
// DL                         {
// DL                             if (node4.hasChildNodes())
// DL                                 ra.setActivityInfo(node4.getFirstChild().getNodeValue());
// DL                         }
// DL                         else if ( nodeName2.equals("tt:activityPerson") )
// DL                         {
// DL                             ra.setActivityPerson(XmlTranslator.PersonReachFromXml(node4));
// DL                         }
// DL                         else if ( nodeName2.equals("tt:activityCode") )
// DL                         {
// DL                             ra.setActivityCode(XmlTranslator.EnumeratedTypeFromXml(node4,ActivityCode.class));
// DL                         }
// DL                         else if ( nodeName2.equals("#comment" ) )
// DL                             continue;
// DL 
// DL                         }
// DL 
// DL                     raVec.addElement(ra);
// DL                  }
// DL             }
// DL 
// DL         }
// DL 
// DL         RepairActivity[] raArray = (RepairActivity[])raVec.toArray(new RepairActivity[0]);
// DL 
// DL 
// DL         RepairActivityList raList = new RepairActivityListImpl();
// DL 
// DL         //use the appropriate  method based on the modifier...
// DL         if (modifier == MultiValueList.SET)
// DL         {
// DL             raList.set(raArray);
// DL         }
// DL         else if (modifier == MultiValueList.ADD)
// DL         {
// DL             raList.add(raArray);
// DL         }
// DL         else if (modifier == MultiValueList.REMOVE)
// DL         {
// DL             raList.remove(raArray);
// DL         }
// DL         else raList.set(raArray);
// DL 
// DL 
// DL         ttv.setRepairActivityList( raList );
// DL 
// DL 
// DL 
// DL 
// DL 
// DL 
// DL     }
// DL 
// DL 
// DL     public static void RepeatReportFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //enum
// DL         ttv.setRepeatReport(XmlTranslator.EnumeratedTypeFromXml(node,RepeatReport.class));
// DL     }
// DL 
// DL     public static void RestoredTimeFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //Date
// DL         ttv.setRestoredTime(XmlTranslator.DateFromXml(node));
// DL     }
// DL 
// DL     public static void ServiceUnavailableBeginTimeFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //Date
// DL         ttv.setServiceUnavailableBeginTime(XmlTranslator.DateFromXml(node));
// DL     }
// DL 
// DL     public static void ServiceUnavailableEndTimeFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //Date
// DL         ttv.setServiceUnavailableEndTime(XmlTranslator.DateFromXml(node));
// DL     }
// DL 
// DL     public static void SPRoleAssignmentListFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL 
// DL 
// DL         java.util.Vector spraVec = new Vector();
// DL 
// DL         // Get all the immediate children of the  node - of type "Item"
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL         for (int x=0; x<nodeList.getLength(); x++)
// DL         {
// DL             node2 = nodeList.item(x);
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL 
// DL             // Now iterate over the contents of each item node
// DL             NodeList nodeList2 = node2.getChildNodes();
// DL             Node node3;
// DL 
// DL             SPRoleAssignment spra = new SPRoleAssignmentImpl();
// DL 
// DL             for (int y=0; y<nodeList2.getLength(); y++)
// DL             {
// DL                 node3 = nodeList2.item(y);
// DL                 if( node3.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL                 String nodeName = node3.getNodeName();
// DL 
// DL                 if ( nodeName.equals("tt:role") )
// DL                 {
// DL                     if (node3.hasChildNodes())
// DL                         spra.setRole(node3.getFirstChild().getNodeValue());
// DL                 }
// DL                 else if ( nodeName.equals("tt:contactPerson") )
// DL                 {
// DL                     spra.setContactPerson(XmlTranslator.PersonReachFromXml(node3));
// DL                 }
// DL                 else if ( nodeName.equals("#comment" ) )
// DL                     continue;
// DL             }
// DL             spraVec.addElement(spra);
// DL         }
// DL 
// DL         SPRoleAssignment[] raArray = (SPRoleAssignment[])spraVec.toArray(new SPRoleAssignment[0]);
// DL         ttv.setSPRoleAssignmentList( raArray );
// DL 
// DL     }
// DL 
// DL 
// DL     public static void SuspectObjectListFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         java.util.Vector solVec = new Vector();
// DL 
// DL         // Get all the immediate children of the  node - of type "Item"
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL         for (int x=0; x<nodeList.getLength(); x++)
// DL         {
// DL             node2 = nodeList.item(x);
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL             // Now iterate over the contents of each item node
// DL             NodeList nodeList2 = node2.getChildNodes();
// DL             Node node3;
// DL 
// DL             SuspectObject so = new SuspectObjectImpl();
// DL 
// DL             for (int y=0; y<nodeList2.getLength(); y++)
// DL             {
// DL                 node3 = nodeList2.item(y);
// DL                 if( node3.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL                 String nodeName = node3.getNodeName();
// DL 
// DL                 if ( nodeName.equals("tt:suspectObjectType") )
// DL                 {
// DL                     if (node3.hasChildNodes())
// DL                     so.setSuspectObjectType(node3.getFirstChild().getNodeValue());
// DL 
// DL                 }
// DL                 else if ( nodeName.equals("tt:suspectObjectId") )
// DL                 {
// DL                     if (node3.hasChildNodes())
// DL                     {
// DL                         so.setSuspectObjectId(node3.getFirstChild().getNodeValue());
// DL                     }
// DL 
// DL                 }
// DL                 else if ( nodeName.equals("tt:failureProbability") )
// DL                 {
// DL                     Integer fp;
// DL                     if (node3.hasChildNodes())
// DL                     {
// DL                      fp = new Integer(node3.getFirstChild().getNodeValue());
// DL                      so.setFailureProbability(fp.intValue());
// DL                     }
// DL                 }
// DL 
// DL                 else if ( nodeName.equals("#comment" ) )
// DL                     continue;
// DL 
// DL             }
// DL             solVec.addElement(so);
// DL         }
// DL 
// DL         SuspectObject[] soArray = (SuspectObject[])solVec.toArray(new SuspectObject[0]);
// DL         ttv.setSuspectObjectList( soArray );
// DL 
// DL     }
// DL 
// DL 
// DL     public static void TroubleDescriptionFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         if (node.hasChildNodes())
// DL             ttv.setTroubleDescription( node.getFirstChild().getNodeValue() );
// DL     }
// DL 
// DL 
// DL     public static void TroubleFoundFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //enum
// DL         ttv.setTroubleFound(XmlTranslator.EnumeratedTypeFromXml(node,TroubleFound.class));
// DL     }
// DL 
// DL 
// DL     public static void TroubleLocationFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         if (node.hasChildNodes())
// DL             ttv.setTroubleLocation( node.getFirstChild().getNodeValue() );
// DL     }
// DL 
// DL 
// DL     public static void TroubleNumListFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         java.util.Vector tnlVec = new java.util.Vector();
// DL 
// DL         // Get all the immediate children of the curr Node.
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL 
// DL         for ( int x=0; x<nodeList.getLength(); x++)
// DL         {
// DL             node2 = nodeList.item(x);
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL             if (node2.hasChildNodes())
// DL                 tnlVec.addElement(node2.getFirstChild().getNodeValue());
// DL         }
// DL 
// DL         String[] tnlArray = (String[])tnlVec.toArray(new String[0]);
// DL         ttv.setTroubleNumList( tnlArray );
// DL 
// DL     }
// DL 
// DL     public static void TroubledObjectFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         if (node.hasChildNodes())
// DL             ttv.setTroubledObject(node.getFirstChild().getNodeValue());
// DL     }
// DL 
// DL     public static void TroubleTypeFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //enum
// DL         ttv.setTroubleType(XmlTranslator.EnumeratedTypeFromXml(node,TroubleType.class));
// DL     }
// DL 
// DL 
// DL     public static void TroubleStateFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //enum
// DL         ttv.setTroubleState(XmlTranslator.EnumeratedTypeFromXml(node,TroubleState.class));
// DL     }
// DL 
// DL 
// DL     public static void TroubleStatusFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //enum
// DL         ttv.setTroubleStatus(XmlTranslator.EnumeratedTypeFromXml(node,TroubleStatus.class));
// DL     }
// DL 
// DL 
// DL 
// DL     public static void TroubleStatusTimeFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //Date
// DL         ttv.setTroubleStatusTime(XmlTranslator.DateFromXml(node));
// DL     }
// DL 
// DL 
// DL     public static void PerceivedTroubleSeverityFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //enum
// DL         ttv.setPerceivedTroubleSeverity(XmlTranslator.EnumeratedTypeFromXml(node,PerceivedTroubleSeverity.class));
// DL     }
// DL 
// DL 
// DL     public static void PreferredPriorityFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //enum
// DL         ttv.setPreferredPriority(XmlTranslator.EnumeratedTypeFromXml(node,PreferredPriority.class));
// DL     }
// DL 
// DL 
// DL     public static void TroubleDetectionTimeFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //Date
// DL         ttv.setTroubleDetectionTime(XmlTranslator.DateFromXml(node));
// DL     }
// DL 
// DL 
// DL     public static void TroubleLocationInfoListFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         log ( "-----TroubleLocationInfoListFromXml----");
// DL 
// DL         java.util.Vector tliVec = new Vector();
// DL 
// DL         // Get all the immediate children of the escalation node - of type "Item"
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL         for (int x=0; x<nodeList.getLength(); x++)
// DL         {
// DL             node2 = nodeList.item(x);
// DL             //log ( "Node name= " + node2.getNodeName());
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL 
// DL             // Now iterate over the contents of each item node
// DL             NodeList nodeList2 = node2.getChildNodes();
// DL             Node node3;
// DL 
// DL             TroubleLocationInfo tli = new TroubleLocationInfoImpl();
// DL 
// DL             for (int y=0; y<nodeList.getLength(); y++)
// DL             {
// DL                 node3 = nodeList2.item(y);
// DL                 //log ( "Node name= " + node3.getNodeName());
// DL                 if( node3.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL                 String nodeName = node3.getNodeName();
// DL                  //log ( "After Node name= " + node3.getNodeName());
// DL                 if ( nodeName.equals("tt:premiseAddress") )
// DL                 {
// DL                     tli.setPremiseAddress(XmlTranslator.AddressFromXml(node3));
// DL                 }
// DL                 else if ( nodeName.equals("tt:premiseName") )
// DL                 {
// DL                     if (node3.hasChildNodes())
// DL                         tli.setPremiseName(node3.getFirstChild().getNodeValue());
// DL                 }
// DL                 else if ( nodeName.equals("tt:objectIdDN") )
// DL                 {
// DL                     if (node3.hasChildNodes())
// DL                         tli.setObjectIdDN(node3.getFirstChild().getNodeValue());
// DL                 }
// DL                 else if ( nodeName.equals("tt:locationPerson") )
// DL                 {
// DL                     tli.setLocationPerson(XmlTranslator.PersonReachFromXml(node3));
// DL                 }
// DL                 else if ( nodeName.equals("tt:accessHoursList") )
// DL                 {
// DL 
// DL                     java.util.Vector acVec = new java.util.Vector();
// DL                     NodeList accHrsNodeList = node3.getChildNodes();
// DL                     Node node4;
// DL                     AccessHours accHoursObj = null;
// DL                     for (int z=0; z<accHrsNodeList.getLength(); z++)
// DL                     {
// DL                         node4 = accHrsNodeList.item(z);
// DL                         if( node4.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL                         //String accHrChildNodeName = accHrsChildNode.getNodeName();
// DL                         acVec.addElement(XmlTranslator.AccessHoursFromXml(node4));
// DL                     }
// DL 
// DL                     tli.setAccessHoursList((AccessHours[])acVec.toArray(new AccessHours[0]));
// DL                 }
// DL                 else if ( nodeName.equals("#comment" ) )
// DL                     continue;
// DL             }
// DL             tliVec.addElement(tli);
// DL 
// DL         }
// DL 
// DL         TroubleLocationInfo[] tliArray = (TroubleLocationInfo[])tliVec.toArray(new TroubleLocationInfo[0]);
// DL         ttv.setTroubleLocationInfoList(tliArray);
// DL 
// DL     }
// DL 
// DL 
// DL     /* Keep this for example of old code
// DL         // Since this element represents an array in the Trouble Ticket System,
// DL         // it is expected to contain a number of elements inside this XML doc
// DL         java.util.Vector trlocationInfoVec = new java.util.Vector();
// DL         AccessHours[] accHrsArray = null;
// DL 
// DL         // As a first step, get all the immediate children whose tag is "Item"
// DL         NodeList nodeList = ttChildNode.getChildNodes();
// DL         Node node2;
// DL 
// DL         for( int q=0; q < nodeList.getLength(); q++ )
// DL         {
// DL             node2 = nodeList.item(q);
// DL 
// DL             // Create a local vector to look after the accessHours array
// DL             java.util.Vector accessHrsVec = new java.util.Vector();
// DL 
// DL             String address = null;
// DL             String locationPerson = null;
// DL             AccessHours accHours = null;
// DL             NodeList trInfoChildNodeList = node2.getChildNodes();
// DL             Node trInfoChildNode;
// DL 
// DL             for (int t=0; t<trInfoChildNodeList.getLength(); t++)
// DL             {
// DL                 trInfoChildNode = trInfoChildNodeList.item(t);
// DL                 String trInfoChildNodeName = trInfoChildNode.getNodeName();
// DL 
// DL                 if ( trInfoChildNodeName.equals("tt:Address") )
// DL                 {
// DL                     address = (trInfoChildNode.getFirstChild()).getNodeValue();
// DL                 }
// DL                 else if ( trInfoChildNodeName.equals("tt:LocationPerson") )
// DL                 {
// DL                     locationPerson = (trInfoChildNode.getFirstChild()).getNodeValue();
// DL                 }
// DL                 else if ( trInfoChildNodeName.equals("tt:AccessHours") )
// DL                 {
// DL                     // This is an array, i.e. there could be multiple accessHours
// DL                     // elements in each troublelocationInfo element
// DL                     java.util.Date startTime = null;
// DL                     java.util.Date endTime = null;
// DL                     NodeList accHrsChildNodeList = trInfoChildNode.getChildNodes();
// DL                     Node accHrsChildNode;
// DL                     AccessHours accHoursObj = null;
// DL                     for (int ac=0; ac < accHrsChildNodeList.getLength(); ac++)
// DL                     {
// DL                         accHrsChildNode = accHrsChildNodeList.item(ac);
// DL                         String accHrChildNodeName = accHrsChildNode.getNodeName();
// DL 
// DL                         String accHrChildNodeValue = (accHrsChildNode.getFirstChild()).getNodeValue();
// DL                         //"2001-02-18T14:01:00Z"
// DL                         StringTokenizer st = new StringTokenizer( accHrChildNodeValue, "T" );
// DL                         String year;
// DL                         String month;
// DL                         String day;
// DL                         String hour;
// DL                         String min;
// DL                         String sec;
// DL 
// DL                         String dateToken = st.nextToken();
// DL                         String timeToken = st.nextToken();
// DL 
// DL                         StringTokenizer st2 = new StringTokenizer( dateToken, "-");
// DL                         year = st2.nextToken();
// DL                         month = st2.nextToken();
// DL                         day = st2.nextToken();
// DL                         //L
// DL 
// DL                         StringTokenizer st3 = new StringTokenizer( timeToken, ":");
// DL 
// DL                         hour = st3.nextToken();
// DL                         min = st3.nextToken();
// DL                         sec = (st3.nextToken()).substring(0,2);
// DL 
// DL 
// DL                         //log ("year: " + year);
// DL                         //log ("month: " + month);
// DL                         //log ("day: " + day);
// DL                         //log ("hour: " + hour);
// DL                         //log ("min: " + min);
// DL                         //log ("sec: " + sec);
// DL 
// DL                         long utcDate = java.util.Date.UTC( (Integer.parseInt(year) - 1900), Integer.parseInt(month ) -1, Integer.parseInt(day),
// DL                                         Integer.parseInt(hour), Integer.parseInt(min), Integer.parseInt(sec) );
// DL 
// DL                         if ( accHrChildNodeName.equals( "tt:StartTime") )
// DL                         {
// DL                             startTime = new java.util.Date ( utcDate );
// DL                         }
// DL                         else
// DL                         {
// DL                             endTime = new java.util.Date ( utcDate );
// DL                         }
// DL                         //public AccessHours              makeAccessHours()             { return new AccessHoursImpl(); }
// DL                         //accHrsObj = new AccessHoursImpl ( startTime, endTime );
// DL                         accHoursObj = ttv.makeAccessHours();
// DL                         //MR1 accHoursObj.setStartTime(startTime);
// DL                         //MR1 accHoursObj.setEndTime(endTime);
// DL                     }
// DL 
// DL                     accessHrsVec.addElement( accHoursObj );
// DL                 }
// DL                 else if ( trInfoChildNodeName.equals("#comment" ) )
// DL                 {
// DL                     continue;
// DL                 }
// DL                 accHrsArray = (AccessHours[])accessHrsVec.toArray( new AccessHours[0] );
// DL 
// DL             }   // for the Item children
// DL             TroubleLocationInfo trlocationInfoObj = ttv.makeTroubleLocationInfo();
// DL             //MR1 trlocationInfoObj.setPremiseAddress(address);
// DL             //MR1 trlocationInfoObj.setLocationPerson(locationPerson);
// DL             //MR1 trlocationInfoObj.setAccessHours(accHrsArray);
// DL             //TroubleLocationInfo trlocationInfoObj = new TroubleLocationInfoImpl ( address, locationPerson, accHrsArray );
// DL             trlocationInfoVec.addElement( trlocationInfoObj );
// DL 
// DL         }   // for loop for next item
// DL 
// DL         // convert the trlocationVec to array here
// DL         TroubleLocationInfo[] trLocationInfoArray = (TroubleLocationInfo[])trlocationInfoVec.toArray(new TroubleLocationInfo[0]);
// DL         // set the attr value
// DL         ttv.setTroubleLocationInfoList( trLocationInfoArray );
// DL         */
// DL 
// DL 
// DL     public static void TroubledObjectAccessFromTimeFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //Date
// DL         ttv.setTroubledObjectAccessFromTime(XmlTranslator.DateFromXml(node));
// DL     }
// DL 
// DL     public static void TroubledObjectAccessHoursListFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL 
// DL         java.util.Vector toahVec = new java.util.Vector();
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL         AccessHours accHoursObj = null;
// DL         for (int x=0; x<nodeList.getLength(); x++)
// DL         {
// DL             node = nodeList.item(x);
// DL             if( node.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL             toahVec.addElement(XmlTranslator.AccessHoursFromXml(node));
// DL         }
// DL 
// DL         ttv.setTroubledObjectAccessHoursList((AccessHours[])toahVec.toArray(new AccessHours[0]));
// DL     }
// DL 
// DL     public static void TroubledObjectAccessToTimeFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         //Date
// DL         ttv.setTroubledObjectAccessToTime(XmlTranslator.DateFromXml(node));
// DL     }
// DL 
// DL 
// DL     public static void TroubleSystemDNFromXml(Node node,TroubleTicketValue ttv)
// DL     {
// DL         if (node.hasChildNodes())
// DL             ttv.setTroubleSystemDN((node.getFirstChild()).getNodeValue());
// DL     }
// DL 
// DL 
// DL 
// DL 
// DL     //----------------------------------------------------------------------------
// DL     //
// DL     //                  "From XML" for sub-types
// DL     //
// DL     //----------------------------------------------------------------------------
// DL     public static AccessHours AccessHoursFromXml(Node node)
// DL     {
// DL 
// DL         AccessHours ah = new AccessHoursImpl();
// DL         //go thru node list
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL 
// DL         for (int x=0; x<nodeList.getLength(); x++)
// DL         {
// DL             node2 = nodeList.item(x);
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL             String nodeName = node2.getNodeName();
// DL 
// DL             if (nodeName.equals("tt:dayOfWeek"))
// DL                 ah.setDayOfWeek(XmlTranslator.EnumeratedTypeFromXml(node2,DayOfWeek.class));
// DL 
// DL             else if (nodeName.equals("tt:timeIntervalList"))
// DL             {
// DL                 NodeList nodeList2 = node2.getChildNodes();
// DL                 java.util.Vector tiVect = new java.util.Vector();
// DL                 Node node22;
// DL                 for (int y=0; y<nodeList2.getLength(); y++)
// DL                 {
// DL                     node22 = nodeList2.item(y);
// DL                     if( node22.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL                     tiVect.addElement(XmlTranslator.TimeIntervalFromXml(node22));
// DL                 }
// DL 
// DL                 ah.setTimeIntervalList((TimeInterval[])tiVect.toArray(new TimeInterval[0]));
// DL             }
// DL         }
// DL 
// DL         return ah;
// DL 
// DL     }
// DL 
// DL     public static ActivityDuration ActivityDurationFromXml(Node node)
// DL     {
// DL         log ( "---ActivityDurationFromXml---");
// DL         log ( "---Node Name--->" + node.getNodeName() );
// DL         ActivityDuration ad = new ActivityDurationImpl();
// DL 
// DL         //go thru node list
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL 
// DL         for (int x=0; x<nodeList.getLength(); x++)
// DL         {
// DL             node2 = nodeList.item(x);
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL             String nodeName = node2.getNodeName();
// DL 
// DL             log ( "---Node Name--->" + nodeName );
// DL             log ( "---Node Type--->" + node2.getNodeType() );
// DL             /*log ( "---Node Type--->" + Node.ELEMENT_NODE );
// DL             log ( "---Node Type--->" + Node.ATTRIBUTE_NODE );
// DL             log ( "---Node Type--->" + Node.TEXT_NODE );
// DL             log ( "---Node Type--->" + Node.CDATA_SECTION_NODE );
// DL             log ( "---Node Type--->" + Node.ENTITY_REFERENCE_NODE );
// DL             log ( "---Node Type--->" + Node.ENTITY_NODE );
// DL             log ( "---Node Type--->" + Node.PROCESSING_INSTRUCTION_NODE );
// DL             log ( "---Node Type--->" + Node.COMMENT_NODE );
// DL             log ( "---Node Type--->" + Node.DOCUMENT_NODE );
// DL             log ( "---Node Type--->" + Node.DOCUMENT_TYPE_NODE );*/
// DL             if (nodeName.equals("tt:duration"))
// DL                 ad.setDuration(XmlTranslator.TimeLengthFromXml(node2));
// DL 
// DL             else if (nodeName.equals("tt:billable"))
// DL                 ad.setBillable(XmlTranslator.BooleanFromXml(node2));
// DL 
// DL             else if (nodeName.equals("tt:activityType"))
// DL                 ad.setActivityType(XmlTranslator.EnumeratedTypeFromXml(node2,ActivityType.class));
// DL 
// DL             else if (nodeName.equals("#comment" ) )
// DL                 continue;
// DL 
// DL         }
// DL 
// DL         return ad;
// DL 
// DL     }
// DL 
// DL 
// DL 
// DL     //-----------------------------------------------------------------------------
// DL     // Maps the enumerated value (the string XML value) to its int equivalent
// DL     // using the interface reflector
// DL     //-----------------------------------------------------------------------------
// DL     public static int EnumeratedTypeFromXml(Node node,
// DL                                             Class interfaceClass)
// DL 
// DL     {
// DL 
// DL         Integer enumVal = null;
// DL 
// DL         //get string enum name from node, use reflector to transform to int
// DL         try
// DL         {
// DL             String enumString;
// DL             if (node.hasChildNodes())
// DL             {
// DL                 enumString = node.getFirstChild().getNodeValue();
// DL                 enumVal = (Integer)(ttIntReflector.getInterfaceFieldValue( interfaceClass, enumString ));
// DL             }
// DL             else
// DL                 enumVal = new Integer(-1);      //-1 means uninitialized
// DL 
// DL         }
// DL         catch( javax.oss.IllegalArgumentException iLLEx )
// DL         {
// DL             //throw new org.xml.sax.SAXException( iLLEx.getMessage() );
// DL             logException (iLLEx);
// DL 
// DL         }
// DL         catch( java.lang.IllegalAccessException iLLAccEx )
// DL         {
// DL             //throw new org.xml.sax.SAXException( iLLAccEx.getMessage() );
// DL             logException (iLLAccEx);
// DL 
// DL         }
// DL         catch( java.lang.NoSuchFieldException noSuchFldEx )
// DL         {
// DL             //throw new org.xml.sax.SAXException( noScuhFldEx.getMessage() );
// DL             logException (noSuchFldEx);
// DL 
// DL         }
// DL 
// DL         return enumVal.intValue();
// DL 
// DL     }
// DL 
// DL 
// DL     public static Authorization AuthorizationFromXml(Node node)
// DL     {
// DL 
// DL         Authorization auth = new AuthorizationImpl();
// DL 
// DL         //go thru node list
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL 
// DL         for (int x=0; x<nodeList.getLength(); x++)
// DL         {
// DL             node2 = nodeList.item(x);
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL             String nodeName = node2.getNodeName();
// DL 
// DL             if (nodeName.equals("tt:requestState"))
// DL                 auth.setRequestState(XmlTranslator.EnumeratedTypeFromXml(node2,RequestState.class));
// DL 
// DL             else if (nodeName.equals("tt:activityType"))
// DL                 auth.setActivityType(XmlTranslator.EnumeratedTypeFromXml(node2,ActivityType.class));
// DL 
// DL             else if (nodeName.equals("tt:authTime"))
// DL                 auth.setAuthTime(XmlTranslator.DateFromXml(node2));
// DL 
// DL             else if (nodeName.equals("tt:authPerson"))
// DL                 auth.setAuthPerson(XmlTranslator.PersonReachFromXml(node2));
// DL 
// DL             else if (nodeName.equals("#comment" ) )
// DL                 continue;
// DL 
// DL         }
// DL 
// DL         return auth;
// DL 
// DL 
// DL 
// DL     }
// DL 
// DL 
// DL 
// DL     public static PersonReach PersonReachFromXml(Node node)
// DL     {
// DL 
// DL         PersonReach pr = new PersonReachImpl();
// DL 
// DL         //go thru node list
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL 
// DL         for (int x=0; x<nodeList.getLength(); x++)
// DL         {
// DL             node2 = nodeList.item(x);
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL             String nodeName = node2.getNodeName();
// DL 
// DL             if (nodeName.equals("tt:email"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                     pr.setEmail(node2.getFirstChild().getNodeValue());
// DL             }
// DL             else if (nodeName.equals("tt:organizationName"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                     pr.setOrganizationName(node2.getFirstChild().getNodeValue());
// DL             }
// DL             else if (nodeName.equals("tt:fax"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                     pr.setFax(node2.getFirstChild().getNodeValue());
// DL             }
// DL             else if (nodeName.equals("tt:location"))
// DL             {
// DL                 pr.setLocation(XmlTranslator.AddressFromXml(node2));
// DL             }
// DL             else if (nodeName.equals("tt:name"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                     pr.setName(node2.getFirstChild().getNodeValue());
// DL             }
// DL             else if (nodeName.equals("tt:number"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                     pr.setNumber(node2.getFirstChild().getNodeValue());
// DL             }
// DL             else if (nodeName.equals("tt:phone"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                     pr.setPhone(node2.getFirstChild().getNodeValue());
// DL             }
// DL             else if (nodeName.equals("tt:responsible"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                     pr.setResponsible(node2.getFirstChild().getNodeValue());
// DL             }
// DL             else if (nodeName.equals("tt:sMSAddress"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                     pr.setSMSAddress(node2.getFirstChild().getNodeValue());
// DL             }
// DL             else if (nodeName.equals("#comment" ) )
// DL                 continue;
// DL 
// DL         }
// DL 
// DL         return pr;
// DL 
// DL     }
// DL 
// DL     public static Address AddressFromXml(Node node)
// DL     {
// DL 
// DL         javax.oss.trouble.NorthAmericaAddress addr = new NorthAmericaAddressImpl();
// DL 
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL 
// DL         for (int x=0; x<nodeList.getLength(); x++)
// DL         {
// DL             node2 = nodeList.item(x);
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL             String nodeName = node2.getNodeName();
// DL             if (nodeName.equals("tt:addressInfo"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                     addr.setAddressInfo(node2.getFirstChild().getNodeValue());
// DL             }
// DL             else if (nodeName.equals("tt:civicAddress"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                     addr.setCivicAddress(node2.getFirstChild().getNodeValue());
// DL             }
// DL             else if (nodeName.equals("tt:city"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                     addr.setCity(node2.getFirstChild().getNodeValue());
// DL             }
// DL             else if (nodeName.equals("tt:state"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                     addr.setState(node2.getFirstChild().getNodeValue());
// DL             }
// DL             else if (nodeName.equals("tt:zip"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                     addr.setZip(node2.getFirstChild().getNodeValue());
// DL             }
// DL             else if (nodeName.equals("#comment" ) )
// DL                 continue;
// DL 
// DL         }
// DL 
// DL         return addr;
// DL 
// DL     }
// DL 
// DL 
// DL     public static boolean BooleanFromXml(Node node)
// DL     {
// DL 
// DL         String val;
// DL         if (node.hasChildNodes())
// DL         {
// DL             val = node.getFirstChild().getNodeValue();
// DL             if (val.equals("true"))
// DL                 return true;
// DL         }
// DL         return false;   //default is false
// DL 
// DL     }
// DL 
// DL     public static TimeLength TimeLengthFromXml(Node node)
// DL     {
// DL         log ( "+++++TimeLengthFromXml++++");
// DL         TimeLength tl = new TimeLengthImpl();
// DL 
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL 
// DL         Integer anInt = null;
// DL 
// DL         for (int x=0; x<nodeList.getLength(); x++)
// DL         {
// DL             node2 = nodeList.item(x);
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL             String nodeName = node2.getNodeName();
// DL             log ( "node name: " + nodeName );
// DL 
// DL             if (nodeName.equals("tt:years"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                 {   log ( "tt:years: " +  node2.getFirstChild().getNodeValue() );
// DL                     anInt = new Integer(node2.getFirstChild().getNodeValue());
// DL                     tl.setYears(anInt.shortValue());
// DL                 }
// DL             }
// DL             else if (nodeName.equals("tt:months"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                 {
// DL                     anInt = new Integer(node2.getFirstChild().getNodeValue());
// DL                     tl.setMonths(anInt.shortValue());
// DL                 }
// DL             }
// DL             else if (nodeName.equals("tt:days"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                 {
// DL                     anInt = new Integer(node2.getFirstChild().getNodeValue());
// DL                     tl.setDays(anInt.shortValue());
// DL                 }
// DL             }
// DL             else if (nodeName.equals("tt:hours"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                 {
// DL                     anInt = new Integer(node2.getFirstChild().getNodeValue());
// DL                     tl.setHours(anInt.shortValue());
// DL                 }
// DL             }
// DL             else if (nodeName.equals("tt:minutes"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                 {
// DL                     anInt = new Integer(node2.getFirstChild().getNodeValue());
// DL                     tl.setMinutes(anInt.shortValue());;
// DL                 }
// DL             }
// DL             else if (nodeName.equals("tt:seconds"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                 {
// DL                     anInt = new Integer(node2.getFirstChild().getNodeValue());
// DL                     tl.setSeconds(anInt.shortValue());
// DL                 }
// DL             }
// DL             else if (nodeName.equals("tt:msecs"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                 {
// DL                     anInt = new Integer(node2.getFirstChild().getNodeValue());
// DL                     tl.setMsecs(anInt.shortValue());
// DL                 }
// DL             }
// DL             else if (nodeName.equals("#comment" ) )
// DL                 continue;
// DL 
// DL         }
// DL 
// DL         return tl;
// DL 
// DL     }
// DL 
// DL     public static javax.oss.trouble.Time TimeFromXml(Node node)
// DL     {
// DL 
// DL         javax.oss.trouble.Time time = new TimeImpl();
// DL 
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL 
// DL         Integer anInt = null;
// DL 
// DL         for (int x=0; x<nodeList.getLength(); x++)
// DL         {
// DL             node2 = nodeList.item(x);
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL             String nodeName = node2.getNodeName();
// DL 
// DL             if (nodeName.equals("tt:hour"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                 anInt = new Integer(node2.getFirstChild().getNodeValue());
// DL                 time.setHour(anInt.shortValue());
// DL             }
// DL             else if (nodeName.equals("tt:minute"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                 anInt = new Integer(node2.getFirstChild().getNodeValue());
// DL                 time.setMinute(anInt.shortValue());;
// DL             }
// DL             else if (nodeName.equals("tt:seconds"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                 anInt = new Integer(node2.getFirstChild().getNodeValue());
// DL                 time.setSeconds(anInt.shortValue());
// DL             }
// DL             else if (nodeName.equals("tt:msecs"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                 anInt = new Integer(node2.getFirstChild().getNodeValue());
// DL                 time.setMilliSeconds(anInt.shortValue());
// DL             }
// DL             else if (nodeName.equals("tt:utcOffset"))
// DL             {
// DL                 if (node2.hasChildNodes())
// DL                 anInt = new Integer(node2.getFirstChild().getNodeValue());
// DL                 time.setUtcOffset(anInt.shortValue());
// DL             }
// DL             else if (nodeName.equals("#comment" ) )
// DL                 continue;
// DL 
// DL         }
// DL 
// DL         return time;
// DL 
// DL     }
// DL 
// DL 
// DL     public static TimeInterval TimeIntervalFromXml(Node node)
// DL     {
// DL 
// DL         TimeInterval ti = null;
// DL         if (! (node.hasChildNodes()) )      //is it nilled?
// DL             return ti;
// DL 
// DL 
// DL         ti = new TimeIntervalImpl();
// DL 
// DL         NodeList nodeList = node.getChildNodes();
// DL         Node node2;
// DL 
// DL         Integer anInt = null;
// DL 
// DL         for (int x=0; x<nodeList.getLength(); x++)
// DL         {
// DL             node2 = nodeList.item(x);
// DL             if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
// DL             String nodeName = node2.getNodeName();
// DL 
// DL             if (nodeName.equals("tt:intervalBegin"))
// DL                 ti.setIntervalBegin(XmlTranslator.TimeFromXml(node2));
// DL             else if (nodeName.equals("tt:intervalEnd"))
// DL                 ti.setIntervalEnd(XmlTranslator.TimeFromXml(node2));
// DL             else if (nodeName.equals("#comment" ) )
// DL                 continue;
// DL         }
// DL 
// DL         return ti;
// DL 
// DL     }
// DL     public String toXml( String elementName )
// DL     {
// DL         return new String();
// DL     }
// DL 
// DL     public String getXmlHeader()
// DL     {
// DL         return new String();
// DL     }
// DL     public static String troubleTicketAttributeValueChangeEventtoXml(
// DL     TroubleTicketAttributeValueChangeEvent event, String tag)
// DL     throws org.xml.sax.SAXException
// DL     {
// DL         TroubleTicketValue ttValue = event.getTroubleTicketValue();
// DL         String[] populatedAttr = ttValue.getPopulatedAttributeNames();
// DL         log ("TTCreateEventImpl:toXml - popAttrs: " + populatedAttr.length);
// DL         ((TroubleTicketValueImpl)ttValue).setDesiredAttributes( populatedAttr, false );
// DL         // As a first step convert to ttValue into an xml string format.
// DL         String xmlTTValue = ((TroubleTicketValueImpl)ttValue).toXml();
// DL         String eventType = TroubleTicketAttributeValueChangeEvent.class.getName();
// DL         return addXmlRootElement( event, tag, xmlTTValue, eventType );
// DL     }
// DL     public static String troubleTicketCancellationEventtoXml(
// DL     TroubleTicketCancellationEvent event, String tag)
// DL     throws org.xml.sax.SAXException
// DL     {
// DL         StringBuffer strBuffer = new StringBuffer();
// DL         //TroubleTicketInterfaceReflector ttIntReflector = new TroubleTicketInterfaceReflector();
// DL 
// DL         TroubleTicketKey trKey = event.getTroubleTicketKey();
// DL         String xmlKey = ((TroubleTicketKeyImpl)trKey).toXml();
// DL         strBuffer.append( xmlKey );
// DL         //---------------------------------
// DL 
// DL         strBuffer.append("<tt:closeOutNarr>");
// DL         strBuffer.append( event.getCloseOutNarr() );
// DL         strBuffer.append("</tt:closeOutNarr>\n");
// DL 
// DL         String eventType = TroubleTicketCancellationEvent.class.getName();
// DL         return addXmlRootElement( event, tag, strBuffer.toString(), eventType );
// DL     }
// DL 
// DL 
// DL     public static String troubleTicketCloseOutEventtoXml(
// DL     TroubleTicketCloseOutEvent event, String tag)
// DL     throws org.xml.sax.SAXException
// DL     {
// DL         TroubleTicketValue ttValue = event.getTroubleTicketValue();
// DL         String[] populatedAttr = ((TroubleTicketValueImpl)ttValue).getPopulatedAttributeNames();
// DL         ((TroubleTicketValueImpl)ttValue).setDesiredAttributes( populatedAttr, false );
// DL 
// DL         //translate the contained ttValue into an xml string format.
// DL         String xmlTTValue = ((TroubleTicketValueImpl)ttValue).toXml();
// DL 
// DL         String eventType = TroubleTicketCloseOutEvent.class.getName();
// DL         return addXmlRootElement( event, tag, xmlTTValue, eventType );
// DL     }
// DL 
// DL     public static String troubleTicketCreateEventtoXml(
// DL     TroubleTicketCreateEvent event, String tag)
// DL     throws org.xml.sax.SAXException
// DL     {
// DL         TroubleTicketValue ttValue = event.getTroubleTicketValue();
// DL         String[] populatedAttr = ((TroubleTicketValueImpl)ttValue).getPopulatedAttributeNames();
// DL         log ("TTCreateEventImpl:toXml - popAttrs: " + populatedAttr.length);
// DL         ((TroubleTicketValueImpl)ttValue).setDesiredAttributes( populatedAttr, false );
// DL         // As a first step convert to ttValue into an xml string format.
// DL         String xmlTTValue = ((TroubleTicketValueImpl)ttValue).toXml();
// DL 
// DL         String eventType = TroubleTicketCreateEvent.class.getName();
// DL         return addXmlRootElement( event, tag, xmlTTValue, eventType );
// DL     }
// DL 
// DL     public static String troubleTicketStatusChangeEventtoXml(
// DL     TroubleTicketStatusChangeEvent event, String tag)
// DL     throws org.xml.sax.SAXException
// DL     {
// DL         StringBuffer strBuffer = new StringBuffer();
// DL         TroubleTicketInterfaceReflector ttIntReflector = new TroubleTicketInterfaceReflector();
// DL 
// DL         TroubleTicketKey trKey = event.getTroubleTicketKey();
// DL         String xmlKey = ((TroubleTicketKeyImpl)trKey).toXml();
// DL         strBuffer.append( xmlKey );
// DL         //---------------------------------
// DL         String stateName = null;
// DL         if ( event.getState() != -1)
// DL         {
// DL             try
// DL             {
// DL                 stateName = (String)(ttIntReflector.getInterfaceFieldName( TroubleState.class, new Integer(event.getState())));
// DL             }
// DL             catch( javax.oss.IllegalArgumentException iLLEx )
// DL             {
// DL                 throw new org.xml.sax.SAXException( iLLEx.getMessage() );
// DL             }
// DL             catch( java.lang.IllegalAccessException iLLAccEx )
// DL             {
// DL                 throw new org.xml.sax.SAXException( iLLAccEx.getMessage() );
// DL             }
// DL         }
// DL 
// DL         strBuffer.append("<tt:troubleState>");
// DL         strBuffer.append( stateName );
// DL         strBuffer.append("</tt:troubleState>\n");
// DL 
// DL         //----------------------------------
// DL 
// DL         String statusName = null;
// DL         if ( event.getStatus() != -1)
// DL         {
// DL             try
// DL             {
// DL                 statusName = (String)(ttIntReflector.getInterfaceFieldName( TroubleStatus.class, new Integer(event.getStatus())));
// DL             }
// DL             catch( javax.oss.IllegalArgumentException iLLEx )
// DL             {
// DL                 throw new org.xml.sax.SAXException( iLLEx.getMessage() );
// DL             }
// DL             catch( java.lang.IllegalAccessException iLLAccEx )
// DL             {
// DL                 throw new org.xml.sax.SAXException( iLLAccEx.getMessage() );
// DL             }
// DL         }
// DL 
// DL         strBuffer.append("<tt:troubleStatus>");
// DL         strBuffer.append( statusName );
// DL         strBuffer.append("</tt:troubleStatus>\n");
// DL 
// DL         //----------------------------------
// DL 
// DL         java.util.Date statusDate = event.getStatusTime();
// DL         strBuffer.append("<tt:statusTime>");
// DL         if ( event.getStatusTime() == null )
// DL         {
// DL             //strBuffer.append( (String)null );
// DL             strBuffer.append( "0000-00-00T00:00:00Z" );
// DL 
// DL         }
// DL         else
// DL         {
// DL             strBuffer.append( getXmlDate( event.getStatusTime() ) );
// DL         }
// DL 
// DL         strBuffer.append("</tt:statusTime>\n");
// DL 
// DL         String eventType = TroubleTicketStatusChangeEvent.class.getName();
// DL         return addXmlRootElement( event, tag, strBuffer.toString(), eventType );
// DL     }
// DL 
// DL 
// DL     public static String addXmlRootElement( javax.oss.Event event,
// DL     String RootElementTag, String xmlEventCore , String eventType )
// DL     {
// DL         StringBuffer strBuffer = new StringBuffer();
// DL 
// DL 
// DL         strBuffer.append("<" + RootElementTag + ">");
// DL         strBuffer.append("<tt:eventType>");
// DL         strBuffer.append( eventType );
// DL         strBuffer.append("</tt:eventType>\n");
// DL 
// DL         strBuffer.append("<tt:eventTime>");
// DL 
// DL         if ( event.getEventTime() != null )
// DL         {
// DL             strBuffer.append( getXmlDate(event.getEventTime()) );
// DL         }
// DL         else
// DL         {
// DL             strBuffer.append( "0000-00-00T00:00:00Z" );
// DL         }
// DL         strBuffer.append("</tt:eventTime>\n");
// DL 
// DL 
// DL         strBuffer.append("<tt:applicationDN>");
// DL         strBuffer.append( event.getApplicationDN() );
// DL         strBuffer.append("</tt:applicationDN>\n");
// DL 
// DL 
// DL         strBuffer.append( xmlEventCore );
// DL 
// DL 
// DL         strBuffer.append("</" + RootElementTag + ">");
// DL 
// DL         return strBuffer.toString();
// DL     }
}

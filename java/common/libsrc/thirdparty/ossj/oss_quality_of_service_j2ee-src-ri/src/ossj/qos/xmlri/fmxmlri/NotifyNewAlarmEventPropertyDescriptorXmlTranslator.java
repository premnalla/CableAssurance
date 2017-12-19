package ossj.qos.xmlri.fmxmlri;



/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc.
 * @author Vijay Sharma
 * @version 1.0
 */
 
import java.util.*;
import java.text.*; 
import java.lang.StringBuffer;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

import javax.oss.ManagedEntityValue;



import javax.oss.MultiValueList;

import javax.oss.fm.monitor.*;
import ossj.qos.fmri.*;

public class NotifyNewAlarmEventPropertyDescriptorXmlTranslator
{


    public NotifyNewAlarmEventPropertyDescriptorXmlTranslator()
    {

    }

    public static String toXml(NotifyNewAlarmEventPropertyDescriptor alarmEventProp,String elementName )
    throws org.xml.sax.SAXException
    {
        StringBuffer sb = new StringBuffer();
        if (alarmEventProp == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
	    sb.append("<" + elementName + ">");
	    String eventType =  alarmEventProp.getEventType();
	    String[] propNames = alarmEventProp.getPropertyNames();
	    String[] propTypes = alarmEventProp.getPropertyTypes();
	    sb.append("<eventType>"+eventType+"</eventType>");
	    sb.append("<propertyNames>");
	    for(int i=0;i<propNames.length;i++)
	    {
		sb.append("<Item>");
		sb.append(propNames[i]);
		sb.append("</Item>");
	    }
	    sb.append("</propertyNames>");
    	    sb.append("<propertyTypes>");
    	    for(int j=0;j<propTypes.length;j++)
	    {
		sb.append("<Item>");
		sb.append(propTypes[j]);
		sb.append("</Item>");
	    }
    	    sb.append("</propertyTypes>");
	    sb.append("</" + elementName + ">");
	}
    return sb.toString();

    }




    public static Object fromXml(org.w3c.dom.Element element, String type  )
    throws IllegalArgumentException 
    {
        try 
	{
            if( type.equals(NotifyNewAlarmEventPropertyDescriptor.class.getName() )) 
	    {
                NotifyNewAlarmEventPropertyDescriptor alval = null;
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, alval);
                return alval;
            }
        }
        catch( org.xml.sax.SAXException ex ) 
	{
            return new IllegalArgumentException( ex.getMessage() );
        }
        return null;
    }

    public static void fromXmlNoRoot(org.jdom.Element node, NotifyNewAlarmEventPropertyDescriptor aval)
    throws org.xml.sax.SAXException
    {
    /*
     NodeList nodeList1 = node.getChildNodes();
        Node node2;
        //VP: System.out.println("fromXmlNoRoot--> translated attributes:");
        //VP: System.out.println("fromXmlNoRoot--> length:" + nodeList1.getLength());
        for (int i=0; i<nodeList1.getLength(); i++)
        {
            node2 = nodeList1.item(i);
            if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
            String nodeName1 = node2.getNodeName();
            //VP: System.out.println( "Node Name = " + nodeName1 );
            if ( nodeName1.equals("fm:attributeName") )
            if (node2.hasChildNodes())
            aval.setAttributeName(node2.getFirstChild().getNodeValue());
            else if ( nodeName1.equals("fm:attributeType") )
            {
                if (node2.hasChildNodes())
                aval.setAttributeType(node2.getFirstChild().getNodeValue());
            }
            else if ( nodeName1.equals("fm:Value") )
            {
                if (node2.hasChildNodes())
                aval.setValue(node2.getFirstChild().getNodeValue());
            }


    }
*/
}

}

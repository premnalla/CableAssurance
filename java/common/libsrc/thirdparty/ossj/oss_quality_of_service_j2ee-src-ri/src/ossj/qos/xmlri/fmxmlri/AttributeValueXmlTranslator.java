package ossj.qos.xmlri.fmxmlri;

/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc.
 * @author Vijay Sharma
 * @version 1.0
 */

import java.util.Vector;
import java.util.HashMap;
import java.util.Date;
import java.util.Iterator;
import java.lang.StringBuffer;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.jdom.input.*;
import javax.oss.ManagedEntityValue;

import javax.oss.MultiValueList;

import javax.oss.fm.monitor.*;
import ossj.qos.fmri.*;

public class AttributeValueXmlTranslator
{

    public AttributeValueXmlTranslator()
    {

    }

    /**
     * <fm:AttributeValue>
     *  <attributeName>fdgsdfg</attributeName>
     *  <attributeType>fdgsd</attributeType>
     *  <value>dfsgsdf</value>
     * </fm:AttributeValue>
     */
    public static String toXml(AttributeValue attValue,String elementName )
    throws org.xml.sax.SAXException
    {
	StringBuffer sb = new StringBuffer();
        if (attValue == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
            sb.append("<" + elementName + ">\n");
	    String attName = attValue.getAttributeName();
	    String type    = attValue.getAttributeType();
	    String value   = (String)attValue.getValue();
	    
	    if(attName == null)
		sb.append("<attributeName xsi:nil=\"true\"></attributeName>\n");
            else
		sb.append("<attributeName>" + attName + "</attributeName>\n");
		
	    if(type == null)
		sb.append("<attributeType xsi:nil=\"true\"></attributeType>\n");
	    else
		sb.append("<attributeType>"+type+"</attributeType>\n");
		
	    if(value == null)
		sb.append("<value xsi:nil=\"true\"></value>\n");
	    else
		sb.append("<value>"+value+"</value>\n");
	    
	    sb.append("</" + elementName + ">");	
        }
    return  sb.toString();	
    }




    public static Object fromXml(org.w3c.dom.Element element, String type  )
    throws IllegalArgumentException {
    try {
            if( type.equals(AttributeValue.class.getName() )) 
	    {
                AttributeValue attValue = new AttributeValueImpl();
		org.jdom.input.DOMBuilder builder= new DOMBuilder();
                org.jdom.Element tempElement = builder.build(element);		
                fromXmlNoRoot(tempElement, attValue);
                return attValue;
            }

        }
        catch( org.xml.sax.SAXException ex ) {
            return new IllegalArgumentException( ex.getMessage() );
        }
        return null;


    }

    public static void fromXmlNoRoot(org.jdom.Element element,AttributeValue attValue)
    throws org.xml.sax.SAXException
    {
	java.util.List children = element.getChildren();
	Iterator it = children.iterator();
	org.jdom.Element child = null;
	String elementName = null;
	String elementValue = null;
	while(it.hasNext())
	{
	    child = (org.jdom.Element)it.next();
	    elementName = child.getName();
	    elementValue = child.getTextTrim();
	    if(elementName.equalsIgnoreCase("attributeName"))
	    {
		attValue.setAttributeName(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("attributeType"))
	    {
		attValue.setAttributeType(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("value"))
	    {
		attValue.setValue(elementValue);
	    }
	    
	}//end while
	
    }//end fromXmlNoRoot

}//end class


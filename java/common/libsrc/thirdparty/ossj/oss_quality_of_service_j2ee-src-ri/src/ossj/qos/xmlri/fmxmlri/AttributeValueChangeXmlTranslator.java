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

public class AttributeValueChangeXmlTranslator
{

    public AttributeValueChangeXmlTranslator()
    {

    }

    /**
     * <fm:AttributeValueChange>
     *      <attributeName>fdgsdfg</attributeName>
     *      <attributeType>fdgsd</attributeType>
     *      <oldValue>dfsgsdf</oldValue>
     *      <newValue>dsfgsd</newValue>		
     * </fm:AttributeValueChange>

     */
    
    public static String toXml(AttributeValueChange attValChange,String elementName )
    throws org.xml.sax.SAXException
    {
	StringBuffer sb = new StringBuffer();
        if (attValChange == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
            String name = attValChange.getAttributeName();
	    String type = attValChange.getAttributeType();
	    String oldValue = (String)attValChange.getOldValue();
	    String newValue = (String)attValChange.getNewValue();
	    
	    sb.append("<" + elementName + ">\n");
	    if(name == null)
		sb.append("<attributeName xsi:nil=\"true\"></alarmPrimaryKey>\n");
            else
		sb.append("<attributeName>" + name + "</attributeName>\n");
	    if(type == null)
		sb.append("<attributeType xsi:nil=\"true\"></attributeType>\n");
            else
		sb.append("<attributeType>" + type + "</attributeType>\n");
	    if(oldValue == null)
		sb.append("<oldValue xsi:nil=\"true\"></oldValue>\n");
            else
		sb.append("<oldValue>" + oldValue + "</oldValue>\n");
	    if(newValue == null)
		sb.append("<newValue xsi:nil=\"true\"></newValue>\n");
            else
		sb.append("<newValue>" + newValue + "</newValue>\n");
		
	    sb.append("</" + elementName + ">");	
        }
    return  sb.toString();
    }




    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException 
    {
        try 
        {
            if( type.equals(AttributeValueChange.class.getName() )) 
	    {
                AttributeValueChange attValChange = new AttributeValueChangeImpl();
		org.jdom.input.DOMBuilder builder= new DOMBuilder();
                org.jdom.Element tempElement = builder.build(element);		
                fromXmlNoRoot(tempElement, attValChange);
                return attValChange;
            }

        }
        catch( org.xml.sax.SAXException ex ) {
            return new IllegalArgumentException( ex.getMessage() );
        }
        return null;
    }

    public static void fromXmlNoRoot(org.jdom.Element element,AttributeValueChange attValChange)
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
		attValChange.setAttributeName(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("oldValue"))
	    {
		attValChange.setOldValue(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("newValue"))
	    {
		attValChange.setNewValue(elementValue);
	    }
	}//end while
    }

}//end class



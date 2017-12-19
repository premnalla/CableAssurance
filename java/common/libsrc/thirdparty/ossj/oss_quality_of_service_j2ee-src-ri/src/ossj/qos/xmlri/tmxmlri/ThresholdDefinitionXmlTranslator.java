package ossj.qos.xmlri.tmxmlri;

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
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
//import ossj.qos.ri.pm.threshold.*;
import ossj.qos.pm.threshold.*;
import javax.oss.pm.threshold.*;
//import ossj.qos.ri.pm.measurement.*;
//import ossj.qos.xmlri.pmxmlri.PerformanceAttributeDescriptorXmlTranslator;
import javax.oss.pm.threshold.*;
import javax.oss.pm.measurement.*;

public class ThresholdDefinitionXmlTranslator
{

  public ThresholdDefinitionXmlTranslator()
  {
  }

/**
<ThresholdDefinition>
	<descriptor>
	    <name>sdsds</name>
	    <measurement:baseAttributeTypes>dsdsd</measurement:baseAttributeTypes>
	    <measurement:baseCollectionMethods>sdsds</measurement:baseCollectionMethods>
	    <isArray>false<isArray>
	</descriptor>
	<threholdValue>asdasd</threholdValue>
	<offset>sadad</offset>
	<threshold:baseThresholdDirection>sdsdsds</threshold:baseThresholdDirection>
</ThresholdDefinition>
 */
public static String toXml(ThresholdDefinition object, String elementName)
throws org.xml.sax.SAXException
    {
        StringBuffer sb = new StringBuffer();
        if (object == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
	    sb.append("<" + elementName + ">");
	    PerformanceAttributeDescriptor attrDesc = object.getAttributeDescriptor();
	    String attrDescXml =  PerformanceAttributeDescriptorXmlTranslator.toXml(attrDesc,"descriptor");
	    String value = (String)object.getValue();
	    String offset = (String)object.getOffset();
	    int direction = object.getDirection();
	    sb.append(attrDescXml);
	    sb.append("<threholdValue>"+value+"</threholdValue>");
	    sb.append("<offset>"+offset+"</offset>");
	    sb.append("<threshold:baseThresholdDirection>"+direction+"</threshold:baseThresholdDirection>");
	    sb.append("</" + elementName + ">");
	}
    return sb.toString();
    }


    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {
        try
        {
            if(type.equals(ThresholdDefinition.class.getName() ))
	    {
                ThresholdDefinition thresholdDef = new ThresholdDefinitionImpl();
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, thresholdDef);
                return thresholdDef;
             }

	}
        catch( org.xml.sax.SAXException ex )
	{
            return new IllegalArgumentException( ex.getMessage() );
        }
        catch(Exception ex)
	{
	    return null;
    	}
    return null;
    }

    public static void fromXmlNoRoot(org.jdom.Element element, ThresholdDefinition object)
    throws org.xml.sax.SAXException, ParseException, JDOMException
    {
	DOMOutputter domOutputter = new DOMOutputter();
	List list = element.getChildren();
	Iterator it = list.iterator();
	String elementName = null;
	String elementValue = null;
	org.jdom.Element localElement = null;
	while(it.hasNext())
	{
	    localElement = (org.jdom.Element)it.next();
	    elementName = localElement.getName();
	    elementValue = localElement.getTextTrim();
	    if(elementName.equalsIgnoreCase("descriptor"))
	    {
		org.w3c.dom.Element w3cElement = domOutputter.output(localElement);
		String className = PerformanceAttributeDescriptor.class.getName();
		PerformanceAttributeDescriptor performAtt = null;
		performAtt = (PerformanceAttributeDescriptor)PerformanceAttributeDescriptorXmlTranslator.fromXml(w3cElement,className);
		object.setAttributeDescriptor(performAtt);
	    }
	    else if(elementName.equalsIgnoreCase("threholdValue"))
	    {
		object.setValue(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("offset"))
	    {
		object.setOffset(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("threshold:baseThresholdDirection"))
	    {
		int direction = Integer.parseInt(elementValue);
		object.setDirection(direction);
	    }

	}//end while
    }

}//end class
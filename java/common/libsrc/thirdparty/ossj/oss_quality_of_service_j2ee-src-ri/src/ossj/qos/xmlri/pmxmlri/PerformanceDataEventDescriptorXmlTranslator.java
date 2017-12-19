package ossj.qos.xmlri.pmxmlri;

/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc.
 * @author Vijay Sharma
 * @version 1.0
 */
 
import javax.oss.pm.measurement.*;
import java.text.*;
import ossj.qos.pm.measurement.PerformanceDataEventDescriptorImpl;
import java.util.*;
import org.jdom.*;

public class PerformanceDataEventDescriptorXmlTranslator 
{

  public PerformanceDataEventDescriptorXmlTranslator() 
  {
  }
  
/**
    <measurement:PerformanceDataEventDescriptor>
	<eventType></eventType>
	<propertyNames>
		<item></item>
		<item></item>
	</propertyNames>
	<propertyTypes>
		<item></item>
		<item></item>
	</propertyTypes>
    </measurement:PerformanceDataEventDescriptor>
 */  
  
public static String toXml(PerformanceDataEventDescriptor object, String elementName)
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
	    String eventType = object.getEventType();
	    String[] props = object.getPropertyNames();
	    String[] types = object.getPropertyTypes();
	    sb.append("<propertyNames>");
	    for(int i=0;i<props.length;i++)    
	    {
		sb.append("<Item>" + props[i] + "</Item>");
	    }
	    sb.append("</propertyNames>");
	    sb.append("<propertyTypes>");
	    for(int i=0;i<types.length;i++)    
	    {
		sb.append("<Item>" + types[i] + "</Item>");
	    }
	    sb.append("</propertyTypes>");
	    sb.append("</" + elementName + ">");
	}  
    return sb.toString();
}


    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException 
    {
       try 
        {
           if(type.equals(PerformanceDataEventDescriptor.class.getName() )) 
	    {
                PerformanceDataEventDescriptor performData = new PerformanceDataEventDescriptorImpl();
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, performData);
                return performData;
             }
	}
        catch( org.xml.sax.SAXException ex ) 
	{
            return new IllegalArgumentException( ex.getMessage() );
        }
        catch(ParseException pe) 
	{
            return null;
        }
	
        return null;    
    }  

    public static void fromXmlNoRoot( org.jdom.Element element, PerformanceDataEventDescriptor object)
    throws org.xml.sax.SAXException, ParseException
    {
    
/*
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
	    if(elementName.equalsIgnoreCase("eventType"))
	    {
		object.segetEventType()
	    }
	}	
*/	    	
    }
  
}//end class
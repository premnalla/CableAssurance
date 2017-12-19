package ossj.qos.xmlri.pmxmlri;

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
import javax.oss.pm.measurement.*;
import ossj.qos.pm.measurement.*;

 
public class PerformanceDataAvailableEventDescriptorXmlTranslator {

  public PerformanceDataAvailableEventDescriptorXmlTranslator() 
  {
  }
  
  
 /**
    <measurement:PerformanceDataAvailableEventDescriptor>
	<eventType></eventType>	
	<propertyNames>
	    <Item></Item>
	    <Item></Item>
	</propertyNames>
	<propertyTypes>
	    <Item></Item>
	    <Item></Item>
	</propertyTypes>
    </measurement:PerformanceDataAvailableEventDescriptor>
  */ 
  
 public static String toXml(PerformanceDataAvailableEventDescriptor eventDesc,String elementName )
    throws org.xml.sax.SAXException
    {

        StringBuffer sb= new StringBuffer();
        if (eventDesc == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
            String eventType = eventDesc.getEventType();
	    String[] propertyNames = eventDesc.getPropertyNames();
	    String[] propertyTypes = eventDesc.getPropertyTypes();
	    sb.append("<" + elementName + ">");
	    if(eventType == null)
	    {
	        sb.append("<eventType xsi:nill=\"true\"></eventType>");
	    }
	    else 
	    {
		sb.append("<eventType>"+eventType+"</eventType>");
	    }
	    
	    sb.append("<propertyNames>");
	    for(int i=0;i<propertyNames.length;i++)
	    {
	        sb.append("<item>"+propertyNames[i]+"</item>");
	    }
	    sb.append("</propertyNames>");
	    sb.append("<propertyTypes>");
	    for(int i=0;i<propertyNames.length;i++)
	    {
		sb.append("<item>"+propertyTypes[i]+"</item>");
	    }
	    sb.append("</propertyTypes>");
	    sb.append("</" + elementName + ">\n");	    	
        }
        return  sb.toString();
    }  
  
  public static Object fromXml( org.w3c.dom.Element element, String type  )
    throws IllegalArgumentException 
    {
        try 
	{
            if( type.equals(PerformanceDataAvailableEventDescriptor.class.getName() )) 
	    {
                PerformanceDataAvailableEventDescriptor performData = new PerformanceDataAvailableEventDescriptorImpl();
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
        catch(ParseException pEx) 
	{
            return null;
	}
	
        return null;


    }
  
  
  /*
  <measurement:PerformanceDataAvailableEventDescriptor>
	<eventType></eventType>	
	<propertyNames></propertyNames>
	    <item></item>
	    <item></item>
	<propertyTYpes></propertyTYpes>
	    <item></item>
	    <item></item>
  </measurement:PerformanceDataAvailableEventDescriptor>
  */
    public static void fromXmlNoRoot(org.jdom.Element element, PerformanceDataAvailableEventDescriptor performData)
    throws org.xml.sax.SAXException, ParseException
    {

	java.util.List children = element.getChildren();
	Iterator it = children.iterator();
	org.jdom.Element child = null;
	String childName = null;
	String childValue = null;
	while(it.hasNext())
	{
	    child = (org.jdom.Element)it.next();
	    childName = child.getName();
	    childValue = child.getTextTrim();
	    if(childName.equalsIgnoreCase("eventType"))
	    {
		//performData.se
		
	    }
	    else if(childName.equalsIgnoreCase("propertyNames"))
	    {
	    
	    }
	}//end while
    }
}//end class
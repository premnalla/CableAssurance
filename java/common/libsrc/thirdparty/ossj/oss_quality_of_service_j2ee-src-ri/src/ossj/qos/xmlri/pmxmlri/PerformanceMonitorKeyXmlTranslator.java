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
import javax.oss.pm.measurement.PerformanceMonitorKey;
import javax.oss.ApplicationContext;
import ossj.qos.pm.measurement.PerformanceMonitorKeyImpl;
import javax.oss.fm.*;
import org.jdom.*;
import org.jdom.input.*;

 

public class PerformanceMonitorKeyXmlTranslator 
{

  public PerformanceMonitorKeyXmlTranslator() 
  {
  }
  

  
    public static String toXml(PerformanceMonitorKey performKey, String elementName)
    {
        StringBuffer sb = new StringBuffer();
	boolean flag = false;
        if (performKey == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
	    sb.append("<" + elementName + ">");	
	    String factoryClass = performKey.getApplicationContext().getFactoryClass();
            String Url = performKey.getApplicationContext().getURL();
	    Map map = performKey.getApplicationContext().getSystemProperties();
	    String appDN =  performKey.getApplicationDN();
	    String type =  performKey.getType();
	    String key =  performKey.getPerformanceMonitorPrimaryKey();
	    
	    
	    sb.append("<applicationContext>");
	    sb.append("<factoryClass>");
	    sb.append(factoryClass);
	    sb.append("</factoryClass>");
	    sb.append("<url>");
	    sb.append(Url);	    
	    sb.append("</url>");	    	
	    sb.append("<systemProperties>");
	    
	    Iterator hashMapKeys =  map.keySet().iterator();
	    Iterator hashMapValues =  map.values().iterator();
	    while(hashMapKeys.hasNext())
	    {
		sb.append("<property>");
		sb.append("<name>"+hashMapKeys.next()+"</name>");
	        sb.append("<value>"+hashMapValues.next()+"</value>");
		sb.append("</property>");		
	    }
	    sb.append("</systemProperties>");
	    sb.append("</applicationContext>");
	    sb.append("<applicationDN>");	    
	    sb.append(appDN);
	    sb.append("</applicationDN>");	    	    
	    sb.append("<type>");	    	    
	    sb.append(type);
	    sb.append("</type>");	    	    
	    sb.append("<performanceMonitorPrimaryKey>");	    	    
	    sb.append(key);
	    sb.append("</performanceMonitorPrimaryKey>");	    	    
	    
	    sb.append("</" + elementName + ">");	
	}
    return sb.toString();	
    }  
  
  
  
  public static Object fromXml(org.w3c.dom.Element element, String type)
  throws IllegalArgumentException 
  {
        try 
        {
            if( type.equals(PerformanceMonitorKey.class.getName() )) 
	    {
                PerformanceMonitorKey performKey = new PerformanceMonitorKeyImpl();
		org.jdom.input.DOMBuilder builder= new DOMBuilder();
                org.jdom.Element tempElement = builder.build(element);		
                fromXmlNoRoot(tempElement, performKey);
                return performKey;
            }

        }
        catch( org.xml.sax.SAXException ex ) 
	{
            return new IllegalArgumentException( ex.getMessage() );
        }
        catch(ParseException pExc) 
	{
	    return null;
	}
	
    return null;
    }    
  

    /**
	<performanceMonitorKey>
		<applicationContext>
			<factoryClass></factoryClass>
			<url>anyURI</url>
			<systemProperties>
				<property>
					<name></name>
					<value></value>
				</property>
			</systemProperties>
		</applicationContext>
		<applicationDN></applicationDN>
		<type></type>
		<performanceMonitorPrimaryKey></performanceMonitorPrimaryKey>
	</performanceMonitorKey>
     */
    public static void fromXmlNoRoot( org.jdom.Element element, PerformanceMonitorKey performKey)
    throws org.xml.sax.SAXException, ParseException
    {
    
	  List list = element.getChildren();
	  Iterator it = list.iterator();
	  String elementName = null;
	  String elementValue = null;
	  org.jdom.Element tempElement = null;
	  while(it.hasNext())
	  {
	      tempElement = (org.jdom.Element)it.next();
	      elementName = element.getName();
	      elementValue = element.getTextTrim();
	      
	      if(elementName.equalsIgnoreCase("applicationContext"))  
	      {
		    org.jdom.Element localElement = null;
		    List appContextChildren = localElement.getChildren();
		    Iterator itApp = appContextChildren.iterator();
		    String name = null;
		    String value = null;
		    ApplicationContext appContext = performKey.getApplicationContext();		    
		    while(itApp.hasNext())
		    {
			localElement = (org.jdom.Element)itApp.next();
			name = localElement.getName();
			value = localElement.getTextTrim();
			if(name.equalsIgnoreCase("factoryClass"))
			{
			    appContext.setFactoryClass(value);
			}
			else if(name.equalsIgnoreCase("url"))
			{
			    appContext.setURL(value);
			}
			else if(name.equalsIgnoreCase("systemProperties"))
			{
			    Iterator itTemp = localElement.getChildren().iterator();
			    org.jdom.Element sysElement = null;
			    HashMap hashMap = new HashMap();
			    while(itTemp.hasNext())
			    {
				sysElement = (org.jdom.Element)itTemp.next();
				name = sysElement.getName();
				value = sysElement.getTextTrim();    
				
				if(name.equalsIgnoreCase("name"))
				{
				    hashMap.put(name,value);
				}
			    }//end while for System Properties element
			appContext.setSystemProperties(hashMap);
			}//end else if
			
		    }//end while
	      }//end if application context
	      else if(elementName.equalsIgnoreCase("applicationDN"))
	      {	
	            performKey.setApplicationDN(elementValue);
	      }
	      else if(elementName.equalsIgnoreCase("type"))
	      {	
	            performKey.setType(elementValue);
	      }
	      else if(elementName.equalsIgnoreCase("performanceMonitorPrimaryKey"))
	      {	
	            performKey.setPerformanceMonitorPrimaryKey(elementValue);
	      }
	 }//end while that brings up the top level elements in the hierarchy
    }    
  
  
}//end class
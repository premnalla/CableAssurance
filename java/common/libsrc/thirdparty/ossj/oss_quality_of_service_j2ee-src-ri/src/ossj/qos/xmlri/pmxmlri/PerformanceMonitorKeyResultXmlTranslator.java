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
import java.util.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import ossj.qos.*;
import ossj.qos.pm.measurement.*;

public class PerformanceMonitorKeyResultXmlTranslator 
{

  public PerformanceMonitorKeyResultXmlTranslator() 
  {
  }
  

/**
    <measurement:PerformanceMonitorKeyResult>
	<success>true</success>	
	<exception>
		<message></message>
	</exception>	
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
    </measurement:PerformanceMonitorKeyResult>
 */
  public static String toXml(PerformanceMonitorKeyResult object, String elementName)
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
	    sb.append("<success>"+object.isSuccess()+"</success>"); 
	    String exMessage = object.getException().getMessage();
	    sb.append("<exception><message>"); 
	    sb.append(exMessage);
	    sb.append("</message></exception>"); 	    
	    PerformanceMonitorKey performKey = object.getPerformanceMonitorKey();
	    String performKeyXml = null;
	    performKeyXml = PerformanceMonitorKeyXmlTranslator.toXml(performKey,"performanceMonitorKey");
	    sb.append(performKeyXml);
	    sb.append("</" + elementName + ">");
	}	
    return sb.toString();	
    }


    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException 
    {
        try 
        {
            if( type.equals(PerformanceMonitorKeyResult.class.getName() )) 
	    {
		ManagedEntityKeyResultImpl a = new ManagedEntityKeyResultImpl();
		PerformanceMonitorKeyResult performKeyResult = (PerformanceMonitorKeyResult)a; 
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, performKeyResult);
                return performKeyResult;
             }

	}
        catch( org.xml.sax.SAXException ex ) 
	{
            return new IllegalArgumentException( ex.getMessage() );
        }
        catch(JDOMException jdomE) 
	{
	    return null;
	}
        catch(Exception e) 
	{
	    return null;
	}
    
        return null;
    }  

    public static void fromXmlNoRoot( org.jdom.Element element, PerformanceMonitorKeyResult object)
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
	    if(elementName.equalsIgnoreCase("success"))
	    {
		boolean isSuccess = (new Boolean(elementValue)).booleanValue();
		object.setSuccess(isSuccess);
	    }
	    else if(elementName.equalsIgnoreCase("success"))
	    {
		org.jdom.Element message =  localElement.getChild("message");
		String strMessage  = message.getTextTrim();
		Exception ex = new Exception(strMessage);
		object.setException(ex);
	    }
	    else if(elementName.equalsIgnoreCase("performanceMonitorKey"))
	    {
		 org.w3c.dom.Element w3cElement = domOutputter.output(localElement);
		 PerformanceMonitorKey performanceKey = null;
		 String className = PerformanceMonitorKey.class.getName();
		 performanceKey = (PerformanceMonitorKey)PerformanceMonitorKeyXmlTranslator.fromXml(w3cElement,className);
		 object.setManagedEntityKey(performanceKey);
	    }
	    
	}	
	
    }
  
}
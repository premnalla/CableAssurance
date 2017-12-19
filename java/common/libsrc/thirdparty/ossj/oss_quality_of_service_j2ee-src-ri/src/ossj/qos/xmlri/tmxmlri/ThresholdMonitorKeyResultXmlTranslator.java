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
import javax.oss.pm.threshold.*;

public class ThresholdMonitorKeyResultXmlTranslator
{

  public ThresholdMonitorKeyResultXmlTranslator()
  {
  }

/**
<ThresholdMonitorKeyResult>
	<success></success>
	<message></message>
	<thresholdMonitorKey>
		<applicationContext></applicationContext>
		<applicationDN></applicationDN>
		<type></type>
	 	<thresholdMonitorPrimaryKey></thresholdMonitorPrimaryKey>
	</thresholdMonitorKey>
<ThresholdMonitorKeyResult>

 */
public static String toXml(ThresholdMonitorKeyResult object, String elementName)
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
	    boolean isSuccess = object.isSuccess();
	    String strIsSuccess = (new Boolean(isSuccess)).toString();
	    String exceptionMessage = object.getException().getMessage();
 	    ThresholdMonitorKey thresholdMonitorKey =  object.getThresholdMonitorKey();
	    String monitorkeyXml = ThresholdMonitorKeyXmlTranslator.toXml(thresholdMonitorKey,"thresholdMonitorKey");
	    sb.append("<success>"+strIsSuccess+"</success>");
	    sb.append("<message>"+exceptionMessage+"</message>");
	    sb.append(monitorkeyXml);
	    sb.append("</" + elementName + ">");
	}
    return sb.toString();
    }

    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {
        try
        {
            if( type.equals(ThresholdMonitorKeyResult.class.getName()))
	    {
                ThresholdMonitorKeyResult keyResult = new ThresholdMonitorKeyResultImpl();
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, keyResult);
                return keyResult;
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

    public static void fromXmlNoRoot(org.jdom.Element element, ThresholdMonitorKeyResult object)
    throws org.xml.sax.SAXException, ParseException, JDOMException
    {
	DOMOutputter outPutter = new DOMOutputter();
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
		object.setSuccess((new Boolean(elementValue)).booleanValue());
	    }
	    else if(elementName.equalsIgnoreCase("message"))
	    {
		Exception ex = new Exception(elementValue);
		object.setException(ex);
	    }
	    else if(elementName.equalsIgnoreCase("thresholdMonitorKey"))
	    {
		org.w3c.dom.Element w3cElement = outPutter.output(localElement);
		String className = ThresholdMonitorKey.class.getName();
		ThresholdMonitorKey monitorKey = null;
		monitorKey = (ThresholdMonitorKey)ThresholdMonitorKeyXmlTranslator.fromXml(w3cElement,className);
		object.setManagedEntityKey(monitorKey);
	    }
	}//end while
    }

}
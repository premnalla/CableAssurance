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


public class ThresholdMonitorKeyXmlTranslator
{

  public ThresholdMonitorKeyXmlTranslator()
  {
  }

/**
<ThresholdMonitorKey>
    <applicationContext>
	<factoryClass>sdsds</factoryClass>
	<url>sdsdsd</url>
	<systemProperties>
	    <property>
		<name></name>
		<value></value>
	    </property>
	    <property>
		<name></name>
		<value></value>
	    </property>
	</systemProperties>
    </applicationContext>
    <applicationDN></applicationDN>
    <type></type>
    <thresholdMonitorPrimaryKey>lakdks</thresholdMonitorPrimaryKey>
<ThresholdMonitorKey>

 */
public static String toXml(ThresholdMonitorKey object, String elementName)
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

	    String appDN = object.getApplicationDN();
	    String primaryKey = object.getThresholdMonitorPrimaryKey();
	    String type =  object.getType();
	    String factoryClass = object.getApplicationContext().getFactoryClass();
	    HashMap sysProps = (HashMap)object.getApplicationContext().getSystemProperties();
	    sysProps.size();
	    Set keySet = sysProps.keySet();
	    Iterator keys = keySet.iterator();
	    StringBuffer sbKeys = new StringBuffer();
	    sbKeys.append("<systemProperties>");
	    String strKey = null;
	    while(keys.hasNext())
	    {
		strKey = (String)keys.next();
		sbKeys.append("<property>");
		sbKeys.append("<name>");
    		sbKeys.append(strKey);
		sbKeys.append("</name>");
	        sbKeys.append("<value>");
		sbKeys.append(sysProps.get(strKey));
		sbKeys.append("</value>");
		sbKeys.append("</property>");
	    }
    	    sbKeys.append("</systemProperties>");
	    String url = object.getApplicationContext().getURL();
	    sb.append("<applicationContext>");
	    sb.append("<factoryClass>"+factoryClass+"</factoryClass>");
	    sb.append("<url>"+url+"</url>");
	    sb.append(sbKeys.toString());
	    sb.append("</applicationContext>");
	    sb.append("<applicationDN>"+appDN+"</applicationDN>");
	    sb.append("<type>"+type+"</type>");
	    sb.append("<thresholdMonitorPrimaryKey>"+primaryKey+"</thresholdMonitorPrimaryKey>");
	    sb.append("</" + elementName + ">");
	}
    return sb.toString();
    }


    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {
        return null;
    }

    public static void fromXmlNoRoot(org.jdom.Element element, ThresholdMonitorKey object)
    throws org.xml.sax.SAXException, ParseException
    {

    }

}
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

public class QueryByMonitorValueXmlTranslator
{

  public QueryByMonitorValueXmlTranslator()
  {
  }

/**
<QueryByMonitorValue>
    <state></state>
    <name></name>
    <granularityPeriod></granularityPeriod>
    <valueType></valueType>
</QueryByMonitorValue>
 */
public static String toXml(QueryByMonitorValue object, String elementName)
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
	    int state = object.getState();
	    String name = object.getName();
	    int granPeriod = object.getGranularityPeriod();
	    String valueType = object.getValueType();
	    sb.append("<state>"+state+"</state>");
	    sb.append("<name>"+name+"</name>");
	    sb.append("<granularityPeriod>"+granPeriod+"</granularityPeriod>");
	    sb.append("<valueType>"+valueType+"</valueType>");
	    sb.append("</" + elementName + ">");
	}
    return sb.toString();
    }

    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {
        try
        {
            if(type.equals(QueryByMonitorValue.class.getName() ))
	    {
                QueryByMonitorValue queryByValue = new QueryByMonitorValueImpl();
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, queryByValue);
                return queryByValue;
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

    public static void fromXmlNoRoot(org.jdom.Element element, QueryByMonitorValue object)
    throws org.xml.sax.SAXException, ParseException
    {
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
	    if(elementName.equalsIgnoreCase("state"))
	    {
		int state =  Integer.parseInt(elementValue);
		object.setState(state);
	    }
	    else if(elementName.equalsIgnoreCase("name"))
	    {
		object.setName(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("granularityPeriod"))
	    {
		int granPeriod = Integer.parseInt(elementValue);
		object.setGranularityPeriod(granPeriod);
	    }
	    else if(elementName.equalsIgnoreCase("valueType"))
	    {
		object.setValueType(elementValue);
	    }
	}
    }


}
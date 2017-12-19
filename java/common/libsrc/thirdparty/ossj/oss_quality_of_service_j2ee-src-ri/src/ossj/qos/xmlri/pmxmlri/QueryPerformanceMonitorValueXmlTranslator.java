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
import org.jdom.*;
import org.jdom.input.*;
import ossj.qos.pm.measurement.QueryPerformanceMonitorValueImpl; 

public class QueryPerformanceMonitorValueXmlTranslator 
{

  public QueryPerformanceMonitorValueXmlTranslator() 
  {
  }
  
/**
<measurement:QueryPerformanceMonitorValue>
	<name></name>
	<granularityPeriod>int</granularityPeriod>
	<measurement:basePerformanceMonitorState>ACTIVE_ON_DUTY</measurement:basePerformanceMonitorState>
	<valueType></valueType>	
</measurement:QueryPerformanceMonitorValue>
 */

 public static String toXml(QueryPerformanceMonitorValue queryPerform, String elementName )
    throws org.xml.sax.SAXException
    {
        StringBuffer sb = new StringBuffer();
        if (queryPerform == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {  
	    String name = queryPerform.getName(); 
	    String granularityPeriod = String.valueOf(queryPerform.getGranularityPeriod());
	    String performState = String.valueOf( queryPerform.getState());
	    String valueType = queryPerform.getValueType();
	    sb.append("<" + elementName + ">");

	    if(name == null)
	    {
		sb.append("<name xsi:nil=\"true\">" + name + "</name>");
	    }
	    else
	    {
		sb.append("<name>" + name + "</name>");
	    }
	    sb.append("<granularityPeriod>" + granularityPeriod + "</granularityPeriod>");
	    sb.append("<measurement:basePerformanceMonitorState>" + performState + "</measurement:basePerformanceMonitorState>");
	    if(valueType == null)
	    {
		sb.append("<valueType xsi:nil=\"true\">" + valueType + "</valueType>");
	    }
	    else
	    {
	        sb.append("<valueType>" + valueType + "</valueType>");
	    }
	    sb.append("</" + elementName + ">");
	}
    return sb.toString();	
    }


    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException 
    {
        try 
        {
            if(type.equals(QueryPerformanceMonitorValue.class.getName() )) 
	    {
                QueryPerformanceMonitorValue queryPerform = new QueryPerformanceMonitorValueImpl();
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, queryPerform);
                return queryPerform;
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

public static void fromXmlNoRoot( org.jdom.Element element,QueryPerformanceMonitorValue queryPerform)
    throws org.xml.sax.SAXException, ParseException
    {
	List list = element.getChildren();
	Iterator it = list.iterator();
	String elementName = null;
	String elementValue = null;
	org.jdom.Element localElement = null;
	while(it.hasNext())
	{
	    elementName = localElement.getName();
	    elementValue = localElement.getTextTrim();
	    if(elementName.equalsIgnoreCase("name"))
	    {
		queryPerform.setName(elementName);
	    }
	    else if(elementName.equalsIgnoreCase("granularityPeriod"))
	    {
		int granularityPeriod =  Integer.parseInt(elementValue);
		queryPerform.setGranularityPeriod(granularityPeriod);
	    }
	    else if(elementName.equalsIgnoreCase("measurement:basePerformanceMonitorState"))
	    {
		int monitorState = Integer.parseInt(elementValue);
		queryPerform.setState(monitorState);
	    }
	    else if(elementName.equalsIgnoreCase("valueType"))
	    {
		queryPerform.setValueType(elementValue);
	    }
	}
    }   
  
}//end class
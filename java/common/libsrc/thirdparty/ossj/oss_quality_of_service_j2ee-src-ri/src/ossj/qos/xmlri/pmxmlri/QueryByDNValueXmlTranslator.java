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
import ossj.qos.pm.measurement.QueryByDNValueImpl;
 

public class QueryByDNValueXmlTranslator 
{

  public QueryByDNValueXmlTranslator() 
  {
  }

/**
    <measurement:QueryByDNValue>
	<name></name>
	<granularityPeriod>int</granularityPeriod>
	<measurement:basePerformanceMonitorState>ACTIVE_ON_DUTY</measurement:basePerformanceMonitorState>
	<valueType></valueType>
	    <distinguishedNames>
		<Item></Item>		
	    </distinguishedNames>
    </measurement:QueryByDNValue>
 */
 public static String toXml(QueryByDNValue queryByDN, String elementName )
    throws org.xml.sax.SAXException
    {
        StringBuffer sb = new StringBuffer();
        if (queryByDN == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
	    sb.append("<" + elementName + ">");
	    String name = queryByDN.getName();
	    String granPeriod = String.valueOf(queryByDN.getGranularityPeriod());
	    String state =  String.valueOf(queryByDN.getState());
	    String type = queryByDN.getValueType();
	    String[] DNs = queryByDN.getDistinguishedNames();
	    
	    if(name == null)
	    {
		sb.append("<name xsi:nil=\"true\">" + name + "</name>");
	    }
	    else
	    {
		sb.append("<name>" + name + "</name>");
	    }
	    
	    if(type == null)
	    {
		sb.append("<valueType xsi:nil=\"true\">" + type + "</valueType>");
	    }
	    else
	    {
		sb.append("<valueType>" + type + "</valueType>");
	    }	    
	    if(DNs.length <= 0)
	    {
		sb.append("<distinguishedNames xsi:nil=\"true\">" + type + "</distinguishedNames>");
	    }
	    else
	    {
		sb.append("<distinguishedNames>");
		for(int i=0;i<DNs.length;i++)
		{
		    sb.append("<Item>");
		    sb.append(DNs[i]);
		    sb.append("</Item>");
		}
		sb.append("</distinguishedNames>");
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
            if( type.equals(QueryByDNValue.class.getName() )) 
	    {
                QueryByDNValue queryByDN = new QueryByDNValueImpl();
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, queryByDN);
                return queryByDN;
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

    public static void fromXmlNoRoot( org.jdom.Element element,QueryByDNValue queryByDN)
    throws org.xml.sax.SAXException, ParseException
    {
	List list = element.getChildren();
	Iterator it = list.iterator();
	String elementName = null;
	String elementValue = null;
	org.jdom.Element localElement = null;
	ArrayList arrayList = new ArrayList();
	while(it.hasNext())
	{
	    elementName = localElement.getName();
	    elementValue = localElement.getTextTrim();
	    if(elementName.equalsIgnoreCase("name"))
	    {
	        queryByDN.setName(elementName);
	    }
	    else if(elementName.equalsIgnoreCase("granularityPeriod"))
	    {
		int granPeriod = Integer.parseInt(elementValue);
		queryByDN.setGranularityPeriod(granPeriod);		
	    }
	    else if(elementName.equalsIgnoreCase("measurement:basePerformanceMonitorState"))
	    {
		 int state = Integer.parseInt(elementValue);
		 queryByDN.setState(state);
	    }
	    else if(elementName.equalsIgnoreCase("valueType"))
	    {
		queryByDN.setValueType(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("distinguishedNames"))
	    {
		List listOfDNs = localElement.getChildren();
		Iterator ItTemp = listOfDNs.iterator();
		String name = null;
		String value = null;
		org.jdom.Element item = null;
		while(ItTemp.hasNext())
		{
		    item = (org.jdom.Element)ItTemp.next();
		    value = item.getTextTrim();
		    arrayList.add(value);
		}
	    String[] DNItems = (String[])arrayList.toArray();
	    queryByDN.setDistinguishedNames(DNItems);
	    }
	    
	}//end while
    }
  
  
}//end class
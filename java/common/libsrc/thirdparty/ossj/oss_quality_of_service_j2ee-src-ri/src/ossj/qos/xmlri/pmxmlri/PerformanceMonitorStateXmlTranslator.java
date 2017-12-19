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
import javax.oss.pm.measurement.*;
import javax.oss.pm.util.*;
import java.text.*;
import java.util.*;
import org.jdom.*;
import org.jdom.output.*;
import org.jdom.input.*;
import ossj.qos.pm.measurement.PerformanceMonitorByObjectsValueImpl;

public class PerformanceMonitorStateXmlTranslator
{

//CONSTANT
  public PerformanceMonitorStateXmlTranslator()
  {
  }

  public static String toXml(PerformanceMonitorState object, String elementName)
throws org.xml.sax.SAXException
    {
	return "";
    }


    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {
/*
	try
	{
//VP
//System.out.println("type = " + type);
//System.out.println("class name is = " + PerformanceMonitorByObjectsValue.class.getName());
            if( type.equalsIgnoreCase(PerformanceMonitorByObjectsValue.class.getName() ))
	    {
		PerformanceMonitorByObjectsValue performByObjects = new PerformanceMonitorByObjectsValueImpl();
		Integer test = null;
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
		org.jdom.Element jdomElement = builder.build(element);
		fromXmlNoRoot(jdomElement, performByObjects);
		return test;
	    }
	}
	catch( org.xml.sax.SAXException ex )
	{
	    ex.printStackTrace();
            return new IllegalArgumentException( ex.getMessage() );
	}
	catch(ParseException pEx)
	{
	    pEx.printStackTrace();
            return null;
	}
	catch(JDOMException jdomExc)
	{
	    jdomExc.printStackTrace();
            return null;
	}

*/
    return null;

    }

    public static void fromXmlNoRoot( org.jdom.Element element, PerformanceMonitorState object)
    throws org.xml.sax.SAXException, ParseException
    {

    }

}

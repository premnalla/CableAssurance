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

public class PerformanceMonitorValueIteratorXmlTranslator {

  public PerformanceMonitorValueIteratorXmlTranslator() {
  }
public static String toXml(PerformanceMonitorValueIterator object, String elementName 
)
throws org.xml.sax.SAXException
    {
	return "";
    }


    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException 
    {
        return null;
    }  

    public static void fromXmlNoRoot( org.jdom.Element element, PerformanceMonitorValueIterator object)
    throws org.xml.sax.SAXException, ParseException
    {
	
    }
  
}
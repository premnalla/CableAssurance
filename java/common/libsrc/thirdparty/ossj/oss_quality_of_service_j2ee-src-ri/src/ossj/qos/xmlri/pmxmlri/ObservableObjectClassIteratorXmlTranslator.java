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
import javax.oss.pm.util.*;
import java.text.*;

public class ObservableObjectClassIteratorXmlTranslator {

  public ObservableObjectClassIteratorXmlTranslator() {
  }
public static String toXml(ObservableObjectClassIterator object, String elementName )
throws org.xml.sax.SAXException
    {
	return "";
    }


    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException 
    {
        return null;
    }  

    public static void fromXmlNoRoot( org.jdom.Element element,ReportFormat reportFormat)
    throws org.xml.sax.SAXException, ParseException
    {
	
    }
  
}
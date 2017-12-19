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


public class ThresholdMonitorStateXmlTranslator
{

  public ThresholdMonitorStateXmlTranslator()
  {
  }

public static String toXml(ThresholdMonitorState object, String elementName)
throws org.xml.sax.SAXException
    {
	return "";
    }


    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {
        return null;
    }

    public static void fromXmlNoRoot(org.jdom.Element element, ThresholdMonitorState object)
    throws org.xml.sax.SAXException, ParseException
    {

    }


}
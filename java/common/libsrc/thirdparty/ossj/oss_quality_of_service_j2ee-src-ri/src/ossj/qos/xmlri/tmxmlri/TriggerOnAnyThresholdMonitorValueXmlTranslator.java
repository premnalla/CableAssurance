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


public class TriggerOnAnyThresholdMonitorValueXmlTranslator
{

  public TriggerOnAnyThresholdMonitorValueXmlTranslator()
  {
  }

  public static String toXml(TriggerOnAnyThresholdMonitorValue object, String elementName)
throws org.xml.sax.SAXException
    {
	return "";
    }


    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {
        return null;
    }

    public static void fromXmlNoRoot(org.jdom.Element element, TriggerOnAnyThresholdMonitorValue object)
    throws org.xml.sax.SAXException, ParseException
    {

    }
}
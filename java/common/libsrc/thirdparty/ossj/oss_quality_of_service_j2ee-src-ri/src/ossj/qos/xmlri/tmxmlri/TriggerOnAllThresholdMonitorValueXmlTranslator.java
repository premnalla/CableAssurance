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
//import ossj.qos.xmlri.pmxmlri.*;
import javax.oss.pm.util.*;

public class TriggerOnAllThresholdMonitorValueXmlTranslator
{

  public TriggerOnAllThresholdMonitorValueXmlTranslator()
  {
  }

  /**
<TriggerOnAllThresholdMonitorValue>
	<lastUpdateVersionNumber></lastUpdateVersionNumber>
	<granularityPeriod></granularityPeriod>
	<schedule></schedule>
	<thresholdMonitorKey></thresholdMonitorKey>
	<name></name>
	<state></state>

	<observableObjects>
		<item>sdsd</item>
		<item>sdsd</item>
	</observableObjects>
	<thresholdDefinitions>
		<item>ThresholdDefinition XMl</item>
	</thresholdDefinitions>
	<alarmConfig>
		<...AlarmConfig XML....>
	</alarmConfig>
</TriggerOnAllThresholdMonitorValue>

   */
  public static String toXml(TriggerOnAllThresholdMonitorValue object, String elementName)
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
	    String verNumber = String.valueOf(object.getLastUpdateVersionNumber());
	    String granPeriod = String.valueOf(object.getGranularityPeriod());
	    Schedule schedule = object.getSchedule();
	    String scheduleXml = ScheduleXmlTranslator.toXml(schedule,"schedule");
	    ThresholdMonitorKey key = object.getThresholdMonitorKey();
	    String keyXml = ThresholdMonitorKeyXmlTranslator.toXml(key,"thresholdMonitorKey");
	    String name = object.getName();
	    String state = String.valueOf(object.getState());
	    String[] objs = object.getObservableObjects();
	    ThresholdDefinition[] thresholdDefs = object.getThresholdDefinitions();
	    AlarmConfig alarmConfig = object.getAlarmConfig();

	    sb.append("<lastUpdateVersionNumber>"+verNumber+"</lastUpdateVersionNumber>");
	    sb.append("<granularityPeriod>"+granPeriod+"</granularityPeriod>");
	    sb.append(scheduleXml);
	    sb.append(keyXml);
	    sb.append("<name>"+name+"</name>");
	    sb.append("<state>"+state+"</state>");
	    sb.append("<observableObjects>");
	    for(int i=0;i<objs.length;i++)
	    {
		sb.append(objs[i]);
	    }
	    sb.append("</observableObjects>");
	    sb.append("<thresholdDefinitions>");
	    for(int j=0;j<thresholdDefs.length;j++)
	    {
		sb.append(ThresholdDefinitionXmlTranslator.toXml(thresholdDefs[j],"item"));
	    }
	    sb.append("</thresholdDefinitions>");
	    String alarmConfigXml = AlarmConfigXmlTranslator.toXml(alarmConfig,"alarmConfig");
	    sb.append(alarmConfigXml);
	    sb.append("</" + elementName + ">");
	}
    return sb.toString();
    }

    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {
        return null;
    }

    public static void fromXmlNoRoot(org.jdom.Element element, TriggerOnAllThresholdMonitorValue object)
    throws org.xml.sax.SAXException, ParseException
    {

    }

}
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
public class AlarmConfigXmlTranslator
{

  public AlarmConfigXmlTranslator()
  {
  }

  /**
	<AlarmConfig>
			<fm:baseAlarmType>
			<fm:basePerceivedAlarmSeverity>
			<specificProblem>
			<fm:baseProbableAlarmCause>
	</AlarmConfig>
   */
 public static String toXml(AlarmConfig object,String elementName )
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
	    String alarmType = object.getAlarmType();
	    short perceivedSeverity = object.getPerceivedSeverity();
	    short probableCause = object.getProbableCause();
	    String specificProblem = object.getSpecificProblem();
	    sb.append("<fm:baseAlarmType>"+alarmType+"</fm:baseAlarmType>");
	    sb.append("<fm:basePerceivedAlarmSeverity>"+perceivedSeverity+"</fm:basePerceivedAlarmSeverity>");
	    sb.append("<specificProblem>"+specificProblem+"</specificProblem>");
	    sb.append("<fm:baseProbableAlarmCause>"+probableCause+"</fm:baseProbableAlarmCause>");
	    sb.append("</" + elementName + ">");
	}
return sb.toString();
}


    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {

        try
        {
            if(type.equals(AlarmConfig.class.getName() ))
	    {
                AlarmConfig alarmConfig = new AlarmConfigImpl();
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, alarmConfig);
                return alarmConfig;
             }

	}
        catch( org.xml.sax.SAXException ex )
	{
            return new IllegalArgumentException( ex.getMessage() );
        }
        catch(Exception e)
	{
            return null;
        }
        return null;
    }

public static void fromXmlNoRoot( org.jdom.Element element, AlarmConfig alarmConfig)
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
	    if(elementName.equalsIgnoreCase("fm:baseAlarmType"))
	    {
		alarmConfig.setAlarmType(elementValue);
	    }
	    else if (elementName.equalsIgnoreCase("fm:basePerceivedAlarmSeverity"))
	    {
		short percvdValue = Short.parseShort(elementValue);
		alarmConfig.setPerceivedSeverity(percvdValue);
	    }
	    else if (elementName.equalsIgnoreCase("specificProblem"))
	    {
		alarmConfig.setSpecificProblem(elementValue);
	    }
	    else if (elementName.equalsIgnoreCase("fm:baseProbableAlarmCause"))
	    {
		short specProb = Short.parseShort(elementValue);
		alarmConfig.setProbableCause(specProb);
	    }
	}//end while
    }

}//end class
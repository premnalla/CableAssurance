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
//import ossj.qos.pm.util.ScheduleImpl;
import ossj.qos.util.*;
import javax.oss.pm.util.*;

public class ScheduleXmlTranslator
{

  public ScheduleXmlTranslator()
  {
  }


    public static String toXml(Schedule schedule, String elementName)
    {
        StringBuffer sb = new StringBuffer();
	boolean flag = false;
        if (schedule == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
	    String startTime = schedule.getStartTime().getTime().toString();
	    String stopTime = schedule.getStopTime().getTime().toString();
	    WeeklySchedule weeklySchedule = schedule.getWeeklySchedule();
	    DailySchedule dailySchedule = schedule.getDailySchedule();
	    String weeklyScheduleClassName  = WeeklySchedule.class.getName();
	    String weeklySchXml = WeeklyScheduleXmlTranslator.toXml(weeklySchedule,weeklyScheduleClassName);

	    String dailyScheduleClassName  = DailySchedule.class.getName();
	    String dailySchXml = DailyScheduleXmlTranslator.toXml(dailySchedule,dailyScheduleClassName);

	    sb.append(startTime);
	    sb.append(stopTime);
	    sb.append(weeklySchXml);
	    sb.append(dailySchXml);
	}
    return sb.toString();
    }


public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {
        try
        {
            if( type.equals(Schedule.class.getName() ))
	    {
                Schedule schedule = new ScheduleImpl();
		org.jdom.input.DOMBuilder builder= new DOMBuilder();
                org.jdom.Element tempElement = builder.build(element);
                fromXmlNoRoot(tempElement, schedule);
                return schedule;
            }

        }
        catch( org.xml.sax.SAXException ex )
	{
            return new IllegalArgumentException( ex.getMessage() );
        }
	catch(ParseException pEx)
	{
	    return null;
	}
        return null;
    }


    public static void fromXmlNoRoot( org.jdom.Element element, Schedule schedule)
    throws org.xml.sax.SAXException, ParseException
    {

	  List list = element.getChildren();
	  Iterator it = list.iterator();
	  String elementName = null;
	  String elementValue = null;
	  while(it.hasNext())
	  {
	      element = (org.jdom.Element)it.next();
	      elementName = element.getName();
	      elementValue = element.getTextTrim();

	      if(elementName.equalsIgnoreCase("performanceMonitorReport"))
	      {
		    //TODO: don't have any set method for this attribute on ReportData
	      }
	      else if(elementName.equalsIgnoreCase("reportFormat"))
	      {

	      }
	 }//end while
    }

}//end class


package ossj.qos.xmlri.pmxmlri;

/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc., Ericsson AB.
 * @author Vijay Sharma
 * @author Hooman Tahamtani
 * @version 1.0
 *
 * Fixed this class, it was not implemented correctly.
 * 2002-06-24, Hooman Tahamtani, Ericsson AB., Gothenburg, Sweden.
 */

import java.util.*;
import java.text.*;
import javax.oss.pm.util.*;
import org.jdom.*;
import org.jdom.output.*;
import org.jdom.input.*;
import javax.oss.pm.util.DailySchedule;
import javax.oss.pm.util.WeeklySchedule;
import ossj.qos.util.*;




public class ScheduleXmlTranslator
{

  public ScheduleXmlTranslator()
  {
  }


    public static String toXml(Schedule schedule, String elementName)
    {
        StringBuffer sb = new StringBuffer();
        String startTime = null;
        String stopTime = null;
        XmlDateHelper dhelper = new XmlDateHelper();

	boolean flag = false;
        if (schedule == null)
        {
            sb.append("<measurement:schedule xsi:nil=\"true\"></measurement:schedule>");
        }
        else
        {
	    sb.append("<measurement:schedule>");
            if(schedule.getStartTime() != null){
              StringBuffer dsb = new StringBuffer();
              dhelper.Date2Xml(dsb, schedule.getStartTime().getTime());
              sb.append("<pmUtil:startTime>" + dsb.toString() + "</pmUtil:startTime>");
            }
            else{
              sb.append("<pmUtil:startTime xsi:nil=\"true\"></pmUtil:startTime>");
            }

	    if(schedule.getStopTime() != null){
              StringBuffer dsb = new StringBuffer();
              dhelper.Date2Xml(dsb, schedule.getStopTime().getTime());
              sb.append("<pmUtil:stopTime>" + dsb.toString() + "</pmUtil:stopTime>");
            }
            else{
              sb.append("<pmUtil:stopTime xsi:nil=\"true\"></pmUtil:stopTime>");
            }
	    WeeklySchedule weeklySchedule = schedule.getWeeklySchedule();
	    DailySchedule dailySchedule = schedule.getDailySchedule();
	    String weeklyScheduleClassName  = WeeklySchedule.class.getName();
	    String weeklySchXml = WeeklyScheduleXmlTranslator.toXml(weeklySchedule,weeklyScheduleClassName);

	    String dailyScheduleClassName  = DailySchedule.class.getName();
	    String dailySchXml = DailyScheduleXmlTranslator.toXml(dailySchedule,dailyScheduleClassName);

	    //sb.append(startTime);
	    //sb.append(stopTime);
	    sb.append(weeklySchXml);
	    sb.append(dailySchXml);
            sb.append("</measurement:schedule>");
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

   //re-wrote this method, 2002-06-24
    public static void fromXmlNoRoot( org.jdom.Element element, Schedule schedule)
    throws org.xml.sax.SAXException, ParseException
    {
         List list = element.getChildren();
         Iterator it = list.iterator();
         while(it.hasNext()){
          org.jdom.Element elm = (org.jdom.Element)it.next();
          String elmName = elm.getName();
          if(elmName.equalsIgnoreCase("startTime")){
            Calendar cal = Calendar.getInstance();
            Date date = XmlDateHelper.DateFromXml(elm, elm.getTextTrim());
            cal.setTime(date);
            schedule.setStartTime(cal);
          }
          else if (elmName.equalsIgnoreCase("stopTime")){
            Calendar cal = Calendar.getInstance();
            Date date = XmlDateHelper.DateFromXml(elm, elm.getTextTrim());
            cal.setTime(date);
            schedule.setStopTime(cal);
          }
          else if (elmName.equalsIgnoreCase("weeklySchedule")){
            try{
              DOMOutputter domOutputter = new DOMOutputter();
              org.w3c.dom.Element el = domOutputter.output(elm);
              WeeklySchedule ws = (WeeklySchedule) WeeklyScheduleXmlTranslator.fromXml(el, WeeklySchedule.class.getName());
              schedule.setWeeklySchedule(ws);
            }
            catch (JDOMException jde){
              jde.printStackTrace();
              WeeklySchedule ws = null;
              schedule.setWeeklySchedule(ws);
            }
          }
          else if (elmName.equalsIgnoreCase("DailySchedule")){
            try{
              DOMOutputter domOutputter = new DOMOutputter();
              org.w3c.dom.Element el = domOutputter.output(elm);
              DailySchedule ds = (DailySchedule) DailyScheduleXmlTranslator.fromXml(el, DailySchedule.class.getName());
              schedule.setDailySchedule(ds);
            }
            catch (JDOMException jde){
              jde.printStackTrace();
              DailySchedule ds = null;
              schedule.setDailySchedule(ds);
            }
          }
         }//while(it.hasNext())
    }

}//end class


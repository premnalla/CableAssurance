package ossj.qos.util;

/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc., Ericsson AB.
 * @author Vijay Sharma
 * @author Hooman Tahamtani
 * @version 1.0
 *
 * 2002-06-24, Fixed this class.
 * Hooman Tahamtani, Ericsson AB., Gothenburg, Sweden.
 */

import java.util.*;
import java.text.*;
import javax.oss.pm.util.WeeklySchedule;
import org.jdom.*;
import org.jdom.input.*;
//import ossj.qos.pm.util.WeeklyScheduleImpl;
import ossj.qos.util.WeeklyScheduleImpl;


public class WeeklyScheduleXmlTranslator
{

  public WeeklyScheduleXmlTranslator()
  {
  }

  /**
	<weeklySchedule>
	    <timeZone></timeZone>
	    <monday>boolean</monday>
	    <tuesday>boolean</tuesday>
	    <wednesday>boolean</wednesday>
	    <thursday>boolean</thursday>
	    <friday>boolean</friday>
	    <saturday>boolean</saturday>
	    <sunday>boolean</sunday>
	</weeklySchedule>
   */

    public static String toXml(WeeklySchedule weeklySchedule, String elementName)
    {
        StringBuffer sb = new StringBuffer();
	boolean flag = false;
        if (weeklySchedule == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
	    String timeZone = weeklySchedule.getTimeZone().toString();
	    sb.append("<timeZone>"+timeZone+"</timeZone>");

	    flag = weeklySchedule.isMondayActive();
	    String isMondayActive = (new Boolean(flag)).toString();
	    sb.append("<monday>"+isMondayActive+"</monday>");

	    flag = weeklySchedule.isTuesdayActive();
	    String isTuesActive = (new Boolean(flag)).toString();
	    sb.append("<tuesday>"+isTuesActive+"</tuesday>");

	    flag = weeklySchedule.isWednesdayActive();
    	    String isWedActive = (new Boolean(flag)).toString();
	    sb.append("<wednesday>"+isWedActive+"</wednesday>");

	    flag = weeklySchedule.isThursdayActive();
	    String isThursActive = (new Boolean(flag)).toString();
	    sb.append("<thursday>"+isThursActive+"</thursday>");

	    flag = weeklySchedule.isFridayActive();
	    String isFriActive = (new Boolean(flag)).toString();
	    sb.append("<friday>"+isFriActive+"</friday>");

	    flag = weeklySchedule.isSaturdayActive();
	    String isSatActive = (new Boolean(flag)).toString();
	    sb.append("<saturday>"+isSatActive+"</saturday>");

	    flag = weeklySchedule.isSundayActive();
	    String isSunActive = (new Boolean(flag)).toString();
	    sb.append("<sunday>"+isSunActive+"</sunday>");
	}
    return sb.toString();
    }



 public static Object fromXml( org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {
        try
	{
            if( type.equals(WeeklySchedule.class.getName() ))
	    {
                WeeklySchedule weeklySchedule = new WeeklyScheduleImpl();
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, weeklySchedule);
                return weeklySchedule;
             }
	}
	catch( org.xml.sax.SAXException ex)
	{
            return new IllegalArgumentException( ex.getMessage() );
        }
	catch( ParseException pExc)
	{
            return null;
        }

    return null;
    }


public static void fromXmlNoRoot( org.jdom.Element element, WeeklySchedule weeklySchedule)
    throws org.xml.sax.SAXException, ParseException
    {
	  List list = element.getChildren();
	  Iterator it = list.iterator();
	  org.jdom.Element tempElement = null;
	  String elementName = null;
	  String elementValue = null;
	  while(it.hasNext())
	  {
	        tempElement = (org.jdom.Element)it.next();
		elementName = element.getName();
	        elementValue = element.getTextTrim();
		if(elementName.equalsIgnoreCase("timeZone"))
		{
		    TimeZone tz = TimeZone.getTimeZone(elementValue);
                    weeklySchedule.setTimeZone(tz);
		}
		else if(elementName.equalsIgnoreCase("monday"))
		{
		    Boolean bObject = new Boolean(elementValue);
		    weeklySchedule.setMondayActive(bObject.booleanValue());
		}
		else if(elementName.equalsIgnoreCase("tuesday"))
		{
		    Boolean bObject = new Boolean(elementValue);
		    weeklySchedule.setTuesdayActive(bObject.booleanValue());
		}

		else if(elementName.equalsIgnoreCase("wednesday"))
		{
		    Boolean bObject = new Boolean(elementValue);
		    weeklySchedule.setWednesdayActive(bObject.booleanValue());
		}
		else if(elementName.equalsIgnoreCase("thursday"))
		{
		    Boolean bObject = new Boolean(elementValue);
		    weeklySchedule.setThursdayActive(bObject.booleanValue());
		}
		else if(elementName.equalsIgnoreCase("friday"))
		{
		    Boolean bObject = new Boolean(elementValue);
		    weeklySchedule.setFridayActive(bObject.booleanValue());
		}
		else if(elementName.equalsIgnoreCase("saturday"))
		{
		    Boolean bObject = new Boolean(elementValue);
		    weeklySchedule.setSaturdayActive(bObject.booleanValue());
		}
		else if(elementName.equalsIgnoreCase("sunday"))
		{
		    Boolean bObject = new Boolean(elementValue);
		    weeklySchedule.setSundayActive(bObject.booleanValue());
		}
	  }//end while
    }


}

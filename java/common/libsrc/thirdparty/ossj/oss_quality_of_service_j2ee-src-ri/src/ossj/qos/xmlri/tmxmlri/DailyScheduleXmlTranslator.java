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
import javax.oss.pm.util.DailySchedule;
import org.jdom.*;
import org.jdom.input.*;
//import ossj.qos.pm.util.DailyScheduleImpl;

import ossj.qos.util.DailyScheduleImpl;

public class DailyScheduleXmlTranslator
{

  public DailyScheduleXmlTranslator()
  {
  }

/**
    <dailySchedule>
	<startTimes>dateTime</startTimes>
	<stopTimes>dateTime</stopTimes>
    </dailySchedule>
*/
   public static Object fromXml( org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {
        try
	{
            if( type.equals(DailySchedule.class.getName() ))
	    {
                DailySchedule dailySchedule = new DailyScheduleImpl();
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, dailySchedule);
                return dailySchedule;
             }
	}
	catch( org.xml.sax.SAXException ex)
	{
            return new IllegalArgumentException( ex.getMessage() );
        }
	catch(ParseException pExc)
	{
	    return null;
	}
    return null;
    }

 public static void fromXmlNoRoot( org.jdom.Element element,DailySchedule dailySchedule)
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
		if(elementName.equalsIgnoreCase("startTimes"))
		{
		    Iterator items = tempElement.getChildren().iterator();
		    Calendar[] startTimes = populateArray(items);
		    dailySchedule.setStartTimes(startTimes);
		}
		else if(elementName.equalsIgnoreCase("stopTimes"))
		{
		    Iterator items = tempElement.getChildren().iterator();
		    Calendar[] stopTimes = populateArray(items);
		    dailySchedule.setStopTimes(stopTimes);
		}

	  }
    }

/**
    <dailySchedule>
	<startTimes>
	    <item>dateTime</item>
	    <item>dateTime</item>
	</startTimes>
	<stopTimes>
	    <item>dateTime</item>
	    <item>dateTime</item>
	</stopTimes>
    </dailySchedule>
*/

 public static String toXml(DailySchedule dailySchedule, String elementName)
 {
        StringBuffer sb = new StringBuffer();

        if (dailySchedule == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
            Calendar[] startTimes = dailySchedule.getStartTimes();
	    Calendar[] stopTimes = dailySchedule.getStopTimes();
	    sb.append("<startTimes>");
	    for(int i=0;i<startTimes.length;i++)
	    {
		sb.append("<item>"+startTimes[i].getTime().toString()+"</item>");
	    }
	    sb.append("</startTimes>");
	    sb.append("<stopTimes>");
	    for(int j=0;j<stopTimes.length;j++)
	    {
		sb.append("<item>"+stopTimes[j].getTime().toString()+"</item>");
	    }
	    sb.append("</stopTimes>");
        }
    return sb.toString();
    }//end toXml method

private static Calendar[] populateArray(Iterator iterator) throws ParseException
{
    org.jdom.Element localElement = null;
    ArrayList arrayList = new ArrayList();
    String strDate = null;

    if(iterator != null)
    {
	while(iterator.hasNext())
	{
	    localElement = (org.jdom.Element)iterator.next();
	    strDate = localElement.getTextTrim();
	    DateFormat df = DateFormat.getDateInstance();
	    java.util.Date date =  df.parse(strDate);
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    arrayList.add(cal);
	}//while there are more elements to be put into arraylist
    return (Calendar[])arrayList.toArray();
    }
    else
    {
        return new Calendar[0];
    }
}

}//end class
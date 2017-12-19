package ossj.qos.util;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:     Ericsson AB:
 * @author  Hooman Tahamtani
 * @version 1.0
 */


import java.util.*;
import java.text.*;
import org.jdom.input.*;
import org.jdom.output.*;
import org.jdom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.*;

public class XmlDateHelper {

private static HashMap monthHash = null;

  public XmlDateHelper() {
  }

    private static void setupMonths(){
        monthHash = new HashMap(12);

        monthHash.put("Jan", "01" );
        monthHash.put("Feb", "02" );
        monthHash.put("Mar", "03" );
        monthHash.put("Apr", "04" );
        monthHash.put("May", "05" );
        monthHash.put("Jun", "06" );
        monthHash.put("Jul", "07" );
        monthHash.put("Aug", "08" );
        monthHash.put("Sep", "09" );
        monthHash.put("Oct", "10" );
        monthHash.put("Nov", "11" );
        monthHash.put("Dec", "12" );

    }
    public static java.util.Date DateFromXml(org.jdom.Element elm, String dateValue)
    {

        //1999-05-31T13:20:00-05:00.
        java.util.Date date = null;
        if(monthHash == null){
          setupMonths();
        }
        if (! (elm.hasChildren()) )      //is it nilled?
        return date;

        String childNodeValue = dateValue;

        //"2001-02-18T14:01:00Z"
        StringTokenizer st = new StringTokenizer( childNodeValue, "T" );
        String year;
        String month;
        String day;
        String hour;
        String min;
        String sec;

        String dateToken = st.nextToken();
        String timeToken = st.nextToken();

        StringTokenizer st2 = new StringTokenizer( dateToken, "-");
        year = st2.nextToken();
        month = st2.nextToken();
        day = st2.nextToken();

        StringTokenizer st3 = new StringTokenizer( timeToken, ":");

        hour = st3.nextToken();
        min = st3.nextToken();
        sec = (st3.nextToken()).substring(0,2);
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(Integer.parseInt(year) - 1900,
        Integer.parseInt(month) - 1,
        Integer.parseInt(day),
        Integer.parseInt(hour),
        Integer.parseInt(min),
        Integer.parseInt(sec));
        long utcDate = cal.getTime().getTime();
        date = new java.util.Date ( utcDate );

        //System.out.println("GMT Date : " + date.toGMTString() );

        return date;

    }

    public static void Date2Xml(StringBuffer sb, java.util.Date date )
    {
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy HH:mm:ss 'GMT'", Locale.US);

        if(monthHash == null){
          setupMonths();
        }

        if (date == null)
        {
            sb.append( "0000-00-00T00:00:00Z" );		//Zero time if date is null
        }
        else
        {
            StringTokenizer stTokenizer = new StringTokenizer(sdf.format(date));
            System.out.println("Tokenized, stTokenizer is: " + stTokenizer.toString());

            String day;
            String month;
            String year;
            String time;
            String zone;

            System.out.println("a");
            day = stTokenizer.nextToken();
            System.out.println("Day 1 is: " + day);
            System.out.println("b");
            if (Integer.parseInt(day) < 10)
            {
                day = "0" + day;
                System.out.println("Day 2 is: " + day);
            }

            month = stTokenizer.nextToken();
            System.out.println("Month is: " + month);
            year  = stTokenizer.nextToken();
            System.out.println("Year is: " + year);
            time  = stTokenizer.nextToken();
            System.out.println("Time is: " + time);
            zone  = stTokenizer.nextToken();
            System.out.println("Zone is: " + zone);




            //----------------------------------------------------------------
            // Convert from java Date format into XML timeInstant format.
            //   date format:  7 Mar 2001 10:10:10 GMT
            //    xml format:  2001-03-07T10:10:10Z
            //----------------------------------------------------------------

            System.out.println( "Date2Xml ---(1) " );
            String xmlStr = year + "-" + (String)monthHash.get(month) + "-" + day + "T" + time + "Z";
            System.out.println( "Date2Xml ---(2) " );
            sb.append(xmlStr);
            System.out.println("leaving date2xml, string bugffer is: " + sb.toString());
        }


    }



}
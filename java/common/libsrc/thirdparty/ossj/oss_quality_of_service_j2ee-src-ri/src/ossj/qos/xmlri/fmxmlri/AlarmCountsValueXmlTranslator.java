package ossj.qos.xmlri.fmxmlri;



/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc.
 * @author Vijay Sharma
 * @version 1.0
 */

import java.util.Vector;
import java.util.HashMap;
import java.util.Date;
import java.lang.StringBuffer;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.jdom.*;
import org.jdom.input.*;
import javax.oss.ManagedEntityValue;



import javax.oss.MultiValueList;

import javax.oss.fm.monitor.*;
import ossj.qos.fmri.*;

public class AlarmCountsValueXmlTranslator
{

    public AlarmCountsValueXmlTranslator()
    {

    }

    /**
     * 	<fm:AlarmCountsValue>
     *  <fm:criticalCount>1</fm:criticalCount>
     *  <fm:majorCount>1</fm:majorCount>
     *  <fm:minorCount>1</fm:minorCount>
     *  <fm:warningCount>1</fm:warningCount>
     *  <fm:indeterminateCount>1</fm:indeterminateCount>
     *  <fm:clearedCount>1</fm:clearedCount>
     *  </fm:AlarmCountsValue>
     **/
    public static String toXml(AlarmCountsValue alctval,String elementName)
    {
        StringBuffer sb= new StringBuffer();
        try{
        if (alctval == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
       // sb.append("<fm:alarmCounts>\n");


           sb.append("<fm:criticalCount>" + alctval.getCriticalCount() + "</fm:criticalCount>\n");

           sb.append("<fm:majorCount>" + alctval.getMajorCount() + "</fm:majorCount>\n");

           sb.append("<fm:minorCount>" + alctval.getMinorCount() + "</fm:minorCount>\n");

           sb.append("<fm:warningCount>" + alctval.getWarningCount() + "</fm:warningCount>\n");

            sb.append("<fm:indeterminateCount>" + alctval.getIndeterminateCount() + "</fm:indeterminateCount>\n");

            sb.append("<fm:clearedCount>" + alctval.getClearedCount() + "</fm:clearedCount>\n");

	   // sb.append("</fm:alarmCounts>");
        }

        }catch(Exception e){
        //VP: System.out.println("ERROR IN ALARMCOUNTSVALUEXMLTRANLATOR"+e.getMessage());
        }
        return  sb.toString();
    }




    public static Object fromXml( org.w3c.dom.Element element, String type  )
    throws IllegalArgumentException
    {


                AlarmCountsValue alctval = new AlarmCountsValueImpl();



        return alctval;


    }

    public static void fromXmlNoRoot( org.jdom.Element element,AlarmCountsValue alarmCountsValue)
    throws org.xml.sax.SAXException
    {
	  /*
	  List list = element.getChildren();
	  Iterator it = list.iterator();
	  org.jdom.Element element = null;
	  String elementName = null;
	  String elementValue = null;
	  while(it.hasNext())
	  {
	     element = (org.jdom.Element)it.next();
	     elementName = element.getName();
	     elementValue = element.getTextTrim();

	     if(elementName.equalsIgnoreCase(""))
	  }
	  */
     }


}//end class




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
import java.util.Iterator;
import java.lang.StringBuffer;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.jdom.input.*;
import javax.oss.ManagedEntityValue;



import javax.oss.MultiValueList;

import javax.oss.fm.monitor.*;
import ossj.qos.fmri.*;

public class AlarmKeyResultXmlTranslator
{



    public AlarmKeyResultXmlTranslator()
    {

    }


    /**
     * <fm:AlarmKeyResult>
     *      <fm:AlarmKey>
     *          <fm:alarmPrimaryKey>alrmPrimaryKey</fm:alarmPrimaryKey>
     *      </fm:AlarmKey>
     * </fm:AlarmKeyResult>
     */

    public static String toXml(AlarmKeyResult alarmKeyResult,String elementName )
    throws org.xml.sax.SAXException
    {
	StringBuffer sb = new StringBuffer();
        if (alarmKeyResult == null)
        {
            //VP: System.out.println("Here, element name is: " + elementName);
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
            //sb.append("<" + elementName + ">\n");
	    String s1 = AlarmKeyXmlTranslator.toXml(alarmKeyResult.getAlarmKey() ,"fm:AlarmKey");
    	    sb.append(s1);
        }
    return  sb.toString();
    }

    public static Object fromXml(org.w3c.dom.Element element, String type  )
    throws IllegalArgumentException
    {
        AlarmKeyResult alarmKeyResult = null;
        try
        {

		alarmKeyResult = new AlarmKeyResultImpl();
                AlarmKey alKey = (AlarmKey)AlarmKeyXmlTranslator.fromXml(element, AlarmKey.class.getName());
                alarmKeyResult.setSuccess(true);
                alarmKeyResult.setManagedEntityKey((javax.oss.ManagedEntityKey)alKey);
		//org.jdom.input.DOMBuilder builder= new DOMBuilder();
                //org.jdom.Element tempElement = builder.build(element);
		//fromXmlNoRoot(tempElement, alarmKeyResult);


        }
        catch(Exception ex ) {
            return new IllegalArgumentException( ex.getMessage() );
        }
        return alarmKeyResult;
    }
/*

<fm:Item>
  <co:success>true</co:success>
  <fm:alarmKey><co:applicationContext><co:factoryClass></co:factoryClass><co:url></co:url>
    <co:systemProperties><co:property><co:name></co:name><co:value></co:value></co:property>
    </co:systemProperties></co:applicationContext><co:applicationDN>System/DFW/ApplicationType/QoS/AlarmMonitor/Application/1-0;1-0;JSR90FMRI/Comp/JVTHome</co:applicationDN>
    <co:type>javax.oss.fm.monitor.AlarmValue</co:type>
    <fm:alarmPrimaryKey>26229bbdac101e5c000e4f9a9741d0b4</fm:alarmPrimaryKey>
  </fm:alarmKey>
</fm:Item>



*/


    public static void fromXmlNoRoot(org.jdom.Element element,AlarmKeyResult alarmKeyResult)
    throws org.xml.sax.SAXException
    {
	//AlarmKey alKey = AlarmKeyXmlTranslator.fromXml(element, AlarmKey.class.getName());

       // alarmKeyResult.setSuccess(true);
        //alarmKeyResult.setManagedEntityKey((ManagedEntityKey)alKey);
        /*java.util.List children = element.getChildren();
	Iterator it = children.iterator();
	org.jdom.Element child = null;
	String elementName = null;
	String elementValue = null;
	while(it.hasNext())
	{
	    child = (org.jdom.Element)it.next();
	    elementName = child.getName();
	    elementValue = child.getTextTrim();
	    if(elementName.equalsIgnoreCase("alarmKey"))
	    {
		AlarmKey alKey = AlarmKeyXmlTranslator.fromXml(child, AlarmKey.class.getName());
	    }
	}//end while*/
    }//end fromXmlNoRoot method

}//end class


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
import java.text.DateFormat;
import java.text.ParseException;
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

public class NotifyAckStateChangedEventXmlTranslator
{
    public NotifyAckStateChangedEventXmlTranslator()
    {

    }

    /**
     * <fm:NotifyAckStateChangedEvent>
     *      <event>
     *  	<ackUserId>fdgdf</ackUserId>
     *          <ackTime>354534</ackTime>
     *          <alarmAckState>dfgdsfg</alarmAckState>		
     *          <ackSystemId>dfgdsfgd</ackSystemId>
     *      </event>
     *  </fm:NotifyAckStateChangedEvent >
     */
    public static String toXml(NotifyAckStateChangedEvent changeEvent , String elementName )
    throws org.xml.sax.SAXException
    {
	StringBuffer sb = new StringBuffer();
        if (changeEvent == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
            String userId = changeEvent.getAckUserId();
	    String time = changeEvent.getAckTime().toString();
	    int state = changeEvent.getAlarmAckState();
	    String systemId = changeEvent.getAckSystemId();
	    
	    sb.append("<" + elementName + ">\n");
	    sb.append("<event>" + "\n");
	    if(userId == null)
		sb.append("<ackUserId xsi:nil=\"true\"></ackUserId>\n");
            else
		sb.append("<ackUserId>" + userId + "</ackUserId>\n");
	    if(time == null)
		sb.append("<ackTime xsi:nil=\"true\"></ackTime>\n");
            else
		sb.append("<ackTime>" + time + "</ackTime>\n");

	    //state id is not nillable		

	    sb.append("<alarmAckState>" + state + "</alarmAckState>\n");
		
	    if(systemId == null)
		sb.append("<ackSystemId xsi:nil=\"true\"></ackSystemId>\n");
            else
		sb.append("<ackSystemId>" + systemId + "</ackSystemId>\n");

	    sb.append("</event>");
	    sb.append("</" + elementName + ">");	
        }
    return  sb.toString();
	
    }




    public static Object fromXml(org.w3c.dom.Element element, String type  )
    throws IllegalArgumentException 
    {
        try 
	{
            if( type.equals(NotifyAckStateChangedEvent.class.getName() )) 
	    {
                NotifyAckStateChangedEvent changeEvent = new NotifyAckStateChangedEventImpl();
		org.jdom.input.DOMBuilder builder= new DOMBuilder();
                org.jdom.Element tempElement = builder.build(element);		
                fromXmlNoRoot(tempElement, changeEvent);
                return changeEvent;
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

    public static void fromXmlNoRoot(org.jdom.Element element , NotifyAckStateChangedEvent changedEvent)
    throws org.xml.sax.SAXException,NumberFormatException,ParseException
    {
	java.util.List children = element.getChildren();
	Iterator it = children.iterator();
	org.jdom.Element child = null;
	String elementName = null;
	String elementValue = null;
	while(it.hasNext())
	{
	    child = (org.jdom.Element)it.next();
	    elementName = child.getName();
	    elementValue = child.getTextTrim();
	    if(elementName.equalsIgnoreCase("ackUserId"))
	    {
		changedEvent.setAckUserId(elementValue);
	    }
	    else if (elementName.equalsIgnoreCase("ackTime"))
	    {
		DateFormat df = DateFormat.getDateInstance();
		java.util.Date date =  df.parse(elementValue);
		changedEvent.setAckTime(date);
	    }
	    
	    else if (elementName.equalsIgnoreCase("alarmAckState"))
	    {
		int state = Integer.parseInt(elementValue);
		changedEvent.setAlarmAckState(state);
	    }
	}//end while
    }

}


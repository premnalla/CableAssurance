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
import java.util.ArrayList;
import java.lang.StringBuffer;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.jdom.input.*;
import org.jdom.*;
import javax.oss.ManagedEntityValue;



import javax.oss.MultiValueList;

import javax.oss.fm.monitor.*;
import ossj.qos.fmri.*;

public class CorrelatedNotificationValueXmlTranslator
{


    public CorrelatedNotificationValueXmlTranslator()
    {

    }

    public static String toXml(CorrelatedNotificationValue corNotValue,String elementName )
    throws org.xml.sax.SAXException
    {
	StringBuffer sb = new StringBuffer();
        if (corNotValue == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
            String[] Ids = corNotValue.getNotificationIds();
	    String strIds = null;
	    StringBuffer sbIdsItems = new StringBuffer();
	    for(int i=0;i<Ids.length;i++)   
	    {
		sbIdsItems.append("<Item>");
		sbIdsItems.append(Ids[i]);
		sbIdsItems.append("</Item>");
	    }
    
	    String managedObjInstance = corNotValue.getManagedObjectInstance();

	    //start building the xml 
	    sb.append("<" + elementName + ">\n");
	    
	    if(Ids.length <= 0)
		sb.append("<notificationIds xsi:nil=\"true\"></notificationIds>\n");
            else
	    {
		sb.append("<notificationIds>");
		sb.append(sbIdsItems.toString());
		sb.append("</notificationIds>");
	    }
		
	    if(managedObjInstance == null)
		sb.append("<managedObjectInstance xsi:nil=\"true\"></managedObjectInstance>\n");
            else
		sb.append("<managedObjectInstance>" + managedObjInstance + "</managedObjectInstance>\n");
		
	    sb.append("</" + elementName + ">");	

        }
    return  sb.toString();
	
    }




    /**
	<correlatedNotifications>
		<item>
		<notificationIds>
		    <item>2343242</item>
		</notificationIds>
			<managedObjectInstance>gfdgfgd</managedObjectInstance>
		</item>
	</correlatedNotifications>     
    */
    public static Object fromXml(org.w3c.dom.Element element , String type )
    throws IllegalArgumentException 
    {
        try 
        {
            if( type.equals(CorrelatedNotificationValue.class.getName() )) 
	    {
                CorrelatedNotificationValue corNotificationValue = new CorrelatedNotificationValueImpl();
		org.jdom.input.DOMBuilder builder= new DOMBuilder();
                org.jdom.Element tempElement = builder.build(element);		
                fromXmlNoRoot(tempElement, corNotificationValue);
                return corNotificationValue;
            }

        }
        catch( org.xml.sax.SAXException ex ) {
            return new IllegalArgumentException( ex.getMessage() );
        }
        return null;
    }

/**
	<correlatedNotifications>
		<item>
		<notificationIds>
		    <item>2343242</item>
		</notificationIds>
			<managedObjectInstance>gfdgfgd</managedObjectInstance>
		</item>
	</correlatedNotifications>     
 */
    public static void fromXmlNoRoot(org.jdom.Element element,CorrelatedNotificationValue corNotificatnValue)
    throws org.xml.sax.SAXException
    {
	ArrayList ids = new ArrayList();
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
	    if(elementName.equalsIgnoreCase("notificationIds"))
	    {
		java.util.List children2 = element.getChildren();
		Iterator it2 = children2.iterator();
		org.jdom.Element child2 = null;
		String elementName2 = null;
		String elementValue2 = null;
		
		while(it2.hasNext())
		{
		    child2 = (org.jdom.Element)it2.next();
		    elementName2 = child2.getName();
		    elementValue2 = child2.getTextTrim();
		    if(elementName2.equalsIgnoreCase("Item"))
		    {
			ids.add(elementValue2);
		    }
		}
		
		corNotificatnValue.setNotificationIds((String[])ids.toArray());
	    }
	    else if(elementName.equalsIgnoreCase("managedObjectInstance"))
	    {
		corNotificatnValue.setManagedObjectInstance(elementValue);
	    }
	}//end while

    }
}//end class


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
import org.jdom.input.*;
import org.jdom.*;
import javax.oss.ManagedEntityValue;



import javax.oss.MultiValueList;

import javax.oss.fm.monitor.*;
import ossj.qos.fmri.*;

public class QueryByFilterableAttributesValueXmlTranslator
{

    public QueryByFilterableAttributesValueXmlTranslator()
    {

    }

    /**
     *  <fm:QueryByFilterableAttributesValue>
     *      <managedObjectInstance>dsgfdsd</managedObjectInstance>
     *      <managedObjectClass>dsfgsd</managedObjectClass>
     *      <alarmType>dfsgsd</alarmType>
     *      <perceivedSeverity>dfsgsd</perceivedSeverity>
     *      <alarmAckState>dfsgsd</alarmAckState>
     *      <timeConstraint>2134213</timeConstraint>
     *  </fm:QueryByFilterableAttributesValue>
    */
    public static String toXml(QueryByFilterableAttributesValue valueObject, String elementName )
    throws org.xml.sax.SAXException
    {

        StringBuffer sb = new StringBuffer();
	try{

        if (valueObject == null)
        {
          //VP: System.out.println("VALUEOBJECT IS NULL");
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {

          String mngdObjectInstance = null;
	    String mngdObjectClass   = null;
	    String alarmType  = null;
	    short perceivedSeverty = 0;
	    short alarmAckState = 0;
	    String timeConstraint = null;
            //get values from the object
	    /*String mngdObjectInstance  = valueObject.getManagedObjectInstance();
	    String mngdObjectClass  = valueObject.getManagedObjectClass();
	    String alarmType = valueObject.getAlarmType();
	    short perceivedSeverty = valueObject.getPerceivedSeverity();
	    short alarmAckState = valueObject.getAlarmAckState();
	    String timeConstraint = valueObject.getTimeConstraint().toString();*/

	    //sb.append("<" + elementName + ">\n");
	    if(mngdObjectInstance == null)
		sb.append("<managedObjectInstance xsi:nil=\"true\"></managedObjectInstance>\n");
            else
		sb.append("<managedObjectInstance>" + mngdObjectInstance + "</managedObjectInstance>\n");

	    if(mngdObjectClass == null)
		sb.append("<managedObjectClass xsi:nil=\"true\"></managedObjectClass>\n");
            else
		sb.append("<managedObjectClass>" + mngdObjectClass + "</managedObjectClass>\n");

	    if(alarmType == null)
		sb.append("<alarmType xsi:nil=\"true\"></alarmType>\n");
            else
		sb.append("<alarmType>" + alarmType + "</alarmType>\n");

	    sb.append("<perceivedSeverity>" + perceivedSeverty + "</perceivedSeverity>\n");
	    sb.append("<alarmAckState>" + alarmAckState + "</alarmAckState>\n");

	    if(timeConstraint == null)
		sb.append("<timeConstraint xsi:nil=\"true\"></timeConstraint>\n");
            else
		sb.append("<timeConstraint>" + timeConstraint + "</timeConstraint>\n");

	    //sb.append("</" + elementName + ">");
        }
        //VP: System.out.println("COMPLETE QueryByFilterableAttributesValue XML IS ::" + sb.toString());

    }catch(Exception e){
    //VP: System.out.println("Exception caught in QueryByFilterableAttributesValueXmlTranslator" + e.getMessage());

    }
    return  sb.toString();
    }




    public static Object fromXml(org.w3c.dom.Element element, String type  )
    throws IllegalArgumentException
    {
        try
	{
            if( type.equals(QueryByFilterableAttributesValue.class.getName() ))
	    {
                QueryByFilterableAttributesValue valueObject = new QueryByFilterableAttributesValueImpl();
		org.jdom.input.DOMBuilder builder= new DOMBuilder();
                org.jdom.Element tempElement = builder.build(element);
                fromXmlNoRoot(tempElement, valueObject);
                return valueObject;
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

    public static void fromXmlNoRoot(org.jdom.Element element,QueryByFilterableAttributesValue valueObject)
    throws org.xml.sax.SAXException,ParseException
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
	    if(elementName.equalsIgnoreCase("managedObjectInstance"))
	    {
		valueObject.setManagedObjectInstance(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("managedObjectClass"))
	    {
		valueObject.setManagedObjectClass(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("alarmType"))
	    {
		valueObject.setAlarmType(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("perceivedSeverty"))
	    {
		valueObject.setPerceivedSeverity(Short.parseShort(elementValue));
	    }
	    else if(elementName.equalsIgnoreCase("alarmAckState"))
	    {
		valueObject.setAlarmAckState(Short.parseShort(elementValue));
	    }
	    else if(elementName.equalsIgnoreCase("timeConstraint"))
	    {
		DateFormat df = DateFormat.getDateInstance();
		java.util.Date date =  df.parse(elementValue);
		valueObject.setTimeConstraint(date);
	    }

	}//end while
    }

}//end class

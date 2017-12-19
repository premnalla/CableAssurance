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

public class ThresholdInfoTypeXmlTranslator
{


    //Java singleton design pattern - private
    static private ThresholdInfoTypeXmlTranslator singleton = new ThresholdInfoTypeXmlTranslator();


    private static HashMap schemaToAttMap;
    private static HashMap monthHash;
   // private static InterfaceReflector ifReflector = new InterfaceReflector();


    //private HashMap desiredAttributes;   //leave in ttv for now
    //transient private InterfaceReflector ifReflector;


    //protected ctor - singleton pattern
    protected ThresholdInfoTypeXmlTranslator()
    {

    }
/**
 * <fm:ThresholdInfoType>
 * 	<observedObject>gdsfgdfg</observedObject>
 *      	<thresholdDefinition>
 *              	<descriptor>
 *                      	<name>fdgsdfg</name>
 *                              <baseAttributeTypes>STRING</baseAttributeTypes>
 *                              <baseCollectionMethods>STATUS_INSPECTION</baseCollectionMethods>
 *                              <isArray>false</isArray>				
 *                       </descriptor>
 *                       <threholdValue>gssdfsdf</threholdValue>
 *                      <offset>sfdgdsfg</offset>
 *             	        <baseThresholdDirection>RISING</baseThresholdDirection>
 *              </thresholdDefinition>
 *      <observedValue>sdgdsfgds</observedValue>
 *      <armTime>4543543</armTime>		
 * </fm:ThresholdInfoType>

 */
    public static String toXml(ThresholdInfoType thresholdInfo , String elementName )
    throws org.xml.sax.SAXException
    {
	StringBuffer sb = new StringBuffer();
        if (thresholdInfo == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
            String armTime = thresholdInfo.getArmTime().toString();
	    String observedObj = thresholdInfo.getObservedObject();
	    String observedValue = (String)thresholdInfo.getObservedValue();
	    String name = thresholdInfo.getThresholdDefinition().getAttributeDescriptor().getName();
    	    int type = thresholdInfo.getThresholdDefinition().getAttributeDescriptor().getType();
	    String collectionMethod  = thresholdInfo.getThresholdDefinition().getAttributeDescriptor().getCollectionMethod();
	    boolean isArray = thresholdInfo.getThresholdDefinition().getAttributeDescriptor().isArray();
	    String thresholdValue = (String)thresholdInfo.getThresholdDefinition().getValue();
	    String offset = thresholdInfo.getThresholdDefinition().getOffset().toString();
	    int direction = thresholdInfo.getThresholdDefinition().getDirection();

	    
	    
	    sb.append("<" + elementName + ">\n");
	    if(observedObj == null)
		sb.append("<fm:observedObject xsi:nil=\"true\"></fm:observedObject>\n");
            else
		sb.append("<fm:observedObject>" + observedObj + "</fm:observedObject>\n");
		
	    sb.append("<fm:thresholdDefinition><fm:descriptor>");
	    sb.append("<fm:name>"+name+"</fm:name>");
	    sb.append("<fm:baseAttributeTypes>"+type+"</fm:baseAttributeTypes>");
	    sb.append("<fm:baseCollectionMethods>"+collectionMethod+"</fm:baseCollectionMethods>");
	    sb.append("<fm:isArray>"+isArray+"</fm:isArray>");
	    sb.append("</fm:descriptor>");
	    sb.append("<fm:threholdValue>"+thresholdValue+"</fm:threholdValue>");
	    sb.append("<fm:offset>"+offset+"</fm:offset>");
	    sb.append("<fm:baseThresholdDirection>"+direction+"</fm:baseThresholdDirection>");
	    sb.append("</fm:thresholdDefinition>");	    		
	    sb.append("<fm:observedValue>"+observedValue+"</fm:observedValue>");
	    sb.append("<fm:armTime>"+armTime+"</fm:armTime>");
	    sb.append("</" + elementName + ">");	
        }
    return  sb.toString();
    }




    
    
    
    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException 
    {
        try 
	{
            if( type.equals(ThresholdInfoType.class.getName() )) 
	    {
                ThresholdInfoType thresholdInfoType = new ThresholdInfoTypeImpl();
		org.jdom.input.DOMBuilder builder= new DOMBuilder();
                org.jdom.Element tempElement = builder.build(element);		
                fromXmlNoRoot(tempElement, thresholdInfoType);
                return thresholdInfoType;
            }

        }
        catch( org.xml.sax.SAXException ex ) {
            return new IllegalArgumentException( ex.getMessage() );
        }
	catch(Exception e)
	{
	    return null;
	}
        return null;
    }

    public static void fromXmlNoRoot(org.jdom.Element element, ThresholdInfoType thresholdObj)
    throws org.xml.sax.SAXException, ParseException
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
	    if(elementName.equalsIgnoreCase("observedObject"))
	    {
		thresholdObj.setObservedObject(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("baseAttributeTypes"))
	    {
		int type = Integer.parseInt(elementValue);
		thresholdObj.getThresholdDefinition().getAttributeDescriptor().setType(type);
	    }
	    else if(elementName.equalsIgnoreCase("baseCollectionMethods"))
	    {
		thresholdObj.getThresholdDefinition().getAttributeDescriptor().setCollectionMethod(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("isArray"))
	    {
		boolean isArray = (new Boolean(elementValue)).booleanValue();
		thresholdObj.getThresholdDefinition().getAttributeDescriptor().setIsArray(isArray);
	    }
	    
	    else if(elementName.equalsIgnoreCase("offset"))
	    {
		thresholdObj.getThresholdDefinition().setOffset(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("baseThresholdDirection"))
	    {
		int direction = Integer.parseInt(elementValue);
		thresholdObj.getThresholdDefinition().setDirection(direction);
	    }
	    else if(elementName.equalsIgnoreCase("observedValue"))
	    {
		thresholdObj.setObservedValue(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("armTime"))
	    {
		DateFormat df = DateFormat.getDateInstance();
		java.util.Date date =  df.parse(elementValue);
		thresholdObj.setArmTime(date);
	    }
	}//end while    

    }

}//end class


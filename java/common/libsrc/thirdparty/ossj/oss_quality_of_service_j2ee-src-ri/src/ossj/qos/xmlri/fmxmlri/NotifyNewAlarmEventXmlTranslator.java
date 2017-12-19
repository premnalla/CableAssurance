package ossj.qos.xmlri.fmxmlri;



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
import java.lang.StringBuffer;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

import javax.oss.ManagedEntityValue;



import javax.oss.MultiValueList;

import javax.oss.fm.monitor.*;
import ossj.qos.fmri.*;

public class NotifyNewAlarmEventXmlTranslator
{


    public NotifyNewAlarmEventXmlTranslator()
    {

    }
    /**
<notifyNewAlarmEvent>
<event>
	<applicationDN></applicationDN>
	<eventTime></eventTime>
	<managedObjectClass></managedObjectClass>
	<managedObjectInstance></managedObjectInstance>
	<notificationId></notificationId>
	<alarmType> </alarmType>
	<alarmKey> 
	....
	</alarmKey>
	<fm:basePerceivedAlarmSeverity></fm:basePerceivedAlarmSeverity>
	<fm:baseProbableAlarmCause></fm:baseProbableAlarmCause>
	<specificProblem>sdsds</specificProblem>
	<correlatedNotifications>
		<item>
		....
		</item>
	</correlatedNotifications>
	<backedUpStatus></backedUpStatus>
	<backUpObject></backUpObject>
	<trendIndication></trendIndication>
	
	<fm:ThresholdInfoType>
  	<observedObject>gdsfgdfg</observedObject>
       	<thresholdDefinition>
               	<descriptor>
                       	<name>fdgsdfg</name>
                               <baseAttributeTypes>STRING</baseAttributeTypes>
                               <baseCollectionMethods>STATUS_INSPECTION</baseCollectionMethods>
                               <isArray>false</isArray>				
                        </descriptor>
                        <threholdValue>gssdfsdf</threholdValue>
                       <offset>sfdgdsfg</offset>
              	        <baseThresholdDirection>RISING</baseThresholdDirection>
               </thresholdDefinition>
       <observedValue>sdgdsfgds</observedValue>
       <armTime>4543543</armTime>		
	</fm:ThresholdInfoType>
	<attributeChanges>
	<Item>
           <attributeName>fdgsdfg</attributeName>
           <attributeType>fdgsd</attributeType>
           <oldValue>dfsgsdf</oldValue>
           <newValue>dsfgsd</newValue>		
	</Item>		   
	</attributeChanges>
	<monitoredAttributes>
	<Item>
       <attributeName>fdgsdfg</attributeName>
       <attributeType>fdgsd</attributeType>
       <value>dfsgsdf</value>
	 <Item>
	</monitoredAttributes>
	
	<proposedRepairActions>ssfds</proposedRepairActions>
	<additionalText>sdadsa</additionalText>
</event>
</notifyNewAlarmEvent>
     */

    public static String toXml(NotifyNewAlarmEvent alarmEvent,String elementName )
    throws org.xml.sax.SAXException
    {
        StringBuffer sb = new StringBuffer();
        if (alarmEvent == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
	    sb.append("<" + elementName + ">");
	    sb.append("<event>");
	    String appDN = alarmEvent.getApplicationDN();
	    Date evenTime = alarmEvent.getEventTime();
	    String strEventTime = evenTime.toString();
	    String mngdObjClass = alarmEvent.getManagedObjectClass();
	    String mngdObjInstance = alarmEvent.getManagedObjectInstance();
	    String notificationId =  alarmEvent.getNotificationId();
	    String alarmType = alarmEvent.getAlarmType();
	    AlarmKey alarmKey = alarmEvent.getAlarmKey();	    
	    short severity = alarmEvent.getPerceivedSeverity();
	    short cause = alarmEvent.getProbableCause();
	    String strSeverity = String.valueOf(severity);
	    String strCause = String.valueOf(cause);
	    String specificProb = alarmEvent.getSpecificProblem();
	    CorrelatedNotificationValue[] corValues = alarmEvent.getCorrelatedNotifications();
	    String backedStatus = alarmEvent.getBackedUpStatus().toString();
	    String backedObject = alarmEvent.getBackUpObject();
	    String trendIndication = alarmEvent.getTrendIndication();
	    ThresholdInfoType thresholdType = alarmEvent.getThresholdInfo();
	    AttributeValueChange[] attrValueChanges = alarmEvent.getAttributeChanges();
	    AttributeValue[] attValues = alarmEvent.getMonitoredAttributes();
	    String additionalText = alarmEvent.getAdditionalText();
	    
	    sb.append("<applicationDN>"+appDN+"</applicationDN>");
	    sb.append("<eventTime>"+strEventTime+"</eventTime>");
	    sb.append("<managedObjectClass>"+mngdObjClass+"</managedObjectClass>");
	    sb.append("<managedObjectInstance>"+mngdObjInstance+"</managedObjectInstance>");
	    sb.append("<notificationId>"+notificationId+"</notificationId>");
	    sb.append("<alarmType>"+alarmType+"</alarmType>");
	    String alarmKeyXml = AlarmKeyXmlTranslator.toXml(alarmKey,"alarmKey");
	    sb.append(alarmKeyXml);
	    sb.append("<fm:basePerceivedAlarmSeverity>"+strSeverity+"</fm:basePerceivedAlarmSeverity>");
	    sb.append("<fm:baseProbableAlarmCause>"+strCause+"</fm:baseProbableAlarmCause>");
	    sb.append("<specificProblem>"+specificProb+"</specificProblem>");
	    sb.append("<correlatedNotifications>");
	    StringBuffer tempBuffer = new StringBuffer();
	    for(int i=0;i<corValues.length;i++)
	    {
		tempBuffer.append(CorrelatedNotificationValueXmlTranslator.toXml(corValues[i],"Item"));
	    }
	    sb.append(tempBuffer.toString());
	    sb.append("</correlatedNotifications>");
	    sb.append(backedStatus);
	    sb.append(backedObject);
	    sb.append(trendIndication);
	    String thresholdXml = ThresholdInfoTypeXmlTranslator.toXml(thresholdType,"ThresholdInfoType");
	    sb.append(thresholdXml);
	    sb.append("<attributeChanges>");
	    tempBuffer = new StringBuffer();
	    for(int i=0;i<attrValueChanges.length;i++)
	    {
		tempBuffer.append(AttributeValueChangeXmlTranslator.toXml(attrValueChanges[i],"Item"));
	    }
	    sb.append(tempBuffer.toString());
	    sb.append("</attributeChanges>");
	    sb.append("<monitoredAttributes>");
	    tempBuffer = new StringBuffer();
	    for(int i=0;i<attValues.length;i++)
	    {
		tempBuffer.append(AttributeValueXmlTranslator.toXml(attValues[i],"Item"));
	    }
	    sb.append(tempBuffer.toString());
	    sb.append("</monitoredAttributes>");
	    sb.append("<additionalText>"+additionalText+"</additionalText>");
	    sb.append("</event>");
	    sb.append("</" + elementName + ">");
	}	
    return sb.toString();	
    }

    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException 
    {
        try 
	{
            if( type.equals(NotifyNewAlarmEvent.class.getName() ))
	    {
                NotifyNewAlarmEvent alarmEvent = new NotifyNewAlarmEventImpl();
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, alarmEvent);
                return alarmEvent;
                }

        }
        catch( org.xml.sax.SAXException ex ) {
            return new IllegalArgumentException( ex.getMessage() );
        }
        catch( Exception e) 
	{   
	    return null;
	}
	
        return null;


    }

    public static void fromXmlNoRoot(org.jdom.Element element, NotifyNewAlarmEvent alarmEvent)
    throws org.xml.sax.SAXException,JDOMException,ParseException
    {
	DOMOutputter domOutputter = new DOMOutputter();
	List list = element.getChildren();
	Iterator it = list.iterator();
	String elementName = null;
	String elementValue = null;
	org.jdom.Element localElement = null;
	while(it.hasNext())//A
	{
	    localElement = (org.jdom.Element)it.next();
	    elementName = localElement.getName();
	    elementValue = localElement.getTextTrim();
	    if(elementName.equalsIgnoreCase("event"))//B
	    {
		Iterator it2 = localElement.getChildren().iterator();
		org.jdom.Element localElement2 = null;
		String name2 = null;
		String value2 = null;
		while(it2.hasNext())//C
		{
		    localElement2 = (org.jdom.Element)it2.next();
		    name2 = localElement2.getName();
		    value2 = localElement.getTextTrim();
		    if(name2.equalsIgnoreCase("applicationDN"))
		    {
			alarmEvent.setApplicationDN(value2);
		    }
		    else if(name2.equalsIgnoreCase("eventTime"))
		    {
			DateFormat df = DateFormat.getInstance();
			Date date = df.parse(value2);
			alarmEvent.setEventTime(date);
		    }
    		    else if(name2.equalsIgnoreCase("managedObjectClass"))
		    {
			alarmEvent.setManagedObjectClass(value2);
		    }
    		    else if(name2.equalsIgnoreCase("managedObjectInstance"))
		    {
			alarmEvent.setManagedObjectInstance(value2);
		    }
    		    else if(name2.equalsIgnoreCase("notificationId"))
		    {
			alarmEvent.setNotificationId(value2);
		    }
    		    else if(name2.equalsIgnoreCase("alarmType"))
		    {
			alarmEvent.setAlarmType(value2);
		    }
    		    else if(name2.equalsIgnoreCase("alarmKey"))
		    {
			String className = AlarmKey.class.getName();
			AlarmKey alarmKey = null;
			org.w3c.dom.Element w3cElement = domOutputter.output(localElement2);
			alarmKey = (AlarmKey)AlarmKeyXmlTranslator.fromXml(w3cElement,className);
			alarmEvent.setAlarmKey(alarmKey); 
		    }
    		    else if(name2.equalsIgnoreCase("fm:basePerceivedAlarmSeverity"))
		    {
			short sevValue =  Short.valueOf(value2).shortValue();
			alarmEvent.setPerceivedSeverity(sevValue);
		    }
    		    else if(name2.equalsIgnoreCase("fm:baseProbableAlarmCause"))
		    {
    			short probableCause =  Short.valueOf(value2).shortValue();
			alarmEvent.setProbableCause(probableCause);
		    }
    		    else if(name2.equalsIgnoreCase("specificProblem"))
		    {
			alarmEvent.setSpecificProblem(value2);
		    }
    		    else if(name2.equalsIgnoreCase("backedUpStatus"))
		    {
			Boolean b = new Boolean(value2);
			alarmEvent.setBackedUpStatus(b);
		    }
    		    else if(name2.equalsIgnoreCase("backUpObject"))
		    {
			alarmEvent.setBackUpObject(value2);
		    }
    		    else if(name2.equalsIgnoreCase("trendIndication"))
		    {
			alarmEvent.setTrendIndication(value2);
		    }
    		    else if(name2.equalsIgnoreCase("fm:ThresholdInfoType"))
		    {
			String className = ThresholdInfoType.class.getName();
			ThresholdInfoType thresholdInfoType = null;
			org.w3c.dom.Element w3cElement = domOutputter.output(localElement2);
			thresholdInfoType = (ThresholdInfoType)ThresholdInfoTypeXmlTranslator.fromXml(w3cElement,className);
			alarmEvent.setThresholdInfo(thresholdInfoType);			
		    }
    		    else if(name2.equalsIgnoreCase("proposedRepairActions"))
		    {
			alarmEvent.setProposedRepairActions(value2);
		    }
    		    else if(name2.equalsIgnoreCase("additionalText"))
		    {
			alarmEvent.setAdditionalText(value2);
		    }
    		    else if(name2.equalsIgnoreCase("correlatedNotifications"))
		    {
			List corList = localElement2.getChildren();
			Iterator corIt = corList.iterator();
			String corName = null;
			String corValue = null;
			org.jdom.Element corElement = null;
			ArrayList corArrayList = new ArrayList();
			while(corIt.hasNext())
			{
			    corElement = (org.jdom.Element)corIt.next();
			    corName = corElement.getName();
			    corValue = corElement.getTextTrim();
			    String className = CorrelatedNotificationValue.class.getName();
			    CorrelatedNotificationValue corNotificationValue = null;
			    if(corName.equalsIgnoreCase("Item"))
			    {
				org.w3c.dom.Element w3cElement = domOutputter.output(localElement2);			    
			        corNotificationValue = (CorrelatedNotificationValue)CorrelatedNotificationValueXmlTranslator.fromXml(w3cElement,className);
				corArrayList.add(corNotificationValue);				
			    }
			}
		    CorrelatedNotificationValue[] corArray = (CorrelatedNotificationValue[])corArrayList.toArray();
		    alarmEvent.setCorrelatedNotifications(corArray);	
		    }
    		    else if(name2.equalsIgnoreCase("attributeChanges"))
		    {
			List attList = localElement2.getChildren();
			Iterator attIt = attList.iterator();
			String attName = null;
			String attValue = null;
			org.jdom.Element attElement = null;
			ArrayList attArrayList = new ArrayList();
			while(attIt.hasNext())
			{
			    attElement = (org.jdom.Element)attIt.next();
			    attName = attElement.getName();
			    attValue = attElement.getTextTrim();
			    String className = AttributeValueChange.class.getName();
			    AttributeValueChange attValueChange = null;
			    org.w3c.dom.Element w3cElement = domOutputter.output(localElement2);			    
			    attValueChange = (AttributeValueChange)AttributeValueChangeXmlTranslator.fromXml(w3cElement,className);
			    attArrayList.add(attValueChange);			    
			}
		    AttributeValueChange[] attValueChangeArray =  (AttributeValueChange[])attArrayList.toArray();
		    alarmEvent.setAttributeChanges(attValueChangeArray);		    
		    }
    		    else if(name2.equalsIgnoreCase("monitoredAttributes"))
		    {
			List attList = localElement2.getChildren();
			Iterator attIt = attList.iterator();
			String attName = null;
			String attValue = null;
			org.jdom.Element attElement = null;
			ArrayList attArrayList = new ArrayList();
			while(attIt.hasNext())
			{
			    attElement = (org.jdom.Element)attIt.next();
			    attName = attElement.getName();
			    attValue = attElement.getTextTrim();
			    String className = AttributeValueChange.class.getName();
			    AttributeValue attValueChange = null;
     			    org.w3c.dom.Element w3cElement = domOutputter.output(localElement2);
			    attValueChange = (AttributeValue)AttributeValueXmlTranslator.fromXml(w3cElement,className);
			    attArrayList.add(attValueChange);			    
			}
		    AttributeValue[] attValueChangeArray =  (AttributeValue[])attArrayList.toArray();
		    alarmEvent.setMonitoredAttributes(attValueChangeArray);		    
			
		    }
    		    else if(name2.equalsIgnoreCase("additionalText"))
		    {
			alarmEvent.setAdditionalText(value2);
		    }
    		    else if(name2.equalsIgnoreCase("additionalText"))
		    {
			alarmEvent.setAdditionalText(value2);
		    }
		    
		    
    		    else if(name2.equalsIgnoreCase("additionalText"))
		    {
			alarmEvent.setAdditionalText(value2);
		    }
		}//end C
	    }//end B
	}//end A	
    }

}

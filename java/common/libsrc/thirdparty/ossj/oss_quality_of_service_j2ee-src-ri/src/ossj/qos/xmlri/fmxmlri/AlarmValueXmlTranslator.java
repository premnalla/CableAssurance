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
import org.jdom.output.*;
import org.jdom.*;

import javax.oss.ManagedEntityValue;



import javax.oss.MultiValueList;

import javax.oss.fm.monitor.*;
import ossj.qos.fmri.*;

public class AlarmValueXmlTranslator
{

    public AlarmValueXmlTranslator()
    {

    }




    public static String toXml(AlarmValue alv,String elementName )
    throws org.xml.sax.SAXException
    {

        StringBuffer sb= new StringBuffer();
        //VP: System.out.println("element name is: " + elementName);
        try{
        if (alv == null)
        {
            //sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
            sb.append("<" + elementName + " xsi:nil=\"true\"><" + elementName + ">");
            //VP: System.out.println("element name is: " + elementName);
            //VP: System.out.println("response so far is: " + sb.toString());
        }
        else
        {
            long luvn = alv.getLastUpdateVersionNumber();
            sb.append("<co:lastUpdateVersionNumber>" + String.valueOf(luvn) + "</co:lastUpdateVersionNumber>\n");
            //VP: System.out.println( "---lastUpdateVersionNumber OK---");

            if( alv.isPopulated( AlarmValue.ALARM_RAISED_TIME ))
            {   //VP: System.out.println( "---ALARM_RAISED_TIME---1");
                java.util.Date alarmRaisedTime = alv.getAlarmRaisedTime();
                //VP: System.out.println(alarmRaisedTime.toString());

                if ( alarmRaisedTime == null )
                sb.append("<fm:alarmRaisedTime xsi:nil=\"true\"></fm:alarmRaisedTime>\n");
                else
                {
                    sb.append("<fm:alarmRaisedTime>");
                    AlarmXmlTranslator.Date2Xml(sb,alarmRaisedTime);
                    sb.append("</fm:alarmRaisedTime>\n");
                }
            }


            if( alv.isPopulated( AlarmValue.MANAGED_OBJECT_CLASS) )
            {   //VP: System.out.println( "---MANAGED_OBJECT_CLASS---");
                String moc = alv.getManagedObjectClass();
                if (moc == null)
                sb.append("<fm:managedObjectClass xsi:nil=\"true\"></fm:managedObjectClass>\n");
                else
                sb.append("<fm:managedObjectClass>" + moc + "</fm:managedObjectClass>\n");
            }


            if( alv.isPopulated( AlarmValue.MANAGED_OBJECT_INSTANCE) )
            {   //VP: System.out.println( "---MANAGED_OBJECT_INSTANCE---");
                String  moi = alv.getManagedObjectInstance();
                if (moi == null)
                sb.append("<fm:managedObjectInstance xsi:nil=\"true\"></fm:managedObjectInstance>\n");
                else
                sb.append("<fm:managedObjectInstance>" + moi + "</fm:managedObjectInstance>\n");
            }



            if( alv.isPopulated( AlarmValue.SYSTEM_DN) )
            {   //VP: System.out.println( "---SYSTEM_DN---");
                String dn = alv.getSystemDN();
                if (dn == null)
                sb.append("<fm:systemDN xsi:nil=\"true\"></fm:systemDN>\n");
                else
                sb.append("<fm:systemDN>" + dn + "</fm:systemDN>\n");
            }


            if( alv.isPopulated( AlarmValue.ALARM_TYPE) )
            {   //VP: System.out.println( "---ALARM_TYPE---");
                String type = alv.getAlarmType();
                if (type == null)
                sb.append("<fm:alarmType xsi:nil=\"true\"></fm:alarmType>\n");
                else
                sb.append("<fm:alarmType>" + type + "</fm:alarmType>\n");
            }


            if( alv.isPopulated( ManagedEntityValue.KEY) )
            {   //VP: System.out.println( "---KEY---");
                AlarmKey key = alv.getAlarmKey();

                if (key == null){
                sb.append("<fm:alarmKey xsi:nil=\"true\"></fm:alarmKey>\n");}
                else{
                String alkey = AlarmKeyXmlTranslator.toXml(key, "fm:alarmKey");
                sb.append("<fm:alarmKey>" + alkey + "</fm:alarmKey>\n");
                }
            }


            if( alv.isPopulated( AlarmValue.NOTIFICATION_ID) )
            {   //VP: System.out.println( "---NOTIFICATION_ID---");
                String id = alv.getNotificationId();
                if (id == null)
                sb.append("<fm:notificationId xsi:nil=\"true\"></fm:notificationId>\n");
                else
                sb.append("<fm:notificationId>" + id + "</fm:notificationId>\n");
            }

            if( alv.isPopulated( AlarmValue.PROBABLE_CAUSE) ) {
                //VP: System.out.println( "---PROBABLE_CAUSE---");
                AlarmXmlTranslator.EnumeratedType2Xml(sb,ProbableCause.class,alv.getProbableCause());
            }

            if( alv.isPopulated( AlarmValue.PERCEIVED_SEVERITY) ) {
                //VP: System.out.println( "---PERCEIVED_SEVERITY---");
                AlarmXmlTranslator.perceivedEnumeratedType2Xml(sb,PerceivedSeverity.class,alv.getPerceivedSeverity());
            }

            if( alv.isPopulated( AlarmValue.SPECIFIC_PROBLEM) )
            {   //VP: System.out.println( "---SPECIFIC_PROBLEM---");
                String sp = alv.getSpecificProblem ();
                if (sp == null)
                sb.append("<fm:specificProblem xsi:nil=\"true\"></fm:specificProblem>\n");
                else
                sb.append("<fm:specificProblem>" + sp + "</fm:specificProblem>\n");
            }


            if( alv.isPopulated( AlarmValue.CORRELATED_NOTIFICATIONS))
            {   //VP: System.out.println( "---CORRELATED_NOTIFICATIONS---");
                CorrelatedNotificationValue[] notifications = alv.getCorrelatedNotifications();
                if ( notifications == null )
                sb.append("<fm:correlatedNotifications xsi:nil=\"true\"></fm:correlatedNotifications>\n");
                else
                {
                    sb.append("<fm:correlatedNotifications>\n");
                    AlarmXmlTranslator.CorrelatedNotificationValues2Xml(sb,notifications);
                    sb.append("</fm:correlatedNotifications>\n");
                }
            }


            if( alv.isPopulated( AlarmValue.BACKED_UP_STATUS) )
            {   //VP: System.out.println( "---BACKED_UP_STATUS---");
                Boolean bstat = alv.getBackedUpStatus ();
                if (bstat == null)
                sb.append("<fm:backedUpStatus xsi:nil=\"true\"></fm:backedUpStatus>\n");
                else
                {
                    sb.append("<fm:backedUpStatus>");
                    AlarmXmlTranslator.Boolean2Xml(sb,bstat.booleanValue());
                    sb.append("</fm:backedUpStatus>\n");
                }
            }


            if( alv.isPopulated( AlarmValue.BACK_UP_OBJECT) )
            {   //VP: System.out.println( "---BACK_UP_OBJECT---");
                String bobj = alv.getBackUpObject ();
                if (bobj == null)
                sb.append("<fm:backUpObject xsi:nil=\"true\"></fm:backUpObject>\n");
                else
                sb.append("<fm:backUpObject>" + bobj + "</fm:backUpObject>\n");
            }


            if( alv.isPopulated( AlarmValue.TREND_INDICATION) )
            {   //VP: System.out.println( "---TREND_INDICATION---");
                String trind = alv.getTrendIndication();
                if (trind == null)
                sb.append("<fm:baseTrendIndicationType xsi:nil=\"true\"></fm:baseTrendIndicationType>\n");
                else
                sb.append("<fm:baseTrendIndicationType>" + trind + "</fm:baseTrendIndicationType>\n");
            }


            if( alv.isPopulated( AlarmValue.THRESHOLD_INFO) )
            {   //VP: System.out.println( "---THRESHOLD_INFO---");
                ThresholdInfoType tinfo = alv.getThresholdInfo();
                if ( tinfo == null )
                sb.append("<fm:thresholdInfo xsi:nil=\"true\"></fm:thresholdInfo>\n");
                else
                {
                    sb.append("<fm:thresholdInfo>\n");
                    AlarmXmlTranslator.ThresholdInfo2Xml(sb,tinfo);
                    sb.append("</fm:thresholdInfo>\n");
                }
            }



            if( alv.isPopulated( AlarmValue.ATTRIBUTE_CHANGES) )
            {   //VP: System.out.println( "---ATTRIBUTE_CHANGES---");
                AttributeValueChange[] avc = alv.getAttributeChanges();
                if ( avc == null )
                sb.append("<fm:attributeChanges xsi:nil=\"true\"></fm:attributeChanges>\n");
                else
                {
                    sb.append("<fm:attributeChanges>\n");
                    AlarmXmlTranslator.AttributeValueChanges2Xml(sb,avc);
                    sb.append("</fm:attributeChanges>\n");
                }
            }


            if( alv.isPopulated( AlarmValue.MONITORED_ATTRIBUTES) )
            {   //VP: System.out.println( "---MONITORED_ATTRIBUTES---");
                AttributeValue[] avl = alv.getMonitoredAttributes();
                if ( avl == null )
                sb.append("<fm:monitoredAttributes xsi:nil=\"true\"></fm:monitoredAttributes>\n");
                else
                {
                    sb.append("<fm:monitoredAttributes>\n");
                    AlarmXmlTranslator.AttributeValues2Xml(sb,avl);
                    sb.append("</fm:monitoredAttributes>\n");
                }
            }



            if( alv.isPopulated( AlarmValue.PROPOSED_REPAIR_ACTIONS) )
            {   //VP: System.out.println( "---PROPOSED_REPAIR_ACTIONS---");
                String actions = alv.getProposedRepairActions();
                if (actions == null)
                sb.append("<fm:proposedRepairActions xsi:nil=\"true\"></fm:proposedRepairActions>\n");
                else
                sb.append("<fm:proposedRepairActions>" + actions + "</fm:proposedRepairActions>\n");
            }


            if( alv.isPopulated( AlarmValue.ADDITIONAL_TEXT) )
            {   //VP: System.out.println( "---ADDITIONAL_TEXT---");
                String adtext = alv.getAdditionalText();
                if (adtext == null)
                sb.append("<fm:additionalText xsi:nil=\"true\"></fm:additionalText>\n");
                else
                sb.append("<fm:additionalText>" + adtext + "</fm:additionalText>\n");
            }


            if( alv.isPopulated( AlarmValue.ACK_USER_ID) )
            {   //VP: System.out.println( "---ACK_USER_ID---");
                String uid = alv.getAckUserId();
                if (uid == null)
                sb.append("<fm:ackUserId xsi:nil=\"true\"></fm:ackUserId>\n");
                else
                sb.append("<fm:ackUserId>" + uid + "</fm:ackUserId>\n");
            }



            if( alv.isPopulated( AlarmValue.ACK_TIME) )
            {   //VP: System.out.println( "---ACK_TIME---");
                java.util.Date acctime = alv.getAckTime();
                //VP: System.out.println( "---(1)---");
                if ( acctime == null )
                sb.append("<fm:ackTime xsi:nil=\"true\"></fm:ackTime>\n");
                else
                {
                    sb.append("<fm:ackTime>");
                    //VP: System.out.println( "---(2)---");
                    AlarmXmlTranslator.Date2Xml(sb,acctime);
                    //VP: System.out.println( "---(3)---");
                    sb.append("</fm:ackTime>\n");
                }
            }

            if( alv.isPopulated( AlarmValue.ALARM_ACK_STATE) ) {
                //VP: System.out.println( "---ALARM_ACK_STATE---");

                AlarmXmlTranslator.EnumeratedType2Xml(sb,AlarmAckState.class, alv.getAlarmAckState());
            }


            if( alv.isPopulated( AlarmValue.ACK_SYSTEM_ID) )
            {  //VP: System.out.println( "---ACK_SYSTEM_ID---");
                String sysid = alv.getAckSystemId();
                if (sysid == null)
                sb.append("<fm:ackSystemId xsi:nil=\"true\"></fm:ackSystemId>\n");
                else
                sb.append("<fm:ackSystemId>" + sysid + "</fm:ackSystemId>\n");
            }


            if( alv.isPopulated( AlarmValue.COMMENTS) )
            {   //VP: System.out.println( "---COMMENTS---");
                CommentValue[] comments = alv.getComments();
                if ( comments == null ){
                //VP: System.out.println( "Reached at 1");
                //sb.append("<fm:comments xsi:nil=\"true\"></fm:comments>\n");
                }else if(comments.length==0){

                }
                else
                {
                  //VP: System.out.println( "Reached at 1 length is :" + comments.length );
                    sb.append("<fm:comments>\n");
                    AlarmXmlTranslator.CommentValues2Xml(sb,comments);
                    sb.append("</fm:comments>\n");
                }
            }


            if( alv.isPopulated( AlarmValue.ALARM_CLEARED_TIME) )
            {   //VP: System.out.println( "---ALARM_CLEARED_TIME---");
                java.util.Date  clrtime = alv.getAlarmClearedTime();
                if ( clrtime == null )
                sb.append("<fm:alarmClearedTime xsi:nil=\"true\"></fm:alarmClearedTime>\n");
                else
                {
                    sb.append("<fm:alarmClearedTime>");
                    AlarmXmlTranslator.Date2Xml(sb,clrtime);
                    sb.append("</fm:alarmClearedTime>\n");
                }
            }


            if( alv.isPopulated( AlarmValue.ALARM_CHANGED_TIME) )
            {   //VP: System.out.println( "---ALARM_CHANGED_TIME---");
                java.util.Date chtime = alv.getAlarmChangedTime();
                if ( chtime == null )
                sb.append("<fm:alarmChangedTime xsi:nil=\"true\"></fm:alarmChangedTime>\n");
                else
                {
                    sb.append("<fm:alarmChangedTime>");
                    AlarmXmlTranslator.Date2Xml(sb,chtime);
                    sb.append("</fm:alarmChangedTime>\n");
                }
            }


        }
        }catch(Exception e){
         sb.append(e.getMessage());
        }

        return  sb.toString();
    }




    public static Object fromXml(org.w3c.dom.Element element, String type  )
    throws IllegalArgumentException
    {

       AlarmValue alarmValue = null;
      try
	{
                //VP: System.out.println("Element is !!!!!!!(&&(*)(" + element.getTagName()+element.getFirstChild().getNodeName()+element.getLastChild().getNodeName());
                alarmValue = new AlarmValueImpl();
                AlarmKey alkey = new AlarmKeyImpl();
                 //javax.oss.ApplicationContext appContext = new ApplicationContextImpl();
                // Start New Code
                NodeList nList = element.getChildNodes();
                 for(int i=0; i < nList.getLength(); i++){
                    Node n = nList.item(i);
                    if(n.getNodeName().equalsIgnoreCase("fm:alarmKey")){
                       NodeList nList1 = n.getChildNodes();
                       for(int j=0; j < nList1.getLength(); j++){
                        Node n1 = nList1.item(j);
                         if(n1.getNodeName().equalsIgnoreCase("fm:alarmPrimaryKey")){
                          alkey.setAlarmPrimaryKey(n1.getFirstChild().getNodeValue());
                        }else if(n1.getNodeName().equalsIgnoreCase("co:applicationDN")){
                           alkey.setApplicationDN(n1.getFirstChild().getNodeValue());
                        }/*else if(n1.getNodeName().equalsIgnoreCase("co:applicationContext")){
                           NodeList nList2 = n1.getChildNodes();
                          for(int k=0; j < nList2.getLength(); k++){
                            Node n2 = nList2.item(k);
                            if(n2.getNodeName().equalsIgnoreCase("co:factoryClass")){
                              appContext.setFactoryClass(n2.getFirstChild().getNodeValue());
                            }else if(n1.getNodeName().equalsIgnoreCase("co:url")){
                                appContext.setURL(n2.getFirstChild().getNodeValue());
                              }

                          }



                        }*/else if(n1.getNodeName().equalsIgnoreCase("co:type")){
                            alkey.setType( AlarmValue.VALUE_TYPE );
                           NodeList nList2 = n1.getChildNodes();
                        }

                       }
                    }
                }
                alarmValue.setAlarmKey(alkey);
                //End New Code

		//org.jdom.input.DOMBuilder builder = new DOMBuilder();
		//org.jdom.Element tempElement = builder.build(element);
                //fromXmlNoRoot(tempElement, alarmValue);



        }
        catch(Exception ex )
	{
            //VP: System.out.println(ex.getMessage());
        }


        return alarmValue;
    }



    public static void fromXmlNoRoot(org.jdom.Element element, AlarmValue alarmValue)
    throws org.xml.sax.SAXException,ParseException,JDOMException
    {
	try{
        java.util.List children = element.getChildren();
	Iterator it = children.iterator();
	org.jdom.Element child = null;
	String elementName = null;
	String elementValue = null;
	DOMOutputter domOutputter = new DOMOutputter(); //to convert between w3c and Jdom elements

	while(it.hasNext())
	{
	    child = (org.jdom.Element)it.next();
	    elementName = child.getName();
	    elementValue = child.getTextTrim();
	    if(elementName.equalsIgnoreCase("fm:alarmRaisedTime"))
	    {
		DateFormat df = DateFormat.getDateInstance();
		java.util.Date date =  df.parse(elementValue);
		alarmValue.setAlarmRaisedTime(date);
	    }
	    else if(elementName.equalsIgnoreCase("fm:managedObjectClass"))
	    {
		alarmValue.setManagedObjectClass(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("fm:managedObjectInstance"))
	    {
		alarmValue.setManagedObjectInstance(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("fm:systemDN"))
	    {
		alarmValue.setSystemDN(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("fm:alarmType"))
	    {
		alarmValue.setAlarmType(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("fm:alarmKey"))
	    {
		AlarmKey alarmKey = null;
		org.w3c.dom.Element w3cElement = domOutputter.output(child);
		alarmKey = (AlarmKey)AlarmKeyXmlTranslator.fromXml(w3cElement, AlarmKey.class.getName());
		alarmValue.setAlarmKey(alarmKey);
	    }
	    else if(elementName.equalsIgnoreCase("fm:notificationId"))
	    {
		alarmValue.setNotificationId(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("fm:probableAlarmCause"))
	    {
		short probabaleAlarmCause = Short.parseShort(elementValue);
		alarmValue.setProbableCause(probabaleAlarmCause);
	    }
	    else if(elementName.equalsIgnoreCase("fm:perceivedAlarmSeverity"))
	    {
		short perceivedAlarmSecurity = Short.parseShort(elementValue);
		alarmValue.setPerceivedSeverity(perceivedAlarmSecurity);
	    }
	    else if(elementName.equalsIgnoreCase("fm:specificProblem"))
	    {
		alarmValue.setSpecificProblem(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("fm:correlatedNotifications"))
	    {
		String className = CorrelatedNotificationValue.class.getName();
		ArrayList arrayList = new ArrayList();
		CorrelatedNotificationValue corValue = null;
		List myItems = child.getChildren();
		Iterator myIt = myItems.iterator();
		org.jdom.Element myElement = null;
		String myElementName = null;
		String myElementValue = null;
		org.w3c.dom.Element w3cElement = null;

		while(myIt.hasNext())
		{
		    myElement = (org.jdom.Element)myIt.next();
		    myElementName = myElement.getName();
		    myElementValue = myElement.getTextTrim();
		    if(myElementName.equalsIgnoreCase("notificationIds"))
		    {
			w3cElement =  domOutputter.output(myElement);
			corValue = (CorrelatedNotificationValue)CorrelatedNotificationValueXmlTranslator.fromXml(w3cElement,className);
			arrayList.add(corValue);
		    }
		}
		alarmValue.setCorrelatedNotifications((CorrelatedNotificationValue[])arrayList.toArray());
	    }
	    else if(elementName.equalsIgnoreCase("fm:backedUpStatus"))
	    {
		Boolean objBoolean = new Boolean(elementValue);
		alarmValue.setBackedUpStatus(objBoolean);
	    }
	    else if(elementName.equalsIgnoreCase("fm:trendIndicationType"))
	    {
		alarmValue.setTrendIndication(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("fm:thresholdInfo"))
	    {
		ThresholdInfoType threholdInfoType = null;
		String className = ThresholdInfoType.class.getName();
		org.w3c.dom.Element w3cElement =  domOutputter.output(child);
		threholdInfoType = (ThresholdInfoType)ThresholdInfoTypeXmlTranslator.fromXml(w3cElement,className);
		alarmValue.setThresholdInfo(threholdInfoType);

		/*
		List secondLevelChildren = child.getChildren();
		Iterator it = secondLevelChildren.iterator();
		org.jdom.Element jdomElement = null;
		String childName = null;
		String childValue = null;

		while(it.hasNext())
		{
		    jdomElement = (org.jdom.Element)it.next();
		    childName = jdomElement.getName();
		    childValue = jdomElement.getTextTrim();
		    if(childName.equalsIgnoreCase("fm:observedObject"))
		    {
			alarmValue.getThresholdInfo().setObservedObject(childValue);
		    }
		    else if(childName.equalsIgnoreCase("fm:observedValue"))
		    {
			alarmValue.getThresholdInfo().setObservedValue(childValue);
		    }
		    else if(childName.equalsIgnoreCase("fm:armTime"))
		    {
			DateFormat df = DateFormat.getDateInstance();
			Date date = df.parse(childValue);
			alarmValue.getThresholdInfo().setArmTime(date);
		    }
		    else if(childName.equalsIgnoreCase("fm:thresholdDefinition"))
		    {
			List list = child.getChildren();
			Iterator it = list.iterator();
			String name = null;
			String value = null;
			org.jdom.Element nextChild = null;
			while(it.hasNext())
			{
			     nextChild = (org.jdom.Element)it.next();
			     name = nextChild.getName();
			     value = nextChild.getTextTrim();
			     if(name.equalsIgnoreCase("fm:name"))
			     {
				alarmValue.getThresholdInfo().getThresholdDefinition().getAttributeDescriptor().setName(value);
			     }
			     else if(name.equalsIgnoreCase("fm:baseCollectionMethods"))
			     {
				alarmValue.getThresholdInfo().getThresholdDefinition().getAttributeeDescriptor().setCollectionMethod(value);
			     }
			     else if(name.equalsIgnoreCase("fm:isArray"))
			     {
				alarmValue.getThresholdInfo().getThresholdDefinition().getAttributeDescriptor().setIsArray(value);
			     }
			     else if(name.equalsIgnoreCase("fm:threholdValue"))
			     {
				alarmValue.getThresholdInfo().getThresholdDefinition().setValue(value);
			     }
			     else if(name.equalsIgnoreCase("fm:offset"))
			     {
				alarmValue.getThresholdInfo().getThresholdDefinition().setOffset(value);
			     }

			}
		    }



		}

		alarmValue.setManagedObjectClass(elementValue);
	*/
	    }
	    else if(elementName.equalsIgnoreCase("managedObjectClass"))
	    {
		alarmValue.setManagedObjectClass(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("managedObjectClass"))
	    {
		alarmValue.setManagedObjectClass(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("managedObjectClass"))
	    {
		alarmValue.setManagedObjectClass(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("managedObjectClass"))
	    {
		alarmValue.setManagedObjectClass(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("managedObjectClass"))
	    {
		alarmValue.setManagedObjectClass(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("managedObjectClass"))
	    {
		alarmValue.setManagedObjectClass(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("managedObjectClass"))
	    {
		alarmValue.setManagedObjectClass(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("managedObjectClass"))
	    {
		alarmValue.setManagedObjectClass(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("managedObjectClass"))
	    {
		alarmValue.setManagedObjectClass(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("managedObjectClass"))
	    {
		alarmValue.setManagedObjectClass(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("managedObjectClass"))
	    {
		alarmValue.setManagedObjectClass(elementValue);
	    }

	}//end while

    }catch(Exception e){
    //VP: System.out.println(e.getMessage());

    }
  }
}//end class



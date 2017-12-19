package ossj.qos.fmri;

/**
 * AlarmXmlTranslator
 *
 * Translates AlarmValue object to and from Java/XML
 *
 * @author Copyright 2001-2002 Nortel Networks Limited. All rights reserved
 */

import java.util.Vector;
import java.util.HashMap;
import java.util.Date;
import java.text.DateFormat;
import java.lang.StringBuffer;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.oss.ManagedEntityValue;
import java.text.SimpleDateFormat;
import java.util.Locale;



import javax.oss.MultiValueList;

import javax.oss.fm.monitor.*;
import ossj.qos.fmri.*;

public class AlarmXmlTranslator
{


    //Java singleton design pattern - private
    static private AlarmXmlTranslator singleton = new AlarmXmlTranslator();


    private static HashMap schemaToAttMap;
    private static HashMap monthHash;
    private static InterfaceReflector ifReflector = new InterfaceReflector();


    //private HashMap desiredAttributes;   //leave in ttv for now
    //transient private InterfaceReflector ifReflector;


    //protected ctor - singleton pattern
    protected AlarmXmlTranslator()
    {
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
    //============================================================
    //==============   Alarm Value toXML =========================
    //============================================================

    public static String toXml(AlarmValue alv,String elementName )
    throws org.xml.sax.SAXException
    {

        //VP: System.out.println( "---NEWTEST---");
        StringBuffer sb= new StringBuffer();
        if (alv == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
            sb.append("<" + elementName + ">\n");


            long luvn = alv.getLastUpdateVersionNumber();
            sb.append("<co:lastUpdateVersionNumber>" + String.valueOf(luvn) + "</co:lastUpdateVersionNumber>\n");
            //VP: System.out.println( "---lastUpdateVersionNumber OK---");

            if( alv.isPopulated( AlarmValue.ALARM_RAISED_TIME ))
            {   //VP: System.out.println( "---ALARM_RAISED_TIME---2");
                java.util.Date alarmRaisedTime = alv.getAlarmRaisedTime();

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
                AlarmXmlTranslator.AlarmKey2Xml(sb,key, "fm:alarmKey");
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
                AlarmXmlTranslator.EnumeratedType2Xml(sb,PerceivedSeverity.class,alv.getPerceivedSeverity());
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
                CorrelatedNotificationValue[]notifications = alv.getCorrelatedNotifications();
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
                String trind = alv.getTrendIndication ();
                if (trind == null)
                sb.append("<fm: xsi:nil=\"true\"></fm:trendIndicationType>\n");
                else
                sb.append("<fm:trendIndicationType>" + trind + "</fm:trendIndicationType>\n");
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
                if ( comments == null )
                sb.append("<fm:comments xsi:nil=\"true\"></fm:comments>\n");
                else
                {
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

            sb.append("</" + elementName + ">\n");
        }

        return  sb.toString();
    }




    public static String toXml(AlarmKey alkey,String elementName )
    throws org.xml.sax.SAXException
    {
        StringBuffer sb= new StringBuffer();
        AlarmKey2Xml(sb, alkey, elementName);
        return  sb.toString();
    }


    public static void AlarmKey2Xml(StringBuffer strBuffer, AlarmKey key, String elementName)
    {
        if ( key == null )
        strBuffer.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">\n");
        else
        {

            strBuffer.append("\n<" + elementName + ">\n");

            javax.oss.ApplicationContext applicationContext = key.getApplicationContext();

            if (applicationContext != null)
            {
                strBuffer.append("<co:applicationContext>\n");

                String fact=applicationContext.getFactoryClass();
                if (fact != null)
                strBuffer.append("<co:factoryClass>" + fact + "</co:factoryClass>\n");

                String url=applicationContext.getURL();
                if (url != null)
                strBuffer.append("<co:url>" + url + "</co:url>\n");


                java.util.Map props=applicationContext.getSystemProperties();
                if (props != null)
                {
                    Object currValue = null;
                    Integer currKey = null;

                    strBuffer.append("<co:systemProperties>\n");

                    for ( java.util.Iterator iter = props.keySet().iterator();iter.hasNext(); )
                    {
                        currKey = (Integer) iter.next();
                        currValue = props.get( currKey );
                        strBuffer.append("<co:property>");
                        //V.R. TO DO get name and value of properties
                        strBuffer.append("<co:name>" +  "</co:name>\n");
                        strBuffer.append("<co:value>" +  "</co:value>\n");
                        strBuffer.append("</co:property>\n");
                    }
                    strBuffer.append("</co:systemProperties>\n");
                }

                strBuffer.append("</co:applicationContext>\n");
            }


            String appDn=key.getApplicationDN();
            if (appDn != null)
            strBuffer.append("<co:applicationDN>" + appDn + "</co:applicationDN>\n");


            strBuffer.append("<co:type>" + "fm:AlarmValue" + "</co:type>\n");

            strBuffer.append("<fm:alarmPrimaryKey>" + key.getAlarmPrimaryKey() +"</fm:alarmPrimaryKey>\n");

            strBuffer.append("</" + elementName + ">\n");
        }
    }

    public static void CorrelatedNotificationValues2Xml(StringBuffer sb,CorrelatedNotificationValue[] cnv)
    {
     int i = cnv.length;
       if(i == 0){
        sb.append("<fm:Item xsi:nil=\"true\"></fm:Item>");
       }
        for (int x=0; x<i; x++)
        {
            if (cnv[x] == null)
            {
                sb.append("<fm:Item xsi:nil=\"true\"></fm:Item>");
            }
            else
            {
                sb.append("<fm:Item>\n");

                String[] nids = cnv[x].getNotificationIds();
                if (nids == null)
                sb.append("<fm:notificationIds xsi:nil=\"true\"></fm:notificationIds>\n");
                else
                {
                    sb.append("<fm:notificationIds>");
                    for (int y=0; y<nids.length;y++)
                    {
                        if (nids[y] == null)
                        sb.append("<co:Item xsi:nil=\"true\"></co:Item>\n");
                        else
                        sb.append("<co:Item>" + nids[y] + "</co:Item>\n");
                    }
                    sb.append("</fm:notificationIds>\n");
                }

                String moi = cnv[x].getManagedObjectInstance();
                if (moi == null)
                sb.append("<fm:managedObjectInstance xsi:nil=\"true\"></fm:managedObjectInstance>\n");
                else
                sb.append("<fm:managedObjectInstance>" + moi + "</fm:managedObjectInstance>\n");

                sb.append("</fm:Item>\n");
            }
        }
    }


    public static void ThresholdInfo2Xml(StringBuffer sb, ThresholdInfoType tinfo)
    {
        String obsobj = tinfo.getObservedObject();
        if (obsobj == null)
        sb.append("<fm:observedObject xsi:nil=\"true\"></fm:observedObject>\n");
        else
        sb.append("<fm:observedObject>" + obsobj + "</fm:observedObject>\n");


        Object obsval = tinfo.getObservedValue();
        if (obsval == null)
        sb.append("<fm:observedValue xsi:nil=\"true\"></fm:observedValue>\n");
        else
        sb.append("<fm:observedValue>" + obsval.toString() + "</fm:observedValue>\n");


        java.util.Date armTime = tinfo.getArmTime();
        if ( armTime == null )
        sb.append("<fm:armTime xsi:nil=\"true\"></fm:armTime>\n");
        else
        {
            sb.append("<fm:armTime>");
            AlarmXmlTranslator.Date2Xml(sb,armTime);
            sb.append("</fm:armTime>\n");
        }
        //V.R. TO DO Get "ThresholdDefinition" from the Threshold  portion of the QoS API and
        //"PerformanceAttributeDescriptor" from the Performance portion of the QoS API.
    }

    public static void AttributeValueChanges2Xml(StringBuffer sb, AttributeValueChange[] avc)
    {
        for (int x=0; x<avc.length; x++)
        {
            if (avc[x] == null)
            {
                sb.append("<fm:Item xsi:nil=\"true\"></fm:Item>");
            }
            else
            {
                sb.append("<fm:Item>\n");

                String atrname = avc[x].getAttributeName();
                if (atrname == null)
                sb.append("<fm:attributeName xsi:nil=\"true\"></fm:attributeName>\n");
                else
                sb.append("<fm:attributeName>" + atrname + "</fm:attributeName>\n");


                String atrtype = avc[x].getAttributeType();
                if (atrtype == null)
                sb.append("<fm:attributeType xsi:nil=\"true\"></fm:attributeType>\n");
                else
                sb.append("<fm:attributeType>" + atrtype + "</fm:attributeType>\n");

                Object attrval = avc[x].getOldValue();
                if (attrval == null)
                sb.append("<fm:oldValue xsi:nil=\"true\"></fm:oldValue>\n");
                else
                sb.append("<fm:oldValue>" + attrval.toString() + "</fm:oldValue>\n");

                Object newval = avc[x].getNewValue();
                if (newval == null)
                sb.append("<fm:newValue xsi:nil=\"true\"></fm:newValue>\n");
                else
                sb.append("<fm:newValue>" + newval.toString() + "</fm:newValue>\n");

                sb.append("</fm:Item>\n");
            }
        }

    }

    public static void AttributeValues2Xml(StringBuffer sb, AttributeValue[] aval)
    {
        for (int x=0; x<aval.length; x++)
        {
            if (aval[x] == null)
            {
                sb.append("<fm:Item xsi:nil=\"true\"></fm:Item>");
            }
            else
            {
                sb.append("<fm:Item>\n");

                String atrname = aval[x].getAttributeName();
                if (atrname == null)
                sb.append("<fm:attributeName xsi:nil=\"true\"></fm:attributeName>\n");
                else
                sb.append("<fm:attributeName>" + atrname + "</fm:attributeName>\n");


                String atrtype = aval[x].getAttributeType();
                if (atrtype == null)
                sb.append("<fm:attributeType xsi:nil=\"true\"></fm:attributeType>\n");
                else
                sb.append("<fm:attributeType>" + atrtype + "</fm:attributeType>\n");

                Object attrval = aval[x].getValue();
                if (attrval == null)
                sb.append("<fm:value xsi:nil=\"true\"></fm:value>\n");
                else
                sb.append("<fm:value>" + attrval.toString() + "</fm:value>\n");

                sb.append("</fm:Item>\n");
            }
        }
    }

    public static void CommentValues2Xml(StringBuffer sb, CommentValue[] cval)
    {
        for (int x=0; x<cval.length; x++)
        {
            if (cval[x] == null)
            {
                sb.append("<fm:item xsi:nil=\"true\"></fm:item>");
            }
            else
            {
                sb.append("<fm:item>\n");

                String uid = cval[x].getCommentUserId();
                if (uid == null)
                sb.append("<fm:commentUserId xsi:nil=\"true\"></fm:commentUserId>\n");
                else
                sb.append("<fm:commentUserId>" + uid + "</fm:commentUserId>\n");


                java.util.Date comTime = cval[x].getCommentTime();
                if ( comTime == null )
                sb.append("<fm:commentTime xsi:nil=\"true\"></fm:commentTime>\n");
                else
                {
                    sb.append("<fm:commentTime>");
                    AlarmXmlTranslator.Date2Xml(sb,comTime);
                    sb.append("</fm:commentTime>\n");
                }


                String ctext = cval[x].getCommentText();
                if (ctext == null)
                sb.append("<fm:commentText xsi:nil=\"true\"></fm:commentText>\n");
                else
                sb.append("<fm:commentText>" + ctext + "</fm:commentText>\n");


                String sysid = cval[x].getCommentSystemId();
                if (sysid == null)
                sb.append("<fm:commentSystemId xsi:nil=\"true\"></fm:commentSystemId>\n");
                else
                sb.append("<fm:commentSystemId>" + sysid + "</fm:commentSystemId>\n");

                sb.append("</fm:item>\n");
            }
        }

    }
    //============================================================
    //==============   Alarm Value toXML - END ===================
    //============================================================


    //============================================================
    //==============   Alarm Value fromXML  ======================
    //============================================================

    public static Object fromXml( Element element, String type  )
    throws IllegalArgumentException {
        try {
            if( type.equals(AlarmValue.class.getName() )) {
                AlarmValue alval = new AlarmValueImpl();
                fromXmlNoRoot(element, alval);
                return alval;
            }
            else if( type.equals(AlarmKey.class.getName() )) {
                //VP: System.out.println("--fromXml on AlarmKey---" );
                AlarmKey  alkey = AlarmKeyFromXml(element);
                return alkey;
            }

        }
        catch( org.xml.sax.SAXException ex ) {
            return new IllegalArgumentException( ex.getMessage() );
        }
        return null;
    }

    public static void fromXmlNoRoot(Node node,AlarmValue aval)
    throws org.xml.sax.SAXException
    {

        NodeList nodeList3 = node.getChildNodes();
        Node node4;
        //System.out.println("fromXmlNoRoot--> translated attributes:");
        //System.out.println("fromXmlNoRoot--> length:" + nodeList3.getLength());
        for (int z=0; z<nodeList3.getLength(); z++)
        {
            node4 = nodeList3.item(z);
            if( node4.getNodeType() != Node.ELEMENT_NODE ) continue;
            String nodeName2 = node4.getNodeName();
            //VP: System.out.println( "Node Name = " + nodeName2 );
            if ( nodeName2.equals("fm:alarmRaisedTime") )
            aval.setAlarmRaisedTime(AlarmXmlTranslator.DateFromXml(node4));
            else if ( nodeName2.equals("fm:managedObjectClass") )
            {
                if (node4.hasChildNodes())
                aval.setManagedObjectClass(node4.getFirstChild().getNodeValue());
            }
            else if ( nodeName2.equals("fm:managedObjectInstance") )
            {
                if (node4.hasChildNodes())
                aval.setManagedObjectInstance(node4.getFirstChild().getNodeValue());
            }
            else if ( nodeName2.equals("fm:systemDN") )
            {
                if (node4.hasChildNodes())
                aval.setSystemDN(node4.getFirstChild().getNodeValue());
            }
            else if ( nodeName2.equals("fm:alarmType") )
            {
                if (node4.hasChildNodes())
                aval.setAlarmType(node4.getFirstChild().getNodeValue());
            }
            else if ( nodeName2.equals("fm:alarmKey") )
            aval.setAlarmKey(AlarmXmlTranslator.AlarmKeyFromXml(node4));
            else if ( nodeName2.equals("fm:notificationId") )
            {
                if (node4.hasChildNodes())
                aval.setNotificationId(node4.getFirstChild().getNodeValue());
            }
            else if ( nodeName2.equals("fm:probableCause") )
            {
                if (node4.hasChildNodes())
                aval.setProbableCause(AlarmXmlTranslator.ShortEnumeratedTypeFromXml(node4,ProbableCause.class));
            }
            else if ( nodeName2.equals("fm:perceivedSeverity") )
            {
                if (node4.hasChildNodes())
                aval.setPerceivedSeverity(AlarmXmlTranslator.ShortEnumeratedTypeFromXml(node4,PerceivedSeverity.class));
            }
            else if ( nodeName2.equals("fm:specificProblem") )
            {
                if (node4.hasChildNodes())
                aval.setSpecificProblem(node4.getFirstChild().getNodeValue());
            }
            else if ( nodeName2.equals("fm:correlatedNotifications") )
            aval.setCorrelatedNotifications(AlarmXmlTranslator.CorrelatedNotificationsFromXml(node4));
            else if ( nodeName2.equals("fm:backedUpStatus") )
            aval.setBackedUpStatus(new Boolean(AlarmXmlTranslator.BooleanFromXml(node)));
            else if ( nodeName2.equals("fm:backUpObject") )
            {
                if (node4.hasChildNodes())
                aval.setBackUpObject(node4.getFirstChild().getNodeValue());
            }
            else if ( nodeName2.equals("fm:trendIndicationType") )
            {
                if (node4.hasChildNodes())
                aval.setTrendIndication(node4.getFirstChild().getNodeValue());
            }
            else if ( nodeName2.equals("fm:thresholdInfo") )
            aval.setThresholdInfo(AlarmXmlTranslator.ThresholdInfoTypeFromXml(node4));
            else if ( nodeName2.equals("fm:attributeChanges") )
            aval.setAttributeChanges(AlarmXmlTranslator.AttributeValueChangesFromXml(node4));
            else if ( nodeName2.equals("fm:monitoredAttributes") )
            aval.setMonitoredAttributes(AlarmXmlTranslator.AttributeValuesFromXml(node4));
            else if ( nodeName2.equals("fm:speproposedRepairActions") )
            {
                if (node4.hasChildNodes())
                aval.setProposedRepairActions(node4.getFirstChild().getNodeValue());
            }
            else if ( nodeName2.equals("fm:additionalText") )
            {
                if (node4.hasChildNodes())
                aval.setAdditionalText(node4.getFirstChild().getNodeValue());
            }
            else if ( nodeName2.equals("fm:ackUserId") )
            {
                if (node4.hasChildNodes())
                aval.setAckUserId(node4.getFirstChild().getNodeValue());
            }
            else if ( nodeName2.equals("fm:ackTime") )
            aval.setAckTime(AlarmXmlTranslator.DateFromXml(node4));
            else if ( nodeName2.equals("fm:alarmAckState") )
            {
                if (node4.hasChildNodes())
                aval.setAlarmAckState(AlarmXmlTranslator.EnumeratedTypeFromXml(node4,AlarmAckState.class));
            }
            else if ( nodeName2.equals("fm:ackSystemId") )
            {
                if (node4.hasChildNodes())
                aval.setAckSystemId(node4.getFirstChild().getNodeValue());
            }
            else if ( nodeName2.equals("fm:comments") )
            aval.setComments(AlarmXmlTranslator.CommentsFromXml(node4));
            else if ( nodeName2.equals("fm:alarmClearedTime") )
            aval.setAlarmClearedTime(AlarmXmlTranslator.DateFromXml(node4));
            else if ( nodeName2.equals("fm:alarmChangedTime") )
            aval.setAlarmChangedTime(AlarmXmlTranslator.DateFromXml(node4));
            else if ( nodeName2.equals("#comment" ) )
            continue;
        }
    }



    public static AlarmKey AlarmKeyFromXml(Node node)
    {

        AlarmKey key = new AlarmKeyImpl();

        if (node != null)
        {
            NodeList nodeList = node.getChildNodes();
            Node node2;

            for (int x=0; x<nodeList.getLength(); x++)
            {
                node2 = nodeList.item(x);
                if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
                String nodeName = node2.getNodeName();

                if ( nodeName.equals("fm:alarmPrimaryKey") )
                key.setPrimaryKey( node2.getFirstChild().getNodeValue() );
                else if ( nodeName.equals("co:type") )
                key.setType( AlarmValue.VALUE_TYPE );
                else if ( nodeName.equals("#comment") )
                continue;
            }
        }
        return key;
    }

    public static CorrelatedNotificationValue[] CorrelatedNotificationsFromXml(Node node)
    {
        java.util.Vector cnVec = new java.util.Vector();

        NodeList nodeList = node.getChildNodes();
        Node node2;

        for ( int x=0; x< nodeList.getLength(); x++ )
        {
            node2 = nodeList.item(x);
            if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
            cnVec.addElement(AlarmXmlTranslator.CorrelatedNotificationValueFromXml(node2));
        }

        return (CorrelatedNotificationValue[])cnVec.toArray(new CorrelatedNotificationValue[0]);
    }

    public static CorrelatedNotificationValue CorrelatedNotificationValueFromXml(Node node)
    {

        CorrelatedNotificationValue cnv = new CorrelatedNotificationValueImpl();
        NodeList nodeList = node.getChildNodes();
        Node node2;

        for (int x=0; x<nodeList.getLength(); x++)
        {
            node2 = nodeList.item(x);
            if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
            String nodeName = node2.getNodeName();

            if (nodeName.equals("fm:notificationIds"))
            {
                NodeList nodeList2 = node2.getChildNodes();
                java.util.Vector niVect = new java.util.Vector();
                Node node3;
                for (int y=0; y<nodeList2.getLength(); y++)
                {
                    node3 = nodeList2.item(y);
                    if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
                    if (node3.hasChildNodes())
                    niVect.addElement(node3.getFirstChild().getNodeValue());
                }

                cnv.setNotificationIds((String[])niVect.toArray(new String[0]));
            }
            else if (nodeName.equals("fm:managedObjectInstance"))
            {
                if (node2.hasChildNodes())
                cnv.setManagedObjectInstance(node2.getFirstChild().getNodeValue());
            }
            else if ( nodeName.equals("#comment") )
            continue;
        }

        return cnv;

    }

    public static ThresholdInfoType ThresholdInfoTypeFromXml(Node node)
    {

        ThresholdInfoType tinfo = new ThresholdInfoTypeImpl();

        if (node != null)
        {
            NodeList nodeList = node.getChildNodes();
            Node node2;

            for (int x=0; x<nodeList.getLength(); x++)
            {
                node2 = nodeList.item(x);
                if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
                String nodeName = node2.getNodeName();

                if ( nodeName.equals("fm:observedObject") )
                tinfo.setObservedObject( node2.getFirstChild().getNodeValue() );
                else    if ( nodeName.equals("fm:setObservedValue") )
                {
                    //V.R. TO DO Get the type from the Performance attribute descriptor of
                    //the Threshold descriptor and convert the value of this attribute.
                }
                else    if ( nodeName.equals("fm:thresholdDefinition") )
                {
                    //V.R. TO DO Get "ThresholdDefinition" from the Threshold  portion of the QoS API and
                    //"PerformanceAttributeDescriptor" from the Performance portion of the QoS API.
                }
                else if ( nodeName.equals("fm:armTime") )
                tinfo.setArmTime(AlarmXmlTranslator.DateFromXml(node2));
                else if ( nodeName.equals("#comment") )
                continue;
            }
        }
        return tinfo;
    }

    public static AttributeValueChange[] AttributeValueChangesFromXml(Node node)
    {
        java.util.Vector atVec = new java.util.Vector();

        NodeList nodeList = node.getChildNodes();
        Node node2;

        for ( int x=0; x< nodeList.getLength(); x++ )
        {
            node2 = nodeList.item(x);
            if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
            atVec.addElement(AlarmXmlTranslator.AttributeValueChangeFromXml(node2));
        }

        return (AttributeValueChange[])atVec.toArray(new AttributeValueChange[0]);
    }

    public static AttributeValueChange AttributeValueChangeFromXml(Node node)
    {

        AttributeValueChange atv = new AttributeValueChangeImpl();
        NodeList nodeList = node.getChildNodes();
        Node node2;

        for (int x=0; x<nodeList.getLength(); x++)
        {
            node2 = nodeList.item(x);
            if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
            String nodeName = node2.getNodeName();

            if (nodeName.equals("fm:attributeName"))
            {
                if (node2.hasChildNodes())
                atv.setAttributeName(node2.getFirstChild().getNodeValue());
            }
            else if (nodeName.equals("fm:attributeType"))
            {
                if (node2.hasChildNodes())
                atv.setAttributeType(node2.getFirstChild().getNodeValue());
            }
            else if (nodeName.equals("fm:oldValue"))
            {
                //V.R. TO DO Use approach consistent with the the QoS API ThresholdInfo
                //to represent the attribute value.
            }
            else if (nodeName.equals("fm:newValue"))
            {
                //V.R. TO DO Use approach consistent with the the QoS API ThresholdInfo
                //to represent the attribute value.
            }
            else if ( nodeName.equals("#comment") )
            continue;
        }
        return atv;
    }

    public static AttributeValue[] AttributeValuesFromXml(Node node)
    {
        java.util.Vector atVec = new java.util.Vector();

        NodeList nodeList = node.getChildNodes();
        Node node2;

        for ( int x=0; x< nodeList.getLength(); x++ )
        {
            node2 = nodeList.item(x);
            if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
            atVec.addElement(AlarmXmlTranslator.AttributeValueFromXml(node2));
        }

        return (AttributeValue[])atVec.toArray(new AttributeValue[0]);
    }

    public static AttributeValue AttributeValueFromXml(Node node)
    {

        AttributeValue atv = new AttributeValueImpl();
        NodeList nodeList = node.getChildNodes();
        Node node2;

        for (int x=0; x<nodeList.getLength(); x++)
        {
            node2 = nodeList.item(x);
            if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
            String nodeName = node2.getNodeName();

            if (nodeName.equals("fm:attributeName"))
            {
                if (node2.hasChildNodes())
                atv.setAttributeName(node2.getFirstChild().getNodeValue());
            }
            else if (nodeName.equals("fm:attributeType"))
            {
                if (node2.hasChildNodes())
                atv.setAttributeType(node2.getFirstChild().getNodeValue());
            }
            else if (nodeName.equals("fm:Value"))
            {
                //V.R. TO DO Use approach consistent with the the QoS API ThresholdInfo
                //to represent the attribute value.
            }
            else if ( nodeName.equals("#comment") )
            continue;
        }
        return atv;
    }

    public static CommentValue[] CommentsFromXml(Node node)
    {
        java.util.Vector comVec = new java.util.Vector();

        NodeList nodeList = node.getChildNodes();
        Node node2;

        for ( int x=0; x< nodeList.getLength(); x++ )
        {
            node2 = nodeList.item(x);
            if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;

            comVec.addElement(AlarmXmlTranslator.CommentValueFromXml(node2));
        }

        return (CommentValue[])comVec.toArray(new CommentValue[0]);
    }

    public static CommentValue CommentValueFromXml(Node node)
    {

        CommentValue comval = new CommentValueImpl();
        NodeList nodeList = node.getChildNodes();
        Node node2;

        for (int x=0; x<nodeList.getLength(); x++)
        {
            node2 = nodeList.item(x);
            if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
            String nodeName = node2.getNodeName();

            if (nodeName.equals("fm:commentUserId"))
            {
                if (node2.hasChildNodes())
                comval.setCommentUserId(node2.getFirstChild().getNodeValue());
            }
            else if ( nodeName.equals("fm:commentTime") )
            comval.setCommentTime(AlarmXmlTranslator.DateFromXml(node2));
            else if (nodeName.equals("fm:commentText"))
            {
                if (node2.hasChildNodes())
                comval.setCommentText(node2.getFirstChild().getNodeValue());
            }
            else if (nodeName.equals("fm:commentSystemId"))
            {
                if (node2.hasChildNodes())
                comval.setCommentSystemId(node2.getFirstChild().getNodeValue());
            }
            else if ( nodeName.equals("#comment") )
            continue;
        }
        return comval;
    }


    //============================================================
    //==============   Alarm Value fromXML - END =================
    //============================================================



    //============================================================
    //==============          Common methods     =================
    //============================================================

    public static java.util.Date DateFromXml(Node node)
    {

        //1999-05-31T13:20:00-05:00.
        java.util.Date date = null;

        if (! (node.hasChildNodes()) )      //is it nilled?
        return date;

        String childNodeValue = node.getFirstChild().getNodeValue();
        //System.out.println("DateFromXml: childNodeValue: " + childNodeValue);

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

        /*
        System.out.println("year: " + year);
        System.out.println("month: " + month);
        System.out.println("day: " + day);
        System.out.println("hour: " + hour);
        System.out.println("min: " + min);
        System.out.println("sec: " + sec);
         */

        /*long utcDate = java.util.Date.UTC( Integer.parseInt(year) - 1900,
        Integer.parseInt(month) - 1,
        Integer.parseInt(day),
        Integer.parseInt(hour),
        Integer.parseInt(min),
        Integer.parseInt(sec) );
        date = new java.util.Date ( utcDate );*/

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

        //String ttAttName = (String)schemaToAttMap.get( childNodeName );
        //setAttributeValue( ttAttName, date);

        return date;

    }

    public static void Date2Xml(StringBuffer sb, java.util.Date date )
    {
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy HH:mm:ss 'GMT'", Locale.US);

        if (date == null)
        {
            sb.append( "0000-00-00T00:00:00Z" );		//Zero time if date is null
        }
        else
        {
            //StringTokenizer stTokenizer = new StringTokenizer(DateFormat.getInstance().format(date));
            StringTokenizer stTokenizer = new StringTokenizer(sdf.format(date));

            String day;
            String month;
            String year;
            String time;
            String zone;

            day = stTokenizer.nextToken();
            if (Integer.parseInt(day) < 10)
            {
                day = "0" + day;
            }

            month = stTokenizer.nextToken();
            year  = stTokenizer.nextToken();
            time  = stTokenizer.nextToken();
            zone  = stTokenizer.nextToken();

            //----------------------------------------------------------------
            // Convert from java Date format into XML timeInstant format.
            //   date format:  7 Mar 2001 10:10:10 GMT
            //    xml format:  2001-03-07T10:10:10Z
            //----------------------------------------------------------------

            String xmlStr = year + "-" + (String)monthHash.get(month) + "-" + day + "T" + time + "Z";
            sb.append(xmlStr);
        }
    }



    public static int EnumeratedTypeFromXml(Node node,
    Class interfaceClass)

    {

        Integer enumVal = null;

        //get string enum name from node, use reflector to transform to int
        try
        {
            String enumString;
            if (node.hasChildNodes())
            {
                enumString = node.getFirstChild().getNodeValue();
                enumVal = (Integer)(ifReflector.getInterfaceFieldValue( interfaceClass, enumString ));
            }
            else
            enumVal = new Integer(-1);      //-1 means uninitialized

        }
        catch( javax.oss.IllegalArgumentException iLLEx )
        {
            //throw new org.xml.sax.SAXException( iLLEx.getMessage() );
            System.out.println(iLLEx.getMessage());

        }
        catch( java.lang.IllegalAccessException iLLAccEx )
        {
            //throw new org.xml.sax.SAXException( iLLAccEx.getMessage() );
            System.out.println(iLLAccEx.getMessage());

        }
        catch( java.lang.NoSuchFieldException noSuchFldEx )
        {
            //throw new org.xml.sax.SAXException( noScuhFldEx.getMessage() );
            System.out.println(noSuchFldEx.getMessage());

        }

        return enumVal.intValue();

    }

    public static String EnumeratedType2Xml( Class interfaceClass, int enumInt )
    {
        String enumStrName = null;

        try
        {
            enumStrName = (String)
            (ifReflector.getInterfaceFieldName(interfaceClass,enumInt));



        }
        catch( javax.oss.IllegalArgumentException iLLEx )
        {
            //throw new org.xml.sax.SAXException( iLLEx.getMessage() );
            System.out.println(iLLEx.getMessage());
        }
        catch( java.lang.IllegalAccessException iLLAccEx )
        {
            //throw new org.xml.sax.SAXException( iLLAccEx.getMessage() );
            System.out.println(iLLAccEx.getMessage());
        }
        return enumStrName;
    }

    public static void EnumeratedType2Xml(StringBuffer sb,
    Class interfaceClass,
    short enumInt)
    {

        String enumStrName = null;
        //VP: System.out.println( "--->enumInt = " + enumInt );
        try
        {

            short senum = (short) enumInt;
            enumStrName = (String)
            (ifReflector.getInterfaceFieldName(interfaceClass,new Short(senum)));
            //VP: System.out.println( "--->enumStrName = " + enumStrName );


        }
        catch( javax.oss.IllegalArgumentException iLLEx )
        {
            //throw new org.xml.sax.SAXException( iLLEx.getMessage() );
            System.out.println(iLLEx.getMessage());
        }
        catch( java.lang.IllegalAccessException iLLAccEx )
        {
            //throw new org.xml.sax.SAXException( iLLAccEx.getMessage() );
            System.out.println(iLLAccEx.getMessage());
        }

        //-----------------------------------------------------------------------------
        // The interface class name corresponds to the XML tag.
        // Class.getName() returns the fully qualified name, so strip out the leaf node.
        // -1 is the default for non-populated enum values.
        //-----------------------------------------------------------------------------
        String fqClassName = interfaceClass.getName();
        //System.out.println ("EnumeratedType2Xml - interface class name: " + fqClassName);
        String classNameTxt = fqClassName.substring((fqClassName.lastIndexOf(".")) + 1);
        //lower case first character
        String lower = classNameTxt.substring(0,1);
        //System.out.println("****lower= " + lower );
        String restOf = classNameTxt.substring( 1 ) ;
        //System.out.println("****restOf= " + restOf );
        String className = lower.toLowerCase() + restOf;
        //System.out.println("****className= " + className );



        if (enumInt == -1)		//if not set (unpopulated)
        sb.append("<fm:" + "baseProbableAlarmCause" + " xsi:nil=\"true\">" + "</fm:" + "baseProbableAlarmCause" + ">\n");
        else
        sb.append("<fm:" + "baseProbableAlarmCause" + ">" + enumStrName + "</fm:" + "baseProbableAlarmCause" + ">\n");


    }
   //added by Vijay Sharma@ Telegea Inc. in order to resolve name space problem
    public static void perceivedEnumeratedType2Xml(StringBuffer sb,
    Class interfaceClass,
    short enumInt)
    {

        String enumStrName = null;
        //VP: System.out.println( "--->enumInt = " + enumInt );
        try
        {

            short senum = (short) enumInt;
            enumStrName = (String)
            (ifReflector.getInterfaceFieldName(interfaceClass,new Short(senum)));
            //VP: System.out.println( "--->enumStrName = " + enumStrName );


        }
        catch( javax.oss.IllegalArgumentException iLLEx )
        {
            //throw new org.xml.sax.SAXException( iLLEx.getMessage() );
            System.out.println(iLLEx.getMessage());
        }
        catch( java.lang.IllegalAccessException iLLAccEx )
        {
            //throw new org.xml.sax.SAXException( iLLAccEx.getMessage() );
            System.out.println(iLLAccEx.getMessage());
        }

        //-----------------------------------------------------------------------------
        // The interface class name corresponds to the XML tag.
        // Class.getName() returns the fully qualified name, so strip out the leaf node.
        // -1 is the default for non-populated enum values.
        //-----------------------------------------------------------------------------
        String fqClassName = interfaceClass.getName();
        //System.out.println ("EnumeratedType2Xml - interface class name: " + fqClassName);
        String classNameTxt = fqClassName.substring((fqClassName.lastIndexOf(".")) + 1);
        //lower case first character
        String lower = classNameTxt.substring(0,1);
        //System.out.println("****lower= " + lower );
        String restOf = classNameTxt.substring( 1 ) ;
        //System.out.println("****restOf= " + restOf );
        String className = lower.toLowerCase() + restOf;
        //System.out.println("****className= " + className );



        if (enumInt == -1)		//if not set (unpopulated)
        sb.append("<fm:" + "basePerceivedAlarmSeverity" + " xsi:nil=\"true\">" + "</fm:" + "basePerceivedAlarmSeverity" + ">\n");
        else
        sb.append("<fm:" + "basePerceivedAlarmSeverity" + ">" + enumStrName + "</fm:" + "basePerceivedAlarmSeverity" + ">\n");


    }


    public static void EnumeratedType2Xml(StringBuffer sb,
    Class interfaceClass,
    int enumInt)
    {

        String enumStrName = null;
        //VP: System.out.println( "--->enumInt = " + enumInt );
        try
        {

            //short senum = (short) enumInt;
            enumStrName = (String)
            (ifReflector.getInterfaceFieldName(interfaceClass,enumInt));
            //VP: System.out.println( "--->enumStrName = " + enumStrName );


        }
        catch( javax.oss.IllegalArgumentException iLLEx )
        {
            //throw new org.xml.sax.SAXException( iLLEx.getMessage() );
            System.out.println(iLLEx.getMessage());
        }
        catch( java.lang.IllegalAccessException iLLAccEx )
        {
            //throw new org.xml.sax.SAXException( iLLAccEx.getMessage() );
            System.out.println(iLLAccEx.getMessage());
        }

        //-----------------------------------------------------------------------------
        // The interface class name corresponds to the XML tag.
        // Class.getName() returns the fully qualified name, so strip out the leaf node.
        // -1 is the default for non-populated enum values.
        //-----------------------------------------------------------------------------
        String fqClassName = interfaceClass.getName();
        //System.out.println ("EnumeratedType2Xml - interface class name: " + fqClassName);
        String classNameTxt = fqClassName.substring((fqClassName.lastIndexOf(".")) + 1);
        //lower case first character
        String lower = classNameTxt.substring(0,1);
        //System.out.println("****lower= " + lower );
        String restOf = classNameTxt.substring( 1 ) ;
        //System.out.println("****restOf= " + restOf );
        String className = lower.toLowerCase() + restOf;
        //System.out.println("****className= " + className );



        if (enumInt == -1)		//if not set (unpopulated)
        sb.append("<fm:" + className + " xsi:nil=\"true\">" + "</fm:" + className + ">\n");
        else
        sb.append("<fm:" + className + ">" + enumStrName + "</fm:" + className + ">\n");


    }




    public static short ShortEnumeratedTypeFromXml(Node node, Class interfaceClass)
    {
        Short enumVal = null;

        try
        {
            String enumString;
            enumString = node.getFirstChild().getNodeValue();
            enumVal = (Short)(ifReflector.getInterfaceFieldValue( interfaceClass, enumString ));
        }
        catch( javax.oss.IllegalArgumentException iLLEx )
        {
            System.out.println(iLLEx.getMessage());
        }
        catch( java.lang.IllegalAccessException iLLAccEx )
        {
            System.out.println(iLLAccEx.getMessage());
        }
        catch( java.lang.NoSuchFieldException noSuchFldEx )
        {
            System.out.println(noSuchFldEx.getMessage());
        }

        return enumVal.shortValue();
    }

    public static void Boolean2Xml(StringBuffer sb,boolean bool)
    {
        //Since boolean is a primitive type, assume that there is no "null" value
        //for it - it's always either true or false.  May want to revisit this later.
        if (bool)
        sb.append("true");
        else
        sb.append("false");
    }
    public static boolean BooleanFromXml(Node node)
    {

        String val;
        if (node.hasChildNodes())
        {
            val = node.getFirstChild().getNodeValue();
            if (val.equals("true"))
            return true;
        }
        return false;   //default is false

    }

    //============================================================
    //==============     Common methods - END    =================
    //============================================================
}


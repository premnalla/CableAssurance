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
import javax.oss.ApplicationContext;


import javax.oss.MultiValueList;

import javax.oss.fm.monitor.*;
import ossj.qos.fmri.*;

public class AlarmKeyXmlTranslator
{



    public AlarmKeyXmlTranslator()
    {

    }

    /**
     * The template xml for this object
     * <fm:AlarmKey>
     *	    <fm:alarmPrimaryKey>primaryKey</fm:alarmPrimaryKey>
     *	</fm:AlarmKey>
     *
	<fm:AlarmKey>
	<co:applicationContext>
		<co:factoryClass>anyString</co:factoryClass>
		<co:url>anyURI</co:url>
		<co:systemProperties>
			<co:property>
				<co:name>anyString</co:name>
				<co:value>anyString</co:value>
			</co:property>
		</co:systemProperties>
	</co:applicationContext>
	<co:applicationDN>anyString</co:applicationDN>
	<co:type>anyString</co:type>
	<fm:alarmPrimaryKey>anyString</fm:alarmPrimaryKey>
	</fm:AlarmKey>
     */
    public static String toXml(Object obj ,String elementName )
    throws org.xml.sax.SAXException
    {
	AlarmKey alarmKey = (AlarmKey)obj;
        StringBuffer sb = new StringBuffer();
        if (alarmKey == null)
        {
            //VP: System.out.println("Alarm key translator, element name = " + elementName);
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
            //VP: System.out.println("Alarm key translateor, response is: " + sb.toString());
        }
        else
        {
	    //sb.append("<" + elementName + ">");
	   // String factoryClass = alarmKey.getApplicationContext().getFactoryClass();
            //String Url = alarmKey.getApplicationContext().getURL();
	    //Map map = alarmKey.getApplicationContext().getSystemProperties();
	    //String appDN =  alarmKey.getApplicationDN();
	    //String type =  alarmKey.getType();
	   // String key =  alarmKey.getAlarmPrimaryKey();


	    sb.append("<co:applicationContext>");
	    sb.append("<co:factoryClass>");
            if(alarmKey.getApplicationContext() != null)
	    sb.append(alarmKey.getApplicationContext().getFactoryClass());
	    sb.append("</co:factoryClass>");
	    sb.append("<co:url>");
            if(alarmKey.getApplicationContext() != null)
	    sb.append(alarmKey.getApplicationContext().getURL());
	    sb.append("</co:url>");

            /*

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


            */
            sb.append("<co:systemProperties>");
            sb.append("<co:property>");
            sb.append("<co:name>");
            Integer currKey = null;
             Object currValue = null;

                if (alarmKey.getApplicationContext() != null)
                {
                 if (alarmKey.getApplicationContext().getSystemProperties() != null)
                {
                    java.util.Map props = alarmKey.getApplicationContext().getSystemProperties();
                    for ( java.util.Iterator iter = props.keySet().iterator();iter.hasNext(); )
                    {
                        currKey = (Integer) iter.next();
                        sb.append(currKey.toString() );
                    }
                    }

                }

               sb.append("</co:name>");
               sb.append("<co:value>");
               if (alarmKey.getApplicationContext() != null)
                {
                  if (alarmKey.getApplicationContext().getSystemProperties() != null)
                {
                java.util.Map props1 = alarmKey.getApplicationContext().getSystemProperties();
                    for ( java.util.Iterator iter = props1.keySet().iterator();iter.hasNext(); )
                    {

                        currValue = props1.get( currKey );


                        sb.append(currValue.toString());

                    }
                    }

                }
               sb.append("</co:value>");
              sb.append("</co:property>");
              sb.append("</co:systemProperties>");
            /*
            sb.append("<co:systemProperties>");

	    Iterator hashMapKeys =  map.keySet().iterator();
	    Iterator hashMapValues =  map.values().iterator();
	    while(hashMapKeys.hasNext())
	    {
		sb.append("<co:property>");
		sb.append("<co:name>"+hashMapKeys.next()+"</co:name>");
	        sb.append("<co:value>"+hashMapValues.next()+"</co:value>");
		sb.append("</co:property>");
	    }
	    sb.append("</co:systemProperties>");*/
	    sb.append("</co:applicationContext>");
	    sb.append("<co:applicationDN>");
            if(alarmKey.getApplicationDN() != null)
	    sb.append(alarmKey.getApplicationDN());
	    sb.append("</co:applicationDN>");
	    sb.append("<co:type>");
            if( alarmKey.getType() != null)
	    sb.append( alarmKey.getType());
	    sb.append("</co:type>");
	    sb.append("<fm:alarmPrimaryKey>");
            if(alarmKey.getAlarmPrimaryKey() != null)
	    sb.append(alarmKey.getAlarmPrimaryKey());
	    sb.append("</fm:alarmPrimaryKey>");

	    //sb.append("</" + elementName + ">");
        }
    return  sb.toString();
    }




    public static Object fromXml(org.w3c.dom.Element element, String type  )
    //throws IllegalArgumentException
    {
          AlarmKey alkey = null;
        try
        {

             alkey = new AlarmKeyImpl();
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

                 /*alarmKey = new AlarmKeyImpl();
		org.jdom.input.DOMBuilder builder= new DOMBuilder();
                org.jdom.Element tempElement = builder.build(element);
                fromXmlNoRoot(tempElement, alarmKey);*/


        }
        catch( Exception ex )
        {
	    //VP: System.out.println(ex.getMessage());
	}
        return alkey;
    }

    public static void fromXmlNoRoot(org.jdom.Element element,AlarmKey alarmKey)
    //throws org.xml.sax.SAXException
    {
	java.util.List children = element.getChildren();
	Iterator it = children.iterator();
	org.jdom.Element child = null;
	String elementName = null;
	String elementValue = null;
	org.jdom.Element tempElement = null;

	while(it.hasNext())
	{
	      tempElement = (org.jdom.Element)it.next();
	      elementName = element.getName();
	      elementValue = element.getTextTrim();


	      if(elementName.equalsIgnoreCase("co:applicationContext"))
	      {
		    org.jdom.Element localElement = null;
		    List appContextChildren = localElement.getChildren();
		    Iterator itApp = appContextChildren.iterator();
		    String name = null;
		    String value = null;
		    ApplicationContext appContext = alarmKey.getApplicationContext();
		    while(itApp.hasNext())
		    {
			localElement = (org.jdom.Element)itApp.next();
			name = localElement.getName();
			value = localElement.getTextTrim();
			if(name.equalsIgnoreCase("co:factoryClass"))
			{
			    appContext.setFactoryClass(value);
			}
			else if(name.equalsIgnoreCase("co:url"))
			{
			    appContext.setURL(value);
			}
			else if(name.equalsIgnoreCase("co:systemProperties"))
			{
			    Iterator itTemp = localElement.getChildren().iterator();
			    org.jdom.Element sysElement = null;
			    HashMap hashMap = new HashMap();
			    while(itTemp.hasNext())
			    {
				sysElement = (org.jdom.Element)itTemp.next();
				name = sysElement.getName();
				value = sysElement.getTextTrim();

				if(name.equalsIgnoreCase("name"))
				{
				    hashMap.put(name,value);
				}
			    }//end while for System Properties element
			appContext.setSystemProperties(hashMap);
			}//end else if

		    }//end while
	      }//end if application context
	      else if(elementName.equalsIgnoreCase("co:applicationDN"))
	      {
	            alarmKey.setApplicationDN(elementValue);
	      }
	      else if(elementName.equalsIgnoreCase("co:type"))
	      {
	            alarmKey.setType(elementValue);
	      }
	      else if(elementName.equalsIgnoreCase("fm:alarmPrimaryKey"))
	      {
	            alarmKey.setAlarmPrimaryKey(elementValue);
	      }
	 }//end while that brings up the top level elements in the hierarchy
    }

}//end class



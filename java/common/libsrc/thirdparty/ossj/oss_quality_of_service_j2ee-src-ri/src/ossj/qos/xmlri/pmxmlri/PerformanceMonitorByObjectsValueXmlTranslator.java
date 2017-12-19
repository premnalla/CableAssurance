package ossj.qos.xmlri.pmxmlri;

/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc., Ericsson AB.
 * @author Vijay Sharma
 * @author Hooman Tahamtani
 * @version 1.0
 *
 * Fixed this class, it was not implemented correctly and according to OSS/J.
 * 2002-06-14, Hooman Tahamtnani, Ericsson AB., Gothenburg, Sweden.
 */

import javax.oss.pm.measurement.*;
import javax.oss.pm.util.*;
import java.text.*;
import java.util.*;
import org.jdom.*;
import org.jdom.output.*;
import org.jdom.input.*;
import ossj.qos.pm.measurement.PerformanceMonitorByObjectsValueImpl;

public class PerformanceMonitorByObjectsValueXmlTranslator
{

  public PerformanceMonitorByObjectsValueXmlTranslator()
  {
  }

  /**
<measurement:PerformanceMonitorByObjectsValue>
	<measurement:PerformanceMonitorValue>
		<lastUpdateVersionNumber>long Value</lastUpdateVersionNumber>
		<name></name>
		<granularityPeriod>int</granularityPeriod>
		<reportByFile>FILE_SINGLE</reportByFile>
		<reportByEvent>EVENT_SINGLE</reportByEvent>
		<reportPeriod>int</reportPeriod>
		<reportFormat>
			<owner></owner>
			<specification></specification>
			<technology></technology>
			<version></version>
			<measurement:baseReportFormatTypes>ASCII</measurement:baseReportFormatTypes>
		</reportFormat>
		<schedule>
			<startTime></startTime>
			<stopTime></stopTime>
			<weeklySchedule>
				<timeZone></timeZone>
				<monday>boolean</monday>
				<tuesday>boolean</tuesday>
				<wednesday>boolean</wednesday>
				<thursday>boolean</thursday>
				<friday>boolean</friday>
				<saturday>boolean</saturday>
				<sunday>boolean</sunday>
			</weeklySchedule>
			<dailySchedule>
				<startTimes>dateTime</startTimes>
				<stopTimes>dateTime</stopTimes>
			</dailySchedule>
		</schedule>
		<measurement:basePerformanceMonitorState>ACTIVE_ON_DUTY</measurement:basePerformanceMonitorState>
		<performanceMonitorKey>
			<applicationContext>
				<factoryClass></factoryClass>
				<url>anyURI</url>
				<systemProperties>
					<property>
						<name></name>
						<value></value>
					</property>
				</systemProperties>
			</applicationContext>
			<applicationDN></applicationDN>
			<type></type>
			<performanceMonitorPrimaryKey></performanceMonitorPrimaryKey>
		</performanceMonitorKey>
	</measurement:PerformanceMonitorValue>
	<measurementAttributes>
		<Item>
			<name></name>
			<threshold:baseAttributeTypes>REAL</threshold:baseAttributeTypes>
			<threshold:baseCollectionMethods>DISCRETE_EVENT_REGISTRATION</threshold:baseCollectionMethods>
			<isArray>false</isArray>
		</Item>
	</measurementAttributes>
	<observedObjects>
		<Item></Item>
		<Item></Item>
	</observedObjects>
</measurement:PerformanceMonitorByObjectsValue>

   */
//2002-06-14, re-wrote this, this was not correct and needed a re-write
  public static String toXml(PerformanceMonitorByObjectsValue object, String elementName)
  throws org.xml.sax.SAXException
    {
      String name = null;
      String granPeriod = null;
      String reportByFile = null;
      String reportByEvent = null;
      String reportPeriod = null;
      Schedule schedule = null;
      String state = "";
      ReportFormat reportFormat = null;
      PerformanceMonitorKey performKey = null;

//Hooman
//VP
//System.out.println("******************************\n\n");
//System.out.println(" In PM by Objects to xml, elment name is : " + elementName);
//System.out.println(" In PM by Objects to xml, object class name is : " + object.getClass().getName());
//System.out.println("******************************\n\n");

        StringBuffer sb = new StringBuffer();
        if (object == null)
        {
            //sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
            sb.append("<measurement:pmValue>" + " xsi:nil=\"true\">" + "</measurement:pmValue>");
        }
        else
        {
            PerformanceMonitorByObjectsValue pmbov = null;
            if(object instanceof PerformanceMonitorByObjectsValue){
               pmbov = (PerformanceMonitorByObjectsValue) object;
            }
            //String lastVersion = String.valueOf(object.getLastUpdateVersionNumber());
            String lastVersion = String.valueOf(pmbov.getLastUpdateVersionNumber());
	    if(pmbov.isPopulated(pmbov.NAME)){
              name = ">" + pmbov.getName();
            }
            else if(!pmbov.isPopulated(pmbov.NAME)){
              name = " xsi:nil=\"true\">";
            }
	    if(pmbov.isPopulated(pmbov.GRANULARITY_PERIOD)){
              granPeriod = ">" + String.valueOf(pmbov.getGranularityPeriod());
            }
            else if(!pmbov.isPopulated(pmbov.GRANULARITY_PERIOD)){
              granPeriod = " xsi:nil=\"true\">";
            }
	    if(pmbov.isPopulated(pmbov.REPORT_BY_FILE)){
              reportByFile =  ">" + String.valueOf(pmbov.getReportByFile());
            }
            else if(!pmbov.isPopulated(pmbov.REPORT_BY_FILE)){
              reportByFile = " xsi:nil=\"true\">";
            }
	    if(pmbov.isPopulated(pmbov.REPORT_BY_EVENT)){
              reportByEvent = ">" + String.valueOf(pmbov.getReportByEvent());
            }
            else if(!pmbov.isPopulated(pmbov.REPORT_BY_EVENT)){
              reportByEvent = " xsi:nil=\"true\">";
            }
	    if(pmbov.isPopulated(pmbov.REPORTING_PERIOD)){
              reportPeriod = ">" + String.valueOf(pmbov.getReportPeriod());
            }
            else if(!pmbov.isPopulated(pmbov.REPORTING_PERIOD)){
              reportPeriod = " xsi:nil=\"true\">";
            }
	    if(pmbov.isPopulated(pmbov.REPORT_FORMAT)){
              reportFormat = pmbov.getReportFormat();
            }
	    String reportFormatXml = ReportFormatXmlTranslator.toXml(reportFormat,"reportFormat");

	    if(pmbov.isPopulated(pmbov.SCHEDULE)){
              schedule = pmbov.getSchedule();
            }
	    String scheduleXml = ScheduleXmlTranslator.toXml(schedule,"schedule");

	    if(pmbov.isPopulated(pmbov.KEY)){
              performKey = pmbov.getPerformanceMonitorKey();
            }
	    String performKeyXml = PerformanceMonitorKeyXmlTranslator.toXml(performKey,"performanceMonitorKey");
	    if(pmbov.isPopulated(pmbov.STATE)){
              state = String.valueOf(pmbov.getState());
            }

            sb.append("<measurement:pmValue xsi:type=\"measurement:PerformanceMonitorByObjectsValue\">");
	    sb.append("<co:lastUpdateVersionNumber>"+lastVersion+"</co:lastUpdateVersionNumber>");
	    sb.append("<measurement:name"+name+"</measurement:name>");
	    sb.append("<measurement:granularityPeriod"+granPeriod+"</measurement:granularityPeriod>");
	    sb.append("<measurement:reportByFile"+reportByFile+"</measurement:reportByFile>");
	    sb.append("<measurement:reportByEvent"+reportByEvent+"</measurement:reportByEvent>");
	    sb.append("<measurement:reportPeriod"+reportPeriod+"</measurement:reportPeriod>");

	    sb.append("<measurement:measurementAttributes>");
            if(pmbov.isPopulated(pmbov.MEASUREMENT_ATTRIBUTES)){
              PerformanceAttributeDescriptor[] measureAttribs = pmbov.getMeasurementAttributes();
              for(int i=0;i<measureAttribs.length;i++)
              {
                  sb.append("<measurement:Item>");
                  sb.append(PerformanceAttributeDescriptorXmlTranslator.toXml(measureAttribs[i],"Item"));
                  sb.append("</measurement:Item>");
              }
            } else {
              sb.append("<measurement:Item>");
              sb.append(PerformanceAttributeDescriptorXmlTranslator.toXml(null,"Item"));
              sb.append("</measurement:Item>");
            }
            sb.append("</measurement:measurementAttributes>");
	    if(pmbov.isPopulated(pmbov.OBSERVED_OBJECTS)){
              sb.append("<measurement:observedObjects>");
              String[] observedObjects = pmbov.getObservedObjects();
              for(int j=0;j<observedObjects.length;j++)
              {
                  sb.append("<co:item>");
                  sb.append(observedObjects[j]);
                  sb.append("</co:item>");
              }
            }
            else{
              sb.append("<measurement:observedObjects xsi:nil=\"true\">");
            }
            sb.append("</measurement:observedObjects>");
	    sb.append("</measurement:pmValue>");

	}


//VP
//System.out.println("\n******************************\n\n");
//System.out.println("out of PM by Objects to xml, response is : \n\n" + sb.toString());
//System.out.println("\n******************************\n\n");


    return sb.toString();
    }

    //2002-06-14, re-wrote this, this was not correct and needed a re-write
    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {
	try
	{
            if( type.equalsIgnoreCase(PerformanceMonitorByObjectsValue.class.getName() ))
	    {
		PerformanceMonitorByObjectsValue performByObjects = new PerformanceMonitorByObjectsValueImpl();
		org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
		org.jdom.Element jdomElement = builder.build(element);
		fromXmlNoRoot(jdomElement, performByObjects);
		return performByObjects;
	    }
	}
	catch( org.xml.sax.SAXException ex )
	{
	    ex.printStackTrace();
            return new IllegalArgumentException( ex.getMessage() );
	}
	catch(ParseException pEx)
	{
	    pEx.printStackTrace();
            return null;
	}
	catch(JDOMException jdomExc)
	{
	    jdomExc.printStackTrace();
            return null;
	}

    return null;
    }

    //2002-06-14, re-wrote this, this was not correct and needed a re-write
    public static void fromXmlNoRoot( org.jdom.Element element, PerformanceMonitorByObjectsValue object)
    throws org.xml.sax.SAXException, ParseException, JDOMException
    {
      Iterator it = element.getChildren().iterator();
      while (it.hasNext()){
        element = (org.jdom.Element) it.next();
        String elementName = element.getName();
        if(elementName.equalsIgnoreCase("lasteUpdateVersionNumber")){
          String lastV = element.getTextTrim();
          if(lastV != null && lastV.length() > 0)
            object.setLastUpdateVersionNumber(Long.parseLong(lastV));
        }
        else if (elementName.equalsIgnoreCase("granularityPeriod")){
          String granP = element.getTextTrim();
          if(granP != null && granP.length() > 0)
            object.setGranularityPeriod(Integer.parseInt(granP));
        }
        else if (elementName.equalsIgnoreCase("reportByFile")){
          String rByFile = element.getTextTrim();
          if(rByFile != null && rByFile.length()> 0)
            object.setReportByFile(Integer.parseInt(rByFile));
        }
        else if (elementName.equalsIgnoreCase("reportByEvent")){
          String rByEvent = element.getTextTrim();
          if(rByEvent != null && rByEvent.length() > 0)
            object.setReportByEvent(Integer.parseInt(rByEvent));
        }
        else if (elementName.equalsIgnoreCase("reportPeriod")){
          String rPeriod = element.getTextTrim();
          if(rPeriod != null && rPeriod.length() > 0)
            object.setReportPeriod(Integer.parseInt(rPeriod));
        }
        else if (elementName.equalsIgnoreCase("reportFormat")){
          DOMOutputter domOutputter = new DOMOutputter();
          org.w3c.dom.Element elm = domOutputter.output(element);
          ReportFormat reportFormat = (ReportFormat) ReportFormatXmlTranslator.fromXml(elm, "ReportFormat");
          object.setReportFormat(reportFormat);
        }
        else if (elementName.equalsIgnoreCase("Schedule")){
          DOMOutputter domOutputter = new DOMOutputter();
          org.w3c.dom.Element elm = domOutputter.output(element);
          Schedule schedule = (Schedule)ScheduleXmlTranslator.fromXml(elm, "Schedule");
          object.setSchedule(schedule);
        }
        else if (elementName.equalsIgnoreCase("basePerfomanceMonitorState")){
          if(element.getTextTrim().equalsIgnoreCase("ACTIVE_ON_DUTY")){
            object.setState(PerformanceMonitorState.ACTIVE_ON_DUTY);
          }
          else if (element.getTextTrim().equalsIgnoreCase("ACTIVE_OF_DUTY")){
            object.setState(PerformanceMonitorState.ACTIVE_OFF_DUTY);
          }
          else if (element.getTextTrim().equalsIgnoreCase("SUSPENDED")){
            object.setState(PerformanceMonitorState.SUSPENDED);
          }
        }
        else if (elementName.equalsIgnoreCase("PerfomanceMonitorKey")){
          DOMOutputter domOutputter = new DOMOutputter();
          org.w3c.dom.Element elm = domOutputter.output(element);
          PerformanceMonitorKey pmKey = (PerformanceMonitorKey) PerformanceMonitorKeyXmlTranslator.fromXml(elm, PerformanceMonitorKey.class.getName());
          object.setPerformanceMonitorKey(pmKey);
        }
        else if (elementName.equalsIgnoreCase("measurementAttributes")){
          org.jdom.Element el = element;
          Iterator itr = el.getChildren().iterator();
          PerformanceAttributeDescriptor[] pmad = null;
          Vector v = new Vector();
          DOMOutputter domOutputter = new DOMOutputter();
          while(itr.hasNext()){
            org.jdom.Element el2 = (org.jdom.Element) itr.next();
            org.w3c.dom.Element elm = domOutputter.output(el2);
            v.add(PerformanceAttributeDescriptorXmlTranslator.fromXml(elm, "PerformanceAttributeDescriptor"));
          }
          pmad = new PerformanceAttributeDescriptor[v.size()];
          for(int i = 0; i < v.size(); i++){
            pmad[i] = (PerformanceAttributeDescriptor) v.elementAt(i);
          }
          object.setMeasurementAttributes(pmad);
        }
        else if (elementName.equalsIgnoreCase("observedObjects")){
          org.jdom.Element el = element;
          Iterator itr = el.getChildren().iterator();
          String[] obsObjects = null;
          Vector v = new Vector();
          while(itr.hasNext()){
            org.jdom.Element el2 = (org.jdom.Element) itr.next();
            v.add(el2.getTextTrim());
          }
          obsObjects = new String[v.size()];
          for(int i = 0; i < v.size(); i++){
            obsObjects[i] = (String) v.elementAt(i);
          }
          object.setObservedObjects(obsObjects);
        }
      }//while (it.hasNext())
    }

}//end class

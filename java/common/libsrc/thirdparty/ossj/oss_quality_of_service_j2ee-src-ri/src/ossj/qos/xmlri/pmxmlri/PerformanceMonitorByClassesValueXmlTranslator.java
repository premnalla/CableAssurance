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
 * 2002-06-25, re-wrote this class, the only thing that was implemented correctly
 * was the constructor and headings on this class.
 * Hooman Tahamtani, ERicsson AB., Gothenburg, Sweden.
 */

import javax.oss.pm.measurement.*;
import javax.oss.pm.util.*;
import java.text.*;
import java.util.*;
import org.jdom.*;
import org.jdom.output.*;
import org.jdom.input.*;
import ossj.qos.pm.measurement.PerformanceMonitorByClassesValueImpl;

public class PerformanceMonitorByClassesValueXmlTranslator
{


  public PerformanceMonitorByClassesValueXmlTranslator()
  {
  }

/**
<measurement:PerformanceMonitorByClassesValue>
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
	<observedObjectClasses>
		<Item></Item>
		<Item></Item>
	</observedObjectClasses>
	<scope></scope>
</measurement:PerformanceMonitorByClassesValue>

 */

 //2002-06-25, re-wrote this, this was not correct and needed a re-write
 public static String toXml(PerformanceMonitorByClassesValue object,String elementName )
    throws org.xml.sax.SAXException
    {
      String name = "";
      String granPeriod = "";
      String reportByFile = "";
      String reportByEvent = "";
      String reportPeriod = "";
      Schedule schedule = null;
      String state = "";
      ReportFormat reportFormat = null;
      PerformanceMonitorKey performKey = null;

        StringBuffer sb = new StringBuffer();
        if (object == null)
        {
            sb.append("<measurement:pmValue>" + " xsi:nil=\"true\">" + "</measurement:pmValue>");
        }
        else
        {
            PerformanceMonitorByClassesValue pmbcv = null;
            if(object instanceof PerformanceMonitorByClassesValue){
               pmbcv = (PerformanceMonitorByClassesValue) object;
            }
            //String lastVersion = String.valueOf(object.getLastUpdateVersionNumber());
            String lastVersion = String.valueOf(pmbcv.getLastUpdateVersionNumber());
	    if(pmbcv.isPopulated(pmbcv.NAME)){
              name = ">" + pmbcv.getName();
            }
            else if(!pmbcv.isPopulated(pmbcv.NAME)){
              name = " xsi:nil=\"true\">";
            }
	    if(pmbcv.isPopulated(pmbcv.GRANULARITY_PERIOD)){
              granPeriod = ">" + String.valueOf(pmbcv.getGranularityPeriod());
            }
            else if(!pmbcv.isPopulated(pmbcv.GRANULARITY_PERIOD)){
              granPeriod = " xsi:nil=\"true\">";
            }
	    if(pmbcv.isPopulated(pmbcv.REPORT_BY_FILE)){
              reportByFile =  String.valueOf(pmbcv.getReportByFile());
            }
            else if(!pmbcv.isPopulated(pmbcv.REPORT_BY_FILE)){
              reportByFile = " xsi:nil=\"true\">";
            }
	    if(pmbcv.isPopulated(pmbcv.REPORT_BY_EVENT)){
              reportByEvent = ">" + String.valueOf(pmbcv.getReportByEvent());
            }
            else if(!pmbcv.isPopulated(pmbcv.REPORT_BY_EVENT)){
              reportByEvent = " xsi:nil=\"true\">";
            }
	    if(pmbcv.isPopulated(pmbcv.REPORTING_PERIOD)){
              reportPeriod = ">" + String.valueOf(pmbcv.getReportPeriod());
            }
            else if(!pmbcv.isPopulated(pmbcv.REPORTING_PERIOD)){
              reportPeriod = " xsi:nil=\"true\">";
            }
	    if(pmbcv.isPopulated(pmbcv.REPORT_FORMAT)){
              reportFormat = pmbcv.getReportFormat();
            }
	    String reportFormatXml = ReportFormatXmlTranslator.toXml(reportFormat,"reportFormat");

	    if(pmbcv.isPopulated(pmbcv.SCHEDULE)){
              schedule = pmbcv.getSchedule();
            }
	    String scheduleXml = ScheduleXmlTranslator.toXml(schedule,"schedule");

	    if(pmbcv.isPopulated(pmbcv.KEY)){
              performKey = pmbcv.getPerformanceMonitorKey();
            }
	    String performKeyXml = PerformanceMonitorKeyXmlTranslator.toXml(performKey,"performanceMonitorKey");
	    if(pmbcv.isPopulated(pmbcv.STATE)){
              state = String.valueOf(pmbcv.getState());
            }

            sb.append("<measurement:pmValue xsi:type=\"measurement:PerformanceMonitorByClassesValue\">");
	    sb.append("<co:lastUpdateVersionNumber>"+lastVersion+"</co:lastUpdateVersionNumber>");
	    sb.append("<measurement:name"+name+"</measurement:name>");
	    sb.append("<measurement:granularityPeriod"+granPeriod+"</measurement:granularityPeriod>");
	    sb.append("<measurement:reportByFile"+reportByFile+"</measurement:reportByFile>");
	    sb.append("<measurement:reportByEvent"+reportByEvent+"</measurement:reportByEvent>");
	    sb.append("<measurement:reportPeriod"+reportPeriod+"</measurement:reportPeriod>");

	    sb.append("<measurement:measurementAttributes>");
            if(pmbcv.isPopulated(pmbcv.MEASUREMENT_ATTRIBUTES)){
              PerformanceAttributeDescriptor[] measureAttribs = pmbcv.getMeasurementAttributes();
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
	    if(pmbcv.isPopulated(pmbcv.OBSERVABLE_OBJECT_CLASSES)){
              sb.append("<measurement:observedObjectClasses>");
              String[] observedObjects = pmbcv.getObservedObjectClasses();
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
            sb.append("</measurement:observedObjectClasses>");

            if(pmbcv.isPopulated(pmbcv.SCOPE)){
              sb.append("<measurement:scope>");
              String scope = pmbcv.getScope();
              sb.append(scope);
            }
            else{
              sb.append("<measurement:scope xsi:nil=\"true\">");
            }
            sb.append("</measurement:scope>");
	    sb.append("</measurement:pmValue>");

	}

        return  sb.toString();

    }



  /*
<measurement:PerformanceMonitorByClassesValue>
	<eventType></eventType>
	<propertyNames>arrayOfStrings</propertyNames>
	<propertyTYpes>arrayOfStrings</propertyTYpes>
</measurement:PerformanceMonitorByClassesValue>
  */


    //2002-06-25, re-wrote this, this was not correct and needed a re-write
    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {
	try
	{
            if( type.equalsIgnoreCase(PerformanceMonitorByClassesValue.class.getName() ))
	    {
		PerformanceMonitorByClassesValue performByClasses = new PerformanceMonitorByClassesValueImpl();
		org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
		org.jdom.Element jdomElement = builder.build(element);
		fromXmlNoRoot(jdomElement, performByClasses);
		return performByClasses;
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

    //2002-06-25, re-wrote this, this was not correct and needed a re-write
    public static void fromXmlNoRoot( org.jdom.Element element, PerformanceMonitorByClassesValue object)
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
        else if (elementName.equalsIgnoreCase("observedObjectClasses")){
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
          object.setObservedObjectClasses(obsObjects);
        }
        else if (elementName.equalsIgnoreCase("scope")){
          String scope = element.getTextTrim();
          if(scope != null && scope.length() > 0)
            object.setScope(scope);
        }
      }//while (it.hasNext())
    }

}
package ossj.qos.xmlri.tmxmlri;

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
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
//import ossj.qos.ri.pm.threshold.*;
import ossj.qos.pm.threshold.*;
import javax.oss.pm.threshold.*;
//import ossj.qos.ri.pm.measurement.*;
import javax.oss.pm.threshold.*;


public class QueryBySimpleThresAttributesValueXmlTranslator {

  public QueryBySimpleThresAttributesValueXmlTranslator()
  {
  }

/**
	<!-- Value type javax.oss.pm.threshold.QueryBySimpleThresAttributesValue -->
<QueryBySimpleThresAttributesValue>
	<state>sdsd</state>
	<name>sdsd</name>
	<granularityPeriod>2323</granularityPeriod>
	<valueType>sdsd</valueType>

	<observableObjects>
			<item>sdsd</item>
			<item>sdsd</item>
	</observableObjects>
	<thresholdDefinitions>
		<item>sds</item>
		<item>sdsd</item>
	</thresholdDefinitions>
	<alarmConfig>
		<fm:baseAlarmType></fm:baseAlarmType>
		<fm:basePerceivedAlarmSeverity></fm:basePerceivedAlarmSeverity>
		<specificProblem><fm:baseAlarmType></specificProblem><fm:baseAlarmType>
		<fm:baseProbableAlarmCause></fm:baseProbableAlarmCause>
	</alarmConfig>
</QueryBySimpleThresAttributesValue>

 */
public static String toXml(QueryBySimpleThresAttributesValue object, String elementName)
throws org.xml.sax.SAXException
    {
        StringBuffer sb = new StringBuffer();
        if (object == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
	    sb.append("<" + elementName + ">");

	    int state = object.getState();
	    String name = object.getName();
	    int granPeriod = object.getGranularityPeriod();
	    String valueType = object.getValueType();

	    String[] observedObjs = object.getObservableObjects();
	    ThresholdDefinition[] thresholdDefs = object.getThresholdDefinitions();
	    AlarmConfig alarmConfig = object.getAlarmConfig();

	    sb.append("<state>"+state+"</state>");
	    sb.append("<name>"+name+"</name>");
	    sb.append("<granularityPeriod>"+granPeriod+"</granularityPeriod>");
	    sb.append("<valueType>"+valueType+"</valueType>");
	    sb.append("<observableObjects>");
	    for(int i=0;i<observedObjs.length;i++)
	    {
		sb.append(observedObjs[i]);
	    }
	    sb.append("</observableObjects>");
	    sb.append("<thresholdDefinitions>");
	    for(int j=0;j<thresholdDefs.length;j++)
	    {
		sb.append(ThresholdDefinitionXmlTranslator.toXml(thresholdDefs[j],"item"));
	    }
	    sb.append("</thresholdDefinitions>");

	    String alarmConfigXml = AlarmConfigXmlTranslator.toXml(alarmConfig,"alarmConfig");
	    sb.append(alarmConfigXml);
	    sb.append("</" + elementName + ">");
	}
    return sb.toString();
    }

    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {
        try
        {
            if(type.equals(QueryBySimpleThresAttributesValue.class.getName() ))
	    {
                QueryBySimpleThresAttributesValue queryBySimple = new QueryBySimpleThresAttributesValueImpl();
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, queryBySimple);
                return queryBySimple;
             }

	}
        catch( org.xml.sax.SAXException ex )
	{
            return new IllegalArgumentException( ex.getMessage() );
        }
        catch(Exception ex)
	{
	    return null;
	}

        return null;
   }

    public static void fromXmlNoRoot(org.jdom.Element element, QueryBySimpleThresAttributesValue object)
    throws org.xml.sax.SAXException, ParseException,JDOMException
    {
	DOMOutputter domOutputter = new DOMOutputter();
	List list = element.getChildren();
	Iterator it = list.iterator();
	String elementName = null;
	String elementValue = null;
	org.jdom.Element localElement = null;
	while(it.hasNext())
	{
	    localElement = (org.jdom.Element)it.next();
	    elementName = localElement.getName();
	    elementValue = localElement.getTextTrim();
	    if(elementName.equalsIgnoreCase("state"))
	    {
		int state = Integer.parseInt(elementValue);
		object.setState(state);
	    }
	    else if(elementName.equalsIgnoreCase("name"))
	    {
		object.setName(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("granularityPeriod"))
	    {
		int granPeriod = Integer.parseInt(elementValue);
		object.setGranularityPeriod(granPeriod);
	    }
	    else if(elementName.equalsIgnoreCase("alarmConfig"))
	    {
		org.w3c.dom.Element w3cElement = domOutputter.output(localElement);
		String className = AlarmConfig.class.getName();
		AlarmConfig alarmConfig = null;
		alarmConfig = (AlarmConfig)AlarmConfigXmlTranslator.fromXml(w3cElement,className);
		object.setAlarmConfig(alarmConfig);
	    }
	    else if(elementName.equalsIgnoreCase("observableObjects"))
	    {
		org.jdom.Element localElement2 = null;
		Iterator it2 = localElement.getChildren().iterator();
		String name2 = null;
		String value2 = null;
		ArrayList arrayList = new ArrayList();
		while(it2.hasNext())
		{
		    localElement2 = (org.jdom.Element )it2.next();
		    name2 = localElement2.getName();
		    value2 = localElement2.getTextTrim();
		    if(name2.equalsIgnoreCase("item"))
		    {
			arrayList.add(value2);
		    }
		}
	    object.setObservableObjects((String[])arrayList.toArray());
	    }
	    else if(elementName.equalsIgnoreCase("thresholdDefinitions"))
	    {
		org.jdom.Element localElement2 = null;
		Iterator it2 = localElement.getChildren().iterator();
		String name2 = null;
		String value2 = null;
		ArrayList arrayList = new ArrayList();
		String className = ThresholdDefinition.class.getName();
		ThresholdDefinition thresholdDef = null;
		while(it2.hasNext())
		{
		    localElement2 = (org.jdom.Element )it2.next();
		    name2 = localElement2.getName();
		    value2 = localElement2.getTextTrim();
		    if(name2.equalsIgnoreCase("item"))
		    {
			org.w3c.dom.Element w3cElement = domOutputter.output(localElement2);
			thresholdDef = (ThresholdDefinition)ThresholdDefinitionXmlTranslator.fromXml(w3cElement,className);
			arrayList.add(thresholdDef);
		    }
		}
	    object.setThresholdDefinitions((ThresholdDefinition[])arrayList.toArray());
	    }
	    else if(elementName.equalsIgnoreCase("valueType"))
	    {
		object.setValueType(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("valueType"))
	    {
		object.setValueType(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("valueType"))
	    {
		object.setValueType(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("valueType"))
	    {
		object.setValueType(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("valueType"))
	    {
		object.setValueType(elementValue);
	    }




	}
    }

}
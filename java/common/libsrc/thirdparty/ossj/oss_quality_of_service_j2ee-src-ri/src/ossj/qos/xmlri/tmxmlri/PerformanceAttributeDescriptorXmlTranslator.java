package ossj.qos.xmlri.tmxmlri;



/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc., Ericsson AB.
 * @author       Vijay Sharma, Najam Hassan
 * @author       Hooman Tahamtani
 * @version      1.0
 *
 *
 * Fixed this class, the toXml() and fromXml(), were not implementd correctly.
 * 2002-06-17, Hooman Tahamtani, Ericsson AB., Gothenburg, Sweden.
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



import javax.oss.MultiValueList;

import javax.oss.fm.monitor.*;
import javax.oss.pm.measurement.*;
import javax.oss.pm.threshold.*;
import javax.oss.pm.util.*;
import ossj.qos.pm.measurement.*;
import ossj.qos.util.*;
import ossj.qos.*;

public class PerformanceAttributeDescriptorXmlTranslator
{


    //Java singleton design pattern - private
    static private PerformanceAttributeDescriptorXmlTranslator singleton = new PerformanceAttributeDescriptorXmlTranslator();
    public PerformanceAttributeDescriptorXmlTranslator()
    {

    }

    /**
<measurement:PerformanceAttributeDescriptor>
	<name></name>
	<threshold:baseAttributeTypes>REAL</threshold:baseAttributeTypes>
	<threshold:baseCollectionMethods>DISCRETE_EVENT_REGISTRATION</threshold:baseCollectionMethods>
	<isArray>false</isArray>
</measurement:PerformanceAttributeDescriptor>
     */
    public static String toXml(PerformanceAttributeDescriptor performanceAttrib ,String elementName )
    throws org.xml.sax.SAXException
    {
        StringBuffer sb= new StringBuffer();
        if (performanceAttrib == null)
        {
            //sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
           //added these.  2002-06-17
            sb.append("<measurement:name></measurement:name>");
            sb.append("<measurement:baseAttributeTypes></measurement:baseAttributeTypes>");
            sb.append("<measurement:baseCollectionMethods></measurement:baseCollectionMethods>");
            sb.append("<measurement:isArray>" + "true" + "</measurement:isArray>");
        }
        else
        {
            //sb.append("<" + elementName + ">\n");

	    String name = performanceAttrib.getName();
	    String colletionMethod = performanceAttrib.getCollectionMethod();
	    int type = performanceAttrib.getType();
	    boolean isArray = performanceAttrib.isArray();

	    if(name == null)
	    {
		sb.append("<measurement:name xsi:nil=\"true\"></measurement:name>");
	    }
	    else
	    {
		sb.append("<measurement:name>"+name+"</measurement:name>");
	    }

	    sb.append("<measurement:baseAttributeTypes>"+type+"</measurement:baseAttributeTypes>");

	    if(colletionMethod == null)
	    {
		sb.append("<measurement:baseCollectionMethods xsi:nil=\"true\"></measurement:baseCollectionMethods>");
	    }
	    else
	    {
		sb.append("<measurement:baseCollectionMethods>"+colletionMethod+"</measurement:baseCollectionMethods>");
	    }
	    sb.append("<measurement:isArray>"+isArray+"</measurement:isArray>");
	    //end the outermost element
	    //sb.append("</" + elementName + ">\n");
        }
        return  sb.toString();
    }

    public static Object fromXml( org.w3c.dom.Element element, String type  )
    throws IllegalArgumentException
    {
        try
	{
            //if( type.equals(AlarmCountsValue.class.getName() ))
	    if( type.equals("PerformanceAttributeDescriptor"))
            {
                PerformanceAttributeDescriptor performAttrib = new PerformanceAttributeDescriptorImpl();
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, performAttrib);
                return performAttrib;
                }

        }
        catch( org.xml.sax.SAXException ex )
	{
            return new IllegalArgumentException( ex.getMessage() );
        }
        return null;


    }

    public static void fromXmlNoRoot( org.jdom.Element element,PerformanceAttributeDescriptor performAttrib)
    throws org.xml.sax.SAXException
    {
	java.util.List children = element.getChildren();
	Iterator it = children.iterator();
	org.jdom.Element child = null;
	String childName = null;
	String childValue = null;
	while(it.hasNext())
	{
	    child = (org.jdom.Element)it.next();
	    childName = child.getName();
	    childValue = child.getTextTrim();
	    if(childName.equalsIgnoreCase("name"))
	    {
		performAttrib.setName(childValue);
	    }
	    else if(childName.equalsIgnoreCase("threshold:baseAttributeTypes"))
	    {
		int type = Integer.parseInt(childValue);
		performAttrib.setType(type);
	    }
	    else if(childName.equalsIgnoreCase("threshold:baseCollectionMethods"))
	    {
	        performAttrib.setCollectionMethod(childValue);
	    }
	    else if(childName.equalsIgnoreCase("isArray"))
	    {
		boolean isArray = (new Boolean(childValue)).booleanValue();
		performAttrib.setIsArray(isArray);
	    }
        }//end while
    }
}//end class


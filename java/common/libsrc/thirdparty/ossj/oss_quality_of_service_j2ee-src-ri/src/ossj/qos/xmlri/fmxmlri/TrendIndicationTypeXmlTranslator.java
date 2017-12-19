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
import java.lang.StringBuffer;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.oss.ManagedEntityValue;



import javax.oss.MultiValueList;

import javax.oss.fm.monitor.*;
import ossj.qos.fmri.*;

public class TrendIndicationTypeXmlTranslator
{


    //Java singleton design pattern - private
    static private TrendIndicationTypeXmlTranslator singleton = new TrendIndicationTypeXmlTranslator();


    private static HashMap schemaToAttMap;
    private static HashMap monthHash;
   // private static InterfaceReflector ifReflector = new InterfaceReflector();


    //private HashMap desiredAttributes;   //leave in ttv for now
    //transient private InterfaceReflector ifReflector;


    //protected ctor - singleton pattern
    protected TrendIndicationTypeXmlTranslator()
    {

    }

    public static String toXml(AttributeValue attval,String elementName )
    throws org.xml.sax.SAXException
    {
     //VP: System.out.println( "---NEWTEST---");
        StringBuffer sb= new StringBuffer();
        if (attval == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
        sb.append("<" + elementName + ">\n");

         if(attval.getAttributeName()== null)
           sb.append("<fm:attributeName xsi:nil=\"true\"></fm:attributeName>\n");
           else
           sb.append("<fm:attributeName>" + attval.getAttributeName() + "</fm:attributeName>\n");
        if(attval.getAttributeType()== null)
           sb.append("<fm:attributeType xsi:nil=\"true\"></fm:attributeType>\n");
           else
           sb.append("<fm:attributeType>" + attval.getAttributeType() + "</fm:attributeType>\n");
        if(attval.getValue()== null)
           sb.append("<fm:value xsi:nil=\"true\"></fm:value>\n");
           else
           sb.append("<fm:value>" + attval.getValue() + "</fm:value>\n");


        }


        return  sb.toString();
    }




    public static Object fromXml( Element element, String type  )
    throws IllegalArgumentException {
    try {
            if( type.equals(AttributeValue.class.getName() )) {
                AttributeValue alval = new AttributeValueImpl();
                fromXmlNoRoot(element, alval);
                return alval;
                }

        }
        catch( org.xml.sax.SAXException ex ) {
            return new IllegalArgumentException( ex.getMessage() );
        }
        return null;


    }

    public static void fromXmlNoRoot(Node node,AttributeValue aval)
    throws org.xml.sax.SAXException
    {
     NodeList nodeList1 = node.getChildNodes();
        Node node2;
        //VP: System.out.println("fromXmlNoRoot--> translated attributes:");
        //VP: System.out.println("fromXmlNoRoot--> length:" + nodeList1.getLength());
        for (int i=0; i<nodeList1.getLength(); i++)
        {
            node2 = nodeList1.item(i);
            if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
            String nodeName1 = node2.getNodeName();
            //VP: System.out.println( "Node Name = " + nodeName1 );
            if ( nodeName1.equals("fm:attributeName") )
            if (node2.hasChildNodes())
            aval.setAttributeName(node2.getFirstChild().getNodeValue());
            else if ( nodeName1.equals("fm:attributeType") )
            {
                if (node2.hasChildNodes())
                aval.setAttributeType(node2.getFirstChild().getNodeValue());
            }
            else if ( nodeName1.equals("fm:Value") )
            {
                if (node2.hasChildNodes())
                aval.setValue(node2.getFirstChild().getNodeValue());
            }


    }

}

}

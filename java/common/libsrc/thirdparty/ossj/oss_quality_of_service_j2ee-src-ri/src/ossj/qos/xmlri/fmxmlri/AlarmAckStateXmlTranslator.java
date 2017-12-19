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
import javax.oss.*;
import javax.oss.fm.monitor.*;
import javax.oss.fm.*;

import ossj.qos.fmri.*;

public class AlarmAckStateXmlTranslator
{
    public AlarmAckStateXmlTranslator()
    {

    }

    public static String toXml(AlarmAckState alrackState,String elementName )
    throws org.xml.sax.SAXException
    {
     //VP: System.out.println( "---NEWTEST---");
        StringBuffer sb= new StringBuffer();
        if (alrackState == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
        sb.append("<" + elementName + ">\n");

         if(alrackState.ACKNOWLEDGED==1)
           sb.append("ACKNOWLEDGED");
           else
           sb.append("UNACKNOWLEDGED");
        sb.append("</" + elementName + ">\n");


        }


        return  sb.toString();
    }




    public static Object fromXml(AlarmAckState alarmAckState, String type  )
    throws java.lang.IllegalArgumentException {
    try {
            if( type.equals(AlarmAckState.class.getName() )) {
                //AlarmAckState alval = new AlarmAckState();
                //fromXmlNoRoot(element, alval);
                //return alval;
                }

        }
        catch(Exception ex ) {
            return new java.lang.IllegalArgumentException( ex.getMessage() );
        }
        return null;


    }

    public static void fromXmlNoRoot(Node node,AttributeValue aval)
    throws org.xml.sax.SAXException
    {
    /*
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
*/
}

}



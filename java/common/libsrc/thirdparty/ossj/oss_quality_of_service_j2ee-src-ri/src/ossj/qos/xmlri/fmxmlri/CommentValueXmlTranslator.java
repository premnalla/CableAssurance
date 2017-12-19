package ossj.qos.xmlri.fmxmlri;



/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc.
 * @author Vijay Sharma
 * @version 1.0
 */

import java.util.Date;
import java.util.Vector;
import java.util.HashMap;
import java.util.Iterator;
import java.text.DateFormat;
import java.text.ParseException;
import java.lang.StringBuffer;
import java.util.StringTokenizer;
import javax.oss.ManagedEntityValue;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.jdom.input.*;



import javax.oss.MultiValueList;

import javax.oss.fm.monitor.*;
import ossj.qos.fmri.*;

public class CommentValueXmlTranslator
{
   public CommentValueXmlTranslator()
    {

    }

    /**
     *  <fm:CommentValue >
     *      <commentUserId>fdgdf</commentUserId>
     *      <commentTime>354534</commentTime>
     *      <commentText>dfgdsfg</commentText>		
     *      <commentSystemId>dfgdsfgd</commentSystemId>	
     *  </fm:CommentValue >
     */
    public static String toXml(CommentValue commentValue,String elementName )
    throws org.xml.sax.SAXException
    {
    StringBuffer sb = new StringBuffer();
        if (commentValue == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
            String userId = commentValue.getCommentUserId();
	    String time = commentValue.getCommentTime().toString();
	    String text = commentValue.getCommentText();
	    String systemId = commentValue.getCommentSystemId();
	    
	    if(userId == null)
		sb.append("<commentUserId xsi:nil=\"true\"></commentUserId>\n");
            else
		sb.append("<commentUserId>" + userId + "</commentUserId>\n");
	    
	    if(time == null)
		sb.append("<commentTime xsi:nil=\"true\"></commentTime>\n");
            else
		sb.append("<commentTime>" + time + "</commentTime>\n");
	    
	    if(text == null)
		sb.append("<commentText xsi:nil=\"true\"></commentText>\n");
            else
		sb.append("<commentText>" + text + "</commentText>\n");
	    
	    if(systemId == null)
		sb.append("<commentSystemId xsi:nil=\"true\"></commentSystemId>\n");
            else
		sb.append("<commentSystemId>" + systemId + "</commentSystemId>\n");

	    sb.append("</" + elementName + ">");	
        }
    return  sb.toString();	
    }




    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException 
    {
    try 
    {
            if( type.equals(CommentValue.class.getName() )) 
	    {
                CommentValue commentValue = new CommentValueImpl();
		org.jdom.input.DOMBuilder builder= new DOMBuilder();
                org.jdom.Element tempElement = builder.build(element);		
                fromXmlNoRoot(tempElement, commentValue);
                return commentValue;
            }

        }
        catch( org.xml.sax.SAXException ex ) 
	{
            return new IllegalArgumentException( ex.getMessage() );
        }
	catch(ParseException pe)
	{
	    //do something
	    //log it 
	}
        return null;


    }

    public static void fromXmlNoRoot(org.jdom.Element element , CommentValue commentValue)
    throws org.xml.sax.SAXException,ParseException
    {
	java.util.List children = element.getChildren();
	Iterator it = children.iterator();
	org.jdom.Element child = null;
	String elementName = null;
	String elementValue = null;
	while(it.hasNext())
	{
	    child = (org.jdom.Element)it.next();
	    elementName = child.getName();
	    elementValue = child.getTextTrim();
	    if(elementName.equalsIgnoreCase("commentUserId"))
	    {
		commentValue.setCommentUserId(elementValue);
	    }
	    else if (elementName.equalsIgnoreCase("commentTime"))
	    {
		DateFormat df = DateFormat.getDateInstance();
		java.util.Date date =  df.parse(elementValue);
		commentValue.setCommentTime(date);
	    }
	    else if(elementName.equalsIgnoreCase("commentText"))
	    {
		commentValue.setCommentText(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("commentSystemId"))
	    {
		commentValue.setCommentSystemId(elementValue);
	    }
	    
	}//end while	
    }
}//end class


package com.sun.oss.common.ri;

import javax.oss.XmlSerializer;
import javax.oss.XmlSerializerEncodingStyles;

import com.nokia.oss.ossj.common.ri.* ;


/*
 * @(#)file      XmlSerializerImpl.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.9
 * @(#)date      02/03/15
 */

/**
 * XmlSerializer Implementation Class
 * 
 * @author Copyright 2001-2002 Nortel Networks Limited. All rights reserved. 
 * @version 1.0
 * @since December 2001
 */

public class XmlSerializerImpl implements javax.oss.XmlSerializer
{
    private String originalType = null;
    private String commonType = null;
    private String encodingStyle = null;
    
     /**
     * Get all the encoding styles supported by this Serializer.
     * This may return an empty array, in case no serializer is implemented
     * 
     * @return An Array of EncodingStyle strings.
     */
     
    public XmlSerializerImpl( String type ) {

		originalType = type ;
		Class currentClass =  null; 
	    Class[] interfaces = null ;

        // ================
        // Get and parse interfaces list for the current class or its super class
        // ================
		try {
			currentClass =  Class.forName(type) ;
	    } catch (java.lang.ClassNotFoundException e) {
			return ;
	    }

		while ( currentClass != null ) {
	        interfaces = currentClass.getInterfaces() ;
	        
	        int size = interfaces.length - 1;
	        int i =  -1 ;
	        while ( i < size ) {
	            i++ ;
				if ( ! ( interfaces[i].isInterface() ) ) {
					continue ;
				}
	
	            String interfaceName = interfaces[i].getName() ;
	            if (    ( interfaceName.equals("javax.oss.Event") )
	                    ||
	                    ( interfaceName.equals("javax.oss.ManagedEntityValue") )
	                    ||
	                    ( interfaceName.equals("javax.oss.ManagedEntityKey") )
	                    ||
	                    ( interfaceName.equals("javax.oss.QueryValue") )
	               )
	            {
	                this.commonType = interfaceName ;
	                break ;
	            } 
	        }
			if ( this.commonType != null ) {
				break ;
			} else {
				currentClass = currentClass.getSuperclass() ;
			}
		}
    }
    
   public  String[] getSupportedEncodingStyles() {
        
        String[] supportedEncodingStyles = new String[1];
        supportedEncodingStyles[0] = 
        new String( XmlSerializerEncodingStyles.OSS_XML_ENCODING_STYLE );
        return supportedEncodingStyles;
    }
    
    /**
     * Returns the default encoding that is the encoding style
     * used by the Serializer when it is created. To set the
     * encoding style to another value use <CODE>setEncodingStyle()</CODE>
     * 
     * @return The default encoding style.
     */
    
    public String getDefaultEncodingStyle() {
        
       return XmlSerializerEncodingStyles.OSS_XML_ENCODING_STYLE;
        
    }
    
    
    /**
     * Set the encoding style associated with this Serializer. 
     * <p>
     * For example "http://java.sun.com/products/oss/xml/encoding/ossxml" for the OSSJ
     * XML Encoding Style defined in the OSSJ Design Guidelines.
     * <p>
     * One of the items returned by <CODE>getSupportedEncodingStyles()</CODE>
     * should be provided as parameter.
     * 
     * @param encodingStyle The encodingStyle string.
     * @exception javax.oss.IllegalArgumentException if the provided
     * encoding style is not valid or is not recognized by the Serializer.
     */
    
    public void setEncodingStyle(String encodingStyle)
    throws java.lang.IllegalArgumentException
    {
        if(  ! encodingStyle.equals( XmlSerializerEncodingStyles.OSS_XML_ENCODING_STYLE))
        throw new IllegalArgumentException("Invalid Encoding Style");
        this.encodingStyle = encodingStyle;
        
    }
    
    
    
    /**
     * The encoding style associated with this Serializer.
     * <p>
     * For example "http://java.sun.com/products/oss/xml/encoding/ossxml" for the OSSJ
     * XML Encoding Style defined in the OSSJ Design Guidelines.
     * <p>
     * A Serializer is always created with a default encoding style.
     * <p>
     * The default encoding style is provided by the value of the 
     * <CODE>OSS_DEFAULT_ENCODING_STYLE</CODE> found in the declaration of the
     * specific serializer type <CODE>EncodingStyles</CODE> interface.
     * 
     * @return The encoding style associated with this Serializer.
     */
   
    public String getEncodingStyle() { 
        return XmlSerializerEncodingStyles.OSS_XML_ENCODING_STYLE;
    }
   
    
    /**
     * Returns the type of object that this Serializer can
     * marshall and unmarshall from and to XML, for example
     * <CODE>javax.oss.TroubleTicketValue</CODE>
     * 
     * @return The type of object that this object can serialize
     */
    public String getType() { //return TroubleTicketValue.getClass().getName();
       return originalType;
    }
    
    /** 
     * Serialize this Java object as an XML element.  No doctype declaration is generated, 
     * in order to allow this String to be embedded as an element of a larger XML 
     * document instance. 
     * <p>
     *
     * For example assuming that this a Serializer for a &lt;ManagedEntity&gt;Key 
     * then calling <CODE>toXML( "myElementName")</CODE> would result in the following: 
     * <pre>
     * < myElementName > 
     *          < co:applicationContext >
     *                  < co:factoryClass >String&lt;/co:factoryClass > 
     *                  < co:url >http://www.xmlspy.com< /co:url > 
     *                  < co:systemProperties > 
     *                          < co:property >
     *                                  < co:name >String< /co:name > 
     *                                  < co:value >String< /co:value >
     *                          < /co:property >
     *                  < /co:systemProperties >
     *          < /co:applicationContext >
     *          < co:applicationDN >String< /co:applicationDN > 
     *          < co:type >String< /co:type >
     *          < primaryKey >String< /primaryKey >
     *  < /myElementName >
     * </pre>
     * 
     * 
     * @param elementName String the name of the element for this value 
     * @param value 
     * @return String XML element 
     * @exception javax.oss.IllegalArgumentException if the value is not of the proper type 
     */ 
    public String toXml( Object object, String elementName) 
    throws java.lang.IllegalArgumentException {
        
     String doc = null;
        doc = XmlTranslator.toXml( object, elementName );
     return doc;
    }
    
    
    
    /** 
     * Deserialize the XML DOM element subtree into the Java object. 
     * For example assuming that this a Serializer for a <CODE>< ManagedEntity >Key</CODE> 
     * and that we have a DOM element representing the following 
     * <pre>
     * < key > 
     *          < co:applicationContext > 
     *                  < co:factoryClass >String< /co:factoryClass > 
     *                  < co:url >String< /co:url > 
     *                  < co:systemProperties > 
     *                          < co:property > 
     *                                  < co:name >String< /co:name > 
     *                                  < co:value >String< /co:value > 
     *                          < /co:property > 
     *                  < /co:systemProperties > 
     *          < /co:applicationContext > 
     *          < co:applicationDN >String< /co:applicationDN > 
     *          < co:type >String< /co:type > 
     *          < primaryKey >String< /primaryKey > 
     *  < /key > 
     * </pre>
     * then the returned object would be a < ManagedEntity >Key key populated 
     * with the proper <CODE>ApplicationContext</CODE>, <CODE>applicationDN</CODE>, <CODE>primaryKey</CODE> 
     * and <CODE>type</CODE> info 
     * @param element <CODE>org.w3c.dom.Element</CODE> the subtree representing the serialized object 
     * @exception javax.oss.IllegalArgumentException if an invalid dom element is passed 
     */ 
    public Object fromXml( org.w3c.dom.Element element ) 
    throws java.lang.IllegalArgumentException {
        Object o = null;
        o = XmlTranslator.fromXml( element, originalType, commonType );
        
        return o;
    }
}

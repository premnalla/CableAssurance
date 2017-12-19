package javax.oss;



/**
 * XML Serializer for marshalling and unmarshalling a Java object to
 * and from XML.
 * <p>
 * For example and assuming that <CODE>mev</CODE>  is a managed entity value:
 * <pre>
 *   Serializer serializer = mev.makeSerializer( XmlSerializer.getClass().getName());
 *   XmlSerializer mevXmlSerializer = (XmlSerializer) serializer;
 *   //If you want to use another encoding style than the default
 *   mevXmlSerializer.setEncodingStyle( &lt;API&gt;XmlSerializerEncodingStyles.SomeEncodingStyle);
 *   String xmlDoc = mevXmlSerializer.toXml( mev , "Value" );
 *   mev = (ManagedEntityValue) xmlSerializer.fromXml( xmlDoc );
 * </pre>
 *
 * @see Serializer
 * @see SerializerFactory
 */


public interface XmlSerializer extends javax.oss.Serializer
{


    /**
     * Serialize this Java object as an XML element.  No doctype declaration is generated,
     * in order to allow this String to be embedded as an element of a larger XML
     * document instance.
     * <p>
     *
     * For example assuming that this is a Serializer for a &lt;ManagedEntity&gt;Key
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
     * @param object the value to be marshalled into XML.
     * @param elementName the name of the XML element.
     * @return the XML representation of the object.
     * @exception java.lang.IllegalArgumentException if the object is not of the proper type.
     */
    public String toXml( Object object, String elementName)
    throws java.lang.IllegalArgumentException;

    /**
     * Deserialize the XML DOM element subtree into the Java object.
     * For example assume that this a Serializer for a <CODE>< ManagedEntity >Key</CODE>
     * and that we have a DOM element representing the following:
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
     * Then the returned object would be a < ManagedEntity >Key key populated
     * with the proper <CODE>ApplicationContext</CODE>, <CODE>applicationDN</CODE>, <CODE>primaryKey</CODE>
     * and <CODE>type</CODE> info.
     * @param element the <CODE>org.w3c.dom.Element</CODE> subtree representing the serialized object.
     * @exception java.lang.IllegalArgumentException if an invalid dom element is passed.
     * @return a java object corresponding to the XML document.
     */
    public Object fromXml( org.w3c.dom.Element element )
    throws java.lang.IllegalArgumentException;

}



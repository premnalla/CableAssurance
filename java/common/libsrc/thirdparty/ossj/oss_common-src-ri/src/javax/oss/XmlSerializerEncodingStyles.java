package javax.oss;

/**
 * A base EncodingStyles enumeration describing the DEFAULT and
 * supported encoding styles associated with the XmlSerializer.
 * 
 *
 * @see javax.oss.XmlSerializer
 */
public interface XmlSerializerEncodingStyles
{
    
    /**
     * Define the OSS_XML Encoding Style. Its value is "http://java.sun.com/products/oss/xml".
     */
    public static final String OSS_XML_ENCODING_STYLE = "http://java.sun.com/products/oss/xml";
    
    /**
     * Define the Default Encoding Style for the XMLSerializer type. Its value is the value of string javax.oss.XmlSerializerEncodingStyles.OSS_XML_ENCODING_STYLE.
     */
    public static final String OSS_DEFAULT_ENCODING_STYLE = OSS_XML_ENCODING_STYLE;
    
}


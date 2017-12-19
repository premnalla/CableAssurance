package javax.oss;

/**
 * Base interface for all Serializers.
 * It defines generic methods for marshalling and unmarshalling a Java object.
 *
 * @see XmlSerializer
 * @see SerializerFactory
 */
public interface Serializer extends java.io.Serializable
{

    /**
     * Get all the encoding styles supported by this Serializer.
     * This may return an empty array, in case no serializer is implemented.
     *
     * @return an array of EncodingStyle strings.
     */

    public String[] getSupportedEncodingStyles();

    /**
     * Returns the default encoding that is the encoding style
     * used by the Serializer when it is created. To set the
     * encoding style to another value use <CODE>setEncodingStyle()</CODE>
     *
     * @return the default encoding style.
     */

    public String getDefaultEncodingStyle();


    /**
     * Set the encoding style associated with this Serializer.
     * <p>
     * For example "http://java.sun.com/products/oss/xml" for the OSSJ
     * XML Encoding Style defined in the OSSJ Design Guidelines.
     * <p>
     * One of the items returned by <CODE>getSupportedEncodingStyles()</CODE>
     * should be provided as parameter.
     *
     * @param encodingStyle the encodingStyle string.
     * @exception java.lang.IllegalArgumentException if the provided
     * encoding style is not valid or is not recognized by the Serializer.
     */

    public void setEncodingStyle(String encodingStyle)
    throws java.lang.IllegalArgumentException;


    /**
     * Get the encoding style associated with this Serializer.
     * <p>
     * A Serializer is always created with a default encoding style.
     * <p>
     * The default encoding style is provided by the value of the
     * <CODE>OSS_DEFAULT_ENCODING_STYLE</CODE> found in the declaration of the
     * specific serializer type <CODE>&lt;Serializer Type&gt;EncodingStyles</CODE> interface.
     *
     * @return the encoding style associated with this Serializer.
     */

    public String getEncodingStyle();


    /**
     * Return the type of object that this Serializer can
     * marshall and unmarshall from and to XML, for example
     * <CODE>javax.oss.TroubleTicketValue</CODE>
     *
     * @return the type of object that this object can serialize.
     */
    public String getType();

}


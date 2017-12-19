package javax.oss;

/**
 * A Factory for the creation of different types of
 * serializers according to different encoding styles.
 * This interface is inherited by the <CODE>ManagedEntityValue</CODE>,
 * <CODE>ManagedEntityKey</CODE>, <CODE>Event</CODE> and <CODE>QueryValue</CODE>
 * interfaces.
 *
 * For example and assuming that <CODE>mev</CODE> is a managed entity value
 * <pre>
 *   Serializer serializer= mev.makeSerializer( XmlSerializer.getClass().getName());
 *   XmlSerializer mevXmlSerializer = (XmlSerializer) Serializer;
 *   serializer.setEncodingStyle( XmlSerializerEncodingStyles.OSS_XML_ENCODING_STYLE);
 * </pre>
 *
 *
 * @see ManagedEntityValue
 * @see ManagedEntityKey
 * @see Event
 * @see QueryValue
 */
public interface SerializerFactory
{

    /**
     * Return all the serializer types than can be created by this factory.
     * This may return an empty array, in case no serializer is
     * implemented.
     *
     * @return an array of supported serializer types.
     */
    String[] getSupportedSerializerTypes();


    /**
     * Manufacture a Serializer for the object type inheriting
     * the interface.
     *
     * @param serializerType the class name of the serializer interface that must
     *  be created. For example <CODE>XmlSerializer.getClass().getName()</CODE>
     * @return a serializer matching the serializer type .
     * @exception java.lang.IllegalArgumentException if no serializer can be created matching
     * the provided Serializer Type.
     */
    Serializer makeSerializer( String serializerType )
    throws java.lang.IllegalArgumentException;
}


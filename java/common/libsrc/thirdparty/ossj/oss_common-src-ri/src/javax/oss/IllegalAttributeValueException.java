package javax.oss;

/**
 * Similar to javax.oss.IllegalArgumentException,
 * except it specifically refers to a bad value associated
 * with an attribute name within an AttributeAccess object.
 *
 * This exception is thrown if an AttributeAccess object passed
 * to a remote method contains an attribute with a bad value.
 *
 */
public class IllegalAttributeValueException extends javax.oss.IllegalArgumentException
{
    private String  attributeName;
    public  String getIllegalAttributeName ()
    {
       return attributeName;
    }
    public IllegalAttributeValueException()
    {
       super();
    }

    /**
     * Construct an IllegalAttributeValueException.
     *
     * @param nameOfBadAttribute the name of the attribute with the bad value.
     * @param message description of the problem.
     */
    public IllegalAttributeValueException( String nameOfBadAttribute,
                                            String message )
    {
       super(message);
      attributeName = nameOfBadAttribute;
    }
}



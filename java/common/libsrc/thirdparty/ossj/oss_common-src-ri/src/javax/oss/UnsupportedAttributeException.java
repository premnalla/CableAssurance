package javax.oss;


/**
 * This exception is thrown for operations affecting
 * unsupported attributes. It is unchecked.
 *
 *
 */

public class UnsupportedAttributeException extends RuntimeException
{
    private String  attributeName;

    /**
     * Return the unsupported attribute name that caused the exception.
     */
    public  String getAttributeName() 
    { 
       return attributeName;
    } 
    public UnsupportedAttributeException () 
    {
       super(); 
    }
    public UnsupportedAttributeException ( String unsupportedAttributeName,
                                           String message )
    { 
       super(message); 
       attributeName = unsupportedAttributeName;
       
    }
}


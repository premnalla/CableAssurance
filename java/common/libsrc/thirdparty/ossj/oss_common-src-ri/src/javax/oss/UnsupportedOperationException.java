package javax.oss;

/**
 * Similar to java.lang.UnsupportedOperationException,
 * except this inherits from Exception, not RuntimeException.
 * This new class is required because J2EE containers deal with
 * RuntimeException in a special way.  See EJB specification.
 * <p>
 * This exception is thrown if a remote method is not implemented
 * (and the throws clause states that this exception may be thrown).
  *
 */

public class UnsupportedOperationException extends java.lang.Exception
{
    public UnsupportedOperationException() {super(); }
    public UnsupportedOperationException( String message) { super(message); }

}


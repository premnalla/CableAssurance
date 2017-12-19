package javax.oss;

/**
 * Similar to java.lang.IllegalArgumentException,
 * except this inherits from Exception, not RuntimeException.
 * This new class is required because J2EE containers deal with
 * RuntimeException in a special way.  See EJB specification.
 * <p>
 * This exception is thrown if the argument of a remote
 * method is invalid.
 * <p>
 *
 *
 */

public class IllegalArgumentException extends Exception
{
    public IllegalArgumentException() {super(); }
    public IllegalArgumentException( String message) { super(message); }
}



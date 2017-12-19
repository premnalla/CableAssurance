package javax.oss;

/**
 * Similar to java.lang.IllegalStateException,
 * except this inherits from Exception, not RuntimeException.
 * This new class is required because J2EE containers deal with
 * RuntimeException in a special way.  See EJB specification.
 * <p>
 * The IllegalStateException  exception is thrown by a JVT Session Bean
 * to report that the invoked business method could not be completed because
 * the operation was invoked at an illegal or inappropriate time.
 *
 */

public class IllegalStateException extends Exception
{
    /**
     * Constructs an IllegalStateException with no detail message.
     */
	public IllegalStateException() {super(); }
    /**
     * Constructs an IllegalStateException with the specified detail message.
     *
     *@param message The detail message.
     */
    public IllegalStateException( String message) { super(message); }
}

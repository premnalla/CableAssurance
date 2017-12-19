package javax.oss;

/**
 * Indicates that there was a problem with a remote call to set the value of a Managed Entity.
 *
 */

public class SetException extends Exception
{
    public SetException() {super(); }
    public SetException( String message) { super(message); }
}


package javax.oss;


/**
 * This exception is thrown if a an update method (remote) contains
 * a stale value object.
 *
 */

public class ResyncRequiredException extends Exception
{
    private ManagedEntityKey  key;

    /**
     * Get the Key of the stale Managed Entity Value.
     * @return Key of the stale Managed Entity Value.
     */
    public  ManagedEntityKey  getManagedEntityKey ()
    {
       return key;
    }
    public ResyncRequiredException ()
    {
       super();
    }
    public ResyncRequiredException ( ManagedEntityKey staleObjectKey,
                                            String message )
    {
       super(message);
       if( staleObjectKey != null )
        key = (ManagedEntityKey) staleObjectKey.clone();
       else key = null;
    }
}



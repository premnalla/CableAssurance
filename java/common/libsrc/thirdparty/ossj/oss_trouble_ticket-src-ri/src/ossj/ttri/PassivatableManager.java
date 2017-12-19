package ossj.ttri;


/**
 * PassivatableManager Class
 *
 * Generic singleton manager of passivatable objects.  Use this to map
 * active objects contained by stateful session beans to a serializable
 * id, such that the SSB can be passivated
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class PassivatableManager {

    static private PassivatableManager singleton = new PassivatableManager();

    java.util.Hashtable passivatables;  //key - Integer ID, value - Passivatable


    static public final PassivatableManager getInstance() {
        log("IteratorManager:getInstance");
        return singleton;
    }

    // creates new PassivatableManager
    protected PassivatableManager() {
        passivatables = new java.util.Hashtable();
    }


    //use this to passivate a Passivatable
    public synchronized int passivate(Passivatable passivatable) {
        Integer passId = new Integer(java.lang.System.identityHashCode(passivatable));
        passivatables.put(passId, passivatable);
        return passId.intValue();
    }

    //use this to re-activate a Passivatable to map the Passivatable ID to its object
    public synchronized Passivatable activate(int passId) {
        return (Passivatable) passivatables.remove(new Integer(passId));
    }

    public static void log(String s) {
        Logger.log(s);
    }

}

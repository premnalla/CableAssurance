package ossj.qos.fmri;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.ejb.SessionBean;
import javax.ejb.CreateException;
import javax.ejb.SessionContext;

import javax.oss.fm.monitor.AlarmValue;
import javax.oss.ManagedEntityValue;
import javax.oss.fm.monitor.AlarmValueIterator;
/**
 * AlarmValueIteratorImpl
 *
 * @author Irene.Wang  
 */
public class AlarmValueIteratorImpl implements AlarmValueIterator {

    private SessionContext ctx = null;
   
    private ArrayList alarmList = null;
    
    private boolean isLoggingEnabled = true;

     AlarmValueIteratorImpl(ArrayList queryResults, boolean logging) {

        
        isLoggingEnabled = logging;
        
        if ( isLoggingEnabled ) {
        }
        alarmList = queryResults;

     }

    //-----------------------------------------------------------------------------
    //
    // Container callbacks/methods
    //
    //-----------------------------------------------------------------------------
    public void create( ArrayList queryResults, boolean logging )
    throws RemoteException {
    
        // creates the log
        
        isLoggingEnabled = logging;
        
        if ( isLoggingEnabled ) {
        }
        alarmList = queryResults;

        return;
    }

    public void ejbActivate() throws RemoteException
    {
        // creates the log
        
        if ( isLoggingEnabled ) {
        }
        return;
    }

    public void ejbPassivate() throws RemoteException
    {
        if ( isLoggingEnabled ) {
        }
        return;
    }

    public void setSessionContext( SessionContext context )
    throws javax.ejb.EJBException, java.rmi.RemoteException {
        ctx = context;
        return;
    }

    public void ejbRemove() throws RemoteException {
        if ( isLoggingEnabled ) {
        }
        alarmList.clear();
        return;
    }
     /**
     * Returns a list of alarms.
     *
     * <p>
     * The function takes one argument that specifies the most number of items to return. 
     * The implementation can decide to return less or the requested number of items.
     *
     * <p>
     * If zero is specified and empty list is returned.
     *
     * <p>
     * If the iteration is empty or has reached then end, an empty array is returned.
     *
     * <p>
     * If the argument is greater than the number of item in the iterator then all or 
     * implementation decided number of items are returned.
     *
     * <p>
     * If the agreement plus the cursor of the iterator is greater than the number of item 
     * in the iterator, all or implementation decided number of items from the cursor to the 
     * end is returned.
     *
     *@return AlarmValue[] List of alarms.
     *@param howMany Maximum of items to return, the implementation can decide to return less.
     *@exception java.rmi.RemoteException Is raised when a unexpected system exception occurrs.
     */
    public AlarmValue[] getNextAlarmValues(int howMany)
        throws java.rmi.RemoteException {

       //System.out.println("wrx--in getNextAlarm");
       //System.out.println("wrx--alarmlist size" + alarmList.size());

        if (isLoggingEnabled) {
            Object[][] parms = {  { "howMany", new Integer (howMany) } };
        }

        AlarmValue[] alarms = null;

        int sz = howMany;
        if ( alarmList.size() < howMany) {
            sz = alarmList.size();
        }

        alarms = new AlarmValue[sz];
        
        // populate the array while removing alarm values from the array list
        for (int x=0; x<sz; x++)
        {
            alarms[x] = (AlarmValue) alarmList.remove(0);
        }
        
       if (isLoggingEnabled) {
            Object[] parms2 = { "alarms", alarms };
        } 
        return alarms;
    }
    
     /**
     * An object created to contain the results of an operation so that 
     * the reaults may be returned to the client at a rate determined by the 
     * client.  The client receives a reference to the iterator as part of the 
     * information returned by the query operation.  The client then invokes 
     * operations on the iterator to receive batches of results in sizes 
     * determined by the client.  
     * The iterator keeps track of how far through the results the client has 
     * progressed.
     * 
     * @param howMany Size of the result batches returned to the client.
     * @return an array of managed entities with a size of at most howMany. The
     * array is empty when no more results are available.
     * @exception java.rmi.RemoteException
     */
    public ManagedEntityValue[] getNext( int howMany )
    throws java.rmi.RemoteException {
        return getNextAlarmValues( howMany );
    }

     public void remove() throws java.rmi.RemoteException {
        ejbRemove();
        return;
    }


}

package ossj.ttri;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.oss.ManagedEntityValue;
import javax.oss.trouble.TroubleTicketValue;
import java.rmi.RemoteException;

/**
 * TroubleTicketValueIterator Bean Implementation Class
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class TroubleTicketValueIteratorBean implements SessionBean {

    private static final boolean VERBOSE = true;
    private SessionContext ctx;

    private Operation operation;  //generic operation interface - bulk op or query
    private int operationId;      //serializable op id


    //----------------------------------------------------------------
    // Container callbacks
    //----------------------------------------------------------------
    public void ejbCreate(Operation op)
            throws CreateException, EJBException {

        log("TroubleTicketValueIteratorBean::ejbCreate");
        this.operation = op;
    }


    public void ejbActivate() throws RemoteException {

        log("TroubleTicketValueIteratorBean::ejbActivate");

        //prepare the bean for re-activation: retrieve my iterator object
        operation = (Operation) PassivatableManager.getInstance().activate(operationId);
    }

    public void ejbPassivate() throws RemoteException {

        log("TroubleTicketValueIteratorBean::ejbPassivate");

        //prepare the bean for passivation...
        operationId = PassivatableManager.getInstance().passivate(operation);
        operation = null;    //you could also declare ttIterator as transient

    }

    public void ejbRemove() throws RemoteException {
        operation.cleanup();
        operation = null;
    }

    public void setSessionContext(SessionContext arg1) throws RemoteException {
        log("TroubleTicketValueIteratorBean:setSessionContext");
        this.ctx = arg1;
    }

    /**
     * get "howMany" subsequent ManagedEntityValue objects
     *
     * @param howMany how many ManagedEntityValue objects the client wishes to retrieve
     * @return ManagedEntityValue array
     * @exception javax.ejb.EJBException
     */
    public ManagedEntityValue[] getNext(int howMany)
            throws javax.ejb.EJBException {

        TroubleTicketValue[] ttvs = getNextTroubleTickets(howMany);
        ManagedEntityValue[] mevs = new ManagedEntityValue[ttvs.length];

        for (int x = 0; x < ttvs.length; x++)
            mevs[x] = ttvs[x];

        return mevs;

    }

    /**
     * get "howMany" subsequent TroubleTicketValue objects
     *
     * @param howMany how many TroubleTicketValue objects the client wishes to retrieve
     * @return TroubleTicketValue array
     * @exception javax.ejb.EJBException
     */
    public TroubleTicketValue[] getNextTroubleTickets(int howMany)
            throws javax.ejb.EJBException {

        if (howMany <= 0) {
            throw new javax.ejb.EJBException("Invalid value for howMany: " + howMany);
        }

        log("TroubleTicketValueIterBean::getNext - howMany " + howMany);
        //--------------------------------------------------------------
        // Retrieve objects from the iterator object.
        // Objects are placed in the retList.
        //--------------------------------------------------------------
        Object[] retObjs = null;
        try {
            retObjs = operation.getNext(howMany);
        } catch (TTIteratorException ieEx) {
            Logger.logException("TTIteratorException caught at TroubleTicketValueIteratorBean::getNext", ieEx);
        }

        //cast to TroubleTicketValue objects
        TroubleTicketValue[] ttVals = new TroubleTicketValue[retObjs.length];
        for (int x = 0; x < retObjs.length; x++) {
            ttVals[x] = (TroubleTicketValue) retObjs[x];
            //log("TTValIter Key: " + ttVals[x].getTroubleTicketKey().getTroubleTicketPrimaryKey())
        }


        return ttVals;

    }



/*VP
   public void clear()
		throws java.rmi.RemoteException, javax.ejb.RemoveException
   {
	ejbRemove();
   }
*/

    public void log(String s) {
        Logger.log(s);
    }

}

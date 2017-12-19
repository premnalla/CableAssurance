package ossj.ttri;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.oss.ManagedEntityKeyResult;
import javax.oss.trouble.TroubleTicketKeyResult;
import java.rmi.RemoteException;

/**
 * TroubleTicketKeyResultIterator Session Bean Implementation Class
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class TroubleTicketKeyResultIteratorBean implements SessionBean {

    private static final boolean VERBOSE = true;
    private SessionContext ctx;

    private Operation operation;              //generic bulk operation/query interface
    private int operationId;                  //serializable iterator id


    //----------------------------------------------------------------
    // ejbCreate
    //----------------------------------------------------------------
    public void ejbCreate(Operation op)
            throws CreateException,
            EJBException {
        this.operation = op;
    }


    public void ejbActivate() throws RemoteException {

        log("TroubleTicketKeyResultIteratorBean::ejbActivate");

        //prepare the bean for re-activation: retrieve my iterator object
        operation = (Operation) (PassivatableManager.getInstance().activate(operationId));
    }

    public void ejbPassivate() throws RemoteException {

        log("TroubleTicketKeyResultIteratorBean::ejbPassivate");

        //prepare the bean for passivation...
        operationId = PassivatableManager.getInstance().passivate(operation);
        operation = null;    //you could also declare ttIterator as transient

    }

    public void ejbRemove() throws RemoteException {
        operation.cleanup();
        operation = null;
    }

    public void setSessionContext(SessionContext arg1) throws RemoteException {
        log("TroubleTicketKeyResultIteratorBean:setSessionContext");
        this.ctx = arg1;
    }


    /**
     * get "howMany" subsequent ManagedEntityKeyResult objects
     *
     * @param howMany how many ManagedEntityKeyResult objects the client wishes to retrieve
     * @return ManagedEntityKeyResult array
     * @exception javax.ejb.EJBException
     */
    public ManagedEntityKeyResult[] getNext(int howMany)
            throws javax.ejb.EJBException {

        TroubleTicketKeyResult[] ttkr = getNextTroubleTicketKeyResults(howMany);
        ManagedEntityKeyResult[] mekr = new ManagedEntityKeyResult[ttkr.length];

        for (int x = 0; x < ttkr.length; x++)
            mekr[x] = ttkr[x];

        return mekr;

    }

    /**
     * get "howMany" subsequent TroubleTicketValue objects
     *
     * @param howMany how many TroubleTicketValue objects the client wishes to retrieve
     * @return TroubleTicketValue array
     * @exception javax.ejb.EJBException
     */
    public TroubleTicketKeyResult[] getNextTroubleTicketKeyResults(int howMany)
            throws javax.ejb.EJBException {

        if (howMany <= 0) {
            throw new javax.ejb.EJBException("Invalid value for howMany: " + howMany);
        }

        log("TroubleTicketKeyResultIterator::getNext: howMany = " + howMany);

        //--------------------------------------------------------------
        // Retrieve objects from the iterator object.
        // Objects are placed in the retList.
        //--------------------------------------------------------------
        Object[] retObjs = null;
        try {
            retObjs = operation.getNext(howMany);
        } catch (TTIteratorException ieEx) {
            Logger.log("TTIteratorException caught at getObjects..");
        }


        TroubleTicketKeyResult[] keyResults = new TroubleTicketKeyResult[retObjs.length];
        for (int x = 0; x < retObjs.length; x++) {
            keyResults[x] = (TroubleTicketKeyResult) retObjs[x];
        }

        return keyResults;

    }


/*VP
   public void clear()
		throws java.rmi.RemoteException, javax.ejb.RemoveException
   {
	operation.cleanup();
	operation = null;
   }
*/

    public void log(String s) {
        Logger.log(s);
    }

}

package ossj.ttri;


import javax.oss.QueryValue;
import javax.oss.trouble.TroubleTicketValue;

/**
 * TTIteratorImpl Class
 * Serves as a base class for Trouble Ticket iterator producer
 * implementation.  This is the iterator implementation object which
 * contains the producer code, and is managed by the IteratorManager
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */

public class TTIteratorImpl {

    //--------------------------------------------------------------
    // Retrieve objects for the iterator.  Objects are placed into
    // the returned array.
    //--------------------------------------------------------------
    public Object[] getObjects(int howMany)
            throws TTIteratorException {

        Object[] retObjs = null;

/*sample code from Clarify
		try
		{
			retObjs = clfyConnect.getNext(howMany)
		}
		catch (java.rmi.RemoteException re)
		{
			log("TTIteratorImpl::getObjects : " + re);
		}
		*/

        return retObjs;

    }


    public TTIteratorImpl(TroubleTicketValue value, //value template
                          String[] attrNames, //fields to load in return values
                          java.util.Properties[] iterProps)         //producer properties - generic
    {

        log("TTIteratorImpl::ctor (single ttValue)");

        initialize();
    }

    public TTIteratorImpl(TroubleTicketValue[] value, //value templates
                          String[] attrNames, //fields to load in return values
                          java.util.Properties[] iterProps)   //producer properties - generic
    {

        log("TTIteratorImpl::ctor (ttValue[])");

        initialize();
    }

    public TTIteratorImpl(QueryValue value, //
                          String[] attrNames, //fields to load in return values
                          java.util.Properties[] iterProps)   //producer properties - generic
    {

        log("TTIteratorImpl::ctor (ttValue[])");

        initialize();
    }

    public void initialize() {


    }


    public void cleanup() {

    }


    public void log(String s) {
        Logger.log(s);
    }

}

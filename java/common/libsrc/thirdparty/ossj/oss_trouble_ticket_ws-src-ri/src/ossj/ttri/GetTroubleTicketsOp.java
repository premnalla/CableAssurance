package ossj.ttri;

import javax.oss.trouble.TroubleTicketValue;
import java.io.Serializable;
import java.sql.SQLException;

/**
 * GetTroubleTicketsOp Implementation Class
 *
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
// KS 25-08-2003	class made serializable

// KS 25-08-2003

public class GetTroubleTicketsOp implements BulkOperation, Serializable {


    private java.util.Vector resultVect = new java.util.Vector();
    private TroubleTicketValue[] templates;
    private String[] attrNames;

    //can use for both single and multiple templated ops
    public GetTroubleTicketsOp(TroubleTicketValue[] templates,
                               String[] attrNames) {
        log("GetTroubleTicketsOp::ctor");
        this.templates = templates;
        this.attrNames = attrNames;
    }


    //--------------------------------------------------------------
    // Retrieve objects for the iterator.  Objects are placed into
    // the returned array.
    //--------------------------------------------------------------
    public Object[] getNext(int howMany)
            throws TTIteratorException {

        int sz = howMany;
        if (resultVect.size() < howMany) sz = resultVect.size();

        Object[] retObjs = new Object[sz];
        for (int x = 0; x < sz; x++) {
            retObjs[x] = resultVect.firstElement();
            resultVect.removeElementAt(0);
        }

        //spew retObjs
        //log("GetTroubleTicketsOp returning retObjs...");
        //for (int x=0;x<retObjs.length;x++)
        //{
        //   TroubleTicketKeyResult ttKeyRes = (TroubleTicketKeyResult)retObjs[x];
        //   log("Key: " + ttKeyRes.getTroubleTicketKey().getTroubleTicketPrimaryKey());
        //}

        return retObjs;

    }


    public void setReturnMode(boolean retMode) {
        //return mode not supported. TODO - revisit inheritance.
    }


    public void abort() {
        //not implemented yet
    }

    public void reset() {
        //not implemented yet
    }

    public void initialize()
            throws javax.ejb.ObjectNotFoundException,
            javax.oss.IllegalArgumentException {

        //execute the select statement and build ttValues from the results.

        log("GetTroubleTicketsOp.initialize");


        resultVect = new java.util.Vector();


        try {
            log("----DBAccess.select STARTING----");

            resultVect = DBAccess.selectValues(attrNames, templates);


            log("-----DBAccess.select completed OK-----");


        } catch (SQLException sqlEx) {
            log("SQLException caught in BestEffortTemplateOp.initialize");
            sqlEx.printStackTrace();

            //throw new javax.ejb.ObjectNotFoundException("SQL Error accessing TroubleTicket DB - embedded SQL message: " + sqlEx.getMessage());
        }

        //Q.  Throw exception if nothing is found or return empty iterator?
        /*
        if (ttKeys.size() == 0)
        {
            String noMatchingTTs = new String
                ("GetTroubleTicketsOp - No TTs found matching the template(s) provided");
            log(noMatchingTTs);

            throw new javax.ejb.ObjectNotFoundException (noMatchingTTs);
        }
        */


    }

    public void setProperties(PropertyList props) {
        //props not supported for get
    }

    public void cleanup() {
        //release resources (e.g. pool connection)
    }

    public void log(String s) {
        Logger.log(s);
    }

}

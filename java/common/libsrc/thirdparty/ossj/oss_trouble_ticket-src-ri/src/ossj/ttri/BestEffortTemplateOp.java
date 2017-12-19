package ossj.ttri;


import javax.oss.SetException;
import javax.oss.trouble.EscalationList;
import javax.oss.trouble.TroubleTicketKey;
import javax.oss.trouble.TroubleTicketValue;
import java.io.Serializable;
import java.sql.SQLException;

// import weblogic.jndi.*;

/**
 *  BestEffortTemplateOp Class
 *
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */

// KS 25-08-2003	class made serializable


// KS 25-08-2003

public class BestEffortTemplateOp implements BulkOperation, Serializable {

    private java.util.Vector resultVect = new java.util.Vector();
    private TroubleTicketValue[] templates = null;
    private TroubleTicketValue ttValue = null;
    private boolean return_failed_only = false;
    private int opType = -1;
    private PropertyList props = null;

    //can use for both single and multiple templated ops
    public BestEffortTemplateOp(TroubleTicketValue[] templates,
                                TroubleTicketValue value,
                                boolean return_failed_only,
                                int opType,
                                PropertyList props) {
        log("BestEffortTemplateOp::ctor");
        this.return_failed_only = return_failed_only;
        this.templates = templates;
        this.ttValue = value;
        this.opType = opType;
        this.props = props;
    }


    //--------------------------------------------------------------
    // Retrieve objects for the iterator.  Objects are placed into
    // the returned array.
    //--------------------------------------------------------------
    public Object[] getNext(int howMany)
            throws TTIteratorException {
        Logger.log("---Object[] getNext----");

        int sz = howMany;
        if (resultVect.size() < howMany) {
            Logger.log("--->Returned Size = " + resultVect.size());
            sz = resultVect.size();
        }

        Object[] retObjs = new Object[sz];
        for (int x = 0; x < sz; x++) {
            retObjs[x] = resultVect.firstElement();
            resultVect.removeElementAt(0);
        }


        return retObjs;


    }

    public void setProperties(PropertyList props) {
        this.props = props;
    }

    public void setReturnMode(boolean failuresOnly) {
        log("BestEffortTemplateOp::setReturnMode");
        this.return_failed_only = failuresOnly;
    }

    public void abort() {

    }

    public void reset() {

    }

    public void initialize()
            throws javax.ejb.ObjectNotFoundException,
            javax.oss.IllegalArgumentException,
            java.sql.SQLException {

        //-----------------------------------------------------------------
        // In Best Effort mode, a response is built which contains individual
        // success/failure details for each TroubleTicketKey affected by the
        // operation.
        //
        // Do a query first to get the TRIDs that are affected, then execute
        // the update.
        //
        // Do not do an update thru cursor - this is typically not optimal
        //
        //------------------------------------------------------------------
        log("BestEffortTemplateOp.initialize");

        //just get the trId
        String[] attrNames = new String[1];
        attrNames[0] = new String("trid");      //SQL: select trid from troubleticket...
        java.util.Vector ttKeys = null;


        try {

            ttKeys = DBAccess.selectKeys(attrNames, templates);
            log("DBAccess.select completed OK");


        } catch (SQLException sqlEx) {
            log("SQLException caught in BestEffortTemplateOp.initialize");
            sqlEx.printStackTrace();

            throw new javax.ejb.ObjectNotFoundException("SQL Error accessing TroubleTicket DB - embedded SQL message: " + sqlEx.getMessage());
        }


        if (ttKeys.size() == 0) {
            String noMatchingTTs = new String
                    ("BestEffortTemplateOp - No TTs found matching the template(s) provided");
            log(noMatchingTTs);
            //WIPRO
            //throw new javax.ejb.ObjectNotFoundException (noMatchingTTs);
            Logger.log("*********** There is no match for the given Template");
            //WIPRO
        }

        //ttKeys contains the keys returned from the query.  Now execute the
        //operation on those keys.
        for (int x = 0; x < ttKeys.size(); x++) {
            try {
                executeOperation((TroubleTicketKey) ttKeys.elementAt(x));
            } catch (javax.oss.IllegalAttributeValueException e) {
                throw new javax.oss.IllegalArgumentException();
            }
        }

    }


    public void executeOperation(TroubleTicketKey ttKey)
            throws javax.ejb.ObjectNotFoundException,
            java.sql.SQLException,
            javax.oss.IllegalAttributeValueException {

        //-------------------------------------------------------------
        // Prepare the ttValue according to the op type.
        // The ttValue will only be set if its a "set" operation,
        // so create it if its null.
        // Load the key and the operation specific data
        //-------------------------------------------------------------
        if (ttValue == null)
            ttValue = new TroubleTicketValueImpl();

        ttValue.setTroubleTicketKey(ttKey);


        Property prop = null;

        switch (this.opType) {
            case OperationTypes.SET:
                break;

            case OperationTypes.CLOSE:

            case OperationTypes.CANCEL:
                prop = props.getProperty("CloseOutStatus");
                int closeOutStatus = prop.getIntProperty();
                ttValue.setTroubleStatus(closeOutStatus);
                break;

            case OperationTypes.ESCALATE:
                prop = props.getProperty("EscalationList");
                EscalationList escaList = (EscalationList) prop.getObjectProperty();
                ttValue.setEscalationList(escaList);

                //reuse the DBAccess applyMVAUpdate method
                java.util.Vector mvaNames = new java.util.Vector();
                mvaNames.add(TroubleTicketValue.ESCALATIONLIST);
                DBAccess.applyMVAUpdate(ttValue,
                        ttKey.getTroubleTicketPrimaryKey(),
                        mvaNames);
                break;

            default:
                String unSuppOp = new String("BestEffortTemplateOp.initialize: Unsupported operation: " + opType);
                log(unSuppOp);
                throw new IllegalArgumentException(unSuppOp);

        }


        int count = 0;

        //todo - should I/Fs expose setters?
        TroubleTicketKeyResultImpl ttkr = new TroubleTicketKeyResultImpl();

        ttkr.setTroubleTicketKey(ttKey);

        try {
            log("BestEffortTemplateOp calling DBAccess.updateByKey");
            count = DBAccess.updateByKey(ttValue, false);
        }

                //exceptions caught here are SQLException, ObjectNotFoundException,
                //and IllegalAttributeValueExcpetion.  Put the exceptiom into the TTKeyResult
        catch (Exception ex) {
            log("Exception occured in executeOperation" + ex.getMessage());
            ex.printStackTrace();
            //set the TTKeyResult fields
            ttkr.setSuccess(false);
            ttkr.setException(ex);
        }

        //count could be = 0 is if this best effort op was not done within the
        //scope of a transaction and someone else has deleted the TT in the interim.
        if (count == 0) {
            ttkr.setSuccess(false);
            String ttid = ttkr.getTroubleTicketKey().getTroubleTicketPrimaryKey();
            ttkr.setException(new SetException("TTKey: " + ttid + " no longer exists"));
        } else {
            ttkr.setSuccess(true);
        }

        //only case where we do not send the result is if it succeeded and
        //only failure results were requested
        if (!(return_failed_only && ttkr.isSuccess())) {
            resultVect.add(ttkr);
        }


        //if the update worked send the appropriate event
        if (count == 1) {
            switch (this.opType) {
                case OperationTypes.SET:
                    TTEventPublisher.getInstance().sendAVCEvent(ttValue);
                    break;

                case OperationTypes.CLOSE:
                    TTEventPublisher.getInstance().sendCloseOutEvent(ttValue);
                    break;      //WIPRO
                case OperationTypes.CANCEL:
                    //needs to be passed in by the JVT method.  TO DO.
                    //Property prop = properties.getProperty("CloseOutNarr");
                    //Property prop = properties.getProperty("TroubleTicketKey");
                    //temporary workaround:

                    String closeOutNarr = new String("Trouble ticket cancelled");
                    TTEventPublisher.getInstance().sendCancellationEvent(ttValue.getTroubleTicketKey(),
                            closeOutNarr);
                    break;

                case OperationTypes.ESCALATE:
                    TTEventPublisher.getInstance().sendAVCEvent(ttValue);
                    break;

                default:
                    String unSuppOp = new String("BestEffortTemplateOp.initialize: Unsupported operation" + opType);
                    log(unSuppOp);
                    throw new IllegalArgumentException(unSuppOp);


            }
        }

    }


    public void cleanup() {
        //release resources (e.g. pool connection)
    }

    public void log(String s) {
        Logger.log(s);
    }

}

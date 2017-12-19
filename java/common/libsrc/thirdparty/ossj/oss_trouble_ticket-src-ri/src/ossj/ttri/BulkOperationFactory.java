package ossj.ttri;

import javax.oss.IllegalArgumentException;
import javax.oss.trouble.TroubleTicketValue;

/**
 * BulkOperationFactory Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class BulkOperationFactory {

    static private BulkOperationFactory singleton = new BulkOperationFactory();

    static public final BulkOperationFactory getInstance() {
        Logger.log("BulkOperationFactory.getInstance");
        return singleton;
    }

    // creates new BulkOperationFactory
    protected BulkOperationFactory() {
    }


    //-------------------------------------------------------------------
    // provide constructors that will provide all functionality as defined
    // in the TT JVT Session Bean bulk methods
    //-------------------------------------------------------------------
    public BulkOperation create(String bulkOpName,
                                TroubleTicketValue[] templates,
                                TroubleTicketValue ttVal,
                                //String[] attrNames,
                                boolean returnMode,
                                int operationType,
                                PropertyList props)   //javax.oss.ReturnMode enumerated type
            throws IllegalArgumentException {

        log("BulkOperation.create: " + bulkOpName);


        if (bulkOpName.equals("BestEffortTemplateOp")) {

            validateOperationType(operationType);
            BulkOperation bulkOp = (BulkOperation) (new BestEffortTemplateOp(templates,
                    ttVal,
                    //attrNames,
                    returnMode,
                    operationType,
                    props));
            return bulkOp;
        } else
            throw new IllegalArgumentException("Unknown bulk operation: " + bulkOpName);
    }

    //create method for "get" ops.
    public BulkOperation create(String bulkOpName,
                                TroubleTicketValue[] templates,
                                String[] attrNames)
            throws IllegalArgumentException {

        log("BulkOperation.create: " + bulkOpName);

        //GetTroubleTicketsOp - templated get (select)operation.
        if (bulkOpName.equals("GetTroubleTicketsOp")) {
            BulkOperation bulkOp = (BulkOperation)
                    (new GetTroubleTicketsOp(templates, attrNames));
            return bulkOp;
        } else
            throw new IllegalArgumentException("Unknown bulk operation: " + bulkOpName);
    }


    private void validateOperationType(int op)
            throws IllegalArgumentException {
        if ((op != OperationTypes.CREATE) &&
                (op != OperationTypes.GET) &&
                (op != OperationTypes.SET) &&
                (op != OperationTypes.CANCEL) &&
                (op != OperationTypes.CLOSE) &&
                (op != OperationTypes.ESCALATE)) {
            String iaEx = new String("BulkOperationFactory - unrecognized operation type");
            Logger.log("BulkOperationFactory - unrecognized operation type");
            throw new IllegalArgumentException(iaEx);
        }

    }


    private void log(String s) {
        Logger.log(s);
    }


}

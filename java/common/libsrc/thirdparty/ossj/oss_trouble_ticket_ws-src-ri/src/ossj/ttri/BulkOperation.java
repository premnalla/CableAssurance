package ossj.ttri;


/**
 *  BulkOperation Interface
 *
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
interface BulkOperation extends Operation,
        Passivatable {
    public void setReturnMode(boolean retMode);
}

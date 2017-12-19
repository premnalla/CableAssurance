package ossj.qos.fmri;

import java.util.ArrayList;
import javax.oss.ManagedEntityValue;
import java.sql.SQLException;

/**
 * DeleteDBCommandImpl
 * 
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
public class DeleteDBCommandImpl extends BaseDBCommandImpl {

    // contains the number of deleted alarms.
    Integer opResult = null;

    ManagedEntityValue whereClause = null;

    /** Creates new DeleteAlarmsOperation */
    public DeleteDBCommandImpl() {
    }

    public void initializeCmd() throws SQLException {

        // build the command form
        String shell = StatementBuilder.buildSQLDeleteCmd( whereClause, dbHelper );

        // debug print...
        log("DeleteDBCommandImpl: Created shell : " + shell );

        // load the command form
        loadCommandShell( shell );

        return;
    }

    /**
     * Executes the operation
     *
     * @exception java.sql.SQLException Is thrown to report a failure
     * in the execution of the operation.
     */
    public void executeCmd() throws java.sql.SQLException {

        // sets the initial position in the prepared statement.
        int pos = 1;

        // load the prepared statement values
        dbHelper.setManagedEntityInPrepStatement( whereClause, pos, ps );

        // execute the command...
        int result = executeUpdateCmd();
        opResult = new Integer( result );

        return;
    }

    /**
     * Returns the next result of the operation. Used to return both results that
     * return both single and multiple results.
     *
     */
    public Object getResult() {
        return opResult;
    }

    public void setWhereClauseTemplate ( ManagedEntityValue template ) {
        whereClause = template;
        return;
    }

}
package ossj.qos.fmri;

import javax.oss.ManagedEntityValue;
import java.sql.SQLException;

/**
 * SetDBCommandImpl
 * 
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
public class SetDBCommandImpl extends BaseDBCommandImpl {

    Integer opResult = null;

    ManagedEntityValue updateTemplate = null;
    ManagedEntityValue whereClause = null;

    /** Creates new UpdateAlarmsByKeyOperationImpl */
    public SetDBCommandImpl() {
    }

    public void initializeCmd() throws SQLException {

        // load the template array
        ManagedEntityValue[] templates = { whereClause };

        // build the command form
        String shell = StatementBuilder.buildSQLUpdateCmd( updateTemplate, templates,
        dbHelper );

        // debug print...
        log("SetDBCommandImpl: Created shell : " + shell );

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
 
        // the position of values in the command form
        int pos;

        // load the position for the 1st value in the command
        pos = 1;
        // load the command values
        pos = dbHelper.setManagedEntityInPrepStatement( updateTemplate, pos, ps );
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

    public void setUpdateTemplate ( ManagedEntityValue template ) {
        updateTemplate = template;
        return;
    }

}
package ossj.qos.fmri;


import java.util.ArrayList;
import java.sql.SQLException;
import javax.oss.ManagedEntityValue;
import javax.oss.ManagedEntityKey;
/**
 * InsertAlarmsOperation
 * 
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public class InsertDBCommandImpl extends BaseDBCommandImpl {

    private ManagedEntityKey opResult = null;
    private ManagedEntityValue insertTemplate = null;

    /**
    Creates new InsertAlarmsOperation
     */
    public InsertDBCommandImpl() {
    }

    public void initializeCmd() throws SQLException {

        // build the command form
        // Note: template should be set to fully populated to ensure that all attributes
        // are used
        String shell = StatementBuilder.buildSQLInsertCmd( insertTemplate, dbHelper );

        // debug print...
        log("InsertDBCommandImpl: Created shell : " + shell );

        // load the command shell into the prepared statement...
        loadCommandShell( shell );
        return;
    }

    /**
     * Executes the operation
     *
     * @exception java.sql.SQLException Is thrown to report a failure
     * in the execution of the operation (Maybe?????).
     */
    public void executeCmd() throws SQLException {

        // set the initial position.
        int pos = 1;

        // load the prepared statement values
        dbHelper.setManagedEntityInPrepStatement( insertTemplate, pos, ps );

        // execute the command...
        int result = executeUpdateCmd();

        if ( result == 1 ) {
            opResult = insertTemplate.getManagedEntityKey();
        }
        else {
            opResult = null;
        }
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

    public void setInsertTemplate ( ManagedEntityValue template ) {
        insertTemplate = template;
        return;
    }
}
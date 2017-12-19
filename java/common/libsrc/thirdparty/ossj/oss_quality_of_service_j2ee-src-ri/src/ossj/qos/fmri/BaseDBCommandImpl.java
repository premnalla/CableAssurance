package ossj.qos.fmri;

import java.util.Properties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;

import ossj.qos.util.Trace;

/**
 * BaseOperationImpl
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public abstract class BaseDBCommandImpl implements DBCommand {

    private Properties properties = null;
    private Connection con = null;
    private Trace myLog = null;

    protected ResultSet rs = null;
    protected PreparedStatement ps = null;
    protected ManagedEntityValueDBRep dbHelper = null;

    /** Creates new BaseOperationImpl */
    public BaseDBCommandImpl() {
    }

    public void setConnection( Connection dbCon ) {
        con = dbCon;
        return;
    }

    public Connection getConnection() {
        return con;
    }
    
    public void setLogger( Trace logger ) {
        myLog = logger;
        return;
    }

    /**
     * Closes the db result set.
     *
     * @param rs The db result set.
     * @exception javax.ejb.EJBException thrown if there is a communications
     * or systems failure.
     */

    private void closeResultSet()
    throws SQLException
    {
        if (rs != null) {
            rs.close();
        }
        return;
    }


    /**
     * Closes the db prepared statement resource.
     *
     * @param ps The prepared statement resource to be closed.
     * @exception javax.ejb.EJBException thrown if there is a communications
     * or systems failure.
     */

    private void closeCommandShell()
    throws SQLException
    {
        if (ps != null) {
            ps.close();
        }
        return;
    }

    /**
     * Closes all the command resources.
     *
     */
    public void closeCmd() {
        try {
            // restore resources...
            closeResultSet();
            closeCommandShell();
          //  closeConnection();
        }
        catch ( SQLException ex1 ) {
            log ( "Operation - Cleanup: Problem closing resources." );
        }
        return;
    }

    protected void loadCommandShell( String shell )
    throws SQLException {
        if ( ps != null ) {
            ps.close();
        }
        ps = con.prepareStatement( shell );
        return;
    }

    protected int executeUpdateCmd() throws SQLException {
        return ps.executeUpdate();
    }

    protected ResultSet executeQueryCmd() throws SQLException {
        if ( rs != null ) {
            rs.close();
        }
        ps.executeQuery();
        return ps.getResultSet();
    }

    protected void setDBHelper ( String valueType ) {
        // get its db representation helper class
        dbHelper = StatementBuilder.getDBHelper( valueType );
        return;
    }

    protected void log(String s) {
        if ( myLog != null ) {
            myLog.log(s);
        }
    }
}

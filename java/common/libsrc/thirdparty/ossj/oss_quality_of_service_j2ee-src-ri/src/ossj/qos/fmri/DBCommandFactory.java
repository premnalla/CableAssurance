package ossj.qos.fmri;

import java.util.HashSet;

/**
 * DBCommandFactory
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public class DBCommandFactory extends Object {

    static public String QUERY_ALARM_COUNTS_DBCMD = "QueryAlarmCountsDBCommand";
    static public String QUERY_BY_QUERYVALUE_DBCMD = "QueryByQueryValueDBCommand";
    static public String QUERY_SINGLE_UPDATE_DBCMD = "QuerySingleUpdateDBCommand";
    static public String DELETE_DBCMD = "DeleteDBCommand";
    static public String INSERT_DBCMD = "InsertDBCommand";
    static public String SET_DBCMD = "SetDBCommand";

    /** Creates new OperationFactory */
    public DBCommandFactory() {
    }

    private static HashSet dbCmdTypes = null;

    static {
        dbCmdTypes = new HashSet();
        dbCmdTypes.add(QUERY_ALARM_COUNTS_DBCMD);
        dbCmdTypes.add(QUERY_BY_QUERYVALUE_DBCMD);
        dbCmdTypes.add(INSERT_DBCMD);
        dbCmdTypes.add(QUERY_SINGLE_UPDATE_DBCMD);
        dbCmdTypes.add(SET_DBCMD);
        dbCmdTypes.add(DELETE_DBCMD);
    }

    public static DBCommand createOperation( String type )
    throws IllegalArgumentException {
        DBCommand dbCmd = null;

        if ( type == QUERY_ALARM_COUNTS_DBCMD ) {
             dbCmd = new QueryCountsDBCommandImpl();
        }
        else if ( type == QUERY_BY_QUERYVALUE_DBCMD ) {
             dbCmd = new QueryByQueryValueDBCommandImpl();
        }
        else if ( type == INSERT_DBCMD ) {
             dbCmd = new InsertDBCommandImpl();
        }
        else if ( type == QUERY_SINGLE_UPDATE_DBCMD ) {
            dbCmd = new QuerySingleUpdateDBCommandImpl();
        }
        else if ( type == SET_DBCMD ) {
            dbCmd = new SetDBCommandImpl();
        }
        else if ( type == DELETE_DBCMD ) {
            dbCmd = new DeleteDBCommandImpl();
        }
        else {
            throw new IllegalArgumentException( "Unsupported DBCommand type" );
        }
        
        return dbCmd;
    }

}
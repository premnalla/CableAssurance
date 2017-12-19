package ossj.qos.fmri;

import javax.oss.fm.monitor.AlarmCountsValue;

import javax.sql.DataSource;
import java.sql.ResultSet;
import javax.oss.ManagedEntityValue;
import java.sql.SQLException;
import javax.oss.fm.monitor.AlarmValue;
import javax.oss.fm.monitor.PerceivedSeverity;

/**
 * QueryCountsOperationImpl
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
public class QueryCountsDBCommandImpl extends BaseDBCommandImpl {

    private AlarmCountsValue countsValue = null;
    private WhereClause[] whereClauses = null;

    /** Creates new QueryCountsOperationImpl */
    public QueryCountsDBCommandImpl() {
    }

    public void initializeCmd() throws SQLException {
 
        // get the db name for perceived severity
        String severityDBName = dbHelper.getDBAttributeName( AlarmValue.PERCEIVED_SEVERITY );
        // load select names array
        String[] names = { severityDBName };
        // build the command form
        String shell = StatementBuilder.buildSpecialSelect( names, dbHelper.getTableName(), whereClauses );
        
        // debug print...
        log("Created shell : " + shell );

        // load the command form
        loadCommandShell( shell );
        return;
    }

    public void executeCmd() throws SQLException {

        int pos = 1;
        for ( int i=0, len=whereClauses.length; i<len; i++ ) {
            ManagedEntityValue entity = whereClauses[i].buildManagedEntityTemplate();
            String[] order = whereClauses[i].getClauseMEVAttributeNames();
            pos = dbHelper.setManagedEntityInPrepStatementByOrder(entity, pos, ps, order );
        }

        // array to hold the results...
        int[] counts = {0,0,0,0,0,0,0};

        // execute the query...
        ResultSet rs = executeQueryCmd();

        // get the db name for perceived severity
        String severityDBName = dbHelper.getDBAttributeName( AlarmValue.PERCEIVED_SEVERITY );
        
        // determine the counts...
        while ( rs.next() ) {
            int cnt = rs.getInt(severityDBName);
            counts[cnt]++;
        }
        rs.close();

        countsValue = new AlarmCountsValueImpl ( counts[PerceivedSeverity.INDETERMINATE], 
                                                 counts[PerceivedSeverity.CRITICAL], 
                                                 counts[PerceivedSeverity.MAJOR],
                                                 counts[PerceivedSeverity.MINOR], 
                                                 counts[PerceivedSeverity.WARNING], 
                                                 counts[PerceivedSeverity.CLEARED] );
        return;
    }

    public Object getResult() {
        return countsValue;
    }

    public void setWhereClauses ( WhereClause[] clauses ) {
        whereClauses = clauses;
    }

}
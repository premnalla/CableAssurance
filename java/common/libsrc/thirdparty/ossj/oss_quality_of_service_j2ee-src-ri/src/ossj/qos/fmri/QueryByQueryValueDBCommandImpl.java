package ossj.qos.fmri;

import java.util.ArrayList;
import java.sql.ResultSet;
import javax.oss.ManagedEntityValue;
import java.sql.SQLException;

/**
 * QueryByQueryValueDBCommandImpl
 * 
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public class QueryByQueryValueDBCommandImpl extends BaseDBCommandImpl {

    private ArrayList entityList = new ArrayList();
    private WhereClause[] whereClauses = null;
    private String[] entityNames = null;

    /** Creates new QueryCountsOperationImpl */
    public QueryByQueryValueDBCommandImpl() {
    }

    public void initializeCmd() throws SQLException {

        // build the command form.
        String shell = StatementBuilder.buildSpecialSelect( null, dbHelper.getTableName(), whereClauses );

        //System.out.println("wrx--query shell" + shell);
        // load the shell into the prepared statement...
        loadCommandShell( shell );
        return;
    }

    public void executeCmd() throws SQLException {

        int pos = 1;
        for ( int i=0, len=whereClauses.length; i<len; i++ ) {
            ManagedEntityValue entity = whereClauses[i].buildManagedEntityTemplate();
            String[] order = whereClauses[i].getClauseMEVAttributeNames();
            pos = dbHelper.setManagedEntityInPrepStatementByOrder( entity, pos, ps, order );
        }

        // execute the query...
        ps.executeQuery();

        ResultSet rs = ps.getResultSet();

        // get the query...
        while ( rs.next() ) {
            
            ManagedEntityValue entity = dbHelper.setManagedEntityAttrsFromDBRep( rs, entityNames );
           
            entityList.add(entity);
            log( entity.toString() );
        }
        rs.close();

        return;
    }

    public Object getResult() {
        ManagedEntityValue[] entities = (ManagedEntityValue[]) entityList.toArray( new ManagedEntityValue[0] );
        for ( int i=0, len=entities.length; i<len; i++ ) {
            log(entities[i].toString());
        }
        return entityList;
    }
    
    public void setWhereClauses ( WhereClause[] clauses ) {
        whereClauses = clauses;
        return;
    }
    
    public void setEntityNames ( String[] names ) {
        entityNames = names;
        return;
    }
    
}

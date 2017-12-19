/*
 * 
 */

package ossj.qos.fmri;

import java.util.ArrayList;
import javax.sql.DataSource;
import java.sql.ResultSet;
import javax.oss.ManagedEntityValue;
import java.sql.SQLException;

/**
 * QuerySingleUpdateDBCommandImpl
 * 
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
public class QuerySingleUpdateDBCommandImpl extends BaseDBCommandImpl {

    // generic result container
    private ArrayList entityList = new ArrayList();

    // select clause values
    private String[] selectNames = null;

    // where clause value template
    private ManagedEntityValue whereClause = null;

    /** Creates new QueryAlarmByTemplateOperation */
    public QuerySingleUpdateDBCommandImpl() {
    }

    public void initializeCmd() throws SQLException {

        // put where clause into array of where clauses used by the statement builder
        // method
        ManagedEntityValue[] whereClauses = { whereClause };

        // convert the value attribute names to their db names
        String[] dbNames = null;
        if ( selectNames != null ) {
            // get the database names of the managed entity names...
            dbNames = new String [selectNames.length];
            for ( int i = 0, len=selectNames.length; i<len; i++ ) {
                dbNames[i] = dbHelper.getDBAttributeName( selectNames[i] );
            }
        }

        // build the command form....
        String shell = StatementBuilder.buildGenericSelect( selectNames, whereClauses, dbHelper );

        // Need to add the following clause to the statement to ensure that the record is exclusively locked 

        //System.out.println("wrx--sql string:" + shell);
        //shell = new String ( shell + " FOR UPDATE AT ISOLATION SERIALIZABLE");
   
        // debug print...
        log("QuerySingleUpdateDBCommandImpl: Created shell : " + shell );

        // load the command form...
        loadCommandShell( shell );

        return;
    }

    public void executeCmd () throws SQLException {

        entityList.clear();

        // set the argument position
        int pos = 1;
        // load the entity values in the command form
        dbHelper.setManagedEntityInPrepStatement( whereClause, pos, ps );
        // execute the query...
        ResultSet rs = executeQueryCmd();

        // get the single query result
         if ( rs.next() ) {        
            ManagedEntityValue entity = dbHelper.setManagedEntityAttrsFromDBRep( rs, selectNames );
            
            entityList.add(entity);
            log( entity.toString() );
        }
        // close the result set
        rs.close();    
        return;
    }

    public Object getResult() {
        return (ManagedEntityValue[]) entityList.toArray( new ManagedEntityValue[0] );
    }

    public void setWhereClauseTemplate ( ManagedEntityValue template ) {
        whereClause = template;
        return;
    }

    public void setSelectNames ( String[] names ) {
        selectNames = names;
        return;
    }
    
}

package ossj.qos.fmri;

import javax.oss.ManagedEntityValue;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.HashMap;
import javax.oss.fm.monitor.AlarmValue;

/**
 * StatementBuilder
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public class StatementBuilder {

    private static HashMap dbHelpers = null;

    /** Creates new StatementBuilder */
    public StatementBuilder() {
    }

    // static initializer
    static {
        dbHelpers = new HashMap();
        dbHelpers.put( AlarmValue.VALUE_TYPE, new AlarmValueDBRep() );
    }

    /**
     * Build the SQL "update" statement (with the "?" placeholders)
     */
    public static String buildSQLUpdateCmd( ManagedEntityValue updateComponent, ManagedEntityValue[] whereComponents,
    ManagedEntityValueDBRep dbHelper )
    throws java.sql.SQLException
    {
        StringBuffer cmd = new StringBuffer(300);
        if ( updateComponent == null || whereComponents == null ||
        whereComponents.length == 0 ) {
            throw new SQLException( "missing components for Update command" );
        }
        else {
            String[] names = dbHelper.getDBAttributeNames( updateComponent );
            if ( names.length == 0 ) {
                throw new SQLException( "no attributes to Update command" );
            }
            int end = names.length-1;
            cmd.append( "Update "  + dbHelper.getTableName() + " SET ");

            for (int x=0; x<end; x++) {
                cmd.append(names[x] + " = ?, ");
            }
            //add the last one..
            cmd = cmd.append( names[end] + " = ? " );

            int numClause = whereComponents.length;
            cmd.append(" Where " );
            int total = total=whereComponents.length-1;
            for (int i=0; i<total; i++) {
                cmd.append( "( " );

                cmd.append( genGenericClause( dbHelper.getDBAttributeNames( whereComponents[i] ) ) );
                cmd.append( " ) OR " );
            }
            // add the last one..
            cmd.append( "( " );
            cmd.append( genGenericClause( dbHelper.getDBAttributeNames( whereComponents[total] ) ) );
            cmd.append( " )" );
        }
        return cmd.toString();
    }

    public static String buildSQLUpdateCmd( String[] names, ManagedEntityValue[] whereComponents,
    ManagedEntityValueDBRep dbHelper ) 
    throws SQLException {

        StringBuffer cmd = new StringBuffer(300);

        if ( names == null || names.length == 0 || whereComponents == null ||
        whereComponents.length == 0 ) {
            throw new SQLException( "missing components for Update command" );
        }

        int end = names.length-1;
        cmd.append( "Update "  + dbHelper.getTableName() + " SET ");

        for (int x=0; x<end; x++) {
            cmd.append(names[x] + " = ?, ");
        }
        //add the last one..
        cmd = cmd.append( names[end] + " = ? " );

        if ( whereComponents != null  && whereComponents.length > 0 ) {
            cmd.append(" Where " );
            int total = total=whereComponents.length-1;
            for ( int i = 0; i<total; i++ ) {
                cmd.append( "( " );
                cmd.append( genGenericClause( dbHelper.getDBAttributeNames( whereComponents[i] ) ) );
                cmd.append( " ) OR " );
            }
            // add the last one..
            cmd.append( "( " );
            cmd.append( genGenericClause( dbHelper.getDBAttributeNames( whereComponents[total] ) ) );
            cmd.append( " )" );
        }
        return cmd.toString();
    }

    public static String buildSQLDeleteCmd( ManagedEntityValue whereComponent, ManagedEntityValueDBRep dbHelper )
    throws java.sql.SQLException
    {
        StringBuffer cmd = new StringBuffer(300);
        if ( whereComponent == null ) {
            throw new SQLException( "missing components for the Delete command" );
        }
        else {
            cmd.append( "Delete FROM " + dbHelper.getTableName() );
            cmd.append(" Where " );
            cmd.append( genGenericClause( dbHelper.getDBAttributeNames( whereComponent ) ) );
        }
        return cmd.toString();
    }

    /*
     * Build the SQL "insert" statement (with the "?" placeholders)
     */
    public static String buildSQLInsertCmd( ManagedEntityValue template, ManagedEntityValueDBRep dbHelper )
    throws SQLException {

        String[] names = dbHelper.getDBAttributeNames( template );
        if ( names.length == 0 ) {
            throw new SQLException();
        }
        //SAMPLE FORMAT
        //"insert into TABLENAME (attrname1, attrname2) values (?, ?)";
        int end = names.length-1;

        StringBuffer cmd = new StringBuffer(300);
        cmd.append( "Insert into " + dbHelper.getTableName() + " ( " );

        for (int x=0; x<end; x++) {
            cmd.append(names[x] + ", ");
        }

        //add the last one..
        cmd = cmd.append( names[end] + " ) values (");

        //insert "?," for each value we will plug later.
        for (int x=0; x<end; x++) {
            cmd = cmd.append("?, ");
        }

        cmd = cmd.append("?)");  //The last "?"

        return cmd.toString();
    }

    public static String buildGenericSelect( String[] names, ManagedEntityValue[] whereComponents,
    ManagedEntityValueDBRep dbHelper ) {

        StringBuffer cmd = new StringBuffer(300);

        if ( names == null || names.length == 0 ) {
            cmd.append( "Select * FROM " + dbHelper.getTableName() );
        }
        else {
            int end = names.length-1;
            cmd.append( "Select " );

            for (int x=0; x<end; x++) {
                cmd.append(names[x] + ", ");
            }
            //add the last one..
            cmd = cmd.append( names[end] + " FROM " + dbHelper.getTableName() );
        }

        if ( whereComponents != null  && whereComponents.length > 0 ) {
            cmd.append(" Where " );
            int total = total=whereComponents.length-1;
            for ( int i = 0; i<total; i++ ) {
                cmd.append( "( " );
                cmd.append( genGenericClause( dbHelper.getDBAttributeNames( whereComponents[i] ) ) );
                cmd.append( " ) OR " );
            }
            // add the last one..
            cmd.append( "( " );
            cmd.append( genGenericClause( dbHelper.getDBAttributeNames( whereComponents[total] ) ) );
            cmd.append( " )" );
        }
        return cmd.toString();
    }

    private static String genGenericClause( String[] names ) {

        int end = names.length-1;

        StringBuffer cmd = new StringBuffer(300);

        for (int x=0; x<end; x++)
        {
            cmd.append(names[x] + " = ? AND ");
        }
        //add the last one..
        cmd = cmd.append( names[end] + " = ? ");
        return cmd.toString();
    }

    public static String buildSpecialSelect( String[] names, String tableName, WhereClause[] whereComponents ) {

        StringBuffer cmd = new StringBuffer(300);
        if ( names == null || names.length == 0 ) {
            cmd.append( "Select * FROM " + tableName );
        }
        else {
            int end = names.length-1;
            cmd.append( "Select " );

            for (int x=0; x<end; x++)
            {
                cmd.append(names[x] + ", ");
            }
            //add the last one..
            cmd = cmd.append( names[end] + " From " + tableName);
        }
        if ( whereComponents != null && whereComponents.length > 0 ) {
            cmd.append(" Where " );
            int total = whereComponents.length-1;
            for (int i=0; i<total; i++)
            {
                cmd.append( "( " );
                cmd.append ( whereComponents[i].buildPrepStatementClause() );
                cmd.append( " ) OR " );
            }
            // add the last one..
            cmd.append( "( " );
            cmd.append( whereComponents[total].buildPrepStatementClause() );
            cmd.append( " )" );
        }
        return cmd.toString();
    }

    static public ManagedEntityValueDBRep getDBHelper( String type ) {
        return (ManagedEntityValueDBRep) dbHelpers.get( type );
    }

}
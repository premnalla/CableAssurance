package ossj.qos.fmri;

import javax.naming.Context;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.sql.Connection;

/**
 * DBCommand
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public interface DBCommand {
    
    /**
     * Gets the Connection.
     *
     */
    public Connection getConnection(); 
    
    /**
     * Sets the Connection.
     *
     */
    public void setConnection( Connection con); 
        
    /** 
     * Executes the command.
     *
     * @exception java.sql.SQLException Is thrown to report a failure 
     * in the execution of the database command.
     */
    public void executeCmd() throws java.sql.SQLException;
    
    /** 
     * Initializes the command.
     *
     * @exception java.sql.SQLException Is thrown to report a failure 
     * in the initialize the database command.
     */
    public void initializeCmd() throws java.sql.SQLException;
       
    /** 
     * Performs any cleanup on resources used by the command.
     *
     */
    public void closeCmd();
    
    /** 
     * Returns the result of the command. 
     *
     */
    public Object getResult();  
        
}


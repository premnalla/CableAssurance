package ossj.ttri;


/**
 * Operation Class
 *
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */


public interface Operation extends Passivatable {

    public void setProperties(PropertyList props);   //free format parameters

    public void initialize()                         //initial set up
            throws javax.ejb.ObjectNotFoundException,
            javax.oss.IllegalArgumentException,
            java.sql.SQLException;

    public void cleanup();                           //cleanup - release resources

    public Object[] getNext(int howMany)             //to get operation results
            throws TTIteratorException;

    public void abort();                            //to abort a lengthy operation

}

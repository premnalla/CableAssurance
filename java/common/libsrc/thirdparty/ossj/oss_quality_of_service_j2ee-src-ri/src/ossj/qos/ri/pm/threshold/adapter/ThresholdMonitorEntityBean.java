/*
 * Copyright: Copyright ?2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */
package ossj.qos.ri.pm.threshold.adapter;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import java.util.Vector;

// api imports
import javax.oss.pm.threshold.ThresholdMonitorKey;
import javax.oss.pm.threshold.ThresholdMonitorValue;
import javax.oss.pm.measurement.PerformanceMonitorKey;

// ri imports
//import ossj.qos.pm.util.Log;


import javax.ejb.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import javax.naming.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;



/**
 * A container-managed persistent entity bean.
 *
 * <p>Data stored by this bean:
 * <pre>
 * thresholdMonitorPrimaryKey   String          CHAR(32), NOT NULL
 * thresholdMonitorKey          Serializable    SERIALIZE
 * thresholdMonitorValue        Serializable    SERIALIZE
 * thresholdMonitorStatus       int             INTEGER
 * performanceMonitorPrimaryKey String          CHAR(32)
 * performanceMonitorKey        Serializable    SERIALIZE
 * thresholdAlarmPrimaryKey     String          CHAR(32)
 * thresholdAlarmNotificationId long            LONGINT
 * </pre>
 * The thresholdMonitorPrimaryKey and performanceMonitorKey is used to make it
 * easier to search the entities.
 *
 * @see ossj.qos.ri.pm.threshold.adapter.ThresholdMonitorBean
 * @author Henrik Lindstrom, Ericsson
 */
public  class ThresholdMonitorEntityBean implements EntityBean {

    // fields
    protected EntityContext ctx;
    protected String thresholdMonitorPrimaryKey;
    protected ThresholdMonitorKey thresholdMonitorKey;
    protected ThresholdMonitorValue thresholdMonitorValue;
    protected int thresholdMonitorStatus;
    protected  String performanceMonitorPrimaryKey;
    protected PerformanceMonitorKey performanceMonitorKey;
    protected String  thresholdAlarmPrimaryKey ;
    protected long thresholdAlarmNotificationId;
    private DataSource dataSource;
    // end fields

    public ThresholdMonitorEntityBean() {
        //Log.write(this,Log.LOG_ALL,"ThresholdMonitorEntityBean","created");
    }

    public  String getThresholdMonitorPrimaryKey( ) {

            return thresholdMonitorPrimaryKey;
     }
    public  void setThresholdMonitorPrimaryKey( String id ) {

              thresholdMonitorPrimaryKey = id;
    }
    public ThresholdMonitorKey getThresholdMonitorKey() {

          return thresholdMonitorKey;
    }
    public  void setThresholdMonitorKey( ThresholdMonitorKey key ) {

             thresholdMonitorKey = key;
    }
    public  ThresholdMonitorValue getThresholdMonitorValue() {

            return thresholdMonitorValue;
    }
    public void setThresholdMonitorValue( ThresholdMonitorValue value ) {

           thresholdMonitorValue = value;
    }
    public  int getThresholdMonitorStatus() {

               return thresholdMonitorStatus;
    }
    public  void setThresholdMonitorStatus( int status ) {

          thresholdMonitorStatus = status;
    }
    public  String getPerformanceMonitorPrimaryKey() {

            return performanceMonitorPrimaryKey;
    }
    public  void setPerformanceMonitorPrimaryKey( String key ) {

            performanceMonitorPrimaryKey = key;
    }
    public void setPerformanceMonitorKey( PerformanceMonitorKey key ) {

           performanceMonitorKey = key;
    }
    public  PerformanceMonitorKey getPerformanceMonitorKey() {

             return performanceMonitorKey;
   }

    public  String getThresholdAlarmPrimaryKey() {

           return thresholdAlarmPrimaryKey;
    }

    public  void setThresholdAlarmPrimaryKey(String primaryKey) {

               thresholdAlarmPrimaryKey = primaryKey;
     }

    public  void setThresholdAlarmNotificationId( long id ) {
 
               thresholdAlarmNotificationId = id;
    }

    public  long getThresholdAlarmNotificationId() {

             return thresholdAlarmNotificationId;
    }

    //
    // EJB required methods follows
    //

    /**
     * Called by container. Implementation can require needed resources.
     */
    public void ejbActivate() {
        //Log.write(this,Log.LOG_ALL,"ejbActivate()","called");
    }

    /**
     * EJB Container calls this method right before it removes the entity bean
     * from the database. Corresponds to when client calls home.remove().
     */
    public void ejbRemove() {
      //Log.write(this,Log.LOG_ALL,"ejbRemove()","called");
      //VP implement the removal from the DB
	  System.out.println("VP----- remove key =["+thresholdMonitorPrimaryKey+"]");
      try {
          deleteByKey(thresholdMonitorPrimaryKey);
      } 
      catch (SQLException sqlex){
          sqlex.printStackTrace();
      }
      catch (ObjectNotFoundException onfex){
          onfex.printStackTrace();
      }
      // end VP

    }

    /**
     * Called by Container. Releases held resources for passivation.
     */
    public void ejbPassivate() {
        //Log.write(this,Log.LOG_ALL,"ejbPassivate()","called");
    }

    /**
     * Called from the Container. Updates the entity bean instance to reflect
     * the current value stored in the database. Since this is a container-managed
     * persistence it can be left blank. The EJB container will automatically
     * load the data in the subclass.
     */
    public void ejbLoad() {
        //Log.write(this,Log.LOG_ALL,"ejbLoad()","called");
		//VP TODO, restore state form DB
    }

    /**
     * Called from the container. Updates the database to reflect the current
     * values of this in-memory entity bean instance representation. Since this
     * is a container-managed persistence it can be left blank. The EJB container
     * will automatically save the data in the subclass.
     */
    public void ejbStore() {
        //Log.write(this,Log.LOG_ALL,"ejbStore()","called");
		// VP TODO, store the current state in the DB
    }

    /**
     * Called by container. Associates this bean instance with a particular
     * context. When it is done it is possible to get the environment info from
     * context.
     * @param ctx the entity context
     */
    public void setEntityContext( EntityContext ctx ) {
        this.ctx = ctx;
        try {
        InitialContext ic = new InitialContext();
        //System.out.println("wrx--lookup datasource");
        //VP dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/ossjqostmri");
        dataSource = (DataSource) ic.lookup("jdbc/ossjqostmri");
        //System.out.println("wrx--after lookup");
      } catch (Exception ex) {
          System.out.println(ex);
          throw new EJBException("Unable to connect to database. " +
             ex.getMessage());
      }

    }

    /**
     * Called by container. Remove the association of the particular context
     * environment for this bean.
     */
    public void unsetEntityContext() {
        //Log.write(this,Log.LOG_ALL,"unsetEntityContext()","called");
        this.ctx = null;
    }

    /**
     * This is the initialization method that corresponds to the create() method
     * in the home interface. When the client calls the home object's create()
     * method, the home object then calls this ejbCreate() method. The bean's
     * fields are initialized with the arguments passed from the client, so that
     * the container can create the corresponding database entries in the subclass
     * after this method completes.
     *
     * <p>The ThresholdMonitorPrimaryKey and PerformanceMonitorPrimaryKey is extracted from
     * the ThresholdMonitorKey and PerformanceMonitorKey.
     *
     * @param thresholdMonitorKey the primary key for this threshold monitor
     * @param value the threshold monitor value
     * @return primary key for threshold monitor
     */
    public String ejbCreate( ThresholdMonitorKey thresholdMonitorKey,
                             ThresholdMonitorValue value,
                             PerformanceMonitorKey performanceMonitorKey ) throws javax.ejb.CreateException {

        PerformanceMonitorKey pmKey = null;
        String pmPrimaryKey = "" ;

	String primaryKey = thresholdMonitorKey.getThresholdMonitorPrimaryKey();

        int status = ThresholdMonitorStatus.THRESHOLD_CLEARED;
        if ( performanceMonitorKey!= null ) {
             pmKey = performanceMonitorKey;
             pmPrimaryKey =  performanceMonitorKey.getPerformanceMonitorPrimaryKey();
        }

        try {
         insertRow(primaryKey,thresholdMonitorKey,value,status,pmPrimaryKey,pmKey,"irene",0L);
      } catch (Exception ex) {
          //System.out.println("wrx--insertRow exceptoin");
          System.out.println(ex);
          throw new EJBException("ejbCreate: " +
             ex.getMessage());
      }

        this.thresholdMonitorPrimaryKey = primaryKey;
        this.thresholdMonitorKey = thresholdMonitorKey;
        this.thresholdMonitorValue = value;
        this.thresholdMonitorStatus = status;
        this.performanceMonitorPrimaryKey = pmPrimaryKey;
        this.performanceMonitorKey = pmKey;
        this.thresholdAlarmPrimaryKey = "";
        this.thresholdAlarmNotificationId = 0L;


        return primaryKey;
    }

    private void insertRow (String tmPrimaryKey,ThresholdMonitorKey tmKey,
    ThresholdMonitorValue tmValue,int status,String pmPrimaryKey,
    PerformanceMonitorKey pmKey,String alarmPrimaryKey,long notify)
                                              throws SQLException {

     
 
      //System.out.println("wrx--before getconnection");
      Connection con = dataSource.getConnection();
      //System.out.println("wrx--after getconnection");
      //VP System.out.println("tmKey: " + tmKey);
      //VP System.out.println("tmValue: " + tmValue);
      //VP System.out.println("pmKey: " + pmKey);
      String insertStatement =
            "insert into THRESHOLDMONITORENTITYBEAN  values (?,?,?,?,?,?,?,?)";
      PreparedStatement prepStmt =
            con.prepareStatement(insertStatement);

      prepStmt.setString(1, tmPrimaryKey);
      //VP prepStmt.setObject(2, tmKey);
	  try {
			ByteArrayOutputStream   ba2         = new ByteArrayOutputStream();
			ObjectOutputStream      bos2        = new ObjectOutputStream(ba2 );
			bos2.writeObject(tmKey);
			bos2.flush();
			byte                    abyte2[]    = ba2.toByteArray();
			ByteArrayInputStream    bi2         = new ByteArrayInputStream(abyte2 );
			prepStmt.setBinaryStream(2, bi2, abyte2.length );

      // prepStmt.setObject(3, tmValue);
			ByteArrayOutputStream   ba3         = new ByteArrayOutputStream();
			ObjectOutputStream      bos3        = new ObjectOutputStream(ba3 );
			bos3.writeObject(tmValue);
			bos3.flush();
			byte                    abyte3[]    = ba3.toByteArray();
			ByteArrayInputStream    bi3         = new ByteArrayInputStream(abyte3 );
			prepStmt.setBinaryStream(3, bi3, abyte3.length );

      prepStmt.setInt(4, status);
      prepStmt.setString(5,pmPrimaryKey);
      // prepStmt.setObject(6,pmKey);
			ByteArrayOutputStream   ba6         = new ByteArrayOutputStream();
			ObjectOutputStream      bos6        = new ObjectOutputStream(ba6 );
			bos6.writeObject(pmPrimaryKey);
			bos6.flush();
			byte                    abyte6[]    = ba6.toByteArray();
			ByteArrayInputStream    bi6         = new ByteArrayInputStream(abyte6 );
			prepStmt.setBinaryStream(6, bi6, abyte6.length );

		} catch (Exception ex){
				throw new SQLException (ex.getClass().getName()+" when writting blob...");
		}
		//end VP
      prepStmt.setString(7, alarmPrimaryKey);
      prepStmt.setLong(8,notify);
      

      prepStmt.executeUpdate();
      prepStmt.close();
      con.close();
   }


    /**
     * Called after ejbCreate(). Now it is possible for the bean to retrieve its
     * EJBObject from its context, and pass it as a 'this' argument.
     * @param thresholdMonitorKey the primary key for this threshold monitor
     * @param value the threshold monitor value
     */
    public void ejbPostCreate( ThresholdMonitorKey thresholdMonitorKey,
                               ThresholdMonitorValue value,
                               PerformanceMonitorKey performanceMonitorKey ) throws javax.ejb.CreateException {
        //Log.write(this,Log.LOG_ALL,"ejbPostCreate()","called");
    }

   public String ejbFindByPrimaryKey(String primaryKey)
      throws FinderException {

      boolean result;

      try {
         result = selectByPrimaryKey(primaryKey);
		 //VP System.out.println("VP----- select return result = ["+result+"]");
       } catch (Exception ex) {
           throw new EJBException("ejbFindByPrimaryKey: " +
              ex.getMessage());
       }

      if (result) {
         return primaryKey;
      }
      else {
         throw new ObjectNotFoundException
            ("Row for id " + primaryKey + " not found.");
      }
   }

    private boolean selectByPrimaryKey(String primaryKey)
      throws SQLException {

      Connection con = dataSource.getConnection();
      String selectStatement =
            "select THRESHOLDMONITORPRIMARYKEY " +
            "from THRESHOLDMONITORENTITYBEAN  where THRESHOLDMONITORPRIMARYKEY = ? ";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);
      prepStmt.setString(1, primaryKey);

      ResultSet rs = prepStmt.executeQuery();
      boolean result = rs.next();
      prepStmt.close();
      con.close();
      return result;
   }

   public Collection  ejbFindAllThresholds()
      throws FinderException {

      Collection allThresholds ;

      try {
         allThresholds = selectAllThresholds();
       } catch (Exception ex) {
           throw new EJBException("ejbFindAllThresholds: " +
              ex.getMessage());
       }
       return allThresholds;
   }

   private Collection selectAllThresholds()
      throws SQLException {

      Connection con = dataSource.getConnection();
      ArrayList a = new ArrayList();

      String selectStatement =
            "select THRESHOLDMONITORPRIMARYKEY " +
            "from THRESHOLDMONITORENTITYBEAN  ";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);

      ResultSet rs = prepStmt.executeQuery();

      while (rs.next()) {
         String tmKey = rs.getString(1);
         a.add(tmKey);
      }

      prepStmt.close();
      con.close();
      return a;
    }

   public Collection  ejbFindThresholdsWithPerformanceMonitorPrimaryKey(String pmKey)
      throws FinderException {

      Collection theThresholds ;

      try {
         theThresholds = selectThresholdsWithPerformanceMonitorPrimaryKey(pmKey);
       } catch (Exception ex) {
           throw new EJBException("ejbFindThresholdsWithPerformanceMonitorPrimaryKey: " +
              ex.getMessage());
       }
       return theThresholds;
   }

   private Collection selectThresholdsWithPerformanceMonitorPrimaryKey(String pmKey)
      throws SQLException {

      Connection con = dataSource.getConnection();
      ArrayList a = new ArrayList();

      String selectStatement =
            "select THRESHOLDMONITORPRIMARYKEY " +
            "from THRESHOLDMONITORENTITYBEAN where PERFORMANCEMONITORPRIMARYKEY=?  ";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);
      prepStmt.setString(1, pmKey);

      ResultSet rs = prepStmt.executeQuery();

      while (rs.next()) {
         String tmKey = rs.getString(1);
         a.add(tmKey);
      }

      prepStmt.close();
      con.close();
      return a;
    }
    //VP add removal
    private void deleteByKey(String tmKey)
        throws SQLException,
               ObjectNotFoundException
    {
		Connection con = dataSource.getConnection();
      
        String cmd = "DELETE FROM THRESHOLDMONITORENTITYBEAN WHERE THRESHOLDMONITORPRIMARYKEY = ?";
		System.out.println("VP------- deletebykey the row =["+tmKey+"]");
        PreparedStatement ps = con.prepareStatement(cmd);
        ps.setString(1,tmKey);   //set the trId in the WHERE clause
        ps.executeUpdate();
      
		ps.close();
      	con.close();
      
    }
    //end VP

}


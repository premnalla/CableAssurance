package ossj.ttri;

// KS 27-08-2003 added

import javax.ejb.ObjectNotFoundException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.oss.IllegalAttributeValueException;
import javax.oss.MultiValueList;
import javax.oss.fm.monitor.AlarmValue;
import javax.oss.trouble.*;
import javax.sql.DataSource;
import java.io.*;
import java.sql.*;
import java.util.Vector;


/**
 * Database Access Class
 * Database access singleton.  Manages the DB connection pool and
 * encapsulates all of the JDBC/SQL methods for TT related access.
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */

// KS 26-08-2003 see below
// KS 27-08-2003 modified methods plugAttrValues(), applyMVAUpdate,
//               populateTTValueFromSelectedResultSet() and populateTTValueFromResultSet() regarding DB access
// KS 28-08-2003 added helper class DBAccessHelper


public class DBAccess {

    private static final boolean VERBOSE = true;
    private static DataSource dataSource = null;

    //singleton pattern
    static private DBAccess singleton = new DBAccess();

    static public final DBAccess getInstance() {
        return singleton;
    }

    //protected ctor for singleton
    protected DBAccess()
            //throws javax.naming.NamingException
    {

        InitialContext initCtx = null;
        log("looking up the datasource ");
        try {
            //Wipro
//           dataSource = (javax.sql.DataSource)
//                    initCtx.lookup("java:comp/env/jdbc/ossjttri");
            initCtx = new InitialContext();
            String dsName=TTHelper.projectProperties.getProperty("TT_DS_NAME");
            log("dsName is "+dsName);
            dataSource = (javax.sql.DataSource)
                    initCtx.lookup(dsName);
            //Wipro
        } catch (NamingException ne) {
            log("Failed to lookup JDBC Datasource. Please double check that");
            log("the JNDI name defined in the resource-description of the ");
            log("EJB's weblogic-ejb-jar.xml file is the same as the JNDI name ");
            log("for the Datasource defined in your config.xml.");
        } finally {
            try {
                if (initCtx != null) initCtx.close();
            } catch (NamingException ne) {
                log("Error closing context: " + ne);
            }
        }

    }


    //--------------------------------------------------------------------------
    // checks if the specified key exists.  Used by ejbFindByPrimaryKey
    //-------------------------------------------------------------------------
    public static boolean findByPrimaryKey(String ttId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBAccess.getConnection();
            ps = con.prepareStatement("select troubledescription from troubleticket where trid = ?");
            ps.setString(1, ttId);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            if (!rs.next()) {
                log("DBAccess.findByPrimaryKey: ttId (" + ttId + ") not found");
                return false;
            }
        } catch (SQLException sqe) {
            log("SQLException:  " + sqe);
            return false;
        } finally {
            //log("CLOSEDB 1");
            DBAccess.closeDB(con, ps);
        }
        return true;

    }


    //--------------------------------------------------------------------------
    // reads in a single tt given a key.  Sets the passed ttVals values.
    //--------------------------------------------------------------------------
    public static void loadTroubleTicket(String ttId, TroubleTicketValue ttVal)
            throws ObjectNotFoundException,
            SQLException {

        Connection con = null;
        PreparedStatement ps = null;

        //log("preparing SELECT statement");
        try {
            con = DBAccess.getConnection();
            ps = con.prepareStatement("select * from TROUBLETICKET where TRID = ?");
            ps.setString(1, ttId);

            // log("Executing select...");
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            if (rs.next()) {
                DBAccess.populateTTValueFromResultSet(ttVal, rs);
            } else {
                String error = "ejbLoad: AccountBean (" + ttId + ") not found";
                log(error);
                throw new ObjectNotFoundException(error);
            }
        } catch (SQLException sqe) {
            log("SQLException in DBAccess.loadTroubleTicket:  " + sqe);
            throw sqe;
        } finally {
            //log("CLOSEDB 2");
            DBAccess.closeDB(con, ps);
        }
    }






    //---------------------------------------------------------------------------------
    //
    // Best effort API methods - select, insert, update
    //
    // For best effort, all DB access is on a "per row" basis. According to the TT spec,
    // we need to set failure responses for each of the rows in the target set.
    //
    // e.g. the semantic for an update is to first select the TRIds that correspond
    // to the desired rows you want to update, them effectuate the update on a row
    // by row basis.  This allows us to correctly populate the response with the TT keys
    // that have been affected by the update.
    //
    //---------------------------------------------------------------------------------

    //public static ResultSet PG just return false if not implemented
    public static boolean checkMatchAll(TroubleTicketValue[] templates) {
        for (int i = 0; i < templates.length; i++) {
            TroubleTicketValue val = templates[i];
            if (val.getPopulatedAttributeNames().length == 0) {
                return true;
            }
        }
        return false;
    }

    public static Vector selectKeys(String[] attrs, //desired attributes
                                    TroubleTicketValue[] templates
                                    )	//where clause
            throws SQLException {

        //log("---ResultSet select Called----");
        Vector ttKeys = new java.util.Vector();
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //PG Check Templates if anyone of them has no attributes then
        //we match all !!!
        //if we're doing a tryClose and match all is true then our
        //comamnd should be a select for all non CLOSED trouble tickets
        //i.e TroubleState != CLOSED the same for cancel ???
        //con = getConnection();
        //ps = con.prepareStatement("select * from TROUBLETICKET where TroubleState <> ?");
        //ps.setInt(1, TroubleState.CLOSED);
        //ps.executeQuery();

        //VP
//if(checkMatchAll( templates ) ) {
        ps = con.prepareStatement("select * from TROUBLETICKET");
        ps.executeQuery();
        rs = ps.getResultSet();
//VP
/*
            }
		else {

		    String cmd = DBAccess.buildSelectString(attrs,templates);
		    ps = con.prepareStatement(cmd);
		    DBAccess.insertAttrValues(ps,templates,null);
		    rs = DBAccess.executeSelect(ps);
            }
          */
        TroubleTicketKey ttKey;

        if (rs == null) log("NULL rs!");
        while (rs.next()) {
//VP Compare templates
            if (templates != null) {
                TroubleTicketValueImpl ttValue = new TroubleTicketValueImpl();
                DBAccess.populateTTValueFromResultSet(ttValue, rs);

                int ntemplates = templates.length;
                for (int ii = 0; ii < ntemplates; ii++) {
                    if (utils.compareValues(templates[ii], ttValue)) {
//end VP

                        ttKey = new TroubleTicketKeyImpl();
                        ttKey.setApplicationDN(TTConfig.getInstance().getApplicationTypeDN());
                        ttKey.setApplicationContext(TTConfig.getInstance().getApplicationContext());
                        log("BestEffortTemplateOp selected Key: " + rs.getString(1));
                        ttKey.setTroubleTicketPrimaryKey(rs.getString(1));
                        ttKeys.add(ttKey);
// VP
                        break;
                    }
                }
            } else {
                ttKey = new TroubleTicketKeyImpl();
                ttKey.setApplicationDN(TTConfig.getInstance().getApplicationTypeDN());
                ttKey.setApplicationContext(TTConfig.getInstance().getApplicationContext());
                log("BestEffortTemplateOp selected Key: " + rs.getString(1));
                ttKey.setTroubleTicketPrimaryKey(rs.getString(1));
                ttKeys.add(ttKey);
            }
//end VP

        }

        closeDB(con, ps);
        return ttKeys; //can't close here because the client needs the ResultSet
//return rs;
    }

    public static Vector selectValues(String[] attrNames, //desired attributes
                                      TroubleTicketValue[] templates
                                      )	//where clause
            throws SQLException {
        Vector resultVect = new java.util.Vector();

        //log("---ResultSet select Called----");
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //Check Templates if anyone of them has no attributes then
        //we match all
//VP		if(checkMatchAll( templates ) ) {
        ps = con.prepareStatement("select * from TROUBLETICKET");
        ps.executeQuery();
        rs = ps.getResultSet();
//VP
/*}
            else {

            String cmd = DBAccess.buildSelectString(attrNames,templates);
		    ps = con.prepareStatement(cmd);
		    DBAccess.insertAttrValues(ps,templates,null);
		    rs = DBAccess.executeSelect(ps);
            }
          */

        TroubleTicketKey ttKey = null;
        TroubleTicketValue ttValue = null;


        while (rs.next()) {
//log ("-----Processing rs.next-----");
            ttValue = new TroubleTicketValueImpl();
            if (attrNames != null) {
                if (attrNames.length > 0)
                    DBAccess.populateTTValueFromSelectedResultSet(attrNames, ttValue, rs);
                else
                    DBAccess.populateTTValueFromResultSet(ttValue, rs);
            } else {
//log ("-----Calling DBAccess.populateTTValueFromResultSet-----");
                DBAccess.populateTTValueFromResultSet(ttValue, rs);
//log ("-----END of DBAccess.populateTTValueFromResultSet-----");
            }

//VP Compare templates:
            if (templates != null) {
                int ntemplates = templates.length;
                for (int ii = 0; ii < ntemplates; ii++) {
                    if (utils.compareValues(templates[ii], ttValue)) {
                        log("Select using template this Value is OK");
                        resultVect.add(ttValue);
                        break;
                    }
                }
            } else {
                resultVect.add(ttValue);
            }
//end VP

//VP   resultVect.add(ttValue);

        }
        closeDB(con, ps);
        return resultVect;
    }


    public static TroubleTicketValue selectByKey(String ttId,
                                                 String[] attrs)
            throws SQLException {
        String cmd = DBAccess.buildSelectString(attrs, null);
        cmd = cmd.concat(" WHERE trid = ?");
        //VP
        log("DBA selectByKey using cmd = " + cmd);

        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(cmd);
        ps.setString(1, ttId);   //set the trId in the WHERE clause

        ResultSet rs = DBAccess.executeSelect(ps);

        //must position to first (only) row
        if (!rs.next()) {
            log("DBAccess.selectByKey: ttId (" + ttId + ") not found");
            return null;
        }

        TroubleTicketValue ttVal = new TroubleTicketValueImpl();
        //populate the key - it probably won't be in the selected attrs
        TroubleTicketKey ttKey = new TroubleTicketKeyImpl(ttId);
        ttKey.setApplicationDN(TTConfig.getInstance().getApplicationTypeDN());
        ttKey.setApplicationContext(TTConfig.getInstance().getApplicationContext());
        ttVal.setTroubleTicketKey(ttKey);


        //populate the rest of the ttVal with the result set.
        if (attrs != null) {
            if (attrs.length > 0)
                populateTTValueFromSelectedResultSet(attrs, ttVal, rs);
            else
                populateTTValueFromResultSet(ttVal, rs);
        } else
            populateTTValueFromResultSet(ttVal, rs);


        //log("CLOSEDB 3");
        closeDB(con, ps);

        log("DBAccess.selectByKey returning ttVal: ");
        log("        Key: " + ttVal.getTroubleTicketKey().getTroubleTicketPrimaryKey());
        log("Description: " + ttVal.getTroubleDescription());

        return ttVal;
    }


    public static int insert(TroubleTicketValue ttValue, boolean lastUpdate)
            throws SQLException {

//lastUpdate = false;
        String ttId = DBAccess.getTTKey(ttValue);
        if (ttId == null)
            throw new IllegalArgumentException("DBAccess.insert: Invalid Trouble Ticket key");

        String cmd = DBAccess.buildInsertString(ttValue, lastUpdate);
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(cmd);
        DBAccess.insertAttrValues(ps, ttValue, ttId, lastUpdate);
        int count = DBAccess.executeUpdate(ps);

//log("CLOSEDB 4");
        closeDB(con, ps);
        return count;
    }


    public static int updateByKey(TroubleTicketValue ttValue, boolean lastUpdate)
            throws SQLException,
            ObjectNotFoundException,
            IllegalAttributeValueException {
        //lastUpdate = false;

        String ttId = DBAccess.getTTKey(ttValue);
        log("DBAccess.updateByKey: Key: " + ttId);

        if (ttId == null) {
            String badKey = new String("DBAccess.update: Invalid Trouble Ticket key");
            log(badKey);
            throw new IllegalArgumentException(badKey);
        }

//true is used to indicate that the lastUpdateVersionNumber
//should be in
        String cmd = DBAccess.buildUpdateString(ttValue, ttId, lastUpdate);
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(cmd);
        DBAccess.insertAttrValues(ps, ttValue, ttId, lastUpdate);

        log("ps = " + ps);
        int count = DBAccess.executeUpdate(ps);

        if (count == 1)
            log("DBAccess.updateByKey:   Key: " + ttId + " updated");

//log("CLOSEDB 5");
        closeDB(con, ps);
        return count;

    }

    //VP add removal
    public static void deleteByKey(TroubleTicketValue ttValue)
            throws SQLException,
            ObjectNotFoundException {
        String ttId = DBAccess.getTTKey(ttValue);
        log("DBAccess.deleteByKey: Key: " + ttId);

        if (ttId == null) {
            String badKey = new String("DBAccess.deleteByKey: Invalid Trouble Ticket key");
            log(badKey);
            throw new IllegalArgumentException(badKey);
        }

        String cmd = "DELETE FROM TROUBLETICKET WHERE trid = ?";
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(cmd);
        ps.setString(1, ttId);   //set the trId in the WHERE clause
        ps.executeUpdate();
        closeDB(con, ps);

    }
    //end VP



    //--------------------------------------------------------------------------
    // builds an SQL SELECT command string
    //--------------------------------------------------------------------------
    public static String buildSelectString(String[] attrNames, //attrs to return
                                           TroubleTicketValue[] templates)    	//where clause
    {

        //SAMPLE FORMAT
        //"select trid, description, from TroubleTicket where (x = ? AND y = ?)

        String cmd = new String("SELECT ");

        printStringArray(attrNames, "attrNames 1");

        //null or zero length attrs means select all
        if ((attrNames == null) ||
                ((attrNames != null) && (attrNames.length == 0))) {
            cmd = cmd.concat("* ");
        } else {
            cmd = plugAttrNames(attrNames, cmd);
        }

        log("Interim cmd: " + cmd);

        cmd = cmd.concat(" FROM TroubleTicket");

        //VP if (templates != null)
        //VP    cmd = cmd.concat(DBAccess.buildWhereString(templates));

        log("Built SQL SELECT statement: " + cmd);
        log("=========>>>>Built SQL SELECT statement: " + cmd);
        return cmd;

    }

    //--------------------------------------------------------------------------
    // builds an SQL insert command string.
    // Issue: ttKey!
    //--------------------------------------------------------------------------
    public static String buildInsertString(TroubleTicketValue ttVal, boolean lastUpdate) {
        //lastUpdate = false;
        //SAMPLE FORMAT
        //"insert into TroubleTicket (trid, description, ...) values (?, ?, ...)";
        String cmd = new String("insert into TROUBLETICKET (");

        Vector popAttrNames = ((TroubleTicketValueImpl) ttVal).populatedAttributeNames;
        int noAttrs = popAttrNames.size();
        int x = 0;

        if (lastUpdate == true) {
            cmd = cmd.concat("lastUpdateVersionNumber");
            cmd = cmd.concat(",");
        }

        for (x = 0; x < noAttrs; x++) {
            cmd = cmd.concat((String) (popAttrNames.elementAt(x)));
            cmd = cmd.concat(",");
        }

        cmd = cmd.concat("trid) values (");      //add the TRID (key)

        //VP
        if (lastUpdate == true) cmd = cmd.concat("?, "); //One more for the lastUpdate
        for (x = 0; x < noAttrs; x++) {
            cmd = cmd.concat("?, ");
        }
        cmd = cmd.concat("?)");  //FOR TRID end values string
        // end VP

        log("Built SQL INSERT statement: " + cmd);

        return cmd;

    }

    //-----------------------------------------------------------------------------
    // builds an SQL UPDATE command with a where clause that has only a primary key.
    // Used for best effort semantic where updates are done on individual rows.
    //-----------------------------------------------------------------------------
    public static String buildUpdateString(TroubleTicketValue ttVal, //value to set
                                           String ttId, boolean lastUpdate)
            throws ObjectNotFoundException,
            SQLException,
            IllegalAttributeValueException {
        //lastUpdate = false;

        //SAMPLE FORMAT
        //ps = con.prepareStatement("update TROUBLETICKET set attr1 = ?, attr2 = ?, attr3 = ? where trid = ?");
        String cmd = new String("update TROUBLETICKET set ");
        Vector popAttrNames = ((TroubleTicketValueImpl) ttVal).populatedAttributeNames;

        int modifier;
        boolean modify = false;
        Vector mvas = new java.util.Vector();

        //The 4 MVA attributes we need to check.  No need to manipulate the array
        //elements if its a SET MVA operation - only ADD and REMOVE require this.
        if (popAttrNames.contains("ActivityDurationList")) {
            mvas.add(new String("ActivityDurationList"));
            modifier = ttVal.getActivityDurationList().getModifier();
            if ((modifier == MultiValueList.ADD) ||
                    (modifier == MultiValueList.REMOVE))
                modify = true;
        }
        if (popAttrNames.contains("RelatedAlarmList")) {
            mvas.add(new String("RelatedAlarmList"));
            modifier = ttVal.getRelatedAlarmList().getModifier();
            if ((modifier == MultiValueList.ADD) ||
                    (modifier == MultiValueList.REMOVE))
                modify = true;
        }
        if (popAttrNames.contains("EscalationList")) {
            mvas.add(new String("EscalationList"));
            modifier = ttVal.getEscalationList().getModifier();
            if ((modifier == MultiValueList.ADD) ||
                    (modifier == MultiValueList.REMOVE))
                modify = true;
        }
        if (popAttrNames.contains("RepairActivityList")) {
            mvas.add(new String("RepairActivityList"));
            modifier = ttVal.getRepairActivityList().getModifier();
            if ((modifier == MultiValueList.ADD) ||
                    (modifier == MultiValueList.REMOVE))
                modify = true;
        }

        if (modify)
            applyMVAUpdate(ttVal, ttId, mvas);

        int noAttrs = popAttrNames.size();

        if (lastUpdate == true) {
            //cmd = cmd.concat(", ");
            cmd = cmd.concat("lastUpdateVersionNumber" + " = ?");
            if (noAttrs > 0) cmd = cmd.concat(", ");
        }

        for (int x = 0; x < noAttrs; x++) {
            if (x > 0) cmd = cmd.concat(", ");
            cmd = cmd.concat(popAttrNames.elementAt(x) + " = ?");
        }
        cmd = cmd.concat(" where TRID = ?");

        log("Built SQL UPDATE String (with key): " + cmd);

        return cmd;
    }

    //-----------------------------------------------------------------------------
    // Applies MultiValue atribute update to MVA values in a TT Value object.
    // Reads the original value from the DB and applies the add or remove
    // operation to the MVA type attributes in the value object.
    //-----------------------------------------------------------------------------
    public static void applyMVAUpdate(TroubleTicketValue ttVal,
                                      String ttId,
                                      java.util.Vector mvaVector)
            throws java.sql.SQLException,
            javax.ejb.ObjectNotFoundException,
            javax.oss.IllegalAttributeValueException {


        String[] mvas = (String[]) mvaVector.toArray(new String[0]);
        //do the select
        String cmd = DBAccess.buildSelectString(mvas, null);
        cmd = cmd.concat(" WHERE trid = ?");
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(cmd);
        ps.setString(1, ttId);   //set the trId in the WHERE clause
        ResultSet rs = DBAccess.executeSelect(ps);

        //must position to first (only) row
        if (!rs.next()) {
            String notFound = new String("DBAccess.applyMVAUpdate: ttId (" + ttId + ") not found");
            log(notFound);
            throw new javax.ejb.ObjectNotFoundException(notFound);
        }

        Vector workVector = null;
        int modifier;


        ActivityDurationList origAdList = null;
        ActivityDurationList newAdList = null;
        ActivityDuration[] origAdArray = new ActivityDuration[0];
        ActivityDuration[] newAdArray = null;

        AlarmValueList origAlmList = null;
        AlarmValueList newAlmList = null;
        AlarmValue[] origAlmArray = new AlarmValue[0];
        AlarmValue[] newAlmArray = null;

        EscalationList origEscList = null;
        EscalationList newEscList = null;
        Escalation[] origEscArray = new Escalation[0];
        Escalation[] newEscArray = null;

        RepairActivityList origRaList = null;
        RepairActivityList newRaList = null;
        RepairActivity[] origRaArray = new RepairActivity[0];
        RepairActivity[] newRaArray = null;


        int x,y;

        for (x = 0; x < mvas.length; x++) {

// KS 27-08-2003 START
            try {
//-------------------------------------------------
// ActivityDurationList
//-------------------------------------------------
                if (mvas[x].equals("ActivityDurationList")) {

                    workVector = new java.util.Vector();

// KS 27-08-2003 modified
//              origAdList = (ActivityDurationList)rs.getObject("ActivityDurationList"); //original values
                    ObjectInputStream ois1 = new ObjectInputStream(rs.getBlob("ActivityDurationList").getBinaryStream());
                    origAdList = ((ActivityDurationList) ois1.readObject());

                    origAdArray = origAdList.get();
                    newAdList = ttVal.getActivityDurationList();
                    modifier = newAdList.getModifier();

//NB. don't need to check if the modifier is SET, this has been done
//by the caller.
                    if (modifier == MultiValueList.ADD) {
                        //put the old elements in the work vector
                        for (y = 0; y < origAdArray.length; y++) workVector.add(origAdArray[y]);
                        //add the new elements to the work vector
                        ActivityDuration[] tmpAdArray = newAdList.get();
                        for (y = 0; y < tmpAdArray.length; y++) workVector.add(newAdArray[y]);
                    } else if (modifier == MultiValueList.REMOVE) {
                        //put the old elements in the work vector
                        for (y = 0; y < origAdArray.length; y++) workVector.add(origAdArray[y]);

                        ActivityDuration[] rmvAdArray = newAdList.get();	//items to be removed
                        for (y = 0; y < rmvAdArray.length; y++) {
                            for (int z = 0; z < origAdArray.length; z++) {
                                ActivityDuration ad = (ActivityDuration) workVector.firstElement();
                                if (ad.equals(origAdArray[z])) {
                                    workVector.removeElementAt(y);
                                }
                            }
                        }

                    } else {
                        throw new IllegalAttributeValueException("ActivityDurationList",
                                "Invalid MultiValue attribute modifier " + modifier);
                    }

                    //transform back to array
                    newAdArray = (ActivityDuration[]) workVector.toArray(new ActivityDuration[0]);
                    //replace list in TT value with newly constructed one.
                    newAdList.set(newAdArray);
                    ttVal.setActivityDurationList(newAdList);


                } else
                //-------------------------------------------------
// AlarmValueList
//-------------------------------------------------
                    if (mvas[x].equals("RelatedAlarmList")) {
                        workVector = new java.util.Vector();

// KS 27-08-2003 modified
//			    origAlmList = (AlarmValueList)rs.getObject("AlarmValueList"); //original values
                        ObjectInputStream ois2 = new ObjectInputStream(rs.getBlob("RelatedAlarmList").getBinaryStream());
                        origAlmList = ((AlarmValueList) ois2.readObject());


//If original list is null, add is OK but remove is inapplicable.
                        if (origAlmList != null)
                            origAlmArray = origAlmList.get();
                        newAlmList = ttVal.getRelatedAlarmList(); //assume session has validated this.
                        modifier = newAlmList.getModifier();    //assume session has validated this.

//NB. don't need to check if the modifier is SET, this has been done
//by the caller.
                        if (modifier == MultiValueList.ADD) {
                            //put the old elements in the work vector
                            for (y = 0; y < origAlmArray.length; y++) workVector.add(origAlmArray[y]);
                            //add the new elements to the work vector
                            AlarmValue[] tmpAlmArray = newAlmList.get();
                            for (y = 0; y < tmpAlmArray.length; y++) workVector.add(tmpAlmArray[y]);
                        } else if (modifier == MultiValueList.REMOVE) {

                            if (origAlmList == null) {
                                throw new IllegalAttributeValueException
                                        ("RelatedAlarmList", "Attempted REMOVE operation on empty list");
                            }

                            //put the old elements in the work vector
                            for (y = 0; y < origAlmArray.length; y++) workVector.add(origAlmArray[y]);

                            AlarmValue[] rmvAlmArray = newAlmList.get();	//items to be removed
                            for (y = 0; y < rmvAlmArray.length; y++) {
                                for (int z = 0; z < origAlmArray.length; z++) {
                                    AlarmValue esc = (AlarmValue) workVector.firstElement();
                                    if (esc.equals(origAlmArray[z])) {
                                        workVector.removeElementAt(y);
                                    }
                                }
                            }

                        } else {
                            throw new IllegalAttributeValueException("RelatedAlarmList",
                                    "Invalid MultiValue attribute modifier " + modifier);
                        }

                        //transform back to array
                        newAlmArray = (AlarmValue[]) workVector.toArray(new AlarmValue[0]);
                        //replace list in TT value with newly constructed one.
                        newAlmList.set(newAlmArray);
                        ttVal.setRelatedAlarmList(newAlmList);
                    } else
                    //-------------------------------------------------
// EscalationList
//-------------------------------------------------
                        if (mvas[x].equals("EscalationList")) {
                            workVector = new java.util.Vector();

// KS 27-08-2003 modified
//			    origEscList = (EscalationList)rs.getObject("EscalationList"); //original values
                            ObjectInputStream ois3 = new ObjectInputStream(rs.getBlob("EscalationList").getBinaryStream());
                            origEscList = ((EscalationList) ois3.readObject());

//If original list is null, add is OK but remove is inapplicable.
                            if (origEscList != null)
                                origEscArray = origEscList.get();
                            newEscList = ttVal.getEscalationList(); //assume session has validated this.
                            modifier = newEscList.getModifier();    //assume session has validated this.

//NB. don't need to check if the modifier is SET, this has been done
//by the caller.
                            if (modifier == MultiValueList.ADD) {
                                //put the old elements in the work vector
                                for (y = 0; y < origEscArray.length; y++) workVector.add(origEscArray[y]);
                                //add the new elements to the work vector
                                Escalation[] tmpEscArray = newEscList.get();
                                for (y = 0; y < tmpEscArray.length; y++) workVector.add(tmpEscArray[y]);
                            } else if (modifier == MultiValueList.REMOVE) {

                                if (origEscList == null) {
                                    throw new IllegalAttributeValueException
                                            ("EscalationList", "Attempted REMOVE operation on empty list");
                                }

                                //put the old elements in the work vector
                                for (y = 0; y < origEscArray.length; y++) workVector.add(origEscArray[y]);

                                Escalation[] rmvEscArray = newEscList.get();	//items to be removed
                                for (y = 0; y < rmvEscArray.length; y++) {
                                    for (int z = 0; z < origEscArray.length; z++) {
                                        Escalation esc = (Escalation) workVector.firstElement();
                                        if (esc.equals(origEscArray[z])) {
                                            workVector.removeElementAt(y);
                                        }
                                    }
                                }

                            } else {
                                throw new IllegalAttributeValueException("EscalationList",
                                        "Invalid MultiValue attribute modifier " + modifier);
                            }

                            //transform back to array
                            newEscArray = (Escalation[]) workVector.toArray(new Escalation[0]);
                            //replace list in TT value with newly constructed one.
                            newEscList.set(newEscArray);
                            ttVal.setEscalationList(newEscList);

                        } else
                        //-------------------------------------------------
// RepairActivityList
//-------------------------------------------------
                            if (mvas[x].equals("RepairActivityList")) {

                                workVector = new java.util.Vector();

// KS 27-08-2003 modified
//			    origRaList = (RepairActivityList)rs.getObject("RepairActivityList"); //original values
                                ObjectInputStream ois4 = new ObjectInputStream(rs.getBlob("RepairActivityList").getBinaryStream());
                                origRaList = ((RepairActivityList) ois4.readObject());

//If original list is null, add is OK but remove is inapplicable.
                                if (origRaList != null)
                                    origRaArray = origRaList.get();
                                newRaList = ttVal.getRepairActivityList(); //assume session has validated this.
                                modifier = newRaList.getModifier();    //assume session has validated this.

//NB. don't need to check if the modifier is SET, this has been done
//by the caller.
                                if (modifier == MultiValueList.ADD) {
                                    //put the old elements in the work vector
                                    for (y = 0; y < origRaArray.length; y++) workVector.add(origRaArray[y]);
                                    //add the new elements to the work vector
                                    RepairActivity[] tmpRaArray = newRaList.get();
                                    for (y = 0; y < tmpRaArray.length; y++) workVector.add(tmpRaArray[y]);
                                } else if (modifier == MultiValueList.REMOVE) {

                                    if (origRaList == null) {
                                        throw new IllegalAttributeValueException
                                                ("RepairActivityList", "Attempted REMOVE operation on empty list");
                                    }

                                    //put the old elements in the work vector
                                    for (y = 0; y < origRaArray.length; y++) workVector.add(origRaArray[y]);

                                    RepairActivity[] rmvRaArray = newRaList.get();	//items to be removed
                                    for (y = 0; y < rmvRaArray.length; y++) {
                                        for (int z = 0; z < origRaArray.length; z++) {
                                            RepairActivity esc = (RepairActivity) workVector.firstElement();
                                            if (esc.equals(origRaArray[z])) {
                                                workVector.removeElementAt(y);
                                            }
                                        }
                                    }

                                } else {
                                    throw new IllegalAttributeValueException("RepairActivityList",
                                            "Invalid MultiValue attribute modifier " + modifier);
                                }

                                //transform back to array
                                newRaArray = (RepairActivity[]) workVector.toArray(new RepairActivity[0]);
                                //replace list in TT value with newly constructed one.
                                newRaList.set(newRaArray);
                                ttVal.setRepairActivityList(newRaList);
                            }

            } catch (IOException ioex) {
                log("IOException caught in TroubleTicketBean.populateTTValueFromSelectedResultSet");
                ioex.printStackTrace();
            } catch (ClassNotFoundException cnfex) {
                log("ClassNotFoundException caught in TroubleTicketBean.populateTTValueFromSelectedResultSet");
                cnfex.printStackTrace();
            }
// KS 27-08-2003 END
        }

        //log("CLOSEDB 6");
        closeDB(con, ps);
    }


    public static String getTTKey(TroubleTicketValue ttVal) {

        String ttId = null;

        TroubleTicketKey ttKey = ttVal.getTroubleTicketKey();
        if (ttKey != null)
            ttId = ttKey.getTroubleTicketPrimaryKey();

        return ttId;
    }


    //plugs the desired attribute name strings into the select or update
    private static String plugAttrNames(String[] attrNames, String cmd) {
//We need to add the key in the select statement
//and make sure we don't include "troubleTicketKey"

//Always select the key...

        String retCmd = cmd;
        retCmd = retCmd.concat("Trid");
        retCmd = retCmd.concat(",");
        if ((attrNames.length == 1) && (attrNames[0].equals("troubleTicketKey"))) return retCmd;
        //Now we may have troubleTicketKey inside
        int y = 0;
        for (int x = 0; x < attrNames.length; x++) {
            if (!attrNames[x].equals("troubleTicketKey")) {
                if (y > 0) retCmd = retCmd.concat(",");
                retCmd = retCmd.concat(attrNames[x]);
                y++;
            }
        }

        return retCmd;

    }


    //--------------------------------------------------------------------------
    // builds an SQL WHERE clause string using TroubleTicketValue(s).  Used
    // for templated updates and selects.
    //--------------------------------------------------------------------------
    public static String buildWhereString(TroubleTicketValue[] ttVals) {

        String wc = new String(" WHERE (");
        Vector popAttrNames = null;

        for (int x = 0; x < ttVals.length; x++) {
            popAttrNames = ((TroubleTicketValueImpl) ttVals[x]).populatedAttributeNames;
            int noAttrs = popAttrNames.size();
            if (x > 0)
                wc = wc.concat(") OR (");    //next template value

            for (int y = 0; y < noAttrs; y++) {
                if (y > 0) wc = wc.concat(" AND ");
                wc = wc.concat(popAttrNames.elementAt(y) + " = ? ");
            }
        }

        wc = wc.concat(")");
        log("Built SQL WHERE clause: " + wc);

        return wc;

    }








    //--------------------------------------------------------------------------
    // inserts attribute values into a PreparedStatement which contains
    // an update commmand which specifies a primary key.
    //--------------------------------------------------------------------------

    //can be used for both updates and inserts for best effort

    public static void insertAttrValues(PreparedStatement ps,
                                        TroubleTicketValue ttVal,
                                        String ttId, boolean lastUpdate) {
        //lastUpdate = false;

        log("InsertAttrValues (single value)");

        //plug the attribute values into the SQL statement
        String[] attrNames = ttVal.getPopulatedAttributeNames();
        int noAttrs = attrNames.length;
        TroubleTicketAttributes attrs = ((TroubleTicketValueImpl) ttVal).ttAttrs;

        log("insertAttrValues inserting " + noAttrs + " attrs...");

        //----------------------------------------------------------------------
        // plug the values that we want to set
        // (e.g. "UPDATE TroubleTicket Set x=?, y=?, ...") WHERE trid = ?
        // or "INSERT into TroubleTicket (troubledescription, troublestate.... trid) Values ?,?,?,?
        //----------------------------------------------------------------------
        attrNames = ttVal.getPopulatedAttributeNames();
        attrs = ((TroubleTicketValueImpl) ttVal).ttAttrs;
        noAttrs = attrNames.length;

        int startposition = 1;

        if (lastUpdate == true) {
            startposition = 2;
            try {
                int value = (int) ttVal.getLastUpdateVersionNumber();
                ps.setInt(1, value);
            } catch (SQLException sqlEx2) {
                log("DBAccess.insertAttrValues: SQLException occured ");
                log(sqlEx2.getMessage());
            }
        }

        DBAccess.plugAttrValues(ps, attrs, attrNames, startposition, noAttrs);



        //set the ttId in the "WHERE" clause or to the last ? for an insert)
        try {
            ps.setString(noAttrs + startposition, ttId);
        } catch (SQLException sqlEx) {
            log("DBAccess.insertAttrValues: SQLException occured ");
            log(sqlEx.getMessage());
        }

        log(" insertAttrValues: inserted ttId: " + ttId);


    }

    //--------------------------------------------------------------------------
    // insert attribute values into a PreparedStatement - used for templated
    // operations
    //--------------------------------------------------------------------------
    public static void insertAttrValues(PreparedStatement ps,
                                        TroubleTicketValue[] templates,
                                        TroubleTicketValue ttVal) {

        log("InsertAttrValues (multiple templates)");

        int position = 1;     // first arg in PreparedStatement

        String[] attrNames;
        TroubleTicketAttributes attrs;
        int noAttrs = 0;

        //----------------------------------------------------------------------
        // plug the values that we want to set
        // (e.g. "UPDATE TroubleTicket Set x=?, y=?, ...")
        //----------------------------------------------------------------------
        if (ttVal != null)    //will be null for a select
        {
            attrNames = ttVal.getPopulatedAttributeNames();

            log("IAV: " + attrNames.length + " attrNames:");
            printStringArray(attrNames, "attrNames 2");

            attrs = ((TroubleTicketValueImpl) ttVal).ttAttrs;
            noAttrs = attrNames.length;
            DBAccess.plugAttrValues(ps, attrs, attrNames, position, noAttrs);
        }
        //----------------------------------------------------------------------
        // plug the values into the where clause (e.g. "WHERE a = ? AND b = ?"
        //----------------------------------------------------------------------
        for (int x = 0; x < templates.length; x++) {
            position += noAttrs;
            attrNames = templates[x].getPopulatedAttributeNames();
            attrs = ((TroubleTicketValueImpl) templates[x]).ttAttrs;
            noAttrs = attrNames.length;
            plugAttrValues(ps, attrs, attrNames, position, noAttrs);
        }

    }


    private static void plugAttrValues(PreparedStatement ps,
                                       TroubleTicketAttributes attrs,
                                       String[] attrNames,
                                       int position,
                                       int noAttrs) {


        log("DBAccess.plugAttrValues: inserting attrs: ");
        log("DBAccess.plugAttrValues: position: " + position + " noAttrs: " + noAttrs);

        printStringArray(attrNames, "attrNames 3");

        try {

            for (int x = 0; x < noAttrs; x++) {
                log("------Plugging Attribute Name-----" + attrNames[x]);

                //set the offset - we could be at the UPDATE part or the WHERE clause in the
                //statement - which may have more than one template tt value for the WHERE
                //clause

// KS 27-08-2003 START
                if (attrNames[x].equals(TroubleTicketValue.ACTIVITYDURATIONLIST)) {
                    //                  ps.setObject(position+x,attrs.getActivityDurationList());
                    ByteArrayOutputStream ba2 = new ByteArrayOutputStream();
                    ObjectOutputStream bos2 = new ObjectOutputStream(ba2);
                    bos2.writeObject(attrs.getActivityDurationList());
                    bos2.flush();
                    byte abyte2[] = ba2.toByteArray();
                    ByteArrayInputStream bi2 = new ByteArrayInputStream(abyte2);
                    ps.setBinaryStream(position + x, bi2, abyte2.length);
                } else if (attrNames[x].equals(TroubleTicketValue.RELATEDALARMLIST)) {
                    //                  ps.setObject(position+x,attrs.getRelatedAlarmList());
                    ByteArrayOutputStream ba3 = new ByteArrayOutputStream();
                    ObjectOutputStream bos3 = new ObjectOutputStream(ba3);
                    bos3.writeObject(attrs.getRelatedAlarmList());
                    bos3.flush();
                    byte abyte3[] = ba3.toByteArray();
                    ByteArrayInputStream bi3 = new ByteArrayInputStream(abyte3);
                    ps.setBinaryStream(position + x, bi3, abyte3.length);
                } else if (attrNames[x].equals(TroubleTicketValue.ADDITIONALTROUBLEINFOLIST)) {
//                    ps.setObject(position+x,attrs.getAdditionalTroubleInfoList());
                    ByteArrayOutputStream ba4 = new ByteArrayOutputStream();
                    ObjectOutputStream bos4 = new ObjectOutputStream(ba4);
                    bos4.writeObject(attrs.getAdditionalTroubleInfoList());
                    bos4.flush();
                    byte abyte4[] = ba4.toByteArray();
                    ByteArrayInputStream bi4 = new ByteArrayInputStream(abyte4);
                    ps.setBinaryStream(position + x, bi4, abyte4.length);
                } else if (attrNames[x].equals(TroubleTicketValue.CLOSEOUTNARR)) {
                    ps.setString(position + x, attrs.getCloseOutNarr());
                } else if (attrNames[x].equals(TroubleTicketValue.RECEIVEDTIME)) {
//                    ps.setObject(position+x,attrs.getReceivedTime());
                    ByteArrayOutputStream ba6 = new ByteArrayOutputStream();
                    ObjectOutputStream bos6 = new ObjectOutputStream(ba6);
                    bos6.writeObject(attrs.getReceivedTime());
                    bos6.flush();
                    byte abyte6[] = ba6.toByteArray();
                    ByteArrayInputStream bi6 = new ByteArrayInputStream(abyte6);
                    ps.setBinaryStream(position + x, bi6, abyte6.length);
                } else if (attrNames[x].equals(TroubleTicketValue.RELATEDTROUBLETICKETKEYLIST)) {
//                    ps.setObject(position+x,attrs.getRelatedTroubleTicketKeyList());
                    ByteArrayOutputStream ba7 = new ByteArrayOutputStream();
                    ObjectOutputStream bos7 = new ObjectOutputStream(ba7);
                    bos7.writeObject(attrs.getRelatedTroubleTicketKeyList());
                    bos7.flush();
                    byte abyte7[] = ba7.toByteArray();
                    ByteArrayInputStream bi7 = new ByteArrayInputStream(abyte7);
                    ps.setBinaryStream(position + x, bi7, abyte7.length);
                } else if (attrNames[x].equals(TroubleTicketValue.REPAIRACTIVITYLIST)) {
//                    ps.setObject(position+x,attrs.getRepairActivityList());
                    ByteArrayOutputStream ba8 = new ByteArrayOutputStream();
                    ObjectOutputStream bos8 = new ObjectOutputStream(ba8);
                    bos8.writeObject(attrs.getRepairActivityList());
                    bos8.flush();
                    byte abyte8[] = ba8.toByteArray();
                    ByteArrayInputStream bi8 = new ByteArrayInputStream(abyte8);
                    ps.setBinaryStream(position + x, bi8, abyte8.length);
                } else if (attrNames[x].equals(TroubleTicketValue.RESTOREDTIME)) {
//                    ps.setObject(position+x,attrs.getRestoredTime());
                    ByteArrayOutputStream ba9 = new ByteArrayOutputStream();
                    ObjectOutputStream bos9 = new ObjectOutputStream(ba9);
                    bos9.writeObject(attrs.getRestoredTime());
                    bos9.flush();
                    byte abyte9[] = ba9.toByteArray();
                    ByteArrayInputStream bi9 = new ByteArrayInputStream(abyte9);
                    ps.setBinaryStream(position + x, bi9, abyte9.length);
                } else if (attrNames[x].equals(TroubleTicketValue.SPROLEASSIGNMENTLIST)) {
//                    ps.setObject(position+x,attrs.getSPRoleAssignmentList());
                    ByteArrayOutputStream ba10 = new ByteArrayOutputStream();
                    ObjectOutputStream bos10 = new ObjectOutputStream(ba10);
                    bos10.writeObject(attrs.getSPRoleAssignmentList());
                    bos10.flush();
                    byte abyte10[] = ba10.toByteArray();
                    ByteArrayInputStream bi10 = new ByteArrayInputStream(abyte10);
                    ps.setBinaryStream(position + x, bi10, abyte10.length);
                } else if (attrNames[x].equals(TroubleTicketValue.TROUBLEDESCRIPTION)) {
                    ps.setString(position + x, attrs.getTroubleDescription());
                } else if (attrNames[x].equals(TroubleTicketValue.CLEARANCEPERSON)) {
//                    ps.setObject(position+x,attrs.getClearancePerson());
                    ByteArrayOutputStream ba12 = new ByteArrayOutputStream();
                    ObjectOutputStream bos12 = new ObjectOutputStream(ba12);
                    bos12.writeObject(attrs.getClearancePerson());
                    bos12.flush();
                    byte abyte12[] = ba12.toByteArray();
                    ByteArrayInputStream bi12 = new ByteArrayInputStream(abyte12);
                    ps.setBinaryStream(position + x, bi12, abyte12.length);
                } else if (attrNames[x].equals(TroubleTicketValue.TROUBLEFOUND)) {
                    ps.setInt(position + x, attrs.getTroubleFound());
                } else if (attrNames[x].equals(TroubleTicketValue.TROUBLELOCATION)) {
                    ps.setString(position + x, attrs.getTroubleLocation());
                } else if (attrNames[x].equals(TroubleTicketValue.TROUBLENUMLIST)) {
//                    ps.setObject(position+x,attrs.getTroubleNumList());
                    ByteArrayOutputStream ba15 = new ByteArrayOutputStream();
                    ObjectOutputStream bos15 = new ObjectOutputStream(ba15);
                    bos15.writeObject(attrs.getTroubleNumList());
                    bos15.flush();
                    byte abyte15[] = ba15.toByteArray();
                    ByteArrayInputStream bi15 = new ByteArrayInputStream(abyte15);
                    ps.setBinaryStream(position + x, bi15, abyte15.length);
                } else if (attrNames[x].equals(TroubleTicketValue.TROUBLEDOBJECT)) {
                    ps.setString(position + x, attrs.getTroubledObject());
                } else if (attrNames[x].equals(TroubleTicketValue.TROUBLETYPE)) {
                    ps.setInt(position + x, attrs.getTroubleType());
                } else if (attrNames[x].equals(TroubleTicketValue.TROUBLESTATE)) {
                    ps.setInt(position + x, attrs.getTroubleState());
                } else if (attrNames[x].equals(TroubleTicketValue.TROUBLESTATUS)) {
                    ps.setInt(position + x, attrs.getTroubleStatus());
                } else if (attrNames[x].equals(TroubleTicketValue.TROUBLESTATUSTIME)) {
//                    ps.setObject(position+x,attrs.getTroubleStatusTime());
                    ByteArrayOutputStream ba20 = new ByteArrayOutputStream();
                    ObjectOutputStream bos20 = new ObjectOutputStream(ba20);
                    bos20.writeObject(attrs.getTroubleStatusTime());
                    bos20.flush();
                    byte abyte20[] = ba20.toByteArray();
                    ByteArrayInputStream bi20 = new ByteArrayInputStream(abyte20);
                    ps.setBinaryStream(position + x, bi20, abyte20.length);
                } else if (attrNames[x].equals(TroubleTicketValue.AFTERHOURSREPAIRAUTHORITY)) {
                    ps.setBoolean(position + x, attrs.getAfterHoursRepairAuthority());
                } else if (attrNames[x].equals(TroubleTicketValue.AUTHORIZATIONLIST)) {
//                    ps.setObject(position+x,attrs.getAuthorizationList());
                    ByteArrayOutputStream ba22 = new ByteArrayOutputStream();
                    ObjectOutputStream bos22 = new ObjectOutputStream(ba22);
                    bos22.writeObject(attrs.getAuthorizationList());
                    bos22.flush();
                    byte abyte22[] = ba22.toByteArray();
                    ByteArrayInputStream bi22 = new ByteArrayInputStream(abyte22);
                    ps.setBinaryStream(position + x, bi22, abyte22.length);
                } else if (attrNames[x].equals(TroubleTicketValue.CANCELREQUESTEDBYCUSTOMER)) {
                    ps.setBoolean(position + x, attrs.getCancelRequestedByCustomer());
                } else if (attrNames[x].equals(TroubleTicketValue.CLOSEOUTVERIFICATION)) {
                    ps.setInt(position + x, attrs.getCloseOutVerification());
                } else if (attrNames[x].equals(TroubleTicketValue.COMMITMENTTIME)) {
//                    ps.setObject(position+x,attrs.getCommitmentTime());
                    ByteArrayOutputStream ba25 = new ByteArrayOutputStream();
                    ObjectOutputStream bos25 = new ObjectOutputStream(ba25);
                    bos25.writeObject(attrs.getCommitmentTime());
                    bos25.flush();
                    byte abyte25[] = ba25.toByteArray();
                    ByteArrayInputStream bi25 = new ByteArrayInputStream(abyte25);
                    ps.setBinaryStream(position + x, bi25, abyte25.length);
                } else if (attrNames[x].equals(TroubleTicketValue.COMMITMENTTIMEREQUESTED)) {
//                    ps.setObject(position+x,attrs.getCommitmentTimeRequested());
                    ByteArrayOutputStream ba26 = new ByteArrayOutputStream();
                    ObjectOutputStream bos26 = new ObjectOutputStream(ba26);
                    bos26.writeObject(attrs.getCommitmentTimeRequested());
                    bos26.flush();
                    byte abyte26[] = ba26.toByteArray();
                    ByteArrayInputStream bi26 = new ByteArrayInputStream(abyte26);
                    ps.setBinaryStream(position + x, bi26, abyte26.length);
                } else if (attrNames[x].equals(TroubleTicketValue.CUSTOMERROLEASSIGNMENTLIST)) {
//                    ps.setObject(position+x,attrs.getCustomerRoleAssignmentList());
                    ByteArrayOutputStream ba27 = new ByteArrayOutputStream();
                    ObjectOutputStream bos27 = new ObjectOutputStream(ba27);
                    bos27.writeObject(attrs.getCustomerRoleAssignmentList());
                    bos27.flush();
                    byte abyte27[] = ba27.toByteArray();
                    ByteArrayInputStream bi27 = new ByteArrayInputStream(abyte27);
                    ps.setBinaryStream(position + x, bi27, abyte27.length);
                } else if (attrNames[x].equals(TroubleTicketValue.CUSTOMERTROUBLENUM)) {
                    ps.setString(position + x, attrs.getCustomerTroubleNum());
                } else if (attrNames[x].equals(TroubleTicketValue.DIALOG)) {
                    ps.setString(position + x, attrs.getDialog());
                } else if (attrNames[x].equals(TroubleTicketValue.ESCALATIONLIST)) {
//                    ps.setObject(position+x,attrs.getEscalationList());
                    ByteArrayOutputStream ba30 = new ByteArrayOutputStream();
                    ObjectOutputStream bos30 = new ObjectOutputStream(ba30);
                    bos30.writeObject(attrs.getEscalationList());
                    bos30.flush();
                    byte abyte30[] = ba30.toByteArray();
                    ByteArrayInputStream bi30 = new ByteArrayInputStream(abyte30);
                    ps.setBinaryStream(position + x, bi30, abyte30.length);
                } else if (attrNames[x].equals(TroubleTicketValue.INITIATINGMODE)) {
                    ps.setInt(position + x, attrs.getInitiatingMode());
                } else if (attrNames[x].equals(TroubleTicketValue.LASTUPDATETIME)) {
//                    ps.setObject(position+x,attrs.getLastUpdateTime());
                    ByteArrayOutputStream ba32 = new ByteArrayOutputStream();
                    ObjectOutputStream bos32 = new ObjectOutputStream(ba32);
                    bos32.writeObject(attrs.getLastUpdateTime());
                    bos32.flush();
                    byte abyte32[] = ba32.toByteArray();
                    ByteArrayInputStream bi32 = new ByteArrayInputStream(abyte32);
                    ps.setBinaryStream(position + x, bi32, abyte32.length);
                } else if (attrNames[x].equals(TroubleTicketValue.MAINTSERVICECHARGE)) {
                    ps.setBoolean(position + x, attrs.getMaintServiceCharge());
                } else if (attrNames[x].equals(TroubleTicketValue.OUTAGEDURATION)) {
//                    ps.setObject(position+x,attrs.getOutageDuration());
                    ByteArrayOutputStream ba34 = new ByteArrayOutputStream();
                    ObjectOutputStream bos34 = new ObjectOutputStream(ba34);
                    bos34.writeObject(attrs.getOutageDuration());
                    bos34.flush();
                    byte abyte34[] = ba34.toByteArray();
                    ByteArrayInputStream bi34 = new ByteArrayInputStream(abyte34);
                    ps.setBinaryStream(position + x, bi34, abyte34.length);
                } else if (attrNames[x].equals(TroubleTicketValue.PERCEIVEDTROUBLESEVERITY)) {
                    ps.setInt(position + x, attrs.getPerceivedTroubleSeverity());
                } else if (attrNames[x].equals(TroubleTicketValue.PREFERREDPRIORITY)) {
                    ps.setInt(position + x, attrs.getPreferredPriority());
                } else if (attrNames[x].equals(TroubleTicketValue.REPEATREPORT)) {
                    ps.setInt(position + x, attrs.getRepeatReport());
                } else if (attrNames[x].equals(TroubleTicketValue.SUSPECTOBJECTLIST)) {
//                    ps.setObject(position+x,attrs.getSuspectObjectList());
                    ByteArrayOutputStream ba38 = new ByteArrayOutputStream();
                    ObjectOutputStream bos38 = new ObjectOutputStream(ba38);
                    bos38.writeObject(attrs.getSuspectObjectList());
                    bos38.flush();
                    byte abyte38[] = ba38.toByteArray();
                    ByteArrayInputStream bi38 = new ByteArrayInputStream(abyte38);
                    ps.setBinaryStream(position + x, bi38, abyte38.length);
                } else if (attrNames[x].equals(TroubleTicketValue.TROUBLEDETECTIONTIME)) {
//                    ps.setObject(position+x,attrs.getTroubleDetectionTime());
                    ByteArrayOutputStream ba39 = new ByteArrayOutputStream();
                    ObjectOutputStream bos39 = new ObjectOutputStream(ba39);
                    bos39.writeObject(attrs.getTroubleDetectionTime());
                    bos39.flush();
                    byte abyte39[] = ba39.toByteArray();
                    ByteArrayInputStream bi39 = new ByteArrayInputStream(abyte39);
                    ps.setBinaryStream(position + x, bi39, abyte39.length);
                } else if (attrNames[x].equals(TroubleTicketValue.TROUBLELOCATIONINFOLIST)) {
//                    ps.setObject(position+x,attrs.getTroubleLocationInfoList());
                    ByteArrayOutputStream ba40 = new ByteArrayOutputStream();
                    ObjectOutputStream bos40 = new ObjectOutputStream(ba40);
                    bos40.writeObject(attrs.getTroubleLocationInfoList());
                    bos40.flush();
                    byte abyte40[] = ba40.toByteArray();
                    ByteArrayInputStream bi40 = new ByteArrayInputStream(abyte40);
                    ps.setBinaryStream(position + x, bi40, abyte40.length);
                } else if (attrNames[x].equals(TroubleTicketValue.TROUBLEDOBJECTACCESSFROMTIME)) {
//                    ps.setObject(position+x,attrs.getTroubledObjectAccessFromTime());
                    ByteArrayOutputStream ba41 = new ByteArrayOutputStream();
                    ObjectOutputStream bos41 = new ObjectOutputStream(ba41);
                    bos41.writeObject(attrs.getTroubledObjectAccessFromTime());
                    bos41.flush();
                    byte abyte41[] = ba41.toByteArray();
                    ByteArrayInputStream bi41 = new ByteArrayInputStream(abyte41);
                    ps.setBinaryStream(position + x, bi41, abyte41.length);
                } else if (attrNames[x].equals(TroubleTicketValue.TROUBLEDOBJECTACCESSHOURSLIST)) {
//                    ps.setObject(position+x,attrs.getTroubledObjectAccessHoursList());
                    ByteArrayOutputStream ba42 = new ByteArrayOutputStream();
                    ObjectOutputStream bos42 = new ObjectOutputStream(ba42);
                    bos42.writeObject(attrs.getTroubledObjectAccessHoursList());
                    bos42.flush();
                    byte abyte42[] = ba42.toByteArray();
                    ByteArrayInputStream bi42 = new ByteArrayInputStream(abyte42);
                    ps.setBinaryStream(position + x, bi42, abyte42.length);
                } else if (attrNames[x].equals(TroubleTicketValue.TROUBLEDOBJECTACCESSTOTIME)) {
//                    ps.setObject(position+x,attrs.getTroubledObjectAccessToTime());
                    ByteArrayOutputStream ba43 = new ByteArrayOutputStream();
                    ObjectOutputStream bos43 = new ObjectOutputStream(ba43);
                    bos43.writeObject(attrs.getTroubledObjectAccessToTime());
                    bos43.flush();
                    byte abyte43[] = ba43.toByteArray();
                    ByteArrayInputStream bi43 = new ByteArrayInputStream(abyte43);
                    ps.setBinaryStream(position + x, bi43, abyte43.length);
                } else if (attrNames[x].equals(TroubleTicketValue.SERVICEUNAVAILABLEBEGINTIME)) {
//                    ps.setObject(position+x,attrs.getServiceUnavailableBeginTime());
                    ByteArrayOutputStream ba44 = new ByteArrayOutputStream();
                    ObjectOutputStream bos44 = new ObjectOutputStream(ba44);
                    bos44.writeObject(attrs.getServiceUnavailableBeginTime());
                    bos44.flush();
                    byte abyte44[] = ba44.toByteArray();
                    ByteArrayInputStream bi44 = new ByteArrayInputStream(abyte44);
                    ps.setBinaryStream(position + x, bi44, abyte44.length);
                } else if (attrNames[x].equals(TroubleTicketValue.SERVICEUNAVAILABLEENDTIME)) {
//                    ps.setObject(position+x,attrs.getServiceUnavailableEndTime());
                    ByteArrayOutputStream ba45 = new ByteArrayOutputStream();
                    ObjectOutputStream bos45 = new ObjectOutputStream(ba45);
                    bos45.writeObject(attrs.getServiceUnavailableEndTime());
                    bos45.flush();
                    byte abyte45[] = ba45.toByteArray();
                    ByteArrayInputStream bi45 = new ByteArrayInputStream(abyte45);
                    ps.setBinaryStream(position + x, bi45, abyte45.length);
                } else if (attrNames[x].equals(TroubleTicketValue.ORIGINATOR)) {
                    ps.setString(position + x, attrs.getOriginator());
                } else if (attrNames[x].equals(TroubleTicketValue.TROUBLESYSTEMDN)) {
                    ps.setString(position + x, attrs.getTroubleSystemDN());
                } else if (attrNames[x].equals(TroubleTicketValue.CUSTOMER)) {
//                    ps.setObject(position+x,attrs.getCustomer());
                    ByteArrayOutputStream ba48 = new ByteArrayOutputStream();
                    ObjectOutputStream bos48 = new ObjectOutputStream(ba48);
                    bos48.writeObject(attrs.getCustomer());
                    bos48.flush();
                    byte abyte48[] = ba48.toByteArray();
                    ByteArrayInputStream bi48 = new ByteArrayInputStream(abyte48);
                    ps.setBinaryStream(position + x, bi48, abyte48.length);
                } else if (attrNames[x].equals(TroubleTicketValue.ACCOUNTOWNER)) {
//                  ps.setObject(position+x,attrs.getAccountOwner());
                    ByteArrayOutputStream ba1 = new ByteArrayOutputStream();
                    ObjectOutputStream bos1 = new ObjectOutputStream(ba1);
                    bos1.writeObject(attrs.getAccountOwner());
                    bos1.flush();
                    byte abyte1[] = ba1.toByteArray();
                    ByteArrayInputStream bi1 = new ByteArrayInputStream(abyte1);
                    ps.setBinaryStream(position + x, bi1, abyte1.length);
                } else {
                    log("TTEntityBean.prepareUpdateStatement - unknown attribute: " + attrNames[x]);
                    //throw new IllegalArgumentException();
                }
// KS 27-08-2003 END

            }   //end for

        } catch (SQLException sqlex) {
            log("SQLException caught in TroubleTicketBean.insertAttrValues");
            sqlex.printStackTrace();
        }
// KS 27-08-2003 START
        catch (IOException ioex) {
            log("IOException caught in TroubleTicketBean.insertAttrValues");
            ioex.printStackTrace();
        }
// KS 27-08-2003 END
    }

    public static ResultSet executeSelect(PreparedStatement ps)
            throws SQLException {
        ResultSet rs;

        try {
            rs = ps.executeQuery();
        } catch (SQLException sqlex) {
            log("SQLException occured in DBAccess.executeSelect: " + sqlex.getMessage());
            sqlex.printStackTrace();
            throw sqlex;
        }

        return rs;
    }

    public static int executeUpdate(PreparedStatement ps)
            throws SQLException {
        int updateCount = 0;

        try {
            updateCount = ps.executeUpdate();
        } catch (SQLException sqlex) {
            log("SQLException occured in DBAccess.executeUpdate: " + sqlex.getMessage());
            sqlex.printStackTrace();
            throw sqlex;
        }

        log("DBAccess.executeUpdate: " + updateCount + " rows inserted/updated/deleted");
        return updateCount;
    }


    //--------------------------------------------------------------------------
    // builds a TTValue from the JDBC ResultSet - used by ejbLoad to load the
    // complete row - all attrs.
    //--------------------------------------------------------------------------
    public static void
            populateTTValueFromResultSet(TroubleTicketValue ttVal, ResultSet rs)
            throws java.sql.SQLException {

        log("DBAccess.populateTTValueFromResultSet   TTKEY = " + rs.getString(1));

        int x = 0;    //skip over the trid.

        try {
            //set the key
            TroubleTicketKeyImpl ttKey = new TroubleTicketKeyImpl(rs.getString(++x));
            ttKey.setApplicationDN(TTConfig.getInstance().getApplicationTypeDN());
            ttKey.setApplicationContext(TTConfig.getInstance().getApplicationContext());
            ttVal.setTroubleTicketKey(ttKey);
            //ttVal.setTroubleTicketKey(new TroubleTicketKeyImpl(               rs.getString(++x)));
            ((TroubleTicketValueImpl) ttVal).setLastUpdateVersionNumber((long) rs.getInt(++x));

// KS 26-08-2003 START
            log("SET ATTRIBUTES");

            log("setActivityDurationList");
//      ttVal.setActivityDurationList((ActivityDurationList)              rs.getObject(++x));
//      ObjectInputStream ois1 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setActivityDurationList((ActivityDurationList) ois1.readObject() );
            DBAccessHelper dbah1 = new DBAccessHelper();
            ttVal.setActivityDurationList((ActivityDurationList) dbah1.convert(rs.getBlob(++x)));

            log("setRelatedAlarmList");
            DBAccessHelper dbah2 = new DBAccessHelper();
            //VP for pointbase that doesnot allow null blob:
            // java.sql.SQLException: Invalid Parameter [byte[] data] in [jdbcBlob(...)] for [jdbcBlob]. [null] not allowed.
            try {
                ttVal.setRelatedAlarmList((AlarmValueList) dbah2.convert(rs.getBlob(++x)));
            } catch (SQLException sqlex) {
                if (!sqlex.getMessage().endsWith("[null] not allowed."))
                    throw sqlex;
            }
            //end VP


// old example KS
//		log("setRelatedAlarmList");
//        ttVal.setRelatedAlarmList((AlarmValueList)                        rs.getObject(++x));
/* 		if(rs.getBlob(++x) == null) {
			log("setRelatedAlarmList - Blob was NULL");
	        ttVal.setRelatedAlarmList(null );
		}
		else {
	        ObjectInputStream ois2 = new ObjectInputStream(rs.getBlob(x).getBinaryStream() );
			log("setRelatedAlarmList - got Blob");
	        ttVal.setRelatedAlarmList((AlarmValueList) ois2.readObject() );
		}
 */


            log("setAdditionalTroubleInfoList");
//        ttVal.setAdditionalTroubleInfoList((String[])                     rs.getObject(++x));
//      ObjectInputStream ois3 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setAdditionalTroubleInfoList((String[]) ois3.readObject() );
            DBAccessHelper dbah3 = new DBAccessHelper();
            ttVal.setAdditionalTroubleInfoList((String[]) dbah3.convert(rs.getBlob(++x)));

            log("setCloseOutNarr");
            ttVal.setCloseOutNarr(rs.getString(++x));

            log("setReceivedTime");
//        ttVal.setReceivedTime((java.util.Date)                            rs.getObject(++x));
//      ObjectInputStream ois5 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setReceivedTime((java.util.Date) ois5.readObject() );
            DBAccessHelper dbah5 = new DBAccessHelper();
            ttVal.setReceivedTime((java.util.Date) dbah5.convert(rs.getBlob(++x)));

            log("setRelatedTroubleTicketKeyList");
//        ttVal.setRelatedTroubleTicketKeyList((TroubleTicketKey[])         rs.getObject(++x));
//      ObjectInputStream ois6 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setRelatedTroubleTicketKeyList((TroubleTicketKey[]) ois6.readObject() );
            DBAccessHelper dbah6 = new DBAccessHelper();
            ttVal.setRelatedTroubleTicketKeyList((TroubleTicketKey[]) dbah6.convert(rs.getBlob(++x)));

            log("setRepairActivityList");
//        ttVal.setRepairActivityList((RepairActivityList)                  rs.getObject(++x));
//      ObjectInputStream ois7 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setRepairActivityList((RepairActivityList) ois7.readObject() );
            DBAccessHelper dbah7 = new DBAccessHelper();
            ttVal.setRepairActivityList((RepairActivityList) dbah7.convert(rs.getBlob(++x)));

            log("setRestoredTime");
//        ttVal.setRestoredTime((java.util.Date)                            rs.getObject(++x));
//      ObjectInputStream ois8 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setRestoredTime((java.util.Date) ois8.readObject() );
            DBAccessHelper dbah8 = new DBAccessHelper();
            ttVal.setRestoredTime((java.util.Date) dbah8.convert(rs.getBlob(++x)));

            log("setSPRoleAssignmentList");
//        ttVal.setSPRoleAssignmentList((SPRoleAssignment[])                rs.getObject(++x));
//      ObjectInputStream ois9 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setSPRoleAssignmentList((SPRoleAssignment[]) ois9.readObject() );
            DBAccessHelper dbah9 = new DBAccessHelper();
            ttVal.setSPRoleAssignmentList((SPRoleAssignment[]) dbah9.convert(rs.getBlob(++x)));

            log("setTroubleDescription");
            ttVal.setTroubleDescription(rs.getString(++x));

            log("setClearancePerson");
//        ttVal.setClearancePerson((PersonReach)                            rs.getObject(++x));
//      ObjectInputStream ois11 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setClearancePerson((PersonReach) ois11.readObject() );
            DBAccessHelper dbah11 = new DBAccessHelper();
            ttVal.setClearancePerson((PersonReach) dbah11.convert(rs.getBlob(++x)));

            log("setTroubleFound");
            ttVal.setTroubleFound(rs.getInt(++x));

            log("setTroubleLocation");
            ttVal.setTroubleLocation(rs.getString(++x));

            log("setTroubleNumList");
//       ttVal.setTroubleNumList((String[])                                rs.getObject(++x));
//      ObjectInputStream ois14 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setTroubleNumList((String[]) ois14.readObject() );
            DBAccessHelper dbah14 = new DBAccessHelper();
            ttVal.setTroubleNumList((String[]) dbah14.convert(rs.getBlob(++x)));

            log("setTroubledObject");
            ttVal.setTroubledObject(rs.getString(++x));

            log("setTroubleType");
            ttVal.setTroubleType(rs.getInt(++x));

            log("setTroubleState");
            ttVal.setTroubleState(rs.getInt(++x));

            log("setTroubleStatus");
            ttVal.setTroubleStatus(rs.getInt(++x));

            log("setTroubleStatusTime");
//       ttVal.setTroubleStatusTime((java.util.Date)                       rs.getObject(++x));
//      ObjectInputStream ois19 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setTroubleStatusTime((java.util.Date) ois19.readObject() );
            DBAccessHelper dbah19 = new DBAccessHelper();
            ttVal.setTroubleStatusTime((java.util.Date) dbah19.convert(rs.getBlob(++x)));

            log("setAfterHoursRepairAuthority");
            ttVal.setAfterHoursRepairAuthority(rs.getBoolean(++x));


            log("setAuthorizationList");
//       ttVal.setAuthorizationList((Authorization[])                      rs.getObject(++x));
//      ObjectInputStream ois21 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setAuthorizationList((Authorization[]) ois21.readObject() );
            DBAccessHelper dbah21 = new DBAccessHelper();
            ttVal.setAuthorizationList((Authorization[]) dbah21.convert(rs.getBlob(++x)));

            log("setCancelRequestedByCustomer");
            ttVal.setCancelRequestedByCustomer(rs.getBoolean(++x));

            log("setCloseOutVerification");
            ttVal.setCloseOutVerification(rs.getInt(++x));

            log("setCommitmentTime");
//      ttVal.setCommitmentTime((java.util.Date)                          rs.getObject(++x));
//      ObjectInputStream ois24 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setCommitmentTime((java.util.Date) ois24.readObject() );
            DBAccessHelper dbah24 = new DBAccessHelper();
            ttVal.setCommitmentTime((java.util.Date) dbah24.convert(rs.getBlob(++x)));

            log("setCommitmentTimeRequested");
//      ttVal.setCommitmentTimeRequested((java.util.Date)                 rs.getObject(++x));
//      ObjectInputStream ois25 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setCommitmentTimeRequested((java.util.Date) ois25.readObject() );
            DBAccessHelper dbah25 = new DBAccessHelper();
            ttVal.setCommitmentTimeRequested((java.util.Date) dbah25.convert(rs.getBlob(++x)));

            log("setCustomerRoleAssignmentList");
//      ttVal.setCustomerRoleAssignmentList((CustomerRoleAssignment[])    rs.getObject(++x));
//      ObjectInputStream ois26 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setCustomerRoleAssignmentList((CustomerRoleAssignment[]) ois26.readObject() );
            DBAccessHelper dbah26 = new DBAccessHelper();
            ttVal.setCustomerRoleAssignmentList((CustomerRoleAssignment[]) dbah26.convert(rs.getBlob(++x)));

            log("setCustomerTroubleNum");
            ttVal.setCustomerTroubleNum(rs.getString(++x));

            log("setDialog");
            ttVal.setDialog(rs.getString(++x));

            log("setEscalationList");
//        ttVal.setEscalationList((EscalationList)                            rs.getObject(++x));
//      ObjectInputStream ois29 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setEscalationList((EscalationList) ois29.readObject() );
            DBAccessHelper dbah29 = new DBAccessHelper();
            ttVal.setEscalationList((EscalationList) dbah29.convert(rs.getBlob(++x)));

            log("setInitiatingMode");
            ttVal.setInitiatingMode(rs.getInt(++x));

            log("setLastUpdateTime");
//        ttVal.setLastUpdateTime((java.util.Date)                          rs.getObject(++x));
//      ObjectInputStream ois31 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setLastUpdateTime((java.util.Date) ois31.readObject() );
            DBAccessHelper dbah31 = new DBAccessHelper();
            ttVal.setLastUpdateTime((java.util.Date) dbah31.convert(rs.getBlob(++x)));

            log("setMaintServiceCharge");
            ttVal.setMaintServiceCharge(rs.getBoolean(++x));

            log("setOutageDuration");
//        ttVal.setOutageDuration((TimeLength)                              rs.getObject(++x));
//      ObjectInputStream ois33 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setOutageDuration((TimeLength) ois33.readObject() );
            DBAccessHelper dbah33 = new DBAccessHelper();
            ttVal.setOutageDuration((TimeLength) dbah33.convert(rs.getBlob(++x)));

            log("setPerceivedTroubleSeverity");
            ttVal.setPerceivedTroubleSeverity(rs.getInt(++x));

            log("setPreferredPriority");
            ttVal.setPreferredPriority(rs.getInt(++x));

            log("setRepeatReport");
            ttVal.setRepeatReport(rs.getInt(++x));

            log("setSuspectObjectList");
//      ttVal.setSuspectObjectList((SuspectObject[])                      rs.getObject(++x));
//      ObjectInputStream ois37 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setSuspectObjectList((SuspectObject[]) ois37.readObject() );
            DBAccessHelper dbah37 = new DBAccessHelper();
            ttVal.setSuspectObjectList((SuspectObject[]) dbah37.convert(rs.getBlob(++x)));

            log("setTroubleDetectionTime");
//        ttVal.setTroubleDetectionTime((java.util.Date)                    rs.getObject(++x));
//      ObjectInputStream ois38 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setTroubleDetectionTime((java.util.Date) ois38.readObject() );
            DBAccessHelper dbah38 = new DBAccessHelper();
            ttVal.setTroubleDetectionTime((java.util.Date) dbah38.convert(rs.getBlob(++x)));

            log("setTroubleLocationInfoList");
//        ttVal.setTroubleLocationInfoList((TroubleLocationInfo[])          rs.getObject(++x));
//      ObjectInputStream ois39 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setTroubleLocationInfoList((TroubleLocationInfo[]) ois39.readObject() );
            DBAccessHelper dbah39 = new DBAccessHelper();
            ttVal.setTroubleLocationInfoList((TroubleLocationInfo[]) dbah39.convert(rs.getBlob(++x)));

            log("setTroubledObjectAccessFromTime");
//        ttVal.setTroubledObjectAccessFromTime((java.util.Date)            rs.getObject(++x));
//      ObjectInputStream ois40 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setTroubledObjectAccessFromTime((java.util.Date) ois40.readObject() );
            DBAccessHelper dbah40 = new DBAccessHelper();
            ttVal.setTroubledObjectAccessFromTime((java.util.Date) dbah40.convert(rs.getBlob(++x)));

            log("setTroubledObjectAccessHoursList");
//        ttVal.setTroubledObjectAccessHoursList((AccessHours[])            rs.getObject(++x));
//      ObjectInputStream ois41 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setTroubledObjectAccessHoursList((AccessHours[]) ois41.readObject() );
            DBAccessHelper dbah41 = new DBAccessHelper();
            ttVal.setTroubledObjectAccessHoursList((AccessHours[]) dbah41.convert(rs.getBlob(++x)));

            log("setTroubledObjectAccessToTime");
//        ttVal.setTroubledObjectAccessToTime((java.util.Date)              rs.getObject(++x));
//      ObjectInputStream ois42 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setTroubledObjectAccessToTime((java.util.Date) ois42.readObject() );
            DBAccessHelper dbah42 = new DBAccessHelper();
            ttVal.setTroubledObjectAccessToTime((java.util.Date) dbah42.convert(rs.getBlob(++x)));

            log("setServiceUnavailableBeginTime");
//        ttVal.setServiceUnavailableBeginTime((java.util.Date)             rs.getObject(++x));
//      ObjectInputStream ois43 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setServiceUnavailableBeginTime((java.util.Date) ois43.readObject() );
            DBAccessHelper dbah43 = new DBAccessHelper();
            ttVal.setServiceUnavailableBeginTime((java.util.Date) dbah43.convert(rs.getBlob(++x)));

            log("setServiceUnavailableEndTime");
//        ttVal.setServiceUnavailableEndTime((java.util.Date)               rs.getObject(++x));
//      ObjectInputStream ois44 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setServiceUnavailableEndTime((java.util.Date) ois44.readObject() );
            DBAccessHelper dbah44 = new DBAccessHelper();
            ttVal.setServiceUnavailableEndTime((java.util.Date) dbah44.convert(rs.getBlob(++x)));

            log("setOriginator");
            ttVal.setOriginator(rs.getString(++x));

            log("setTroubleSystemDN");
            ttVal.setTroubleSystemDN(rs.getString(++x));

            log("setCustomer");
//        ttVal.setCustomer((PersonReach)                                   rs.getObject(++x));
//      ObjectInputStream ois47 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setCustomer((PersonReach) ois47.readObject() );
            DBAccessHelper dbah47 = new DBAccessHelper();
            ttVal.setCustomer((PersonReach) dbah47.convert(rs.getBlob(++x)));

            log("setAccountOwner");
//        ttVal.setAccountOwner((PersonReach)                               rs.getObject(++x));
//      ObjectInputStream ois48 = new ObjectInputStream(rs.getBlob(++x).getBinaryStream() );
//      ttVal.setAccountOwner((PersonReach) ois48.readObject() );
            DBAccessHelper dbah48 = new DBAccessHelper();
            ttVal.setAccountOwner((PersonReach) dbah48.convert(rs.getBlob(++x)));

            log("DONE");
// KS 26-08-2003 END
        } catch (SQLException sqlEx) {
            log("DBAccess.populateTTValueFromResultSet caught SQLException");
            sqlEx.printStackTrace();
            throw sqlEx;
        }
    }


    //--------------------------------------------------------------------------
    // builds a TTValue from the JDBC ResultSet - used when doing a select
    // on a subset of the attributes in the row
    //
    // Note: use the same String array used to build the select statement
    // as the attributes to this method - this ensures that the order of attrs
    // in the ResultSet will be the same!
    //--------------------------------------------------------------------------
    public static void
            populateTTValueFromSelectedResultSet(String[] requestedAttrNames,
                                                 TroubleTicketValue ttVal,
                                                 ResultSet rs)
            throws java.sql.SQLException {

        log("DBAccess.populateTTValueFromSelectedResultSet");
        log("Desired attrnames: ");
        for (int x = 0; x < requestedAttrNames.length; x++)
            log(requestedAttrNames[x]);

        TroubleTicketKeyImpl ttKey = new TroubleTicketKeyImpl(rs.getString(1));
        ttKey.setApplicationDN(TTConfig.getInstance().getApplicationTypeDN());
        ttKey.setApplicationContext(TTConfig.getInstance().getApplicationContext());
        ttVal.setTroubleTicketKey(ttKey);

        int position;
        for (int x = 0; x < requestedAttrNames.length; x++) {

// KS 27-08-2003 START
            try {
                String attrName = requestedAttrNames[x];
                position = x + 2;

                if (attrName.equals(TroubleTicketValue.ACTIVITYDURATIONLIST)) {
//                ttVal.setActivityDurationList((ActivityDurationList)rs.getObject(position));
                    ObjectInputStream ois1 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setActivityDurationList((ActivityDurationList) ois1.readObject());
                } else if (attrName.equals(TroubleTicketValue.RELATEDALARMLIST)) {
//                ttVal.setRelatedAlarmList((AlarmValueList)rs.getObject(position));
                    ObjectInputStream ois2 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setRelatedAlarmList((AlarmValueList) ois2.readObject());
                } else if (attrName.equals(TroubleTicketValue.ADDITIONALTROUBLEINFOLIST)) {
//                ttVal.setAdditionalTroubleInfoList((String[])rs.getObject(position));
                    ObjectInputStream ois3 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setAdditionalTroubleInfoList((String[]) ois3.readObject());
                } else if (attrName.equals(TroubleTicketValue.CLOSEOUTNARR)) {
                    ttVal.setCloseOutNarr(rs.getString(position));
                } else if (attrName.equals(TroubleTicketValue.RECEIVEDTIME)) {
//                ttVal.setReceivedTime((java.util.Date)rs.getObject(position));
                    ObjectInputStream ois5 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setReceivedTime((java.util.Date) ois5.readObject());
                } else if (attrName.equals(TroubleTicketValue.RELATEDTROUBLETICKETKEYLIST)) {
//                ttVal.setRelatedTroubleTicketKeyList((TroubleTicketKey[])rs.getObject(position));
                    ObjectInputStream ois6 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setRelatedTroubleTicketKeyList((TroubleTicketKey[]) ois6.readObject());
                } else if (attrName.equals(TroubleTicketValue.REPAIRACTIVITYLIST)) {
//                ttVal.setRepairActivityList((RepairActivityList)rs.getObject(position));
                    ObjectInputStream ois7 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setRepairActivityList((RepairActivityList) ois7.readObject());
                } else if (attrName.equals(TroubleTicketValue.RESTOREDTIME)) {
//                ttVal.setRestoredTime((java.util.Date)rs.getObject(position));
                    ObjectInputStream ois8 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setRestoredTime((java.util.Date) ois8.readObject());
                } else if (attrName.equals(TroubleTicketValue.SPROLEASSIGNMENTLIST)) {
//                ttVal.setSPRoleAssignmentList((SPRoleAssignment[])rs.getObject(position));
                    ObjectInputStream ois9 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setSPRoleAssignmentList((SPRoleAssignment[]) ois9.readObject());
                } else if (attrName.equals(TroubleTicketValue.TROUBLEDESCRIPTION)) {
                    ttVal.setTroubleDescription(rs.getString(position));
                } else if (attrName.equals(TroubleTicketValue.CLEARANCEPERSON)) {
//                ttVal.setClearancePerson((PersonReach)rs.getObject(position));
                    ObjectInputStream ois11 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setClearancePerson((PersonReach) ois11.readObject());
                } else if (attrName.equals(TroubleTicketValue.TROUBLEFOUND)) {
                    ttVal.setTroubleFound(rs.getInt(position));
                } else if (attrName.equals(TroubleTicketValue.TROUBLELOCATION)) {
                    ttVal.setTroubleLocation(rs.getString(position));
                } else if (attrName.equals(TroubleTicketValue.TROUBLENUMLIST)) {
//                ttVal.setTroubleNumList((String[])rs.getObject(position));
                    ObjectInputStream ois14 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setTroubleNumList((String[]) ois14.readObject());
                } else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECT)) {
                    ttVal.setTroubledObject(rs.getString(position));
                } else if (attrName.equals(TroubleTicketValue.TROUBLETYPE)) {
                    ttVal.setTroubleType(rs.getInt(position));
                } else if (attrName.equals(TroubleTicketValue.TROUBLESTATE)) {
                    ttVal.setTroubleState(rs.getInt(position));
                } else if (attrName.equals(TroubleTicketValue.TROUBLESTATUS)) {
                    ttVal.setTroubleStatus(rs.getInt(position));
                } else if (attrName.equals(TroubleTicketValue.TROUBLESTATUSTIME)) {
//                ttVal.setTroubleStatusTime((java.util.Date)rs.getObject(position));
                    ObjectInputStream ois19 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setTroubleStatusTime((java.util.Date) ois19.readObject());
                } else if (attrName.equals(TroubleTicketValue.AFTERHOURSREPAIRAUTHORITY)) {
                    ttVal.setAfterHoursRepairAuthority(rs.getBoolean(position));
                } else if (attrName.equals(TroubleTicketValue.AUTHORIZATIONLIST)) {
//                ttVal.setAuthorizationList((Authorization[])rs.getObject(position));
                    ObjectInputStream ois21 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setAuthorizationList((Authorization[]) ois21.readObject());
                } else if (attrName.equals(TroubleTicketValue.CANCELREQUESTEDBYCUSTOMER)) {
                    ttVal.setCancelRequestedByCustomer(rs.getBoolean(position));
                } else if (attrName.equals(TroubleTicketValue.CLOSEOUTVERIFICATION)) {
                    ttVal.setCloseOutVerification(rs.getInt(position));
                } else if (attrName.equals(TroubleTicketValue.COMMITMENTTIME)) {
//                ttVal.setCommitmentTime((java.util.Date)rs.getObject(position));
                    ObjectInputStream ois24 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setCommitmentTime((java.util.Date) ois24.readObject());
                } else if (attrName.equals(TroubleTicketValue.COMMITMENTTIMEREQUESTED)) {
//                ttVal.setCommitmentTimeRequested((java.util.Date)rs.getObject(position));
                    ObjectInputStream ois25 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setCommitmentTimeRequested((java.util.Date) ois25.readObject());
                } else if (attrName.equals(TroubleTicketValue.CUSTOMERROLEASSIGNMENTLIST)) {
//                ttVal.setCustomerRoleAssignmentList((CustomerRoleAssignment[])rs.getObject(position));
                    ObjectInputStream ois26 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setCustomerRoleAssignmentList((CustomerRoleAssignment[]) ois26.readObject());
                } else if (attrName.equals(TroubleTicketValue.CUSTOMERTROUBLENUM)) {
                    ttVal.setCustomerTroubleNum(rs.getString(position));
                } else if (attrName.equals(TroubleTicketValue.DIALOG)) {
                    ttVal.setDialog(rs.getString(position));
                } else if (attrName.equals(TroubleTicketValue.ESCALATIONLIST)) {
//                ttVal.setEscalationList((EscalationList)rs.getObject(position));
                    ObjectInputStream ois29 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setEscalationList((EscalationList) ois29.readObject());
                } else if (attrName.equals(TroubleTicketValue.INITIATINGMODE)) {
                    ttVal.setInitiatingMode(rs.getInt(position));
                } else if (attrName.equals(TroubleTicketValue.LASTUPDATETIME)) {
//                ttVal.setLastUpdateTime((java.util.Date)rs.getObject(position));
                    ObjectInputStream ois31 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setLastUpdateTime((java.util.Date) ois31.readObject());
                } else if (attrName.equals(TroubleTicketValue.MAINTSERVICECHARGE)) {
                    ttVal.setMaintServiceCharge(rs.getBoolean(position));
                } else if (attrName.equals(TroubleTicketValue.OUTAGEDURATION)) {
//                ttVal.setOutageDuration((TimeLength)rs.getObject(position));
                    ObjectInputStream ois33 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setOutageDuration((TimeLength) ois33.readObject());
                } else if (attrName.equals(TroubleTicketValue.PERCEIVEDTROUBLESEVERITY)) {
                    ttVal.setPerceivedTroubleSeverity(rs.getInt(position));
                } else if (attrName.equals(TroubleTicketValue.PREFERREDPRIORITY)) {
                    ttVal.setPreferredPriority(rs.getInt(position));
                } else if (attrName.equals(TroubleTicketValue.REPEATREPORT)) {
                    ttVal.setRepeatReport(rs.getInt(position));
                } else if (attrName.equals(TroubleTicketValue.SUSPECTOBJECTLIST)) {
//                ttVal.setSuspectObjectList((SuspectObject[])rs.getObject(position));
                    ObjectInputStream ois37 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setSuspectObjectList((SuspectObject[]) ois37.readObject());
                } else if (attrName.equals(TroubleTicketValue.TROUBLEDETECTIONTIME)) {
//                ttVal.setTroubleDetectionTime((java.util.Date)rs.getObject(position));
                    ObjectInputStream ois38 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setTroubleDetectionTime((java.util.Date) ois38.readObject());
                } else if (attrName.equals(TroubleTicketValue.TROUBLELOCATIONINFOLIST)) {
//                ttVal.setTroubleLocationInfoList((TroubleLocationInfo[])rs.getObject(position));
                    ObjectInputStream ois39 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setTroubleLocationInfoList((TroubleLocationInfo[]) ois39.readObject());
                } else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSFROMTIME)) {
//                ttVal.setTroubledObjectAccessFromTime((java.util.Date)rs.getObject(position));
                    ObjectInputStream ois40 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setTroubledObjectAccessFromTime((java.util.Date) ois40.readObject());
                } else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSHOURSLIST)) {
//                ttVal.setTroubledObjectAccessHoursList((AccessHours[])rs.getObject(position));
                    ObjectInputStream ois41 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setTroubledObjectAccessHoursList((AccessHours[]) ois41.readObject());
                } else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSTOTIME)) {
//                ttVal.setTroubledObjectAccessToTime((java.util.Date)rs.getObject(position));
                    ObjectInputStream ois42 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setTroubledObjectAccessToTime((java.util.Date) ois42.readObject());
                } else if (attrName.equals(TroubleTicketValue.SERVICEUNAVAILABLEBEGINTIME)) {
//                ttVal.setServiceUnavailableBeginTime((java.util.Date)rs.getObject(position));
                    ObjectInputStream ois43 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setServiceUnavailableBeginTime((java.util.Date) ois43.readObject());
                } else if (attrName.equals(TroubleTicketValue.SERVICEUNAVAILABLEENDTIME)) {
//                ttVal.setServiceUnavailableEndTime((java.util.Date)rs.getObject(position));
                    ObjectInputStream ois44 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setServiceUnavailableEndTime((java.util.Date) ois44.readObject());
                } else if (attrName.equals(TroubleTicketValue.ORIGINATOR)) {
                    ttVal.setOriginator(rs.getString(position));
                } else if (attrName.equals(TroubleTicketValue.TROUBLESYSTEMDN)) {
                    ttVal.setTroubleSystemDN(rs.getString(position));
                } else if (attrName.equals(TroubleTicketValue.CUSTOMER)) {
//                ttVal.setCustomer((PersonReach)rs.getObject(position));
                    ObjectInputStream ois47 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setCustomer((PersonReach) ois47.readObject());
                } else if (attrName.equals(TroubleTicketValue.ACCOUNTOWNER)) {
//                ttVal.setAccountOwner((PersonReach)rs.getObject(position));
                    ObjectInputStream ois48 = new ObjectInputStream(rs.getBlob(position).getBinaryStream());
                    ttVal.setAccountOwner((PersonReach) ois48.readObject());
                } else {
                    log("DBAccess.populateTTValueFromSelectedResultSet - Unknown attribute: " + attrName);
                }

            } catch (IOException ioex) {
                log("IOException caught in TroubleTicketBean.populateTTValueFromSelectedResultSet");
                ioex.printStackTrace();
            } catch (ClassNotFoundException cnfex) {
                log("ClassNotFoundException caught in TroubleTicketBean.populateTTValueFromSelectedResultSet");
                cnfex.printStackTrace();
            }
// KS 27-08-2003 END
        }
    }

    /**
     * Gets current connection to the connection pool.
     *
     * @return                  Connection
     * @exception               javax.ejb.EJBException
     *                          if there is a communications or systems failure
     */
    private static Connection getConnection()
            throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Closes the current connection
     *
     * @exception               javax.ejb.EJBException
     *                          if there is a communications or systems failure
     */

    private static void closeDB(Connection con, PreparedStatement ps) {

        try {
            if (ps != null) ps.close();
        } catch (Exception ex) {
            log("Error closing PreparedStatement: " + ex);
        }

        try {
            if (con != null) con.close();
        } catch (Exception ex2) {
            log("Error closing Connection: " + ex2);
        }

    }


    private static void printStringArray(String[] array, String arrayName) {
        if (array == null)
            log(" => the Array is NULL");
        else {
            log("String array: " + arrayName + ", Size = " + array.length);
            for (int x = 0; x < array.length; x++) {
                log(" => element at [" + x + "]=" + array[x]);
            }
        }
    }


    //--------------------------------------------------------------
    // You might also consider using WebLogic's log service
    //--------------------------------------------------------------
    private static void log(String s) {
        Logger.log(s);
    }


// KS 28-08-2003 START
    private static class DBAccessHelper {
        public DBAccessHelper() {
        }

        public java.lang.Object convert(java.lang.Object pBlobRef) {

            try {

                if (pBlobRef == null) {
                    log("DBAccessHelper - Blob was NULL");
                    return (null);
                } else {
                    ObjectInputStream ois = new ObjectInputStream(((Blob) pBlobRef).getBinaryStream());
                    log("DBAccessHelper - got Blob");
                    return (ois.readObject());
                }
            } catch (SQLException sqlEx) {
                log("SQLException caught in DBAccessHelper.convert");
                sqlEx.printStackTrace();
                return (null);
            } catch (IOException ioex) {
                log("IOException caught in DBAccessHelper.convert");
                ioex.printStackTrace();
                return (null);
            } catch (ClassNotFoundException cnfex) {
                log("ClassNotFoundException caught in DBAccessHelper.convert");
                cnfex.printStackTrace();
                return (null);
            }
        }

    }
// KS 28-08-2003 END


}

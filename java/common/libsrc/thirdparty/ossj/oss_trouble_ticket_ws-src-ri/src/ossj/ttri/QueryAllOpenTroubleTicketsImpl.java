package ossj.ttri;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.oss.XmlSerializer;
import javax.oss.trouble.QueryAllOpenTroubleTicketsValue;
import javax.oss.trouble.TroubleState;
import javax.oss.trouble.TroubleTicketKey;
import javax.oss.trouble.TroubleTicketValue;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * QueryAllOpenTroubleTickets Implementation Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class QueryAllOpenTroubleTicketsImpl implements Query,
        QueryAllOpenTroubleTicketsValue {

    public javax.oss.Serializer makeSerializer(String serializerType)
            throws java.lang.IllegalArgumentException {
        if (serializerType.equals(XmlSerializer.class.getName()))
            return new XmlSerializerImpl(QueryAllOpenTroubleTicketsValue.class.getName());
        else
            throw new java.lang.IllegalArgumentException("Unrecognized Serializer Type");

    }

    public String[] getSupportedSerializerTypes() {
        String[] serializerTypes = new String[1];
        serializerTypes[0] = new String(XmlSerializer.class.getName());
        return serializerTypes;
    }

    public QueryAllOpenTroubleTicketsImpl() {
    }


    public String[] getSupportedOptionalAttributeNames() {
        return null;
    }

    InitialContext initCtx;
    TroubleTicketHome ttEntityHome = null;
    java.util.Hashtable hashTab = null;
    Enumeration enum = null;
    int returnMode = -1;
    java.util.Vector resultVect;
    PropertyList props;
    String[] attrNames = null;

    public void initialize() {
    }

    public QueryAllOpenTroubleTicketsImpl(String[] attrNames) {
        log("QueryAllOpenTroubleTicketsImpl::ctor");
        this.attrNames = attrNames;

    }

    public void setSelectedAttributeNames(String[] attrNames) {
        this.attrNames = attrNames;
    }


    /**
     * Deep copy of this Event
     *
     * @return deep copy of this Event
     */
    public Object clone()
//throws CloneNotSupportedException
    {
        return null;
//super.clone();
    }

    //--------------------------------------------------------------
    // Retrieve objects for the iterator.  Objects are placed into
    // the returned array.
    //--------------------------------------------------------------
    public Object[] getNext(int howMany)
            throws TTIteratorException {


        // place the TroubleTicketValues in the retObjs array.
        int sz = howMany;
        if (resultVect.size() < howMany) sz = resultVect.size();

        Object[] retObjs = new Object[sz];
        for (int x = 0; x < sz; x++) {
            retObjs[x] = resultVect.firstElement();
            resultVect.removeElementAt(0);
        }

        //spew retObjs
        log("QueryAllOpenTroubleTicketsImpl returning " + retObjs.length + " ttvals...");
        for (int x = 0; x < retObjs.length; x++) {
            TroubleTicketValue ttVal = (TroubleTicketValue) retObjs[x];
            log("Key: " + ttVal.getTroubleTicketKey().getTroubleTicketPrimaryKey());
        }

        return retObjs;

    }

    public void setProperties(PropertyList props) {
        this.props = props;
    }

    public void abort() {
        //not implemented yet
    }

    public void reset() {
        //not implemented yet
    }

    public void executeQuery() {

        log("QueryAllOpenTroubleTicketsImpl:executeQuery");
        //-----------------------------------------------------------------
        // Implementor Notes:
        //
        // Implement an operation that retrieves all open Trouble Tickets
        // To optimize, do NOT use Entity Beans for the operation,
        // go straight to the back end.
        //
        //------------------------------------------------------------------



        //put the query results in a vector
        Connection con = null;
        PreparedStatement ps = null;
        resultVect = new java.util.Vector();

        try {
            con = getConnection();
            ps = con.prepareStatement("select * from TROUBLETICKET where TroubleState <> ?");
            ps.setInt(1, TroubleState.CLOSED);
            ps.executeQuery();

//make TTValues from the JDBC ResultSet
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                TroubleTicketValue ttValue = new TroubleTicketValueImpl(rs);
                TroubleTicketKey ttKey = ttValue.getTroubleTicketKey();
                TroubleTicketValue ttRetVal = utils.setDesiredAttributes(ttValue, attrNames);
                ttRetVal.setTroubleTicketKey(ttKey);
                resultVect.add(ttRetVal);
            }
            log("QueryAllOpenTTsImpl - found " + resultVect.size() + " open TTs");
            ps.close();
            con.close();

        } catch (SQLException sqlex) {
            Logger.logException("SQLException caught in QueryAllopenTTs.initialize", sqlex);
            sqlex.printStackTrace();
        }

    }

    private Connection getConnection()
            throws SQLException {

        InitialContext initCtx = null;
        log("looking up the Data Source");
        try {
            initCtx = new InitialContext();
            //Wipro
//            DataSource ds = (javax.sql.DataSource)
//                    initCtx.lookup("java:comp/env/jdbc/ossjttri");
            DataSource ds = (javax.sql.DataSource)
                    initCtx.lookup(TTHelper.projectProperties.getProperty("TT_DS_NAME"));
            //Wipro
            //initCtx.lookup("java:comp/env/jdbc/demoPool");
            return ds.getConnection();
        } catch (NamingException ne) {
            Logger.logException("Failed to lookup JDBC Datasource. Please double check that \n the JNDI name defined in the resource-description of the ", ne);
            Logger.log("EJB's weblogic-ejb-jar.xml file is the same as the JNDI name \nfor the Datasource defined in your config.xml.");
            throw new EJBException(ne);
        } finally {
            try {
                if (initCtx != null) initCtx.close();
            } catch (NamingException ne) {
                Logger.logException("Error closing context: ", ne);
                throw new EJBException(ne);
            }
        }
    }

    public void cleanup() {
        //release resources (e.g. pool connection)
    }


    public void log(String s) {
        Logger.log(s);
    }


    //XML methods
    public void fromXml(org.w3c.dom.Element doc) {
    }

    public String toXml() {
        return new String("Not implemented");
    }

    public String toXml(String xmlString) {
        return new String("Not implemented");
    }

    public String getRootName() {
        return new String("Not implemented");
    }

    public String getXmlHeader() {
        return new String("Not implemented");
    }

    //-------------------------------------------------------------------------
    //
    // Methods from AttributeAccess - not implemented yet.
    //
    //-------------------------------------------------------------------------

    /**
     * Get multiple attribute values given an array of attribute names.
     *
     * @param attributeNames
     * @exception java.lang.IllegalArgumentException
     * @exception java.lang.IllegalStateException
     */
    public java.util.Map getAttributeValues(String[] attributeNames)
            throws java.lang.IllegalArgumentException,
            java.lang.IllegalStateException {
        return new java.util.Hashtable();
    }

    /**
     * Setting multiple attribute values
     *
     * @param attributeNamesAndValuePairs
     * @exception java.lang.IllegalArgumentException
     * @exception java.lang.IllegalStateException
     */
    public void setAttributeValues(java.util.Map attributeNamesAndValuePairs)
            throws java.lang.IllegalArgumentException,
            java.lang.IllegalStateException {
    }

    /**
     * Get all populated attribute values.
     *
     * @exception java.lang.IllegalStateException
     */
    public java.util.Map getAllPopulatedAttributes()
            throws java.lang.IllegalStateException {
        return new java.util.Hashtable();
    }


    /** This method returns the value of the specified attribute.
     * @param attributeName the attribute's name
     * @return The attribute's value. Primitive types are wrapped in their respective classes.
     * @throws java.lang.IllegalArgumentException An <CODE>IllegalArgumentException</CODE> is thrown, when
     * <UL>
     * <LI>there is no attribute with this name
     * <LI>the attribute is not populated
     * </UL>
     */
    public Object getAttributeValue(String attributeName)
            throws java.lang.IllegalArgumentException,
            java.lang.IllegalStateException {
        return new Object();
    }

    /** Assings a new value to an attribute. <p>
     * Even though some attributes
     * may be readonly in the server implementation, they can be set here nontheless.
     * This is because value objects are also used as templates for a "query by template".
     * To see which attributes can be set in the server implementation, the client needs to call
     * <CODE>getSettableAttributeNames()</CODE>
     * @param attributeName The attribute's name which shall be changed
     * @param Value The attribute's new value. This can either be:
     * <UL>
     * <LI>An Object which can be casted to the real type of <CODE>attributesName</CODE>
     * <LI>A wrapper class for primitive types, i.e. <CODE>Integer</CODE> instead of <CODE>int</CODE>.
     * In any other case an exception is thrown.
     * </UL>
     * @throws java.lang.IllegalArgumentException This Exception is thrown, when either
     * <UL>
     * <LI>There is no attribute with this name
     * <LI>The value is out-of-range.
     * </UL>
     */
    public void setAttributeValue(String attributeName, Object Value)
            throws java.lang.IllegalArgumentException {
    }


    /** Returns all attribute names, which are available in this value object.
     * <p>
     * Use
     * one of the returned names to access the generic methods <CODE>getAttributeValue(...)</CODE>
     * and <CODE>setAttributeValue(...)</CODE>.
     * <p>This method may be used by generic
     * clients to obtain information on the attributes. It does not say anything about
     * the state of an attribute, i.e. if it is populated or not.
     * @return the array contains all attribute names in no particular order.
     */
    public String[] getAttributeNames() {
        return new String[1];
    }

    /** Gets all attribute names, which attribute values contain something
     * meaningful.
     * <p>Although an attribute is populated, it can be <CODE>null</CODE>!
     * @return all names of attributes, which contain some data.
     * When no attributes are populated an <B>empty array</B> is
     * returned.
     * It is required to return a subset of the array returned
     * by <CODE>getAttributeNames()</CODE>.
     */
    public String[] getPopulatedAttributeNames() {
        return new String[1];
    }


    /** Checks if a specific attribute is populated.
     * If the value object is fully
     * populated, i.e. <CODE>isFullyPopulated()</CODE> returns true,
     * this method returns true;
     * @param name the name of the attribute which is to be checked for population
     * @throws java.lang.IllegalArgumentException this exception is thrown, when there is no attribute with this name
     * @return true, if this attribute contains some data, false otherwise
     * @see #isFullyPopulated()
     */
    public boolean isPopulated(String name)
            throws java.lang.IllegalArgumentException {
        //VP
        if (name == null)
            throw new java.lang.IllegalArgumentException("null is not a valid attribute Name");
        return false;
    }

    /** Returns true, if all attributes in this value object are populated.
     *
     * @return true, if all attributes are populated
     * @see #isPopulated(String attribute)
     */
    public boolean isFullyPopulated() {
        return false;
    }


    /**
     * Reset all the attributes to unpopulated.
     * After this call calling getAllAttributes() must
     * raise the IllegalStateException.
     */
    public void unpopulateAllAttributes() {
    }


    /**
     * Unpopulate a Single Attribute.
     * After this call getAttribute( String attName ) must
     * raise the IllegalStateException
     *
     * @throws java.lang.IllegalArgumentException thrown, if this is not a valid attribute name
     *
     * @param attr_name The attribute which shall be unpopulated.
     * @exception java.lang.IllegalArgumentException
     * @see #unpopulateAllAttributes()
     */
    public void unpopulateAttribute(String attr_name)
            throws java.lang.IllegalArgumentException {
        //VP
        if (attr_name == null)
            throw new java.lang.IllegalArgumentException("null is not a valid attribute Name");
    }

    public String getRootAttributes() {
        return null;
    }

}



package ossj.ttri;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.oss.EventPropertyDescriptor;
import javax.oss.ManagedEntityValue;
import javax.oss.ManagedEntityValueIterator;
import javax.oss.QueryValue;
import javax.oss.trouble.*;
import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;

/**
 * JVTTroubleTicketSessionBean Class
 *
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class JVTTroubleTicketSessionBean implements SessionBean {


    private static final boolean VERBOSE = true;

    private SessionContext ctx;
    private InitialContext initCtx;

    private TroubleTicketHome ttEntityHome = null;
    private TroubleTicketValueIteratorHome ttValueIterHome = null;
    private TroubleTicketKeyResultIteratorHome ttKeyResultIterHome = null;

    //supported types
    private static final String TroubleTicketType = "TroubleTicket";
    private static final String[] SupportedTypes = {TroubleTicketType};

    //supported operations
    private static final String createTroubleTicketOp = "createTroubleTicket";
    private static final String createTroubleTicketsOp = "createTroubleTickets";
    private static final String getTroubleTicketOp = "getTroubleTicket";
    private static final String getTroubleTicketsOp = "getTroubleTickets";


    private static final String[] SupportedOperations = {createTroubleTicketOp,
                                                         createTroubleTicketsOp,
                                                         getTroubleTicketOp,
                                                         getTroubleTicketsOp};
    private static final String[] MANAGEDENTITY_TYPES = {TroubleTicketValue.class.getName()};
    private static final String[] QUERY_TYPES = {QueryAllOpenTroubleTicketsValue.class.getName()};  //WIPRO

//WIPRO
    private static final String[] EVENT_TYPES = {TroubleTicketCloseOutEvent.class.getName(),
                                                 TroubleTicketCancellationEvent.class.getName(),
                                                 TroubleTicketAttributeValueChangeEvent.class.getName(),
                                                 TroubleTicketStatusChangeEvent.class.getName(),
                                                 TroubleTicketCreateEvent.class.getName()};
//WIPRO

    //-----------------------------------------------------------------------------
    //
    // Container callbacks/methods
    //
    //-----------------------------------------------------------------------------
    public void ejbCreate() {

        log("TTSession:ejbCreate");

        try {
            initCtx = new InitialContext();
        } catch (NamingException nex) {
            Logger.logException("TTSession:ejbCreate:  Naming exception caught while creating InitialContext", nex);
            nex.printStackTrace();
        }

        TTConfig.getInstance();

    }

    public void ejbActivate() {
        log("TTSession:ejbActivate");
    }

    public void ejbRemove() {
        log("TTSession:ejbRemove");

        try {
            initCtx.close();
        } catch (NamingException nex) {
        }

    }

    public void ejbPassivate() {
        log("TTSession:ejbPassivate called");
    }

    public void setSessionContext(SessionContext ctx) {
        log("TTSession:setSessionContext");
        this.ctx = ctx;
    }



    //-----------------------------------------------------------------------------
    //
    // JVTSESSION interface methods
    //
    //-----------------------------------------------------------------------------

    //------------------------------------------------------------------
    // MetaData Methods
    //------------------------------------------------------------------


    /**
     * Get the Operations supported by The Trouble Ticket
     * Session Bean.
     *
     * @exception java.rmi.RemoteException
     */
    public String[] getSupportedOperations()
            throws java.rmi.RemoteException {
        return SupportedOperations;
    }

    /**
     * Get the names of the optional operations supported by a JVT
     * Session Bean
     *
     * @return String array which contains the names of all the supported
     * operations. An operation name is the string as it would be
     * returned by calling the Method.toString() operation on each
     * individual elements returned by the class.getMethods()
     * on the Optional Operations supported by the Session interface.
     * @exception javax.ejb.EJBException
     */
    public String[] getSupportedOptionalOperations()
            throws javax.ejb.EJBException {
        return new String[0];
    }

    /**
     * Get the Supported Trouble Ticket types.
     *
     * @exception java.rmi.RemoteException
     */

    public String[] getTroubleTicketTypes()
            throws java.rmi.RemoteException {
        return SupportedTypes;
    }

    /**
     * Get the supported Query Names
     *
     * @return The names of the supported Query Types
     * @exception javax.ejb.EJBException
     */
    public String[] getQueryTypes()
            throws javax.ejb.EJBException {
        //VP return new String[0];
        return QUERY_TYPES;
    }


    /**
     * Get the supported Event Type Names
     *
     * @return The names of the supported Event Types
     * @exception javax.ejb.EJBException
     */
    public String[] getEventTypes()
            throws javax.ejb.EJBException {
        //return new String[0];
        return EVENT_TYPES;
    }

    /**
     * Get the Event Descriptor associated with an event type
     *
     * @param eventType Event Type Name
     * @exception javax.ejb.EJBException
     */
    public EventPropertyDescriptor getEventDescriptor(String eventType)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {

        if (eventType.equals(TroubleTicketCreateEvent.class.getName())) {
            return new TroubleTicketCreateEventPDImpl();
        } else if (eventType.equals(TroubleTicketCloseOutEvent.class.getName())) {
            return new TroubleTicketCloseOutEventPDImpl();
        } else if (eventType.equals(TroubleTicketAttributeValueChangeEvent.class.getName())) {
            return new TroubleTicketAttributeValueChangeEventPDImpl();
        } else if (eventType.equals(TroubleTicketCancellationEvent.class.getName())) {
            return new TroubleTicketCancellationEventPDImpl();
        } else if (eventType.equals(TroubleTicketStatusChangeEvent.class.getName())) {
            return new TroubleTicketStatusChangeEventPDImpl();
        }

        throw new javax.oss.IllegalArgumentException("Unknown Event Type: " + eventType);

    }


    /**
     * Get the Managed Entity types supported by a JVT Session Bean
     *
     * @return String array which contains the fully qualified names of the leaf
     * node interfaces representing the supported managed entity types.
     * Note that it is not the implementation class name that is returned.
     * @exception javax.ejb.EJBException
     */


    public String[] getManagedEntityTypes()
            throws javax.ejb.EJBException {

        return MANAGEDENTITY_TYPES;
    }


    //----------------------------------------------------------------------------
    //
    // Factory Methods
    //
    //----------------------------------------------------------------------------
    /**
     * Create a Value Type object for a specific Managed Entity type.
     * Not to be confused with the creation of a Managed Entity.
     * The Session Bean is used as a factory for the creation of
     * Value types.
     *
     * @param valueType fully qualified name of the leaf managed entity value interface
     * @return the implementation class of a managed entity of a specific type
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.EJBException
     */

    public ManagedEntityValue makeManagedEntityValue(String valueType)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {
        try {
			//Wipro
			//return makeTroubleTicketValue(new String());
			return makeTroubleTicketValue(valueType);
			//Wipro
        } catch (Exception ex) {
            throw new javax.ejb.EJBException();
        }
    }


    /**
     * Create a Value Type object for a specific Trouble Ticket type.
     * Not to be confused with the creation of a Trouble Ticket.
     * The Session Bean is used as a factory for the creation of Value types.
     *
     * @param valuetype ""
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.EJBException
     */

    public TroubleTicketValue makeTroubleTicketValue(String valuetype)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {
        //ignore the type for now
		
//Wipro
//        return new TroubleTicketValueImpl();
		if(valuetype.equals("javax.oss.trouble.TroubleTicketValue")){
			return new TroubleTicketValueImpl();
		}else {
            throw new javax.oss.IllegalArgumentException("Unrecognized Trouble Ticket Value Type" +  valuetype);
        }
    }
//Wipro
	
    /**
     * Create a Query Value Instance matching a Query type
     *
     * @param queryType The name of the Query Type
     * @exception javax.ejb.EJBException
     */
    public QueryValue makeQueryValue(String queryType)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {

        //PG BAD...
        if (queryType.equals(QueryAllOpenTroubleTicketsValue.class.getName())) {
            return new QueryAllOpenTroubleTicketsImpl();
        } else {
            throw new javax.oss.IllegalArgumentException("Unrecognized Query Type" + queryType);
        }
    }



    //----------------------------------------------------------------------------
    //
    // Query Methods
    //
    //----------------------------------------------------------------------------

    /**
     * Query Multiple Managed Entities using a QueryValue
     *
     * @param query A QueryValue object representing the Query.
     * @param attrNames representing the attribute names
     * @return A ManagedEnityValueIterator used to extract the results of the Query.
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.EJBException
     */

    public ManagedEntityValueIterator
            queryManagedEntities(QueryValue query, String[] attrNames)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {
        return null;    //not implemented yet...
    }

    //----------------------------------------------------------------------------
    //
    // Get Methods
    //
    //----------------------------------------------------------------------------

    /**
     * Get a Single Trouble Ticket given its Keys
     * and return only the requested attributes.
     *
     * @param key       TT Key
     * @param attrNames attribute selection
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.ObjectNotFoundException
     * @exception javax.ejb.EJBException
     */
    public TroubleTicketValue
            getTroubleTicketByKey(TroubleTicketKey key, String[] attrNames)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.ObjectNotFoundException,
            javax.ejb.EJBException {

        //for now, use findByPrimaryKey.  Use common group finder later
        //since we are a stateless session, look up the entity home again.

        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null)
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");

        TroubleTicketValue retVal = new TroubleTicketValueImpl();
        TroubleTicket ttIF = null;
        try {
            ttIF = (TroubleTicket) PortableRemoteObject.narrow(ttEntityHome.findByPrimaryKey((TroubleTicketKeyImpl) key),
                    TroubleTicket.class);
        } catch (javax.ejb.FinderException fex) {
            //VP add the key ID in exception
            throw new javax.ejb.ObjectNotFoundException("key=[" + (String) key.getPrimaryKey() + "]");
        } catch (java.rmi.RemoteException rex) {
            Logger.logException("RemoteException caught in JVTSessBean:getTroubleTicket", rex);
            throw new EJBException("RemoteException" + rex.getMessage());
        }


        try {
            retVal = ttIF.getAttributes(attrNames);
            TroubleTicketKey ttKey = ttIF.getTroubleTicketKey();
            // ttKey.setApplicationDN( TTConfig.getInstance().getApplicationTypeDN());
            // ttKey.setApplicationContext(TTConfig.getInstance().getApplicationContext());
            retVal.setTroubleTicketKey(ttKey);
        } catch (java.rmi.RemoteException rex) {
            Logger.logException("RemoteException caught in JVTSessBean:getTroubleTicket", rex);
            throw new EJBException("RemoteException" + rex.getMessage());
        }
        return retVal;

    }

    /**
     * Get Multiple Trouble Tickets given their Keys
     * and return only the requested attributes
     *
     * @param keys TT Keys
     * @param attrNames attribute selection
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.FinderException
     * @exception javax.ejb.EJBException
     */
    public TroubleTicketValue[]
            getTroubleTicketsByKeys(TroubleTicketKey[] keys, String[] attrNames)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.FinderException,
            javax.ejb.EJBException {

        //validate stuff
        if (keys == null)
            throw new javax.oss.IllegalArgumentException("Null TroubleTicketKey[] in processGet operation");
        if (keys.length == 0)
            throw new javax.oss.IllegalArgumentException("Zero length TroubleTicketKey[] in processGet operation");

        // since we are a stateless session, look up the entity home again.
        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null)
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");

        // delegate the call to the Entity Home
        try {
            return ttEntityHome.getTroubleTickets(keys, attrNames);
        } catch (java.rmi.RemoteException rex) {
            Logger.logException("JVTTTSession.getTroubleTickets:  Remote Exception on TTHome getTroubleTickets", rex);
            throw new EJBException("RemoteException" + rex.getMessage());
        }

    }

    /**
     * Get Multiple Trouble Tickets matching the value template
     * and return only the requested attributes
     *
     * @param template TT Template
     * @param attrNames attribute selection
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.ObjectNotFoundException
     * @exception javax.ejb.EJBException
     */
    public TroubleTicketValueIterator
            getTroubleTicketsByTemplate(TroubleTicketValue template, String[] attrNames)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.FinderException,
            javax.ejb.EJBException {

        //validate stuff
        if (template == null)
            throw new javax.oss.IllegalArgumentException("Null template in getTroubleTickets");
        //NB. null or empty attrNames means return all.

        TroubleTicketValue[] templates = new TroubleTicketValue[1];
        templates[0] = template;

        //since were stateless, look up the Entity Bean Home again
        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null)
            throw new javax.ejb.ObjectNotFoundException("Lookup of TTValueIteratorHome failed");

        try {
            return ttEntityHome.getTroubleTickets(templates, attrNames);
        } catch (java.rmi.RemoteException rex) {
            throw new EJBException("RemoteException" + rex.getMessage());
        }


    }


    /**
     * Get Multiple Trouble Tickets matching at least one
     * of the value templates
     * and return only the requested attributes
     *
     * @param templates TT Values
     * @param attrNames attribute selection
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.FinderException
     * @exception javax.ejb.EJBException
     */
    public TroubleTicketValueIterator
            getTroubleTicketsByTemplates(TroubleTicketValue[] templates, String[] attrNames)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.FinderException,
            javax.ejb.EJBException {
        //validate templates
        if (templates == null)
            throw new javax.oss.IllegalArgumentException("Null templates in getTroubleTickets");
        if (templates.length == 0)
            throw new javax.oss.IllegalArgumentException("Zero length templates in getTroubleTickets");
        for (int x = 0; x < templates.length; x++) {
            if (templates[x] == null)
                throw new javax.oss.IllegalArgumentException("Null template in getTroubleTickets");
        }
        //NB. null or empty attrNames means return all.

        //since were stateless, look up the Entity Bean Home again
        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null)
            throw new javax.ejb.ObjectNotFoundException("Lookup of TTValueIteratorHome failed");

        try {
            return ttEntityHome.getTroubleTickets(templates, attrNames);
        } catch (java.rmi.RemoteException rex) {
            throw new EJBException("RemoteException" + rex.getMessage());
        }

    }



    //----------------------------------------------------------------------------
    //
    // Single Ticket Methods - create, set, escalate, close, cancel
    //
    //----------------------------------------------------------------------------

    /**
     * Create a Single Trouble Ticket given an initial value
     *
     * @param value TT Value
     * @return A new trouble ticket
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.DuplicateKeyException
     * @exception javax.ejb.CreateException
     * @exception javax.ejb.EJBException
     */
    public TroubleTicketKey
            createTroubleTicketByValue(TroubleTicketValue value)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.CreateException,
            javax.ejb.EJBException {

        log("--- JVTSessionBean::createTroubleTicket ---");

        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null)
            throw new javax.ejb.CreateException("Lookup of TT Entity Bean Home failed");

        TroubleTicket ttEntityIF = null;
        //use Entity Bean home to create the TT
        try {
            ttEntityIF = ttEntityHome.create(value);
        } catch (CreateException cex) {
            Logger.logException("CreateException caught in TTSessionBean::createTroubleTicket", cex);
            cex.printStackTrace();
            throw cex;
        } catch (RemoteException rex) {
            Logger.logException("RemoteException caught in TTSessionBean::createTroubleTicket", rex);
            rex.printStackTrace();
            throw new EJBException("RemoteException" + rex.getMessage());
        }

        //return the assigned key
        log("JVTSession:createTroubleTicket - getting assigned ttKey...");
        TroubleTicketKey retKey = null;
        try {
            retKey = ttEntityIF.getTroubleTicketKey();
        } catch (java.rmi.RemoteException rex) {
            Logger.logException("RemoteException caught in TTSessionBean::createTroubleTicket - retrieving TT Key", rex);
            rex.printStackTrace();
            throw new EJBException("RemoteException" + rex.getMessage());
        }

        log("JVTSession:createTroubleTicket - assigned ttKey: " + retKey.getPrimaryKey());

        //set some values (temp workaround for suspected XML bug...

        retKey.setType("TroubleTicketPrimaryKey");
        return retKey;

    }


    /**
     * Update a Single Trouble Ticket given a Value Object
     *
     * @param value TT Value
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.oss.SetException
     * @exception javax.ejb.ObjectNotFoundException
     * @exception javax.ejb.EJBException
     */
    public void
            setTroubleTicketByValue(TroubleTicketValue value, boolean resyncRequired)
            throws javax.oss.IllegalArgumentException,
            javax.oss.SetException,
            javax.ejb.ObjectNotFoundException,
            javax.ejb.EJBException,
            javax.oss.ResyncRequiredException {
        /* Context ctx = null;
         UserTransaction tx = null;
         try {
         ctx = new InitialContext();
         tx = (UserTransaction) ctx.lookup( "javax.transaction.UserTransaction");
         tx.begin();
         }

         catch( Exception e ) {
             log ("Fail to begin transaction");
         }

         */


        //since we are a stateless session, look up the entity home again.
        //do upfront check of lastUpdateNumber if resyncRequired is set
        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null)
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");

        TroubleTicketKey ttKey = value.getTroubleTicketKey();

        TroubleTicket ttIF = null;
        try {

            ttIF = (TroubleTicket) PortableRemoteObject.narrow(ttEntityHome.findByPrimaryKey((TroubleTicketKeyImpl) ttKey),
                    TroubleTicket.class);
        } catch (javax.ejb.FinderException fex) {
            throw new javax.ejb.ObjectNotFoundException();
        } catch (java.rmi.RemoteException rex) {
            throw new EJBException("RemoteException" + rex.getMessage());
        }


        try {
            log("JVTSession calling TTBean::setAttributes");
            ttIF.setAttributes(value, resyncRequired);
        } catch (java.rmi.RemoteException rex) {
            Logger.logException("RemoteException caught in JVTSessBean:setTroubleTicket", rex);
            throw new EJBException("RemoteException" + rex.getMessage());
        } catch (javax.oss.IllegalAttributeValueException e) {
            throw new javax.oss.IllegalArgumentException(e.getMessage());
        } catch (javax.oss.ResyncRequiredException ex) {
            throw ex;
        }
        /* try {
              tx.rollback();
          }
          catch( Exception e2 ) {
              log("fail to rollback transaction");
          }

          */

    }

    /**
     * Close a Trouble Ticket matching a Trouble Ticket Key
     *
     * @param key Trouble Ticket Key
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.ObjectNotFoundException
     * @exception javax.ejb.EJBException
     */
    public void closeTroubleTicketByKey(TroubleTicketKey key,
                                        int closeOutStatus,
                                        String closeOutNarr)
            throws javax.oss.IllegalArgumentException,
            javax.oss.trouble.CloseException,
            javax.ejb.ObjectNotFoundException,
            javax.ejb.EJBException {


        //since we are a stateless session, look up the entity home again.
        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null)
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");

        TroubleTicket ttIF = null;
        try {
            ttIF = (TroubleTicket) PortableRemoteObject.narrow(ttEntityHome.findByPrimaryKey((TroubleTicketKeyImpl) key),
                    TroubleTicket.class);
        } catch (javax.ejb.FinderException fex) {
            Logger.logException("JVTTTSession:closeTroubleTicket: FinderException ", fex);
            log("Could not find TT Entity Bean with primary key: " + key.getTroubleTicketPrimaryKey());
            throw new javax.ejb.ObjectNotFoundException();
        } catch (java.rmi.RemoteException rex) {
            Logger.logException("RemoteException caught in JVTSessBean:closeTroubleTicket", rex);
            throw new EJBException("RemoteException" + rex.getMessage());
        }

        try {
            ttIF.closeTroubleTicket(closeOutStatus, closeOutNarr);
        } catch (java.rmi.RemoteException rex) {
            Logger.logException("RemoteException caught in JVTTTSession:closeTroubleTicket", rex);
            throw new EJBException("RemoteException" + rex.getMessage());
        }

    }


    /**
     * Escalate a Trouble Ticket matching a given Key
     *
     * @param key TT Key
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.ObjectNotFoundException
     * @exception javax.ejb.EJBException
     */

    public void escalateTroubleTicketByKey(TroubleTicketKey key,
                                           EscalationList esc)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.ObjectNotFoundException,
            javax.oss.trouble.EscalateException,
            javax.ejb.EJBException {

        //since we are a stateless session, look up the entity home again.
        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null) {
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");
        }

        TroubleTicket ttIF = null;
        try {
            ttIF = (TroubleTicket) PortableRemoteObject.narrow(ttEntityHome.findByPrimaryKey((TroubleTicketKeyImpl) key),
                    TroubleTicket.class);
        } catch (javax.ejb.FinderException fex) {
            Logger.logException("JVTTTSession:escalateTroubleTicket: FinderException ", fex);
            log("Could not find TT Entity Bean with primary key: " + key.getTroubleTicketPrimaryKey());
            throw new javax.ejb.ObjectNotFoundException();
        } catch (java.rmi.RemoteException rex) {
            Logger.logException("RemoteException caught in JVTTTSession:escalateTroubleTicket", rex);
            throw new EJBException("RemoteException" + rex.getMessage());
        }

        try {
            ttIF.escalateTroubleTicket(esc);
        } catch (java.rmi.RemoteException rex) {
            Logger.logException("RemoteException caught in JVTTTSession:escalateTroubleTicket", rex);
            throw new EJBException("RemoteException" + rex.getMessage());
        }

    }

    /**
     * Cancel a Trouble Ticket matching a Trouble Ticket Key
     *
     * @param key Trouble Ticket Key
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.ObjectNotFoundException
     * @exception javax.ejb.EJBException
     */
    public void cancelTroubleTicketByKey(TroubleTicketKey key,
                                         int closeOutStatus,
                                         String closeOutNarr)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.ObjectNotFoundException,
            javax.oss.trouble.CancelException,
            javax.ejb.EJBException {

        //since we are a stateless session, look up the entity home again.
        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null)
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");

        TroubleTicket ttIF = null;
        try {
            ttIF = (TroubleTicket) PortableRemoteObject.narrow(ttEntityHome.findByPrimaryKey((TroubleTicketKeyImpl) key),
                    TroubleTicket.class);
        } catch (javax.ejb.FinderException fex) {
            Logger.logException("JVTTTSession.cancelTroubleTicket: FinderException ", fex);
            log("Could not find TT Entity Bean with primary key: " + key.getTroubleTicketPrimaryKey());
            throw new javax.ejb.ObjectNotFoundException();
        } catch (java.rmi.RemoteException rex) {
            Logger.logException("RemoteException caught in JVTSessBean:cancelTroubleTicket", rex);
            throw new EJBException("RemoteException" + rex.getMessage());
        }

        try {
            ttIF.cancelTroubleTicket(closeOutStatus, closeOutNarr);
        } catch (java.rmi.RemoteException rex) {
            Logger.logException("RemoteException caught in JVTTTSession:cancelTroubleTicket", rex);
            throw new EJBException("RemoteException" + rex.getMessage());
        }

    }



    //----------------------------------------------------------------------------
    //
    // Query
    //
    //----------------------------------------------------------------------------

    /**
     * Query Multiple Trouble Tickets using a Query Value and a set of parameters
     *
     * @param query A QueryValue
     * @param attrNames Selected Attributes
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.EJBException
     */
    public TroubleTicketValueIterator
            queryTroubleTickets(QueryValue query, String[] attrNames)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {


        //We will use the Entity Home business method to do this.
        //since we are a stateless session, look up the entity home again.

        //kludge - use remote exception - Q. should API throw ObjectNotFound for all ops?
        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null) {
            throw new javax.ejb.EJBException("Lookup of TT Entity Bean Home failed");
        }

        //defer to the Entity Home business method
        TroubleTicketValueIterator ttValueIter = null;
        try {
            ttValueIter = ttEntityHome.queryTroubleTickets(query, attrNames);
        } catch (java.rmi.RemoteException rex) {
            Logger.logException("JVTTTSession:queryTroubleTickets:  Remote Exception on TTHome queryTroubleTickets", rex);
            throw new EJBException("RemoteException" + rex.getMessage());
        }


        return ttValueIter;

    }





    //----------------------------------------------------------------------------
    //
    // Best Effort Bulk Update/Create Operations
    //
    //----------------------------------------------------------------------------

    //Best Effort Creation of Multiple Trouble Tickets each with a different value
    public TroubleTicketKeyResult[]
            tryCreateTroubleTicketsByValues(TroubleTicketValue[] values)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {

        try {
            return processValues(values, OperationTypes.CREATE, null);
        }
                //map exceptions
        catch (javax.ejb.ObjectNotFoundException onfEx) {
            throw new javax.ejb.EJBException(onfEx.getMessage());
        } catch (java.rmi.RemoteException rex) {
            throw new EJBException("RemoteException" + rex.getMessage());
        }

    }


    /**
     * Best Effort Update Multiple Trouble Tickets each with a different value
     *
     * @param values TT Values
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.EJBException
     */

    public TroubleTicketKeyResult[]
            trySetTroubleTicketsByValues(TroubleTicketValue[] values, boolean resyncRequired)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {

        try {
            return processValues(values, OperationTypes.SET, null);
        }
                //map exceptions
        catch (javax.ejb.ObjectNotFoundException onfEx) {
            throw new javax.ejb.EJBException(onfEx.getMessage());
        } catch (java.rmi.RemoteException rex) {
            throw new EJBException("RemoteException" + rex.getMessage());
        }


    }


    /**
     * Best Effort Update Multiple Trouble Tickets given their Keys
     * to the same value
     *
     * @param keys TT Keys
     * @param value TT Value
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.EJBException
     */
    public TroubleTicketKeyResult[]
            trySetTroubleTicketsByKeys(TroubleTicketKey[] keys, TroubleTicketValue value)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {
        /* Context ctx = null;
         UserTransaction tx = null;
         try {
         ctx = new InitialContext();
         tx = (UserTransaction) ctx.lookup( "javax.transaction.UserTransaction");
         tx.begin();
         }
         catch( Exception e ) {
             log ("Fail to begin transaction");
         }*/
        try {
            TroubleTicketKeyResult[] kr = processKeys(keys, value, OperationTypes.SET, null);
            for (int x = 0; x < kr.length; x++) {

                TroubleTicketKeyResult ttkr = kr[x];
                if (ttkr == null) {
                    log("null KeyResult at array element " + x);
//fail ("null KeyResult at array element " + x);
//return;
                }
                TroubleTicketKey ttKey = kr[x].getTroubleTicketKey();
                if (ttKey != null) {
                    String ttId = kr[x].getTroubleTicketKey().getTroubleTicketPrimaryKey();
                    if (!ttkr.isSuccess()) {
                        log("Create/update FAILED for TT Id: " + ttId);
                        log("Failure reason: " + kr[x].getException().getMessage());
//fail ("Create/update FAILED for TT Id: " + ttKey);
                    } else {
                        log("Create/update SUCCEEDED for TT Id: " + ttId);
//TroubleTicketValue ttVal = getTT( ttKey);
//compareValues( ttVals[x], ttVal , true );
//receivedKeys.add( ttKey );
                    }
                } else {
                    log("trouble ticket key is null at: " + x);
                }
            }
/* try {
            tx.rollback();
        }
        catch( Exception e2 ) {
            log("fail to rollback transaction");
        } */
            return kr;

        }
                //map exceptions
        catch (javax.ejb.ObjectNotFoundException onfEx) {
            throw new javax.ejb.EJBException(onfEx.getMessage());
        } catch (java.rmi.RemoteException rex) {
            throw new EJBException("RemoteException" + rex.getMessage());
        }

    }

    /**
     * Update Multiple Trouble Tickets using associative lookup
     *
     * @param template Trouble Ticket Template
     * @param value Value to which the Trouble Tickets matching
     * the template will be updated to.
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.EJBException
     */
    public TroubleTicketKeyResultIterator
            trySetTroubleTicketsByTemplate(TroubleTicketValue template,
                                           TroubleTicketValue value,
                                           boolean failuresOnly)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {


        TroubleTicketValue[] templates = new TroubleTicketValue[1];
        templates[0] = template;

        try {
            return processTemplates(templates, value, failuresOnly, OperationTypes.SET, null);
        }
                //map exceptions
        catch (javax.ejb.ObjectNotFoundException onfEx) {
            throw new javax.ejb.EJBException(onfEx.getMessage());
        } catch (java.rmi.RemoteException rex) {
            throw new EJBException("RemoteException" + rex.getMessage());
        }

    }

    /**
     * Update Multiple Trouble Tickets matching at least one of the template
     * to the same value
     *
     * @param templates TT Values
     * @param value TT Value
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.EJBException
     */

    public TroubleTicketKeyResultIterator
            trySetTroubleTicketsByTemplates(TroubleTicketValue[] templates,
                                            TroubleTicketValue value,
                                            boolean failuresOnly)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {
        try {
            return processTemplates(templates, value, failuresOnly, OperationTypes.SET, null);
        }
                //map exceptions
        catch (javax.ejb.ObjectNotFoundException onfEx) {
            throw new javax.ejb.EJBException(onfEx.getMessage());
        } catch (java.rmi.RemoteException rex) {
            throw new EJBException("RemoteException" + rex.getMessage());
        }
    }


    /**
     * Best Effort Escalate Multiple Trouble Tickets given their keys.
     *
     * @param keys TT Keys
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.EJBException
     */
    public TroubleTicketKeyResult[]
            tryEscalateTroubleTicketsByKeys(TroubleTicketKey[] keys, EscalationList esc)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {
        //build the "Escalation" property
        Property prop = new Property("EscalationList");
        prop.setObjectProperty(esc);
        PropertyList propList = new PropertyList();
        propList.addProperty(prop);

        try {
            return processKeys(keys, null, OperationTypes.ESCALATE, propList);
        }
                //map exceptions
        catch (javax.ejb.ObjectNotFoundException onfEx) {
            throw new EJBException(onfEx.getMessage());
        } catch (java.rmi.RemoteException rex) {
            throw new EJBException("RemoteException" + rex.getMessage());
        }
    }


    /**
     * Best Effort Escalate every Trouble Ticket matching at least one of the template
     *
     * @param template TT Value
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.EJBException
     */

    public TroubleTicketKeyResultIterator
            tryEscalateTroubleTicketsByTemplate(TroubleTicketValue template,
                                                EscalationList esc,
                                                boolean failuresOnly)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {


        TroubleTicketValue[] templates = new TroubleTicketValue[1];
        templates[0] = template;

        //build the "Escalation" property
        Property prop = new Property("EscalationList");
        prop.setObjectProperty(esc);
        PropertyList propList = new PropertyList();
        propList.addProperty(prop);


        try {
            return processTemplates(templates, null, failuresOnly, OperationTypes.ESCALATE, propList);
        }
                //map exceptions
        catch (javax.ejb.ObjectNotFoundException onfEx) {
            throw new javax.ejb.EJBException(onfEx.getMessage());
        } catch (java.rmi.RemoteException rex) {
            throw new EJBException("RemoteException" + rex.getMessage());
        }
    }


    /**
     * Best Effort Escalate every Trouble Ticket matching at least one of the template
     *
     * @param templates TT Templates
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.EJBException
     */
    public TroubleTicketKeyResultIterator
            tryEscalateTroubleTicketsByTemplates(TroubleTicketValue[] templates,
                                                 EscalationList esc,
                                                 boolean failuresOnly)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {

        //build the "Escalation" property
        Property prop = new Property("EscalationList");
        prop.setObjectProperty(esc);
        PropertyList propList = new PropertyList();
        propList.addProperty(prop);

        try {
            return processTemplates(templates, null, failuresOnly, OperationTypes.ESCALATE, propList);
        }
                //map exceptions
        catch (javax.ejb.ObjectNotFoundException onfEx) {
            throw new javax.ejb.EJBException(onfEx.getMessage());
        } catch (java.rmi.RemoteException rex) {
            throw new EJBException("RemoteException" + rex.getMessage());
        }
    }


    /**
     * Best Effort Close Multiple Trouble Tickets given their keys
     *
     * @param keys Trouble Ticket Keys
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.EJBException
     */
    public TroubleTicketKeyResult[]
            tryCloseTroubleTicketsByKeys(TroubleTicketKey[] keys,
                                         int closeOutStatus,
                                         String closeOutNarr)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {

        //build the "CloseOutStatus" and "CloseOutNarr" properties
        PropertyList propList = new PropertyList();
        Property prop = null;

        prop = new Property("CloseOutStatus");
        prop.setIntProperty(closeOutStatus);
        propList.addProperty(prop);
        prop = new Property("CloseOutNarr");
        prop.setStringProperty(closeOutNarr);
        propList.addProperty(prop);

        try {
            return processKeys(keys, null, OperationTypes.CLOSE, propList);
        }
                //map exceptions
        catch (javax.ejb.ObjectNotFoundException onfEx) {
            throw new javax.ejb.EJBException(onfEx.getMessage());
        } catch (java.rmi.RemoteException rex) {
            throw new EJBException("RemoteException" + rex.getMessage());
        }

    }

    /**
     * Close the Trouble Tickets using associative lookup on
     * a single template
     *
     * @param template Trouble Ticket for which associative lookup is
     * performed. A Trouble Ticket match the template
     * if it match the populated attributes of the template.
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.EJBException
     */
    public TroubleTicketKeyResultIterator
            tryCloseTroubleTicketsByTemplate(TroubleTicketValue template,
                                             int closeOutStatus,
                                             String closeOutNarr,
                                             boolean failuresOnly)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {

        TroubleTicketValue[] templates = new TroubleTicketValue[1];
        templates[0] = template;



        //build the "CloseOutStatus" and "CloseOutNarr" properties
        PropertyList propList = new PropertyList();
        Property prop = null;

        prop = new Property("CloseOutStatus");
        prop.setIntProperty(closeOutStatus);
        propList.addProperty(prop);
        prop = new Property("CloseOutNarr");
        prop.setStringProperty(closeOutNarr);
        propList.addProperty(prop);
        //TroubleTicketValue val = new TroubleTicketValueImpl();

        try {
            return processTemplates(templates, null, failuresOnly, OperationTypes.CLOSE, propList);
        }
                //map exceptions
        catch (javax.ejb.ObjectNotFoundException onfEx) {
            throw new javax.ejb.EJBException(onfEx.getMessage());
        } catch (java.rmi.RemoteException rex) {
            throw new EJBException("RemoteException" + rex.getMessage());
        }

    }

    /**
     * Close the Trouble Tickets using associative lookup on
     * multiple templates
     *
     * @param templates Trouble Tickets for which associative lookup is
     * performed. A Trouble Ticket match the templates
     * if it match at leat one of the templates.
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.EJBException
     */
    public TroubleTicketKeyResultIterator
            tryCloseTroubleTicketsByTemplates(TroubleTicketValue[] templates,
                                              int closeOutStatus,
                                              String closeOutNarr,
                                              boolean failuresOnly)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {

        //build the "CloseOutStatus" and "CloseOutNarr" properties
        PropertyList propList = new PropertyList();
        Property prop = null;

        prop = new Property("CloseOutStatus");
        prop.setIntProperty(closeOutStatus);
        propList.addProperty(prop);
        prop = new Property("CloseOutNarr");
        prop.setStringProperty(closeOutNarr);
        propList.addProperty(prop);

        try {
            return processTemplates(templates, null, failuresOnly, OperationTypes.CLOSE, propList);
        }
                //map exceptions
        catch (javax.ejb.ObjectNotFoundException onfEx) {
            throw new javax.ejb.EJBException(onfEx.getMessage());
        } catch (java.rmi.RemoteException rex) {
            throw new EJBException("RemoteException" + rex.getMessage());
        }

    }


    /**
     * Cancel Multiple Trouble Tickets given their keys
     *
     * @param keys Trouble Ticket Keys
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.EJBException
     */
    public TroubleTicketKeyResult[]
            tryCancelTroubleTicketsByKeys(TroubleTicketKey[] keys,
                                          int closeOutStatus,
                                          String closeOutNarr)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {

        log("JVTSession.tryCancelTroubleTickets (keys version)");

        //build the "CloseOutStatus" and "CloseOutNarr" properties
        PropertyList propList = new PropertyList();
        Property prop = null;

        prop = new Property("CloseOutStatus");
        prop.setIntProperty(closeOutStatus);
        propList.addProperty(prop);
        prop = new Property("CloseOutNarr");
        prop.setStringProperty(closeOutNarr);
        propList.addProperty(prop);

        try {
            return processKeys(keys, null, OperationTypes.CANCEL, propList);
        }
                //map exceptions
        catch (javax.ejb.ObjectNotFoundException onfEx) {
            throw new javax.ejb.EJBException(onfEx.getMessage());
        } catch (java.rmi.RemoteException rex) {
            throw new EJBException("RemoteException" + rex.getMessage());
        }
    }

    /**
     * Cancel the Trouble Tickets using associative lookup on
     * a single template
     *
     * @param template Trouble Ticket for which associative lookup is
     * performed. A Trouble Ticket match the template
     * if it match the populated attributes of the template.
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.EJBException
     */

    public TroubleTicketKeyResultIterator
            tryCancelTroubleTicketsByTemplate(TroubleTicketValue template,
                                              int closeOutStatus,
                                              String closeOutNarr,
                                              boolean failuresOnly)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {

        TroubleTicketValue[] templates = new TroubleTicketValue[1];
        templates[0] = template;

        //build the "CloseOutStatus" and "CloseOutNarr" properties
        PropertyList propList = new PropertyList();
        Property prop = null;

        prop = new Property("CloseOutStatus");
        prop.setIntProperty(closeOutStatus);
        propList.addProperty(prop);
        prop = new Property("CloseOutNarr");
        prop.setStringProperty(closeOutNarr);
        propList.addProperty(prop);

        try {
            return processTemplates(templates, null, failuresOnly, OperationTypes.CLOSE, propList);
        }
                //map exceptions
        catch (javax.ejb.ObjectNotFoundException onfEx) {
            throw new javax.ejb.EJBException(onfEx.getMessage());
        } catch (java.rmi.RemoteException rex) {
            throw new EJBException("RemoteException" + rex.getMessage());
        }
    }

    /**
     * Cancel the Trouble Tickets using associative lookup on
     * multiple templates
     *
     * @param templates Trouble Tickets for which associative lookup is
     * performed. A Trouble Ticket match the templates
     * if it match at leat one of the templates.
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.EJBException
     */
    public TroubleTicketKeyResultIterator
            tryCancelTroubleTicketsByTemplates(TroubleTicketValue[] templates,
                                               int closeOutStatus,
                                               String closeOutNarr,
                                               boolean failuresOnly)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.EJBException {
        //build the "CloseOutStatus" and "CloseOutNarr" properties
        PropertyList propList = new PropertyList();
        Property prop = null;

        prop = new Property("CloseOutStatus");
        prop.setIntProperty(closeOutStatus);
        propList.addProperty(prop);
        prop = new Property("CloseOutNarr");
        prop.setStringProperty(closeOutNarr);
        propList.addProperty(prop);

        try {
            return processTemplates(templates, null, failuresOnly, OperationTypes.CANCEL, propList);
        }
                //map exceptions
        catch (javax.ejb.ObjectNotFoundException onfEx) {
            throw new javax.ejb.EJBException(onfEx.getMessage());
        } catch (java.rmi.RemoteException rex) {
            throw new EJBException("RemoteException" + rex.getMessage());
        }

    }





    //----------------------------------------------------------------------------
    //
    // Atomic Bulk Update/Create Operations
    //
    // NOT IMPLEMENTED IN THIS RELEASE!
    //
    //----------------------------------------------------------------------------

    //Atomic Creation of Multiple Trouble Tickets each with a different value
    public TroubleTicketKey[]
            createTroubleTicketsByValues(TroubleTicketValue[] values)
            throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalArgumentException,
            javax.ejb.CreateException,
            javax.ejb.EJBException {

        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null) {
            throw new javax.ejb.CreateException("Lookup of TT Entity Bean Home failed");
        }

        throw new javax.oss.UnsupportedOperationException();

        //TroubleTicketKey[] ttKeys = null;
        //return ttKeys;
    }

    /**
     * Atomic Update Multiple Trouble Tickets each with a different values
     *
     * @param values TT Values
     * @exception javax.oss.UnsupportedOperationException
     * @exception javax.oss.SetException
     * @exception javax.ejb.DuplicateKeyException
     * @exception javax.ejb.FinderException
     * @exception javax.ejb.EJBException
     */

    public void setTroubleTicketsByValues(TroubleTicketValue[] values,
                                          boolean resyncRequired)
            throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalArgumentException,
            javax.oss.SetException,
            javax.ejb.DuplicateKeyException,
            javax.ejb.FinderException,
            javax.ejb.EJBException,
            javax.oss.ResyncRequiredException {

        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null) {
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");
        }


        throw new javax.ejb.EJBException("Not implemented");

    }

    /**
     * Atomic Update Multiple Trouble Tickets given their Keys
     * to the same value
     *
     * @param keys TT Keys
     * @param value TT Value
     * @exception javax.oss.UnsupportedOperationException
     * @exception javax.oss.SetException
     * @exception javax.ejb.FinderException
     * @exception javax.ejb.EJBException
     */
    public void setTroubleTicketsByKeys(TroubleTicketKey[] keys, TroubleTicketValue value)
            throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalArgumentException,
            javax.oss.SetException,
            javax.ejb.FinderException,
            javax.ejb.EJBException {
        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null) {
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");
        }

        throw new javax.oss.UnsupportedOperationException();

    }

    /**
     * Atomic Update Multiple Trouble Tickets using associative lookup
     *
     * @param template Trouble Ticket Template
     * @param value Value to which the Trouble Tickets matching
     * the template will be updated to.
     * @exception javax.oss.UnsupportedOperationException
     * @exception javax.oss.SetException
     * @exception javax.ejb.ObjectNotFoundException
     * @exception javax.ejb.EJBException
     */
    public void
            setTroubleTicketsByTemplate(TroubleTicketValue template, TroubleTicketValue value)
            throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalArgumentException,
            javax.oss.SetException,
            javax.ejb.FinderException,
            javax.ejb.EJBException {

        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null) {
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");
        }

        throw new javax.oss.UnsupportedOperationException();

    }

    /**
     * Atomic Update Multiple Trouble Tickets matching at least one of the template
     * to the same value
     *
     * @param templates TT Values
     * @param value TT Value
     * @exception javax.oss.UnsupportedOperationException
     * @exception javax.oss.SetException
     * @exception javax.ejb.FinderException
     * @exception javax.ejb.DuplicateKeyException
     * @exception javax.ejb.EJBException
     */

    public void
            setTroubleTicketsByTemplates(TroubleTicketValue[] templates, TroubleTicketValue value)
            throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalArgumentException,
            javax.oss.SetException,
            javax.ejb.FinderException,
            javax.ejb.DuplicateKeyException,
            javax.ejb.EJBException {

        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null) {
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");
        }

        throw new javax.oss.UnsupportedOperationException();

    }

    /**
     * Atomic Escalate Multiple Trouble Tickets given their keys.
     *
     * @param keys TT Keys
     * @exception javax.oss.UnsupportedOperationException
     * @exception javax.ejb.FinderException
     * @exception javax.oss.trouble.EscalateException
     * @exception javax.ejb.EJBException
     */
    public void escalateTroubleTicketsByKeys(TroubleTicketKey[] keys,
                                             EscalationList esc)
            throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalArgumentException,
            javax.oss.trouble.EscalateException,
            javax.ejb.FinderException,
            javax.ejb.EJBException {
        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null) {
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");
        }

        throw new javax.oss.UnsupportedOperationException();

    }

    /**
     * Atomic Escalate every Trouble Ticket matching the provided template
     *
     * @param template TT Value
     * @exception javax.oss.UnsupportedOperationException
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.oss.trouble.EscalateException
     * @exception javax.ejb.FinderException
     * @exception javax.ejb.EJBException
     */
    public void escalateTroubleTicketsByTemplate(TroubleTicketValue template,
                                                 EscalationList esc)
            throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalArgumentException,
            javax.oss.trouble.EscalateException,
            javax.ejb.FinderException,
            javax.ejb.EJBException {

        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null)
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");


        throw new javax.oss.UnsupportedOperationException();

    }

    /**
     * Atomic Escalate every Trouble Ticket matching at least one of the template
     *
     * @param templates TT Templates
     * @exception javax.oss.UnsupportedOperationException
     * @exception javax.oss.trouble.EscalateException
     * @exception javax.ejb.FinderException
     * @exception javax.ejb.EJBException
     */
    public void escalateTroubleTicketsByTemplates(TroubleTicketValue[] templates,
                                                  EscalationList esc)
            throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalArgumentException,
            javax.oss.trouble.EscalateException,
            javax.ejb.FinderException,
            javax.ejb.DuplicateKeyException,
            javax.ejb.EJBException {
        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null) {
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");
        }

        throw new javax.oss.UnsupportedOperationException();

    }

    /**
     * Atomic Close Multiple Trouble Tickets given their keys
     *
     * @param keys Trouble Ticket Keys
     * @exception javax.oss.UnsupportedOperationException
     * @exception javax.ejb.FinderException
     * @exception javax.oss.trouble.CloseException
     * @exception javax.ejb.EJBException
     */
    public void closeTroubleTicketsByKeys(TroubleTicketKey[] keys,
                                          int closeOutStatus,
                                          String closeOutNarr)
            throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalArgumentException,
            javax.oss.trouble.CloseException,
            javax.ejb.FinderException,
            javax.ejb.EJBException {
        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null) {
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");
        }

        throw new javax.oss.UnsupportedOperationException();

    }

    /**
     * Atomic Close the Trouble Tickets using associative lookup on
     * a single template
     *
     * @param template Trouble Ticket for which associative lookup is
     * performed. A Trouble Ticket match the template
     * if it match the populated attributes of the template.
     * @exception javax.oss.UnsupportedOperationException
     * @exception javax.oss.trouble.CloseException
     * @exception javax.ejb.ObjectNotFoundException
     * @exception javax.ejb.EJBException
     */

    public void closeTroubleTicketsByTemplate(TroubleTicketValue template,
                                              int closeOutStatus,
                                              String closeOutNarr)
            throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalArgumentException,
            javax.oss.trouble.CloseException,
            javax.ejb.FinderException,
            javax.ejb.EJBException {

        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null) {
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");
        }

        throw new javax.oss.UnsupportedOperationException();

    }

    /**
     * Atomic Close the Trouble Tickets using associative lookup on
     * multiple templates
     *
     * @param templates Trouble Tickets for which associative lookup is
     * performed. A Trouble Ticket match the templates
     * if it match at leat one of the templates.
     * @exception javax.oss.UnsupportedOperationException
     * @exception javax.oss.trouble.CloseException
     * @exception javax.ejb.FinderException
     * @exception javax.ejb.EJBException
     */
    public void closeTroubleTicketsByTemplates(TroubleTicketValue[] templates,
                                               int closeOutStatus,
                                               String closeOutNarr)
            throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalArgumentException,
            javax.oss.trouble.CloseException,
            javax.ejb.FinderException,
            javax.ejb.DuplicateKeyException,
            javax.ejb.EJBException {
        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null) {
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");
        }

        throw new javax.oss.UnsupportedOperationException();

    }

    /**
     * Atomic Cancel Multiple Trouble Tickets given their keys
     *
     * @param keys Trouble Ticket Keys
     * @exception javax.oss.UnsupportedOperationException
     * @exception javax.ejb.FinderException
     * @exception javax.oss.trouble.CancelException
     * @exception javax.ejb.EJBException
     */
    public void cancelTroubleTicketsByKeys(TroubleTicketKey[] keys,
                                           int closeOutStatus,
                                           String closeOutNarr)
            throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalArgumentException,
            javax.oss.trouble.CancelException,
            javax.ejb.FinderException,
            javax.ejb.EJBException {
        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null) {
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");
        }

        throw new javax.oss.UnsupportedOperationException();

    }

    /**
     * Atomic Cancel the Trouble Tickets using associative lookup on
     * a single template
     *
     * @param template Trouble Ticket for which associative lookup is
     * performed. A Trouble Ticket match the template
     * if it match the populated attributes of the template.
     * @exception javax.oss.UnsupportedOperationException
     * @exception javax.oss.trouble.CancelException
     * @exception javax.ejb.ObjectNotFoundException
     * @exception javax.ejb.EJBException
     */

    public void cancelTroubleTicketsByTemplate(TroubleTicketValue template,
                                               int closeOutStatus,
                                               String closeOutNarr)
            throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalArgumentException,
            javax.oss.trouble.CancelException,
            javax.ejb.FinderException,
            javax.ejb.EJBException {

        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null) {
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");
        }

        throw new javax.oss.UnsupportedOperationException();

    }

    /**
     * Atomic Cancel the Trouble Tickets using associative lookup on
     * multiple templates
     *
     * @param templates Trouble Tickets for which associative lookup is
     * performed. A Trouble Ticket match the templates
     * if it match at leat one of the templates.
     * @exception javax.oss.UnsupportedOperationException
     * @exception javax.oss.trouble.CancelException
     * @exception javax.ejb.FinderException
     * @exception javax.ejb.EJBException
     */
    public void cancelTroubleTicketsByTemplates(TroubleTicketValue[] templates,
                                                int closeOutStatus,
                                                String closeOutNarr)
            throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalArgumentException,
            javax.oss.trouble.CancelException,
            javax.ejb.FinderException,
            javax.ejb.DuplicateKeyException,
            javax.ejb.EJBException {

        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null) {
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");
        }

        throw new javax.oss.UnsupportedOperationException();

    }


    //--------------------------------------------------------------------
    //
    // Generic implementations for the 4 types of Best Effort bulk calls:
    //
    // - Multiple Array
    // - Multiple Keys, single array
    // - Template (both single and multiple templates handled via the
    //   multiple template method
    //
    // The parameters are validated, the Entity Bean Home is looked up,
    // and the call is delegated to the Entity Bean Home business method.
    //
    //--------------------------------------------------------------------

    //Multiple Array - updates
    private TroubleTicketKeyResult[]
            processValues(TroubleTicketValue[] values,
                          int opType,
                          PropertyList props)
            throws javax.ejb.ObjectNotFoundException,
            javax.oss.IllegalArgumentException,
            java.rmi.RemoteException {


        String opStr = getStringOp(opType);

        //validate the ttValues
        if (values == null)
            throw new javax.oss.IllegalArgumentException("Null TroubleTicketValue[] in " + opStr + " operation");
        if (values.length == 0)
            throw new javax.oss.IllegalArgumentException("Zero length TroubleTicketValue[] in " + opStr + " operation");

        //int len = values.length;
        //TroubleTicketKeyResult[] ttKeyResults = null;

        //since we are a stateless session, look up the entity home again.
        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null)
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");

        //delegate the call to the Entity Home
        try {
            return ttEntityHome.processValues(values, opType, props);
        } catch (java.rmi.RemoteException rex) {
            log("JVTTTSession.processValues:  Remote Exception on TTHome processValues");
            throw rex;
        }

    }


    //Multiple Keys,  (Single Value)
    private TroubleTicketKeyResult[]
            processKeys(TroubleTicketKey[] ttKeys,
                        TroubleTicketValue ttValue,
                        int opType,
                        PropertyList props)
            throws javax.ejb.ObjectNotFoundException,
            javax.oss.IllegalArgumentException,
            java.rmi.RemoteException {

        //validate stuff
        String opStr = getStringOp(opType);
        if (ttKeys == null)
            throw new javax.oss.IllegalArgumentException("Null TroubleTicketKey[] in " + opStr + " operation");
        if (ttKeys.length == 0)
            throw new javax.oss.IllegalArgumentException("Zero length TroubleTicketKey[] in " + opStr + " operation");


        //ttValue is only used in a SET request.
        if ((opType == OperationTypes.SET) && (ttValue == null))
            throw new javax.oss.IllegalArgumentException("Null TroubleTicketValue in " + opStr + " operation");

        // since we are a stateless session, look up the entity home again.
        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null)
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");

        // delegate the call to the Entity Home
        try {
            return ttEntityHome.processKeys(ttKeys, ttValue, opType, props);
        } catch (java.rmi.RemoteException rex) {
            log("JVTTTSession.processKeys:  Remote Exception on TTHome processKeys");
            throw rex;
        }

    }


    //Multiple Templates (single template methods also use this method)
    public TroubleTicketKeyResultIterator
            processTemplates(TroubleTicketValue[] templates,
                             TroubleTicketValue value,
                             boolean failuresOnly,
                             int opType,
                             PropertyList props)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.ObjectNotFoundException,
            java.rmi.RemoteException {


        //validate stuff -
        //For now, assume that null or no templates are not permitted

        String opStr = getStringOp(opType);
        if (templates == null)
            throw new javax.oss.IllegalArgumentException("Null TroubleTicketValue[] templates in " + opStr + " operation");
        if (templates.length == 0)
            throw new javax.oss.IllegalArgumentException("Zero length TroubleTicketValue[] templates in " + opStr + " operation");

        //value object only used in SET request
        if ((opType == OperationTypes.SET) && (value == null))
            throw new javax.oss.IllegalArgumentException("Null TroubleTicketValue in " + opStr + " operation");

        // since we are a stateless session, look up the entity home again.
        ttEntityHome = Finder.getInstance().lookupEntityHome();
        if (ttEntityHome == null)
            throw new javax.ejb.ObjectNotFoundException("Lookup of TT Entity Bean Home failed");

        // delegate the call to the Entity Home
        try {
            return ttEntityHome.processTemplates(templates, value, failuresOnly, opType, props);
        } catch (java.rmi.RemoteException rex) {
            log("JVTTTSession.processTemplates:  Remote Exception on TTHome processTemplates");
            throw rex;
        }

    }

    public String getStringOp(int opType) {
        if (opType == OperationTypes.CREATE) return new String("CREATE");
        if (opType == OperationTypes.GET) return new String("GET");
        if (opType == OperationTypes.SET) return new String("SET");
        if (opType == OperationTypes.CANCEL) return new String("CANCEL");
        if (opType == OperationTypes.CLOSE) return new String("CLOSE");
        return new String("UNKNOWN");
    }


    // You might also consider using WebLogic's log service
    private void log(String s) {
        Logger.log(s);

    }


}

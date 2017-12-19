/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
 */
package ossj.common.ex;

import ossj.common.ApplicationContextImpl;
import ossj.common.ManagedEntityValueIteratorImpl;
import ossj.common.UpdateProcedureResponseImpl;
import ossj.common.UpdateProcedureValueImpl;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.SessionContext;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.oss.ApplicationContext;
import javax.oss.ManagedEntityKey;
import javax.oss.ManagedEntityValue;
import javax.oss.ManagedEntityValueIterator;
import javax.oss.NamedQueryResponse;
import javax.oss.NamedQueryValue;
import javax.oss.OssIllegalAttributeValueException;
import javax.oss.UpdateProcedureResponse;
import javax.oss.UpdateProcedureValue;




/**
 * @stereotype SessionBean
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public class JVTActivationBean implements javax.ejb.SessionBean {
    private static final String[] MANAGEDENTITY_TYPES = {
        ManagedEntityEx1ValueImpl.class.getName(), ManagedEntityEx2ValueImpl.class.getName(),
    };
    private static final String[] MY_SUPPORTED_OPERATIONS = {
        JVTActivationOption.REMOVE_MANAGEDENTITY
    };
    private static final String[] MY_QUERY_TYPES = {
        QueryAllManagedEntitiesValue.class.getName(), QueryFromCreationDateImpl.class.getName()
    };
    private static final String[] MY_EVENT_TYPES = { ManagedEntityCreateEvent.class.getName() };
    private static final String[] MY_UPV_TYPES = { UpdateProcedureValueImpl.class.getName() };
    private static final Map MANAGED_EVENT_DESCRIPTOR_MAP;
    
    //*************
    static {
        MANAGED_EVENT_DESCRIPTOR_MAP = new java.util.HashMap();
        
        String[] propnames = {
            javax.oss.EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
            javax.oss.EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
        };
        String[] proptypes = {
            javax.oss.EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_TYPE,
            javax.oss.EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_TYPE,
        };
        
        MANAGED_EVENT_DESCRIPTOR_MAP.put(ManagedEntityCreateEvent.class.getName(),
                new ManagedEntityPropertyDescriptorImpl(ManagedEntityCreateEvent.class.getName(),
                propnames, proptypes,
                new ManagedEntityCreateEvent("dummy applicationDN", new ManagedEntityEx1ValueImpl())));
    }
    
    //***************************
    
    /*
    static {
     
         MANAGED_EVENT_DESCRIPTOR_MAP = new java.util.HashMap();
       MANAGED_EVENT_DESCRIPTOR_MAP.put(
       //  ManagedEntityCreateEvent.class.getName(),
        //        new ManagedEntityPropertyDescriptorImpl()
        // ) ;
     }
     */
    private String myApplicationDN;
    private JmsSender myJmsSender;
    private SessionContext mySessionContext;
    private Context myNamingContext;
    private ManagedEntityManager manager;
    private ApplicationContext myApplicationContext;
    private int count = 0;
    
    /**
     * Creates a new JVTActivationBean object.
     */
    public JVTActivationBean() {
    }
    
    // ---------------------------------------------------------------------------------------------------
    
    /**
     * to {@link javax.oss.JVTSession#makeManagedEntityValue}.
     *
     * <p><b>Postcondition:</b>Returns a new ManagedEntityValue  for usage in the client.
     * <p>
     * This method does not create a (back-end) Manged Entity object,
     * just a ManagedEntity value object.
     * It is the factory method for the value object that this session bean
     * can handle
     * Create a Value Type object for a specific Managed Entity type.
     * Not to be confused with the creation of a Managed Entity.
     * The Session Bean is used as a factory for the creation of
     * Value types.
     *
     * @param valuetype fully qualified name of the leaf managed entity value interface
     * @param valueType
     * @return the implementation class of a managed entity of a specific type
     * @exception javax.oss.OssIllegalArgumentException
     * @exception javax.ejb.CreateException
     */
    public ManagedEntityValue makeManagedEntityValue(String valuetype)
    throws javax.oss.OssIllegalArgumentException {
        if (!Arrays.asList(MANAGEDENTITY_TYPES).contains(valuetype)) {
            javax.oss.OssIllegalArgumentException e = new javax.oss.OssIllegalArgumentException(
                    "ManagedEntity type " + valuetype + " is not supported");
            throw e;
        }
        
        if (valuetype.equals(ManagedEntityEx1ValueImpl.class.getName())) {
            ManagedEntityEx1ValueImpl managedEntityValue = new ManagedEntityEx1ValueImpl();
            managedEntityValue.setManagedEntityKeyInstance(new ManagedEntityEx1KeyImpl(
                    getApplicationContext(), getApplicationDN(), valuetype));
            
            //managedEntityValue.setLastModifiedOnServer(new java.util.Date());
            return (ManagedEntityValue) managedEntityValue;
        } else if (valuetype.equals(ManagedEntityEx2ValueImpl.class.getName())) {
            ManagedEntityEx2ValueImpl managedEntityValue = new ManagedEntityEx2ValueImpl();
            managedEntityValue.setManagedEntityKeyInstance(new ManagedEntityEx2KeyImpl(
                    getApplicationContext(), getApplicationDN(), valuetype));
            
            //managedEntityValue.setLastModifiedOnServer(new java.util.Date());
            return (ManagedEntityValue) managedEntityValue;
        }
        
        return null;
    }
    
    /**
     * Get the Managed Entity types supported by a JVT Session Bean
     *
     * @return String array which contains the fully qualified names of the leaf
     * node interfaces representing the supported managed entity types.
     * Note that it is not the implementation class name that is returned.
     */
    public String[] getManagedEntityTypes() {
        return MANAGEDENTITY_TYPES;
    }
    
    /**
     * Gives information which methods will not throw UnsupportedOperationException.
     *
     * <p><b>Postcondition:</b>
     * <ul>
     * <li>Every returned string must be one mentioned in JVTActivationOption</li>
     * <li>If no option is supported, then an empty array is returned:<br>
     * <code>result != null</code>
     * </li>
     * </ul>
     *
     * @see JVTActivationOption
     */
    public String[] getSupportedOptionalOperations() {
        return MY_SUPPORTED_OPERATIONS;
    }
    
    /**
     * Returns the fully qualified names of all implemented named query types.
     *
     * @see #makeQueryValue
     */
    public String[] getNamedQueryTypes() {
        return MY_QUERY_TYPES;
    }
    
    /**
     * Returns the fully qualified names of all implemented update procedures types.
     */
    public String[] getUpdateProcedureTypes() {
        return MY_UPV_TYPES;
    }
    
    /**
     * Returns a value object that can be used to query for ManagedEntity.
     *
     * By using the set methods of the returned value object, the parameter values for query
     * can be given and afterwards queryManagedEntities can be called.
     *
     * <p><b>Postcondition:</b>
     * <ul>
     * <li><code>result instanceof Class.forName(queryName)</code>.
     *        The queryName is one of the base types of the result.
     * </li>
     * </ul>
     * @param queryName identifies the type of query.
     * @exception
     *  javax.oss.OssIllegalArgumentException if precondition violated:
     *  <ul>
     *  <li>queryName returned by getQueryTypes()
     *  </ul>
     **/
    public javax.oss.NamedQueryValue makeNamedQueryValue(String queryName)
    throws javax.oss.OssIllegalArgumentException {
        if (!java.util.Arrays.asList(MY_QUERY_TYPES).contains(queryName)) {
            javax.oss.OssIllegalArgumentException e = new javax.oss.OssIllegalArgumentException(
                    "query \"" + queryName + "\" not available");
            throw e;
        }
        
        NamedQueryValue aQueryValue = null;
        
        if (queryName.equals(QueryAllManagedEntitiesValue.class.getName())) {
            aQueryValue = new QueryAllManagedEntitiesValue();
        } else if (queryName.equals(QueryFromCreationDateImpl.class.getName())) {
            aQueryValue = new QueryFromCreationDateImpl();
        }
        
        return aQueryValue;
    }
    
    /**
     * return an instance of a procedure valau of the given type
     */
    public UpdateProcedureValue makeUpdateProcedureValue(String type)
    throws javax.oss.OssIllegalArgumentException {
        if (type == null){
            throw new javax.oss.OssIllegalArgumentException("null is not a valid UpdateProcedureValue type " + type);
        }
        if (type.equals(UpdateProcedureValueImpl.class.getName())) {
            return new UpdateProcedureValueImpl();
        } else {
            throw new javax.oss.OssIllegalArgumentException("Unsupported type " + type);
        }
    }
    
    /**
     * update Multiple Managed Entities using a update procedure Value
     *
     * @param query A UpdateProcedureValue object representing the Query.
     * @return A UpdateProcedureResponse always COMPLETE
     * @exception javax.oss.IllegalArgumentException
     */
    public UpdateProcedureResponse update(UpdateProcedureValue value)
    throws javax.oss.OssIllegalArgumentException {
        if (value == null) {
            throw new javax.oss.OssIllegalArgumentException("null is not a valid update procedure value");
        }
        return new UpdateProcedureResponseImpl(UpdateProcedureResponse.COMPLETE);
    }
    
    /**
     * Query Multiple Managed Entities using a NamedQueryValue
     *
     * @param query A NamedQueryValue object representing the Query.
     * @return A ManagedEnityValueIterator used to extract the results of the Query.
     * @exception javax.oss.OssIllegalArgumentException
     */
    public NamedQueryResponse query(NamedQueryValue queryVal)
    throws javax.oss.OssIllegalArgumentException {
        if (queryVal == null) {
            throw new javax.oss.OssIllegalArgumentException("null is not a valid query value");
        }
        
        if (!java.util.Arrays.asList(MY_QUERY_TYPES).contains(queryVal.getClass().getName())) {
            throw new javax.oss.OssIllegalArgumentException("query ["
                    + queryVal.getClass().getName() + "] not available");
        }
        
        ManagedEntityValueIterator anManagedValueIterator = new ManagedEntityValueIteratorImpl();
        
        // implemtation yet to be completed
        EJBHome myHome = mySessionContext.getEJBHome();
        
        // use home find methods...there is no entity bean to search.....
        return anManagedValueIterator;
    }
    
    /**
     * Returns the fully qualified names of event types that are published by this implementation.
     * <p>
     * Can be used to dynmically download the classes of the the event types
     * to the client and thus automatically unmarshall JMS messages.
     * <p>
     *
     *
     */
    public String[] getEventTypes() {
        return MY_EVENT_TYPES;
    }
    
    /**
     * Get the Event Descriptor associated with an event type name.
     *
     * <p><b>Postcondition:</b>
     * <ul>
     * <li>The returned descriptor must be one for the requested type:<br>
     *                  <code>result.getEventType().equals(eventType)</code>.
     * </li>
     * </ul>
     * @param eventType identifies the type of the event.
     * @exception
     *  javax.oss.OssIllegalArgumentException if precondition violated:
     *  <ul>
     *  <li>eventType returned by getEventTypes()
     *  </ul>
     */
    
    // initially one event supported but other evnts can be added later
    public javax.oss.EventPropertyDescriptor getEventDescriptor(String eventType)
    throws javax.oss.OssIllegalArgumentException {
        for (int i = 0; i < MY_EVENT_TYPES.length; i++) {
            if (MY_EVENT_TYPES[i].equals(eventType)) {
                javax.oss.EventPropertyDescriptor returnEventDescriptor = (javax.oss.EventPropertyDescriptor) MANAGED_EVENT_DESCRIPTOR_MAP
                        .get(eventType);
                
                return returnEventDescriptor;
            }
        }
        
        javax.oss.OssIllegalArgumentException e = new javax.oss.OssIllegalArgumentException(
                "eventType must be  value as returned by getEventTypes()");
        throw e;
    }
    
    private String getApplicationDN() {
        if (myApplicationDN == null) {
            myApplicationDN = (String) lookup("java:comp/env/ApplicationDN");
        }
        
        // System.out.println("hi in application DN" + myApplicationDN);
        return myApplicationDN;
    }
    
    /**
     * Creates a new ManagedEntity object and returns the key for the new object.
     * <p>
     * The populated attributes of <code>value</code> are used to initialize the ManagedEnity.
     * All other attributes are initialzed by the implementation to their default
     * values (which may be a null value for non-primitive types).
     * The values of the attributes <code>key()</code> and <code>state</code> are ignored,
     * even if they are populated. Instead, a new, unique
     * key is created by this method and returned as result. The state is
     * initialized to NOT_STARTED.
     * <p>
     * Implementation controlled attributes are ignored if they are populated.
     * <p><b>Postcondition:</b>
     * <ul>
     * <li>The ManagedEntity type is as requested:<br>
     *         <code>result.getType().equals(type)</code>, where type is the one used in makeManagedEntityType(type).
     * </li>
     * <li><code>result.getPrimaryKey().toString()()</code> is different from all other
     *         primary keys of this implementation bean instance.
     * </li>
     * <li>All other attributes of result have values, such that the client
     *         can re-located this implementation bean instance.
     * </li>
     * </ul>
     *
     * <p><b>Message Postcondition:</b>
     * When this method is called, a JMS message <code>msg</code> will be published:
     * <ul>
     * <li><code>msg.getStringProperty(OSS_EVENT_TYPE_PROP_NAME).equals(ManagedEntityCreateEvent.class.getName())</code>
     * <li><code>msg.getObject() instanceof ManagedEntityCreateEvent</code>
     * <li><code>msg.getObject().getManagedEntityValue()</code> must have the
     *                  attributes KEY and STATE populated.
     *                  An implementation may populate further attributes,
     *                  especially the ones that were populated in <code>value</code>
     * </ul>
     * @exception javax.oss.OssIllegalArgumentException if precondition violated: <br>
     *  <code>value</code> must be a return value of {@link #makeManagedEntityValue makeManagedEntityValue}
     *  from this implementation bean instance.
     * @exception javax.oss.OssIllegalAttributeValueException if precondition violated: <br>
     *  all populated attribute values must be in the value range
     *  that is allowed be the implementation.
     * @exception javax.ejb.CreateException in case of an implementation internal problem.
     */
    public ManagedEntityExKey createManagedEntityExByValue(ManagedEntityValue managedEntityValue)
    throws javax.oss.OssIllegalArgumentException, javax.oss.OssIllegalAttributeValueException,
            javax.ejb.CreateException {
        //System.err.println("hi entered the firts function");
        ManagedEntityExKey returnManagedEntityKey = null;
        
        if (managedEntityValue == null) {
            CreateException e = new CreateException(" ManagedEntityValue-Object is null");
            throw e;
        }
        
        if (managedEntityValue instanceof ManagedEntityEx1ValueImpl) {
            ManagedEntityEx1ValueImpl anManagedEntityValue = (ManagedEntityEx1ValueImpl) managedEntityValue;
            
            try {
                ManagedEntityEx1KeyImpl mgkey = new ManagedEntityEx1KeyImpl(getApplicationContext(),
                        getApplicationDN(), ManagedEntityEx1ValueImpl.class.getName());
                
                mgkey.setPrimaryKey("ManagedEntityEx1Key" + count);
                count++;
                anManagedEntityValue.setManagedEntityEx1Key(mgkey);
                
                ManagedEntityEx1 aManagedEntity = manager.createManagedEntityEx1(anManagedEntityValue);
                returnManagedEntityKey = (ManagedEntityExKey) anManagedEntityValue
                        .getManagedEntityEx1Key();
                getJmsSender().publish(new ManagedEntityCreateEvent(getApplicationDN(),
                        anManagedEntityValue), ManagedEntityCreateEvent.class.getName(),
                        aManagedEntity);
            } catch (Exception e) {
                e.printStackTrace();
                CreateException e1 = new CreateException(
                        "could not create primary key by banu1. \n" + e.toString());
                
                throw e1;
            }
        } else if (managedEntityValue instanceof ManagedEntityEx2ValueImpl) {
            ManagedEntityEx2ValueImpl anManagedEntityValue = (ManagedEntityEx2ValueImpl) managedEntityValue;
            
            try {
                ManagedEntityEx2KeyImpl mg2key = new ManagedEntityEx2KeyImpl(getApplicationContext(),
                        getApplicationDN(), ManagedEntityEx2ValueImpl.class.getName());
                
                mg2key.setPrimaryKey("ManagedEntityEx2Key" + count);
                count++;
                
                anManagedEntityValue.setManagedEntityEx2Key(mg2key);
                
                ManagedEntityEx2 aManagedEntity = manager.createManagedEntityEx2(anManagedEntityValue);
                returnManagedEntityKey = (ManagedEntityExKey) anManagedEntityValue
                        .getManagedEntityEx2Key();
                getJmsSender().publish(new ManagedEntityCreateEvent(getApplicationDN(),
                        anManagedEntityValue), ManagedEntityCreateEvent.class.getName(),
                        aManagedEntity);
            } catch (Exception e) {
                CreateException e1 = new CreateException("could not create primary key. \n"
                        + e.toString());
                throw e1;
            }
        } else {
            CreateException e1 = new CreateException("ManagedEntityValue-Object is Wrong type ");
            throw e1;
        }
        
        //   ManagedEntityKey returnManagedEntityKey1 = anManagedEntityValue.getManagedEntityKey();
        return returnManagedEntityKey;
    }
    
    /**
     * Creates multiple ManagedEntity objects and returns for each a new key.
     *
     *
     */
    public ManagedEntityExKeyResult[] tryCreateManagedEntityExsByValues(ManagedEntityValue[] values)
    throws javax.oss.OssIllegalArgumentException, javax.ejb.CreateException {
        ManagedEntityExKeyResult[] result = new ManagedEntityExKeyResultImpl[values.length];
        
        for (int i = values.length-1; i >= 0 ; i--) {
            try {
                ManagedEntityExKey k = (ManagedEntityExKey) createManagedEntityExByValue(values[i]);
                result[i] = new ManagedEntityExKeyResultImpl(k, true, null);
            } catch (Exception e) {
                result[i] = new ManagedEntityExKeyResultImpl(null, false, e);
            }
        }
        
        return result;
    }
    
    /**
     * Terminates the ManagedEnity . <p>
     *
     * The ManagedEnity is removed from the implementation . <p>
     * See also {@link JVTActivationOption}.
     *
     *
     * @exception
     *  javax.oss.OssIllegalArgumentException if precondition violated:
     *        The <code>key</code> has been returned by {@link JVTActivationSession#createManagedEnityByValue createManagedEnityByValue}
     *           of this JVTActivationSession before.
     * @exception
     *  javax.oss.OssUnsupportedOperationException if precondition violated:
     *         <code>JVTActivationOption.REMOVE_MANAGEDENITY returned by getSupportedOptionalOperations()</code>
     * @exception javax.ejb.RemoveException in case of an implementation internal problem.
     * @exception javax.ejb.ObjectNotFoundException if precondition violated:
     *         The <code>managedEntity.getManagedEntityKey()</code> must refer to an existing object.
     */
    public void removeManagedEntityByKey(ManagedEntityExKey managedEntityKey)
    throws javax.oss.OssIllegalArgumentException, javax.ejb.ObjectNotFoundException,
            javax.oss.OssUnsupportedOperationException, javax.ejb.RemoveException {
        checkManagedEntityKey(managedEntityKey);
        
        if (managedEntityKey instanceof ManagedEntityEx1KeyImpl) {
            manager.removeManagedEntityEx1(managedEntityKey);
        } else if (managedEntityKey instanceof ManagedEntityEx2KeyImpl) {
            manager.removeManagedEntityEx2(managedEntityKey);
        }
    }
    
    /**
     * Removes multiple ManagedEntity objects.
     *
     */
    public ManagedEntityExKeyResult[] tryRemoveManagedEntitiesByKeys(
            ManagedEntityExKey[] managedEntityKeys)
            throws javax.oss.OssIllegalArgumentException, javax.oss.OssUnsupportedOperationException,
            javax.ejb.RemoveException {
        if (managedEntityKeys == null) {
            javax.oss.OssIllegalArgumentException e = new javax.oss.OssIllegalArgumentException(
                    "OrderKey[] argument may not be null");
            throw e;
        }
        
        Vector result = new Vector();
        
        for (int i = 0; i < managedEntityKeys.length; i++) {
            try {
                removeManagedEntityByKey(managedEntityKeys[i]);
            } catch (javax.ejb.ObjectNotFoundException e) {
                result.add(new ManagedEntityExKeyResultImpl(managedEntityKeys[i], false, e));
            } catch (javax.oss.OssIllegalArgumentException e) {
                result.add(new ManagedEntityExKeyResultImpl(managedEntityKeys[i], false, e));
            } catch (javax.oss.OssUnsupportedOperationException e) {
                result.add(new ManagedEntityExKeyResultImpl(managedEntityKeys[i], false, e));
            }
        }
        
        return (ManagedEntityExKeyResult[]) result.toArray(new ManagedEntityExKeyResultImpl[0]);
    }
    
    /**
     * Changes the attribute values of an ManagedEntity.
     * <p>
     * Only the attributes that are populated are changed.
     * The attribute key identifies the ManagedEntity to change.
     * Implementation controlled attributes, inluding the attribute state are ignored,
     * even if populated.
     * <p>
     * Otherwise this method is atomic in the sense that all attributes are updated:
     * If some attribute values are not correct, the managedEntity object is not changed at all.
     *
     *
     * @param managedEntityValue ManagedEntityValue.
     * @exception javax.oss.OssIllegalArgumentException (Programmin Errorg)
     * @exception javax.ejb.ObjectNotFoundException if precondition violated:
     *         The <code>managedEntity.getManagedEntityKey()</code> must refer to an existing object.
     * @exception javax.oss.OssIllegalAttributeValueException if precondition violated:
     * all populated attribute values must be in the value range
     * that is allowed be the implementation.
     * @exception javax.oss.OssSetException in case of an implementation internal problem.
     *
     */
    public void setManagedEntityExByValue(ManagedEntityValue managedEntityValue)
    throws javax.oss.OssIllegalArgumentException, javax.ejb.ObjectNotFoundException,
            javax.ejb.CreateException {
        if (managedEntityValue == null) {
            CreateException e = new CreateException("Wrong ManagedEntityValue-Object is null");
            throw e;
        }
        
        if (managedEntityValue instanceof ManagedEntityEx1ValueImpl) {
        } else if (managedEntityValue instanceof ManagedEntityEx2ValueImpl) {
        } else {
            CreateException e = new CreateException("Wrong ManagedEntityValue-Object set");
            throw e;
        }
        
        if (!managedEntityValue.isPopulated(ManagedEntityValue.KEY)) {
            javax.oss.OssIllegalArgumentException e = new javax.oss.OssIllegalArgumentException(
                    "No ManagedEnityKey in ManagedEntityValue");
            throw e;
        }
        
        if (manager == null) {
            javax.ejb.ObjectNotFoundException e = new javax.ejb.ObjectNotFoundException(
                    "Manager could not be found");
            throw e;
        }
        
        if (managedEntityValue instanceof ManagedEntityEx1ValueImpl) {
            manager.setManagedEntityEx1((ManagedEntityEx1ValueImpl) managedEntityValue);
        } else if (managedEntityValue instanceof ManagedEntityEx2ValueImpl) {
            manager.setManagedEntityEx2((ManagedEntityEx2ValueImpl) managedEntityValue);
        }
    }
    
    /**
     * Changes the attributes of multiple ManagedEntities.
     *  @exception javax.oss.OssIllegalArgumentException (Programming Error) if precondition violated:
     *
     */
    public ManagedEntityExKeyResult[] trySetManagedEntityExsByValues(ManagedEntityValue[] values)
    throws javax.oss.OssIllegalArgumentException, javax.oss.OssSetException {
        if (values == null) {
            javax.oss.OssIllegalArgumentException e = new javax.oss.OssIllegalArgumentException(
                    "OrderValue[] argument may not be null");
            throw e;
        }
        
        Vector result = new Vector();
        
        for (int i = 0; i < values.length; i++) {
            try {
                setManagedEntityExByValue(values[i]);
            } catch (ObjectNotFoundException e) {
                result.add(new ManagedEntityExKeyResultImpl(
                        (ManagedEntityExKey) values[i].getManagedEntityKey(), false, e));
            } catch (javax.oss.OssIllegalArgumentException e) {
                if (!values[i].isPopulated(ManagedEntityValue.KEY)) {
                    result.add(new ManagedEntityExKeyResultImpl(null, false, e));
                } else {
                    result.add(new ManagedEntityExKeyResultImpl(
                            (ManagedEntityExKey) values[i].getManagedEntityKey(), false, e));
                }
            } catch (Exception e) {
                result.add(new ManagedEntityExKeyResultImpl(
                        (ManagedEntityExKey) values[i].getManagedEntityKey(), false, e));
            }
        }
        
        return (ManagedEntityExKeyResult[]) result.toArray(new ManagedEntityExKeyResultImpl[0]);
    }
    
    /**
     * Returns values for the ManagedEntity identified by the (unique) key.
     *
     *
     * @param key identifies the ManagedEntity.
     * @param attributeNames only extract part of the attributes.
     *                  In this case this is null, all possible properties are extracted.
     * @exception javax.ejb.ObjectNotFoundException if precondition violated:
     *
     * @exception javax.oss.OssIllegalArgumentException (Programming Error)
     */
    public ManagedEntityValue getManagedEntityByKey(ManagedEntityExKey managedEntityKey)
    throws javax.oss.OssIllegalArgumentException, javax.ejb.ObjectNotFoundException {
        ManagedEntityValue result = null;
        checkManagedEntityKey(managedEntityKey);
        
        if (managedEntityKey instanceof ManagedEntityEx1KeyImpl) {
            result = (ManagedEntityValue) manager.getManagedEntityEx1Value(managedEntityKey);
        } else if (managedEntityKey instanceof ManagedEntityEx2KeyImpl) {
            result = (ManagedEntityValue) manager.getManagedEntityEx2Value(managedEntityKey);
        }
        
        return result;
    }
    
    /**
     * Returns values for a list of ManagedEntities identified by the (unique) keys.
     *
     *
     * @exception javax.oss.OssIllegalArgumentException (Programming Error)
     */
    public ManagedEntityValue[] getManagedEntitiesByKeys(ManagedEntityExKey[] managedEntityKeys)
    throws javax.oss.OssIllegalArgumentException {
        if (managedEntityKeys == null || managedEntityKeys.length==0) {
            javax.oss.OssIllegalArgumentException e = new javax.oss.OssIllegalArgumentException(
                    "Key array is of zero length or null");
            throw e;
        }
        
        ManagedEntityValue[] result = new ManagedEntityValue[managedEntityKeys.length];
        
        for (int i = 0; i < managedEntityKeys.length; i++) {
            try {
                result[i] = getManagedEntityByKey(managedEntityKeys[i]);
            } catch (javax.ejb.ObjectNotFoundException e) {
                result[i] = null;
            }
        }
        
        return result;
    }
    
    private Context getNamingContext() {
        if (myNamingContext == null) {
            try {
                myNamingContext = new javax.naming.InitialContext();
            } catch (javax.naming.NamingException ne) {
            }
        }
        
        return myNamingContext;
    }
    
    private Object lookup(String name) {
        Object obj = null;
        
        try {
            obj = getNamingContext().lookup(name);
        } catch (javax.naming.NamingException ne) {
        }
        
        return obj;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param sCtx DOCUMENT ME!
     *
     * @throws EJBException DOCUMENT ME!
     */
    public void setSessionContext(final SessionContext sCtx)
    throws EJBException {
        mySessionContext = sCtx;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @throws EJBException DOCUMENT ME!
     */
    public void ejbRemove() throws EJBException {
    }
    
    /**
     * DOCUMENT ME!
     *
     * @throws EJBException DOCUMENT ME!
     */
    public void ejbActivate() throws EJBException {
    }
    
    /**
     * DOCUMENT ME!
     *
     * @throws EJBException DOCUMENT ME!
     */
    public void ejbPassivate() throws EJBException {
    }
    
    /**
     * DOCUMENT ME!
     *
     * @throws CreateException DOCUMENT ME!
     * @throws EJBException DOCUMENT ME!
     */
    public void ejbCreate() throws CreateException, EJBException {
        manager = new ManagedEntityManager();
    }
    
    private JmsSender getJmsSender() {
        if (myJmsSender == null) {
            try {
                JmsSenderHome aHome = (JmsSenderHome) lookup("java:comp/env/ejb/JmsSender");
                myJmsSender = aHome.create();
            } catch (java.rmi.RemoteException re) {
                re.printStackTrace();
            } catch (CreateException ce) {
                ce.printStackTrace();
            }
        }
        
        return myJmsSender;
    }
    
    private ApplicationContext getApplicationContext() {
        if (myApplicationContext == null) {
            myApplicationContext = getmyApplicationContext(getNamingContext());
        }
        
        return myApplicationContext;
    }
    
    private void checkManagedEntityKey(ManagedEntityKey anManagedEntityKey)
    throws javax.oss.OssIllegalArgumentException {
        if (anManagedEntityKey == null) {
            javax.oss.OssIllegalArgumentException e = new javax.oss.OssIllegalArgumentException(
                    "ManagedEntityKey is null");
            throw e;
        }
        
        if (!(anManagedEntityKey instanceof ManagedEntityExKey)) {
            javax.oss.OssIllegalArgumentException e = new javax.oss.OssIllegalArgumentException(
                    "ManagedEntityKey not of the correct type");
            throw e;
        }
        
        if (!getApplicationDN().equals(anManagedEntityKey.getApplicationDN())) {
            javax.oss.OssIllegalArgumentException e = new javax.oss.OssIllegalArgumentException(
                    "ManagedEntityKey has different ApplicationDN than this application");
            throw e;
        }
    }
    
    /**
     * Creates an ApplicationContext based on the current server configuration
     */
    public ApplicationContextImpl getmyApplicationContext(Context aNamingContext) {
        try {
            Hashtable map = aNamingContext.getEnvironment();
            String factory = (String) map.get(Context.INITIAL_CONTEXT_FACTORY);
            String url = (String) map.get(Context.PROVIDER_URL);
            
            /* VP non portable code
             * The DD shal contain the factoryClass and the Url
             */
            /*
            if (factory == null)
            {
                factory = System.getProperty(Context.INITIAL_CONTEXT_FACTORY);
            }
            if (url == null)
            {
                url = System.getProperty(Context.PROVIDER_URL);
            }
            if (url == null) //still null
            {
                // ok, take some drastic measures here
                // are we within weblogic?
                if (factory.equals("weblogic.jndi.WLInitialContextFactory"))
                {
                    try
                    {
                        String protocol = null;
                        String address = null;
                        String port = null;
                        weblogic.management.MBeanHome anMBeanHome = (weblogic.management.MBeanHome)aNamingContext.lookup("weblogic.management.home.localhome");
                        //System.out.println("Domain: "+anMBeanHome.getDomainName());
                        java.util.Set allMBeans = anMBeanHome.getMBeansByClass(weblogic.management.configuration.ServerMBean.class);
                        for (Iterator itr = allMBeans.iterator(); itr.hasNext(); )
                        {
                            weblogic.management.WebLogicMBean mbean = (weblogic.management.WebLogicMBean)itr.next();
                            weblogic.management.WebLogicObjectName objectName = mbean.getObjectName();
                            if (mbean.getType().equals("Server"))
                            {
                                weblogic.management.configuration.ServerMBean aServerBean =
                                (weblogic.management.configuration.ServerMBean)mbean;
                                //System.out.println("Protocol: "+aServerBean.getDefaultProtocol());
                                //System.out.println("Address: "+aServerBean.getListenAddress());
                                //System.out.println("DNS: "+aServerBean.getExternalDNSName());
                                //System.out.println("Port: "+aServerBean.getListenPort());
                                //
                                protocol = aServerBean.getDefaultProtocol();
                                address = aServerBean.getListenAddress();
                                port = String.valueOf(aServerBean.getListenPort());
                                weblogic.management.configuration.ClusterMBean aClusterBean = aServerBean.getCluster();
                                if (aClusterBean != null)
                                {
                                    System.out.println("Cluster.address: "+aClusterBean.getClusterAddress());
                                    address = aClusterBean.getClusterAddress();
                                }
                                weblogic.management.configuration.MachineMBean aMachineBean = aServerBean.getMachine();
                                if (aMachineBean != null)
                                {
                                    String[] addresses = aMachineBean.getAddresses();
                                    //for (int i=0 ; addresses!=null && i<addresses.length ; i++) {
                                    //    System.out.println("Machine.address."+i+": "+addresses[i]);
                                    //}
                                    if (addresses!=null && addresses.length>0)
                                    {
                                        address = addresses[0];
                                    }
                                }
                            }
                        }
                        if (address==null)
                        {
                            // address is STILL null?!
                            address = java.net.InetAddress.getLocalHost().getHostAddress();
                        }
                        url=protocol+"://"+address+":"+port;
                    }
                    catch (NamingException ne)
                    {
                    }
                    catch (java.net.UnknownHostException uhe)
                    {
                    }
                }
            }
            //System.out.println("hi the factory application context is " + factory);
            //System.out.println("hi the url application context is " + url);
             */
            return new ApplicationContextImpl(factory, new java.util.HashMap(), url);
        } catch (NamingException ne) {
            System.out.println(ne.toString());
            
            return null;
        }
    }
}

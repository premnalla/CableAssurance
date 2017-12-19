

package com.nokia.oss.ossj.common.ri;

import javax.oss.*;
import com.sun.oss.common.ri.XmlSerializerImpl ;

/**
 * A basic implementation of javax.oss.ManagedEntityKey. This class provides a hashCode(), equals(...) and clone() methode
 * - the latter still has to be overridden by a subclass in order to copy the primary key to the new object. since it is 
 * declared as java.lang.Object, no clone() can be created of it.
 *
 *   @author BanuPrasad Dhanakoti Nokia Networks
 *   @version 1.1
 * January 2002
 */
public abstract class ManagedEntityKeyImpl extends java.lang.Object implements javax.oss.ManagedEntityKey {
    
    /** Holds value of property applicationContext. */
    private ApplicationContext applicationContext;
    
    /** Holds value of property applicationTypeDN. */
    private String applicationDN;
    
    /** Holds value of property primaryKey. */
    public Object primaryKey;
    
    /** Holds value of property type. */
    public String type;
    
    /** Creates new ManagedEntityKeyImpl */
    public ManagedEntityKeyImpl() {
    }

    /** Creates new ManagedEntityKeyImpl */
    public ManagedEntityKeyImpl( ApplicationContext anApplicationContext, String appDN, String MevType, Object primaryKey) {
        applicationContext = anApplicationContext;
        applicationDN = appDN;
        type = MevType;
        this.primaryKey = primaryKey;
    }
    
    /**
     * ApplicationContext used to locate the Application
     * Components in charge of this managed entity
     *
     * @return ApplicationContext
     */
    public javax.oss.ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    
    public void setApplicationContext(javax.oss.ApplicationContext context) throws java.lang.IllegalArgumentException{
       if(context == null)
         throw new java.lang.IllegalArgumentException("Application Context can't be null");
       
     
        applicationContext = context;
    }
    
    /**
     * The DN of the ApplicationType JNDI Naming Context
     * The format of this DN is: <p>
     *
     * <i>deployment dependent top context</i>
     * /System=<i>vendor</i>-<i>id</i>
     * /ApplicationType=<i>api-name</i>
     * <p>
     * under which the components of a building block
     * are deployed.
     *
     * @return DN of ApplicationTypeDN
     */
    public String getApplicationDN() {
        return applicationDN;
    }
    
    public void setApplicationDN(String newADN)throws java.lang.IllegalArgumentException {
      if(newADN == null)
         throw new java.lang.IllegalArgumentException("ApplicationDN can't be null");
       
        
        applicationDN = newADN;
    }
    
    
    /** The unique identifier for the Managed Entity. <p>
     * In case the implementation uses Entity Beans,
     * an implementation may map the primary key to the EJB primary key.
     * @return the primary key
     */
    public Object getPrimaryKey() {
        return primaryKey;
    }
    
    /** Gets the type of <CODE>ManagedEntityValue</CODE> within <CODE>ManagedEntityKey</CODE>.
     * <p>
     * This is part of <CODE>ManagedEntityKey</CODE>, because <CODE>primaryKey</CODE>s can be
     * duplicate between <CODE>ManagedEntityValue</CODE>, implemented by the same
     * class, but from different type (not java type).
     * @return String entity's type name
     */
    public String getType() {
        return type;
    }
    

    /**
     * Sets a new value for key.
     * @param primaryKey new value for primary key
     * @throws java.lang.IllegalArgumentException to be defined in subinterfaces / classes.
 */
    public void setPrimaryKey(Object primaryKey) throws java.lang.IllegalArgumentException {
        this.primaryKey = primaryKey;
    }
    
    /** Sets a new value for type.<p>
     * @param type the new value for type
     * @throws java.lang.IllegalArgumentException to be defined in subinterfaces / classes.
     */
    public void setType(String type) throws java.lang.IllegalArgumentException {
       if(!(type instanceof String))
        throw new java.lang.IllegalArgumentException ();
        this.type = type;
    }

    
    public boolean equals( Object other )
    {
        if ( ! (other instanceof ManagedEntityKey )) {
            return false;
        }
        ManagedEntityKey meKey = ( ManagedEntityKey )other;
        
        // compare first which is most likely to fail!!!
        return (
         ( primaryKey==null ? meKey.getPrimaryKey()==null: primaryKey.equals(meKey.getPrimaryKey()) ) &&
         ( applicationContext==null ? meKey.getApplicationContext()==null: applicationContext.equals(meKey.getApplicationContext()) ) &&
         ( applicationDN==null ? meKey.getApplicationDN()==null: applicationDN.equals(meKey.getApplicationDN()) ) &&
         ( type==null ? meKey.getType()==null: type.equals(meKey.getType()) ) );
    }

    /**
     *It is not required that if two objects are unequal according to the
     * <code>equals(java.lang.Object)</code> method, then calling the
     * <code>hashCode</code> method on each of the two objects must produce
     * distinct integer results. However, the programmer should be
     * aware that producing distinct integer results for unequal objects may
     * improve the performance of hashtables.
     */
    public int hashCode()
    {
        return primaryKey.hashCode();
    }

    public String toString() {
        return (applicationContext==null?"[unknown context]":applicationContext.getURL())+"|"+applicationDN +"|"+
        primaryKey + " - " + type;
    }

    // abstract methods:

    /**
     * Deep copy of this key. Subclasses of this type still have to copy the primary key to the new object!
     * 
     * @return deep copy of this key
     */
    public Object clone() {
        try {
            // first call clone in Object
            Object myClone = super.clone();

            // then update all fields which are only a shallow copy
            ManagedEntityKeyImpl anMevKeyClone = (ManagedEntityKeyImpl)myClone;
            
            // TODO remove cast to ApplicationContextImpl
            anMevKeyClone.applicationContext = (ApplicationContext)(((ApplicationContextImpl)applicationContext).clone());
           
            return anMevKeyClone;
            
        } catch (Exception cnse) {
            return null;
        }
    }

    /**
     * Factory method for ApplicationContext
     * 
     * 
     * @return ApplicationContext
     */
    
    public javax.oss.ApplicationContext makeApplicationContext() {
		return ((ApplicationContext) new ApplicationContextImpl(null,null,null) ) ;
	}

    

    /**
     * Manufacture a PrimaryKey
     * 
     * @return PrimaryKey implementation class
     */
    public abstract Object makePrimaryKey();
    
    /**
     * Manufacture a Serializer for the object type inheriting
     * the interface.
     *
     * @param serializerType The class name of the serializer interface that must
     * be created. For example <CODE>XmlSerializer.getClass().getName()</CODE>
     * @return A serializer matching the serializer type .
     * @exception java.lang.IllegalArgumentException If an Illegal Argument is provided or if no Serializer can be created matching
     * the provided Serializer Type.
     */
    public Serializer makeSerializer(String serializerType) throws java.lang.IllegalArgumentException {
        if( serializerType.equals(XmlSerializer.class.getName()))
        return new XmlSerializerImpl(this.getClass().getName() );
        else throw new java.lang.IllegalArgumentException("Unrecognized Serializer Type");
    }
    
    /**
     * Returns all the serializer types than can be created by this factory.
     * This may return an empty array, in case no serializer is
     * implemented.
     *
     * @return Array of supported serializer types.
     */
    public String[] getSupportedSerializerTypes() {
           String[] serializerTypes = new String[1];
        serializerTypes[0] = new String( XmlSerializer.class.getName());
        return serializerTypes;
    }
   
}


/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common;

import javax.oss.*;


/**
 * A basic implementation of javax.oss.ManagedEntityKey.
 * This class provides a hashCode(), equals(...) and clone() methods
 * - the latter still has to be overridden by a subclass in order to copy the primary key
 * to the new object. since it is
 * declared as java.lang.Object, no clone() can be created of it.
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 *
 */
public abstract class ManagedEntityKeyImpl extends java.lang.Object
    implements javax.oss.ManagedEntityKey {
    /** Holds value of property applicationContext. */
    private ApplicationContext applicationContext;

    /** Holds value of property applicationTypeDN. */
    private String applicationDN;

    /** Holds value of property primaryKey. */
    public Object primaryKey;

    /** Holds value of property type. */
    public String type;

    /**
     * Creates new ManagedEntityKeyImpl with all attributes empty
     */
    public ManagedEntityKeyImpl() {
        /* VP change to build a valid default implementation
                applicationContext = null;
                applicationDN = null;
                type = null;
                this.primaryKey = null;
        */
        applicationContext = new ApplicationContextImpl();
        applicationDN = "";
        type = "";
        this.primaryKey = new String(); //VP use a default empty string (to avoid serialization issue with new Object())
    }

    /**
     * Creates new ManagedEntityKeyImpl
     *
     * @param anApplicationContext used to locate the Application Components in charge of this managed entity
     * @param appDN The DN of the ApplicationType JNDI Naming Context
     * @param MevType the type of ManagedEntityValue within ManagedEntityKey
     * @param primaryKey the unique identifier for the Managed Entity
     */
    /*
    public ManagedEntityKeyImpl(ApplicationContext anApplicationContext, String appDN,
        String MevType, Object primaryKey) {
        applicationContext = anApplicationContext;
        applicationDN = appDN;
        type = MevType;
        this.primaryKey = primaryKey;

     }
*/
    /**
     * ApplicationContext used to locate the Application
     * Components in charge of this managed entity
     *
     * @return ApplicationContext
     */
    public javax.oss.ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /** set the new applicationContext.
     * null value is supported
     *
     * @param context the new ApplicationContext
     * @throws java.lang.IllegalArgumentException if the given ApplicationContext is null
     */
    public void setApplicationContext(javax.oss.ApplicationContext context)
        throws java.lang.IllegalArgumentException {
//Spec chapter 3.1.2 all filed in key can be null
    	/*
    	if (context == null) {
            throw new java.lang.IllegalArgumentException("null is not a valid ApplicationContext");
        }
        */

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

    /** 
     * Set applicationDN
     * null value is supported
     *
     * @param newADN the new applicationDN
     * @throws java.lang.IllegalArgumentException if the newADN is null
     */
    public void setApplicationDN(String newADN) throws java.lang.IllegalArgumentException {
/*
    	if (newADN == null) {
            throw new java.lang.IllegalArgumentException("null is not a valid ApplicationDN");
        }
*/
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

    /** 
     * Sets a new value for key.
     * null value is supported.
     * 
     *  @param primaryKey new value for primary key
     * @throws java.lang.IllegalArgumentException if the given primaryKey is null.
     */

    public void setPrimaryKey(Object primaryKey) throws java.lang.IllegalArgumentException {
    	if (primaryKey == null) {
            throw new java.lang.IllegalArgumentException("null is not a valid Primary Key");
        }
    	
        this.primaryKey = primaryKey;
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

    /** Sets a new value for type.<p>
     * @param type the new value for type
     * @throws java.lang.IllegalArgumentException if the type argument is null.
     */
    public void setType(String type) throws java.lang.IllegalArgumentException {
        if (type == null) {
            throw new java.lang.IllegalArgumentException(
                "null is not a value type for ManagedEntityKey");
        }

        this.type = type;
    }

    /**
     * Compares this ManagedEntityKey to the specified object.
     * The result is true if and only if the argument is not null
     * and is an ManagedEntityKey that represents the same context as this object.
     *
     * @param other the object to compare this ManagedEntityKey against
     * @return true if the ManagedEntityKey are equals; false otherwise
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (!(other instanceof ManagedEntityKey)) {
            return false;
        }

        ManagedEntityKey meKey = (ManagedEntityKey) other;

        // compare first which is most likely to fail!!!
        return (((primaryKey == null) ? (meKey.getPrimaryKey() == null)
                                      : primaryKey.equals(meKey.getPrimaryKey()))
        && ((applicationContext == null) ? (meKey.getApplicationContext() == null)
                                         : applicationContext.equals(meKey.getApplicationContext()))
        && ((applicationDN == null) ? (meKey.getApplicationDN() == null)
                                    : applicationDN.equals(meKey.getApplicationDN()))
        && ((type == null) ? (meKey.getType() == null) : type.equals(meKey.getType())));
    }

    /** It is not required that if two objects are unequal according to the
     * <code>equals(java.lang.Object)</code> method, then calling the
     * <code>hashCode</code> method on each of the two objects must produce
     * distinct integer results. However, the programmer should be
     * aware that producing distinct integer results for unequal objects may
     * improve the performance of hashtables.
     */
    public int hashCode() {
    	if (primaryKey != null){
    		return primaryKey.hashCode();
    	} else {
    		return hashCode();
    	}
    }

    /** Returns a string representation of this key instance arguments.
     *
     */
    public String toString() {
        return ((applicationContext == null) ? "[null context]" : applicationContext.getURL())
        + "|" + applicationDN + "|" + primaryKey + " - " + type;
    }

    // abstract methods:

    /**
     * Deep copy of this key. Subclasses of this type still have to copy the
     * primary key to the new object!
     *
     * @return deep copy of this key
     */
    public Object clone() {
        try {
            // first call clone in Object
            Object myClone = super.clone();

            // then update all fields which are only a shallow copy
            ManagedEntityKeyImpl anMevKeyClone = (ManagedEntityKeyImpl) myClone;

            if (this.getApplicationContext() != null) {
                anMevKeyClone.setApplicationContext((ApplicationContext) ((ApplicationContextImpl) this
                    .getApplicationContext()).clone());
            } else {
            	anMevKeyClone.setApplicationContext(null);
            }

            if (this.getApplicationDN() != null) {
                anMevKeyClone.setApplicationDN(this.getApplicationDN());
            } else {
            	anMevKeyClone.setApplicationDN(null);
            }

            if (this.getPrimaryKey() != null) {
                anMevKeyClone.setPrimaryKey(this.getPrimaryKey());
            }

            return anMevKeyClone;
        } catch (CloneNotSupportedException cnse) {
            //cnse.printStackTrace();
            throw new java.lang.RuntimeException("ManagedEntityKeyImpl: Error invoking clone() CloneNotSupportedException, " +cnse.getMessage());
            //return null;
        }
    }

    /**
     * Factory method for ApplicationContext
     *
     * @return ApplicationContext
     */
    public javax.oss.ApplicationContext makeApplicationContext() {
        return ((ApplicationContext) new ApplicationContextImpl());
    }

    /**
     * Manufacture a PrimaryKey
     *
     * @return PrimaryKey implementation class
     */
    public abstract Object makePrimaryKey();

}

package ossj.qos.fmri;

/**
 * AlarmKeyImpl
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */

import javax.oss.fm.monitor.AlarmKey;
import javax.oss.fm.monitor.AlarmValue;
import javax.oss.ApplicationContext;
import javax.oss.Serializer;
import javax.oss.XmlSerializer;
import ossj.qos.util.Util;

import ossj.qos.xmlri.fmxmlri.FmXmlSerializerImpl;

public class AlarmKeyImpl implements AlarmKey {

    private String alarmPrimaryKey = null;
    private String appDomainName = null;
    private String entityType = AlarmValue.VALUE_TYPE;
    private ApplicationContext appContext = null;

    /**
     * AlarmKeyImpl - default constructor
     */
    public AlarmKeyImpl() {
    }

    /**
     * Returns alarm key.
     *
     * @return <code>String</code> - alarm key
     * @see #setAlarmPrimaryKey
     */
    public String getAlarmPrimaryKey() {
        return alarmPrimaryKey;
    }

    /**
     * Sets alarm key.
     *
     * @param key Alarm key
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getAlarmPrimaryKey
     */
    public void setAlarmPrimaryKey(String key) throws java.lang.IllegalArgumentException {
        if ( key == null ) {
            throw new IllegalArgumentException( "Null alarm primary key entered." );
        }
        alarmPrimaryKey = key;
        return;
    }

    /**
     * The Domain name identifies an application's administrative domain.
     * An administrative domain maintains a JNDI namespace.
     *
     * @return String domain name
     */
    public String getApplicationDN() {
        return appDomainName;
    }

    /**
     * Sets the Domain name that identifies an application's administrative domain.
     * An administrative domain maintains a JNDI namespace.
     *
     * @param domain A string name that represents the administrative domain in a
     * JNDI namespace.
     */

    public void setApplicationDN(String domain) throws java.lang.IllegalArgumentException {
        if ( domain == null ) {
            throw new IllegalArgumentException (" Null application domain name entered." );
        }
        appDomainName = domain;
        return;
    }

    /**
     *  Gets the entity's type name.
     * @return String entity's type name
     */
    public String getType() {
        return entityType;
    }

    /**
     * Sets the entity type name
     * @return String entity's type name
     */
    public void setType(String type) throws java.lang.IllegalArgumentException {
        if ( type == null || type.equals( AlarmValue.VALUE_TYPE ) == false ) {
            throw new IllegalArgumentException( "Invalid entity type entered." );
        }
        entityType = type;
        return;
    }

    /**
     * The unique identifier for the Managed Entity.
     * This key is mappable to the EJB primary key
     * when the ManagedEntity is represented by an
     * Entity Bean
     */
    public Object getPrimaryKey() {
        return alarmPrimaryKey;
    }

    /**
     * Sets the unique identifier for the Managed Entity.
     * This key is mappable to the EJB primary key
     * when the ManagedEntity is represented by an
     * Entity Bean
     * @param key A primary key that is mappable to the EJB primary key.
     */
    public void setPrimaryKey(Object key) throws java.lang.IllegalArgumentException {
        if ( key == null || key instanceof String == false ) {
            throw new IllegalArgumentException ( "Invalid Primary Key. Object needs to be an instance of type String." );
        }
        alarmPrimaryKey = (String) key;
        return;
    }

    /**
     * ApplicationContext used to locate the Application
     * Components in charge of this managed entity
     *
     * @return ApplicationContext
     */
    public javax.oss.ApplicationContext getApplicationContext() {
        return appContext;
    }

    /**
     * ApplicationContext used to locate the Application
     * Components in charge of this managed entity
     *
     * @return ApplicationContext
     */
    public void setApplicationContext( javax.oss.ApplicationContext ctx ) {
        appContext = ctx;
        return;
    }
    /**
     * Create an empty instance of application contex.
     * Hooman
     */

    public javax.oss.ApplicationContext makeApplicationContext() {
       return new ossj.qos.ApplicationContextImpl();
    }

    /**
     * Deep copy of this key
     *
     * @return deep copy of this key
     */
    public Object clone() {
        AlarmKeyImpl obj = null;
        try {
            obj = (AlarmKeyImpl) super.clone();
            obj.appContext = (ApplicationContext)((ossj.qos.ApplicationContextImpl) appContext).clone();
        }
        catch ( CloneNotSupportedException cex ) {
            //System.out.println(" Problem cloning AlarmKey.");
        }
        return obj;
    }

    /**
     * Returns all the serializer types than can be created by this factory
     */

    /*
    public String[] getSupportedSerializerTypes() {
        String[] serializerTypes = new String[1];
        serializerTypes[0] = new String( XmlSerializer.class.getName());
        return serializerTypes;
    }
    */
     // Took out the above and added this, 2002-03-14, Hooman Tahamtani
      public String[] getSupportedSerializerTypes() {
          String[] serializerTypes = new String[1];
          serializerTypes[0] = new String( FmXmlSerializerImpl.class.getName());
          return serializerTypes;
      }


    /**
     * Manufacture a Serializer for the object type inheriting
     * the interface.
     *
     * @param serializerType The class name of the serializer interface that must
     *  be created. For example "XmlSerializer.getClass().getName()"
     *
     *
     * @return A serializer matching the serializer type
     * which can be used to serialize and deserialize the object which
     * inherits this interface.
     * @exception java.lang.IllegalArgumentException If an Illegal Argument is provided.
     * or if no Serializer can be created matching the provided
     * Serializer Type.
     */
    public Serializer makeSerializer( String serializerType )
    throws java.lang.IllegalArgumentException {
        Serializer  serializer = null;
    /*
        if ( serializerType.equals( XmlSerializer.class.getName() ) ) {
            serializer = new AlarmKeyXmlSerializerImpl(AlarmKey.class.getName() );
        }
        else {
            throw new java.lang.IllegalArgumentException("Unrecognized Serializer Type");
        }
    */
     /*
        Commented out the above and added below to comply with the new implementation.
        February 14, 2002.
        Hooman Tahamtani, Ericsson Microwave AB
        Gothenburg, Sweden
     */
     try{
        serializer = new FmXmlSerializerImpl(serializerType);
     }catch(java.lang.IllegalArgumentException e){
        throw new java.lang.IllegalArgumentException(e.getMessage());
     }
        return serializer;
    }

    /**
     * Manufacture a PrimaryKey
     *
     * @return PrimaryKey implementation class
     */
    public Object makePrimaryKey() {
        return new String();
    }

    /**
     * Returns a boolean that indicates whether the contents of the
     * passed in AlarmKeyImpl instance is equal to
     * contents of this instance.
     *
     * @return boolean - indicating if the instances are equal.
     */
    public boolean equals( Object anObject ) {
		if (anObject == null || anObject.getClass() != this.getClass()) {
			return false;
		}

        AlarmKeyImpl objAlarmKey = (AlarmKeyImpl)anObject;
        return ( Util.isEqual( alarmPrimaryKey, objAlarmKey.alarmPrimaryKey ) &&
                 Util.isEqual( appDomainName, objAlarmKey.appDomainName ) &&
                 Util.isEqual( entityType, objAlarmKey.entityType ) &&
                 Util.isEqual( appContext, objAlarmKey.appContext ) );
    }

    public int hashCode() {
		return ((alarmPrimaryKey == null) ? -1 : alarmPrimaryKey.hashCode());
	}

    /**
     * Returns a string representation of the class.
     *
     * @return String - representation of the class.
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer(500);
        buffer.append(Util.rightJustify(30,"alarmKey = ") + alarmPrimaryKey + "\n");
        buffer.append(Util.rightJustify(30,"domainName = ") + appDomainName + "\n");
        buffer.append(Util.rightJustify(30,"entityType = ") + entityType + "\n");
        buffer.append(Util.rightJustify(30,"appContext = ") + Util.printObject( appContext ) );
        return buffer.toString();
    }
}

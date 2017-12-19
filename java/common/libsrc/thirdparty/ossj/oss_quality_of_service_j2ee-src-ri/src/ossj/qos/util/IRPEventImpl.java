/*
 * Copyright © 2001 Ericsson Radio Systems AB. All rights reserved.
 * Use is subject to license terms.
 */
package ossj.qos.util;

import java.util.Date;
import javax.oss.Serializer;
import javax.oss.util.IRPEvent;


import ossj.qos.AttributeAccessImpl;
import ossj.qos.util.Log;

/**
 * IRPEvent implementation.
 *
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi, Henrik Lindstrom
 */
public class IRPEventImpl extends AttributeAccessImpl implements IRPEvent {

  public IRPEventImpl() {
    map.put(this.APPLICATION_DN,null);
    map.put(this.EVENT_TIME,null);
    map.put(this.MANAGED_OBJECT_CLASS, null);
    map.put(this.MANAGED_OBJECT_INSTANCE, null);
    map.put(this.NOTIFICATION_ID, null);
  }

  public String getManagedObjectClass() {
    String managedObjectClass;
    if(this.isPopulated(this.MANAGED_OBJECT_CLASS) == false)
      managedObjectClass = null;
    else
      managedObjectClass = (String) this.getAttributeValue(this.MANAGED_OBJECT_CLASS);
    return managedObjectClass;
  }

  public String getManagedObjectInstance() {
    String managedObjectInstance;
    if(this.isPopulated(this.MANAGED_OBJECT_INSTANCE) == false)
      managedObjectInstance = null;
    else
      managedObjectInstance = (String) this.getAttributeValue(this.MANAGED_OBJECT_INSTANCE);
    return managedObjectInstance;
  }

  public String getNotificationId() {
    String notificationId;
    if(this.isPopulated(this.NOTIFICATION_ID) == false)
      notificationId = null;
    else
      notificationId = (String) this.getAttributeValue(this.NOTIFICATION_ID);
    return notificationId;
  }

  public void setManagedObjectClass(String moc) {
    this.setAttributeValue(this.MANAGED_OBJECT_CLASS, moc);
  }

  public void setManagedObjectInstance(String moi) {
    this.setAttributeValue(this.MANAGED_OBJECT_INSTANCE, moi);
  }

  public void setNotificationId(String id) throws java.lang.IllegalArgumentException {
    this.setAttributeValue(this.NOTIFICATION_ID, id);
  }

  public String[] getSupportedOptionalAttributeNames() {
    return new String[0];
  }

  protected boolean isValidAttribute( String attrName, Object attrValue ) {

    boolean isValid = false;

    if ( attrName != null && attrName.equals( IRPEvent.MANAGED_OBJECT_CLASS )
        && attrValue != null && attrValue instanceof String )
      isValid = true;

    if ( attrName != null && attrName.equals( IRPEvent.MANAGED_OBJECT_INSTANCE )
        && attrValue != null && attrValue instanceof String )
      isValid = true;

    if ( attrName != null && attrName.equals( IRPEvent.NOTIFICATION_ID )
        && attrValue != null && attrValue instanceof String )
      isValid = true;

    if ( attrName != null && attrName.equals( IRPEvent.APPLICATION_DN )
        && attrValue != null && attrValue instanceof String )
      isValid = true;

    if ( attrName != null && attrName.equals( IRPEvent.EVENT_TIME )
        && attrValue != null && attrValue instanceof Date )
      isValid = true;

    return isValid;
  }

  // --
  // -- Event interface implementation
  // --


  public String getApplicationDN() {
    String applicationDN;
    if(this.isPopulated(this.APPLICATION_DN) == false)
      applicationDN = null;
    else
      applicationDN = (String) this.getAttributeValue(this.APPLICATION_DN);
    return applicationDN;

  }

  public Date getEventTime() {
    Date eventTime;
    if(this.isPopulated(this.EVENT_TIME) == false)
      eventTime = null;
    else
      eventTime = (Date) this.getAttributeValue(this.EVENT_TIME);
    return eventTime;
  }

    public void setApplicationDN(String applicationDN) throws java.lang.IllegalArgumentException {
        this.setAttributeValue(this.APPLICATION_DN, applicationDN);
    }

    public void setEventTime(Date time) throws java.lang.IllegalArgumentException {
        this.setAttributeValue(this.EVENT_TIME, time);
    }

  // --
  // -- SerializerFactory interface methods
  // --

   public String[] getSupportedSerializerTypes() {
      Log.write(this,Log.LOG_ALL,"getSupportedSerializerTypes()","Not implemented");
      return new String[0];
    }

    public Serializer makeSerializer( String serializerType )
                          throws java.lang.IllegalArgumentException {
      Log.write(this,Log.LOG_ALL,"makeSerializer()","Not implemented");
        throw new java.lang.IllegalArgumentException( "NOT IMPLEMENTED/SUPPORTED" );

    }

}

package com.nokia.oss.ossj.common.ri.ex;

import javax.oss.*;
import com.nokia.oss.ossj.common.ri.*;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * EJB remote interface, the central interface of this package to create, manage and remove ManagedEntity.
 *
 *
 * @see JVTActivationHome
 */
public interface JVTActivationSession extends javax.oss.JVTSession {
    
    
  
   public ManagedEntityExKey createManagedEntityExByValue(ManagedEntityValue managedEntityValue) 
     throws   javax.oss.IllegalArgumentException,
            javax.oss.IllegalAttributeValueException,
            javax.ejb.CreateException,
            java.rmi.RemoteException;

  public ManagedEntityExKeyResult[] tryCreateManagedEntityExsByValues(ManagedEntityValue[] values)
    throws	 javax.oss.IllegalArgumentException,
    javax.ejb.CreateException,
    java.rmi.RemoteException;
     
  
   public void removeManagedEntityByKey(ManagedEntityExKey managedEntityKey)
    throws	javax.oss.IllegalArgumentException,
    javax.ejb.ObjectNotFoundException,
    javax.oss.UnsupportedOperationException,
    javax.ejb.RemoveException,
    java.rmi.RemoteException;
    
    public ManagedEntityExKeyResult[] tryRemoveManagedEntitysByKeys(ManagedEntityExKey[] managedEntityKeys)
    throws	javax.oss.IllegalArgumentException,
    javax.oss.UnsupportedOperationException,
    javax.ejb.RemoveException,
    java.rmi.RemoteException ;
  
   public void setManagedEntityExByValue(ManagedEntityValue managedEntityValue )
   throws javax.oss.IllegalArgumentException,
    javax.ejb.ObjectNotFoundException,
        javax.ejb.CreateException ,
      RemoteException  ;
      
   public ManagedEntityExKeyResult[] trySetManagedEntitysExByValues( ManagedEntityValue[] values )
    throws	 javax.oss.IllegalArgumentException,
    javax.oss.SetException,
    RemoteException ;
    
    public ManagedEntityValue getManagedEntityByKey(ManagedEntityExKey managedEntityKey)
    throws	 javax.oss.IllegalArgumentException,
    javax.ejb.ObjectNotFoundException,
    RemoteException;   
   
  public ManagedEntityValue[] getManagedEntitysByKeys(ManagedEntityExKey[] managedEntityKeys) throws 
  javax.oss.IllegalArgumentException, 
  RemoteException;
    
   
}

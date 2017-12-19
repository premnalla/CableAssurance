/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.ex;

import javax.oss.JVTSession;
import javax.oss.ManagedEntityValue;
import javax.oss.OssSetException;
import javax.oss.OssIllegalArgumentException;
import javax.oss.OssIllegalAttributeValueException;
import javax.oss.OssUnsupportedOperationException;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.ejb.CreateException;

/**
 * EJB remote interface, the central interface of this package to create, 
 * manage and remove ManagedEntity.
 *
 * @see JVTActivationHome
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public interface JVTActivationSession extends JVTSession {
  
	public ManagedEntityExKey createManagedEntityExByValue(ManagedEntityValue managedEntityValue) 
     	throws   OssIllegalArgumentException,
            OssIllegalAttributeValueException,
            CreateException,
            RemoteException;

	public ManagedEntityExKeyResult[] tryCreateManagedEntityExsByValues(ManagedEntityValue[] values)
    	throws	 OssIllegalArgumentException,
			CreateException,
			RemoteException;     
  
	public void removeManagedEntityByKey(ManagedEntityExKey managedEntityKey)
    	throws	OssIllegalArgumentException,
			ObjectNotFoundException,
			OssUnsupportedOperationException,
			RemoveException,
			RemoteException;
    
    public ManagedEntityExKeyResult[] tryRemoveManagedEntitiesByKeys(ManagedEntityExKey[] managedEntityKeys)
    	throws	OssIllegalArgumentException,
			OssUnsupportedOperationException,
			RemoveException,
			RemoteException ;
  
    public void setManagedEntityExByValue(ManagedEntityValue managedEntityValue )
		throws OssIllegalArgumentException,
			ObjectNotFoundException,
			CreateException ,
			RemoteException  ;
      
    public ManagedEntityExKeyResult[] trySetManagedEntityExsByValues( ManagedEntityValue[] values )
    	throws	 OssIllegalArgumentException,
			OssSetException,
			RemoteException ;
    
    public ManagedEntityValue getManagedEntityByKey(ManagedEntityExKey managedEntityKey)
    	throws	 OssIllegalArgumentException,
			ObjectNotFoundException,
			RemoteException;   
   
    public ManagedEntityValue[] getManagedEntitiesByKeys(ManagedEntityExKey[] managedEntityKeys) 
		throws  OssIllegalArgumentException, 
			RemoteException; 
}

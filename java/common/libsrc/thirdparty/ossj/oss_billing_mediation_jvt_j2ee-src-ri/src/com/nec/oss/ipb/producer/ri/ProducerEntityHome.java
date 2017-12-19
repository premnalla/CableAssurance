
/**
 * Copyright © 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */

package com.nec.oss.ipb.producer.ri;

/**
 * Standard imports
 */
import java.util.Collection;

import javax.ejb.FinderException;
import java.rmi.RemoteException;

/**
 * OSS/J Interface imports.
 */
import javax.oss.ipb.producer.ProducerKey;
import javax.oss.ipb.producer.ProducerPrimaryKey;
import javax.oss.ipb.producer.ProducerValue;


/**
 * RI imports.
 */
import com.nec.oss.ipb.producer.ri.ProducerPrimaryKeyImpl;

/**
 * A com.nec.oss.ipb.producer.ri.ProducerEntity
 * Remote Home interface definition.
 * <p>
 *  This interface is implemented by the EJB container.
 * The implemented object is called the home
 * object, and serves as a factory for EJB objects.
 * The create() method is in
 * this interface corresponds to the ejbCreate() method in
 * the corresponding bean class.
 * *
 * @see com.nec.oss.ipb.producer.ri.ProducerEntity
 * @see com.nec.oss.ipb.producer.ri.ProducerEntityBean
 *
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 */

public interface ProducerEntityHome
extends javax.ejb.EJBHome
{
    /**
     * Creates the Producer Entity Bean.
     * @param producerKey Key for the Producer to create
     * @producerValue ProducerValue containing the attributes to set 
     * @return ProducerEntitiy Created Producer
     * @exception javax.ejb.CreateException Thrown if an error occurs creating
     * the Producer
     * @exception RemoteException Thrown if a system error occurs
     */
    ProducerEntity create(
        ProducerKey producerKey,
        ProducerValue producerValue)
        throws javax.ejb.CreateException,
        RemoteException;

    
    /**
     * Finder metods. These are implemented by the container.
     * It is possible to customize the functionality of these methods
     * in the deployment descriptor through EJB-QL and container tools.
     */

    /**
     * Find a Producer using the primary key. The primary key is
     * <code>ProducerKey.getProducerPrimaryKey()</code>.
     *
     * @param primaryKey the primary key
     * @return matching Producer 
     * @exception FinderException Thrown if an error occurs finding the match
     * @exception RemoteException Thrown if a system error occurs
     */
    public ProducerEntity findByPrimaryKey( ProducerPrimaryKeyImpl primaryKey )
        throws FinderException,
            RemoteException;

    /**
     * Find all producers.
     * @return Collection containing all Producers
     * @exception FinderException Thrown if an error occurs finding the match
     * @exception RemoteException Thrown if a system error occurs
     */
    public Collection findAllProducers()
        throws FinderException, RemoteException;
}

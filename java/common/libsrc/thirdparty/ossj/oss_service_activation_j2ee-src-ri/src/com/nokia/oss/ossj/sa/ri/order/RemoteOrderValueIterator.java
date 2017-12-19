/*
 * Copyright (c) 2002
 * Cisco Systems, Inc., Ericsson Radio Systems AB.,
 * Motorola, Inc., NEC Corporation, Nokia Networks Oy,
 * Nortel Networks Limited, Sun Microsystems, Inc.,
 * Telcordia Technologies, Inc., Cygent, Inc.,
 * Agilent Technologies, Inc., BEA Systems, Inc.,
 * Digital Fairway Corporation, Orchestream Holdings plc.
 *
 * All rights reserved.  Use is subject to license terms.
 *
 * DOCUMENTATION IS PROVIDED "AS IS" AND ALL EXPRESS OR IMPLIED
 * CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE DISCLAIMED, EXCEPT
 * TO THE EXTENT THAT SUCH DISCLAIMERS ARE HELD TO BE LEGALLY
 * INVALID.
 */
package com.nokia.oss.ossj.sa.ri.order;

import java.rmi.RemoteException;
import javax.oss.ManagedEntityValue;
import javax.oss.order.OrderValue;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public interface RemoteOrderValueIterator extends javax.ejb.EJBObject {


    /**
     * Return the next n order values from the result of the query. <p>
     * An empty result indicates that there are no further values.<p>
     * An implementation may return less values than requested,
     * for example <code>getNext(100000)</code> may only return 256
     * values. Returning less values than requested does <b>not</b> mean
     * that there are no more values. <p>
     * If the last call returned an empty array (indicating that there are
     * no further values), all future calls will return an empty array as well.<p>
     * Please not that the implementation does not lock the orders if they are not
     * read by the client via an OrderValueIterator yet. That has the consequences.
     * Generally, the order entity on the server can already be altered of even
     * removed before the client had a chance reading it.
     * <OL>
     * <LI>In case an order is already removed, either by the implementation itself
     * or by another client, the client receives a javax.ejb.ObjectNotFoundException when
     * it tries to get the correspondant OrderValue object from the server</LI>
     * <li>In case an order was modified, it might not anymore match the search criteria, the
     * client specified initially.
     * </OL>
     * <p><b>Postcondition:</b>
     * <ul>
     * <li><code>result != null and result.length <= howMany </code>
     * </ul>
     * @param howMany maximum number of values returned.
     * @throws RemoteException
     * @exception javax.oss.IllegalArgumentException if violated:
     * howMany() > 0
     * @return the array of OrderValues
     */
      public OrderValue[] getNextOrder(int howMany)
      throws javax.oss.IllegalArgumentException, RemoteException;


    /**getNext is called to retrieve the next available values.
     * 
     * @param howMany Size of the result batches returned to the client.
     * @return an array of managed entities with a size of at most howMany. The
     * array is empty when no more results are available.
     * @exception java.rmi.RemoteException
     */
    public ManagedEntityValue[] getNext( int howMany )
    throws java.rmi.RemoteException;
}


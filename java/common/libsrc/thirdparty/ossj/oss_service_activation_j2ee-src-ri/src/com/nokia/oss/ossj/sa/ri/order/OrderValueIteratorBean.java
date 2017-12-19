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

import java.util.*;
import javax.oss.*;
import javax.oss.order.*;

import javax.naming.*;
import javax.ejb.*;
import java.rmi.*;

import com.nokia.oss.ossj.sa.ri.*;
/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public class OrderValueIteratorBean implements SessionBean
{
    
	//VP 
	private Context context;
    SessionContext myContext;
    
    List orderKeys;
    Iterator orderKeyIterator;
    String[] attributes;
    
    OrderLocalHome anOrderHome;
    
    public ManagedEntityValue[] getNext(int howMany) {
        if (anOrderHome==null) {
            try {
                //VP Context namingContext = new InitialContext();
                //VP anOrderHome = (OrderLocalHome)namingContext.lookup("java:comp/env/ejb/Order");
                anOrderHome = (OrderLocalHome)context.lookup("ejb/Order");
            } catch (NamingException ne) {
                throw new EJBException("Could not get a handle for Order remote home interface", ne);
            }
        }

        OrderValue[] orderValues = new OrderValue[howMany];
        OrderLocal anOrder;
        OrderKey anOrderKey;
        for (int i=0 ; i<howMany ; i++) {
            // check if there is still something in the iterator;
            if (!orderKeyIterator.hasNext()) {
                //if not, create a smaller array, copy the content and return it
                OrderValue[] smallerOrderValues = new OrderValue[i];
                System.arraycopy(orderValues, 0, smallerOrderValues, 0, i);
                return smallerOrderValues;
            }
            anOrderKey = (OrderKey)orderKeyIterator.next();
            try {
                anOrder = anOrderHome.findByPrimaryKey( new CMPManagedEntityKey(anOrderKey));
                orderValues[i] = anOrder.getAttributes(attributes);
            } catch (FinderException fe) {
                fe.printStackTrace();
            }
        }

        return orderValues;
    }

    /**
     * Return the next n order values from the result of the query. <p>
     * An empty result indicates that there are no further values.<p>
     * An implementation may return less values than requested,
     * for example <code>getNext(100000)</code> may only return 256
     * values. Returning less values than requested does <b>not</b> mean
     * that there are no more values. <p>
     * If the last call returned an empty array (indicating that there are
     * no further values), all future calls will return an empty array as well.<p>
     * Issue: Implementation may auto-remove instances?
     * <p><b>Postcondition:</b>
     * <ul>
     * <li><code>result != null or result.length <= howMany </code>
     * </ul>
     * @param howMany maximum number of values returned.
     * @exception
     *  javax.oss.IllegalArgumentException if violated:
     *  howMany() > 0 
     */
      public OrderValue[] getNextOrder(int howMany)
      throws javax.oss.IllegalArgumentException {
          return (OrderValue[])getNext(howMany);
      }
      
      public void ejbCreate(OrderKey[] orderKeys, String[] attributes) throws CreateException {
		//VP
		try {
			context = (Context) new InitialContext().lookup("java:comp/env");
		} catch (Exception e) {
			throw new CreateException("ejbCreate(): " + e.getMessage());
		} 
		//end VP
        this.orderKeys = Arrays.asList(orderKeys);
        orderKeyIterator = this.orderKeys.iterator();
        this.attributes = attributes;
    }

    /**
     * remove is called, when the client has finished iterating 
     * through  the collection to allow resources to be deallocated.
     * 
     * @exception java.rmi.RemoteException
     * @exception javax.ejb.RemoveException
     */
    public void remove() {
    }
    
    public void ejbActivate(){
        // Write your code here
    }

    public void ejbPassivate(){
        // Write your code here
    }

    public void ejbRemove(){
        // Write your code here
    }

    public void setSessionContext(SessionContext newContext){
        myContext = newContext;
    }
}

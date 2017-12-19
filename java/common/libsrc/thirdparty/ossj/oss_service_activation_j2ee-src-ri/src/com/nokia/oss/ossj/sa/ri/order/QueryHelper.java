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

import javax.oss.*;
import javax.oss.order.*;
import java.util.*;
import java.rmi.*;
import java.sql.*;
import javax.ejb.*;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public class QueryHelper extends Object {
    
    public class OrderComparator implements Comparator {
        public int compare(Object orderOne,Object orderTwo) {
                OrderLocal firstOrder = (OrderLocal)orderOne;
                OrderLocal secondOrder = (OrderLocal)orderTwo;
                // order with a "smaller" timestamp (i.e. the requestedCompletion of firstOrder was BEFORE the
                // requestedCompletion of the second) is to be considered "smaller" (i.e. is to be proceeded first)
                return firstOrder.getRequestedCompletionDate().compareTo(secondOrder.getRequestedCompletionDate());
        }
        
        public boolean equals(Object anotherComparator) {
            return (anotherComparator instanceof OrderComparator);
        }
    }
    
    public class PrioOrderComparator extends QueryHelper.OrderComparator {
        public int compare(Object orderOne,Object orderTwo) {
                OrderLocal firstOrder = (OrderLocal)orderOne;
                OrderLocal secondOrder = (OrderLocal)orderTwo;
                int firstPriority = firstOrder.getPriority();
                int secondPriority = secondOrder.getPriority();
                
                // if orders are of same priority, order them according to requestedCompletion only
                if (firstPriority == secondPriority) return super.compare(orderOne, orderTwo);
                
                // if they are of different priority, order them in DESCENDING order
                if (firstPriority < secondPriority) {
                    return 1;
                } else {
                    return -1;
                }
        }
        
        public boolean equals(Object anotherComparator) {
            return (anotherComparator instanceof PrioOrderComparator);
        }
    }
    
    OrderLocalHome anOrderHome;
    OrderValueIteratorHome anOrderValueIteratorHome;
    
    
    public QueryHelper(OrderLocalHome anOrderHome, OrderValueIteratorHome anOrderValueIteratorHome) {
        this.anOrderHome = anOrderHome;
        this.anOrderValueIteratorHome = anOrderValueIteratorHome;
    }
    
    private List sortOrders(Collection orders, Comparator aComparator) {
        
        OrderLocal[] sortedOrders = (OrderLocal[])orders.toArray(new OrderLocal[0]);
        Arrays.sort(sortedOrders, aComparator);
        return Arrays.asList(sortedOrders);
    }
    

    public OrderValueIterator processQuery(QueryAllOrdersValue allOrdersQuery, String[] attributeNames) 
        throws javax.oss.IllegalArgumentException, RemoteException, FinderException 
    {
        try {
            Collection urgentOrders = anOrderHome.findAllOrders();
            OrderKey[] orderKeys = new OrderKey[urgentOrders.size()];
            Iterator orderIterator = urgentOrders.iterator();
            for (int i=0 ; orderIterator.hasNext() ; i++) {
                orderKeys[i] = ((OrderLocal)orderIterator.next()).getOrderKey();
            }
            return new OrderValueIteratorProxy(anOrderValueIteratorHome.create(orderKeys, attributeNames).getHandle());
        } catch (CreateException ce) {
            throw new RemoteException("could not create OrderValueIterator");
        }
        
    }
    
    public OrderValueIterator processQuery(QueryUrgentOrders urgentOrderQuery, String[] attributeNames) 
        throws javax.oss.IllegalArgumentException, RemoteException, FinderException 
    {
        if (urgentOrderQuery.getRequestedCompletionDateThreshold() == null) {
            throw new javax.oss.IllegalArgumentException("RequestedCompletionDateThreshold is null");
        }
        try {
            Collection urgentOrders = anOrderHome.findUrgentOrders(new Timestamp(urgentOrderQuery.getRequestedCompletionDateThreshold().getTime()));
            Comparator aComparator = new PrioOrderComparator();
            List sortedOrders = sortOrders(urgentOrders, aComparator);
            OrderKey[] orderKeys = new OrderKey[sortedOrders.size()];
            Iterator orderIterator = sortedOrders.iterator();
            for (int i=0 ; orderIterator.hasNext() ; i++) {
                orderKeys[i] = ((OrderLocal)orderIterator.next()).getOrderKey();
            }
            return new OrderValueIteratorProxy(anOrderValueIteratorHome.create(orderKeys, attributeNames).getHandle());
        } catch (CreateException ce) {
            throw new RemoteException("could not create OrderValueIterator");
        }
        
    }
    
    public OrderValueIterator processQuery(QueryOrdersByDateInterval dateIntervalQuery, String[] attributeNames) 
        throws javax.oss.IllegalArgumentException, RemoteException, FinderException  
    {
        if (dateIntervalQuery.getFromRequestedCompletionDate() == null) {
            throw new javax.oss.IllegalArgumentException("FromRequestedCompletionDate is null");
        }
        if (dateIntervalQuery.getToRequestedCompletionDate() == null) {
            throw new javax.oss.IllegalArgumentException("ToRequestedCompletionDate is null");
        }
        
        // swap dates if they are in the wrong order
        if (dateIntervalQuery.getFromRequestedCompletionDate().compareTo(dateIntervalQuery.getToRequestedCompletionDate())>0) {
            java.util.Date dummyDate = dateIntervalQuery.getFromRequestedCompletionDate();
            dateIntervalQuery.setFromRequestedCompletionDate(dateIntervalQuery.getToRequestedCompletionDate());
            dateIntervalQuery.setToRequestedCompletionDate(dummyDate);
        }
        
        try {
            Collection nextOrders = anOrderHome.findNextOrders(new Timestamp(dateIntervalQuery.getFromRequestedCompletionDate().getTime()),
                                                                new Timestamp(dateIntervalQuery.getToRequestedCompletionDate().getTime()));
            Comparator aComparator = new OrderComparator();
            List sortedOrders = sortOrders(nextOrders, aComparator);
            OrderKey[] orderKeys = new OrderKey[sortedOrders.size()];
            Iterator orderIterator = sortedOrders.iterator();
            for (int i=0 ; orderIterator.hasNext() ; i++) {
                orderKeys[i] = ((OrderLocal)orderIterator.next()).getOrderKey();
            }
            return new OrderValueIteratorProxy(anOrderValueIteratorHome.create(orderKeys, attributeNames).getHandle());
        } catch (CreateException ce) {
            throw new RemoteException("could not create OrderValueIterator");
        }
    }
    
    
    public OrderValueIterator processQuery(QueryValue aQueryValue, String[] attributeNames) 
        throws javax.oss.IllegalArgumentException, RemoteException, FinderException 
    {
        if (aQueryValue == null) {
            throw new javax.oss.IllegalArgumentException("query is null.");
        }
        
        if (aQueryValue instanceof QueryUrgentOrders) {
            return processQuery((QueryUrgentOrders)aQueryValue, attributeNames );
        } if (aQueryValue instanceof QueryOrdersByDateInterval) {
            return processQuery((QueryOrdersByDateInterval)aQueryValue, attributeNames );
        } if (aQueryValue instanceof QueryAllOrdersValue) {
            return processQuery((QueryAllOrdersValue)aQueryValue, attributeNames );
        } else throw new javax.oss.IllegalArgumentException("unsupported query.");
    }
}


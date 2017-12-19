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

import java.sql.*;
import javax.sql.*;
import java.io.*;
import javax.naming.*;
import java.rmi.*;
import javax.rmi.*;
import javax.ejb.*;
import javax.jms.*;
import javax.oss.*;
import javax.oss.order.*;
import java.util.*;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public class OrderScheduler extends Thread implements javax.jms.MessageListener, javax.jms.ExceptionListener {
    
    public static final int ORDER_QUERY_TO_PROCESS_RATIO = 10;
    
    public static final int SLEEP_INTERVALL = 1000*60*5;
    //    public static final int SLEEP_INTERVALL = 1000*30;
    
    public class ContextCreationException extends Exception {
        public ContextCreationException() {super();}
        public ContextCreationException(String message) {super(message);}
    }
    
    private String[][] initialContextDefaults = {
    		{"com.sun.appserv.naming.S1ASCtxFactory","com.sun.enterprise.naming.SerialInitContextFactory","weblogic.jndi.WLInitialContextFactory"},
			{"iiop://localhost:3700","rmi://127.0.0.1","t3://127.0.0.1:7001"}
    };
    private static final int CONTEXT_CLASS = 0;
    private static final int CONTEXT_URL = 1;
    
    private Context myContext = null;
    private Hashtable myContextEnv;
    
    /** Holds value of property eventImplClassLoader. */
    // VP private ClassLoader eventImplClassLoader;
    
    private TopicConnection eventConnection = null;

    /** Holds value of property eventTopicPresent. */
    private boolean eventTopicPresent = false;

    /** Holds value of property waitingForOrders. */
    private boolean waitingForOrders = false;

    /** Holds value of property orderProcessTime. */
    private long orderProcessTime;
    
    /** Holds value of property activationSessionPresent. */
    private boolean activationSessionPresent = false;
    
    /** Holds value of property threadTermination. */
    private boolean threadTermination = false;
    
    private java.util.Date nextOrderRequestedCompletionDate;
    
    private QueryUrgentOrders urgentOrderQuery;
    private QueryOrdersByDateInterval nextOrderQuery;
    
    private JVTActivationSession anActivationSession;

    private String orderProcessorHomeName;
    private String jvtActivationHomeName;
    private String topicConnectionFactoryName;
    private String eventTopicName;
    
    public OrderScheduler(Hashtable contextEnv, String jvtEventTopicName,String topicFactoryName,String jvtActivationHomeName,String orderProcessorHomeName) {
        
        myContextEnv = contextEnv;
        this.orderProcessorHomeName = orderProcessorHomeName;
        this.jvtActivationHomeName = jvtActivationHomeName;
        this.topicConnectionFactoryName = topicFactoryName;
        this.eventTopicName = jvtEventTopicName;
    }
    //
    // accessor methods
    //
    
    private String getMyName() {
        return Thread.currentThread().getName();
    }
    
    private boolean isEventTopicPresent() {
        return eventTopicPresent;
    }
    
    private void setEventTopicPresent(boolean status) {
        eventTopicPresent = status;
    }
    
    /** Getter for property activationSessionPresent.
     * @return Value of property activationSessionPresent.
     */
    public boolean isJVTActivationSessionPresent() {
        return activationSessionPresent;
    }
    
    /** Setter for property activationSessionPresent.
     * @param activationSessionPresent New value of property activationSessionPresent.
     */
    public void setJVTActivationSessionPresent(boolean activationSessionPresent) {
        this.activationSessionPresent = activationSessionPresent;
    }
    
    private java.util.Date getNextOrderRequestedCompletionDate() {
        return nextOrderRequestedCompletionDate;
    }
    
    private void setNextOrderRequestedCompletionDate(java.util.Date newOrder) {
        nextOrderRequestedCompletionDate = newOrder;
    }
    
    private boolean isWaitingForOrders() {
        return waitingForOrders;
    }
    
    private synchronized void setWaitingForOrders(boolean status) {
        waitingForOrders = status;
    }
    
    //
    // JMS HANDLING
    //
    protected void handleOrderEvent(OrderAttributeValueChangeEvent event) {
        //System.out.println("OrderAttributeValueChangeEvent");
        if (isWaitingForOrders()) {
            synchronized(this) {
                this.notify();
            }
        }
    }
    
    protected void handleOrderEvent(OrderCreateEvent event) {
        //System.out.println("OrderCreateEvent");
    }
    
    protected void handleOrderEvent(OrderRemoveEvent event) {
        //System.out.println("OrderRemoveEvent");
    }
    
    protected void handleOrderEvent(OrderStateChangeEvent event) {
        //System.out.println("OrderStateChangeEvent");
        if (event.getCurrentState().startsWith(OrderState.RUNNING) && isWaitingForOrders()) {
            synchronized(this) {
                this.notify();
            }
        }
    }
    
    protected void handleOrderEvent(javax.oss.Event event) {
        //System.out.println("javax.oss.Event");
    }
    
    public void onMessage(javax.jms.Message message) {
		System.out.println("===> Receive message in OrderScheduler !");

        if (message instanceof ObjectMessage) {
            try {
                //VP Thread.currentThread().setContextClassLoader(getEventImplClassLoader());
                Object event = ((ObjectMessage)message).getObject();
                /* String className = message.getStringProperty(OrderMessageProperty.OSSJ_EVENT_TYPE_PROP_NAME);
                Class eventClass = Class.forName(className); */
                if (event instanceof OrderAttributeValueChangeEvent) {
                    handleOrderEvent((OrderAttributeValueChangeEvent)event);
                } else if (event instanceof OrderCreateEvent) {
                    handleOrderEvent((OrderCreateEvent)event);
                } else if (event instanceof OrderRemoveEvent) {
                    handleOrderEvent((OrderRemoveEvent)event);
                } else if (event instanceof OrderStateChangeEvent) {
                    handleOrderEvent((OrderStateChangeEvent)event);
                }
            } catch (JMSException jmse) {
                System.out.println("Order Scheduler ("+getMyName()+"): "+jmse.toString());
            }
        }
    }
    
    public void onException(javax.jms.JMSException jmse) {
        //System.out.println(jmse.toString());
        resetConnection();
        notify();
    }
    
    //
    // ORDER HANDLING
    //
    
    
    private String getOrderProcessorHomeName() {
        return orderProcessorHomeName;
    }
    
    private String getJVTActivationHomeName() {
        return jvtActivationHomeName;
    }
    
    private Iterator queryUrgentOrders() throws ContextCreationException, RemoteException, NamingException {
        
        long start = System.currentTimeMillis();
        
        // VP QueryUrgentOrders anUrgentQuery = getUrgentOrderQuery();
        QueryUrgentOrders anUrgentQuery = null;
		try {
			anUrgentQuery = getUrgentOrderQuery();
        
        } catch (Exception re) {
        	System.out.println(re.toString());
			re.printStackTrace();
        }
		// end VP

        try {
            anUrgentQuery.setRequestedCompletionDateThreshold(new Timestamp(System.currentTimeMillis()));
            OrderValueIterator urgentIterator = getJVTActivationSession().queryOrders(anUrgentQuery, new String[] {OrderValue.KEY});
            List orderKeyList = new ArrayList();
            OrderValue[] orderValues;
            while (true) {
                orderValues = urgentIterator.getNextOrder(10);
                // returns empty array if no more elements
                if (orderValues.length==0) {
                    break;
                }
                for (int i=0 ; i<orderValues.length ; i++) {
                    orderKeyList.add(orderValues[i].getOrderKey());
                }
            }
            
            long stop = System.currentTimeMillis();
            
            try {
                urgentIterator.remove();
            } catch (RemoveException re) {
                System.out.println(re.toString());
            }
            
            // process orders for x times as long as it take to query these
            setOrderProcessTime( (stop-start) * ORDER_QUERY_TO_PROCESS_RATIO );
            
            return orderKeyList.iterator();
        } catch (RemoteException re) {
            System.out.println("Order Scheduler ("+getMyName()+"): "+re.toString());
        } catch (javax.oss.IllegalArgumentException iae) {
            System.out.println("Order Scheduler ("+getMyName()+"): "+iae.toString());
        }
        
        return null;
        
    }
    
    private java.util.Date queryNextOrderRequestedCompletionDate() throws ContextCreationException, RemoteException, NamingException {
        QueryOrdersByDateInterval aNextQuery = getNextOrderQuery();
        
        long currentTime = System.currentTimeMillis();
        try {
            aNextQuery.setFromRequestedCompletionDate(new Timestamp(currentTime));
            aNextQuery.setToRequestedCompletionDate(new Timestamp(currentTime+SLEEP_INTERVALL));
            
            OrderValueIterator urgentIterator = getJVTActivationSession().queryOrders(aNextQuery, new String[] {OrderValue.REQUESTED_COMPLETION_DATE});
            OrderValue[] orderValues = orderValues = urgentIterator.getNextOrder(1);
            try {
                urgentIterator.remove();
            } catch (RemoveException re) {
                System.out.println(re.toString());
            }
            if (orderValues.length==0) {
                return null;
            }
            return orderValues[0].getRequestedCompletionDate();
        } catch (RemoteException re) {
            System.out.println("Order Scheduler ("+getMyName()+"): "+re.toString());
        } catch (javax.oss.IllegalArgumentException iae) {
            System.out.println("Order Scheduler ("+getMyName()+"): "+iae.toString());
        }
        
        return null;
    }
    
    private void processOrder(Iterator urgentOrderKeyIterator) {
        try {
            Context aContext = getContext();
            OrderProcessorHome anOrderProcessorHome = (OrderProcessorHome)aContext.lookup(getOrderProcessorHomeName());
            OrderProcessor anOrderProcessor = anOrderProcessorHome.create();
            
            // only do this for n seconds, since new orders might
            // be to be delivered with high priority
            
            long startTime = System.currentTimeMillis();
            long processTime = getOrderProcessTime();
            while (urgentOrderKeyIterator.hasNext() && (startTime + processTime) > System.currentTimeMillis() && !userExit()) {
                try {
                    OrderKey anOrderKey = (OrderKey)urgentOrderKeyIterator.next();
                    //System.out.print("Processing Order: "+anOrderKey.toString()+" ... ");
                    anOrderProcessor.finishOrder(anOrderKey);
                    //System.out.println("done!");
                } catch (javax.oss.IllegalStateException ise) {
                }
            }
        } catch (Exception e) {
            System.out.println("Order Scheduler ("+getMyName()+"): "+e.toString());
        }
    }
    
    //
    // THREAD
    //
    
    private boolean userExit() {
        return isThreadTermination();
    }
    
    private long getConnectionSleepTime() {
        //System.out.println("Sleeping for "+(1000*30)/1000+" seconds");
        return 1000*30;
    }
    
    private long getNormalSleepTime() {
        if (getNextOrderRequestedCompletionDate()!=null) {
            long deliveryTime = getNextOrderRequestedCompletionDate().getTime();
            long sleepTime = deliveryTime - System.currentTimeMillis();
            //System.out.println("Sleeping for "+(sleepTime<=0?0:sleepTime)/1000+" seconds");
            return (sleepTime<=0?0:sleepTime);
        }
        //System.out.println("Sleeping for "+(SLEEP_INTERVALL)/1000+" seconds");
        return SLEEP_INTERVALL;
    }
    
    public void run() {
        System.out.println("Order Scheduler ("+getMyName()+"): started");
        /*
        System.out.println("Order Scheduler ("+getMyName()+"): configuration:");
        System.out.println("\tJVTHome       : "+getJVTActivationHomeName());
        System.out.println("\tJVTEventTopic : "+getEventTopicName());
        System.out.println("\tTopicFactory  : "+getTopicConnectionFactoryName());
        System.out.println("\tOrderProcessor: "+getOrderProcessorHomeName());
         **/
        while (!userExit()) {
            int errorOnConnectCount = 0;
            try {
                if ( ! (isEventTopicPresent() && isJVTActivationSessionPresent()) ) {
                    try {
                        if (!isEventTopicPresent()) {
                            // throws exception if subscription fails
                            ensureEventSubscription();
                            setEventTopicPresent(true);
                        }
                        if (!isJVTActivationSessionPresent()) {
                            // throws exception if subscription fails
                            ensureJVTActivationSessionPresent();
                            setJVTActivationSessionPresent(true);
                        }
                        System.out.println("Order Scheduler ("+getMyName()+"): connection established");
                    } catch (Exception e) {
                        // VP if (errorOnConnectCount>-1) {
                        // if (errorOnConnectCount == 0 ) {
                            e.printStackTrace();
                        // }
                        errorOnConnectCount++;
                        sleep(getConnectionSleepTime());
                    }
                } else {
                    errorOnConnectCount = 0;
                    synchronized(this) {
                        Iterator urgentOrderIterator;
                        try {
                            while (!userExit()) {
                                urgentOrderIterator = queryUrgentOrders();
                                if (urgentOrderIterator == null || !urgentOrderIterator.hasNext()) break;
                                processOrder(urgentOrderIterator);
                            }
                            if (!userExit()) {
                                setNextOrderRequestedCompletionDate(queryNextOrderRequestedCompletionDate());
                            }
                        } catch (RemoteException re) {
                            System.out.println("Order Scheduler ("+getMyName()+"): "+re.toString());
                        } catch (NamingException ne) {
                            System.out.println("Order Scheduler ("+getMyName()+"): "+ne.toString(true));
                        } catch (ContextCreationException cce) {
                            System.out.println("Order Scheduler ("+getMyName()+"): "+cce.toString());
                        }
                        if (!userExit()) {
                            setWaitingForOrders(true);
                            wait(getNormalSleepTime());
                            setWaitingForOrders(false);
                        }
                    }
                }
            } catch (InterruptedException ie) {
                //System.out.println(ie.toString());
            }
        }
        System.out.print("Order Scheduler ("+getMyName()+"): unsubscribing from event topic . . . ");
        try {
            eventConnection.close();
        } catch (JMSException jmse) {
            System.out.println("FAILED!!!!");
        }
        System.out.println("done");
        
        System.out.println("Order Scheduler ("+getMyName()+"): terminated");
    }
    
    //
    // REGISTER ON EVENT TOPIC AND START DB-THREAD
    //

    private void resetConnection() {
        System.out.println("Order Scheduler ("+getMyName()+"): lost Connection");
        setEventTopicPresent(false);
        myContext=null;
    }
    
    public Context getContext() throws ContextCreationException {
        
        if (myContext!=null) return myContext;
        
        try {
            myContext = new InitialContext(myContextEnv);
        } catch (NamingException ne) {
            System.out.println("Order Scheduler ("+getMyName()+"): "+"Could not create Context from these parameters!");
            System.out.println(Context.INITIAL_CONTEXT_FACTORY+": "+myContextEnv.get(Context.INITIAL_CONTEXT_FACTORY));
            System.out.println(Context.PROVIDER_URL+": "+myContextEnv.get(Context.PROVIDER_URL));
            System.out.println(ne.toString());
        }
        if (myContext == null) {
            throw new ContextCreationException("Could not create Context");
        }
        
        return myContext;
    }
    
    private String getTopicConnectionFactoryName() {
        return topicConnectionFactoryName;
    }
    
    private String getEventTopicName() {
        return eventTopicName;
    }
    
    private void ensureEventSubscription() throws NamingException, ClassCastException, JMSException, ContextCreationException {
        javax.jms.TopicSubscriber eventSubscriber = null;
        javax.jms.TopicSession eventSession = null;
        javax.jms.TopicConnectionFactory tConnFactory = null;
        Object result;
        Context aContext = getContext();
        
        // Get Topic Connection
        result = aContext.lookup(getTopicConnectionFactoryName());
        tConnFactory = (javax.jms.TopicConnectionFactory)PortableRemoteObject.narrow(result, javax.jms.TopicConnectionFactory.class);
        eventConnection = tConnFactory.createTopicConnection();
		// VP see CR 6197813
		// J2EE spec 6.6 the setExceptionListener shall be used only the client container context
		// (not in the ejb or web container)
        //eventConnection.setExceptionListener(this);
        eventSession = eventConnection.createTopicSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
        
        // Get Topic
        result = aContext.lookup(getEventTopicName());
        javax.jms.Topic eventTopic = (javax.jms.Topic)PortableRemoteObject.narrow(result, javax.jms.Topic.class);
        eventSubscriber = eventSession.createSubscriber(eventTopic);
        
        eventSubscriber.setMessageListener(this);
        eventConnection.start();
    }
    
    private void ensureJVTActivationSessionPresent() throws Exception {
        JVTActivationSession anActivationSession = getJVTActivationSession();
        if (anActivationSession == null) {
            // there was an caught exception in getJVTActivationSession()
            throw new Exception("no JVTActivationSession present");
        }
		 anActivationSession.getEventDescriptor(OrderCreateEvent.class.getName());

        EventPropertyDescriptor createEventDescriptor = anActivationSession.getEventDescriptor(OrderCreateEvent.class.getName());
        OrderCreateEvent aCreateEvent = (OrderCreateEvent)createEventDescriptor.makeEvent();
        //VP setEventImplClassLoader(aCreateEvent.getClass().getClassLoader());
    }
    
    private JVTActivationSession getJVTActivationSession() {
        if (anActivationSession != null) {
            try {
                anActivationSession.getHandle();
            } catch (Exception e) {
                anActivationSession = null;
            }
        }
        if (anActivationSession == null) {
            try {
                Object obj = getContext().lookup(getJVTActivationHomeName());
                //System.out.println("Order Scheduler ("+getMyName()+"): Received as JVTActivationHome: "+obj+" ("+obj.getClass().getName()+")");
                Object obj2 = javax.rmi.PortableRemoteObject.narrow(obj, JVTActivationHome.class);
                //System.out.println("Order Scheduler ("+getMyName()+"): narrowed to JVTActivationHome: "+obj2+" ("+obj2.getClass().getName()+")");
                //System.out.println("Order Scheduler ("+getMyName()+"): should be: "+JVTActivationHome.class.getName());
                javax.oss.order.JVTActivationHome anActivationHome = (javax.oss.order.JVTActivationHome)obj2;
                anActivationSession = anActivationHome.create();
            } catch (RemoteException re) {
                System.out.println("Order Scheduler ("+getMyName()+"): "+re.toString());
            } catch (CreateException ce) {
                System.out.println("Order Scheduler ("+getMyName()+"): "+ce.toString());
            } catch (NamingException ne) {
                System.out.println("Order Scheduler ("+getMyName()+"): "+ne.toString());
            } catch (ContextCreationException cce) {
                cce.toString();
            }
        }
        return anActivationSession;
    }
    
    private QueryUrgentOrders getUrgentOrderQuery() {
        if (urgentOrderQuery==null) {
            try {
                urgentOrderQuery = (QueryUrgentOrders)getJVTActivationSession().makeQueryValue(QueryUrgentOrders.class.getName());
            } catch (NullPointerException npe) {
                System.out.println("Order Scheduler ("+getMyName()+"): "+npe.toString());
            } catch (javax.oss.IllegalArgumentException iae) {
                System.out.println("Order Scheduler ("+getMyName()+"): "+iae.toString());
            } catch (RemoteException re) {
                System.out.println("Order Scheduler ("+getMyName()+"): "+re.toString());
            }
        }
        return urgentOrderQuery;
    }
    
    private QueryOrdersByDateInterval getNextOrderQuery() {
        if (nextOrderQuery==null) {
            try {
                nextOrderQuery = (QueryOrdersByDateInterval)getJVTActivationSession().makeQueryValue(QueryOrdersByDateInterval.class.getName());
            } catch (javax.oss.IllegalArgumentException iae) {
                System.out.println("Order Scheduler ("+getMyName()+"): "+iae.toString());
            } catch (RemoteException re) {
                System.out.println("Order Scheduler ("+getMyName()+"): "+re.toString());
            }
        }
        return nextOrderQuery;
    }
    
    /** Getter for property orderProcessTime.
     * @return Value of property orderProcessTime.
     */
    public long getOrderProcessTime() {
        return orderProcessTime;
    }
    
    /** Setter for property orderProcessTime.
     * @param orderProcessTime New value of property orderProcessTime.
     */
    public void setOrderProcessTime(long orderProcessTime) {
        this.orderProcessTime = orderProcessTime;
    }
    
    /** Getter for property threadTermination.
     * @return Value of property threadTermination.
 */
    public boolean isThreadTermination() {
        return threadTermination;
    }    
    
    /** Setter for property threadTermination.
     * @param threadTermination New value of property threadTermination.
 */
    public void setThreadTermination(boolean threadTermination) {
        this.threadTermination = threadTermination;
        //System.out.println("Order Scheduler ("+getMyName()+"): received remination request");
        if (isWaitingForOrders()) {
            synchronized(this) {
                //System.out.print("Order Scheduler ("+getMyName()+"): notify myself ... ");
                this.notify();
                //System.out.println(" ... done");
            }
        }
    }
    
    /** Getter for property eventImplClassLoader.
     * @return Value of property eventImplClassLoader.
 */
    //VP public ClassLoader getEventImplClassLoader() {
    //VP     return eventImplClassLoader;
    //VP }
    
    /** Setter for property eventImplClassLoader.
     * @param eventImplClassLoader New value of property eventImplClassLoader.
 */
    //VP public void setEventImplClassLoader(ClassLoader eventImplClassLoader) {
    //VP     this.eventImplClassLoader = eventImplClassLoader;
    //VP }
    
}

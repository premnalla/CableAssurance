/*
 * OrderEventListener.java
 *
 * Created on 21. März 2001, 13:28
 */

package com.nokia.oss.ossj.sa.client;

import javax.jms.*;
import javax.oss.order.*;
/**
 *
 * @author  aebbert
 * @version 
 */
public class OrderEventListener extends Object implements javax.jms.MessageListener {

    Logger myLog;
    javax.oss.Event[] eventImplementations;
    //VP 
    ClassLoader eventImplClassLoader;
    //VP 
    boolean addedEventClassLoader = false;
    
    /** Creates new OrderEventListener */
    public OrderEventListener(Logger aLog, javax.oss.Event[] eventImpls) {
        myLog = aLog;
        //VP 
        eventImplementations = eventImpls;
        //VP 
        eventImplClassLoader = eventImpls[0].getClass().getClassLoader();
        System.out.println("storing classloader which knows events: "+eventImplClassLoader);
        myLog.log("OrderEventListener created");
    }

    protected void handleOrderEvent(OrderAttributeValueChangeEvent event) {
        myLog.log(">>> Attributes Changed:");
        GenericTextClient.printAttributeContents(event.getNewOrderValue(), myLog);
        myLog.log("<<<");
    }
    
    protected void handleOrderEvent(OrderCreateEvent event) {
        myLog.log(">>> Order created:");
        GenericTextClient.printAttributeContents(event.getOrderValue(), myLog);
        myLog.log("<<<");
    }
    
    protected void handleOrderEvent(OrderRemoveEvent event) {
        myLog.log(">>> Order removed:");
        GenericTextClient.printAttributeContents(event.getOrderValue(), myLog);
        myLog.log("<<<");
    }
    
    protected void handleOrderEvent(OrderStateChangeEvent event) {
        myLog.log(">>> Order State Changed:");
        myLog.log("Order: "+event.getOrderKey().toString());
        myLog.log("new State: "+event.getCurrentState());
        myLog.log("reason: "+event.getReason());
        myLog.log("<<<");
    }
    
    protected void handleOrderEvent(javax.oss.Event event) {
        myLog.log("GENERAL EVENT RECEIVED");
    }
    
    public void onMessage(javax.jms.Message message) {
        myLog.logAlways("");
        //VP 
        if (!Thread.currentThread().getContextClassLoader().equals(eventImplClassLoader)) {
        //VP 
            System.out.println("current classloader in onMessage is: "+Thread.currentThread().getContextClassLoader());
        //VP 
            Thread.currentThread().setContextClassLoader(eventImplClassLoader);
        //VP 
            System.out.println("changed classloader in onMessage is: "+Thread.currentThread().getContextClassLoader());
        //VP 
        }
        try {
	  if (message instanceof ObjectMessage) {
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
                } else if (event instanceof javax.oss.Event) {
                    handleOrderEvent((javax.oss.Event)event);
                }
        } else if (message instanceof TextMessage) {
	  System.out.println("Received XML Event:\n"+((TextMessage)message).getText());
	}
            } catch (JMSException jmse) {
                myLog.log(jmse);
            }
    }
}

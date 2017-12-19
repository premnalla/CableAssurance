// %Z%file      %M%
// %Z%author    Vincent Perrot, Sun Microsystems, Inc.
// %Z%version   %I%
// %Z%date      %D%
// 
// Copyright 2003 Sun Microsystems, Inc. All rights reserved.
// This software is the proprietary information of Sun Microsystems, Inc.
// Use is subject to license terms.
// 
// Copyright 2003 Sun Microsystems, Inc. Tous droits réservés.
// Ce logiciel est proprieté de Sun Microsystems, Inc.
// Distribué par des licences qui en restreignent l'utilisation.
// 

package com.nokia.oss.ossj.sa.ri;

import javax.jms.Destination;
import javax.jms.Topic;
import javax.jms.Queue;
import javax.jms.TemporaryTopic;
import javax.jms.TemporaryQueue;

public class SaDestination implements java.io.Serializable {
    transient private Destination d = null;
    public SaDestination (Destination d) {  
        this.d = d;
    }
    
    public Destination getDestination(){
        return d;
    }
    
    public void setDestination(Destination d){
        this.d = d;
    }
     
    public int hashCode(){
        return d.toString().hashCode();
    }
    
    public boolean equals(Object o){
        try {
            if (o instanceof SaDestination){
                Object de = ((SaDestination)o).getDestination();
                if ((de instanceof Topic && d instanceof Topic )
                    || (de instanceof TemporaryTopic && d instanceof TemporaryTopic ))
                    return (((Topic)d).getTopicName().equals(((Topic)de).getTopicName()));
                else if ((de instanceof Queue && d instanceof Queue )
                         || (de instanceof TemporaryQueue && d instanceof TemporaryQueue ))
                    return (((Queue)d).getQueueName().equals(((Queue)de).getQueueName()));
            }
        } catch (javax.jms.JMSException e){
        }
        return false;
        
    }
}

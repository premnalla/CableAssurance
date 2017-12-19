package ossj.qos.fmri;

import javax.oss.Event;
import javax.jms.ObjectMessage;
import java.util.HashMap;
import javax.jms.Session;
import javax.jms.TextMessage;
/**
 * MessageBuilder
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public interface MessageBuilder {
    
    public ObjectMessage makeObjectMessage( Event event,
    java.util.HashMap properties, Session session ) throws java.lang.IllegalArgumentException;
    
    public TextMessage makeTextMessage( Event event, 
    java.util.HashMap properties, javax.jms.Session session) throws java.lang.IllegalArgumentException;
   
}

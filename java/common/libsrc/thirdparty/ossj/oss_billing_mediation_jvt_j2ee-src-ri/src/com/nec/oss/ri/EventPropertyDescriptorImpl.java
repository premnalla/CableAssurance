package com.nec.oss.ri;

import java.util.Hashtable;
import javax.oss.Event;
import javax.oss.EventPropertyDescriptor;
import javax.jms.TopicSession;
/**
 * EventPropertyDescriptor Implementation Class
 *
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class EventPropertyDescriptorImpl
       implements EventPropertyDescriptor,
                  java.io.Serializable
{

   private String       EventType;
   private String[]     PropertyNames;
   private String[]     PropertyTypes;

   protected TopicSession topicSession;



   public String   getEventType()               { return EventType; }
   public String[] getPropertyNames()           { return PropertyNames; }
   public String[] getPropertyTypes()           { return PropertyTypes; }

   public TopicSession getTopicSession()        { return topicSession; }

   public void setEventType(String value)       { EventType = value; }
   public void setPropertyNames(String[] value) { PropertyNames = value; }
   public void setPropertyTypes(String[] value) { PropertyTypes = value; }

   public void setTopicSession(TopicSession value) { topicSession = value; }


public Event makeEvent()
{
return null;
}
}

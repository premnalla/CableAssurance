package ossj.qos;


import javax.oss.*;
import ossj.qos.util.IRPEventImpl;
import java.util.Hashtable;
import javax.jms.*;


/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 0.2
 */

public class EventPropertyDescriptorImpl implements EventPropertyDescriptor {

  public EventPropertyDescriptorImpl() {
  }

  public String getEventType() {
    return null;
  }

  public String[] getPropertyNames() {
    return null;
  }

  public String[] getPropertyTypes() {
    return null;
  }

  public Event makeEvent() {
    return new IRPEventImpl();
  }
}

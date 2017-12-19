package ossj.qos.pm.measurement;


import javax.oss.Event;
import javax.oss.pm.measurement.*;

//import ossj.qos.util.IRPEventPropertyDescriptorImpl;


/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 0.2
 */

//public class PerformanceDataAvailableEventDescriptorImpl extends IRPEventPropertyDescriptorImpl implements PerformanceDataAvailableEventDescriptor {

public class PerformanceDataAvailableEventDescriptorImpl extends PmIRPEventPropertyDescriptorImpl implements PerformanceDataAvailableEventDescriptor {
  public PerformanceDataAvailableEventDescriptorImpl() {
  }

  public PerformanceDataAvailableEvent makePerformanceDataAvailableEvent() {
    PerformanceDataAvailableEvent ev = new PerformanceDataAvailableEventImpl();
    return ev;
  }

  public String getEventType() {
    return OSS_EVENT_TYPE_VALUE;
  }

   public Event makeEvent() {
    PerformanceDataAvailableEvent ev = new PerformanceDataAvailableEventImpl();
    return ev;
  }

}

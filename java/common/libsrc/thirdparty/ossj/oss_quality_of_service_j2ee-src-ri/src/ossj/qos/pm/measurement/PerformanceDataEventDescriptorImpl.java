package ossj.qos.pm.measurement;


import javax.oss.pm.measurement.*;
import javax.oss.Event;

//import ossj.qos.util.IRPEventPropertyDescriptorImpl;




/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 0.2
 */

//public class PerformanceDataEventDescriptorImpl extends IRPEventPropertyDescriptorImpl implements PerformanceDataEventDescriptor {

public class PerformanceDataEventDescriptorImpl extends PmIRPEventPropertyDescriptorImpl implements PerformanceDataEventDescriptor {
  public PerformanceDataEventDescriptorImpl() {
  }

  public PerformanceDataEvent makePerformanceDataEvent() {
    PerformanceDataEvent ev = new PerformanceDataEventImpl();
    return ev;
  }

  public String getEventType() {
    return OSS_EVENT_TYPE_VALUE;
  }

   public Event makeEvent() {
    PerformanceDataEvent ev = new PerformanceDataEventImpl();
    return ev;
  }

}

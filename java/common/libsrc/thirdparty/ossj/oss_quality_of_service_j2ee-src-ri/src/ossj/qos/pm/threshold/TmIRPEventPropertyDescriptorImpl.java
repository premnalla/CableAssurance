package ossj.qos.pm.threshold;


import javax.oss.Event;
import javax.oss.EventPropertyDescriptor;
import javax.oss.util.IRPEventPropertyDescriptor;
import javax.oss.util.IRPEvent;
import ossj.qos.util.IRPEventPropertyDescriptorImpl;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class TmIRPEventPropertyDescriptorImpl extends IRPEventPropertyDescriptorImpl{

  public TmIRPEventPropertyDescriptorImpl() {
    super();
  }

  public IRPEvent makeIRPEvent() {
    return new TmIRPEventImpl();
  }

   public Event makeEvent() {
    return new TmIRPEventImpl();
  }

}
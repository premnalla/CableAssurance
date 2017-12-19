package ossj.qos.pm.measurement;



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

public class PmIRPEventPropertyDescriptorImpl extends IRPEventPropertyDescriptorImpl{

  public PmIRPEventPropertyDescriptorImpl() {
    super();
  }

  public IRPEvent makeIRPEvent() {
    return new PmIRPEventImpl();
  }

   public Event makeEvent() {
    return new PmIRPEventImpl();
  }

}
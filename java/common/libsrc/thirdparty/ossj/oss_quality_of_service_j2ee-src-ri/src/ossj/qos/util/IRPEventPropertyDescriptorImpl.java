package ossj.qos.util;

import ossj.qos.EventPropertyDescriptorImpl;

import javax.oss.Event;
import javax.oss.EventPropertyDescriptor;
import javax.oss.util.IRPEventPropertyDescriptor;
import javax.oss.util.IRPEvent;

/**
 * Title:        ossj.qos
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Copyright 2001 Ericsson Radio Systems AB
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 0.2
 */

public class IRPEventPropertyDescriptorImpl extends EventPropertyDescriptorImpl implements IRPEventPropertyDescriptor {

  public IRPEventPropertyDescriptorImpl() {
  }

  public IRPEvent makeIRPEvent() {
    return new IRPEventImpl();
  }

   public Event makeEvent() {
    return new IRPEventImpl();
  }

}
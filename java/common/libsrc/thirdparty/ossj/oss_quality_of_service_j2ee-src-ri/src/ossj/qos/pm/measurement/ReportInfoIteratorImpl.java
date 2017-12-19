package ossj.qos.pm.measurement;


import java.rmi.RemoteException;

import javax.oss.pm.measurement.*;
import javax.oss.pm.measurement.ReportInfoIterator;
//import ossj.qos.pm.util.ObjectIterator;
import ossj.qos.util.ObjectIterator;


/**
 * Title:        ossj.qos
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Copyright 2001 Ericsson Radio Systems AB
 * @author Andreas Jirven, Ali Feizabadi, Anna Eriksson
 * @version 0.2
 */

public class ReportInfoIteratorImpl extends ObjectIterator implements ReportInfoIterator {

  public ReportInfoIteratorImpl(ReportInfo[] ri) {
    super (ri);
  }


  public ReportInfo[] getNext(int how_many) throws RemoteException {
    Object[] nextValues = super.getNextValue(how_many);
    ReportInfo[] values = null;

    if (nextValues != null) {
      values = new  ReportInfo[nextValues.length];
      System.arraycopy(nextValues, 0, values, 0, nextValues.length);
    }
    return values;
  }
}

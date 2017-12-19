package ossj.qos.pm.measurement;


import javax.oss.pm.measurement.PerformanceMonitorValueIterator;
import javax.oss.pm.measurement.PerformanceMonitorValue;

import java.rmi.RemoteException;

import ossj.qos.ManagedEntityValueIteratorImpl;


/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 0.2
 */

public class PerformanceMonitorValueIteratorImpl extends ManagedEntityValueIteratorImpl implements PerformanceMonitorValueIterator {

  public PerformanceMonitorValueIteratorImpl(PerformanceMonitorValue[] measurementJobs) {
    super (measurementJobs);
  }

  public PerformanceMonitorValue[] getNextPerformanceMonitors(int how_many) throws RemoteException {
    Object[] nextValues = getNext(how_many);
    PerformanceMonitorValue[] values = null;

    if (nextValues != null) {
      values = new  PerformanceMonitorValue[nextValues.length];
      System.arraycopy(nextValues, 0, values, 0, nextValues.length);
    }
    return values;
  }

}

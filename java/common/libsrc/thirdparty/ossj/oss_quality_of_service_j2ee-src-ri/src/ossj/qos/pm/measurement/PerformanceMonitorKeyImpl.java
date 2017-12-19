package ossj.qos.pm.measurement;


import javax.oss.pm.measurement.PerformanceMonitorKey;

import ossj.qos.ManagedEntityKeyImpl;


/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 0.2
 */

public class PerformanceMonitorKeyImpl extends ManagedEntityKeyImpl implements PerformanceMonitorKey {

  public PerformanceMonitorKeyImpl() {
  }

  /**
   * Returns the primary key of the measurement job.
   *
   *@return String The primary key of the measurement job in string format.
   */
  public String getPerformanceMonitorPrimaryKey() {
    return (String) super.getPrimaryKey();
  }

  /**
   * Sets the primary key of the measurement job.
   *
   *@param key The primary key of the measurement job in string format.
   */
  public void setPerformanceMonitorPrimaryKey(String key) throws java.lang.IllegalArgumentException{
    if(key == null)
      throw new java.lang.IllegalArgumentException();

    super.setPrimaryKey(key);
  }

}

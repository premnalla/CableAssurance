//package ossj.qos.pm.util;

package ossj.qos.util;


import javax.oss.pm.util.*;


/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 0.2
 */

public class ObservableObjectIteratorImpl extends ObjectIterator implements ObservableObjectIterator {

  public ObservableObjectIteratorImpl(String[] observableObjects) {
    super (observableObjects);
  }


  public String[] getNext(int how_many)
  throws java.rmi.RemoteException {

    Object[] nextValues = getNextValue(how_many);
    String[] values = null;

    if (nextValues != null) {
      values = new String[nextValues.length];
      System.arraycopy(nextValues, 0, values, 0, nextValues.length);
    }
    return values;
  }
}

//package ossj.qos.pm.util;

package ossj.qos.util;


import java.io.Serializable;
import java.rmi.RemoteException;


/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 0.2
 */

public class ObjectIterator implements Serializable {

  protected Object[] objects;
  private int currentPos = -1;


  public ObjectIterator(Object[] objects) {

  try {
    this.objects = (Object[]) Util.clone(objects);
    //VP for(int i=0; i<objects.length; i++)
    //VP   this.objects[i] = Util.clone(objects[i]);
	} catch (java.lang.CloneNotSupportedException clex) {
	  clex.printStackTrace();
	  this.objects	= null;
	}
  }

  public Object[] getNextValue(int how_many) throws RemoteException {

    int numberOfObjectsToReturn;
    Object[] nextValues;

    // Determine the number of objects to return
    if ( (currentPos + how_many) <= (objects.length - 1) )
      numberOfObjectsToReturn = how_many;
    else
      numberOfObjectsToReturn = (objects.length - 1) - currentPos;

    if (numberOfObjectsToReturn > 0) {
      nextValues = new Object[numberOfObjectsToReturn];
      try {
        System.arraycopy(objects, currentPos + 1, nextValues, 0, numberOfObjectsToReturn);
        currentPos += numberOfObjectsToReturn;
      }
      catch (Exception e) {
        Log.write(this,Log.LOG_MAJOR,"getNextValue()","exception="+e);
        throw new RemoteException();
      }
    } else {
      nextValues = new Object[0];
	}

    return nextValues;

  }

  public void remove() {
    objects = null;
  }

}

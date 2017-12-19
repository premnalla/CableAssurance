package ossj.qos;

import java.rmi.RemoteException;

import ossj.qos.util.ObjectIterator;
import ossj.qos.util.Log;
import javax.oss.ManagedEntityValueIterator;
import javax.oss.ManagedEntityValue;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Ericsson Radio Systems AB
 * @author Henrik Lindström
 * @version 0.2
 */

public class ManagedEntityValueIteratorImpl extends ObjectIterator implements ManagedEntityValueIterator{

    public ManagedEntityValueIteratorImpl( Object[] objects )
    {
        super( objects );
    }

   /**
    * @param howMany
    * @return javax.oss.ManagedEntityValue[]
    * @throws java.rmi.RemoteException
    * @roseuid 3B938AEF0079
    */
   public ManagedEntityValue[] getNext(int howMany) throws RemoteException
   {
        Log.write(this,Log.LOG_ALL,"getNext()","howMany="+howMany);
        Object[] nextValues = getNextValue(howMany);
        ManagedEntityValue[] values = null;
        if(nextValues != null) {
          values = new  ManagedEntityValue[nextValues.length];
          System.arraycopy(nextValues, 0, values, 0, nextValues.length);
        }
        return values;
   }
}

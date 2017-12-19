package ossj.qos;

import javax.oss.ManagedEntityKeyResult;
import javax.oss.ManagedEntityKey;

import ossj.qos.util.Log;


/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Henrik Lindstrom
 * @version 0.2
 */

public class ManagedEntityKeyResultImpl implements ManagedEntityKeyResult
{
   protected ManagedEntityKey managedEntityKey = null;
   protected boolean isSuccess = false;
   protected Exception exception = null;

   /**
    * @roseuid 3B9F19420060
    */
   public ManagedEntityKeyResultImpl()
   {
        Log.write(this,"ManagedEntityKeyResultImpl()","called");
   }

   /**
    * @return javax.oss.ManagedEntityKey
    * @roseuid 3B9F194200F6
    */
   public ManagedEntityKey getManagedEntityKey()
   {
        Log.write(this,Log.LOG_ALL,"getManagedEntityKey()","managedEntityKey="+managedEntityKey);
        return managedEntityKey;
   }

   public void setManagedEntityKey(ManagedEntityKey key)
                         throws java.lang.IllegalArgumentException {

        // add some check of key here...!?
        managedEntityKey = key;
   }

   /**
    * @return boolean
    * @roseuid 3B9F1942016E
    */
   public boolean isSuccess()
   {
        Log.write(this,Log.LOG_ALL,"isSuccess()","isSuccess="+isSuccess);
        return isSuccess;
   }

   public void setSuccess( boolean success ) {
        isSuccess = success;
    }

   /**
    * @return java.lang.Exception
    * @roseuid 3B9F194201BE
    */
   public Exception getException()
   {
        Log.write(this,Log.LOG_ALL,"getException()","exception="+exception);
        return exception;
   }

    public void setException(java.lang.Exception ex)
                  throws java.lang.IllegalArgumentException {
        exception = ex;
    }


    public String toString() {
        return "ManagedEntityKey=" + managedEntityKey + ", isSuccess="+isSuccess +", exception="+exception;
    }
}

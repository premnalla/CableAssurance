/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.ex;

import javax.oss.*;

import java.util.Map;
import java.util.Date;


/**
  * String constants to indicate which optional 
  * parts of the interface are supported by the implementation. <p>
  * If a string METHOD is returned by {@link JVTActivationSession#getSupportedOptionalOperations},
  * a client can call the corresponding method.
  * Otherwise, {@link javax.oss.UnsupportedOperationException} is thrown. <p>
  * Note:
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
  * 
  */
public class JVTActivationOption {

    /* Returns the toString() of the method with name <code>methodName</code>
     * and arguments <code>args</code>.
     */
    private static String initConstant(String methodName, Class[] args) {
        String result = null;
        try {
            java.lang.reflect.Method m =
                JVTActivationSession.class.getMethod(methodName, args);
            if (m!=null) {
                result = m.toString(); 
            }
        } catch (NoSuchMethodException e) {
            // nothing, return null in this case
        }
        return result;
    }
    
    
    
    
    /**
      * Indictates that {@link JVTActivationSession#removeOrderByKey} 
      * is implemented.
      */
    public static final String REMOVE_MANAGEDENTITY =
        initConstant(
            "removeManagedEntityByKey",
            new Class[] { ManagedEntityExKey.class }
        );

   
    
}


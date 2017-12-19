package com.nokia.oss.ossj.common.ri.ex;

import javax.oss.*;
import com.nokia.oss.ossj.common.ri.*;

import java.util.Map;
import java.util.Date;


/**
  * String constants to indicate which optional 
  * parts of the interface are supported by the implementation. <p>
  * If a string METHOD is returned by {@link JVTActivationSession#getSupportedOptionalOperations},
  * a client can call the corresponding method.
  * Otherwise, {@link javax.oss.UnsupportedOperationException} is thrown. <p>
  * Note:
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


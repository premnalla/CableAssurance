/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common;


/**
 * 
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.2
 * @since March 2005
 */
public class ManagedEntityKeyResultImpl extends java.lang.Object
    implements javax.oss.ManagedEntityKeyResult {
    /** Holds value of property managedEntityKey. */
    private javax.oss.ManagedEntityKey managedEntityKey = null;

    /** Holds value of property success. */
    private boolean success = false;

    /** Holds value of property exception. */
    private Exception exception = null;

    /** Constructs new ManagedEntityKeyResultImpl */
    public ManagedEntityKeyResultImpl() {
    }

    /** Getter for property managedEntityKey.
     * @return Value of property managedEntityKey.
    */
    public javax.oss.ManagedEntityKey getManagedEntityKey() {
        return managedEntityKey;
    }

    /** Setter for property managedEntityKey.
     * @param managedEntityKey New value of property managedEntityKey.
    */
    public void setManagedEntityKey(javax.oss.ManagedEntityKey managedEntityKey) {
        this.managedEntityKey = managedEntityKey;
    }

    /** Getter for property success.
     * @return Value of property success.
    */
    public boolean isSuccess() {
        return success;
    }

    /** Setter for property success.
     * @param success New value of property success.
    */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /** Getter for property exception.
     * @return Value of property exception.
    */
    public Exception getException() {
        return exception;
    }

    /** Setter for property exception.
     * @param exception New value of property exception.
    */
    public void setException(Exception exception) {
        this.exception = exception;
    }

    /**
     * String representation of the KeyResult
     *
     * @return the string representing the KeyResult
     */
    public String toString() {
        return "KeyResult: key="
        + ((managedEntityKey == null) ? "null" : managedEntityKey.toString()) + ", success="
        + isSuccess() + ", Exception:" + ((exception == null) ? "null" : exception.toString());
    }
}

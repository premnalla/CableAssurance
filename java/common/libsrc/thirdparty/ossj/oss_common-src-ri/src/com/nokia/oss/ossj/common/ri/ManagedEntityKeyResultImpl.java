
package com.nokia.oss.ossj.common.ri;

/**
 *
 * @author BanuPrasad Dhanakoti Nokia Networks
 * @version 1.1
 * January 2002
 */
public class ManagedEntityKeyResultImpl extends java.lang.Object implements javax.oss.ManagedEntityKeyResult {

    /** Holds value of property managedEntityKey. */
    private javax.oss.ManagedEntityKey managedEntityKey;

    /** Holds value of property success. */
    private boolean success;
    
    /** Holds value of property exception. */
    private Exception exception;
    
    /** Creates new ManagedEntityKeyResultImpl */
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
    
    public String toString() {
        return "KeyResult: key="+(managedEntityKey==null?"null":managedEntityKey.toString())+
                ", success="+isSuccess()+
                ", Exception:"+(exception==null?"null":exception.toString());
    }
}


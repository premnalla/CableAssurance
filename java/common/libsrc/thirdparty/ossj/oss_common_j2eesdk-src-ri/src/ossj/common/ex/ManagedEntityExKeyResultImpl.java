/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.ex;

import javax.oss.*;
import ossj.common.ManagedEntityKeyResultImpl;

/**
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
 
public class ManagedEntityExKeyResultImpl 
extends ManagedEntityKeyResultImpl 
implements ManagedEntityExKeyResult {
    
    public ManagedEntityExKeyResultImpl() {
    }
    
    /** Creates new OrderKeyResultImpl */
    public ManagedEntityExKeyResultImpl(ManagedEntityExKey manageKey, boolean success, Exception exception) {
        setManagedEntityKey(manageKey);
        setSuccess(success);
        setException(exception);
    }

    public ManagedEntityExKey getManagedEntityExKey() {
        return (ManagedEntityExKey)getManagedEntityKey();
    }
    
    public void setOrderKey(ManagedEntityExKey newKey) {
        setManagedEntityKey(newKey);
    }
}


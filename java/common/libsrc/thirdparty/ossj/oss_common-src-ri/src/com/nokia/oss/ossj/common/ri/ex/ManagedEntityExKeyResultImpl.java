

package com.nokia.oss.ossj.common.ri.ex;

import javax.oss.*;
import com.nokia.oss.ossj.common.ri.*;

/**
 *
 * @author  BanuPrasad 
 * @version 
 */
 
public class ManagedEntityExKeyResultImpl extends ManagedEntityKeyResultImpl implements ManagedEntityExKeyResult {
    
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


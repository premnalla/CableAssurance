
package com.nokia.oss.ossj.common.ri.ex;

import com.nokia.oss.ossj.common.ri.BaseEvent;


public class ManagedEntityCreateEvent extends BaseEvent {

    private javax.oss.ManagedEntityValue value;
    
    public ManagedEntityCreateEvent() {
		this(null,null) ;
	}
    public ManagedEntityCreateEvent(String domain, javax.oss.ManagedEntityValue value) {
        super(domain);
        this.value = value;
    }
    public javax.oss.ManagedEntityValue getManagedEntityValue() {
        return value;
     }
}

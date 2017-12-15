/*
 *  Copyright 1999-2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.jk.status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter Rossbach
 * @version $Revision: 1.1 $ $Date: 2007/03/01 20:19:03 $
 * @see org.apache.jk.status.JkStatusParser
 */
public class JkStatus implements Serializable {

    JkServer server ;
    List balancers = new ArrayList() ;
    
    /**
     * @return Returns the balancers.
     */
    public List getBalancers() {
        return balancers;
    }
    /**
     * @param balancers The balancers to set.
     */
    public void setBalancers(List balancers) {
        this.balancers = balancers;
    }
    
    public void addBalancer(JkBalancer balancer) {
      balancers.add(balancer);
    }
    
    public void removeBalancer(JkBalancer balancer) {
      balancers.remove(balancer);
    }

    /**
     * @return Returns the server.
     */
    public JkServer getServer() {
        return server;
    }
    public void setServer(JkServer server) {
       this.server = server ;
    }
    
}

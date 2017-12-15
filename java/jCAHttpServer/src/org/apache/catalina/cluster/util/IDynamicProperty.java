/*
 * Copyright 1999,2004-2005 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.catalina.cluster.util;

import java.util.Iterator;

/**
 * @author Peter Rossbach
 * @version $Revision: 1.1 $, $Date: 2007/03/01 20:19:04 $
 */

public interface IDynamicProperty {

    /**
     * set config attributes with reflect
     * 
     * @param name
     * @param value
     */
    public void setProperty(String name, Object value) ;

    /**
     * get current config
     * 
     * @param key
     * @return The property
     */
    public Object getProperty(String key) ;
    /**
     * Get all properties keys
     * 
     * @return An iterator over the property names
     */
    public Iterator getPropertyNames() ;

    /**
     * remove a configured property.
     * 
     * @param key
     */
    public void removeProperty(String key) ;

}

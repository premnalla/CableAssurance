/*
 * Copyright 1999,2000,2004,2005 The Apache Software Foundation.
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
package org.apache.wml.dom;

import org.apache.wml.*;

/**
 * @xerces.internal
 * @version $Id: WMLPostfieldElementImpl.java,v 1.1.1.1 2006/04/05 02:30:19 prem Exp $
 * @author <a href="mailto:david@topware.com.tw">David Li</a>
 */
public class WMLPostfieldElementImpl extends WMLElementImpl implements WMLPostfieldElement {
    
    private static final long serialVersionUID = -1169432003991642980L;

    public WMLPostfieldElementImpl (WMLDocumentImpl owner, String tagName) {
        super( owner, tagName);
    }
    
    public void setValue(String newValue) {
        setAttribute("value", newValue);
    }
    
    public String getValue() {
        return getAttribute("value");
    }
    
    public void setClassName(String newValue) {
        setAttribute("class", newValue);
    }
    
    public String getClassName() {
        return getAttribute("class");
    }
    
    public void setId(String newValue) {
        setAttribute("id", newValue);
    }
    
    public String getId() {
        return getAttribute("id");
    }
    
    public void setName(String newValue) {
        setAttribute("name", newValue);
    }
    
    public String getName() {
        return getAttribute("name");
    }
    
}
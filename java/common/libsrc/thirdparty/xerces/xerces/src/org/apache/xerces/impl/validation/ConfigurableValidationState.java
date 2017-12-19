/*
 * Copyright 2006 The Apache Software Foundation.
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

package org.apache.xerces.impl.validation;

/**
 * <p>An extension of ValidationState which can be configured to turn 
 * off checking for ID/IDREF errors and unparsed entity errors.</p>
 * 
 * @xerces.internal
 *
 * @author Peter McCracken, IBM
 * @version $Id: ConfigurableValidationState.java,v 1.1.1.1 2006/04/05 02:30:20 prem Exp $
 */
public final class ConfigurableValidationState extends ValidationState {
    
    /**
     * Whether to check for ID/IDREF errors
     */
    private boolean fIdIdrefChecking;
    
    /**
     * Whether to check for unparsed entity errors
     */
    private boolean fUnparsedEntityChecking;
    
    /**
     * Creates a new ConfigurableValidationState.
     * By default, error checking for both ID/IDREFs 
     * and unparsed entities are turned on.
     */
    public ConfigurableValidationState() {
        super();
        fIdIdrefChecking = true;
        fUnparsedEntityChecking = true;
    }
    
    /**
     * Turns checking for ID/IDREF errors on and off.
     * @param setting: true to turn on error checking
     *                 false to turn off error checking
     */
    public void setIdIdrefChecking(boolean setting) {
        fIdIdrefChecking = setting;
    }
    
    /**
     * Turns checking for unparsed entity errors on and off.
     * @param setting: true to turn on error checking
     *                 false to turn off error checking
     */
    public void setUnparsedEntityChecking(boolean setting) {
        fUnparsedEntityChecking = setting;
    }
    
    /**
     * Checks if all IDREFs have a corresponding ID.
     * @return null, if ID/IDREF checking is turned off
     *         otherwise, returns the value of the super implementation
     */
    public String checkIDRefID() {
        return (fIdIdrefChecking) ? super.checkIDRefID() : null;
    }
    
    /**
     * Checks if an ID has already been declared.
     * @return false, if ID/IDREF checking is turned off
     *         otherwise, returns the value of the super implementation
     */
    public boolean isIdDeclared(String name) {
        return (fIdIdrefChecking) ? super.isIdDeclared(name) : false;
    }
    
    /**
     * Checks if an entity is declared.
     * @return true, if unparsed entity checking is turned off
     *         otherwise, returns the value of the super implementation
     */
    public boolean isEntityDeclared(String name) {
        return (fUnparsedEntityChecking) ? super.isEntityDeclared(name) : true;
    }
    
    /**
     * Checks if an entity is unparsed.
     * @return true, if unparsed entity checking is turned off
     *         otherwise, returns the value of the super implementation
     */
    public boolean isEntityUnparsed(String name) {
        return (fUnparsedEntityChecking) ? super.isEntityUnparsed(name) : true;
    }
    
    /**
     * Adds the ID, if ID/IDREF checking is enabled.
     * @param the ID to add
     */
    public void addId(String name) {
        if (fIdIdrefChecking) {
            super.addId(name);
        }
    }
    
    /**
     * Adds the IDREF, if ID/IDREF checking is enabled.
     * @param the IDREF to add
     */
    public void addIdRef(String name) {
        if (fIdIdrefChecking) {
            super.addIdRef(name);
        }
    }
}

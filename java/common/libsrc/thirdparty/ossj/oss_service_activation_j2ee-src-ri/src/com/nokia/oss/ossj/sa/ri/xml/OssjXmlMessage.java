/*
 * Copyright (c) 2002
 * Cisco Systems, Inc., Ericsson Radio Systems AB.,
 * Motorola, Inc., NEC Corporation, Nokia Networks Oy,
 * Nortel Networks Limited, Sun Microsystems, Inc.,
 * Telcordia Technologies, Inc., Cygent, Inc.,
 * Agilent Technologies, Inc., BEA Systems, Inc.,
 * Digital Fairway Corporation, Orchestream Holdings plc.
 *
 * All rights reserved.  Use is subject to license terms.
 *
 * DOCUMENTATION IS PROVIDED "AS IS" AND ALL EXPRESS OR IMPLIED
 * CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE DISCLAIMED, EXCEPT
 * TO THE EXTENT THAT SUCH DISCLAIMERS ARE HELD TO BE LEGALLY
 * INVALID.
 */
package com.nokia.oss.ossj.sa.ri.xml;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
//VP public class OssjXmlMessage extends Object {
public class OssjXmlMessage extends Object implements java.io.Serializable {
    
    public static final String REQUEST_TYPE = "REQUEST";
    public static final String RESPONSE_TYPE = "RESPONSE";
    public static final String EXCEPTION_TYPE = "EXCEPTION";
    
    private String message;
    private String type;
    private String name;
    
    /** Holds value of property id. */
    private String id;
    
    public OssjXmlMessage() {
	//VP
	message = new String();
	// end VP
    }
    
    public OssjXmlMessage(String aMessage, String aType, String aName, String anId) {
        message = aMessage;
        type = aType;
        name = aName;
        id = anId;
    }
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String aMessage) {
        message = aMessage;
    }
    public String getType() {
        return type;
    }
    public void setType(String aType) {
        type = aType;
    }
    public String getName() {
        return name;
    }
    public void setName(String aName) {
        name = aName;
    }
    public String toString() {
        return message;
    }
    
    /** Getter for property id.
     * @return Value of property id.
 */
    public String getId() {
        return id;
    }
    
    /** Setter for property id.
     * @param id New value of property id.
 */
    public void setId(String id) {
        this.id = id;
    }

    //VP
    public int hashCode(){
        return message.hashCode();
    }
    
    public boolean equals(Object o){
        if (o instanceof OssjXmlMessage){
            OssjXmlMessage mes = (OssjXmlMessage)o;
            if (mes.getMessage().equals(getMessage())
                && mes.getType().equals(getType())
                && mes.getName().equals(getName()))
                return true;
        }
        return false;
        
    }
    //end VP
    
}

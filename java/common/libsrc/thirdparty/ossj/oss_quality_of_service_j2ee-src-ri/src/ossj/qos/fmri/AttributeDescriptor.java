package ossj.qos.fmri;

/**
 * AttributeDescriptorImpl
 * 
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public class AttributeDescriptor {

    private boolean settable = true;
    private boolean optional = false;
    private boolean supported = true;
    
    /** 
     * AttributeDescriptorImpl - default constructor 
     */
    public AttributeDescriptor()  {
    }
       
    /** 
     * AttributeDescriptorImpl - constructor 
     */
    public AttributeDescriptor(boolean settable,boolean optional,boolean supported)  {
                                        
        this.settable = settable;
        this.optional = optional;
        this.supported = supported;
    }
    
    public boolean isSettable() {
        return settable;
    }
    public boolean isSupported() {
        return supported;
    }
    public boolean isOptional() {
        return optional;
    }
    
}
package ossj.qos.fmri;

import java.util.*;

/**
 * AttributeManager
 * 
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
public class AttributeManager extends Object {
    
    AttributeManager parent = null;
    TreeMap attributeDescriptorMap = null;

    /** 
     * AttributeManager - default constructor 
     */
    public AttributeManager() {
    }

    public void setParent( AttributeManager manager ) {
        parent = manager;
        return;
    }
    
    public void loadDescriptors( TreeMap descriptors ) {
        attributeDescriptorMap = descriptors;
        return;
    }
    
    public TreeSet getAttributeNames() {
        TreeSet attributeNames = null;
        if ( parent != null ) {
            attributeNames = parent.getAttributeNames();
        }
        else {
            attributeNames = new TreeSet();
        }
        String[] names = (String[]) attributeDescriptorMap.keySet().toArray( new String[0] );
        for ( int i=0, len=names.length; i<len; i++ ) { 
            attributeNames.add( names[i] );
        }         
        return attributeNames;
    }
     
    public TreeSet getSupportedAttributes() {
        TreeSet attributes = null;
        if ( parent != null ) {
            attributes = parent.getSupportedAttributes();
        }
        else {
            attributes = new TreeSet();
        }
        Iterator iter = attributeDescriptorMap.keySet().iterator();
        while ( iter.hasNext() ) {
            String name = (String) iter.next();
            AttributeDescriptor descriptor = (AttributeDescriptor) attributeDescriptorMap.get( name );
            if ( descriptor.isSupported() ) {
                attributes.add( name );
            }
        }       
        return attributes;
    }
    
      public TreeSet getSupportedOptionalAttributes() {
        TreeSet attributes = null;
        if ( parent != null ) {
            attributes = parent.getSupportedOptionalAttributes();
        }
        else {
            attributes = new TreeSet();
        }
        Iterator iter = attributeDescriptorMap.keySet().iterator();
        while ( iter.hasNext() ) {
            String name = (String) iter.next();
            AttributeDescriptor descriptor = (AttributeDescriptor) attributeDescriptorMap.get( name );
            if ( descriptor.isOptional() && descriptor.isSupported() ) {
                attributes.add( name );
            }
        }       
        return attributes;
    }
    
     public TreeSet getSettableAttributes() {
        TreeSet attributes = null;
        if ( parent != null ) {
            attributes = parent.getSettableAttributes();
        }
        else {
            attributes = new TreeSet();
        }
        Iterator iter = attributeDescriptorMap.keySet().iterator();
        while ( iter.hasNext() ) {
            String name = (String) iter.next();
            AttributeDescriptor descriptor = (AttributeDescriptor) attributeDescriptorMap.get( name );
            if ( descriptor.isSupported() && descriptor.isSettable() ) {
                attributes.add( name );
            }
        }       
        return attributes;
    }
    
    public boolean isSupportedAttribute( String attributeName ) {
        boolean isSupported = false;
        AttributeDescriptor descriptor = (AttributeDescriptor)attributeDescriptorMap.get( attributeName ); 
        if ( descriptor != null ) {
            isSupported = descriptor.isSupported();
        }
        else {
            if ( parent != null ) {
                isSupported = parent.isSupportedAttribute( attributeName );
            }
        }       
        return isSupported;
    }
    
    public boolean isSettableAttribute( String attributeName ) {
        boolean isSettable = false;
        AttributeDescriptor descriptor = (AttributeDescriptor)attributeDescriptorMap.get( attributeName ); 
        if ( descriptor != null ) {
            isSettable = descriptor.isSettable();
        }
        else {
            if ( parent != null ) {
                isSettable = parent.isSettableAttribute( attributeName );
            }
        }       
        return isSettable;
    }
    
    public int getNumberOfSupportedAttributes() {
        int numberOfAttributes = 0;
        if ( parent != null ) {
            numberOfAttributes = numberOfAttributes + parent.getNumberOfSupportedAttributes();
        }
        Iterator iter = attributeDescriptorMap.keySet().iterator();
        while ( iter.hasNext() ) {
            String name = (String) iter.next();
            AttributeDescriptor descriptor = (AttributeDescriptor) attributeDescriptorMap.get( name );
            if ( descriptor.isSupported() ) {
                numberOfAttributes++;
            }
        }       
        return numberOfAttributes;
    }
    
    public AttributeDescriptor getAttributeDescriptor( String attributeName ) {
        AttributeDescriptor descriptor = null;
        descriptor = (AttributeDescriptor)attributeDescriptorMap.get( attributeName ); 
        if ( descriptor == null && parent != null ) {
            descriptor = parent.getAttributeDescriptor( attributeName );
        }       
        return descriptor;
    }
    
}
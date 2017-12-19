/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common;

import java.util.*;
import javax.oss.*;



/**
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */

public class AttributeManager implements java.io.Serializable {

    private Map allAttributes;
    private BitSet settableAttributes;
    private BitSet supportedOptionalAttributes;
    private int attributeCounter;
    private boolean attributesLocked;
    
    public BitSet EMPTY_BITSET;
    public BitSet SET_BITSET;
    
    /**
     * Creates a new AttributeManager instance with no attribute
     *
     */
    public AttributeManager() {
        allAttributes = new TreeMap();
        attributeCounter = 0;
        attributesLocked = false;
    }
    
    /**
     * Add array of attributes
     * @param newAttributes to add to the manager
     * @exception java.lang.IllegalArgumentException
     * (the attribute name is null or the class is already locked)
     */
    public void addAttributes(String[] newAttributes) {
        if (attributesLocked) {
            throw new java.lang.IllegalArgumentException("Attributes are already locked. Call this method before lock() is called.");
        }
        if (newAttributes == null) {
            throw new java.lang.IllegalArgumentException("null is not a valid attributes array");
        }
         
        for (int i=0 ; i<newAttributes.length ; i++) {
            // add null first, the position is inserted afterwards
            allAttributes.put(newAttributes[i], null);
        }
    }
    
    /**
     * Lock the class
     *
     */
    public void lock() {
        attributesLocked = true;
        
        // entry set returs entries in ascending order for a TreeMap
        Iterator entrySetIterator = allAttributes.entrySet().iterator();
        while (entrySetIterator.hasNext()) {
            // assign positions in BitSets to attributes
            ((Map.Entry)entrySetIterator.next()).setValue(new Integer(attributeCounter));
            attributeCounter++;
        }
        
        EMPTY_BITSET = new BitSet(attributeCounter);
        SET_BITSET = new BitSet(attributeCounter);
        for (int i=0 ; i<attributeCounter ; i++) {
            SET_BITSET.set(i);
        }
        settableAttributes = new BitSet(attributeCounter);
        supportedOptionalAttributes = new BitSet(attributeCounter);
    }
    
    /**
     * Set the supported optional attributes List
     * @param newAttributes
     */
    public void setSupportedOptionalAttributes(String[] newAttributes) {
        if (!attributesLocked) {
            throw new java.lang.IllegalArgumentException("Attributes are not locked. Call this method after lock() was called.");
        }
        if (newAttributes == null) {
            throw new java.lang.IllegalArgumentException("null is not a valid attributes array");
        }
       for (int i=0 ; i<newAttributes.length ; i++) {
            supportedOptionalAttributes.set(getPosition(newAttributes[i]));
        }
    }
    
    /**
     * Set the list of writtable attributes
     * @param newAttributes
     */
    public void setSettableAttributes(String[] newAttributes) {
        if (!attributesLocked) {
            throw new java.lang.IllegalArgumentException("Attributes are not locked. Call this method after lock() was called.");
        }
        if (newAttributes == null) {
            throw new java.lang.IllegalArgumentException("null is not a valid attributes array");
        }
        for (int i=0 ; i<newAttributes.length ; i++) {
            settableAttributes.set(getPosition(newAttributes[i]));
        }
    }

    /**
     * Returns the position of the given attribute, or 0 if the attribute is null or not recognized
     * Returns the position to which this map maps the specified attribute, 
     * or 0 if the map contains no mapping for the attribute.
     * @param attribute
     * @return 
     */
    public int getPosition(String attribute) {
    	if (attribute == null || allAttributes.get(attribute) == null) 
    		return 0;
    	else
    		return ((Integer)allAttributes.get(attribute)).intValue();
    }
    
    /**
     * Returns the number of attributes
     * @return
     */
    public int getAttributeCount() {
        return attributeCounter;
    }

    /**
     *returns an iterator which returns the attributes in ascending order
     */
    public Iterator getAttributeIterator() {
        return allAttributes.keySet().iterator();
    }
    
    /**
     * Returns all the attribute names
     * @return
     */
    public String[] getAttributeNames() {
        String[] attributes = (String[])allAttributes.keySet().toArray(new String[0]);
        Arrays.sort(attributes);
        return attributes;
    }
    /**
     * Returns the array of aupported optional attribute names.
     * @return
     */
    public String[] getSupportedOptionalAttributeNames() {
        Iterator attributeIterator = getAttributeIterator();
        List settableList = new ArrayList();
        String attribute;
        int bit = 0;
        while (attributeIterator.hasNext()) {
            attribute = (String)attributeIterator.next();
            if (supportedOptionalAttributes.get(bit)) {
                settableList.add(attribute);
            }
            bit++;
        }
        return (String[])settableList.toArray(new String[0]);
    }
    /**
     * Returns the array of writtable attribute names.
     * @return
     */
    public String[] getSettableAttributeNames() {
        Iterator attributeIterator = getAttributeIterator();
        List settableList = new ArrayList();
        String attribute;
        int bit = 0;
        while (attributeIterator.hasNext()) {
            attribute = (String)attributeIterator.next();
            if (settableAttributes.get(bit)) {
                settableList.add(attribute);
            }
            bit++;
        }
        return (String[])settableList.toArray(new String[0]);
    }
}


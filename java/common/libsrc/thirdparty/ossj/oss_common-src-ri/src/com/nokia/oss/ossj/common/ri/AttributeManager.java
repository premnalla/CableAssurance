package com.nokia.oss.ossj.common.ri;
import java.util.*;
import javax.oss.*;



/*
 * @author BanuPrasad Dhanakoti Nokia Networks
 * @version 1.1
 * January 2002
 */

public class AttributeManager implements java.io.Serializable {

    private Map allAttributes;
    private BitSet settableAttributes;
    private BitSet supportedOptionalAttributes;
    private int attributeCounter;
    private boolean attributesLocked;
    
    public BitSet EMPTY_BITSET;
    public BitSet SET_BITSET;
    
    
    public AttributeManager() {
        allAttributes = new TreeMap();
        attributeCounter = 0;
        attributesLocked = false;
    }
    
    public void addAttributes(String[] newAttributes) {
        if (attributesLocked) {
            throw new java.lang.IllegalArgumentException("Attributes are already locked. Call this method before lock() is called.");
        }
        for (int i=0 ; i<newAttributes.length ; i++) {
            // add null first, the position is inserted afterwards
            allAttributes.put(newAttributes[i], null);
        }
    }
    
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
    
    public void setSupportedOptionalAttributes(String[] newAttributes) {
        if (!attributesLocked) {
            throw new java.lang.IllegalArgumentException("Attributes are not locked. Call this method after lock() was called.");
        }
        for (int i=0 ; i<newAttributes.length ; i++) {
            supportedOptionalAttributes.set(getPosition(newAttributes[i]));
        }
    }
    
    public void setSettableAttributes(String[] newAttributes) {
        if (!attributesLocked) {
            throw new java.lang.IllegalArgumentException("Attributes are not locked. Call this method after lock() was called.");
        }
        for (int i=0 ; i<newAttributes.length ; i++) {
            settableAttributes.set(getPosition(newAttributes[i]));
        }
    }
    
    public int getPosition(String attribute) {
        return ((Integer)allAttributes.get(attribute)).intValue();
    }
    
    public int getAttributeCount() {
        return attributeCounter;
    }

    /**
     *returns an iterator which returns the attributes in ascending order
     */
    public Iterator getAttributeIterator() {
        return allAttributes.keySet().iterator();
    }
    
    public String[] getAttributeNames() {
        String[] attributes = (String[])allAttributes.keySet().toArray(new String[0]);
        Arrays.sort(attributes);
        return attributes;
    }

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


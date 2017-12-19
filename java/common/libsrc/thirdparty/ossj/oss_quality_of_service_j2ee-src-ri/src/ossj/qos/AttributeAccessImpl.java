/*
 * Copyright © 2001 Ericsson Radio Systems AB.
 * All rights reserved. Use is subject to license terms.
 */
package ossj.qos;

import javax.oss.AttributeAccess;
import javax.oss.Serializer;
import javax.oss.SerializerFactory;
import java.util.*;
import ossj.qos.util.Log;


/**
 * Implemtation of AttributeAccess. Uses a HashMap to store the attributes.
 * The class has generic methods for setting arbitrary attributes. As long as
 * all attributes are initialized to null in the constructor (of any sub-class)
 * all methods in this class are generic and do not have to be overridden. Like
 * for instance the isFullyPopulated() method. Note, clone() need to be
 * overriden if new class members are added that need "deep-copy".
 *
 * @author Henrik Lindstrom, Ericsson
 */
public abstract class AttributeAccessImpl implements AttributeAccess, SerializerFactory {
    /**
     * Error message for illegal arguments.
     */
    protected static final String ERR_ILLEGAL_ARGUMENT =
        "An illegal argument was found. Might be null or not valid.";

    /**
     * Error message for illegal state.
     */
    protected static final String ERR_ILLEGAL_STATE =
        "Illegal state for attribute(s). At least one attribute have an illegal state.";

    /**
     * Contains all attributes.
     */
    protected HashMap map = null;

    /**
     * Creates a new attribute acccess. It is required that a subclass
     * initialize all legal attributes in the map to null values in the
     * constructor. Otherwise they can not be populated later.
     */
    public AttributeAccessImpl() {
        map = new HashMap();
    }

    /**
     * Validate the value for an attribute. Method should return true if the
     * attribute name and the attribute value are valid, otherwise false.
     *
     * @param attrName the name of the attribute
     * @param attrValue the value for the attribute
     * @return true if the attribute name and the value are valid.
     */
  	protected abstract boolean isValidAttribute(String attrName, Object attrValue );

    // clone this instance
    public Object clone() {
        AttributeAccessImpl clone = null;
        try {
            clone = (AttributeAccessImpl) super.clone();
            clone.map = (HashMap) map.clone();
        } catch ( CloneNotSupportedException e ) {
            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e); // "should not happen"
        }
        return clone;
    }

    public Map getAttributeValues(String[] attributeNames)
        throws IllegalArgumentException, IllegalStateException {

        if ( attributeNames==null || attributeNames.length==0 ) {
            Log.write(this,Log.LOG_MINOR,"getAttributeValues()",ERR_ILLEGAL_ARGUMENT);
            throw new IllegalArgumentException( ERR_ILLEGAL_ARGUMENT );
        }

        HashMap attribValues = new HashMap();
        Object value = null;
        for (int i=0;i<attributeNames.length;i++) {

            // check that current argumentName is legal
            if ( map.containsKey( attributeNames[i] ) == false ) {
                Log.write(this,Log.LOG_MINOR,"getAttributeValues()",
                    ERR_ILLEGAL_ARGUMENT + " [" + attributeNames[i] + "]" );
                throw new IllegalArgumentException( ERR_ILLEGAL_ARGUMENT );
            }

            value = map.get( attributeNames[i] );
            if ( value != null ) {
                attribValues.put( attributeNames[i], value );
            } else {
                // unpopulated attribute found, throw exception.
                Log.write(this,Log.LOG_MINOR,"getAttributeValues()",ERR_ILLEGAL_STATE);
                throw new IllegalStateException( ERR_ILLEGAL_STATE );
            }
        }
        return attribValues;
    }

    public void setAttributeValues(Map attributeNamesAndValuePairs)
        throws IllegalArgumentException, IllegalStateException {

        if ( attributeNamesAndValuePairs == null
          || attributeNamesAndValuePairs.isEmpty() ) {
            Log.write(this,Log.LOG_MINOR,"setAttributeValues()",ERR_ILLEGAL_ARGUMENT);
            throw new IllegalArgumentException( ERR_ILLEGAL_ARGUMENT );
        }

        // Check that all attributes in map are populated.
        Set keySet = attributeNamesAndValuePairs.keySet();
        Iterator iter = keySet.iterator();
        Object value = null;
        String attributeName = null;
        while ( iter.hasNext() ) {
            attributeName = (String)iter.next();
            value = attributeNamesAndValuePairs.get( attributeName );
            if ( value == null ) {
                Log.write(this,Log.LOG_MINOR,"setAttributeValues()",ERR_ILLEGAL_STATE);
                throw new IllegalStateException( ERR_ILLEGAL_STATE );
            } else if ( this.isValidAttribute(attributeName, value )==false ) {
                Log.write(this,Log.LOG_MINOR,"setAttributeValues()",
                    ERR_ILLEGAL_ARGUMENT + " ["+attributeName+"="+value+"]" );
                throw new IllegalArgumentException( ERR_ILLEGAL_ARGUMENT );
            } else if ( map.containsKey( attributeName )==false ) {
                // extra check to ensure that no illegal arguments are added
                Log.write(this,Log.LOG_MINOR,"setAttributeValues()",
                    ERR_ILLEGAL_ARGUMENT + " [" + attributeName + "]" );
                throw new IllegalArgumentException( ERR_ILLEGAL_ARGUMENT );
            }
        }

        // Copies all of the mappings from the specified map to this one.
        // These mappings replace any mappings that this map had for any of the
        // keys currently in the specified Map.
        map.putAll( attributeNamesAndValuePairs );
    }

    public Map getAllPopulatedAttributes() throws IllegalStateException {
        Set entrySet = map.entrySet();
        HashMap populatedMap = new HashMap();
        if ( entrySet.size() == 0 ) { // no populated attributes
            return populatedMap;
        }
        Iterator iter = entrySet.iterator();
        Map.Entry entry = null;
        while ( iter.hasNext() ) {
            entry = (Map.Entry)iter.next();
            if ( entry.getValue() != null ) {
                populatedMap.put(entry.getKey(),entry.getValue());
            }
        }
        return populatedMap; // return all populated attributes
    }

    public Object getAttributeValue(String attributeName)
        throws IllegalArgumentException, IllegalStateException {

        if ( attributeName == null || map.containsKey( attributeName )==false ) { // illegal argument
            Log.write(this,Log.LOG_MINOR,"getAttributeValue()",ERR_ILLEGAL_ARGUMENT
                + " [" + attributeName + "]");
            throw new IllegalArgumentException( ERR_ILLEGAL_ARGUMENT );
        }

        Object obj = map.get( attributeName );
        if ( obj == null ) { // attribute not populated
            Log.write(this,Log.LOG_MINOR,"getAttributeValue()",ERR_ILLEGAL_STATE);
            throw new IllegalStateException( ERR_ILLEGAL_STATE );
        }
        return obj;
    }

    public void setAttributeValue( String attributeName, Object value)
        throws IllegalArgumentException {

        if ( this.isValidAttribute( attributeName, value )==false ) {
            Log.write(this,Log.LOG_MINOR,"setAttributeValue()",ERR_ILLEGAL_ARGUMENT);
            throw new IllegalArgumentException( ERR_ILLEGAL_ARGUMENT );
        }
        map.put( attributeName, value );
    }

    public String[] getAttributeNames() {
        return this.getKeyArray( map );
    }

    public String[] getPopulatedAttributeNames() {
        Map populatedMap = this.getAllPopulatedAttributes();
        return this.getKeyArray( populatedMap );
    }

    public boolean isPopulated(String name) throws IllegalArgumentException {
        if ( name == null || map.containsKey( name )==false ) {
            Log.write(this,Log.LOG_MINOR,"isPopulated()",ERR_ILLEGAL_ARGUMENT
                + " [" + name + "]" );
            throw new IllegalArgumentException( ERR_ILLEGAL_ARGUMENT );
        }

        if ( map.get( name ) == null ) { // does not exist or value is null
            return false;
        }
        return true;
    }

    public boolean isFullyPopulated() {
        if ( this.getAllPopulatedAttributes().size() == map.size() ) {
            return true;
        }
        return false;
    }

    public void unpopulateAllAttributes() {
        // Clear all values in map. All keys will remain.
        Set keys = map.keySet();
        Iterator iter = keys.iterator();
        while ( iter.hasNext() ) {
            map.put( iter.next(), null ); // clear value for key
        }
    }

    public void unpopulateAttribute(String attr_name) throws IllegalArgumentException {
        if ( attr_name == null ||  map.containsKey( attr_name ) == false ) {
            Log.write(this,Log.LOG_MINOR,"unpopulateAttribute()",
                ERR_ILLEGAL_ARGUMENT + " [" + attr_name + "]" );
            throw new IllegalArgumentException( ERR_ILLEGAL_ARGUMENT );
        }

        Object value = map.get( attr_name );
        if ( value==null ) {
            // Maybe an exception should be thrown here, but that is not stated
            // in specification.
        }
        map.put( attr_name, null ); // unpopulate attribute
    }

    public String[] getSupportedOptionalAttributeNames() {
      return new String[0];
    }

    public String[] getSupportedSerializerTypes() {
      return new String[0];
    }

    public Serializer makeSerializer( String serializerType )
                          throws java.lang.IllegalArgumentException {
        throw new IllegalArgumentException( "NOT IMPLEMENTED/SUPPORTED" );

    }

    /**
     * Returns all the keys in a map.
     * @param aMap map to return keys for
     * @return array with all String keys in the map
     */
    private String[] getKeyArray(Map aMap) {
        Set keys = aMap.keySet();
        String[] keysArray = new String[ keys.size() ];
        keysArray = (String[]) keys.toArray( keysArray );
        return keysArray;
    }
}

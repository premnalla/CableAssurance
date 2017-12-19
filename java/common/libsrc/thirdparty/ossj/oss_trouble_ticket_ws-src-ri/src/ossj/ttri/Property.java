package ossj.ttri;


/**
 * Property Class
 * Simple property class for sending generic properties to bulk operations
 * and queries.
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
class Property implements java.io.Serializable {
    int PropertyType;       //enumerated type PropertyTypes
    String PropertyName;

    //single holder for all types.  Primitives are stored as classes
    Object PropertyValue;

    //ctor
    public Property(String propertyName) {
        PropertyName = propertyName;
    }

    //mutators
    public void setPropertyName(String propName) {
        PropertyName = propName;
    }

    public void setStringProperty(String value) {
        PropertyValue = value;
        PropertyType = PropertyTypes.STRING;
    }

    public void setIntProperty(int value) {
        PropertyValue = new Integer(value);
        PropertyType = PropertyTypes.INT;
    }

    public void setBooleanProperty(boolean value) {
        PropertyValue = new Boolean(value);
        PropertyType = PropertyTypes.BOOLEAN;
    }

    public void setObjectProperty(Object value) {
        PropertyValue = value;
        PropertyType = PropertyTypes.OBJECT;
    }

    //accessors
    public int getPropertyType() {
        return PropertyType;
    }

    public String getPropertyName() {
        return PropertyName;
    }

    public String getStringProperty() {
        return (String) PropertyValue;
    }

    public int getIntProperty() {
        return ((Integer) PropertyValue).intValue();
    }

    public boolean getBooleanProperty() {
        return ((Boolean) PropertyValue).booleanValue();
    }

    public Object getObjectProperty() {
        return PropertyValue;
    }


}



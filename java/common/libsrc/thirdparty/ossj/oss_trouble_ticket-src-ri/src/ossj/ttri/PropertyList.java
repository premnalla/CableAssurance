package ossj.ttri;


/**
 * PropertyList Helper Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
class PropertyList implements java.io.Serializable {

    java.util.Vector properties = new java.util.Vector();

    public void addProperty(Property prop) {
        properties.add(prop);
    }

    public Property getProperty(String propName) {
        Property retProp;
        for (int x = 0; x < properties.size(); x++) {
            retProp = (Property) properties.elementAt(x);
            String propertyName = retProp.getPropertyName();
            if (propertyName != null)
                if (propertyName.equals(propName))
                    return retProp;
        }

        return null;
    }

}

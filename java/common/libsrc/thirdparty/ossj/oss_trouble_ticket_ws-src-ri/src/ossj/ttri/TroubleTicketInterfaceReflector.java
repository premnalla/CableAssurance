package ossj.ttri;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;

/**
 * TroubleTicketInterfaceReflector Class
 * Provides mapping of enumerated types to their String equivalents
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class TroubleTicketInterfaceReflector implements java.io.Serializable {
    //----------------------------------------------------------------------
    // HashMap of HashMaps:
    //     Key: String interface name
    //   Value: HashMap i.e. the key is a String and the value is a HashMap
    //          The contained HashMap hold String to int maps for the actual
    //          enum values.
    //----------------------------------------------------------------------
    private HashMap populatedInterfacesMap;

    //ctor
    public TroubleTicketInterfaceReflector() {
        populatedInterfacesMap = new HashMap();
    }

    public Object getInterfaceFieldValue(Class interfaceClass,
                                         String fieldName)
            throws javax.oss.IllegalArgumentException,
            java.lang.NoSuchFieldException,
            java.lang.IllegalAccessException {
        String interfaceName = interfaceClass.getName();
        //System.out.println("InterfaceName: " + interfaceName );

        HashMap interfaceMap = null;
        if (!isPopulated(interfaceName)) {
            interfaceMap = populateInterfaceMap(interfaceClass);
        } else {
            interfaceMap = (HashMap) populatedInterfacesMap.get(interfaceName);
        }
        if (!interfaceMap.containsKey(fieldName)) {
            throw new javax.oss.IllegalArgumentException
                    ("Passed in field Name does not exist: " + fieldName);
        }

        return interfaceMap.get(fieldName);

    }

    //------------------------------------------------------------
    // Expose an "int" signature so clients don't have to convert
    // to Integer for enumerated types
    //------------------------------------------------------------
    public String getInterfaceFieldName(Class interfaceClass,
                                        int fieldValue)
            throws javax.oss.IllegalArgumentException,
            java.lang.IllegalAccessException {
        Integer intFieldVal = new Integer(fieldValue);
        return getInterfaceFieldName(interfaceClass, intFieldVal);
    }


    public String getInterfaceFieldName(Class interfaceClass,
                                        Object fieldValue)
            throws javax.oss.IllegalArgumentException,
            java.lang.IllegalAccessException {
        String interfaceName = interfaceClass.getName();
        //System.out.println("InterfaceName: " + interfaceName );

        HashMap interfaceMap = null;
        if (!isPopulated(interfaceName)) {
            interfaceMap = populateInterfaceMap(interfaceClass);
        } else {
            interfaceMap = (HashMap) populatedInterfacesMap.get(interfaceName);
        }

        if (!interfaceMap.containsValue(fieldValue)) {
            throw new javax.oss.IllegalArgumentException("Passed in field Value does not exist: " + fieldValue.toString());
        }


        String currKey = null;
        for (Iterator iter = interfaceMap.keySet().iterator(); iter.hasNext();) {
            currKey = (String) iter.next();

            if (interfaceMap.get(currKey).equals(fieldValue)) {
                break;
            }
        }

        return currKey;
    }

    public String getEnumFieldName(Class interfaceClass, int enumValue) {

        String val = null;

        try {
            val = getInterfaceFieldName(interfaceClass, enumValue);
        } catch (javax.oss.IllegalArgumentException iaex) {
            val = "UNDEFINED (-1)";
        } catch (java.lang.IllegalAccessException iacex) {
            val = "UNDEFINED (-1)";
        }

        return val;

    }


    //determines if the interface's map has been populated
    private boolean isPopulated(String interfaceName) {
        return populatedInterfacesMap.containsKey(interfaceName);
    }

    private HashMap populateInterfaceMap(Class interfaceClass)
            throws java.lang.IllegalAccessException {
        String interfaceName = interfaceClass.getName();

        //Populate the interface hashMap with field names of the i/f
        HashMap interfaceMap = new HashMap();
        Field[] publicFields = interfaceClass.getFields();
        for (int x = 0; x < publicFields.length; x++) {
            interfaceMap.put(publicFields[x].getName(), //key - the string name
                    publicFields[x].get(interfaceClass));   //value - the int value
        }

        //Add the interface name to the populated map
        populatedInterfacesMap.put(interfaceName, interfaceMap);

        return interfaceMap;
    }

}



/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common;

import java.sql.ResultSet;

import java.util.*;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageNotWriteableException;

import javax.oss.*;


/**
 * Utility Class
 *
 *
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public class Utils {
    /**
     * DOCUMENT ME!
     *
     * @param msg DOCUMENT ME!
     * @param properties DOCUMENT ME!
     */
    public static void setMessageProperties(Message msg, Hashtable properties) {
        Enumeration keys = properties.keys();
        String key = null;
        Object value = null;

        while (keys.hasMoreElements()) {
            Object okey = keys.nextElement();

            if (!(okey instanceof String)) {
                // Logger.log ("utils.setMessageProperties - Invalid property name");
            } else {
                key = (String) okey;
            }

            value = properties.get(key);

            try {
                if (value instanceof Integer) {
                    msg.setIntProperty(key, ((Integer) value).intValue());
                } else if (value instanceof Byte) {
                    msg.setByteProperty(key, ((Byte) value).byteValue());
                } else if (value instanceof Short) {
                    msg.setShortProperty(key, ((Short) value).shortValue());
                } else if (value instanceof Boolean) {
                    msg.setBooleanProperty(key, ((Boolean) value).booleanValue());
                } else if (value instanceof Double) {
                    msg.setDoubleProperty(key, ((Double) value).doubleValue());
                } else if (value instanceof Long) {
                    msg.setLongProperty(key, ((Long) value).longValue());
                } else if (value instanceof Float) {
                    msg.setFloatProperty(key, ((Float) value).floatValue());
                } else if (value instanceof String) {
                    msg.setStringProperty(key, (String) value);
                } else {
                    //  Logger.log("utils::setMessageProperty: unknown value type, property ignored");
                }
            } catch (MessageNotWriteableException mex) {
                //  Logger.log("utils::setMessageProperty: MessageNotWriteableException occured");
            } catch (JMSException jex) {
                // Logger.log("utils::setMessageProperty: JMSException occured");
            }
        }
    }

    //"match" methods used by equality operators for complex attributes.
    public static boolean matchString(String str1, String str2) {
        if ((str1 == null) && (str2 == null)) {
            return true;
        }

        if ((str1 != null) && (str2 != null)) {
            return str1.equals(str2);
        }

        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param date1 DOCUMENT ME!
     * @param date2 DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static boolean matchDate(java.util.Date date1, java.util.Date date2) {
        if ((date1 == null) && (date2 == null)) {
            return true;
        }

        if ((date1 != null) && (date2 != null)) {
            // Logger.log( "---date1---" + date1 );
            // Logger.log( "---date2---" + date2 );
            String date1String = new String(date1.toString());
            String date2String = new String(date2.toString());
            boolean val = date1String.equals(date2String);

            if (val == true) {
                // Logger.log( "---Dates are equal---" );
            } else {
                // Logger.log( "---Dates are not equal---" );
            }

            return val;
        }

        return false;
    }

    /*    public static boolean matchAddress(Address addr1, Address addr2)
        {
            if ( (addr1 == null) && (addr2 == null)) return true;
            if ( (addr1 != null) && (addr2 != null))
               return (addr1).equals(addr2);
            return false;
        }

    */
    public static boolean testEqualityOfArrays(Object[] ArrayArg1, Object[] ArrayArg2) {

        Collection col1 = Arrays.asList(ArrayArg1);
        Collection col2 = Arrays.asList(ArrayArg2);

        if (col1.equals(col2)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param ttVal1 DOCUMENT ME!
     * @param ttVal2 DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static boolean compareAttributeAccess(AttributeAccess ttVal1, AttributeAccess ttVal2) {
        String[] attrNamesForVal1 = ttVal1.getPopulatedAttributeNames();
        String[] attrNamesForVal2 = ttVal2.getPopulatedAttributeNames();
//        log("-------START COMPARE------------------");
//
//        log("Number of Populated Attributes in Value 1");
//        log(new Integer(attrNamesForVal1.length).toString());
//
//        log("Number of Populated Attributes in Value 2");
//
//        log(new Integer(attrNamesForVal2.length).toString());
//
//        if (attrNamesForVal1.length == attrNamesForVal2.length) {
//            log("Same number of Populated Attributes");
//        } else {
//            log("Different number of Populated Attributes");
//        }

        for (int i = 0; i < attrNamesForVal1.length; i++) {
            Object attributeValue1 = null;
            Object attributeValue2 = null;
            boolean att1isGet = false;
            boolean att2isGet = false;

            try {
//                log("-----Testing Equality for Attribute----->" + attrNamesForVal1[i]);

                att1isGet = true;
                attributeValue1 = ttVal1.getAttributeValue(attrNamesForVal1[i]);
                att1isGet = false;

//                if (attributeValue1 == null) {
//                    log("--Attribute Value 1 is NULL---");
//                    log(attrNamesForVal1[i]);
//                }
//
//                log(attrNamesForVal1[i]);

                att2isGet = true;
                attributeValue2 = ttVal2.getAttributeValue(attrNamesForVal1[i]);
                att2isGet = false;

                if (compareAttributes(attributeValue1, attributeValue2)) {
//                    log("Attribute are the same");
                } else {
//                    log("Attribute Values are not the same for: " + attrNamesForVal1[i]);

                    return false;
                }
            } catch (java.lang.IllegalArgumentException e1) {
//                log("IllegalArgumentException Exception getting attribute value on");
//
//                if (att2isGet) {
//                    log("On Value 2 ");
//                } else {
//                    log("On Value 1");
//                }
//
//                log(attrNamesForVal1[i]);

                String failure = "IllegalArgumentException Exception getting attribute value on"
                    + attrNamesForVal1[i];

                //if( failMode ) fail(failure);
                return false;
            } catch (java.lang.IllegalStateException e2) {
//                log("IllegalStateException Exception getting attribute value on");
//
//                if (att2isGet) {
//                    log("On Value 2 ");
//                } else {
//                    log("On Value 1");
//                }
//
//                log(attrNamesForVal1[i]);

                String failure = "IllegalStateException Exception getting attribute value on"
                    + attrNamesForVal1[i];

                //if( failMode ) fail(failure);
                return false;
            } catch (Exception e) {
//                log("System Exception getting attribute value on");
//                log("Exception is: " + e.getMessage());
                e.printStackTrace();

//                if (att2isGet) {
//                    log("On Value 2 ");
//                } else {
//                    log("On Value 1");
//                }
//
//                log(attrNamesForVal1[i]);
//                log(e.toString());
//
//                String failure = "System Exception getting attribute value on"
//                    + attrNamesForVal1[i];
//                log("SYSTEM EXCEPTION" + failure);

                return false;
            }
        }

        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param ttVal1 DOCUMENT ME!
     * @param ttVal2 DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static boolean compareValues(ManagedEntityValue ttVal1, ManagedEntityValue ttVal2) {
        String[] attrNamesForVal1 = ttVal1.getPopulatedAttributeNames();
        String[] attrNamesForVal2 = ttVal2.getPopulatedAttributeNames();
//        log("-------START COMPARE------------------");
//
//        log("Number of Populated Attributes in Value 1");
//        log(new Integer(attrNamesForVal1.length).toString());
//
//        log("Number of Populated Attributes in Value 2");
//
//        log(new Integer(attrNamesForVal2.length).toString());

//        if (attrNamesForVal1.length == attrNamesForVal2.length) {
//            log("Same number of Populated Attributes");
//        } else {
//            log("Different number of Populated Attributes");
//        }

        for (int i = 0; i < attrNamesForVal1.length; i++) {
            Object attributeValue1 = null;
            Object attributeValue2 = null;
            boolean att1isGet = false;
            boolean att2isGet = false;

            try {
                log("-----Testing Equality for Attribute----->" + attrNamesForVal1[i]);

                att1isGet = true;
                attributeValue1 = ttVal1.getAttributeValue(attrNamesForVal1[i]);
                att1isGet = false;

//                if (attributeValue1 == null) {
//                    log("--Attribute Value 1 is NULL---");
//                    log(attrNamesForVal1[i]);
//                }
//
//                log(attrNamesForVal1[i]);

                att2isGet = true;
                attributeValue2 = ttVal2.getAttributeValue(attrNamesForVal1[i]);
                att2isGet = false;

                if (compareAttributes(attributeValue1, attributeValue2)) {
                    //log("Attribute are the same");
                } else {
                    //log("Attribute Values are not the same for: " + attrNamesForVal1[i]);

                    return false;
                }
            } catch (java.lang.IllegalArgumentException e1) {
//                log("IllegalArgumentException Exception getting attribute value on");
//
//                if (att2isGet) {
//                    log("On Value 2 ");
//                } else {
//                    log("On Value 1");
//                }
//
//                log(attrNamesForVal1[i]);

                String failure = "IllegalArgumentException Exception getting attribute value on"
                    + attrNamesForVal1[i];

                //if( failMode ) fail(failure);
                return false;
            } catch (java.lang.IllegalStateException e2) {
//                log("IllegalStateException Exception getting attribute value on");

//                if (att2isGet) {
//                    log("On Value 2 ");
//                } else {
//                    log("On Value 1");
//                }
//
//                log(attrNamesForVal1[i]);

                String failure = "IllegalStateException Exception getting attribute value on"
                    + attrNamesForVal1[i];

                //if( failMode ) fail(failure);
                return false;
            } catch (Exception e) {
//                log("System Exception getting attribute value on");
//                log("Exception is: " + e.getMessage());
                e.printStackTrace();

//                if (att2isGet) {
//                    log("On Value 2 ");
//                } else {
//                    log("On Value 1");
//                }
//
//                log(attrNamesForVal1[i]);
//                log(e.toString());

                String failure = "System Exception getting attribute value on"
                    + attrNamesForVal1[i];
//                log("SYSTEM EXCEPTION" + failure);

                return false;
            }
        }

        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param attributeValue1 DOCUMENT ME!
     * @param attributeValue2 DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static boolean compareAttributes(Object attributeValue1, Object attributeValue2) {
        if ((attributeValue1 == null) && (attributeValue2 == null)) {
            return true;
        }

        if ((attributeValue1 != null) && (attributeValue2 == null)) {
            return false;
        }

        if ((attributeValue1 == null) && (attributeValue2 != null)) {
            return false;
        }

//        log("------Attribute Values are-------");
//        log(attributeValue1.getClass().getName());

        boolean isArray = attributeValue1.getClass().isArray();

//        if (isArray) {
//            log("------This is an Array-------");
//        } else {
//            log("------This is not an Array-------");
//        }

        //log(attributeValue1.toString());

        //log(attributeValue2.toString());

        if (isArray) {
            //log("START Processing Array Attribute");

            return compareArrays((Object[]) attributeValue1, (Object[]) attributeValue2);
        } else {
            if (attributeValue1.equals(attributeValue2)) {
                //log("attribute values are equal");

                return true;
            } else {
                //log("attribute values are not equal");

                return false;
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param attributeValue1 DOCUMENT ME!
     * @param attributeValue2 DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static boolean compareArrays(Object[] attributeValue1, Object[] attributeValue2) {
        //log("-----TEST EQUALITY OF ARRAYS--- ");

        if ((attributeValue1 == null) && (attributeValue2 == null)) {
            return true;
        }

        if ((attributeValue1 == null) || (attributeValue2 == null)) {
            return false;
        }

        if (attributeValue1.length != attributeValue2.length) {
            return false;
        }

//        for (int i = 0; i < attributeValue1.length; i++) {
//            log("--- BEGIN OF attribute value[i]----");
//            log(attributeValue1[i].toString());
//            log(attributeValue2[i].toString());
//            log("----END OF attribute value[i]---");
//        }

//        log("BEFORE COLLECTION COMPARE");

        Collection col1 = Arrays.asList(attributeValue1);
        Collection col2 = Arrays.asList(attributeValue2);

        if (col1.equals(col2)) {
            //log("attribute ARRAY values are equal under COLLECTION");

            return true;
        } else {
            //log("attribute ARRAY values are NOT equal under COLLECTION");

            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param log DOCUMENT ME!
     */
    public static void log(String log) {
        //Logger.log(log);
    	//System.out.println(log);
    }

    //compare application DN, primaryKey etc...
    // public javax.oss.ApplicationContext getApplicationContext();
    // public String getApplicationDN();
    // public String getType();
    // public Object getPrimaryKey();
    public static boolean compareKeys(ManagedEntityKey key1, ManagedEntityKey key2) {
        Object pKey1 = key1.getPrimaryKey();
        Object pKey2 = key2.getPrimaryKey();

        ApplicationContext ctx1 = key1.getApplicationContext();
        ApplicationContext ctx2 = key2.getApplicationContext();

        String appDN1 = key1.getApplicationDN();
        String appDN2 = key2.getApplicationDN();

        String type1 = key1.getType();
        String type2 = key2.getType();

        return (((pKey1 == null) ? (pKey2 == null) : pKey1.equals(pKey2))
        && ((ctx1 == null) ? (ctx2 == null) : ctx1.equals(ctx2))
        && ((appDN1 == null) ? (appDN2 == null) : appDN1.equals(appDN2))
        && ((type1 == null) ? (type2 == null) : type1.equals(type2)));
    }

    /**
     * DOCUMENT ME!
     *
     * @param mev DOCUMENT ME!
     */
    public static void logMev(ManagedEntityValue mev) {
        System.out.println("---------Managed Entity Value----------");
        System.out.println("Class Name: " + mev.getClass().getName());

        String[] attrNames = mev.getAttributeNames();
        System.out.println("Attributes [" + attrNames.length + "]:");

        for (int i = 0; i < attrNames.length; i++) {
            System.out.println(attrNames[i]);
        }

        String[] popAttrNames = mev.getPopulatedAttributeNames();
        System.out.println("Populated Attributes [" + popAttrNames.length + "]:");

        for (int i = 0; i < popAttrNames.length; i++) {
            if (mev.getAttributeValue(popAttrNames[i]) != null) {
                System.out.println("\"" + popAttrNames[i] + "\" = "
                    + mev.getAttributeValue(popAttrNames[i]).toString());
            } else {
                System.out.println("\"" + popAttrNames[i] + "\" = null");
            }
        }
    }
}

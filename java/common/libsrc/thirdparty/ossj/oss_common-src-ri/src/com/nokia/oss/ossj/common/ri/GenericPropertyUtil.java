

package com.nokia.oss.ossj.common.ri;

import java.beans.*;
import java.lang.reflect.*;

/**
 *
 *   @author BanuPrasad Dhanakoti Nokia Networks
 * @version 1.1
 * January 2002
 */
public class GenericPropertyUtil extends Object {

    public static Class getPropertyType(Class whichClass, String attributeName) {
        java.lang.reflect.Method getMethod;
        try {
            BeanInfo myBeanInfo = Introspector.getBeanInfo(whichClass);
            PropertyDescriptor[] propDescs = myBeanInfo.getPropertyDescriptors();
            for (int i=0 ; i<propDescs.length ; i++) {
                if (propDescs[i].getName().equals(attributeName)) {
                    return propDescs[i].getPropertyType();
                }
            }
            throw new IllegalArgumentException("Did not find a property with name \""+attributeName+"\" in class "+whichClass.getName());
        } catch (IntrospectionException ie) {
            System.out.println(ie.toString());
        } 
        return null;
    }
    /** This method returns the value of the specified attribute.
     * @param attributeName the attribute's name
     * @return The attribute's value. Primitive types are wrapped in their respective classes.
     * @throws java.lang.IllegalArgumentException An <CODE>IllegalArgumentException</CODE> is thrown, when
     * <UL>
     * <LI>there is no attribute with this name
     * </UL>
 */
    public static Object getAttributeValue(Object whichObject, String attributeName) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        java.lang.reflect.Method getMethod;
        try {
            BeanInfo myBeanInfo = Introspector.getBeanInfo(whichObject.getClass());
            PropertyDescriptor[] propDescs = myBeanInfo.getPropertyDescriptors();
            for (int i=0 ; i<propDescs.length ; i++) {
                if (propDescs[i].getName().equals(attributeName)) {
                    getMethod = propDescs[i].getReadMethod();
                    if (getMethod!=null) {
                        return getMethod.invoke(whichObject, new Object[] {});
                    } else {
                        throw new java.lang.IllegalArgumentException("Could not find setMethod for property with name \""+attributeName+"\" in class "+whichObject.getClass().getName());
                    }
                }
            }
            throw new IllegalArgumentException("Did not find a property with name \""+attributeName+"\" in class "+whichObject.getClass().getName());
        } catch (IntrospectionException ie) {
            System.out.println(ie.toString());
        } catch (IllegalAccessException iae) {
            System.out.println(iae.toString());
        } catch (InvocationTargetException ite) {
            System.out.println(ite.toString());
            System.out.println(ite.getTargetException().toString());
            ite.printStackTrace();
        }
        return null;
    }
    
    /** Assings a new value to an attribute. <p>
     * @param attributeName The attribute's name which shall be changed
     * @param Value The attribute's new value. This can either be:
     * <UL>
     * <LI>An Object which can be casted to the real type of <CODE>attributesName</CODE>
     * <LI>A wrapper class for primitive types, i.e. <CODE>Integer</CODE> instead of <CODE>int</CODE>.
     * In any other case an exception is thrown.
     * </UL>
     * @throws java.lang.IllegalArgumentException This Exception is thrown, when either
     * <UL>
     * <LI>There is no attribute with this name
     * </UL>
 */
    public static void setAttributeValue(Object whichObject, String attributeName, Object newValue) throws java.lang.IllegalArgumentException {
        java.lang.reflect.Method setMethod;
        try {
            BeanInfo myBeanInfo = Introspector.getBeanInfo(whichObject.getClass());
            PropertyDescriptor[] propDescs = myBeanInfo.getPropertyDescriptors();
            for (int i=0 ; i<propDescs.length ; i++) {
                if (propDescs[i].getName().equals(attributeName)) {
                    setMethod = propDescs[i].getWriteMethod();
                    setMethod.invoke(whichObject, new Object[] {newValue});
                    return;
                }
            }
            throw new IllegalArgumentException("Did not find a property with name \""+attributeName+"\" in class "+whichObject.getClass().getName());
        } catch (IntrospectionException ie) {
        } catch (IllegalAccessException iae) {
        } catch (InvocationTargetException ite) {
        }
    }
}


/*
 * Copyright (c) 2002
 * Cisco Systems, Inc., Ericsson Radio Systems AB.,
 * Motorola, Inc., NEC Corporation, Nokia Networks Oy,
 * Nortel Networks Limited, Sun Microsystems, Inc.,
 * Telcordia Technologies, Inc., Cygent, Inc.,
 * Agilent Technologies, Inc., BEA Systems, Inc.,
 * Digital Fairway Corporation, Orchestream Holdings plc.
 *
 * All rights reserved.  Use is subject to license terms.
 *
 * DOCUMENTATION IS PROVIDED "AS IS" AND ALL EXPRESS OR IMPLIED
 * CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE DISCLAIMED, EXCEPT
 * TO THE EXTENT THAT SUCH DISCLAIMERS ARE HELD TO BE LEGALLY
 * INVALID.
 */

package com.nokia.oss.ossj.sa.ri;

import java.util.*;
import java.sql.*;

import com.nokia.oss.ossj.common.ri.*;
import com.nokia.oss.ossj.sa.ri.*;

import javax.oss.ManagedEntityValue;
import javax.oss.ManagedEntityKey;

import javax.ejb.CreateException;
import javax.naming.NamingException;
import javax.naming.Context;

/**
 *
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public abstract class TimestampedMEV implements javax.ejb.EntityBean {


    protected javax.ejb.EntityContext myEntityContext;
    
    // CMP methods
    
    // Start primary key - attribute names must match public fields in key implementation classes
    
    public abstract void setMevPrimaryKey(String newValue);
    public abstract String getMevPrimaryKey();

    // End primary key 

    public abstract void setMevType(String newValue);
    public abstract String getMevType();
    
    public abstract void setLastModifiedCmp(Timestamp newValue);
    public abstract Timestamp getLastModifiedCmp();
    
    protected Context myNamingContext;

    public CMPManagedEntityKey create(ManagedEntityValue mev) throws CreateException {
        /* THIS DOES NOT WORK BECAUSE EJBC DOES NOT FIND IMPLEMENTED METHOD setAttributs in Order an Service
         * ERROR: Error from ejbc:
         *        Order: setAttributes:  The entity bean contains an extra abstract method.  
         *        Abstract methods on the bean class must be a getXXX/setXXX method for a container managed field, 
         *        or a valid ejbSelect method.
         *        Service: setAttributes:  The entity bean contains an extra abstract method.  
         *        Abstract methods on the bean class must be a getXXX/setXXX method for a container managed field, 
         *        or a valid ejbSelect method.
         *
        ManagedEntityKey key = mev.getManagedEntityKey();
        mev.unpopulateAttribute(javax.oss.ManagedEntityValue.KEY);
        setDomain(key.getDomain());
        setType(key.getType());
        setPrimaryKey((String)key.getPrimaryKey());
        setAttributes(mev);
         */
        return null;
    }
    // VP workaroung to Bug ID 4149119 
    //public void ejbPostCreate(ManagedEntityValue mev) throws CreateException {
    protected void my_ejbPostCreate(ManagedEntityValue mev) throws CreateException {
    }
    
    public void unsetEntityContext() throws javax.ejb.EJBException, java.rmi.RemoteException {
        myEntityContext = null;
    }
    
    public void setEntityContext(javax.ejb.EntityContext newEntityContext) throws javax.ejb.EJBException, java.rmi.RemoteException {
        myEntityContext = newEntityContext;
    }
    
    public void ejbActivate() {
    }
    
    public void ejbLoad() {
    }
    
    public void ejbPassivate() {
    }
    
    public void ejbRemove() {
    }
    
    public void ejbStore() {
        // unfortunatly this does not really work, since ejbStore is not only called when it is really necessary
        //setLastModified(new Timestamp(System.currentTimeMillis()));
    }

    // public abstract void setAttributes(ManagedEntityValue orderValue);

    public java.util.Date getLastModified() {
            return convertTimestampToDate(getLastModifiedCmp());
    }
    
    protected void punchTime() {
        setLastModifiedCmp(new Timestamp(System.currentTimeMillis()));        
    }
    
    protected Context getNamingContext() {
        if (myNamingContext==null) {
            try {
                //VP myNamingContext = new javax.naming.InitialContext();
                myNamingContext = (Context) new javax.naming.InitialContext().lookup("java:comp/env");
            } catch (javax.naming.NamingException ne) {
            }
        }
        return myNamingContext;
    }
    
    protected Object lookup(String name) {
        Object obj = null;
        try {
            obj = getNamingContext().lookup(name);
        } catch (javax.naming.NamingException ne) {
        }
        return obj;
    }
    
    protected static java.sql.Timestamp convertDateToTimestamp(java.util.Date aDate) {
        if (aDate == null) return null;
        return new java.sql.Timestamp(aDate.getTime());
    }
    
    protected static java.util.Date convertTimestampToDate(java.sql.Timestamp aTimestamp) {
        if (aTimestamp == null) return null;
        return new java.util.Date(aTimestamp.getTime()+(int)(aTimestamp.getNanos()/1000000));        
    }
    
    /**
     * This method takes a property name as argument and turns it into the corresponding column name of
     * the database. This transition is made by seperating and uppercasing words, seperated by underscores:
     * "ColorAttribute" -> "COLOR_ATTRIBUTE"
     */
    protected static String getColumnName(String attribute) {
        if (attribute.length() == 1) return attribute.toUpperCase();
        List wordList = new ArrayList(5);
        int start=0, i=0;
        for (i=0; i<attribute.length(); i++) {
            if (Character.isLowerCase(attribute.charAt(i))) {
                if ( (i+1)==attribute.length() || Character.isUpperCase(attribute.charAt(i+1)) ) {
                    wordList.add(attribute.substring(start, i+1));
                    start=i+1;
                }
            } else if (Character.isUpperCase(attribute.charAt(i)) && start < i) {
                if ( (i+1)==attribute.length() || Character.isLowerCase(attribute.charAt(i+1)) ) {
                    wordList.add(attribute.substring(start, i));
                    start=i;
                }
            }
        }
        StringBuffer sb = new StringBuffer();
        Iterator it = wordList.iterator();
        while (it.hasNext()) {
            sb.append(((String)it.next()).toUpperCase());
            if (it.hasNext()) {
                sb.append("_");
            }
        }
        return sb.toString();
    }
 
    protected String getApplicationDN() {
        try {
            //VP return (String)getNamingContext().lookup("java:comp/env/ApplicationDN");
            return (String)getNamingContext().lookup("ApplicationDN");
        } catch (NamingException ne) {
            System.out.println(ne.toString());
            return null;
        }
    }
   
    protected void markRollback() {
        try {
            myEntityContext.setRollbackOnly();
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
        }
    }
}

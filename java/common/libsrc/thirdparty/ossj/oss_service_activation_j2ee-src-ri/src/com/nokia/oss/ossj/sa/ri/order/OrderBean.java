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
package com.nokia.oss.ossj.sa.ri.order;

// OSS/J imports

import javax.oss.*;
import javax.oss.order.*;
import javax.oss.service.*;

// RI imports

import com.nokia.oss.ossj.common.ri.*;
import com.nokia.oss.ossj.sa.ri.*;
import com.nokia.oss.ossj.sa.ri.service.*;

// Utility imports

import java.sql.*;
import javax.sql.*;
import java.util.*;
import javax.naming.*;

// Expetion imports

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.naming.Context;

    /**
     *although dates have to be of the type java.sql.Timestamp for CMP-fields, the outer
     *representation has to be java.util.Date, since these are declared in OrderValue too.
     *Unfortunatly, Timestamp is not a real java.util.Date, although it inherits it:
     *
     *<p><b>Note:</b> This type is a composite of a <code>java.util.Date</code> and a
     * separate nanoseconds value. Only integral seconds are stored in the
     * <code>java.util.Date</code> component. The fractional seconds - the nanos - are
     * separate. The <code>getTime</code> method will return only integral seconds. If
     * a time value that includes the fractional seconds is desired, you
     * must convert nanos to milliseconds (nanos/1000000) and add this to
     * the <code>getTime</code> value.  The
     * <code>Timestamp.equals(Object)</code> method never returns
     * <code>true</code> when passed a value of type <code>java.util.Date</code>
     *because the nanos component of a date is unknown.
     * As a result, the <code>Timestamp.equals(Object)</code>
     *method is not symmetric with respect to the
     * <code>java.util.Date.equals(Object)</code>
     *method.  Also, the <code>hashcode</code> method uses the underlying
     * <code>java.util.Data</code> implementation and therefore does not include nanos in its computation.
     *Due to the differences between the <code>Timestamp</code> class
     * and the <code>java.util.Date</code>
     *class mentioned above, it is recommended that code not view
     * <code>Timestamp</code> values generically as an instance of
     * <code>java.util.Date</code>.  The
     * inheritance relationship between <code>Timestamp</code>
     *and <code>java.util.Date</code> really
     * denotes implementation inheritance, and not type inheritance.
     *
     *So the inner representation is a Timestamp to which the outer Date has to be converted to and contructed from
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
     */
public abstract class OrderBean extends TimestampedMEV {
    
    
    public abstract void setApiClientIdCmp(String newValue);
    public abstract String getApiClientIdCmp();
    
    public abstract void setDescriptionCmp(String newValue);
    public abstract String getDescriptionCmp();
    
    public abstract void setStateCmp(String newValue);
    public abstract String getStateCmp(); // Business method
    
    public abstract void setPriorityCmp(int newValue);
    public abstract int getPriorityCmp();
    
    public abstract void setPurchaseOrderCmp(String newValue);
    public abstract String getPurchaseOrderCmp();
    
    public abstract void setOrderDateCmp(Timestamp newValue);
    public abstract Timestamp getOrderDateCmp();
    
    public abstract void setRequestedCompletionDateCmp(Timestamp newValue);
    public abstract Timestamp getRequestedCompletionDateCmp();
    
    public abstract void setActualCompletionDateCmp(Timestamp newValue);
    public abstract Timestamp getActualCompletionDateCmp();
    
    public abstract void setServicesCmr(Collection services);
    public abstract Collection getServicesCmr();
    
    /**
     * form ejb-spec-pfd2, 10.5.2:
     * The entity
     * Bean Provider must not attempt to modify the values of cmr-fields in an 
     * ejbCreate<METHOD(...) method; this should be done in the 
     * ejbPostCreate<METHOD(...) method instead.
     * => service has to be set in postCreate
     */
    public CMPManagedEntityKey ejbCreate(OrderValueImpl mev) throws CreateException {
        super.create(mev);
        
        // see comment in super.ejbCreate
        ManagedEntityKey key = mev.getManagedEntityKey();
        
        // do not set services in ejbCreate, because a service is dependant on an order - so if it is created
        // first a foreign key violation occurs - unpopulate services here and populate it again later.
        
        // the only order which is allowed to have no services is the dummy order for the service inventory
        boolean hasServices = mev.isPopulated(OrderValue.SERVICES);
        ServiceValue[] services = null;
        if (hasServices) {
            services = mev.getServices();
            mev.unpopulateAttribute(OrderValue.SERVICES);
        }
        
        //set all attributes may be written on the server
        setAttributes(mev);
        
        // repopulate services
        if (hasServices) {
            mev.setServices(services);
        }
        
        //set key and state
        setMevType(key.getType());
        setMevPrimaryKey((String)key.getPrimaryKey());
        setState(mev.getState());
        
        return null;
    }
    
    public void ejbPostCreate(OrderValueImpl mev) throws CreateException {

        //VP workaround bug id 4149119
		//super.ejbPostCreate(mev);
        super.my_ejbPostCreate(mev);

        // do not use setServices directly, because then the orderValueArrayPosition will not be set
        // setAttribute(OrderValue.SERVICES, mev);
        // they are set in JVTActivationSession.createOrderByKey
    }
    
    public void ejbStore() {
        super.ejbStore();
    }
    

    
    // Business Methods
    
    public void setAttributes(OrderValue anOrderValue) {
        String[] populatedAttributes = anOrderValue.getPopulatedAttributeNames();
        Object value=null;
        for (int i=0 ; i < populatedAttributes.length; i++) {
            setAttribute(populatedAttributes[i], anOrderValue);
        }
    }
    
    /**
     * this method compares the attribute name in the order value with the internal value of
     * that attribute. if they are different true is returned. this method may only be called
     * with a valid attribute name and a populated attribute in order value 
     */
    protected boolean hasDifferentValue(String attributeName, OrderValue anOrderValue) {

        if (! (attributeName.equals(OrderValue.SERVICES) || attributeName.equals(OrderValue.FAILED_SERVICES))) {
            Object thisObj = null;
            try {
                thisObj = GenericPropertyUtil.getAttributeValue(this, attributeName);
            } catch (java.lang.IllegalArgumentException iae) {
                // should only happen if RI is poorly written
                iae.printStackTrace();
            }
            Object orderValueObj = anOrderValue.getAttributeValue(attributeName);
            if ( thisObj==null ) {
                return orderValueObj!=null;
            } else {
                return !thisObj.equals(orderValueObj); 
            }
        } else {
            // if in doubt, say they are different
            return true;
        }
    }
    
    public String[] updateAttributes(OrderValue anOrderValue) {
        String[] attributes = null;
        Set changedAttributes = new HashSet();
        if (anOrderValue instanceof OrderValueImpl) {
            attributes = ((OrderValueImpl)anOrderValue).getDirtyAttributeNames();
        } else {
            attributes = anOrderValue.getPopulatedAttributeNames();
        }
        for (int i=0 ; i < attributes.length; i++) {
            if (!attributes[i].equals(OrderValue.KEY)) {
                if (hasDifferentValue(attributes[i], anOrderValue)) {
                    setAttribute(attributes[i], anOrderValue);
                    changedAttributes.add(attributes[i]);
                }
            }
        }
        return (String[])changedAttributes.toArray(new String[0]);
    }
    
    public void setAttribute(String attributeName, OrderValue anOrderValue) {
        if (attributeName.equals(OrderValue.KEY) || attributeName.equals(OrderValue.FAILED_SERVICES)) {
            return;
            // neither primary key nor type should be rewritten to the order
        } else {
            try {
                GenericPropertyUtil.setAttributeValue(this, attributeName, anOrderValue.getAttributeValue(attributeName));
            } catch (java.lang.IllegalArgumentException e) {
                // should only happen if RI is poorly written
                e.printStackTrace();
            }
        }
    }
    
    public OrderValue getAttributes(String[] requestedAttributes) {

        OrderValueImpl anOrderValue = new OrderValueImpl();

        anOrderValue.setLastModifiedOnServer( getLastModified() );

        if (requestedAttributes == null || requestedAttributes.length==0) {
            requestedAttributes = anOrderValue.getAttributeNames();
        }
        
        // first, set the key, so that the orderValueImpl knows about its type
        // note: shouldn't it always be set?!
        boolean keySet = false;
        for (int i=0; i<requestedAttributes.length && !keySet; i++) {
            if (requestedAttributes[i].equals(OrderValue.KEY)) {
                anOrderValue.setOrderKey(getOrderKey());
                keySet = true;
            }
        }
        
        for (int i=0; i<requestedAttributes.length; i++) {
            if (requestedAttributes[i].equals(OrderValue.SERVICES)) {
                ServiceLocal[] services = getServiceEntityBeans();
                ServiceValue[] serviceValues = new ServiceValue[services.length];
                    for (int j=0 ; j<serviceValues.length ; j++) {
                        serviceValues[j] = services[j].getAttributes(null); // = all attributes
                    }

                if (serviceValues.length==0) {
                    System.out.println("UPS, I wanted to set a zero length service array");
                    System.out.println("my key is: "+getMevPrimaryKey());
                    Thread.dumpStack();
                    // do not tolerate this
                    markRollback();
                } 
                anOrderValue.setServices( serviceValues );
            } else if (requestedAttributes[i].equals(OrderValue.KEY)) {
                // already handled before
            } else {
                anOrderValue.setAttributeValue(requestedAttributes[i], GenericPropertyUtil.getAttributeValue(this, requestedAttributes[i]));
            }
        }
        
        anOrderValue.cleanAttributes();
        return anOrderValue;
    }
    
    public OrderKey getOrderKey() {
        return new OrderKeyImpl(  ApplicationContextImpl.getApplicationContext(getNamingContext()), 
                                    getApplicationDN(), 
                                    getMevType(), 
                                    (String)getMevPrimaryKey());
    }
    
    // Utility methods
    
    private ServiceLocal[] findOrCreateServices(ServiceValue[] serviceValues) throws CreateException {
        //VP ServiceLocalHome aServiceHome = (ServiceLocalHome)lookup("java:comp/env/ejb/Service");
        ServiceLocalHome aServiceHome = (ServiceLocalHome)lookup("ejb/Service");
        String fromOrderPK = (String)((CMPManagedEntityKey)myEntityContext.getPrimaryKey()).mevPrimaryKey;
        ServiceLocal[] services = new ServiceLocal[serviceValues.length];
        for (int i=0 ; i<services.length ; i++) {
                try {
                    CMPServiceDeltaKey aCMPServiceDeltaKey = new CMPServiceDeltaKey((String)serviceValues[i].getServiceKey().getPrimaryKey(), fromOrderPK);
                    services[i] = aServiceHome.findByPrimaryKey( aCMPServiceDeltaKey );
                } catch (FinderException fe) {
                    services[i] = aServiceHome.create((RiServiceValueImpl)serviceValues[i], fromOrderPK);
                    services[i].setPositionInOrderValueArray(i);
                }

        }
        return services;
    }
    
    // Remote Wrapper methods for CMP/CMR fields
    // CMP/CMR should not be exposed in Remote interface
    
    public void setDescription(String newValue) {
        punchTime();
        setDescriptionCmp(newValue);
    }
    public String getDescription() {
        return getDescriptionCmp();
    }
    
    public void setState(String newValue) {
        punchTime();
        setStateCmp(newValue);
    }
    public String getState() {
        return getStateCmp();
    }
    
    public void setApiClientId(String newValue) {
        punchTime();
        setApiClientIdCmp(newValue);
    }
    public String getApiClientId() {
        return getApiClientIdCmp();
    }
    
    public void setPriority(int newValue) {
        punchTime();
        setPriorityCmp(newValue);
    }
    public int getPriority() {
        return getPriorityCmp();
    }
    
    public void setPurchaseOrder(String newValue) {
        punchTime();
        setPurchaseOrderCmp(newValue);
    }
    public String getPurchaseOrder() {
        return getPurchaseOrderCmp();
    }
    
    public void setOrderDate(java.util.Date newValue) {
        punchTime();
        setOrderDateCmp(convertDateToTimestamp(newValue));
    }
    public java.util.Date getOrderDate() {
        return convertTimestampToDate(getOrderDateCmp());
    }
    
    public void setRequestedCompletionDate(java.util.Date newValue) {
        punchTime();
        setRequestedCompletionDateCmp(convertDateToTimestamp(newValue));
    }
    public java.util.Date getRequestedCompletionDate() {
        return convertTimestampToDate(getRequestedCompletionDateCmp());
    }
    
    public void setActualCompletionDate(java.util.Date newValue) {
        punchTime();
        setActualCompletionDateCmp(convertDateToTimestamp(newValue));
    }
    public java.util.Date getActualCompletionDate() {
        return convertTimestampToDate(getActualCompletionDateCmp());
    }
    
    public void setServices(ServiceValue[] serviceValues) {
        punchTime();
        try {
            ServiceLocal[] newServices = findOrCreateServices(serviceValues);
            Collection serviceCollection = getServicesCmr();
            if (newServices.length != serviceCollection.size()) {
                System.out.println("Husten, we have a problem!");
                System.out.println("Not all unneeded services were deleted on a set");
            }
            // do NOT set services, because they are set implicitly when the service bean
            // writes the order pk to its foreign pk field! (which is part of the service's pk)
            //javax.transaction.TransactionRolledbackException: EJB Exception: : javax.ejb.EJBException: The setXXX method for a cmr-field that is mapped to a primary key may not be called.  The cmr-field is read-only.
             //setServices(newServices);
            for (int i=0 ; i<newServices.length ; i++) {
                    newServices[i].updateAttributes((RiServiceValue)serviceValues[i]);
                    newServices[i].setPositionInOrderValueArray(i);

            }
        } catch (CreateException ce) {
            ce.printStackTrace();
            markRollback();
        }
    } 

    public ServiceLocal[] getServiceEntityBeans() {
        Collection serviceCollection = getServicesCmr();
        Iterator it = serviceCollection.iterator();
        ServiceLocal[] services = new ServiceLocal[serviceCollection.size()];
        int pos;
        ServiceLocal tempService;
        while (it.hasNext()) {
            tempService = (ServiceLocal)it.next();
                pos = tempService.getPositionInOrderValueArray();
                if (pos>=services.length) {
                    StringBuffer buf = new StringBuffer();
                    Iterator it2 = serviceCollection.iterator();
                    buf.append("\nPositions in service array:\n");
                    while (it2.hasNext()) {
                        buf.append("Service on position "+((ServiceLocal)it2.next()).getPositionInOrderValueArray()+"\n");
                    }
                    System.out.println(buf.toString());
                    markRollback();
                }
                services[pos] = tempService;

        }
        // TEST START
        if (services.length == 0) {
            System.out.println("NO services available for order "+getMevPrimaryKey());
            Thread.dumpStack();
            markRollback();
        }
        boolean dump = false;
        for (int i=0 ; i<services.length ; i++) {
            if (services[i]==null) {
                dump = true;
                System.out.println("Service "+i+" is null for order "+getMevPrimaryKey());
            }
        }
        if (dump) {
            Thread.dumpStack();
            markRollback();
        }
        // TEST END 
        return services;
    }

    public ServiceKeyResult[] getFailedServices() {
        Collection services = getServicesCmr();
        Iterator it = services.iterator();
        List failedList = new ArrayList(services.size());
        ServiceLocal tempService;
        while (it.hasNext()) {
            tempService = (ServiceLocal)it.next();
                if (tempService.isFailed()) {
                    failedList.add(new ServiceKeyResultImpl(tempService.getServiceKey(), false, tempService.getFailedException()));
                }
        }
        if (failedList.isEmpty()) {
            return new ServiceKeyResult[0];
        } else {
            return (ServiceKeyResult[])failedList.toArray(new ServiceKeyResultImpl[0]);
        }
        
        
    }
    
    // Home methods
    
    protected String addComparisonExpression(String attributeName, String prefix, boolean isNull) {
        if (attributeName.equals(OrderValue.STATE) || attributeName.equals(ServiceValue.STATE)) {
            return prefix+OrderValue.STATE+" LIKE ?";
        } else if (isNull) {
            return prefix+getColumnName(attributeName)+" is null";
        } else {
            return prefix+getColumnName(attributeName)+" = ?";
        }
    }
    
    protected String addComparisonExpression(ManagedEntityKey aKey) {
        String prefix = "";
        if (aKey instanceof OrderKey) {
            prefix = "O.";
        } else if (aKey instanceof ServiceKey) {
            prefix = "S.";
        }
        StringBuffer sb = new StringBuffer();
        //sb.append("( ");
        if (aKey.getType() != null) {
            sb.append(addComparisonExpression("mevType", prefix, false));
            // DG-TODO: ignore domain and primary key for OrderKey
            /*if ( aKey.getPrimaryKey() != null ) {
                sb.append(" AND ");
            }
        } else if (aKey.getPrimaryKey() != null) {
            addComparisonExpression("MevPrimaryKey", prefix);
             **/
        }
        //sb.append(" )");
        return sb.toString();
    }
    
    protected void setKeyParameter(PreparedStatement aStatement, int index, ManagedEntityKey aKey) {
        // DG-TODO: ignore domain and primary key for OrderKey
        if (aKey.getType() != null) {
            try {
                aStatement.setString(index, aKey.getType() );
                //System.out.println("set key.type["+index+"] to "+aKey.getType());
            } catch (SQLException sqle) {
                //???
            }
        }
    }

    protected void setStateParameter(PreparedStatement aStatement, int index, Object value) {
        try {
            aStatement.setString(index, ((String)value)+"%");
            //System.out.println("set string parameter["+index+"] to "+((String)value)+"%");
        } catch (SQLException sqle) {
        }
    }
    
    protected void setParameter(PreparedStatement aStatement, int index, Object value) {
        if (value!=null) {
            setParameter(aStatement, index, value, value.getClass());
        }
    }
    
    protected void setParameter(PreparedStatement aStatement, int index, ManagedEntityValue mev, String attributeName) {
        setParameter(aStatement, index, mev.getAttributeValue(attributeName),
        GenericPropertyUtil.getPropertyType(mev.getClass(), attributeName));
    }
    
    protected void setParameter(PreparedStatement aStatement, int index, Object value, Class aClass) {
        try {
            // in OrderLocal and ServiceLocal, there are only Strings, Dates and ints as parameter
            // ... ignoring failedServices in OrderValue
            //System.out.println("value to be set is of type "+aClass.getName());
            if (java.lang.String.class.isAssignableFrom(aClass)) {
                if (value == null) {
                    aStatement.setNull(index, java.sql.Types.VARCHAR);
                } else {
                    aStatement.setString(index, (String)value);
                }
                //System.out.println("set string parameter["+index+"] to "+((String)value));
            } else if (java.util.Date.class.isAssignableFrom(aClass)) {
                if (value == null) {
                    aStatement.setNull(index, java.sql.Types.TIMESTAMP);
                } else {
                    aStatement.setTimestamp(index, convertDateToTimestamp((java.util.Date)value));
                }
                //System.out.println("set timestamp parameter["+index+"] to "+convertDateToTimestamp((java.util.Date)value));
            } else if (java.lang.Integer.class.isAssignableFrom(aClass) || java.lang.Integer.TYPE.isAssignableFrom(aClass)) {
                if (value == null) {
                    aStatement.setNull(index, java.sql.Types.INTEGER);
                } else {
                    aStatement.setInt(index, ((Integer)value).intValue() );
                }
                //System.out.println("set integer parameter["+index+"] to "+value);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        
    }
    
    public PreparedStatement getSqlStatement(OrderValue[] orderValueTemplates, Connection dbConnection, boolean useService) throws SQLException {
        
        StringBuffer sqlStatement = new StringBuffer();
        
        // construct select ... from ...
        sqlStatement.append("SELECT DISTINCT O.MEV_TYPE, O.MEV_PRIMARY_KEY FROM ORDERBEANTABLE AS O");
        if (useService) {
            sqlStatement.append(", SERVICEBEANTABLE AS S WHERE O.MEV_PRIMARY_KEY=S.ORDER_MEV_PRIMARY_KEY AND "); // TODO STIMMT DAS?!
        } else {
            sqlStatement.append(" WHERE ");
        }
        //VP sqlStatement.append(" NOT (O.mevType = '---INVENTORY---') AND ( ");
        sqlStatement.append(" NOT (O.MEV_TYPE = '---INVENTORY---') AND ( ");

        boolean or = false;
        for (int i=0 ; i<orderValueTemplates.length ; i++) {
            
            // construct where clause
            
            if (or) {
                sqlStatement.append(" OR ");
            }
            sqlStatement.append("( ");
            
            
            boolean and=false;
            // always sort arrays to make sure that the order is the same after the next call when parameters are set
            // in the sql statement
            String[] populatedAttributes = orderValueTemplates[i].getPopulatedAttributeNames();
            Arrays.sort(populatedAttributes);
            for (int j=0 ; j<populatedAttributes.length ; j++) {
                if (populatedAttributes[j].equals(OrderValue.SERVICES) || populatedAttributes[j].equals(OrderValue.FAILED_SERVICES)){
                    continue;
                }
                
                String compString = null;
                boolean nullValue = (orderValueTemplates[i].getAttributeValue(populatedAttributes[j])==null);
                //System.out.println("adding comparison for attribute "+populatedAttributes[j]);
                if ( populatedAttributes[j].equals(OrderValue.KEY)) {
                    //System.out.println("trying to add key comparison expression");
                    compString = addComparisonExpression(orderValueTemplates[i].getOrderKey());
                    if (compString!=null && compString.length()<=2) {
                        // only the prefixes "O." ?
                        compString = null;
                    }
                    //System.out.println("... resulting in "+compString);
                } else {
                    compString = addComparisonExpression(populatedAttributes[j], "O.", nullValue);
                }
                
                if (compString!=null) {
                    if (and) {
                        sqlStatement.append(" AND ");
                    }
                    sqlStatement.append("( ");
                    sqlStatement.append(compString);
                    sqlStatement.append(" )");
                    and = true;
                }
                
            }
            
            if (useService) {
                ServiceValue serviceValueTemplate = orderValueTemplates[i].getServices()[0];
                populatedAttributes = serviceValueTemplate.getPopulatedAttributeNames();
                Arrays.sort(populatedAttributes);
                for (int j=0 ; j<populatedAttributes.length ; j++) {
                    
                    if (!isRiServiceAttribute(populatedAttributes[j])) {
                        continue;
                    }
                    
                    boolean nullValue = (serviceValueTemplate.getAttributeValue(populatedAttributes[j])==null);
                    String compString = null;
                    if ( populatedAttributes[j].equals(RiServiceValue.KEY)) {
                        compString = addComparisonExpression(serviceValueTemplate.getServiceKey());
                        if (compString!=null && compString.length()<=2) {
                            // just the prefix "S." ?
                            compString = null;
                        }
                    } else {
                        compString = addComparisonExpression(populatedAttributes[j], "S.", nullValue);
                    }

                    if (compString!=null) {
                        if (and) {
                            sqlStatement.append(" AND ");
                        }
                        sqlStatement.append("( ");
                        sqlStatement.append(compString);
                        sqlStatement.append(" )");
                        and = true;
                    }
                }
            }
            
            sqlStatement.append(" )");
            or = true;
        }
        
        sqlStatement.append(")");
        //System.out.println("preparedStatment: "+sqlStatement.toString());
        PreparedStatement templateSearchStatement = dbConnection.prepareStatement(sqlStatement.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        
        // set values in where clause
        int parameterCounter = 1;
        for (int i=0 ; i<orderValueTemplates.length ; i++) {
            
            String[] populatedAttributes = orderValueTemplates[i].getPopulatedAttributeNames();
            
            Arrays.sort(populatedAttributes);
            for (int j=0 ; j<populatedAttributes.length ; j++) {
                // service is handled later independantly
                if (populatedAttributes[j].equals(OrderValue.SERVICES) || populatedAttributes[j].equals(OrderValue.FAILED_SERVICES)) {
                    continue;
                }
                // null values are already queried by ISNULL 
                if (orderValueTemplates[i].getAttributeValue(populatedAttributes[j])==null) {
                    continue;
                }
                
                if ( populatedAttributes[j].equals(OrderValue.KEY)) {
                    OrderKey orderKey = orderValueTemplates[i].getOrderKey();
                    setKeyParameter( templateSearchStatement, parameterCounter, orderKey);
                } else if (populatedAttributes[j].equals(OrderValue.STATE)) {
                    setStateParameter(templateSearchStatement, parameterCounter,
                    orderValueTemplates[i].getAttributeValue(populatedAttributes[j])
                    );
                } else {
                    setParameter( templateSearchStatement, parameterCounter,
                    orderValueTemplates[i], populatedAttributes[j]);
                }
                parameterCounter++;

            }

            if (useService) {
                ServiceValue aServiceValueTemplate = orderValueTemplates[i].getServices()[0];
                populatedAttributes = aServiceValueTemplate.getPopulatedAttributeNames();
                Arrays.sort(populatedAttributes);
                for (int j=0 ; j<populatedAttributes.length ; j++) {
                    if (!isRiServiceAttribute(populatedAttributes[j])) {
                        continue;
                    }

                    // null values are already queried by ISNULL 
                    if (aServiceValueTemplate.getAttributeValue(populatedAttributes[j])==null) {
                        continue;
                    }
                    
                    if ( populatedAttributes[j].equals(ServiceValue.KEY)) {
                        ServiceKey serviceKey = aServiceValueTemplate.getServiceKey();
                        setKeyParameter( templateSearchStatement, parameterCounter, serviceKey);
                    } else if (populatedAttributes[j].equals(ServiceValue.STATE)) {
                        setStateParameter(templateSearchStatement, parameterCounter,
                        aServiceValueTemplate.getAttributeValue(populatedAttributes[j])
                        );
                    } else {
                        setParameter( templateSearchStatement, parameterCounter,
                        aServiceValueTemplate, populatedAttributes[j]
                        );
                    }
                    parameterCounter++;

                }
            }
        }
        
        return templateSearchStatement;
        
    }
    
    private boolean isRiServiceAttribute(String attribute) {
        return  attribute.equals(ServiceValue.KEY) || 
                attribute.equals(ServiceValue.STATE) ||
                attribute.equals(ServiceValue.SUBSCRIBER_ID) ||
                attribute.equals(RiServiceValue.SERVICE_ACTIVATOR_HOME_JNDI_NAME);
    }
    
    public Collection ejbHomeFilterOrders(OrderValue[] orderValueTemplates) throws NamingException, SQLException {
        
        Context aContext = new InitialContext();
	//VP
      //VP  OrderLocalHome anOrderHome = (OrderLocalHome)myEntityContext.getEJBHome();
      OrderLocalHome anOrderHome =(OrderLocalHome)lookup("java:comp/env/ejb/Order");//this; ???
	//end VP  
	//VP DataSource riDbDataSource = (DataSource)aContext.lookup("java:comp/env/jdbc/RiDB");
        DataSource riDbDataSource = (DataSource)aContext.lookup("jdbc/ossjsari");

        Connection riDbConnection = null;
        PreparedStatement aPreparedStatement = null;
        ResultSet filterResult = null;

        Collection orderKeys = new HashSet();
        try {
            riDbConnection = riDbDataSource.getConnection();

            // seperate templates after ones with service and ones without service
            List withService = new ArrayList(orderValueTemplates.length/2);
            List withoutService = new ArrayList(orderValueTemplates.length/2);
            for (int i=0 ; i<orderValueTemplates.length ; i++) {
                if (orderValueTemplates[i].isPopulated(OrderValue.SERVICES)) {
                    withService.add(orderValueTemplates[i]);
                } else {
                    withoutService.add(orderValueTemplates[i]);
                }
            }
            
            if (withoutService.size()>0) {
                // handle template searches which do not include a service
                aPreparedStatement = getSqlStatement((OrderValue[])withoutService.toArray(new OrderValue[0]), riDbConnection, false);
                filterResult = aPreparedStatement.executeQuery();

                OrderKey aKey;
                while (filterResult.next()) {
                     aKey = new OrderKeyImpl( ApplicationContextImpl.getApplicationContext(getNamingContext()),
                     getApplicationDN(), 
                     filterResult.getString(1), // type
                    filterResult.getString(2)  // primary key
                    );
                    orderKeys.add(aKey);
                }

                filterResult.close();
                filterResult = null;

                aPreparedStatement.close();
                aPreparedStatement = null;
            }
            
            // TEST: Handle OrderTemplates with Service AND additional Attributes!
            Iterator withServiceIterator = withService.iterator();
            while (withServiceIterator.hasNext()) {
                OrderValue anOrderValueWithService = (OrderValue)withServiceIterator.next();
                ServiceValue aServiceValue = anOrderValueWithService.getServices()[0];

                // prefilter all orders with all attributes beside non-ri-extra-service-attributes, which
                // are stored in a serialized hashmap
                aPreparedStatement = getSqlStatement(new OrderValue[] {anOrderValueWithService}, riDbConnection, true);
                filterResult = aPreparedStatement.executeQuery();
                
                while (filterResult.next()) {
                     OrderKey aKey = new OrderKeyImpl( ApplicationContextImpl.getApplicationContext(getNamingContext()),
                     getApplicationDN(), 
                     filterResult.getString(1), // type
                    filterResult.getString(2)  // primary key
                    );
                     
                    boolean useOrder = true;
                    
                    // read attributes which have to be compared with persistent copy of service
                    String[] serviceTemplateAttributes = aServiceValue.getPopulatedAttributeNames();
                    
                    // check if there are any additional attributes beside that in riSericeValue
                    boolean checkAdditionalAttributes = false;
                    for (int i=0 ; i<serviceTemplateAttributes.length && !checkAdditionalAttributes; i++) {
                        checkAdditionalAttributes = !isRiServiceAttribute(serviceTemplateAttributes[i]);
                    }
                    
                    // only compare additional attributes if necessary
                    if (checkAdditionalAttributes) {
                        // find persistent order and additional service attributes
                        try {
                            OrderLocal tempOrder = anOrderHome.findByPrimaryKey( new CMPManagedEntityKey(aKey));
                            TreeMap serviceAttributes = tempOrder.getServiceEntityBeans()[0].getAdditionalAttributes();

                            for (int i=0 ; i<serviceTemplateAttributes.length && useOrder; i++) {
                                String attribute = serviceTemplateAttributes[i];
                                if (!isRiServiceAttribute(attribute)) {
                                    if (!serviceAttributes.containsKey(attribute)) {
                                        useOrder = false;
                                    } else {
                                        Object dbValue = serviceAttributes.get(attribute);
                                        Object svValue = aServiceValue.getAttributeValue(attribute);
                                        if ( (dbValue == null && svValue != null) || 
                                              dbValue!=null && !dbValue.equals(svValue))
                                        {
                                            useOrder = false;
                                        }
                                    }
                                }
                            }
                        } catch (FinderException fe) {
                            // this should not happen since the order was previously queried.
                        }
                        
                    }
                    
                    if (useOrder && !orderKeys.contains(aKey) ) {
                        orderKeys.add(aKey);
                    }
                }
            }
            
            riDbConnection.close();
            riDbConnection = null;
            
        } catch (SQLException sqle) {
            // try to close what has been opened
            if (filterResult != null) filterResult.close();
            if (aPreparedStatement != null) aPreparedStatement.close();
            if (riDbConnection != null) riDbConnection.close();
            throw sqle;
        }
        
        return orderKeys;
    }
    
}

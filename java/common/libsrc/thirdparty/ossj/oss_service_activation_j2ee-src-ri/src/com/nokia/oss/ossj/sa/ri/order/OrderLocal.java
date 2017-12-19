package com.nokia.oss.ossj.sa.ri.order;
import javax.ejb.EJBLocalObject;
import javax.oss.order.OrderKey;
import javax.oss.order.OrderValue;
import javax.oss.service.ServiceValue;

import com.nokia.oss.ossj.sa.ri.service.ServiceLocal;

/**
 * @author aebbert
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface OrderLocal extends EJBLocalObject {

    // all attributes are updated
    public void setAttributes(OrderValue orderValue);
    public void setAttribute(String attributeName, OrderValue anOrderValue);
    
    // only dirty attributes are updated, returns attribute which really changed
    public String[] updateAttributes(OrderValue orderValue);
    
    // read attributes and return populated OrderValue
    public OrderValue getAttributes(String[] attributes);

    public OrderKey getOrderKey();

    // Remote-CMP methods
    
    public java.util.Date getLastModified();
    
    public void setApiClientId(String newValue);
    public String getApiClientId();
    
    public void setDescription(String newValue);
    public String getDescription();
    
    public void setState(String newValue);
    public String getState();
    
    public void setPriority(int newValue);
    public int getPriority();
    
    public void setPurchaseOrder(String newValue);
    public String getPurchaseOrder();
    
    public void setOrderDate(java.util.Date newValue);
    public java.util.Date getOrderDate();
    
    public void setRequestedCompletionDate(java.util.Date newValue);
    public java.util.Date getRequestedCompletionDate();
    
    public void setActualCompletionDate(java.util.Date newValue);
    public java.util.Date getActualCompletionDate();
    
    // Remote-CMR methods
    
    public void setServices(ServiceValue[] serviceValues);
    public ServiceLocal[] getServiceEntityBeans();

}

/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import java.util.Date;
import javax.oss.cbe.bi.RequestKey;
import javax.oss.cbe.datatypes.TimePeriod;
import ossj.common.cbe.datatypes.TimePeriodImpl;
import javax.oss.cbe.bi.RequestValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.RequestValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.bi.RequestValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class RequestValueImpl
extends ossj.common.cbe.bi.BusinessInteractionValueImpl
implements RequestValue
{ 

    /**
     * Constructs a new RequestValueImpl with empty attributes.
     * 
     */

    public RequestValueImpl() {
        super();
        setManagedEntityKeyInstance( new RequestKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        RequestValue.EXPECTED_COMPLETION_DATE,
        RequestValue.PRIORITY,
        RequestValue.VALID_FOR
    };

    private static final String[] settableAttributeNames = {
        RequestValue.EXPECTED_COMPLETION_DATE,
        RequestValue.PRIORITY,
        RequestValue.VALID_FOR
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (RequestValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (RequestValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(RequestValueImpl.settableAttributeNames);
            super.configureAttributes(anAttributeManager);
        }
    }

    /**
     * Holds the Attribute manager that manage the optional fields
    */
    private static AttributeManager attributemanager = null;

    protected AttributeManager getAttributeManager() {
        return attributemanager;
    }
    protected AttributeManager makeAttributeManager() {
        attributemanager = new AttributeManager();
        return attributemanager;
    }

    public String[] attributeTypeForValidFor() {
        return attributeTypeFor("validFor");
    }

    
    //==================================================================
    // FACTORIES AND ATTRIBUTE TYPE FOR ================================
    //==================================================================
    
    public javax.oss.cbe.datatypes.TimePeriod makeTimePeriod(String type){
        if(type.equals("javax.oss.cbe.datatypes.TimePeriod") || type.equals("ossj.common.cbe.datatypes.TimePeriodImpl")) {
            return new TimePeriodImpl();
        } else {
            return null;
        }
    }

    public javax.oss.cbe.bi.RequestKey makeRequestKey(){
        return (RequestKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.datatypes.TimePeriod";
        addAttributeTypes("validFor", list);
        list[0] = "java.util.Date";
        addAttributeTypes("expectedCompletionDate", list);
        list[0] = "java.lang.String";
        addAttributeTypes("priority", list);
    }

    private java.util.Date _requestvalueimpl_expectedCompletionDate = null;
    private java.lang.String _requestvalueimpl_priority = null;
    private javax.oss.cbe.datatypes.TimePeriod _requestvalueimpl_validFor = null;


    /**
     * Changes the expectedCompletionDate to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new expectedCompletionDate for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setExpectedCompletionDate(java.util.Date value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(RequestValue.EXPECTED_COMPLETION_DATE);
        _requestvalueimpl_expectedCompletionDate = value;
    }


    /**
     * Returns this RequestValueImpl's expectedCompletionDate
     * 
     * @return the expectedCompletionDate
     * 
    */

    public java.util.Date getExpectedCompletionDate()
    throws java.lang.IllegalStateException {
        checkAttribute(RequestValue.EXPECTED_COMPLETION_DATE);
        return _requestvalueimpl_expectedCompletionDate;
    }

    /**
     * Changes the priority to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new priority for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setPriority(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(RequestValue.PRIORITY);
        _requestvalueimpl_priority = value;
    }


    /**
     * Returns this RequestValueImpl's priority
     * 
     * @return the priority
     * 
    */

    public java.lang.String getPriority()
    throws java.lang.IllegalStateException {
        checkAttribute(RequestValue.PRIORITY);
        return _requestvalueimpl_priority;
    }

    /**
     * Changes the requestKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new requestKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setRequestKey(javax.oss.cbe.bi.RequestKey value)
    throws java.lang.IllegalArgumentException    {
        setBusinessInteractionKey(value);
    }


    /**
     * Returns this RequestValueImpl's requestKey
     * 
     * @return the requestKey
     * 
    */

    public javax.oss.cbe.bi.RequestKey getRequestKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.bi.RequestKey)getBusinessInteractionKey();
    }

    /**
     * Changes the validFor to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new validFor for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setValidFor(javax.oss.cbe.datatypes.TimePeriod value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(RequestValue.VALID_FOR);
        _requestvalueimpl_validFor = value;
    }


    /**
     * Returns this RequestValueImpl's validFor
     * 
     * @return the validFor
     * 
    */

    public javax.oss.cbe.datatypes.TimePeriod getValidFor()
    throws java.lang.IllegalStateException {
        checkAttribute(RequestValue.VALID_FOR);
        return _requestvalueimpl_validFor;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        RequestValue val = null;
            val = (RequestValue)super.clone();

            if( isPopulated(RequestValue.EXPECTED_COMPLETION_DATE)) {
                if( this.getExpectedCompletionDate()!=null) 
                    val.setExpectedCompletionDate((java.util.Date)((java.util.Date) this.getExpectedCompletionDate()).clone());
                else
                    val.setExpectedCompletionDate( null );
            }

            if( isPopulated(RequestValue.PRIORITY)) {
                if( this.getPriority()!=null) 
                    val.setPriority(((java.lang.String) new String( this.getPriority())));
                else
                    val.setPriority( null );
            }

            if( isPopulated(RequestValue.VALID_FOR)) {
                if( this.getValidFor()!=null) 
                    val.setValidFor((javax.oss.cbe.datatypes.TimePeriod)((javax.oss.cbe.datatypes.TimePeriod) this.getValidFor()).clone());
                else
                    val.setValidFor( null );
            }

            return val;
    }

    /**
     * Checks whether two RequestValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an RequestValue object that has the arguments.
     * 
     * @param value the Object to compare with this RequestValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof RequestValue)) {
            RequestValue argVal = (RequestValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}

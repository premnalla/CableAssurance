
package com.nokia.oss.ossj.common.ri.ex;

import javax.oss.*;
import com.nokia.oss.ossj.common.ri.*;

import java.util.Map;
import java.util.Date;


/**
 *
 * @author BanuPrasad Dhanakoti  Nokia Networks
 * @version 1.1
 * @since  January 2002
 */
 
public class ManagedEntityEx1Value extends ManagedEntityValueImpl implements java.io.Serializable
{
    private static AttributeManager attributemanager;
    
    public static final String EX1NAME = "ex1Name";  // attribute name
    public static final String EX1VALUE = "ex1Value"; // attribute name
    public static final String EX1MAXVALUE = "ex1MaxValue"; // attribute name
    public static final String EX1MINVALUE = "ex1MinValue";  // attribute name
    public static final String EX1CREATIONDATE = "ex1CreationDate";  // attribute name
    public static final String EX1DESCRIPTION = "ex1Description";  // attribute name
    public static final String EX1API_CLIENT_ID = "ex1ApiClientId";  // attribute name
    

    
    // String array which conatins all attribute Names
    private static final String[] attributeNames = {
        EX1NAME, EX1VALUE, EX1MAXVALUE, EX1MINVALUE, 
        EX1CREATIONDATE, EX1DESCRIPTION, EX1API_CLIENT_ID
    };
    // writeable attributes
    private static final String[] settableAttributeNames = {
                EX1NAME, EX1VALUE, EX1MAXVALUE,
         EX1DESCRIPTION, EX1API_CLIENT_ID
    };
    
    // OrderValue
    private String ex1ApiClientId;
    private String ex1Description;
    private Date   ex1CreationDate;
    private String ex1Name;
    private String ex1Value;
    private String ex1MinValue;
    private String ex1MaxValue;
     
    private ManagedEntityKey managedEntityKey;
    
    //temporary ManagedEntityType - valid as long as no primary key, containing the type, was created
    private String tempType;
    
    // Array of Classes which contains attribute classes in same order as attributeNames
    private static Map attributeClasses;
    
    
                /** Creates new ManagedEntityEx1Value */
    public ManagedEntityEx1Value() {
        super();
    }
    

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        anAttributeManager.addAttributes(this.attributeNames);
        super.addAttributesTo(anAttributeManager);
    }
    
    protected void configureAttributes(AttributeManager anAttributeManager) {
        anAttributeManager.setSettableAttributes(this.settableAttributeNames);
        super.configureAttributes(anAttributeManager);
    }


    protected AttributeManager getAttributeManager() {
        return attributemanager;
    }

    protected AttributeManager makeAttributeManager() {
        attributemanager = new AttributeManager();
        return attributemanager;
    }
        
   
        
    public String getex1ApiClientId(){
        checkAttribute(EX1API_CLIENT_ID);
        return ex1ApiClientId;
    }
    
    public void setex1ApiClientId(String name){
        setDirtyAttribute(EX1API_CLIENT_ID);
        this.ex1ApiClientId = name;
    }
    
    public String getex1Description(){
        checkAttribute(EX1DESCRIPTION);
        return ex1Description;
    }
    
    public void setex1Description(String text){
        setDirtyAttribute(EX1DESCRIPTION);
        ex1Description = text;
    }
    
    public String getex1Name(){
        checkAttribute(EX1NAME);
        return ex1Name;
    }
    
    public void setex1Name(String name){
        setDirtyAttribute(EX1NAME);
        ex1Name = name;
    }
    
    public String getex1Value(){
    	checkAttribute(EX1VALUE);
    	return ex1Value;
    }
    public void setex1Value(String value){
    	setDirtyAttribute(EX1VALUE);
        ex1Value = value;
    }
    public String getex1MaxValue(){
    	checkAttribute(EX1MAXVALUE);
    	return ex1MaxValue;
    }
    public void setex1MaxValue(String maxvalue){
    	setDirtyAttribute(EX1MAXVALUE);
        ex1MaxValue = maxvalue;
    }	
    public String getex1MinValue(){
    	checkAttribute(EX1MINVALUE);
    	return ex1MinValue;
    }
    public void setex1MinValue(String maxvalue){
    	setDirtyAttribute(EX1MINVALUE);
        ex1MinValue = maxvalue;
    }
    public void setex1CreationDate(Date datval)
    {
        setDirtyAttribute(EX1CREATIONDATE);
        ex1CreationDate = datval;
    }
    public Date getex1CreationDate()
    {
        checkAttribute(EX1CREATIONDATE);
    	return ex1CreationDate;
    }
   /* 
    
    /** Setter for property managedEntityKeyDummy.
     * @param managedEntityKeyDummy New value of property managedEntityKeyDummy.
 */
    public void setManagedEntityKeyDummy(javax.oss.ManagedEntityKey managedEntityKeyDummy) {
        if (managedEntityKeyDummy instanceof ManagedEntityExKey) {
            super.setManagedEntityKeyDummy(managedEntityKeyDummy);
        }
    }
    
    public ManagedEntityKey getManagedEntityKey()
    {
    	checkAttribute(KEY);
        return managedEntityKey;
        
    }
         
    public void setManagedEntityKey( ManagedEntityKey managedEntityKey) 
    {
    	if (managedEntityKey instanceof ManagedEntityExKey)
        {
        	setDirtyAttribute(KEY);
        	this.managedEntityKey = managedEntityKey;
        } 
        else
         throw new java.lang.IllegalArgumentException("Not the correct type of key");
    }

   public ManagedEntityKey makeManagedEntityKey() {
            if ( managedEntityKeyDummy != null ) return super.makeManagedEntityKey() ;
            else return  new ManagedEntityEx1KeyImpl() ;
        }
    
}

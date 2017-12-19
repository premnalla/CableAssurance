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
public class ManagedEntityEx2Value extends ManagedEntityValueImpl implements java.io.Serializable
    {
    private static AttributeManager attributemanager;
    
    public static final String EX2NAME = "ex2Name";  // attribute name
    public static final String EX2VALUE = "ex2Value"; // attribute name
    public static final String EX2MAXVALUE = "ex2MaxValue"; // attribute name
    public static final String EX2MINVALUE = "ex2MinValue";  // attribute name
    public static final String EX2CREATIONDATE = "ex2CreationDate";  // attribute name
    public static final String EX2DESCRIPTION = "ex2Description";  // attribute name
    public static final String EX2API_CLIENT_ID = "ex2ApiClientId";  // attribute name
    
    // String array which conatins all attribute Names
    private static final String[] attributeNames = {
        EX2NAME, EX2VALUE, EX2MAXVALUE, EX2MINVALUE, 
        EX2CREATIONDATE, EX2DESCRIPTION, EX2API_CLIENT_ID
    };
    // writeable attributes
    private static final String[] settableAttributeNames = {
               EX2NAME, EX2VALUE, EX2MAXVALUE,
         EX2DESCRIPTION, EX2API_CLIENT_ID
    };
    
    // OrderValue
    private String ex2ApiClientId;
    private String ex2Description;
    private Date   ex2CreationDate;
    private String ex2Name;
    private String ex2Value;
    private String ex2MinValue;
    private String ex2MaxValue;
     
    private ManagedEntityKey managedEntityKey;
    
    //temporary ManagedEntityType - valid as long as no primary key, containing the type, was created
    private String tempType;
    
    // Array of Classes which contains attribute classes in same order as attributeNames
    private static Map attributeClasses;
    
    
                /** Creates new ManagedEntityEx2Value */
    public ManagedEntityEx2Value() {
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
        
   
        
    public String getex2ApiClientId(){
        checkAttribute(EX2API_CLIENT_ID);
        return ex2ApiClientId;
    }
    
    public void setex2ApiClientId(String name){
        setDirtyAttribute(EX2API_CLIENT_ID);
        this.ex2ApiClientId = name;
    }
    
    public String getex2Description(){
        checkAttribute(EX2DESCRIPTION);
        return ex2Description;
    }
    
    public void setex2Description(String text){
        setDirtyAttribute(EX2DESCRIPTION);
        ex2Description = text;
    }
    
    public String getex2Name(){
        checkAttribute(EX2NAME);
        return ex2Name;
    }
    
    public void setex2Name(String name){
        setDirtyAttribute(EX2NAME);
        ex2Name = name;
    }
    
    public String getex2Value(){
    	checkAttribute(EX2VALUE);
    	return ex2Value;
    }
    public void setex2Value(String value){
    	setDirtyAttribute(EX2VALUE);
        ex2Value = value;
    }
    public String getex2MaxValue(){
    	checkAttribute(EX2MAXVALUE);
    	return ex2MaxValue;
    }
    public void setex2MaxValue(String maxvalue){
    	setDirtyAttribute(EX2MAXVALUE);
        ex2MaxValue = maxvalue;
    }	 	             
    public String getex2MinValue(){
    	checkAttribute(EX2MINVALUE);
    	return ex2MinValue;
    }
    public void setex2MinValue(String minvalue){
    	setDirtyAttribute(EX2MINVALUE);
        ex2MinValue = minvalue;
    }
    public void setex2CreationDate(Date datval)
    {
        setDirtyAttribute(EX2CREATIONDATE);
        ex2CreationDate = datval;
    }
    public Date getex2CreationDate()
    {
        checkAttribute(EX2CREATIONDATE);
    	return ex2CreationDate;
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
			else return  new ManagedEntityEx2KeyImpl() ;
        }
}

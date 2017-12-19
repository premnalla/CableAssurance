package com.nokia.oss.ossj.common.ri.ex;

// OSS/J imports

import javax.oss.*;
import com.nokia.oss.ossj.common.ri.*;


// Utility imports
import java.util.*;
    

import javax.ejb.CreateException;

public class ManagedEntityEx2   {
    
    private static final String MIN ="0";
    private String ex2ApiClientId;
    private String ex2Description;
    private Date   ex2CreationDate;
    private String ex2Name;
    private String ex2Value;
    private String ex2MinValue;
    private String ex2MaxValue;
    private ManagedEntityKey key;
    
   
    public ManagedEntityKey CreateManagedEntityEx2(ManagedEntityEx2Value mev)
    {            
        key = mev.getManagedEntityKey();
        setAttributes(mev);  
        setex2CreationDate( new Date());
        setex2MinValue ( MIN) ;
        return key;
    } 
    public ManagedEntityEx2Value getManagedEntityValue()
    {
        ManagedEntityEx2Value result = new ManagedEntityEx2Value();
            if( key != null)
                result.setManagedEntityKey(key);    
            if ( ex2ApiClientId != null)
                result.setex2ApiClientId(ex2ApiClientId);
            if (ex2Description != null)
                result.setex2Description(ex2Description);
            if(ex2Name != null)
                result.setex2Name(ex2Name);
            if (ex2Value != null)
                result.setex2Value(ex2Value);
            if (ex2MaxValue != null)
                result.setex2MaxValue(ex2MaxValue);
         return result;
    }
    public ManagedEntityKey getManagedEntityKey()
    {
        return key ;
    }
   public String getex2ApiClientId(){
                return ex2ApiClientId;
    }
    
    public void setex2ApiClientId(String name){
                this.ex2ApiClientId = name;
    }
    
    public Date getex2CreationDate(){
                return ex2CreationDate;
    }
    public void setex2CreationDate(Date date){
                this.ex2CreationDate = date;
    }
    public String getex2MinValue(){
                return ex2MinValue;
    }
    
    
    public void setex2MinValue(String val){
                this.ex2MinValue = val;
    }
    
    public String getex2Description(){
                return ex2Description;
    }
    
    public void setex2Description(String text){
                ex2Description = text;
    }
    
    public String getex2Name(){
              return ex2Name;
    }
    
    public void setex2Name(String name){
                ex2Name = name;
    }
    
    public String getex2Value(){
    	    	return ex2Value;
    }
    public void setex2Value(String value){
    	        ex2Value = value;
    }
    public String getex2MaxValue(){
    	    	return ex2MaxValue;
    }
    public void setex2MaxValue(String maxvalue){
    	        ex2MaxValue = maxvalue;
    }	
    
   
   
    public void setAttributes(ManagedEntityEx2Value mev) 
    {
        
        String[] populatedAttributes = mev.getPopulatedAttributeNames();
        Object value=null;
        for (int i=0 ; i < populatedAttributes.length; i++) {
            try {
                setAttribute(populatedAttributes[i], mev);
            } catch (java.lang.IllegalArgumentException iae) 
            {
                iae.printStackTrace();
            }
        }
    }
    
    public void updateAttributes(ManagedEntityEx2Value mev) 
    {
        // in actual practise use getDirtyattribute using attribute manager and then compare those which have changed value and then update 
        
      setAttributes( mev);
    }    
    
   public void setAttribute(String attributeName, ManagedEntityEx2Value anValue) 
   {   
        if( attributeName.equals(ex2ApiClientId))
            setex2ApiClientId(anValue.getex2ApiClientId());
        else if(attributeName.equals(ex2Name))
            setex2Name(anValue.getex2Name());
        else if(attributeName.equals(ex2Value))
            setex2Value(anValue.getex2Value());
        else if (attributeName.equals(ex2MaxValue))
            setex2MaxValue(anValue.getex2MaxValue());         
    } 
}
    
    
    
    
    



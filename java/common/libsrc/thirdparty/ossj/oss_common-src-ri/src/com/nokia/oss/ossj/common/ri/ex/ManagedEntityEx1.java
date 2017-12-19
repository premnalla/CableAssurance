package com.nokia.oss.ossj.common.ri.ex;

// OSS/J imports

import javax.oss.*;
import com.nokia.oss.ossj.common.ri.*;


// Utility imports
import java.util.*;
    

import javax.ejb.CreateException;

public class ManagedEntityEx1   {
    
    private static final String MIN ="0";
    private String ex1ApiClientId;
    private String ex1Description;
    private Date   ex1CreationDate;
    private String ex1Name;
    private String ex1Value;
    private String ex1MinValue;
    private String ex1MaxValue;
    private ManagedEntityKey key;
    
   
    public ManagedEntityKey CreateManagedEntityEx1(ManagedEntityEx1Value mev)
    {            
        key = mev.getManagedEntityKey();
        System.out.println("hi the value passed till here is " +  mev.getex1ApiClientId());
        System.out.println( "hi the key is" + key);
        setAttributes(mev);  
        setex1CreationDate( new Date());
        setex1MinValue ( MIN) ;
        System.out.println( "!" + MIN );
        System.out.println( "2" + ex1ApiClientId);
       
        
        return key;
    } 
    public ManagedEntityEx1Value getManagedEntityValue()
    {
        ManagedEntityEx1Value result = new ManagedEntityEx1Value();
            if( key != null)
                result.setManagedEntityKey(key);
            if ( ex1ApiClientId != null)
                result.setex1ApiClientId(ex1ApiClientId);
            if (ex1Description != null)
                result.setex1Description(ex1Description);
            if(ex1Name != null)
                result.setex1Name(ex1Name);
            if (ex1Value != null)
                result.setex1Value(ex1Value);
            if (ex1MaxValue != null)
                result.setex1MaxValue(ex1MaxValue);
                
         return result;
    }
    
    public ManagedEntityKey getManagedEntityKey()
    {
        return key ;
    }
   public String getex1ApiClientId(){
                return ex1ApiClientId;
    }
    
    public void setex1ApiClientId(String name){
                this.ex1ApiClientId = name;
    }
    
    public Date getex1CreationDate(){
                return ex1CreationDate;
    }
    public void setex1CreationDate(Date date){
                this.ex1CreationDate = date;
    }
    public String getex1MinValue(){
                return ex1MinValue;
    }
    
    
    public void setex1MinValue(String val){
                this.ex1MinValue = val;
    }
    
    public String getex1Description(){
                return ex1Description;
    }
    
    public void setex1Description(String text){
                ex1Description = text;
    }
    
    public String getex1Name(){
              return ex1Name;
    }
    
    public void setex1Name(String name){
                ex1Name = name;
    }
    
    public String getex1Value(){
    	    	return ex1Value;
    }
    public void setex1Value(String value){
    	        ex1Value = value;
    }
    public String getex1MaxValue(){
    	    	return ex1MaxValue;
    }
    public void setex1MaxValue(String maxvalue){
    	        ex1MaxValue = maxvalue;
    }	
    
   
   
    public void setAttributes(ManagedEntityEx1Value mev) 
    {
        
        String[] populatedAttributes = mev.getPopulatedAttributeNames();
        System.out.println("hi number of populateed attributes is "+ populatedAttributes.length);
        Object value=null;
        for (int i=0 ; i < populatedAttributes.length; i++) {
            try {
                System.out.println( "hi "+1 + "  " + populatedAttributes[i]);
                setAttribute(populatedAttributes[i], mev);
            } catch (java.lang.IllegalArgumentException iae) 
            {
                iae.printStackTrace();
            }
        }
    }
    
    public void updateAttributes(ManagedEntityEx1Value mev) 
    {
        // in actual practise use getDirtyattribute using attribute manager and then compare those which have changed value and then update 
        
      setAttributes( mev);
    }    
    
   public void setAttribute(String attributeName, ManagedEntityEx1Value anValue) 
   {   
    if( !attributeName.equals(ex1ApiClientId))
      System.out.println( "hi it doesnt match ex1apiclient");
        if( attributeName.equals("ex1ApiClientId"))
        {
            setex1ApiClientId(anValue.getex1ApiClientId());
            
        }
        else if(attributeName.equals("ex1Name"))
        {
            setex1Name(anValue.getex1Name());
             
        }
        else if(attributeName.equals("ex1Value"))
        {
            setex1Value(anValue.getex1Value());
            
        }
        else if (attributeName.equals("ex1MaxValue"))
        {
            setex1MaxValue(anValue.getex1MaxValue());
            
            
        }
    } 
}
    
    
    
    
    



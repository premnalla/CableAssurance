package ossj.qos.fmri;

/**
 * InterfaceReflector
 * 
 * Currently provides mapping of enumerated types to their String equivalents.
 * In the future, this class may be evolved to handle more than just enums; 
 * e.g. automation of attribute and XML handling.
 *
 * TODO: look at changing interface so client does not have to handle exceptions
 *
 * @author Copyright 2001-2002 Nortel Networks Limited. All rights reserved
 */

import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import java.util.HashMap;

public class InterfaceReflector  implements java.io.Serializable
{
    //----------------------------------------------------------------------
    // HashMap of HashMaps: 
    //     Key: String interface name
    //   Value: HashMap i.e. the key is a String and the value is a HashMap
    //          The contained HashMap hold String to int maps for the actual
    //          enum values.
    //----------------------------------------------------------------------
    private HashMap populatedInterfacesMap;
    
    //ctor
    public InterfaceReflector()
    {
        populatedInterfacesMap = new HashMap();
    }
    
    public Object getInterfaceFieldValue ( Class interfaceClass, 
                                           String fieldName )
    throws javax.oss.IllegalArgumentException, 
           java.lang.NoSuchFieldException, 
           java.lang.IllegalAccessException
    {
        String interfaceName = interfaceClass.getName();
        //System.out.println("interfaceClass: " + interfaceName );
        //System.out.println("fieldName: " + fieldName );
               
        HashMap interfaceMap = null;
        if (! isPopulated( interfaceName ) )
        {
            interfaceMap = populateInterfaceMap( interfaceClass );
        }
        else
        {
            interfaceMap = (HashMap)populatedInterfacesMap.get( interfaceName );   
        }
        if (! interfaceMap.containsKey( fieldName ) )
        {
            throw new javax.oss.IllegalArgumentException
                ("Passed in field Name does not exist: " + fieldName );   
        }
        
        return interfaceMap.get( fieldName );
        
    }
    
    //------------------------------------------------------------  
    // Expose an "int" signature so clients don't have to convert
    // to Integer for enumerated types
    //------------------------------------------------------------  
    public String getInterfaceFieldName ( Class interfaceClass, 
                                          int fieldValue )
    throws javax.oss.IllegalArgumentException, 
           java.lang.IllegalAccessException
    {
        Integer intFieldVal = new Integer(fieldValue);
        return getInterfaceFieldName(interfaceClass,intFieldVal);
    }
    
    
    public String getInterfaceFieldName ( Class interfaceClass, 
                                          Object fieldValue )
    throws javax.oss.IllegalArgumentException, 
           java.lang.IllegalAccessException
    {
        String interfaceName = interfaceClass.getName();
        //System.out.println("InterfaceName: " + interfaceName );
        
        HashMap interfaceMap = null;
        if (! isPopulated( interfaceName ) )
        {
            interfaceMap = populateInterfaceMap( interfaceClass );
        }
        else
        {
            interfaceMap = (HashMap)populatedInterfacesMap.get( interfaceName );   
        }
        
        if(! interfaceMap.containsValue( fieldValue ) )
        {
            throw new javax.oss.IllegalArgumentException("Passed in field Value does not exist: " + fieldValue.toString() );      
        }
        
        
        String currKey = null;
        for ( Iterator iter = interfaceMap.keySet().iterator();iter.hasNext(); )
		{
            currKey = (String) iter.next();
                            
            if ( interfaceMap.get(currKey).equals( fieldValue ) )
            {
                break;
            }
        }
        
        return currKey;   
    }
    
    public String getEnumFieldName(Class interfaceClass,int enumValue)
    {
       
        String val = null;
        
        try
        {    
            val = getInterfaceFieldName ( interfaceClass,enumValue );
        }
        catch (javax.oss.IllegalArgumentException iaex)
        {
            val = "UNDEFINED (-1)";   
        }
        catch (java.lang.IllegalAccessException iacex)
        {
            val = "UNDEFINED (-1)";   
        }
        
        return val;
        
    }
    
   
    //determines if the interface's map has been populated 
    private boolean isPopulated ( String interfaceName )
    {   
        return populatedInterfacesMap.containsKey( interfaceName );
    }
        
    private HashMap populateInterfaceMap ( Class interfaceClass )
    throws java.lang.IllegalAccessException
    {
        String interfaceName = interfaceClass.getName();
                
        //Populate the interface hashMap with field names of the i/f 
        HashMap interfaceMap = new HashMap();
        Field[] publicFields = interfaceClass.getFields();
        for (int x=0; x<publicFields.length; x++)
        {
           // System.out.println( "Name: " + publicFields[x].getName() );
           // System.out.println( "Value: " + publicFields[x].get(interfaceClass ) );
            interfaceMap.put( publicFields[x].getName(),                //key - the string name
                              publicFields[x].get(interfaceClass ) );   //value - the int value
        }
            
        //Add the interface name to the populated map
        populatedInterfacesMap.put( interfaceName, interfaceMap );
       
        return interfaceMap;
    }
        
}  
  
  
 
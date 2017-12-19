

package com.nokia.oss.ossj.common.ri.ex;

import javax.oss.ApplicationContext;
import com.nokia.oss.ossj.common.ri.*;


import java.io.Serializable;

public class ManagedEntityEx1KeyImpl extends ManagedEntityKeyImpl implements ManagedEntityExKey, java.io.Serializable
{
    ApplicationContext anContext = null;

    public ManagedEntityEx1KeyImpl()
    {
    }

    public ManagedEntityEx1KeyImpl( ApplicationContext anApplicationContext, String appTypeDN, String mevType )
    {
        super(anApplicationContext, appTypeDN, mevType, "this is not a primary key of Ex1");
        anContext = anApplicationContext;
    }

    public void setPrimaryKey( Object pk )throws java.lang.IllegalArgumentException 
    {
        if(pk == null)
         throw new java.lang.IllegalArgumentException ();
        super.setPrimaryKey(pk.toString());
    }

    /**
     * Deep copy of this key
     * 
     * @return depp copy of this key
     */
    public Object clone() {
        ManagedEntityEx1KeyImpl myClone = (ManagedEntityEx1KeyImpl)super.clone();
        
        // nothing has to be changed since primary key is a String anyway
        
        return myClone;
    }

public javax.oss.ApplicationContext makeApplicationContext() {
        if(anContext == null)
		return ((ApplicationContext) new ApplicationContextImpl(null,null,null) ) ;
	    else 
	    return (ApplicationContext)anContext.clone();
	}


    /**
     * Manufacture a PrimaryKey
     * 
     * @return PrimaryKey implementation class
     */
    public Object makePrimaryKey() {
        return new String("this is not a primary key");
    }
   
}

package com.nokia.oss.ossj.common.ri.ex;

import javax.oss.*;
import com.nokia.oss.ossj.common.ri.*;

import java.util.HashMap;
import java.util.Date;


public class ManagedEntityManager 
{
  private HashMap listManagedEntityEx1;
  private HashMap listManagedEntityEx2;
  public ManagedEntityManager()
  {
  	listManagedEntityEx1 = new HashMap();
  	listManagedEntityEx2 = new HashMap();
  }

public ManagedEntityEx1 createManagedEntityEx1( ManagedEntityEx1Value value)
{
    
	ManagedEntityEx1 mang = new ManagedEntityEx1();
	ManagedEntityEx1KeyImpl ret =(ManagedEntityEx1KeyImpl) mang.CreateManagedEntityEx1(value);
	
	listManagedEntityEx1.put(ret, mang);
	
	return  mang;
	
}
public ManagedEntityEx2 createManagedEntityEx2( ManagedEntityEx2Value value)
{
    ManagedEntityEx2 mang = new ManagedEntityEx2();
	ManagedEntityEx2KeyImpl ret =(ManagedEntityEx2KeyImpl) mang.CreateManagedEntityEx2(value);
	listManagedEntityEx2.put(ret, mang);
	
	return mang;
}
public void removeManagedEntityEx1(ManagedEntityExKey key)
{
    listManagedEntityEx1.remove(key);
}
public void removeManagedEntityEx2(ManagedEntityExKey key)
{
    listManagedEntityEx2.remove(key);
}
public ManagedEntityEx1Value getManagedEntityEx1Value(ManagedEntityExKey key)
{
    if(listManagedEntityEx1.containsKey(key))
      return ((ManagedEntityEx1)listManagedEntityEx1.get(key)).getManagedEntityValue();
    return  null;
}
public ManagedEntityEx2Value getManagedEntityEx2Value(ManagedEntityExKey key)
{
    if(listManagedEntityEx2.containsKey(key))
      return ((ManagedEntityEx2)listManagedEntityEx2.get(key)).getManagedEntityValue();
    return  null;
    
}
public void setManagedEntityEx1( ManagedEntityEx1Value value)
{
    ManagedEntityEx1KeyImpl ret1 = (ManagedEntityEx1KeyImpl)value.getManagedEntityKey();
    if(listManagedEntityEx1.containsKey(ret1))
    {
      ManagedEntityEx1 val =(ManagedEntityEx1)listManagedEntityEx1.get(ret1);
      val.updateAttributes(value) ;
    }
    
    
}
public void setManagedEntityEx2( ManagedEntityEx2Value value)
{
     ManagedEntityEx2KeyImpl ret1 = (ManagedEntityEx2KeyImpl)value.getManagedEntityKey();
    if(listManagedEntityEx2.containsKey(ret1))
    {
      ManagedEntityEx2 val =(ManagedEntityEx2)listManagedEntityEx2.get(ret1);
      val.updateAttributes(value) ;
    }
}
}
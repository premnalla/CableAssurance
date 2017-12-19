package ossj.qos.pm.measurement;


import ossj.qos.ManagedEntityValueImpl;
import javax.oss.Serializer;
import ossj.qos.xmlri.pmxmlri.PmXmlSerializerImpl;
import ossj.qos.util.Log;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class PmManagedEntityValueImpl extends ManagedEntityValueImpl{

  public PmManagedEntityValueImpl() {
    super();
  }

    public Object clone()
    {
        PmManagedEntityValueImpl clone = null;
        try {
            clone = (PmManagedEntityValueImpl)super.clone();
        } catch ( Exception e ) {
            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
            // "should not happen"
        }
       return clone;
    }


  public String[] getSupportedSerializerTypes() {
    String[] serializerTypes = new String[1];
    serializerTypes[0] = new String( PmXmlSerializerImpl.class.getName());
    return serializerTypes;
  }


  public Serializer makeSerializer( String serializerType )
  throws java.lang.IllegalArgumentException {
    Serializer serializer = null;
    try{
      serializer = new PmXmlSerializerImpl(serializerType);
    }catch(java.lang.IllegalArgumentException e){
      throw new java.lang.IllegalArgumentException(e.getMessage());
    }
    return serializer;
  }

}
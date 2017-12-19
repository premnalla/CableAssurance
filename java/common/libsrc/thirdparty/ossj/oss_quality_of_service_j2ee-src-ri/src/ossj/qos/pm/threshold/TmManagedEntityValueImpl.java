package ossj.qos.pm.threshold;


import ossj.qos.ManagedEntityValueImpl;
import javax.oss.Serializer;
import ossj.qos.xmlri.tmxmlri.TmXmlSerializerImpl;
import ossj.qos.util.Log;
//import ossj.qos.pm.util.Log;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class TmManagedEntityValueImpl extends ManagedEntityValueImpl{

  public TmManagedEntityValueImpl() {
    super();
  }

    public Object clone()
    {
        TmManagedEntityValueImpl clone = null;
        try {
            clone = (TmManagedEntityValueImpl)super.clone();
        } catch ( Exception e ) {
            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
            // "should not happen"
        }
       return clone;
    }


  public String[] getSupportedSerializerTypes() {
    String[] serializerTypes = new String[1];
    serializerTypes[0] = new String( TmXmlSerializerImpl.class.getName());
    return serializerTypes;
  }


  public Serializer makeSerializer( String serializerType )
  throws java.lang.IllegalArgumentException {
    Serializer serializer = null;
    try{
      serializer = new TmXmlSerializerImpl(serializerType);
    }catch(java.lang.IllegalArgumentException e){
      throw new java.lang.IllegalArgumentException(e.getMessage());
    }
    return serializer;
  }


}
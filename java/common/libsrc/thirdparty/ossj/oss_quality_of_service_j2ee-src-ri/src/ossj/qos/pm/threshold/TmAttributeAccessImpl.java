package ossj.qos.pm.threshold;

import ossj.qos.AttributeAccessImpl;
import javax.oss.Serializer;
import ossj.qos.xmlri.tmxmlri.*;
//import ossj.qos.pm.util.*;
import ossj.qos.util.*;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class TmAttributeAccessImpl {

  public TmAttributeAccessImpl() {
    super();
  }
    // clone this instance
    public Object clone() {
        TmAttributeAccessImpl clone = null;
        try {
            clone = (TmAttributeAccessImpl) super.clone();
            //clone.map = (HashMap) map.clone();
        } catch ( CloneNotSupportedException e ) {
            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e); // "should not happen"
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
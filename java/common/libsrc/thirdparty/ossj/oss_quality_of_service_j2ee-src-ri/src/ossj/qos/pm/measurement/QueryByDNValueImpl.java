package ossj.qos.pm.measurement;


import javax.oss.pm.measurement.QueryByDNValue;


/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi, Hooman Tahamtani, Katarina Wahlström
 * @version 0.2
 */

public class QueryByDNValueImpl extends QueryPerformanceMonitorValueImpl implements QueryByDNValue {


  public QueryByDNValueImpl() {
    super();
    map.put(this.DISTINGUISHED_NAMES, null);
  }

  public String[] getDistinguishedNames() throws java.lang.IllegalStateException {

     if(this.isPopulated(this.DISTINGUISHED_NAMES) == false)
      throw new java.lang.IllegalStateException();

    return (String[]) this.getAttributeValue(this.DISTINGUISHED_NAMES);
  }

  public void setDistinguishedNames(String[] distinguishedNames) throws java.lang.IllegalArgumentException {

    if(distinguishedNames == null)
      throw new java.lang.IllegalArgumentException();

    for(int i=0; i<distinguishedNames.length; i++)
      if(distinguishedNames[i] == null)
         throw new java.lang.IllegalArgumentException();

    this.setAttributeValue(this.DISTINGUISHED_NAMES, distinguishedNames);
  }

   protected boolean isValidAttribute( String attrName, Object attrValue ) {

    boolean isValid = false;

    isValid = super.isValidAttribute(attrName, attrValue);

    if ( attrName != null && attrName.equals( QueryByDNValue.DISTINGUISHED_NAMES )
        && attrValue != null && attrValue instanceof String[] ) {
      isValid = true;
      String[] names = (String[])attrValue;
      for(int i=0; i<names.length; i++)
        if(names[i] == null)
          isValid = false;
    }

    return isValid;
  }


}




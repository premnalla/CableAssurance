package ossj.qos;

import javax.oss.ManagedEntityValue;
import javax.oss.ManagedEntityKey;
import ossj.qos.util.Log;

/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi, Henrik Lindström
 * @version 0.2
 */
public class ManagedEntityValueImpl extends AttributeAccessImpl implements ManagedEntityValue {

    private long lastUpdateVersionNumber;

    public ManagedEntityValueImpl() {
        // initialize all known attributes to null
        map.put( ManagedEntityValue.KEY, null );
        // Note, subclasses should initialize their own attributes also!
    }

    /**
     * Validates the ManagedEntityValue attributes.
     * @param attrName name of attribute
     * @param attrValue value of attribute
     * @return true if attribute is valid
     */
	protected boolean isValidAttribute( String attrName, Object attrValue ) {
		if ( attrName != null && attrName.equals( ManagedEntityValue.KEY )
		  && attrValue != null && attrValue instanceof ManagedEntityKey ) {
			return true;
		}
		return false;
	}

    public long getLastUpdateVersionNumber() {
        return lastUpdateVersionNumber;
    }

    public void setLastUpdateVersionNumber(long lastUpdateNumber) throws java.lang.IllegalArgumentException {
      if(lastUpdateNumber < 0)
        throw new java.lang.IllegalArgumentException();

      lastUpdateVersionNumber = lastUpdateNumber;
    }

    public ManagedEntityKey getManagedEntityKey() throws java.lang.IllegalStateException {

        if(this.isPopulated(ManagedEntityValue.KEY) == false)
          throw new java.lang.IllegalStateException();

        Object key = map.get( ManagedEntityValue.KEY );
        return (ManagedEntityKey)key;
    }

    public ManagedEntityKey makeManagedEntityKey() {
        return new ManagedEntityKeyImpl();
    }

  //set the managed entity key
  public void setManagedEntityKey( ManagedEntityKey key ) throws java.lang.IllegalArgumentException {

    if ( key == null ) {
        Log.write(this,Log.LOG_MINOR,"setManagedEntityKey()",ERR_ILLEGAL_ARGUMENT);
        throw new java.lang.IllegalArgumentException( ERR_ILLEGAL_ARGUMENT );
    }
    map.put( ManagedEntityValue.KEY, key );
  }

  //Return the list of attribute names that can be modified
  public String[] getSettableAttributeNames() {
    return this.getAttributeNames(); // assume that all attributes are possible to set
  }
}

package javax.oss;
import java.io.Serializable;


/**
 * Base interface from which each application specific <CODE>QueryValue</CODE>
 * type must be derived. A <CODE>QueryValue</CODE> interface is also a
 * generic attribute accessor (i.e., it extends AttributeAccess).
 *
 */
public interface QueryValue
    extends AttributeAccess, java.io.Serializable, Cloneable, javax.oss.SerializerFactory
{
   /**
     * Deep copy this query value.
     *
     * @return deep copy of this query value.
     */
     public Object clone();


}

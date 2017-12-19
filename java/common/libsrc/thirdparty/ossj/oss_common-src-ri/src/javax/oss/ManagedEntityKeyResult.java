
package javax.oss;

/**
 * A base interface for every application specific <CODE>&lt;ManagedEntity&gt;KeyResult</CODE>
 * interface.
 * <p>
 * A <CODE>&lt;ManagedEntity&gt;KeyResult</CODE> interface must comply with the following template:
 * <pre>
 * public interface &lt;ManagedEntity&gt;KeyResult extends ManagedEntityKeyResult
 * {
 *     public &lt;ManagedEntity&gt;Key get&lt;ManagedEntity&gt;Key();
 * }
 *</pre>
 *<p>
 * The managed entity key result encapsulates all the information necessary to
 * indicate the result of  a BEST EFFORT operation on a specific managed entity.
 * <p>
 * The managed entity key result is used in operations involving the update of
 * multiple managed entities, in the deletion of multiple managed entities or in
 * the creation of multiple managed entities or in bulk business operations.
 * <p>
 * The base <CODE>ManagedEntityKeyResult</CODE> interface contains the managed entity key,
 * a boolean value indicating if the operation on the targeted managed entity was
 * succesful and finally the exception that would have been thrown if this operation
 * had been attempted on the individual Managed Entity.
 *
 *
 */
public interface ManagedEntityKeyResult extends java.io.Serializable
{

      /**
       * Return the Managed Entity Key for which an operation result is reported.
       * @return ManagedEntityKey
       */
      public ManagedEntityKey getManagedEntityKey();

      /**
       * Returns the success state of the operation.
       * @return true if operation was successful, false if it failed.
       */
      public boolean            isSuccess();
      /**
       * Returns the Exception associated with an operation failure.
       * @return the associated exception if the operation failed,
       * <CODE>null</CODE> otherwise.
       */

      public Exception          getException();

      /**
       * Set the exception associated with an operation failure.
       *
       * @param ex
       * @exception java.lang.IllegalArgumentException
       */
      public void setException(Exception ex)
      throws java.lang.IllegalArgumentException;

      /**
       * Set the key of the managed entity targeted by the operation.
       *
       * @param key
       * @exception java.lang.IllegalArgumentException
       */
      public void setManagedEntityKey(ManagedEntityKey key)
      throws java.lang.IllegalArgumentException;

      /**
       * Set the Success state of the operation.
       *
       * @param success
       * @exception java.lang.IllegalArgumentException
       */
      public void setSuccess(boolean success)
      throws java.lang.IllegalArgumentException;

}




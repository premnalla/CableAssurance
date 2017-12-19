/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.alarm;

import java.util.Date;
import javax.oss.cbe.alarm.Comment;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.alarm.Comment</CODE> interface.  
 * 
 * @see javax.oss.cbe.alarm.Comment
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class CommentImpl
implements Comment
{ 

    /**
     * Constructs a new CommentImpl with empty attributes.
     * 
     */

    public CommentImpl() {
    }

    private java.lang.String _commentimpl_commentSystemId = null;
    private java.lang.String _commentimpl_commentText = null;
    private java.util.Date _commentimpl_commentTime = null;
    private java.lang.String _commentimpl_commentUserId = null;


    /**
     * Changes the commentSystemId to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new commentSystemId for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setCommentSystemId(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _commentimpl_commentSystemId = value;
    }


    /**
     * Returns this CommentImpl's commentSystemId
     * 
     * @return the commentSystemId
     * 
    */

    public java.lang.String getCommentSystemId() {
        return _commentimpl_commentSystemId;
    }

    /**
     * Changes the commentText to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new commentText for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setCommentText(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _commentimpl_commentText = value;
    }


    /**
     * Returns this CommentImpl's commentText
     * 
     * @return the commentText
     * 
    */

    public java.lang.String getCommentText() {
        return _commentimpl_commentText;
    }

    /**
     * Changes the commentTime to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new commentTime for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setCommentTime(java.util.Date value)
    throws java.lang.IllegalArgumentException    {
        _commentimpl_commentTime = value;
    }


    /**
     * Returns this CommentImpl's commentTime
     * 
     * @return the commentTime
     * 
    */

    public java.util.Date getCommentTime() {
        return _commentimpl_commentTime;
    }

    /**
     * Changes the commentUserId to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new commentUserId for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setCommentUserId(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _commentimpl_commentUserId = value;
    }


    /**
     * Returns this CommentImpl's commentUserId
     * 
     * @return the commentUserId
     * 
    */

    public java.lang.String getCommentUserId() {
        return _commentimpl_commentUserId;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        Comment val = null;
        try { 
            val = (Comment)super.clone();

            if( this.getCommentTime()!=null) 
                val.setCommentTime((java.util.Date)((java.util.Date) this.getCommentTime()).clone());
            else
                val.setCommentTime( null );

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("CommentImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two Comment are equal.
     * The result is true if and only if the argument is not null 
     * and is an Comment object that has the arguments.
     * 
     * @param value the Object to compare with this Comment
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof Comment)) {
            Comment argVal = (Comment) value;
            if( !Utils.compareAttributes( getCommentSystemId(), argVal.getCommentSystemId())) { return false; } 
            if( !Utils.compareAttributes( getCommentText(), argVal.getCommentText())) { return false; } 
            if( !Utils.compareAttributes( getCommentTime(), argVal.getCommentTime())) { return false; } 
            if( !Utils.compareAttributes( getCommentUserId(), argVal.getCommentUserId())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}

package ossj.qos.fmri;

import javax.oss.fm.monitor.CommentValue;
import java.util.Date;
import ossj.qos.util.Util;
/**
 * CommentValueImpl
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public class CommentValueImpl implements CommentValue {
    
    String commentText = null;
    String commentUserId = null;
    Date commentTime = null;
    String commentSystemId = null;
    
     /**
     * CommentValueImpl - default constructor
     */
    public CommentValueImpl() {
    }

     /**
     * Returns ID of the user that set the comment.
     * 
     * @return <code>String</code> - ID of the user that set the comment.
     * @see #setCommentUserId
     */
    public String getCommentUserId() {
        return commentUserId;
    }
    
       /**
     * Returns comment text.
     * 
     * @return <code>String</code> - Comment text.
     * @see #setCommentText
     */
    public String getCommentText() {
        return commentText;
    }
    
      /**
     * Returns the time that the comment was set.
     * 
     * @return <code>Date</code> - The time that the comment was set.
     * @see #setCommentTime
     */
    public Date getCommentTime() {
        return commentTime;
    }
    
    /**
     * Returns the system ID of the user that set the comment.
     * 
     * <p>
     * If the system id has not been set then it returns an empty string.
     *
     * @return <code>String</code> - System ID of the user that set the comment.
     * @see #setCommentSystemId
     */
    public String getCommentSystemId() {
        return commentSystemId;
    }    
    
    /** 
     * Sets the ID of the user that set the comment.
     * 
     * @param user_Id A String representing the ID of the user that set the comment.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getCommentUserId
     */
    public void setCommentUserId( String userId ) throws java.lang.IllegalArgumentException {
        if ( userId == null ) {
            throw new java.lang.IllegalArgumentException( "Null comment userId entered.");
        }
        commentUserId = userId;
        return;
    }
    
    /** 
     * Sets the comment text.
     * 
     * @param text A String representing the Comment text.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getCommentText
     */
    public void setCommentText( String text ) throws java.lang.IllegalArgumentException {
        if ( text == null ) {
            throw new java.lang.IllegalArgumentException( "Null comment text entered.");
        }
        commentText = text;
        return;
    }
    
      /** 
     * Sets the time that the comment was set.
     *
     * 
     * @param time A Date representing the time that the comment was set.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getCommentTime
     */
    public void setCommentTime( Date time ) 
      throws java.lang.IllegalArgumentException {
        if ( time == null ) {
            throw new java.lang.IllegalArgumentException( "Null comment time entered.");
        }
        commentTime = time;
        return;
    }
    
    /** 
     * Sets the System ID of the user that set the comment.
     * 
     * @param system_Id A String representing the system ID of the user that set the comment.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getCommentSystemId
     */
    public void setCommentSystemId( String system_Id ) 
      throws java.lang.IllegalArgumentException {
        if ( system_Id == null ) {
            throw new java.lang.IllegalArgumentException( "Null comment system id entered.");
        }   
        commentSystemId = system_Id;
        return;
    }
    
    /**
     * Performs a deep copy of this Instance.
     * 
     * @return Object that represents a deep copy of this Instance.
     */
     public Object clone() {
        CommentValueImpl obj = null;
        try {
            obj = (CommentValueImpl)super.clone();
            obj.commentTime = (Date)commentTime.clone();
        }
        catch ( CloneNotSupportedException ex ) {
            // should never happen
            //System.out.println( "Problem cloning CommentValue." );
        }
        return obj;
    }    
    
    /**
     * Returns a boolean that indicates whether the contents of the
     * passed in CommentValueImpl instance are equal to the 
     * contents of this instance.
     *
     * @return boolean - indicating if the instances are equal.
     */
    public boolean equals ( Object anObject ) {
        boolean isEqual = false;
        if ( anObject instanceof CommentValue ) {
            CommentValueImpl objCommentVal = (CommentValueImpl)anObject;
            isEqual = ( Util.isEqual( commentText, objCommentVal.commentText ) && 
                        Util.isEqual( commentUserId, objCommentVal.commentUserId ) &&  
                        Util.isEqual( commentTime, objCommentVal.commentTime ) &&
                        Util.isEqual( commentSystemId, objCommentVal.commentSystemId ) );
        }
        return isEqual;    
    }
    
    /**
     * Returns a string representation of the class.
     *
     * @return String - representation of the class.
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer(200);
        buffer.append( Util.rightJustify(30,"commentText = ") + commentText + "\n");
        buffer.append( Util.rightJustify(30,"commentUserId = ") + commentUserId + "\n");
        buffer.append( Util.rightJustify(30,"commentTime = ") + Util.printObject( commentTime) );
        buffer.append(Util.rightJustify(30,"commentSystemId = ") + commentSystemId + "\n");

        return buffer.toString();
    }
    
}

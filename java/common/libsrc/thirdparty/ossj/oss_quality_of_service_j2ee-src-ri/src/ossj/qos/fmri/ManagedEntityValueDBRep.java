package ossj.qos.fmri;

import javax.oss.ManagedEntityValue;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ManagedEntityValueDBRep
 * 
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public interface ManagedEntityValueDBRep {

    /**
     * Sets the db representation of a managed entity value in a prepared statement.
     *
     * @param entity The ManagedEntityValue that contains the attribute's value.
     * @param pos The index position of the prepared statement variable
     * @param ps A PreparedStatement that will contain the db representation of the
     * populated attributes in the managed entity.
     * @returns Int The position of the next vacant position in the prepared statement.
     * @exception SQLException thrown if there is a problem setting the db
     * representation of the attribute.
     */
    public int setManagedEntityInPrepStatement( ManagedEntityValue entity, int pos, PreparedStatement ps )
    throws SQLException;

    /**
     * Sets the db representation of a managed entity value attribute in a prepared statement.
     *
     * @param entity The ManagedEntityValue that contains the attribute's value.
     * @param pos The index the vacant position in the prepared statement
     * @param ps A PreparedStatement that will contain the db representation of the attribute object.
     * @param attributeName The name of the attribute in the managedEntity to populate in the
     * prepared statement.
     * @returns Int The position of the next vacant position in the prepared statement.
     * @exception SQLException thrown if there is a problem setting the db
     * representation of the attribute.
     */
    public int setManagedEntityAttributeInPrepStatement( ManagedEntityValue entity, int pos, PreparedStatement ps,
    String attributeName )
    throws SQLException;

    /**
     * Sets the db representation of populated managed entity value attributes
     * in a prepared statement in a specified order.
     *
     * @param entity The ManagedEntityValue that contains the attribute's value.
     * @param pos The index the vacant position in the prepared statement
     * @param ps A PreparedStatement that will contain the db representation of the attribute object.
     * @param attributeName The name of the attribute in the managedEntity to populate in the
     * prepared statement.
     * @returns Int The position of the next vacant position in the prepared statement.
     * @exception SQLException thrown if there is a problem setting the db
     * representation of the attribute.
     */
    public int setManagedEntityInPrepStatementByOrder( ManagedEntityValue entity, int pos, PreparedStatement ps,
    String[] order )
    throws SQLException;
    
    /** 
     * Sets the db representation of the attribute in a PreparedStatement.
     *
     * @param ps A PreparedStatement that will contain the db representation of 
     * the attribute object.
     * @param pos The attribute value's position in the prepared statement.
     * @param object The Object that contains the attribute's value.
     * @exception SQLException thrown if there is a problem setting the db
     * representation of the attribute.
     */
    public int setObjectInPrepStatement ( String name, int pos, PreparedStatement ps, Object attrValue )
    throws SQLException;

    /**
     * Sets the attributes of a ManagedEntity value from the DB representation
     * in a ResultSet.
     *
     * @param rs A ResultSet.
     * @param names An array containing the attribute names to populate.
     * @exception SQLException thrown if there is a problem setting the managed entity
     * attributes from their db representation.
     */
    public ManagedEntityValue setManagedEntityAttrsFromDBRep( ResultSet rs, String[] names )
    throws SQLException;

    /**
     * Sets all the attributes of a ManagedEntity value from the DB representation
     * in a ResultSet.
     *
     * @param rs A ResultSet.
     * @param entity The ManagedEntity to populate.
     * @exception SQLException thrown if there is a problem setting the managed entity
     * attributes from its db representation.
     */
    public ManagedEntityValue setManagedEntityFromDBRep( ResultSet rs )
    throws SQLException;

    /**
     * Returns the database name of the ManagedEntity value attribute.
     *
     * @param name The name of the ManagedEntity value attribute.
     * @return String A string that represents the database name of the attribute
     * @exception IllegalArgumentException thrown if the name doesn't exist.
     */
    public String getDBAttributeName ( String name )
    throws IllegalArgumentException;

    /**
     * Returns the database names for the populated ManagedEntity value attributes.
     *
     * @param entity A ManagedEntity.
     * @return String[] An array of strings that represents the database names of the
     * populated attributes.
     * @exception IllegalArgumentException thrown if a db name doesn't exist.
     */
    public String[] getDBAttributeNames ( ManagedEntityValue entity )
    throws IllegalArgumentException;
    
    public String getTableName();
}

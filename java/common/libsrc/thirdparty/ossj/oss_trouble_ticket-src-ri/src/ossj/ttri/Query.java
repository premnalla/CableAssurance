package ossj.ttri;


/**
 * Query base interface
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
interface Query extends Operation, Cloneable {
    public void setSelectedAttributeNames(String[] attrNames);

    public void executeQuery();
}

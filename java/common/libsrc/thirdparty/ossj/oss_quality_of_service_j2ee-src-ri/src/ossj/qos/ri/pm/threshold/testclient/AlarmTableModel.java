/*
 * Copyright: Copyright © 2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */
package ossj.qos.ri.pm.threshold.testclient;

import javax.swing.table.*;
import java.util.ArrayList;

/**
 * Table model for alarms.
 * @author Henrik Lindstrom, Ericsson
 */
class AlarmTableModel extends AbstractTableModel {

    /**
     * Column names.
     */
    private static String[] COLUMN_NAMES = {
                                             "Severity",
                                             "Time",
                                             "Object",
                                             "Alarm" };

    /**
     * Table data model.
     */
    private ArrayList dataModel = null;

    /**
     * Create a new measurement table model.
     */
    public AlarmTableModel() {
        dataModel = new ArrayList();
    }

    public String getColumnName(int column) {
        if ( column>= 0 && column<COLUMN_NAMES.length ) {
            return COLUMN_NAMES[column];
        } else {
            return "";
        }
    }

    /**
     * Add a new row to the data model.
     */
    void addRow( ArrayList dataRow ) {
        if ( dataRow.size()<COLUMN_NAMES.length ) {
            throw new IllegalArgumentException( "dataRow.size()<COLUMN_NAMES.length" );
        }
        dataModel.add( dataRow );
        int insertedAt = dataModel.size()-1;
        this.fireTableRowsInserted(insertedAt, insertedAt );
    }

    /**
     * Returns the number of columns.
     */
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    public Object getValueAt(int row, int column) throws ArrayIndexOutOfBoundsException  {
        if ( row>=0 && row<dataModel.size() && column>=0 && column<COLUMN_NAMES.length ) {
            return ((ArrayList)dataModel.get(row)).get(column);
        } else {
            throw new ArrayIndexOutOfBoundsException(
                "!(row>=0 && row<dataModel.size() && column>=0 && column<COLUMN_NAMES.length)" );
        }

        /**@todo: implement this javax.swing.table.AbstractTableModel abstract method*/
    }

    public int getRowCount() {
        return dataModel.size();
        /**@todo: implement this javax.swing.table.AbstractTableModel abstract method*/
    }
}
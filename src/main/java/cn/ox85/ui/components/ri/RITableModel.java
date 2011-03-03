package cn.ox85.ui.components.ri;

import javax.swing.table.DefaultTableModel;

/**
 * @author alec zhang
 */
public class RITableModel extends DefaultTableModel {

    public RITableModel() {
        super(RITable.COLUMNS, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}

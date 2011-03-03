package cn.ox85.ui.components.bbr;

import javax.swing.table.DefaultTableModel;

/**
 * @author alec zhang
 */
public class BBRTableModel extends DefaultTableModel {

    public BBRTableModel() {
        super(BBRTable.COLUMNS, 0);
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
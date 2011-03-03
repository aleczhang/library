package cn.ox85.ui.components.reader;

import javax.swing.table.DefaultTableModel;

/**
 * @author alec zhang
 */
public class ReaderTableModel extends DefaultTableModel {

    public ReaderTableModel() {
        super(ReaderTable.COLUMNS, 0);
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

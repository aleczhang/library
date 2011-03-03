package cn.ox85.ui.components.book;

import javax.swing.table.DefaultTableModel;

/**
 * @author alec zhang
 */
public class BookTableModel extends DefaultTableModel {

    public BookTableModel() {
        super(BookTable.COLUMNS, 0);
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
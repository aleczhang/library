package cn.ox85.ui.components.user;

import javax.swing.table.DefaultTableModel;

/**
 * @author alec zhang
 */
public class UserTableModel extends DefaultTableModel {

    public UserTableModel() {
        super(UserTable.COLUMNS, 0);
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

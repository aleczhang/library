package cn.ox85.ui.components.user;

import cn.ox85.models.User;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.UserMap;
import cn.ox85.ui.beans.UserBean;
import cn.ox85.ui.util.BundleUtil;
import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * @author alec zhang
 */
public class UserTable extends JTable implements KeyListener, MouseListener {
    static final String[] COLUMNS = {
            BundleUtil.getString("CLMN_UserId"),
            BundleUtil.getString("CLMN_Username"),
            BundleUtil.getString("CLMN_UserType"),
            BundleUtil.getString("CLMN_UserCreateDate")
    };

    private DefaultTableModel dataModel_;

    public UserTable(UserTableModel model) {
        super();
        dataModel_ = model;
        setModel(dataModel_);
        new ShowResultWork().execute();
    }

    public void updateUserTable() {
        new ShowResultWork().execute();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //TODO: implement method
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //TODO: implement method
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //TODO: implement method
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //TODO: implement method
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //TODO: implement method
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //TODO: implement method
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //TODO: implement method
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //TODO: implement method
    }

    private class ShowResultWork extends SwingWorker<Object[][], Object> {

        @Override
        protected Object[][] doInBackground() throws Exception {
            SqlSession session = ConnectionFactory.getSession().openSession();
            java.util.List<User> users = new ArrayList<User>();
            try {
                UserMap userDAO = session.getMapper(UserMap.class);
                users = userDAO.selectUsers();
            } finally {
                session.close();
            }
            return convertTo2DArray(users);
        }

        @Override
        protected void done() {
            int rowCount = UserTable.this.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dataModel_.removeRow(i);
            }
            Object[][] data = new Object[0][];
            try {
                data = get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            for (Object[] aData : data) {
                dataModel_.addRow(aData);
            }
        }
    }

    private static Object[][] convertTo2DArray(java.util.List<User> users) {
        int rowSize = users.size();
        int columnSize = COLUMNS.length;
        Object[][] data = new Object[rowSize][columnSize];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < rowSize; i++) {
            UserBean user = new UserBean(users.get(i));
            data[i][0] = user.getId();
            data[i][1] = user.getUsername();
            data[i][2] = user.getType();
            data[i][3] = format.format(user.getDate());
        }
        return data;
    }
}

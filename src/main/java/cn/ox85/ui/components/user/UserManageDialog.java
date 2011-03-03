package cn.ox85.ui.components.user;

import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.UserMap;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.IconLoader;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author alec zhang
 */
public class UserManageDialog extends JDialog {
    private static final Logger logger_ = LoggerFactory.getLogger(UserManageDialog.class);

    private UserTable userTable_;
    private UserTableModel userModel_;
    private JButton addButton_;
    private JButton modifyButton_;
    private JButton deleteButton_;

    public UserManageDialog(Frame owner) {
        super(owner);
        initComponents();
        wireEvents();
    }

    private void initComponents() {
        JScrollPane scrollPane = new JScrollPane();
        userModel_ = new UserTableModel();
        userTable_ = new UserTable(userModel_);
        scrollPane.setViewportView(userTable_);
        addButton_ = new JButton(BundleUtil.getString("BTN_Add"));
        modifyButton_ = new JButton(BundleUtil.getString("BTN_Modify"));
        deleteButton_ = new JButton(BundleUtil.getString("BTN_Delete"));

        Container contentPane = getContentPane();
        contentPane.setLayout(new FormLayout(
                "$lcgap, 480px:grow, $lcgap",
                "$lgap, 300px:grow, $lgap"
        ));

        FormLayout layout = new FormLayout(
                "default:grow, $lcgap, 80px",
                "3*(default, $lgap), default:grow");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        builder.add(addButton_, cc.xy(3, 1));
        builder.add(modifyButton_, cc.xy(3, 3));
        builder.add(deleteButton_, cc.xy(3, 5));
        builder.add(scrollPane, cc.xywh(1, 1, 1, 7));
        builder.setBorder(new TitledBorder(BundleUtil.getString("TITLE_User")));
        contentPane.add(builder.getPanel(), cc.xy(2, 2));

        setIconImage(IconLoader.getIcon32("main.png").getImage());
        setTitle(BundleUtil.getString("TITLE_UserManage"));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        pack();
        setLocationRelativeTo(getOwner());
    }

    private void wireEvents() {
        addButton_.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new UserAddDialog(UserManageDialog.this).setVisible(true);
            }
        });
        modifyButton_.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int row = userTable_.getSelectedRow();
                if (row < 0) return;
                int userId = (Integer) userModel_.getValueAt(row, 0);
                new UserEditDialog(UserManageDialog.this, userId).setVisible(true);
            }
        });
        deleteButton_.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: enrich delete action
                int[] rows = userTable_.getSelectedRows();
                if (rows.length == 0) return;
                java.util.List<Integer> ids = new ArrayList<Integer>();
                for (int row : rows) {
                    String user = (String) userModel_.getValueAt(row, 1);
                    if ("admin".equals(user)) {
                        logger_.warn("The super administrator \"admin\" is not allowed to be deleted.");
                        continue;
                    }
                    ids.add((Integer) userModel_.getValueAt(row, 0));
                }
                if (ids.size() == 0) return;
                SqlSession session = ConnectionFactory.getSession().openSession();
                int count;
                try {
                    UserMap userDAO = session.getMapper(UserMap.class);
                    count = userDAO.deleteUsersById(ids);
                    session.commit();
                } finally {
                    session.close();
                }
                if (count > 0) {
                    userTable_.removeRowSelectionInterval(rows[0], rows[rows.length - 1]);
                }
                updateUserTable();
            }
        });
    }

    public void updateUserTable() {
        userTable_.updateUserTable();
    }
}

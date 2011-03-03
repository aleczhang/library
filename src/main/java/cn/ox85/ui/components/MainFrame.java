package cn.ox85.ui.components;

import cn.ox85.ui.ActionConstant;
import cn.ox85.ui.components.bbr.*;
import cn.ox85.ui.components.book.BookAddDialog;
import cn.ox85.ui.components.book.BookManagePanel;
import cn.ox85.ui.components.book.BookNavigationPanel;
import cn.ox85.ui.components.reader.ReaderAddDialog;
import cn.ox85.ui.components.reader.ReaderManagePanel;
import cn.ox85.ui.components.reader.ReaderNavigationPanel;
import cn.ox85.ui.components.ri.RIManagePanel;
import cn.ox85.ui.components.ri.RINavigationPanel;
import cn.ox85.ui.components.user.UserManageDialog;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.IconLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame implements ActionListener {

    private JPanel statusBar_;
    private Container pane_;
    private JSplitPane splitPane_;
    private ToolBar toolBar_;
    private boolean exited_ = false;

    public MainFrame() {
        super();
        setTitle(BundleUtil.getString("TITLE"));
        this.setIconImage(IconLoader.getIcon32("main.png").getImage());
        pane_ = getContentPane();
        pane_.setLayout(new BorderLayout());
        initComponents();
        InitialFocusSetter.setInitialFocus(this, pane_);
    }

    private void initComponents() {
        configureToolBar();
        configMenuBar();
        configureStatusBar();
        splitPane_ = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        ManagePanel managePanel = new RIManagePanel();
        setContainedComponents(
                new RINavigationPanel(managePanel.getFilterChangeListener()),
                managePanel
        );
        pane_.add(splitPane_, BorderLayout.CENTER);
    }

    private void configMenuBar() {
        cn.ox85.ui.components.MenuBar menuBar = new cn.ox85.ui.components.MenuBar(this);
        setJMenuBar(menuBar);
    }

    private void configureToolBar() {
        toolBar_ = new ToolBar(this);
        pane_.add(toolBar_, BorderLayout.NORTH);
    }

    private void configureStatusBar() {
        statusBar_ = new StatusBar();
        pane_.add(statusBar_, BorderLayout.SOUTH);
    }

    private void exit() {
        dispose();
    }

    @Override
    public void dispose() {
        int result = JOptionPane.showConfirmDialog(null,
                BundleUtil.getString("MSG_Exit"),
                BundleUtil.getString("TITLE_Exit"),
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            super.dispose();
            exited_ = true;
        }
    }

    private void setContainedComponents(JComponent navigation, JComponent managePanel) {
        splitPane_.setRightComponent(managePanel);
        navigation.setMinimumSize(new Dimension(280, 300));
        splitPane_.setLeftComponent(navigation);
    }

    public ToolBar getToolBar() {
        return toolBar_;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (ActionConstant.SYSTEM_EXIT_CMD.equals(cmd)) {
            exit();
        } else if (ActionConstant.BOOK_BORROW_CMD.equals(cmd)) {
            BookBorrowDialog bookBorrow = new BookBorrowDialog(this);
            bookBorrow.setVisible(true);
        } else if (ActionConstant.BOOK_RETURN_CMD.equals(cmd)) {
            BookReturnDialog bookReturn = new BookReturnDialog(this);
            bookReturn.setVisible(true);
        } else if (ActionConstant.BOOK_ADD_CMD.equals(cmd)) {
            BookAddDialog bookAdd = new BookAddDialog(this);
            bookAdd.setVisible(true);
        } else if (ActionConstant.READER_ADD_CMD.equals(cmd)) {
            ReaderAddDialog readerAdd = new ReaderAddDialog(this);
            readerAdd.setVisible(true);
        } else if (ActionConstant.DATA_BOOK_CMD.equals(cmd)) {
            //TODO: optimize init Book Manage panel
            BookManagePanel bookManagePanel = new BookManagePanel();
            setContainedComponents(
                    new BookNavigationPanel(bookManagePanel.getFilterChangeListener()),
                    bookManagePanel
            );
        } else if (ActionConstant.DATA_READER_CMD.equals(cmd)) {
            //TODO: optimize init Reader Manage panel
            ReaderManagePanel readerManagePanel = new ReaderManagePanel();
            setContainedComponents(
                    new ReaderNavigationPanel(readerManagePanel.getFilterChangeListener()),
                    readerManagePanel
            );
        } else if (ActionConstant.DATA_BOOK_BORROW_CMD.equals(cmd)) {
            //TODO: optimize init Book Borrow Manage panel
            BBRManagePanel bbrManagePanel = new BBRManagePanel();
            setContainedComponents(
                    new BBRNavigationPanel(bbrManagePanel.getFilterChangeListener()),
                    bbrManagePanel
            );
        } else if (ActionConstant.DATA_READER_IN_CMD.equals(cmd)) {
            //TODO: optimize init Reader In Manage panel
            RIManagePanel riManagePanel = new RIManagePanel();
            setContainedComponents(
                    new RINavigationPanel(riManagePanel.getFilterChangeListener()),
                    riManagePanel
            );
        } else if (ActionConstant.REPORT_BBR_CMD.equals(cmd)) {
            BBRReportDialog reportDialog = new BBRReportDialog(this);
            reportDialog.setVisible(true);
        } else if (ActionConstant.USER_MANAGE_CMD.equals(cmd)) {
            UserManageDialog userManageDialog = new UserManageDialog(this);
            userManageDialog.setVisible(true);
        } else if (ActionConstant.DATABASE_MANAGE_CMD.equals(cmd)) {
            DatabaseManageDialog databaseManageDialog = new DatabaseManageDialog(this);
            databaseManageDialog.setVisible(true);
        } else if (ActionConstant.USER_RELOGING_CMD.equals(cmd)) {
            exit();
            if (exited_) {
                LoginFrame login = new LoginFrame();
                login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                login.setVisible(true);
            }
        }
    }

    private static class InitialFocusSetter {
        public static void setInitialFocus(Window w, Component c) {
            w.addWindowListener(new FocusSetter(c));
        }
    }

    private static class FocusSetter extends WindowAdapter {
        Component initComp_;

        FocusSetter(Component c) {
            initComp_ = c;
        }

        public void windowOpened(WindowEvent e) {
            initComp_.requestFocus();
            e.getWindow().removeWindowListener(this);
        }
    }
}
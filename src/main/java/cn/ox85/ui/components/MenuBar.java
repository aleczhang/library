package cn.ox85.ui.components;

import cn.ox85.ui.ActionConstant;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.IconLoader;

import javax.swing.*;

/**
 * @author alec zhang
 */
public class MenuBar extends JMenuBar {
    private MainFrame frame_;

    private JMenuItem borrowBookItem_;
    private JMenuItem returnBookItem_;
    private JMenuItem addBookItem_;
    private JMenuItem addReaderItem_;

    private JMenuItem bookItem_;
    private JMenuItem readerItem_;
    private JMenuItem bookBorrowItem_;
    private JMenuItem readerInItem_;

    private JMenuItem reportBookBorrowItem_;
    private JMenuItem reportReaderInItem_;

    private JMenuItem userItem_;
    private JMenuItem databaseItem_;
    private JMenuItem reloginItem_;
    private JMenuItem exitItem_;

    private JMenuItem aboutItem_;

    public MenuBar(MainFrame frame) {
        super();
        frame_ = frame;
        initComponents();
    }

    private void initComponents() {
        buildMenus();
        buildActions();
    }

    private void buildMenus() {
        JMenu processMenu = new JMenu(BundleUtil.getString("MENU_Process"));
        processMenu.setMnemonic('P');
        borrowBookItem_ = new JMenuItem(BundleUtil.getString("MENU_Process_BorrowBook"));
        borrowBookItem_.setIcon(IconLoader.getIcon16("book_borrow.png"));
        returnBookItem_ = new JMenuItem(BundleUtil.getString("MENU_Process_ReturnBook"));
        returnBookItem_.setIcon(IconLoader.getIcon16("book_return.png"));
        addBookItem_ = new JMenuItem(BundleUtil.getString("MENU_Process_AddBook"));
        addBookItem_.setIcon(IconLoader.getIcon16("book_add.png"));
        addReaderItem_ = new JMenuItem(BundleUtil.getString("MENU_Process_AddReader"));
        addReaderItem_.setIcon(IconLoader.getIcon16("reader_add.png"));
        processMenu.add(borrowBookItem_);
        processMenu.add(returnBookItem_);
        processMenu.add(new JSeparator());
        processMenu.add(addBookItem_);
        processMenu.add(addReaderItem_);
        add(processMenu);

        JMenu dataMenu = new JMenu(BundleUtil.getString("MENU_Data"));
        dataMenu.setMnemonic('I');
        bookItem_ = new JMenuItem(BundleUtil.getString("MENU_Data_Book"));
        bookItem_.setIcon(IconLoader.getIcon16("book.png"));
        readerItem_ = new JMenuItem(BundleUtil.getString("MENU_Data_Reader"));
        readerItem_.setIcon(IconLoader.getIcon16("reader.png"));
        bookBorrowItem_ = new JMenuItem(BundleUtil.getString("MENU_Data_BookBorrow"));
        bookBorrowItem_.setIcon(IconLoader.getIcon16("book_borrow_data.png"));
        readerInItem_ = new JMenuItem(BundleUtil.getString("MENU_Data_ReaderIn"));
        readerInItem_.setIcon(IconLoader.getIcon16("reader_in_data.png"));
        dataMenu.add(bookItem_);
        dataMenu.add(readerItem_);
        dataMenu.add(new JSeparator());
        dataMenu.add(bookBorrowItem_);
        dataMenu.add(new JSeparator());
        dataMenu.add(readerInItem_);
        add(dataMenu);

        JMenu reportMenu = new JMenu(BundleUtil.getString("MENU_Report"));
        reportMenu.setMnemonic('R');
        reportBookBorrowItem_ = new JMenuItem(BundleUtil.getString("MENU_Report_BookBorrow"));
        reportReaderInItem_ = new JMenuItem(BundleUtil.getString("MENU_Report_ReaderIn"));
        reportMenu.add(reportBookBorrowItem_);
        reportMenu.add(reportReaderInItem_);
        add(reportMenu);

        JMenu systemMenu = new JMenu(BundleUtil.getString("MENU_System"));
        systemMenu.setMnemonic('S');
        userItem_ = new JMenuItem(BundleUtil.getString("MENU_System_User"));
        userItem_.setIcon(IconLoader.getIcon16("users.png"));
        databaseItem_ = new JMenuItem(BundleUtil.getString("MENU_System_Database"));
        databaseItem_.setIcon(IconLoader.getIcon16("database.png"));
        reloginItem_ = new JMenuItem(BundleUtil.getString("MENU_System_Relogin"));
        exitItem_ = new JMenuItem(BundleUtil.getString("MENU_System_Exit"));
        exitItem_.setIcon(IconLoader.getIcon16("exit.png"));
        systemMenu.add(userItem_);
        systemMenu.add(new JSeparator());
        systemMenu.add(databaseItem_);
        systemMenu.add(new JSeparator());
        systemMenu.add(reloginItem_);
        systemMenu.add(exitItem_);
        add(systemMenu);

        JMenu helpMenu = new JMenu(BundleUtil.getString("MENU_Help"));
        helpMenu.setMnemonic('H');
        aboutItem_ = new JMenuItem(BundleUtil.getString("MENU_Help_About"));
        helpMenu.add(aboutItem_);
        add(helpMenu);
    }

    private void buildActions() {
        addAction(borrowBookItem_, ActionConstant.BOOK_BORROW_CMD);
        addAction(returnBookItem_, ActionConstant.BOOK_RETURN_CMD);
        addAction(addBookItem_, ActionConstant.BOOK_ADD_CMD);
        addAction(addReaderItem_, ActionConstant.READER_ADD_CMD);

        addAction(bookItem_, ActionConstant.DATA_BOOK_CMD);
        addAction(readerItem_, ActionConstant.DATA_READER_CMD);
        addAction(bookBorrowItem_, ActionConstant.DATA_BOOK_BORROW_CMD);
        addAction(readerInItem_, ActionConstant.DATA_READER_IN_CMD);
        bookItem_.addActionListener(frame_.getToolBar());
        readerItem_.addActionListener(frame_.getToolBar());
        bookBorrowItem_.addActionListener(frame_.getToolBar());
        readerInItem_.addActionListener(frame_.getToolBar());

        addAction(reportBookBorrowItem_, ActionConstant.REPORT_BBR_CMD);
        addAction(reportReaderInItem_, ActionConstant.REPORT_RI_CMD);

        addAction(userItem_, ActionConstant.USER_MANAGE_CMD);
        addAction(databaseItem_, ActionConstant.DATABASE_MANAGE_CMD);
        addAction(reloginItem_, ActionConstant.USER_RELOGING_CMD);
        addAction(exitItem_, ActionConstant.SYSTEM_EXIT_CMD);
    }

    private void addAction(JMenuItem item, String command) {
        item.setActionCommand(command);
        item.addActionListener(frame_);
    }
}

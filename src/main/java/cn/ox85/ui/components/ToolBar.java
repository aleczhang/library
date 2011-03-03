package cn.ox85.ui.components;

import cn.ox85.ui.ActionConstant;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.IconLoader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author alec zhang
 */
public class ToolBar extends JToolBar implements ActionListener {
    private MainFrame frame_;

    private JButton borrowBookButton_;
    private JButton returnBookButton_;
    private JButton addBookButton_;
    private JButton addReaderButton_;
    private JToggleButton dataBookButton_;
    private JToggleButton dataReaderButton_;
    private JToggleButton dataBookBorrowButton_;
    private JToggleButton dataReaderInButton_;
    private JButton exitButton_;

    public ToolBar(MainFrame frame) {
        super();
        frame_ = frame;
        initComponents();
    }

    private void initComponents() {
        borrowBookButton_ = buildButton("BTN_BorrowBook", "book_borrow.png", "BTN_BorrowBook");
        borrowBookButton_.setActionCommand(ActionConstant.BOOK_BORROW_CMD);
        borrowBookButton_.addActionListener(frame_);
        returnBookButton_ = buildButton("BTN_ReturnBook", "book_return.png", "BTN_ReturnBook");
        returnBookButton_.setActionCommand(ActionConstant.BOOK_RETURN_CMD);
        returnBookButton_.addActionListener(frame_);

        addBookButton_ = buildButton("BTN_AddBook", "book_add.png", "BTN_AddBook");
        addBookButton_.setActionCommand(ActionConstant.BOOK_ADD_CMD);
        addBookButton_.addActionListener(frame_);

        addReaderButton_ = buildButton("BTN_AddReader", "reader_add.png", "BTN_AddReader");
        addReaderButton_.setActionCommand(ActionConstant.READER_ADD_CMD);
        addReaderButton_.addActionListener(frame_);

        dataBookButton_ = buildToggleButton("BTN_DataBook", "book.png", "BTN_DataBook");
        dataBookButton_.setActionCommand(ActionConstant.DATA_BOOK_CMD);
        dataBookButton_.addActionListener(frame_);

        dataReaderButton_ = buildToggleButton("BTN_DataReader", "reader.png", "BTN_DataReader");
        dataReaderButton_.setActionCommand(ActionConstant.DATA_READER_CMD);
        dataReaderButton_.addActionListener(frame_);

        dataBookBorrowButton_ = buildToggleButton("BTN_DataBookBorrow", "book_borrow_data.png", "BTN_DataBookBorrow");
        dataBookBorrowButton_.setActionCommand(ActionConstant.DATA_BOOK_BORROW_CMD);
        dataBookBorrowButton_.addActionListener(frame_);

        dataReaderInButton_ = buildToggleButton("BTN_DataReaderIn", "reader_in_data.png", "BTN_DataReaderIn");
        dataReaderInButton_.setActionCommand(ActionConstant.DATA_READER_IN_CMD);
        dataReaderInButton_.setSelected(true);
        dataReaderInButton_.addActionListener(frame_);

        ButtonGroup group = new ButtonGroup();
        group.add(dataBookButton_);
        group.add(dataReaderButton_);
        group.add(dataBookBorrowButton_);
        group.add(dataReaderInButton_);

        exitButton_ = buildButton("BTN_Exit", "exit.png", "BTN_Exit");
        exitButton_.setActionCommand(ActionConstant.SYSTEM_EXIT_CMD);
        exitButton_.addActionListener(frame_);

        add(borrowBookButton_);
        add(returnBookButton_);
        addSeparator();
        add(addBookButton_);
        add(addReaderButton_);
        addSeparator();
        add(dataBookButton_);
        add(dataReaderButton_);
        add(dataBookBorrowButton_);
        add(dataReaderInButton_);
        addSeparator();
        add(exitButton_);
    }

    private static JButton buildButton(String name, String icon, String toolTip) {
        JButton button = new JButton(BundleUtil.getString(name),
                IconLoader.getIcon64(icon));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setToolTipText(BundleUtil.getString(toolTip));
        return button;
    }

    private static JToggleButton buildToggleButton(String name, String icon, String toolTip) {
        JToggleButton button = new JToggleButton(BundleUtil.getString(name),
                IconLoader.getIcon64(icon));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setToolTipText(BundleUtil.getString(toolTip));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (ActionConstant.DATA_BOOK_CMD.equals(cmd)) {
            if (!dataBookButton_.isSelected()) {
                dataBookButton_.setSelected(true);
            }
        } else if (ActionConstant.DATA_READER_CMD.equals(cmd)) {
            if (!dataReaderButton_.isSelected()) {
                dataReaderButton_.setSelected(true);
            }
        } else if (ActionConstant.DATA_BOOK_BORROW_CMD.equals(cmd)) {
            if (!dataBookBorrowButton_.isSelected()) {
                dataBookBorrowButton_.setSelected(true);
            }
        } else if (ActionConstant.DATA_READER_IN_CMD.equals(cmd)) {
            if (!dataReaderInButton_.isSelected()) {
                dataReaderInButton_.setSelected(true);
            }
        }
    }
}

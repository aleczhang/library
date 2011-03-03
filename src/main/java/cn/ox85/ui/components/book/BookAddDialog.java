package cn.ox85.ui.components.book;

import cn.ox85.ui.beans.BookBean;
import cn.ox85.ui.binding.BindingModel;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.validation.BookAddValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * @author alec zhang
 */
public class BookAddDialog extends AbstractBookDialog {
    private static final Logger logger_ = LoggerFactory.getLogger(BookAddDialog.class);

    public BookAddDialog(Frame owner) {
        super(owner);
        setTitle(BundleUtil.getString("TITLE_BookAdd"));
    }

    @Override
    protected BindingModel<BookBean> initBindingModel() {
        return new BindingModel<BookBean>(new BookBean(), new BookAddValidator());
    }

    @Override
    protected void initWidgetStatus() {
        //Do nothing
    }

    @Override
    protected boolean commitBook() {
        BookBean bookBean = bindingModel_.getBean();
        try {
            return bookBean.insertBook() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this, "Fail to add book.",
                    BundleUtil.getString("TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
            logger_.error("Fail to add book.", e);
            return false;
        }
    }
}

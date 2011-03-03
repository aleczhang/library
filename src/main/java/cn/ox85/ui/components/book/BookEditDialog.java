package cn.ox85.ui.components.book;

import cn.ox85.models.Book;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.BookMap;
import cn.ox85.ui.beans.BookBean;
import cn.ox85.ui.binding.BindingModel;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.validation.BookValidator;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

/**
 * @author alec zhang
 */
public class BookEditDialog extends AbstractBookDialog {
    private static final Logger logger_ = LoggerFactory.getLogger(BookEditDialog.class);

    public BookEditDialog(Frame owner, int bookId) {
        super(owner);
        setTitle(BundleUtil.getString("TITLE_BookEdit"));
        SqlSession session = ConnectionFactory.getSession().openSession();
        Book book;
        try {
            BookMap bookMap = session.getMapper(BookMap.class);
            book = bookMap.selectBookById(bookId);
        } finally {
            session.close();
        }
        try {
            pubDatePicker_.setDate(book.getPubDate());
        } catch (PropertyVetoException e) {
            logger_.error("Fail to initialize publish date.", e);
        }
        priceSpinner_.setValue(book.getPrice());
        editionSpinner_.setValue(book.getEdition());
        pageNumSpinner_.setValue(book.getPageNum());
        bindingModel_.setBean(new BookBean(book));
    }

    @Override
    protected BindingModel<BookBean> initBindingModel() {
        return new BindingModel<BookBean>(new BookBean(), new BookValidator());
    }

    @Override
    protected void initWidgetStatus() {
        barCodeTextField_.setEditable(false);
    }

    @Override
    protected boolean commitBook() {
        BookBean bookBean = bindingModel_.getBean();
        try {
            return bookBean.updateBook() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this, "Fail to edit book.",
                    BundleUtil.getString("TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
            logger_.error("Fail to edit book.", e);
            return false;
        }
    }
}

package cn.ox85.ui.validation;

import cn.ox85.ui.beans.BookBean;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.SimpleValidationSupport;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * @author alec zhang
 */
public class BookValidator implements Validator<BookBean> {
    @Override
    public ValidationResult validate(BookBean bookBean) {
        SimpleValidationSupport support = new SimpleValidationSupport();
        if (ValidationUtils.isBlank(bookBean.getBarCode())) {
            support.addError(BookBean.PROPERTYNAME_BARCODE,
                    BundleUtil.getString("MSG_NoBlankBookBarCode"));
        }

        if (ValidationUtils.isBlank(bookBean.getIsbn())) {
            support.addError(BookBean.PROPERTYNAME_ISBN,
                    BundleUtil.getString("MSG_NoBlankBookISBN"));
        }
        if (ValidationUtils.isBlank(bookBean.getName())) {
            support.addError(BookBean.PROPERTYNAME_NAME,
                    BundleUtil.getString("MSG_NoBlankBookName"));
        }
        if (ValidationUtils.isBlank(bookBean.getPublisher())) {
            support.addError(BookBean.PROPERTYNAME_PUBLISHER,
                    BundleUtil.getString("MSG_NoBlankBookPublisher"));
        }
        if (ValidationUtils.isBlank(bookBean.getAuthor())) {
            support.addError(BookBean.PROPERTYNAME_AUTHOR,
                    BundleUtil.getString("MSG_NoBlankBookAuthor"));
        }
        if (ValidationUtils.isBlank(bookBean.getCallNum())) {
            support.addError(BookBean.PROPERTYNAME_CALLNUM,
                    BundleUtil.getString("MSG_NoBlankBookCallNum"));
        }
        return support.getResult();
    }
}
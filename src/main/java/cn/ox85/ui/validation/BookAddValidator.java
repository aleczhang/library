package cn.ox85.ui.validation;

import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.BookMap;
import cn.ox85.ui.beans.BookBean;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.SimpleValidationSupport;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.util.ValidationUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * @author alec zhang
 */
public class BookAddValidator extends BookValidator {
    @Override
    public ValidationResult validate(BookBean bookBean) {
        SimpleValidationSupport support = new SimpleValidationSupport(super.validate(bookBean));
        String barCode = bookBean.getBarCode();
        if (!ValidationUtils.isBlank(bookBean.getBarCode())) {
            SqlSession session = ConnectionFactory.getSession().openSession();
            try {
                BookMap bookMap = session.getMapper(BookMap.class);
                int count = bookMap.selectBookCountByBarCode(barCode);
                if (count > 0) {
                    support.addError(BookBean.PROPERTYNAME_BARCODE,
                            BundleUtil.getString("MSG_DuplicateBookBarCode"));
                }
            } finally {
                session.close();
            }
        }
        return support.getResult();
    }
}

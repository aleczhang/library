package cn.ox85.ui.validation;

import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.ReaderMap;
import cn.ox85.ui.beans.ReaderBean;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.SimpleValidationSupport;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.util.ValidationUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * @author alec zhang
 */
public class ReaderAddValidator extends ReaderValidator {
    @Override
    public ValidationResult validate(ReaderBean readerBean) {
        SimpleValidationSupport support = new SimpleValidationSupport(super.validate(readerBean));
        String barCode = readerBean.getBarCode();
        if (!ValidationUtils.isBlank(barCode)) {
            SqlSession session = ConnectionFactory.getSession().openSession();
            try {
                ReaderMap readerMap = session.getMapper(ReaderMap.class);
                int count = readerMap.selectReaderCountByBarCode(barCode);
                if (count > 0) {
                    support.addError(ReaderBean.PROPERTYNAME_BARCODE,
                            BundleUtil.getString("MSG_DuplicateReaderBarCode"));
                }
            } finally {
                session.close();
            }
        }
        return support.getResult();
    }
}

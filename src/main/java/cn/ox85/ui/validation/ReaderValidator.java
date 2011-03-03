package cn.ox85.ui.validation;

import cn.ox85.ui.beans.ReaderBean;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.SimpleValidationSupport;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * @author alec zhang
 */
public class ReaderValidator implements Validator<ReaderBean> {
    @Override
    public ValidationResult validate(ReaderBean readerBean) {
        SimpleValidationSupport support = new SimpleValidationSupport();
        if (ValidationUtils.isBlank(readerBean.getBarCode())) {
            support.addError(ReaderBean.PROPERTYNAME_BARCODE,
                    BundleUtil.getString("MSG_NoBlankReaderBarCode"));
        }
        if (ValidationUtils.isBlank(readerBean.getName())) {
            support.addError(ReaderBean.PROPERTYNAME_NAME,
                    BundleUtil.getString("MSG_NoBlankReaderName"));
        }
        return support.getResult();
    }
}

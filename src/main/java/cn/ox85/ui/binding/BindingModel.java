package cn.ox85.ui.binding;

import cn.ox85.ui.util.IconFeedbackPanel;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.DefaultValidationResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author alec zhang
 */
public class BindingModel<B> extends PresentationModel<B> {
    private static final Logger logger_ = LoggerFactory.getLogger(BindingModel.class);
    private final ValidationResultModel validationResultModel_ = new DefaultValidationResultModel();
    private final Validator<B> validator_;

    public BindingModel(B bean, Validator<B> validator) {
        super(bean);
        validator_ = validator;
        initEventHandling();
    }

    private void initEventHandling() {
        PropertyChangeListener handler = new ValidationUpdateHandler();
        addBeanPropertyChangeListener(handler);
        getBeanChannel().addValueChangeListener(handler);
    }

    public ValidationResultModel getValidationResultModel() {
        return validationResultModel_;
    }

    public boolean hasValidationErrors() {
        return validationResultModel_.getResult().hasErrors();
    }

    public boolean hasValidationWarnings() {
        return validationResultModel_.getResult().hasWarnings();
    }

    public ValidationResult getUnexpectedResult() {
        return validationResultModel_.getResult().keyMap().get(null);
    }

    public JComponent getIconFeedbackPanel(JComponent root) {
        return new IconFeedbackPanel(validationResultModel_, root);
    }

    private void updateValidationResult() {
        B bean = getBean();
        ValidationResult result;
        try {
            result = validator_.validate(bean);
        } catch (Exception e) {
            result = new ValidationResult();
            result.addError(e.getMessage());
            logger_.error("Exception happens when validate.", e);
        }
        validationResultModel_.setResult(result);
    }

    @Override
    public void triggerCommit() {
        if (!isBuffering()) {
            updateValidationResult();
        }
        super.triggerCommit();
    }

    /**
     * Validates the order using an OrderValidator and
     * updates the validation result.
     */
    private final class ValidationUpdateHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateValidationResult();
        }

    }
}

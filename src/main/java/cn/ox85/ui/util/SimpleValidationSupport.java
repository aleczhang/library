package cn.ox85.ui.util;

import com.jgoodies.validation.Severity;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.message.SimpleValidationMessage;

/**
 * @author alec zhang
 */
public class SimpleValidationSupport {

    private ValidationResult defaultResult_;

    public SimpleValidationSupport() {
        this(new ValidationResult());
    }

    public SimpleValidationSupport(ValidationResult result) {
        defaultResult_ = result;
    }

    public void clearResult() {
        defaultResult_ = new ValidationResult();
    }

    public ValidationResult getResult() {
        return defaultResult_;
    }

    public void addWarning(String key, String text) {
        addWarning(defaultResult_, key, text);
    }

    public void addError(String key, String text) {
        addError(defaultResult_, key, text);
    }

    public static void addWarning(ValidationResult result, String key, String text) {
        result.add(createWarning(key, text));
    }

    public static void addError(ValidationResult result, String key, String text) {
        result.add(createError(key, text));
    }

    public static SimpleValidationMessage createWarning(String key, String text) {
        return create(Severity.WARNING, key, text);
    }

    public static SimpleValidationMessage createError(String key, String text) {
        return create(Severity.ERROR, key, text);
    }

    private static SimpleValidationMessage create(Severity severity, String key, String text) {
        return new SimpleValidationMessage(text, severity, key);
    }
}

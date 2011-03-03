package cn.ox85.ui.util;

import com.jgoodies.validation.Severity;

import javax.swing.*;

/**
 * @author alec zhang
 */
public final class ValidationIconFactory {
    private static final Icon SMALL_ERROR_ICON = IconLoader.getIcon16("error.png");
    private static final Icon SMALL_WARNING_ICON = IconLoader.getIcon16("warning.png");

    public static Icon getSmallIcon(Severity severity) {
        if (severity == Severity.ERROR) {
            return SMALL_ERROR_ICON;
        } else if (severity == Severity.WARNING) {
            return SMALL_WARNING_ICON;
        } else {
            return null;
        }
    }
}

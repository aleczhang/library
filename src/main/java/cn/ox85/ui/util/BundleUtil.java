package cn.ox85.ui.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class BundleUtil {
    private static Locale currLocale_ = Locale.getDefault();
    private static ResourceBundle resourceBundle_ =
            ResourceBundle.getBundle("cn.ox85.ui.resources.Resources", currLocale_);

    public static String getString(String key) {
        return resourceBundle_.getString(key);
    }

    public static String getString(String key, String... values) {
        MessageFormat mf = new MessageFormat(resourceBundle_.getString(key));
        mf.setLocale(currLocale_);
        return mf.format(values);
    }
}
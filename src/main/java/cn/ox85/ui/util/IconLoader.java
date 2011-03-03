package cn.ox85.ui.util;

import javax.swing.*;

public class IconLoader {
    private static final ClassLoader LOADER = Thread.currentThread().getContextClassLoader();
    private static final String ICON_PATH = "cn/ox85/ui/resources/";
    private static final String ICON64_PATH = "cn/ox85/ui/resources/icon64/";
    private static final String ICON32_PATH = "cn/ox85/ui/resources/icon32/";
    private static final String ICON16_PATH = "cn/ox85/ui/resources/icon16/";

    public static ImageIcon getIcon(String name) {
        return new ImageIcon(LOADER.getResource(ICON_PATH + name));
    }

    public static ImageIcon getIcon64(String name) {
        return new ImageIcon(LOADER.getResource(ICON64_PATH + name));
    }

    public static ImageIcon getIcon32(String name) {
        return new ImageIcon(LOADER.getResource(ICON32_PATH + name));
    }

    public static ImageIcon getIcon16(String name) {
        return new ImageIcon(LOADER.getResource(ICON16_PATH + name));
    }
}

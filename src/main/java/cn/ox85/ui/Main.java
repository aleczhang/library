package cn.ox85.ui;

import cn.ox85.ui.components.LoginFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class Main {
    public static final String VERSION = "0.1";
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logSystemInfo();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoginFrame login = new LoginFrame();
                login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                login.setVisible(true);
            }
        });
    }

    private static void logSystemInfo() {
        logger.info("*******************************************");
        logger.info("OX85 Library " + VERSION + " started.");
        logger.info("JAVA_HOME: " + System.getProperty("java.home"));
        logger.info("JDK version: " + System.getProperty("java.version"));
        logger.info("CLASS_PATH: " + System.getProperty("java.class.path"));
        logger.info("LIBRARY_PATH: " + System.getProperty("java.library.path"));
        logger.info("EXT_DIR: " + System.getProperty("java.ext.dirs"));
        logger.info("*******************************************");
    }
}
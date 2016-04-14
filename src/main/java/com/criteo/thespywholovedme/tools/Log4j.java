package com.criteo.thespywholovedme.tools;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;

public final class Log4j {

    private final static String pattern = "%d{yyyy/MM/dd HH:mm:ss} %-5p [%t] %x %C{1}: %m%n";
    private final static PatternLayout mainLayout = new PatternLayout(pattern);
    private final static Priority mainPriority = Level.INFO;

    public static void initialize() {
        ConsoleAppender console = new ConsoleAppender();
        console.setLayout(mainLayout);
        console.setThreshold(mainPriority);
        console.activateOptions();

        Logger.getRootLogger().removeAllAppenders();
        Logger.getRootLogger().addAppender(console);
    }
}
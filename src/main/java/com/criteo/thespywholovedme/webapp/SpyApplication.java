package com.criteo.thespywholovedme.webapp;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.criteo.thespywholovedme" })
public class SpyApplication {

    private static final Logger logger = LoggerFactory.getLogger(SpyApplication.class);

    /**
     * @param args
     */
    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(new Object[] { SpyApplication.class }, args);
        logger.info("beans detected:");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName: beanNames) {
            logger.info(beanName);
        }
    }
}

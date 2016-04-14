package com.criteo.thespywholovedme.webapp;

import java.io.File;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.criteo.thespywholovedme" })
public class SpyApplication {

    private static final Logger logger = LoggerFactory.getLogger(SpyApplication.class);

    public static final String PDF_DIR = "web/pdf-input";
    public static final String TXT_DIR = "web/txt-output";

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            ApplicationContext ctx = SpringApplication.run(new Object[] { SpyApplication.class }, args);
            logger.info("beans detected:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName: beanNames) {
                logger.info(beanName);
            }
        } catch (Throwable e) {
            logger.error("Error in Application", e);
        }
    }

    @Bean
    CommandLineRunner init() {
        return new CommandLineRunner() {

            @Override
            public void run(String... arg0) throws Exception {
                new File(PDF_DIR).mkdirs();
                new File(TXT_DIR).mkdirs();

            }
        };
    }
}

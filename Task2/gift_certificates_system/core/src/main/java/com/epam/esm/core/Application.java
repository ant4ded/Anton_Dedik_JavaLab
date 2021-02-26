package com.epam.esm.core;

import com.epam.esm.data_access.conf.RepositoryConfiguration;
import com.epam.esm.core.conf.ServiceConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ServiceConfiguration.class,
                RepositoryConfiguration.class);
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }
    }
}

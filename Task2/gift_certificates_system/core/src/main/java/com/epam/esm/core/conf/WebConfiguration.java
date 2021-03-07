package com.epam.esm.core.conf;

import com.epam.esm.data_access.conf.DataAccessConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@Import(DataAccessConfiguration.class)
@ComponentScan(basePackages = "com.epam.esm.core")
public class WebConfiguration implements WebMvcConfigurer {

}

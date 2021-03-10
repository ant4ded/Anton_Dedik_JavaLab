package com.epam.esm.core.conf;

import com.epam.esm.data_access.conf.DataAccessConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
@EnableWebMvc
@Import(DataAccessConfiguration.class)
@ComponentScan(basePackages = "com.epam.esm.core")
public class WebConfiguration {
    private static final String PROPERTIES_FILE_NAME = "exception_locale";

    private static final List<Locale> locales = Arrays.asList(
            new Locale(Locale.ENGLISH.getLanguage()),
            new Locale("ru"));

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setSupportedLocales(locales);
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        Locale.setDefault(Locale.ENGLISH);

        return localeResolver;
    }

    @Bean
    public ResourceBundleMessageSource exceptionLocaleSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename(PROPERTIES_FILE_NAME);
        source.setDefaultEncoding(StandardCharsets.UTF_8.name());
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }
}

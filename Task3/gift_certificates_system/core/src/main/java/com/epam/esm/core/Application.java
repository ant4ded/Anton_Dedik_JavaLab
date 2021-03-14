package com.epam.esm.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Override
//    public void onStartup(ServletContext servletContext) {
//        AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
//        webCtx.register(WebConfiguration.class);
//        webCtx.setServletContext(servletContext);
//
//        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(webCtx));
//        servlet.setLoadOnStartup(1);
//        servlet.addMapping("/");
//        servlet.setAsyncSupported(true);
//
//        FilterRegistration.Dynamic filter = servletContext.addFilter("encodingFilter",
//                new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true, true));
//        filter.setAsyncSupported(true);
//        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
//    }
}

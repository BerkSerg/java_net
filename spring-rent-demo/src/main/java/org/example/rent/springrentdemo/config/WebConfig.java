package org.example.rent.springrentdemo.config;

import lombok.SneakyThrows;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Objects;

public class WebConfig implements WebApplicationInitializer {

    @SneakyThrows
    @Override
    public void onStartup(ServletContext servletContext){

        ResourcePropertySource propertySource = new ResourcePropertySource("classpath:application.properties");

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.getEnvironment().setActiveProfiles(Objects.requireNonNull(propertySource.getProperty("application.profile")).toString());
        context.register(AppConfig.class);

        ContextLoaderListener loaderListener = new ContextLoaderListener(context);

        servletContext.addListener(loaderListener);
        ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        dispatcherServlet.setLoadOnStartup(1);
        dispatcherServlet.addMapping("/");

    }
}

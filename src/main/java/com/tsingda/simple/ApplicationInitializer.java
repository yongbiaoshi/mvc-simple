package com.tsingda.simple;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import ch.qos.logback.ext.spring.web.LogbackConfigListener;

import com.alibaba.druid.support.http.StatViewServlet;
import com.tsingda.simple.config.AppConfig;
import com.tsingda.simple.config.MvcConfig;

public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // servletContext.setInitParameter("spring.profiles.active", "dev");

        servletContext.setInitParameter("logbackConfigLocation", "classpath:${spring.profiles.active}/logback.xml");

        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter("UTF-8", true);
        FilterRegistration.Dynamic encodingFilterDynamic = servletContext.addFilter("characterEncodingFilter",
                encodingFilter);
        encodingFilterDynamic.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");

        servletContext.addListener(LogbackConfigListener.class);

        StatViewServlet statViewServlet = new StatViewServlet();
        Dynamic druidServletDynamic = servletContext.addServlet("DruidStatView", statViewServlet);
        druidServletDynamic.addMapping("/druid/*");

        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(AppConfig.class, MvcConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);

        Dynamic dispatcherDynamic = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcherDynamic.setAsyncSupported(true);
        dispatcherDynamic.addMapping("/");
        dispatcherDynamic.setLoadOnStartup(1);

    }

}

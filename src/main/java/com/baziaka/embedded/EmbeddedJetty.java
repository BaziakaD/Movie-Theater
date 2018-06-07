package com.baziaka.embedded;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import javax.servlet.MultipartConfigElement;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.EnumSet;

import static org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME;

public class EmbeddedJetty extends Server {

    private static final Logger LOGGER = LogManager.getLogger(EmbeddedJetty.class);

    public EmbeddedJetty(WebApplicationContext context, int port, String profile) throws Exception {

        super(port);

        ServletContextHandler servletContextHandler = getServletContextHandler(context);
        servletContextHandler.setInitParameter("spring.profiles.active", profile);

        setHandler(servletContextHandler);
        LOGGER.info("Use spring profile - {}", profile);
        LOGGER.info("Application start on {} port", port);
        start();
        join();
    }

    private ServletContextHandler getServletContextHandler(WebApplicationContext context) throws IOException, URISyntaxException {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath("/");
        ServletHolder servlet = new ServletHolder(new DispatcherServlet(context));
        servlet.getRegistration().setMultipartConfig(new MultipartConfigElement("/tmp"));
        contextHandler.addServlet(servlet, "/*");
        contextHandler.addEventListener(new ContextLoaderListener(context));

        URL url = this.getClass().getClassLoader().getResource("webapp/");

        if (url != null) {
            contextHandler.setBaseResource(Resource.newResource(url.toURI()));
        }

        contextHandler.addFilter(
                new FilterHolder(new DelegatingFilterProxy(DEFAULT_FILTER_NAME)),
                "/*",
                EnumSet.allOf(DispatcherType.class));

        return contextHandler;
    }


}

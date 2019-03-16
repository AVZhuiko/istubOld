package ru.avzhuiko.istub;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MainWebAppInitializer extends AbstractSecurityWebApplicationInitializer {

  public MainWebAppInitializer() {
    super(WebConfig.class);
  }

  @Override
  protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
    ServletRegistration.Dynamic dispatcher = servletContext.addServlet("istub",
        new DispatcherServlet(new AnnotationConfigWebApplicationContext()));
    dispatcher.setLoadOnStartup(1);
    dispatcher.addMapping("/*");
  }
}
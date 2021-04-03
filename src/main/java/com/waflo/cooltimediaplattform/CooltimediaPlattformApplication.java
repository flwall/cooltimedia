package com.waflo.cooltimediaplattform;

import com.cloudinary.Cloudinary;
import com.waflo.cooltimediaplattform.backend.model.Role;
import com.waflo.cooltimediaplattform.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@EntityScan(basePackages = "com.waflo.cooltimediaplattform.backend.model")
public class CooltimediaPlattformApplication extends SpringBootServletInitializer {

    @Value("${cloudinary.cloud_name}")
    private String cloudName;

    @Value("${cloudinary.api_key}")
    private String apiKey;

    @Value("${cloudinary.api_secret}")
    private String apiSecret;

    public static void main(String[] args) {
        SpringApplication.run(CooltimediaPlattformApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CooltimediaPlattformApplication.class);
    }

    @Bean
    public CommandLineRunner loadData(RoleService roleService) {
        return args -> {
            var admin = roleService.findByRoleName("ADMIN");
            var user = roleService.findByRoleName("USER");
            admin.ifPresentOrElse(act -> Role.ROLE_ADMIN = act, () -> Role.ROLE_ADMIN = roleService.save(Role.ROLE_ADMIN));
            user.ifPresentOrElse(act -> Role.ROLE_USER = act, () -> Role.ROLE_USER = roleService.save(Role.ROLE_USER));
        };
    }

    @Bean
    ServletRegistrationBean jsfServletRegistration(ServletContext servletContext) {
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
        servletContext.setInitParameter("primefaces.THEME", "bootstrap");
        servletContext.setInitParameter("primefaces.FONT_AWESOME", "true");

        ServletRegistrationBean<FacesServlet> servletRegistrationBean = new ServletRegistrationBean<>(
                new FacesServlet(), "*.xhtml");
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }

    @Bean
    public Cloudinary cloudinaryConfig() {
        Cloudinary cloudinary = null;
        Map config = new HashMap();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }
}

package co.flexidev.theta.config;
import co.flexidev.theta.controller.PersonController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        // register(PersonController.class);
        // Register packages to scan for controllers
        packages("co.flexidev.theta.controller");
    }
}


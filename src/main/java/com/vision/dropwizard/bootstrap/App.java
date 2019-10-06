package com.vision.dropwizard.bootstrap;

/**
 * @author sonveer.narwaria on 06/10/19
 * @project dropwizard-pipeline
 */

import com.vision.dropwizard.auth.AppAuthorizer;
import com.vision.dropwizard.auth.AppBasicAuthenticator;
import com.vision.dropwizard.auth.User;
import com.vision.dropwizard.resource.AppHealthCheck;
import com.vision.dropwizard.resource.EmployeeRESTController;
import com.vision.dropwizard.resource.HealthCheckController;
import com.vision.dropwizard.resource.RESTClientController;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;

@Slf4j
public class App extends Application<Configuration> {

    @Override
    public void initialize(Bootstrap<Configuration> b) {
    }

    @Override
    public void run(Configuration c, Environment environment) throws Exception {
        log.info("Registering REST resources");
        environment.jersey().register(new EmployeeRESTController(environment.getValidator()));
        //Now we added REST Client Resource named RESTClientController

        final Client client = new JerseyClientBuilder(environment).build("sonveerrestClient");
        environment.jersey().register(new RESTClientController(client));

        //Application health check
        environment.healthChecks().register("APIHealthCheck", new AppHealthCheck(client));

        //Run multiple health checks
        environment.jersey().register(new HealthCheckController(environment.healthChecks()));


        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new AppBasicAuthenticator())
                .setAuthorizer(new AppAuthorizer())
                .setRealm("BASIC-AUTH-REALM")
                .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));


    }

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }
}
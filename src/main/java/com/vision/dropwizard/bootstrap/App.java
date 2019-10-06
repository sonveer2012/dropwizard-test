package com.vision.dropwizard.bootstrap;

/**
 * @author sonveer.narwaria on 06/10/19
 * @project dropwizard-pipeline
 */

import com.vision.dropwizard.resource.AppHealthCheck;
import com.vision.dropwizard.resource.EmployeeRESTController;
import com.vision.dropwizard.resource.HealthCheckController;
import com.vision.dropwizard.resource.RESTClientController;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
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
    public void run(Configuration c, Environment e) throws Exception {
        log.info("Registering REST resources");
        e.jersey().register(new EmployeeRESTController(e.getValidator()));
        //Now we added REST Client Resource named RESTClientController

        final Client client = new JerseyClientBuilder(e).build("sonveerrestClient");
        e.jersey().register(new RESTClientController(client));

        //Application health check
        e.healthChecks().register("APIHealthCheck", new AppHealthCheck(client));

        //Run multiple health checks
        e.jersey().register(new HealthCheckController(e.healthChecks()));


    }

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }
}
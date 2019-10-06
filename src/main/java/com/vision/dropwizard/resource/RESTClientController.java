package com.vision.dropwizard.resource;

/**
 * @author sonveer.narwaria on 06/10/19
 * @project dropwizard-pipeline
 */

import com.vision.dropwizard.model.Employee;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static com.vision.dropwizard.utils.Constants.PORT;

@Produces(MediaType.APPLICATION_JSON)
@Path("/client/")
public class RESTClientController {
    private Client client;

    public RESTClientController(Client client) {
        this.client = client;
    }

    @GET
    @Path("/employees/")
    public Response getEmployees() {
        //Do not hard code in your application
        WebTarget webTarget = client.target("http://localhost:" + PORT + "/employees");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        @SuppressWarnings("rawtypes")
        ArrayList employees = response.readEntity(ArrayList.class);
        return Response.ok(employees).build();
    }

    @GET
    @Path("/employees/{id}")
    public Response getEmployeeById(@PathParam("id") int id) {
        //Do not hard code in your application
        WebTarget webTarget = client.target("http://localhost:" + PORT + "/employees" + id);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        Employee employee = response.readEntity(Employee.class);
        return Response.ok(employee).build();
    }
}
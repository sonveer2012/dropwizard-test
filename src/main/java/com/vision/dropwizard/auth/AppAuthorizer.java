package com.vision.dropwizard.auth;

/**
 * @author sonveer.narwaria on 06/10/19
 * @project dropwizard-pipeline
 */
import io.dropwizard.auth.Authorizer;

public class AppAuthorizer implements Authorizer<User>
{
    @Override
    public boolean authorize(User user, String role) {
        return user.getRoles() != null && user.getRoles().contains(role);
    }
}
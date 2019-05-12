package com.fye.fly.app.configuration;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/flights-app/")
public class JaxrsApplication extends Application {
}

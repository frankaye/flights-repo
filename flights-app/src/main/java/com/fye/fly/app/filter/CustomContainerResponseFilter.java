package com.fye.fly.app.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

@Component
@Provider
public class CustomContainerResponseFilter implements ContainerResponseFilter {

	/***
	 * check request token 
	 */
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
        if(requestContext.getHeaderString("ping") != null) {
            responseContext.getHeaders().add("pong", "pong");
        }
	}

}

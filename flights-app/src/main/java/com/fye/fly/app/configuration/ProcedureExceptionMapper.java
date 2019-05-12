package com.fye.fly.app.configuration;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;


@Component
@Provider
public class ProcedureExceptionMapper implements ExceptionMapper<NotFoundException> {

    public Response toResponse(NotFoundException exception) {

        Response.ResponseBuilder responseBuilder = Response.status(Response.Status.NOT_FOUND).entity("The resource you've requested, has not been found!");
        return responseBuilder.type(MediaType.TEXT_PLAIN).build();
    }

}

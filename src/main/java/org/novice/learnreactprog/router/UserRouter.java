package org.novice.learnreactprog.router;

import jakarta.validation.ValidationException;
import org.novice.learnreactprog.exception.ErrorResponse;
import org.novice.learnreactprog.exception.ResourceNotFoundException;
import org.novice.learnreactprog.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouter {
    @Bean
    public RouterFunction<ServerResponse> userRoutes(UserHandler handler){
        return RouterFunctions.route()
                .GET("/api/v1/users", handler::getAll)
                .POST("/api/v1/users", handler::create)
                .GET("/api/v1/users/{id}",handler::getById)
                .onError(ResourceNotFoundException.class, (rnfe, req) ->
                        ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(new ErrorResponse(rnfe.getMessage())))
                .onError(ValidationException.class, (ve, req) ->
                        ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(new ErrorResponse(ve.getMessage())))
                .onError(IllegalArgumentException.class, (iae, req) ->
                        ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(new ErrorResponse(iae.getMessage())))
                .build();

    }
}

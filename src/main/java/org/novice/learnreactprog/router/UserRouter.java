package org.novice.learnreactprog.router;

import org.novice.learnreactprog.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                .build();

    }
}

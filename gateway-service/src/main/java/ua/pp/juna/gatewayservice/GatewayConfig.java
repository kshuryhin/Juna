package ua.pp.juna.gatewayservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.pp.juna.gatewayservice.filter.JwtAuthenticationFilter;

@Configuration
public class GatewayConfig {
//    @Autowired
//    private JwtAuthenticationFilter filter;
//
//    @Bean
//    public RouteLocator routes(RouteLocatorBuilder builder) {
//        return builder.routes().route("auth", r -> r.path("/authenticate/**").filters(f -> f.filter(filter)).uri("http://localhost:8080/auth/authenticate"))
//                .route("register", r -> r.path("/register/**").filters(f -> f.filter(filter)).uri("http://localhost:8080/auth/register"))
//                .route("vacancies", r -> r.path("/vacancies/**").filters(f -> f.filter(filter)).uri("http://localhost:8089/api/v1/vacancies/")).build();
//    }
}

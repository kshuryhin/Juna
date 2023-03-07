package ua.pp.juna.gatewayservice.filter;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import ua.pp.juna.gatewayservice.exception.JwtTokenMalformedException;
import ua.pp.juna.gatewayservice.exception.JwtTokenMissingException;

import ua.pp.juna.gatewayservice.util.JwtUtil;

import javax.naming.AuthenticationException;
import java.util.function.Predicate;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GatewayFilterFactory {
    private final JwtUtil jwtUtil;

    @Override
    public GatewayFilter apply(final Object config) {
        return ((exchange, chain) -> {
            final var request = exchange.getRequest();

            final var apiEndpoints = List.of("/register", "/authenticate");

            final Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                    .noneMatch(uri -> r.getURI().getPath().contains(uri));

            if (isApiSecured.test(request)) {
                if (!request.getHeaders().containsKey("Authorization")) {
                    final var response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);

                    return response.setComplete();
                }

                final var token = request.getHeaders().getOrEmpty("Authorization").get(0);

                try {
                    jwtUtil.validateToken(token);
                    //jwtUtil.validateRoles(token, Set.of("USER"));
                } catch (JwtTokenMalformedException | JwtTokenMissingException e) {
                    final var response = exchange.getResponse();
                    if(e.getMessage().equals("Expired JWT token")) {
                        response.setStatusCode(HttpStatus.I_AM_A_TEAPOT);
                    }else response.setStatusCode(HttpStatus.BAD_REQUEST);
                    log.error(e.getMessage());
                    return response.setComplete();
                }catch (AuthenticationException e) {
                    final var response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    log.error(e.getMessage());
                    return response.setComplete();
                }

                final var claims = jwtUtil.getClaims(token);
                exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id"))).build();
            }
            return chain.filter(exchange);
        });
    }

    @Override
    public Config newConfig() {
        return new Config("JwtAuthenticationFilter");
    }

    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }

    @Data
    public static class Config {

        public Config(String name){
            this.name = name;
        }
        private String name;
    }
}

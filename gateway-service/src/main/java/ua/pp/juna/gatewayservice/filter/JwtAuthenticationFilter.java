package ua.pp.juna.gatewayservice.filter;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import ua.pp.juna.gatewayservice.exception.JwtTokenMalformedException;
import ua.pp.juna.gatewayservice.exception.JwtTokenMissingException;

import io.jsonwebtoken.Claims;
import ua.pp.juna.gatewayservice.util.JwtUtil;

import javax.naming.AuthenticationException;
import java.util.Set;
import java.util.function.Predicate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GatewayFilterFactory {
    private final JwtUtil jwtUtil;

    @Override
    public GatewayFilter apply(Object config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            final List<String> apiEndpoints = List.of("/register", "/authenticate");

            Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                    .noneMatch(uri -> r.getURI().getPath().contains(uri));

            if (isApiSecured.test(request)) {
                if (!request.getHeaders().containsKey("Authorization")) {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);

                    return response.setComplete();
                }

                final String token = request.getHeaders().getOrEmpty("Authorization").get(0);

                try {
                    jwtUtil.validateToken(token);
                    jwtUtil.validateRoles(token, Set.of("USER"));
                } catch (JwtTokenMalformedException | JwtTokenMissingException e) {
                    // e.printStackTrace();

                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.BAD_REQUEST);

                    return response.setComplete();
                }catch (AuthenticationException e) {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }

                Claims claims = jwtUtil.getClaims(token);
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

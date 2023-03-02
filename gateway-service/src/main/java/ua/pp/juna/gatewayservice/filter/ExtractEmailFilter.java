package ua.pp.juna.gatewayservice.filter;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import ua.pp.juna.gatewayservice.util.JwtUtil;

import javax.naming.AuthenticationException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExtractEmailFilter implements GatewayFilterFactory {
    private final JwtUtil jwtUtil;
    @Override
    public GatewayFilter apply(Object config) {
        return (((exchange, chain) -> {
            final ServerHttpRequest request = exchange.getRequest();
            final String token = request.getHeaders().get("Authorization").get(0);

            if(token == null && !request.getURI().getPath().contains("/authenticate")) {
                try {
                    throw new AuthenticationException("Missing token!");
                } catch (AuthenticationException e) {
                    log.error(e.getMessage());
                }
            }

            final String email = jwtUtil.getSubject(token);
            if (email != null) {
                final String newPath = exchange.getRequest().getPath().value()+"/"+email;
                ServerHttpRequest updatedRequest = request.mutate().path(newPath).build();
                exchange.getAttributes().put(request.getURI().getPath(), updatedRequest.getURI());
                return chain.filter(exchange.mutate().request(updatedRequest).build());
            }
            return chain.filter(exchange);
        }));
    }

    @Override
    public JwtAuthenticationFilter.Config newConfig() {
        return new JwtAuthenticationFilter.Config("JwtAuthenticationFilter");
    }

    @Override
    public Class<ExtractEmailFilter.Config> getConfigClass() {
        return ExtractEmailFilter.Config.class;
    }

    @Data
    public static class Config {

        public Config(String name){
            this.name = name;
        }
        private String name;
    }
}

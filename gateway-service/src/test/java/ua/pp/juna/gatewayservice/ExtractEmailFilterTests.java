package ua.pp.juna.gatewayservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ua.pp.juna.gatewayservice.filter.ExtractEmailFilter;
import ua.pp.juna.gatewayservice.filter.JwtAuthenticationFilter;
import ua.pp.juna.gatewayservice.util.JwtUtil;

import java.util.WeakHashMap;

import static org.mockito.Mockito.calls;
import static org.mockito.Mockito.mock;

public class ExtractEmailFilterTests {
    private ExtractEmailFilter filter;
    private ServerWebExchange exchange;


    private JwtAuthenticationFilter factory = mock(JwtAuthenticationFilter.class);

    private GatewayFilterChain filterChain = mock(GatewayFilterChain.class);

    private ArgumentCaptor<ServerWebExchange> captor = ArgumentCaptor.forClass(ServerWebExchange.class);



    @BeforeEach
    public void initEach() {
        filter = new ExtractEmailFilter(new JwtUtil());
    }

    @Test
    void apply_happyPath() {
        MockServerHttpRequest request = MockServerHttpRequest.get("http://localhost:8085/logout").build();
        exchange = MockServerWebExchange.from(request);
        GatewayFilter filter = new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                return null;
            }
        };
        filter.filter(exchange, filterChain);

        ServerHttpRequest actualRequest = captor.getValue().getRequest();

        Assertions.assertEquals(request, actualRequest);
    }

    @Test
    void getConfigClass_happyPath() {
        Assertions.assertEquals(ExtractEmailFilter.Config.class, filter.getConfigClass());
    }

    @Test
    void newConfig_happyPath() {
        JwtAuthenticationFilter.Config config = new JwtAuthenticationFilter.Config("JwtAuthenticationFilter");
        Assertions.assertEquals(config, filter.newConfig());
    }


}


//
//package ua.pp.juna.gatewayservice;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
////import org.springframework.cloud.gateway.filter.ServerWebExchange;
//import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
//import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
//import org.springframework.mock.web.server.MockServerWebExchange;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.ServerWebExchangeDecorator;
//import ua.pp.juna.gatewayservice.filter.ExtractEmailFilter;
//import ua.pp.juna.gatewayservice.util.JwtUtil;
//
//import static org.mockito.Mockito.*;
//
//class ExtractEmailFilterTest {
//
//    private ExtractEmailFilter extractEmailFilter;
//
//    @Mock
//    private JwtUtil jwtUtil;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        extractEmailFilter = new ExtractEmailFilter(jwtUtil);
//    }
//
//    @Test
//    public void testFilterWithValidToken() {
//        String token = "valid-token";
//        String email = "test@example.com";
//
//        ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/test")
//                .header("Authorization", token)
//                .build());
//
//        when(jwtUtil.getSubject(token)).thenReturn(email);
//
//        GatewayFilterChain filterChain = mock(GatewayFilterChain.class);
//        extractEmailFilter.apply(new ExtractEmailFilter.Config("test")).filter(exchange, filterChain);
//
//        verify(jwtUtil, times(1)).getSubject(token);
//        verify(filterChain, times(1)).filter(any(ServerWebExchangeDecorator.class));
//    }
//
//    @Test
//    public void testFilterWithInvalidToken() {
//        String token = "invalid-token";
//
//        ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/test")
//                .header("Authorization", token)
//                .build());
//
//        GatewayFilterChain filterChain = mock(GatewayFilterChain.class);
//        extractEmailFilter.apply(new ExtractEmailFilter.Config("test")).filter(exchange, filterChain);
//
//        verify(jwtUtil, times(1)).getSubject(token);
//        verify(filterChain, times(1)).filter(any(ServerWebExchangeDecorator.class));
//    }
//
//    @Test
//    public void testFilterWithoutToken() {
//        ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/test")
//                .build());
//
//        GatewayFilterChain filterChain = mock(GatewayFilterChain.class);
//        extractEmailFilter.apply(new ExtractEmailFilter.Config("test")).filter(exchange, filterChain);
//
//        verify(jwtUtil, never()).getSubject(anyString());
//        verify(filterChain, times(1)).filter(any(ServerWebExchangeDecorator.class));
//    }
//}

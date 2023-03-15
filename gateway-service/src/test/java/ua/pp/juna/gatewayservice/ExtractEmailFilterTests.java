package ua.pp.juna.gatewayservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import org.mockito.MockitoAnnotations;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;

import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;

import ua.pp.juna.gatewayservice.filter.ExtractEmailFilter;
import ua.pp.juna.gatewayservice.filter.JwtAuthenticationFilter;
import ua.pp.juna.gatewayservice.util.JwtUtil;

import org.mockito.Mock;

import org.springframework.web.server.ServerWebExchangeDecorator;


import static org.mockito.Mockito.*;

public class ExtractEmailFilterTests {

    private JwtAuthenticationFilter factory = mock(JwtAuthenticationFilter.class);

    private GatewayFilterChain filterChain = mock(GatewayFilterChain.class);

    private ArgumentCaptor<ServerWebExchange> captor = ArgumentCaptor.forClass(ServerWebExchange.class);

    private ExtractEmailFilter extractEmailFilter;
//
    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        extractEmailFilter = new ExtractEmailFilter(jwtUtil);
    }


    @Test
    void apply_happyPath() {
        String token = "valid-token";
        String email = "test@example.com";

        ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/test")
                .header("Authorization", token)
                .build());

        when(jwtUtil.getSubject(token)).thenReturn(email);

        GatewayFilterChain filterChain = mock(GatewayFilterChain.class);
        extractEmailFilter.apply(new ExtractEmailFilter.Config("test")).filter(exchange, filterChain);

        verify(jwtUtil, times(1)).getSubject(token);
        verify(filterChain, times(1)).filter(any(ServerWebExchangeDecorator.class));
    }

    @Test
    void getConfigClass_happyPath() {
        Assertions.assertEquals(ExtractEmailFilter.Config.class, extractEmailFilter.getConfigClass());
    }

    @Test
    void newConfig_happyPath() {
        JwtAuthenticationFilter.Config config = new JwtAuthenticationFilter.Config("JwtAuthenticationFilter");
        Assertions.assertEquals(config, extractEmailFilter.newConfig());
    }


}

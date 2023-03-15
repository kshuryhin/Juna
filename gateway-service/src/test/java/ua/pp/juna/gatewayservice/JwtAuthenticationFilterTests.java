package ua.pp.juna.gatewayservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.server.ServerWebExchangeContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ua.pp.juna.gatewayservice.exception.JwtTokenMalformedException;
import ua.pp.juna.gatewayservice.exception.JwtTokenMissingException;
import ua.pp.juna.gatewayservice.filter.JwtAuthenticationFilter;
import ua.pp.juna.gatewayservice.util.JwtUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil);
    }

    @Test
    void apply_withUnauthorizedRequest_shouldReturnUnauthorizedResponse() {
        // given
        final var exchange = mock(ServerWebExchange.class);
        final var request = mock(ServerHttpRequest.class);
        final var response = mock(ServerHttpResponse.class);
        final var chain = mock(GatewayFilterChain.class);

        when(exchange.getRequest()).thenReturn(request);
        when(request.getURI()).thenReturn(null);
        when(chain.filter(exchange)).thenReturn(Mono.empty());
        when(response.setComplete()).thenReturn(Mono.empty());
        when(response.setStatusCode(HttpStatus.UNAUTHORIZED)).thenReturn(response);

        // when
        final var result = jwtAuthenticationFilter.apply(null).filter(exchange, chain);

        // then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void apply_withValidToken_shouldCallFilterChain() throws Exception {
        // given
        final var exchange = mock(ServerWebExchange.class);
        final var request = mock(ServerHttpRequest.class);
        final var response = mock(ServerHttpResponse.class);
        final var chain = mock(GatewayFilterChain.class);

        when(exchange.getRequest()).thenReturn(request);
        when(request.getURI()).thenReturn(null);
        when(chain.filter(exchange)).thenReturn(Mono.empty());
        when(jwtUtil.validateToken(any())).thenReturn(true);

        // when
        final var result = jwtAuthenticationFilter.apply(null).filter(exchange, chain);

        // then
        verify(chain, times(1)).filter(exchange);
    }

    @Test
    void apply_withMalformedToken_shouldReturnBadRequestResponse() throws Exception {
        // given
        final var exchange = mock(ServerWebExchange.class);
        final var request = mock(ServerHttpRequest.class);
        final var response = mock(ServerHttpResponse.class);
        final var chain = mock(GatewayFilterChain.class);

        when(exchange.getRequest()).thenReturn(request);
        when(request.getURI()).thenReturn(null);
        when(chain.filter(exchange)).thenReturn(Mono.empty());
        when(jwtUtil.validateToken(any())).thenThrow(JwtTokenMalformedException.class);
        when(response.setComplete()).thenReturn(Mono.empty());
        when(response.setStatusCode(HttpStatus.BAD_REQUEST)).thenReturn(response);

        // when
        final var result = jwtAuthenticationFilter.apply(null).filter(exchange, chain);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

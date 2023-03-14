package ua.pp.juna.gatewayservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.pp.juna.gatewayservice.filter.ExtractEmailFilter;
import ua.pp.juna.gatewayservice.filter.JwtAuthenticationFilter;
import ua.pp.juna.gatewayservice.util.JwtUtil;

import java.util.WeakHashMap;

public class ExtractEmailFilterTests {
    private ExtractEmailFilter filter;

    @BeforeEach
    public void initEach() {
        filter = new ExtractEmailFilter(new JwtUtil());
    }

    @Test
    void apply_happyPath() {

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

package ua.pp.juna.gatewayservice.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClock;

import java.util.Date;

public class CustomClock extends DefaultClock {
    @Override
    public Date now() {
        return new Date(Long.MAX_VALUE);
    }
}
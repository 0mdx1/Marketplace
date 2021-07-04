package com.ncgroup.marketplaceserver.security.constants;

public class JwtConstants {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_HEADER = "Authorization";
    public static final long EXPIRATION_TIME_MS = 1000 * 60 * 60; // 1 hour = 1000 ms * 60s * 60m
    public static final String AUTHORITIES = "authorities";
}

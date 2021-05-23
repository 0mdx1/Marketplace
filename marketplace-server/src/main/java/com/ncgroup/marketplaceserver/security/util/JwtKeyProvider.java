package com.ncgroup.marketplaceserver.security.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Component
@PropertySource("classpath:jwt.properties")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter
public class JwtKeyProvider {
	
	@Value("${jwt.secret}")
    private String secret;
	
}

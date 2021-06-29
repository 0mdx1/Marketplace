package com.ncgroup.marketplaceserver;

import java.time.ZoneId;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketplaceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketplaceServerApplication.class, args);
    }
    
    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("EET")); 
    }

}

package com.ncgroup.marketplaceserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;
    private boolean isEnabled;
    private int failedAuth;
    private LocalDateTime lastFailedAuth;
    private Role role;
    //private List<String> authorities;
}

package com.ncgroup.marketplaceserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;
    private String name;
    private String surname;
    private String phone;
    private LocalDate birthday;
    private String email;
    
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;
    private boolean isEnabled;
    private int failedAuth;
    private LocalDateTime lastFailedAuth;
    private Role role;
    private String status;
    private String authLink;
    private LocalDateTime authLinkDate;
    //private List<String> authorities;
}

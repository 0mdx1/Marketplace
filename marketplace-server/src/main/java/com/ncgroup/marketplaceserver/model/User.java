package com.ncgroup.marketplaceserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private boolean isEnabled;
    @JsonIgnore
    private int failedAuth;
    @JsonIgnore
    private LocalDateTime lastFailedAuth;
    private Role role;
    private String status;
    @JsonIgnore
    private String authLink;
    @JsonIgnore
    private LocalDateTime authLinkDate;
    //private List<String> authorities;

    public void toDto(User user) {
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        user.setBirthday(birthday);
        user.setEmail(email);
        user.setEnabled(isEnabled);
        user.setFailedAuth(failedAuth);
        user.setLastFailedAuth(lastFailedAuth);
        user.setRole(role);
        user.setStatus(status);
        user.setAuthLink(authLink);
        user.setAuthLinkDate(authLinkDate);

    }
}

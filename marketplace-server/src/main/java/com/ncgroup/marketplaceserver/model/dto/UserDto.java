package com.ncgroup.marketplaceserver.model.dto;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private long id;
	@NotBlank(message = "Name is mandatory")
    private String name;
	@NotBlank(message = "Surname is mandatory")
    private String surname;
    private String phone;
    private LocalDate birthday;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;
    
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;
    private Role role;
    private String status;
    
    public static UserDto convertToDto(User user) {
    	return UserDto.builder()
    			.id(user.getId())
    			.name(user.getName())
    			.surname(user.getSurname())
    			.phone(user.getPhone())
    			.birthday(user.getBirthday())
    			.role(user.getRole())
    			.email(user.getEmail())
                .status(user.getStatus())
    			.build();
    }
}

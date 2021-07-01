package com.ncgroup.marketplaceserver.model.dto;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.validator.Birthday;
import com.ncgroup.marketplaceserver.model.validator.Password;

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
	@Pattern(regexp = "^\\+380\\d{9}$")
	@NotBlank(message = "Phone is mandatory")
    private String phone;
	@Birthday(message = "Birthday not valid")
    private LocalDate birthday;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;
    
    @JsonProperty(access = Access.WRITE_ONLY)
    @Password(message = "Password not valid")
    private String password;
    private Role role;
    private String status;
    
    @JsonProperty(value = "dateOfBirth")
    public LocalDate getBirthday() {
    	return birthday;
    }
    
    @JsonProperty(value = "birthday")
    @JsonAlias(value = "dateOfBirth")
    public void setBirthday(LocalDate birthday) {
    	this.birthday = birthday;
    }
    
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

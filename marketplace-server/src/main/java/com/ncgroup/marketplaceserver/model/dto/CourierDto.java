package com.ncgroup.marketplaceserver.model.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.ncgroup.marketplaceserver.model.Courier;
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
public class CourierDto {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Surname is mandatory")
    private String surname;
    private String phone;
    @JsonProperty("dateOfBirth")
    @JsonAlias("birthday")
    private LocalDate birthday;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;
    private String status;

    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

/*    public static CourierDto convertToDto(Courier courier) {
        return CourierDto.builder()
                .id(courier.getUser().getId())
                .name(courier.getUser().getName())
                .surname(courier.getUser().getSurname())
                .phone(courier.getUser().getPhone())
                .birthday(courier.getUser().getBirthday())
                .role(courier.getUser().getRole())
                .email(courier.getUser().getEmail())
                .status(courier.isStatus())
                .build();
    }*/
}

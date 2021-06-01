package com.ncgroup.marketplaceserver.model.dto;

import com.ncgroup.marketplaceserver.model.Courier;
import com.ncgroup.marketplaceserver.model.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class CourierUpdateDto {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Surname is mandatory")
    private String surname;
    private String phone;
    private LocalDate birthday;
    private String status;

    public void toDto(User courier) {
        courier.setName(name);
        courier.setSurname(surname);
        courier.setPhone(phone);
        courier.setBirthday(birthday);
        courier.setStatus(status);
    }
}

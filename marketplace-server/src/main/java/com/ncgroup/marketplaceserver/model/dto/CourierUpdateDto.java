package com.ncgroup.marketplaceserver.model.dto;

import com.ncgroup.marketplaceserver.model.Courier;
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
    private boolean userStatus;
    private boolean courierStatus;

    public void toDto(Courier courier) {
        courier.getUser().setName(name);
        courier.getUser().setSurname(surname);
        courier.getUser().setPhone(phone);
        courier.getUser().setBirthday(birthday);
        courier.getUser().setEnabled(userStatus);
        courier.setStatus(courierStatus);

    }
}

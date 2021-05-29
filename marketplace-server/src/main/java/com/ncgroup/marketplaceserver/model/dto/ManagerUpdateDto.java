package com.ncgroup.marketplaceserver.model.dto;

import com.ncgroup.marketplaceserver.model.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class ManagerUpdateDto {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Surname is mandatory")
    private String surname;
    private String phone;
    private LocalDate birthday;
    private boolean userStatus;

    public void toDto(User manager) {
        manager.setName(name);
        manager.setSurname(surname);
        manager.setPhone(phone);
        manager.setBirthday(birthday);
        manager.setEnabled(userStatus);
    }
}

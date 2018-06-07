package com.baziaka.dto;

import com.baziaka.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.LocalDate;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotEmpty;

public class UserDTO {

    @NonNull
    @NotEmpty
    private String email;

    @NonNull
    @NotEmpty
    private String password;

    @NonNull
    @JsonIgnore
    private LocalDate birthday;

    private Double bill;

    public UserDTO() {
        this.bill = 0d;
    }

    public static UserDTO of(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.email = user.getEmail();
        userDTO.password = user.getPassword();
        userDTO.birthday = user.getBirthday();
        userDTO.bill = user.getBill();

        return userDTO;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = LocalDate.parse(birthday);
    }

    @JsonProperty("birthday")
    public String getBirthdayAsString() {
        return birthday.toString();
    }

    public Double getBill() {
        return bill;
    }

    public void setBill(Double bill) {
        this.bill = bill;
    }
}

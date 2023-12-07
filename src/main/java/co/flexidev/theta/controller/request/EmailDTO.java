package co.flexidev.theta.controller.request;

import co.flexidev.theta.annotation.EmailValidation;

public class EmailDTO {
    @EmailValidation(type = EmailValidation.Flag.COMPANY)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

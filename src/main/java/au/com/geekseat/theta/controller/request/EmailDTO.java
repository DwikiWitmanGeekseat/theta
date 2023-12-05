package au.com.geekseat.theta.controller.request;

import au.com.geekseat.theta.annotation.EmailValidation;

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

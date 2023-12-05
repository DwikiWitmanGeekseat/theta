package au.com.geekseat.theta.annotation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailValidation, String> {

    private Integer type;

    @Override
    public void initialize(EmailValidation params) {
        type = params.type().getValue();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        // null values are valid
        if (!StringUtils.hasText(value)) {
            return true;
        }
        boolean valid = value.matches(regexPattern);
        if (valid && type.equals(EmailValidation.Flag.COMPANY.getValue())) {
            valid = !BlackListEmailEnum.isValid(value);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("is not company email")
                    .addConstraintViolation();
        }
        return valid;
    }
}

package co.flexidev.theta.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface EmailValidation {

    Flag type() default Flag.ALL;

    String message() default "Invalid email format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public static enum Flag {
        ALL(1),
        COMPANY(2);

        private final int value;

        private Flag(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}

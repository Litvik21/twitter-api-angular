package structure.twitterapi.lib;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    private static final String PASSWORD_VALIDATION_REGEX = "^\\w(\\d)+(A-Za-z)+$";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password != null && password.matches(PASSWORD_VALIDATION_REGEX);
    }
}

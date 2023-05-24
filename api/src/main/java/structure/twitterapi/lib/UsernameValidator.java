package structure.twitterapi.lib;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import structure.twitterapi.model.UserAccount;
import structure.twitterapi.service.UserAccountService;
import java.util.List;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {
    private static final String LOGIN_VALIDATION_REGEX = "^[A-Za-z]$";
    private final UserAccountService accountService;

    public UsernameValidator(UserAccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null) {
            return false;
        }
        boolean checker = true;
        List<UserAccount> accounts = accountService.getAll();
        if (!accounts.isEmpty()) {
            for (UserAccount account : accounts) {
                if (username.equals(account.getUsername())) {
                    checker = false;
                    break;
                }
            }
        }
        username.matches(LOGIN_VALIDATION_REGEX);
        return username.matches(LOGIN_VALIDATION_REGEX) && checker;
    }
}

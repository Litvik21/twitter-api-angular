package structure.twitterapi.dto;

import structure.twitterapi.lib.FieldsValueMatch;
import structure.twitterapi.lib.ValidUsername;
import javax.validation.constraints.Size;

@FieldsValueMatch(
        field = "password",
        fieldMatch = "repeatPassword"
)
public record UserRegisterDto (@Size(min = 4, max = 16, message = "Must contains 4 - 16 symbols")
                               @ValidUsername
                               String username,
                               @Size(min = 4, max = 16, message = "Must contains 4 - 16 symbols")
                               String password,
                               String repeatPassword) {
}

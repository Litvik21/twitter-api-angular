package structure.twitterapi.dto;

import javax.validation.constraints.NotBlank;

public record UserLoginDto (@NotBlank(message = "Login can't be null or blank!")
                            String username,
                            @NotBlank(message = "Password can't be null or blank!")
                            String password) {
}

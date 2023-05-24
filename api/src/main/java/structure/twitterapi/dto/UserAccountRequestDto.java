package structure.twitterapi.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserAccountRequestDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
}

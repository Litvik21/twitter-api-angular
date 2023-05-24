package structure.twitterapi.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAccountResponseDto {
    private Long id;
    private String username;
    private List<RoleResponseDto> roles;
}

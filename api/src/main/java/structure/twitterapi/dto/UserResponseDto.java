package structure.twitterapi.dto;

import java.util.List;

public record UserResponseDto(Long id,
                              String username,
                              List<RoleResponseDto> role) {
}

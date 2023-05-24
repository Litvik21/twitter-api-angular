package structure.twitterapi.dto.mapper;

import org.springframework.stereotype.Component;
import structure.twitterapi.dto.RoleResponseDto;
import structure.twitterapi.model.Role;

@Component
public class RoleMapper {

    public RoleResponseDto toDto(Role role) {
        RoleResponseDto dto = new RoleResponseDto();
        dto.setId(role.getId());
        dto.setName(role.getRoleName().name());
        return dto;
    }
}

package structure.twitterapi.dto.mapper;

import java.util.HashSet;
import org.springframework.stereotype.Component;
import structure.twitterapi.dto.UserRegisterDto;
import structure.twitterapi.dto.UserResponseDto;
import structure.twitterapi.model.UserAccount;

@Component
public class UserAccountMapper {
    private final RoleMapper roleMapper;

    public UserAccountMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public UserResponseDto toDto(UserAccount user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getRole().stream().map(roleMapper::toDto).toList());
    }

    public UserAccount toModel(UserRegisterDto dto) {
        UserAccount user = new UserAccount();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setRole(new HashSet<>());
        return user;
    }
}

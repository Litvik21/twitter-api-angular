package structure.twitterapi.dto.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import structure.twitterapi.dto.RoleResponseDto;
import structure.twitterapi.dto.UserAccountRequestDto;
import structure.twitterapi.dto.UserAccountResponseDto;
import structure.twitterapi.model.UserAccount;

@Component
public class UserAccountMapper {
    private final RoleMapper roleMapper;

    public UserAccountMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public UserAccountResponseDto toDto(UserAccount account) {
        UserAccountResponseDto dto = new UserAccountResponseDto();
        dto.setId(account.getId());
        dto.setUsername(account.getUsername());
        List<RoleResponseDto> roles = account.getRole().stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
        dto.setRoles(roles);
        return dto;
    }

    public UserAccount toModel(UserAccountRequestDto dto) {
        UserAccount account = new UserAccount();
        account.setUsername(dto.getUsername());
        account.setPassword(dto.getPassword());
        account.setRole(new HashSet<>());
        return account;
    }
}

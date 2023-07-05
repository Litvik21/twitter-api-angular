package structure.twitterapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import structure.twitterapi.dto.UserResponseDto;
import structure.twitterapi.dto.mapper.UserAccountMapper;
import structure.twitterapi.model.UserAccount;
import structure.twitterapi.service.UserAccountService;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserAccountService service;
    private final UserAccountMapper mapper;

    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable(name = "id") Long id) {
        return mapper.toDto(service.get(id));
    }

    @GetMapping("/username")
    public UserResponseDto getByUsername(@RequestParam(name = "username") String username) {
        UserAccount user = service.findByUsername(username).orElseThrow(()
                -> new RuntimeException("Can't find user by username: " + username));
        return mapper.toDto(user);
    }
}

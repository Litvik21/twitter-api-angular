package structure.twitterapi.controller;

import java.util.Map;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import structure.twitterapi.dto.UserAccountRequestDto;
import structure.twitterapi.dto.UserAccountResponseDto;
import structure.twitterapi.dto.mapper.RoleMapper;
import structure.twitterapi.dto.mapper.UserAccountMapper;
import structure.twitterapi.model.UserAccount;
import structure.twitterapi.security.AuthenticationService;
import structure.twitterapi.security.JwtTokenProvider;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider provider;
    private final RoleMapper roleMapper;
    private final UserAccountMapper userAccountMapper;


    public AuthenticationController(AuthenticationService authenticationService,
                                    JwtTokenProvider provider, RoleMapper roleMapper,
                                    UserAccountMapper userAccountMapper) {
        this.authenticationService = authenticationService;
        this.provider = provider;
        this.roleMapper = roleMapper;
        this.userAccountMapper = userAccountMapper;
    }

    @PostMapping("/register")
    @ApiOperation(value = "registration new account for user")
    public UserAccountResponseDto register(@RequestBody @Valid UserAccountRequestDto dto) {
        UserAccount account = authenticationService.addNewAccount(dto.getUsername(),
                dto.getPassword());
        return userAccountMapper.toDto(account);
    }

    @PostMapping("/login")
    @ApiOperation(value = "login to account")
    public ResponseEntity<Object> login(@RequestBody @Valid UserAccountRequestDto dto) {
        UserAccount account = authenticationService.login(dto.getUsername(), dto.getPassword());
        String token = provider.createToken(account.getUsername(), account.getRole().stream()
                .map(r -> r.getRoleName().name())
                .collect(Collectors.toList()));
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }
}

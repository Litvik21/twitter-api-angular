package structure.twitterapi.controller;

import java.util.Map;
import java.util.stream.Collectors;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import structure.twitterapi.dto.UserLoginDto;
import structure.twitterapi.dto.UserRegisterDto;
import structure.twitterapi.dto.UserResponseDto;
import structure.twitterapi.dto.mapper.UserAccountMapper;
import structure.twitterapi.model.UserAccount;
import structure.twitterapi.security.AuthenticationService;
import structure.twitterapi.security.JwtTokenProvider;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider provider;
    private final UserAccountMapper userAccountMapper;


    public AuthenticationController(AuthenticationService authenticationService,
                                    JwtTokenProvider provider, UserAccountMapper userAccountMapper) {
        this.authenticationService = authenticationService;
        this.provider = provider;
        this.userAccountMapper = userAccountMapper;
    }

    @PostMapping("/register")
    @ApiOperation(value = "registration new account for user")
    public UserResponseDto register(@RequestBody @Valid UserRegisterDto dto) {
        UserAccount account = authenticationService.addNewAccount(dto.username(),
                dto.password());
        return userAccountMapper.toDto(account);
    }

    @PostMapping("/login")
    @ApiOperation(value = "login to account")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto dto) {
        UserAccount account = authenticationService.login(dto.username(), dto.password());
        String token = provider.createToken(account.getUsername(), account.getRole().stream()
                .map(r -> r.getRoleName().name())
                .collect(Collectors.toList()));
        return new ResponseEntity<>(Map.of("token", token,
                "id", account.getId(), "username", account.getUsername()), HttpStatus.OK);

    }
}

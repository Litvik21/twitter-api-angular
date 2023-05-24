package structure.twitterapi.security;

import java.util.Optional;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import structure.twitterapi.model.Role;
import structure.twitterapi.model.UserAccount;
import structure.twitterapi.service.RoleService;
import structure.twitterapi.service.UserAccountService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserAccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;


    public AuthenticationServiceImpl(UserAccountService accountService,
                                     PasswordEncoder passwordEncoder,
                                     RoleService roleService) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public UserAccount addNewAccount(String username, String password) {
        UserAccount account = new UserAccount();

        account.setUsername(username);
        account.setPassword(password);
        account.setRole(Set.of(roleService.getRoleByRoleName(Role.RoleName.USER)));
        return accountService.add(account);
    }

    @Override
    public UserAccount login(String username, String password) {
        Optional<UserAccount> account = accountService.findByUsername(username);
        if (account.isEmpty() || !passwordEncoder.matches(password, account.get().getPassword())) {
            throw new RuntimeException("Incorrect username or password!");
        }
        return account.get();
    }
}

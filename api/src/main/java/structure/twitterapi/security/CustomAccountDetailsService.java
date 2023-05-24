package structure.twitterapi.security;

import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import structure.twitterapi.model.UserAccount;
import structure.twitterapi.service.UserAccountService;
import java.util.Optional;

@Service
public class CustomAccountDetailsService implements UserDetailsService {
    private final UserAccountService accountService;

    public CustomAccountDetailsService(UserAccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> account = accountService.findByUsername(username);

        UserBuilder builder;
        if (account.isPresent()) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(account.get().getPassword());
            builder.roles(account.get().getRole()
                    .stream()
                    .map(r -> r.getRoleName().name())
                    .toArray(String[]::new));
            return builder.build();
        }
        throw new UsernameNotFoundException("Account not found.");
    }
}

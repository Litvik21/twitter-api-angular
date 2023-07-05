package structure.twitterapi.service;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import structure.twitterapi.model.Post;
import structure.twitterapi.model.UserAccount;
import structure.twitterapi.repository.UserAccountRepository;

@AllArgsConstructor
@Service
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserAccount add(UserAccount account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public UserAccount update(UserAccount account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public List<UserAccount> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public UserAccount get(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find account by id: " + id));
    }

    @Override
    public Optional<UserAccount> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Long getIdByUsername(String username) {
        UserAccount user = findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Can't find user bu username: " + username));
        return user.getId();
    }
}

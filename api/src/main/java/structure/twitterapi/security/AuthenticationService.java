package structure.twitterapi.security;

import structure.twitterapi.model.UserAccount;

public interface AuthenticationService {
    UserAccount addNewAccount(String username, String password);

    UserAccount login(String username, String password);
}

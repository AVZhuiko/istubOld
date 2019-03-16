package ru.avzhuiko.istub.service.security;

import java.util.Optional;
import org.springframework.security.core.userdetails.User;
import ru.avzhuiko.istub.service.user.Token;

public interface UserAuthenticationService {

  /**
   * Logs in with the given {@code username} and {@code password}.
   *
   * @return an {@link Optional} of a user when login succeeds
   */
  Token login(String username, String password);

  /**
   * Logs in with the given {@code username} and {@code password}.
   * This token has a long time duration
   *
   * @return an {@link Optional} of a user when login succeeds
   */
  Token loginForUnlimitedToken(String username, String password);

  /**
   * Finds a user by its dao-key.
   *
   * @param token user dao key
   */
  Optional<User> findByToken(String token);

  /**
   * Logs out the given input {@code user}.
   *
   * @param token the user to logout
   */
  void logout(String token);
}
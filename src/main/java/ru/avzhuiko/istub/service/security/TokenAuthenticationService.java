package ru.avzhuiko.istub.service.security;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avzhuiko.istub.common.MessageSource;
import ru.avzhuiko.istub.service.user.Token;
import ru.avzhuiko.istub.service.user.TokenRepository;
import ru.avzhuiko.istub.service.user.User;
import ru.avzhuiko.istub.service.user.UserDetailsServiceImpl;

@Service
@Log4j2
public class TokenAuthenticationService implements UserAuthenticationService {

  @Autowired
  private UserDetailsServiceImpl users;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private MessageSource messageSource;
  @Autowired
  private TokenRepository tokenRepository;

  @Override
  @Transactional
  public Token login(final String username, final String password) {
    User user = users.loadUserByUsername(username);
    checkCredentials(password, user);
    Token token = buildToken(user, new LocalDate().plusDays(90).toDate());
    user.getTokens().add(token);
    return token;
  }

  @Override
  @Transactional
  public Token loginForUnlimitedToken(final String username, final String password) {
    User user = users.loadUserByUsername(username);
    checkCredentials(password, user);
    Token token = buildToken(user, new LocalDate().plusYears(100).toDate());
    token.setUnlimited(true);
    return token;
  }

  private void checkCredentials(final String password, @NotNull final User user) {
    if (!passwordEncoder.matches(password, user.getPassword())) {
      String message = messageSource
          .getMessage("userAuthenticationService.login.badCredentials", user.getUsername());
      throw new BadCredentialsException(message);
    }
  }

  private Token buildToken(final User user, final Date expiredAt) {
    Token token = new Token();
    token.setToken(UUID.randomUUID().toString());
    token.setExpiredAt(expiredAt);
    token.setUser(user);
    return token;
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<org.springframework.security.core.userdetails.User> findByToken(final String token) {
    return users.loadUserByToken(token);
  }

  @Override
  @Transactional
  public void logout(final String token) {
    Token tokenObj = tokenRepository.findByToken(token);
    tokenObj.setExpiredAt(new Date());
  }
}
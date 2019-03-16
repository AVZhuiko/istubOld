package ru.avzhuiko.istub.service.security;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true)
public class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  private final UserAuthenticationService userAuthenticationService;

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    return super.authenticate(authentication);
  }

  @Override
  protected UserDetails retrieveUser(String username,
      UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {
    final Object token = authentication.getCredentials();
    return Optional
        .ofNullable(token)
        .map(String::valueOf)
        .flatMap(userAuthenticationService::findByToken)
        .orElseThrow(() -> new UsernameNotFoundException(
            "Cannot find user with authentication token=" + token));
  }
}

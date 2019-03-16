package ru.avzhuiko.istub.service.user;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avzhuiko.istub.common.MessageSource;
import ru.avzhuiko.istub.service.user.role.RoleRepository;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private TokenRepository tokenRepository;
  @Autowired
  private MessageSource messageSource;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public User loadUserByUsername(String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(
          messageSource.getMessage("userDetailsService.user.notFound", username));
    }
    return user;
  }

  public void register(User user, final String matchingPassword) {
    if (StringUtils.isBlank(user.getUsername())) {
      throw new UsernameIsBlankException(
          messageSource.getMessage("userDetailsService.register.usernameIsBlank"));
    }
    if (StringUtils.isBlank(user.getPassword())) {
      throw new PasswordValidationException(
          messageSource.getMessage("userDetailsService.register.passwordIsBlank"));
    }
    if (!user.getPassword().equals(matchingPassword)) {
      throw new PasswordNotMatchException(
          messageSource.getMessage("userDetailsService.register.matchingPasswordIsNotMatch"));
    }
    if (userRepository.findByUsername(user.getUsername()) != null) {
      throw new UserAlreadyExistException(
          messageSource.getMessage("userDetailsService.register.userAlreadyExist"));
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  @Transactional(readOnly = true)
  public Optional<org.springframework.security.core.userdetails.User> loadUserByToken(String token) {
    Token readToken = tokenRepository.findByToken(token);
    if (readToken != null) {
      if (readToken.getExpiredAt().before(new Date())) {
        throw new SessionAuthenticationException(
            messageSource.getMessage("userDetailsService.token.expired"));
      }
      User proxyUser = readToken.getUser();
      org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(proxyUser.getUsername(), proxyUser.getPassword(), proxyUser.isEnabled(),
          proxyUser.isAccountNonExpired(), proxyUser.isCredentialsNonExpired(),
          proxyUser.isAccountNonLocked(),
          buildAuthorities(proxyUser.getAuthorities()));
      return Optional.of(user);
    }
    return Optional.empty();
  }

  private List<SimpleGrantedAuthority> buildAuthorities(
      Collection<? extends GrantedAuthority> authorities) {
    return authorities.stream()
        .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
        .collect(Collectors.toList());
  }

}

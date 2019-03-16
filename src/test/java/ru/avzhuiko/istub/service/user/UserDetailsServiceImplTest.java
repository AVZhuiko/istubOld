package ru.avzhuiko.istub.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {

  @Spy
  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserDetailsServiceImpl userDetailsService;

  @Test
  public void register() {
    String matchingPassword = "q1w2e3r4";
    User user = new User();
    user.setUsername("admin");
    user.setPassword(matchingPassword);

    userDetailsService.register(user, matchingPassword);

  }

}
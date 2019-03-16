package ru.avzhuiko.istub.ui.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static ru.avzhuiko.istub.service.user.role.Roles.ADMIN;

import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.avzhuiko.istub.service.security.UserAuthenticationService;
import ru.avzhuiko.istub.service.user.Token;
import ru.avzhuiko.istub.service.user.User;
import ru.avzhuiko.istub.service.user.UserDetailsServiceImpl;
import ru.avzhuiko.istub.ui.converter.UserConverter;
import ru.avzhuiko.istub.ui.dto.UserDto;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SecurityController {

  private final UserAuthenticationService tokenAuthenticationService;
  private final UserConverter userConverter;
  private final UserDetailsServiceImpl userDetailsService;

  @PostMapping("/register")
  @PreAuthorize("hasAuthority('" + ADMIN + "')")
  public UserDto registerUser(@RequestBody UserDto userDto) {
    User user = userConverter.convert(userDto);
    userDetailsService.register(user, userDto.getMatchingPassword());
    return userDto;
  }

  @PostMapping("/login")
  @PreAuthorize("isAnonymous()")
  public void login(@RequestBody UserDto userDto, HttpServletResponse response) {
    Token token = tokenAuthenticationService
        .login(userDto.getUsername(), userDto.getPassword());
    response.addHeader(AUTHORIZATION, "Bearer " + token.getToken());
    Cookie cookie = new Cookie("Bearer", token.getToken());
    cookie
        .setMaxAge(Math.toIntExact((token.getExpiredAt().getTime() - new Date().getTime()) / 1000));
    response.addCookie(cookie);
  }

  @PostMapping("/logout")
  @PreAuthorize("isAnonymous()")
  public void logout(@RequestBody String token) {
    tokenAuthenticationService.logout(token);
  }

}

package ru.avzhuiko.istub.ui.converter;

import org.springframework.stereotype.Component;
import ru.avzhuiko.istub.service.user.User;
import ru.avzhuiko.istub.ui.dto.UserDto;

@Component
public class UserConverter {

  public User convert(UserDto dto) {
    User user = new User();
    user.setUsername(dto.getUsername());
    user.setPassword(dto.getPassword());
    return user;
  }

}

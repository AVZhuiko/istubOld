package ru.avzhuiko.istub.service.user;

import org.springframework.http.HttpStatus;
import ru.avzhuiko.istub.common.MainException;

public class UsernameIsBlankException extends MainException {

  public UsernameIsBlankException(String msg) {
    super(msg, HttpStatus.BAD_REQUEST);
  }
}

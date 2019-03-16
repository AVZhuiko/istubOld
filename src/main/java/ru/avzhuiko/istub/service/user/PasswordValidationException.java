package ru.avzhuiko.istub.service.user;

import org.springframework.http.HttpStatus;
import ru.avzhuiko.istub.common.MainException;

public class PasswordValidationException extends MainException {

  public PasswordValidationException(String msg) {
    super(msg, HttpStatus.BAD_REQUEST);
  }
}

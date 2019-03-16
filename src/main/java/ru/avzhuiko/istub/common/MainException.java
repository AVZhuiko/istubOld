package ru.avzhuiko.istub.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class MainException extends RuntimeException {

  @Getter
  private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

  public MainException(String message) {
    super(message);
  }

  public MainException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public MainException(String message, Throwable cause) {
    super(message, cause);
  }

  public MainException(String message, HttpStatus status, Throwable cause) {
    super(message, cause);
    this.status = status;
  }

}

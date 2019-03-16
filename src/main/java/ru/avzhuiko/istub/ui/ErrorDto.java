package ru.avzhuiko.istub.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorDto {

  private String message;
  private String uuid;

  @Override
  public String toString() {
    return "{"
        + "\"uuid\": \"" + uuid + "\""
        + ", \"message\": \"" + message + "\""
        + "}";
  }
}

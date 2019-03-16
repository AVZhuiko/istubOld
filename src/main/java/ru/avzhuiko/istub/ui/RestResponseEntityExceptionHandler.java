package ru.avzhuiko.istub.ui;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.avzhuiko.istub.common.MainException;
import ru.avzhuiko.istub.common.MessageSource;

@ControllerAdvice
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Log4j2
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  private final MessageSource messageSource;

  @ExceptionHandler(value = {MainException.class})
  protected ResponseEntity<Object> handleMainException(MainException ex, WebRequest request) {
    ErrorDto error = new ErrorDto(ex.getMessage(), UUID.randomUUID().toString());
    log.error(error, ex);
    return handleExceptionInternal(ex, error, new HttpHeaders(), ex.getStatus(), request);
  }

  @ExceptionHandler(value = {AccessDeniedException.class})
  protected ResponseEntity<Object> handleMainException(AccessDeniedException ex,
      WebRequest request) {
    ErrorDto error = new ErrorDto(messageSource.getMessage("handleException.AccessDeniedException",
        ((ServletWebRequest) request).getRequest().getPathInfo()), UUID.randomUUID().toString());
    log.error(error, ex);
    return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
  }

  @ExceptionHandler(value = {RuntimeException.class})
  protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
    ErrorDto error = new ErrorDto(messageSource.getMessage("handleUnexpectedException"),
        UUID.randomUUID().toString());
    log.error(error, ex);
    return handleExceptionInternal(ex, error, new HttpHeaders(),
        HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
}
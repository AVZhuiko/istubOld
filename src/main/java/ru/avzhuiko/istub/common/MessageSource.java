package ru.avzhuiko.istub.common;

import java.util.Locale;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageSource extends ResourceBundleMessageSource {

  private static Locale locale_ru = new Locale("ru", "RU");

  private static Locale currentLocale = Locale.ENGLISH;

  public MessageSource() {
    super();
    setBasename("messages");
  }

  public String getMessage(final String code) {
    return super.getMessage(code, null, currentLocale);
  }

  public String getMessage(final String code, final Object... args) {
    return super.getMessage(code, args, currentLocale);
  }

}

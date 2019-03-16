package ru.avzhuiko.istub.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Errors {

  private final List<String> errors = new ArrayList<>();

  public void add(String error) {
    errors.add(error);
  }

  public boolean hasErrors(){
    return !errors.isEmpty();
  }

  public String collectErrors() {
    StringBuilder builder = new StringBuilder();
    Iterator iterator = errors.iterator();
    while (iterator.hasNext()) {
      builder.append(" * ").append(iterator.next());
      if (iterator.hasNext()) {
        builder.append("\n");
      }
    }
    return builder.toString();
  }

}

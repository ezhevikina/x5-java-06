package com.ezhevikina.course.homework6.service.exceptions;

public class UnknownAccountException extends Exception {
  public UnknownAccountException(String message, Throwable cause) {
    super(message, cause);
  }
}

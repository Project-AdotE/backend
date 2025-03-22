package com.adote.api.core.exceptions.auth;

public class LoginInvalidoException extends RuntimeException {
  public LoginInvalidoException() {
    super("Email ou senha incorretos");
  }
}

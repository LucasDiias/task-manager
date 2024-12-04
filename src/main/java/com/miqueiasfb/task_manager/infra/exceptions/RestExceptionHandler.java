package com.miqueiasfb.task_manager.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.miqueiasfb.task_manager.exceptions.BadRequestException;
import com.miqueiasfb.task_manager.exceptions.ForbiddenException;
import com.miqueiasfb.task_manager.exceptions.ResourceNotFoundException;
import com.miqueiasfb.task_manager.exceptions.TokenException;
import com.miqueiasfb.task_manager.exceptions.UnauthorizedException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  private ResponseEntity<RestErrorMessage> resourceNotFoundHandler(ResourceNotFoundException exception) {
    RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(treatedResponse);
  }

  @ExceptionHandler(UnauthorizedException.class)
  private ResponseEntity<RestErrorMessage> unauthorizedHandler(UnauthorizedException exception) {
    RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.UNAUTHORIZED, exception.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(treatedResponse);
  }

  @ExceptionHandler(BadRequestException.class)
  private ResponseEntity<RestErrorMessage> badRequestHandler(BadRequestException exception) {
    RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(treatedResponse);
  }

  @ExceptionHandler(ForbiddenException.class)
  private ResponseEntity<RestErrorMessage> forbiddenHandler(ForbiddenException exception) {
    RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.FORBIDDEN, exception.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(treatedResponse);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  private ResponseEntity<RestErrorMessage> illegalArgumentHandler(IllegalArgumentException exception) {
    RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(treatedResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  private ResponseEntity<RestErrorMessage> methodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
    RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(treatedResponse);
  }

  @ExceptionHandler(AccessDeniedException.class)
  private ResponseEntity<RestErrorMessage> accessDeniedHandler(AccessDeniedException exception) {
    RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.FORBIDDEN, exception.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(treatedResponse);
  }

  @ExceptionHandler(IllegalStateException.class)
  private ResponseEntity<RestErrorMessage> illegalStateHandler(IllegalStateException exception) {
    RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(treatedResponse);
  }

  @ExceptionHandler(AuthenticationException.class)
  private ResponseEntity<RestErrorMessage> authenticationHandler(AuthenticationException exception) {
    RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.UNAUTHORIZED, exception.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(treatedResponse);
  }

  @ExceptionHandler(TokenException.class)
  private ResponseEntity<RestErrorMessage> tokenHandler(TokenException exception) {
    RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.UNAUTHORIZED, exception.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(treatedResponse);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  private ResponseEntity<RestErrorMessage> usernameNotFoundHandler(UsernameNotFoundException exception) {
    RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(treatedResponse);
  }

  @ExceptionHandler(Exception.class)
  private ResponseEntity<RestErrorMessage> genericExceptionHandler(Exception exception) {
    RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(treatedResponse);
  }
}

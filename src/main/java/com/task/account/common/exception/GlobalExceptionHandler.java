package com.task.account.common.exception;

import com.task.account.common.dto.ResponseDto;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // @Valid 실패
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseDto<?>> handleValidation(MethodArgumentNotValidException ex) {
    String errorMessage = ex.getBindingResult().getFieldErrors()
        .stream()
        .findFirst()
        .map(e -> e.getField() + ": " + e.getDefaultMessage())
        .orElse("Validation error");

    return ResponseEntity
        .badRequest()
        .body(new ResponseDto<>(
            HttpStatus.BAD_REQUEST.value(),
            errorMessage,
            errorMessage
        ));
  }

  // 바인딩 실패
  @ExceptionHandler(BindException.class)
  public ResponseEntity<ResponseDto<?>> handleBind(BindException ex) {
    String errorMessage = ex.getFieldErrors()
        .stream()
        .findFirst()
        .map(e -> e.getField() + ": " + e.getDefaultMessage())
        .orElse("Binding error");

    return ResponseEntity
        .badRequest()
        .body(new ResponseDto<>(
            HttpStatus.BAD_REQUEST.value(),
            null,
            errorMessage
        ));
  }

  // 로그인 실패, 검증 실패 등
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ResponseDto<?>> handleRuntime(RuntimeException ex) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new ResponseDto<>(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage(),
            ex.getMessage()
        ));
  }

  // 리소스 없음
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ResponseDto<?>> handleNotFound(NoSuchElementException ex) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ResponseDto<>(
            HttpStatus.NOT_FOUND.value(),
            null,
            "존재하지 않는 계정입니다."
        ));
  }
}

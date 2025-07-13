package com.task.account.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
@Builder
public class ResponseDto<T> {
    private int code;
    private String message;
    private String error;

    private T response;

    public ResponseDto(int code) {
        this(code, null, null, null);
    }

    public ResponseDto(int code, String message, String error) {
        this(code, message, error, null);
    }

    public ResponseDto(int code, T response) {
        this(code, null, null, response);
    }

    public static ResponseEntity<ResponseDto<?>> ok() {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value()));
    }

    public static <T> ResponseEntity<ResponseDto<T>> ok(T response) {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), response));
    }

    public static <T> ResponseEntity<ResponseDto<T>> created(T response) {
        ResponseDto<T> body = new ResponseDto<>(HttpStatus.OK.value(), response);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    public static ResponseEntity<ResponseDto<?>> badRequest() {
        ResponseDto<?> body = new ResponseDto<>(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

}

package com.ssafy.house.controller.exception.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e) {
		log.error("exception Detecetd"+e.getMessage(),e);
		return ResponseEntity.internalServerError().header("Content-Type", "text/plain;charset=utf-8").body("처리 중 문제가 발생하였습니다. :"+e.getMessage());
	}
}

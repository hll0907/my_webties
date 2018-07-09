package com.hll.web.util;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hll.web.result.ResultMsg;

@ControllerAdvice
@ResponseBody
public class ExceptionAspect {
	private static final Logger log = Logger.getLogger(Exception.class);

	/**
	 * 400 Bad Request
	 * 
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResultMsg handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		log.error("could_not_read_json...", e);
		return ResultMsg.failure("could_not_read_json...", null, 0);
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResultMsg handleValidationException(MethodArgumentNotValidException e) {
		log.error("parameter_validation_exception...", e);
		return ResultMsg.failure("parameter_validation_exception", null, 0);
	}
	
	/**
	 * 405 - Method Not Allowed。HttpRequestMethodNotSupportedException
	 * 是ServletException的子类,需要Servlet API支持
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResultMsg handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException e) {
		log.error("request_method_not_supported...", e);
		return ResultMsg.failure("request_method_not_supported", null, 0);
	}

	/**
	 * 415 - Unsupported Media Type。HttpMediaTypeNotSupportedException
	 * 是ServletException的子类,需要Servlet API支持
	 */
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
	public ResultMsg handleHttpMediaTypeNotSupportedException(Exception e) {
		log.error("content_type_not_supported...", e);
		return ResultMsg.failure("content_type_not_supported", null, 0);
	}


	/**
	 * 500 - Internal Server Error
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResultMsg handleException(Exception e) {
		log.error("Internal Server Error...", e);
		return ResultMsg.failure("Internal Server Error", null, 0);
	}
}

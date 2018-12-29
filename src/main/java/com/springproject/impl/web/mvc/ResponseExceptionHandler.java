package com.springproject.impl.web.mvc;

import com.springproject.to.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseExceptionHandler.class);

    /**
     * ExceptionHandler for these Exceptions :
     * <p>
     * HttpRequestMethodNotSupportedException(405),  HttpMediaTypeNotSupportedException(415),
     * HttpMediaTypeNotAcceptableException(406),     MissingPathVariableException(500),
     * ConversionNotSupportedException(500),         HttpMessageNotWritableException(500),
     * HttpMessageNotReadableException(400),         TypeMismatchException(400),
     * MethodArgumentNotValidException(400),         MissingServletRequestPartException(400),
     * MissingServletRequestParameterException(400), ServletRequestBindingException(400),
     * BindException(500), NoHandlerFoundException(404), AsyncRequestTimeoutException(503)
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error("APP Exception Cause: ", ex);
        ExceptionResponse exResponse = new ExceptionResponse();
        exResponse.errorCode = status.name();
        return ResponseEntity.status(status).body(exResponse);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ExceptionResponse> exceptionMapper(Throwable throwable) {
        LOGGER.error("Unexpected Exception Cause: ", throwable);
        ExceptionResponse exResponse = new ExceptionResponse();
        HttpStatus responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        exResponse.content = throwable.getMessage();
        exResponse.errorCode = responseStatus.toString();
        return ResponseEntity.status(responseStatus).body(exResponse);
    }
}

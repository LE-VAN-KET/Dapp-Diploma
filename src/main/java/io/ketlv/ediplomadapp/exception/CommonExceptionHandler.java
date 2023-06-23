package io.ketlv.ediplomadapp.exception;

import io.ketlv.ediplomadapp.utils.MessageSourceUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.joining;

@RestControllerAdvice
public class CommonExceptionHandler {
    private static final Logger log = (Logger) LogManager.getLogger(CommonExceptionHandler.class);
    @Autowired
    private MessageSourceUtil messageSourceUtil;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    protected ResponseEntity<CommonErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Optional<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> String.format("'%s' %s", x.getField(), messageSourceUtil.getMessage(x.getDefaultMessage())
                ))
                .collect(collectingAndThen(joining("; "), Optional::ofNullable));


        addErrorLog(HttpStatus.BAD_REQUEST, errors.orElse(ex.getMessage()), "MethodArgumentNotValidException");
        CommonErrorResponse commonErrorResponse = new CommonErrorResponse(
                HttpStatus.BAD_REQUEST,
                errors.orElse(ex.getMessage()));
        return new ResponseEntity<>(commonErrorResponse, HttpStatus.BAD_REQUEST);
    }

    /*** Logging ***/
    protected void addErrorLog(HttpStatus httpStatus, String errorMessage, Throwable ex) {
        addErrorLog(httpStatus.value(), errorMessage);
    }

    protected void addErrorLog(HttpStatus httpStatus, String errorMessage, String exceptionType) {
        addErrorLog(httpStatus.value(), errorMessage, exceptionType);
    }

    protected void addErrorLog(Integer errorCode, String errorMessage) {
        log.error(String.format("[Error] | Code: %s | Message: %s",errorCode, errorMessage));
    }

    protected void addErrorLog(Integer errorCode, String errorMessage, String exceptionType) {
        log.error(String.format("[Error] | Code: %s | Type: %s | Message: %s",
                errorCode, exceptionType, errorMessage));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonErrorResponse handleServerErrorException(Exception ex, String exceptionType) {
        addErrorLog(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), exceptionType);
        return new CommonErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {CommonBadRequest.class})
    public CommonErrorResponse handleCommonException(CommonBadRequest ex) {
        addErrorLog(HttpStatus.BAD_REQUEST, ex.getMessage(), "CommonBadRequest");
        return new CommonErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {InvalidTokenHeader.class})
    public CommonErrorResponse handleCommonAuthException(InvalidTokenHeader ex) {
        addErrorLog(HttpStatus.UNAUTHORIZED, ex.getMessage(), "InvalidTokenHeader");
        return new CommonErrorResponse(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {UserNotFoundException.class})
    public CommonErrorResponse handleCommonAuthException(UserNotFoundException ex) {
        addErrorLog(HttpStatus.UNAUTHORIZED, ex.getMessage(), "UserNotFoundException");
        return new CommonErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {

        Optional<String> violations = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + messageSourceUtil.getMessage(v.getMessage()))
                .collect(collectingAndThen(joining("; "), Optional::ofNullable));

        String errMsg = violations.orElse(ex.getMessage());
        addErrorLog(HttpStatus.BAD_REQUEST, errMsg, "ConstraintViolationException");
        return new CommonErrorResponse(
                HttpStatus.BAD_REQUEST,
                errMsg);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public CommonErrorResponse handleAll(Exception ex) {
        addErrorLog(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        String errMsg = "Unexpected internal server error occurs";
        return new CommonErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                errMsg);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(LoadKeyStoreTokenException.class)
    public CommonErrorResponse handleLoadKeystoreTokenException(LoadKeyStoreTokenException exception) {
        return handleServerErrorException(exception, "LoadKeyStoreTokenException");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RetrievePublicKeyException.class)
    public CommonErrorResponse handleRetrievePublicKeyException(RetrievePublicKeyException exception) {
        return handleServerErrorException(exception, "RetrievePublicKeyException");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RetrievePrivateKeyException.class)
    public CommonErrorResponse handleRetrievePrivateKeyException(RetrievePrivateKeyException exception) {
        return handleServerErrorException(exception, "RetrievePrivateKeyException");
    }
}

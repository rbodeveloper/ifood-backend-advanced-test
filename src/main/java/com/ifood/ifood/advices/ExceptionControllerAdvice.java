package com.ifood.ifood.advices;

import com.ifood.ifood.exceptions.NotFoundCitytException;
import com.ifood.ifood.exceptions.NotFoundPlaylistsException;
import com.ifood.ifood.exceptions.UnexpectedException;
import com.ifood.ifood.models.GeneralErrorResponse;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception controller advice responsible for gathering all exceptions thrown from controllers error message.
 *
 */
@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {
    @ResponseBody
    @ExceptionHandler(UnexpectedException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    ResponseEntity<GeneralErrorResponse> handler(UnexpectedException ex) {
        log.error("Unexpected error happened, but It was caught by advice", ex);
        return new ResponseEntity<>(new GeneralErrorResponse("Service is unavailable. Contact the support, please"),
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ResponseBody
    @ExceptionHandler(HystrixRuntimeException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    ResponseEntity<GeneralErrorResponse> handlerHystrixRuntimeException(HystrixRuntimeException ex) {
        log.error("Unexpected error happened, but It was caught by advice", ex);
        return new ResponseEntity<>(new GeneralErrorResponse("Service is unavailable. Contact the support, please"),
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ResponseBody
    @ExceptionHandler(NotFoundCitytException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<GeneralErrorResponse> handlerNotFoundCitytException(NotFoundCitytException ex) {
        log.error("Unexpected error happened, but It was caught by advice", ex);
        return new ResponseEntity<>(new GeneralErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(NotFoundPlaylistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<GeneralErrorResponse> handlerNotFoundPlaylistsException(NotFoundPlaylistsException ex) {
        log.error("Unexpected error happened, but It was caught by advice", ex);
        return new ResponseEntity<>(new GeneralErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}

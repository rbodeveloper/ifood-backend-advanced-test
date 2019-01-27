package com.ifood.ifood.advices;

import com.ifood.ifood.exceptions.NotFoundCitytException;
import com.ifood.ifood.exceptions.NotFoundPlaylistsException;
import com.ifood.ifood.exceptions.UnexpectedException;
import com.ifood.ifood.models.GeneralErrorResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ExceptionControllerAdvice.class)
public class ExceptionControllerAdviceTest {

    @Autowired
    private ExceptionControllerAdvice advice;

    private static final String MESSAGE = "The error";
    private static final String UNAVAILABLE = "Service is unavailable. Contact the support, please";

    @Test
    public void handler() {
        ResponseEntity<GeneralErrorResponse> error = advice.handler(new UnexpectedException(MESSAGE));
        assertThat(error.getBody(), equalTo(new GeneralErrorResponse(UNAVAILABLE)));
    }

    @Test
    public void handlerNotFoundCitytException() {
        ResponseEntity<GeneralErrorResponse> error = advice.handlerNotFoundCitytException(new NotFoundCitytException(MESSAGE));
        assertThat(error.getBody(), equalTo(new GeneralErrorResponse(MESSAGE)));
    }

    @Test
    public void handlerNotFoundPlaylistsException() {
        ResponseEntity<GeneralErrorResponse> error = advice.handlerNotFoundPlaylistsException(new NotFoundPlaylistsException(MESSAGE));
        assertThat(error.getBody(), equalTo(new GeneralErrorResponse(MESSAGE)));
    }
}
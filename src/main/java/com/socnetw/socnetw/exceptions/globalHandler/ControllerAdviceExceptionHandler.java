package com.socnetw.socnetw.exceptions.globalHandler;

import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.exceptions.InternalServerException;
import com.socnetw.socnetw.exceptions.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityExistsException;

@ControllerAdvice
@Slf4j
public class ControllerAdviceExceptionHandler {
    private static final String ATTRIBUTE_NAME_ERROR = "error";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    public ModelAndView badRequestHandler(BadRequestException badRequest) {
        log.error(badRequest.getMessage());
        return new ModelAndView("/error/400", ATTRIBUTE_NAME_ERROR, badRequest.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = UnauthorizedException.class)
    public ModelAndView unauthorizedHandler(UnauthorizedException unauthorized) {
        log.error(unauthorized.getMessage());
        return new ModelAndView("/error/404", ATTRIBUTE_NAME_ERROR, unauthorized.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = EntityExistsException.class)
    public ModelAndView entityExistsHandler(EntityExistsException notExist) {
        log.error(notExist.getMessage());
        return new ModelAndView("/error/404", ATTRIBUTE_NAME_ERROR, notExist.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = NumberFormatException.class)
    public ModelAndView numberFormatHandler(NumberFormatException numberFormat) {
        log.error(numberFormat.getMessage());
        return new ModelAndView("/error/400", ATTRIBUTE_NAME_ERROR, numberFormat.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = InternalServerException.class)
    public ModelAndView internalServerException(InternalServerException internalServer) {
        log.error(internalServer.getMessage());
        return new ModelAndView("/error/500", ATTRIBUTE_NAME_ERROR, internalServer.getMessage());
    }
}

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    public ModelAndView badRequestHandler(BadRequestException badRequest) {
        log.error(badRequest.getMessage());
        ModelAndView modelAndView = new ModelAndView("/error/400");
        modelAndView.addObject("error", badRequest.getMessage());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = UnauthorizedException.class)
    public ModelAndView unauthorizedHandler(UnauthorizedException unauthorized) {
        log.error(unauthorized.getMessage());
        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.addObject("error", unauthorized.getMessage());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = EntityExistsException.class)
    public ModelAndView entityExistsHandler(EntityExistsException notExist) {
        log.error(notExist.getMessage());
        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.addObject("error", notExist.getMessage());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = NumberFormatException.class)
    public ModelAndView numberFormatHandler(NumberFormatException numberFormat) {
        log.error(numberFormat.getMessage());
        ModelAndView modelAndView = new ModelAndView("/error/400");
        modelAndView.addObject("error", numberFormat.getMessage());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = InternalServerException.class)
    public ModelAndView internalServerException(InternalServerException internalServer) {
        log.error(internalServer.getMessage());
        ModelAndView modelAndView = new ModelAndView("/error/500");
        modelAndView.addObject("error", internalServer.getMessage());
        return modelAndView;
    }
}

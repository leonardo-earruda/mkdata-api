package com.mkdata.mkdataapi.exception_handler;

import com.mkdata.mkdataapi.exception_handler.exceptions.DuplicatedCnpjOrRgException;
import com.mkdata.mkdataapi.exception_handler.exceptions.PersonTypeWithWrongDocumentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicatedCnpjOrRgException.class)
    public ResponseError handle(DuplicatedCnpjOrRgException ex) {
        ex.printStackTrace();
        return new ResponseError(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PersonTypeWithWrongDocumentException.class)
    public ResponseError handleWrongDocumentPattern(PersonTypeWithWrongDocumentException ex) {
        ex.printStackTrace();
        return new ResponseError(ex.getMessage());
    }
}

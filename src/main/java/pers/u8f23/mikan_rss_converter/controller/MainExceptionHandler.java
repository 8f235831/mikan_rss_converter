package pers.u8f23.mikan_rss_converter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
@Slf4j
public class MainExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String error(Exception ex) {
        log.error("Exception", ex);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        ex.printStackTrace(writer);
        return stringWriter.toString();
    }
}

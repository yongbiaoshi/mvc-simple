package com.tsingda.simple.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

@ControllerAdvice
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
    private static final Logger logger = LoggerFactory.getLogger(JsonpAdvice.class);

    public JsonpAdvice() {
        super("callback", "jsonp");
        logger.debug("JsonpAdvice 实例化");
    }
}

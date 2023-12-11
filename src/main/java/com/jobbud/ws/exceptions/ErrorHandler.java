package com.jobbud.ws.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RestController
public class ErrorHandler implements ErrorController {
        @Autowired
        ErrorAttributes errorAttributes;

        @RequestMapping("/error")
        ApiError handleError(WebRequest webRequest) {
            Map<String, Object> attributes = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults().including(ErrorAttributeOptions.Include.MESSAGE));

            String message = (String) attributes.get("message");
            String url = (String) attributes.get("path");
            int status = (int) attributes.get("status");

            return new ApiError(status, message, url);
        }

    }
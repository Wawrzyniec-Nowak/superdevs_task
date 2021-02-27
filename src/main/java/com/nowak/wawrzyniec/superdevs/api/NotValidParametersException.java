package com.nowak.wawrzyniec.superdevs.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
class NotValidParametersException extends RuntimeException{

    NotValidParametersException(String msg) {
        super(msg);
    }
}

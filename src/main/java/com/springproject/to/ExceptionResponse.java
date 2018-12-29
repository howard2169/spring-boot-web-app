package com.springproject.to;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExceptionResponse {

    @JsonProperty("errorCode")
    public String errorCode;

    @JsonProperty("content")
    public String content;

}

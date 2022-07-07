package com.frank.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.util.Map;


public class APIResponse {

    public static final Integer OK = 0;

    @JsonIgnore
    private  HttpStatus httpStatus = null;

    @JsonIgnore
    private Integer customHttpStatus = null;

    public APIResponse() {
        super();
    }

    public APIResponse(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public APIResponse(Integer code) {
        super();
        this.code = code;
    }

    Integer code;
    String message;
    String localizedMessage;

    public Integer getCustomHttpStatus() {
        return customHttpStatus;
    }

    public void setCustomHttpStatus(Integer customHttpStatus) {
        this.customHttpStatus = customHttpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @JsonIgnore
    public HttpStatus getHttpStatus(){

        if (this.httpStatus != null) {
            return this.httpStatus;
        }

        return HttpStatus.OK;
    }

    @JsonProperty("errors")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors = null;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}

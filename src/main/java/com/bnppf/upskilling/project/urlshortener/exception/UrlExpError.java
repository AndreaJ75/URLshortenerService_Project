package com.bnppf.upskilling.project.urlshortener.exception;

import com.fasterxml.jackson.annotation.JsonFormat;


public class UrlExpError {

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private String urlError;
        private int status;
        private String error;

        //...getters setters

    public String getUrlError() {
        return urlError;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public void setUrlError(String urlError) {
        this.urlError = urlError;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setError(String error) {
        this.error = error;
    }
}

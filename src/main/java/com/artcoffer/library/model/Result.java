package com.artcoffer.library.model;

/**
 * Simple operation result indicating if operation was successful or hasErrors and if errors the category they represent
 */
public class Result<T> {

    public enum ErrorType {
        NOT_FOUND,
        UNKNOWN_ERROR,
        NONE
    }

    private final boolean errors;

    private final ErrorType errorType;

    private final String errorMessage;

    private final T entity;

    public Result(T entity) {
        this.entity = entity;
        this.errors = false;
        this.errorType = ErrorType.NONE;
        this.errorMessage = "";
    }

    public Result(boolean errors, ErrorType errorType, String errorMessage) {
        this.entity = null;
        this.errors = errors;
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }

    public boolean hasErrors() {
        return errors;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public T getEntity() {
        return entity;
    }
}

package com.jeon.devloper.miniproject.Data;

public class NetworkResult<T> {
    private T result;
    private int code;

    public T getResult() {
        return this.result;
    }
    public int getCode() {
        return this.code;
    }
}

package com.example.springboottest.service;

public interface CallerService {

    void call(String message);

    void callAndCatchException(String message);

    void callNoRollback(String message);

    void callCatchException(String message);
}

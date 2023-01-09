package com.chillycheesy.exceptions;

public class NoSessionSetException extends Exception {

    public NoSessionSetException() {
        super("The mail could not be build because no session was set (you must call the setSession methods).");
    }
}

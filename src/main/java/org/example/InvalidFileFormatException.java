package org.example;

public class InvalidFileFormatException extends Exception{
    private int detail;
    InvalidFileFormatException(int errorCode) {
        detail = errorCode;
    }

    public String toString() {
        return "InvalidFileFormatException [" + detail + "]";
    }
}

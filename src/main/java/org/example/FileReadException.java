package org.example;

public class FileReadException extends Exception{
    private int detail;
    FileReadException(int errorCode) {
        detail = errorCode;
    }
    @Override
    public String toString() {
        return "FileReadException [" + detail + "]";
    }
}

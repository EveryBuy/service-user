package ua.everybuy.errorhandling.exception;

import java.io.IOException;

public class FileFormatException extends IOException {
    public FileFormatException(String message){
        super(message);
    }
}

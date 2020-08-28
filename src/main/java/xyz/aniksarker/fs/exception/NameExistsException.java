package xyz.aniksarker.fs.exception;

public class NameExistsException extends RuntimeException {

    public NameExistsException(String errorMessage) {
        super(errorMessage);
    }

}
package xyz.aniksarker.fs.exception;

public class NodeExistsException extends RuntimeException {
    public NodeExistsException(String errorMessage) {
        super(errorMessage);
    }
}

package edu.nitin.downloder;

/**
 * Created by nitin.verma on 03/04/15.
 */
public class DException extends Exception {
    public DException() {
        super();
    }

    public DException(String message) {
        super(message);
    }

    public DException(String message, Throwable cause) {
        super(message, cause);
    }

    public DException(Throwable cause) {
        super(cause);
    }
}

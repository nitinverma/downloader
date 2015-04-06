package edu.nitin.downloder;

/**
 * Created by nitin.verma on 03/04/15.
 */
public class DownloaderException extends Exception {
    public DownloaderException() {
        super();
    }

    public DownloaderException(String message) {
        super(message);
    }

    public DownloaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public DownloaderException(Throwable cause) {
        super(cause);
    }
}

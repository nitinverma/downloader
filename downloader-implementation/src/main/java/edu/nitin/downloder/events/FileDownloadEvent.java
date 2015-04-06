package edu.nitin.downloder.events;

import edu.nitin.downloder.FileDownloadContext;

/**
 * Created by nitin.verma on 06/04/15.
 */
public class FileDownloadEvent {
    private final FileDownloadContext context;

    public FileDownloadEvent(final FileDownloadContext context) {
        this.context = context;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (context != null) {
            sb.append("URL: ").append(context.getDownloadURL());
        }
        else {
            sb.append("null context");
        }
        sb.append(": ").append(getClass().getName()).append("@").append(System.identityHashCode(this));
        return sb.toString();
    }

    public FileDownloadContext getContext() {
        return context;
    }
}

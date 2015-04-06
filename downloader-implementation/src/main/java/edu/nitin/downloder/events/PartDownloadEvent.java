package edu.nitin.downloder.events;

import edu.nitin.downloder.Range;
import edu.nitin.downloder.FileDownloadContext;

/**
 * Created by nitin.verma on 04/04/15.
 */
public class PartDownloadEvent {
    private final Range range;
    private final FileDownloadContext context;

    public PartDownloadEvent(final FileDownloadContext context, final Range range) {
        this.range = range;
        this.context = context;
    }

    public Range getRange() {
        return range;
    }

    public FileDownloadContext getContext() {
        return context;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (context != null) {
            sb.append("URL: ").append(context.getDownloadURL()).append(",")
            .append("Working Dir: ").append(context.getWorkDir()).append(",");
        }
        else {
            sb.append("null context");
        }

        if (range != null) {
            sb.append("Range: ").append(range);
        }
        else {

            sb.append("null range");
        }

        sb.append(": ").append(getClass().getName()).append("@").append(System.identityHashCode(this));
        return sb.toString();
    }
}

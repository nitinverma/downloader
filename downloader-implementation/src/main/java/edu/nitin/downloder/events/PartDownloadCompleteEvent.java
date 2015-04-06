package edu.nitin.downloder.events;

import edu.nitin.downloder.FileDownloadContext;
import edu.nitin.downloder.Range;

import java.io.File;

/**
 * Created by nitin.verma on 04/04/15.
 */
public class PartDownloadCompleteEvent {

    private final FileDownloadContext context;
    private final Range range;
    private final File partFile;
    private final Thread birthThread = Thread.currentThread();

    public PartDownloadCompleteEvent(final FileDownloadContext context, final Range range, final File partFile) {
        this.context = context;
        this.range = range;
        this.partFile = partFile;
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

        if (range != null) {
            sb.append(", Range: ").append(range);
        }
        else {

            sb.append(", null range");
        }

        if (partFile != null) {
            sb.append(", Part: ").append(partFile.getAbsoluteFile());
        }
        else {

            sb.append(", null range");
        }

        sb.append(", Birth Thread: ").append(birthThread.getName());
        sb.append(": ").append(getClass().getName()).append("@").append(System.identityHashCode(this));
        return sb.toString();
    }

    public FileDownloadContext getContext() {
        return context;
    }

    public Range getRange() {
        return range;
    }

    public File getPartFile() {
        return partFile;
    }

    public Thread getBirthThread() {
        return birthThread;
    }
}

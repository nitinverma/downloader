package edu.nitin.downloder.events;

import edu.nitin.downloder.FileDownloadContext;

/**
 * Created by nitin.verma on 04/04/15.
 */
public class FileDownloadCompleteEvent {

    final FileDownloadContext context;

    public FileDownloadCompleteEvent(final FileDownloadContext context) {
        this.context = context;
    }
}

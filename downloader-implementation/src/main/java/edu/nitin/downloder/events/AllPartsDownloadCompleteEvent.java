package edu.nitin.downloder.events;

import edu.nitin.downloder.FileDownloadContext;
import edu.nitin.downloder.Range;

import java.util.Map;

/**
 * Created by nitin.verma on 04/04/15.
 */
public class AllPartsDownloadCompleteEvent {
    private final FileDownloadContext context;
    private final Map<Range, PartDownloadCompleteEvent> completeRanges;

    public AllPartsDownloadCompleteEvent(final FileDownloadContext context, final Map<Range, PartDownloadCompleteEvent> completeRanges) {
        this.context = context;
        this.completeRanges = completeRanges;
    }

    public FileDownloadContext getContext() {
        return context;
    }

    public Map<Range, PartDownloadCompleteEvent> getCompleteRanges() {
        return completeRanges;
    }
}

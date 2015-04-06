package edu.nitin.downloder;

import edu.nitin.downloder.dagger.Injector;
import edu.nitin.downloder.events.AllPartsDownloadCompleteEvent;
import edu.nitin.downloder.events.PartDownloadCompleteEvent;
import edu.nitin.downloder.otto.EventBus;

import javax.inject.Inject;
import java.util.*;

/**
 * Once use object!
 *
 * Created by nitin.verma on 06/04/15.
 */
public class FileDownloadRanges {

    @Inject
    EventBus bus;

    private final FileDownloadContext context;
    private volatile boolean frozen = false;
    private volatile boolean complete = false;
    private final Object lock = new Object();
    private final Set<Range> triggerRanges = new TreeSet<Range>(new Range.NoRangeOverlapComparator());
    private final Map<Range, PartDownloadCompleteEvent> completeRanges = new TreeMap<Range,PartDownloadCompleteEvent>(new Range.NoRangeOverlapComparator());

    public FileDownloadRanges(final FileDownloadContext context) {
        this.context = context;
        Injector.inject(this);
        bus.register(this);
    }


    public void triggerRange(final Range range) {
        synchronized (lock) {
            if (!frozen && !complete) {
                triggerRanges.add(range);
            }
        }
    }

    public List<Range> freeze() {
        synchronized (lock) {
            if (frozen) {
                throw new RuntimeException("Can not refreeze this object");
            }
            this.frozen = true;
        }
        return new ArrayList<Range>(triggerRanges);
    }

    public void completeRange(final PartDownloadCompleteEvent event) {
        if (event != null && this.context != event.getContext()) {
            throw new RuntimeException("Not the right context");
        }
        synchronized (lock) {
            if (!frozen) {
                throw new RuntimeException("This object should be frozen before part downloads are tiggerred");
            }
            if (complete) {
                throw new RuntimeException("Object in complete state");
            }
            System.out.println(">Tigger Events Size: " + triggerRanges.size());
            if (!complete && event != null && triggerRanges.size() != 0) {
                boolean removed = triggerRanges.remove(event.getRange());
                if (!removed) {
                    throw new RuntimeException("No such range: " + event.getRange());
                }
                completeRanges.put(event.getRange(), event);
                if (triggerRanges.size() == 0) {
                    complete = true;
                }
            }
            System.out.println("<Tigger Events Size: " + triggerRanges.size());
        }

        if (complete) {
            bus.post(new AllPartsDownloadCompleteEvent(context, completeRanges));
        }
    }
}

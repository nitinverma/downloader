package edu.nitin.downloder;

import com.squareup.otto.Subscribe;
import edu.nitin.downloder.dagger.Injector;
import edu.nitin.downloder.events.PartDownloadEvent;
import edu.nitin.downloder.events.PartDownloadCompleteEvent;
import edu.nitin.downloder.otto.EventBus;

import javax.inject.Inject;
import java.util.concurrent.*;

/**
 * Created by nitin.verma on 04/04/15.
 */
public class PartDownloadProcessor {

    @Inject
    EventBus bus;

    private final FileDownloadContext context;

    {
        Injector.inject(this);
        bus.register(this);
    }

    public PartDownloadProcessor(final FileDownloadContext context) {
        this.context = context;
    }

    @Subscribe
    public void onDownloadPartEvent(final PartDownloadEvent event) {
        if (context == event.getContext()) {
            System.out.println("Event: " + event);
            final Future<PartDownloadEvent> future = DownloadExecutorService.getExecutorService().submit(new PartDownloadTask(event));
        }
    }

}

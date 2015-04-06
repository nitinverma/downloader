package edu.nitin.downloder;

import edu.nitin.downloder.dagger.Injector;
import edu.nitin.downloder.events.FileDownloadEvent;
import edu.nitin.downloder.otto.EventBus;

import javax.inject.Inject;

/**
 * Created by nitin.verma on 06/04/15.
 */
public class TriggerFileDownload {
    @Inject
    EventBus bus;

    {
        Injector.inject(this);
        bus.register(this);
    }

    public void download(final FileDownloadContext context) {
        final FileDownloadProcessor fileDownloadProcessor = new FileDownloadProcessor(context);
        final PartDownloadProcessor partDownloadProcessor = new PartDownloadProcessor(context);
        bus.post(new FileDownloadEvent(context));
    }

}

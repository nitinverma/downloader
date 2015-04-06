package edu.nitin.downloder.dagger;

import dagger.Provides;
import edu.nitin.downloder.*;
import edu.nitin.downloder.otto.EventBus;

import javax.inject.Singleton;

/**
 * Created by nitin.verma on 03/04/15.
 */
@dagger.Module(
        injects = {
                FileDownloadProcessor.class,
                PartDownloadProcessor.class,
                PartDownloadTask.class,
                FileDownloadRanges.class,
                TriggerFileDownload.class
        },
        complete = true,
        library = false
)
public class Module {

    private EventBus bus = new EventBus();

    @Provides
    @Singleton
    public EventBus provideEventBus() {
        return bus;
    }
}

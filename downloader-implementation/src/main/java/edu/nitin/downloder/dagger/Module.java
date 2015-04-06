package edu.nitin.downloder.dagger;

import dagger.Provides;
import edu.nitin.downloder.PartDownloadProcessor;
import edu.nitin.downloder.FileDownloadProcessor;
import edu.nitin.downloder.PartDownloadTask;
import edu.nitin.downloder.TriggerFileDownload;
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

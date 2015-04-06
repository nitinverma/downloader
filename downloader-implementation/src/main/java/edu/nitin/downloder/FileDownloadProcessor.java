package edu.nitin.downloder;

import com.squareup.otto.Subscribe;
import edu.nitin.downloder.dagger.Injector;
import edu.nitin.downloder.events.*;
import edu.nitin.downloder.otto.EventBus;

import javax.inject.Inject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by nitin.verma on 03/04/15.
 */
public class FileDownloadProcessor {

    @Inject
    EventBus bus;

    private final FileDownloadContext context;
    private final FileDownloadRanges fileDownloadRanges;

    {
        Injector.inject(this);
        this.bus.register(this);
    }

    public FileDownloadProcessor(final FileDownloadContext context) {
        this.context = context;
        this.fileDownloadRanges = new FileDownloadRanges(context);
    }


    @Subscribe
    public void download(final FileDownloadEvent event) throws DownloaderException {
        if (context == event.getContext()) {
            System.out.println("File: " + event.getContext().getDownloadURL());
            try {
                download0(event.getContext());
            } catch (final DownloaderException de) {
                throw de;
            } catch (final Throwable th) {
                throw new DownloaderException(th);
            }
        } else {
            System.out.println("Not my context");
        }
    }

    private void download0(final FileDownloadContext context) throws Throwable {
        final long length = getContentLength(context);
        System.out.println("L: " + length);
        final int partSize = 1024000; //1000kb
        for (int i = 0; partSize * i < length; i++) {
            long partEnd = ((i + 1) * partSize) - 1;
            partEnd = partEnd > length - 1 ? length - 1 : partEnd;
            final Range range = new Range(i * partSize, partEnd);
            System.out.println("Trigger range: " + range);
            fileDownloadRanges.triggerRange(range);
        }
        final List<Range> ranges = fileDownloadRanges.freeze();
        for (final Range range : ranges) {
            bus.post(new PartDownloadEvent(context, range));
        }
    }

    @Subscribe
    public void onPartDownloadCompleteEvent(final PartDownloadCompleteEvent event) {
        if (context == event.getContext()) {
            System.out.println("Event: " + event);
            fileDownloadRanges.completeRange(event);
        }
    }

    @Subscribe
    public void onAllPartsDownloadCompleteEvent(final AllPartsDownloadCompleteEvent event) {
        if (context == event.getContext()) {
            final File outputFile = context.getOutputFile();
            try {
                OutputStream fos = null;
                try {
                    fos = new BufferedOutputStream(new FileOutputStream(outputFile));
                    for (final PartDownloadCompleteEvent partEvent : event.getCompleteRanges().values()) {
                        System.out.println("Part files to merge: " + partEvent.getPartFile());
                        if (!partEvent.getPartFile().exists()) {
                            throw new RuntimeException("This is odd, partition full?");
                        }
                        InputStream fis = null;
                        try {
                            fis = new BufferedInputStream(new FileInputStream(partEvent.getPartFile()));
                            int read;
                            while (( read = fis.read() ) != -1) {
                                fos.write(read);
                            }
                        }
                        finally {
                            if (fis != null) fis.close();
                        }
                        if (!partEvent.getPartFile().delete() ){
                            System.err.println("Could not delete file: " + partEvent.getPartFile().getAbsolutePath());
                        }
                    }

                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                }
                bus.post(new FileDownloadCompleteEvent(context));
            } catch (final Throwable th) {
                throw new RuntimeException(th);
            }
        }
    }

    private long getContentLength(final FileDownloadContext context) throws Throwable {
        HttpURLConnection urlConnection = null;
        long contentLength = 0;
        try {
            final URL url = context.getDownloadURL();
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("HEAD");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setAllowUserInteraction(false);
            urlConnection.setDoOutput(false);
            urlConnection.setRequestProperty("Accept", "*/*");
            final double start = System.nanoTime();
            urlConnection.connect();
            final double end = System.nanoTime();
            final double delta = end - start;
            contentLength = urlConnection.getContentLengthLong();

            System.out.println("Time to connect: " + delta + " nano sec");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return contentLength;
    }

}

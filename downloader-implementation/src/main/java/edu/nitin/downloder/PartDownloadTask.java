package edu.nitin.downloder;

import edu.nitin.downloder.dagger.Injector;
import edu.nitin.downloder.events.PartDownloadCompleteEvent;
import edu.nitin.downloder.events.PartDownloadEvent;
import edu.nitin.downloder.otto.EventBus;

import javax.inject.Inject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * Created by nitin.verma on 04/04/15.
 */
public class PartDownloadTask implements Callable<PartDownloadEvent> {

    @Inject
    EventBus bus;

    private final PartDownloadEvent event;

    {
        Injector.inject(this);
        bus.register(this);
    }

    public PartDownloadTask(final PartDownloadEvent event) {
        this.event = event;
    }

    @Override
    public PartDownloadEvent call() throws Exception {
        try {
            event.getContext().getWorkDir().mkdirs();
            final File file  = event.getContext().getPartFile(event.getRange());

            long byteStart = event.getRange().start;
            boolean append = false;

            if (file.exists()) {
                append = true;
                final long size = file.length();
                byteStart = event.getRange().start + size;
            }

            if (byteStart < event.getRange().end) {
                final OutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(file, append));
                try {
                    final URL url = event.getContext().getDownloadURL();
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("Range", "bytes=" + byteStart + "-" + event.getRange().end);
                    urlConnection.setConnectTimeout(10000);
                    urlConnection.setReadTimeout(10000);
                    urlConnection.setAllowUserInteraction(false);
                    urlConnection.setRequestProperty("Accept", "*/*");

                    final double start = System.nanoTime();
                    urlConnection.connect();
                    System.out.println("Respnse Code: " + urlConnection.getResponseCode());
                    System.out.println("Content-Length: " + urlConnection.getContentLengthLong());
                    System.out.println("Part File: " + file);

                    InputStream inputStream = urlConnection.getInputStream();
                    long size = 0;

                    int read;
                    while ((read = inputStream.read()) != -1) {
                        fileOutputStream.write(read);
                        size++;
                    }
                    final double end = System.nanoTime();
                    final double delta = end - start;

                    System.out.println("Downloaded Size: " + size + ":" + size / delta);
                } finally {
                    fileOutputStream.close();
                }
            }

            bus.post(new PartDownloadCompleteEvent(event.getContext(), event.getRange(), file));
        }
        catch (final Throwable th) {
            th.printStackTrace();
            throw new DownloaderException(th);
        }
        return event;
    }
}

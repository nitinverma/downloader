package edu.nitin.downloder;

import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nitin.verma on 03/04/15.
 */
public class UrlDTest {
    @Test
    public void test() throws DownloaderException, InterruptedException {
        final FileDownloadContext context1 = new FileDownloadContext();
        {
            final String s = "https://s3-ap-southeast-1.amazonaws.com/com.inmobi.oem.ap-southeast-1/com.inmobi.oem.core.services/1.1.5/app-10105.apk";
            context1.setDownloadLocation(new File("/tmp/app.apk"));
            try {
                context1.setDownloadURL(new URL(s));
            } catch (MalformedURLException e) {
                throw new DownloaderException(e);
            }
            context1.setWorkDir(new File("/tmp/context1/." + getClass().getPackage().getName()));
        }
        final FileDownloadContext context2 = new FileDownloadContext();
        {
            final String s = "https://s3-ap-southeast-1.amazonaws.com/com.inmobi.oem.ap-southeast-1/com.inmobi.oem.core.services/1.1.5/app-10105.apk";
            context2.setDownloadLocation(new File("/tmp/app.apk"));
            try {
                context2.setDownloadURL(new URL(s));
            } catch (MalformedURLException e) {
                throw new DownloaderException(e);
            }
            context2.setWorkDir(new File("/tmp/context2/." + getClass().getPackage().getName()));
        }

        {
            final TriggerFileDownload triggerFileDownload = new TriggerFileDownload();
            triggerFileDownload.download(context1);
            triggerFileDownload.download(context2);
        }

        Thread.sleep(20000);
    }
}

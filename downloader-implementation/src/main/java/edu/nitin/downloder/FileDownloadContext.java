package edu.nitin.downloder;

import java.io.File;
import java.net.URL;

/**
 * Created by nitin.verma on 03/04/15.
 */
public class FileDownloadContext {

    private URL downloadURL;
    private File downloadLocation;
    private File workDir;

    public File getWorkDir() {
        return workDir;
    }

    public void setWorkDir(final File workDir) {
        this.workDir = workDir;
    }
    public URL getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(URL downloadURL) {
        this.downloadURL = downloadURL;
    }

    public File getDownloadLocation() {
        return downloadLocation;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("URL: ").append(downloadURL);
        sb.append(": ").append(getClass().getName()).append("@").append(System.identityHashCode(this));
        return sb.toString();
    }

    public void setDownloadLocation(File downloadLocation) {
        this.downloadLocation = downloadLocation;
    }
}

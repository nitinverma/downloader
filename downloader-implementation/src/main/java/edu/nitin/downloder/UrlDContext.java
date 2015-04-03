package edu.nitin.downloder;

import java.net.URL;

/**
 * Created by nitin.verma on 03/04/15.
 */
public class UrlDContext implements DContext {
    private URL downloadURL;

    public URL getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(URL downloadURL) {
        this.downloadURL = downloadURL;
    }
}

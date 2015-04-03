package edu.nitin.downloder;

/**
 * Created by nitin.verma on 03/04/15.
 */
public class UrlDConfig implements DConfig {

    private int numberOfDownloadThreads = 5;

    public int getNumberOfDownloadThreads() {
        return numberOfDownloadThreads;
    }

    public void setNumberOfDownloadThreads(int numberOfDownloadThreads) {
        this.numberOfDownloadThreads = numberOfDownloadThreads;
    }
}

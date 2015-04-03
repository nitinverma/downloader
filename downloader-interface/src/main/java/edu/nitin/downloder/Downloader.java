package edu.nitin.downloder;

/**
 * Created by nitin.verma on 03/04/15.
 */
public interface Downloader {

    /**
     *
     * @param context
     * @param config
     * @throws DException
     */
    public void download(final DContext context, final DConfig config) throws DException;
}

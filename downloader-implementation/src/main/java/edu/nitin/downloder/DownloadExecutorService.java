package edu.nitin.downloder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by nitin.verma on 06/04/15.
 */
public class DownloadExecutorService {
    private final static ExecutorService executorService = Executors.newWorkStealingPool(10);

    public static ExecutorService getExecutorService() {
        return executorService;
    }
}

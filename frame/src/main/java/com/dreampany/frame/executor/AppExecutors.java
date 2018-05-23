package com.dreampany.frame.executor;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

/**
 * Created by Hawladar Roman on 26/4/18.
 * Dreampany
 * dreampanymail@gmail.com
 */

@Singleton
public class AppExecutors {

    private static final int THREAD_COUNT = 3;

    private final Executor mainThread;

    private final Executor diskIO;

    private final Executor networkIO;

    public AppExecutors() {
        this(
                new AppExecutors.MainThreadExecutor(),
                new AppExecutors.DiskIOThreadExecutor(),
                new AppExecutors.NetworkIOThreadExecutor()
        );
    }

    public AppExecutors(Executor mainThread, Executor diskIO, Executor networkIO) {
        this.mainThread = mainThread;
        this.diskIO = diskIO;
        this.networkIO = networkIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

    private static class DiskIOThreadExecutor implements Executor {
        private final Executor diskIO;

        public DiskIOThreadExecutor() {
            diskIO = Executors.newSingleThreadExecutor();
        }

        @Override
        public void execute(@NonNull Runnable command) {
            diskIO.execute(command);
        }
    }

    private static class NetworkIOThreadExecutor implements Executor {
        private final Executor networkIO;

        public NetworkIOThreadExecutor() {
            networkIO = Executors.newFixedThreadPool(THREAD_COUNT);
        }

        @Override
        public void execute(@NonNull Runnable command) {
            networkIO.execute(command);
        }
    }
}

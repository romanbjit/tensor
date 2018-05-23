package com.dreampany.frame.injector;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * Created by air on 4/4/18.
 */

@Module
public abstract class AppModule {
    @Binds
    abstract Context bindContext(Application application);
}

package com.unicom.carapp.di.model;

import com.unicom.carapp.data.remote.ApiServiceTest;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.scopes.ActivityRetainedScoped;
import retrofit2.Retrofit;

@InstallIn(ActivityRetainedComponent.class)
@Module
public class ServiceModule {

    @Provides
    @ActivityRetainedScoped
    ApiServiceTest provideService(Retrofit retrofit) {
        return retrofit.create(ApiServiceTest.class);
    }
}
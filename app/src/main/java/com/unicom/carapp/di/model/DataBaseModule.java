package com.unicom.carapp.di.model;

import android.app.Application;

import androidx.room.Room;

import com.unicom.carapp.room.CarDB;
import com.unicom.carapp.room.dao.CarsDao;
import com.unicom.carapp.room.dao.CarsRemoteKeyDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DataBaseModule {

    @Provides
    @Singleton
    CarDB provideDB(Application application){
        return Room.databaseBuilder(application , CarDB.class , "car_db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    CarsDao provideDao(CarDB carDB){
        return carDB.carsDao();
    }

    @Provides
    @Singleton
    CarsRemoteKeyDao provideRemoteKeysDao(CarDB carDB){
        return carDB.carsRemoteKeyDao();
    }
}

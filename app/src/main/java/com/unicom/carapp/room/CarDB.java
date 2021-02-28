package com.unicom.carapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.unicom.carapp.room.dao.CarsDao;
import com.unicom.carapp.room.dao.CarsRemoteKeyDao;
import com.unicom.carapp.room.entity.Car;
import com.unicom.carapp.room.entity.CarItemRemoteKeys;

@Database(entities = {Car.class, CarItemRemoteKeys.class} , version = 1 , exportSchema = false)
public abstract class CarDB extends RoomDatabase {
    public abstract CarsDao carsDao();
    public abstract CarsRemoteKeyDao carsRemoteKeyDao();
}

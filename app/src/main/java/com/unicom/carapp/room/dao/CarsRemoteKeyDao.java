package com.unicom.carapp.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.unicom.carapp.room.entity.CarItemRemoteKeys;

import java.util.List;

@Dao
public interface CarsRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CarItemRemoteKeys> remoteKeys);

    @Query("SELECT * FROM car_item_remote_key WHERE newsId = :carsId")
    CarItemRemoteKeys remoteKeysByCarsId(int carsId);

    @Query("DELETE FROM car_item_remote_key")
    int clearRemoteKeys();
}

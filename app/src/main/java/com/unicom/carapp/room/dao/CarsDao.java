package com.unicom.carapp.room.dao;

import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.unicom.carapp.room.entity.Car;

import java.util.List;

@Dao
public interface CarsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCarsList(List<Car> users);

    @Query("SELECT * FROM cars_table")
    PagingSource<Integer, Car> observeCarsPaginated();

    @Query("DELETE FROM cars_table")
    int deleteCarsItems();

}

package com.unicom.carapp.data.repository;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingSource;
import androidx.paging.rxjava3.PagingRx;
import com.unicom.carapp.data.remote.ApiServiceTest;
import com.unicom.carapp.room.dao.CarsDao;
import com.unicom.carapp.room.entity.Car;

import javax.inject.Inject;
import dagger.hilt.android.scopes.ActivityRetainedScoped;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import kotlin.jvm.functions.Function0;

@ActivityRetainedScoped
public class ApiRepositoryTest {
//    private CarPagingSource carPagingSource;
    private CarsDao carsDao;
    private CarRemoteMediator carRemoteMediator;

    @Inject
    public ApiRepositoryTest(CarRemoteMediator carRemoteMediator,CarsDao carsDao) {
//        this.carPagingSource = carPagingSource;
        this.carRemoteMediator = carRemoteMediator;
        this.carsDao = carsDao;
    }

    public Flowable<PagingData<Car>> getCars() {
//        Pager<Integer, Car> pager =  new Pager(new PagingConfig(5, 5, false), new Function0<PagingSource>() {
//            @Override
//            public PagingSource invoke() {
//                return carsDao.observeCarsPaginated();
//            }
//        });
//        Flowable<PagingData<Car>> flowAble = PagingRx.getFlowable(pager);
//        return flowAble;

        Pager<Integer, Car> pager =  new Pager(new PagingConfig(5 , 5 , true), null, carRemoteMediator, new Function0<PagingSource>() {
            @Override
            public PagingSource invoke() {
                return carsDao.observeCarsPaginated();
            }
        });

        Flowable<PagingData<Car>> flowAble = PagingRx.getFlowable(pager);
        return flowAble;
    }


}




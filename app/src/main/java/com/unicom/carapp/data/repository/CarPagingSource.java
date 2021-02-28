package com.unicom.carapp.data.repository;

import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.unicom.carapp.data.remote.ApiServiceTest;
import com.unicom.carapp.data.response.CarsResponse;
import com.unicom.carapp.room.entity.Car;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

@ActivityRetainedScoped
public class CarPagingSource extends RxPagingSource<Integer, Car> {
    private ApiServiceTest apiServiceTest;

    @Inject
    public CarPagingSource(ApiServiceTest apiServiceTest) {
        this.apiServiceTest = apiServiceTest;
    }

    @NotNull
    @Override
    public Single<LoadResult<Integer, Car>> loadSingle(@NotNull LoadParams<Integer> loadParams) {
       int page =  loadParams.getKey() != null ? loadParams.getKey() : 1;
        return apiServiceTest.getCarsData(page)
                .subscribeOn(Schedulers.io())
                .map(new Function<CarsResponse, LoadResult<Integer, Car>>() {
                    @Override
                    public LoadResult<Integer, Car> apply(CarsResponse carsResponse) throws Throwable {
                        List<Car> cars = carsResponse.getData();
                        Integer prevPage = page == 1 ? null : page-1;
                        Integer nextPage = page == 4 ? null : page+1;
                        return new LoadResult.Page(cars , prevPage , nextPage);
                    }
                })
                .onErrorReturn(new Function<Throwable, LoadResult<Integer, Car>>() {
                    @Override
                    public LoadResult<Integer, Car> apply(Throwable throwable) throws Throwable {
                        return new LoadResult.Error<>(throwable);
                    }
                });
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NotNull PagingState<Integer, Car> pagingState) {
        return null;
    }
}

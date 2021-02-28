package com.unicom.carapp.data.repository;

import androidx.annotation.experimental.UseExperimental;
import androidx.paging.ExperimentalPagingApi;
import androidx.paging.LoadType;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxRemoteMediator;

import com.unicom.carapp.data.remote.ApiServiceTest;
import com.unicom.carapp.data.response.CarsResponse;
import com.unicom.carapp.room.dao.CarsDao;
import com.unicom.carapp.room.dao.CarsRemoteKeyDao;
import com.unicom.carapp.room.entity.Car;
import com.unicom.carapp.room.entity.CarItemRemoteKeys;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.OptIn;
import retrofit2.HttpException;


@UseExperimental(markerClass = ExperimentalPagingApi.class)
@ActivityRetainedScoped
public class CarRemoteMediator extends RxRemoteMediator<Integer, Car> {
    CarsDao carsDao;
    CarsRemoteKeyDao carsRemoteKeyDao;
    private ApiServiceTest apiServiceTest;
    private Integer initialPage = 1;
    Integer page;

    @Inject
    public CarRemoteMediator(CarsDao carsDao, ApiServiceTest apiServiceTest, CarsRemoteKeyDao carsRemoteKeyDao) {
        this.carsDao = carsDao;
        this.apiServiceTest = apiServiceTest;
        this.carsRemoteKeyDao = carsRemoteKeyDao;
    }

    @NotNull
    @Override
    public Single<MediatorResult> loadSingle(@NotNull LoadType loadType, @NotNull PagingState<Integer, Car> pagingState) {
        // The network load method takes an optional after=<user.id> parameter. For
        // every page after the first, pass the last user ID to let it continue from
        // where it left off. For REFRESH, pass null to load the first page.
//        Integer loadKey = null;

        switch (loadType) {
            case REFRESH:
                CarItemRemoteKeys remoteKeys = getRemoteKeyClosestToCurrentPosition(pagingState);
                page = remoteKeys != null ? (remoteKeys.getNextKey() - 1) : initialPage;
                break;
            case PREPEND:
                // In this example, you never need to prepend, since REFRESH will always
                // load the first page in the list. Immediately return, reporting end of
                // pagination.
//                CarItemRemoteKeys remoteKeysPrepend = getRemoteKeyForFirstItem(pagingState);
//                if (remoteKeysPrepend == null) {
//                    try {
//                        throw new InvalidObjectException("Remote key and the prevKey should not be null");
//                    } catch (InvalidObjectException e) {
//                        e.printStackTrace();
//                    }
//                }
//                page = remoteKeysPrepend.getPrevKey();
//                if (remoteKeysPrepend.getPrevKey() == null) {
//                    return Single.just(new MediatorResult.Success(false));
//                }
                break;
            case APPEND:
                CarItemRemoteKeys remoteKeysAppend = getRemoteKeyForLastItem(pagingState);
                // You must explicitly check if the last item is null when appending,
                // since passing null to networkService is only valid for initial load.
                // If lastItem is null it means no items were loaded after the initial
                // REFRESH and there are no more items to load.
                if (remoteKeysAppend == null) {
                    try {
                        throw new InvalidObjectException("Remote key should not be null for $loadType");
                    } catch (InvalidObjectException e) {
                        e.printStackTrace();
                    }
                }
                if (remoteKeysAppend.getNextKey() == null){
                    return Single.just(new MediatorResult.Success(true));
                }
                page = remoteKeysAppend.getNextKey();
                break;
        }

        return apiServiceTest.getCarsData(page)
                .subscribeOn(Schedulers.io())
                .map(new Function<CarsResponse, MediatorResult>() {
                    @Override
                    public MediatorResult apply(CarsResponse carsResponse) throws Throwable {
                        if (loadType == LoadType.REFRESH) {
                            carsRemoteKeyDao.clearRemoteKeys();
                            carsDao.deleteCarsItems();
                        }
                        Integer prevPage = page == 1 ? null : page - 1;
                        Integer nextPage = page == 4 ? null : page + 1;
                        List<CarItemRemoteKeys> remoteKeys = new ArrayList<>();
                        for (int i = 0; i < carsResponse.getData().size(); i++) {
                             remoteKeys.add(new CarItemRemoteKeys(carsResponse.getData().get(i).getId(), prevPage, nextPage));
                        }
                        carsDao.insertCarsList(carsResponse.getData());
                        carsRemoteKeyDao.insertAll(remoteKeys);
                        int q = carsResponse.getData().size();
                        int y = pagingState.getConfig().pageSize;
                        boolean endOfPaginationReached =  q < y;


                        return new MediatorResult.Success(endOfPaginationReached);
                    }
                })
                .onErrorResumeNext(new Function<Throwable, SingleSource<? extends MediatorResult>>() {
                    @Override
                    public SingleSource<? extends MediatorResult> apply(Throwable e) throws Throwable {
                        if (e instanceof IOException || e instanceof HttpException) {
                            return Single.just(new MediatorResult.Error(e));
                        }
                        return Single.error(e);
                    }
                });
    }

    @NotNull
    @Override
    public Single<InitializeAction> initializeSingle() {
        return super.initializeSingle();
    }

    private CarItemRemoteKeys getRemoteKeyForLastItem(PagingState<Integer, Car> state) {
        Car car = state.lastItemOrNull();
        return carsRemoteKeyDao.remoteKeysByCarsId(car.getId());
    }

    private CarItemRemoteKeys getRemoteKeyForFirstItem(PagingState<Integer, Car> state) {
        Car car = state.firstItemOrNull();
        return carsRemoteKeyDao.remoteKeysByCarsId(car.getId());
    }

    private CarItemRemoteKeys getRemoteKeyClosestToCurrentPosition(PagingState<Integer, Car> state) {
        Integer pos = state.getAnchorPosition();
        if (pos == null) {
            return null;
        }
        return carsRemoteKeyDao.remoteKeysByCarsId(state.closestItemToPosition(pos).getId());
    }

}

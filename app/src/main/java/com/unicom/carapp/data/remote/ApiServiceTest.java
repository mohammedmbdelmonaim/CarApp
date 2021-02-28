package com.unicom.carapp.data.remote;

import com.unicom.carapp.data.response.CarsResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServiceTest {

    @GET("api/v1/cars")
    Single<CarsResponse> getCarsData(@Query("page") int page);
}
/*
 Single<CarItemRemoteKeys> remoteKeySingle = null;
        switch (loadType) {
            case REFRESH:
                remoteKeySingle = getRemoteKeyClosestToCurrentPosition(pagingState);
//                page = remoteKeys != null ? (remoteKeys.getNextKey() - 1) : initialPage;
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
                return Single.just(new MediatorResult.Success(true));
//                break;
            case APPEND:
                remoteKeySingle = getRemoteKeyForLastItem(pagingState);
                // You must explicitly check if the last item is null when appending,
                // since passing null to networkService is only valid for initial load.
                // If lastItem is null it means no items were loaded after the initial
                // REFRESH and there are no more items to load.
//                if (remoteKeysAppend == null) {
//                    try {
//                        throw new InvalidObjectException("Remote key should not be null for $loadType");
//                    } catch (InvalidObjectException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (remoteKeysAppend.getNextKey() == null){
//                    return Single.just(new MediatorResult.Success(true));
//                }
//                page = remoteKeysAppend.getNextKey();
                break;
        }
        return remoteKeySingle
                .subscribeOn(Schedulers.io())
                .flatMap((Function<CarItemRemoteKeys, Single<MediatorResult>>) remoteKey -> {
                    // You must explicitly check if the page key is null when appending,
                    // since null is only valid for initial load. If you receive null
                    // for APPEND, that means you have reached the end of pagination and
                    // there are no more items to load.
                    if (loadType != LoadType.REFRESH && remoteKey.getNextKey() == null) {
                        return Single.just(new MediatorResult.Success(true));
                    }
                    if (remoteKey.getNextKey() == null){
                        page = 1;
                    }else{
                        page = remoteKey.getNextKey();
                    }
 */
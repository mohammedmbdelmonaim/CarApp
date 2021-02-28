package com.unicom.carapp.ui.cars;

import android.content.Context;
import android.util.Log;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.unicom.carapp.data.repository.ApiRepositoryTest;
import com.unicom.carapp.room.entity.Car;

import dagger.hilt.android.qualifiers.ActivityContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlinx.coroutines.CoroutineScope;
import retrofit2.Retrofit;

public class CarsViewModel extends ViewModel {
    private SavedStateHandle savedStateHandle;
    private ApiRepositoryTest apiRepositoryTest;
    private Retrofit retrofit;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private Context context;
    private MutableLiveData<PagingData<Car>> mCarsData;

    @ViewModelInject
    public CarsViewModel(@Assisted SavedStateHandle savedStateHandle, @ActivityContext Context context , ApiRepositoryTest apiRepositoryTest, Retrofit retrofit) {
        this.savedStateHandle = savedStateHandle;
        this.context = context;
        this.apiRepositoryTest = apiRepositoryTest;
        this.retrofit = retrofit;
    }

    public LiveData<PagingData<Car>> getCarsLiveData() {
        if (mCarsData == null) {
            mCarsData = new MutableLiveData<>();
           getCarsData();
        }
        return mCarsData;
    }

    private void getCarsData() {
//        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
//        PagingRx.cachedIn(apiRepositoryTest.getCars(), viewModelScope);
        apiRepositoryTest.getCars().observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleSuccess , this::handleError);
    }

    private void handleError(Throwable throwable) {
        Log.d("TAG", "handleError: "+throwable);
    }

    public void onRefresh(){
        getCarsData();
    }

    private void handleSuccess(PagingData<Car> carPagingData) {
        mCarsData.setValue(carPagingData);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}

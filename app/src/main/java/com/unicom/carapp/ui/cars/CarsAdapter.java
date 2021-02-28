package com.unicom.carapp.ui.cars;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.unicom.carapp.R;
import com.unicom.carapp.data.response.CarsResponse;
import com.unicom.carapp.databinding.LayoutCarRowBinding;
import com.unicom.carapp.room.entity.Car;

import javax.inject.Inject;

import dagger.hilt.android.scopes.FragmentScoped;

@FragmentScoped
public class CarsAdapter extends PagingDataAdapter<Car, CarsAdapter.CarsViewHolder> {

    LayoutInflater layoutInflater;

    @Inject
    public CarsAdapter() {
        super(Car.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public CarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        LayoutCarRowBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_car_row, parent, false);
        return new CarsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CarsViewHolder holder, int position) {
        Car item = getItem(position);
        holder.binding.setCar(item);
        holder.binding.executePendingBindings();
    }

    public void retry(View view) {
        retry();
    }

    class CarsViewHolder extends RecyclerView.ViewHolder {
        private final LayoutCarRowBinding binding;

        public CarsViewHolder(final LayoutCarRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

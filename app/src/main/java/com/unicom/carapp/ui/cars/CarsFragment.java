package com.unicom.carapp.ui.cars;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.LoadState;
import androidx.paging.PagingData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unicom.carapp.R;
import com.unicom.carapp.databinding.FragmentCarsBinding;
import com.unicom.carapp.room.entity.Car;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CarsFragment extends Fragment {
    FragmentCarsBinding binding;
    CarsViewModel viewModel;
    NavController navController;
    @Inject
    CarsAdapter carsAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CarsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_cars , container , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (navController == null){
            navController = Navigation.findNavController(view);
        }
        binding.setLifecycleOwner(this);
        loadState();
        observeCars();
        refreshData();
    }

    private void refreshData() {
        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.onRefresh();
            }
        });
    }

    private void loadState() {

        binding.carsRecycler.setAdapter(carsAdapter.withLoadStateFooter(new CarsFooterStateAdapter(carsAdapter::retry)));
        carsAdapter.addLoadStateListener(loadStates -> {
            binding.refresh.setRefreshing(loadStates.getSource().getRefresh() instanceof LoadState.Loading
                    ? true : false);
            binding.error.errorContainer.setVisibility(loadStates.getSource().getRefresh() instanceof LoadState.Error
                    ? View.VISIBLE : View.GONE);
          binding.carsRecycler.setVisibility(loadStates.getSource().getRefresh() instanceof LoadState.Error
                    ? View.GONE : View.VISIBLE);
          if (loadStates.getSource().getRefresh() instanceof LoadState.Error){
              binding.error.btnRetry.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      viewModel.onRefresh();
                  }
              });
          }
            return null;
        });

    }

    private void observeCars() {
        viewModel.getCarsLiveData().observe(getViewLifecycleOwner(), new Observer<PagingData<Car>>() {
            @Override
            public void onChanged(PagingData<Car> carPagingData) {
                carsAdapter.submitData(getLifecycle(),carPagingData);
            }
        });
    }
}
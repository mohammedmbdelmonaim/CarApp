package com.unicom.carapp.ui.cars;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.unicom.carapp.R;
import com.unicom.carapp.databinding.LoadStateItemBinding;

import org.jetbrains.annotations.NotNull;

public class CarsFooterStateAdapter extends LoadStateAdapter<CarsFooterStateAdapter.CarsFooterStateViewHolder> {
    private View.OnClickListener mRetryCallback;

    public CarsFooterStateAdapter(View.OnClickListener retryCallback) {
        mRetryCallback = retryCallback;
    }

    @Override
    public void onBindViewHolder(@NotNull CarsFooterStateViewHolder carsFooterStateViewHolder, @NotNull LoadState loadState) {
        carsFooterStateViewHolder.bind(loadState);
    }

    @NotNull
    @Override
    public CarsFooterStateViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, @NotNull LoadState loadState) {
        return new CarsFooterStateViewHolder(viewGroup, mRetryCallback);
    }

    class CarsFooterStateViewHolder extends RecyclerView.ViewHolder {
        LoadStateItemBinding binding;

        public CarsFooterStateViewHolder(@NonNull ViewGroup parent,
                                         @NonNull View.OnClickListener retryCallback) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.load_state_item, parent, false));
            binding = LoadStateItemBinding.bind(itemView);
            binding.btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    retryCallback.onClick(view);
                }
            });
        }

        public void bind(LoadState loadState) {
            if (loadState instanceof LoadState.Error) {
                LoadState.Error loadStateError = (LoadState.Error) loadState;
                binding.tvErrorMessage.setText(loadStateError.getError().getLocalizedMessage());
            }
            binding.progressBar.setVisibility(loadState instanceof LoadState.Loading
                    ? View.VISIBLE : View.GONE);
            binding.btnRetry.setVisibility(loadState instanceof LoadState.Error
                    ? View.VISIBLE : View.GONE);
            binding.tvErrorMessage.setVisibility(loadState instanceof LoadState.Error
                    ? View.VISIBLE : View.GONE);
        }
    }
}

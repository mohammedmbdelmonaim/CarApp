package com.unicom.carapp.room.entity;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.unicom.carapp.R;

import java.util.Objects;

@Entity(tableName = "cars_table")
public class Car {
    @PrimaryKey
    private int id;
    private String brand;
    private String constractionYear;
    private Boolean isUsed;
    private String imageUrl;

    public Car(int id, String brand, String constractionYear, Boolean isUsed, String imageUrl) {
        this.id = id;
        this.brand = brand;
        this.constractionYear = constractionYear;
        this.isUsed = isUsed;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getConstractionYear() {
        return constractionYear;
    }

    public void setConstractionYear(String constractionYear) {
        this.constractionYear = constractionYear;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id) &&
                Objects.equals(brand, car.brand) &&
                Objects.equals(constractionYear, car.constractionYear) &&
                Objects.equals(isUsed, car.isUsed) &&
                Objects.equals(imageUrl, car.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, constractionYear, isUsed, imageUrl);
    }

    public static DiffUtil.ItemCallback<Car> DIFF_CALLBACK = new DiffUtil.ItemCallback<Car>() {
        @Override
        public boolean areItemsTheSame(@NonNull Car oldItem, @NonNull Car newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Car oldItem, @NonNull Car newItem) {
            return oldItem.equals(newItem);
        }
    };

    @BindingAdapter("android:loadCarImage")
    public static void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView)
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher_car)
                .into(imageView);
    }
}

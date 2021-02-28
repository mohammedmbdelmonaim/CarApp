package com.unicom.carapp.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "car_item_remote_key")
public class CarItemRemoteKeys {
    @PrimaryKey
    private Integer newsId;
    private Integer prevKey;
    private Integer nextKey;

    public CarItemRemoteKeys(Integer newsId, Integer prevKey, Integer nextKey) {
        this.newsId = newsId;
        this.prevKey = prevKey;
        this.nextKey = nextKey;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public Integer getPrevKey() {
        return prevKey;
    }

    public void setPrevKey(Integer prevKey) {
        this.prevKey = prevKey;
    }

    public Integer getNextKey() {
        return nextKey;
    }

    public void setNextKey(Integer nextKey) {
        this.nextKey = nextKey;
    }
}

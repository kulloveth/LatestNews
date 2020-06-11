package com.kulloveth.newsfeed.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import java.util.List;

import io.reactivex.Completable;

@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertFavorite(FavoriteEntity favoriteEntity);

    @Query("Select * from article_info")
    public LiveData<List<FavoriteEntity>> fetchFavorite();

    @Delete
    Completable deleteFavorite(FavoriteEntity favoriteEntity);

}

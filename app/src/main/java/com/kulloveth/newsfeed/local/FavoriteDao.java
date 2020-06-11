package com.kulloveth.newsfeed.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;


import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertFavorite(FavoriteEntity favoriteEntity);

    public LiveData<List<FavoriteEntity>> fetchFavorite();

    @Delete
    void deleteFavorite(FavoriteEntity favoriteEntity);

}

package com.kulloveth.latestnews.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import java.util.List;

import io.reactivex.Completable;

/**
 * Create statement to Query Room db
 */

@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     Completable insertFavorite(FavoriteEntity favoriteEntity);

    @Query("Select * from article_info")
    LiveData<List<FavoriteEntity>> fetchFavorite();

    @Delete
    Completable deleteFavorite(FavoriteEntity favoriteEntity);

}

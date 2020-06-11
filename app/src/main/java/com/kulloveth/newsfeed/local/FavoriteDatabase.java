package com.kulloveth.newsfeed.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = FavoriteEntity.class, version = 1, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {
    public abstract FavoriteDao favoriteDao();

    public static volatile FavoriteDatabase INSTANCE;

    public static FavoriteDatabase getINSTANCE(final Context context) {
        if (INSTANCE != null) {
            synchronized (FavoriteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FavoriteDatabase.class, "favorite-db")
                            .fallbackToDestructiveMigration().build();
                }
            }

        }
        return INSTANCE;
    }

}

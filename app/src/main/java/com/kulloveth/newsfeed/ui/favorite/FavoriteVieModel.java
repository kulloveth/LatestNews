package com.kulloveth.newsfeed.ui.favorite;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.kulloveth.newsfeed.local.FavoriteDao;
import com.kulloveth.newsfeed.local.FavoriteDatabase;
import com.kulloveth.newsfeed.local.FavoriteEntity;

import java.util.List;

public class FavoriteVieModel extends AndroidViewModel {
    private MutableLiveData<List<FavoriteEntity>> favoriteLiveData;
    FavoriteEntity favoriteEntity;
    FavoriteDatabase database;
    private FavoriteDao favoriteDao;

    public FavoriteVieModel(@NonNull Application application) {
        super(application);

        favoriteLiveData = new MutableLiveData<>();
        favoriteEntity = new FavoriteEntity();
        database = FavoriteDatabase.getINSTANCE(application);
        favoriteDao = database.favoriteDao();
    }

    public void insertFavorite(FavoriteEntity favoriteEntity) {
        new InsertAsyncTask(favoriteDao).execute(favoriteEntity);
    }

    private class InsertAsyncTask extends AsyncTask<FavoriteEntity, Void, Void> {
        FavoriteDao favoriteDao;

        public InsertAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(FavoriteEntity... favoriteEntities) {
            favoriteDao.insertFavorite(favoriteEntities[0]);
            return null;
        }
    }
}

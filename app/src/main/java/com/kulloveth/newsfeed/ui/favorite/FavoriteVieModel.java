package com.kulloveth.newsfeed.ui.favorite;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kulloveth.newsfeed.local.FavoriteDao;
import com.kulloveth.newsfeed.local.FavoriteDatabase;
import com.kulloveth.newsfeed.local.FavoriteEntity;

import java.util.List;

import io.reactivex.Completable;

public class FavoriteVieModel extends AndroidViewModel {
    private MutableLiveData<List<FavoriteEntity>> favoriteLiveData;
    FavoriteEntity favoriteEntity;
    private FavoriteDao favoriteDao;
    FavoriteDatabase database;





    public FavoriteVieModel(Application application) {
        super(application);
        database = FavoriteDatabase.getINSTANCE(application);
        favoriteDao = database.favoriteDao();
        favoriteLiveData = new MutableLiveData<>();
        favoriteEntity = new FavoriteEntity();


    }

    public void insertFavorite(FavoriteEntity favoriteEntity) {
        new InsertAsyncTask(favoriteDao).execute(favoriteEntity);
    }


    public LiveData<List<FavoriteEntity>> fetchFavoriteEntity() {
        return favoriteDao.fetchFavorite();
    }

    public void deleteFavorite(FavoriteEntity favoriteEntity) {
        // favoriteDao.deleteFavorite(favoriteEntity)
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

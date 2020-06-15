package com.kulloveth.newsfeed.ui.favorite;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kulloveth.newsfeed.local.FavoriteDao;
import com.kulloveth.newsfeed.local.FavoriteDatabase;
import com.kulloveth.newsfeed.local.FavoriteEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FavoriteVieModel extends AndroidViewModel {
    private MutableLiveData<List<FavoriteEntity>> favoriteLiveData;
    FavoriteEntity favoriteEntity;
    private FavoriteDao favoriteDao;
    FavoriteDatabase database;
    CompositeDisposable disposable ;





    public FavoriteVieModel(Application application) {
        super(application);
        database = FavoriteDatabase.getINSTANCE(application);
        favoriteDao = database.favoriteDao();
        favoriteLiveData = new MutableLiveData<>();
        favoriteEntity = new FavoriteEntity();
        disposable = new CompositeDisposable();


    }

    public void insertFavorite(FavoriteEntity favoriteEntity) {
        new InsertAsyncTask(favoriteDao).execute(favoriteEntity);
    }


    public LiveData<List<FavoriteEntity>> fetchFavoriteEntity() {
        return favoriteDao.fetchFavorite();
    }

    public void deleteFavorite(FavoriteEntity favoriteEntity) {
      Completable completable = favoriteDao.deleteFavorite(favoriteEntity).subscribeOn(Schedulers.io());
      disposable.add(completable.subscribe());

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

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}

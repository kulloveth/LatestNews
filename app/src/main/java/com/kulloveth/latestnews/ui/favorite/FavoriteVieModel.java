package com.kulloveth.latestnews.ui.favorite;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kulloveth.latestnews.local.FavoriteDao;
import com.kulloveth.latestnews.local.FavoriteDatabase;
import com.kulloveth.latestnews.local.FavoriteEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FavoriteVieModel extends AndroidViewModel {
    private MutableLiveData<List<FavoriteEntity>> favoriteLiveData;
    FavoriteEntity favoriteEntity;
    private FavoriteDao favoriteDao;
    FavoriteDatabase database;
    CompositeDisposable disposable;


    public FavoriteVieModel(Application application) {
        super(application);
        database = FavoriteDatabase.getINSTANCE(application);
        favoriteDao = database.favoriteDao();
        favoriteLiveData = new MutableLiveData<>();
        favoriteEntity = new FavoriteEntity();
        disposable = new CompositeDisposable();


    }

    public void insertFavorite(FavoriteEntity favoriteEntity) {
        Completable completable = favoriteDao.insertFavorite(favoriteEntity).subscribeOn(Schedulers.io());
        disposable.add(completable.subscribe());
    }


    LiveData<List<FavoriteEntity>> fetchFavoriteEntity() {
        return favoriteDao.fetchFavorite();
    }

    void deleteFavorite(FavoriteEntity favoriteEntity) {
        Completable completable = favoriteDao.deleteFavorite(favoriteEntity).subscribeOn(Schedulers.io());
        disposable.add(completable.subscribe());

    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}

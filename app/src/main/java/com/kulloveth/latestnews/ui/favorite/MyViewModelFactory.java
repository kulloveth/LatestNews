package com.kulloveth.latestnews.ui.favorite;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    public MyViewModelFactory(Application application) {
        mApplication = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        // Replace UserViewModel →  with whatever or however you create your ViewModel
        return (T) new FavoriteVieModel(mApplication);
    }
}

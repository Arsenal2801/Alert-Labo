package com.example.alert.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Presione Durante 3 Segundos Para Solicitar Ayuda");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
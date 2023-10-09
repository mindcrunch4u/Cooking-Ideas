package com.bob.info.ui.cook;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CookViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    public void updateText(String text){
        this.mText.setValue(text);
    }

    public String retrieveText(){
        return this.mText.<String>getValue().toString();
    }
    public CookViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
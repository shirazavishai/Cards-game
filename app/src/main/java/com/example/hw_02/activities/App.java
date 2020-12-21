package com.example.hw_02.activities;

import android.app.Application;

import com.example.hw_02.utils.MySP;
import com.example.hw_02.utils.Signals;

public class App extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        MySP.init(this);
        Signals.init(this);
    }

}
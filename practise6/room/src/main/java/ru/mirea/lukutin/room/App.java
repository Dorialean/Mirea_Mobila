package ru.mirea.lukutin.room;

import android.app.Application;

import androidx.room.Room;

//Из-за того, что в Котлине нет статики пришлось создавать Java файл
public class App extends Application {
    public static App instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
         database = Room.databaseBuilder(this, AppDatabase.class, "MyDatabase")
                 .allowMainThreadQueries()
                 .build();
    }
    public static App getInstance() {
        return instance;
    }
    public AppDatabase getDatabase() {
        return database;
    }
}


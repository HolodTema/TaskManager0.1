package com.terabyte.taskmanager01;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
    private Context context;
    private static DatabaseClient clientInstance;

    private AppDatabase appDatabase;

    private DatabaseClient(Context context) {
        this.context = context;
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, Constant.DATABASE_NAME).build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if(clientInstance == null) {
            clientInstance = new DatabaseClient(context);
        }
        return clientInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}

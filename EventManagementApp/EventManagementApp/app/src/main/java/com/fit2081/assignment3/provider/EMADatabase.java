package com.fit2081.assignment3.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Event.class, EventCategory.class}, version=1)
public abstract class EMADatabase extends RoomDatabase {

    public static final String EMA_DATABASE_NAME = "ema_database";

    public abstract EventCategoryDao eventCategoryDao();
    public abstract EventDao eventDao();

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile EMADatabase INSTANCE;

    static EMADatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EMADatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), EMADatabase.class, EMA_DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }
}


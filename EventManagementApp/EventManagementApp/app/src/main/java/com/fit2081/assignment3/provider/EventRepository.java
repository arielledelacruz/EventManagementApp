package com.fit2081.assignment3.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EventRepository {

    private EventDao mEventDao;
    private LiveData<List<Event>> mAllEvents;

    EventRepository(Application application) {
        EMADatabase db = EMADatabase.getDatabase(application);
        mEventDao = db.eventDao();
        mAllEvents = mEventDao.getAllEvents();
    }

    LiveData<List<Event>> getAllEvents() {
        return mAllEvents;
    }

    void insert(Event event) {
        EMADatabase.databaseWriteExecutor.execute(() -> mEventDao.addEvent(event));
    }

    void deleteEventByName(String name) {
        EMADatabase.databaseWriteExecutor.execute(() -> mEventDao.deleteEventByName(name));
    }

    void deleteAll() {
        EMADatabase.databaseWriteExecutor.execute(mEventDao::deleteAllEvents);
    }
}


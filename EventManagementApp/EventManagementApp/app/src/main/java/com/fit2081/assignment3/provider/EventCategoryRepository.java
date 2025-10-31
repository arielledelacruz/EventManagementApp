package com.fit2081.assignment3.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EventCategoryRepository {

    private EventCategoryDao mEventCategoryDao;

    private LiveData<List<EventCategory>> mAllEventCategories;

    EventCategoryRepository(Application application) {
        EMADatabase db = EMADatabase.getDatabase(application);
        mEventCategoryDao = db.eventCategoryDao();
        mAllEventCategories = mEventCategoryDao.getAllEventCategory();
    }

    LiveData<List<EventCategory>> getAllEventCategories() {
        return mAllEventCategories;
    }

    LiveData<List<EventCategory>> getEventCategory(String name) {
        return mEventCategoryDao.getEventCategory(name);
    }

    void insert(EventCategory eventCategory) {
        EMADatabase.databaseWriteExecutor.execute(() -> mEventCategoryDao.addEventCategory(eventCategory));
    }

    void deleteEventCategory(String name){
        EMADatabase.databaseWriteExecutor.execute(() -> mEventCategoryDao.deleteEventCategory(name));
    }

    void deleteAll(){
        EMADatabase.databaseWriteExecutor.execute(() -> mEventCategoryDao.deleteAllCategories());
    }
}


package com.fit2081.assignment3.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EventCategoryViewModel extends AndroidViewModel {

    private EventCategoryRepository mRepository;
    private LiveData<List<EventCategory>> mAllEventCategories;
    public EventCategoryViewModel(@NonNull Application application) {
        super(application);
        mRepository = new EventCategoryRepository(application);
        mAllEventCategories = mRepository.getAllEventCategories();
    }

    public LiveData<List<EventCategory>> getAllEventCategories() {
        return mAllEventCategories;
    }

    public void insert(EventCategory eventCategory) {
        mRepository.insert(eventCategory);
    }

    public LiveData<List<EventCategory>> getEventCategory(String name) {
        return mRepository.getEventCategory(name);
    }

    public void deleteEventCategory(String name) {
        mRepository.deleteEventCategory(name);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}


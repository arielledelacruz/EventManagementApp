package com.fit2081.assignment3.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventCategoryDao {

    @Query("SELECT * FROM eventCategories")
    LiveData<List<EventCategory>> getAllEventCategory();

    @Query("SELECT * FROM eventCategories WHERE categoryName = :name")
    LiveData<List<EventCategory>> getEventCategory(String name);

    @Insert
    void addEventCategory(EventCategory eventCategory);

    @Query("DELETE FROM eventCategories WHERE categoryName = :name")
    void deleteEventCategory(String name);

    @Query("DELETE FROM eventCategories")
    void deleteAllCategories();
}



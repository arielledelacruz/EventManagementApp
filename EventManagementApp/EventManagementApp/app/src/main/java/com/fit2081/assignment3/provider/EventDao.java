package com.fit2081.assignment3.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventDao {

    @Query("SELECT * FROM events")
    LiveData<List<Event>> getAllEvents();

    @Query("SELECT * FROM events WHERE eventName = :name")
    List<Event> getEventByName(String name);

    @Insert
    void addEvent(Event event);

    @Query("DELETE FROM events WHERE eventName = :name")
    void deleteEventByName(String name);

    @Query("DELETE FROM events")
    void deleteAllEvents();
}


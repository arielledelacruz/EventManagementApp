package com.fit2081.assignment3.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "events")
public class Event {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "eventIdKey")
    @NonNull
    private int eventIdKey;

    @ColumnInfo(name = "eventId")
    private String eventId;
    @ColumnInfo(name = "eventName")
    private String eventName;
    @ColumnInfo(name = "categoryId")
    private String categoryId;
    @ColumnInfo(name = "ticketsAvailable")
    private int ticketsAvailable;
    @ColumnInfo(name = "isActive")
    private boolean isActive;

    public Event(String eventId, String eventName, String categoryId, int ticketsAvailable, boolean isActive) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.categoryId = categoryId;
        this.ticketsAvailable = ticketsAvailable;
        this.isActive = isActive;
    }

    public int getEventIdKey() {
        return eventIdKey;
    }

    public void setEventIdKey(@NonNull int eventIdKey) {
        this.eventIdKey = eventIdKey;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}


package com.fit2081.assignment3.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "eventCategories")
public class EventCategory {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "categoryIdKey")
    @NonNull
    private int categoryIdKey;

    @ColumnInfo(name = "categoryId")
    private String categoryId;

    @ColumnInfo(name = "categoryName")
    private String name;

    @ColumnInfo(name = "categoryEventCount")
    private int eventCount;

    @ColumnInfo(name = "categoryIsActive")
    private boolean isActive;

    @ColumnInfo(name = "categoryEventLocation")
    private String eventLocation;

    public EventCategory(String categoryId, String name, int eventCount, boolean isActive, String eventLocation) {
        this.categoryId = categoryId;
        this.name = name;
        this.eventCount = eventCount;
        this.isActive = isActive;
        this.eventLocation = eventLocation;
    }

    public int getCategoryIdKey() {
        return categoryIdKey;
    }

    public void setCategoryIdKey(@NonNull int categoryIdKey) {
        this.categoryIdKey = categoryIdKey;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEventCount() {
        return eventCount;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }
}



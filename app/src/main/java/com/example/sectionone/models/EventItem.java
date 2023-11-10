package com.example.sectionone.models;

import java.io.Serializable;
import java.util.List;

public class EventItem implements Serializable {
    String title;
    String description;
    int viewCounts;
    boolean isRead;
    List<String> images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventItem(String title, String description, int viewCounts, boolean isRead, List<String> images) {
        this.title = title;
        this.description = description;
        this.viewCounts = viewCounts;
        this.isRead = isRead;
        this.images = images;
    }

    public int getViewCounts() {
        return viewCounts;
    }

    public void setViewCounts(int viewCounts) {
        this.viewCounts = viewCounts;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}

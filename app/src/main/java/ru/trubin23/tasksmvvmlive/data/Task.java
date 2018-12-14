package ru.trubin23.tasksmvvmlive.data;

public class Task {

    private final String mId;

    private final String mTitle;

    private final String mDescription;

    private final boolean mCompleted;

    public Task(String id, String title, String description, boolean completed) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mCompleted = completed;
    }

    public String getTaskId() {
        return null;
    }

    public String getTitle() {
        return null;
    }

    public String getDescription() {
        return null;
    }

    public boolean isCompleted() {
        return false;
    }
}

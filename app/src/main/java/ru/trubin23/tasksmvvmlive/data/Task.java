package ru.trubin23.tasksmvvmlive.data;

public class Task {

    private final String mId;

    private final String mTitle;

    private final String mDescription;

    private final String mCompleted;

    public Task(String id, String title, String description, String completed) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mCompleted = completed;
    }
}

package ru.trubin23.tasksmvvmlive.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import java.util.UUID;

@Entity(tableName = "tasks")
public final class Task {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "task_id")
    private final String mId;

    @NonNull
    @ColumnInfo(name = "title")
    private final String mTitle;

    @NonNull
    @ColumnInfo(name = "description")
    private final String mDescription;

    @ColumnInfo(name = "completed")
    private final boolean mCompleted;

    public Task(@NonNull String title, @NonNull String description,
                @NonNull String id, boolean completed) {
        mTitle = title;
        mDescription = description;
        mId = id;
        mCompleted = completed;
    }

    @Ignore
    public Task(@NonNull String title, @NonNull String description, @NonNull String id) {
        this(title, description, id, false);
    }

    @Ignore
    public Task(@NonNull String title, @NonNull String description) {
        this(title, description, UUID.randomUUID().toString(), false);
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public String getDescription() {
        return mDescription;
    }

    public String getTitleForList() {
        if (!Strings.isNullOrEmpty(mTitle)) {
            return mTitle;
        } else {
            return mDescription;
        }
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public boolean isActive() {
        return !mCompleted;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(mTitle) && Strings.isNullOrEmpty(mDescription);
    }
}
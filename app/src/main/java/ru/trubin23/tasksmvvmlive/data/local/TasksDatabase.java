package ru.trubin23.tasksmvvmlive.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import ru.trubin23.tasksmvvmlive.data.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class TasksDatabase extends RoomDatabase {

    private static TasksDatabase INSTANCE;

    public abstract TasksDao tasksDao();

    private static final Object sLock = new Object();

    public static TasksDatabase getInstance(@NonNull Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        TasksDatabase.class, "Tasks.db").build();
            }
            return INSTANCE;
        }
    }
}
package ru.trubin23.tasksmvvmlive.data;

import android.support.annotation.NonNull;

import java.util.List;

public interface TasksDataSource {

    interface LoadTasksCallback {

        void onTasksLoaded(@NonNull List<Task> tasks);

        void onDataNotAvailable();
    }

    interface GetTaskCallback {

        void onTaskLoaded(@NonNull Task task);

        void onDataNotAvailable();
    }

    void getTasks(@NonNull LoadTasksCallback callback);

    void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback);

    void saveTask(@NonNull Task task);

    void updateTask(@NonNull Task task);

    void deleteTask(@NonNull String taskId);

    void completedTask(@NonNull String taskId, boolean completed);

    void clearCompletedTask();
}
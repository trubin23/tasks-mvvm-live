package ru.trubin23.tasksmvvmlive.tasks;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import ru.trubin23.tasksmvvmlive.data.source.TasksRepository;

public class TasksViewModel extends AndroidViewModel {

    private final Context mContext;

    private final TasksRepository mTasksRepository;

    public TasksViewModel(@NonNull Application context,
                          @NonNull TasksRepository repository) {
        super(context);
        mContext = context.getApplicationContext();
        mTasksRepository = repository;
    }

    void handleActivityResult(int requestCode, int resultCode) {

    }
}

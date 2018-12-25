package ru.trubin23.tasksmvvmlive.tasks;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

public class TasksViewModel extends AndroidViewModel {

    public TasksViewModel(@NonNull Application application) {
        super(application);
    }

    void handleActivityResult(int requestCode, int resultCode) {

    }
}

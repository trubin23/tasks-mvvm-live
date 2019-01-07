package ru.trubin23.tasksmvvmlive.addedittask;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import ru.trubin23.tasksmvvmlive.data.source.TasksRepository;

public class AddEditTaskViewModel extends AndroidViewModel {

    private final TasksRepository mTasksRepository;

    public AddEditTaskViewModel(@NonNull Application application,
                                @NonNull TasksRepository repository) {
        super(application);
        mTasksRepository = repository;
    }
}

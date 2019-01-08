package ru.trubin23.tasksmvvmlive.statistics;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import ru.trubin23.tasksmvvmlive.data.source.TasksRepository;

public class StatisticsViewModel extends AndroidViewModel {

    private final TasksRepository mTasksRepository;

    public StatisticsViewModel(@NonNull Application application,
                               @NonNull TasksRepository repository) {
        super(application);
        mTasksRepository = repository;
    }
}

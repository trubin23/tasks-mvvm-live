package ru.trubin23.tasksmvvmlive.statistics;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import ru.trubin23.tasksmvvmlive.data.source.TasksRepository;

public class StatisticsViewModel extends AndroidViewModel {

    private final Context mContext;

    private final TasksRepository mTasksRepository;

    public final ObservableBoolean mDataLoading = new ObservableBoolean(false);

    public final ObservableBoolean mEmpty = new ObservableBoolean(false);

    public final ObservableField<String> mNumberOfActiveTasks = new ObservableField<>();

    public final ObservableField<String> mNumberOfCompletedTasks = new ObservableField<>();

    private int mNumberOfActiveTasksInt = 0;

    private int mNumberOfCompletedTasksInt = 0;

    public StatisticsViewModel(@NonNull Application application,
                               @NonNull TasksRepository repository) {
        super(application);
        mContext = application.getApplicationContext();
        mTasksRepository = repository;
    }
}

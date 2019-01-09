package ru.trubin23.tasksmvvmlive.statistics;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import java.util.List;

import ru.trubin23.tasksmvvmlive.R;
import ru.trubin23.tasksmvvmlive.data.Task;
import ru.trubin23.tasksmvvmlive.data.source.TasksDataSource;
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

    void loadStatistics() {
        mDataLoading.set(true);

        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                int active = 0;
                int completed = 0;

                for (Task task : tasks){
                    if (task.isActive()){
                        active++;
                    } else {
                        completed++;
                    }
                }
                mNumberOfActiveTasksInt = active;
                mNumberOfCompletedTasksInt = completed;

                updateDataBindingObservables();
            }

            @Override
            public void onDataNotAvailable() {
                mNumberOfActiveTasksInt = 0;
                mNumberOfCompletedTasksInt = 0;
                updateDataBindingObservables();
            }
        });
    }

    private void updateDataBindingObservables(){
        mNumberOfActiveTasks.set(mContext.getString(
                R.string.statistics_active_tasks, mNumberOfActiveTasksInt));
        mNumberOfCompletedTasks.set(mContext.getString(
                R.string.statistics_completed_tasks, mNumberOfCompletedTasksInt));
        mEmpty.set(mNumberOfActiveTasksInt + mNumberOfCompletedTasksInt == 0);
        mDataLoading.set(false);
    }
}

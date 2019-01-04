package ru.trubin23.tasksmvvmlive.taskdetail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import ru.trubin23.tasksmvvmlive.SingleLiveEvent;
import ru.trubin23.tasksmvvmlive.SnackbarMessage;
import ru.trubin23.tasksmvvmlive.data.Task;
import ru.trubin23.tasksmvvmlive.data.source.TasksDataSource;
import ru.trubin23.tasksmvvmlive.data.source.TasksRepository;

public class TaskDetailViewModel extends AndroidViewModel {

    private final TasksRepository mTasksRepository;

    public final ObservableField<Task> mTask = new ObservableField<>();

    public final ObservableBoolean mCompleted = new ObservableBoolean();

    private final SingleLiveEvent<Void> mEditTaskCommand = new SingleLiveEvent<>();

    private final SingleLiveEvent<Void> mDeleteTaskCommand = new SingleLiveEvent<>();

    private final SnackbarMessage mSnackbarText = new SnackbarMessage();

    private boolean mIsDataLoading;

    public TaskDetailViewModel(@NonNull Application application,
                               @NonNull TasksRepository repository) {
        super(application);
        mTasksRepository = repository;
    }

    public void start(String taskId) {
        if (taskId != null) {
            mIsDataLoading = true;
            mTasksRepository.getTask(taskId, new TasksDataSource.GetTaskCallback() {

                @Override
                public void onTaskLoaded(@NonNull Task task) {
                    mTask.set(task);
                    mCompleted.set(task.isCompleted());
                    mIsDataLoading = false;
                }

                @Override
                public void onDataNotAvailable() {
                    mTask.set(null);
                    mIsDataLoading = false;
                }
            });
        }
    }
}

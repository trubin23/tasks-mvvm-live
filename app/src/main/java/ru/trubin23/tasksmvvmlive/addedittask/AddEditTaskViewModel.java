package ru.trubin23.tasksmvvmlive.addedittask;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.trubin23.tasksmvvmlive.R;
import ru.trubin23.tasksmvvmlive.SingleLiveEvent;
import ru.trubin23.tasksmvvmlive.SnackbarMessage;
import ru.trubin23.tasksmvvmlive.data.Task;
import ru.trubin23.tasksmvvmlive.data.source.TasksDataSource;
import ru.trubin23.tasksmvvmlive.data.source.TasksRepository;

public class AddEditTaskViewModel extends AndroidViewModel {

    private final TasksRepository mTasksRepository;

    public final ObservableField<String> mTitle = new ObservableField<>();

    public final ObservableField<String> mDescription = new ObservableField<>();

    public final ObservableBoolean mDataLoading = new ObservableBoolean(false);

    private final SnackbarMessage mSnackbarMessage = new SnackbarMessage();

    private final SingleLiveEvent<Void> mTaskUpdate = new SingleLiveEvent<>();

    @Nullable
    private String mTaskId;

    private boolean mIsNewTask;

    private boolean mIsDataLoaded = false;

    private boolean mTaskCompleted = false;

    public AddEditTaskViewModel(@NonNull Application application,
                                @NonNull TasksRepository repository) {
        super(application);
        mTasksRepository = repository;
    }

    public void start(String taskId) {
        if (mDataLoading.get()) {
            return;
        }

        mTaskId = taskId;
        if (mTaskId == null) {
            mIsNewTask = true;
            return;
        }

        if (mIsDataLoaded) {
            return;
        }

        mIsNewTask = false;
        mDataLoading.set(true);

        mTasksRepository.getTask(taskId, new TasksDataSource.GetTaskCallback() {
            @Override
            public void onTaskLoaded(@NonNull Task task) {
                mTitle.set(task.getTitle());
                mDescription.set(task.getDescription());
                mTaskCompleted = task.isCompleted();
                mDataLoading.set(false);
                mIsDataLoaded = true;
            }

            @Override
            public void onDataNotAvailable() {
                mDataLoading.set(false);
            }
        });
    }

    SnackbarMessage getSnackbarMessage() {
        return mSnackbarMessage;
    }

    public SingleLiveEvent<Void> getTaskUpdate() {
        return mTaskUpdate;
    }

    void saveTask() {
        Task task = new Task(mTitle.get(), mDescription.get());
        if (task.isEmpty()) {
            mSnackbarMessage.setValue(R.string.empty_task_message);
            return;
        }
        if (mIsNewTask || mTaskId == null) {
            createTask(task);
        } else {
            task = new Task(mTitle.get(), mDescription.get(), mTaskId, mTaskCompleted);
            updateTask(task);
        }
    }

    private void createTask(Task newTask) {
        mTasksRepository.saveTask(newTask);
        mTaskUpdate.call();
    }

    private void updateTask(Task task) {
        if (mIsNewTask) {
            throw new RuntimeException("updateTask(Task task) was called but task is new");
        }
        mTasksRepository.saveTask(task);
        mTaskUpdate.call();
    }
}

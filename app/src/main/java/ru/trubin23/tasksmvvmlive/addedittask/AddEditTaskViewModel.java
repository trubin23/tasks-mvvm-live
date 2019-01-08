package ru.trubin23.tasksmvvmlive.addedittask;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.trubin23.tasksmvvmlive.SingleLiveEvent;
import ru.trubin23.tasksmvvmlive.SnackbarMessage;
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

    }
}

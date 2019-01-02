package ru.trubin23.tasksmvvmlive.tasks;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import ru.trubin23.tasksmvvmlive.SingleLiveEvent;
import ru.trubin23.tasksmvvmlive.data.Task;
import ru.trubin23.tasksmvvmlive.data.source.TasksRepository;

public class TasksViewModel extends AndroidViewModel {

    private final Context mContext;

    public final ObservableList<Task> mItems = new ObservableArrayList<>();

    public final ObservableBoolean mDataLoading = new ObservableBoolean(false);

    public final ObservableField<String> mCurrentFilteringLabel = new ObservableField<>();

    public final ObservableField<String> mNoTasksLabel = new ObservableField<>();

    public final ObservableField<Drawable> mNoTaskIconRes = new ObservableField<>();

    public final ObservableBoolean mEmpty = new ObservableBoolean(false);

    public final ObservableBoolean mTasksAddViewVisible = new ObservableBoolean();

    private SingleLiveEvent<String> mOpenTaskEvent = new SingleLiveEvent<>();

    private SingleLiveEvent<Void> mNewTaskEvent = new SingleLiveEvent<>();

    private final TasksRepository mTasksRepository;

    public TasksViewModel(@NonNull Application context,
                          @NonNull TasksRepository repository) {
        super(context);
        mContext = context.getApplicationContext();
        mTasksRepository = repository;
    }

    void handleActivityResult(int requestCode, int resultCode) {

    }

    SingleLiveEvent<String> getOpenTaskEvent() {
        return mOpenTaskEvent;
    }

    SingleLiveEvent<Void> getNewTaskEvent() {
        return mNewTaskEvent;
    }

    public void loadTasks(boolean forceUpdate) {

    }

    public void addNewTask(){
        mNewTaskEvent.call();
    }
}

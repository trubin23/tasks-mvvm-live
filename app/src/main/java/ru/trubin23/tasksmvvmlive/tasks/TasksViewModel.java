package ru.trubin23.tasksmvvmlive.tasks;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import ru.trubin23.tasksmvvmlive.R;
import ru.trubin23.tasksmvvmlive.SingleLiveEvent;
import ru.trubin23.tasksmvvmlive.SnackbarMessage;
import ru.trubin23.tasksmvvmlive.data.Task;
import ru.trubin23.tasksmvvmlive.data.source.TasksRepository;

public class TasksViewModel extends AndroidViewModel {

    private final Context mContext;

    private final TasksRepository mTasksRepository;

    public final ObservableList<Task> mItems = new ObservableArrayList<>();

    public final ObservableBoolean mDataLoading = new ObservableBoolean(false);

    public final ObservableField<String> mCurrentFilteringLabel = new ObservableField<>();

    public final ObservableField<String> mNoTasksLabel = new ObservableField<>();

    public final ObservableField<Drawable> mNoTaskIconRes = new ObservableField<>();

    public final ObservableBoolean mEmpty = new ObservableBoolean(false);

    public final ObservableBoolean mTasksAddViewVisible = new ObservableBoolean();

    private final SnackbarMessage mSnackbarText = new SnackbarMessage();

    private TasksFilterType mCurrentFiltering = null;

    private final ObservableBoolean mIsDataLoadingError = new ObservableBoolean();

    private SingleLiveEvent<String> mOpenTaskEvent = new SingleLiveEvent<>();

    private SingleLiveEvent<Void> mNewTaskEvent = new SingleLiveEvent<>();

    public TasksViewModel(@NonNull Application context,
                          @NonNull TasksRepository repository) {
        super(context);
        mContext = context.getApplicationContext();
        mTasksRepository = repository;

        setFiltering(TasksFilterType.ALL_TASKS);
    }

    void setFiltering(TasksFilterType requestType) {
        mCurrentFiltering = requestType;

        switch (mCurrentFiltering) {
            case ALL_TASKS:
                updateFilterType(R.string.label_all, R.string.no_tasks_all,
                        R.drawable.ic_assignment, true);
                break;

            case ACTIVE_TASKS:
                updateFilterType(R.string.label_active, R.string.no_tasks_active,
                        R.drawable.ic_check_circle, false);
                break;

            case COMPLETED_TASKS:
                updateFilterType(R.string.label_completed, R.string.no_tasks_completed,
                        R.drawable.ic_verified_user, false);
                break;
        }
    }

    private void updateFilterType(
            @StringRes int filteringLabelStringId, @StringRes int noTasksLabelStringId,
            @DrawableRes int noTaskIconDrawableId, boolean tasksAddViewVisible) {

        mCurrentFilteringLabel.set(mContext.getString(filteringLabelStringId));
        mNoTasksLabel.set(mContext.getString(noTasksLabelStringId));
        mNoTaskIconRes.set(mContext.getResources().getDrawable(noTaskIconDrawableId));
        mTasksAddViewVisible.set(tasksAddViewVisible);
    }

    public void loadTasks(boolean forceUpdate) {

    }

    void handleActivityResult(int requestCode, int resultCode) {

    }

    SnackbarMessage getSnackbarMessage() {
        return mSnackbarText;
    }

    SingleLiveEvent<String> getOpenTaskEvent() {
        return mOpenTaskEvent;
    }

    SingleLiveEvent<Void> getNewTaskEvent() {
        return mNewTaskEvent;
    }

    public void addNewTask() {
        mNewTaskEvent.call();
    }

    void clearCompletedTasks() {

    }
}

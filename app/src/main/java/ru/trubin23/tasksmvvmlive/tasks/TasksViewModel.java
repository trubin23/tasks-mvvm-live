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

import java.util.ArrayList;
import java.util.List;

import ru.trubin23.tasksmvvmlive.R;
import ru.trubin23.tasksmvvmlive.SingleLiveEvent;
import ru.trubin23.tasksmvvmlive.SnackbarMessage;
import ru.trubin23.tasksmvvmlive.addedittask.AddEditTaskActivity;
import ru.trubin23.tasksmvvmlive.data.Task;
import ru.trubin23.tasksmvvmlive.data.source.TasksDataSource;
import ru.trubin23.tasksmvvmlive.data.source.TasksRepository;
import ru.trubin23.tasksmvvmlive.taskdetail.TaskDetailActivity;

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
        loadTasks(forceUpdate, true);
    }

    private void loadTasks(boolean forceUpdate, boolean showLoadingUI) {
        if (showLoadingUI) {
            mDataLoading.set(true);
        }
        if (forceUpdate) {
            mTasksRepository.refreshTasks();
        }

        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                List<Task> tasksForShow = new ArrayList<>();

                for (Task task : tasks) {
                    switch (mCurrentFiltering) {
                        case ACTIVE_TASKS:
                            if (task.isActive()) {
                                tasksForShow.add(task);
                            }
                            break;
                        case COMPLETED_TASKS:
                            if (task.isCompleted()) {
                                tasksForShow.add(task);
                            }
                            break;
                        case ALL_TASKS:
                            tasksForShow.add(task);
                            break;
                        default:
                            tasksForShow.add(task);
                            break;
                    }
                }

                if (showLoadingUI) {
                    mDataLoading.set(false);
                }

                mItems.clear();
                mItems.addAll(tasksForShow);
                mEmpty.set(mItems.isEmpty());
            }

            @Override
            public void onDataNotAvailable() {
                if (showLoadingUI) {
                    mDataLoading.set(false);
                }
            }
        });
    }

    void handleActivityResult(int requestCode, int resultCode) {
        if (TasksActivity.REQUEST_CODE_TASK_DETAIL == requestCode) {
            if (TaskDetailActivity.DELETE_RESULT_OK == resultCode) {
                mSnackbarText.setValue(R.string.deleted_task_message);
            }
            if (TaskDetailActivity.EDIT_RESULT_OK == resultCode) {
                mSnackbarText.setValue(R.string.saved_task_message);
            }
        }

        if (TasksActivity.REQUEST_CODE_ADD_EDIT_TASK == requestCode) {
            if (AddEditTaskActivity.ADD_EDIT_RESULT_OK == resultCode) {
                mSnackbarText.setValue(R.string.added_task_message);
            }
        }
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
        mTasksRepository.clearCompletedTask();
        mSnackbarText.setValue(R.string.completed_tasks_cleared);
        loadTasks(false, false);
    }

    void completeTask(Task task, boolean completed) {
        mTasksRepository.completedTask(task.getId(), completed);
        if (completed) {
            mSnackbarText.setValue(R.string.task_marked_complete);
        } else {
            mSnackbarText.setValue(R.string.task_marked_active);
        }
    }
}

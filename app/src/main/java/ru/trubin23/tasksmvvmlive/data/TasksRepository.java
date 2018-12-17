package ru.trubin23.tasksmvvmlive.data;

import android.support.annotation.NonNull;

import java.util.List;

import ru.trubin23.tasksmvvmlive.data.cache.TasksCacheDataSource;
import ru.trubin23.tasksmvvmlive.data.local.TasksLocalDataSource;


public class TasksRepository implements TasksMainDataSource {

    private static TasksRepository INSTANCE;

    private final TasksDataSource mTasksRemoteDataSource;
    private final TasksLocalDataSource mTasksLocalDataSource;
    private final TasksCacheDataSource mTasksCacheDataSource;

    private boolean mForceRefresh = false;

    private TasksRepository(@NonNull TasksDataSource tasksRemoteDataSource,
                            @NonNull TasksLocalDataSource tasksLocalDataSource,
                            @NonNull TasksCacheDataSource tasksCacheDataSource) {
        mTasksRemoteDataSource = tasksRemoteDataSource;
        mTasksLocalDataSource = tasksLocalDataSource;
        mTasksCacheDataSource = tasksCacheDataSource;
    }

    public static TasksRepository getInstance(@NonNull TasksDataSource tasksRemoteDataSource,
                                              @NonNull TasksLocalDataSource tasksLocalDataSource,
                                              @NonNull TasksCacheDataSource tasksCacheDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TasksRepository(tasksRemoteDataSource,
                    tasksLocalDataSource, tasksCacheDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getTasks(@NonNull LoadTasksCallback callback) {
        List<Task> tasks = mTasksCacheDataSource.getTasks();
        if (tasks != null) {
            callback.onTasksLoaded(tasks);
            return;
        }

        if (mForceRefresh) {
            getTasksFromRemoteDataSource(callback, true);
        } else {
            getTasksFromLocalDataSource(callback, true);
        }
    }

    private void getTasksFromLocalDataSource(@NonNull final LoadTasksCallback callback,
                                             final boolean handleErrors) {
        mTasksLocalDataSource.getTasks(new LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                mTasksCacheDataSource.setTasks(tasks);
                callback.onTasksLoaded(tasks);
            }

            @Override
            public void onDataNotAvailable() {
                if (handleErrors) {
                    getTasksFromRemoteDataSource(callback, false);
                } else {
                    callback.onDataNotAvailable();
                }
            }
        });
    }

    private void getTasksFromRemoteDataSource(@NonNull final LoadTasksCallback callback,
                                              final boolean handleErrors) {
        mTasksRemoteDataSource.getTasks(new LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                mTasksCacheDataSource.setTasks(tasks);
                mTasksLocalDataSource.setTasks(tasks);
                mForceRefresh = false;
                callback.onTasksLoaded(tasks);
            }

            @Override
            public void onDataNotAvailable() {
                if (handleErrors) {
                    getTasksFromLocalDataSource(callback, false);
                } else {
                    callback.onDataNotAvailable();
                }
            }
        });
    }

    @Override
    public void getTask(@NonNull final String taskId,
                        @NonNull final GetTaskCallback callback) {
        Task task = mTasksCacheDataSource.getTaskById(taskId);
        if (task != null){
            callback.onTaskLoaded(task);
            return;
        }

        mTasksLocalDataSource.getTask(taskId, new GetTaskCallback() {
            @Override
            public void onTaskLoaded(@NonNull Task task) {
                mTasksCacheDataSource.addTask(task);
                callback.onTaskLoaded(task);
            }

            @Override
            public void onDataNotAvailable() {
                getOneTaskFromRemoteDataSource(taskId, callback);
            }
        });
    }

    private void getOneTaskFromRemoteDataSource(@NonNull String taskId,
                                                @NonNull final GetTaskCallback callback) {
        mTasksRemoteDataSource.getTask(taskId, new GetTaskCallback() {
            @Override
            public void onTaskLoaded(@NonNull Task task) {
                mTasksCacheDataSource.addTask(task);
                mTasksLocalDataSource.saveTask(task);
                callback.onTaskLoaded(task);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveTask(@NonNull Task task) {
        mTasksRemoteDataSource.saveTask(task);
        mTasksLocalDataSource.saveTask(task);
        mTasksCacheDataSource.addTask(task);
    }

    @Override
    public void updateTask(@NonNull Task task) {
        mTasksRemoteDataSource.updateTask(task);
        mTasksLocalDataSource.updateTask(task);
        mTasksCacheDataSource.addTask(task);
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        mTasksRemoteDataSource.deleteTask(taskId);
        mTasksLocalDataSource.deleteTask(taskId);
        mTasksCacheDataSource.removeTask(taskId);
    }

    @Override
    public void completedTask(@NonNull String taskId, boolean completed) {
        mTasksRemoteDataSource.completedTask(taskId, completed);
        mTasksLocalDataSource.completedTask(taskId, completed);
        mTasksCacheDataSource.completedTask(taskId, completed);
    }

    @Override
    public void clearCompletedTask() {
        mTasksRemoteDataSource.clearCompletedTask();
        mTasksLocalDataSource.clearCompletedTask();
        mTasksCacheDataSource.clearCompletedTask();
    }

    @Override
    public void refreshTasks() {
        mTasksCacheDataSource.irrelevantState();
        mForceRefresh = true;
    }
}

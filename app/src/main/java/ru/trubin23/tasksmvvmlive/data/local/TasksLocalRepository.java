package ru.trubin23.tasksmvvmlive.data.local;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ru.trubin23.tasksmvvmlive.data.Task;

public class TasksLocalRepository implements TasksLocalDataSource {

    private static TasksLocalRepository INSTANCE;

    private TasksDao mTasksDao;
    private final Executor mDiskIO;

    private TasksLocalRepository(@NonNull TasksDao tasksDao,
                                 @NonNull Executor diskIO) {
        mTasksDao = tasksDao;
        mDiskIO = diskIO;
    }

    @NonNull
    public static TasksLocalRepository getInstance(@NonNull TasksDao tasksDao) {
        if (INSTANCE == null) {
            synchronized (TasksLocalRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TasksLocalRepository(tasksDao,
                            Executors.newSingleThreadExecutor());
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getTasks(@NonNull LoadTasksCallback callback) {
        mDiskIO.execute(() -> {
            List<Task> tasks = mTasksDao.getTasks();
            if (tasks.isEmpty()) {
                callback.onDataNotAvailable();
            } else {
                callback.onTasksLoaded(tasks);
            }
        });
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback) {
        mDiskIO.execute(() -> {
            Task task = mTasksDao.getTaskById(taskId);
            if (task == null) {
                callback.onDataNotAvailable();
            } else {
                callback.onTaskLoaded(task);
            }
        });
    }

    @Override
    public void saveTask(@NonNull Task task) {
        mDiskIO.execute(() -> mTasksDao.insertTask(task));
    }

    @Override
    public void updateTask(@NonNull Task task) {
        mDiskIO.execute(() -> mTasksDao.updateTask(task));
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        mDiskIO.execute(() -> mTasksDao.deleteTaskById(taskId));
    }

    @Override
    public void completedTask(@NonNull String taskId, boolean completed) {
        mDiskIO.execute(() -> mTasksDao.updateCompleted(taskId, completed));
    }

    @Override
    public void clearCompletedTask() {
        mDiskIO.execute(mTasksDao::deleteCompletedTasks);
    }

    @Override
    public void setTasks(@NonNull List<Task> tasks) {
        mDiskIO.execute(() -> {
            mTasksDao.deleteTasks();
            mTasksDao.insertTasks(tasks);
        });
    }
}
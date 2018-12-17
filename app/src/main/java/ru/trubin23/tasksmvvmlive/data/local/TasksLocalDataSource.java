package ru.trubin23.tasksmvvmlive.data.local;

import android.support.annotation.NonNull;

import java.util.List;

import ru.trubin23.tasksmvvmlive.data.Task;
import ru.trubin23.tasksmvvmlive.data.TasksDataSource;

public interface TasksLocalDataSource extends TasksDataSource {

    void setTasks(@NonNull List<Task> tasks);
}

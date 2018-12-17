package ru.trubin23.tasksmvvmlive.data.source.local;

import android.support.annotation.NonNull;

import java.util.List;

import ru.trubin23.tasksmvvmlive.data.Task;
import ru.trubin23.tasksmvvmlive.data.source.TasksDataSource;

public interface TasksLocalDataSource extends TasksDataSource {

    void setTasks(@NonNull List<Task> tasks);
}

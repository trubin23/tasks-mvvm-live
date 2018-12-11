package ru.trubin23.tasksmvvmlive.tasks;

import android.view.View;

import ru.trubin23.tasksmvvmlive.data.Task;

public interface TaskItemUserActionsListener {

    void onCompleteChanged(Task task, View view);

    void onTaskClicked(Task task);
}

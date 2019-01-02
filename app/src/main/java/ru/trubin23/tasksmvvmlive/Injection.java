package ru.trubin23.tasksmvvmlive;

import android.content.Context;
import android.support.annotation.NonNull;

import ru.trubin23.tasksmvvmlive.data.source.TasksRepository;
import ru.trubin23.tasksmvvmlive.data.source.cache.TasksCacheRepository;
import ru.trubin23.tasksmvvmlive.data.source.local.TasksDatabase;
import ru.trubin23.tasksmvvmlive.data.source.local.TasksLocalRepository;
import ru.trubin23.tasksmvvmlive.data.source.remote.TasksRemoteRepository;

class Injection {

    static TasksRepository provideTasksRepository(@NonNull Context context) {
        TasksDatabase tasksDatabase = TasksDatabase.getInstance(context);

        return TasksRepository.getInstance(TasksRemoteRepository.getInstance(),
                TasksLocalRepository.getInstance(tasksDatabase.tasksDao()),
                TasksCacheRepository.getInstance());
    }
}

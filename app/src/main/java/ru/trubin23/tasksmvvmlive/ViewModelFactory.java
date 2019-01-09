package ru.trubin23.tasksmvvmlive;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import ru.trubin23.tasksmvvmlive.addedittask.AddEditTaskViewModel;
import ru.trubin23.tasksmvvmlive.data.source.TasksRepository;
import ru.trubin23.tasksmvvmlive.statistics.StatisticsViewModel;
import ru.trubin23.tasksmvvmlive.taskdetail.TaskDetailViewModel;
import ru.trubin23.tasksmvvmlive.tasks.TasksViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ViewModelFactory INSTANCE;

    private final Application mApplication;

    private final TasksRepository mTasksRepository;

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application,
                            Injection.provideTasksRepository(application.getApplicationContext()));
                }
            }
        }
        return INSTANCE;
    }

    private ViewModelFactory(Application application, TasksRepository repository) {
        mApplication = application;
        mTasksRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TasksViewModel.class)) {
            //noinspection unchecked
            return (T) new TasksViewModel(mApplication, mTasksRepository);
        }
        if (modelClass.isAssignableFrom(TaskDetailViewModel.class)) {
            //noinspection unchecked
            return (T) new TaskDetailViewModel(mApplication, mTasksRepository);
        }
        if (modelClass.isAssignableFrom(AddEditTaskViewModel.class)) {
            //noinspection unchecked
            return (T) new AddEditTaskViewModel(mApplication, mTasksRepository);
        }
        if (modelClass.isAssignableFrom(StatisticsViewModel.class)) {
            //noinspection unchecked
            return (T) new StatisticsViewModel(mApplication, mTasksRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}

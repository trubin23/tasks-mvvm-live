package ru.trubin23.tasksmvvmlive;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import ru.trubin23.tasksmvvmlive.data.source.TasksRepository;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ViewModelFactory INSTANCE;

    private final Application mApplication;

    private final TasksRepository mTasksRepository;

    public static ViewModelFactory getInstance(Application application){
        if (INSTANCE == null){
            synchronized (ViewModelFactory.class){
                if (INSTANCE == null){
                    INSTANCE = new ViewModelFactory(application,
                            null);
                }
            }
        }
        return INSTANCE;
    }

    private ViewModelFactory(Application application, TasksRepository repository){
        mApplication = application;
        mTasksRepository = repository;
    }
}

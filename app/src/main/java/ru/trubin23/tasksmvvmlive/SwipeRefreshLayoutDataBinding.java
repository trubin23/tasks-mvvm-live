package ru.trubin23.tasksmvvmlive;

import android.databinding.BindingAdapter;

import ru.trubin23.tasksmvvmlive.tasks.TasksViewModel;

public class SwipeRefreshLayoutDataBinding {

    @BindingAdapter("android:onRefresh")
    public static void setSwipeRefreshLayoutOnRefreshListener(
            ScrollChildSwipeRefreshLayout view,
            final TasksViewModel viewModel){
        view.setOnRefreshListener(() -> viewModel.loadTasks(true));
    }
}

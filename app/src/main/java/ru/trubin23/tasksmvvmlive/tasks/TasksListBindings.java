package ru.trubin23.tasksmvvmlive.tasks;

import android.databinding.BindingAdapter;
import android.widget.ListView;

import java.util.List;

import ru.trubin23.tasksmvvmlive.data.Task;

public class TasksListBindings {

    @BindingAdapter("app:items")
    public static void setItems(ListView listView, List<Task> items){
        TasksAdapter adapter = (TasksAdapter) listView.getAdapter();
        if (adapter != null){
            adapter.replaceData(items);
        }
    }
}

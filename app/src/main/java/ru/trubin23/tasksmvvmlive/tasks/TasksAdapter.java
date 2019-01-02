package ru.trubin23.tasksmvvmlive.tasks;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import ru.trubin23.tasksmvvmlive.data.Task;

public class TasksAdapter extends BaseAdapter {

    private TasksViewModel mTasksViewModel;

    List<Task> mTasks;

    TasksAdapter(List<Task> tasks,
                 TasksViewModel tasksViewModel) {
        mTasksViewModel = tasksViewModel;
        setData(tasks);
    }

    void setData(List<Task> items) {
        mTasks = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mTasks != null ? mTasks.size() : 0;
    }

    @Override
    public Task getItem(int position) {
        return mTasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        return null;
    }
}

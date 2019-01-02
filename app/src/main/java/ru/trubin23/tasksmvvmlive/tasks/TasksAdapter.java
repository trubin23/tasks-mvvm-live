package ru.trubin23.tasksmvvmlive.tasks;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import java.util.List;

import ru.trubin23.tasksmvvmlive.data.Task;
import ru.trubin23.tasksmvvmlive.databinding.TaskItemBinding;

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
        TaskItemBinding taskItemBinding;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            taskItemBinding = TaskItemBinding.inflate(inflater, viewGroup, false);
        } else {
            taskItemBinding = DataBindingUtil.getBinding(view);
        }

        TaskItemUserActionsListener listener = new TaskItemUserActionsListener() {
            @Override
            public void onCompleteChanged(Task task, View view) {
                boolean checked = ((CheckBox) view).isChecked();
                mTasksViewModel.completeTask(task, checked);
            }

            @Override
            public void onTaskClicked(Task task) {
                mTasksViewModel.getOpenTaskEvent().setValue(task.getId());
            }
        };

        taskItemBinding.setTask(getItem(position));

        taskItemBinding.setListener(listener);

        taskItemBinding.executePendingBindings();
        return taskItemBinding.getRoot();
    }
}

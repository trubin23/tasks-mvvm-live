package ru.trubin23.tasksmvvmlive.taskdetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.trubin23.tasksmvvmlive.R;
import ru.trubin23.tasksmvvmlive.ViewModelFactory;
import ru.trubin23.tasksmvvmlive.addedittask.AddEditTaskActivity;
import ru.trubin23.tasksmvvmlive.tasks.TasksViewModel;

public class TaskDetailActivity extends AppCompatActivity implements TaskDetailNavigator {

    public static final String EXTRA_TASK_ID = "TASK_ID";

    public static final int DELETE_RESULT_OK = 1;
    public static final int EDIT_RESULT_OK = 2;

    private static final int REQUEST_EDIT_TASK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskdetail_act);
    }

    void setupToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private TaskDetailFragment findOrCreateViewFragment(){
        TaskDetailFragment taskDetailFragment = (TaskDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (taskDetailFragment == null){
            String taskId = getIntent().getStringExtra(EXTRA_TASK_ID);
            taskDetailFragment = TaskDetailFragment.newInstance(taskId);
        }

        return taskDetailFragment;
    }

    public static TaskDetailViewModel obtainViewModel(FragmentActivity activity){
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        return ViewModelProviders.of(activity, factory).get(TaskDetailViewModel.class);
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onTaskDeleted() {
        setResult(DELETE_RESULT_OK);

        finish();
    }

    @Override
    public void onStartEditTask() {
        String taskId = getIntent().getStringExtra(EXTRA_TASK_ID);
        Intent intent = new Intent(this, AddEditTaskActivity.class);
        intent.putExtra(AddEditTaskActivity.EXTRA_EDIT_TASK_ID, taskId);
        startActivityForResult(intent, REQUEST_EDIT_TASK);
    }
}

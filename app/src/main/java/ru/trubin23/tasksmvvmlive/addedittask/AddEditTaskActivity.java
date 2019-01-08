package ru.trubin23.tasksmvvmlive.addedittask;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.trubin23.tasksmvvmlive.R;
import ru.trubin23.tasksmvvmlive.ViewModelFactory;
import ru.trubin23.tasksmvvmlive.util.ActivityUtils;

public class AddEditTaskActivity extends AppCompatActivity implements AddEditTaskNavigator {

    public static final String EXTRA_EDIT_TASK_ID = "EDIT_TASK_ID";

    public static final int ADD_EDIT_RESULT_OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtask_act);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        findOrCreateViewFragment();

        AddEditTaskViewModel viewModel = obtainViewModel(this);
        viewModel.getTaskUpdate().observe(this, aVoid -> onTaskSaved());
    }

    @Override
    public void onTaskSaved() {
        setResult(ADD_EDIT_RESULT_OK);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static AddEditTaskViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        return ViewModelProviders.of(activity, factory).get(AddEditTaskViewModel.class);
    }

    private void findOrCreateViewFragment() {
        AddEditTaskFragment addEditTaskFragment = (AddEditTaskFragment)
                getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (addEditTaskFragment == null) {
            String taskId = getIntent().getStringExtra(EXTRA_EDIT_TASK_ID);
            addEditTaskFragment = AddEditTaskFragment.newInstance(taskId);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    addEditTaskFragment, R.id.contentFrame);
        }
    }
}

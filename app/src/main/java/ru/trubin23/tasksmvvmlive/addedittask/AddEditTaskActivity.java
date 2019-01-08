package ru.trubin23.tasksmvvmlive.addedittask;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import ru.trubin23.tasksmvvmlive.R;
import ru.trubin23.tasksmvvmlive.ViewModelFactory;
import ru.trubin23.tasksmvvmlive.taskdetail.TaskDetailViewModel;

public class AddEditTaskActivity extends AppCompatActivity {

    public static final String EXTRA_EDIT_TASK_ID = "EDIT_TASK_ID";

    public static final int ADD_EDIT_RESULT_OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtask_act);
    }

    public static AddEditTaskViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        return ViewModelProviders.of(activity, factory).get(AddEditTaskViewModel.class);
    }
}

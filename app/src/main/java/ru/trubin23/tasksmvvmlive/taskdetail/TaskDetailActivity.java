package ru.trubin23.tasksmvvmlive.taskdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.trubin23.tasksmvvmlive.R;

public class TaskDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_ID = "TASK_ID";

    public static final int DELETE_RESULT_OK = 1;
    public static final int EDIT_RESULT_OK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskdetail_act);
    }
}

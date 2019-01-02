package ru.trubin23.tasksmvvmlive.addedittask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.trubin23.tasksmvvmlive.R;

public class AddEditTaskActivity extends AppCompatActivity {

    public static final int ADD_EDIT_RESULT_OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtask_act);
    }
}

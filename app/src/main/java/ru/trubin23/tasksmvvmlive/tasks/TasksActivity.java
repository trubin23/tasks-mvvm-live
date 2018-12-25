package ru.trubin23.tasksmvvmlive.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import ru.trubin23.tasksmvvmlive.R;
import ru.trubin23.tasksmvvmlive.addedittask.AddEditTaskActivity;
import ru.trubin23.tasksmvvmlive.statistics.StatisticsActivity;
import ru.trubin23.tasksmvvmlive.taskdetail.TaskDetailActivity;

public class TasksActivity extends AppCompatActivity
        implements TaskItemNavigator, TasksNavigator {

    public static final int REQUEST_CODE_ADD_EDIT_TASK = 1;
    public static final int REQUEST_CODE_TASK_DETAIL = 2;

    private DrawerLayout mDrawerLayout;

    private TasksViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_act);

        setupToolbar();

        setupNavigationDrawer();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupNavigationDrawer() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.statistics_nav_menu_item) {
                Intent intent = new Intent(TasksActivity.this, StatisticsActivity.class);
                startActivity(intent);
            }

            menuItem.setChecked(true);
            mDrawerLayout.closeDrawers();
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mViewModel.handleActivityResult(requestCode, resultCode);
    }

    @Override
    public void openTaskDetails(String taskId) {
        Intent intent = new Intent(this, TaskDetailActivity.class);
        intent.putExtra(TaskDetailActivity.EXTRA_TASK_ID, taskId);
        startActivityForResult(intent, REQUEST_CODE_TASK_DETAIL);
    }

    @Override
    public void addNewTask() {
        Intent intent = new Intent(this, AddEditTaskActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_EDIT_TASK);
    }
}

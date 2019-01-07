package ru.trubin23.tasksmvvmlive.tasks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;

import ru.trubin23.tasksmvvmlive.R;
import ru.trubin23.tasksmvvmlive.ScrollChildSwipeRefreshLayout;
import ru.trubin23.tasksmvvmlive.SnackbarMessage;
import ru.trubin23.tasksmvvmlive.databinding.TasksFragBinding;
import ru.trubin23.tasksmvvmlive.util.SnackbarUtils;

public class TasksFragment extends Fragment {

    private TasksFragBinding mTasksFragBinding;

    private TasksViewModel mTasksViewModel;

    private TasksAdapter mTasksAdapter;

    public static TasksFragment getInstance() {
        return new TasksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mTasksFragBinding = TasksFragBinding.inflate(inflater, container, false);

        mTasksViewModel = TasksActivity.obtainViewModel(getActivity());

        mTasksFragBinding.setViewmodel(mTasksViewModel);

        setHasOptionsMenu(true);

        return mTasksFragBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTasksViewModel.loadTasks(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tasks_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                mTasksViewModel.clearCompletedTasks();
                break;
            case R.id.menu_filter:
                showFilteringPopupMenu();
                break;
            case R.id.menu_refresh:
                mTasksViewModel.loadTasks(true);
                break;
        }
        return true;
    }

    private void showFilteringPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(
                getContext(), getActivity().findViewById(R.id.menu_filter));
        popupMenu.getMenuInflater().inflate(R.menu.filter_tasks, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_active:
                    mTasksViewModel.setFiltering(TasksFilterType.ACTIVE_TASKS);
                    break;
                case R.id.menu_completed:
                    mTasksViewModel.setFiltering(TasksFilterType.COMPLETED_TASKS);
                    break;
                case R.id.menu_all:
                    mTasksViewModel.setFiltering(TasksFilterType.ALL_TASKS);
                    break;
                default:
                    return false;
            }

            mTasksViewModel.loadTasks(false);
            return true;
        });

        popupMenu.show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupSnackbar();

        setupFab();

        setupListAdapter();

        setupRefreshLayout();
    }

    private void setupSnackbar() {
        mTasksViewModel.getSnackbarMessage().observe(this,
                (SnackbarMessage.ShackbarObserver) snackbarMessageResId ->
                        SnackbarUtils.showSnackbar(getView(), getString(snackbarMessageResId)));
    }

    private void setupFab() {
        FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_task);

        fab.setOnClickListener(v -> mTasksViewModel.addNewTask());

    }

    private void setupListAdapter() {
        ListView listView = mTasksFragBinding.tasksList;

        mTasksAdapter = new TasksAdapter(new ArrayList<>(0), mTasksViewModel);

        listView.setAdapter(mTasksAdapter);
    }

    private void setupRefreshLayout() {
        ListView listView = mTasksFragBinding.tasksList;
        ScrollChildSwipeRefreshLayout swipeRefreshLayout = mTasksFragBinding.refreshLayout;
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getContext(), R.color.colorPrimary),
                ContextCompat.getColor(getContext(), R.color.colorAccent),
                ContextCompat.getColor(getContext(), R.color.colorPrimaryDark)
        );
        swipeRefreshLayout.setScrollUpChild(listView);
    }
}

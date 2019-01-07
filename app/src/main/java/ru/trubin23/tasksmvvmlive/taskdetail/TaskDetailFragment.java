package ru.trubin23.tasksmvvmlive.taskdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import ru.trubin23.tasksmvvmlive.R;
import ru.trubin23.tasksmvvmlive.SnackbarMessage;
import ru.trubin23.tasksmvvmlive.databinding.TaskdetailFragBinding;
import ru.trubin23.tasksmvvmlive.util.SnackbarUtils;

public class TaskDetailFragment extends Fragment {

    public static final String ARGUMENT_TASK_ID = "TASK_ID";

    public static final int REQUEST_EDIT_TASK = 1;

    private TaskDetailViewModel mTaskDetailViewModel;

    public static TaskDetailFragment newInstance(String taskId) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_TASK_ID, taskId);

        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        TaskdetailFragBinding fragBinding =
                TaskdetailFragBinding.inflate(inflater, container, false);

        mTaskDetailViewModel = TaskDetailActivity.obtainViewModel(getActivity());

        fragBinding.setViewmodel(mTaskDetailViewModel);

        TaskDetailUserActionsListener listener =
                view -> mTaskDetailViewModel.setCompleted(((CheckBox) view).isChecked());

        fragBinding.setListener(listener);

        setHasOptionsMenu(true);

        return fragBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupFab();

        setupSnackbar();
    }

    private void setupFab() {
        FloatingActionButton fab = getActivity().findViewById(R.id.fab_edit_task);

        fab.setOnClickListener(v -> mTaskDetailViewModel.editTask());
    }

    private void setupSnackbar() {
        mTaskDetailViewModel.getSnackbarMessage().observe(this,
                (SnackbarMessage.ShackbarObserver) snackbarMessageResId ->
                        SnackbarUtils.showSnackbar(getView(), getString(snackbarMessageResId))
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        mTaskDetailViewModel.start(getArguments().getString(ARGUMENT_TASK_ID));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.taskdetail_frag_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_delete:
                mTaskDetailViewModel.deleteTask();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

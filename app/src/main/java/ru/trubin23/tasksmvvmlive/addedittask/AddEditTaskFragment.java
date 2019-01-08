package ru.trubin23.tasksmvvmlive.addedittask;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.trubin23.tasksmvvmlive.R;
import ru.trubin23.tasksmvvmlive.SnackbarMessage;
import ru.trubin23.tasksmvvmlive.databinding.AddtaskFragBinding;
import ru.trubin23.tasksmvvmlive.util.SnackbarUtils;

public class AddEditTaskFragment extends Fragment {

    public static final String ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID";

    private AddEditTaskViewModel mViewModel;

    public static AddEditTaskFragment newInstance() {
        return new AddEditTaskFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        AddtaskFragBinding addtaskFragBinding =
                AddtaskFragBinding.inflate(inflater, container, false);

        mViewModel = AddEditTaskActivity.obtainViewModel(getActivity());

        addtaskFragBinding.setViewmodel(mViewModel);

        setHasOptionsMenu(true);

        return addtaskFragBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupFab();

        setupSnackbar();

        setupActionBar();

        loadData();
    }

    private void setupFab() {
        FloatingActionButton fab = getActivity().findViewById(R.id.fab_edit_task_done);
        fab.setOnClickListener(v -> mViewModel.saveTask());
    }

    private void setupSnackbar() {
        mViewModel.getSnackbarMessage().observe(this,
                (SnackbarMessage.ShackbarObserver) snackbarMessageResId ->
                        SnackbarUtils.showSnackbar(getView(), getString(snackbarMessageResId))
        );
    }

    private void setupActionBar() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar == null){
            return;
        }
        if (getArguments()!=null && getArguments().get(ARGUMENT_EDIT_TASK_ID)!=null){
            actionBar.setTitle(R.string.title_edit_task);
        } else {
            actionBar.setTitle(R.string.title_add_task);
        }
    }

    private void loadData() {

    }
}

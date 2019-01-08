package ru.trubin23.tasksmvvmlive.addedittask;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.trubin23.tasksmvvmlive.databinding.AddtaskFragBinding;

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

        return addtaskFragBinding.getRoot();
    }
}

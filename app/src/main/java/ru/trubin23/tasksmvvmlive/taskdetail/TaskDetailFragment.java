package ru.trubin23.tasksmvvmlive.taskdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.trubin23.tasksmvvmlive.databinding.TaskdetailFragBinding;

public class TaskDetailFragment extends Fragment {

    public static final String ARGUMENT_TASK_ID = "TASK_ID";

    public static final int REQUEST_EDIT_TASK = 1;

    private TaskDetailViewModel mTaskDetailViewModel;

    public static TaskDetailFragment newInstance(String taskId){
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

        TaskDetailUserActionsListener listener = getTaskDetailUserActionsListener();

        fragBinding.setListener(listener);

        setHasOptionsMenu(true);

        return fragBinding.getRoot();
    }

    private TaskDetailUserActionsListener getTaskDetailUserActionsListener(){
        return new TaskDetailUserActionsListener() {
            @Override
            public void onCompleteChanged(View view) {

            }
        };
    }
}

package ru.trubin23.tasksmvvmlive.statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.trubin23.tasksmvvmlive.databinding.StatisticsFragBinding;

public class StatisticsFragment extends Fragment {

    private StatisticsViewModel mStatisticsViewModel;

    public static StatisticsFragment newInstance(){
        return new StatisticsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        StatisticsFragBinding statisticsFragBinding =
                StatisticsFragBinding.inflate(inflater, container, false);

        mStatisticsViewModel = StatisticsActivity.obtainViewModel(getActivity());

        statisticsFragBinding.setViewmodel(mStatisticsViewModel);

        return statisticsFragBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mStatisticsViewModel.loadStatistics();
    }
}

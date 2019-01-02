package ru.trubin23.tasksmvvmlive;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

public class SnackbarMessage extends SingleLiveEvent<Integer> {

    public void observe(@NonNull LifecycleOwner owner, final ShackbarObserver observer) {
        super.observe(owner, snackbarMessageResId -> {
            if (snackbarMessageResId != null) {
                observer.onNewMessage(snackbarMessageResId);
            }
        });
    }

    public interface ShackbarObserver {
        void onNewMessage(@StringRes int snackbarMessageResId);
    }
}

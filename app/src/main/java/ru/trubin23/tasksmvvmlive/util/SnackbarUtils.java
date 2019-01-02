package ru.trubin23.tasksmvvmlive.util;

import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackbarUtils {

    public static void showSnackbar(View view, String snackbarText){
        if (view != null && snackbarText != null){
            Snackbar.make(view, snackbarText, Snackbar.LENGTH_LONG).show();
        }
    }
}

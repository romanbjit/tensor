package com.dreampany.frame.util;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public final class BarUtil {

    private BarUtil() {}

    public static void hide(AppCompatActivity activity) {
        if (AndroidUtil.hasJellyBeanMR2()) {
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;

/*            if (AndroidUtil.hasKitkat()) {
                uiOptions = uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE;
            }*/
            decorView.setSystemUiVisibility(uiOptions);
            ActionBar bar = activity.getSupportActionBar();
            if (bar != null) {
                bar.hide();
            }
        }
    }

    public static void show(AppCompatActivity activity) {
        if (activity != null && AndroidUtil.hasJellyBeanMR2()) {
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;

            decorView.setSystemUiVisibility(uiOptions);
            ActionBar bar = activity.getSupportActionBar();
            if (bar != null) {
                bar.show();
            }
        }
    }
}

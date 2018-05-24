package com.dreampany.frame.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public final class FragmentUtil {

    private FragmentUtil() {
    }

/*    public static <T extends Fragment> T commitFragment(final AppCompatActivity activity, final Class<T> fragmentClass, final int parentId) {

        final T fragment = getFragment(activity, fragmentClass);

        Runnable commitRunnable = new Runnable() {
            @Override
            public void run() {
                if (activity.isDestroyed() || activity.isFinishing()) {
                    return;
                }
                activity.getSupportFragmentManager().
                        beginTransaction().
                        //setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).
                                replace(parentId, fragment, fragmentClass.getName()).
                        commitAllowingStateLoss();
            }
        };

        AndroidUtil.getUiHandler().postDelayed(commitRunnable, 250L);

        return fragment;
    }*/

    public static <T extends Fragment> T commitFragment(final AppCompatActivity activity, final T fragment, final int parentId) {

        Runnable commitRunnable = new Runnable() {
            @Override
            public void run() {
                if (activity.isDestroyed() || activity.isFinishing()) {
                    return;
                }
                activity.getSupportFragmentManager().
                        beginTransaction().
                        //setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).
                                replace(parentId, fragment, fragment.getClass().getSimpleName()).
                        commitAllowingStateLoss();
            }
        };

        AndroidUtil.getUiHandler().postDelayed(commitRunnable, 250L);

        return fragment;
    }

    public static <T extends Fragment> T getFragment(AppCompatActivity activity, Class<T> fragmentClass) {

        T fragment = getFragmentByTag(activity, fragmentClass.getName());

        if (fragment == null) {
            fragment = newFragment(fragmentClass);
            if (fragment != null /*&& task != null*/) {
                Bundle bundle = new Bundle();
                //bundle.putSerializable(Task.class.getName(), task);
                fragment.setArguments(bundle);
            }
        } /*else if (task != null) {
            if (task instanceof Parcelable) {
                fragment.getArguments().putParcelable(Task.class.getName(), (Parcelable) task);
            } else if (task instanceof Serializable) {
                fragment.getArguments().putSerializable(Task.class.getName(), task);
            }
        }*/

        return fragment;
    }

    public static <T extends Fragment> T getFragmentByTag(AppCompatActivity activity, String fragmentTag) {
        return (T) activity.getSupportFragmentManager().findFragmentByTag(fragmentTag);
    }

    public static <T extends Fragment> T newFragment(Class<T> fragmentClass) {
        try {
            return fragmentClass.newInstance();
        } catch (Exception ignored) {
        }
        return null;
    }
}

package com.dreampany.frame.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreampany.frame.data.model.Task;
import com.dreampany.frame.util.AndroidUtil;
import com.dreampany.frame.ui.activity.BaseActivity;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.Serializable;
import java.util.List;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment extends DaggerFragment
        implements MultiplePermissionsListener, PermissionRequestErrorListener {

    protected ViewDataBinding binding;
    protected Task currentTask;
    protected View currentView;

    protected int getLayoutId() {
        return 0;
    }

    public boolean hasBackPressed() {
        return false;
    }

    protected abstract void onStartUi(Bundle state);

    protected abstract void onStopUi();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (currentView != null) {
            if (currentView.getParent() != null) {
                ((ViewGroup) currentView.getParent()).removeView(currentView);
            }
            return currentView;
        }
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            binding = DataBindingUtil.inflate(inflater, layoutId, container, false);
            currentView = binding.getRoot();
        } else {
            currentView = super.onCreateView(inflater, container, savedInstanceState);
        }
        return currentView;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onStartUi(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        onStopUi();
        if (currentView != null) {
            ViewGroup parent = (ViewGroup) currentView.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
        }
        super.onDestroyView();
    }

    @Override
    public Context getContext() {
        if (AndroidUtil.hasMarshmallow()) {
            return super.getContext();
        }
        View view = getView();
        if (view != null) {
            return view.getContext();
        }
        return getParent();
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {

    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

    }

    @Override
    public void onError(DexterError error) {

    }

    protected BaseActivity getParent() {
        Activity activity = getActivity();
        if (!BaseActivity.class.isInstance(activity) || activity.isFinishing() || activity.isDestroyed()) {
            return null;
        }
        return (BaseActivity) activity;
    }

    protected boolean isParentActive() {
        Activity activity = getParent();
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return false;
        }
        return true;
    }

    protected <T extends Task> T getCurrentTask(boolean freshTask) {
        if (currentTask == null || freshTask) {
            currentTask = getIntentValue(Task.class.getSimpleName());
        }
        return (T) currentTask;
    }

    protected <T> T getIntentValue(String key) {
        Bundle bundle = getBundle();
        return getIntentValue(key, bundle);
    }

    protected <T> T getIntentValue(String key, Bundle bundle) {
        T t = null;
        if (bundle != null) {
            t = (T) bundle.getParcelable(key);
        }
        if (bundle != null && t == null) {
            t = (T) bundle.getSerializable(key);
        }
        return t;
    }

    protected Bundle getBundle() {
        return getArguments();
    }

    protected void setTitle(int resId) {
        Activity activity = getActivity();
        if (BaseActivity.class.isInstance(activity)) {
            ((BaseActivity) activity).setTitle(resId);
        }
    }

    protected void setSubtitle(int resId) {
        Activity activity = getActivity();
        if (BaseActivity.class.isInstance(activity)) {
            ((BaseActivity) activity).setSubtitle(resId);
        }
    }

    protected void setTitle(String title) {
        Activity activity = getActivity();
        if (BaseActivity.class.isInstance(activity)) {
            ((BaseActivity) activity).setTitle(title);
        }
    }

    protected void setSubtitle(String subtitle) {
        Activity activity = getActivity();
        if (BaseActivity.class.isInstance(activity)) {
            ((BaseActivity) activity).setSubtitle(subtitle);
        }
    }

    protected void openActivity(Class<?> clazz) {
        startActivity(new Intent(getParent(), clazz));
    }

    protected void openActivityForResult(Class<?> clazz, int requestCode) {
        startActivityForResult(new Intent(getParent(), clazz), requestCode);
    }

    protected void openActivityParcelable(Class<?> clazz, Task task) {
        Intent bundle = new Intent(getParent(), clazz);
        bundle.putExtra(Task.class.getSimpleName(), (Parcelable) task);
        startActivity(bundle);
    }

    protected void openActivitySerializable(Class<?> clazz, Task task) {
        Intent bundle = new Intent(getParent(), clazz);
        bundle.putExtra(Task.class.getSimpleName(), (Serializable) task);
        startActivity(bundle);
    }
}

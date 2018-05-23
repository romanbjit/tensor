package com.dreampany.frame.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.afollestad.aesthetic.Aesthetic;
import com.dreampany.frame.R;
import com.dreampany.frame.data.model.Task;
import com.dreampany.frame.data.util.BarUtil;
import com.dreampany.frame.data.util.FragmentUtil;
import com.dreampany.frame.data.util.TextUtil;
import com.dreampany.frame.ui.fragment.BaseFragment;

import java.io.Serializable;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;


public abstract class BaseActivity extends DaggerAppCompatActivity {

    protected ViewDataBinding binding;
    protected Task currentTask;
    protected BaseFragment currentFragment;
    protected boolean fireOnStartUi = true;

    protected int getLayoutId() {
        return 0;
    }

    protected int getToolbarId() {
        return R.id.toolbar;
    }

    protected boolean isFullScreen() {
        return false;
    }

    protected boolean isHomeUp() {
        return true;
    }

    protected abstract void onStartUi(Bundle state);

    protected abstract void onStopUi();

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        Aesthetic.attach(this);
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            initLayout(layoutId);
            initToolbar();
            //initTheme();
        }
        if (fireOnStartUi) {
            onStartUi(savedInstanceState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Aesthetic.resume(this);
    }

    @Override
    protected void onPause() {
        //Aesthetic.pause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        onStopUi();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
/*        if (!beBackPressed()) {
            return;
        }

        BaseFragment currentFragment = getCurrentFragment();
        if (currentFragment != null) {
            if (currentFragment.beBackPressed()) {
                return;
            }
        }

        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack();
            return;
        }*/

        super.onBackPressed();
    }

/*    @Nullable
    @Override
    public String key() {
        return "base";
    }*/

    private void initLayout(int layoutId) {
        if (isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            BarUtil.hide(this);
        } else {
            BarUtil.show(this);
        }
        binding = DataBindingUtil.setContentView(this, layoutId);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(getToolbarId());
        if (toolbar != null) {
            if (isFullScreen()) {
                if (toolbar.isShown()) {
                    toolbar.setVisibility(View.GONE);
                }
            } else {
                if (!toolbar.isShown()) {
                    toolbar.setVisibility(View.VISIBLE);
                }
                setSupportActionBar(toolbar);
                if (isHomeUp()) {
                    ActionBar actionBar = getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.setDisplayHomeAsUpEnabled(true);
                        actionBar.setHomeButtonEnabled(true);
                    }
                }
            }
        }
    }

    private void initTheme() {
        //if (Aesthetic.isFirstTime()) {
        Aesthetic.get()
                .colorPrimaryRes(R.color.colorPrimary)
                .colorPrimaryDarkRes(R.color.colorPrimaryDark)
                .colorAccentRes(R.color.colorAccent)
                .colorStatusBarAuto()
                .colorNavigationBarAuto()
                .textColorPrimaryRes(android.R.color.black)
                .textColorPrimaryInverseRes(android.R.color.white)
                .textColorSecondaryRes(R.color.material_grey500)
                .textColorSecondaryInverseRes(R.color.material_grey100)
                //.bottomNavigationBackgroundMode(BottomNavBgMode.PRIMARY)
                //.bottomNavigationIconTextMode(BottomNavIconTextMode.SELECTED_ACCENT)
                .apply();
        //}
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
        return getIntent().getExtras();
    }

    public void setTitle(int resId) {
        setTitle(TextUtil.getString(this, resId));
    }

    public void setSubtitle(int resId) {
        setSubtitle(TextUtil.getString(this, resId));
    }

    public void setTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public void setSubtitle(String subtitle) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(subtitle);
        }
    }

    public BaseFragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(BaseFragment fragment) {
        this.currentFragment = fragment;
    }

    public void openActivity(Class<?> clazz) {
        startActivity(new Intent(this, clazz));
    }

    public void openActivityForResult(Class<?> clazz, int requestCode) {
        startActivityForResult(new Intent(this, clazz), requestCode);
    }

    public void openActivityParcelable(Class<?> clazz, Task task) {
        Intent bundle = new Intent(this, clazz);
        bundle.putExtra(Task.class.getSimpleName(), (Parcelable) task);
        startActivity(bundle);
    }

    public void openActivitySerializable(Class<?> clazz, Task task) {
        Intent bundle = new Intent(this, clazz);
        bundle.putExtra(Task.class.getSimpleName(), (Serializable) task);
        startActivity(bundle);
    }

/*    protected <T extends BaseFragment> T commitFragment(final Class<T> fragmentClass, final int parentId) {
        T currentFragment = FragmentUtil.commitFragment(this, fragmentClass, parentId);
        setCurrentFragment(currentFragment);
        return currentFragment;
    }*/

    protected <T extends BaseFragment> T commitFragment(Class<T> clazz, Lazy<T> fragmentProvider, final int parentId) {
        T fragment = FragmentUtil.getFragmentByTag(this, clazz.getSimpleName());
        if (fragment == null) {
            fragment = fragmentProvider.get();
        }
        T currentFragment = FragmentUtil.commitFragment(this, fragment, parentId);
        setCurrentFragment(currentFragment);
        return currentFragment;
    }

    protected <T extends BaseFragment> T commitFragment(Class<T> clazz, Lazy<T> fragmentProvider, final int parentId, Task task) {
        T fragment = FragmentUtil.getFragmentByTag(this, clazz.getSimpleName());
        if (fragment == null) {
            fragment = fragmentProvider.get();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Task.class.getSimpleName(), task);
            fragment.setArguments(bundle);
        } else {
            fragment.getArguments().putParcelable(Task.class.getSimpleName(), task);
        }

        T currentFragment = FragmentUtil.commitFragment(this, fragment, parentId);
        setCurrentFragment(currentFragment);
        return currentFragment;
    }
}

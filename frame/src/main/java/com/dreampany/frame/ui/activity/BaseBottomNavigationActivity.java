package com.dreampany.frame.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

public abstract class BaseBottomNavigationActivity extends BaseMenuActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private int currentNavigationId;

    protected int getNavViewId() {
        return 0;
    }

    protected int getDefaultSelectedNavigationItemId() {
        return 0;
    }

    protected abstract void onNavigationItem(int navItemId);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        fireOnStartUi = false;
        super.onCreate(savedInstanceState);

        final BottomNavigationView navigationView = findViewById(getNavViewId());
        if (navigationView != null) {
            navigationView.setOnNavigationItemSelectedListener(this);
        }
        setSelectedItem(getDefaultSelectedNavigationItemId());
        onStartUi(savedInstanceState);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int targetNavigationId = item.getItemId();
        if (targetNavigationId != currentNavigationId) {
            onNavigationItem(targetNavigationId);
            currentNavigationId = targetNavigationId;
            return true;
        }
        return false;
    }

    public void setSelectedItem(final int navigationItemId) {
        if (navigationItemId != 0) {
            final BottomNavigationView navigationView = findViewById(getNavViewId());
            if (navigationView != null) {
                navigationView.post(() -> navigationView.setSelectedItemId(navigationItemId));
            }
        }
    }
}

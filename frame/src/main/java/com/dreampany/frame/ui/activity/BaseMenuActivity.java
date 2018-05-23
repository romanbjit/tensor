package com.dreampany.frame.ui.activity;

import android.view.Menu;

public abstract class BaseMenuActivity extends BaseActivity {

    protected int getMenuId() {
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menuId = getMenuId();
        if (menuId != 0) { //this need clear
            menu.clear();
            getMenuInflater().inflate(menuId, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }
}

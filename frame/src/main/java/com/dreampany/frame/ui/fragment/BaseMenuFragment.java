package com.dreampany.frame.ui.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public abstract class BaseMenuFragment extends BaseFragment {

    protected Menu menu;
    protected MenuInflater inflater;

    protected int getMenuId() {
        return 0;
    }

    protected abstract void onMenuCreated(Menu menu);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        this.menu = menu;
        this.inflater = inflater;
        int menuId = getMenuId();

        if (menuId != 0) {
            menu.clear();
            inflater.inflate(menuId, menu);
            binding.getRoot().post(new Runnable() {
                @Override
                public void run() {
                    onMenuCreated(menu);
                }
            });
        }
    }
}

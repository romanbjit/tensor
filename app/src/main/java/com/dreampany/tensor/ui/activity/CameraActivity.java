package com.dreampany.tensor.ui.activity;

import android.os.Bundle;

import com.dreampany.frame.ui.activity.BaseActivity;
import com.dreampany.tensor.R;

/**
 * Created by Hawladar Roman on 5/24/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */
public abstract class CameraActivity extends BaseActivity {

    @Override
    protected boolean isScreenOn() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera;
    }

    @Override
    protected void onStartUi(Bundle state) {

    }
}

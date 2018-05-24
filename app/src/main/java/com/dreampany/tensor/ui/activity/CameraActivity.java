package com.dreampany.tensor.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.util.Size;

import com.dreampany.frame.ui.activity.BaseActivity;
import com.dreampany.frame.util.AndroidUtil;
import com.dreampany.tensor.R;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.listener.PermissionGrantedResponse;

/**
 * Created by Hawladar Roman on 5/24/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */
public abstract class CameraActivity extends BaseActivity {

    protected abstract void processImage();

    protected abstract void onPreviewSizeChosen(final Size size, final int rotation);
    protected abstract int getCameraLayoutId();
    protected abstract Size getDesiredPreviewFrameSize();

    private final String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

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

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        if (AndroidUtil.hasPermissions(permissions)) {
            //open camera fragment
        }
    }
}

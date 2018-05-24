package com.dreampany.frame.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.dreampany.frame.R;

import java.util.Arrays;

import eu.davidea.flexibleadapter.utils.FlexibleUtils;

public final class AndroidUtil {
    private AndroidUtil() {
    }

    private static int colorPrimary = -1;
    private static int colorPrimaryDark = -1;
    private static int colorAccent = -1;

    private static Handler uiHandler;
    private static Handler backHandler;

    public static boolean hasJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    public static boolean hasKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean hasMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean hasNougat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    public static Handler getUiHandler() {
        if (uiHandler == null) {
            uiHandler = new Handler(Looper.getMainLooper());
        }
        return uiHandler;
    }

    public static boolean isUiThread() {
        if (hasMarshmallow()) {
            return Looper.getMainLooper().isCurrentThread();
        }
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    public static boolean isScreenOn(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            DisplayManager dm = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
            boolean screenOn = false;
            for (Display display : dm.getDisplays()) {
                if (display.getState() != Display.STATE_OFF) {
                    screenOn = true;
                }
            }
            return screenOn;
        } else {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            //noinspection deprecation
            return pm.isScreenOn();
        }
    }

    public static String getApplicationId(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            return packageInfo.packageName;
        }
        return null;
    }

    public static int getVersionCode(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        return 0;
    }

    public static String getVersionName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            return packageInfo.versionName;
        }
        return null;
    }

    private static PackageInfo getPackageInfo(Context context) {
        return getPackageInfo(context, 0);
    }

    private static PackageInfo getPackageInfo(Context context, int flags) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), flags);
        } catch (PackageManager.NameNotFoundException nameException) {
            return null;
        }
    }

    public static boolean hasPermissions(String... permissions) {
        if (permissions != null) {

        }
        return false; //when not decided or not expected solution
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean hasOverlayPermission(Context context) {
        return Settings.canDrawOverlays(context.getApplicationContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean hasWriteSettingsPermission(Context context) {
        return Settings.System.canWrite(context.getApplicationContext());
    }

    public static boolean checkOverlayPermission(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (!hasOverlayPermission(activity)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivityForResult(intent, requestCode);
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkWriteSettingsPermission(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (!hasWriteSettingsPermission(activity.getApplicationContext())) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + activity.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivityForResult(intent, requestCode);
            return false;
        } else {
            return true;
        }
    }

    public static boolean hasPermission(Context context, String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return hasPermissionInManifest(context, permission);
        }
        return ContextCompat.checkSelfPermission(context.getApplicationContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    private static boolean hasPermissionInManifest(Context context, String permission) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_PERMISSIONS);
        if (packageInfo != null) {
            String[] requestedPermissions = packageInfo.requestedPermissions;
            if (requestedPermissions != null) {
                if (Arrays.asList(requestedPermissions).contains(permission)) {
                    return true;
                }
            }
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static int getColorPrimary(Context context) {
        if (colorPrimary < 0) {
            int primaryAttr = FlexibleUtils.hasLollipop() ? android.R.attr.colorPrimary : R.attr.colorPrimary;
            TypedArray androidAttr = context.getTheme().obtainStyledAttributes(new int[]{primaryAttr});
            colorPrimary = androidAttr.getColor(0, 0xFFFFFFFF); //Default: material_deep_teal_500
            androidAttr.recycle();
        }
        return colorPrimary;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static int getColorPrimaryDark(Context context) {
        if (colorPrimaryDark < 0) {
            int primaryDarkAttr = FlexibleUtils.hasLollipop() ? android.R.attr.colorPrimaryDark : R.attr.colorPrimaryDark;
            TypedArray androidAttr = context.getTheme().obtainStyledAttributes(new int[]{primaryDarkAttr});
            colorPrimaryDark = androidAttr.getColor(0, 0xFFFFFFFF); //Default: material_deep_teal_500
            androidAttr.recycle();
        }
        return colorPrimaryDark;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static int getColorAccent(Context context) {
        if (colorAccent < 0) {
            int accentAttr = FlexibleUtils.hasLollipop() ? android.R.attr.colorAccent : R.attr.colorAccent;
            TypedArray androidAttr = context.getTheme().obtainStyledAttributes(new int[]{accentAttr});
            colorAccent = androidAttr.getColor(0, 0xFFFFFFFF); //Default: material_deep_teal_500
            androidAttr.recycle();
        }
        return colorAccent;
    }

    /**
     * Show Soft Keyboard with new Thread
     *
     * @param activity
     */
    public static void hideSoftInput(final Activity activity) {
        if (activity.getCurrentFocus() != null) {
            ((Runnable) () -> {
                InputMethodManager imm =
                        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                }
            }).run();
        }
    }

    /**
     * Hide Soft Keyboard from Dialogs with new Thread
     *
     * @param context
     * @param view
     */
    public static void hideSoftInputFrom(final Context context, final View view) {
        ((Runnable) () -> {
            InputMethodManager imm =
                    (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }).run();
    }

    /**
     * Show Soft Keyboard with new Thread
     *
     * @param context
     * @param view
     */
    public static void showSoftInput(final Context context, final View view) {
        ((Runnable) () -> {
            InputMethodManager imm =
                    (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }).run();
    }
}

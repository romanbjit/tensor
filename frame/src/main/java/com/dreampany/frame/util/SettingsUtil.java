package com.dreampany.frame.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.dreampany.frame.R;
import com.webianks.easy_feedback.EasyFeedback;

/**
 * Created by Hawladar Roman on 5/24/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */
public final class SettingsUtil {
    private SettingsUtil() {
    }

    public static void rateUs(Activity activity) {
        String id = AndroidUtil.getApplicationId(activity);
        Uri uri = Uri.parse("market://details?id=" + id);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(goToMarket);
    }

    public static void feedback(Activity activity) {
        new EasyFeedback.Builder(activity)
                .withEmail(activity.getString(R.string.email))
                .withSystemInfo()
                .build()
                .start();
    }
}

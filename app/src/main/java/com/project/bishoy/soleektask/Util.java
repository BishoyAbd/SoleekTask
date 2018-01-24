package com.project.bishoy.soleektask;

import android.content.Context;

/**
 * Created by bisho on 1/24/2018.
 */

public class Util {
    public static final long ERROR_404 = 404;
    public static final String ERROR_NO_DATA = "No data found";
    public static final String IMAGE_URL = "http://www.thejerb.com/jerb/public/uploads/tips/";
    public static final String TODO_ENDPOINT = "guest_todos";
    public static final String TIPS_ENDPOINT = "tips";


    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}

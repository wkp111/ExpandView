package com.wkp.expandview_lib.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by user on 2017/10/24.
 */

public class ViewUtil {
    /**
     * px转换为dp
     * @param context
     * @param px
     * @return
     */
    public static int pxForDp(Context context,int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics());
    }

    /**
     * dp/sp转换为px
     * @param context
     * @param dpOrSp
     * @return
     */
    public static int dspForPx(Context context,int dpOrSp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dpOrSp, context.getResources().getDisplayMetrics());
    }

    /**
     * px转换为sp
     * @param context
     * @param px
     * @return
     */
    public static int pxForSp(Context context,int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, px, context.getResources().getDisplayMetrics());
    }
}

package com.sky.timetracker.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;

public class ViewUtils {

    private static final String TAG = "ViewUtils";

    /**
     * 保存view为图片
     *
     * @param activity     Activity
     * @param view         View
     * @param savePathName 保存的文件路径及文件名
     */
    public static void saveView(Activity activity, View view, String savePathName) throws Exception {

        //计算设备分辨率
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        // 整个View的大小 参数是左上角 和右下角的坐标
//        view.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);

        //测量，布局View
//        view.measure(measuredWidth, measuredHeight);
//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);

        // 把一个View转换成图片
        Bitmap cacheBmp = viewConversionBitmap(view);

        File file = new File(savePathName);
        FileOutputStream fos = new FileOutputStream(file);

        cacheBmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
        fos.flush();
        fos.close();
        view.destroyDrawingCache();

        //发送广播更新相册
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        activity.sendBroadcast(intent);

    }


    /**
     * view转bitmap
     *
     * @param v View
     * @return Bitmap
     */
    public static Bitmap viewConversionBitmap(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Log.e(TAG, "width: " + w + " height: " + h);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

//        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }
}
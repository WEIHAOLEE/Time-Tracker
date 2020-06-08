package com.sky.timetracker.util;

import android.graphics.Bitmap;

import java.nio.ByteBuffer;

public class BitmapToByteArray {
    public static byte[] toByteArray(Bitmap bmp){

        int bytes = bmp.getByteCount();

        ByteBuffer buf = ByteBuffer.allocate(bytes);
        bmp.copyPixelsToBuffer(buf);

        byte[] byteArray = buf.array();
        return byteArray;
    }
}

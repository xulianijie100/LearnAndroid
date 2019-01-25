package com.hy.android.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

public class ImageDownLoader {

    private static final String TAG = "ImageDownLoader";

    private LruCache<String, Bitmap>lruCache;

    public ImageDownLoader() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        Log.e(TAG, "maxMemory---"+maxMemory);
        int cacheSize= (int) (maxMemory/8);
        lruCache=new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public void addBitmapToMemory(String key ,Bitmap bitmap){
        if(getBitmapFromMemory(key)==null){
            lruCache.put(key,bitmap);
        }
    }

    public Bitmap getBitmapFromMemory(String key){
        return lruCache.get(key);

    }

    public void removeBitmapFromMemory(String key){
        lruCache.remove(key);
    }
}

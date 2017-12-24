package com.hometest.wechatapp.util;

/**
 * File name: ImageLoaderUtil.java
 * Author: liwenlu
 * Description: The util of ImageLoader to display the image
 * 
 */

import com.hometest.wechatapp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageLoaderUtil {
	
    public static ImageLoader getImageLoader( ) {
        return ImageLoader.getInstance();
    }
    
    public static DisplayImageOptions getPhotoImageOption() {
        Integer extra = 1;
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .showImageOnFail(R.mipmap.error)
                .showImageOnLoading(R.mipmap.loading)
                .extraForDownloader(extra)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        return options;
    }
    
    public static void displayImage(ImageView imageView, String url) {
        getImageLoader().displayImage(url, imageView, getPhotoImageOption());
    }

    public static void displayImage(ImageView imageView, String url, ImageLoadingListener listener) {
        getImageLoader().displayImage(url, imageView, getPhotoImageOption(), listener);
    }
}

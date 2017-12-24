package com.hometest.wechatapp;
/**
 * File name: WeChatApiplication.java
 * Author: liwenlu
 * Description: The Application of WeChatApp
 *              init the configuration of ImageLoader 
 */

import java.io.File;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class WeChatApplication  extends Application{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Context context = getApplicationContext();
		File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "WeChatApp");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration  
			    .Builder(context)  
			    .threadPoolSize(5)
			    .threadPriority(Thread.NORM_PRIORITY)  
			    .tasksProcessingOrder(QueueProcessingType.FIFO)  
			    .discCacheFileCount(100)
			     .discCache(new UnlimitedDiskCache(cacheDir))
			    .defaultDisplayImageOptions(DisplayImageOptions.createSimple())  
			    .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000))
			    .build();  
		ImageLoader.getInstance().init(config);		
	}
	
	
 
}

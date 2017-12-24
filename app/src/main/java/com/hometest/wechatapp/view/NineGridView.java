package com.hometest.wechatapp.view;
/**
 * File name: NineGridView.java
 * Author: liwenlu 
 * Description: The View to show 1~9 images of wechat
 * 
 */
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import com.hometest.wechatapp.util.ImageLoaderUtil;
import com.hometest.wechatapp.data.Images;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class NineGridView extends ViewGroup {
	
    private static final String TAG = "NineGridView";
    private static final float DEFUALT_SPACING = 10f;
    private static final int MAX_COUNT = 9;
	
    protected Context mContext;
    private float mSpacing = DEFUALT_SPACING;
    private int mColumns;
    private int mRows;
    private int mTotalWidth;
    private int mSingleWidth;
    private boolean mIsFirst = true;
    private List<String> mUrlList = new ArrayList<String>();
    protected static final int MAX_W_H_RATIO = 3;

    
	public NineGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
        mContext = context;
        if (getListSize(mUrlList) == 0) {
            setVisibility(GONE);
        }
	}
	
    public NineGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if (getListSize(mUrlList) == 0) {
            setVisibility(GONE);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mTotalWidth = right - left;
        mSingleWidth = (int) ((mTotalWidth - mSpacing * (3 - 1)) / 3);
        if (mIsFirst) {
            notifyDataSetChanged();
            mIsFirst = false;
        }
    }



    public void setUrlList(List<Images> imageList) {
        if (imageList == null||imageList.size() == 0 ) {
            setVisibility(GONE);
            return;
        }
        setVisibility(VISIBLE);

        mUrlList.clear();
        for(int i = 0; i<imageList.size(); i++) {
        	mUrlList.add(imageList.get(i).getUrl());
        }

        if (!mIsFirst) {
            notifyDataSetChanged();
        }
        
    }
    
    public void notifyDataSetChanged() {
        post(new TimerTask() {
            @Override
            public void run() {
                refresh();
            }
        });
    }
    
    private int getListSize(List<String> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return list.size();
    }
    
    private void refresh( ) {
    	removeAllViews();
        int size = getListSize(mUrlList);
        if (size > 0) {
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }
        
        if(size == 0) {
        	String url = mUrlList.get(0);
        	ImageView imageView = new ImageView(mContext);
//           setOneImageLayoutParams(imageView,mTotalWidth,mSingleWidth);
            ImageLoaderUtil.displayImage(imageView, url);
        } else {
            generateChildrenLayout(size);
            layoutParams();
            
            for (int i = 0; i < size; i++) {
                String url = mUrlList.get(i);
                ImageView imageView = new ImageView(mContext);
                layoutImageView(imageView, i, url);
            }
        }
    }
    
    private void generateChildrenLayout(int length) {
        if (length <= 3) {
            mRows = 1;
            mColumns = length;
        } else if (length <= 6) {
            mRows = 2;
            mColumns = 3;
            if (length == 4) {
                mColumns = 2;
            }
        } else {
            mRows = 3;
        }

    }
   
    private void layoutImageView(ImageView imageView, int i, String url) {
        final int singleWidth = (int) ((mTotalWidth - mSpacing * (3 - 1)) / 3);
        int singleHeight = singleWidth;

        int[] position = findPosition(i);
        int left = (int) ((singleWidth + mSpacing) * position[1]);
        int top = (int) ((singleHeight + mSpacing) * position[0]);
        int right = left + singleWidth;
        int bottom = top + singleHeight;

        imageView.layout(left, top, right, bottom);

        addView(imageView);
        ImageLoaderUtil.displayImage(imageView, url);
    }
    
    private int[] findPosition(int childNum) {
        int[] position = new int[2];
        for (int i = 0; i < mRows; i++) {
            for (int j = 0; j < mColumns; j++) {
                if ((i * mColumns + j) == childNum) {
                    position[0] = i;
                    position[1] = j;
                    break;
                }
            }
        }
        return position;
    }
    
    private void layoutParams() {
        int singleHeight = mSingleWidth;
        LayoutParams params = getLayoutParams();
        params.height = (int) (singleHeight * mRows + mSpacing * (mRows - 1));
        setLayoutParams(params);
    }
    
    protected void setOneImageLayoutParams(ImageView imageView, int width, int height) {
        imageView.setLayoutParams(new LayoutParams(width, height));
        imageView.layout(0, 0, width, height);

        LayoutParams params = getLayoutParams();
        params.width = width;
        params.height = height;
        setLayoutParams(params);
    }
    

}

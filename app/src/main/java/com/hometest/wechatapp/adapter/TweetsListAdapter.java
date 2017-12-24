package com.hometest.wechatapp.adapter;
/**
 * File name: TweetsListAdapter.java
 * Author: liwenlu
 * Description: The adapter of the list to show the tweets
 * 
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hometest.wechatapp.R;
import com.hometest.wechatapp.util.ImageLoaderUtil;
import com.hometest.wechatapp.util.Utility;
import com.hometest.wechatapp.view.NineGridView;
import com.hometest.wechatapp.data.Tweet;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class TweetsListAdapter extends BaseAdapter {
	private Context mContext = null;
	private List<Tweet> mTweets = null;
	private static final String TAG = "TweetsListAdapter";
	
	
	public TweetsListAdapter(List<Tweet> lists, Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mTweets = new ArrayList<Tweet>();
		mTweets.addAll(lists);
		Log.d(TAG, "lwl TweetsListAdapter mTweets:"+mTweets.size());

	}
	
	public void updateListData(List<Tweet> lists ) {
		mTweets.clear();
		mTweets.addAll(lists);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTweets.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mTweets.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
	    if(convertView == null) {
	    	holder = new ViewHolder();
	    	LayoutInflater inflater = LayoutInflater.from(mContext);
	    	convertView =(LinearLayout)inflater.inflate(R.layout.listview_tweets_item, null);
	    	holder.mAvatarView = (ImageView) convertView.findViewById(R.id.tweet_avatar_image);
	    	holder.mNickName = (TextView)convertView.findViewById(R.id.tweet_nick);
	    	holder.mContent = (TextView)convertView.findViewById(R.id.content);
	    	holder.mGridView = (NineGridView)convertView.findViewById(R.id.nine_gridview);
	    	holder.mComment = (ListView) convertView.findViewById(R.id.comment);
	    	convertView.setTag(holder);
	    } else {
	    	holder = (ViewHolder)convertView.getTag();
	    }

	    
	    ImageLoaderUtil.displayImage(holder.mAvatarView,mTweets.get(position).getSender().getAvatar());
	    holder.mNickName.setText(mTweets.get(position).getSender().getNick());
	    
	    if(mTweets.get(position).getContent()!=null){
	    	holder.mContent.setVisibility(View.VISIBLE);
	    	holder.mContent.setText(mTweets.get(position).getContent());
	    } else {
	    	holder.mContent.setVisibility(View.GONE);
	    }
	    
	    holder.mGridView.setUrlList(mTweets.get(position).getImages());
	    
	    if(mTweets.get(position).getComments() != null) {
		    CommentListAdapter commentAdpater = new CommentListAdapter(mTweets.get(position).getComments(),mContext);
		    holder.mComment.setAdapter(commentAdpater);
		    Utility.setListViewHeightBasedOnChildren(holder.mComment);
	    }


		return convertView;
	}

	class ViewHolder {
		ImageView mAvatarView;
		TextView mNickName;
		TextView mContent; 
		NineGridView mGridView;
		ListView mComment;
	}

}

package com.hometest.wechatapp.adapter;
/**
 * File name: CommentListAdapter.java
 * Author: liwenlu
 * Description: The adapter of the list to show the comments
 * 
 */

import java.util.ArrayList;
import java.util.List;

import com.hometest.wechatapp.R;
import com.hometest.wechatapp.adapter.TweetsListAdapter.ViewHolder;
import com.hometest.wechatapp.data.Comments;
import com.hometest.wechatapp.data.Tweet;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommentListAdapter extends BaseAdapter {

	private Context mContext = null;
	private List<Comments> mComments = null;
	private static final String TAG = "CommentListAdapter";

	
	public CommentListAdapter(List<Comments> list, Context context) {
		//Log.d(TAG, "lwl CommentListAdapter") 
		mContext = context;
		mComments = new ArrayList<Comments>();
		mComments.addAll(list);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mComments.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mComments.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub		
		ViewHolder holder = null;
		if(convertView == null) {
			holder = new ViewHolder();
	    	LayoutInflater inflater = LayoutInflater.from(mContext);
	    	convertView =(LinearLayout)inflater.inflate(R.layout.listview_comment_item, null);
	    	holder.mTextView = (TextView)convertView.findViewById(R.id.comment_content);
	    	convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		String comment = mComments.get(position).getSender().getNick().toString()+": "
		+mComments.get(position).getContent().toString();
		holder.mTextView.setText(comment);
		return convertView;
	}
	
	class ViewHolder {
		TextView mTextView;
	}

}

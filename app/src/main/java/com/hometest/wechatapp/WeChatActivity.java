package com.hometest.wechatapp;

/**
 * File name: WeChatActivity.java
 * Author: liwenlu
 * Description: The Main activity of this app
 * Inner Class:
 * 			 1. UserDatasyncTask is the syncTask to load the information of user data
 * 			 2.TweetsDatasyncTask is the syncTask to load the information of twenets list
 * Others:
 * 			1. User Gson to parson the json flie
 *          2. implements OnRefreshListener to achieve pulling down and refreshing views
 *          3. implements OnLoadMore to achieve pulling up and refreshing view
 */
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.hometest.wechatapp.adapter.TweetsListAdapter;
import com.hometest.wechatapp.util.ImageLoaderUtil;
import com.hometest.wechatapp.view.TweetListView;
import com.hometest.wechatapp.view.TweetListView.OnLoadMore;
import com.hometest.wechatapp.data.Tweet;
import com.hometest.wechatapp.data.User;
import com.hometest.wechatapp.http.HttpConnectionData;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;


import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WeChatActivity extends ActionBarActivity implements OnRefreshListener, OnLoadMore {

    private static final String TAG = "WeChatActivity";
    private static final String USER_URL = "http://thoughtworks-ios.herokuapp.com/user/jsmith";
    private static final String TWEETS_URL = "http://thoughtworks-ios.herokuapp.com/user/jsmith/tweets";
    private static final int  EVERY_PAGE_COUNT = 5;

    private UserDatasyncTask mUserTask = null;
    private TweetsDatasyncTask mTweetsTask = null;

    private Context mContext = null;
    private ImageView mProfileView = null;
    private ImageView mAvatarView = null;
    private TextView mNickView = null;
    private TweetListView mTweetsList = null;
    private View mHeaderView = null;
    private SwipeRefreshLayout mSwip = null;

    private TweetsListAdapter mTweetsAdpater= null;
    private List<Tweet> mValidTweets = null;
    private List<Tweet> mLoadTweets = null;
    private Boolean mIsFirstIn = true;
    private Boolean mIsAllLoad = false;
    private int mPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"lwl onCreate");
        setContentView(R.layout.activity_wechatapp);
        mContext = getApplicationContext();
        initView();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d(TAG,"lwl onDestroy");
        if( null != mValidTweets) {
            mValidTweets.clear();
        }

        if(null != mLoadTweets) {
            mLoadTweets.clear();
        }
        if(null != mUserTask||!mUserTask.isCancelled()) {
            mUserTask.cancel(true);
        }
        if(null != mTweetsTask||!mTweetsTask.isCancelled()) {
            mTweetsTask.cancel(true);
        }
    }

    private void initView() {
        //tweets list view init
        mTweetsList = (TweetListView)findViewById(R.id.tweets_list);
        mTweetsList.setLoadMoreListen(this);
        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.head_view, mTweetsList,false);
        mTweetsList.addHeaderView(mHeaderView);

        //user info view init
        mProfileView = (ImageView)mHeaderView.findViewById(R.id.profile_image);
        mAvatarView = (ImageView)mHeaderView.findViewById(R.id.avatar_image);
        mNickView = (TextView)mHeaderView.findViewById(R.id.nick);

        //refresh init
        mSwip = (SwipeRefreshLayout) findViewById(R.id.swip_index);
        mSwip.setOnRefreshListener(this);
        mSwip.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
    }

    private void initData(){

        SharedPreferences sharePref = mContext.getSharedPreferences("app_start_state", Context.MODE_PRIVATE);
        mIsFirstIn = sharePref.getBoolean("firstIn", true);
        Editor editor = sharePref.edit();
        editor.putBoolean("firstIn", false);
        editor.commit();

        Log.d(TAG,"lwl initData firstin = "+mIsFirstIn);

        mLoadTweets = new ArrayList<Tweet>();
        mUserTask = new UserDatasyncTask();
        mUserTask.execute(USER_URL);
        mTweetsTask = new TweetsDatasyncTask();
        mTweetsTask.execute(TWEETS_URL);
    }

    class UserDatasyncTask extends AsyncTask<String, String, User> {

        @Override
        protected User doInBackground(String... param) {
            // TODO Auto-generated method stub
            String url = param[0];
            HttpConnectionData userConnct = new HttpConnectionData(url);
            String dataFromHttp = userConnct.getConnectionData();
            Gson gson = new Gson ();
            User user = gson.fromJson(dataFromHttp, User.class);
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            // TODO Auto-generated method stub
            super.onPostExecute(user);
            Log.d(TAG, "lwl onPostExecute user:"+user.getNick());
            ImageLoaderUtil.displayImage(mProfileView,user.getProfieImage());
            ImageLoaderUtil.displayImage(mAvatarView,user.getAvatar());
            mNickView.setText(user.getNick());

        }

    }

    class TweetsDatasyncTask extends AsyncTask<String, String, List<Tweet>> {

        @Override
        protected List<Tweet> doInBackground(String... param) {
            // TODO Auto-generated method stub
            String url = param[0];
            HttpConnectionData userConnct = new HttpConnectionData(url);
            String dataFromHttp = userConnct.getConnectionData();

            Gson gson = new Gson();
            List<Tweet> tweets = gson.fromJson(dataFromHttp, new TypeToken<List<Tweet>>(){}.getType());

            mValidTweets = getValidTweets(tweets);

            return mValidTweets;
        }

        @Override
        protected void onPostExecute(List<Tweet> tweets) {
            // TODO Auto-generated method stub
            super.onPostExecute(tweets);
            Log.d(TAG, "lwl onPostExecute tweets:"+ tweets.size());

            if(mIsFirstIn) {//when first in load all data
                mTweetsAdpater = new TweetsListAdapter(tweets, mContext);
                mTweetsList.setAdapter(mTweetsAdpater);
                mIsAllLoad = true;
            } else {// not first in, load first 5 data
                Log.d(TAG,"lwl new initTweetData");
                mIsAllLoad = false;
                mPage = 0;
                initTweetData(mPage);
            }
        }
    }

    private void initTweetData(int page){
        Log.d(TAG,"lwl initTweetData page: "+page);
        if( 0 == page) {
            for (int pagIndex = 0; pagIndex < EVERY_PAGE_COUNT; pagIndex++) {
                if( mValidTweets.get(pagIndex)!= null) {
                    mLoadTweets.add(mValidTweets.get(pagIndex));
                }
            }
            mTweetsAdpater = new TweetsListAdapter(mLoadTweets, mContext);
            mTweetsList.setAdapter(mTweetsAdpater);
        } else {
            int startDex = page * EVERY_PAGE_COUNT;
            for (int pagIndex = startDex; pagIndex < startDex + EVERY_PAGE_COUNT; pagIndex++) {
                if( mValidTweets.size() > pagIndex && mValidTweets.get(pagIndex) != null) {
                    mLoadTweets.add(mValidTweets.get(pagIndex));
                }
            }
            mTweetsAdpater.updateListData(mLoadTweets);
        }
    }

    private List<Tweet> getValidTweets(List<Tweet> lists ) {
        List<Tweet> validTweet = new ArrayList<Tweet>();
        Tweet tweet = null;
        for(int tweetIndex = 0; tweetIndex < lists.size(); tweetIndex++) {
            tweet =lists.get(tweetIndex);
            if(null == tweet.getSender()) {
                continue;
            } else if(null == tweet.getContent() && null == tweet.getImages()) {
                continue;
            }
            validTweet.add(tweet);
        }
        return validTweet;

    }
    @Override
    public void loadMore() {

        mPage++;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mIsAllLoad) {
                    mTweetsList.onLoadComplete();
                    return;
                } else {
                    initTweetData(mPage);
                    mTweetsList.onLoadComplete();
                    mTweetsAdpater.notifyDataSetChanged();
                }
            }
        }, 3000);
    }
    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPage = 0;
                mIsAllLoad = false;
                initTweetData(mPage);
                mTweetsList.onLoadComplete();
                mTweetsAdpater.notifyDataSetChanged();
                if(mSwip.isShown()){
                    mSwip.setRefreshing(false);
                }
            }
        }, 3000);
    }
}

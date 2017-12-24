package com.hometest.wechatapp.http;
/**
 * File name: HttpConnectionData.java
 * Author: liwenlu 
 * Description: use HttpURLConnection to get the data
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class HttpConnectionData {
	
	private static final String TAG = "HttpConnectionData";
	private URL mUrl;
	private HttpURLConnection mConn;
	public HttpConnectionData(String urlStr){
		Log.d(TAG,"HttpConnectionData urlStr = "+ urlStr);
		try {
			mUrl = new URL(urlStr);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getConnectionData (){
		String dataFromConn = null;
		InputStream inputStream = null;
		try {
			HttpURLConnection mConn = (HttpURLConnection) mUrl.openConnection();
			mConn.setRequestMethod("GET");
			mConn.setConnectTimeout(10*1000);
			int code = mConn.getResponseCode();
			Log.d(TAG,"lwl HttpConnectionData code = "+ code);
			if(code == 200) {
				inputStream = mConn.getInputStream();
				StringBuilder sb = new StringBuilder();
				String line;

				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = br.readLine()) != null) {
				    sb.append(line);
				}
				dataFromConn = sb.toString();
				Log.d(TAG,"lwl getConnectionData :"+dataFromConn);
			} else {
				Log.e(TAG, "Connecting error: "+code);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if(mConn != null){
            	mConn.disconnect();
            }
            if (inputStream != null) {
                try {
                	inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
		return dataFromConn;		
	}
}

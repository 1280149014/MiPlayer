package com.yong.miplayer.service;

import com.yong.miplayer.activity.MainActivity;
import com.yong.miplayer.constant.Def;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class PlayerService extends Service {
	private static String TAG = "PlayerService";
	// MediaPlayer实例
	private MediaPlayer mediaPlayer;
	// 当前正在播放歌曲的路径
	private String curMusicPath;
	// play states
	private byte playStatus = 0;

	private void _pause() {

	}

	private void _play() {

	}

	private void _play(int position) {

	}

	private void _play_next() {

	}

	private void _play_prev() {

	}

	private void _stop() {

	}

	private void init() {

	}

	private void updateMediaPlayer() {
		if (mediaPlayer != null) {
			mediaPlayer.reset();
			if (Def.isDebug)
				Log.e(TAG, "mediaPlayer.reset()...");
		}

		Uri uri = Uri.parse("file://" + curMusicPath);

		if (Def.isDebug)
			Log.e(TAG, curMusicPath);

		mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
//		mediaPlayer = MediaPlayer.create(MainActivity.this, uri);
		mediaPlayer.setLooping(false);
		mediaPlayer.setOnCompletionListener(onCompletionListener);
	}

	private MediaPlayer.OnCompletionListener onCompletionListener = new OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mp) {
			// 播放完了
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		if (Def.isDebug)
			System.out.println("TEST--->PlayerService.onBind()...");
		return null;
	}

	@Override
	public void onCreate() {
		// Service启动时会首先调用此函数，一般用于做一些初始化操作
		super.onCreate();
		if (Def.isDebug)
			System.out.println("TEST--->PlayerService.onCreate()...");
		
		init();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (Def.isDebug)
			System.out.println("TEST--->PlayerService.onDestroy()...");
	}

	@Override
	public void onRebind(Intent intent) {
		if (Def.isDebug)
			System.out.println("TEST--->PlayerService.onRebind()...");
		super.onRebind(intent);
	}

//	@Override
//	public int onStartCommand(Intent intent, int flags, int startId) {
//		if (Def.isDebug)
//			System.out.println("TEST--->PlayerService.onStartCommand()...");
//		return super.onStartCommand(intent, flags, startId);
//	}

	@Override
	public void onStart(Intent intent, int startId) {
//		super.onStart(intent, startId);
		if (Def.isDebug)
			System.out.println("TEST--->PlayerService.onStart()...");
		
		// get curMusicPath;
		updateMediaPlayer();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		if (Def.isDebug)
			System.out.println("TEST--->PlayerService.onUnbind()...");
		return super.onUnbind(intent);
	}

}

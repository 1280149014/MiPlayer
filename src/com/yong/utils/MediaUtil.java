package com.yong.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;
import android.provider.MediaStore.MediaColumns;
import android.widget.Toast;

import com.yong.miplayer.AudioInfo;

/**
 * @author Yong
 * */
public final class MediaUtil
{
	private static ContentResolver contentResolver;
	private static Cursor cursor;
	private static Context ctx;
	private static List<AudioInfo> audioInfos = new ArrayList<AudioInfo>();
	static AudioInfo audioInfo;

	/** 初始化 */
	public static void init(Context context)
	{
		ctx = context;
		getCursor();
	}

	/** 得到用于查询MediaStore中媒体信息的Cursor对象 */
	private static void getCursor()
	{
		contentResolver = ctx.getContentResolver();
		cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
				null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
	}

	public static void updateMediaStore()
	{
		// 先看看不更新Cursor还能不能查询，如果能，构造函数里就不查询了。
		// getCursor();
	}

	public static List<AudioInfo> updateAudioInfos()
	{
		updateMediaStore();
		if (cursor.moveToFirst())
		{
			do
			{
				audioInfo = new AudioInfo();
				// 获取歌曲名
				String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaColumns.TITLE));
				audioInfo.setTitle(title);

				// 获取歌手名
				String artist = cursor.getString(cursor.getColumnIndexOrThrow(AudioColumns.ARTIST));
				audioInfo.setArtist(artist);

				// 获取歌曲大小（单位：byte）
				int size = (int) cursor.getLong(cursor.getColumnIndexOrThrow(MediaColumns.SIZE));
				audioInfo.setSize(size);

				// 获取歌曲时长（单位：毫秒）
				int duration = cursor.getInt(cursor.getColumnIndexOrThrow(AudioColumns.DURATION));
				audioInfo.setDuration(duration);

				// 获取歌曲所属专辑
				String album = cursor.getString(cursor.getColumnIndexOrThrow(AudioColumns.ALBUM));
				audioInfo.setAlbum(album);

				// 获取歌曲所属专辑
				String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaColumns.DATA));
				audioInfo.setPath(path);

				// 将AudioInfo对象添加到AudioInfo对象数组里
				audioInfos.add(audioInfo);
				audioInfo = null;
			} while (cursor.moveToNext());
		} else
		{
			Toast.makeText(ctx, "未找到歌曲，请刷新！", Toast.LENGTH_LONG).show();
		}

		return audioInfos;
	}

}

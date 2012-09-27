package com.yong.utils;

import java.util.ArrayList;
import java.util.List;

import com.yong.musicplayer.AudioInfo;
import com.yong.musicplayer.Constants;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.widget.Toast;

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

	// private static String[] Mp3Name = new String[1];

	/** 初始化 */
	public static void init(Context context)
	{
		ctx = context;
		getCursor();
		// contentResolver = ctx.getContentResolver();
		// cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
		// null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

		// String[] fileList = FileUtil.getFileList(Constants.MUSIC_FILE);
		// for(int i = 0;i<fileList.length;i++)
		// {
		// if(fileList[i].endsWith(".mp3"))
		// {
		// System.out.println("TEST--->指定的音频文件："+fileList[i]);
		// // Mp3Name[0] = fileList[i];
		// break;
		// }
		// }
		// String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
		// String selection = MediaColumns.DATA + " = ?"; // like
		// cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, selection,
		// Mp3Name, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
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
		// getCursor(); // 先看看不更新Cursor还能不能查询，如果能，构造函数里就不查询了。
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
				String title = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
				audioInfo.setTitle(title);

				// 获取歌手名
				String artist = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
				audioInfo.setArtist(artist);

				// 获取歌曲大小（单位：byte）
				int size = (int) cursor.getLong(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
				audioInfo.setSize(size);

				// 获取歌曲时长（单位：毫秒）
				int duration = cursor.getInt(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
				audioInfo.setDuration(duration);

				// 获取歌曲所属专辑
				String album = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
				audioInfo.setAlbum(album);

				// 获取歌曲所属专辑
				String path = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
				audioInfo.setPath(path);

				// 将AudioInfo对象添加到AudioInfo对象数组里
				audioInfos.add(audioInfo);
				audioInfo = null;

			} while (cursor.moveToNext());
		} else
		{
			Toast.makeText(ctx, "未找到歌曲！", Toast.LENGTH_LONG).show();
		}

		return audioInfos;
	}

	// private String title;// 歌曲名称
	// private String artist;// 歌手
	// private int size;// 歌曲大小
	// private int duration;// 歌曲时长
	// private String album;// 歌曲专辑
	// public static void printAllMediaInfo()
	// {
	// // String tilte = cursor.getString(cursor
	// // .getColumnIndexOrThrow(MediaColumns.TITLE));
	// // System.out.println(tilte);
	//
	// if (cursor.moveToFirst())
	// {
	// do
	// {
	// String title = cursor.getString(cursor
	// .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
	// String artist = cursor.getString(cursor
	// .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
	// int size = (int) cursor.getLong(cursor
	// .getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
	// int duration = cursor.getInt(cursor
	// .getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
	// String album = cursor.getString(cursor
	// .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
	// if(Constants.isDebug)
	// {System.out.println("-------------------------");
	// System.out.println("title : " + title);
	// System.out.println("artist : " + artist);
	// System.out.println("size : " + size);
	// System.out.println("duration : " + duration);
	// System.out.println("album : " + album);
	// System.out.println("-------------------------");
	// }
	// } while (cursor.moveToNext());
	// } else
	// {
	// Toast.makeText(ctx, "未找到歌曲！", Toast.LENGTH_LONG).show();
	// }
	// }

	// public boolean GetMp3Info(String mpFullname)
	// {
	// try
	// {
	// mpFilename = "";
	// mpTitle = "";
	// mpArtist = "";
	// mpAlbum = "";
	// mpDuration = "";
	// mpSize = "0";
	// String selection = MediaStore.Audio.Media.DATA + " = ?"; // like
	// // String path="/mnt/sdcard/music";
	// String[] selectionArgs = { "/mnt" + mpFullname };
	// // String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
	// String[] projection = {
	// // MediaStore.Audio.Media._ID,
	// MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST,
	// MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.DURATION,
	// MediaStore.Audio.Media.SIZE
	// // MediaStore.Audio.Media.DATA, // --&gt; Location
	// // MediaStore.Audio.Media.DISPLAY_NAME,
	// };
	//
	// cursor = query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection,
	// selectionArgs, null);
	// // Cursor cursor2 = query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
	// mpTitle = cursor.getString(0).toString();
	// mpArtist = cursor.getString(1).toString();
	// mpAlbum = cursor.getString(2).toString();
	// mpDuration = cursor.getString(3).toString();
	// mpSize = cursor.getString(4).toString();
	// // cursor.getString(5).toString();
	// return true;
	// } catch (Exception e)
	// {
	// return false;
	// }
	// }
}

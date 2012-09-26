package com.yong.utils;

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
//	private static String[] Mp3Name = new String[1];

	public static void init(Context context)
	{
		ctx = context;
		contentResolver = ctx.getContentResolver();
		cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
				null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		
//		String[] fileList = FileUtil.getFileList(Constants.MUSIC_FILE);
//		for(int i = 0;i<fileList.length;i++)
//		{
//			if(fileList[i].endsWith(".mp3"))
//			{
//				System.out.println("TEST--->指定的音频文件："+fileList[i]);
////				Mp3Name[0] = fileList[i];
//				break;
//			}
//		}
//		String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
//		String selection = MediaColumns.DATA + " = ?"; // like
//		cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, selection,
//				Mp3Name, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
	}

	public static void printAllMediaInfo()
	{
//		String tilte = cursor.getString(cursor
//				.getColumnIndexOrThrow(MediaColumns.TITLE));
//		System.out.println(tilte);
		
		if (cursor.moveToFirst())
		{
			do
			{
				String tilte = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
				System.out.println(tilte);
			} while (cursor.moveToNext());
		} else
		{
			Toast.makeText(ctx, "未找到歌曲！", Toast.LENGTH_LONG).show();
		}
	}

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
//	 String selection = MediaStore.Audio.Media.DATA + " = ?"; // like
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

package com.yong.musicplayer;

import java.io.File;

import com.yong.utils.FileUtil;

import android.view.Menu;

public final class Constants
{
	public static final int MENU_UPDATE_ID = Menu.FIRST;
	public static final int MENU_ABOUT_ID = Menu.FIRST + 1;
	public static final int PLAY = 0;
	public static final int PAUSE = 1;
	public static final String MUSIC_FILE = FileUtil.getSDPath() + "Music" + File.separator;
}

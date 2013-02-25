package com.yong.miplayer.constant;

import java.io.File;

import com.yong.miplayer.util.FileUtils;

import android.view.Menu;

public final class Def
{

	public static final boolean isDebug = true;
	public static final int MENU_UPDATE_ID = Menu.FIRST;
	public static final int MENU_ABOUT_ID = Menu.FIRST + 1;
	public static final String MUSIC_FILE = FileUtils.getSDPath() + "Music" + File.separator;
}

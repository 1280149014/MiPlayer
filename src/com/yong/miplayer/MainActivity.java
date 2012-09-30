package com.yong.miplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.yong.constants.Def;
import com.yong.musicplayer.R;
import com.yong.musicplayer.R.drawable;
import com.yong.musicplayer.R.id;
import com.yong.musicplayer.R.layout;
import com.yong.musicplayer.R.menu;
import com.yong.utils.FileUtil;
import com.yong.utils.MediaUtil;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity
{
	private MediaPlayer mediaPlayer;
	/** 播放界面布局 */
	private LinearLayout linearLayout;
	// 歌曲列表按钮
	private ImageButton imgBtn_List;
	// 播放按钮
	private ImageButton imgBtn_Play;
	// 上一首按钮
	private ImageButton imgBtn_Previous;
	// 下一首按钮
	private ImageButton imgBtn_Next;
	// 播放状态
	private boolean isPlaying;
	private boolean isPause;
	private boolean isReleased;
	// 存放歌曲列表中的信息
	private ArrayList<HashMap<String, String>> musicList = new ArrayList<HashMap<String, String>>();
	// 存放了歌曲信息的歌曲信息对象
	private List<AudioInfo> audioInfos = new ArrayList<AudioInfo>();
	// 当前正在播放歌曲名称界面
	private TextView tv_curMusicTitle;
	// 当前正在播放歌曲歌手/专辑信息界面
	private TextView tv_curMusicArtist;
	// 当前正在播放歌曲名称
	private String curMusicTitle;
	// 当前正在播放歌曲歌手/专辑信息
	private String curMusicArtist;
	// 当前正在播放歌曲的路径
	private String curMusicPath;

	private void init()
	{
		// 播放界面布局对象
		// LayoutInflater inflater = LayoutInflater.from(this);
		linearLayout = (LinearLayout) findViewById(R.id.linearLayout_Play);
		tv_curMusicTitle = (TextView) findViewById(R.id.textView_curMusicTitle);
		tv_curMusicArtist = (TextView) findViewById(R.id.textView_curMusicArtist);
		// “播放”按钮
		imgBtn_Play = (ImageButton) findViewById(R.id.imgBtn_Play);
		imgBtn_Play.setOnClickListener(playBtn_listener);
		// “上一首”按钮
		imgBtn_Previous = (ImageButton) findViewById(R.id.imgBtn_Previous);
		imgBtn_Previous.setOnClickListener(previousBtn_listener);
		// “下一首”按钮
		imgBtn_Next = (ImageButton) findViewById(R.id.imgBtn_Next);
		imgBtn_Next.setOnClickListener(nextBtn_listener);
		// “播放界面/歌曲列表”按钮
		imgBtn_List = (ImageButton) findViewById(R.id.imgBtn_List);
		imgBtn_List.setOnClickListener(imgbtn_list_listener);
		// 初始化媒体信息
		MediaUtil.init(MainActivity.this);
		updateMusicList();
		setCurMusicInfo();
		updateCurMusicInfoView();
		updateMediaPlayer();

		if (Def.isDebug)
			System.out.println("TEST--->SD卡路径：" + FileUtil.getSDPath());
	}

	/** 设置当前播放歌曲 */
	private void setCurMusicInfo()
	{
		/**
		 * 当无法获取播放器退出时最后一次播放的歌曲并且媒体库歌曲列表不为空时：<br>
		 * 默认将歌曲列表中的第一首歌曲设置为当前播放歌曲
		 */
		if (!getLastMusicInfo() && !isMusicListNull())
		{
			curMusicTitle = audioInfos.get(0).getTitle();
			curMusicArtist = audioInfos.get(0).getArtist();
			curMusicPath = audioInfos.get(0).getPath();
		}
	}

	/**
	 * 歌曲列表是否为空
	 * 
	 * @return ture: 播放列表为空<br>
	 *         false: 播放列表非空
	 * */
	private boolean isMusicListNull()
	{
		return false;
	}

	/**
	 * 获取播放器退出时最后一次播放的歌曲的信息
	 * 
	 * @return true: 获取成功<br>
	 *         false: 获取失败
	 * */
	private boolean getLastMusicInfo()
	{
		return false;
	}

	/** 更新当前播放的歌曲的信息 */
	private void updateCurMusicInfo(int position)
	{
		curMusicTitle = audioInfos.get(position).getTitle();
		curMusicArtist = audioInfos.get(position).getArtist();
		curMusicPath = audioInfos.get(position).getPath();

		if (Def.isDebug)
		{
			System.out.println("TEST--->选择的歌曲信息：");
			System.out.println("TEST--->歌名：" + curMusicTitle);
			System.out.println("TEST--->歌手：" + curMusicArtist);
		}

		updateCurMusicInfoView();
	}

	/** 更新当前播放的歌曲的信息界面显示 */
	private void updateCurMusicInfoView()
	{
		tv_curMusicTitle.setText(curMusicTitle);
		// tv_curMusicTitle.setText("askjflksjdfl113242ljfdsofjwoef2");
		tv_curMusicArtist.setText(curMusicArtist);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 代码实现隐藏标题栏（必须放在setContentView之前）
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		if (Def.isDebug)
			System.out.println("TEST--->onCreate()...");

		init();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		if (Def.isDebug)
		{
			System.out.println("TEST--->id:" + id);
			System.out.println("TEST--->position:" + position);
		}
		updateCurMusicInfo(position);
		updateMediaPlayer();
		// mediaPlayer.reset();
		play();
		imgBtn_Play.setImageResource(R.drawable.pause);
	}

	/** 响应“播放/暂停”按钮按键 */
	private Button.OnClickListener playBtn_listener = new Button.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			// playState = (playState == Constants.PLAY) ? (Constants.PAUSE) : (Constants.PLAY);

			if (isPlaying)
			{// 播放状态：暂停按钮可见
				imgBtn_Play.setImageResource(R.drawable.play);
				pause();
				if (Def.isDebug)
					System.out.println("TEST--->暂停");
			} else
			{// 暂停状态：播放按钮可见
				imgBtn_Play.setImageResource(R.drawable.pause);
				play();
				if (Def.isDebug)
					System.out.println("TEST--->播放");
			}

			if (Def.isDebug)
				Toast.makeText(MainActivity.this, "Play按钮被点击了！", Toast.LENGTH_SHORT).show();
		}
	};

	private void updateMediaPlayer()
	{
		Uri uri = Uri.parse("file://" + curMusicPath);
		mediaPlayer = MediaPlayer.create(MainActivity.this, uri);
		mediaPlayer.setLooping(false);
		// if (isPlaying || isPause)
		// {
		// mediaPlayer.reset();
		// }
	}

	/** 播放 */
	private void play()
	{
		mediaPlayer.start();
		isPlaying = true;
		isPause = false;
	}

	/** 暂停 */
	private void pause()
	{
		if (!isReleased && !isPause)
		{
			mediaPlayer.pause();
			isPlaying = false;
			isPause = true;
		}
	}

	// 响应“上一首”按钮
	private Button.OnClickListener previousBtn_listener = new Button.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			// FileUtil.creatFolder("Photo");
			// FileUtil.creatFolder("Ebook");
			Toast.makeText(MainActivity.this, "Previous", Toast.LENGTH_SHORT).show();
		}
	};

	// 响应“下一首”按钮
	private Button.OnClickListener nextBtn_listener = new Button.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			// System.out.println("TEST--->" + FileUtil.isFolderExist("Music", ""));
			Toast.makeText(MainActivity.this, "Next", Toast.LENGTH_SHORT).show();
		}
	};

	// 响应“列表”按钮
	private Button.OnClickListener imgbtn_list_listener = new Button.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			if (linearLayout.getVisibility() == View.VISIBLE)
			{
				linearLayout.setVisibility(View.INVISIBLE);
				imgBtn_List.setImageResource(R.drawable.icon_play);
				if (Def.isDebug)
					Toast.makeText(MainActivity.this, "返回歌曲列表", Toast.LENGTH_SHORT).show();
			} else
			{
				linearLayout.setVisibility(View.VISIBLE);
				imgBtn_List.setImageResource(R.drawable.icon_list);
				if (Def.isDebug)
					Toast.makeText(MainActivity.this, "返回播放界面", Toast.LENGTH_SHORT).show();
			}
		}
	};

	/** MENU菜单 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/** 更新播放列表 */
	public void updateMusicList()
	{
//		audioInfos = new ArrayList<AudioInfo>();
		audioInfos = MediaUtil.updateAudioInfos();
		
		if(!musicList.isEmpty())
		{
			musicList.clear();
		}

		for (Iterator<AudioInfo> iterator = audioInfos.iterator(); iterator.hasNext();)
		{
			AudioInfo audioInfo = iterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("music_title", audioInfo.getTitle());
			map.put("music_artist", audioInfo.getArtist());
			musicList.add(map);

			if (Def.isDebug)
				System.out.println(audioInfo);
		}

		SimpleAdapter listAdapter = new SimpleAdapter(MainActivity.this, musicList,
				R.layout.list_item, new String[] { "music_title", "music_artist" }, new int[] {
						R.id.lstItem_MusicName, R.id.lstItem_ArtInfo });
		setListAdapter(listAdapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getOrder())
		{
		case Def.MENU_UPDATE_ID:
		{// 更新
			updateMusicList();
			if (Def.isDebug)
				Toast.makeText(MainActivity.this, "更新", Toast.LENGTH_SHORT).show();
			break;
		}
		case Def.MENU_ABOUT_ID:
		{// 关于
			if (Def.isDebug)
				Toast.makeText(MainActivity.this, "关于", Toast.LENGTH_SHORT).show();
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}
}

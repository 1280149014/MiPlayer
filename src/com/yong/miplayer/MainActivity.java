package com.yong.miplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.ListActivity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yong.constants.Def;
import com.yong.musicplayer.R;
import com.yong.utils.FileUtil;
import com.yong.utils.MediaUtil;

public class MainActivity extends ListActivity
{
	// MediaPlayer实例
	private MediaPlayer mediaPlayer;
	// 播放界面布局实例
	private RelativeLayout layoutPlay;
	// 歌曲列表按钮
	private ImageButton imgBtn_List;
	// 播放按钮
	private ImageButton imgBtn_Play;
	// 上一首按钮
	private ImageButton imgBtn_Prev;
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
	// 当前正在播放歌曲的名称
	private String curMusicTitle;
	// 当前正在播放歌曲的歌手/专辑信息
	private String curMusicArtist;
	// 当前正在播放歌曲的路径
	private String curMusicPath;
	// 当前正在播放歌曲在音乐列表中的位置
	private int curPosition;
	// 音乐列表实例
	private ListView lv_musicList;
	// 歌词列表实例
	private ListView lv_lrcList;

	private void init()
	{
		// 播放界面布局对象
		// LayoutInflater inflater = LayoutInflater.from(this);
		layoutPlay = (RelativeLayout) findViewById(R.id.layout_play);

		tv_curMusicTitle = (TextView) findViewById(R.id.textView_curMusicTitle);
		tv_curMusicArtist = (TextView) findViewById(R.id.textView_curMusicArtist);

		lv_musicList = this.getListView();
		lv_lrcList = (ListView) findViewById(R.id.lv_lrcList);

		// “播放”按钮
		imgBtn_Play = (ImageButton) findViewById(R.id.imgBtn_Play);
		imgBtn_Play.setOnClickListener(playBtn_listener);
		// “上一首”按钮
		imgBtn_Prev = (ImageButton) findViewById(R.id.imgBtn_Prev);
		imgBtn_Prev.setOnClickListener(previousBtn_listener);
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

	/** 响应音乐列表按下 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		if (Def.isDebug)
		{
			System.out.println("TEST--->onListItem position:" + position);
		}
		curPosition = position;
		play(curPosition);
	}

	/** 响应“播放/暂停”按钮按键 */
	private Button.OnClickListener playBtn_listener = new Button.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
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
		if (mediaPlayer != null)
		{
			mediaPlayer.reset();
			if (Def.isDebug)
				System.out.println("mediaPlayer.reset()...");
		}

		Uri uri = Uri.parse("file://" + curMusicPath);
		mediaPlayer = MediaPlayer.create(MainActivity.this, uri);
		mediaPlayer.setLooping(false);
		mediaPlayer.setOnCompletionListener(onCompletionListener);
	}
	
	/*
	 * 1、实现播放“上一首”、“下一首”歌曲功能
2、添加了MediaPlayer.OnCompletionListener监听器用来处理音乐播放完成后的操作（操作代码未实现，正在理逻辑）

2012-10-15 11:38:30 by Yong
	 * */

	private MediaPlayer.OnCompletionListener onCompletionListener = new OnCompletionListener()
	{

		@Override
		public void onCompletion(MediaPlayer mp)
		{
			// 播放完了
		}
	};

	/** 播放 */
	private void play()
	{
		mediaPlayer.start();
		isPlaying = true;
		isPause = false;
	}

	/** 根据歌曲所在列表位置播放 */
	private void play(int position)
	{
		updateCurMusicInfo(position);
		updateMediaPlayer();
		play();
		imgBtn_Play.setImageResource(R.drawable.pause);
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
			curPosition = curPosition-- > 0 ? curPosition : (audioInfos.size() - 1);
			play(curPosition);

			Toast.makeText(MainActivity.this, "上一首", Toast.LENGTH_SHORT).show();
		}
	};

	// 响应“下一首”按钮
	private Button.OnClickListener nextBtn_listener = new Button.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			curPosition = curPosition++ < (audioInfos.size() - 1) ? curPosition : 0;
			play(curPosition);

			Toast.makeText(MainActivity.this, "下一首", Toast.LENGTH_SHORT).show();
		}
	};

	// 响应“列表”按钮
	private Button.OnClickListener imgbtn_list_listener = new Button.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			if (lv_musicList.getVisibility() == View.VISIBLE)
			{
				layoutPlay.setVisibility(View.VISIBLE);
				lv_musicList.setVisibility(View.INVISIBLE);
				imgBtn_List.setImageResource(R.drawable.icon_play);
				if (Def.isDebug)
					Toast.makeText(MainActivity.this, "返回歌曲列表", Toast.LENGTH_SHORT).show();
			} else
			{
				layoutPlay.setVisibility(View.INVISIBLE);
				lv_musicList.setVisibility(View.VISIBLE);
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
		audioInfos = MediaUtil.updateAudioInfos();

		if (!musicList.isEmpty())
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
			lv_lrcList.setBackgroundColor(0xff0000);
			if (Def.isDebug)
				Toast.makeText(MainActivity.this, "关于", Toast.LENGTH_SHORT).show();
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}
}

package com.yong.musicplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
	MediaPlayer mediaPlayer;
	/** 播放界面布局 */
	LinearLayout linearLayout;
	// 歌曲列表按钮
	ImageButton imgBtn_List;
	// 播放按钮
	ImageButton imgBtn_Play;
	// 上一首按钮
	ImageButton imgBtn_Previous;
	// 下一首按钮
	ImageButton imgBtn_Next;
	// 播放状态
	private boolean isPlaying;
	private boolean isPause;
	private boolean isReleased;
	// 存放歌曲列表中的信息
	ArrayList<HashMap<String, String>> musicList = new ArrayList<HashMap<String, String>>();
	// 存放了歌曲信息的歌曲信息对象
	List<AudioInfo> audioInfos = new ArrayList<AudioInfo>();
	// 当前正在播放歌曲名称界面
	TextView tv_curMusicTitle;
	// 当前正在播放歌曲歌手/专辑信息界面
	TextView tv_curMusicArtist;
	// 当前正在播放歌曲名称
	String curMusicTitle;
	// 当前正在播放歌曲歌手/专辑信息
	String curMusicArtist;
	// 当前正在播放歌曲的路径
	String curMusicPath;

	// HashMap<String, String> map1 = new HashMap<String, String>();
	// HashMap<String, String> map2 = new HashMap<String, String>();
	// HashMap<String, String> map3 = new HashMap<String, String>();
	// HashMap<String, String> map4 = new HashMap<String, String>();
	// HashMap<String, String> map5 = new HashMap<String, String>();
	// HashMap<String, String> map6 = new HashMap<String, String>();
	// HashMap<String, String> map7 = new HashMap<String, String>();
	// HashMap<String, String> map8 = new HashMap<String, String>();
	// HashMap<String, String> map9 = new HashMap<String, String>();

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

		if (Constants.isDebug)
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

		if (Constants.isDebug)
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
		// requestWindowFeature(Window.FEATURE_NO_TITLE);// 代码实现隐藏标题栏（必须放在setContentView之前）
		setContentView(R.layout.activity_main);

		if (Constants.isDebug)
			System.out.println("TEST--->onCreate()...");

		init();

		// map1.put(from[0], "忘情水");
		// map1.put(from[1], "刘德华");
		// map2.put(from[0], "With him");
		// map2.put(from[1], "facebaby");
		// map3.put(from[0], "因为爱情");
		// map3.put(from[1], "王菲");
		// map4.put(from[0], "东风破");
		// map4.put(from[1], "周杰伦");
		// map5.put(from[0], "HelloWorld");
		// map5.put(from[1], "hello");
		// map6.put(from[0], "给我一首歌的时间");
		// map6.put(from[1], "周杰伦");
		// map7.put(from[0], "如果没有你");
		// map7.put(from[1], "李昊翰");
		// map8.put(from[0], "你把我灌醉");
		// map8.put(from[1], "张赫宣");
		// map9.put(from[0], "传奇");
		// map9.put(from[1], "王菲");

		// musicList.add(map1);
		// musicList.add(map2);
		// musicList.add(map3);
		// musicList.add(map4);
		// musicList.add(map5);
		// musicList.add(map6);
		// musicList.add(map7);
		// musicList.add(map8);
		// musicList.add(map9);

		// SimpleAdapter listAdapter = new SimpleAdapter(MainActivity.this, musicList,
		// R.layout.list_item_main, from, to);
		// setListAdapter(listAdapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		if (Constants.isDebug)
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
				if (Constants.isDebug)
					System.out.println("TEST--->暂停");
			} else
			{// 暂停状态：播放按钮可见
				imgBtn_Play.setImageResource(R.drawable.pause);
				play();
				if (Constants.isDebug)
					System.out.println("TEST--->播放");
			}

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
			Toast.makeText(MainActivity.this, "Previous按钮被点击了！", Toast.LENGTH_SHORT).show();
		}
	};

	// 响应“下一首”按钮
	private Button.OnClickListener nextBtn_listener = new Button.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			// System.out.println("TEST--->" + FileUtil.isFolderExist("Music", ""));
			Toast.makeText(MainActivity.this, "Next按钮被点击了！", Toast.LENGTH_SHORT).show();
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
				// String[] file_list = FileTools.getFileList(Constants.MUSIC_FILE);
				// for (int i = 0; i < file_list.length; i++)
				// {
				// System.out.println(file_list[i]);
				// }

				// MediaUtil.printAllMediaInfo();

				linearLayout.setVisibility(View.INVISIBLE);
				imgBtn_List.setImageResource(R.drawable.icon_play);
				Toast.makeText(MainActivity.this, "返回歌曲列表", Toast.LENGTH_SHORT).show();
			} else
			{
				linearLayout.setVisibility(View.VISIBLE);
				imgBtn_List.setImageResource(R.drawable.icon_list);
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

	// private final String[] from = new String[] { "music_title", "music_artist" };
	// private final int[] to = new int[] { R.id.lstItem_MusicName, R.id.lstItem_ArtInfo };

	/** 更新播放列表 */
	public void updateMusicList()
	{
		audioInfos = new ArrayList<AudioInfo>();
		audioInfos = MediaUtil.updateAudioInfos();

		for (Iterator<AudioInfo> iterator = audioInfos.iterator(); iterator.hasNext();)
		{
			AudioInfo audioInfo = (AudioInfo) iterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("music_title", audioInfo.getTitle());
			map.put("music_artist", audioInfo.getArtist());
			musicList.add(map);

			if (Constants.isDebug)
				System.out.println(audioInfo);
		}

		SimpleAdapter listAdapter = new SimpleAdapter(MainActivity.this, musicList,
				R.layout.list_item_main, new String[] { "music_title", "music_artist" }, new int[] {
						R.id.lstItem_MusicName, R.id.lstItem_ArtInfo });
		setListAdapter(listAdapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getOrder())
		{
		case Constants.MENU_UPDATE_ID:
		{// 更新
			updateMusicList();
			Toast.makeText(MainActivity.this, "选择了“更新”！", Toast.LENGTH_SHORT).show();
			break;
		}
		case Constants.MENU_ABOUT_ID:
		{// 关于
			Toast.makeText(MainActivity.this, "选择了“关于”！", Toast.LENGTH_SHORT).show();
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}
}

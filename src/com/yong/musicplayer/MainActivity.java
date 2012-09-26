package com.yong.musicplayer;

import java.util.ArrayList;
import java.util.HashMap;

import com.yong.utils.FileUtil;
import com.yong.utils.MediaUtil;

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
	TextView textView;
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
	private int playState = Constants.PAUSE;
	ArrayList<HashMap<String, String>> musicList = new ArrayList<HashMap<String, String>>();

	HashMap<String, String> map1 = new HashMap<String, String>();
	HashMap<String, String> map2 = new HashMap<String, String>();
	HashMap<String, String> map3 = new HashMap<String, String>();
	HashMap<String, String> map4 = new HashMap<String, String>();
	HashMap<String, String> map5 = new HashMap<String, String>();
	HashMap<String, String> map6 = new HashMap<String, String>();
	HashMap<String, String> map7 = new HashMap<String, String>();
	HashMap<String, String> map8 = new HashMap<String, String>();
	HashMap<String, String> map9 = new HashMap<String, String>();

	String[] from = new String[] { "music_name", "art_info" };
	int[] to = new int[] { R.id.lstItem_MusicName, R.id.lstItem_ArtInfo };

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);// 代码实现隐藏标题栏（必须放在setContentView之前）
		setContentView(R.layout.activity_main);

		System.out.println("TEST--->onCreate()...");
		System.out.println("TEST--->" + FileUtil.getSDPath());

		MediaUtil.init(MainActivity.this);

		// 播放界面
		// LayoutInflater inflater = LayoutInflater.from(this);
		linearLayout = (LinearLayout) findViewById(R.id.linearLayout_Play);
		// textView = (TextView) findViewById(R.id.textView_Test);

		imgBtn_Play = (ImageButton) findViewById(R.id.imgBtn_Play);
		imgBtn_Play.setOnClickListener(imgbtn_play_listener);

		imgBtn_Previous = (ImageButton) findViewById(R.id.imgBtn_Previous);
		imgBtn_Previous.setOnClickListener(imgbtn_previous_listener);

		imgBtn_Next = (ImageButton) findViewById(R.id.imgBtn_Next);
		imgBtn_Next.setOnClickListener(imgbtn_next_listener);

		imgBtn_List = (ImageButton) findViewById(R.id.imgBtn_List);
		imgBtn_List.setOnClickListener(imgbtn_list_listener);

		map1.put(from[0], "忘情水");
		map1.put(from[1], "刘德华");
		map2.put(from[0], "With him");
		map2.put(from[1], "facebaby");
		map3.put(from[0], "因为爱情");
		map3.put(from[1], "王菲");
		map4.put(from[0], "东风破");
		map4.put(from[1], "周杰伦");
		map5.put(from[0], "HelloWorld");
		map5.put(from[1], "hello");
		map6.put(from[0], "给我一首歌的时间");
		map6.put(from[1], "周杰伦");
		map7.put(from[0], "如果没有你");
		map7.put(from[1], "李昊翰");
		map8.put(from[0], "你把我灌醉");
		map8.put(from[1], "张赫宣");
		map9.put(from[0], "传奇");
		map9.put(from[1], "王菲");

		musicList.add(map1);
		musicList.add(map2);
		musicList.add(map3);
		musicList.add(map4);
		musicList.add(map5);
		musicList.add(map6);
		musicList.add(map7);
		musicList.add(map8);
		musicList.add(map9);

		SimpleAdapter listAdapter = new SimpleAdapter(MainActivity.this, musicList,
				R.layout.list_item_main, from, to);
		setListAdapter(listAdapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		System.out.println("TEST--->id:" + id);
		System.out.println("TEST--->position:" + position);

		System.out.println("TEST--->选择的歌曲信息：" + musicList.get(position).get(from[0]));
		System.out.println("TEST--->歌名：" + musicList.get(position).get(from[0]));
		System.out.println("TEST--->歌手：" + musicList.get(position).get(from[1]));
	}

	// 响应“播放/暂停”按钮按键
	private Button.OnClickListener imgbtn_play_listener = new Button.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			playState = (playState == Constants.PLAY) ? (Constants.PAUSE) : (Constants.PLAY);
			if (playState == Constants.PLAY)
			{// 播放状态下暂停按钮可见
				imgBtn_Play.setImageResource(R.drawable.pause);
			} else
			{// 暂停状态下播放按钮可见
				imgBtn_Play.setImageResource(R.drawable.play);
			}

			Toast.makeText(MainActivity.this, "Play按钮被点击了！", Toast.LENGTH_SHORT).show();
		}
	};

	// 响应“上一首”按钮
	private Button.OnClickListener imgbtn_previous_listener = new Button.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			FileUtil.creatFolder("Photo");
			FileUtil.creatFolder("Ebook");
			Toast.makeText(MainActivity.this, "Previous按钮被点击了！", Toast.LENGTH_SHORT).show();
		}
	};

	// 响应“下一首”按钮
	private Button.OnClickListener imgbtn_next_listener = new Button.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			System.out.println("TEST--->" + FileUtil.isFolderExist("Music", ""));
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

				MediaUtil.printAllMediaInfo();

				linearLayout.setVisibility(View.INVISIBLE);
				imgBtn_List.setImageResource(R.drawable.icon_play);
				// Toast.makeText(MainActivity.this, "List按钮被点击了！", Toast.LENGTH_SHORT).show();
			} else
			{
				linearLayout.setVisibility(View.VISIBLE);
				imgBtn_List.setImageResource(R.drawable.icon_list);
				Toast.makeText(MainActivity.this, "“返回播放界面”按钮被点击了！", Toast.LENGTH_SHORT).show();
			}
		}
	};

	/** 菜单键菜单 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getOrder())
		{
		case Constants.MENU_UPDATE_ID:
		{// 更新
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

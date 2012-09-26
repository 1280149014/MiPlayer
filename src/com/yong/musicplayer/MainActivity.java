package com.yong.musicplayer;

import com.yong.utils.FileUtil;
import com.yong.utils.MediaUtil;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
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

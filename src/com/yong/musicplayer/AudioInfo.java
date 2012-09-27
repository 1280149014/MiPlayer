package com.yong.musicplayer;

/**
 * 用于存放歌曲信息的实体类
 * */
public class AudioInfo
{
	// 歌曲名称
	private String title;
	// 歌手
	private String artist;
	// 歌曲大小
	private int size;
	// 歌曲时长
	private int duration;
	// 歌曲专辑
	private String album;
	// 歌曲文件路径
	private String path;

	/**
	 * @return the path
	 */
	public String getPath()
	{
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path)
	{
		this.path = path;
	}

	public AudioInfo()
	{
		super();
	}

	public AudioInfo(String title, String artist, int size, int duration, String album, String path)
	{
		super();
		this.title = title;
		this.artist = artist;
		this.size = size;
		this.duration = duration;
		this.album = album;
		this.path = path;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return the artist
	 */
	public String getArtist()
	{
		return artist;
	}

	/**
	 * @param artist
	 *            the artist to set
	 */
	public void setArtist(String artist)
	{
		this.artist = artist;
	}

	/**
	 * @return the size
	 */
	public int getSize()
	{
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(int size)
	{
		this.size = size;
	}

	/**
	 * @return the duration
	 */
	public int getDuration()
	{
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	/**
	 * @return the album
	 */
	public String getAlbum()
	{
		return album;
	}

	/**
	 * @param album
	 *            the album to set
	 */
	public void setAlbum(String album)
	{
		this.album = album;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "AudioInfo [title=" + title + ", artist=" + artist + ", size=" + size
				+ ", duration=" + duration + ", album=" + album + ", path=" + path + "]";
	}

}

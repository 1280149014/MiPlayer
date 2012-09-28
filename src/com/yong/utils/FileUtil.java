package com.yong.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.yong.constants.Def;

import android.os.Environment;

public final class FileUtil
{
	private static String SDCardRoot;

	public static String getSDPath()
	{
		SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
		if (Def.isDebug)
			System.out.println("TEST--->getSDCardAbsolutePath() : " + SDCardRoot);
		return SDCardRoot;
	}

	/**
	 * 在SD卡上创建文件
	 * 
	 * @throws IOException
	 */
	public static File creatFile(String fileName, String filePath) throws IOException
	{
		String path = SDCardRoot + filePath + File.separator + fileName;
		File file = new File(path);
		file.createNewFile();
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 */
	public static File creatFolder(String folderName)
	{
		String path = SDCardRoot + folderName + File.separator;
		File file = new File(path);
		file.mkdirs();
		return file;
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 */
	public static boolean isFolderExist(String folderName, String filePath)
	{
		String path = SDCardRoot + filePath + File.separator + folderName;
		if (Def.isDebug)
			System.out.println("TEST--->theFilePath : " + path);
		File file = new File(path);
		if (Def.isDebug)
			System.out.println("TEST--->getFilePath : " + file.getPath());
		return file.exists();
	}

	public static String[] getFileList(String filePath)
	{
		String[] file_list;
		File file = new File(filePath);
		file_list = file.list();
		return file_list;
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 */
	public static File write2SDFromInput(String path, String fileName, InputStream input)
	{
		File file = null;
		OutputStream output = null;
		try
		{
			creatFolder(path);
			file = creatFile(fileName, path);
			output = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			int temp;
			while ((temp = input.read(buffer)) != -1)
			{
				output.write(buffer, 0, temp);
			}
			output.flush();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				output.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return file;
	}

}
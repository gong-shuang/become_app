package com.gs.learn.media.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.gs.learn.media.bean.LrcContent;

import android.util.Log;

public class LyricsLoader {
	private static final String TAG = "LyricsLoader";
	private static ArrayList<LrcContent> mLrcList;
	private static LyricsLoader mLoader;
	private static int mOffset = 0;
	
	public static LyricsLoader getInstance(String path) {
		if(mLoader == null){
			mLoader = new LyricsLoader();
		}
		readLRC(path);
		return mLoader;
	}

	private static void readLRC(String path) {
		mOffset = 0;
		mLrcList = new ArrayList<LrcContent>();
		String extendName = "." + Utils.getExtendName(path);
		File f = new File(path.replace(extendName, ".lrc"));
		try {
			//创建一个文件输入流对象
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader isr = new InputStreamReader(fis, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while((s = br.readLine()) != null) {
				s = s.replace("[", "");
				s = s.replace("]", "@");
				if (s.indexOf("0")!=0) {
					continue;
				}

				String[] splitData = s.split("@");
				if (splitData.length>0 && splitData[0].indexOf("offset")>=0) {
					String[] splitOffset = splitData[0].split(":");
					if (splitOffset.length > 1) {
						mOffset = Integer.parseInt(splitOffset[1]);
						Log.d(TAG, "offset="+mOffset);
					}
				}
				for (int i = 0; i < splitData.length - 1; i++) {
					LrcContent lrcContent = new LrcContent();
					int lrcTime = time2Str(splitData[i]) + mOffset;
					lrcContent.setLrcTime(lrcTime);
					lrcContent.setLrcStr(splitData[splitData.length - 1]);
					mLrcList.add(lrcContent);
				}
			}
			Collections.sort(mLrcList, new Comparator<LrcContent>() {
				@Override
				public int compare(LrcContent o1, LrcContent o2) {
					return (o1.getLrcTime()>o2.getLrcTime())?1:-1;
				}
			});
			for (int i = 0; i < mLrcList.size(); i++) {
				LrcContent item = mLrcList.get(i);
				Log.d(TAG, "i="+i+",time="+item.getLrcTime()+",str="+item.getLrcStr());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int time2Str(String timeStr) {
		timeStr = timeStr.replace(":", ".");
		timeStr = timeStr.replace(".", "@");
		// 将时间分隔成字符串数组
		String timeData[] = timeStr.split("@");
		// 分离出分、秒并转换为整型
		int minute = Integer.parseInt(timeData[0]);
		int second = Integer.parseInt(timeData[1]);
		int millisecond = Integer.parseInt(timeData[2]);
		// 计算上一行与下一行的时间转换为毫秒数
		int currentTime = (minute * 60 + second) * 1000 + millisecond * 10;
		return currentTime;
	}
	
	public ArrayList<LrcContent> getLrcList() {
		return mLrcList;
	}
}

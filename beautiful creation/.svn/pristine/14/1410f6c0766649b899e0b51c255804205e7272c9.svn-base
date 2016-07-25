package com.example.zuimeichuangyi.db;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2016/4/30.
 * 保存授权登录的基本信息
 * @author mgc
 *
 */
public class SharedPreferencesHelp {

	private SharedPreferences sharedPreferences;

	private static final String FILE_NAME = "beautyIdea";

	public SharedPreferencesHelp(Context context) {

		sharedPreferences = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
	}

	// 保存登录信息
	public boolean save(String nickname, String imgUrl) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("nickname", nickname);
		editor.putString("imgUrl", imgUrl);
		return editor.commit();
	}

	// 获取登录信息
	public List<String> get() {
		String nickname = sharedPreferences.getString("nickname", "最美创意");
		String imgUrl = sharedPreferences.getString("imgUrl", "");
		List<String> list = new ArrayList<String>();
		list.add(nickname);
		list.add(imgUrl);
		return list;
	}
	
	//是否登录
	public boolean setLogin(boolean flag){
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean("isLogin", flag);
		return editor.commit();
	}
	public boolean getLogin(){
		boolean flag = sharedPreferences.getBoolean("isLogin", false);
		return flag;
	}
}

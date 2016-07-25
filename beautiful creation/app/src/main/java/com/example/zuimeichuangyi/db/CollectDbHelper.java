package com.example.zuimeichuangyi.db;

import android.util.Log;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

public class CollectDbHelper {

	public static List<Collect> list = new ArrayList<Collect>();
	public static boolean add(DbUtils dbUtils, String reId,
			String collecttime, String jsonString, String albumId) {
		boolean flag = false;
		list.clear();
		Collect collect = new Collect(1,reId, collecttime, jsonString, albumId);
		flag = list.add(collect);
		if (flag) {
			try {
				dbUtils.saveAll(list);
			} catch (com.lidroid.xutils.exception.DbException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	public static boolean delete(DbUtils dbUtils, String rsId)
	{
		boolean flag = false;
		//dbUtils.findAll(Selector.from(Collect.class).where("rsId", "=", rsId));
		try {
			dbUtils.delete(Collect.class, WhereBuilder.b("rsId", "=", rsId));
			flag = true;
		} catch (DbException e) {
			// TODO: 2016/5/1
			e.printStackTrace();
		}
		return flag;
	}
	
	public static List<Collect> query(DbUtils dbUtils)
	{
		List<Collect> list_collect = new ArrayList<Collect>();
		try {
			//list_collect = dbUtils.findAll(Selector.from(Collect.class));
			list_collect = dbUtils.findAll(Selector.from(Collect.class));
		} catch (com.lidroid.xutils.exception.DbException e) {
			e.printStackTrace();
		}
		return list_collect;
	}
	
	public static boolean queryOne(DbUtils dbUtils,String rsId) throws com.lidroid.xutils.exception.DbException {
		List<Collect> list_coll = new ArrayList<Collect>();
		boolean flag = false;
		list_coll = dbUtils.findAll(Selector.from(Collect.class).where("rsId", "=", rsId));
		//list_coll.add((Collect) dbUtils.findAll(Selector.from(Collect.class).where("rsId", "=", rsId))) ;
		if (list_coll!=null&&list_coll.size()>0) {
            flag = true;
        }
		Log.e("MainActivity", flag+"");
		return flag;
	}
}

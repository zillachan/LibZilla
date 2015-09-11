/*
Copyright 2015 Zilla Chen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.zilla.android.zillacore.libzilla.db.util;

import java.util.ArrayList;

/**
 * 表信息缓存记录
 * @author zilla
 *
 */
public class TableHolder {

	public static ArrayList<String> tableList = new ArrayList<String>();
	
	/**
	 * 添加表信息到缓存中
	 * @param tableName tableName
	 */
	public static void addTable(String tableName){
		if(tableList.contains(tableName)){
			return;
		}
		tableList.add(tableName);
	}
	
	/**
	 * 判断数据库中是否存在该表
	 * @param tableName tableName
	 * @return if the tableName exist
	 */
	public static boolean isTableExist(String tableName){
		return tableList.contains(tableName);
	}
}

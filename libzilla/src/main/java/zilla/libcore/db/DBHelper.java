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
package zilla.libcore.db;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import com.github.snowdream.android.util.Log;
import zilla.libcore.file.FileHelper;

import java.util.ArrayList;

/**
 * Basic class to deal db's create and upgrade.
 * <br>
 * 数据库基础类，处理数据库的创建和升级
 *
 * @author ze.chen
 */
public final class DBHelper extends SQLiteOpenHelper {

    private static DBHelper helper = null;
    private DBUpgradeListener dbUpgradeListener;

    private DBHelper(Context context, String name, CursorFactory factory,
                     int version) {
        super(context, name, factory, version);
    }

    public static DBHelper getInstance() {
        return helper;
    }

    /**
     * init db
     * <br>
     * 系统初始化时构造
     *
     * @param app application
     * @param dbName dbName from system.properties file
     * @param version version of the db from system.properties file
     */
    public static void init(Application app, String dbName, int version) {
        helper = new DBHelper(app, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (dbUpgradeListener != null) {
            dbUpgradeListener.onCreate(db);
        }
    }

    /**
     * whe upgrade ,you can add your upgrade sql in upgrade.sql file.
     * <br>
     * 数据库升级时，根据配置文件中的配置信息进行升级，可以涉及到添加字段,具体升级配置在res/raw/system.properties文件中定义
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        executeLocalSQL(db);
        if (dbUpgradeListener != null) {
            dbUpgradeListener.onUpgrade(db, oldVersion, newVersion);
        }
    }

    public DBUpgradeListener getDbUpgradeListener() {
        return dbUpgradeListener;
    }

    public void setDbUpgradeListener(DBUpgradeListener dbUpgradeListener) {
        this.dbUpgradeListener = dbUpgradeListener;
    }

    /**
     * an interfance for create and upgrade callback
     * <br>
     * 数据库初始化和升级回调接口
     */
    public interface DBUpgradeListener {
        void onCreate(SQLiteDatabase db);

        void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    }

    /**
     * execute local sql;upgrade.sql
     * <br>
     * 执行本地SQL语句
     * @param db the SQLiteDatabase
     */
    private void executeLocalSQL(SQLiteDatabase db) {
        ArrayList<String> sqlList = FileHelper.getUpgradeSqls();
        for (int i = 0, l = sqlList.size(); i < l; i++) {
            try {
                db.execSQL(sqlList.get(i));
            } catch (Exception e) {
                Log.e(e.getMessage());
            }
        }
    }
}

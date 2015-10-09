###This is an Android library for fast develope.
##Application
```
1.You need an Application,and onCreate(),you need call 

new Zilla().setCallBack(this).initSystem(this);

for example:

public class ZillaApplication extends Application implements Zilla.InitCallback, DBHelper.DBUpgradeListener {
    @Override
    public void onCreate() {
        super.onCreate();
        new Zilla().setCallBack(this).initSystem(this);
    }

    /**
     * init
     *
     * @param context Context
     */
    @Override
    public void onInit(Context context) {
        initApi();
        DBHelper.getInstance().setDbUpgradeListener(this);
    }

    /**
     * Config API info
     */
    private void initApi() {
        ZillaApi.NormalRestAdapter = ZillaApi.getRESTAdapter(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade requestFacade) {
            }
        });
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("onCreate(SQLiteDatabase db)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("onUpgrade(SQLiteDatabase db)");
    }
}

```
##DB
####Config
1. assets/config/system.properties
```
dbversion:if upgrade the database,dbversion+1.
dbname:the name of database.
```
2. assets/config/upgrade.sql
```
upgrade sql by hand
```
3. upgrade
```
if you change po Model, the table will change automatic.
```

####@Id
```
@Id annotation
Each po need an Id annotation,otherwise it will throw a RuntimeException.

@Table("t_user")
public class User implements Serializable {

    @Id
    private int id;

    private String name;

    private String email;

    private String address;

    public User() {

    }
    getter
    setter
}

By default if the type of the Id is Integer,it will AUTOINCREMENT.
```
####@Table
```
@Table annotation
By default,Each po can create a table by it's className,if you add @Table annotion on a class,the table name will be the name you give.

@Table("t_user")
public class User implements Serializable {
	...
	...
}
    
```
###DBOperator
```
A Singleton,It provide the CURD operations for datebase.

//Delete all rows
DBOperator.getInstance().deleteAll(User.class);

//save
User user = new User();
user.setName("user1");
user.setEmail("user1@example.com");
user.setAddress("user1 address");
DBOperator.getInstance().save(user);

//save list
List<User> userList = new ArrayList<User>();
for (int i = 0; i < 1000; i++) {
    User u = new User();
    u.setName("name" + i);
    u.setEmail("name" + i + "@example.com");
    u.setAddress("address" + i);
    userList.add(u);
}
DBOperator.getInstance().saveList(userList);

//Query
User user1 = DBOperator.getInstance().query(User.class,"address = ?",new String[]{"address1"});
Log.i("user1:"+user1.toString());

DBOperator.getInstance().update(user1);

//query all rows
List<User> users = DBOperator.getInstance().queryAll(User.class);
for (User u : users) {
    Log.i(u.toString());
}
    
```

##File
###AddressManager
    
```
Read file assets/config/address.properties,It will be called automatic by ZillaApi    
```

###FileHelper
Some static functons

1. createFile
2. deleteFile
3. copyFile
4. saveFile
5. formateFileSize
6. getRealPath by uri


###PropertiesManager
```
Read file assets/config/system.properties,you can also add your own property in system.properties,and read it by PropertiesManager.
```

###SharedPreferenceService

```
The packaging of SharedPreference,you only need call put or get
```

##LifeCircle
LifeCircle Manager,It provide the life circle manager for any object implements the ILifeCircle interface.

```
You want control an loading dialog when activity onCreate,init the dialog;whe the activity call onDestroy,dismiss the dialog.

public class MainActivity extends Activity {

    @LifeCircleInject
    public LoadingDialog loadingDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        	...
            LifeCircle.onCreate(this);
            ...
        }
        ...
        @Override
    protected void onDestroy() {
        super.onDestroy();
        LifeCircle.onDestory(this);
    }
}


And then you can call loadingDialog.show() or loadingDialog.dismiss() anywhere.


LoadingDialog.java
public class LoadingDialog implements ILifeCircle,IDialog {

    private Context context;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    private LoadingDialogEntity dialogEntity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        dialogEntity = new LoadingDialogEntity(context, R.style.CustomProgressDialog);
        dialogEntity.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onDestroy() {
        dismiss();
    }

    /**
     * 显示dialog
     */
    @Override
    public void show() {
        try {
            if (dialogEntity != null) {
                dialogEntity.show();
            }
        } catch (Exception e) {
            Log.e(e.getMessage());
        }
    }

    @Override
    public void show(String title) {
        try {
            if (dialogEntity != null) {
                dialogEntity.setTitle(title);
                dialogEntity.show();
            }
        } catch (Exception e) {
            Log.e(e.getMessage());
        }
    }

    /**
     * 隐藏dialog
     */
    @Override
    public void dismiss() {
        try {
            if (dialogEntity != null) {
                dialogEntity.dismiss();
            }
        } catch (Exception e) {
            Log.e(e.getMessage());
        }
    }

}
```

##ZillaApi
A packaging of retrofit.

##ZillaAdapter
This provide a very effictive function for AdapterView(ListView,ExpandableView,GridView)

```
When you use ListView,you need only 3 things
1.Create your listView item Model
2.Create your listView item viewholder
3.Create your adapter by ZillaAdapter
4.set listview's adapter

protected void initDatas() {
        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setName("User" + i);
            user.setEmail("user" + i + "@example.com");
            user.setAddress("address" + i);
            userList.add(user);
        }
        adapter = new ZillaAdapter<User>(this, userList, R.layout.user_item, ViewHolder.class);
        listView.setAdapter(adapter);
    }
    
Your viewholder:the name must be same as the model.

static class ViewHolder {
        @InjectView(R.id.name)
        TextView name;
        @InjectView(R.id.email)
        TextView email;
        @InjectView(R.id.address)
        TextView address;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
}
    
```
This can also support checkbox,button event,linearlayout hide/visiable,image,ratingbar.

##Others
###BusProvider
If you use otto before,then you can understand it easy.

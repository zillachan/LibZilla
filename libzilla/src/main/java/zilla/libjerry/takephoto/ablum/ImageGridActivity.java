package zilla.libjerry.takephoto.ablum;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import zilla.libcore.R;

public class ImageGridActivity extends AppCompatActivity {

    private GridView gv_album;
    private Toolbar toolbar;
    private TextView tv_completed;
    private LinearLayout choose;
    private TextView tv_tip;

    AlbumHelper helper;
    List<ImageItem> dataList=new ArrayList<>();
    ImageAdapter adapter;
    private List<ImageBucket> albumList;
    //存放已经被选择了的图像
    HashMap<String,ImageItem> selectedItem=new HashMap<>();
    int selectCount=0;
    int limit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid);
        limit=getIntent().getIntExtra("limit",0);
        gv_album= (GridView) findViewById(R.id.gv_album);
        tv_completed= (TextView) findViewById(R.id.completed);
        choose= (LinearLayout) findViewById(R.id.choose);
        tv_tip= (TextView) findViewById(R.id.tip);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseAlbum();
            }
        });
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(0xFF393A3E);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        helper=new AlbumHelper(getApplicationContext());
        albumList=helper.getImagesBucketList(false);
        if(!albumList.isEmpty()){
            dataList.clear();
            albumList.get(0).isSelected=true;
            dataList.addAll(albumList.get(0).imageList);
        }
        tv_completed.setEnabled(false);
        adapter=new ImageAdapter(this,dataList);
        gv_album.setAdapter(adapter);
        gv_album.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageItem item=dataList.get(i);
                if(item.isSelected){
                    selectCount--;
                    item.isSelected=false;
                    selectedItem.remove(item.imageId);
                }else {
                    selectCount++;
                    if(selectCount>limit){
                        selectCount--;
                        Toast.makeText(ImageGridActivity.this,"最多选择"+limit+"张图片", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    item.isSelected=true;
                    selectedItem.put(item.imageId,item);
                }
                if(selectCount==0){
                    tv_completed.setEnabled(false);
                    tv_completed.setText("完成");
                }else {
                    tv_completed.setEnabled(true);
                    tv_completed.setText("完成(");
                    tv_completed.append(selectCount+"/"+limit+")");
                }

                adapter.notifyDataSetChanged();
            }
        });
        tv_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=getIntent();
                ArrayList<String> imgs=new ArrayList<>();
                Iterator<Map.Entry<String,ImageItem>> itr=selectedItem.entrySet().iterator();
                while (itr.hasNext()){
                    Map.Entry<String,ImageItem> entry=itr.next();
                    imgs.add(entry.getValue().imagePath);
                }
                intent.putStringArrayListExtra("imgs",imgs);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    BottomSheetDialog dialog;
    private void chooseAlbum() {
        final ListView lv = new ListView(this);
        final ImageBucketAdapter adapter=new ImageBucketAdapter(this,albumList);
        lv.setAdapter(adapter);
        lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (lv.getFirstVisiblePosition() != 0) {
                            lv.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ImageBucket bucket=albumList.get(i);
                if(!bucket.isSelected){
                    bucket.isSelected=true;
                    dataList.clear();
                    dataList.addAll(bucket.imageList);
                    ImageGridActivity.this.adapter.notifyDataSetChanged();
                    tv_tip.setText(bucket.bucketName);
                    for (int j=0;j<albumList.size();j++){
                        if(j!=i){
                            albumList.get(j).isSelected=false;
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                dialog.dismiss();

            }
        });
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(lv);
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialog = null;
            }
        });
    }
}

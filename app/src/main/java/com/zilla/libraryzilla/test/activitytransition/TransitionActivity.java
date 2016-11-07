package com.zilla.libraryzilla.test.activitytransition;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.github.snowdream.android.util.Log;
import com.zilla.libraryzilla.R;
import com.zilla.libraryzilla.ZillaApplication;
import com.zilla.libraryzilla.common.BaseActivity;

import butterknife.BindView;
import zilla.libcore.ui.InjectLayout;
import zilla.libjerry.activitytransition.transition.ActivityTransitionSender;
import zilla.libjerry.permission.MPermission;
import zilla.libjerry.permission.PermissionFail;
import zilla.libjerry.permission.PermissionOK;

@InjectLayout(R.layout.activity_transition)
public class TransitionActivity extends BaseActivity {

    @BindView(R.id.image1)
    ImageView imageView;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("dsadadada");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MPermission.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    @PermissionOK
    private void valdateSuccess(){
        Log.i("拍照权限通过啦");

    }

    @PermissionFail
    public void validateFail(){
        Log.i("没有获取拍照权限");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZillaApplication.getRefWatcher(this).watch(this);
    }

    @Override
    protected void initViews() {
        imageView= (ImageView) findViewById(R.id.image1);
        imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.avatar));
        MPermission.with(this).setPermission(Manifest.permission.CAMERA).requestPermission();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityTransitionSender.build(TransitionActivity.this).from(imageView,R.drawable.avatar).launch(new Intent(TransitionActivity.this,ImageDetailActivity.class));
                //finish();
            }
        });
    }

    @Override
    protected void initDatas() {

    }
}

package com.zilla.libraryzilla.test.activitytransition;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.github.snowdream.android.util.Log;
import com.zilla.libraryzilla.R;
import com.zilla.libraryzilla.ZillaApplication;

import zilla.libjerry.activitytransition.transition.ActivityTransitionSender;
import zilla.libjerry.permission.MPermission;
import zilla.libjerry.permission.PermissionFail;
import zilla.libjerry.permission.PermissionOK;

public class TransitionActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        imageView= (ImageView) findViewById(R.id.image1);
        imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.avatar));
        MPermission.with(this).setPermission(Manifest.permission.CAMERA).requestPermission();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityTransitionSender.build(TransitionActivity.this).from(imageView,R.drawable.avatar).launch(new Intent(TransitionActivity.this,ImageDetailActivity.class));
            }
        });
    }

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
}

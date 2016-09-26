package com.zilla.libraryzilla.test.permission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.github.snowdream.android.util.Log;
import com.zilla.libraryzilla.R;
import com.zilla.libraryzilla.common.BaseFragment;

import zilla.libcore.ui.InjectLayout;
import zilla.libjerry.permission.MPermission;
import zilla.libjerry.permission.PermissionFail;
import zilla.libjerry.permission.PermissionOK;

@InjectLayout(R.layout.fragment_test)
public class TestFragmen extends BaseFragment {
    public TestFragmen() {
        // Required empty public constructor
    }

    String[] a={Manifest.permission.CAMERA};
    @Override
    protected void initView() {
//        if(ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED){
//            Log.i("没有相机权限");
//            requestPermissions(a,100);
//            //ActivityCompat.requestPermissions(getActivity(),a,100);
//
//        }

    }

    @Override
    protected void initData() {
        //MPermission.with(this).setPermission(Manifest.permission.CAMERA).requestPermission();
        getChildFragmentManager().beginTransaction().add(R.id.fragment,new TestFragmen1()).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("requestCode===="+requestCode);
        //MPermission.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

//    @PermissionOK
//    private void valdateSuccess(){
//        Log.i("拍照权限通过啦");
//
//    }
//
//    @PermissionFail
//    public void validateFail(){
//        Log.i("没有获取拍照权限");
//    }
}

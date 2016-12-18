package com.zilla.libraryzilla.test.selectorphoto;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.github.snowdream.android.util.Log;
import com.zilla.libraryzilla.R;
import com.zilla.libraryzilla.common.BaseActivity;

import java.io.File;
import java.util.List;

import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;
import zilla.libjerry.takephoto.ImageFileCropSelector;

@InjectLayout(R.layout.activity_selector_photo)
public class SelectorPhotoActivity extends BaseActivity {

    ImageFileCropSelector selector;

    @Override
    protected void initViews() {
        selector=new ImageFileCropSelector(this);
        selector.setQuality(80);//图片的压缩质量
        selector.setOutPutImageSize(100,100);//图片压缩后的输出大小
//        selector.setOpenCrop(this);//开启裁减
//        selector.setCropOutWH(100,100);//设置裁减的最大宽高
//        selector.setAspectXY(1,1);//设置裁减框的比例

        selector.setCallback(new ImageFileCropSelector.Callback() {


            @Override
            public void onTakePhotoSuccess(File file) {
                Log.i("图片返回成功"+file);
            }

            @Override
            public void onMutilSelectedSuccess(List<String> files) {

            }

            @Override
            public void onError() {
                Log.i("图片返回失败");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selector.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        selector.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
    @Override
    protected void initDatas() {

    }

    @OnClick({R.id.takephoto,R.id.selectorimage})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.takephoto:
                selector.takePhoto(this);
                break;
            case R.id.selectorimage:
                selector.selectImage(this);
                break;
            default:
                break;
        }
    }
}

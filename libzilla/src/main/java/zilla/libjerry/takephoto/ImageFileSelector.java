package zilla.libjerry.takephoto;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.File;
import java.util.List;

import zilla.libjerry.permission.MPermission;

public class ImageFileSelector {

    private static final String TAG = ImageFileSelector.class.getSimpleName();

    private Callback mCallback;
    private ImagePickHelper mImagePickHelper;
    private ImageCaptureHelper mImageTaker;
    private ImageCompressHelper mImageCompressHelper;

    public ImageFileSelector(final Context context) {
        mImagePickHelper = new ImagePickHelper();
        mImagePickHelper.setCallback(new ImagePickHelper.Callback() {

            @Override
            public void onMutilSussess(List<String> imgs) {
                handleMutilResult(imgs,false);
            }

            @Override
            public void onError() {
                handleError();
            }
        });

        mImageTaker = new ImageCaptureHelper();
        mImageTaker.setCallback(new ImageCaptureHelper.Callback() {
            @Override
            public void onSuccess(String file) {
                Log.i(TAG,"select image from camera: " + file);
                handleResult(file, true);
            }

            @Override
            public void onError() {
                handleError();
            }
        });

        mImageCompressHelper = new ImageCompressHelper(context);
        mImageCompressHelper.setCallback(new ImageCompressHelper.CompressCallback() {
            @Override
            public void onCallBack(File outFile) {
                Log.i(TAG, "compress image output: " + outFile);
                if (mCallback != null) {
                    mCallback.onTakePhoto(outFile);
                }
            }

            @Override
            public void onMutilCallBack(List<String> outFiles) {
                if (mCallback != null) {
                    mCallback.onMutilSelected(outFiles);
                }
            }
        });
    }

    /**
     * 设置压缩后的文件大小
     *
     * @param maxWidth  压缩后文件宽度
     * @param maxHeight 压缩后文件高度
     */
    @SuppressWarnings("unused")
    public void setOutPutImageSize(int maxWidth, int maxHeight) {
        mImageCompressHelper.setOutPutImageSize(maxWidth, maxHeight);
    }

    /**
     * 设置压缩后保存图片的质量
     *
     * @param quality 图片质量 0 - 100
     */
    @SuppressWarnings("unused")
    public void setQuality(int quality) {
        mImageCompressHelper.setQuality(quality);
    }

    /**
     * set image compress format
     *
     * @param compressFormat compress format
     */
    public void setCompressFormat(Bitmap.CompressFormat compressFormat) {
        mImageCompressHelper.setCompressFormat(compressFormat);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mImageTaker.onActivityResult(requestCode, resultCode, data);
        mImagePickHelper.onActivityResult(requestCode, resultCode, data);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mImageTaker.onRequestPermissionsResult(requestCode,permissions,grantResults);
        mImagePickHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onSaveInstanceState(Bundle outState) {
        mImageTaker.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mImageTaker.onRestoreInstanceState(savedInstanceState);
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    //这是图片单选
    public void selectImage(Activity activity) {
        mImagePickHelper.selectorMutilImage(activity,1);
    }

    public void selectImage(Fragment fragment) {
        mImagePickHelper.selectorMutilImage(fragment,1);
    }

    //下面是多图选择
    public void selectMutilImage(Activity activity, int limit) {
        mImagePickHelper.selectorMutilImage(activity,limit);
    }

    public void selectMutilImage(Fragment fragment, int limit) {
        mImagePickHelper.selectorMutilImage(fragment,limit);
    }

    public void takePhoto(Activity activity) {
        mImageTaker.captureImage(activity);
    }

    public void takePhoto(Fragment fragment) {
        mImageTaker.captureImage(fragment);
    }

    private void handleResult(String fileName, boolean deleteSrc) {
        File file = new File(fileName);
        if (file.exists()) {
            mImageCompressHelper.compress(fileName, deleteSrc);

        } else {
            if (mCallback != null) {
                mCallback.onError();
            }
        }
    }

    private void handleMutilResult(List<String> fileName, boolean deleteSrc) {
        if (!fileName.isEmpty()) {
            mImageCompressHelper.compressMutil(fileName, deleteSrc);
        } else {
            if (mCallback != null) {
                mCallback.onError();
            }
        }
    }

    private void handleError() {
        if (mCallback != null) {
            mCallback.onError();
        }
    }

    public interface Callback {
        void onTakePhoto(File file);
        void onMutilSelected(List<String> files);
        void onError();
    }

}

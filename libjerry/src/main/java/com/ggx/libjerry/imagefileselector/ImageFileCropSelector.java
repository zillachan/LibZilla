package com.ggx.libjerry.imagefileselector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.io.File;

/**
 * created by jerry.guan
 * 调用系统相册/拍照
 */
public class ImageFileCropSelector implements ImageCropHelper.ImageCropperCallback{

    private static final String TAG = ImageFileCropSelector.class.getSimpleName();

    private Callback mCallback;
    private ImagePickHelper mImagePickHelper;
    private ImageCaptureHelper mImageTaker;
    private ImageCompressHelper mImageCompressHelper;
    private ImageCropHelper mImageCropperHelper;


    public ImageFileCropSelector(final Context context) {
        mImagePickHelper = new ImagePickHelper(context);
        mImagePickHelper.setCallback(new ImagePickHelper.Callback() {
            @Override
            public void onSuccess(String file) {
                AppLogger.d(TAG, "select image from sdcard: " + file);
                handleResult(file, false);
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
            public void onCallBack(String outFile) {
                if (mCallback != null) {
                    mCallback.onSuccess(outFile);
                }
            }
        });

    }

    public static void setDebug(boolean debug) {
        AppLogger.DEBUG = debug;
    }

    private int mOutPutW;
    private int mOutPutH;
    private int mAspectX = 1;
    private int mAspectY = 1;

    //设置裁减的比例
    public void setAspectXY(int aspectX, int aspectY){
        this.mAspectX=aspectX;
        this.mAspectY = aspectY;
        if(mImageCropperHelper!=null){
            mImageCropperHelper.setOutPutAspect(aspectX,aspectY);
        }
    }

    //设置裁减的最大宽高
    public void setCropOutWH(int outW, int outH){
        this.mOutPutW=outW;
        this.mOutPutH = outH;
        if(mImageCropperHelper!=null){
            mImageCropperHelper.setOutPut(outW, outH);
        }
    }

    /**
     * 开启裁减功能
     */
    public void setOpenCrop(Activity activity){

        mImageCropperHelper =new ImageCropHelper();
        mImageCropperHelper.imageCropper(activity);
        mImageCropperHelper.setOutPut(mOutPutW, mOutPutH);
        mImageCropperHelper.setOutPutAspect(mAspectX,mAspectY);
        mImageCropperHelper.setCallback(this);

    }
    public void setOpenCrop(Fragment fragment){

        mImageCropperHelper =new ImageCropHelper();
        mImageCropperHelper.imageCropper(fragment);
        mImageCropperHelper.setOutPut(mOutPutW, mOutPutH);
        mImageCropperHelper.setOutPutAspect(mAspectX,mAspectY);
        mImageCropperHelper.setCallback(this);
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
    @SuppressWarnings("unused")
    public void setCompressFormat(Bitmap.CompressFormat compressFormat) {
        mImageCompressHelper.setCompressFormat(compressFormat);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mImagePickHelper.onActivityResult(requestCode, resultCode, data);
        mImageTaker.onActivityResult(requestCode, resultCode, data);
        if(mImageCropperHelper!=null){
            mImageCropperHelper.onActivityResult(requestCode,resultCode,data);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mImagePickHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mImageTaker.onRequestPermissionsResult(requestCode,permissions,grantResults);
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

    public void selectImage(Activity activity) {
        mImagePickHelper.selectorImage(activity);
    }

    public void selectImage(Fragment fragment) {
        mImagePickHelper.selectImage(fragment);
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
            if(mImageCropperHelper !=null){
                mImageCropperHelper.cropImage(file);
            }else {
                //压缩
                mImageCompressHelper.compress(fileName, true);
            }
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

    @Override
    public void onCropperCallback(ImageCropHelper.CropperResult result, File srcFile, File outFile, Uri outUri) {
        //压缩
        mImageCompressHelper.compress(outFile.getAbsolutePath(), true);
    }

    public interface Callback {
        void onSuccess(String file);

        void onError();
    }

    /**
     * 清楚所有缓存目录图片
     */
    public void clearImages(Context context){
        File imageCache=CommonUtils.getExternalImageCacheDir(context);
        CommonUtils.deleteFile(imageCache);
    }
}

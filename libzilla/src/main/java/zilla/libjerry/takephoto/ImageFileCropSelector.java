package zilla.libjerry.takephoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.io.File;
import java.util.List;

@SuppressWarnings("unused")
public class ImageFileCropSelector {

    private static final String TAG = ImageFileCropSelector.class.getSimpleName();

    private Callback mCallback;
    private ImagePickHelper mImagePickHelper;
    private ImageCaptureHelper mImageTaker;
    private ImageCompressHelper mImageCompressHelper;
    private ImageCropHelper mImageCropperHelper;


    public ImageCropHelper getmImageCropperHelper() {
        return mImageCropperHelper;
    }

    public ImageFileCropSelector(final Context context) {
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
                if (mCallback != null) {
                    mCallback.onTakePhotoSuccess(outFile);
                }
            }

            @Override
            public void onMutilCallBack(List<String> outFiles) {
                if (mCallback != null) {
                    mCallback.onMutilSelectedSuccess(outFiles);
                }
            }
        });
        mImageCropperHelper =new ImageCropHelper();
        mImageCropperHelper.setCallback(new ImageCropHelper.ImageCropperCallback() {
            @Override
            public void onCropperCallback(ImageCropHelper.CropperResult result, File srcFile, File outFile, Uri outUri) {
                //压缩
                mImageCompressHelper.compress(outFile.getAbsolutePath(), true);
            }
        });
    }


    public void setOutPut(int width, int height) {
       mImageCropperHelper.setOutPut(width, height);
    }

    public void setOutPutAspect(int width, int height) {
        mImageCropperHelper.setOutPutAspect(width, height);
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
        mImageCropperHelper.onActivityResult(requestCode,resultCode,data);

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

    public void selectImage(Activity activity) {
        mImagePickHelper.selectorMutilImage(activity,1);
        mImageCropperHelper.imageCropper(activity);
    }

    public void selectImage(Fragment fragment) {
        mImagePickHelper.selectorMutilImage(fragment,1);
        mImageCropperHelper.imageCropper(fragment);
    }

    public void takePhoto(Activity activity) {
        mImageTaker.captureImage(activity);
        mImageCropperHelper.imageCropper(activity);
    }

    public void takePhoto(Fragment fragment) {
        mImageTaker.captureImage(fragment);
        mImageCropperHelper.imageCropper(fragment);
    }

    private void handleResult(String fileName, boolean deleteSrc) {
        File file = new File(fileName);
        if (file.exists()) {
            if(mImageCropperHelper !=null){
                mImageCropperHelper.cropImage(file);
            }
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
        void onTakePhotoSuccess(File file);
        void onMutilSelectedSuccess(List<String> files);
        void onError();
    }

}

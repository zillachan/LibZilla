package com.ggx.libjerry.imagefileselector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;

/**
 * Created by John on 2016/4/27.
 * 图片裁剪助手
 */
public class ImageCropHelper {
    private static final String TAG = ImageCropper.class.getSimpleName();


    public enum CropperResult {
        /**
         * 裁切成功
         */
        success,
        /**
         * 输入文件错误
         */
        error_illegal_input_file,
        /**
         * 输出文件错误
         */
        error_illegal_out_file
    }

    private Fragment mFragment = null;
    private Activity mActivity = null;

    private int mOutPutX;
    private int mOutPutY;
    private int mAspectX = 1;
    private int mAspectY = 1;

    private File mSrcFile;
    private File mOutFile;

    /**
     * 记录裁切过程中产生的临时文件，裁切完成后进行删除
     */
    private File mTempFile;

    private ImageCropperCallback mCallback;

    public ImageCropHelper(){

    }

    public void imageCropper(Fragment fragment) {
        mFragment = fragment;
    }

    public void imageCropper(Activity activity) {
        mActivity = activity;
    }

    public void setOutPut(int width, int height) {
        mOutPutX = width<100?100:width;
        mOutPutY = height<100?100:height;
    }

    public void setOutPutAspect(int aspectX, int aspectY) {
        mAspectX = aspectX;
        mAspectY = aspectY;
    }

    public void setCallback(ImageCropperCallback callback) {
        mCallback = callback;
    }

    private Context getContext() {
        if (mActivity != null) {
            return mActivity;
        } else {
            return mFragment.getActivity();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            if (mTempFile != null && mTempFile.exists()) {
                mTempFile.delete();
            }
            Uri outUri=UCrop.getOutput(data);
            if (outUri != null ) {
                if (mCallback != null) {
                    mCallback.onCropperCallback(CropperResult.success, mSrcFile, mOutFile,outUri);
                }
            } else {
                if (mCallback != null) {
                    mCallback.onCropperCallback(CropperResult.error_illegal_out_file, mSrcFile, null,null);
                }
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void cropImage(File srcFile) {

        AppLogger.i(TAG, "------------------ start crop file ---------------");
        if (!(srcFile != null && srcFile.exists())) {
            AppLogger.i(TAG, "input file null or not exists ");
            if (mCallback != null) {
                mCallback.onCropperCallback(CropperResult.error_illegal_input_file, srcFile, null,null);
            }
            return;
        }

        File outFile = CommonUtils.generateExternalImageCacheFile(getContext(), ".jpg");
        AppLogger.i(TAG, "output file:" + outFile.getPath());
        if (outFile.exists()) {
            outFile.delete();
        }
        if (!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }

        mSrcFile = srcFile;
        mOutFile = outFile;
        Uri uri = Uri.fromFile(srcFile);

        if (uri.toString().contains("%")) {

            String inputFileName = srcFile.getName();
            String ext = inputFileName.substring(inputFileName.lastIndexOf("."));
            mTempFile = CommonUtils.generateExternalImageCacheFile(getContext(), ext);
            CommonUtils.copy(srcFile, mTempFile);
            uri = Uri.fromFile(mTempFile);
            AppLogger.w(TAG, "use temp file:" + mTempFile.getPath());
        }
        UCrop.Options options=new UCrop.Options();
        options.setShowCropGrid(false);
        options.setHideBottomControls(true);
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        options.setToolbarColor(ActivityCompat.getColor(getContext(), android.R.color.black));
        options.setToolbarTitle(" ");
        if (mActivity != null) {
            UCrop.of(uri,Uri.fromFile(outFile)).withMaxResultSize(mOutPutX,mOutPutY)
                    .withAspectRatio(mAspectX,mAspectY).withOptions(options).start(mActivity);
        } else {
            UCrop.of(uri,Uri.fromFile(outFile)).withMaxResultSize(mOutPutX,mOutPutY)
                    .withAspectRatio(mAspectX,mAspectY).withOptions(options).start(getContext(),mFragment);
        }
    }

    public interface ImageCropperCallback {
        void onCropperCallback(CropperResult result, File srcFile, File outFile, Uri outUri);
    }
}

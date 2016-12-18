package zilla.libjerry.takephoto;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;

import zilla.libcore.Zilla;

class ImageCaptureHelper {

    private static final String KEY_OUT_PUT_FILE = "key_out_put_file";
    private static final int CHOOSE_PHOTO_FROM_CAMERA = 0x702;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 0x703;

    private File mOutFile;
    private Callback mCallback;
    private WeakReference<Activity> mActivityWeakReference;
    private WeakReference<Fragment> mFragmentWeakReference;

    private static String[] permission={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void onSaveInstanceState(Bundle outState) {
        if (mOutFile != null) {
            outState.putString(KEY_OUT_PUT_FILE, mOutFile.getPath());
        }
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String tempFilePath = savedInstanceState.getString(KEY_OUT_PUT_FILE);
            if (!TextUtils.isEmpty(tempFilePath)) {
                mOutFile = new File(tempFilePath);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length == permissions.length) {
                for (int grant : grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        //只要有一个不允许就返回失败
                        if (mCallback != null) {
                            mCallback.onError();
                        }
                        return;
                    }
                }
                //权限都允许了
                if (mFragmentWeakReference != null) {
                    Fragment fragment = mFragmentWeakReference.get();
                    if (fragment != null) {
                        openCamera(fragment);
                    }
                } else if (mActivityWeakReference != null) {
                    Activity activity = mActivityWeakReference.get();
                    if (activity != null) {
                        openCamera(activity);
                    }
                } else if (mCallback != null) {
                    mCallback.onError();
                }
            } else {
                if (mCallback != null) {
                    mCallback.onError();
                }
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHOOSE_PHOTO_FROM_CAMERA && resultCode == Activity.RESULT_OK) {
            if (mOutFile != null && mOutFile.exists()) {
                saveImageToGallery(mOutFile);
                if (mCallback != null) {
                    mCallback.onSuccess(mOutFile.getPath());
                }
            } else {
                if (mCallback != null) {
                    mCallback.onError();
                }
            }
        }
    }
    public void captureImage(Activity activity) {
        mOutFile = CommonUtils.generateExternalImageCacheFile(activity, ".jpg");
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            //需要申请权限
            if(hasPermission(permission)!=-1){//表示不存在此权限
                mActivityWeakReference=new WeakReference(activity);
                ActivityCompat.requestPermissions(activity, permission,
                        CAMERA_PERMISSION_REQUEST_CODE);
            }else {
                openCamera(activity);
            }
        }else {
            openCamera(activity);
        }
    }

    public void captureImage(Fragment fragment) {
        mOutFile = CommonUtils.generateExternalImageCacheFile(fragment.getContext(), ".jpg");
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            //需要申请权限
            if(hasPermission(permission)!=-1){//表示不存在此权限
                mFragmentWeakReference=new WeakReference(fragment);
                fragment.requestPermissions(permission, CAMERA_PERMISSION_REQUEST_CODE);
            }else {
                openCamera(fragment);
            }
        }else {
            openCamera(fragment);
        }
    }

    private void openCamera(Activity activity){
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri out;
            if(Build.VERSION.SDK_INT<=23){
                out= Uri.fromFile(mOutFile);
            }else {
                out= FileProvider.getUriForFile(activity.getApplicationContext(),"zilla.libcore.fileprovider",mOutFile);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, out);
            activity.startActivityForResult(intent, CHOOSE_PHOTO_FROM_CAMERA);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            if (mCallback != null) {
                mCallback.onError();
            }
        }
    }

    private void openCamera(Fragment fragment){
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri out;
            if(Build.VERSION.SDK_INT<=23){
                out= Uri.fromFile(mOutFile);
            }else {
                out= FileProvider.getUriForFile(fragment.getContext().getApplicationContext(),"zilla.libcore.fileprovider",mOutFile);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, out);
            fragment.startActivityForResult(intent, CHOOSE_PHOTO_FROM_CAMERA);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            if (mCallback != null) {
                mCallback.onError();
            }
        }
    }

    private void saveImageToGallery(File image){
        Context context=null;
        if(mFragmentWeakReference!=null){
            Fragment fragment=mFragmentWeakReference.get();
            if(fragment!=null){
                context=fragment.getContext();
            }
        }else if(mActivityWeakReference!=null){
            Activity activity=mActivityWeakReference.get();
            if(activity!=null){
                context=activity.getApplicationContext();
            }
        }
        if(context!=null){
            try {
                MediaStore.Images.Media.insertImage(context.getContentResolver(),
                        image.getAbsolutePath(),image.getName(),null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(mOutFile)));
        }
    }

    //检查是否存在此权限
    private int hasPermission(String[] permissions) {
        int index=-1;
        for (int i=0,j=permissions.length;i<j;i++) {
            if (ActivityCompat.checkSelfPermission(Zilla.APP.getApplicationContext(), permissions[i])
                    != PackageManager.PERMISSION_GRANTED) {
                index=i;
                break;
            }
        }
        return index;
    }

    public interface Callback {

        void onSuccess(String fileName);

        void onError();
    }
}

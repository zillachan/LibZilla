package zilla.libjerry.imagefileselector;

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
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;

@SuppressWarnings("unused")
class ImageCaptureHelper {

    private static final String KEY_OUT_PUT_FILE = "key_out_put_file";
    private static final int CHOOSE_PHOTO_FROM_CAMERA = 0x702;
    private static final int CAMERA_PREMISSION = 0x12;

    private File mOutFile;
    private Callback mCallback;
    private WeakReference<Activity> mActivityWeakReference;
    private WeakReference<Fragment> mFragmentWeakReference;
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

    private void saveImageToGallery(File file){
        //把图片插入到系统图册
        Context context = null;
        if (mFragmentWeakReference != null) {
            Fragment fragment = mFragmentWeakReference.get();
            if (fragment != null) {
                context=fragment.getContext();
            }
        } else if (mActivityWeakReference != null) {
            Activity activity = mActivityWeakReference.get();
            if (activity != null) {
                context=activity.getApplicationContext();
            }
        }
        if(context!=null){
            try {
                MediaStore.Images.Media.insertImage(context.getContentResolver(),
                        file.getAbsolutePath(),file.getName(),null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(mOutFile)));
        }
    }
    public void captureImage(Activity activity) {
        mOutFile = CommonUtils.generateExternalImageCacheFile(activity, ".jpg");
        mActivityWeakReference = new WeakReference(activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                    //允许弹出提示
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PREMISSION);

                } else {
                    //不允许弹出提示
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PREMISSION);
                }
            } else {
                try {
                    activity.startActivityForResult(createIntent(), CHOOSE_PHOTO_FROM_CAMERA);
                } catch (ActivityNotFoundException e) {
                    AppLogger.printStackTrace(e);
                    if (mCallback != null) {
                        mCallback.onError();
                    }
                }
            }
        }else {
            try {
                activity.startActivityForResult(createIntent(), CHOOSE_PHOTO_FROM_CAMERA);
            } catch (ActivityNotFoundException e) {
                AppLogger.printStackTrace(e);
                if (mCallback != null) {
                    mCallback.onError();
                }
            }
        }
    }

    public void captureImage(Fragment fragment) {
        mOutFile = CommonUtils.generateExternalImageCacheFile(fragment.getContext(), ".jpg");
        mFragmentWeakReference = new WeakReference(fragment);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                if (ActivityCompat.shouldShowRequestPermissionRationale(fragment.getActivity(), Manifest.permission.CAMERA)) {
                    //允许弹出提示
                    ActivityCompat.requestPermissions(fragment.getActivity(),
                            new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PREMISSION);

                } else {
                    //不允许弹出提示
                    ActivityCompat.requestPermissions(fragment.getActivity(),
                            new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PREMISSION);
                }
            } else {
                try {
                    fragment.startActivityForResult(createIntent(), CHOOSE_PHOTO_FROM_CAMERA);
                } catch (ActivityNotFoundException e) {
                    AppLogger.printStackTrace(e);
                    if (mCallback != null) {
                        mCallback.onError();
                    }
                }
            }
        }else {
            try {
                fragment.startActivityForResult(createIntent(), CHOOSE_PHOTO_FROM_CAMERA);
            } catch (ActivityNotFoundException e) {
                AppLogger.printStackTrace(e);
                if (mCallback != null) {
                    mCallback.onError();
                }
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CAMERA_PREMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (mFragmentWeakReference != null) {
                    Fragment fragment = mFragmentWeakReference.get();
                    if (fragment != null) {
                        try {
                            fragment.startActivityForResult(createIntent(), CHOOSE_PHOTO_FROM_CAMERA);
                        } catch (ActivityNotFoundException e) {
                            AppLogger.printStackTrace(e);
                            if (mCallback != null) {
                                mCallback.onError();
                            }
                        }
                    }
                } else if (mActivityWeakReference != null) {
                    Activity activity = mActivityWeakReference.get();
                    if (activity != null) {
                        try {
                            activity.startActivityForResult(createIntent(), CHOOSE_PHOTO_FROM_CAMERA);
                        } catch (ActivityNotFoundException e) {
                            AppLogger.printStackTrace(e);
                            if (mCallback != null) {
                                mCallback.onError();
                            }
                        }
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

    private Intent createIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mOutFile));
        return intent;
    }

    public interface Callback {

        void onSuccess(String fileName);

        void onError();
    }
}

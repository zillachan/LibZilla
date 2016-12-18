package zilla.libjerry.takephoto;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.lang.ref.WeakReference;
import java.util.List;

import zilla.libjerry.takephoto.ablum.ImageGridActivity;


class ImagePickHelper {

    private static final int SELECT_PIC = 0x701;
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 0x11;

    private Callback mCallback;

    private WeakReference<Activity> mActivityWeakReference;
    private WeakReference<Fragment> mFragmentWeakReference;

    private int limit;

    public ImagePickHelper() {

    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void selectorMutilImage(Fragment fragment, int limit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                this.limit=limit;
                mFragmentWeakReference = new WeakReference<>(fragment);
                fragment.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_EXTERNAL_STORAGE_REQUEST_CODE);

            } else {
                doSelect(fragment,limit);
            }
        } else {
            doSelect(fragment,limit);
        }

    }

    public void selectorMutilImage(Activity activity, int limit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                this.limit=limit;
                mActivityWeakReference = new WeakReference<>(activity);
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_EXTERNAL_STORAGE_REQUEST_CODE);

            } else {
                doSelect(activity,limit);
            }
        } else {
            doSelect(activity,limit);
        }

    }

    private void doSelect(Activity activity, int limit){
        if(limit<=1){
            limit=1;
        }
        activity.startActivityForResult(new Intent(activity,
                    ImageGridActivity.class).putExtra("limit",limit),SELECT_PIC);

    }
    private void doSelect(Fragment fragment, int limit){
        if(limit<=1){
            limit=1;
        }
        fragment.startActivityForResult(new Intent(fragment.getContext(),
                ImageGridActivity.class).putExtra("limit",limit),SELECT_PIC);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == SELECT_PIC) {
            List<String> imgs=intent.getStringArrayListExtra("imgs");
            if (mCallback != null) {
                mCallback.onMutilSussess(imgs);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (mFragmentWeakReference != null) {
                    Fragment fragment = mFragmentWeakReference.get();
                    if (fragment != null) {
                        doSelect(fragment,limit);
                    }
                } else if (mActivityWeakReference != null) {
                    Activity activity = mActivityWeakReference.get();
                    if (activity != null) {
                        doSelect(activity,limit);
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

    public interface Callback {

        void onMutilSussess(List<String> imgs);

        void onError();
    }


}

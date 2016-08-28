package zilla.libjerry.activitytransition.model;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by John on 2016/8/28.
 *
 */
public class TransitionBundleFactory {
    public static final String TEMP_IMAGE_FILE_NAME = "activity_transition_image.png";

    public static Bundle  createTransitionBundle(Context context, ImageView fromImage,int resourceId) {
        File imageFile = saveImage(context, resourceId);
        //获取这个view的位置
        int[] screenLocation = new int[2];
        fromImage.getLocationInWindow(screenLocation);
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constant.IMAGE_FILE_KEY, imageFile);
        bundle.putSerializable(Constant.KEY_SCALE_TYPE, fromImage.getScaleType());
        bundle.putInt(Constant.KEY_THUMBNAIL_INIT_LEFT_POSITION, screenLocation[0]);
        bundle.putInt(Constant.KEY_THUMBNAIL_INIT_TOP_POSITION, screenLocation[1]);
        bundle.putInt(Constant.KEY_THUMBNAIL_INIT_WIDTH, fromImage.getWidth());
        bundle.putInt(Constant.KEY_THUMBNAIL_INIT_HEIGHT, fromImage.getHeight());
        return bundle;
    }


    private static File saveImage(final Context context, int resourceId) {
        File fileDir = context.getCacheDir();
        File file = new File(fileDir + File.separator + TEMP_IMAGE_FILE_NAME);
        if(file.exists()){
            file.delete();
        }
        saveIntoFile(context,file, resourceId);
        return file;
    }

    private static void saveIntoFile(Context mContext,File file, int resource) {
        try {
            InputStream inputStream = mContext.getResources().openRawResource(resource);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            byte buf[] = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                fileOutputStream.write(buf, 0, len);
            }

            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

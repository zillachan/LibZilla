package zilla.libjerry.takephoto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class ImageCompressHelper {

    private static final String TAG = ImageCompressHelper.class.getSimpleName();

    private int mMaxWidth = 1000;
    private int mMaxHeight = 1000;
    private int mQuality = 80;
    private Bitmap.CompressFormat mCompressFormat = null;

    private Context mContext;
    private CompressCallback mCallback;

    public ImageCompressHelper(Context context) {
        mContext = context;
    }

    public void setCallback(CompressCallback callback) {
        mCallback = callback;
    }

    /**
     * 设置压缩后的文件大小
     *
     * @param maxWidth  压缩后文件宽度
     * @param maxHeight 压缩后文件高度
     */
    public void setOutPutImageSize(int maxWidth, int maxHeight) {
        mMaxWidth = maxWidth;
        mMaxHeight = maxHeight;
    }

    public void setCompressFormat(Bitmap.CompressFormat compressFormat) {
        mCompressFormat = compressFormat;
    }

    /**
     * 设置压缩后保存图片的质量
     *
     * @param quality 图片质量 0 - 100
     */
    public void setQuality(int quality) {
        mQuality = quality;
    }

    public void compress(String fileName, boolean deleteSrc) {
        if (mMaxHeight <= 0 || mMaxWidth <= 0) {
            if (mCallback != null) {
                File outputFile = CommonUtils.generateExternalImageCacheFile(mContext, ".jpg");
                CommonUtils.copy(new File(fileName), outputFile);
                mCallback.onCallBack(outputFile);
            }
        } else {
            ImageFile srcImageFile = new ImageFile(fileName, deleteSrc);
            new CompressTask().execute(srcImageFile);
        }
    }
    public void compressMutil(List<String> fileNames, boolean deleteSrc) {
        ImageFile[] files=new ImageFile[fileNames.size()];
        for (int i=0;i<fileNames.size();i++){
            files[i]=new ImageFile(fileNames.get(i), deleteSrc);
        }
        new CompressMutilTask().execute(files);

    }

    private class CompressTask extends AsyncTask<ImageFile, Integer, File> {

        @Override
        protected File doInBackground(ImageFile... params) {
            Log.i(TAG, "------------------ start compress file ------------------");

            ImageFile srcFileInfo = params[0];

            Bitmap.CompressFormat format = mCompressFormat;
            if (format == null) {
                format = CompressFormatUtils.parseFormat(srcFileInfo.mSrcFilePath);
            }
            Log.i(TAG, "use compress format:" + format.name());

            File outputFile = CommonUtils.generateExternalImageCacheFile(mContext, CompressFormatUtils.getExt(format));
            File srcFile = new File(srcFileInfo.mSrcFilePath);
            boolean isCompress = compressImageFile(srcFileInfo.mSrcFilePath, outputFile.getPath(), mMaxWidth, mMaxHeight, mQuality, format);
            if (!isCompress) {
                // 没有压缩，直接copy
                CommonUtils.copy(srcFile, outputFile);
            }
            if (srcFileInfo.mDeleteSrc) {
                srcFile.delete();
            }
            return outputFile;
        }

        @Override
        protected void onPostExecute(File result) {
            if (mCallback != null) {
                mCallback.onCallBack(result);
            }
        }

    }

    private class CompressMutilTask extends AsyncTask<ImageFile, Integer, List<String>> {

        @Override
        protected List<String> doInBackground(ImageFile... params) {
            Log.i(TAG, "------------------ start compress file ------------------");
            List<String> outs=new ArrayList<>(params.length);
            for (ImageFile srcFileInfo:params){
                Bitmap.CompressFormat format = mCompressFormat;
                if (format == null) {
                    format = CompressFormatUtils.parseFormat(srcFileInfo.mSrcFilePath);
                }
                File outputFile = CommonUtils.generateExternalImageCacheFile(mContext, CompressFormatUtils.getExt(format));
                File srcFile = new File(srcFileInfo.mSrcFilePath);
                boolean isCompress = compressImageFile(srcFileInfo.mSrcFilePath, outputFile.getPath(), mMaxWidth, mMaxHeight, mQuality, format);
                if (!isCompress) {
                    // 没有压缩，直接copy
                    CommonUtils.copy(srcFile, outputFile);
                }
                if (srcFileInfo.mDeleteSrc) {
                    //noinspection ResultOfMethodCallIgnored
                    srcFile.delete();
                }
                outs.add(outputFile.getPath());
            }


            return outs;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            super.onPostExecute(result);
            if (mCallback != null) {
                mCallback.onMutilCallBack(result);
            }
        }

    }

    /**
     * 压缩图片文件
     *
     * @param srcFile   源文件
     * @param dstFile   目标文件
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return true进行了压缩，false无需压缩
     */
    public static boolean compressImageFile(String srcFile, String dstFile, int maxWidth, int maxHeight, int quality, Bitmap.CompressFormat compressFormat) {

        Log.i(TAG, "compress file:" + srcFile);
        Log.i(TAG, "file length:" + (int) (new File(srcFile).length() / 1024d) + "kb");
        Log.i(TAG, "output size:(" + maxWidth + ", " + maxHeight + ")");

        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        decodeOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(srcFile, decodeOptions);
        int actualWidth = decodeOptions.outWidth;
        int actualHeight = decodeOptions.outHeight;

        Log.i(TAG, "input size:(" + actualWidth + ", " + actualHeight + ")");

        if (actualWidth < maxWidth && actualHeight < maxHeight) {
            Log.i(TAG, "stop compress: input size < output size");
            return rotateImage(srcFile, dstFile, quality, compressFormat);
        }

        int sampleSize;
        int w;
        int h;
        if (actualWidth * maxHeight > maxWidth * actualHeight) {
            w = maxWidth;
            h = (int) (w * actualHeight / (double) actualWidth);
            sampleSize = (int) (actualWidth / (double) maxWidth);
        } else {
            h = maxHeight;
            w = (int) (h * actualWidth / (double) actualHeight);
            sampleSize = (int) (actualHeight / (double) maxHeight);
        }

        Log.i(TAG, "in simple size:" + sampleSize);

        decodeOptions.inJustDecodeBounds = false;
        decodeOptions.inSampleSize = sampleSize;
        decodeOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        decodeOptions.inPurgeable = true;
        decodeOptions.inInputShareable = true;
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(srcFile, decodeOptions);
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
            Log.i(TAG, "OutOfMemoryError:" + srcFile + ", size(" + actualWidth + ", " + actualHeight + ")");
        }

        if (bitmap == null) {
            Log.i(TAG, "stop compress:decode file error");
            return false;
        }

        Log.i(TAG, "origin bitmap size:(" + bitmap.getWidth() + ", " + bitmap.getHeight() + ")");

        if (bitmap.getWidth() > maxWidth || bitmap.getHeight() > maxHeight) {
            Bitmap tempBitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
            bitmap.recycle();
            bitmap = tempBitmap;
            Log.i(TAG, "scale down:(" + bitmap.getWidth() + ", " + bitmap.getHeight() + ")");
        }


        int degree = ImageUtils.getExifOrientation(srcFile);
        if (degree != 0) {
            Log.i(TAG, "rotate image from:" + degree);
            Bitmap rotate = ImageUtils.rotateImage(degree, bitmap);
            bitmap.recycle();
            bitmap = rotate;
        }

        ImageUtils.saveBitmap(bitmap, dstFile, compressFormat, quality);

        Log.i(TAG, "output file length:" + (int) (new File(dstFile).length() / 1024d) + "kb");
        Log.i(TAG, "------------------ compress file complete ---------------");
        return true;
    }

    private static boolean rotateImage(String imageFile, String outputFile, int quality, Bitmap.CompressFormat compressFormat) {
        int degree = ImageUtils.getExifOrientation(imageFile);
        if (degree != 0) {
            Log.i(TAG,"rotate image from:" + degree);
            Bitmap origin = BitmapFactory.decodeFile(imageFile);
            Bitmap rotate = ImageUtils.rotateImage(degree, origin);
            if (rotate != null) {
                ImageUtils.saveBitmap(rotate, outputFile, compressFormat, quality);
                rotate.recycle();
                origin.recycle();
                return true;
            } else {
                Log.i(TAG,"rotate image failed:" + imageFile);
                Log.i(TAG,"use origin image");
            }
            origin.recycle();
        }

        return false;
    }

    public interface CompressCallback {
        void onCallBack(File outFile);
        void onMutilCallBack(List<String> outFiles);
    }

    private class ImageFile {
        public final String mSrcFilePath;
        public final boolean mDeleteSrc;

        public ImageFile(String srcFilePath, boolean deleteSrc) {
            mSrcFilePath = srcFilePath;
            mDeleteSrc = deleteSrc;
        }
    }
}

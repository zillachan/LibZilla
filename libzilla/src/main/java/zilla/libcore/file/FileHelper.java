/*
Copyright 2015 Zilla Chen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package zilla.libcore.file;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.github.snowdream.android.util.Log;

import zilla.libcore.Zilla;
import zilla.libcore.util.Util;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 文件处理工具类<br>
 * 文件处理工具类,提供文件创建,删除,判断是否存在,拷贝,保存,读取;及sd卡状态等方法
 *
 * @author ze.chen
 * @version 1.0
 */
public class FileHelper {

    /**
     * 缓存路径，没有设为常量，在应用初始化的时候，根据具体应用做相应的目录变更
     */
    public static String PATH_CACHE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/%s/cache/";

    /**
     * 下载路径，没有设为常量，在应用初始化的时候，根据具体应用做相应的目录变更
     */
    public static String PATH_DOWNLOAD = Environment.getExternalStorageDirectory().getAbsolutePath() + "/%s/DOWNLOAD/";

    /**
     * 应用文件存储位置
     */
    public static final String PATH_FILES = Zilla.APP.getDir("files", 0).getAbsolutePath() + "/";

    /**
     * 应用图标存储位置
     */
    public static final String PATH_DRAWABLE = Zilla.APP.getDir("drawables", 0).getAbsolutePath() + "/";

    /**
     * 闪屏图片存储路径
     */
    public static final String PATH_LAUNCH = Zilla.APP.getDir("launch", 0).getAbsolutePath() + "/";

    /**
     * 如果需要Cookie记录用户信息，存储在这里
     */
    public static final String PATH_COOKIE = Zilla.APP.getDir("cookie", 0).getAbsolutePath() + "/";

    /**
     * 截图位置
     */
    public static final String PATH_CAPTURE = Zilla.APP.getDir("capture", 0).getAbsolutePath() + "/";

    /**
     * 路径初始化
     *
     * @param path pathName to create
     */
    public static void initPath(String path) {
        PATH_CACHE = String.format(PATH_CACHE, path);
        PATH_DOWNLOAD = String.format(PATH_DOWNLOAD, path);
    }

    /**
     * 由指定的路径和文件名创建文件
     *
     * @param path the path of file
     * @param name the name of file
     * @return the file
     * @throws java.io.IOException IOException
     */
    public static File createFile(String path, String name) throws IOException {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(path + name);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * 根据指定的路径创建文件
     *
     * @param filepath fullpath of file
     * @return the file
     * @throws java.io.IOException IOException
     */
    public static File createFile(String filepath) throws IOException {
        int last_seperate = filepath.lastIndexOf("/") + 1;
        String path = filepath.substring(0, last_seperate);
        String name = filepath.substring(last_seperate);
        return createFile(path, name);
    }

    /**
     * 删除指定文件
     *
     * @param path the path of file
     * @param name the name of file
     */
    public static void deleteFile(String path, String name) {
        if (!fileExist(path, name)) {
            return;
        }
        File file = new File(path + name);
        file.delete();
    }

    /**
     * 判断文件是否存在
     *
     * @param path the path of file
     * @param name the name of file
     * @return if the file exist
     */
    public static boolean fileExist(String path, String name) {
        File file = new File(path + name);
        if (file.exists() & !file.isDirectory()) {
            return true;
        }
        return false;
    }

    /**
     * 拷贝文件
     *
     * @param srcPath srcPath of file
     * @param desPaht desPathe of file
     * @return boolean if copy success
     */
    public static boolean copyFile(String srcPath, String desPaht) {
        try {
            File originFile = new File(srcPath);
            File destFile = new File(desPaht);
            String tempsrcName = originFile.getName();
            String tempsrcPath = originFile.getCanonicalPath().replace(tempsrcName, "");
            String tempdesName = destFile.getName();
            String tempdesPath = destFile.getPath().replace(tempdesName, "");
            return copyFile(tempsrcPath, tempsrcName, tempdesPath, tempdesName);
        } catch (Exception e) {
            Log.e("copyFileError", e);
        }
        return false;

    }

    /**
     * 拷贝文件
     *
     * @param srcPath src filePath
     * @param srcName src fileName
     * @param desPath des filePath
     * @param desName des fileName
     * @return boolean if copy success
     */
    public static boolean copyFile(String srcPath, String srcName, String desPath, String desName) {
        if (!fileExist(srcPath, srcName)) {
            return false;
        }

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            File inFile = new File(srcPath, srcName);
            File outFile = new File(desPath, desName);

            if (!fileExist(desPath, desName)) {
                createFile(desPath, desName);
            }

            fis = new FileInputStream(inFile);
            bis = new BufferedInputStream(fis);

            fos = new FileOutputStream(outFile);
            bos = new BufferedOutputStream(fos);

            byte[] buffer = new byte[1024 * 8];
            int len = -1;
            while ((len = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.closeStream(bis);
            Util.closeStream(fis);
            Util.closeStream(bos);
            Util.closeStream(fos);
        }
        return false;
    }


    /**
     * 保存bitmap成图片
     *
     * @param bitmap the bitmap object
     * @param name   fileName
     * @param path   filePath
     * @throws java.io.IOException IOException
     */
    public static void saveBitmap(Bitmap bitmap, String name, String path) throws IOException {
        if (bitmap == null) {
            return;
        }
        File file = createFile(path, name);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
        } finally {
            Util.closeStream(fos);
        }
    }

    /**
     * 保存bitmap成图片
     *
     * @param bitmap the bitmap object
     * @param name   fullPath of file
     * @throws java.io.IOException IOException
     */
    public static void saveBitmap(Bitmap bitmap, String name) throws IOException {
        if (bitmap == null) {
            return;
        }
        File f = new File(name);
        if (!f.exists()) {
            f.createNewFile();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(name);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
        } finally {
            Util.closeStream(fos);
        }
    }

    /**
     * 写入文件
     *
     * @param dataPath fullPath of file
     * @param data     the data to save
     * @throws java.io.IOException IOException
     */
    public static synchronized void saveData(String dataPath, String data) throws IOException {
        File file = createFile(dataPath);
        RandomAccessFile raf = new RandomAccessFile(file, "rws");
        // raf.seek(0);
        byte[] byteds = data.getBytes();
        raf.setLength(byteds.length);
        raf.write(byteds);
        Util.closeStream(raf);
    }

    /**
     * 读取文件内容，并以字符串形式返回（该方法去掉了注释：//和#）path或者file只要能获取绝对路径即可
     *
     * @param path filePath
     * @param file fileName
     * @return the string of the file
     * @throws java.io.IOException IOException
     */
    public static String readFile(String path, String file) throws IOException {
        if (path == null)
            path = "";
        if (file == null)
            file = "";
        FileInputStream fis = new FileInputStream(path + file);
        return readFromInputStream(fis);

    }

    /**
     * 将输入流内容写入字符串
     *
     * @param is inputStream
     * @return the string value of the inputStream
     * @throws java.io.IOException IOException
     */
    private static String readFromInputStream(InputStream is) throws IOException {
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        StringBuilder sb = new StringBuilder();
        try {
            inputReader = new InputStreamReader(is);
            bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                if (line.startsWith("//") || line.startsWith("#")) {
                    continue;
                }
                sb.append(line);
            }
        } finally {
            Util.closeStream(bufReader);
            Util.closeStream(inputReader);
            Util.closeStream(is);
        }
        return sb.toString();
    }

    /**
     * SD卡是否可用（挂载）
     *
     * @return if sdCard is mounted
     */
    public static boolean isSDCardMounted() {
        /*
         * Environment.MEDIA_MOUNTED // sd卡在手机上正常使用状态
		 * Environment.MEDIA_UNMOUNTED // 用户手工到手机设置中卸载sd卡之后的状态
		 * Environment.MEDIA_REMOVED // 用户手动卸载，然后将sd卡从手机取出之后的状态
		 * Environment.MEDIA_BAD_REMOVAL // 用户未到手机设置中手动卸载sd卡，直接拨出之后的状态
		 * Environment.MEDIA_SHARED // 手机直接连接到电脑作为u盘使用之后的状态
		 * Environment.MEDIA_CHECKINGS // 手机正在扫描sd卡过程中的状态
		 */
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * Calulate the size of a folder
     *
     * @param filePath the folder's path
     * @return the size of the folder
     */
    @SuppressLint("NewApi")
    public static long calculateFilePathSize(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
            return 0l;
        }
        return file.getTotalSpace();
    }

    /**
     * delete file/folder
     *
     * @param filePath the fullPath of file to be deleted
     */
    public static void clearFilePath(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            String[] children = file.list();
            if (children == null) {// 该目录没有子元素(空目录)
                file.delete();
                return;
            }
            for (String child : children) {
                clearFilePath(child);
            }
        }
    }

    /**
     * get the upgrade shell of sqlite
     *
     * @return the string list of /config/upgrade.sql file
     */
    public static ArrayList<String> getUpgradeSqls() {
        ArrayList<String> result = new ArrayList<String>();
        InputStream is = null;
        InputStreamReader reader = null;
        BufferedReader br = null;
        try {
            is = Zilla.APP.getAssets().open("/config/upgrade.sql");
            reader = new InputStreamReader(is);
            br = new BufferedReader(reader);
            String line = null;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("--")) {
                    result.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            Log.e(e.getMessage());
        } catch (IOException e) {
            Log.e(e.getMessage());
        } finally {
            Util.closeStream(br);
            Util.closeStream(reader);
            Util.closeStream(is);
        }
        return result;
    }

    /**
     * 序列化对象
     *
     * @param obj  the object to be saved
     * @param path fullPath the objected will be saved to
     * @return if saved success
     */
    public static boolean saveObj(Object obj, String path) {
        ObjectOutputStream objOutput = null;
        try {
            objOutput = new ObjectOutputStream(new FileOutputStream(path));
            objOutput.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            Util.closeStream(objOutput);
        }
        return true;
    }

    /**
     * 反序列化对象
     *
     * @param path the fullPath of an saved object
     * @return the object be saved
     */
    public static Object readObj(String path) {
        Object result = null;
        ObjectInputStream objInput = null;
        try {
            objInput = new ObjectInputStream(new FileInputStream(path));
            result = objInput.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Util.closeStream(objInput);
        }
        return result;
    }

    /**
     * 文件大小格式化B,K,M,G
     *
     * @param fileS fileSize
     * @return the formatted file style
     */
    public static String formetFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * @param context   上下文对象
     * @param fileUrl   文件的URI
     * @param mediaDATA MediaStore.Images.Media.DATA||MediaStore.Audio.Media.DATA||MediaStore.Video.Media.DATA
     * @return the absoult path
     */
    public static String getRealPath(Context context, Uri fileUrl, String mediaDATA) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat) {
            return getKitKatPath(context, fileUrl);
        }
        String fileName = null;
        Uri filePathUri = fileUrl;
        if (fileUrl != null) {
            if (fileUrl.getScheme().toString().compareTo("content") == 0)           //content://开头的uri
            {
                Cursor cursor = context.getContentResolver().query(fileUrl, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int column_index = cursor.getColumnIndexOrThrow(mediaDATA);
                    fileName = cursor.getString(column_index);          //取出文件路径
                    if (!fileName.startsWith("/mnt")) { //检查是否有”/mnt“前缀
                        fileName = "/mnt" + fileName;
                    }
                    cursor.close();
                }
            } else if (fileUrl.getScheme().compareTo("file") == 0)         //file:///开头的uri
            {
                fileName = filePathUri.toString().replace("file://", ""); //替换file://
                if (!fileName.startsWith("/mnt")) { //加上"/mnt"头
                    fileName += "/mnt";
                }
            }
        }
        return fileName;
    }


    /**
     * get real path for kitkat+
     */
    @SuppressLint("NewApi")
    private static String getKitKatPath(final Context context, final Uri uri) {
        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}

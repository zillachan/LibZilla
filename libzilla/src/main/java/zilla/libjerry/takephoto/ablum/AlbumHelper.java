package zilla.libjerry.takephoto.ablum;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Albums;
import android.provider.MediaStore.Images.Media;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 相册助手
 * 用来获取手机的缩略图以及相册的原图
 * @author jerry.Guan
 *         created by 2016/12/10
 */

public class AlbumHelper {

    private ContentResolver resolver;
    /**
     * 缩略图列表键值
     * 图像的image_id(原图的对应id)为key
     * 图像的路径为值
     */
    private HashMap<String, String> thumbnailList = new HashMap<>();
    HashMap<String, ImageBucket> bucketList = new HashMap<>();

    // 专辑列表
    List<HashMap<String, String>> albumList = new ArrayList<>();

    /**
     * 是否创建了图片集
     */
    boolean hasBuildImagesBucketList = false;


    public AlbumHelper(Context context) {
        this.resolver=context.getContentResolver();
    }

    //从数据库中获取图片缩略图
    public void getThumbnail(){
        String[] projection = { MediaStore.Images.Thumbnails._ID, MediaStore.Images.Thumbnails.IMAGE_ID,
                MediaStore.Images.Thumbnails.DATA };
        Cursor cursor=resolver.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                projection,null,null, MediaStore.Images.Thumbnails._ID+" desc");
        if(cursor.moveToFirst()){
            int _id;
            int image_id;
            String image_path;
            int _idColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails._ID);
            int image_idColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID);
            int dataColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
            do {
                _id=cursor.getInt(_idColumn);
                image_id=cursor.getInt(image_idColumn);
                image_path=cursor.getString(dataColumn);
                thumbnailList.put(String.valueOf(image_id),image_path);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    //从数据库中获取原图
    public void getAlbum(){
        String[] projection = { Albums._ID, Albums.ALBUM, Albums.ALBUM_ART,
                Albums.ALBUM_KEY, Albums.ARTIST, Albums.NUMBER_OF_SONGS };
        Cursor cursor = resolver.query(Albums.EXTERNAL_CONTENT_URI, projection, null,
                null, Albums._ID+" desc");
        if(cursor.moveToFirst()){
            int _id;
            String album ;
            String albumArt ;
            String albumKey;
            String artist;
            int numOfSongs;
            int _idColumn = cursor.getColumnIndex(Albums._ID);
            int albumColumn = cursor.getColumnIndex(Albums.ALBUM);
            int albumArtColumn = cursor.getColumnIndex(Albums.ALBUM_ART);
            int albumKeyColumn = cursor.getColumnIndex(Albums.ALBUM_KEY);
            int artistColumn = cursor.getColumnIndex(Albums.ARTIST);
            int numOfSongsColumn = cursor.getColumnIndex(Albums.NUMBER_OF_SONGS);
            do {
                _id = cursor.getInt(_idColumn);
                album = cursor.getString(albumColumn);
                albumArt = cursor.getString(albumArtColumn);
                albumKey = cursor.getString(albumKeyColumn);
                artist = cursor.getString(artistColumn);
                numOfSongs = cursor.getInt(numOfSongsColumn);

                HashMap<String, String> hash = new HashMap<>();
                hash.put("_id", _id + "");
                hash.put("album", album);
                hash.put("albumArt", albumArt);
                hash.put("albumKey", albumKey);
                hash.put("artist", artist);
                hash.put("numOfSongs", numOfSongs + "");
                albumList.add(hash);

            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    //获取所有相册集合
    public void buildImageBucketList(){
        //先获取缩略图索引
        getThumbnail();
        //查询获取相册索引
        String columns[] = new String[] {
                Media._ID,
                Media.BUCKET_ID,
                Media.PICASA_ID,
                Media.DATA,
                Media.DISPLAY_NAME,
                Media.TITLE,
                Media.SIZE,
                Media.BUCKET_DISPLAY_NAME };
        Cursor cursor = resolver.query(Media.EXTERNAL_CONTENT_URI, columns, null, null,
                Media._ID+" desc");
        if (cursor.moveToFirst()){
            // 获取指定列的索引
            int photoIDIndex = cursor.getColumnIndexOrThrow(Media._ID);
            int photoPathIndex = cursor.getColumnIndexOrThrow(Media.DATA);
            int photoNameIndex = cursor.getColumnIndexOrThrow(Media.DISPLAY_NAME);
            int photoTitleIndex = cursor.getColumnIndexOrThrow(Media.TITLE);
            int photoSizeIndex = cursor.getColumnIndexOrThrow(Media.SIZE);
            int bucketDisplayNameIndex = cursor
                    .getColumnIndexOrThrow(Media.BUCKET_DISPLAY_NAME);
            int bucketIdIndex = cursor.getColumnIndexOrThrow(Media.BUCKET_ID);
            int picasaIdIndex = cursor.getColumnIndexOrThrow(Media.PICASA_ID);
            // 获取图片总数
            int totalNum = cursor.getCount();
            do {
                String _id = cursor.getString(photoIDIndex);
                String name = cursor.getString(photoNameIndex);
                String path = cursor.getString(photoPathIndex);
                String title = cursor.getString(photoTitleIndex);
                String size = cursor.getString(photoSizeIndex);
                String bucketName = cursor.getString(bucketDisplayNameIndex);
                String bucketId = cursor.getString(bucketIdIndex);
                String picasaId = cursor.getString(picasaIdIndex);

                ImageBucket bucket = bucketList.get(bucketId);
                if (bucket == null) {
                    bucket = new ImageBucket();
                    bucketList.put(bucketId, bucket);
                    bucket.imageList = new ArrayList<>();
                    bucket.bucketName = bucketName;
                }
                bucket.count++;
                ImageItem imageItem = new ImageItem();
                imageItem.imageId = _id;
                imageItem.imagePath = path;
                imageItem.thumbnailPath = thumbnailList.get(_id);
                bucket.imageList.add(imageItem);
            }while (cursor.moveToNext());

        }
        cursor.close();
        hasBuildImagesBucketList = true;
    }

    /**
     * 得到图片集
     *
     * @param refresh
     * @return
     */
    public List<ImageBucket> getImagesBucketList(boolean refresh) {
        if (refresh || (!refresh && !hasBuildImagesBucketList)) {
            buildImageBucketList();
        }
        List<ImageBucket> tmpList = new ArrayList<>();
        ImageBucket bucket=new ImageBucket();
        bucket.bucketName="所有图片";
        bucket.count=0;
        bucket.imageList=new ArrayList<>();
        tmpList.add(bucket);
        Iterator<Map.Entry<String, ImageBucket>> itr = bucketList.entrySet()
                .iterator();
        while (itr.hasNext()) {
            Map.Entry<String, ImageBucket> entry = itr
                    .next();
            ImageBucket tmp=entry.getValue();
            bucket.count+=tmp.count;
            bucket.imageList.addAll(tmp.imageList);
            tmpList.add(entry.getValue());
        }
        return tmpList;
    }

    /**
     * 得到原始图像路径
     *
     * @param image_id
     * @return
     */
    String getOriginalImagePath(String image_id) {
        String path = null;
        String[] projection = { Media._ID, Media.DATA };
        Cursor cursor = resolver.query(Media.EXTERNAL_CONTENT_URI, projection,
                Media._ID + "=" + image_id, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(Media.DATA));
            cursor.close();
        }
        return path;
    }

}

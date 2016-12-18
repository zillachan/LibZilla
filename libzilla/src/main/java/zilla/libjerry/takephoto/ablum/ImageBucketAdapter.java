package zilla.libjerry.takephoto.ablum;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import zilla.libcore.R;

public class ImageBucketAdapter extends BaseAdapter {

	Context act;
	/**
	 * 图片集列表
	 */
	List<ImageBucket> dataList;
	BitmapCache cache;

	public ImageBucketAdapter(Context act, List<ImageBucket> list) {
		this.act = act;
		dataList = list;
		cache=new BitmapCache();
	}

	@Override
	public int getCount() {
		int count = 0;
		if (dataList != null) {
			count = dataList.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		if(dataList!=null){
			return dataList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup viewParent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(act).inflate(R.layout.item_single, viewParent,false);
			holder.iv = (ImageView) convertView.findViewById(R.id.iv_first_image);
			holder.selected = (CheckedTextView) convertView.findViewById(R.id.ctv);
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.count = (TextView) convertView.findViewById(R.id.count);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		ImageBucket item = dataList.get(position);
		holder.count.setText(item.count+"张");
		holder.name.setText(item.bucketName);

		if(item.isSelected){
			holder.selected.setChecked(true);
		}else {
			holder.selected.setChecked(false);
		}
		if (item.imageList != null && item.imageList.size() > 0) {
			String thumbPath = item.imageList.get(0).thumbnailPath;
			String sourcePath = item.imageList.get(0).imagePath;
			holder.iv.setTag(sourcePath);
			cache.displayBmp(holder.iv, thumbPath, sourcePath, new BitmapCache.ImageCallback() {
				@Override
				public void imageLoad(ImageView imageView, Bitmap bitmap, Object... params) {
					if (imageView != null && bitmap != null) {
						String url = (String) params[0];
						if (url != null && url.equals(imageView.getTag())) {
							imageView.setImageBitmap(bitmap);
						}
					}
				}
			});
		}
		return convertView;
	}
	static class Holder {
		private ImageView iv;
		private CheckedTextView selected;
		private TextView name;
		private TextView count;
	}
}

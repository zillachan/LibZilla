package zilla.libjerry.takephoto.ablum;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.sw926.imagefileselector.R;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

	private Context act;
	/**
	 * 图片集列表
	 */
	private List<ImageItem> dataList;
	BitmapCache cache;

	public ImageAdapter(Context act, List<ImageItem> list) {
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
			convertView = LayoutInflater.from(act).inflate(R.layout.item_image, viewParent,false);
			holder.iv = (ImageView) convertView.findViewById(R.id.image);
			holder.fl_selected= (FrameLayout) convertView.findViewById(R.id.fl_selected);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		ImageItem item = dataList.get(position);
		if(item.isSelected){
			holder.fl_selected.setVisibility(View.VISIBLE);
		}else {
			holder.fl_selected.setVisibility(View.GONE);
		}
		String thumbPath = item.thumbnailPath;
		String sourcePath = item.imagePath;
		holder.iv.setTag(item.imagePath);
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
		return convertView;
	}

	static class Holder {
		private ImageView iv;
		private FrameLayout fl_selected;
	}
}

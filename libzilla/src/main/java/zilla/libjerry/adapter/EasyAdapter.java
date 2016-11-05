package zilla.libjerry.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 通用的视图数据适配器
 * @author jerry.Guan
 * created by 2016/11/3
 */

public abstract class EasyAdapter<T> extends BaseAdapter{

    private Context context;
    private List<T> datas;//数据集合
    private int resource;//布局资源文件

    public EasyAdapter(Context context,List<T> datas,int resource) {
        this.datas = datas;
        this.context=context;
        this.resource=resource;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=ViewHolder.get(context,resource,convertView,parent);
        convert(holder,datas.get(position),position);
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder,T data,int position);
}

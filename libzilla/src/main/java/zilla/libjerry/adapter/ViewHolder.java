package zilla.libjerry.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 这是一个通用视图数据持有者
 * @author jerry.Guan
 * created by 2016/11/3
 */

public final class ViewHolder {

    private SparseArray<View> views;
    private View convertView;

    private ViewHolder(Context context,int resource,ViewGroup parent){
        views=new SparseArray<>();
        convertView=LayoutInflater.from(context).inflate(resource,parent,false);
        convertView.setTag(this);
    }

    public static ViewHolder get(Context context,int resource, View convertView, ViewGroup parent){
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder(context,resource,parent);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        return holder;
    }

    public <T extends View>T getView(int id){
        View view=views.get(id);
        if(view==null){
            view=convertView.findViewById(id);
            views.put(id,view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }
}

package zilla.libjerry.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * 通用的视图数据适配器
 *
 * @author jerry.Guan
 *         created by 2016/11/3
 */

public abstract class EasyAdapter<T> extends BaseAdapter {

    private Context context;
    private List<T> datas;//数据集合
    private int resource;//布局资源文件

    public EasyAdapter(Context context, List<T> datas, int resource) {
        this.datas = datas;
        this.context = context;
        this.resource = resource;
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
        ViewHolder holder = ViewHolder.get(context, resource, convertView, parent);
        convert(holder, datas.get(position), position);
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T data, int position);

    /**
     * 局部更新数据，调用一次getView()方法；Google推荐的做法
     *
     * @param listView 要更新的listview
     * @param position 要更新的位置
     */
    public void notifyDataSetChanged(ListView listView, int position) {
        /**第一个可见的位置**/
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        /**最后一个可见的位置**/
        int lastVisiblePosition = listView.getLastVisiblePosition();
        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /**获取指定位置view对象,
             * 通过position-firstVisiblePosition获得，原理在于listView中的view数量未必是adapter中的position数量，
             * 或许adapter中有20个选项，当前listview中只创建了8个view，
             * 此时当前listview的的可见范围view是第3个到第6个，我目前需要变更的是第10个item，那么获取到的view就是第7个。
             */
            View view = listView.getChildAt(position - firstVisiblePosition);
            getView(position, view, listView);
        }
    }

}

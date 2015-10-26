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
package zilla.libcore.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import zilla.libcore.ui.util.AdapterUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenze on 13-12-10.
 */
public class ZillaAdapter<T> extends BaseAdapter {

    protected List<T> list = new ArrayList<T>();
    protected LayoutInflater inflater;
    protected int resid = 0;
    protected Class<?> viewHolder;
    protected Context context;

    protected IZillaAdapterCallback iZillaAdapterCallback;
    /**
     * CheckBox事件，传入的模型中，字段值为1代表选中状态
     */
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    private ZillaAdapterButtonClickListener zillaAdapterButtonClickListener;

    public ZillaAdapterButtonClickListener getZillaAdapterButtonClickListener() {
        return zillaAdapterButtonClickListener;
    }

    public void setZillaAdapterButtonClickListener(ZillaAdapterButtonClickListener zillaAdapterButtonClickListener) {
        this.zillaAdapterButtonClickListener = zillaAdapterButtonClickListener;
    }

    public ZillaAdapter(Context context, List<T> list) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public ZillaAdapter(Context context, List<T> list, int resid) {
        this.list = list;
        this.resid = resid;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 构造函数
     * @param context context
     * @param list data
     * @param resid 控件必须有一个顶级的布局
     * @param c viewHolder class
     */
    public ZillaAdapter(Context context, List<T> list, int resid, Class c) {
        this.list = list;
        this.resid = resid;
        this.viewHolder = c;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = new AdapterUtil<T>(context).getView(
                list, inflater,
                resid, viewHolder, onCheckedChangeListener, zillaAdapterButtonClickListener,
                position, convertView, parent);
        if(iZillaAdapterCallback!=null){
            iZillaAdapterCallback.getView(position,view,parent);
        }
        return view;
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return onCheckedChangeListener;
    }

    /**
     * 设置checkbox监听事件，传入的模型中，字段值为1代表选中状态。Model可以通过view.getTag()获得
     *
     * @param onCheckedChangeListener onCheckedChangeListener
     */
    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }



    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getResid() {
        return resid;
    }

    public void setResid(int resid) {
        this.resid = resid;
    }

    public Class<?> getViewHolder() {
        return viewHolder;
    }

    public void setViewHolder(Class<?> viewHolder) {
        this.viewHolder = viewHolder;
    }

    public IZillaAdapterCallback getiZillaAdapterCallback() {
        return iZillaAdapterCallback;
    }

    public void setiZillaAdapterCallback(IZillaAdapterCallback iZillaAdapterCallback) {
        this.iZillaAdapterCallback = iZillaAdapterCallback;
    }

    /**
     * 为了方便后续调用，特作回调处理
     */
    public interface IZillaAdapterCallback{
        public void getView(int position, View convertView, ViewGroup parent);
    }

    public interface ZillaAdapterButtonClickListener {
        public void onZillaAdapterButtonClick(Button button, int position);
    }
}

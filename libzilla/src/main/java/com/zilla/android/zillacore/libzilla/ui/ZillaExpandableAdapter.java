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
package com.zilla.android.zillacore.libzilla.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenze on 13-12-10.
 */
public class ZillaExpandableAdapter<T, Child> extends BaseExpandableListAdapter {

    protected List<T> parentList = new ArrayList<T>();
    protected List<List<Child>> childList = new ArrayList<List<Child>>();

    protected int parentResId = 0;
    protected int childResId = 0;
    protected Class<?> parentViewHolder;
    protected Class<?> childViewHolder;

    protected Context context;
    protected LayoutInflater inflater;
    /**
     * CheckBox事件，传入的模型中，字段值为1代表选中状态
     */
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

    private ZillaAdapterButtonClickListener zillaAdapterButtonClickListener;

    protected IZillaAdapterCallback iZillaAdapterCallback;

    public ZillaAdapterButtonClickListener getZillaAdapterButtonClickListener() {
        return zillaAdapterButtonClickListener;
    }

    public void setZillaAdapterButtonClickListener(ZillaAdapterButtonClickListener zillaAdapterButtonClickListener) {
        this.zillaAdapterButtonClickListener = zillaAdapterButtonClickListener;
    }

    public ZillaExpandableAdapter(Context context, List<T> parent, int parentResId, Class parentViewHolder,
                                  List<List<Child>> child, int childResId, Class childViewHolder) {

        this.parentList = parent;
        this.childList = child;

        this.parentResId = parentResId;
        this.childResId = childResId;

        this.parentViewHolder = parentViewHolder;
        this.childViewHolder = childViewHolder;

        this.context = context;
        inflater = LayoutInflater.from(context);
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

    @Override
    public int getGroupCount() {
        return parentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return new AdapterUtil<T>(context).getView(
                parentList, inflater,
                parentResId, parentViewHolder, onCheckedChangeListener, zillaAdapterButtonClickListener,
                groupPosition, convertView, parent);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        List<Child> children = childList.get(groupPosition);
        View view = new AdapterUtil<Child>(context).getView(
                children, inflater,
                childResId, childViewHolder, onCheckedChangeListener, zillaAdapterButtonClickListener,
                childPosition, convertView, parent);
        if(iZillaAdapterCallback!=null){
            iZillaAdapterCallback.getView(groupPosition,childPosition,isLastChild,view,parent);
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
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
        public void getView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent);
    }
}

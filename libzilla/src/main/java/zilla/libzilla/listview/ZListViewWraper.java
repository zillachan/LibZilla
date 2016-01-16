/*
 * Copyright (c) 2015. Zilla Chen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zilla.libzilla.listview;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import zilla.libcore.ui.ZillaAdapter;
import zilla.libcore.R;

public abstract class ZListViewWraper<T> {
    private View rootView;
    protected List<T> modelList = new ArrayList<T>();
    protected ZillaAdapter adapter;
    protected ZListView zListView;
    protected int itemId;
    protected Class<?> holderClass;
    /**
     * prefer page size
     */
    public static int PAGE_SIZE = 20;

    public ZListViewWraper(View rootView, int resItem, Class<?> holderClass) {
        this.rootView = rootView;
        this.itemId = resItem;
        this.holderClass = holderClass;
        zListView = (ZListView) rootView.findViewById(R.id.zListView);
        init();
    }

    public void init() {
        adapter = new ZillaAdapter(rootView.getContext(), modelList, itemId, holderClass);
        zListView.setAdapter(adapter);
        zListView.setPullLoadEnable(false);
        zListView.showRefreshProgress();
        zListView.setXListViewListener(new XListViewListener());
        loadData();
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        adapter.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    public void showRefreshProgress() {
        zListView.showRefreshProgress();
    }

    public abstract void loadData();

    public abstract void loadMore();

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        zListView.setOnItemClickListener(onItemClickListener);
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        zListView.setOnItemLongClickListener(onItemLongClickListener);
    }

    /**
     * setXListViewListener
     *
     * @param listViewListener the listener
     */
    public void setXListViewListener(ZListView.IZEventListener listViewListener) {
        zListView.setXListViewListener(listViewListener);
    }

    public void refreshSuccess() {
        zListView.setRefreshTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        zListView.stopRefresh();
    }

    public void refreshFail() {
        zListView.stopRefresh();
    }

    public void loadmoreSuccess() {
        zListView.stopLoadMore();
    }

    public void setPullLoadEnable(boolean pullLoadEnable) {
        zListView.setPullLoadEnable(pullLoadEnable);
    }

    public List<T> getModelList() {
        return modelList;
    }

    public void setModelList(List<T> modelList) {
        this.modelList.clear();
        this.modelList.addAll(modelList);
        adapter.notifyDataSetChanged();
        refreshSuccess();
        if (modelList.size() < PAGE_SIZE) {
            setPullLoadEnable(false);
        } else {
            setPullLoadEnable(true);
        }
        if (modelList.size() == 0) {
            zListView.setBackgroundResource(R.drawable.xml_listview_no_data);
        } else {
            zListView.setBackgroundResource(R.drawable.transparent);
        }
    }

    /**
     * if preferSize is less than the size of modelList,stop pullLoad
     *
     * @param modelList  list
     * @param preferSize the size of a page
     */
    public void setModelList(List<T> modelList, int preferSize) {
        this.modelList.clear();
        this.modelList.addAll(modelList);
        adapter.notifyDataSetChanged();
        refreshSuccess();
        if (modelList.size() < preferSize) {
            setPullLoadEnable(false);
        } else {
            setPullLoadEnable(true);
        }
    }

    public void addModelList(List<T> modelList) {
        this.modelList.addAll(modelList);
        adapter.notifyDataSetChanged();
        refreshSuccess();
        if (modelList.size() < PAGE_SIZE) {
            setPullLoadEnable(false);
        } else {
            setPullLoadEnable(true);
        }
    }

    public void addModelList(List<T> modelList, int preferSize) {
        this.modelList.addAll(modelList);
        adapter.notifyDataSetChanged();
        refreshSuccess();
        if (modelList.size() < preferSize) {
            setPullLoadEnable(false);
        } else {
            setPullLoadEnable(true);
        }
    }

    public ZillaAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ZillaAdapter adapter) {
        this.adapter = adapter;
    }

    public ZListView getzListView() {
        return zListView;
    }

    public void setzListView(ZListView zListView) {
        this.zListView = zListView;
    }

    public void setVisibility(int visibility) {
        zListView.setVisibility(visibility);
    }

    public int getVisibility() {
        return zListView.getVisibility();
    }

    class XListViewListener implements ZListView.IZEventListener {

        @Override
        public void onRefresh() {
            loadData();
        }

        @Override
        public void onLoadMore() {
            loadMore();
        }
    }
}

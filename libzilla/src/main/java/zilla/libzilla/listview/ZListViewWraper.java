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

import zilla.libcore.R;
import zilla.libcore.ui.ZillaAdapter;

public abstract class ZListViewWraper<T> {
    private View rootView;
    protected List<T> modelList = new ArrayList<T>();
    protected ZillaAdapter adapter;
    protected ZListView zListView;
    protected int itemId;
    protected Class<?> holderClass;
    /**
     * preferSize
     */
    protected int preferSize = PAGE_SIZE;
    /**
     * empty resource id
     */
    protected int emptyResource;
    /**
     * prefer page size
     */
    public static int PAGE_SIZE = 20;

    public ZListViewWraper(View rootView, int listViewId, int resItem, Class<?> holderClass) {
        this.rootView = rootView;
        this.itemId = resItem;
        this.holderClass = holderClass;
        zListView = (ZListView) rootView.findViewById(listViewId);
        init();
    }

    public ZListViewWraper(View rootView, int resItem, Class<?> holderClass) {
        this.rootView = rootView;
        this.itemId = resItem;
        this.holderClass = holderClass;
        zListView = (ZListView) rootView.findViewById(R.id.zListView);
        init();
    }

    public void init() {
        if (this.onInitWrapper != null) {
            this.onInitWrapper.beforeInitAdapter();
        }
        adapter = new ZillaAdapter(rootView.getContext(), modelList, itemId, holderClass);
        zListView.setAdapter(adapter);
        zListView.setPullLoadEnable(false);
        zListView.showRefreshProgress();
        zListView.setZListViewListener(new ZListViewListener());
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
     * setZListViewListener
     *
     * @param listViewListener the listener
     */
    public void setZListViewListener(ZListView.IZEventListener listViewListener) {
        zListView.setZListViewListener(listViewListener);
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

    /**
     * if preferSize is less than the size of modelList,stop pullLoad
     *
     * @param modelList list
     */
    public void setModelList(List<T> modelList) {
        this.modelList.clear();
        this.modelList.addAll(modelList);
        this.setDataResponse();
    }

    public void addModelList(List<T> modelList) {
        this.modelList.addAll(modelList);
        this.setDataResponse();
    }

    private void setDataResponse() {
        adapter.notifyDataSetChanged();
        refreshSuccess();

        if (this.modelList.size() < preferSize) {
            setPullLoadEnable(false);
        } else {
            setPullLoadEnable(true);
        }
        if (this.modelList.size() == 0) {
            if (emptyResource > 1) {
                zListView.setBackgroundResource(emptyResource);
            } else if (emptyResource == 0) {
                zListView.setBackgroundResource(R.drawable.xml_listview_no_data);
            } else {
                zListView.setBackgroundResource(R.drawable.transparent);
            }
        } else {
            zListView.setBackgroundResource(R.drawable.transparent);
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

    public int getPreferSize() {
        return preferSize;
    }

    public void setPreferSize(int preferSize) {
        this.preferSize = preferSize;
    }

    public int getEmptyResource() {
        return emptyResource;
    }

    public void setEmptyResource(int emptyResource) {
        this.emptyResource = emptyResource;
    }

    class ZListViewListener implements ZListView.IZEventListener {

        @Override
        public void onRefresh() {
            loadData();
        }

        @Override
        public void onLoadMore() {
            loadMore();
        }
    }

    public void setOnInitWrapper(OnInitWrapper onInitWrapper) {
        this.onInitWrapper = onInitWrapper;
    }

    private OnInitWrapper onInitWrapper;

    /**
     * This will be called before instance adapter
     */
    public interface OnInitWrapper {
        void beforeInitAdapter();
    }
}

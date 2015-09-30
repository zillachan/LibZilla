/*
* 文件名：LoadDialog.java
* 版权：(C)版权所有2013-2013 zilla
* 描述：
* 修改人：ze.chen
* 修改时间：2013-10-10 下午11:28:20
*/
package com.zilla.libraryzilla.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.zilla.libraryzilla.R;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author zilla
 * @version 1.0
 * @date 2013-10-10 下午11:28:20
 */
public class LoadingDialogEntity extends Dialog {


    private TextView titleView;
    /**
     * <一句话功能简述>
     *
     * @param context
     */
    public LoadingDialogEntity(Context context) {
        super(context);
        init(context);
    }

    public LoadingDialogEntity(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    public LoadingDialogEntity(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    private void init(Context context) {
        setContentView(R.layout.dialog_loading);
        titleView = (TextView) findViewById(R.id.uilib_dialog_title);
    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title){
        titleView.setText(title);
    }
}

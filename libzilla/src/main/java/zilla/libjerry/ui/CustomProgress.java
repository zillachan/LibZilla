package zilla.libjerry.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import zilla.libcore.R;

/**
 * @author jerry.Guan
 * @date 2016/8/31
 */
public class CustomProgress extends Dialog{
    public CustomProgress(Context context) {
        super(context);
    }

    public CustomProgress(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
        // 获取ImageView上的动画背景
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        // 开始动画
        spinner.start();
    }

    /**
     * 给Dialog设置提示信息
     *
     * @param message
     */
    public void setMessage(CharSequence message) {
        if (!TextUtils.isEmpty(message)) {
            findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }

    /**
     * 弹出自定义ProgressDialog
     *
     * @param context
     *            上下文
     * @param message
     *            提示
     * @return
     */
    public static CustomProgress build(Context context, CharSequence message) {
        CustomProgress dialog = new CustomProgress(context, R.style.Custom_Progress);
        dialog.setTitle("");
        dialog.setContentView(R.layout.progress_dialog);
        if (TextUtils.isEmpty(message)) {
            dialog.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) dialog.findViewById(R.id.message);
            txt.setText(message);
        }
        dialog.setCanceledOnTouchOutside(false);
        // 设置居中
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        return dialog;
    }
}

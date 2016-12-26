package com.zilla.libraryzilla.test.gallery;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * @author jerry.Guan
 *         created by 2016/12/26
 */

public class MyRelative extends RelativeLayout{
    public MyRelative(Context context) {
        super(context);
    }

    public MyRelative(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRelative(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(ev);
    }
}

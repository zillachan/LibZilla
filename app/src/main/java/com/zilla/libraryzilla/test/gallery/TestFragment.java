package com.zilla.libraryzilla.test.gallery;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.snowdream.android.util.Log;
import com.zilla.libraryzilla.MainActivity;
import com.zilla.libraryzilla.R;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {


    public TestFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.activity_gallery)
    MyRelative relativeLayout;

    @BindView(R.id.viewpage)
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test2,container,false);
    }
    boolean flag=false;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager= (ViewPager) view.findViewById(R.id.viewpage);
        relativeLayout= (MyRelative) view.findViewById(R.id.activity_gallery);
        viewPager.setOffscreenPageLimit(8);
        final int px=getResources().getDisplayMetrics().widthPixels/4;
        Log.i("dev="+(px*4));
        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) viewPager.getLayoutParams();
        params.width=px;
        viewPager.setLayoutParams(params);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 8;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                ImageView iv=new ImageView(getContext());
                ViewPager.LayoutParams params=new ViewPager.LayoutParams();
                params.width=px;
                params.height=px;
                iv.setLayoutParams(params);
                iv.setImageResource(R.mipmap.ic_launcher);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        com.github.snowdream.android.util.Log.i("点击了"+position);
                    }
                });
                container.addView(iv,position);
                return iv;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                (container).removeView((ImageView)object);
            }

            @Override
            public float getPageWidth(int position) {
                return super.getPageWidth(position);
            }

        });

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("ScrollX====="+viewPager.getScrollX());
            }
        });

        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });
    }

}

package com.zilla.libraryzilla.test.gallery;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zilla.libraryzilla.R;
import com.zilla.libraryzilla.common.BaseActivity;
import com.zilla.libraryzilla.common.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_gallery)
public class GalleryActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener{

    @BindView(R.id.content)
    ViewPager content;

    List<Fragment> fragments=new ArrayList<>();
    @Override
    protected void initViews() {
        fragments.add(new TestFragment());
        fragments.add(new TestFragment2());
        content.setOffscreenPageLimit(2);
        content.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

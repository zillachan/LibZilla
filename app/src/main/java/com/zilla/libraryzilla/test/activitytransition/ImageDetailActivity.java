package com.zilla.libraryzilla.test.activitytransition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.zilla.libraryzilla.R;
import com.zilla.libraryzilla.common.BaseActivity;

import butterknife.Bind;
import zilla.libcore.ui.InjectLayout;
import zilla.libjerry.activitytransition.transition.ActivityTransitionReceiver;

@InjectLayout(R.layout.activity_image_detail)
public class ImageDetailActivity extends BaseActivity {

    @Bind(R.id.image2)
    ImageView image2;
    ActivityTransitionReceiver receiver;

    @Override
    protected void initViews() {
        receiver=ActivityTransitionReceiver.with(this).to(image2).start();
    }

    @Override
    protected void initDatas() {

    }


    @Override
    public void onBackPressed() {
        receiver.exit();

    }
}

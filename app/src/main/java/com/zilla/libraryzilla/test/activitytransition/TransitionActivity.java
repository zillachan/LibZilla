package com.zilla.libraryzilla.test.activitytransition;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.zilla.libraryzilla.R;

import zilla.libjerry.activitytransition.transition.ActivityTransitionSender;

public class TransitionActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        imageView= (ImageView) findViewById(R.id.image1);
        imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.avatar));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityTransitionSender.build(TransitionActivity.this).from(imageView,R.drawable.avatar).launch(new Intent(TransitionActivity.this,ImageDetailActivity.class));

            }
        });
    }


}

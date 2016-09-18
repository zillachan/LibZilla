package com.zilla.libraryzilla.test.permission;

import android.Manifest;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zilla.libraryzilla.R;
import com.zilla.libraryzilla.common.BaseFragment;

import zilla.libjerry.permission.MPermission;

public class PermissionActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        MPermission.with(this)
                .setPermission(Manifest.permission.READ_EXTERNAL_STORAGE
                ,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .requestPermission();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content,new TestFragmen())
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

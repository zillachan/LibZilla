package zilla.libjerry.activitytransition.transition;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import zilla.libjerry.activitytransition.animations.EnterScreenAnimations;
import zilla.libjerry.activitytransition.animations.ExitScreenAnimations;
import zilla.libjerry.activitytransition.model.Constant;

/**
 * Created by John on 2016/8/27.
 * 接收方
 */
public class ActivityTransitionReceiver {

    private static final String TAG=ActivityTransitionReceiver.class.getSimpleName();

    private EnterScreenAnimations mEnterScreenAnimations;
    private ExitScreenAnimations mExitScreenAnimations;
    private ImageView mTransitionImage;
    private ImageView toView;
    private View viewContainer;
    private Bundle bundle;
    private Activity activity;

    private ActivityTransitionReceiver(Activity activity){
        this.activity=activity;
        FrameLayout androidContent = (FrameLayout) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        this.viewContainer=/*androidContent*/androidContent.getChildAt(0);
        mTransitionImage = new ImageView(activity);
        androidContent.addView(mTransitionImage);

        bundle = activity.getIntent().getBundleExtra("viewBundle");
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        int thumbnailTop = bundle.getInt(Constant.KEY_THUMBNAIL_INIT_TOP_POSITION)
                - result;
        int thumbnailLeft = bundle.getInt(Constant.KEY_THUMBNAIL_INIT_LEFT_POSITION);
        int thumbnailWidth = bundle.getInt(Constant.KEY_THUMBNAIL_INIT_WIDTH);

        int thumbnailHeight = bundle.getInt(Constant.KEY_THUMBNAIL_INIT_HEIGHT);
        ImageView.ScaleType scaleType = (ImageView.ScaleType) bundle.getSerializable(Constant.KEY_SCALE_TYPE);

        // We set initial margins to the view so that it was situated at exact same spot that view from the previous screen were.
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mTransitionImage.getLayoutParams();
        layoutParams.height = thumbnailHeight;
        layoutParams.width = thumbnailWidth;
        layoutParams.setMargins(thumbnailLeft, thumbnailTop, 0, 0);
        mTransitionImage.setScaleType(scaleType);
        File file= (File) bundle.getSerializable(Constant.IMAGE_FILE_KEY);
        Picasso.with(activity).load(file).noFade().into(mTransitionImage);
    }

    public static ActivityTransitionReceiver with(Activity activity){
        return new ActivityTransitionReceiver(activity);
    }

    public ActivityTransitionReceiver to(ImageView toView){
        this.toView=toView;
        mEnterScreenAnimations = new EnterScreenAnimations(mTransitionImage,  toView,viewContainer);
        mExitScreenAnimations = new ExitScreenAnimations(mTransitionImage, toView, viewContainer);
        return this;
    }

    public ActivityTransitionReceiver start(){
        Picasso.with(activity).load((File) bundle.getSerializable(Constant.IMAGE_FILE_KEY)).into(toView, new Callback() {
            @Override
            public void onSuccess() {
                runEnteringAnimation();
            }

            @Override
            public void onError() {

            }
        });
        /*toView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            int mFrames = 0;

            @Override
            public boolean onPreDraw() {
                switch (mFrames++) {
                    case 0:
                        *//**
                         * 1. start animation on first frame
                         *//*
                        final int[] finalLocationOnTheScreen = new int[2];
                        toView.getLocationOnScreen(finalLocationOnTheScreen);

                        mEnterScreenAnimations.playEnteringAnimation(
                                finalLocationOnTheScreen[0], // left
                                finalLocationOnTheScreen[1], // top
                                toView.getWidth(),
                                toView.getHeight());

                        return true;
                    case 1:
                        *//**
                         * 2. Do nothing. We just draw this frame
                         *//*

                        return true;
                }
                *//**
                 * 3.
                 * Make view on previous screen invisible on after this drawing frame
                 * Here we ensure that animated view will be visible when we make the viw behind invisible
                 *//*
                toView.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });*/
        return this;
    }

    public void exit(){
        mEnterScreenAnimations.cancelRunningAnimations();
        int toTop = bundle.getInt(Constant.KEY_THUMBNAIL_INIT_TOP_POSITION);
        int toLeft = bundle.getInt(Constant.KEY_THUMBNAIL_INIT_LEFT_POSITION);
        int toWidth = bundle.getInt(Constant.KEY_THUMBNAIL_INIT_WIDTH);
        int toHeight = bundle.getInt(Constant.KEY_THUMBNAIL_INIT_HEIGHT);
        mExitScreenAnimations.playExitAnimations(
                toTop,
                toLeft,
                toWidth,
                toHeight,
                mEnterScreenAnimations.getInitialThumbnailMatrixValues());
    }

    private void runEnteringAnimation() {
        Log.v(TAG, "runEnteringAnimation, addOnPreDrawListener");

        toView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            int mFrames = 0;

            @Override
            public boolean onPreDraw() {
                // When this method is called we already have everything laid out and measured so we can start our animation
                Log.v(TAG, "onPreDraw, mFrames " + mFrames);

                switch (mFrames++) {
                    case 0:
                        /**
                         * 1. start animation on first frame
                         */
                        final int[] finalLocationOnTheScreen = new int[2];
                        toView.getLocationOnScreen(finalLocationOnTheScreen);

                        mEnterScreenAnimations.playEnteringAnimation(
                                finalLocationOnTheScreen[0], // left
                                finalLocationOnTheScreen[1], // top
                                toView.getWidth(),
                                toView.getHeight());

                        return true;
                    case 1:
                        /**
                         * 2. Do nothing. We just draw this frame
                         */

                        return true;
                }
                /**
                 * 3.
                 * Make view on previous screen invisible on after this drawing frame
                 * Here we ensure that animated view will be visible when we make the viw behind invisible
                 */
                toView.getViewTreeObserver().removeOnPreDrawListener(this);

                Log.v(TAG, "onPreDraw, << mFrames " + mFrames);

                return true;
            }
        });
    }

}

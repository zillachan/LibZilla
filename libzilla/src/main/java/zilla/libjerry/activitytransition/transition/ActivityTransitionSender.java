package zilla.libjerry.activitytransition.transition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import zilla.libjerry.activitytransition.model.TransitionBundleFactory;


/**
 * Created by John on 2016/8/26.
 * 此类用来代替 startActivity跳转
 */
public class ActivityTransitionSender {

    private Activity activity;
    private Bundle bundle;

    private ActivityTransitionSender(Activity activity){
        this.activity=activity;
    }
    /**
     * 定义构造开始点
     *
     * @return
     */
    public static ActivityTransitionSender build(Activity activity){
        return new ActivityTransitionSender(activity);
    }

    /**
     * 表示从哪个ImageView开启动画跳转
     * @param view
     */
    public ActivityTransitionSender from(ImageView view,int resourceId){
        bundle= TransitionBundleFactory.createTransitionBundle(activity,view,resourceId);
        return this;
    }



    /**
     * 开启跳转
     * @param intent
     */
    public void launch(Intent intent){
        if(bundle==null){
            throw new RuntimeException("bundle can not null!");
        }else {
            intent.putExtra("viewBundle",bundle);
            activity.startActivity(intent);
            activity.overridePendingTransition(0,0);
        }

    }


}

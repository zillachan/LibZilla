package zilla.libcore.api;

import android.content.DialogInterface;
import android.database.Observable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import zilla.libcore.Zilla;
import zilla.libcore.api.annotation.Dialog;
import zilla.libcore.api.eventModel.EventModel;
import zilla.libjerry.ui.CustomProgress;

/**
 * Created by Jerry.Guan on 2016/8/30.
 *
 */
public class ServiceProxy implements InvocationHandler,DialogInterface.OnCancelListener{

    private Object obj;
    CustomProgress progressDialog;
    EventBus eventBus;
    private List<Call> callList;

    public ServiceProxy(Object obj) {
        this.obj = obj;
        progressDialog=CustomProgress.build(Zilla.ACTIVITY,null);
        progressDialog.setOnCancelListener(this);
        eventBus=EventBus.getDefault();
        callList=new ArrayList<>();
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if(method.isAnnotationPresent(Dialog.class)){
            if(!eventBus.isRegistered(this)){
                EventBus.getDefault().register(this);
            }
            Dialog dialog=method.getAnnotation(Dialog.class);
            progressDialog.setMessage(dialog.value());
            if(!progressDialog.isShowing())
                progressDialog.show();
        }
        //代理方法返回值
        Object realObj=method.invoke(obj,objects);
        if(realObj instanceof Call){
            callList.add((Call) realObj);
        }
        InvocationHandler handler=new CallProxy(realObj);
        return Proxy.newProxyInstance(handler.getClass().getClassLoader(),realObj.getClass().getInterfaces(),handler);
    }

    @Subscribe
    public void onEvent(EventModel model){
        EventBus.getDefault().unregister(this);
        if(progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        callList.clear();
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        EventBus.getDefault().unregister(this);
        for (Call call:callList){
            call.cancel();
        }
        callList.clear();
    }
}

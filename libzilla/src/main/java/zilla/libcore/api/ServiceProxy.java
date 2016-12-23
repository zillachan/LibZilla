package zilla.libcore.api;

import android.content.DialogInterface;
import android.database.Observable;
import android.view.WindowManager;

import com.github.snowdream.android.util.Log;

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
    private List<Call> callList;

    public ServiceProxy(Object obj) {
        this.obj = obj;
        progressDialog=CustomProgress.build(Zilla.APP,null);
        progressDialog.setOnCancelListener(this);
        callList=new ArrayList<>();
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

        //代理方法返回值
        Object realObj=method.invoke(obj,objects);
        if(realObj instanceof Call){
            callList.add((Call) realObj);
        }
        CallProxy handler=new CallProxy(realObj,progressDialog);
        if(method.isAnnotationPresent(Dialog.class)){
            Dialog dialog=method.getAnnotation(Dialog.class);
            progressDialog.setMessage(dialog.value());
            handler.setShow(true);

        }
        return Proxy.newProxyInstance(handler.getClass().getClassLoader(),realObj.getClass().getInterfaces(),handler);
    }


    @Override
    public void onCancel(DialogInterface dialogInterface) {
        for (Call call:callList){
            call.cancel();
        }
        Log.i("dialog 被取消啦啦啦啦啦啦啦");
        callList.clear();
    }
}

package zilla.libcore.api;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import retrofit2.Callback;
import zilla.libcore.api.eventModel.EventModel;

/**
 * Created by jerry.guan on 2016/8/30.
 */
public class CallProxy implements InvocationHandler{

    private Object realObj;

    public CallProxy(Object realObj) {
        this.realObj = realObj;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        /**
         * 此处需要将CALLBACK方法参数对象进行代理
         * 并且将代理的对象重新设置为方法的参
         * 注意：这里需要判断同步和异步请求
         * 只有异步请求才需要重新设置方法参数
         */
        if(objects.length>0){
            Callback callback= (Callback) objects[0];
            /*InvocationHandler callbackHandler=new CallbackProxy(callback);
            Object callProxy= Proxy.newProxyInstance(callbackHandler.getClass().getClassLoader(),callback.getClass().getInterfaces(),callbackHandler);*/
            return method.invoke(realObj,new Object[]{new CallbackProxy(callback)});
        }else {
            String name=method.getName();
            if(name.equals("execute")) {
                Object result=method.invoke(realObj,objects);
                EventBus.getDefault().post(new EventModel());
                return result;
            }
            return method.invoke(realObj,objects);
        }
    }
}

package zilla.libcore.api;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import retrofit2.Callback;
import zilla.libjerry.ui.CustomProgress;

/**
 * Created by jerry.guan on 2016/8/30.
 */
public class CallProxy implements InvocationHandler{

    private Object realObj;

    private boolean isShow;
    private CustomProgress progress;
    public CallProxy(Object realObj,CustomProgress progress) {
        this.realObj = realObj;
        this.progress=progress;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        /**
         * 此处需要将CALLBACK方法参数对象进行代理
         * 并且将代理的对象重新设置为方法的参
         * 注意：这里需要判断同步和异步请求
         * 只有异步请求才需要重新设置方法参数
         */
        if(isShow&&progress!=null&&!progress.isShowing())
            progress.show();
        if(objects.length>0){
            Callback callback= (Callback) objects[0];
            return method.invoke(realObj,new Object[]{new CallbackProxy(callback,progress)});
        }else {
            String name=method.getName();
            //如果调用的是同步执行的代码
            if(name.equals("execute")) {
                Object result=method.invoke(realObj,objects);
                if(progress!=null&&progress.isShowing()){
                    progress.dismiss();
                }
                return result;
            }
            return method.invoke(realObj,objects);
        }
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}

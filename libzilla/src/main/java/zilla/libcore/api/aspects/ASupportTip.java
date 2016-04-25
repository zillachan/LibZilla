package zilla.libcore.api.aspects;


import com.github.snowdream.android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

import retrofit.RetrofitError;

/**
 * Created by Zilla on 25/4/16.
 */
@Aspect
public class ASupportTip {
    private static final String POINTCUT_METHOD =
            "execution(@zilla.libcore.api.SupportTip * *(RetrofitError)) && args(error)";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithTip(RetrofitError error) {

    }

    @After("methodAnnotatedWithTip(RetrofitError error)")
    public void afterSomething(RetrofitError error) {
        Log.d("error" + error.getMessage());
    }

//    @Around("methodAnnotatedWithTip()")
//    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
//        deal(joinPoint);
//    }

    static void deal(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Class[] parameterTypes = methodSignature.getParameterTypes();
        for (Class cc : parameterTypes) {

        }
//        //proceed
//        joinPoint.proceed();
//
//        //after
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        Method method = methodSignature.getMethod();
//        SupportLoading supportLoading = method.getAnnotation(SupportLoading.class);
////        SupportLoading supportLoading = (SupportLoading) joinPoint.getSignature().getDeclaringType().getAnnotation(SupportLoading.class);
//        if (supportLoading != null && supportLoading.autoDismiss()) {
//            if (iDialog != null) {
//                iDialog.dismiss();
//            }
//        }
//        Log.d(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + " finish");
    }
}

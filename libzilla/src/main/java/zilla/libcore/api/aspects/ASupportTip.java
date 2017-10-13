//package zilla.libcore.api.aspects;
//
//
//import pub.zilla.logzilla.Log;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import retrofit.RetrofitError;
//import zilla.libcore.api.ZillaApi;
//
//import java.lang.reflect.Method;
//
///**
// * Created by Zilla on 25/4/16.
// */
//@Aspect
//public class ASupportTip {
//    private static final String POINTCUT_METHOD =
//            "execution(@zilla.libcore.api.SupportTip * *(retrofit.RetrofitError)) && args(error)";
//
//    @Pointcut(value = POINTCUT_METHOD, argNames = "param")
//    public void methodAnnotatedWithTip(RetrofitError error) {
//
//    }
//
//    @After(value = "methodAnnotatedWithTip(error)", argNames = "error")
//    public void afterSomething(RetrofitError error) {
//        Log.d("error" + error.getMessage());
//        ZillaApi.dealNetError(error);
//    }
//}

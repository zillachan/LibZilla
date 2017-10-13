//package zilla.libcore.api.aspects;
//
//
//import pub.zilla.logzilla.Log;
//
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//
///**
// * Created by Zilla on 25/4/16.
// */
//@Aspect
//public class ASupportNetLoading {
////    //    && args(..,callback)
////    private static final String POINTCUT_PARAMETER =
////            "execution(* *(..,retrofit.Callback)) && @args(..,zilla.libcore.api.SupportNetLoading)";
////    //"execution(* *(..,@zilla.libcore.api.SupportNetLoading))";
////
////    @Pointcut(value = POINTCUT_PARAMETER)
////    public void methodAnnotatedWithLoading() {
////
////    }
//
//    @Pointcut(value = "execution(* * (..,@zilla.libcore.api.SupportNetLoading (*) ))")
//    public void methodWithAnnotationOnAtLeastOneParameter() {
////        Log.d("afterSomething");
////        Log.e("===show loading");
//    }
//
//    @Before("methodWithAnnotationOnAtLeastOneParameter()")
//    public void beforeMethod() {
//        Log.e("===show loading");
//    }
//
//}

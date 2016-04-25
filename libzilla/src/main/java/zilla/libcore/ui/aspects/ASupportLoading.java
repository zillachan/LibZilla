package zilla.libcore.ui.aspects;

import com.github.snowdream.android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.ui.SupportLoading;
import zilla.libzilla.dialog.IDialog;

/**
 * Description:
 *
 * @author Zilla
 * @version 1.0
 * @date 2016-04-21
 */
@Aspect
public class ASupportLoading {

    private static final String POINTCUT_METHOD =
            "execution(@zilla.libcore.ui.SupportLoading * *(..))";

    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@zilla.libcore.ui.SupportLoading *.new(..))";


    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithLoading() {

    }

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedLoading() {
    }

//    @Before("methodAnnotatedWithLoading")
//    public void beforeMethod(ProceedingJoinPoint joinPoint) {
//        Log.d("=============before==========");
//    }
//
//    @After("methodAnnotatedWithLoading()")
//    public void afterMethod(ProceedingJoinPoint joinPoint) {
//        Log.d("=============after==========");
//    }

    @Around("methodAnnotatedWithLoading() || constructorAnnotatedLoading()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        deal(joinPoint);
    }

    static void deal(ProceedingJoinPoint joinPoint) throws Throwable {
        IDialog iDialog = null;
        Object container = joinPoint.getTarget();

        Field[] fields = container.getClass().getFields();
        for (Field field : fields) {
            if (field.getAnnotation(LifeCircleInject.class) != null) {
                if (IDialog.class.isAssignableFrom(field.getType())) {
                    iDialog = (IDialog) field.get(container);
                    iDialog.show();
                }
            }
        }
        //proceed
        joinPoint.proceed();

        //after
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        SupportLoading supportLoading = method.getAnnotation(SupportLoading.class);
//        SupportLoading supportLoading = (SupportLoading) joinPoint.getSignature().getDeclaringType().getAnnotation(SupportLoading.class);
        if (supportLoading != null && supportLoading.autoDismiss()) {
            if (iDialog != null) {
                iDialog.dismiss();
            }
        }
        Log.d(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + " finish");
    }
}

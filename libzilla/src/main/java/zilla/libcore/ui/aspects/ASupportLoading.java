package zilla.libcore.ui.aspects;

import com.github.snowdream.android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.ui.SupportMethodLoading;
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
            "execution(@zilla.libcore.ui.SupportMethodLoading * *(..))";
//    private static final String POINTCUT_CONSTRUCTOR =
//            "execution(@zilla.libcore.ui.SupportMethodLoading *.new(..))";


    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithLoading() {

    }

//    @Pointcut(POINTCUT_CONSTRUCTOR)
//    public void constructorAnnotatedLoading() {
//    }

//    @Before("methodAnnotatedWithLoading")
//    public void beforeMethod(ProceedingJoinPoint joinPoint) {
//        Log.d("=============before==========");
//    }
//
//    @After("methodAnnotatedWithLoading()")
//    public void afterMethod(ProceedingJoinPoint joinPoint) {
//        Log.d("=============after==========");
//    }

    /**
     * 弹出对话框
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Around("methodAnnotatedWithLoading()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        deal(joinPoint);
    }

    /**
     * 关闭对话框
     *
     * @param joinPoint
     * @throws IllegalAccessException
     */
    @After("execution(@zilla.libcore.ui.SupportMethodLoading * *(..))")
    public void dismissDialog(ProceedingJoinPoint joinPoint) {
        try {
            Object container = joinPoint.getTarget();

            Field[] fields = container.getClass().getFields();
            for (Field field : fields) {
                if (field.getAnnotation(LifeCircleInject.class) != null) {
                    if (IDialog.class.isAssignableFrom(field.getType())) {
                        IDialog iDialog = (IDialog) field.get(container);
                        iDialog.dismiss();
                        return;
                    }
                }
            }
        } catch (Exception e) {
            Log.e(e.getMessage());
        }
    }

    static void deal(ProceedingJoinPoint joinPoint) throws Throwable {
        try {

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
            SupportMethodLoading supportLoading = method.getAnnotation(SupportMethodLoading.class);
//        SupportMethodLoading supportLoading = (SupportMethodLoading) joinPoint.getSignature().getDeclaringType().getAnnotation(SupportMethodLoading.class);
            if (supportLoading != null && supportLoading.autoDismiss()) {
                if (iDialog != null) {
                    iDialog.dismiss();
                }
            }
            Log.d(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + " finish");
        } catch (Exception e) {
            Log.e(e.getMessage());
        }
    }
}

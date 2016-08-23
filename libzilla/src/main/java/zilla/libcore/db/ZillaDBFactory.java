package zilla.libcore.db;

import java.lang.reflect.Proxy;

import zilla.libcore.db.handler.DBHandler;


/**
 * Factory class to implements dao methods.
 * Created by Zilla on 22/8/2016.
 */
public class ZillaDBFactory {
    public <T> T create(Class<T> service) {
        validateServiceClass(service);
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new DBHandler(service));
    }

    static <T> void validateServiceClass(Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("Only interface endpoint definitions are supported.");
        }
        // Prevent API interfaces from extending other interfaces. This not only avoids a bug in
        // Android (http://b.android.com/58753) but it forces composition of API declarations which is
        // the recommended pattern.
        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("Interface definitions must not extend other interfaces.");
        }
    }
}

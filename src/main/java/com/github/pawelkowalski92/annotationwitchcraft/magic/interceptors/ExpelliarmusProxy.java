package com.github.pawelkowalski92.annotationwitchcraft.magic.interceptors;

import com.github.pawelkowalski92.annotationwitchcraft.magic.spells.Expelliarmus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ExpelliarmusProxy implements InvocationHandler {

    private final Object target;

    public ExpelliarmusProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(target, args);
        } catch (InvocationTargetException ex) {
            if (disarmedExceptions(method).stream().anyMatch(it -> it.isInstance(ex.getCause()))) {
                return null;
            }
            throw ex;
        }
    }

    private List<Class<? extends Throwable>> disarmedExceptions(Method method) {
        Expelliarmus expelliarmus = method.getAnnotation(Expelliarmus.class);
        return expelliarmus == null
                ? List.of()
                : List.of(expelliarmus.exceptions());
    }

}

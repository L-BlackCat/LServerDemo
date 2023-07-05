package org.example.proxy.java_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxy<T> implements InvocationHandler {
    private T target;

    public JdkProxy(T target) {
        this.target = target;
    }

    public static <T> T getProxy(T t){
        Object obj = Proxy.newProxyInstance(t.getClass().getClassLoader(),
                t.getClass().getInterfaces(),
                new JdkProxy<>(t));
        return (T) obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("真正执行被代理对象的方法");
        Object invoke = method.invoke(target, args);
        System.out.println("代理对象的方法执行完了");
        return invoke;
    }

    public static void main(String[] args) {
        IAnimal dog = JdkProxy.getProxy(new Dog());
        dog.eat();
    }
}

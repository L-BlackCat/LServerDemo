package org.example.proxy.cglib_proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class CglibProxy {
    public static void main(String[] args) {

        // CGLib 在初始化被代理类时，是通过 Enhancer 对象把代理对象设置为被代理类的子类来实现动态代理的。
        // 因此被代理类不能被关键字 final 修饰，如果被 final 修饰，再使用 Enhancer 设置父类时会报错，动态代理的构建会失败。
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CglibDog.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args1, proxy) -> {
            System.out.println("DOg说谢谢");
            Object result = proxy.invokeSuper(obj, args1);
            System.out.println("Dog吃完了");
            return result;
        });
        CglibDog dog = (CglibDog) enhancer.create();
        dog.eat();
        // CGlib创建代理的速度比较慢，但创建代理之后运行的速度却非常快，而JDK动态代理刚好相反。
        // 如果在运行的时候不断地用CGlib去创建代理，系统的性能会大打折扣，所以建议一般在系统初始化的时候用CGlib去创建代理。


        /**
         * JDK Proxy和CGLib有什么区别？
         * JDK Proxy 是 Java 语言自带的功能，无需通过加载第三方类实现；
         * Java 对 JDK Proxy 提供了稳定的支持，并且会持续的升级和更新 JDK Proxy，例如 java 8 版本中的 JDK Proxy 性能相比于之前版本提升了很多；
         * JDK Proxy 是通过拦截器加反射的方式实现的；
         * JDK Proxy 只能代理继承接口的类；
         * JDK Proxy 实现和调用起来比较简单；
         * CGLib 是第三方提供的工具，基于 ASM 实现的，性能比较高；
         * CGLib 无需通过接口来实现，它是通过实现子类的方式来完成调用的。
         */
    }
}

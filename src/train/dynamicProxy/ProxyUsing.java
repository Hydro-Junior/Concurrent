package train.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Author: Mr.Xu
 * @Date: Created in 9:18 2019/3/18
 * @Description:
 */
public class ProxyUsing {
    public static void main(String[] args) {
        Subject sb = new SubjectImpl();
        //InvocationHandler myInvocationHandler = new MyInvocationHandler(sb);
        Subject proxy = (Subject) Proxy.newProxyInstance(SubjectImpl.class.getClassLoader(),SubjectImpl.class.getInterfaces(),new
                MyInvocationHandler(sb));
        proxy.test();
        System.out.println(proxy);
    }
}

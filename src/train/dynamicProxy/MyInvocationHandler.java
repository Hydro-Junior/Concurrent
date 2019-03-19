package train.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: Mr.Xu
 * @Date: Created in 9:15 2019/3/18
 * @Description:
 */
public class MyInvocationHandler implements InvocationHandler {
    Subject sb;
    MyInvocationHandler(Subject sb){
        this.sb = sb;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("前置处理");
        Object ob = method.invoke(sb,args);
        System.out.println("后置处理");
        return ob;
    }
}

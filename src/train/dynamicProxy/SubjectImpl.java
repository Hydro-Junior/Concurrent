package train.dynamicProxy;

/**
 * @Author: Mr.Xu
 * @Date: Created in 9:14 2019/3/18
 * @Description:
 */
public class SubjectImpl implements Subject {
    @Override
    public void test() {
        System.out.println("执行委托类方法");
    }
}

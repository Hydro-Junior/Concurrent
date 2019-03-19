import org.junit.Test;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * @Author: Mr.Xu
 * @Date: Created in 19:26 2018/10/29
 * @Description: 杂项测试(from <<think in java>>)
 */
public class OtherTest {

    /**
     * 系统的一些属性
     */
    @Test
    public void testProperties(){
        //System.getProperties().list(System.out);
        //System.out.println(System.getProperty("user.dir"));
        //System.out.println(System.getProperty("java.library.path"));
        //System.out.println(System.getProperty("java.ext.dirs"));
        TreeMap<String,Integer> map = new TreeMap<>();
        System.out.println(System.getProperty("java.class.path"));
        System.out.println("------------------------------------------");
        System.out.println(this.getClass().getClassLoader().getResource("").toString());
    }
    /**
        《深入理解计算机系统》中的案例，C表达式(3.14+1e20)-1e20求得的值会是0.0,而 3.14+(1e20-1e20)的值会是3.14，
         以此说明由于精度有限，浮点运算是不可结合的。这里用Java进行一番测试。
     */
    @Test
    public void testFloatCalculate(){
        double a = (3.14+1e20)-1e20;
        double b = 3.14+(1e20-1e20);
        System.out.println(a + " " + b);
    }

    public static void main(String[] args) {
        Object A = new Object(),B = new Object();
        new Thread(()->{
            synchronized (A){
                System.out.println("线程1拿到A");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B){
                    System.out.println("线程1拿到B");
                }
            }
        }).start();
        new Thread(()->{
            synchronized (B){
                System.out.println("线程2拿到B");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (A){
                    System.out.println("线程2拿到A");
                }
            }
        }).start();
    }
}

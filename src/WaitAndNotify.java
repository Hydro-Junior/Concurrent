import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Mr.Xu
 * @Date: Created in 11:26 2018/9/24
 * @Description:实现一个容器，提供两个方法，add,size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束。
 * 思路1：使用wait和notify，利用线程等待和通知实现
 *  Note : wait,notify只能在同步语句中调用，wait会释放锁，notify不会释放锁！
 * 思路2：门闩法，见CountDownLatchTest
 */
public class WaitAndNotify {
    volatile List list = new ArrayList();
    public void add(Object o){
        list.add(o);
    }
    public int size(){
        return list.size();
    }
    public static void main(String[] args){
        WaitAndNotify container = new WaitAndNotify();
        final Object lock = new Object();
        new Thread(()->{
            synchronized (lock){
                System.out.println("t2 启动");
                if(container.size() != 5){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 结束");
                lock.notify();//结束后通知t1继续执行
            }
        },"t2").start();
        try{
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            System.out.println("t1 启动");
            synchronized (lock){
                for(int i = 0; i < 10; i++){
                    container.add(new Object());
                    System.out.println("add" + i);
                    if(container.size() == 5){
                        lock.notify(); //这句执行后并不会释放锁
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try{
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        },"t1").start();

    }
}

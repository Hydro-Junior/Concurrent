import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Mr.Xu
 * @Date: Created in 17:25 2018/9/24
 * @Description:
 * @Description:实现一个容器，提供两个方法，add,size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束。
 * 思路1：使用wait和notify，利用线程等待和通知实现
 *  Note : wait会释放锁，notify不会释放锁！（sleep 不释放锁）
 * 思路2：门闩法，即本类，使用CountdownLatch实现（或者是CyclicBarrier Semaphore）
 */
public class CountdownLatchTest {
    volatile List list = new ArrayList();
    public void add(Object o){
        list.add(o);
    }
    public int size(){
        return list.size();
    }
    public static void main(String[] args) {
        CountdownLatchTest container = new CountdownLatchTest();
        CountDownLatch latch = new CountDownLatch(1);//当个数变为0
        new Thread(() -> {
                System.out.println("t2 启动");
                if (container.size() != 5) {
                    try {
                        latch.await();//拉上门闩，阻止线程继续运行
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 结束");
        }, "t2").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            System.out.println("t1 启动");
                for (int i = 0; i < 10; i++) {
                    container.add(new Object());
                    System.out.println("add" + i);
                    if (container.size() == 5) {
                        latch.countDown();
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }, "t1").start();
    }
}

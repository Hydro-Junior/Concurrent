import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Mr.Xu
 * @Date: Created in 12:02 2018/9/23
 * @Description: AtomicX类本身方法都为原子性（CAS实现），但不能保证多个方法连续调用是原子性的
 */
public class AtomicTest {
    //volatile int count = 0;
    AtomicInteger count = new AtomicInteger(0);
    /*synchronized*/ void m(){
        for(int i = 0 ; i < 10000; i++){
            count.incrementAndGet();
        }
    }
    public static void main(String[] args){
        AtomicTest t = new AtomicTest();
        List<Thread> threads = new ArrayList<Thread>();
        for(int i = 0; i < 10; i++){
            threads.add(new Thread(t::m,"thread-"+i));
        }
        threads.forEach((o)->o.start());
        threads.forEach((o)->{
            //o.start();//如果在这里启动线程，则可以正常输出100000
            try {
                o.join();//让线程依次执行,本质是让主线程进入该对象的等待池
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(t.count);
    }
}

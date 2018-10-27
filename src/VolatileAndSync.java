import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr.Xu
 * @Date: Created in 11:39 2018/9/23
 * @Description: volatile不能保证同步
 */
public class VolatileAndSync {
    volatile int count = 0;
    /*synchronized*/ void m(){
        for(int i = 0 ; i < 10000; i++){
            count ++;
        }
    }
    public static void main(String[] args){
        VolatileAndSync t = new VolatileAndSync();
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

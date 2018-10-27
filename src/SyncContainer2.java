import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Mr.Xu
 * @Date: Created in 16:52 2018/9/25
 * @Description:同步容器实现
 *
 * 写一个固定容量的同步容器，拥有put和get方法，以及getCount方法，
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 *
 * 使用Lock配合Condition实现,精确通知哪些线程该被叫醒 --> await 和 signalAll
 */
public class SyncContainer2<T>{
    final private LinkedList<T> list = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Condition producer  = lock.newCondition();
    private Condition consumer = lock.newCondition();

    public void put(T t){
        try{
            //ReentrantLock还有 tryLock()<返回boolean> lockInterruptibly()<可被打断，防止拿不到锁一直等待> 支持公平锁new ReentrantLock(true)
            lock.lock();
            while(list.size() == MAX){
                producer.await();
            }
            list.add(t);
            ++count;
            consumer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock(); //必须要手动释放！
        }

    }
    public T get(){
        T t = null;
        try{
            lock.lock();
            while(list.size() == 0){
                consumer.await();
            }
            t = list.removeFirst();
            count --;
            producer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return t;
    }
    public static void main(String[] args) {
        SyncContainer2<String> c = new SyncContainer2<>();
        //启动消费者线程(10个消费者，一次拿5个)
        for(int i = 0 ; i < 10; i++){
            new Thread(()->{
                for(int j = 0 ; j < 5; j++) System.out.println(c.get());
            },"c"+ i).start();
        }
        //启动生产者线程(2个生产者，一次生产25个)
        for(int i = 0 ; i < 2; i++){
            new Thread(()->{
                for(int j = 0 ;j < 25; j++) c.put(Thread.currentThread().getName() + " "+ j);
            }).start();
        }
    }
}

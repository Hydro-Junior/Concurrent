package train.hw20190317;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Mr.Xu
 * @Date: Created in 16:22 2019/3/17
 * @Description:
 */
public class MyContainerWithLock<T> {
    private static final int CAP = 10;
    private ReentrantLock lock = new ReentrantLock();
    private Condition consumer = lock.newCondition();
    private Condition producer = lock.newCondition();
    private List<T> list;
    MyContainerWithLock(){
        list = new LinkedList<>();
    }
    public void put(T t){
        try {
            lock.lock();
            while(list.size() == CAP){
                producer.await();
            }
            list.add(t);
            consumer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public T get(){
        T res = null;
        try {
            lock.lock();
            while(list.size() == 0){
                consumer.await();
            }
            res = list.remove(0);
            producer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return res;
    }

    public static void main(String[] args) {
        MyContainerWithLock<String> container = new MyContainerWithLock<>();
        for(int i = 0 ;  i < 10; i++){
            new Thread(()->{
                while(true){
                    container.put("0");
                    System.out.println(Thread.currentThread().getName()+"生产了一个O");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"生产者"+i).start();
        }
        for(int i = 0 ;  i < 8; i++){
            new Thread(()->{
                while(true){
                    String a = container.get();
                    System.out.println(Thread.currentThread().getName()+"消费了一个O");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"消费者"+i).start();
        }
    }
}

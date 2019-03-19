package train;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Mr.Xu
 * @Date: Created in 16:24 2019/2/23
 * @Description:重入锁实现生产者消费者模式
 */
public class ProCsm<T> {
    private final LinkedList<T> list = new LinkedList<>();
    private final int CAP = 10;
    private ReentrantLock lock = new ReentrantLock();
    Condition producer = lock.newCondition();
    Condition consumer = lock.newCondition();
    public void put(T t){
        try {
            lock.lock();
            while(list.size() == CAP){
                try {
                    producer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.push(t);
            consumer.signalAll();
        }finally {
            lock.unlock();
        }
    }
    public T get(){
        try {
            lock.lock();
            while(list.size() == 0){
                try {
                    consumer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = list.removeFirst();
            consumer.signalAll();
            return t;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ProCsm<Integer> container = new ProCsm<>();
        for(int i = 0 ; i < 3 ; i++){
            new Thread(()->{
                while (true){
                    int product = new Random().nextInt(500);
                    container.put(product);
                    String s = Thread.currentThread().getName();
                    System.out.println(s + " 放入 "+ product);
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"生产线程-"+i).start();
        }
        for(int i = 0; i < 5; i++){
            new Thread(()->{
                while (true){
                    int product = container.get();
                    String s = Thread.currentThread().getName();
                    System.out.println(s + " 取出 "+ product);
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"消费线程-"+i).start();
        }
    }
}

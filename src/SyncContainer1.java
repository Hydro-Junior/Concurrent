import java.util.LinkedList;

/**
 * @Author: Mr.Xu
 * @Date: Created in 16:08 2018/9/25
 * @Description: 同步容器初级实现
 * 写一个固定容量的同步容器，拥有put和get方法，以及getCount方法，
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 *
 * 使用wait/notify(notifyAll)来实现
 */
public class SyncContainer1<T>{
    final private LinkedList<T> list = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;

    public synchronized void put(T t){
        while (list.size() == MAX){//注意用while而非if
            try {
                this.wait();//wait在大多数情况下与while搭配---effective java
                /**
                 * 这里用while循环的具体原因，如果用的if，当多个线程同时被叫醒，都将继续执行add，导致超出容器范围！
                 */
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(t);
        ++count;
        this.notifyAll();
    }
    public synchronized T get(){
        T t = null;
        while (list.size() == 0){
            try{
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = list.removeFirst();
        count -- ;
        this.notifyAll();
        return t;
    }
    public int getCount(){
        return count;
    }
    public static void main(String[] args) {
        SyncContainer1<String> c = new SyncContainer1<>();
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

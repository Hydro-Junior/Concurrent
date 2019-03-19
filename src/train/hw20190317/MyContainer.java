package train.hw20190317;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Mr.Xu
 * @Date: Created in 15:52 2019/3/17
 * @Description:
 */
public class MyContainer<T> {
    private static final int CAP = 10;
    private List<T> list;
    MyContainer(){
        list = new LinkedList<>();
    }
    public synchronized T get(){
        T res;
        while(list.size() <= 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        res = list.remove(0);
        notifyAll();
        return res;
    }
    public synchronized void put(T t){
        while (list.size() >= CAP){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(t);
        notifyAll();
    }


    public static void main(String[] args) {
        final MyContainer<String> container = new MyContainer<>();
        for(int i = 0 ; i < 10; i++){
            new Thread(()->{
                while (true){
                    container.put("O");
                    System.out.println("生产了一个O");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        for(int i = 0 ; i < 5; i++){
            new Thread(()->{
                while(true){
                    String o = container.get();
                    System.out.println("消费了一个O");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }
    }
}

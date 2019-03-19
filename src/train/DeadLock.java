package train;

import sun.awt.windows.ThemeReader;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Mr.Xu
 * @Date: Created in 10:53 2019/3/12
 * @Description: 写个死锁并使用interrupt解除死锁，tryLock限制申请用时也可避免死锁
 */
public class DeadLock implements Runnable{
    private int lockTP ;
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();
    DeadLock(int lockType){
        lockTP = lockType;
    }
    @Override
    public void run() {
        if(lockTP == 1){
            try {
                lock1.lockInterruptibly();
                System.out.println("Thread1 get lock1!");
                Thread.sleep(500);
                lock2.lockInterruptibly();
                System.out.println("Thread1 get lock2!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                if(lock1.isHeldByCurrentThread()){
                    lock1.unlock();
                }
                if(lock2.isHeldByCurrentThread()){
                    lock2.unlock();
                }
            }
        }else{
            try {
                lock2.lockInterruptibly();
                System.out.println("Thread2 get lock2!");
                Thread.sleep(500);
                lock1.lockInterruptibly();
                System.out.println("Thread2 get lock1!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                if(lock1.isHeldByCurrentThread()){
                    lock1.unlock();
                }
                if(lock2.isHeldByCurrentThread()){
                    lock2.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        DeadLock thread1 = new DeadLock(1);
        DeadLock thread2 = new DeadLock(2);
        Thread  t1 = new Thread(thread1);
        Thread  t2 = new Thread(thread2);
        t1.start();
        t2.start();
        try {
            Thread.sleep(500);
            t1.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
